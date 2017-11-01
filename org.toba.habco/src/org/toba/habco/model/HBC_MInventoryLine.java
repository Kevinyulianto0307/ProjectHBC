package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.util.DB;
import org.compiere.util.Msg;

public class HBC_MInventoryLine extends MInventoryLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5357364898942991343L;

	public HBC_MInventoryLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public HBC_MInventoryLine(Properties ctx, int M_InventoryLine_ID,
			String trxName) {
		super(ctx, M_InventoryLine_ID, trxName);
	}

	public HBC_MInventoryLine(MInventory inventory, int M_Locator_ID,
			int M_Product_ID, int M_AttributeSetInstance_ID,
			BigDecimal QtyBook, BigDecimal QtyCount, BigDecimal QtyInternalUse) {
		super(inventory, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID,
				QtyBook, QtyCount, QtyInternalUse);
	}
	
	public HBC_MInventoryLine(MInventory inventory, int M_Locator_ID,
			int M_Product_ID, int M_AttributeSetInstance_ID,
			BigDecimal QtyBook, BigDecimal QtyCount) {
		super(inventory, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID,
				QtyBook, QtyCount);
	}
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if can be saved
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "M_InventoryLine"));
			return false;
		}
		/* IDEMPIERE-1770 - ASI validation must be moved to MInventory.prepareIt, saving a line without ASI is ok on draft
		if (m_isManualEntry)
		{
			//	Product requires ASI
			if (getM_AttributeSetInstance_ID() == 0)
			{
				MProduct product = MProduct.get(getCtx(), getM_Product_ID());
				if (product != null && product.isASIMandatory(isSOTrx()))
				{
					if(product.getAttributeSet()==null){
						log.saveError("NoAttributeSet", product.getValue());
						return false;
					}
					if (! product.getAttributeSet().excludeTableEntry(MInventoryLine.Table_ID, isSOTrx())) {
						log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstance_ID));
						return false;
					}
				}
			}	//	No ASI
		}	//	manual
		*/

		//	Set Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_InventoryLine WHERE M_Inventory_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getM_Inventory_ID());
			setLine (ii);
		}

		// Enforce QtyCount >= 0  - teo_sarca BF [ 1722982 ]
		// GlobalQSS -> reverting this change because of Bug 2904321 - Create Inventory Count List not taking negative qty products
		/*
		if ( (!newRecord) && is_ValueChanged("QtyCount") && getQtyCount().signum() < 0)
		{
			log.saveError("Warning", Msg.getElement(getCtx(), COLUMNNAME_QtyCount)+" < 0");
			return false;
		}
		*/
		
		//	Enforce Qty UOM
		if (newRecord || is_ValueChanged("QtyCount"))
			setQtyCount(getQtyCount());
		if (newRecord || is_ValueChanged("QtyInternalUse"))
			setQtyInternalUse(getQtyInternalUse());
		
		MDocType dt = MDocType.get(getCtx(), getParent().getC_DocType_ID());
		String docSubTypeInv = dt.getDocSubTypeInv();

		//@phie
		//set unit cost
		if(get_ValueAsBoolean("isUnitCost"))
		{
			if(newRecord || is_ValueChanged("UnitCostEntered") || is_ValueChanged("C_UOM_ID"))
			{
				if(!MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv))
				{
					BigDecimal unitCostEntered = (BigDecimal) get_Value("UnitCostEntered");
					int p_C_UOM_ID = getC_UOM_ID();
					BigDecimal unitCostEntered1 = unitCostEntered.setScale(MUOM.getPrecision(getCtx(), p_C_UOM_ID), BigDecimal.ROUND_HALF_UP);
					if (unitCostEntered.compareTo(unitCostEntered1) != 0)
					{
						unitCostEntered = unitCostEntered1;
						set_ValueOfColumn("UnitCostEntered",unitCostEntered);
					}
					
					BigDecimal unitCost = MUOMConversion.convertProductTo (getCtx(), getM_Product_ID(),
							p_C_UOM_ID, unitCostEntered);
					
					if (unitCost == null)
						unitCost = unitCostEntered;
					
					if (((BigDecimal) get_Value("UnitCost")).compareTo(unitCost) != 0)
						set_ValueOfColumn("UnitCost", unitCost);
				}
			}
		}
		//end @phie
		
		if (MDocType.DOCSUBTYPEINV_InternalUseInventory.equals(docSubTypeInv)) {

			// Internal Use Inventory validations
			if (!INVENTORYTYPE_ChargeAccount.equals(getInventoryType()))
				setInventoryType(INVENTORYTYPE_ChargeAccount);
			//
			if (getC_Charge_ID() == 0)
			{
				log.saveError("InternalUseNeedsCharge", "");
				return false;
			}
			// error if book or count are filled on an internal use inventory
			// i.e. coming from import or web services
			if (getQtyBook().signum() != 0) {
				log.saveError("Quantity", Msg.getElement(getCtx(), COLUMNNAME_QtyBook));
				return false;
			}
			if (getQtyCount().signum() != 0) {
				log.saveError("Quantity", Msg.getElement(getCtx(), COLUMNNAME_QtyCount));
				return false;
			}
			if (getQtyInternalUse().signum() == 0) {
				log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_QtyInternalUse));
				return false;
			}

		} else if (MDocType.DOCSUBTYPEINV_PhysicalInventory.equals(docSubTypeInv)) {

			// Physical Inventory validations
			if (INVENTORYTYPE_ChargeAccount.equals(getInventoryType()))
			{
				if (getC_Charge_ID() == 0)
				{
					log.saveError("FillMandatory", Msg.getElement(getCtx(), "C_Charge_ID"));
					return false;
				}
			}
			else if (getC_Charge_ID() != 0) {
				setC_Charge_ID(0);
			}
			if (getQtyInternalUse().signum() != 0) {
				log.saveError("Quantity", Msg.getElement(getCtx(), COLUMNNAME_QtyInternalUse));
				return false;
			}
		} else if (MDocType.DOCSUBTYPEINV_CostAdjustment.equals(docSubTypeInv)) {
			if (getNewCostPrice().signum() == 0) {
				log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_NewCostPrice));
				return false;
			}
			
			int M_ASI_ID = getM_AttributeSetInstance_ID();
			MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
			MClient client = MClient.get(getCtx());
			MAcctSchema as = client.getAcctSchema();
			String costingLevel = product.getCostingLevel(as);
			if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(costingLevel)) {				
				if (M_ASI_ID == 0) {
					log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstance_ID));
					return false;
				}
			}
			
			String costingMethod = getParent().getCostingMethod();
			int AD_Org_ID = getAD_Org_ID();
			MCost cost = product.getCostingRecord(as, AD_Org_ID, M_ASI_ID, costingMethod);					
			if (cost == null) {
				if (!MCostElement.COSTINGMETHOD_StandardCosting.equals(costingMethod)) {
					log.saveError("NoCostingRecord", "");
					return false;
				}
			}
			setM_Locator_ID(0);
		} else if (MDocType.DOCSUBTYPEINV_MiscReceipt.equals(docSubTypeInv)) {

			// Internal Use Inventory validations
			if (!INVENTORYTYPE_ChargeAccount.equals(getInventoryType()))
				setInventoryType(INVENTORYTYPE_ChargeAccount);
			//
			if (getC_Charge_ID() == 0)
			{
				log.saveError("MiscReceiptNeedsCharge", "");
				return false;
			}
			// error if book or count are filled on an internal use inventory
			// i.e. coming from import or web services
			if (getQtyBook().signum() != 0) {
				log.saveError("Quantity", Msg.getElement(getCtx(), COLUMNNAME_QtyBook));
				return false;
			}
			if (getQtyCount().signum() != 0) {
				log.saveError("Quantity", Msg.getElement(getCtx(), COLUMNNAME_QtyCount));
				return false;
			}
			if (getQtyInternalUse().signum() == 0) {
				log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_QtyInternalUse));
				return false;
			}

		
		} else {
			log.saveError("Error", "Document inventory subtype not configured, cannot complete");
			return false;
		}

		//	Set AD_Org to parent if not charge
		if (getC_Charge_ID() == 0)
			setAD_Org_ID(getParent().getAD_Org_ID());

		return true;
	}	//	beforeSave
}
