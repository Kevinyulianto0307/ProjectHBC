package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;


public class MCrewOnOff extends X_HBC_CrewOnOff {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8808949829114591604L;

	/**	Process Message 			*/
	protected String		m_processMsg = null;


	public MCrewOnOff(Properties ctx, int HBC_CrewOnOff_ID, String trxName) {
		super(ctx, HBC_CrewOnOff_ID, trxName);
	}

	public MCrewOnOff(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		//@KevinY HBC - 
		/*if (newRecord || is_ValueChanged(COLUMNNAME_HBC_CrewOnOffType)) {
			MEmployee employee = new MEmployee(getCtx(), getHC_Employee_ID(), get_TrxName());

			if ((getHBC_CrewOnOffType().equals(X_HBC_CrewOnOff.HBC_CREWONOFFTYPE_OnLeave))
					|| (getHBC_CrewOnOffType().equals(X_HBC_CrewOnOff.HBC_CREWONOFFTYPE_OnDuty))) {

				if(employee.getHC_Status().equals(MEmployee.HC_STATUS_Terminate)){
					m_processMsg = "Employee Status is Terminate";
					return false;
				} 	
			}
		}*/
		if (newRecord) {
			//@KevinY HBC - 2395
			
			String sql = "SELECT HC_EmployeeJob_ID FROM HC_EmployeeJob WHERE HC_Employee_ID=? AND HC_Job_ID=?";
			int employeeJobID = DB.getSQLValue(get_TrxName(), sql, getHC_Employee_ID(), getPreviousJob_ID());
			MEmployeeJob employeeJob = new MEmployeeJob(getCtx(), employeeJobID, get_TrxName());
			
			String whereClause = " HBC_CrewOnOff.HC_Employee_ID = ? "
					+ "AND HBC_CrewOnOff.isActive='Y' "
					+ "AND HBC_CrewOnOff.Processed='Y' AND HBC_CrewOnoff.isCancelled='N' "
					+ "AND (jdc.isCancelled ='N' OR jdc.isCancelled IS NULL) ";
			
			List<MCrewOnOff> CrewOnOffs = new ArrayList<MCrewOnOff>();
			CrewOnOffs = new Query(getCtx(), MCrewOnOff.Table_Name, whereClause, get_TrxName())
							.setParameters(getHC_Employee_ID())
							.addJoinClause(" JOIN HC_JobDataChange jdc ON HBC_CrewOnOff.HC_JobDataChange_ID = jdc.HC_JobDataChange_ID ")
							.setOrderBy("HBC_CrewOnOff.Updated ASC")
							.list();
			
			MJobDataChange jDataChange = null;

			jDataChange = new MJobDataChange(getCtx(), 0, get_TrxName());
			jDataChange.setHC_Employee_ID(getHC_Employee_ID());
			jDataChange.set_ValueOfColumn("HC_EmployeeJob_ID", employeeJob.getHC_EmployeeJob_ID());
			
			int flag = 0;
			if(getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer)){
				jDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
				jDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Transfer);
				//jDataChange.setHC_Org_ID(getHC_OrgTo_ID());
			}
			else if(getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)){
				if(CrewOnOffs.size() >0){
					if(CrewOnOffs.get(CrewOnOffs.size()-1).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)){
						throw new AdempiereException("Error: Can't Terminate Terminated Employee");
					}
				}
				jDataChange.setHC_Status(MJobDataChange.HC_STATUS_Terminate);
				jDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Terminate);
				//jDataChange.setHC_Org_ID(getHC_Org_ID());
			}else if(getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding)){
				if(CrewOnOffs.size() > 0){
					flag = 0;
					for(int i = 0; i < CrewOnOffs.size() ; i++ ){
						if(CrewOnOffs.get(i).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding)){
							flag++;
						}
					}//for
					if(flag !=0){
						/*if crew on off ever on boarding*/
						if(CrewOnOffs.get(CrewOnOffs.size() - 1 ).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Onboarding) ||
								CrewOnOffs.get(CrewOnOffs.size() - 1 ).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer))
							throw new AdempiereException("Error: Can't Onboard Employee already OnBoarding ");
					}else{
						/*if crew on off never on boarding before*/
						if(CrewOnOffs.get(CrewOnOffs.size()-1).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Transfer)){
							jDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
							jDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Hire);
						}
					}//else
					
					/*if employee data is terminate then rehire*/
					if(CrewOnOffs.get(CrewOnOffs.size()-1).getHBC_CrewOnOffType().equals(MCrewOnOff.HBC_CREWONOFFTYPE_Offboarding)){
							jDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
							jDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Rehire);
					}
				}else{
					jDataChange.setHC_Status(MJobDataChange.HC_STATUS_Active);
					jDataChange.setHC_JobAction(MJobDataChange.HC_JOBACTION_Hire);
				}
				//jDataChange.setHC_Org_ID(getHC_Org_ID());
			}
			
			jDataChange.set_ValueOfColumn("HC_Org_ID", getHC_Org_ID());
			jDataChange.set_ValueOfColumn("HC_OrgTo_ID", getHC_OrgTo_ID());
			jDataChange.set_ValueOfColumn("PreviousJob_ID", getPreviousJob_ID());
			jDataChange.setHC_Job_ID(getHC_Job_ID());//Job To
			jDataChange.set_ValueOfColumn("PreviousHC_Compensation1", get_Value("PreviousHC_Compensation1"));
			jDataChange.set_ValueOfColumn("PreviousHC_Compensation2", get_Value("PreviousHC_Compensation2"));
			jDataChange.setHC_Compensation1(getHC_Compensation1());
			jDataChange.setHC_Compensation2(getHC_Compensation2());
			jDataChange.setEffectiveDateFrom(getDateTrx());
			jDataChange.setHC_PayGroup_ID(employeeJob.getHC_PayGroup_ID());
			jDataChange.set_ValueOfColumn("SeqNo", employeeJob.get_Value("SeqNo"));
			if(getHC_Reason_ID() > 0)
				jDataChange.setHC_Reason_ID(getHC_Reason_ID());
			if(getHC_Manager_ID() > 0)
				jDataChange.setHC_Manager_ID(getHC_Manager_ID());
			if(getHC_ManagerPosition_ID() > 0)
				jDataChange.setHC_ManagerPosition_ID(getHC_ManagerPosition_ID());
			if(get_ValueAsInt("PreviousHC_Manager_ID") > 0)
				jDataChange.set_ValueOfColumn("PreviousHC_Manager_ID", get_ValueAsInt("PreviousHC_Manager_ID"));
			if(get_ValueAsInt("PreviousHC_ManagerLocation_ID") > 0)
				jDataChange.set_ValueOfColumn("PreviousHC_ManagerLocation_ID", get_ValueAsInt("PreviousHC_ManagerLocation_ID"));
			jDataChange.saveEx();
			
			set_ValueOfColumn("HC_JobDataChange_ID", jDataChange.getHC_JobDataChange_ID());

		//@KevinY end
		}
		
		return true;
	}
}
