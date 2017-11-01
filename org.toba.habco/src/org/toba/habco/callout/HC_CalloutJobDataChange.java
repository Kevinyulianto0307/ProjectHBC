package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.toba.habco.model.MEmployeeJob;
import org.toba.habco.model.MJobDataChange;


public class HC_CalloutJobDataChange extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MJobDataChange.COLUMNNAME_HC_Employee_ID)){
			return jobData(ctx, WindowNo, mTab, mField, value);
		}

		//end habco-1665 callout paymentterm by edwin handy
				
		return "";
	}
	
	private String jobData(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		int employeeID= (Integer)value;
		
		int jobDataID = new Query(Env.getCtx(),MEmployeeJob.Table_Name,"HC_Employee_ID=?",null)
			.setParameters(employeeID)
			.setOnlyActiveRecords(true)
			.firstId();
		
		if (jobDataID > 0) {
			MEmployeeJob employeeJob = new MEmployeeJob(Env.getCtx(), jobDataID, null);
			mTab.setValue(MJobDataChange.COLUMNNAME_AD_Org_ID, employeeJob.getAD_Org_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_C_Location_ID, employeeJob.getC_Location_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_Description, employeeJob.getDescription());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Compensation3, employeeJob.getHC_Compensation3());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Compensation4, employeeJob.getHC_Compensation4());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Employee_ID, employeeJob.getHC_Employee_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_EmployeeCategory_ID, employeeJob.getHC_EmployeeCategory_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_EmployeeClass_ID, employeeJob.getHC_EmployeeClass_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_EmployeeGrade_ID, employeeJob.getHC_EmployeeGrade_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_JobAction, employeeJob.getHC_JobAction());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_PayGroup_ID, employeeJob.getHC_PayGroup_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Position_ID, employeeJob.getHC_Position_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Reason_ID, employeeJob.getHC_Reason_ID());
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Status, employeeJob.getHC_Status());
			
			//@phie
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Org_ID, employeeJob.getHC_Org_ID());
			mTab.setValue("PreviousJob_ID", employeeJob.getHC_Job_ID());
			mTab.setValue("PreviousHC_Compensation1", employeeJob.getHC_Compensation1());
			mTab.setValue("PreviousHC_Compensation2", employeeJob.getHC_Compensation2());
			if(employeeJob.getHC_Manager_ID() == 0 || employeeJob.get_Value("HC_Manager_ID")==null)
				mTab.setValue("PreviousHC_Manager_ID", null);
			else
				mTab.setValue("PreviousHC_Manager_ID", employeeJob.getHC_Manager_ID());
			mTab.setValue("PreviousHC_ManagerPosition_ID", employeeJob.getHC_ManagerPosition_ID());
			
			mTab.setValue("HC_OrgTo_ID", null);
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Job_ID, null);
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Compensation1, null);
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Compensation2, null);
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_Manager_ID, null);
			mTab.setValue(MJobDataChange.COLUMNNAME_HC_ManagerPosition_ID, null);
			//end phie
		}
		return "";
	}
	//end of HABCO-1588

}
