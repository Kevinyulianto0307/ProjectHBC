package org.toba.habco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.Core;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.ProductNotOnPriceListException;
import org.adempiere.model.ITaxProvider;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderTax;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MResourceAssignment;
import org.compiere.model.MRole;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MTaxProvider;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
//import org.compiere.model.MTax;

public class HBC_MOrderLine extends MOrderLine{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6036153759900496996L;
	
	public HBC_MOrderLine(MOrder order) {
		super(order);
	}
	public HBC_MOrderLine(MRequisitionLine reqLine) {
		super(reqLine);
	}

	public HBC_MOrderLine(Properties ctx, int C_OrderLine_ID, String trxName) {
		super(ctx, C_OrderLine_ID, trxName);
	}
	public HBC_MOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	protected MTax 		m_tax = null;

	
	protected boolean beforeSave (boolean newRecord)
	{
		if(getM_Product_ID() == 0 && getC_Charge_ID() == 0)
			throw new AdempiereException(Msg.getMsg(getCtx(), "Product or charge must be selected"));
		
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "C_OrderLine"));
			return false;
		}
		//	Get Defaults from Parent
		if (getC_BPartner_ID() == 0 || getC_BPartner_Location_ID() == 0
			|| getM_Warehouse_ID() == 0 
			|| getC_Currency_ID() == 0)
			setOrder (getParent());
		if (m_M_PriceList_ID == 0)
			setHeaderInfo(getParent());
		
		//@win - recalculate multi uom on 
		if (newRecord || is_ValueChanged(COLUMNNAME_C_UOM_ID) || 
				is_ValueChanged(COLUMNNAME_M_Product_ID) || is_ValueChanged(COLUMNNAME_QtyEntered)) {
			BigDecimal qtyEntered = getQtyEntered();
			int p_C_UOM_ID = getC_UOM_ID();
			BigDecimal qtyEntered1 = qtyEntered.setScale(MUOM.getPrecision(getCtx(), p_C_UOM_ID), BigDecimal.ROUND_HALF_UP);
			if (qtyEntered.compareTo(qtyEntered1) != 0)
			{
				qtyEntered = qtyEntered1;
				setQtyEntered(qtyEntered);
			}
			
			BigDecimal qtyOrdered = MUOMConversion.convertProductFrom (getCtx(), getM_Product_ID(),
					p_C_UOM_ID, qtyEntered);
			
			if (qtyOrdered == null)
					qtyOrdered = qtyEntered;
			
			if (getQtyOrdered().compareTo(qtyOrdered) != 0)
				setQtyOrdered(qtyOrdered);
		}

		//end @win - recalculate multi uom
		//	R/O Check - Product/Warehouse Change
		if (!newRecord 
			&& (is_ValueChanged("M_Product_ID") || is_ValueChanged("M_Warehouse_ID"))) 
		{
			if (!canChangeWarehouse())
				return false;
		}	//	Product Changed
		
		//	Charge
		if (getC_Charge_ID() != 0 && getM_Product_ID() != 0)
				setM_Product_ID(0);
		
		// @Stephan add validation is no price list
		MOrder order = getParent();
		boolean isNoPrice = order.get_ValueAsBoolean("IsNoPriceList");
		
		//	No Product
		if (getM_Product_ID() == 0)
			setM_AttributeSetInstance_ID(0);
		//	Product
		else	//	Set/check Product Price
		{
			//	Set Price if Actual = 0
			// TODO not set price if different uom
			if (!isNoPrice && m_productPrice == null 
				&&  Env.ZERO.compareTo(getPriceActual()) == 0
				&&  Env.ZERO.compareTo(getPriceList()) == 0)
				setPrice();
			//	Check if on Price list
			if (m_productPrice == null)
				getProductPricing(m_M_PriceList_ID);
			// IDEMPIERE-1574 Sales Order Line lets Price under the Price Limit when updating
			//	Check PriceLimit
			boolean enforce = m_IsSOTrx && getParent().getM_PriceList().isEnforcePriceLimit();
			if (enforce && MRole.getDefault().isOverwritePriceLimit())
				enforce = false;
			//	Check Price Limit?
			if (enforce && getPriceLimit() != Env.ZERO
			  && getPriceActual().compareTo(getPriceLimit()) < 0)
			{
				log.saveError("UnderLimitPrice", "PriceEntered=" + getPriceEntered() + ", PriceLimit=" + getPriceLimit()); 
				return false;
			}
			
			if (!m_productPrice.isCalculated() && !isNoPrice)
			{
				throw new ProductNotOnPriceListException(m_productPrice, getLine());
			}
		}

		//	UOM
		if (getC_UOM_ID() == 0 
			&& (getM_Product_ID() != 0 
				|| getPriceEntered().compareTo(Env.ZERO) != 0
				|| getC_Charge_ID() != 0))
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID (C_UOM_ID);
		}
		//	Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged("QtyOrdered"))
			setQtyOrdered(getQtyOrdered());
		
		//	Qty on instance ASI for SO
		if (m_IsSOTrx 
			&& getM_AttributeSetInstance_ID() != 0
			&& (newRecord || is_ValueChanged("M_Product_ID")
				|| is_ValueChanged("M_AttributeSetInstance_ID")
				|| is_ValueChanged("M_Warehouse_ID")))
		{
			MProduct product = getProduct();
			if (product.isStocked())
			{
				int M_AttributeSet_ID = product.getM_AttributeSet_ID();
				boolean isInstance = M_AttributeSet_ID != 0;
				if (isInstance)
				{
					MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
					isInstance = mas.isInstanceAttribute();
				}
				//	Max
				if (isInstance)
				{
					MStorageOnHand[] storages = MStorageOnHand.getWarehouse(getCtx(), 
						getM_Warehouse_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(), 
						null, true, false, 0, get_TrxName());
					BigDecimal qty = Env.ZERO;
					for (int i = 0; i < storages.length; i++)
					{
						if (storages[i].getM_AttributeSetInstance_ID() == getM_AttributeSetInstance_ID())
							qty = qty.add(storages[i].getQtyOnHand());
					}
					
					if (getQtyOrdered().compareTo(qty) > 0)
					{
						log.warning("Qty - Stock=" + qty + ", Ordered=" + getQtyOrdered());
						log.saveError("QtyInsufficient", "=" + qty); 
						return false;
					}
				}
			}	//	stocked
		}	//	SO instance
		
		//	FreightAmt Not used
		if (Env.ZERO.compareTo(getFreightAmt()) != 0)
			setFreightAmt(Env.ZERO);

		//	Set Tax
		if (getC_Tax_ID() == 0)
			setTax();

		//	Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getC_Order_ID());
			setLine (ii);
		}
		//	Calculations & Rounding
		setLineNetAmt();	//	extended Amount with or without tax
		//setDiscount();
		/* Carlos Ruiz - globalqss
		 * IDEMPIERE-178 Orders and Invoices must disallow amount lines without product/charge
		 */
		if (getParent().getC_DocTypeTarget().isChargeOrProductMandatory()) {
			if (getC_Charge_ID() == 0 && getM_Product_ID() == 0 && getPriceEntered().signum() != 0) {
				log.saveError("FillMandatory", Msg.translate(getCtx(), "ChargeOrProductMandatory"));
				return false;
			}
		}
		
		/* @TommyAng Validation no longer needed
		 *	stephan
		 *	TAOWI-897 check tax in order line must be same with tax header 
		 
		if(getParent().getC_Tax_ID() == 0)
			return true;
		else if(newRecord && getParent().getC_Tax_ID() != getC_Tax_ID())
			setC_Tax_ID(getParent().getC_Tax_ID());
		*/
		
		//TODO: @win.. need to remove this hard code
		//int fueldoctypeid= new Query(getCtx(),MDocType.Table_Name,"Name='Purchase Order Fuel'",get_TrxName()).firstId();
		
		if(getC_Order().getC_DocTypeTarget_ID() == 1000250){
			MBPartnerLocation bpartnerlocation = new MBPartnerLocation(getCtx(),getC_Order().getC_BPartner_Location_ID(),get_TrxName());
			BigDecimal pph= ((BigDecimal)bpartnerlocation.get_Value("PPhPercentage")).divide(Env.ONEHUNDRED);
			BigDecimal ppn= ((BigDecimal)bpartnerlocation.get_Value("PPNPercentage")).divide(Env.ONEHUNDRED);
			BigDecimal pbbkb= ((BigDecimal)bpartnerlocation.get_Value("PBBKBPercentage")).divide(Env.ONEHUNDRED);
			BigDecimal taxtotal=((Env.ONE.add(pph)).add(ppn)).add(pbbkb);
			BigDecimal DPPAmount= getPriceEntered().divide(taxtotal,3,RoundingMode.HALF_UP);
			int precision = getC_Order().getC_Currency().getStdPrecision();
			
			order = new MOrder(getCtx(),getC_Order_ID(),get_TrxName());
			order.set_ValueOfColumn("TaxBaseAmt", DPPAmount.setScale(precision, BigDecimal.ROUND_HALF_UP));
			order.set_ValueOfColumn("Totaltaxbaseamt", DPPAmount.multiply(getQtyEntered()).setScale(precision, BigDecimal.ROUND_HALF_UP));
			order.set_ValueOfColumn("PPHTaxAmt", DPPAmount.multiply(pph).setScale(precision, BigDecimal.ROUND_DOWN));
			order.set_ValueOfColumn("TotalPPHTaxAmt", (DPPAmount.multiply(pph)).multiply(getQtyEntered()).setScale(precision, BigDecimal.ROUND_DOWN));
			order.set_ValueOfColumn("PBBKBTaxAmt", DPPAmount.multiply(pbbkb).setScale(precision, BigDecimal.ROUND_DOWN));
			order.set_ValueOfColumn("TotalPBBKBTaxAmt", (DPPAmount.multiply(pbbkb)).multiply(getQtyEntered()).setScale(precision, BigDecimal.ROUND_DOWN));
			order.set_ValueOfColumn("TaxAmt", DPPAmount.multiply(ppn).setScale(precision, BigDecimal.ROUND_DOWN));
			order.set_ValueOfColumn("TotalTaxAmt", (DPPAmount.multiply(ppn)).multiply(getQtyEntered()).setScale(precision, BigDecimal.ROUND_DOWN));
			order.saveEx();
		}
		
		return true;
	}	//	beforeSave

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		MTax tax = new MTax(getCtx(), getC_Tax_ID(), get_TrxName());
        MTaxProvider provider = new MTaxProvider(tax.getCtx(), tax.getC_TaxProvider_ID(), tax.get_TrxName());
		ITaxProvider calculator = Core.getTaxProvider(provider);
		if (calculator == null)
			throw new AdempiereException(Msg.getMsg(getCtx(), "TaxNoProvider"));
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		
		//@phie
		//fix set price actual if isNoPriceList 
		if(order.get_ValueAsBoolean("isNoPriceList") == true)
		{
			if(getM_Product_ID()>0)
			{
				//set price actual
				if(getC_UOM_ID() == getM_Product().getC_UOM_ID())
				{
					StringBuilder sb = new StringBuilder();
					sb.append("UPDATE C_OrderLine SET PriceActual = "+getPriceEntered()+" WHERE C_OrderLine_ID = "+getC_OrderLine_ID());
					DB.executeUpdate(sb.toString(), get_TrxName());
				}
				else 
				{
					MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
					//get multiply rate
					StringBuilder query = new StringBuilder();
					query.append("SELECT MultiplyRate FROM C_UOM_Conversion WHERE C_UOM_ID = ? AND C_UOM_to_ID = ? AND M_Product_ID = ?");
					BigDecimal multiplyRate = DB.getSQLValueBD(get_TrxName(), query.toString(), new Object[]{product.getC_UOM_ID(), getC_UOM_ID(), getM_Product_ID()});
					
					StringBuilder sb = new StringBuilder();
					sb.append("UPDATE C_OrderLine SET PriceActual = "+getPriceEntered().multiply(multiplyRate)+" WHERE C_OrderLine_ID = "+getC_OrderLine_ID());
					DB.executeUpdate(sb.toString(), get_TrxName());
				}
			}
		}
		//end phie
		BigDecimal currentDisc = (BigDecimal) order.get_Value("DiscountAmt");
		BigDecimal DiscAmt = (BigDecimal) get_Value("DiscAmt");
		//BigDecimal DiscPO = Env.ZERO;
		
		if(currentDisc == null)
			currentDisc = Env.ZERO;
		if(DiscAmt == null)
			DiscAmt = Env.ZERO;
		
		/* comment by phie
		BigDecimal totalDiscAmt = Env.ZERO;
		for(MOrderLine line : order.getLines()){
			totalDiscAmt = totalDiscAmt.add((BigDecimal) line.get_Value("DiscAmt"));
		}
		*/
		//DiscAmt = currentDisc.add(DiscAmt);
		
		//order.set_ValueOfColumn("DiscountAmt", totalDiscAmt);
		//order.saveEx();
		
		boolean infoCalc = calculator.recalculateTax(provider, this, newRecord);
		if(!infoCalc)
			return false;
		
		/*
		//@TommyAng
		int precision = order.getC_Currency().getStdPrecision();
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE C_OrderTax SET TaxAmt=TRUNC(TaxAmt,?) WHERE C_Order_ID=?");
		int no = DB.executeUpdateEx(sb.toString(), new Object[]{precision,getC_Order_ID()}, get_TrxName());
		log.info("UPDATED C_OrderTax TaxAmt C_Order#"+no);
			
		//recalculate grand total
		sb = new StringBuilder();
		//@phie add sql where istaxincluded
		sb.append("UPDATE C_Order SET GrandTotal=COALESCE(TotalLines,0)+COALESCE((SELECT sum(TaxAmt) "
				+ "FROM C_OrderTax WHERE C_Order_ID=? AND isTaxIncluded = 'N'),?) "
				+ "WHERE C_Order_ID=?");
		
		no = DB.executeUpdateEx(sb.toString(), new Object[]{getC_Order_ID(), precision, getC_Order_ID()}, get_TrxName());
		log.info("UPDATED GrandTotal C_Order#"+no);
		*/

    	return true;
	}	//	afterSave

	
	/**
	 * 	Before Delete
	 *	@return true if it can be deleted
	 */
	protected boolean beforeDelete ()
	{
		if(isProcessed())
			return false;
		
		//	R/O Check - Something delivered. etc.
		if (Env.ZERO.compareTo(getQtyDelivered()) != 0)
		{
			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyDelivered") + "=" + getQtyDelivered());
			return false;
		}
		if (Env.ZERO.compareTo(getQtyInvoiced()) != 0)
		{
			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyInvoiced") + "=" + getQtyInvoiced());
			return false;
		}
		/*	@Stephan comment out related with qty reserved
		if (Env.ZERO.compareTo(getQtyReserved()) != 0)
		{
			//	For PO should be On Order
			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
			return false;
		}*/
		
		//@win remove MatchPR link
		try {
			DB.executeUpdate("DELETE FROM M_MatchPR WHERE C_OrderLine_ID=" + get_ID(), get_TrxName());
		} catch (Exception e) {
			log.saveError("DeleteError", "Cannot Delete Match PR records");
			e.printStackTrace();
		}
		try {
			DB.executeUpdate("DELETE FROM M_MatchQuotation WHERE C_OrderLine_ID=" + get_ID(), get_TrxName());
		} catch (Exception e) {
			log.saveError("DeleteError", "Cannot Delete Match Quotation records");
			e.printStackTrace();
		}
		try {
			DB.executeUpdate("DELETE FROM M_MatchRequest WHERE C_OrderLine_ID=" + get_ID(), get_TrxName());
		} catch (Exception e) {
			log.saveError("DeleteError", "Cannot Delete Match Request records");
			e.printStackTrace();
		}
		try {
			DB.executeUpdate("DELETE FROM M_MatchMovement WHERE C_OrderLine_ID=" + get_ID(), get_TrxName());
		} catch (Exception e) {
			log.saveError("DeleteError", "Cannot Delete Match Movement records");
			e.printStackTrace();
		}
		// UnLink All Requisitions
		//MRequisitionLine.unlinkC_OrderLine_ID(getCtx(), get_ID(), get_TrxName());
		//@win end remove MatchPR link
		
		// @Stephan delete cost detail and cost history before delete
		HBC_MOrder order = (HBC_MOrder) getC_Order();
		MOrderLine orderLine = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
		order.deleteMatchPOCostDetail(orderLine);
		// @Stephan end
		
		return true;
	}	//	beforeDelete
	
	
	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		if (getS_ResourceAssignment_ID() != 0)
		{
			MResourceAssignment ra = new MResourceAssignment(getCtx(), getS_ResourceAssignment_ID(), get_TrxName());
			ra.delete(true);
		}
		MTax tax = new MTax(getCtx(), getC_Tax_ID(), get_TrxName());
        MTaxProvider provider = new MTaxProvider(tax.getCtx(), tax.getC_TaxProvider_ID(), tax.get_TrxName());
		ITaxProvider calculator = Core.getTaxProvider(provider);
		if (calculator == null)
			throw new AdempiereException(Msg.getMsg(getCtx(), "TaxNoProvider"));
		
		//TommyAng Calculate DiscountAmt
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		
		BigDecimal currentDisc = Env.ZERO;
		if (order.get_Value("DiscountAmt")!=null &&  get_Value("DiscAmt")!=null)
				currentDisc = ((BigDecimal) order.get_Value("DiscountAmt")).subtract((BigDecimal)get_Value("DiscAmt"));
		
		/*String sql = "UPDATE C_Order SET DiscountAmt ="+currentDisc+"WHERE C_Order_ID ="+order.getC_Order_ID();
		try{
			DB.executeUpdate(sql, get_TrxName());
		}catch(Exception e){
			log.severe("Failed to Update DiscountAmt.");
		}*/
		//order.set_ValueOfColumn("DiscountAmt", currentDisc.subtract((BigDecimal) get_Value("DiscAmt")));
		order.set_ValueOfColumn("DiscountAmt", currentDisc);
		order.saveEx();
		//end TommyAng Calculate DiscountAmt
		boolean infoCalc = calculator.recalculateTax(provider, this, true);
		
		if(!infoCalc)
			return false;
		
		//@TommyAng
		if(order.getC_Currency().getISO_Code().equals("IDR")){
			
			//round down ,00 C_OrderTax tax amt
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_OrderTax SET TaxAmt=TRUNC(TaxAmt) WHERE C_Order_ID=?");
			int no = DB.executeUpdate(sb.toString(), getC_Order_ID(), get_TrxName());
			log.info("UPDATED C_OrderTax TaxAmt C_Order#"+no);
			
			//recalculate grand total
			sb = new StringBuilder();
			sb.append("UPDATE C_Order SET GrandTotal=COALESCE(TotalLines,0)+COALESCE((SELECT sum(TaxAmt) FROM C_OrderTax WHERE C_Order_ID=?),0) "
					+ "WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), new Object[]{getC_Order_ID(), getC_Order_ID()}, true, get_TrxName());
			log.info("UPDATED GrandTotal C_Order#"+no);
			
		} else{
			
			//round down ,** C_OrderTax tax amt
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_OrderTax SET TaxAmt=TRUNC(TaxAmt,2) WHERE C_Order_ID=?");
			int no = DB.executeUpdate(sb.toString(), getC_Order_ID(), get_TrxName());
			log.info("UPDATED C_OrderTax TaxAmt C_Order#"+no);	
			
			//  recalculate grand total
			sb = new StringBuilder();
			sb.append("UPDATE C_Order SET GrandTotal=COALESCE(TotalLines,2)+COALESCE((SELECT TaxAmt FROM C_OrderTax WHERE C_Order_ID=?),2) "
					+ "WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), new Object[]{getC_Order_ID(), getC_Order_ID()}, true, get_TrxName());
			log.info("UPDATED GrandTotal C_Order#"+no);
			
		}
		//end @TommyAng
		return true;
	}	//	afterDelete
	
	/**
	 * 	Calculate Extended Amt.
	 * 	May or may not include tax
	 */
	public void setLineNetAmt ()
	{
		//BigDecimal bd = getPriceActual().multiply(getQtyOrdered()); 
		BigDecimal bd = getPriceEntered().multiply(getQtyEntered());
		
		int precision = this.getC_Order().getC_Currency().getStdPrecision();
		boolean documentLevel = getTax().isDocumentLevel();
		
		//	juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		//  http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded() && !documentLevel)	{
			BigDecimal taxStdAmt = Env.ZERO, taxThisAmt = Env.ZERO;
			
			MTax orderTax = getTax();
			MTax stdTax = null;
			
			//	get the standard tax
			if (getProduct() == null)
			{
				if (getCharge() != null)	// Charge 
				{
					stdTax = new MTax (getCtx(), 
							((MTaxCategory) getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(),
							get_TrxName());
				}
					
			}
			else	// Product
				stdTax = new MTax (getCtx(), 
							((MTaxCategory) getProduct().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(), 
							get_TrxName());

			if (stdTax != null)
			{
				if (log.isLoggable(Level.FINE)){
					log.fine("stdTax rate is " + stdTax.getRate());
					log.fine("orderTax rate is " + orderTax.getRate());
				}
								
				taxThisAmt = taxThisAmt.add(orderTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				taxThisAmt.setScale(precision, BigDecimal.ROUND_DOWN);
				taxStdAmt = taxStdAmt.add(stdTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				taxStdAmt.setScale(precision, BigDecimal.ROUND_DOWN);
				
				bd = bd.subtract(taxStdAmt).add(taxThisAmt);
				
				if (log.isLoggable(Level.FINE)) log.fine("Price List includes Tax and Tax Changed on Order Line: New Tax Amt: " 
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);	
			}
			
		}
		if (bd.scale() > precision)
			bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
		
		super.setLineNetAmt (bd);
	}	//	setLineNetAmt
	
	/**
	 * Recalculate order tax
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 * 
	 * @author teo_sarca [ 1583825 ]
	 */
	public boolean updateOrderTax(boolean oldTax) {
		MOrderTax tax = MOrderTax.get (this, getPrecision(), oldTax, get_TrxName());
		if (tax != null) {
			if (!tax.calculateTaxFromLines())
				return false;
			if (tax.getTaxAmt().signum() != 0) {
				if (!tax.save(get_TrxName()))
					return false;
			}
			else {
				if (!tax.is_new() && !tax.delete(false, get_TrxName()))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 	Get Tax
	 *	@return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	//	getTax

	//@phie
	/**
	 * 	Custom method from parent with new parameter timestamp
	 * 	Set Price for Product and PriceList
	 * 	@param M_PriceList_ID price list
	 */
	public void setPriceBasedOnDate (int M_PriceList_ID, Timestamp datePriceVersion)
	{
		if (getM_Product_ID() == 0)
			return;
		//
		if (log.isLoggable(Level.FINE)) log.fine(toString() + " - M_PriceList_ID=" + M_PriceList_ID);
		getProductPricingBasedOnDate (M_PriceList_ID, datePriceVersion);
		
		setPriceActual (m_productPrice.getPriceStd());
		setPriceList (m_productPrice.getPriceList());
		setPriceLimit (m_productPrice.getPriceLimit());
		setPriceEntered(getPriceActual());
				
		//	Calculate Discount
		setDiscount(m_productPrice.getDiscount());
		//	Set UOM
		setC_UOM_ID(m_productPrice.getC_UOM_ID());
	}	//	setPrice
	//end phie
	
	//@phie
	/**
	 *  Custom method from parent with new parameter timestamp
	 * 	Get and calculate Product Pricing
	 *	@param M_PriceList_ID id
	 *	@return product pricing
	 */
	protected MProductPricing getProductPricingBasedOnDate (int M_PriceList_ID, Timestamp datePriceVersion)
	{
		m_productPrice = new MProductPricing (getM_Product_ID(), 
			getC_BPartner_ID(), getQtyOrdered(), m_IsSOTrx);
		m_productPrice.setM_PriceList_ID(M_PriceList_ID);
		m_productPrice.setPriceDate(datePriceVersion);
		//
		m_productPrice.calculatePrice();
		return m_productPrice;
	}	//	getProductPrice
	//end phie

}
