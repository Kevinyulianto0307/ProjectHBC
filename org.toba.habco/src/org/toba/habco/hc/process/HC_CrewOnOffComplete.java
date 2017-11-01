package org.toba.habco.hc.process;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.base.Core;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPInstance;
import org.compiere.model.MProcess;
import org.compiere.model.Query;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.toba.habco.model.MCrewOnOff;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeJob;
import org.toba.habco.model.MJobDataChange;

public class HC_CrewOnOffComplete extends SvrProcess{

	int p_CrewOnOff_ID = 0;
	boolean p_BlackList = false;
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
			else if (name.equals("isBlackList")){
				p_BlackList = para[i].getParameterAsBoolean();
			}
		else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		p_CrewOnOff_ID = getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_CrewOnOff_ID==0){
			return "Crew On/Off Transaction not selected";
		}
		
		MCrewOnOff crewOnOff = new MCrewOnOff(getCtx(), p_CrewOnOff_ID, get_TrxName());

		if (crewOnOff.getHC_Employee_ID() <= 0)
			return "Aborted.. No Employee Selected";
			
		MEmployee employee = new MEmployee(getCtx(), crewOnOff.getHC_Employee_ID(), get_TrxName());
		
		//@KevinY HBC - 2395
		String sql = "SELECT HC_EmployeeJob_ID FROM HC_EmployeeJob WHERE "
				+ "HC_Employee_ID=? "
				+ "AND HC_Job_ID=?";
		int employeeJobID = DB.getSQLValue(
				get_TrxName(), sql, employee.getHC_Employee_ID(), crewOnOff.getPreviousJob_ID());
		
		String whereClause = " HBC_CrewOnOff.HC_Employee_ID = ? "
				+ "AND HBC_CrewOnOff.isActive='Y' "
				+ "AND HBC_CrewOnOff.Processed='Y' "
				+ "AND (jdc.isCancelled ='N' "
				+ "OR jdc.isCancelled IS NULL) ";
		
		List<MCrewOnOff> CrewOnOffs = new ArrayList<MCrewOnOff>();
		CrewOnOffs = new Query(getCtx(), MCrewOnOff.Table_Name, whereClause, get_TrxName())
						.setParameters(crewOnOff.getHC_Employee_ID())
						.addJoinClause(" JOIN HC_JobDataChange jdc ON "
								+ "HBC_CrewOnOff.HC_JobDataChange_ID = jdc.HC_JobDataChange_ID ")
						.setOrderBy("HBC_CrewOnOff.Updated ASC")
						.list();
		//@KevinY end
		
		if (employeeJobID <= 0)
			return "No Employee Job Record found for Employee Name:" + employee.getName();
		
		MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), employeeJobID, get_TrxName());
		
		//@KevinY HBC - 2395
		if(employeeJob.get_ValueAsInt("SeqNo")!=1)
			return "Error: Selected Job related to employee is not first sequence";
		//@KevinY end
		
		//Edited by TommyAng : Renew Offboarding
		if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)) {
			//employee.set_CustomColumn("IsOnLeave", "Y");
			//employee.set_CustomColumn("LeaveStartDate", crewOnOff.getDateTrx());
			//employee.set_CustomColumn("LeaveEndDate", null);
			
			employee.set_CustomColumn("HC_WorkEndDate", crewOnOff.getDateTrx());
			//@KevinY HBC - 2395
				//employee.set_CustomColumn("HC_Status", "T");
			//@KevinY end
			employee.set_CustomColumn("isBlackList", p_BlackList);
			employee.saveEx();
		//End edit
		//Edited by TommyAng : Renew Onboarding
		} else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding)) {
			/*
			employee.set_CustomColumn("IsOnLeave", "N");
			employee.set_CustomColumn("LeaveStartDate", null);
			employee.set_CustomColumn("LeaveEndDate", crewOnOff.getDateTrx());
			*/
			employee.set_CustomColumn("HC_Status", "A");
			employee.set_CustomColumn("HC_WorkStartDate", crewOnOff.getDateTrx());
			employee.saveEx();
		//End edit
			
		//Created by TommyAng : OnLeave Action
		}  else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_OnLeave)) {
			employee.set_CustomColumn("IsOnLeave", "Y");
			employee.set_CustomColumn("LeaveStartDate", crewOnOff.get_Value("LeaveStartDate"));
			employee.set_CustomColumn("LeaveEndDate", crewOnOff.get_Value("LeaveEndDate"));
			employee.saveEx();
		//End create	
		//Created by TommyAng : OnDuty Action
		}  else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_OnDuty)) {
			employee.set_CustomColumn("IsOnLeave", "N");
			employee.set_CustomColumn("DutyDate", crewOnOff.getDateTrx());
			employee.saveEx();
		//End create	
			
		}  
			//@KevinY HBC - 2395
			/*else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer)) {
			
			employeeJob.setHC_Job_ID(crewOnOff.getHC_Job_ID());
		//	employeeJob.setHC_Position_ID(crewOnOff.getHC_Position_ID());
			employeeJob.setHC_Manager_ID(crewOnOff.getHC_Manager_ID());
			employeeJob.setHC_ManagerPosition_ID(crewOnOff.getHC_ManagerPosition_ID());
			employeeJob.setHC_Org_ID(crewOnOff.getHC_OrgTo_ID());
			employeeJob.setHC_Compensation1(crewOnOff.getHC_Compensation1());
			employeeJob.setHC_Compensation2(crewOnOff.getHC_Compensation2());
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			employeeJob.setDescription(crewOnOff.getDescription());
			employeeJob.setEffectiveDateFrom(crewOnOff.getDateTrx());		
			employeeJob.setHC_JobAction(MEmployeeJob.HC_JOBACTION_Transfer);
			employeeJob.setHC_Reason_ID(crewOnOff.getHC_Reason_ID());
			employeeJob.saveEx();
			
		}*/
		
		//employeeJob.saveEx();

		int flag = 0;//flag for onboarding custom
		MJobDataChange jobDataChange = new MJobDataChange(getCtx(), crewOnOff.get_ValueAsInt("HC_JobDataChange_ID"), get_TrxName());
		jobDataChange.setHC_Employee_ID(employee.getHC_Employee_ID());
		jobDataChange.set_ValueOfColumn("HC_EmployeeJob_ID", employeeJob.getHC_EmployeeJob_ID());
		if(crewOnOff.getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer)){
			jobDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
			jobDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Transfer);
			//jobDataChange.setHC_Org_ID(crewOnOff.getHC_OrgTo_ID());
		}
		else if(crewOnOff.getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)){
			if(CrewOnOffs.size() >0){
				if(CrewOnOffs.get(CrewOnOffs.size()-1).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)){
					throw new AdempiereException("Error: Can't Terminate Terminated Employee");
				}
			}
			jobDataChange.setHC_Status(MJobDataChange.HC_STATUS_Terminate);
			jobDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Terminate);
			//jobDataChange.setHC_Org_ID(crewOnOff.getHC_Org_ID());
		}else if(crewOnOff.getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding)){
			if(CrewOnOffs.size() > 0){
				flag = 0;
				for(int i = 0; i < CrewOnOffs.size() ; i++ ){
					if(CrewOnOffs.get(i).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding)){
						flag++;
					}
				}//for
				if(flag !=0){
					/*if crew on off has been once onboarding*/
					if(CrewOnOffs.get(CrewOnOffs.size() - 1 ).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding) ||
							CrewOnOffs.get(CrewOnOffs.size() - 1 ).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer))
						throw new AdempiereException("Error: Can't Onboard Employee already OnBoarding ");
				}else{
					/*if crew on off never on boarding before*/
					if(CrewOnOffs.get(CrewOnOffs.size()-1).getHBC_CrewOnOffType()
							.equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer)){
						jobDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
						jobDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Hire);
					}
				}//else
				
				/*if employee data is terminate then rehire*/
				if(CrewOnOffs.get(CrewOnOffs.size()-1).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)){
						jobDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
						jobDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Rehire);
				}
			}else{
				jobDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
				jobDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Hire);
			}
			//jobDataChange.setHC_Org_ID(crewOnOff.getHC_Org_ID());
		}//else if
		
		jobDataChange.setHC_Org_ID(crewOnOff.getHC_Org_ID());
		jobDataChange.set_ValueOfColumn("HC_OrgTo_ID", crewOnOff.getHC_OrgTo_ID());
		jobDataChange.set_ValueOfColumn("PreviousJob_ID", crewOnOff.getPreviousJob_ID());
		jobDataChange.setHC_Job_ID(crewOnOff.getHC_Job_ID());//Job To
		jobDataChange.setHC_Compensation1(crewOnOff.getHC_Compensation1());
		jobDataChange.setHC_Compensation2(crewOnOff.getHC_Compensation2());
		jobDataChange.setHC_Compensation3(employeeJob.getHC_Compensation3());
		jobDataChange.set_ValueOfColumn("PreviousHC_Compensation1", crewOnOff.get_Value("PreviousHC_Compensation1"));
		jobDataChange.set_ValueOfColumn("PreviousHC_Compensation2", crewOnOff.get_Value("PreviousHC_Compensation2"));
		jobDataChange.setEffectiveDateFrom(crewOnOff.getDateTrx());
		jobDataChange.setHC_PayGroup_ID(employeeJob.getHC_PayGroup_ID());
		if(crewOnOff.getHC_Reason_ID() > 0)
			jobDataChange.setHC_Reason_ID(crewOnOff.getHC_Reason_ID());
		if(crewOnOff.getHC_Manager_ID() > 0)
			jobDataChange.setHC_Manager_ID(crewOnOff.getHC_Manager_ID());
		if(crewOnOff.getHC_ManagerPosition_ID() > 0)
			jobDataChange.setHC_ManagerPosition_ID(crewOnOff.getHC_ManagerPosition_ID());
		if(crewOnOff.get_ValueAsInt("PreviousHC_Manager_ID") > 0)
			jobDataChange.set_ValueOfColumn("PreviousHC_Manager_ID", crewOnOff.get_ValueAsInt("PreviousHC_Manager_ID"));
		if(crewOnOff.get_ValueAsInt("PreviousHC_ManagerLocation_ID") > 0)
			jobDataChange.set_ValueOfColumn("PreviousHC_ManagerLocation_ID", crewOnOff.get_ValueAsInt("PreviousHC_ManagerLocation_ID"));
		jobDataChange.saveEx();
		
		
		
		ProcessCall process = Core.getProcess("org.toba.habco.hc.process.HC_JobDataChangeComplete");
		MProcess JDC_Complete = new MProcess(Env.getCtx(), 1100023, get_TrxName());
		
		MPInstance instance = new MPInstance(JDC_Complete, jobDataChange.getHC_JobDataChange_ID());
		instance.saveEx();
		
		ProcessInfo pi = new ProcessInfo("HC_JobDataChangeComplete",1100023,300174,jobDataChange.getHC_JobDataChange_ID());
		pi.setAD_Client_ID(1000013);
		pi.setAD_User_ID(100);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());
		
		Trx trx = Trx.get(get_TrxName(), false);
		
		process.startProcess(Env.getCtx(), pi,  trx);
		//@KevinY end
		crewOnOff.setProcessed(true);
		crewOnOff.saveEx();
		
		if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding))
				return "Employee Offboarding Success";
		else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding)) 
				return "Employee Onboarding Success";
		else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_OnLeave))
				return "Employee OnLeave Success";
		else if (crewOnOff.getHBC_CrewOnOffType()
				.equals(MCrewOnOff.HBC_CREWONOFFTYPE_OnDuty))
				return "Employee OnDuty Success";
		
		return "Crew Transfer Success" ;
	}

}
