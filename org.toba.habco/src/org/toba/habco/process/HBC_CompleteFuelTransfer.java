package org.toba.habco.process;

import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.toba.habco.model.X_HBC_ShipActivity;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_CompleteFuelTransfer extends SvrProcess {

	int p_HBC_ShipActivity_ID=0;
	int p_M_Product_ID=0;
	/*
	 * created by yonk
	 *
	 */
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_Product_ID")){
				p_M_Product_ID= para[i].getParameterAsInt();
			}
		}
		p_HBC_ShipActivity_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_ShipActivity_ID==0){
			return "No Ship Activity Selected";
		}
		
		MMovement movement = new MMovement(getCtx(),0,get_TrxName());
		X_HBC_ShipActivity sactivity = new X_HBC_ShipActivity(getCtx(),p_HBC_ShipActivity_ID,get_TrxName());
		X_HBC_Tugboat tugboat1=new X_HBC_Tugboat(getCtx(), sactivity.getHBC_Tugboat_ID(), get_TrxName());
		X_HBC_Tugboat tugboat2=new X_HBC_Tugboat(getCtx(), sactivity.getTugBoatPartner_ID(), get_TrxName());
		int locatorid1 = new Query(getCtx(),MLocator.Table_Name,"M_Warehouse_ID="+tugboat1.get_ValueAsInt("M_Warehouse_ID"),get_TrxName()).firstId();
		int locatorid2 = new Query(getCtx(),MLocator.Table_Name,"M_Warehouse_ID="+tugboat2.get_ValueAsInt("M_Warehouse_ID"),get_TrxName()).firstId();
//		if(getMovementID(spositionline.getStartDate().getHours(),tugboat1.get_ValueAsInt("M_Locator_ID"),tugboat2.get_ValueAsInt("M_Locator_ID"))>0){
//			return "";
//		}
		
		MProduct product= new MProduct(getCtx(),p_M_Product_ID,get_TrxName());
		movement.setM_Warehouse_ID(tugboat1.get_ValueAsInt("M_Warehouse_ID"));
		movement.setC_Project_ID(sactivity.get_ValueAsInt("C_Project_ID"));
		movement.set_ValueOfColumn("M_WarehouseTo_ID", tugboat2.get_ValueAsInt("M_WareHouse_ID"));
		movement.setMovementDate(sactivity.getStartDate());
		movement.setAD_Org_ID(sactivity.getAD_Org_ID());
		movement.saveEx();
		
		MMovementLine moveline=new MMovementLine(movement);
		moveline.setM_Product_ID(p_M_Product_ID);
		moveline.setM_Locator_ID(locatorid1);
		moveline.setM_LocatorTo_ID(locatorid2);
		moveline.setMovementQty(sactivity.getTotalLiters());
		moveline.setQtyEntered(sactivity.getTotalLiters());
		moveline.setC_UOM_ID(product.getC_UOM_ID());
		moveline.saveEx();
		
		sactivity.set_ValueOfColumn("Processed", true);
		sactivity.set_ValueOfColumn("M_Movement_ID", movement.getM_Movement_ID());
		sactivity.saveEx();
		
		movement.setDocAction(DocAction.ACTION_Complete);
		if(movement.processIt(DocAction.ACTION_Complete)){
			String msg = Msg.parseTranslation(getCtx(), "@Intra Warehouse@ @Created@ " + movement.getDocumentNo());
			addLog(movement.getM_Movement_ID(), null, null, msg, MMovement.Table_ID, movement.getM_Movement_ID());
		}

		return "";
	}
	
	protected int getMovementID(int hour,int M_Locator_ID,int M_LocatorTO_ID){
		int movementid=0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M_MovementLine_ID FROM M_MovementLine WHERE "+M_Locator_ID+" IN (M_Locator_ID,M_LocatorTO_ID) AND DATE_PART('hour',created) <="+hour+" OR "+M_LocatorTO_ID+" IN (M_Locator_ID,M_LocatorTO_ID)");
		movementid=DB.getSQLValue(get_TrxName(), sql.toString());
		return movementid;
	}

}
