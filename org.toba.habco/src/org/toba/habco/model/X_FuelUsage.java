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

/** Generated Model for FuelUsage
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_FuelUsage extends PO implements I_FuelUsage, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161230L;

    /** Standard Constructor */
    public X_FuelUsage (Properties ctx, int FuelUsage_ID, String trxName)
    {
      super (ctx, FuelUsage_ID, trxName);
      /** if (FuelUsage_ID == 0)
        {
			setFuelUsage_ID (0);
        } */
    }

    /** Load Constructor */
    public X_FuelUsage (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_FuelUsage[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Fuel Estimation.
		@param FuelEstimation Fuel Estimation	  */
	public void setFuelEstimation (BigDecimal FuelEstimation)
	{
		set_Value (COLUMNNAME_FuelEstimation, FuelEstimation);
	}

	/** Get Fuel Estimation.
		@return Fuel Estimation	  */
	public BigDecimal getFuelEstimation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelEstimation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fuel Ship Balance.
		@param FuelShipBalance Fuel Ship Balance	  */
	public void setFuelShipBalance (BigDecimal FuelShipBalance)
	{
		set_Value (COLUMNNAME_FuelShipBalance, FuelShipBalance);
	}

	/** Get Fuel Ship Balance.
		@return Fuel Ship Balance	  */
	public BigDecimal getFuelShipBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelShipBalance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fuel Usage.
		@param FuelUsage_ID Fuel Usage	  */
	public void setFuelUsage_ID (int FuelUsage_ID)
	{
		if (FuelUsage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_FuelUsage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_FuelUsage_ID, Integer.valueOf(FuelUsage_ID));
	}

	/** Get Fuel Usage.
		@return Fuel Usage	  */
	public int getFuelUsage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FuelUsage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getFuelUsage_ID()));
    }

	/** Set FuelUsage_UU.
		@param FuelUsage_UU FuelUsage_UU	  */
	public void setFuelUsage_UU (String FuelUsage_UU)
	{
		set_Value (COLUMNNAME_FuelUsage_UU, FuelUsage_UU);
	}

	/** Get FuelUsage_UU.
		@return FuelUsage_UU	  */
	public String getFuelUsage_UU () 
	{
		return (String)get_Value(COLUMNNAME_FuelUsage_UU);
	}

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
	
	/** Set Trip.
	@param HBC_Trip_ID Trip	  */
	public void setTCS_BBMPlan_ID (int TCS_BBMPlan_ID)
	{
		if (TCS_BBMPlan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_TCS_BBMPlan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_TCS_BBMPlan_ID, Integer.valueOf(TCS_BBMPlan_ID));
	}
	
	/** Get BBMPlan.
	@return BBMPlan	  */
	public int getTCS_BBMPlan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TCS_BBMPlan_ID);
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

	/** Set Usage Diff.
		@param UsageDiff Usage Diff	  */
	public void setUsageDiff (BigDecimal UsageDiff)
	{
		set_Value (COLUMNNAME_UsageDiff, UsageDiff);
	}

	/** Get Usage Diff.
		@return Usage Diff	  */
	public BigDecimal getUsageDiff () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UsageDiff);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}