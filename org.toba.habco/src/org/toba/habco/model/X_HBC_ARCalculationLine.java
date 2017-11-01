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

/** Generated Model for HBC_ARCalculationLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ARCalculationLine extends PO implements I_HBC_ARCalculationLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161206L;

    /** Standard Constructor */
    public X_HBC_ARCalculationLine (Properties ctx, int HBC_ARCalculationLine_ID, String trxName)
    {
      super (ctx, HBC_ARCalculationLine_ID, trxName);
      /** if (HBC_ARCalculationLine_ID == 0)
        {
			setHBC_ARCalculationLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ARCalculationLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ARCalculationLine[")
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

	/** Set Amount Charge.
		@param AmountCharge Amount Charge	  */
	public void setAmountCharge (BigDecimal AmountCharge)
	{
		set_Value (COLUMNNAME_AmountCharge, AmountCharge);
	}

	/** Get Amount Charge.
		@return Amount Charge	  */
	public BigDecimal getAmountCharge () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountCharge);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** SPAL = SPAL */
	public static final String ARCALCULATIONTYPE_SPAL = "SPAL";
	/** Annual = ANN */
	public static final String ARCALCULATIONTYPE_Annual = "ANN";
	/** Time Charter = TMC */
	public static final String ARCALCULATIONTYPE_TimeCharter = "TMC";
	/** Demurrage = DMG */
	public static final String ARCALCULATIONTYPE_Demurrage = "DMG";
	/** Set AR Calculation Type.
		@param ARCalculationType AR Calculation Type	  */
	public void setARCalculationType (String ARCalculationType)
	{

		set_Value (COLUMNNAME_ARCalculationType, ARCalculationType);
	}

	/** Get AR Calculation Type.
		@return AR Calculation Type	  */
	public String getARCalculationType () 
	{
		return (String)get_Value(COLUMNNAME_ARCalculationType);
	}

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
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

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
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

	public I_C_ProjectMilestone getC_ProjectMilestone() throws RuntimeException
    {
		return (I_C_ProjectMilestone)MTable.get(getCtx(), I_C_ProjectMilestone.Table_Name)
			.getPO(getC_ProjectMilestone_ID(), get_TrxName());	}

	/** Set Project Milestone.
		@param C_ProjectMilestone_ID Project Milestone	  */
	public void setC_ProjectMilestone_ID (int C_ProjectMilestone_ID)
	{
		if (C_ProjectMilestone_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectMilestone_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectMilestone_ID, Integer.valueOf(C_ProjectMilestone_ID));
	}

	/** Get Project Milestone.
		@return Project Milestone	  */
	public int getC_ProjectMilestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectMilestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Day Charge.
		@param DayCharge Day Charge	  */
	public void setDayCharge (BigDecimal DayCharge)
	{
		set_Value (COLUMNNAME_DayCharge, DayCharge);
	}

	/** Get Day Charge.
		@return Day Charge	  */
	public BigDecimal getDayCharge () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayCharge);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Day Charge Calculation.
		@param DayChargeCalculation Day Charge Calculation	  */
	public void setDayChargeCalculation (BigDecimal DayChargeCalculation)
	{
		set_Value (COLUMNNAME_DayChargeCalculation, DayChargeCalculation);
	}

	/** Get Day Charge Calculation.
		@return Day Charge Calculation	  */
	public BigDecimal getDayChargeCalculation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayChargeCalculation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Day Invoice Percentage.
		@param DayInvoicePercentage Day Invoice Percentage	  */
	public void setDayInvoicePercentage (BigDecimal DayInvoicePercentage)
	{
		set_Value (COLUMNNAME_DayInvoicePercentage, DayInvoicePercentage);
	}

	/** Get Day Invoice Percentage.
		@return Day Invoice Percentage	  */
	public BigDecimal getDayInvoicePercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayInvoicePercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Day To Invoice.
		@param DayToInvoice Day To Invoice	  */
	public void setDayToInvoice (BigDecimal DayToInvoice)
	{
		set_Value (COLUMNNAME_DayToInvoice, DayToInvoice);
	}

	/** Get Day To Invoice.
		@return Day To Invoice	  */
	public BigDecimal getDayToInvoice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayToInvoice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_HBC_ARCalculation getHBC_ARCalculation() throws RuntimeException
    {
		return (I_HBC_ARCalculation)MTable.get(getCtx(), I_HBC_ARCalculation.Table_Name)
			.getPO(getHBC_ARCalculation_ID(), get_TrxName());	}

	/** Set AR Calculation.
		@param HBC_ARCalculation_ID AR Calculation	  */
	public void setHBC_ARCalculation_ID (int HBC_ARCalculation_ID)
	{
		if (HBC_ARCalculation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ARCalculation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ARCalculation_ID, Integer.valueOf(HBC_ARCalculation_ID));
	}

	/** Get AR Calculation.
		@return AR Calculation	  */
	public int getHBC_ARCalculation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ARCalculation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_ARCalculationLine.
		@param HBC_ARCalculationLine_ID HBC_ARCalculationLine	  */
	public void setHBC_ARCalculationLine_ID (int HBC_ARCalculationLine_ID)
	{
		if (HBC_ARCalculationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ARCalculationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ARCalculationLine_ID, Integer.valueOf(HBC_ARCalculationLine_ID));
	}

	/** Get HBC_ARCalculationLine.
		@return HBC_ARCalculationLine	  */
	public int getHBC_ARCalculationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ARCalculationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ARCalculationLine_ID()));
    }

	/** Set HBC_ARCalculationLine_UU.
		@param HBC_ARCalculationLine_UU HBC_ARCalculationLine_UU	  */
	public void setHBC_ARCalculationLine_UU (String HBC_ARCalculationLine_UU)
	{
		set_Value (COLUMNNAME_HBC_ARCalculationLine_UU, HBC_ARCalculationLine_UU);
	}

	/** Get HBC_ARCalculationLine_UU.
		@return HBC_ARCalculationLine_UU	  */
	public String getHBC_ARCalculationLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ARCalculationLine_UU);
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

	public I_HBC_Contract getHBC_Contract() throws RuntimeException
    {
		return (I_HBC_Contract)MTable.get(getCtx(), I_HBC_Contract.Table_Name)
			.getPO(getHBC_Contract_ID(), get_TrxName());	}

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

	public I_HBC_Trip getHBC_Trip() throws RuntimeException
    {
		return (I_HBC_Trip)MTable.get(getCtx(), I_HBC_Trip.Table_Name)
			.getPO(getHBC_Trip_ID(), get_TrxName());	}

	/** Set Trip.
		@param HBC_Trip_ID Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID)
	{
		if (HBC_Trip_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Trip_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Trip_ID, Integer.valueOf(HBC_Trip_ID));
	}

	/** Get Trip.
		@return Trip	  */
	public int getHBC_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Trip_ID);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
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

	/** Set Milestone Percentage.
		@param MilestonePercentage Milestone Percentage	  */
	public void setMilestonePercentage (BigDecimal MilestonePercentage)
	{
		set_Value (COLUMNNAME_MilestonePercentage, MilestonePercentage);
	}

	/** Get Milestone Percentage.
		@return Milestone Percentage	  */
	public BigDecimal getMilestonePercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MilestonePercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Qty Charge.
		@param QtyCharge 
		Quantity Charge
	  */
	public void setQtyCharge (BigDecimal QtyCharge)
	{
		set_Value (COLUMNNAME_QtyCharge, QtyCharge);
	}

	/** Get Qty Charge.
		@return Quantity Charge
	  */
	public BigDecimal getQtyCharge () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCharge);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty To Invoiced.
		@param QtyToInvoiced Qty To Invoiced	  */
	public void setQtyToInvoiced (BigDecimal QtyToInvoiced)
	{
		set_Value (COLUMNNAME_QtyToInvoiced, QtyToInvoiced);
	}

	/** Get Qty To Invoiced.
		@return Qty To Invoiced	  */
	public BigDecimal getQtyToInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoiced);
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

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
}