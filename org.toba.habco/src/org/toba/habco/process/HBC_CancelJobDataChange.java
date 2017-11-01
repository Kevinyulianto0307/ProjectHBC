package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
 * 
 * @author KevinY
 * Process for HC_JobDataChange 
 * PSD - #2843
 */
public class HBC_CancelJobDataChange extends SvrProcess{

	private int HC_JobDataChange_ID = 0;
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
		HC_JobDataChange_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		if(HC_JobDataChange_ID <= 0)
			throw new AdempiereException("Error: Job Data Change is not selected");
		
		MJobDataChange jobDataChange  = new MJobDataChange(getCtx(), HC_JobDataChange_ID, get_TrxName());
		MEmployee employee = new MEmployee(getCtx(), jobDataChange.getHC_Employee_ID(), get_TrxName());
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int HC_CrewOnOff_ID = 0;
		String sql = "SELECT HBC_CrewOnOff_ID FROM HBC_CrewOnOff WHERE "
				+ "HC_JobDataChange_ID = ? AND Processed = 'Y'";
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, HC_JobDataChange_ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HC_CrewOnOff_ID = rs.getInt(1);
			}
		} catch (SQLException e){
			log.log(Level.SEVERE, sql.toString(), e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		if(HC_CrewOnOff_ID > 0){
			throw new AdempiereException("Error: Cancel from Crew On Off due Job Data Change is still related to Crew on off");
		}
		
		int HC_EmployeeJob_ID = jobDataChange.get_ValueAsInt("HC_EmployeeJob_ID");
		
		if(HC_EmployeeJob_ID <= 0) 	
			throw new AdempiereException("Error: Employee Job Data is not exists");
		
		
		pstmt = null;
		rs = null;
		List<MJobDataChange> jobDataChanges = new ArrayList<MJobDataChange>();
		sql = "SELECT HC_JobDataChange_ID FROM HC_JobDataChange WHERE HC_Employee_ID = ? AND HC_EmployeeJob_ID = ?"
				+ " AND isCancelled='N' AND Processed ='Y' ORDER BY Updated ASC";
		
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, employee.getHC_Employee_ID());
			pstmt.setInt(2, jobDataChange.get_ValueAsInt("HC_EmployeeJob_ID"));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MJobDataChange dataChange = new MJobDataChange(getCtx(), rs.getInt("HC_JobDataChange_ID"), get_TrxName());
				jobDataChanges.add(dataChange);
			}
		} catch (SQLException e){
			log.log(Level.SEVERE, sql.toString(), e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		if(jobDataChanges.size() <= 0)
			throw new AdempiereException("Error: Employee Job Data Change for employee " + employee.getName() +" not exist");
		
		if(jobDataChanges.get(jobDataChanges.size()-1).get_ID() == jobDataChange.get_ID()){
			jobDataChange.set_ValueOfColumn("isCancelled", true);
			jobDataChange.saveEx();
			MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), HC_EmployeeJob_ID, get_TrxName());
			employeeJob.setHC_Job_ID(jobDataChange.get_ValueAsInt("PreviousJob_ID"));
			employeeJob.setHC_Org_ID(jobDataChange.getHC_Org_ID());
			employeeJob.setHC_Manager_ID(jobDataChange.get_ValueAsInt("PreviousHC_Manager_ID"));
			employeeJob.setHC_Compensation1((BigDecimal)jobDataChange.get_Value("PreviousHC_Compensation1"));
			employeeJob.setHC_Compensation2((BigDecimal)jobDataChange.get_Value("PreviousHC_Compensation2"));
			employeeJob.saveEx();
			int previousJob = jobDataChanges.size()-2;
			if(jobDataChanges.size() >= 2){
				if(matchRecord(jobDataChanges.get(previousJob),employeeJob) == true){
					employeeJob.setHC_PayGroup_ID(jobDataChanges.get(previousJob).getHC_PayGroup_ID());
					employeeJob.setC_Location_ID(jobDataChanges.get(previousJob).getC_Location_ID());
					employeeJob.set_ValueOfColumn("HC_WorkStartDate", (Timestamp)jobDataChanges.get(previousJob).get_Value("HC_WorkStartDate"));
					employeeJob.set_ValueOfColumn("HC_WorkEndDate", (Timestamp)jobDataChanges.get(previousJob).get_Value("HC_WorkEndDate"));
					employeeJob.set_ValueOfColumn("HC_JobAction", jobDataChanges.get(previousJob).get_Value("HC_JobAction"));
					employeeJob.set_ValueOfColumn("HC_Status", jobDataChanges.get(previousJob).get_Value("HC_Status"));
					employee.set_ValueOfColumn("HC_Status", jobDataChanges.get(previousJob).get_Value("HC_Status"));
				}
			}else if(jobDataChanges.size() == 1){
					employeeJob.set_ValueOfColumn("HC_PayGroup_ID"	, jobDataChange.getHC_PayGroup_ID());
					employeeJob.set_ValueOfColumn("C_Location_ID"	, null);
					employeeJob.set_ValueOfColumn("HC_WorkStartDate", null);
					employeeJob.set_ValueOfColumn("HC_WorkEndDate"	, null);
					employeeJob.set_ValueOfColumn("HC_Status"		, employeeJob.HC_STATUS_Active);
					employeeJob.set_ValueOfColumn("HC_JobAction"	, employeeJob.HC_JOBACTION_Hire);
					employee.set_ValueOfColumn("HC_Status"			, employeeJob.HC_STATUS_Active);
			}
			employee.saveEx();
			employeeJob.saveEx();
		}else{
			throw new AdempiereException("Error: Employee job Data Change is not last Employee Job Data Change");
		}
		return "Success Cancel Job Data Change";
	}//doIt
	
	
	/**
	 * check if previous Job data change match with employee Job
	 * @param jobDataChange
	 * @param employeeJob
	 * @return
	 * false or true 
	 */
	private boolean matchRecord(MJobDataChange prevJobDataChange ,MEmployeeJob employeeJob){
				if(prevJobDataChange.getHC_Job_ID() != employeeJob.getHC_Job_ID())
					return false;

				if(prevJobDataChange.getHC_Compensation1().compareTo(employeeJob.getHC_Compensation1()) != 0)
					return false;
				
				if(prevJobDataChange.getHC_Compensation2().compareTo(employeeJob.getHC_Compensation2()) != 0 )
					return false;
				
				if(prevJobDataChange.getHC_Employee_ID() != employeeJob.getHC_Employee_ID())
					return false;
				
				if(prevJobDataChange.get_ValueAsInt("HC_OrgTo_ID") != employeeJob.getHC_Org_ID())
					return false;
				
				return true;
	}//matchRecord
	
}//endClass
