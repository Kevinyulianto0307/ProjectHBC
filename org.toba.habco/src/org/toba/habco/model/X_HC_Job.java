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

/** Generated Model for HC_Job
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_Job extends PO implements I_HC_Job, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HC_Job (Properties ctx, int HC_Job_ID, String trxName)
    {
      super (ctx, HC_Job_ID, trxName);
      /** if (HC_Job_ID == 0)
        {
			setHC_Job_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_Job (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HC_Job[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_HC_EmployeeGrade getHC_GradeMax() throws RuntimeException
    {
		return (I_HC_EmployeeGrade)MTable.get(getCtx(), I_HC_EmployeeGrade.Table_Name)
			.getPO(getHC_GradeMax_ID(), get_TrxName());	}

	/** Set Grade Max.
		@param HC_GradeMax_ID Grade Max	  */
	public void setHC_GradeMax_ID (int HC_GradeMax_ID)
	{
		if (HC_GradeMax_ID < 1) 
			set_Value (COLUMNNAME_HC_GradeMax_ID, null);
		else 
			set_Value (COLUMNNAME_HC_GradeMax_ID, Integer.valueOf(HC_GradeMax_ID));
	}

	/** Get Grade Max.
		@return Grade Max	  */
	public int getHC_GradeMax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_GradeMax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_EmployeeGrade getHC_GradeMid() throws RuntimeException
    {
		return (I_HC_EmployeeGrade)MTable.get(getCtx(), I_HC_EmployeeGrade.Table_Name)
			.getPO(getHC_GradeMid_ID(), get_TrxName());	}

	/** Set Grade Mid.
		@param HC_GradeMid_ID Grade Mid	  */
	public void setHC_GradeMid_ID (int HC_GradeMid_ID)
	{
		if (HC_GradeMid_ID < 1) 
			set_Value (COLUMNNAME_HC_GradeMid_ID, null);
		else 
			set_Value (COLUMNNAME_HC_GradeMid_ID, Integer.valueOf(HC_GradeMid_ID));
	}

	/** Get Grade Mid.
		@return Grade Mid	  */
	public int getHC_GradeMid_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_GradeMid_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_EmployeeGrade getHC_GradeMin() throws RuntimeException
    {
		return (I_HC_EmployeeGrade)MTable.get(getCtx(), I_HC_EmployeeGrade.Table_Name)
			.getPO(getHC_GradeMin_ID(), get_TrxName());	}

	/** Set Grade Min.
		@param HC_GradeMin_ID Grade Min	  */
	public void setHC_GradeMin_ID (int HC_GradeMin_ID)
	{
		if (HC_GradeMin_ID < 1) 
			set_Value (COLUMNNAME_HC_GradeMin_ID, null);
		else 
			set_Value (COLUMNNAME_HC_GradeMin_ID, Integer.valueOf(HC_GradeMin_ID));
	}

	/** Get Grade Min.
		@return Grade Min	  */
	public int getHC_GradeMin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_GradeMin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_JobFamily getHC_JobFamily() throws RuntimeException
    {
		return (I_HC_JobFamily)MTable.get(getCtx(), I_HC_JobFamily.Table_Name)
			.getPO(getHC_JobFamily_ID(), get_TrxName());	}

	/** Set Job Family.
		@param HC_JobFamily_ID Job Family	  */
	public void setHC_JobFamily_ID (int HC_JobFamily_ID)
	{
		if (HC_JobFamily_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_JobFamily_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_JobFamily_ID, Integer.valueOf(HC_JobFamily_ID));
	}

	/** Get Job Family.
		@return Job Family	  */
	public int getHC_JobFamily_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_JobFamily_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_JobFunction getHC_JobFunction() throws RuntimeException
    {
		return (I_HC_JobFunction)MTable.get(getCtx(), I_HC_JobFunction.Table_Name)
			.getPO(getHC_JobFunction_ID(), get_TrxName());	}

	/** Set Job Function.
		@param HC_JobFunction_ID Job Function	  */
	public void setHC_JobFunction_ID (int HC_JobFunction_ID)
	{
		if (HC_JobFunction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_JobFunction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_JobFunction_ID, Integer.valueOf(HC_JobFunction_ID));
	}

	/** Get Job Function.
		@return Job Function	  */
	public int getHC_JobFunction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_JobFunction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Job.
		@param HC_Job_ID Job	  */
	public void setHC_Job_ID (int HC_Job_ID)
	{
		if (HC_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Job_ID, Integer.valueOf(HC_Job_ID));
	}

	/** Get Job.
		@return Job	  */
	public int getHC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_Job_ID()));
    }

	/** HC_JobLevel AD_Reference_ID=300053 */
	public static final int HC_JOBLEVEL_AD_Reference_ID=300053;
	/** Director = DIR */
	public static final String HC_JOBLEVEL_Director = "DIR";
	/** Division = DIV */
	public static final String HC_JOBLEVEL_Division = "DIV";
	/** Department = DEP */
	public static final String HC_JOBLEVEL_Department = "DEP";
	/** Section = SEC */
	public static final String HC_JOBLEVEL_Section = "SEC";
	/** SubUnit = SUN */
	public static final String HC_JOBLEVEL_SubUnit = "SUN";
	/** Unit = UNT */
	public static final String HC_JOBLEVEL_Unit = "UNT";
	/** Staff = STF */
	public static final String HC_JOBLEVEL_Staff = "STF";
	/** Set HC Job Level.
		@param HC_JobLevel HC Job Level	  */
	public void setHC_JobLevel (String HC_JobLevel)
	{

		set_Value (COLUMNNAME_HC_JobLevel, HC_JobLevel);
	}

	/** Get HC Job Level.
		@return HC Job Level	  */
	public String getHC_JobLevel () 
	{
		return (String)get_Value(COLUMNNAME_HC_JobLevel);
	}

	/** Set HC_Job_UU.
		@param HC_Job_UU HC_Job_UU	  */
	public void setHC_Job_UU (String HC_Job_UU)
	{
		set_Value (COLUMNNAME_HC_Job_UU, HC_Job_UU);
	}

	/** Get HC_Job_UU.
		@return HC_Job_UU	  */
	public String getHC_Job_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_Job_UU);
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