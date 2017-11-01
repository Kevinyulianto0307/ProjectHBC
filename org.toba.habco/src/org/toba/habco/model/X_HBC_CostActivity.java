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
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_CostActivity
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_CostActivity extends PO implements I_HBC_CostActivity, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161121L;

    /** Standard Constructor */
    public X_HBC_CostActivity (Properties ctx, int HBC_CostActivity_ID, String trxName)
    {
      super (ctx, HBC_CostActivity_ID, trxName);
      /** if (HBC_CostActivity_ID == 0)
        {
			setHBC_CostActivity_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_CostActivity (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_CostActivity[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set Allocation Number.
		@param AllocationCode Allocation Number	  */
	public void setAllocationCode (int AllocationCode)
	{
		set_Value (COLUMNNAME_AllocationCode, Integer.valueOf(AllocationCode));
	}

	/** Get Allocation Number.
		@return Allocation Number	  */
	public int getAllocationCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AllocationCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (org.compiere.model.I_C_Project)MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Day.
		@param Day Day	  */
	public void setDay (BigDecimal Day)
	{
		set_Value (COLUMNNAME_Day, Day);
	}

	/** Get Day.
		@return Day	  */
	public BigDecimal getDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Reference Code.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Reference Code.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	public I_HBC_LegalizedLine getEndorse_Activity() throws RuntimeException
    {
		return (I_HBC_LegalizedLine)MTable.get(getCtx(), I_HBC_LegalizedLine.Table_Name)
			.getPO(getEndorse_Activity_ID(), get_TrxName());	}

	/** Set Endorse Activity.
		@param Endorse_Activity_ID Endorse Activity	  */
	public void setEndorse_Activity_ID (int Endorse_Activity_ID)
	{
		if (Endorse_Activity_ID < 1) 
			set_Value (COLUMNNAME_Endorse_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_Endorse_Activity_ID, Integer.valueOf(Endorse_Activity_ID));
	}

	/** Get Endorse Activity.
		@return Endorse Activity	  */
	public int getEndorse_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Endorse_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Finish Date.
		@param FinishDate Finish Date	  */
	public void setFinishDate (Timestamp FinishDate)
	{
		set_Value (COLUMNNAME_FinishDate, FinishDate);
	}

	/** Get Finish Date.
		@return Finish Date	  */
	public Timestamp getFinishDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FinishDate);
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
			set_ValueNoCheck (COLUMNNAME_HBC_Barge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Barge_ID, Integer.valueOf(HBC_Barge_ID));
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

	/** Set HBC_CostActivity.
		@param HBC_CostActivity_ID HBC_CostActivity	  */
	public void setHBC_CostActivity_ID (int HBC_CostActivity_ID)
	{
		if (HBC_CostActivity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_CostActivity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_CostActivity_ID, Integer.valueOf(HBC_CostActivity_ID));
	}

	/** Get HBC_CostActivity.
		@return HBC_CostActivity	  */
	public int getHBC_CostActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_CostActivity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_CostActivity_ID()));
    }

	/** Set HBC_CostActivity_UU.
		@param HBC_CostActivity_UU HBC_CostActivity_UU	  */
	public void setHBC_CostActivity_UU (String HBC_CostActivity_UU)
	{
		set_Value (COLUMNNAME_HBC_CostActivity_UU, HBC_CostActivity_UU);
	}

	/** Get HBC_CostActivity_UU.
		@return HBC_CostActivity_UU	  */
	public String getHBC_CostActivity_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_CostActivity_UU);
	}

	public I_HBC_CrewOnOff getHBC_CrewOnOff() throws RuntimeException
    {
		return (I_HBC_CrewOnOff)MTable.get(getCtx(), I_HBC_CrewOnOff.Table_Name)
			.getPO(getHBC_CrewOnOff_ID(), get_TrxName());	}

	/** Set Crew On/Off.
		@param HBC_CrewOnOff_ID Crew On/Off	  */
	public void setHBC_CrewOnOff_ID (int HBC_CrewOnOff_ID)
	{
		if (HBC_CrewOnOff_ID < 1) 
			set_Value (COLUMNNAME_HBC_CrewOnOff_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_CrewOnOff_ID, Integer.valueOf(HBC_CrewOnOff_ID));
	}

	/** Get Crew On/Off.
		@return Crew On/Off	  */
	public int getHBC_CrewOnOff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_CrewOnOff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_LegalizedLine.
		@param HBC_LegalizedLine_ID HBC_LegalizedLine	  */
	public void setHBC_LegalizedLine_ID (int HBC_LegalizedLine_ID)
	{
		if (HBC_LegalizedLine_ID < 1) 
			set_Value (COLUMNNAME_HBC_LegalizedLine_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_LegalizedLine_ID, Integer.valueOf(HBC_LegalizedLine_ID));
	}

	/** Get HBC_LegalizedLine.
		@return HBC_LegalizedLine	  */
	public int getHBC_LegalizedLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_LegalizedLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Position getHBC_Position() throws RuntimeException
    {
		return (I_HBC_Position)MTable.get(getCtx(), I_HBC_Position.Table_Name)
			.getPO(getHBC_Position_ID(), get_TrxName());	}

	/** Set Ship Position.
		@param HBC_Position_ID Ship Position	  */
	public void setHBC_Position_ID (int HBC_Position_ID)
	{
		if (HBC_Position_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Position_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Position_ID, Integer.valueOf(HBC_Position_ID));
	}

	/** Get Ship Position.
		@return Ship Position	  */
	public int getHBC_Position_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Position_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_ShipActivity getHBC_ShipActivity() throws RuntimeException
    {
		return (I_HBC_ShipActivity)MTable.get(getCtx(), I_HBC_ShipActivity.Table_Name)
			.getPO(getHBC_ShipActivity_ID(), get_TrxName());	}

	/** Set Ship Activity.
		@param HBC_ShipActivity_ID Ship Activity	  */
	public void setHBC_ShipActivity_ID (int HBC_ShipActivity_ID)
	{
		if (HBC_ShipActivity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipActivity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipActivity_ID, Integer.valueOf(HBC_ShipActivity_ID));
	}

	/** Get Ship Activity.
		@return Ship Activity	  */
	public int getHBC_ShipActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipActivity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_ShipDocumentLine getHBC_ShipDocumentLine() throws RuntimeException
    {
		return (I_HBC_ShipDocumentLine)MTable.get(getCtx(), I_HBC_ShipDocumentLine.Table_Name)
			.getPO(getHBC_ShipDocumentLine_ID(), get_TrxName());	}

	/** Set Ship Document Line.
		@param HBC_ShipDocumentLine_ID Ship Document Line	  */
	public void setHBC_ShipDocumentLine_ID (int HBC_ShipDocumentLine_ID)
	{
		if (HBC_ShipDocumentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentLine_ID, Integer.valueOf(HBC_ShipDocumentLine_ID));
	}

	/** Get Ship Document Line.
		@return Ship Document Line	  */
	public int getHBC_ShipDocumentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocumentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_HBC_Tugboat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Tugboat_ID, Integer.valueOf(HBC_Tugboat_ID));
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

	/** Set Hour.
		@param Hour Hour	  */
	public void setHour (BigDecimal Hour)
	{
		set_Value (COLUMNNAME_Hour, Hour);
	}

	/** Get Hour.
		@return Hour	  */
	public BigDecimal getHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Hour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Agent Invoice.
		@param IsAgentInvoices Agent Invoice	  */
	public void setIsAgentInvoices (boolean IsAgentInvoices)
	{
		set_Value (COLUMNNAME_IsAgentInvoices, Boolean.valueOf(IsAgentInvoices));
	}

	/** Get Agent Invoice.
		@return Agent Invoice	  */
	public boolean isAgentInvoices () 
	{
		Object oo = get_Value(COLUMNNAME_IsAgentInvoices);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allocate.
		@param IsAllocates Allocate	  */
	public void setIsAllocates (boolean IsAllocates)
	{
		set_Value (COLUMNNAME_IsAllocates, Boolean.valueOf(IsAllocates));
	}

	/** Get Allocate.
		@return Allocate	  */
	public boolean isAllocates () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllocates);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Charge To Employee.
		@param IsChargeEmployee Charge To Employee	  */
	public void setIsChargeEmployee (boolean IsChargeEmployee)
	{
		set_Value (COLUMNNAME_IsChargeEmployee, Boolean.valueOf(IsChargeEmployee));
	}

	/** Get Charge To Employee.
		@return Charge To Employee	  */
	public boolean isChargeEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsChargeEmployee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoice AR.
		@param IsInvoicedAR Invoice AR	  */
	public void setIsInvoicedAR (boolean IsInvoicedAR)
	{
		set_Value (COLUMNNAME_IsInvoicedAR, Boolean.valueOf(IsInvoicedAR));
	}

	/** Get Invoice AR.
		@return Invoice AR	  */
	public boolean isInvoicedAR () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoicedAR);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Legalized Activity.
		@param Legalized_activity Legalized Activity	  */
	public void setLegalized_activity (int Legalized_activity)
	{
		set_Value (COLUMNNAME_Legalized_activity, Integer.valueOf(Legalized_activity));
	}

	/** Get Legalized Activity.
		@return Legalized Activity	  */
	public int getLegalized_activity () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Legalized_activity);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product_Version getM_Product_Version() throws RuntimeException
    {
		return (I_M_Product_Version)MTable.get(getCtx(), I_M_Product_Version.Table_Name)
			.getPO(getM_Product_Version_ID(), get_TrxName());	}

	/** Set M_Product_Version.
		@param M_Product_Version_ID M_Product_Version	  */
	public void setM_Product_Version_ID (int M_Product_Version_ID)
	{
		if (M_Product_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Version_ID, Integer.valueOf(M_Product_Version_ID));
	}

	/** Get M_Product_Version.
		@return M_Product_Version	  */
	public int getM_Product_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Date.
		@param StartDate 
		The Start Date indicates the first or starting date
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return The Start Date indicates the first or starting date
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Total Cargo Qty.
		@param TotalCargoQty Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty)
	{
		set_Value (COLUMNNAME_TotalCargoQty, TotalCargoQty);
	}

	/** Get Total Cargo Qty.
		@return Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalCargoQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}