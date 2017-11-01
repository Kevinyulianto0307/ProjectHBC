package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MInOutLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInOut;
import org.toba.habco.model.HBC_MInventory;
import org.toba.habco.model.HBC_MInventoryLine;
import org.toba.habco.model.HBC_MOrder;
import org.toba.habco.model.HBC_MOrderLine;

public class TCS_GenerateMiscIssueFromInOut extends SvrProcess{

	private int C_DocType_ID = 0;
	private int M_InOut_ID = 0;
	private Timestamp movementDate = null;
	private String DocAction;
		
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
				C_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("DateInvoiced"))
				movementDate = para[i].getParameterAsTimestamp();
			else if(name.equals("DocAction"))
				DocAction = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		//@kevinf HBC-2577
		int countLine = 0;
		//@kevinf end
		
		M_InOut_ID = getRecord_ID();
		
		if (M_InOut_ID==0) 
			return "No Material Receipt / Customer Return selected";
		
		HBC_MInOut inout = new HBC_MInOut (getCtx(), M_InOut_ID, get_TrxName());
		if (!HBC_MInOut.DOCSTATUS_Completed.equals(inout.getDocStatus()))
			throw new IllegalArgumentException("Meterial Reciept is not completed");
		
		MInOutLine[] inoutLines = inout.getLines();
		
		MWarehouse warehouse = new MWarehouse(getCtx(), inout.get_ValueAsInt("M_Warehouse_ID"), get_TrxName());
		
		HBC_MInventory inventory = new HBC_MInventory(warehouse);
		inventory.setMovementDate(movementDate);
		inventory.setDescription("Generated from material receipt no " + inout.getDocumentNo());
		inventory.setC_DocType_ID(C_DocType_ID);
		inventory.setDocStatus(org.compiere.process.DocAction.STATUS_Drafted);
		inventory.setDocAction(DocAction);
		inventory.saveEx();
		
		//@KevinY Habco - 2717
		HBC_MOrder order = new HBC_MOrder(getCtx(), inout.getC_Order_ID(), get_TrxName());
		HBC_MOrderLine[] orderlines = order.getLines();
		int flag = 0;
		//@KevinY end
		
		for(MInOutLine ioLine : inoutLines)
		{
			//@kevinf HBC-2650
			Integer c_Charge_ID = ioLine.get_ValueAsInt("C_Charge_ID");
			if(c_Charge_ID>0){
				continue;
			}
			//@kevinf end
			
			if (!ioLine.getM_Product().getProductType().equals(MProduct.PRODUCTTYPE_Item))
				continue;
			
			countLine++;
			
			int productid = ioLine.getM_Product_ID();
			int locatorid = ioLine.getM_Locator_ID();
			BigDecimal qty = BigDecimal.valueOf(0);
			int p_C_UOM_ID = ioLine.getC_UOM_ID();
			int C_Charge_ID = 0;
			
			MProduct p = new MProduct(getCtx(), productid, get_TrxName());
			MProductCategory pc = new MProductCategory(getCtx(), p.getM_Product_Category_ID(), get_TrxName());
			C_Charge_ID = pc.get_ValueAsInt("C_Charge_ID");
			
			HBC_MInventoryLine invLine = new HBC_MInventoryLine(inventory, locatorid, productid, 0, qty, qty);
			invLine.setC_Charge_ID(C_Charge_ID);
			invLine.setC_UOM_ID(p_C_UOM_ID);
			
			BigDecimal qtyEntered = ioLine.getQtyEntered()
					.setScale(MUOM.getPrecision(getCtx(), p_C_UOM_ID), BigDecimal.ROUND_HALF_UP);
			
			invLine.setQtyEntered(qtyEntered);
			
			BigDecimal qtyInternalUse = MUOMConversion.convertProductFrom (getCtx(), invLine.getM_Product_ID(),
					p_C_UOM_ID, qtyEntered);
			
			if (qtyInternalUse == null)
				qtyInternalUse = qtyEntered;
			
			invLine.setQtyInternalUse(qtyInternalUse);
			//@KevinY Habco - 2717
			invLine.set_ValueOfColumn("IsUnitCost","Y");
			invLine.set_ValueOfColumn("UnitCost", orderlines[flag].getPriceEntered());
			//@KevinY end
			//@phie
			invLine.set_ValueOfColumn("UnitCostEntered", invLine.get_Value("UnitCost"));
			/*
			 * unit cost will recalculate when save
			 */
			MWarehouse w = new MWarehouse(getCtx(), ioLine.getM_Locator().getM_Warehouse_ID(), get_TrxName());
			invLine.set_ValueOfColumn("HBC_Tugboat_ID", w.get_ValueAsInt("HBC_Tugboat_ID"));
			//end phie
			invLine.saveEx();
			flag++;
		}
		
		//@kevinf HBC-2577
		if(countLine==0)
		{
			inventory.deleteEx(true);
			String msg = "There's no Item type in all products, Fail to Create Internal Use";
			throw new IllegalArgumentException(msg);
		}
		//@kevinf end		
		
		inventory.processIt(DocAction);
		inventory.saveEx();
		
		//addLog(inventory.getM_Inventory_ID(), inventory.getMovementDate(), Env.ZERO, "Success");
		String message = Msg.parseTranslation(getCtx(), "@Generated@ " +  inventory.getDocumentNo());
		addBufferLog(0, null, BigDecimal.ZERO, message, inventory.get_Table_ID(), inventory.getM_Inventory_ID());
		return inventory.getDocumentNo();
	}

}
