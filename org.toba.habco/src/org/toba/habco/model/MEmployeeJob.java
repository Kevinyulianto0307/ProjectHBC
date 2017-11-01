package org.toba.habco.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;

public class MEmployeeJob extends X_HC_EmployeeJob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4004204955679151108L;

	public MEmployeeJob(Properties ctx, int HC_EmployeeJob_ID, String trxName) {
		super(ctx, HC_EmployeeJob_ID, trxName);
	}

	public MEmployeeJob(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	//@KevinY HBC - 2395
		/**
		 * 
		 * @param ctx
		 * @param employee
		 * @param trxName
		 * @return
		 * All Active EmployeeJob with related Employee 
		 */
		public static List<MEmployeeJob> GetActive(Properties ctx,MEmployee employee,String trxName){
			String whereClause = "HC_Employee_ID = ? AND HC_Status='A' AND isActive='Y' ";
			
			List<MEmployeeJob> employeeJobs = new ArrayList<MEmployeeJob>();
			employeeJobs=  new Query(ctx, MEmployeeJob.Table_Name, whereClause,trxName)
			.setParameters(employee.getHC_Employee_ID())
			.list();
		
			return employeeJobs;
		}//GetActive
		
		/**
		 * 
		 * @param ctx
		 * @param employee
		 * @param trxName
		 * @return
		 * All EmployeeJob with related Employee
		 */
		public static List<MEmployeeJob> GetAll(Properties ctx, MEmployee employee, String trxName){
			String whereClause = "HC_Employee_ID = ?";
			List<MEmployeeJob> employeeJobs = new ArrayList<MEmployeeJob>();
			employeeJobs = new Query(ctx,MEmployeeJob.Table_Name, whereClause, trxName)
			.setParameters(employee.getHC_Employee_ID())
			.list();
			
			return employeeJobs;
		}//GetAll
	//@KevinY end
	
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return true if can be saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success || newRecord)
			return success;
		
		MEmployee employee = new MEmployee(getCtx(), getHC_Employee_ID(), get_TrxName());
		employee.set_ValueOfColumn("HC_Org_ID", getHC_Org_ID());
		employee.saveEx();
		
		return true;
	}

	public void refreshFromDataChange()  {
		final String whereClause = "EffectiveDateFrom = (SELECT MAX(EffectiveDateFrom) "
				+ "FROM HC_JobDataChange WHERE EffectiveDateFrom <?) AND HC_EmployeeJob_ID=?";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(System.currentTimeMillis()));
		cal.add(Calendar.DATE, 1);
		Timestamp currentDate = null;
		currentDate.setTime(cal.getTime().getTime());
		
		MJobDataChange jDataChange = new Query(getCtx(), MJobDataChange.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.setParameters(new Object[]{currentDate, getHC_EmployeeJob_ID()})
		.setOrderBy(COLUMNNAME_EffectiveDateFrom + " DESC")
		.first();
		
		MEmployeeJob employeeJob = this;
		MEmployee employee = new MEmployee(getCtx(), getHC_Employee_ID(), get_TrxName());
		
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_DataChange)) {
			employeeJob.setHC_EmployeeCategory_ID(jDataChange.getHC_EmployeeCategory_ID());
			employeeJob.setHC_EmployeeClass_ID(jDataChange.getHC_EmployeeClass_ID());
			employeeJob.setHC_EmployeeGrade_ID(jDataChange.getHC_EmployeeGrade_ID());
			employeeJob.setC_Location_ID(jDataChange.getC_Location_ID());
		}
		
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Promotion) ||
				jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Demotion) ||
				jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Transfer)
				) {
			employeeJob.setHC_Job_ID(jDataChange.getHC_Job_ID());
			employeeJob.setHC_Position_ID(jDataChange.getHC_Position_ID());
			employeeJob.setHC_Manager_ID(jDataChange.getHC_Manager_ID());
			employeeJob.setHC_ManagerPosition_ID(jDataChange.getHC_ManagerPosition_ID());
			employeeJob.setHC_Org_ID(jDataChange.getHC_Org_ID());
			employeeJob.setHC_Compensation1(jDataChange.getHC_Compensation1());
			employeeJob.setHC_Compensation2(jDataChange.getHC_Compensation2());
			employeeJob.setHC_Compensation3(jDataChange.getHC_Compensation3());
			employeeJob.setHC_Compensation4(jDataChange.getHC_Compensation4());
			employeeJob.setHC_PayGroup_ID(jDataChange.getHC_PayGroup_ID());
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Active); 
		}
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Hire)) {
			String whereClause2 = "SELECT COALESCE(Max(SeqNo),0)+1 FROM HC_EmployeeJOB WHERE HC_Employee_ID=?";
			int seqno = DB.getSQLValue(get_TrxName(), whereClause2, jDataChange.getHC_Employee_ID());
			employeeJob.set_CustomColumn("SeqNo", seqno);
			employeeJob.setHC_EffectiveSeq(99);
			
		}
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Hire) ||
				jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Rehire)) {
			
			employeeJob.setHC_Employee_ID(jDataChange.getHC_Employee_ID());
			employeeJob.setHC_Job_ID(jDataChange.getHC_Job_ID());
			employeeJob.setHC_Position_ID(jDataChange.getHC_Position_ID());
			employeeJob.setHC_Manager_ID(jDataChange.getHC_Manager_ID());
			employeeJob.setHC_ManagerPosition_ID(jDataChange.getHC_ManagerPosition_ID());
			employeeJob.setHC_Org_ID(jDataChange.getHC_Org_ID());
			employeeJob.setHC_EmployeeCategory_ID(jDataChange.getHC_EmployeeCategory_ID());
			employeeJob.setHC_EmployeeClass_ID(jDataChange.getHC_EmployeeClass_ID());
			employeeJob.setHC_EmployeeGrade_ID(jDataChange.getHC_EmployeeGrade_ID());
			employeeJob.setC_Location_ID(jDataChange.getC_Location_ID());
			employeeJob.setHC_Compensation1(jDataChange.getHC_Compensation1());
			employeeJob.setHC_Compensation2(jDataChange.getHC_Compensation2());
			employeeJob.setHC_Compensation3(jDataChange.getHC_Compensation3());
			employeeJob.setHC_Compensation4(jDataChange.getHC_Compensation4());
			employeeJob.setHC_PayGroup_ID(jDataChange.getHC_PayGroup_ID());
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Active); 
		}
		
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_SalaryChange)) {
			employeeJob.setHC_Compensation1(jDataChange.getHC_Compensation1());
			employeeJob.setHC_Compensation2(jDataChange.getHC_Compensation2());
			employeeJob.setHC_Compensation3(jDataChange.getHC_Compensation3());
			employeeJob.setHC_Compensation4(jDataChange.getHC_Compensation4());
			employeeJob.setHC_PayGroup_ID(jDataChange.getHC_PayGroup_ID());
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Active);
		}
		
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Terminate)) {
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Terminate);
			
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Terminate);
		}
		
		employeeJob.setDescription(jDataChange.getDescription());
		employeeJob.setEffectiveDateFrom(jDataChange.getEffectiveDateFrom());		
		employeeJob.setHC_JobAction(jDataChange.getHC_JobAction());
		employeeJob.setHC_Reason_ID(jDataChange.getHC_Reason_ID());
		employeeJob.saveEx();
		employee.saveEx();

	}
}
