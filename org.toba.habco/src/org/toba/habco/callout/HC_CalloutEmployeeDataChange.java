package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.util.Env;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeDataChange;
import org.toba.habco.model.X_HBC_Contract;


public class HC_CalloutEmployeeDataChange extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals("HC_Employee_ID")){
			return Employee(ctx, WindowNo, mTab, mField, value);

		}else if(mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_C_BPartner_ID)){
			return BPartner(ctx, WindowNo, mTab, mField, value);
		
		}
				
		return "";
	}
	
	private String BPartner(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		int bpartnerID= (Integer)value;
		if (bpartnerID > 0) {
			MBPartner partner = new MBPartner(Env.getCtx(), bpartnerID, null);
			mTab.setValue(MEmployee.COLUMNNAME_Value, partner.getValue());
			mTab.setValue(MEmployee.COLUMNNAME_Name, partner.getName());
			mTab.setValue(MEmployee.COLUMNNAME_Name2, partner.getName2());
			
		}
		
		return msg;
	}

	private String Employee(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		int employeeID= (Integer)value;
		
		/*
		int employeeID = new Query(Env.getCtx(),MEmployee.Table_Name,"HC_Employee_ID=?",null)
							.setParameters(BPartnerID)
							.setOnlyActiveRecords(true)
							.firstId();
	
		*/
		
		if (employeeID > 0) {
			MEmployee employee = new MEmployee(Env.getCtx(), employeeID, null);
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_AD_Org_ID, employee.getAD_Org_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Birthday, employee.getBirthday());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_C_BPartner_ID, employee.getC_BPartner_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_C_TaxOffice_ID, employee.getC_TaxOffice_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Description, employee.getDescription());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_EMail, employee.getEMail());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Email2, employee.getEmail2());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_AltID1, employee.getHC_AltID1());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_AltID2, employee.getHC_AltID2());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_BirthCountry_ID, employee.getHC_BirthCountry_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_BirthRegion_ID, employee.getHC_BirthRegion_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_BloodType, employee.getHC_BloodType());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_Ethnic_ID, employee.getHC_Ethnic_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_Gender, employee.getHC_Gender());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_ID1_ExpDate, employee.getHC_ID1_ExpDate());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_ID2_ExpDate, employee.getHC_ID2_ExpDate());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_ID3_ExpDate, employee.getHC_ID3_ExpDate());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_ID4_ExpDate, employee.getHC_ID4_ExpDate());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_ID5_ExpDate, employee.getHC_ID5_ExpDate());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_MaritalDate, employee.get_Value("HC_MaritalDate"));
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_MaritalStatus, employee.get_Value("HC_MaritalStatus"));
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_NationalID1, employee.getHC_NationalID1());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_NationalID2, employee.getHC_NationalID2());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_NationalID3, employee.getHC_NationalID3());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_NationalID4, employee.getHC_NationalID4());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_NationalID5, employee.getHC_NationalID5());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_Religion_ID, employee.getHC_Religion_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_TaxStatus_ID, employee.getHC_TaxStatus_ID());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Name, employee.getName());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Name2, employee.getName2());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Phone, employee.getPhone());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Phone2, employee.getPhone2());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_PhoneExt1, employee.getPhoneExt1());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_PhoneExt2, employee.getPhoneExt2());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_Value, employee.getValue());		
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_City, employee.getCity());
			mTab.setValue(MEmployeeDataChange.COLUMNNAME_HC_WorkStartDate, employee.getHC_WorkStartDate());
		}
		return "";
	}
	//end of HABCO-1588

}
