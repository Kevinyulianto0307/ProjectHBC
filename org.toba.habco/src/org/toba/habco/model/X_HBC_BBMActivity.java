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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_BBMActivity
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_BBMActivity extends PO implements I_HBC_BBMActivity, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161230L;

    /** Standard Constructor */
    public X_HBC_BBMActivity (Properties ctx, int HBC_BBMActivity_ID, String trxName)
    {
      super (ctx, HBC_BBMActivity_ID, trxName);
      /** if (HBC_BBMActivity_ID == 0)
        {
			setHBC_BBMActivity_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_BBMActivity (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_BBMActivity[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Fuel Consumption Per Hour.
		@param FuelConsumptionPerHour Fuel Consumption Per Hour	  */
	public void setFuelConsumptionPerHour (BigDecimal FuelConsumptionPerHour)
	{
		set_Value (COLUMNNAME_FuelConsumptionPerHour, FuelConsumptionPerHour);
	}

	/** Get Fuel Consumption Per Hour.
		@return Fuel Consumption Per Hour	  */
	public BigDecimal getFuelConsumptionPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelConsumptionPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HBC_BBMActivity.
		@param HBC_BBMActivity_ID HBC_BBMActivity	  */
	public void setHBC_BBMActivity_ID (int HBC_BBMActivity_ID)
	{
		if (HBC_BBMActivity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_BBMActivity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_BBMActivity_ID, Integer.valueOf(HBC_BBMActivity_ID));
	}

	/** Get HBC_BBMActivity.
		@return HBC_BBMActivity	  */
	public int getHBC_BBMActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_BBMActivity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_BBMActivity_ID()));
    }

	/** Set HBC_BBMActivity_UU.
		@param HBC_BBMActivity_UU HBC_BBMActivity_UU	  */
	public void setHBC_BBMActivity_UU (String HBC_BBMActivity_UU)
	{
		set_Value (COLUMNNAME_HBC_BBMActivity_UU, HBC_BBMActivity_UU);
	}

	/** Get HBC_BBMActivity_UU.
		@return HBC_BBMActivity_UU	  */
	public String getHBC_BBMActivity_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_BBMActivity_UU);
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

	/** Set Processed.
		@param IsProcess Processed	  */
	public void setIsProcess (boolean IsProcess)
	{
		set_Value (COLUMNNAME_IsProcess, Boolean.valueOf(IsProcess));
	}

	/** Get Processed.
		@return Processed	  */
	public boolean isProcess () 
	{
		Object oo = get_Value(COLUMNNAME_IsProcess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Total Liters.
		@param TotalLiters Total Liters	  */
	public void setTotalLiters (BigDecimal TotalLiters)
	{
		set_Value (COLUMNNAME_TotalLiters, TotalLiters);
	}

	/** Get Total Liters.
		@return Total Liters	  */
	public BigDecimal getTotalLiters () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLiters);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}