package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MAsset;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutConfirm;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MMatchInv;
import org.compiere.model.MMatchPO;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.model.MSequence;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class HBC_MInOut extends MInOut {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1027510521731559982L;

	public HBC_MInOut(MInOut original, int C_DocTypeShipment_ID,
			Timestamp movementDate) {
		super(original, C_DocTypeShipment_ID, movementDate);
	}
	public HBC_MInOut(MInvoice invoice, int C_DocTypeShipment_ID,
			Timestamp movementDate, int M_Warehouse_ID) {
		super(invoice, C_DocTypeShipment_ID, movementDate, M_Warehouse_ID);
	}

	public HBC_MInOut(MOrder order, int C_DocTypeShipment_ID,
			Timestamp movementDate) {
		super(order, C_DocTypeShipment_ID, movementDate);
	}
	public HBC_MInOut(Properties ctx, int M_InOut_ID, String trxName) {
		super(ctx, M_InOut_ID, trxName);
	}

	public HBC_MInOut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true or false
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		//	Warehouse Org
		if (newRecord)
		{	
			if(isSOTrx()==false)
				setDocumentNo(getDocumentNoRomanFormat());
		}

		boolean disallowNegInv = wh.isDisallowNegativeInv();
		String DeliveryRule = getDeliveryRule();
		if((disallowNegInv && DELIVERYRULE_Force.equals(DeliveryRule)) ||
				(DeliveryRule == null || DeliveryRule.length()==0))
			setDeliveryRule(DELIVERYRULE_Availability);

        // Shipment/Receipt can have either Order/RMA (For Movement type)
        if (getC_Order_ID() != 0 && getM_RMA_ID() != 0)
        {
            log.saveError("OrderOrRMA", "");
            return false;
        }

		//	Shipment - Needs Order/RMA
		if (!getMovementType().contentEquals(MInOut.MOVEMENTTYPE_CustomerReturns) && isSOTrx() && getC_Order_ID() == 0 && getM_RMA_ID() == 0)
		{
			log.saveError("FillMandatory", Msg.translate(getCtx(), "C_Order_ID"));
			return false;
		}

        if (isSOTrx() && getM_RMA_ID() != 0)
        {
            // Set Document and Movement type for this Receipt
            MRMA rma = new MRMA(getCtx(), getM_RMA_ID(), get_TrxName());
            MDocType docType = MDocType.get(getCtx(), rma.getC_DocType_ID());
            setC_DocType_ID(docType.getC_DocTypeShipment_ID());
        }

		return true;
	}	//	beforeSave

	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
	
		//	Outstanding (not processed) Incoming Confirmations ?
		MInOutConfirm[] confirmations = getConfirmations(true);
		for (int i = 0; i < confirmations.length; i++)
		{
			MInOutConfirm confirm = confirmations[i];
			if (!confirm.isProcessed())
			{
				if (MInOutConfirm.CONFIRMTYPE_CustomerConfirmation.equals(confirm.getConfirmType()))
					continue;
				//
				m_processMsg = "Open @M_InOutConfirm_ID@: " +
					confirm.getConfirmTypeName() + " - " + confirm.getDocumentNo();
				return DocAction.STATUS_InProgress;
			}
		}
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		StringBuilder info = new StringBuilder();

		//	For all lines
		MInOutLine[] lines = getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInOutLine sLine = lines[lineIndex];
			MProduct product = sLine.getProduct();

			//	Qty & Type
			String MovementType = getMovementType();
			BigDecimal Qty = sLine.getMovementQty();
			if (MovementType.charAt(1) == '-')	//	C- Customer Shipment - V- Vendor Return
				Qty = Qty.negate();

			//	Update Order Line
			HBC_MOrderLine oLine = null;
			if (sLine.getC_OrderLine_ID() != 0)
			{
				oLine = new HBC_MOrderLine (getCtx(), sLine.getC_OrderLine_ID(), get_TrxName());
				if (log.isLoggable(Level.FINE)) log.fine("OrderLine - Reserved=" + oLine.getQtyReserved()
					+ ", Delivered=" + oLine.getQtyDelivered());
			}


            // Load RMA Line
            MRMALine rmaLine = null;

            if (sLine.getM_RMALine_ID() != 0)
            {
                rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), get_TrxName());
            }

			if (log.isLoggable(Level.INFO)) log.info("Line=" + sLine.getLine() + " - Qty=" + sLine.getMovementQty());

			//	Stock Movement - Counterpart MOrder.reserveStock
			if (product != null
				&& product.isStocked() )
			{
				//Ignore the Material Policy when is Reverse Correction
				if(!isReversal())
				{
					BigDecimal movementQty = sLine.getMovementQty();
					BigDecimal qtyOnLineMA = MInOutLineMA.getManualQty(sLine.getM_InOutLine_ID(), get_TrxName());
					
					if ((movementQty.signum() != 0 && qtyOnLineMA.signum() != 0 && movementQty.signum() != qtyOnLineMA.signum()) // must have same sign
							|| (qtyOnLineMA.abs().compareTo(movementQty.abs())>0)) { // compare absolute values
					
						// More then line qty on attribute tab for line 10
						m_processMsg = "@Over_Qty_On_Attribute_Tab@ " + sLine.getLine();
						return DOCSTATUS_Invalid;
					}
					
					checkMaterialPolicy(sLine,movementQty.subtract(qtyOnLineMA));
				}

				log.fine("Material Transaction");
				MTransaction mtrx = null;
				
				//
				BigDecimal overReceipt = BigDecimal.ZERO;
				if (!isReversal()) 
				{
					if (oLine != null) 
					{
						BigDecimal toDelivered = oLine.getQtyOrdered()
								.subtract(oLine.getQtyDelivered());
						if (toDelivered.signum() < 0) // IDEMPIERE-2889
							toDelivered = Env.ZERO;
						if (sLine.getMovementQty().compareTo(toDelivered) > 0)
							overReceipt = sLine.getMovementQty().subtract(
									toDelivered);
						if (overReceipt.signum() != 0) 
						{
							sLine.setQtyOverReceipt(overReceipt);
							sLine.saveEx();
						}
					}
				} 
				else 
				{
					overReceipt = sLine.getQtyOverReceipt();
				}
				//@Stephan temporary comment
				//BigDecimal orderedQtyToUpdate = sLine.getMovementQty().subtract(overReceipt);
				//end
				if (sLine.getM_AttributeSetInstance_ID() == 0)
				{
					MInOutLineMA mas[] = MInOutLineMA.get(getCtx(),
						sLine.getM_InOutLine_ID(), get_TrxName());
					for (int j = 0; j < mas.length; j++)
					{
						MInOutLineMA ma = mas[j];
						BigDecimal QtyMA = ma.getMovementQty();
						if (MovementType.charAt(1) == '-')	//	C- Customer Shipment - V- Vendor Return
							QtyMA = QtyMA.negate();

						//	Update Storage - see also VMatch.createMatchRecord
						if (!MStorageOnHand.add(getCtx(), getM_Warehouse_ID(),
							sLine.getM_Locator_ID(),
							sLine.getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(),
							QtyMA,ma.getDateMaterialPolicy(),
							get_TrxName()))
						{
							String lastError = CLogger.retrieveErrorString("");
							m_processMsg = "Cannot correct Inventory OnHand (MA) [" + product.getValue() + "] - " + lastError;
							return DocAction.STATUS_Invalid;
						}					
						
						//	Create Transaction
						mtrx = new MTransaction (getCtx(), sLine.getAD_Org_ID(),
							MovementType, sLine.getM_Locator_ID(),
							sLine.getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),
							QtyMA, getMovementDate(), get_TrxName());
						mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
						if (!mtrx.save())
						{
							m_processMsg = "Could not create Material Transaction (MA) [" + product.getValue() + "]";
							return DocAction.STATUS_Invalid;
						}
					}
					
					if (oLine!=null && mtrx!=null && oLine.getQtyOrdered().signum() > 0)
					{					
						if (sLine.getC_OrderLine_ID() != 0)
						{
							/*	@Stephan temporary comment
							if (!MStorageReservation.add(getCtx(), oLine.getM_Warehouse_ID(),
									sLine.getM_Product_ID(),
									oLine.getM_AttributeSetInstance_ID(),
									orderedQtyToUpdate.negate(),
									isSOTrx(),
									get_TrxName()))
							{
								String lastError = CLogger.retrieveErrorString("");
								m_processMsg = "Cannot correct Inventory " + (isSOTrx()? "Reserved" : "Ordered") + " (MA) - [" + product.getValue() + "] - " + lastError;
								return DocAction.STATUS_Invalid;
							}
							 */
						}
					}
					
				}
				//	sLine.getM_AttributeSetInstance_ID() != 0
				if (mtrx == null)
				{
					//	@Stephan IDEMPIERE-3037
					/*
					Timestamp dateMPolicy = getMovementDate();
					if(sLine.getM_AttributeSetInstance_ID()>0){
						I_M_AttributeSetInstance asi = sLine.getM_AttributeSetInstance();
						dateMPolicy = asi.getCreated();
					}*/
					Timestamp dateMPolicy= null;
					MStorageOnHand[] storages = null;
					if (sLine.getMovementQty().compareTo(Env.ZERO) > 0) {
						// Find Date Material Policy bases on ASI
						storages = MStorageOnHand.getWarehouse(getCtx(), 0,
								sLine.getM_Product_ID(), sLine.getM_AttributeSetInstance_ID(), null,
								MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), false,
								sLine.getM_Locator_ID(), get_TrxName());
					} else {
						//Case of reversal
						storages = MStorageOnHand.getWarehouse(getCtx(), 0,
								sLine.getM_Product_ID(), sLine.getM_AttributeSetInstance_ID(), null,
								MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), false,
								sLine.getM_Locator_ID(), get_TrxName());
					}
					for (MStorageOnHand storage : storages) {
						if (storage.getQtyOnHand().compareTo(sLine.getMovementQty()) >= 0) {
							dateMPolicy = storage.getDateMaterialPolicy();
							break;
						}
					}

					if (dateMPolicy == null && storages.length > 0)
						dateMPolicy = storages[0].getDateMaterialPolicy();

					if (dateMPolicy==null && sLine.getM_AttributeSetInstance_ID()!=0) {
						I_M_AttributeSetInstance asi = sLine.getM_AttributeSetInstance();
						dateMPolicy = asi.getCreated();
					} else if(dateMPolicy==null)
						dateMPolicy = getMovementDate();
					//	@Stephan end
					
					//	Fallback: Update Storage - see also VMatch.createMatchRecord
					if (!MStorageOnHand.add(getCtx(), getM_Warehouse_ID(),
						sLine.getM_Locator_ID(),
						sLine.getM_Product_ID(),
						sLine.getM_AttributeSetInstance_ID(),
						Qty,dateMPolicy,get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory OnHand [" + product.getValue() + "] - " + lastError;
						return DocAction.STATUS_Invalid;
					}
					if (oLine!=null && oLine.getQtyOrdered().signum() > 0)  
					{
						/*	@Stephan temporary comment
						if (!MStorageReservation.add(getCtx(), oLine.getM_Warehouse_ID(),
								sLine.getM_Product_ID(),
								oLine.getM_AttributeSetInstance_ID(),
								orderedQtyToUpdate.negate(), isSOTrx(), get_TrxName()))
						{
							m_processMsg = "Cannot correct Inventory Reserved " + (isSOTrx()? "Reserved [" :"Ordered [") + product.getValue() + "]";
							return DocAction.STATUS_Invalid;
						}
						*/
					}
					
					//	FallBack: Create Transaction
					mtrx = new MTransaction (getCtx(), sLine.getAD_Org_ID(),
						MovementType, sLine.getM_Locator_ID(),
						sLine.getM_Product_ID(), sLine.getM_AttributeSetInstance_ID(),
						Qty, getMovementDate(), get_TrxName());
					mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
					if (!mtrx.save())
					{
						m_processMsg = CLogger.retrieveErrorString("Could not create Material Transaction [" + product.getValue() + "]");
						return DocAction.STATUS_Invalid;
					}
				}
			}	//	stock movement

			//	Correct Order Line
			if (product != null && oLine != null)		//	other in VMatch.createMatchRecord
			{
				oLine.setQtyReserved(oLine.getQtyReserved().subtract(sLine.getMovementQty().subtract(sLine.getQtyOverReceipt())));
			}

			//	Update Sales Order Line
			if (oLine != null)
			{
				if (isSOTrx()							//	PO is done by Matching
					|| sLine.getM_Product_ID() == 0)	//	PO Charges, empty lines
				{
					if (isSOTrx())
						oLine.setQtyDelivered(oLine.getQtyDelivered().subtract(Qty));
					else
						oLine.setQtyDelivered(oLine.getQtyDelivered().add(Qty));
					oLine.setDateDelivered(getMovementDate());	//	overwrite=last
				}
				if (!oLine.save())
				{
					m_processMsg = "Could not update Order Line";
					return DocAction.STATUS_Invalid;
				}
				else
					if (log.isLoggable(Level.FINE)) log.fine("OrderLine -> Reserved=" + oLine.getQtyReserved()
						+ ", Delivered=" + oLine.getQtyReserved());
			}
            //  Update RMA Line Qty Delivered
            else if (rmaLine != null)
            {
                if (isSOTrx())
                {
                    rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().add(Qty));
                }
                else
                {
                    rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().subtract(Qty));
                }
                if (!rmaLine.save())
                {
                    m_processMsg = "Could not update RMA Line";
                    return DocAction.STATUS_Invalid;
                }
            }

			//	Create Asset for SO
			if (product != null
				&& isSOTrx()
				&& product.isCreateAsset()
				&& !product.getM_Product_Category().getA_Asset_Group().isFixedAsset()
				&& sLine.getMovementQty().signum() > 0
				&& !isReversal())
			{
				log.fine("Asset");
				info.append("@A_Asset_ID@: ");
				int noAssets = sLine.getMovementQty().intValue();
				if (!product.isOneAssetPerUOM())
					noAssets = 1;
				for (int i = 0; i < noAssets; i++)
				{
					if (i > 0)
						info.append(" - ");
					int deliveryCount = i+1;
					if (!product.isOneAssetPerUOM())
						deliveryCount = 0;
					MAsset asset = new MAsset (this, sLine, deliveryCount);
					if (!asset.save(get_TrxName()))
					{
						m_processMsg = "Could not create Asset";
						return DocAction.STATUS_Invalid;
					}
					info.append(asset.getValue());
				}
			}	//	Asset


			//	Matching
			if (!isSOTrx()
				&& sLine.getM_Product_ID() != 0
				&& !isReversal())
			{
				BigDecimal matchQty = sLine.getMovementQty();
				//	Invoice - Receipt Match (requires Product)
				MInvoiceLine iLine = MInvoiceLine.getOfInOutLine (sLine);
				if (iLine != null && iLine.getM_Product_ID() != 0)
				{
					if (matchQty.compareTo(iLine.getQtyInvoiced())>0)
						matchQty = iLine.getQtyInvoiced();

					MMatchInv[] matches = MMatchInv.get(getCtx(),
						sLine.getM_InOutLine_ID(), iLine.getC_InvoiceLine_ID(), get_TrxName());
					if (matches == null || matches.length == 0)
					{
						MMatchInv inv = new MMatchInv (iLine, getMovementDate(), matchQty);
						if (sLine.getM_AttributeSetInstance_ID() != iLine.getM_AttributeSetInstance_ID())
						{
							iLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							iLine.saveEx();	//	update matched invoice with ASI
							inv.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						}
						if (!inv.save(get_TrxName()))
						{
							m_processMsg = CLogger.retrieveErrorString("Could not create Inv Matching");
							return DocAction.STATUS_Invalid;
						}
						addDocsPostProcess(inv);
					}
				}

				//	Link to Order
				if (sLine.getC_OrderLine_ID() != 0)
				{
					log.fine("PO Matching");
					//	Ship - PO
					MMatchPO po = MMatchPO.create (null, sLine, getMovementDate(), matchQty);
					if (po != null) {
						if (!po.save(get_TrxName()))
						{
							m_processMsg = "Could not create PO Matching";
							return DocAction.STATUS_Invalid;
						}
						if (!po.isPosted())
							addDocsPostProcess(po);
						MMatchInv matchInvCreated = po.getMatchInvCreated();
						if (matchInvCreated != null) {
							addDocsPostProcess(matchInvCreated);
						}
					}
					//	Update PO with ASI
					if (   oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
						&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0) //  just if full match [ 1876965 ]
					{
						oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						oLine.saveEx(get_TrxName());
					}
				}
				else	//	No Order - Try finding links via Invoice
				{
					//	Invoice has an Order Link
					if (iLine != null && iLine.getC_OrderLine_ID() != 0)
					{
						//	Invoice is created before  Shipment
						log.fine("PO(Inv) Matching");
						//	Ship - Invoice
						MMatchPO po = MMatchPO.create (iLine, sLine,
							getMovementDate(), matchQty);
						if (po != null) {
							if (!po.save(get_TrxName()))
							{
								m_processMsg = "Could not create PO(Inv) Matching";
								return DocAction.STATUS_Invalid;
							}
							if (!po.isPosted())
								addDocsPostProcess(po);
						}
						
						//	Update PO with ASI
						oLine = new HBC_MOrderLine (getCtx(), iLine.getC_OrderLine_ID(), get_TrxName());
						if (   oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
							&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0) //  just if full match [ 1876965 ]
						{
							oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							oLine.saveEx(get_TrxName());
						}
					}
				}	//	No Order
			}	//	PO Matching
			
		}	//	for all lines

		//	Counter Documents
		MInOut counter = createCounterDoc();
		if (counter != null)
			info.append(" - @CounterDoc@: @M_InOut_ID@=").append(counter.getDocumentNo());

		//  Drop Shipments
		MInOut dropShipment = createDropShipment();
		if (dropShipment != null)
			info.append(" - @DropShipment@: @M_InOut_ID@=").append(dropShipment.getDocumentNo());
		
		//	@stephan
		//	set orderline isreceipt true if qty in order equals with sum receipt qty
		MInOutLine inoutLines[] = getLines();
		for (MInOutLine inoutLine : inoutLines) {
			if (inoutLine.getC_OrderLine_ID() > 0) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT COALESCE(SUM(QtyEntered),0) FROM M_InOutLine line "
						+ "LEFT JOIN M_InOut head on head.M_InOut_ID=line.M_InOut_ID "
						+ "WHERE head.DocStatus='CO' AND C_OrderLine_ID=?");
				BigDecimal sumQty = DB.getSQLValueBD(get_TrxName(),
						sql.toString(), inoutLine.getC_OrderLine_ID());
				sumQty = sumQty.add(inoutLine.getQtyEntered());
				MOrderLine orderLine = new MOrderLine(getCtx(),
						inoutLine.getC_OrderLine_ID(), get_TrxName());
				if (sumQty.compareTo(orderLine.getQtyEntered()) == 0) {
					orderLine.set_ValueOfColumn("IsReceipt", true);
					orderLine.saveEx();
				}
			}
		}
		//	end
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		//TommyAng
		//if(isSOTrx()==false)
		//	setDocumentNo(getDocumentNoRomanFormat());
		
		m_processMsg = info.toString();
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		if(m_processMsg == null)
			m_processMsg = super.getProcessMsg();
		
		return m_processMsg;
	}	//	getProcessMsg
	
	public String getDocumentNoRomanFormat(){
		String DocumentNo="";
		/*
		String romanmonth=RomanNumber(getMonthDate(getMovementDate()));
		String yeardate = String.valueOf(getYearDate(getMovementDate()));
		*/
		String romanmonth=RomanNumber(getMonthDate(getDateReceived()));
		String yeardate = String.valueOf(getYearDate(getDateReceived()));
		MDocType doctype=new MDocType(getCtx(),getC_DocType_ID(),get_TrxName());
		MSequence sequence = new MSequence(getCtx(),doctype.getDocNoSequence_ID(),get_TrxName());
		MWarehouse warehouse = new MWarehouse(getCtx(),getM_Warehouse_ID(),get_TrxName());
		DocumentNo=sequence.getCurrentNext()+doctype.get_ValueAsString("TypeDoc")+romanmonth+"/"+warehouse.getName()+"/"+yeardate;
		sequence.setCurrentNext(sequence.getCurrentNext()+1);
		sequence.saveEx();
		return DocumentNo;
	}
	
	protected int getYearDate(Timestamp Date){
		int year =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		year= calender.get(Calendar.YEAR);
		return year;
	}

	
	protected int getMonthDate(Timestamp Date){
		int month =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		month = calender.get(Calendar.MONTH)+1;
		return month;
	}

	/**
	 * get roman number format
	 */
	public static String RomanNumber(int input) {
		if (input < 1 || input > 3999)
			return "Invalid Roman Number Value";
		String s = "";
		while (input >= 1000) {
			s += "M";
			input -= 1000;        }
		while (input >= 900) {
			s += "CM";
			input -= 900;
		}
		while (input >= 500) {
			s += "D";
			input -= 500;
		}
		while (input >= 400) {
			s += "CD";
			input -= 400;
		}
		while (input >= 100) {
			s += "C";
			input -= 100;
		}
		while (input >= 90) {
			s += "XC";
			input -= 90;
		}
		while (input >= 50) {
			s += "L";
			input -= 50;
		}
		while (input >= 40) {
			s += "XL";
			input -= 40;
		}
		while (input >= 10) {
			s += "X";
			input -= 10;
		}
		while (input >= 9) {
			s += "IX";
			input -= 9;
		}
		while (input >= 5) {
			s += "V";
			input -= 5;
		}
		while (input >= 4) {
			s += "IV";
			input -= 4;
		}
		while (input >= 1) {
			s += "I";
			input -= 1;
		}    
		return s;
	}


}
