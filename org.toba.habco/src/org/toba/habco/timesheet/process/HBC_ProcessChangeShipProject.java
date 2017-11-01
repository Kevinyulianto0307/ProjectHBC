package org.toba.habco.timesheet.process;

import java.util.logging.Level;

import org.compiere.model.MProject;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MBarge;
import org.toba.habco.model.MTugboat;

public class HBC_ProcessChangeShipProject extends SvrProcess {

	int p_C_Project_ID= 0;
	int p_HBC_Tugboat_ID=0;
	int p_HBC_Barge_ID=0;

	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			 else if (name.equals("HBC_Tugboat_ID")){
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
			} else if (name.equals("HBC_Barge_ID")){
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
			} else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_Project_ID=getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_C_Project_ID==0){
			return "Project not selected";
		}

		boolean noTugboat = false;
		boolean noBarge = false;
		
		if(p_HBC_Tugboat_ID==0)
			noTugboat = true;
		if (p_HBC_Barge_ID==0)
			noBarge = true;

		if (noTugboat && noBarge)
			return "No Tugboat / Barge Selected";
		
		StringBuilder msg = new StringBuilder();
		
		MProject project=  new MProject (getCtx(),p_C_Project_ID,get_TrxName());	
		if (!noTugboat) {
			project.set_CustomColumn("HBC_Tugboat_ID",p_HBC_Tugboat_ID);
			MTugboat tb = new MTugboat(getCtx(), p_HBC_Tugboat_ID, get_TrxName());
			msg.append("Tugboat Changed to ").append(tb.getValue());
		}
		if (!noBarge) {
			project.set_CustomColumn("HBC_Barge_ID",p_HBC_Tugboat_ID);
			if (!noTugboat)
				msg.append(",");
			
			MBarge ba = new MBarge(getCtx(), p_HBC_Barge_ID, get_TrxName());
			msg.append(" Barge Changed to ").append(ba.getValue());
		}
			project.saveEx();

		return msg.toString();

		
	}

}
