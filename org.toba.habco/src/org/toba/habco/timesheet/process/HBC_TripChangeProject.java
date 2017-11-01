package org.toba.habco.timesheet.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.X_C_Project;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.X_HBC_ShipAssignment;
import org.toba.habco.model.X_HBC_Trip;

public class HBC_TripChangeProject extends SvrProcess{

	int p_C_Project_ID= 0;
	int p_HBC_Trip_ID = 0;
	Timestamp EffectiveDateFrom = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Project_ID")){
				p_C_Project_ID = para[i].getParameterAsInt();
				
			} else if (name.equals("EffectiveDateFrom")){
					EffectiveDateFrom = para[i].getParameterAsTimestamp();
					
			/*
			else if (name.equals("HBC_Tugboat_ID")){
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
			}else if (name.equals("HBC_Barge_ID")){
				p_HBC_Barge_ID = para[i].getParameterAsInt();
			*/
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		p_HBC_Trip_ID = getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_C_Project_ID==0){
			return "Project not selected";
		}
		
		X_C_Project project = new X_C_Project(getCtx(), p_C_Project_ID, get_TrxName());
		if (project.getProjectCategory().equals("DOC"))
			return "Cannot Change To Docking Project";
		else if (project.getProjectCategory().equals("NES"))
			return "Cannot change to New Ship Project";
		
		X_HBC_Trip trip = new X_HBC_Trip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		
		//TODO: add validation for completed trip
		if (trip.get_ValueAsBoolean("Processed")==true)
			return "Error: Cannot Update Completed Trip";
		
		//set assignment record
		X_HBC_ShipAssignment shipAssignment = new X_HBC_ShipAssignment(getCtx(), 0, get_TrxName());
		shipAssignment.setAD_Org_ID(trip.getAD_Org_ID());
		shipAssignment.setHBC_Contract_ID(project.get_ValueAsInt("HBC_Contract_ID"));
		shipAssignment.setC_Project_ID(project.getC_Project_ID());
		shipAssignment.setHBC_Barge_ID(trip.getHBC_Barge_ID());
		shipAssignment.setHBC_Tugboat_ID(trip.getHBC_Tugboat_ID());
		shipAssignment.setEffectiveDateFrom(EffectiveDateFrom);
		shipAssignment.saveEx();
		
		//change trip voyage information
		trip.setC_Project_ID(project.get_ID());
		trip.setHBC_Contract_ID(project.get_ValueAsInt("HBC_Contract_ID"));
		trip.setFrom_PortPosition_ID(project.get_ValueAsInt("From_PortPosition_ID"));
		trip.setTo_PortPosition_ID(project.get_ValueAsInt("To_PortPosition_ID"));
		trip.saveEx();
		
		return "Trip Updated: Project Change To" + project.getValue() + "-" + project.getName() ;
	}

}
