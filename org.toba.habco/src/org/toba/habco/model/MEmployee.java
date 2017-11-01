package org.toba.habco.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

public class MEmployee extends X_HC_Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = -634814915006843410L;

	public MEmployee(Properties ctx, int HC_Employee_ID, String trxName) {
		super(ctx, HC_Employee_ID, trxName);
	}

	public MEmployee(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	protected boolean beforeSave (boolean newRecord)
	{
		/*
		//@TommyAng Temporary Validation Requested by Bella
		int matchID= new Query(getCtx(),MEmployee.Table_Name,"Name='"+getName()+"' and Birthday='"+getBirthday()+"'",get_TrxName())
		.setClient_ID()
		.firstId();
		*/
		
		//@phie fix query set parameter (sql injection, name contain apostrophe
		int matchID= new Query(getCtx(),MEmployee.Table_Name,"Name=? and Birthday=?",get_TrxName())
		.setClient_ID()
		.setParameters(new Object[]{getName(), getBirthday()})
		.firstId();
		//end phie
		
		if(matchID > 0 && matchID!=get_ID()){
			MEmployee emp = new MEmployee(getCtx(), matchID, get_TrxName());
			throw new AdempiereException("There's an employee that have the same name and birthday ="+emp.getValue());
		}
		
		matchID= new Query(getCtx(),MEmployee.Table_Name,"HC_NationalID1='"+getHC_NationalID1()+"'",get_TrxName())
		.setClient_ID()
		.firstId();
		
		if(matchID > 0 && matchID!=get_ID()){
			MEmployee emp = new MEmployee(getCtx(), matchID, get_TrxName());
			throw new AdempiereException("There's an employee that have the same National ID ="+emp.getValue());
		}
		
		matchID= new Query(getCtx(),MEmployee.Table_Name,"Value='"+getValue()+"'",get_TrxName())
		.setClient_ID()
		.firstId();
		
		if(matchID > 0 && matchID!=get_ID()){
			MEmployee emp = new MEmployee(getCtx(), matchID, get_TrxName());
			throw new AdempiereException("There's an employee that have the same Search Key ="+emp.getValue());
		}
		
		//end @TommyAng
		return true;
	}

	public void refreshFromDataChange()  {
		final String whereClause = "EffectiveDateFrom = (SELECT MAX(EffectiveDateFrom) "
				+ "FROM HC_EmployeeDataChange WHERE EffectiveDateFrom <?) AND HC_Employee_ID=?";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(System.currentTimeMillis()));
		cal.add(Calendar.DATE, 1);
		Timestamp currentDate = null;
		currentDate.setTime(cal.getTime().getTime());
		
		MEmployeeDataChange eDataChange = new Query(getCtx(), MEmployeeDataChange.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.setParameters(new Object[]{currentDate, getHC_Employee_ID()})
		.setOrderBy(COLUMNNAME_EffectiveDateFrom + " DESC")
		.first();
		
		MEmployee employee = this;
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_NameChange)) {
			employee.setName(eDataChange.getName());
			employee.setName2(eDataChange.getName2());		
		}
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_ContactChange)) {
			employee.setPhone(eDataChange.getPhone());
			employee.setPhone2(eDataChange.getPhone2());
			employee.setPhoneExt1(eDataChange.getPhoneExt1());
			employee.setPhoneExt2(eDataChange.getPhoneExt2());		
			employee.setEMail(eDataChange.getEMail());
			employee.setEmail2(eDataChange.getEmail2());	
		}
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_IDChange)) {
			employee.setHC_NationalID1(eDataChange.getHC_NationalID1());
			employee.setHC_NationalID2(eDataChange.getHC_NationalID2());
			employee.setHC_NationalID3(eDataChange.getHC_NationalID3());
			employee.setHC_NationalID4(eDataChange.getHC_NationalID4());
			employee.setHC_NationalID5(eDataChange.getHC_NationalID5());
			employee.setHC_ID1_ExpDate(eDataChange.getHC_ID1_ExpDate());
			employee.setHC_ID2_ExpDate(eDataChange.getHC_ID2_ExpDate());
			employee.setHC_ID3_ExpDate(eDataChange.getHC_ID3_ExpDate());
			employee.setHC_ID4_ExpDate(eDataChange.getHC_ID4_ExpDate());
			employee.setHC_ID5_ExpDate(eDataChange.getHC_ID5_ExpDate());
			
		}
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_AlternateIDChange)) {
			employee.setHC_AltID1(eDataChange.getHC_AltID1());
			employee.setHC_AltID2(eDataChange.getHC_AltID2());	
		}
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_TaxStatusChange)) {
			employee.setHC_TaxStatus_ID(eDataChange.getHC_TaxStatus_ID());
			employee.setC_TaxOffice_ID(eDataChange.getC_TaxOffice_ID());
		}
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_MaritalStatusChange)) {
			employee.set_CustomColumn("HC_MaritalStatus", eDataChange.getHC_MaritalStatus());
			employee.set_CustomColumn("HC_MaritalDate", eDataChange.getHC_MaritalDate());
		}
		
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New) ||
			eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_PersonalDataChange)) {
			employee.setBirthday(eDataChange.getBirthday());
			employee.setHC_BirthCountry_ID(eDataChange.getHC_BirthCountry_ID());
			employee.setHC_BirthRegion_ID(eDataChange.getHC_BirthRegion_ID());
			employee.setHC_BloodType(eDataChange.getHC_BloodType());
			employee.setHC_Ethnic_ID(eDataChange.getHC_Ethnic_ID());
			employee.setHC_Gender(eDataChange.getHC_Gender());
			employee.setHC_Religion_ID(eDataChange.getHC_Religion_ID());
		}
		if (eDataChange.getHC_PersonalDataAction()
				.equals(MEmployeeDataChange.HC_PERSONALDATAACTION_New)){
			employee.setValue(eDataChange.getValue());
			employee.setC_BPartner_ID(eDataChange.getC_BPartner_ID());
		}
			employee.setDescription(eDataChange.getDescription());
			employee.setEffectiveDateFrom(eDataChange.getEffectiveDateFrom());
			employee.setHC_PersonalDataAction(eDataChange.getHC_PersonalDataAction());
			employee.saveEx();
	}
	
}
