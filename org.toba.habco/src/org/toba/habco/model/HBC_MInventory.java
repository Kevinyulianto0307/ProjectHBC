package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MInventoryLineMA;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MStorageOnHandTemp;
import org.compiere.model.MTransaction;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;


/**
 *@author TommyAng
 */
public class HBC_MInventory extends MInventory {
	
	public HBC_MInventory(MWarehouse wh) {
		super(wh);
	}
	public HBC_MInventory(MWarehouse wh, String trxName) {
		super(wh, trxName);
	}
	public HBC_MInventory(Properties ctx, int M_Inventory_ID, String trxName) {
		super(ctx, M_Inventory_ID, trxName);
	}
	public HBC_MInventory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7113499436231035551L;
	

	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;
	/** Reversal Flag		*/
	private boolean m_reversal = false;
	
	/**
	 * 	Set Reversal
	 *	@param reversal reversal
	 */
	private void setReversal(boolean reversal)
	{
		m_reversal = reversal;
	}	//	setReversal
	
	protected boolean beforeSave (boolean newRecord)
	{
		if (getC_DocType_ID() == 0)
		{
			log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_C_DocType_ID));
			return false;
		}
		if(newRecord){
			setDocumentNo(getDocumentNoRomanFormat());
		}
			
		
		return true;
	}
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		String docSubTypeInv = dt.getDocSubTypeInv();
		if (Util.isEmpty(docSubTypeInv)) {
			m_processMsg = "Document inventory subtype not configured, cannot complete";
			return DocAction.STATUS_Invalid;
		}

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

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());

		MInventoryLine[] lines = getLines(false);
		for (MInventoryLine line : lines)
		{
			if (!line.isActive())
				continue;

			MProduct product = line.getProduct();	

			BigDecimal qtyDiff = Env.ZERO;
			if (MDocType.DOCSUBTYPEINV_InternalUseInventory.equals(docSubTypeInv) | MDocType.DOCSUBTYPEINV_MiscReceipt.equals(docSubTypeInv))
				qtyDiff = line.getQtyInternalUse().negate();
			else if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv))
				qtyDiff = line.getQtyCount().subtract(line.getQtyBook());
			else if (MDocType.DOCSUBTYPEINV_CostAdjustment.equals(docSubTypeInv))
			{
				if (!isReversal())
				{
					BigDecimal currentCost = line.getCurrentCostPrice();
					MClient client = MClient.get(getCtx(), getAD_Client_ID());
					MAcctSchema as = client.getAcctSchema();
					MCost cost = product.getCostingRecord(as, getAD_Org_ID(), line.getM_AttributeSetInstance_ID(), getCostingMethod());
					if (cost != null && cost.getCurrentCostPrice().compareTo(currentCost) != 0) 
					{
						m_processMsg = "Current Cost for Line " + line.getLine() + " have changed.";
						return DocAction.STATUS_Invalid; 
					}
				}
			}

			//If Quantity Count minus Quantity Book = Zero, then no change in Inventory
			if (qtyDiff.signum() == 0)
				continue;

			//Ignore the Material Policy when is Reverse Correction
			if(!isReversal()){
				BigDecimal qtyOnLineMA = MInventoryLineMA.getManualQty(line.getM_InventoryLine_ID(), get_TrxName());
				
				if(qtyDiff.signum()<0){
					if(qtyOnLineMA.compareTo(qtyDiff)<0){
						m_processMsg = "@Over_Qty_On_Attribute_Tab@ " + line.getLine();
						return DOCSTATUS_Invalid;
					}
				}else{
					if(qtyOnLineMA.compareTo(qtyDiff)>0){
						m_processMsg = "@Over_Qty_On_Attribute_Tab@ " + line.getLine();
						return DOCSTATUS_Invalid;
					}
				}
				checkMaterialPolicy(line, qtyDiff.subtract(qtyOnLineMA));
			}
			//	Stock Movement - Counterpart MOrder.reserveStock
			if (product != null 
					&& product.isStocked() )
			{
				log.fine("Material Transaction");
				MTransaction mtrx = null; 

				//If AttributeSetInstance = Zero then create new  AttributeSetInstance use Inventory Line MA else use current AttributeSetInstance
				if (line.getM_AttributeSetInstance_ID() == 0 || qtyDiff.compareTo(Env.ZERO) == 0)
				{
					MInventoryLineMA mas[] = MInventoryLineMA.get(getCtx(),
							line.getM_InventoryLine_ID(), get_TrxName());

					for (int j = 0; j < mas.length; j++)
					{
						
						MInventoryLineMA ma = mas[j];
						BigDecimal QtyMA = ma.getMovementQty();
						BigDecimal QtyNew = QtyMA.add(qtyDiff);
						if (log.isLoggable(Level.FINE)) log.fine("Diff=" + qtyDiff 
								+ " - Instance OnHand=" + QtyMA + "->" + QtyNew);

						if (!MStorageOnHand.add(getCtx(), getM_Warehouse_ID(),
								line.getM_Locator_ID(),
								line.getM_Product_ID(), 
								ma.getM_AttributeSetInstance_ID(), 
								QtyMA.negate(),ma.getDateMaterialPolicy(), get_TrxName()))
						{
							String lastError = CLogger.retrieveErrorString("");
							m_processMsg = "Cannot correct Inventory (MA) - " + lastError;
							return DocAction.STATUS_Invalid;
						}

						// Only Update Date Last Inventory if is a Physical Inventory
						if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv))
						{	
							MStorageOnHand storage = MStorageOnHand.get(getCtx(), line.getM_Locator_ID(), 
									line.getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),ma.getDateMaterialPolicy(),get_TrxName());						
							storage.setDateLastInventory(getMovementDate());
							if (!storage.save(get_TrxName()))
							{
								m_processMsg = "Storage not updated(2)";
								return DocAction.STATUS_Invalid;
							}
						}
						//@TommyAng
						String m_MovementType =null;
						if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv)){
							//@phie
							//m_MovementType = "P";
							if(QtyMA.negate().compareTo(Env.ZERO) > 0 )
								m_MovementType = "O+";
							else
								m_MovementType = "O-";
							//end phie
						}
						else{
							if(QtyMA.negate().compareTo(Env.ZERO) > 0 )
								m_MovementType = MTransaction.MOVEMENTTYPE_InventoryIn;
							else
								m_MovementType = MTransaction.MOVEMENTTYPE_InventoryOut;
						}
						//end @TommyAng
						//	Transaction
						mtrx = new MTransaction (getCtx(), line.getAD_Org_ID(), m_MovementType,
								line.getM_Locator_ID(), line.getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),
								QtyMA.negate(), getMovementDate(), get_TrxName());
						
							mtrx.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
							if (!mtrx.save())
							{
								m_processMsg = "Transaction not inserted(2)";
								return DocAction.STATUS_Invalid;
							}
							
							qtyDiff = QtyNew;						

					}	
				}

				//sLine.getM_AttributeSetInstance_ID() != 0
				// Fallback
				if (mtrx == null)
				{
					//	@Stephan
					Timestamp dateMPolicy= null;
					MStorageOnHand[] storages = null;
					if (line.getMovementQty().compareTo(Env.ZERO) > 0) {
						// Find Date Material Policy bases on ASI
						storages = MStorageOnHand.getWarehouse(getCtx(), 0,
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), null,
								MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), false,
								line.getM_Locator_ID(), get_TrxName());
					} else {
						//Case of reversal
						storages = MStorageOnHand.getWarehouse(getCtx(), 0,
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), null,
								MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), false,
								line.getM_Locator_ID(), get_TrxName());
					}
					for (MStorageOnHand storage : storages) {
						if (storage.getQtyOnHand().compareTo(line.getMovementQty()) >= 0) {
							dateMPolicy = storage.getDateMaterialPolicy();
							break;
						}
					}

					if (dateMPolicy == null && storages.length > 0)
						dateMPolicy = storages[0].getDateMaterialPolicy();

					if (dateMPolicy==null && line.getM_AttributeSetInstance_ID()!=0) {
						I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
						dateMPolicy = asi.getCreated();
					} else if(dateMPolicy==null)
						dateMPolicy = getMovementDate();
					//	@Stephan end
					
					//Fallback: Update Storage - see also VMatch.createMatchRecord
					if (!MStorageOnHand.add(getCtx(), getM_Warehouse_ID(),
							line.getM_Locator_ID(),
							line.getM_Product_ID(), 
							line.getM_AttributeSetInstance_ID(), 
							qtyDiff,dateMPolicy,get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError;
						return DocAction.STATUS_Invalid;
					}

					// Only Update Date Last Inventory if is a Physical Inventory
					if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv))
					{	
						MStorageOnHand storage = MStorageOnHand.get(getCtx(), line.getM_Locator_ID(), 
								line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),dateMPolicy, get_TrxName());						

						storage.setDateLastInventory(getMovementDate());
						if (!storage.save(get_TrxName()))
						{
							m_processMsg = "Storage not updated(2)";
							return DocAction.STATUS_Invalid;
						}
					}
					//@TommyAng
					String m_MovementType = null;
					if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv)){
						//@Phie
						//m_MovementType = "P";
						if(qtyDiff.compareTo(Env.ZERO) > 0 )
							m_MovementType = "O+";
						else
							m_MovementType = "O-";
						//end @Phie
					}
					else{
						if(qtyDiff.compareTo(Env.ZERO) > 0 )
							m_MovementType = MTransaction.MOVEMENTTYPE_InventoryIn;
						else
							m_MovementType = MTransaction.MOVEMENTTYPE_InventoryOut;
					}
					//end @TommyAng
					//	Transaction
					mtrx = new MTransaction (getCtx(), line.getAD_Org_ID(), m_MovementType,
							line.getM_Locator_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							qtyDiff, getMovementDate(), get_TrxName());
					mtrx.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
					if (!mtrx.save())
					{
						m_processMsg = "Transaction not inserted(2)";
						return DocAction.STATUS_Invalid;
					}					
				}	//	Fallback
			}	//	stock movement

		}	//	for all lines
		//		User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return false 
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		HBC_MInventory reversal = reverse(false);
		if (reversal == null)
			return false;
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();

		return true;
	}	//	reverseCorrectIt
	
	/**
	 * 	Reverse Accrual
	 * 	@return false 
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		HBC_MInventory reversal = reverse(true);
		if (reversal == null)
			return false;
		
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		
		return true;
	}	//	reverseAccrualIt
	
	private HBC_MInventory reverse(boolean accrual) {
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getMovementDate();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		MPeriod.testPeriodOpen(getCtx(), reversalDate, dt.getDocBaseType(), getAD_Org_ID());

		//	Deep Copy
		HBC_MInventory reversal = new HBC_MInventory(getCtx(), 0, get_TrxName());
		copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);	//	indicate reversals
		reversal.setMovementDate(reversalDate);
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setIsApproved (false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		StringBuilder msgd = new StringBuilder("{->").append(getDocumentNo()).append(")");
		reversal.addDescription(msgd.toString());
		//FR1948157
		reversal.setReversal_ID(getM_Inventory_ID());
		reversal.saveEx();
		reversal.setReversal(true);

		//Reverse Line Qty
		MInventoryLine[] oLines = getLines(true);
		MDocType doctype = MDocType.get(getCtx(), getC_DocType_ID());
		String docSubTypeInv = doctype.getDocSubTypeInv();
		
		ArrayList<MStorageOnHandTemp> onHandTemp = new ArrayList<MStorageOnHandTemp>();
		boolean skip = false;
		BigDecimal sumQtyOnhand=Env.ZERO;
		//@phie
		if(MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv) 
				|| MDocType.DOCSUBTYPEINV_MiscReceipt.equals(docSubTypeInv))
		{
			//Create temporary on hand
			for(int j=0 ; j<oLines.length;j++){
				//get storage on hand and store to onhand temp, for the first line
	            if(j==0){
	            	MStorageOnHand[] onhand = MStorageOnHand.getAll(getCtx(), oLines[j].getM_Product_ID(), 
	            			oLines[j].getM_Locator_ID(), get_TrxName());
	            	
	            	for(int n=0;n<onhand.length;n++){
	            		MStorageOnHandTemp temp = new MStorageOnHandTemp(onhand[n].getQtyOnHand(), onhand[n].getDateMaterialPolicy(), 
	            				onhand[n].getM_Product_ID(), onhand[n].getM_Locator_ID());
	            		onHandTemp.add(temp);
	            	}
	            }
	            else{
	            	skip = false;
	            	//check the next line, if both product and locator same with one of the previous line then skip
	                for(int k=0;k<j;k++){
	                    if(oLines[j].getM_Product_ID() == oLines[k].getM_Product_ID() 
	                    		&& oLines[j].getM_Locator_ID() == oLines[k].getM_Locator_ID()){
	                    	skip = true;
	                        break;
	                    }
	                }
	                
	                //if this line has a different product and locator with all of the previous line then get storage on hand and store to onhand temp
	                if(!skip){
	                	MStorageOnHand[] onhand = MStorageOnHand.getAll(getCtx(), oLines[j].getM_Product_ID(), 
	                			oLines[j].getM_Locator_ID(), get_TrxName());
		            	
		                for(int n=0;n<onhand.length;n++){
		            		MStorageOnHandTemp temp = new MStorageOnHandTemp(onhand[n].getQtyOnHand(), onhand[n].getDateMaterialPolicy(), 
		            				onhand[n].getM_Product_ID(), onhand[n].getM_Locator_ID());
		            		onHandTemp.add(temp);
		            	}
	                } 
	            }
	        }
		}//end phie
		
		for (int i = 0; i < oLines.length; i++)
		{
			MInventoryLine oLine = oLines[i];
			MInventoryLine rLine = new MInventoryLine(getCtx(), 0, get_TrxName());
			copyValues(oLine, rLine, oLine.getAD_Client_ID(), oLine.getAD_Org_ID());
			rLine.setM_Inventory_ID(reversal.getM_Inventory_ID());
			rLine.setParent(reversal);
			//AZ Goodwill
			// store original (voided/reversed) document line
			rLine.setReversalLine_ID(oLine.getM_InventoryLine_ID());
			//
			rLine.setQtyBook (oLine.getQtyCount());		//	switch
			rLine.setQtyCount (oLine.getQtyBook());
			rLine.setQtyInternalUse (oLine.getQtyInternalUse().negate());		
			rLine.setNewCostPrice(oLine.getCurrentCostPrice());
			rLine.setCurrentCostPrice(oLine.getNewCostPrice());
			
			rLine.saveEx();

			//create MA
			//@phie TAOWI-2676
			if (rLine.getM_AttributeSetInstance_ID() == 0)
			{
				if(MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv) || MDocType.DOCSUBTYPEINV_MiscReceipt.equals(docSubTypeInv))
				{
					sumQtyOnhand = Env.ZERO;
					BigDecimal qtyToBeReversed = MDocType.DOCSUBTYPEINV_MiscReceipt.equals(docSubTypeInv) 
							? oLines[i].getQtyMiscReceipt() 
							: oLines[i].getQtyCount().subtract(oLines[i].getQtyBook());
					
					//sumqty onhand
					for (int a = 0; a < onHandTemp.size(); a++)
					{
						MStorageOnHandTemp onhand=onHandTemp.get(a);
						boolean pair = (rLine.getM_Product_ID() == onhand.getM_Product_ID() && 
								rLine.getM_Locator_ID() == onhand.getM_Locator_ID()) ? true : false;
						if(pair)
							sumQtyOnhand = sumQtyOnhand.add(onhand.getQtyOnHand());
					}
							
							
					/*
						if can be reversed, qty will be reduced from anywhere (different datematerialpolicy) 
						Note : 
							inventorylineMA record the detail with the same datematerialpolicy 
							qtyMovement will be : (-) for created
					 							  (+) for reversed
					*/
					
					if(sumQtyOnhand.compareTo(qtyToBeReversed)>=0)
					{
						BigDecimal residual = qtyToBeReversed;
						BigDecimal prevResidual;
						for (int a = 0; a < onHandTemp.size(); a++)
						{
							MStorageOnHandTemp onhand=onHandTemp.get(a);
							
							boolean pair = (rLine.getM_Product_ID() == onhand.getM_Product_ID() && 
									rLine.getM_Locator_ID() == onhand.getM_Locator_ID()) ? true : false;
							if(!pair)
								continue;
							
							if(onhand.getQtyOnHand().compareTo(Env.ZERO) <= 0)
								continue;
							
							prevResidual = residual;
							residual = residual.subtract(onhand.getQtyOnHand());
							if(residual.compareTo(Env.ZERO)==0)
							{
								MInventoryLineMA ma = new MInventoryLineMA (rLine, 0, onhand.getQtyOnHand(),onhand.getDatematerialpolicy(),true);
								ma.saveEx();
								onhand.setQtyOnHand(Env.ZERO);
								break;
							}
							else if(residual.compareTo(Env.ZERO)<0)
							{
								MInventoryLineMA ma = new MInventoryLineMA (rLine, 0, prevResidual,onhand.getDatematerialpolicy(),true);
								ma.saveEx();
								onhand.setQtyOnHand(onhand.getQtyOnHand().subtract(prevResidual));
								break;
							}
							else if(residual.compareTo(Env.ZERO)>0)
							{
								MInventoryLineMA ma = new MInventoryLineMA (rLine, 0, onhand.getQtyOnHand(),onhand.getDatematerialpolicy(),true);
								ma.saveEx();
								onhand.setQtyOnHand(Env.ZERO);
							}
						}	
					}
					else
					{
						MInventoryLineMA mas[] = MInventoryLineMA.get(getCtx(),
								oLines[i].getM_InventoryLine_ID(), get_TrxName());
						for (int j = 0; j < mas.length; j++)
						{
							MInventoryLineMA ma = new MInventoryLineMA (rLine, 
									mas[j].getM_AttributeSetInstance_ID(),
									mas[j].getMovementQty().negate(),mas[j].getDateMaterialPolicy(),true);
							ma.saveEx();
						}
					}
				}
				else
				{
					//We need to copy MA (ori code)
					MInventoryLineMA mas[] = MInventoryLineMA.get(getCtx(),
							oLines[i].getM_InventoryLine_ID(), get_TrxName());
					for (int j = 0; j < mas.length; j++)
					{
						MInventoryLineMA ma = new MInventoryLineMA (rLine, 
								mas[j].getM_AttributeSetInstance_ID(),
								mas[j].getMovementQty().negate(),mas[j].getDateMaterialPolicy(),true);
						ma.saveEx();
					}
				}
			}//end phie	
		}
		
		doctype = MDocType.get(getCtx(), getC_DocType_ID());
		docSubTypeInv = doctype.getDocSubTypeInv();
		if(MDocType.DOCSUBTYPEINV_InternalUseInventory.equals(docSubTypeInv))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE FuelUsage SET M_Inventory_ID = NULL WHERE M_Inventory_ID = "+getM_Inventory_ID());
			DB.executeUpdate(sb.toString(), get_TrxName());
		
			sb = new StringBuilder();
			sb.append("UPDATE HBC_BBMAdjustment SET M_Inventory_ID = NULL WHERE M_Inventory_ID = "+getM_Inventory_ID());
			DB.executeUpdate(sb.toString(), get_TrxName());
		}
		
		//
		if (!reversal.processIt(DocAction.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return null;
		}
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx();
		
		//	Update Reversed (this)
		msgd = new StringBuilder("(").append(reversal.getDocumentNo()).append("<-)");
		addDescription(msgd.toString());
		setProcessed(true);
		//FR1948157
		setReversal_ID(reversal.getM_Inventory_ID());
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);
		
		return reversal;
	}
	
	/**
	 * 	Check Material Policy.
	 */
	private void checkMaterialPolicy(MInventoryLine line, BigDecimal qtyDiff)
	{	
		
		int no = MInventoryLineMA.deleteInventoryLineMA(line.getM_InventoryLine_ID(), get_TrxName());
		if (no > 0)
			if (log.isLoggable(Level.CONFIG)) log.config("Delete old #" + no);		
		
		if(qtyDiff.compareTo(Env.ZERO)==0)
			return;
		
		//	Attribute Set Instance
		if (line.getM_AttributeSetInstance_ID() == 0)
		{
			MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
			if (qtyDiff.signum() > 0)	//	Incoming Trx
			{
				//auto balance negative on hand
				MStorageOnHand[] storages = MStorageOnHand.getWarehouseNegative(getCtx(), getM_Warehouse_ID(), line.getM_Product_ID(), 0,
						null, MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), line.getM_Locator_ID(), get_TrxName(), false);
				for (MStorageOnHand storage : storages)
				{
					if (storage.getQtyOnHand().signum() < 0)
					{
						BigDecimal maQty = qtyDiff;
						if(maQty.compareTo(storage.getQtyOnHand().negate())>0)
						{
							maQty = storage.getQtyOnHand().negate();
						}
						
						//backward compatibility: -ve in MA is incoming trx, +ve in MA is outgoing trx 
						MInventoryLineMA lineMA =  new MInventoryLineMA(line, storage.getM_AttributeSetInstance_ID(), maQty.negate(), storage.getDateMaterialPolicy(),true);
						lineMA.saveEx();
						
						qtyDiff = qtyDiff.subtract(maQty);
						if (qtyDiff.compareTo(Env.ZERO)==0)
							break;
					}
				}
				
				if(qtyDiff.compareTo(Env.ZERO)>0)
				{
					MInventoryLineMA lineMA =  MInventoryLineMA.addOrCreate(line, 0, qtyDiff.negate(), getMovementDate(),true);
					lineMA.saveEx();
				}				
			}
			else	//	Outgoing Trx
			{
				String MMPolicy = product.getMMPolicy();
				MStorageOnHand[] storages = MStorageOnHand.getWarehouse(getCtx(), getM_Warehouse_ID(), line.getM_Product_ID(), 0,
						null, MClient.MMPOLICY_FiFo.equals(MMPolicy), true, line.getM_Locator_ID(), get_TrxName(), false);

				BigDecimal qtyToDeliver = qtyDiff.negate();
				for (MStorageOnHand storage: storages)
				{					
					if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0)
					{
						MInventoryLineMA ma = new MInventoryLineMA (line, 
								storage.getM_AttributeSetInstance_ID(),
								qtyToDeliver,storage.getDateMaterialPolicy(),true);
						ma.saveEx();		
						qtyToDeliver = Env.ZERO;
						if (log.isLoggable(Level.FINE)) log.fine( ma + ", QtyToDeliver=" + qtyToDeliver);		
					}
					else
					{	
						MInventoryLineMA ma = new MInventoryLineMA (line, 
								storage.getM_AttributeSetInstance_ID(),
								storage.getQtyOnHand(),storage.getDateMaterialPolicy(),true);
						ma.saveEx();
						qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
						if (log.isLoggable(Level.FINE)) log.fine( ma + ", QtyToDeliver=" + qtyToDeliver);		
					}
					if (qtyToDeliver.signum() == 0)
						break;
				}

				//	No AttributeSetInstance found for remainder
				if (qtyToDeliver.signum() != 0)
				{
					MInventoryLineMA lineMA =  MInventoryLineMA.addOrCreate(line, 0, qtyToDeliver, getMovementDate(),true);
					lineMA.saveEx();
					if (log.isLoggable(Level.FINE)) log.fine("##: " + lineMA);
				}
			}	//	outgoing Trx
		}	//	for all lines

	}	//	checkMaterialPolicy
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setMovementDate(new Timestamp (System.currentTimeMillis()));
			MPeriod.testPeriodOpen(getCtx(), getMovementDate(), MDocType.DOCBASETYPE_MaterialPhysicalInventory, getAD_Org_ID());
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}		
	
	/**
	 * 	Is Reversal
	 *	@return reversal
	 */
	private boolean isReversal()
	{
		return m_reversal;
	}	//	isReversal
	
	public String getDocumentNoRomanFormat(){
		String DocumentNo="";
		String romanmonth=RomanNumber(getMonthDate(getMovementDate()));
		String yeardate = String.valueOf(getYearDate(getMovementDate()));
		//String year =Character.toString(yeardate.charAt(2))+Character.toString(yeardate.charAt(3));
		MDocType doctype=new MDocType(getCtx(),getC_DocType_ID(),get_TrxName());
		MSequence sequence = new MSequence(getCtx(),doctype.getDocNoSequence_ID(),get_TrxName());
		MWarehouse wh = new MWarehouse(getCtx(), getM_Warehouse_ID(), get_TrxName());
		String whName = wh.getName();
		DocumentNo=sequence.getCurrentNext()+"/"+doctype.get_ValueAsString("TypeDoc")+"/"+romanmonth+"/"+whName+"/"+yeardate;
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
