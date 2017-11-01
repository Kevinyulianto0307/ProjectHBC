package org.toba.habco.hc.process;

import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeJob;

public class HBC_UpdateOrgEmployee extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		String whereClause = "HC_Org_ID IS NULL";
		int[] employee_IDs = new Query(getCtx(), MEmployee.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.getIDs();
		
		for (int employee_ID : employee_IDs) {
			whereClause = "HC_Employee_ID = "+employee_ID;
			int HC_EmployeeJob_ID = new Query(getCtx(), MEmployeeJob.Table_Name, whereClause, get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstId();
			
			MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), HC_EmployeeJob_ID, get_TrxName());
			int HC_Org_ID = employeeJob.getHC_Org_ID();
			
			if(HC_Org_ID > 0){
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE HC_Employee SET HC_Org_ID="+HC_Org_ID
						+" WHERE HC_Employee_ID=?");
				int no = DB.executeUpdate(sb.toString(), employee_ID, get_TrxName());
				log.info("UPDATED HC_Employee#"+no);
			}
		}
		
		return "";
	}

}
