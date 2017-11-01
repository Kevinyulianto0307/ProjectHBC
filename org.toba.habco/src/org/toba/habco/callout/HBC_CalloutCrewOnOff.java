package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeJob;
import org.toba.habco.model.X_HBC_CrewOnOff;

public class HBC_CalloutCrewOnOff extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (mField.getColumnName().equals(X_HBC_CrewOnOff.COLUMNNAME_HC_Employee_ID)){
			return employee(ctx, WindowNo, mTab, mField, value) ;
		}
		/*	
		}else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("C_Project_ID")){
			return project(ctx, WindowNo, mTab, mField, value);
		*/
		
		return "";
	}//start
	
	
	private String employee(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
	
		String msg="";
		
		if (value==null){
			return msg;
		}
		
		int employeeID= (Integer) value;
	
		MEmployee employee = new MEmployee(Env.getCtx(), employeeID, null);
		
		//String whereClause = "SELECT HC_EmployeeJob_ID FROM HC_EmployeeJob WHERE HC_Employee_ID=? AND HC_STATUS='A'";
		String whereClause = "SELECT HC_EmployeeJob_ID FROM HC_EmployeeJob WHERE HC_Employee_ID=?"
				+ " AND SeqNo=1 AND HC_Status NOT IN ('B','T')";
		int employeeJobID = DB.getSQLValue(null, whereClause, employeeID);
		
		if (employeeJobID <= 0) {
			mTab.setValue("HC_Org_ID", null);
			return "No First Sequence Employee Job Record found for Employee Name:" + employee.getName();
		}
		MEmployeeJob employeeJob = new MEmployeeJob(Env.getCtx(), employeeJobID, null);
		
		//if(mTab.getValue("hbc_Crewonofftype").equals("TRF")){
		//@KevinY HBC - 2395
		Integer job= new Integer(0);
		job = employeeJob.getHC_Job_ID();
		
		/*StringBuilder sql = new StringBuilder();
		sql.append("SELECT hej.hc_job_ID "
				+ " FROM  HC_Employee he "
				+ " Left Join HC_EmployeeJob hej on hej.HC_Employee_id = he.HC_Employee_id"
				+ " WHERE he.HC_Employee_ID = ?");
		
		job = DB.getSQLValue(null, sql.toString(), new Object[]{employeeID});
		*/
		//@KevinY end
		
		String desc= new String();
		desc = employeeJob.getDescription();
		//@KevinY hBC - 2395
		/*
		sql = new StringBuilder();
		sql.append("SELECT hej.Description "
				+ " FROM  HC_Employee he "
				+ " Left Join HC_EmployeeJob hej on hej.HC_Employee_id = he.HC_Employee_id"
				+ " WHERE SeqNo= 1 and he.HC_Employee_ID = ? ");
		
		desc = DB.getSQLValueStringEx(null, sql.toString(), new Object[]{employeeID});
		*/
		//@KevinY end
		
		mTab.setValue("HC_Org_ID", employeeJob.getHC_Org_ID());
		mTab.setValue("PreviousJob_ID", job);
		mTab.setValue("Description", desc);
		//}
		
		//@KevinY HBC - 2395
		mTab.setValue("PreviousHC_Compensation1", employeeJob.getHC_Compensation1());
		mTab.setValue("PreviousHC_Compensation2", employeeJob.getHC_Compensation2());
		if(employeeJob.getHC_Manager_ID() > 0)
			mTab.setValue("PreviousHC_Manager_ID", employeeJob.getHC_Manager_ID());
		//@KevinY end
		
		
		return "";
	}//employee
}
