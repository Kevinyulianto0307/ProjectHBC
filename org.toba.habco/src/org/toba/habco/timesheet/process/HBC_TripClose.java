package org.toba.habco.timesheet.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.X_HBC_ShipActivity;
import org.toba.habco.model.X_HBC_ShipSpecialActivity;

public class HBC_TripClose extends SvrProcess{

	//int p_C_Project_ID= 0;
	int p_HBC_Trip_ID = 0;
	//Timestamp EffectiveDateFrom = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			//String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			/*
			
			else if (name.equals("C_Project_ID")){
				p_C_Project_ID = para[i].getParameterAsInt();
				
			} else if (name.equals("EffectiveDateFrom")){
					EffectiveDateFrom = para[i].getParameterAsTimestamp();
					
			else if (name.equals("HBC_Tugboat_ID")){
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
			}else if (name.equals("HBC_Barge_ID")){
				p_HBC_Barge_ID = para[i].getParameterAsInt();
			
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			*/
		}
		
		p_HBC_Trip_ID = getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_Trip_ID==0){
			return "Trip not selected";
		}
		
		
		MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		
		MPosition[] positions = trip.getPosition();
		
		for (MPosition position: positions) {
			
			X_HBC_ShipActivity[] shipActivities = position.getShipActivity();
			for (X_HBC_ShipActivity shipActivity : shipActivities) {
				shipActivity.setProcessed(true);
				shipActivity.saveEx();
			}
				
			X_HBC_ShipSpecialActivity[] shipSpecialActivities = position.getShipSpecialActivity();
			for (X_HBC_ShipSpecialActivity shipSpecialActivity : shipSpecialActivities) {
				shipSpecialActivity.setProcessed(true);
				shipSpecialActivity.saveEx();
			}
			
			position.set_CustomColumn("Processed", 'Y');
			position.saveEx();
			
		}
		return "Trip Closed";
	}

}
