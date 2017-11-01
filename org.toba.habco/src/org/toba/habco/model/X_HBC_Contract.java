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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for HBC_Contract
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Contract extends PO implements I_HBC_Contract, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161207L;

    /** Standard Constructor */
    public X_HBC_Contract (Properties ctx, int HBC_Contract_ID, String trxName)
    {
      super (ctx, HBC_Contract_ID, trxName);
      /** if (HBC_Contract_ID == 0)
        {
			setCargoCondition (null);
// -
			setC_BankAccount_ID (0);
			setC_Currency_ID (0);
			setHBC_Contract_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_Contract (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_HBC_Contract[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual Hire Day.
		@param ActualHireDate Actual Hire Day	  */
	public void setActualHireDate (BigDecimal ActualHireDate)
	{
		set_Value (COLUMNNAME_ActualHireDate, ActualHireDate);
	}

	/** Get Actual Hire Day.
		@return Actual Hire Day	  */
	public BigDecimal getActualHireDate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualHireDate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Addendum Number.
		@param AddendumNumber Addendum Number	  */
	public void setAddendumNumber (String AddendumNumber)
	{
		set_Value (COLUMNNAME_AddendumNumber, AddendumNumber);
	}

	/** Get Addendum Number.
		@return Addendum Number	  */
	public String getAddendumNumber () 
	{
		return (String)get_Value(COLUMNNAME_AddendumNumber);
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount Of Cargo.
		@param AmountOfCargo Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo)
	{
		set_Value (COLUMNNAME_AmountOfCargo, AmountOfCargo);
	}

	/** Get Amount Of Cargo.
		@return Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountOfCargo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Cargo Condition.
		@param CargoCondition Cargo Condition	  */
	public void setCargoCondition (String CargoCondition)
	{
		set_Value (COLUMNNAME_CargoCondition, CargoCondition);
	}

	/** Get Cargo Condition.
		@return Cargo Condition	  */
	public String getCargoCondition () 
	{
		return (String)get_Value(COLUMNNAME_CargoCondition);
	}

	/** Coal = C */
	public static final String CARGONAME_Coal = "C";
	/** Wood = W */
	public static final String CARGONAME_Wood = "W";
	/** - = - */
	public static final String CARGONAME__ = "-";
	/** Gravel = G */
	public static final String CARGONAME_Gravel = "G";
	/** Equipment = E */
	public static final String CARGONAME_Equipment = "E";
	/** Heave Equipment = H */
	public static final String CARGONAME_HeaveEquipment = "H";
	/** CPO = P */
	public static final String CARGONAME_CPO = "P";
	/** Nickel = N */
	public static final String CARGONAME_Nickel = "N";
	/** Set Cargo Name.
		@param CargoName Cargo Name	  */
	public void setCargoName (String CargoName)
	{

		set_Value (COLUMNNAME_CargoName, CargoName);
	}

	/** Get Cargo Name.
		@return Cargo Name	  */
	public String getCargoName () 
	{
		return (String)get_Value(COLUMNNAME_CargoName);
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount2() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount2_ID(), get_TrxName());	}

	/** Set Bank Account 2.
		@param C_BankAccount2_ID Bank Account 2	  */
	public void setC_BankAccount2_ID (int C_BankAccount2_ID)
	{
		if (C_BankAccount2_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount2_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount2_ID, Integer.valueOf(C_BankAccount2_ID));
	}

	/** Get Bank Account 2.
		@return Bank Account 2	  */
	public int getC_BankAccount2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
    {
		return (org.compiere.model.I_C_ConversionType)MTable.get(getCtx(), org.compiere.model.I_C_ConversionType.Table_Name)
			.getPO(getC_ConversionType_ID(), get_TrxName());	}

	/** Set Currency Type.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Currency Type.
		@return Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocTypeTarget_ID(), get_TrxName());	}

	/** Set Target Document Type.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Target Document Type.
		@return Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contract Day.
		@param ContractDay Contract Day	  */
	public void setContractDay (BigDecimal ContractDay)
	{
		set_Value (COLUMNNAME_ContractDay, ContractDay);
	}

	/** Get Contract Day.
		@return Contract Day	  */
	public BigDecimal getContractDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ContractDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Cancel = CA */
	public static final String CONTRACTSTATUS_Cancel = "CA";
	/** Complete = CO */
	public static final String CONTRACTSTATUS_Complete = "CO";
	/** Draft = DR */
	public static final String CONTRACTSTATUS_Draft = "DR";
	/** Finish = FI */
	public static final String CONTRACTSTATUS_Finish = "FI";
	/** Closed = CL */
	public static final String CONTRACTSTATUS_Closed = "CL";
	/** Set Contract Status.
		@param ContractStatus Contract Status	  */
	public void setContractStatus (String ContractStatus)
	{

		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	/** Get Contract Status.
		@return Contract Status	  */
	public String getContractStatus () 
	{
		return (String)get_Value(COLUMNNAME_ContractStatus);
	}

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ProjectTypeMilestone getC_ProjectTypeMilestone() throws RuntimeException
    {
		return (I_C_ProjectTypeMilestone)MTable.get(getCtx(), I_C_ProjectTypeMilestone.Table_Name)
			.getPO(getC_ProjectTypeMilestone_ID(), get_TrxName());	}

	/** Set Project Type Milestone.
		@param C_ProjectTypeMilestone_ID Project Type Milestone	  */
	public void setC_ProjectTypeMilestone_ID (int C_ProjectTypeMilestone_ID)
	{
		if (C_ProjectTypeMilestone_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectTypeMilestone_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectTypeMilestone_ID, Integer.valueOf(C_ProjectTypeMilestone_ID));
	}

	/** Get Project Type Milestone.
		@return Project Type Milestone	  */
	public int getC_ProjectTypeMilestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectTypeMilestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CurrentPositionDescription.
		@param CurrentPositionDescription CurrentPositionDescription	  */
	public void setCurrentPositionDescription (String CurrentPositionDescription)
	{
		set_Value (COLUMNNAME_CurrentPositionDescription, CurrentPositionDescription);
	}

	/** Get CurrentPositionDescription.
		@return CurrentPositionDescription	  */
	public String getCurrentPositionDescription () 
	{
		return (String)get_Value(COLUMNNAME_CurrentPositionDescription);
	}

	public org.compiere.model.I_C_BPartner getCustomer_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getCustomer_BPartner_ID(), get_TrxName());	}

	/** Set Customer.
		@param Customer_BPartner_ID Customer	  */
	public void setCustomer_BPartner_ID (int Customer_BPartner_ID)
	{
		if (Customer_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Customer_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Customer_BPartner_ID, Integer.valueOf(Customer_BPartner_ID));
	}

	/** Get Customer.
		@return Customer	  */
	public int getCustomer_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Customer_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getCustomer_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getCustomer_Location_ID(), get_TrxName());	}

	/** Set Customer Location.
		@param Customer_Location_ID Customer Location	  */
	public void setCustomer_Location_ID (int Customer_Location_ID)
	{
		if (Customer_Location_ID < 1) 
			set_Value (COLUMNNAME_Customer_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Customer_Location_ID, Integer.valueOf(Customer_Location_ID));
	}

	/** Get Customer Location.
		@return Customer Location	  */
	public int getCustomer_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Customer_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getCustomer_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getCustomer_User_ID(), get_TrxName());	}

	/** Set Customer User/Contact.
		@param Customer_User_ID Customer User/Contact	  */
	public void setCustomer_User_ID (int Customer_User_ID)
	{
		if (Customer_User_ID < 1) 
			set_Value (COLUMNNAME_Customer_User_ID, null);
		else 
			set_Value (COLUMNNAME_Customer_User_ID, Integer.valueOf(Customer_User_ID));
	}

	/** Get Customer User/Contact.
		@return Customer User/Contact	  */
	public int getCustomer_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Customer_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contract Date.
		@param DateContract 
		The (planned) effective date of this document.
	  */
	public void setDateContract (Timestamp DateContract)
	{
		set_ValueNoCheck (COLUMNNAME_DateContract, DateContract);
	}

	/** Get Contract Date.
		@return The (planned) effective date of this document.
	  */
	public Timestamp getDateContract () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateContract);
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

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Request Approval = RA */
	public static final String DOCSTATUS_RequestApproval = "RA";
	/** Feedback = FD */
	public static final String DOCSTATUS_Feedback = "FD";
	/** Reject = RJ */
	public static final String DOCSTATUS_Reject = "RJ";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getFrom_PortPosition_ID(), get_TrxName());	}

	/** Set Port/Position From.
		@param From_PortPosition_ID Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID)
	{
		if (From_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_From_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_From_PortPosition_ID, Integer.valueOf(From_PortPosition_ID));
	}

	/** Get Port/Position From.
		@return Port/Position From	  */
	public int getFrom_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_From_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Barge getHBC_Barge() throws RuntimeException
    {
		return (I_HBC_Barge)MTable.get(getCtx(), I_HBC_Barge.Table_Name)
			.getPO(getHBC_Barge_ID(), get_TrxName());	}

	/** Set Barge.
		@param HBC_Barge_ID Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID)
	{
		if (HBC_Barge_ID < 1) 
			set_Value (COLUMNNAME_HBC_Barge_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Barge_ID, Integer.valueOf(HBC_Barge_ID));
	}

	/** Get Barge.
		@return Barge	  */
	public int getHBC_Barge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Barge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Contract.
		@param HBC_Contract_ID Contract	  */
	public void setHBC_Contract_ID (int HBC_Contract_ID)
	{
		if (HBC_Contract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Contract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Contract_ID, Integer.valueOf(HBC_Contract_ID));
	}

	/** Get Contract.
		@return Contract	  */
	public int getHBC_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Annual Contract = ANNC */
	public static final String HBC_CONTRACTTYPE_AnnualContract = "ANNC";
	/** Docking = DOCK */
	public static final String HBC_CONTRACTTYPE_Docking = "DOCK";
	/** New Ship = NEWS */
	public static final String HBC_CONTRACTTYPE_NewShip = "NEWS";
	/** Spot Shipment = SPAL */
	public static final String HBC_CONTRACTTYPE_SpotShipment = "SPAL";
	/** Stand By = STAN */
	public static final String HBC_CONTRACTTYPE_StandBy = "STAN";
	/** Time Charter = TIME */
	public static final String HBC_CONTRACTTYPE_TimeCharter = "TIME";
	/** SPAL = SPHJ */
	public static final String HBC_CONTRACTTYPE_SPAL = "SPHJ";
	/** Set Contract Type.
		@param HBC_ContractType Contract Type	  */
	public void setHBC_ContractType (String HBC_ContractType)
	{

		set_Value (COLUMNNAME_HBC_ContractType, HBC_ContractType);
	}

	/** Get Contract Type.
		@return Contract Type	  */
	public String getHBC_ContractType () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ContractType);
	}

	/** Set HBC_Contract_UU.
		@param HBC_Contract_UU HBC_Contract_UU	  */
	public void setHBC_Contract_UU (String HBC_Contract_UU)
	{
		set_Value (COLUMNNAME_HBC_Contract_UU, HBC_Contract_UU);
	}

	/** Get HBC_Contract_UU.
		@return HBC_Contract_UU	  */
	public String getHBC_Contract_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Contract_UU);
	}

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getHBC_PortPosition_ID(), get_TrxName());	}

	/** Set Port / Position.
		@param HBC_PortPosition_ID Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID)
	{
		if (HBC_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_HBC_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_PortPosition_ID, Integer.valueOf(HBC_PortPosition_ID));
	}

	/** Get Port / Position.
		@return Port / Position	  */
	public int getHBC_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Barge = BOL */
	public static final String HBC_SHIPREQTYPE_Barge = "BOL";
	/** TugBoat = TOL */
	public static final String HBC_SHIPREQTYPE_TugBoat = "TOL";
	/** TugBoat & Barge = TNB */
	public static final String HBC_SHIPREQTYPE_TugBoatBarge = "TNB";
	/** No Ship = NOS */
	public static final String HBC_SHIPREQTYPE_NoShip = "NOS";
	/** Set Ship Required Type.
		@param HBC_ShipReqType Ship Required Type	  */
	public void setHBC_ShipReqType (String HBC_ShipReqType)
	{

		set_Value (COLUMNNAME_HBC_ShipReqType, HBC_ShipReqType);
	}

	/** Get Ship Required Type.
		@return Ship Required Type	  */
	public String getHBC_ShipReqType () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipReqType);
	}

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException
    {
		return (I_HBC_Tugboat)MTable.get(getCtx(), I_HBC_Tugboat.Table_Name)
			.getPO(getHBC_Tugboat_ID(), get_TrxName());	}

	/** Set TugBoat.
		@param HBC_Tugboat_ID TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID)
	{
		if (HBC_Tugboat_ID < 1) 
			set_Value (COLUMNNAME_HBC_Tugboat_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Tugboat_ID, Integer.valueOf(HBC_Tugboat_ID));
	}

	/** Get TugBoat.
		@return TugBoat	  */
	public int getHBC_Tugboat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Tugboat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Is Copy.
		@param IsCopy Is Copy	  */
	public void setIsCopy (boolean IsCopy)
	{
		set_Value (COLUMNNAME_IsCopy, Boolean.valueOf(IsCopy));
	}

	/** Get Is Copy.
		@return Is Copy	  */
	public boolean isCopy () 
	{
		Object oo = get_Value(COLUMNNAME_IsCopy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsCurrentDescription.
		@param IsCurrentDescription IsCurrentDescription	  */
	public void setIsCurrentDescription (boolean IsCurrentDescription)
	{
		set_Value (COLUMNNAME_IsCurrentDescription, Boolean.valueOf(IsCurrentDescription));
	}

	/** Get IsCurrentDescription.
		@return IsCurrentDescription	  */
	public boolean isCurrentDescription () 
	{
		Object oo = get_Value(COLUMNNAME_IsCurrentDescription);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lumpsum.
		@param IsLumpsum Lumpsum	  */
	public void setIsLumpsum (boolean IsLumpsum)
	{
		set_Value (COLUMNNAME_IsLumpsum, Boolean.valueOf(IsLumpsum));
	}

	/** Get Lumpsum.
		@return Lumpsum	  */
	public boolean isLumpsum () 
	{
		Object oo = get_Value(COLUMNNAME_IsLumpsum);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPercentageCargo.
		@param IsPercentageCargo IsPercentageCargo	  */
	public void setIsPercentageCargo (boolean IsPercentageCargo)
	{
		set_Value (COLUMNNAME_IsPercentageCargo, Boolean.valueOf(IsPercentageCargo));
	}

	/** Get IsPercentageCargo.
		@return IsPercentageCargo	  */
	public boolean isPercentageCargo () 
	{
		Object oo = get_Value(COLUMNNAME_IsPercentageCargo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Laycan Date To.
		@param LaycanDateTo Laycan Date To	  */
	public void setLaycanDateTo (Timestamp LaycanDateTo)
	{
		set_Value (COLUMNNAME_LaycanDateTo, LaycanDateTo);
	}

	/** Get Laycan Date To.
		@return Laycan Date To	  */
	public Timestamp getLaycanDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LaycanDateTo);
	}

	public org.compiere.model.I_C_BPartner getLoadingAgent_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getLoadingAgent_BPartner_ID(), get_TrxName());	}

	/** Set Loading Agent.
		@param LoadingAgent_BPartner_ID Loading Agent	  */
	public void setLoadingAgent_BPartner_ID (int LoadingAgent_BPartner_ID)
	{
		if (LoadingAgent_BPartner_ID < 1) 
			set_Value (COLUMNNAME_LoadingAgent_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_LoadingAgent_BPartner_ID, Integer.valueOf(LoadingAgent_BPartner_ID));
	}

	/** Get Loading Agent.
		@return Loading Agent	  */
	public int getLoadingAgent_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LoadingAgent_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Loading Date.
		@param LoadingDate Loading Date	  */
	public void setLoadingDate (Timestamp LoadingDate)
	{
		set_Value (COLUMNNAME_LoadingDate, LoadingDate);
	}

	/** Get Loading Date.
		@return Loading Date	  */
	public Timestamp getLoadingDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LoadingDate);
	}

	/** Set NextID.
		@param NextID NextID	  */
	public void setNextID (int NextID)
	{
		set_Value (COLUMNNAME_NextID, Integer.valueOf(NextID));
	}

	/** Get NextID.
		@return NextID	  */
	public int getNextID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NextID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Number Invoiced.
		@param NumberInvoiced Number Invoiced	  */
	public void setNumberInvoiced (BigDecimal NumberInvoiced)
	{
		set_Value (COLUMNNAME_NumberInvoiced, NumberInvoiced);
	}

	/** Get Number Invoiced.
		@return Number Invoiced	  */
	public BigDecimal getNumberInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NumberInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Off Hire Date.
		@param OffHireDate Off Hire Date	  */
	public void setOffHireDate (Timestamp OffHireDate)
	{
		set_Value (COLUMNNAME_OffHireDate, OffHireDate);
	}

	/** Get Off Hire Date.
		@return Off Hire Date	  */
	public Timestamp getOffHireDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_OffHireDate);
	}

	public I_HBC_PortPosition getOFFHired_Position() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getOFFHired_Position_ID(), get_TrxName());	}

	/** Set Off Hired Position.
		@param OFFHired_Position_ID Off Hired Position	  */
	public void setOFFHired_Position_ID (int OFFHired_Position_ID)
	{
		if (OFFHired_Position_ID < 1) 
			set_Value (COLUMNNAME_OFFHired_Position_ID, null);
		else 
			set_Value (COLUMNNAME_OFFHired_Position_ID, Integer.valueOf(OFFHired_Position_ID));
	}

	/** Get Off Hired Position.
		@return Off Hired Position	  */
	public int getOFFHired_Position_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OFFHired_Position_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set On Hire Date.
		@param OnHireDate On Hire Date	  */
	public void setOnHireDate (Timestamp OnHireDate)
	{
		set_Value (COLUMNNAME_OnHireDate, OnHireDate);
	}

	/** Get On Hire Date.
		@return On Hire Date	  */
	public Timestamp getOnHireDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_OnHireDate);
	}

	public I_HBC_PortPosition getONHired_Position() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getONHired_Position_ID(), get_TrxName());	}

	/** Set On Hired Position.
		@param ONHired_Position_ID On Hired Position	  */
	public void setONHired_Position_ID (int ONHired_Position_ID)
	{
		if (ONHired_Position_ID < 1) 
			set_Value (COLUMNNAME_ONHired_Position_ID, null);
		else 
			set_Value (COLUMNNAME_ONHired_Position_ID, Integer.valueOf(ONHired_Position_ID));
	}

	/** Get On Hired Position.
		@return On Hired Position	  */
	public int getONHired_Position_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ONHired_Position_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getOwner_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getOwner_User_ID(), get_TrxName());	}

	/** Set Owner User / Contact.
		@param Owner_User_ID Owner User / Contact	  */
	public void setOwner_User_ID (int Owner_User_ID)
	{
		if (Owner_User_ID < 1) 
			set_Value (COLUMNNAME_Owner_User_ID, null);
		else 
			set_Value (COLUMNNAME_Owner_User_ID, Integer.valueOf(Owner_User_ID));
	}

	/** Get Owner User / Contact.
		@return Owner User / Contact	  */
	public int getOwner_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Owner_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PercentageCargo.
		@param PercentageCargo PercentageCargo	  */
	public void setPercentageCargo (int PercentageCargo)
	{
		set_Value (COLUMNNAME_PercentageCargo, Integer.valueOf(PercentageCargo));
	}

	/** Get PercentageCargo.
		@return PercentageCargo	  */
	public int getPercentageCargo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PercentageCargo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_PortPosition getPortPosition_From2() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getPortPosition_From2_ID(), get_TrxName());	}

	/** Set Port / Position From 2.
		@param PortPosition_From2_ID Port / Position From 2	  */
	public void setPortPosition_From2_ID (int PortPosition_From2_ID)
	{
		if (PortPosition_From2_ID < 1) 
			set_Value (COLUMNNAME_PortPosition_From2_ID, null);
		else 
			set_Value (COLUMNNAME_PortPosition_From2_ID, Integer.valueOf(PortPosition_From2_ID));
	}

	/** Get Port / Position From 2.
		@return Port / Position From 2	  */
	public int getPortPosition_From2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PortPosition_From2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_PortPosition getPortPosition_To2() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getPortPosition_To2_ID(), get_TrxName());	}

	/** Set Port / Position To 2.
		@param PortPosition_To2_ID Port / Position To 2	  */
	public void setPortPosition_To2_ID (int PortPosition_To2_ID)
	{
		if (PortPosition_To2_ID < 1) 
			set_Value (COLUMNNAME_PortPosition_To2_ID, null);
		else 
			set_Value (COLUMNNAME_PortPosition_To2_ID, Integer.valueOf(PortPosition_To2_ID));
	}

	/** Get Port / Position To 2.
		@return Port / Position To 2	  */
	public int getPortPosition_To2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PortPosition_To2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Contract.
		@param ProcessContract Process Contract	  */
	public void setProcessContract (String ProcessContract)
	{
		set_Value (COLUMNNAME_ProcessContract, ProcessContract);
	}

	/** Get Process Contract.
		@return Process Contract	  */
	public String getProcessContract () 
	{
		return (String)get_Value(COLUMNNAME_ProcessContract);
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

	public org.compiere.model.I_C_BPartner getReceiver_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getReceiver_BPartner_ID(), get_TrxName());	}

	/** Set Receiver.
		@param Receiver_BPartner_ID Receiver	  */
	public void setReceiver_BPartner_ID (int Receiver_BPartner_ID)
	{
		if (Receiver_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Receiver_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Receiver_BPartner_ID, Integer.valueOf(Receiver_BPartner_ID));
	}

	/** Get Receiver.
		@return Receiver	  */
	public int getReceiver_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Receiver_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference Code.
		@param RefCode Reference Code	  */
	public void setRefCode (String RefCode)
	{
		set_Value (COLUMNNAME_RefCode, RefCode);
	}

	/** Get Reference Code.
		@return Reference Code	  */
	public String getRefCode () 
	{
		return (String)get_Value(COLUMNNAME_RefCode);
	}

	/** Set Reference Code.
		@param ReferenceCode Reference Code	  */
	public void setReferenceCode (String ReferenceCode)
	{
		set_Value (COLUMNNAME_ReferenceCode, ReferenceCode);
	}

	/** Get Reference Code.
		@return Reference Code	  */
	public String getReferenceCode () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceCode);
	}

	/** Ship Borne By Owner = O */
	public static final String SHIPBORNE_ShipBorneByOwner = "O";
	/** Ship Borne By Shipper = S */
	public static final String SHIPBORNE_ShipBorneByShipper = "S";
	/** Set Ship Borne.
		@param ShipBorne Ship Borne	  */
	public void setShipBorne (String ShipBorne)
	{

		set_Value (COLUMNNAME_ShipBorne, ShipBorne);
	}

	/** Get Ship Borne.
		@return Ship Borne	  */
	public String getShipBorne () 
	{
		return (String)get_Value(COLUMNNAME_ShipBorne);
	}

	/** Ship Borne By Owner = O */
	public static final String SHIPINSURANCE_ShipBorneByOwner = "O";
	/** Ship Borne By Shipper = S */
	public static final String SHIPINSURANCE_ShipBorneByShipper = "S";
	/** Set Ship Insurance .
		@param ShipInsurance Ship Insurance 	  */
	public void setShipInsurance (String ShipInsurance)
	{

		set_Value (COLUMNNAME_ShipInsurance, ShipInsurance);
	}

	/** Get Ship Insurance .
		@return Ship Insurance 	  */
	public String getShipInsurance () 
	{
		return (String)get_Value(COLUMNNAME_ShipInsurance);
	}

	public org.compiere.model.I_C_BPartner getShipper_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getShipper_BPartner_ID(), get_TrxName());	}

	/** Set Shipper.
		@param Shipper_BPartner_ID Shipper	  */
	public void setShipper_BPartner_ID (int Shipper_BPartner_ID)
	{
		if (Shipper_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, Integer.valueOf(Shipper_BPartner_ID));
	}

	/** Get Shipper.
		@return Shipper	  */
	public int getShipper_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Shipper_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Signing Place.
		@param SigningPlace Signing Place	  */
	public void setSigningPlace (String SigningPlace)
	{
		set_Value (COLUMNNAME_SigningPlace, SigningPlace);
	}

	/** Get Signing Place.
		@return Signing Place	  */
	public String getSigningPlace () 
	{
		return (String)get_Value(COLUMNNAME_SigningPlace);
	}

	/** Set SI Number.
		@param SINumber SI Number	  */
	public void setSINumber (String SINumber)
	{
		set_Value (COLUMNNAME_SINumber, SINumber);
	}

	/** Get SI Number.
		@return SI Number	  */
	public String getSINumber () 
	{
		return (String)get_Value(COLUMNNAME_SINumber);
	}

	public I_HBC_PortPosition getTo_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getTo_PortPosition_ID(), get_TrxName());	}

	/** Set Port/Position To.
		@param To_PortPosition_ID Port/Position To	  */
	public void setTo_PortPosition_ID (int To_PortPosition_ID)
	{
		if (To_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_To_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_To_PortPosition_ID, Integer.valueOf(To_PortPosition_ID));
	}

	/** Get Port/Position To.
		@return Port/Position To	  */
	public int getTo_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unit Price.
		@param UnitPrice Unit Price	  */
	public void setUnitPrice (BigDecimal UnitPrice)
	{
		set_Value (COLUMNNAME_UnitPrice, UnitPrice);
	}

	/** Get Unit Price.
		@return Unit Price	  */
	public BigDecimal getUnitPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnitPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BPartner getUnLoadingAgent_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getUnLoadingAgent_BPartner_ID(), get_TrxName());	}

	/** Set Unloading Agent.
		@param UnLoadingAgent_BPartner_ID Unloading Agent	  */
	public void setUnLoadingAgent_BPartner_ID (int UnLoadingAgent_BPartner_ID)
	{
		if (UnLoadingAgent_BPartner_ID < 1) 
			set_Value (COLUMNNAME_UnLoadingAgent_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_UnLoadingAgent_BPartner_ID, Integer.valueOf(UnLoadingAgent_BPartner_ID));
	}

	/** Get Unloading Agent.
		@return Unloading Agent	  */
	public int getUnLoadingAgent_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnLoadingAgent_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}