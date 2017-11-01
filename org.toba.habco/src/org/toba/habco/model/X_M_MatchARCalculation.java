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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_MatchARCalculation
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_M_MatchARCalculation extends PO implements I_M_MatchARCalculation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161110L;

    /** Standard Constructor */
    public X_M_MatchARCalculation (Properties ctx, int M_MatchARCalculation_ID, String trxName)
    {
      super (ctx, M_MatchARCalculation_ID, trxName);
      /** if (M_MatchARCalculation_ID == 0)
        {
			setM_MatchARCalculation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_MatchARCalculation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_MatchARCalculation[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_HBC_ARCalculationLine getHBC_ARCalculationLine() throws RuntimeException
    {
		return (I_HBC_ARCalculationLine)MTable.get(getCtx(), I_HBC_ARCalculationLine.Table_Name)
			.getPO(getHBC_ARCalculationLine_ID(), get_TrxName());	}

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

	/** Set Match AR Calculation.
		@param M_MatchARCalculation_ID Match AR Calculation	  */
	public void setM_MatchARCalculation_ID (int M_MatchARCalculation_ID)
	{
		if (M_MatchARCalculation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MatchARCalculation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MatchARCalculation_ID, Integer.valueOf(M_MatchARCalculation_ID));
	}

	/** Get Match AR Calculation.
		@return Match AR Calculation	  */
	public int getM_MatchARCalculation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MatchARCalculation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_MatchARCalculation_ID()));
    }

	/** Set M_MatchARCalculation_UU.
		@param M_MatchARCalculation_UU M_MatchARCalculation_UU	  */
	public void setM_MatchARCalculation_UU (String M_MatchARCalculation_UU)
	{
		set_Value (COLUMNNAME_M_MatchARCalculation_UU, M_MatchARCalculation_UU);
	}

	/** Get M_MatchARCalculation_UU.
		@return M_MatchARCalculation_UU	  */
	public String getM_MatchARCalculation_UU () 
	{
		return (String)get_Value(COLUMNNAME_M_MatchARCalculation_UU);
	}
}