package org.toba.habco.timesheet.process;

import java.sql.Timestamp;

import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;

public class HBC_ProcessSetEndDate extends SvrProcess{

	int p_HBC_Position_ID=0;
	@Override
	protected void prepare() {
		p_HBC_Position_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		MPosition position = new MPosition(getCtx(),p_HBC_Position_ID,get_TrxName());
		
		// must delete end related with sorting activity
		deleteEndDate();
		
		MShipActivity[] shipActivitys=position.getShipActivityDesc();
		Timestamp enddate = null;
		int count = 0;
		for(MShipActivity shipActivity:shipActivitys){
			count += 1;
			if(count == 1){
				enddate=shipActivity.getStartDate();
				continue;
			}else{
				//phie
				if(shipActivity.get_ValueAsBoolean("isSecondActivity"))
					continue;
				//end phie
				shipActivity.setFinishDate(enddate);
				shipActivity.saveEx();
				enddate=shipActivity.getStartDate();
			}
				
		}
		overing();
		return "Refreshed";
	}

	/**
	 * delete end date
	 */
	protected void deleteEndDate(){
		
		MPosition position = new MPosition(getCtx(),p_HBC_Position_ID,get_TrxName());
		
		MShipActivity[] shipActivitys=position.getShipActivityDesc();
		
		for(MShipActivity shipActivity:shipActivitys){
			//shipActivity.setFinishDate(null);
			//shipActivity.saveEx();
			
			//phie
			if(shipActivity.get_ValueAsBoolean("isSecondActivity"))
				continue;
			//end phie
			
			int LastShipActivityID= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+shipActivity.getHBC_Position_ID(),get_TrxName())
					.setOnlyActiveRecords(true)
					.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
					.setClient_ID()
					.firstId();
			
			if(shipActivity.getHBC_ShipActivity_ID()==LastShipActivityID)
				continue;
			else{
				String sql = "UPDATE "+MShipActivity.Table_Name+" SET FinishDate = NULL WHERE "+MShipActivity.COLUMNNAME_HBC_ShipActivity_ID+"=?";
				int no = DB.executeUpdate(sql, shipActivity.get_ID(), get_TrxName());
				log.info("UPDATED FinishDate Ship Activity#"+no);
			}
		}
		
	}
	
	protected void overing(){
			
		MPosition position = new MPosition(getCtx(),p_HBC_Position_ID,get_TrxName());
		MShipActivity[] shipActivitys=position.getShipActivity();
		int t_TugBoat_Over = position.getHBC_Tugboat_ID();
		for(MShipActivity shipActivity:shipActivitys){
			if(shipActivity.getHBC_Tugboat_ID() == 0)
				;
			else{
				shipActivity.setHBC_Tugboat_ID(t_TugBoat_Over);
				shipActivity.saveEx();		 		
			}
			if(shipActivity.getTugBoatPartner_ID() > 0 && shipActivity.getC_Activity_ID()!=1000023 && shipActivity.getC_Activity_ID()!=1000006){
				t_TugBoat_Over = shipActivity.getTugBoatPartner_ID();
			}
		}
	}
	
}
