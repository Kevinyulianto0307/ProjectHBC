package org.toba.habco.timesheet.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.X_HBC_ShipActivity;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_DischargingCreateInventory extends SvrProcess {

	/*
	 * created by yonk
	 * HABCO-1668 Process Total Liters BBM > Inventory
	 */
	int p_HBC_ShipActivity_ID=0;
	int p_Charge_ID=0;
	int p_M_Product_ID=0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Charge_ID")){
				p_Charge_ID = para[i].getParameterAsInt();
			}else if (name.equals("M_Product_ID")){
				p_M_Product_ID= para[i].getParameterAsInt();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		p_HBC_ShipActivity_ID=getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_ShipActivity_ID==0){
			return "No Ship Activity Selected!";
		}
		
		X_HBC_ShipActivity sactivity= new X_HBC_ShipActivity(getCtx(), p_HBC_ShipActivity_ID, get_TrxName());
		X_HBC_Tugboat tugboat=new X_HBC_Tugboat(getCtx(), sactivity.getHBC_Tugboat_ID(), get_TrxName());
		int locatorid = new Query(getCtx(),MLocator.Table_Name,"M_Warehouse_ID="+tugboat.get_ValueAsInt("M_Warehouse_ID"),get_TrxName()).firstId();
		int doctypeid= new Query(getCtx(),MDocType.Table_Name,"Name='Internal Use Inventory' AND AD_Client_ID="+getAD_Client_ID(),get_TrxName()).firstId();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT sumtotal FROM Tao_Storage_vw WHERE M_Warehouse_ID="+tugboat.get_ValueAsInt("M_Warehouse_ID")
						+ " AND M_Locator_ID="+locatorid+" AND M_Product_ID="+p_M_Product_ID);
		BigDecimal totalbbm = DB.getSQLValueBD(get_TrxName(), sql.toString());
		if (totalbbm.subtract(sactivity.getTotalLiters()).compareTo(Env.ZERO)<0){
			return "Error-Insufficient BBM";
		}
		MInventory inventory = new MInventory(getCtx(),0,get_TrxName());
		inventory.setM_Warehouse_ID(tugboat.get_ValueAsInt("M_Warehouse_ID"));
		inventory.setM_Locator_ID(locatorid);
		inventory.setC_DocType_ID(doctypeid);
		inventory.setAD_Org_ID(sactivity.getAD_Org_ID());
		inventory.setMovementDate(sactivity.getStartDate());
		inventory.setC_Project_ID(sactivity.getHBC_Position().getC_Project_ID());
		inventory.saveEx();
		
		MInventoryLine inventoryline = new MInventoryLine(getCtx(), 0, get_TrxName());
		inventoryline.setM_Inventory_ID(inventory.getM_Inventory_ID());
		inventoryline.setM_Locator_ID(inventory.getM_Locator_ID());
		inventoryline.setM_Product_ID(p_M_Product_ID);
		inventoryline.setQtyEntered(totalbbm.subtract(sactivity.getTotalLiters()));
		inventoryline.setAD_Org_ID(sactivity.getAD_Org_ID());
		inventoryline.setC_Charge_ID(p_Charge_ID);
		inventoryline.setC_UOM_ID(inventoryline.getM_Product().getC_UOM_ID());
		inventoryline.setQtyInternalUse(totalbbm.subtract(sactivity.getTotalLiters()));
		inventoryline.saveEx();
		
		sactivity.set_ValueOfColumn("Processed", true);
		sactivity.set_ValueOfColumn("M_Inventory_ID", inventory.getM_Inventory_ID());
		sactivity.saveEx();
		
		inventory.setDocAction(DocAction.ACTION_Complete);
		if(inventory.processIt(DocAction.ACTION_Complete)){
			String msg = Msg.parseTranslation(getCtx(), "@Internal Use Inventory@ @Created@ " + inventory.getDocumentNo());
			addLog(inventory.getM_Inventory_ID(), null, null, msg, MInventory.Table_ID, inventory.getM_Inventory_ID());
		}
		
		return "";
	}

}
