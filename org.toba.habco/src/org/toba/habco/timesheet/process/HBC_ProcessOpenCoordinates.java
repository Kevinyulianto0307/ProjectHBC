package org.toba.habco.timesheet.process;

import org.compiere.process.SvrProcess;
import org.toba.habco.model.X_HBC_Position;
import org.zkoss.zk.ui.Executions;

public class HBC_ProcessOpenCoordinates extends SvrProcess {
	
	int p_HBC_Position_ID=0;

	@Override
	protected void prepare() {
		p_HBC_Position_ID=getRecord_ID();		
	}
	
	String getFullAdress()
	{
		String Address="";
		X_HBC_Position position = new X_HBC_Position(getCtx(), p_HBC_Position_ID, get_TrxName());
		Address=position.getCoordinates();
		return Address;
	}	

	@Override
	protected String doIt() throws Exception {
		String urlStrings = "http://local.google.com/maps?q=,+,+,+OR,+United+States";
		String message = null;
		try {
			Executions.getCurrent().sendRedirect(urlStrings, "_blank");
		}
		catch (Exception e) {
			message = e.getMessage();
		
		}
		
		return message;
	}

}
