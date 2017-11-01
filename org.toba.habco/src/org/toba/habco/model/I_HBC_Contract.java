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

/** Generated Interface for HBC_Contract
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Contract 
{

    /** TableName=HBC_Contract */
    public static final String Table_Name = "HBC_Contract";

    /** AD_Table_ID=1000168 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActualHireDate */
    public static final String COLUMNNAME_ActualHireDate = "ActualHireDate";

	/** Set Actual Hire Day	  */
	public void setActualHireDate (BigDecimal ActualHireDate);

	/** Get Actual Hire Day	  */
	public BigDecimal getActualHireDate();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AddendumNumber */
    public static final String COLUMNNAME_AddendumNumber = "AddendumNumber";

	/** Set Addendum Number	  */
	public void setAddendumNumber (String AddendumNumber);

	/** Get Addendum Number	  */
	public String getAddendumNumber();

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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name AmountOfCargo */
    public static final String COLUMNNAME_AmountOfCargo = "AmountOfCargo";

	/** Set Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo);

	/** Get Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo();

    /** Column name CargoCondition */
    public static final String COLUMNNAME_CargoCondition = "CargoCondition";

	/** Set Cargo Condition	  */
	public void setCargoCondition (String CargoCondition);

	/** Get Cargo Condition	  */
	public String getCargoCondition();

    /** Column name CargoName */
    public static final String COLUMNNAME_CargoName = "CargoName";

	/** Set Cargo Name	  */
	public void setCargoName (String CargoName);

	/** Get Cargo Name	  */
	public String getCargoName();

    /** Column name C_BankAccount2_ID */
    public static final String COLUMNNAME_C_BankAccount2_ID = "C_BankAccount2_ID";

	/** Set Bank Account 2	  */
	public void setC_BankAccount2_ID (int C_BankAccount2_ID);

	/** Get Bank Account 2	  */
	public int getC_BankAccount2_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount2() throws RuntimeException;

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Bank Account.
	  * Account at the Bank
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/** Set Currency Type.
	  * Currency Conversion Rate Type
	  */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/** Get Currency Type.
	  * Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID();

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/** Set Target Document Type.
	  * Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/** Get Target Document Type.
	  * Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException;

    /** Column name ContractDay */
    public static final String COLUMNNAME_ContractDay = "ContractDay";

	/** Set Contract Day	  */
	public void setContractDay (BigDecimal ContractDay);

	/** Get Contract Day	  */
	public BigDecimal getContractDay();

    /** Column name ContractStatus */
    public static final String COLUMNNAME_ContractStatus = "ContractStatus";

	/** Set Contract Status	  */
	public void setContractStatus (String ContractStatus);

	/** Get Contract Status	  */
	public String getContractStatus();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

    /** Column name C_ProjectTypeMilestone_ID */
    public static final String COLUMNNAME_C_ProjectTypeMilestone_ID = "C_ProjectTypeMilestone_ID";

	/** Set Project Type Milestone	  */
	public void setC_ProjectTypeMilestone_ID (int C_ProjectTypeMilestone_ID);

	/** Get Project Type Milestone	  */
	public int getC_ProjectTypeMilestone_ID();

	public I_C_ProjectTypeMilestone getC_ProjectTypeMilestone() throws RuntimeException;

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

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name CurrentPositionDescription */
    public static final String COLUMNNAME_CurrentPositionDescription = "CurrentPositionDescription";

	/** Set CurrentPositionDescription	  */
	public void setCurrentPositionDescription (String CurrentPositionDescription);

	/** Get CurrentPositionDescription	  */
	public String getCurrentPositionDescription();

    /** Column name Customer_BPartner_ID */
    public static final String COLUMNNAME_Customer_BPartner_ID = "Customer_BPartner_ID";

	/** Set Customer	  */
	public void setCustomer_BPartner_ID (int Customer_BPartner_ID);

	/** Get Customer	  */
	public int getCustomer_BPartner_ID();

	public org.compiere.model.I_C_BPartner getCustomer_BPartner() throws RuntimeException;

    /** Column name Customer_Location_ID */
    public static final String COLUMNNAME_Customer_Location_ID = "Customer_Location_ID";

	/** Set Customer Location	  */
	public void setCustomer_Location_ID (int Customer_Location_ID);

	/** Get Customer Location	  */
	public int getCustomer_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getCustomer_Location() throws RuntimeException;

    /** Column name Customer_User_ID */
    public static final String COLUMNNAME_Customer_User_ID = "Customer_User_ID";

	/** Set Customer User/Contact	  */
	public void setCustomer_User_ID (int Customer_User_ID);

	/** Get Customer User/Contact	  */
	public int getCustomer_User_ID();

	public org.compiere.model.I_AD_User getCustomer_User() throws RuntimeException;

    /** Column name DateContract */
    public static final String COLUMNNAME_DateContract = "DateContract";

	/** Set Contract Date.
	  * The (planned) effective date of this document.
	  */
	public void setDateContract (Timestamp DateContract);

	/** Get Contract Date.
	  * The (planned) effective date of this document.
	  */
	public Timestamp getDateContract();

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

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name From_PortPosition_ID */
    public static final String COLUMNNAME_From_PortPosition_ID = "From_PortPosition_ID";

	/** Set Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID);

	/** Get Port/Position From	  */
	public int getFrom_PortPosition_ID();

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException;

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_Contract_ID */
    public static final String COLUMNNAME_HBC_Contract_ID = "HBC_Contract_ID";

	/** Set Contract	  */
	public void setHBC_Contract_ID (int HBC_Contract_ID);

	/** Get Contract	  */
	public int getHBC_Contract_ID();

    /** Column name HBC_ContractType */
    public static final String COLUMNNAME_HBC_ContractType = "HBC_ContractType";

	/** Set Contract Type	  */
	public void setHBC_ContractType (String HBC_ContractType);

	/** Get Contract Type	  */
	public String getHBC_ContractType();

    /** Column name HBC_Contract_UU */
    public static final String COLUMNNAME_HBC_Contract_UU = "HBC_Contract_UU";

	/** Set HBC_Contract_UU	  */
	public void setHBC_Contract_UU (String HBC_Contract_UU);

	/** Get HBC_Contract_UU	  */
	public String getHBC_Contract_UU();

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException;

    /** Column name HBC_ShipReqType */
    public static final String COLUMNNAME_HBC_ShipReqType = "HBC_ShipReqType";

	/** Set Ship Required Type	  */
	public void setHBC_ShipReqType (String HBC_ShipReqType);

	/** Get Ship Required Type	  */
	public String getHBC_ShipReqType();

    /** Column name HBC_Tugboat_ID */
    public static final String COLUMNNAME_HBC_Tugboat_ID = "HBC_Tugboat_ID";

	/** Set TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID);

	/** Get TugBoat	  */
	public int getHBC_Tugboat_ID();

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException;

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

    /** Column name IsCopy */
    public static final String COLUMNNAME_IsCopy = "IsCopy";

	/** Set Is Copy	  */
	public void setIsCopy (boolean IsCopy);

	/** Get Is Copy	  */
	public boolean isCopy();

    /** Column name IsCurrentDescription */
    public static final String COLUMNNAME_IsCurrentDescription = "IsCurrentDescription";

	/** Set IsCurrentDescription	  */
	public void setIsCurrentDescription (boolean IsCurrentDescription);

	/** Get IsCurrentDescription	  */
	public boolean isCurrentDescription();

    /** Column name IsLumpsum */
    public static final String COLUMNNAME_IsLumpsum = "IsLumpsum";

	/** Set Lumpsum	  */
	public void setIsLumpsum (boolean IsLumpsum);

	/** Get Lumpsum	  */
	public boolean isLumpsum();

    /** Column name IsPercentageCargo */
    public static final String COLUMNNAME_IsPercentageCargo = "IsPercentageCargo";

	/** Set IsPercentageCargo	  */
	public void setIsPercentageCargo (boolean IsPercentageCargo);

	/** Get IsPercentageCargo	  */
	public boolean isPercentageCargo();

    /** Column name LaycanDateTo */
    public static final String COLUMNNAME_LaycanDateTo = "LaycanDateTo";

	/** Set Laycan Date To	  */
	public void setLaycanDateTo (Timestamp LaycanDateTo);

	/** Get Laycan Date To	  */
	public Timestamp getLaycanDateTo();

    /** Column name LoadingAgent_BPartner_ID */
    public static final String COLUMNNAME_LoadingAgent_BPartner_ID = "LoadingAgent_BPartner_ID";

	/** Set Loading Agent	  */
	public void setLoadingAgent_BPartner_ID (int LoadingAgent_BPartner_ID);

	/** Get Loading Agent	  */
	public int getLoadingAgent_BPartner_ID();

	public org.compiere.model.I_C_BPartner getLoadingAgent_BPartner() throws RuntimeException;

    /** Column name LoadingDate */
    public static final String COLUMNNAME_LoadingDate = "LoadingDate";

	/** Set Loading Date	  */
	public void setLoadingDate (Timestamp LoadingDate);

	/** Get Loading Date	  */
	public Timestamp getLoadingDate();

    /** Column name NextID */
    public static final String COLUMNNAME_NextID = "NextID";

	/** Set NextID	  */
	public void setNextID (int NextID);

	/** Get NextID	  */
	public int getNextID();

    /** Column name NumberInvoiced */
    public static final String COLUMNNAME_NumberInvoiced = "NumberInvoiced";

	/** Set Number Invoiced	  */
	public void setNumberInvoiced (BigDecimal NumberInvoiced);

	/** Get Number Invoiced	  */
	public BigDecimal getNumberInvoiced();

    /** Column name OffHireDate */
    public static final String COLUMNNAME_OffHireDate = "OffHireDate";

	/** Set Off Hire Date	  */
	public void setOffHireDate (Timestamp OffHireDate);

	/** Get Off Hire Date	  */
	public Timestamp getOffHireDate();

    /** Column name OFFHired_Position_ID */
    public static final String COLUMNNAME_OFFHired_Position_ID = "OFFHired_Position_ID";

	/** Set Off Hired Position	  */
	public void setOFFHired_Position_ID (int OFFHired_Position_ID);

	/** Get Off Hired Position	  */
	public int getOFFHired_Position_ID();

	public I_HBC_PortPosition getOFFHired_Position() throws RuntimeException;

    /** Column name OnHireDate */
    public static final String COLUMNNAME_OnHireDate = "OnHireDate";

	/** Set On Hire Date	  */
	public void setOnHireDate (Timestamp OnHireDate);

	/** Get On Hire Date	  */
	public Timestamp getOnHireDate();

    /** Column name ONHired_Position_ID */
    public static final String COLUMNNAME_ONHired_Position_ID = "ONHired_Position_ID";

	/** Set On Hired Position	  */
	public void setONHired_Position_ID (int ONHired_Position_ID);

	/** Get On Hired Position	  */
	public int getONHired_Position_ID();

	public I_HBC_PortPosition getONHired_Position() throws RuntimeException;

    /** Column name Owner_User_ID */
    public static final String COLUMNNAME_Owner_User_ID = "Owner_User_ID";

	/** Set Owner User / Contact	  */
	public void setOwner_User_ID (int Owner_User_ID);

	/** Get Owner User / Contact	  */
	public int getOwner_User_ID();

	public org.compiere.model.I_AD_User getOwner_User() throws RuntimeException;

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public BigDecimal getPayAmt();

    /** Column name PercentageCargo */
    public static final String COLUMNNAME_PercentageCargo = "PercentageCargo";

	/** Set PercentageCargo	  */
	public void setPercentageCargo (int PercentageCargo);

	/** Get PercentageCargo	  */
	public int getPercentageCargo();

    /** Column name PortPosition_From2_ID */
    public static final String COLUMNNAME_PortPosition_From2_ID = "PortPosition_From2_ID";

	/** Set Port / Position From 2	  */
	public void setPortPosition_From2_ID (int PortPosition_From2_ID);

	/** Get Port / Position From 2	  */
	public int getPortPosition_From2_ID();

	public I_HBC_PortPosition getPortPosition_From2() throws RuntimeException;

    /** Column name PortPosition_To2_ID */
    public static final String COLUMNNAME_PortPosition_To2_ID = "PortPosition_To2_ID";

	/** Set Port / Position To 2	  */
	public void setPortPosition_To2_ID (int PortPosition_To2_ID);

	/** Get Port / Position To 2	  */
	public int getPortPosition_To2_ID();

	public I_HBC_PortPosition getPortPosition_To2() throws RuntimeException;

    /** Column name ProcessContract */
    public static final String COLUMNNAME_ProcessContract = "ProcessContract";

	/** Set Process Contract	  */
	public void setProcessContract (String ProcessContract);

	/** Get Process Contract	  */
	public String getProcessContract();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Receiver_BPartner_ID */
    public static final String COLUMNNAME_Receiver_BPartner_ID = "Receiver_BPartner_ID";

	/** Set Receiver	  */
	public void setReceiver_BPartner_ID (int Receiver_BPartner_ID);

	/** Get Receiver	  */
	public int getReceiver_BPartner_ID();

	public org.compiere.model.I_C_BPartner getReceiver_BPartner() throws RuntimeException;

    /** Column name RefCode */
    public static final String COLUMNNAME_RefCode = "RefCode";

	/** Set Reference Code	  */
	public void setRefCode (String RefCode);

	/** Get Reference Code	  */
	public String getRefCode();

    /** Column name ReferenceCode */
    public static final String COLUMNNAME_ReferenceCode = "ReferenceCode";

	/** Set Reference Code	  */
	public void setReferenceCode (String ReferenceCode);

	/** Get Reference Code	  */
	public String getReferenceCode();

    /** Column name ShipBorne */
    public static final String COLUMNNAME_ShipBorne = "ShipBorne";

	/** Set Ship Borne	  */
	public void setShipBorne (String ShipBorne);

	/** Get Ship Borne	  */
	public String getShipBorne();

    /** Column name ShipInsurance */
    public static final String COLUMNNAME_ShipInsurance = "ShipInsurance";

	/** Set Ship Insurance 	  */
	public void setShipInsurance (String ShipInsurance);

	/** Get Ship Insurance 	  */
	public String getShipInsurance();

    /** Column name Shipper_BPartner_ID */
    public static final String COLUMNNAME_Shipper_BPartner_ID = "Shipper_BPartner_ID";

	/** Set Shipper	  */
	public void setShipper_BPartner_ID (int Shipper_BPartner_ID);

	/** Get Shipper	  */
	public int getShipper_BPartner_ID();

	public org.compiere.model.I_C_BPartner getShipper_BPartner() throws RuntimeException;

    /** Column name SigningPlace */
    public static final String COLUMNNAME_SigningPlace = "SigningPlace";

	/** Set Signing Place	  */
	public void setSigningPlace (String SigningPlace);

	/** Get Signing Place	  */
	public String getSigningPlace();

    /** Column name SINumber */
    public static final String COLUMNNAME_SINumber = "SINumber";

	/** Set SI Number	  */
	public void setSINumber (String SINumber);

	/** Get SI Number	  */
	public String getSINumber();

    /** Column name To_PortPosition_ID */
    public static final String COLUMNNAME_To_PortPosition_ID = "To_PortPosition_ID";

	/** Set Port/Position To	  */
	public void setTo_PortPosition_ID (int To_PortPosition_ID);

	/** Get Port/Position To	  */
	public int getTo_PortPosition_ID();

	public I_HBC_PortPosition getTo_PortPosition() throws RuntimeException;

    /** Column name UnitPrice */
    public static final String COLUMNNAME_UnitPrice = "UnitPrice";

	/** Set Unit Price	  */
	public void setUnitPrice (BigDecimal UnitPrice);

	/** Get Unit Price	  */
	public BigDecimal getUnitPrice();

    /** Column name UnLoadingAgent_BPartner_ID */
    public static final String COLUMNNAME_UnLoadingAgent_BPartner_ID = "UnLoadingAgent_BPartner_ID";

	/** Set Unloading Agent	  */
	public void setUnLoadingAgent_BPartner_ID (int UnLoadingAgent_BPartner_ID);

	/** Get Unloading Agent	  */
	public int getUnLoadingAgent_BPartner_ID();

	public org.compiere.model.I_C_BPartner getUnLoadingAgent_BPartner() throws RuntimeException;

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();
}
