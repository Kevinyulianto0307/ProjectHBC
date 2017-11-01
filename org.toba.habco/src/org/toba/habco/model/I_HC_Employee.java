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
package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HC_Employee
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HC_Employee 
{

    /** TableName=HC_Employee */
    public static final String Table_Name = "HC_Employee";

    /** AD_Table_ID=300167 */
    public static final int Table_ID = 300167;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/** Set Birthday.
	  * Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday);

	/** Get Birthday.
	  * Birthday or Anniversary day
	  */
	public Timestamp getBirthday();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

    /** Column name C_TaxOffice_ID */
    public static final String COLUMNNAME_C_TaxOffice_ID = "C_TaxOffice_ID";

	/** Set Tax Office	  */
	public void setC_TaxOffice_ID (int C_TaxOffice_ID);

	/** Get Tax Office	  */
	public int getC_TaxOffice_ID();

	public I_C_TaxOffice getC_TaxOffice() throws RuntimeException;

    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/** Set City.
	  * Identifies a City
	  */
	public void setCity (String City);

	/** Get City.
	  * Identifies a City
	  */
	public String getCity();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DutyDate */
    public static final String COLUMNNAME_DutyDate = "DutyDate";

	/** Set Duty Date	  */
	public void setDutyDate (Timestamp DutyDate);

	/** Get Duty Date	  */
	public Timestamp getDutyDate();

    /** Column name EffectiveDateFrom */
    public static final String COLUMNNAME_EffectiveDateFrom = "EffectiveDateFrom";

	/** Set Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom);

	/** Get Effective Date From	  */
	public Timestamp getEffectiveDateFrom();

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name Email2 */
    public static final String COLUMNNAME_Email2 = "Email2";

	/** Set Email Address 2	  */
	public void setEmail2 (String Email2);

	/** Get Email Address 2	  */
	public String getEmail2();

    /** Column name HC_AltID1 */
    public static final String COLUMNNAME_HC_AltID1 = "HC_AltID1";

	/** Set Kartu Keluarga	  */
	public void setHC_AltID1 (String HC_AltID1);

	/** Get Kartu Keluarga	  */
	public String getHC_AltID1();

    /** Column name HC_AltID2 */
    public static final String COLUMNNAME_HC_AltID2 = "HC_AltID2";

	/** Set Alternate ID 2	  */
	public void setHC_AltID2 (String HC_AltID2);

	/** Get Alternate ID 2	  */
	public String getHC_AltID2();

    /** Column name HC_BirthCountry_ID */
    public static final String COLUMNNAME_HC_BirthCountry_ID = "HC_BirthCountry_ID";

	/** Set Birth Country	  */
	public void setHC_BirthCountry_ID (int HC_BirthCountry_ID);

	/** Get Birth Country	  */
	public int getHC_BirthCountry_ID();

    /** Column name HC_BirthRegion_ID */
    public static final String COLUMNNAME_HC_BirthRegion_ID = "HC_BirthRegion_ID";

	/** Set Birth Province	  */
	public void setHC_BirthRegion_ID (int HC_BirthRegion_ID);

	/** Get Birth Province	  */
	public int getHC_BirthRegion_ID();

	public org.compiere.model.I_C_Region getHC_BirthRegion() throws RuntimeException;

    /** Column name HC_BloodType */
    public static final String COLUMNNAME_HC_BloodType = "HC_BloodType";

	/** Set Blood Type	  */
	public void setHC_BloodType (String HC_BloodType);

	/** Get Blood Type	  */
	public String getHC_BloodType();

    /** Column name HC_Employee_ID */
    public static final String COLUMNNAME_HC_Employee_ID = "HC_Employee_ID";

	/** Set Employee Data	  */
	public void setHC_Employee_ID (int HC_Employee_ID);

	/** Get Employee Data	  */
	public int getHC_Employee_ID();

    /** Column name HC_Employee_UU */
    public static final String COLUMNNAME_HC_Employee_UU = "HC_Employee_UU";

	/** Set HC_Employee_UU	  */
	public void setHC_Employee_UU (String HC_Employee_UU);

	/** Get HC_Employee_UU	  */
	public String getHC_Employee_UU();

    /** Column name HC_Ethnic_ID */
    public static final String COLUMNNAME_HC_Ethnic_ID = "HC_Ethnic_ID";

	/** Set Ethnic	  */
	public void setHC_Ethnic_ID (int HC_Ethnic_ID);

	/** Get Ethnic	  */
	public int getHC_Ethnic_ID();

	public I_HC_Ethnic getHC_Ethnic() throws RuntimeException;

    /** Column name HC_Gender */
    public static final String COLUMNNAME_HC_Gender = "HC_Gender";

	/** Set Gender	  */
	public void setHC_Gender (String HC_Gender);

	/** Get Gender	  */
	public String getHC_Gender();

    /** Column name HC_ID1_ExpDate */
    public static final String COLUMNNAME_HC_ID1_ExpDate = "HC_ID1_ExpDate";

	/** Set KTP Expiry Date	  */
	public void setHC_ID1_ExpDate (Timestamp HC_ID1_ExpDate);

	/** Get KTP Expiry Date	  */
	public Timestamp getHC_ID1_ExpDate();

    /** Column name HC_ID2_ExpDate */
    public static final String COLUMNNAME_HC_ID2_ExpDate = "HC_ID2_ExpDate";

	/** Set Passport Expiry Date	  */
	public void setHC_ID2_ExpDate (Timestamp HC_ID2_ExpDate);

	/** Get Passport Expiry Date	  */
	public Timestamp getHC_ID2_ExpDate();

    /** Column name HC_ID3_ExpDate */
    public static final String COLUMNNAME_HC_ID3_ExpDate = "HC_ID3_ExpDate";

	/** Set NPWP Expiry Date	  */
	public void setHC_ID3_ExpDate (Timestamp HC_ID3_ExpDate);

	/** Get NPWP Expiry Date	  */
	public Timestamp getHC_ID3_ExpDate();

    /** Column name HC_ID4_ExpDate */
    public static final String COLUMNNAME_HC_ID4_ExpDate = "HC_ID4_ExpDate";

	/** Set BPJS TK Expiry Date	  */
	public void setHC_ID4_ExpDate (Timestamp HC_ID4_ExpDate);

	/** Get BPJS TK Expiry Date	  */
	public Timestamp getHC_ID4_ExpDate();

    /** Column name HC_ID5_ExpDate */
    public static final String COLUMNNAME_HC_ID5_ExpDate = "HC_ID5_ExpDate";

	/** Set BPJS KS Expiry Date	  */
	public void setHC_ID5_ExpDate (Timestamp HC_ID5_ExpDate);

	/** Get BPJS KS Expiry Date	  */
	public Timestamp getHC_ID5_ExpDate();

    /** Column name HC_MaritalDate */
    public static final String COLUMNNAME_HC_MaritalDate = "HC_MaritalDate";

	/** Set Marital Date	  */
	public void setHC_MaritalDate (Timestamp HC_MaritalDate);

	/** Get Marital Date	  */
	public Timestamp getHC_MaritalDate();

    /** Column name HC_MaritalStatus */
    public static final String COLUMNNAME_HC_MaritalStatus = "HC_MaritalStatus";

	/** Set Marital Status	  */
	public void setHC_MaritalStatus (String HC_MaritalStatus);

	/** Get Marital Status	  */
	public String getHC_MaritalStatus();

    /** Column name HC_NationalID1 */
    public static final String COLUMNNAME_HC_NationalID1 = "HC_NationalID1";

	/** Set KTP	  */
	public void setHC_NationalID1 (String HC_NationalID1);

	/** Get KTP	  */
	public String getHC_NationalID1();

    /** Column name HC_NationalID2 */
    public static final String COLUMNNAME_HC_NationalID2 = "HC_NationalID2";

	/** Set Passport	  */
	public void setHC_NationalID2 (String HC_NationalID2);

	/** Get Passport	  */
	public String getHC_NationalID2();

    /** Column name HC_NationalID3 */
    public static final String COLUMNNAME_HC_NationalID3 = "HC_NationalID3";

	/** Set NPWP	  */
	public void setHC_NationalID3 (String HC_NationalID3);

	/** Get NPWP	  */
	public String getHC_NationalID3();

    /** Column name HC_NationalID4 */
    public static final String COLUMNNAME_HC_NationalID4 = "HC_NationalID4";

	/** Set BPJS TK	  */
	public void setHC_NationalID4 (String HC_NationalID4);

	/** Get BPJS TK	  */
	public String getHC_NationalID4();

    /** Column name HC_NationalID5 */
    public static final String COLUMNNAME_HC_NationalID5 = "HC_NationalID5";

	/** Set BPJS KS	  */
	public void setHC_NationalID5 (String HC_NationalID5);

	/** Get BPJS KS	  */
	public String getHC_NationalID5();

    /** Column name HC_Org_ID */
    public static final String COLUMNNAME_HC_Org_ID = "HC_Org_ID";

	/** Set HC Organization	  */
	public void setHC_Org_ID (int HC_Org_ID);

	/** Get HC Organization	  */
	public int getHC_Org_ID();

	public I_HC_Org getHC_Org() throws RuntimeException;

    /** Column name HC_PersonalDataAction */
    public static final String COLUMNNAME_HC_PersonalDataAction = "HC_PersonalDataAction";

	/** Set Personal Data Action	  */
	public void setHC_PersonalDataAction (String HC_PersonalDataAction);

	/** Get Personal Data Action	  */
	public String getHC_PersonalDataAction();

    /** Column name HC_Religion_ID */
    public static final String COLUMNNAME_HC_Religion_ID = "HC_Religion_ID";

	/** Set Religion	  */
	public void setHC_Religion_ID (int HC_Religion_ID);

	/** Get Religion	  */
	public int getHC_Religion_ID();

	public I_HC_Religion getHC_Religion() throws RuntimeException;

    /** Column name HC_Status */
    public static final String COLUMNNAME_HC_Status = "HC_Status";

	/** Set Employment Status	  */
	public void setHC_Status (String HC_Status);

	/** Get Employment Status	  */
	public String getHC_Status();

    /** Column name HC_TaxStatus_ID */
    public static final String COLUMNNAME_HC_TaxStatus_ID = "HC_TaxStatus_ID";

	/** Set HC Tax Status	  */
	public void setHC_TaxStatus_ID (int HC_TaxStatus_ID);

	/** Get HC Tax Status	  */
	public int getHC_TaxStatus_ID();

	public I_HC_TaxStatus getHC_TaxStatus() throws RuntimeException;

    /** Column name HC_WorkEndDate */
    public static final String COLUMNNAME_HC_WorkEndDate = "HC_WorkEndDate";

	/** Set Work End Date	  */
	public void setHC_WorkEndDate (Timestamp HC_WorkEndDate);

	/** Get Work End Date	  */
	public Timestamp getHC_WorkEndDate();

    /** Column name HC_WorkStartDate */
    public static final String COLUMNNAME_HC_WorkStartDate = "HC_WorkStartDate";

	/** Set Work Start Date	  */
	public void setHC_WorkStartDate (Timestamp HC_WorkStartDate);

	/** Get Work Start Date	  */
	public Timestamp getHC_WorkStartDate();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsOnLeave */
    public static final String COLUMNNAME_IsOnLeave = "IsOnLeave";

	/** Set On Leave	  */
	public void setIsOnLeave (boolean IsOnLeave);

	/** Get On Leave	  */
	public boolean isOnLeave();

    /** Column name IsPersonInCharge */
    public static final String COLUMNNAME_IsPersonInCharge = "IsPersonInCharge";

	/** Set Person In Charge	  */
	public void setIsPersonInCharge (boolean IsPersonInCharge);

	/** Get Person In Charge	  */
	public boolean isPersonInCharge();

    /** Column name JoinDate */
    public static final String COLUMNNAME_JoinDate = "JoinDate";

	/** Set Join Date	  */
	public void setJoinDate (Timestamp JoinDate);

	/** Get Join Date	  */
	public Timestamp getJoinDate();

    /** Column name LeaveEndDate */
    public static final String COLUMNNAME_LeaveEndDate = "LeaveEndDate";

	/** Set Leave End Date	  */
	public void setLeaveEndDate (Timestamp LeaveEndDate);

	/** Get Leave End Date	  */
	public Timestamp getLeaveEndDate();

    /** Column name LeaveStartDate */
    public static final String COLUMNNAME_LeaveStartDate = "LeaveStartDate";

	/** Set Leave Start Date	  */
	public void setLeaveStartDate (Timestamp LeaveStartDate);

	/** Get Leave Start Date	  */
	public Timestamp getLeaveStartDate();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Additional Name
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Additional Name
	  */
	public String getName2();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

	/** Set 2nd Phone.
	  * Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2);

	/** Get 2nd Phone.
	  * Identifies an alternate telephone number.
	  */
	public String getPhone2();

    /** Column name PhoneExt1 */
    public static final String COLUMNNAME_PhoneExt1 = "PhoneExt1";

	/** Set Phone Ext 1	  */
	public void setPhoneExt1 (String PhoneExt1);

	/** Get Phone Ext 1	  */
	public String getPhoneExt1();

    /** Column name PhoneExt2 */
    public static final String COLUMNNAME_PhoneExt2 = "PhoneExt2";

	/** Set Phone Ext 2	  */
	public void setPhoneExt2 (String PhoneExt2);

	/** Get Phone Ext 2	  */
	public String getPhoneExt2();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
