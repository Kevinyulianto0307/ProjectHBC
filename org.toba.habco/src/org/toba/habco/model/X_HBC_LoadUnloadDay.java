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

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_LoadUnloadDay
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_LoadUnloadDay extends PO implements I_HBC_LoadUnloadDay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_LoadUnloadDay (Properties ctx, int HBC_LoadUnloadDay_ID, String trxName)
    {
      super (ctx, HBC_LoadUnloadDay_ID, trxName);
      /** if (HBC_LoadUnloadDay_ID == 0)
        {
			setHBC_LoadUnloadDay_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_LoadUnloadDay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_LoadUnloadDay[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Active From.
		@param ActiveFrom Active From	  */
	public void setActiveFrom (Timestamp ActiveFrom)
	{
		set_Value (COLUMNNAME_ActiveFrom, ActiveFrom);
	}

	/** Get Active From.
		@return Active From	  */
	public Timestamp getActiveFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActiveFrom);
	}

	/** Set Active To.
		@param ActiveTo Active To	  */
	public void setActiveTo (Timestamp ActiveTo)
	{
		set_Value (COLUMNNAME_ActiveTo, ActiveTo);
	}

	/** Get Active To.
		@return Active To	  */
	public Timestamp getActiveTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActiveTo);
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

	public I_HBC_BargeCategory getHBC_BargeCategory() throws RuntimeException
    {
		return (I_HBC_BargeCategory)MTable.get(getCtx(), I_HBC_BargeCategory.Table_Name)
			.getPO(getHBC_BargeCategory_ID(), get_TrxName());	}

	/** Set Barge Category.
		@param HBC_BargeCategory_ID Barge Category	  */
	public void setHBC_BargeCategory_ID (int HBC_BargeCategory_ID)
	{
		if (HBC_BargeCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_BargeCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_BargeCategory_ID, Integer.valueOf(HBC_BargeCategory_ID));
	}

	/** Get Barge Category.
		@return Barge Category	  */
	public int getHBC_BargeCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_BargeCategory_ID);
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

	/** Set Loading / Unloading Day.
		@param HBC_LoadUnloadDay_ID Loading / Unloading Day	  */
	public void setHBC_LoadUnloadDay_ID (int HBC_LoadUnloadDay_ID)
	{
		if (HBC_LoadUnloadDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_LoadUnloadDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_LoadUnloadDay_ID, Integer.valueOf(HBC_LoadUnloadDay_ID));
	}

	/** Get Loading / Unloading Day.
		@return Loading / Unloading Day	  */
	public int getHBC_LoadUnloadDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_LoadUnloadDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_LoadUnloadDay_ID()));
    }

	/** Set HBC_LoadUnloadDay_UU.
		@param HBC_LoadUnloadDay_UU HBC_LoadUnloadDay_UU	  */
	public void setHBC_LoadUnloadDay_UU (String HBC_LoadUnloadDay_UU)
	{
		set_Value (COLUMNNAME_HBC_LoadUnloadDay_UU, HBC_LoadUnloadDay_UU);
	}

	/** Get HBC_LoadUnloadDay_UU.
		@return HBC_LoadUnloadDay_UU	  */
	public String getHBC_LoadUnloadDay_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_LoadUnloadDay_UU);
	}

	/** Set Prorate.
		@param IsProrate Prorate	  */
	public void setIsProrate (boolean IsProrate)
	{
		set_Value (COLUMNNAME_IsProrate, Boolean.valueOf(IsProrate));
	}

	/** Get Prorate.
		@return Prorate	  */
	public boolean isProrate () 
	{
		Object oo = get_Value(COLUMNNAME_IsProrate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Prorate Day.
		@param ProrateDay Prorate Day	  */
	public void setProrateDay (int ProrateDay)
	{
		set_Value (COLUMNNAME_ProrateDay, Integer.valueOf(ProrateDay));
	}

	/** Get Prorate Day.
		@return Prorate Day	  */
	public int getProrateDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProrateDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Loading Day.
		@param TotalLoadingDay Total Loading Day	  */
	public void setTotalLoadingDay (int TotalLoadingDay)
	{
		set_Value (COLUMNNAME_TotalLoadingDay, Integer.valueOf(TotalLoadingDay));
	}

	/** Get Total Loading Day.
		@return Total Loading Day	  */
	public int getTotalLoadingDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalLoadingDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Unloading Day.
		@param TotalUnloadingDay Total Unloading Day	  */
	public void setTotalUnloadingDay (int TotalUnloadingDay)
	{
		set_Value (COLUMNNAME_TotalUnloadingDay, Integer.valueOf(TotalUnloadingDay));
	}

	/** Get Total Unloading Day.
		@return Total Unloading Day	  */
	public int getTotalUnloadingDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalUnloadingDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}