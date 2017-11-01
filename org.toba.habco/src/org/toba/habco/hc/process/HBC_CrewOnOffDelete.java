package org.toba.habco.hc.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.MCrewOnOff;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeJob;

public class HBC_CrewOnOffDelete extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int HBC_CrewOnOff_ID = getRecord_ID();
		MCrewOnOff crewOnOff = new MCrewOnOff(getCtx(), HBC_CrewOnOff_ID, get_TrxName());
		crewOnOff.deleteEx(true, get_TrxName());
		
		MEmployee employee = new MEmployee(getCtx(), crewOnOff.getHC_Employee_ID(), get_TrxName());
		
		String whereClause = "SELECT HC_EmployeeJob_ID FROM HC_EmployeeJob WHERE HC_Employee_ID=? AND HC_Status='A'";
		int employeeJobID = DB.getSQLValue(get_TrxName(), whereClause, crewOnOff.getHC_Employee_ID());
		MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), employeeJobID, get_TrxName());

		employeeJob.refreshFromDataChange();
		
		return "Deleted";
	}

}
