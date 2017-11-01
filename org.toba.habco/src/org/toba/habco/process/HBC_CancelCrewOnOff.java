package org.toba.habco.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.MCrewOnOff;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeJob;
import org.toba.habco.model.MJobDataChange;

/**
 * @author KevinY 
 * HBC - 2395
 * Cancel Current Crew On Off and JobDataChange and change into previous data
 */

public class HBC_CancelCrewOnOff extends SvrProcess {

	private int p_CrewOnOff_ID = 0;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_CrewOnOff_ID = getRecord_ID();
	}// prepare

	@Override
	protected String doIt() throws Exception {
		
		if(p_CrewOnOff_ID <= 0)
			throw new AdempiereException("Error: Please select Crew On Off");
		
		MCrewOnOff CurrCrewOnOff = new MCrewOnOff(getCtx(), p_CrewOnOff_ID, get_TrxName());
		
		if(CurrCrewOnOff.getHC_Employee_ID() <= 0)
			throw new AdempiereException("Error: Field Employee Data must be filled");
		
		if(CurrCrewOnOff.get_ValueAsInt("HC_JobDataChange_ID") <= 0)
			throw new AdempiereException("Error: Crew On Off must be processed first");
		
		MJobDataChange CurrJobDataChange = new MJobDataChange(
				getCtx(),CurrCrewOnOff.get_ValueAsInt("HC_JobDataChange_ID"), get_TrxName());
		
		String whereClause = "HBC_CrewOnOff.HC_Employee_ID = ? "
				+ "AND HBC_CrewOnOff.isActive='Y' "
				+ "AND HBC_CrewOnOff.Processed='Y' "
				+ "AND (jdc.isCancelled ='N' OR jdc.isCancelled IS NULL)";
		
		List<MCrewOnOff> CrewOnOffs = new ArrayList<MCrewOnOff>();
		CrewOnOffs = new Query(getCtx(), MCrewOnOff.Table_Name, whereClause, get_TrxName())
						.setParameters(CurrCrewOnOff.getHC_Employee_ID())
						.addJoinClause(" JOIN HC_JobDataChange jdc ON "
								+ "HBC_CrewOnOff.HC_JobDataChange_ID = jdc.HC_JobDataChange_ID")
						.setOrderBy("HBC_CrewOnOff.Updated ASC")
						.list();
		
		/*check CurrCrewOnOff is the last crewonoff and jobdatachange is not canceled*/
		if(CrewOnOffs.size() <= 0){
			throw new AdempiereException("Error: Not Found Crew On Off Processed");
		}
		
		int lastCrew = CrewOnOffs.size()-1;
		if( CrewOnOffs.get(lastCrew).getHBC_CrewOnOff_ID() != p_CrewOnOff_ID 
				&& CurrJobDataChange.get_ValueAsBoolean("isCancelled") != false){
			throw new AdempiereException("Error: Please Cancel the last CrewOnOff and JobDataChange not canceled");
		}
		
		MEmployee employee = new MEmployee(
				getCtx(), CurrCrewOnOff.getHC_Employee_ID(), get_TrxName());
		
		
		int hc_job_id = 0;
		if(CurrCrewOnOff.getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer))
			hc_job_id = CurrCrewOnOff.getHC_Job_ID();
		else
			hc_job_id = CurrCrewOnOff.getPreviousJob_ID();
		
		
		int employeeJobID = 0;
		try{
			String sql ="SELECT HC_EmployeeJob_ID FROM HC_EmployeeJob WHERE HC_Employee_ID = ? "
					+ "AND HC_Job_ID=? "
					+ "AND SeqNo=1 ";
			employeeJobID= DB.getSQLValue(get_TrxName(), sql, 
					CurrCrewOnOff.getHC_Employee_ID(), hc_job_id);
		}catch(Exception e){
			employeeJobID = 0;
		}
		
		List<MEmployeeJob> employeeJobs = new ArrayList<MEmployeeJob>();
		//employeeJobs = MEmployeeJob.GetAll(getCtx(), employee, get_TrxName());
		
		/*first sequence EmployeeJob must exist*/
		if(employeeJobID <= 0)
			throw new AdempiereException("Error: First sequence Employee Job related to Employee does not exist");
		
		MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), employeeJobID, get_TrxName());//first sequence job
		
		if(CrewOnOffs.size() > 1){
			
			int PrevCrewOnOff_ID = CrewOnOffs.get(lastCrew-1).getHBC_CrewOnOff_ID();
			MCrewOnOff PrevCrewOnOff = new MCrewOnOff(getCtx(),PrevCrewOnOff_ID,get_TrxName()); 
			MJobDataChange PrevJobDataChange = new MJobDataChange(
					getCtx(), PrevCrewOnOff.get_ValueAsInt("HC_JobDataChange_ID"),get_TrxName());
			
			employee.setHC_Status(PrevJobDataChange.getHC_Status());
			employee.setHC_Org_ID(CurrJobDataChange.get_ValueAsInt("HC_Org_ID"));
			employee.saveEx();
			
			employeeJob.setHC_Status(PrevJobDataChange.getHC_Status());
			employeeJob.setHC_Org_ID(CurrJobDataChange.get_ValueAsInt("HC_Org_ID"));
			if(CurrJobDataChange.get_ValueAsInt("PreviousHC_Manager_ID") > 0)
				employeeJob.setHC_Manager_ID(CurrJobDataChange.get_ValueAsInt("PreviousHC_Manager_ID"));
			if(CurrJobDataChange.get_ValueAsInt("PreviousHC_ManagerPosition_ID") > 0)
				employeeJob.setHC_ManagerPosition_ID(CurrJobDataChange.get_ValueAsInt("PreviousHC_ManagerPosition_ID"));
			if(PrevJobDataChange.getHC_Reason_ID()> 0)
				employeeJob.setHC_Reason_ID(PrevJobDataChange.getHC_Reason_ID());
			employeeJob.setHC_Compensation1((BigDecimal)CurrJobDataChange.get_Value("PreviousHC_Compensation1"));
			employeeJob.setHC_Compensation2((BigDecimal)CurrJobDataChange.get_Value("PreviousHC_Compensation2"));
			employeeJob.setHC_Job_ID(CurrJobDataChange.get_ValueAsInt("PreviousJob_ID"));
			employeeJob.setHC_JobAction(PrevJobDataChange.getHC_JobAction());
			employeeJob.saveEx();
			if(employeeJobs.size() > 0){
				for(int i = 0; i < employeeJobs.size(); i++){
					if(employeeJobs.get(i).getHC_Job_ID()!= employeeJob.getHC_Job_ID()){
						employeeJobs.get(i).setHC_Status(PrevJobDataChange.getHC_Status());
						employeeJobs.get(i).setHC_Org_ID(CurrJobDataChange.get_ValueAsInt("HC_Org_ID"));
						employeeJobs.get(i).setHC_JobAction(PrevJobDataChange.getHC_JobAction());
					}
				}
			}//if
		}else{
			/*The Last Crew On Off */
			employee.setHC_Status(MEmployee.HC_STATUS_Active);
			employee.setHC_Org_ID(CurrJobDataChange.get_ValueAsInt("HC_Org_ID"));
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			employeeJob.setHC_Org_ID(CurrJobDataChange.get_ValueAsInt("HC_Org_ID"));
			employeeJob.set_ValueOfColumn("HC_reason_ID", null);
			if(CurrJobDataChange.get_ValueAsInt("PreviousHC_Manager_ID") > 0)
				employeeJob.setHC_Manager_ID(CurrJobDataChange.get_ValueAsInt("PreviousHC_Manager_ID"));
			if(CurrJobDataChange.get_ValueAsInt("PreviousHC_ManagerPosition_ID") > 0)
				employeeJob.setHC_ManagerPosition_ID(CurrJobDataChange.get_ValueAsInt("PreviousHC_ManagerPosition_ID"));
			employeeJob.set_ValueOfColumn("HC_Reason_ID", null);
			employeeJob.setHC_JobAction(MEmployeeJob.HC_JOBACTION_Hire);
			employeeJob.setHC_Status(MEmployeeJob.HC_STATUS_Active);
			employeeJob.setHC_Job_ID(CurrJobDataChange.get_ValueAsInt("PreviousJob_ID"));
			employeeJob.setHC_Compensation1((BigDecimal)CurrJobDataChange.get_Value("PreviousHC_Compensation1"));
			employeeJob.setHC_Compensation2((BigDecimal)CurrJobDataChange.get_Value("PreviousHC_Compensation2"));
			employee.saveEx();
			employeeJob.saveEx();
			if(employeeJobs.size() > 0)
				for(int i = 0; i < employeeJobs.size(); i++){
					if(employeeJobs.get(i).getHC_Job_ID()!= employeeJob.getHC_Job_ID()){
						employeeJobs.get(i).setHC_Status(MEmployeeJob.HC_STATUS_Active);
						employeeJobs.get(i).setHC_Org_ID(CurrJobDataChange.get_ValueAsInt("HC_Org_ID"));
						employeeJobs.get(i).setHC_JobAction(MEmployeeJob.HC_JOBACTION_Hire);
					}
				}
		}//else
		
		/*Job Data Change canceled*/
		CurrJobDataChange.set_ValueOfColumn("IsCancelled", "Y");
		CurrJobDataChange.saveEx();
		//@phie
		CurrCrewOnOff.set_ValueOfColumn("IsCancelled", "Y");
		CurrCrewOnOff.saveEx();
		//end phie
		return "Success Cancelling Crew On Off";
	}//doIt
}// endClass

