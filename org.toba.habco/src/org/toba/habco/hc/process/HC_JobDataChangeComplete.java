package org.toba.habco.hc.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeJob;
import org.toba.habco.model.MJobDataChange;

public class HC_JobDataChangeComplete extends SvrProcess{

	int p_JobDataChange_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			/*
			else if (name.equals("C_Project_ID")){
				p_C_Project_ID = para[i].getParameterAsInt();
				
			} else if (name.equals("EffectiveDateFrom")){
					EffectiveDateFrom = para[i].getParameterAsTimestamp();
			
			}*/
		else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
			
	}//prepare
	
	@Override
	protected String doIt() throws Exception {
		p_JobDataChange_ID = getRecord_ID();	
		
		if(p_JobDataChange_ID==0){
			return "Data Change Transaction not selected";
		}
		
		MJobDataChange jDataChange = new MJobDataChange(getCtx(), p_JobDataChange_ID, get_TrxName());
		if (jDataChange.isProcessed())
			return "Abort.. Data Change Transaction Has Been Processed";
		
		//@TommyAng
		if(jDataChange.getEffectiveDateFrom()==null)
			return "Abort.. Effective Date From is Mandatory";
		//end @TommyAng
		
		int employeeJobID = 0;
		int employeeID = 0; 
		//@KevinY HBC - 2395
		/*if (!jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Hire)) {
			employeeJobID = jDataChange.get_ValueAsInt("HC_EmployeeJob_ID");
			employeeID = jDataChange.get_ValueAsInt("HC_Employee_ID");
		}*/
		employeeJobID = jDataChange.get_ValueAsInt("HC_EmployeeJob_ID");
		employeeID = jDataChange.get_ValueAsInt("HC_Employee_ID");
		//@KevinY end
		
		MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), employeeJobID, get_TrxName());
		MEmployee employee = new MEmployee(getCtx(), employeeID, get_TrxName());
		
		//@KevinY HBC - 2395
		List<MEmployeeJob> employeeJobs = new ArrayList<MEmployeeJob>();
		employeeJobs = MEmployeeJob.GetAll(getCtx(), employee, get_TrxName());
		//@KevinY end
		
		if (jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Promotion) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Demotion) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_DataChange) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Hire) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Rehire) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_SalaryChange) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Transfer)) {
		
				if (employee.get_ValueAsBoolean("IsBlackList"))
					throw new AdempiereException("Abort.. Cannot hire / rehire blacklisted employee");
		}
		
		if (jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_DataChange)) {
			employeeJob.setHC_EmployeeCategory_ID(jDataChange.getHC_EmployeeCategory_ID());
			employeeJob.setHC_EmployeeClass_ID(jDataChange.getHC_EmployeeClass_ID());
			employeeJob.setHC_EmployeeGrade_ID(jDataChange.getHC_EmployeeGrade_ID());
			employeeJob.setC_Location_ID(jDataChange.getC_Location_ID());	
			employeeJob.saveEx();
		}//DataChange
		
		if (jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Promotion) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Demotion) ||
				jDataChange.getHC_JobAction().equals(MJobDataChange.HC_JOBACTION_Transfer)
				) {
			
			//@KevinY HBC-2563
				employeeJob.setHC_Job_ID(jDataChange.getHC_Job_ID());
				//employeeJob.setHC_Position_ID(jDataChange.getHC_Position_ID());
				employeeJob.setHC_Manager_ID(jDataChange.getHC_Manager_ID());
				employeeJob.setHC_ManagerPosition_ID(jDataChange.getHC_ManagerPosition_ID());
				employeeJob.setHC_Org_ID(jDataChange.get_ValueAsInt("HC_OrgTo_ID"));
				employeeJob.setHC_Compensation1(jDataChange.getHC_Compensation1());
				employeeJob.setHC_Compensation2(jDataChange.getHC_Compensation2());
				employeeJob.setHC_Compensation3(jDataChange.getHC_Compensation3());
				if(jDataChange.getHC_PayGroup_ID() > 0)
					employeeJob.setHC_PayGroup_ID(jDataChange.getHC_PayGroup_ID());
				employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
				employeeJob.setHC_JobAction(jDataChange.getHC_JobAction());
				if(employeeJob.get_ValueAsInt("SeqNo") == 1){
					for(int i = 0 ; i < employeeJobs.size() ; i++){
						if(employeeJob.getHC_EmployeeJob_ID() != employeeJobs.get(i).getHC_EmployeeJob_ID()){
							employeeJobs.get(i).setHC_Status(MEmployeeJob.HC_STATUS_Active);
							employeeJobs.get(i).setDescription(jDataChange.getDescription());
							employeeJobs.get(i).setHC_JobAction(MEmployeeJob.HC_JOBACTION_Transfer);
							employeeJobs.get(i).saveEx();
						}
					}
				}
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Active); 
			employee.setHC_Org_ID(jDataChange.get_ValueAsInt("HC_OrgTo_ID"));
			employee.saveEx();
			//@KevinY end
		}//Transfer || Promotion || Demotion
		
		/*
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Hire)) {
			String whereClause = "SELECT COALESCE(Max(SeqNo),0)+1 FROM HC_EmployeeJOB WHERE HC_Employee_ID=?";
			int seqno = DB.getSQLValue(get_TrxName(), whereClause, jDataChange.getHC_Employee_ID());
			employeeJob.set_CustomColumn("SeqNo", seqno);
			employeeJob.setHC_EffectiveSeq(99);

		}//Hire
		*/
		
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Hire) ||
				jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Rehire)) {

			
				
			employeeJob.setHC_Job_ID(jDataChange.get_ValueAsInt("PreviousJob_ID"));
			employeeJob.setHC_Manager_ID(jDataChange.get_ValueAsInt("PreviousHC_Manager_ID"));
			employeeJob.setHC_ManagerPosition_ID(jDataChange.get_ValueAsInt("PreviousHC_ManagerPosition_ID"));
			employeeJob.setHC_Org_ID(jDataChange.getHC_Org_ID());
			employeeJob.setC_Location_ID(jDataChange.getC_Location_ID());
			employeeJob.setHC_Compensation1((BigDecimal)jDataChange.get_Value("PreviousHC_Compensation1"));
			employeeJob.setHC_Compensation2((BigDecimal)jDataChange.get_Value("PreviousHC_Compensation2"));
			employeeJob.setHC_Compensation3(jDataChange.getHC_Compensation3());
			employeeJob.setHC_PayGroup_ID(jDataChange.getHC_PayGroup_ID());
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			employeeJob.set_ValueOfColumn("HC_WorkEndDate", null);
			if(jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Rehire)){
				employeeJob.set_ValueOfColumn("HC_WorkStartDate", (Timestamp)jDataChange.get_Value("EffectiveDateFrom"));
				employee.set_ValueOfColumn("HC_WorkStartDate", (Timestamp)jDataChange.get_Value("EffectiveDateFrom"));
			}
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Active);
			employee.setHC_Org_ID(jDataChange.getHC_Org_ID());
			employee.set_ValueOfColumn("HC_WorkEndDate", null);
			employee.saveEx();
			
			//@KevinY HBC - 2395
			if(employeeJob.get_ValueAsInt("SeqNo") == 1)
				for(int i = 0 ; i < employeeJobs.size() ; i++){
					if(employeeJob.getHC_EmployeeJob_ID() != employeeJobs.get(i).getHC_EmployeeJob_ID()){
						employeeJobs.get(i).setHC_Status(MEmployeeJob.HC_STATUS_Active);
						employeeJobs.get(i).setDescription(jDataChange.getDescription());
						employeeJobs.get(i).set_ValueOfColumn("HC_WorkEndDate", null);
						if(jDataChange.getHC_JobAction()
								.equals(MJobDataChange.HC_JOBACTION_Rehire)){
								employeeJobs.get(i).set_ValueOfColumn("HC_WorkStartDate", (Timestamp)jDataChange.get_Value("EffectiveDateFrom"));
						}
						employeeJobs.get(i).setHC_JobAction(jDataChange.getHC_JobAction());
						employeeJobs.get(i).saveEx();
					}
				}
			//@KevinY end
		}//Rehire||Hire
		
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
		}//SalaryChange
		
		if (jDataChange.getHC_JobAction()
				.equals(MJobDataChange.HC_JOBACTION_Terminate)) {
			
			employee.setHC_Status(MEmployee.HC_STATUS_Terminate);
			employee.setHC_Org_ID(jDataChange.getHC_Org_ID());
			employee.setHC_WorkEndDate(jDataChange.getEffectiveDateFrom());
			employee.saveEx();
			
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Terminate);
			employeeJob.setHC_JobAction(jDataChange.getHC_JobAction());
			employeeJob.setHC_Manager_ID(jDataChange.get_ValueAsInt("PreviousHC_Manager_ID"));
			employeeJob.setHC_ManagerPosition_ID(jDataChange.get_ValueAsInt("PreviousHC_ManagerPosition_ID"));
			employeeJob.setHC_Compensation1((BigDecimal)jDataChange.get_Value("PreviousHC_Compensation1"));
			employeeJob.setHC_Compensation2((BigDecimal)jDataChange.get_Value("PreviousHC_Compensation2"));
			employeeJob.set_ValueOfColumn("HC_WorkEndDate", (Timestamp)jDataChange.get_Value("EffectiveDateFrom"));
			//@TommyAng
			employee.setHC_Status(MEmployee.HC_STATUS_Terminate);
			//@KevinY HBC - 2563
			if(employeeJob.get_ValueAsInt("SeqNo")== 1)
				for(int i = 0 ; i < employeeJobs.size() ; i++){
					if(employeeJob.getHC_EmployeeJob_ID() != employeeJobs.get(i).getHC_EmployeeJob_ID()){
						employeeJobs.get(i).setHC_Status("T");
						employeeJobs.get(i).set_ValueOfColumn("HC_WorkEndDate", (Timestamp)jDataChange.get_Value("EffectiveDateFrom"));
						employeeJobs.get(i).setDescription(jDataChange.getDescription());
						employeeJobs.get(i).setHC_JobAction(jDataChange.getHC_JobAction());
						employeeJobs.get(i).saveEx();
					}
				}
			//@KevinY end
		}//Terminate
		
		employeeJob.setDescription(jDataChange.getDescription());
		employeeJob.setEffectiveDateFrom(jDataChange.getEffectiveDateFrom());		
		employeeJob.setHC_JobAction(jDataChange.getHC_JobAction());
		employeeJob.setHC_Reason_ID(jDataChange.getHC_Reason_ID());
		employeeJob.saveEx();
		
		employee.setEffectiveDateFrom(jDataChange.getEffectiveDateFrom());
		employee.saveEx();
		
		jDataChange.set_ValueOfColumn("Processed", "Y");
		jDataChange.saveEx();
		
		return "Successfully Updated Job Data" ;
	}

}
