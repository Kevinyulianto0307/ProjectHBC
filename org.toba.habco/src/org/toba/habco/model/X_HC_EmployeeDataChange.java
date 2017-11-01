/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.toba.habco.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for HC_EmployeeDataChange
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_EmployeeDataChange extends PO implements I_HC_EmployeeDataChange, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170118L;

    /** Standard Constructor */
    public X_HC_EmployeeDataChange (Properties ctx, int HC_EmployeeDataChange_ID, String trxName)
    {
      super (ctx, HC_EmployeeDataChange_ID, trxName);
      /** if (HC_EmployeeDataChange_ID == 0)
        {
			setHC_EmployeeDataChange_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_EmployeeDataChange (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_HC_EmployeeDataChange[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Birthday.
		@param Birthday 
		Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_TaxOffice getC_TaxOffice() throws RuntimeException
    {
		return (I_C_TaxOffice)MTable.get(getCtx(), I_C_TaxOffice.Table_Name)
			.getPO(getC_TaxOffice_ID(), get_TrxName());	}

	/** Set Tax Office.
		@param C_TaxOffice_ID Tax Office	  */
	public void setC_TaxOffice_ID (int C_TaxOffice_ID)
	{
		if (C_TaxOffice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxOffice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxOffice_ID, Integer.valueOf(C_TaxOffice_ID));
	}

	/** Get Tax Office.
		@return Tax Office	  */
	public int getC_TaxOffice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxOffice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set City.
		@param City 
		Identifies a City
	  */
	public void setCity (String City)
	{
		set_ValueNoCheck (COLUMNNAME_City, City);
	}

	/** Get City.
		@return Identifies a City
	  */
	public String getCity () 
	{
		return (String)get_Value(COLUMNNAME_City);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Effective Date From.
		@param EffectiveDateFrom Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom)
	{
		set_Value (COLUMNNAME_EffectiveDateFrom, EffectiveDateFrom);
	}

	/** Get Effective Date From.
		@return Effective Date From	  */
	public Timestamp getEffectiveDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EffectiveDateFrom);
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Email Address 2.
		@param Email2 Email Address 2	  */
	public void setEmail2 (String Email2)
	{
		set_Value (COLUMNNAME_Email2, Email2);
	}

	/** Get Email Address 2.
		@return Email Address 2	  */
	public String getEmail2 () 
	{
		return (String)get_Value(COLUMNNAME_Email2);
	}

	/** Set Kartu Keluarga.
		@param HC_AltID1 Kartu Keluarga	  */
	public void setHC_AltID1 (String HC_AltID1)
	{
		set_Value (COLUMNNAME_HC_AltID1, HC_AltID1);
	}

	/** Get Kartu Keluarga.
		@return Kartu Keluarga	  */
	public String getHC_AltID1 () 
	{
		return (String)get_Value(COLUMNNAME_HC_AltID1);
	}

	/** Set Alternate ID 2.
		@param HC_AltID2 Alternate ID 2	  */
	public void setHC_AltID2 (String HC_AltID2)
	{
		set_Value (COLUMNNAME_HC_AltID2, HC_AltID2);
	}

	/** Get Alternate ID 2.
		@return Alternate ID 2	  */
	public String getHC_AltID2 () 
	{
		return (String)get_Value(COLUMNNAME_HC_AltID2);
	}

	/** Set Birth Country.
		@param HC_BirthCountry_ID Birth Country	  */
	public void setHC_BirthCountry_ID (int HC_BirthCountry_ID)
	{
		if (HC_BirthCountry_ID < 1) 
			set_Value (COLUMNNAME_HC_BirthCountry_ID, null);
		else 
			set_Value (COLUMNNAME_HC_BirthCountry_ID, Integer.valueOf(HC_BirthCountry_ID));
	}

	/** Get Birth Country.
		@return Birth Country	  */
	public int getHC_BirthCountry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_BirthCountry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Region getHC_BirthRegion() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getHC_BirthRegion_ID(), get_TrxName());	}

	/** Set Birth Province.
		@param HC_BirthRegion_ID Birth Province	  */
	public void setHC_BirthRegion_ID (int HC_BirthRegion_ID)
	{
		if (HC_BirthRegion_ID < 1) 
			set_Value (COLUMNNAME_HC_BirthRegion_ID, null);
		else 
			set_Value (COLUMNNAME_HC_BirthRegion_ID, Integer.valueOf(HC_BirthRegion_ID));
	}

	/** Get Birth Province.
		@return Birth Province	  */
	public int getHC_BirthRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_BirthRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** HC_BloodType AD_Reference_ID=300057 */
	public static final int HC_BLOODTYPE_AD_Reference_ID=300057;
	/** 0 = 0 */
	public static final String HC_BLOODTYPE_0 = "0";
	/** A = A */
	public static final String HC_BLOODTYPE_A = "A";
	/** B = B */
	public static final String HC_BLOODTYPE_B = "B";
	/** AB = AB */
	public static final String HC_BLOODTYPE_AB = "AB";
	/** Set Blood Type.
		@param HC_BloodType Blood Type	  */
	public void setHC_BloodType (String HC_BloodType)
	{

		set_Value (COLUMNNAME_HC_BloodType, HC_BloodType);
	}

	/** Get Blood Type.
		@return Blood Type	  */
	public String getHC_BloodType () 
	{
		return (String)get_Value(COLUMNNAME_HC_BloodType);
	}

	public I_HC_Employee getHC_Employee() throws RuntimeException
    {
		return (I_HC_Employee)MTable.get(getCtx(), I_HC_Employee.Table_Name)
			.getPO(getHC_Employee_ID(), get_TrxName());	}

	/** Set Employee Data.
		@param HC_Employee_ID Employee Data	  */
	public void setHC_Employee_ID (int HC_Employee_ID)
	{
		if (HC_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Employee_ID, Integer.valueOf(HC_Employee_ID));
	}

	/** Get Employee Data.
		@return Employee Data	  */
	public int getHC_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Employee Data Change.
		@param HC_EmployeeDataChange_ID Employee Data Change	  */
	public void setHC_EmployeeDataChange_ID (int HC_EmployeeDataChange_ID)
	{
		if (HC_EmployeeDataChange_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeDataChange_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeDataChange_ID, Integer.valueOf(HC_EmployeeDataChange_ID));
	}

	/** Get Employee Data Change.
		@return Employee Data Change	  */
	public int getHC_EmployeeDataChange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_EmployeeDataChange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HC_EmployeeDataChange_UU.
		@param HC_EmployeeDataChange_UU HC_EmployeeDataChange_UU	  */
	public void setHC_EmployeeDataChange_UU (String HC_EmployeeDataChange_UU)
	{
		set_Value (COLUMNNAME_HC_EmployeeDataChange_UU, HC_EmployeeDataChange_UU);
	}

	/** Get HC_EmployeeDataChange_UU.
		@return HC_EmployeeDataChange_UU	  */
	public String getHC_EmployeeDataChange_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_EmployeeDataChange_UU);
	}

	public I_HC_Ethnic getHC_Ethnic() throws RuntimeException
    {
		return (I_HC_Ethnic)MTable.get(getCtx(), I_HC_Ethnic.Table_Name)
			.getPO(getHC_Ethnic_ID(), get_TrxName());	}

	/** Set Ethnic.
		@param HC_Ethnic_ID Ethnic	  */
	public void setHC_Ethnic_ID (int HC_Ethnic_ID)
	{
		if (HC_Ethnic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Ethnic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Ethnic_ID, Integer.valueOf(HC_Ethnic_ID));
	}

	/** Get Ethnic.
		@return Ethnic	  */
	public int getHC_Ethnic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Ethnic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** HC_Gender AD_Reference_ID=300055 */
	public static final int HC_GENDER_AD_Reference_ID=300055;
	/** Male = M */
	public static final String HC_GENDER_Male = "M";
	/** Female = F */
	public static final String HC_GENDER_Female = "F";
	/** Set Gender.
		@param HC_Gender Gender	  */
	public void setHC_Gender (String HC_Gender)
	{

		set_Value (COLUMNNAME_HC_Gender, HC_Gender);
	}

	/** Get Gender.
		@return Gender	  */
	public String getHC_Gender () 
	{
		return (String)get_Value(COLUMNNAME_HC_Gender);
	}

	/** Set KTP Expiry Date.
		@param HC_ID1_ExpDate KTP Expiry Date	  */
	public void setHC_ID1_ExpDate (Timestamp HC_ID1_ExpDate)
	{
		set_Value (COLUMNNAME_HC_ID1_ExpDate, HC_ID1_ExpDate);
	}

	/** Get KTP Expiry Date.
		@return KTP Expiry Date	  */
	public Timestamp getHC_ID1_ExpDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_ID1_ExpDate);
	}

	/** Set Passport Expiry Date.
		@param HC_ID2_ExpDate Passport Expiry Date	  */
	public void setHC_ID2_ExpDate (Timestamp HC_ID2_ExpDate)
	{
		set_Value (COLUMNNAME_HC_ID2_ExpDate, HC_ID2_ExpDate);
	}

	/** Get Passport Expiry Date.
		@return Passport Expiry Date	  */
	public Timestamp getHC_ID2_ExpDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_ID2_ExpDate);
	}

	/** Set NPWP Expiry Date.
		@param HC_ID3_ExpDate NPWP Expiry Date	  */
	public void setHC_ID3_ExpDate (Timestamp HC_ID3_ExpDate)
	{
		set_Value (COLUMNNAME_HC_ID3_ExpDate, HC_ID3_ExpDate);
	}

	/** Get NPWP Expiry Date.
		@return NPWP Expiry Date	  */
	public Timestamp getHC_ID3_ExpDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_ID3_ExpDate);
	}

	/** Set BPJS TK Expiry Date.
		@param HC_ID4_ExpDate BPJS TK Expiry Date	  */
	public void setHC_ID4_ExpDate (Timestamp HC_ID4_ExpDate)
	{
		set_Value (COLUMNNAME_HC_ID4_ExpDate, HC_ID4_ExpDate);
	}

	/** Get BPJS TK Expiry Date.
		@return BPJS TK Expiry Date	  */
	public Timestamp getHC_ID4_ExpDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_ID4_ExpDate);
	}

	/** Set BPJS KS Expiry Date.
		@param HC_ID5_ExpDate BPJS KS Expiry Date	  */
	public void setHC_ID5_ExpDate (Timestamp HC_ID5_ExpDate)
	{
		set_Value (COLUMNNAME_HC_ID5_ExpDate, HC_ID5_ExpDate);
	}

	/** Get BPJS KS Expiry Date.
		@return BPJS KS Expiry Date	  */
	public Timestamp getHC_ID5_ExpDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_ID5_ExpDate);
	}

	/** Set Marital Date.
		@param HC_MaritalDate Marital Date	  */
	public void setHC_MaritalDate (Timestamp HC_MaritalDate)
	{
		set_Value (COLUMNNAME_HC_MaritalDate, HC_MaritalDate);
	}

	/** Get Marital Date.
		@return Marital Date	  */
	public Timestamp getHC_MaritalDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_MaritalDate);
	}

	/** HC_MaritalStatus AD_Reference_ID=300056 */
	public static final int HC_MARITALSTATUS_AD_Reference_ID=300056;
	/** Single = SGL */
	public static final String HC_MARITALSTATUS_Single = "SGL";
	/** Married = MAR */
	public static final String HC_MARITALSTATUS_Married = "MAR";
	/** Divorced = DVC */
	public static final String HC_MARITALSTATUS_Divorced = "DVC";
	/** Set Marital Status.
		@param HC_MaritalStatus Marital Status	  */
	public void setHC_MaritalStatus (String HC_MaritalStatus)
	{

		set_Value (COLUMNNAME_HC_MaritalStatus, HC_MaritalStatus);
	}

	/** Get Marital Status.
		@return Marital Status	  */
	public String getHC_MaritalStatus () 
	{
		return (String)get_Value(COLUMNNAME_HC_MaritalStatus);
	}

	/** Set KTP.
		@param HC_NationalID1 KTP	  */
	public void setHC_NationalID1 (String HC_NationalID1)
	{
		set_Value (COLUMNNAME_HC_NationalID1, HC_NationalID1);
	}

	/** Get KTP.
		@return KTP	  */
	public String getHC_NationalID1 () 
	{
		return (String)get_Value(COLUMNNAME_HC_NationalID1);
	}

	/** Set Passport.
		@param HC_NationalID2 Passport	  */
	public void setHC_NationalID2 (String HC_NationalID2)
	{
		set_Value (COLUMNNAME_HC_NationalID2, HC_NationalID2);
	}

	/** Get Passport.
		@return Passport	  */
	public String getHC_NationalID2 () 
	{
		return (String)get_Value(COLUMNNAME_HC_NationalID2);
	}

	/** Set NPWP.
		@param HC_NationalID3 NPWP	  */
	public void setHC_NationalID3 (String HC_NationalID3)
	{
		set_Value (COLUMNNAME_HC_NationalID3, HC_NationalID3);
	}

	/** Get NPWP.
		@return NPWP	  */
	public String getHC_NationalID3 () 
	{
		return (String)get_Value(COLUMNNAME_HC_NationalID3);
	}

	/** Set BPJS.
		@param HC_NationalID4 BPJS	  */
	public void setHC_NationalID4 (String HC_NationalID4)
	{
		set_Value (COLUMNNAME_HC_NationalID4, HC_NationalID4);
	}

	/** Get BPJS.
		@return BPJS	  */
	public String getHC_NationalID4 () 
	{
		return (String)get_Value(COLUMNNAME_HC_NationalID4);
	}

	/** Set BPJS KS.
		@param HC_NationalID5 BPJS KS	  */
	public void setHC_NationalID5 (String HC_NationalID5)
	{
		set_Value (COLUMNNAME_HC_NationalID5, HC_NationalID5);
	}

	/** Get BPJS KS.
		@return BPJS KS	  */
	public String getHC_NationalID5 () 
	{
		return (String)get_Value(COLUMNNAME_HC_NationalID5);
	}

	/** HC_PersonalDataAction AD_Reference_ID=300054 */
	public static final int HC_PERSONALDATAACTION_AD_Reference_ID=300054;
	/** New = NEW */
	public static final String HC_PERSONALDATAACTION_New = "NEW";
	/** Name Change = NMC */
	public static final String HC_PERSONALDATAACTION_NameChange = "NMC";
	/** Photo Change = PHC */
	public static final String HC_PERSONALDATAACTION_PhotoChange = "PHC";
	/** Marital Status Change = MSC */
	public static final String HC_PERSONALDATAACTION_MaritalStatusChange = "MSC";
	/** Tax Status Change = TSC */
	public static final String HC_PERSONALDATAACTION_TaxStatusChange = "TSC";
	/** Personal Data Change = PDC */
	public static final String HC_PERSONALDATAACTION_PersonalDataChange = "PDC";
	/** ID Change = IDC */
	public static final String HC_PERSONALDATAACTION_IDChange = "IDC";
	/** Address Change = ADC */
	public static final String HC_PERSONALDATAACTION_AddressChange = "ADC";
	/** Phone Change = PNC */
	public static final String HC_PERSONALDATAACTION_PhoneChange = "PNC";
	/** Contact Change = EMC */
	public static final String HC_PERSONALDATAACTION_ContactChange = "EMC";
	/** Alternate ID Change = ALC */
	public static final String HC_PERSONALDATAACTION_AlternateIDChange = "ALC";
	/** Set Personal Data Action.
		@param HC_PersonalDataAction Personal Data Action	  */
	public void setHC_PersonalDataAction (String HC_PersonalDataAction)
	{

		set_Value (COLUMNNAME_HC_PersonalDataAction, HC_PersonalDataAction);
	}

	/** Get Personal Data Action.
		@return Personal Data Action	  */
	public String getHC_PersonalDataAction () 
	{
		return (String)get_Value(COLUMNNAME_HC_PersonalDataAction);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_PersonalDataAction()));
    }

	public I_HC_Religion getHC_Religion() throws RuntimeException
    {
		return (I_HC_Religion)MTable.get(getCtx(), I_HC_Religion.Table_Name)
			.getPO(getHC_Religion_ID(), get_TrxName());	}

	/** Set Religion.
		@param HC_Religion_ID Religion	  */
	public void setHC_Religion_ID (int HC_Religion_ID)
	{
		if (HC_Religion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Religion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Religion_ID, Integer.valueOf(HC_Religion_ID));
	}

	/** Get Religion.
		@return Religion	  */
	public int getHC_Religion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Religion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** HC_Status AD_Reference_ID=300058 */
	public static final int HC_STATUS_AD_Reference_ID=300058;
	/** Active = A */
	public static final String HC_STATUS_Active = "A";
	/** Terminate = T */
	public static final String HC_STATUS_Terminate = "T";
	/** Pending = P */
	public static final String HC_STATUS_Pending = "P";
	/** Leave = L */
	public static final String HC_STATUS_Leave = "L";
	/** Blacklist = B */
	public static final String HC_STATUS_Blacklist = "B";
	/** Set Employment Status.
		@param HC_Status Employment Status	  */
	public void setHC_Status (String HC_Status)
	{

		set_Value (COLUMNNAME_HC_Status, HC_Status);
	}

	/** Get Employment Status.
		@return Employment Status	  */
	public String getHC_Status () 
	{
		return (String)get_Value(COLUMNNAME_HC_Status);
	}

	public I_HC_TaxStatus getHC_TaxStatus() throws RuntimeException
    {
		return (I_HC_TaxStatus)MTable.get(getCtx(), I_HC_TaxStatus.Table_Name)
			.getPO(getHC_TaxStatus_ID(), get_TrxName());	}

	/** Set HC Tax Status.
		@param HC_TaxStatus_ID HC Tax Status	  */
	public void setHC_TaxStatus_ID (int HC_TaxStatus_ID)
	{
		if (HC_TaxStatus_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_TaxStatus_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_TaxStatus_ID, Integer.valueOf(HC_TaxStatus_ID));
	}

	/** Get HC Tax Status.
		@return HC Tax Status	  */
	public int getHC_TaxStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_TaxStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Work End Date.
		@param HC_WorkEndDate Work End Date	  */
	public void setHC_WorkEndDate (Timestamp HC_WorkEndDate)
	{
		set_Value (COLUMNNAME_HC_WorkEndDate, HC_WorkEndDate);
	}

	/** Get Work End Date.
		@return Work End Date	  */
	public Timestamp getHC_WorkEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_WorkEndDate);
	}

	/** Set Work Start Date.
		@param HC_WorkStartDate Work Start Date	  */
	public void setHC_WorkStartDate (Timestamp HC_WorkStartDate)
	{
		set_Value (COLUMNNAME_HC_WorkStartDate, HC_WorkStartDate);
	}

	/** Get Work Start Date.
		@return Work Start Date	  */
	public Timestamp getHC_WorkStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_HC_WorkStartDate);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_ValueNoCheck (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set 2nd Phone.
		@param Phone2 
		Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get 2nd Phone.
		@return Identifies an alternate telephone number.
	  */
	public String getPhone2 () 
	{
		return (String)get_Value(COLUMNNAME_Phone2);
	}

	/** Set Phone Ext 1.
		@param PhoneExt1 Phone Ext 1	  */
	public void setPhoneExt1 (String PhoneExt1)
	{
		set_Value (COLUMNNAME_PhoneExt1, PhoneExt1);
	}

	/** Get Phone Ext 1.
		@return Phone Ext 1	  */
	public String getPhoneExt1 () 
	{
		return (String)get_Value(COLUMNNAME_PhoneExt1);
	}

	/** Set Phone Ext 2.
		@param PhoneExt2 Phone Ext 2	  */
	public void setPhoneExt2 (String PhoneExt2)
	{
		set_Value (COLUMNNAME_PhoneExt2, PhoneExt2);
	}

	/** Get Phone Ext 2.
		@return Phone Ext 2	  */
	public String getPhoneExt2 () 
	{
		return (String)get_Value(COLUMNNAME_PhoneExt2);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}