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

/** Generated Model for HC_Position
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_Position extends PO implements I_HC_Position, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HC_Position (Properties ctx, int HC_Position_ID, String trxName)
    {
      super (ctx, HC_Position_ID, trxName);
      /** if (HC_Position_ID == 0)
        {
			setHC_Position_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_Position (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HC_Position[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_HC_Job getHC_Job() throws RuntimeException
    {
		return (I_HC_Job)MTable.get(getCtx(), I_HC_Job.Table_Name)
			.getPO(getHC_Job_ID(), get_TrxName());	}

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

	/** Set Position.
		@param HC_Position_ID Position	  */
	public void setHC_Position_ID (int HC_Position_ID)
	{
		if (HC_Position_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Position_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Position_ID, Integer.valueOf(HC_Position_ID));
	}

	/** Get Position.
		@return Position	  */
	public int getHC_Position_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Position_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_Position_ID()));
    }

	public I_HC_Position getHC_PositionReportTo() throws RuntimeException
    {
		return (I_HC_Position)MTable.get(getCtx(), I_HC_Position.Table_Name)
			.getPO(getHC_PositionReportTo_ID(), get_TrxName());	}

	/** Set Position Reports To.
		@param HC_PositionReportTo_ID Position Reports To	  */
	public void setHC_PositionReportTo_ID (int HC_PositionReportTo_ID)
	{
		if (HC_PositionReportTo_ID < 1) 
			set_Value (COLUMNNAME_HC_PositionReportTo_ID, null);
		else 
			set_Value (COLUMNNAME_HC_PositionReportTo_ID, Integer.valueOf(HC_PositionReportTo_ID));
	}

	/** Get Position Reports To.
		@return Position Reports To	  */
	public int getHC_PositionReportTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_PositionReportTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HC_Position_UU.
		@param HC_Position_UU HC_Position_UU	  */
	public void setHC_Position_UU (String HC_Position_UU)
	{
		set_Value (COLUMNNAME_HC_Position_UU, HC_Position_UU);
	}

	/** Get HC_Position_UU.
		@return HC_Position_UU	  */
	public String getHC_Position_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_Position_UU);
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

	/** Set Title.
		@param Title 
		Name this entity is referred to as
	  */
	public void setTitle (String Title)
	{
		set_ValueNoCheck (COLUMNNAME_Title, Title);
	}

	/** Get Title.
		@return Name this entity is referred to as
	  */
	public String getTitle () 
	{
		return (String)get_Value(COLUMNNAME_Title);
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