package org.toba.habco.timesheet.process;

import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MShipPositionLine;
import org.toba.habco.model.X_HBC_Cargo;

public class HBC_ShipPositionCreateCargo extends SvrProcess {

	int p_HBC_ShipPosition_ID=0;
	
	@Override
	protected void prepare() {
		p_HBC_ShipPosition_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_ShipPosition_ID==0){
			return "No Ship Position Selected";
		}
		
		//int loadingid[] = new Query(Env.getCtx(),MActivity.Table_Name,"Name='Loading' OR  Name='Discharging' OR Name='Draft Survey'",get_TrxName()).getIDs();
		int spositionlineid[]= new Query(getCtx(),MShipPositionLine.Table_Name,"HBC_ShipPosition_ID="+p_HBC_ShipPosition_ID,get_TrxName()).getIDs();
		for(int i=0;i<spositionlineid.length;i++){
			MShipPositionLine spositionline = new MShipPositionLine(getCtx(), spositionlineid[i], get_TrxName());
			int cargoid=0;
			if (spositionline.getC_Activity().getName().equals("Loading") || spositionline.getC_Activity().getName().equals("Draft Survey") ){
				X_HBC_Cargo cargo = new X_HBC_Cargo (getCtx(),0,get_TrxName());	
				cargo.setC_Activity_ID(spositionline.getC_Activity_ID());
				cargo.setAmountOfCargo(spositionline.getAmountOfCargo());
				cargo.setHBC_Tugboat_ID(spositionline.getHBC_Tugboat_ID());
				cargo.setFrom_PortPosition_ID(spositionline.getFrom_PortPosition_ID());
				cargo.saveEx();
				cargoid=cargo.getHBC_Cargo_ID();
			}else if (spositionline.getC_Activity().getName().equals("Discharging")){
				X_HBC_Cargo cargo = new X_HBC_Cargo(getCtx(), cargoid, get_TrxName());
				cargo.setActivityCargo_ID(spositionline.getC_Activity_ID());
				cargo.saveEx();
				//cargo.setHBC_
				
			}
		}
		return "";
	}

}
