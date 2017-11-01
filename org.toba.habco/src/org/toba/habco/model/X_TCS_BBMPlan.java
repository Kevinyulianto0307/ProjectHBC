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

/** Generated Model for TCS_BBMPlan
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_TCS_BBMPlan extends PO implements I_TCS_BBMPlan, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170316L;

    /** Standard Constructor */
    public X_TCS_BBMPlan (Properties ctx, int TCS_BBMPlan_ID, String trxName)
    {
      super (ctx, TCS_BBMPlan_ID, trxName);
      /** if (TCS_BBMPlan_ID == 0)
        {
			setHBC_Tugboat_ID (0);
			setTCS_BBMPlan_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_TCS_BBMPlan (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_TCS_BBMPlan[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
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

	/** Set BBM Plan.
		@param TCS_BBMPlan_ID BBM Plan	  */
	public void setTCS_BBMPlan_ID (int TCS_BBMPlan_ID)
	{
		if (TCS_BBMPlan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_TCS_BBMPlan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_TCS_BBMPlan_ID, Integer.valueOf(TCS_BBMPlan_ID));
	}

	/** Get BBM Plan.
		@return BBM Plan	  */
	public int getTCS_BBMPlan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TCS_BBMPlan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getTCS_BBMPlan_ID()));
    }

	/** Set TCS_BBMPlan_UU.
		@param TCS_BBMPlan_UU TCS_BBMPlan_UU	  */
	public void setTCS_BBMPlan_UU (String TCS_BBMPlan_UU)
	{
		set_Value (COLUMNNAME_TCS_BBMPlan_UU, TCS_BBMPlan_UU);
	}

	/** Get TCS_BBMPlan_UU.
		@return TCS_BBMPlan_UU	  */
	public String getTCS_BBMPlan_UU () 
	{
		return (String)get_Value(COLUMNNAME_TCS_BBMPlan_UU);
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
	
	//@TommyAng
	/** Set Trip Allocation Day.
	@param TripAllocationDay Trip Allocation Day	  
	*/
	public void setTripAllocationDay (BigDecimal TripAllocationDay)
	{
		set_Value (COLUMNNAME_TripAllocationDay, TripAllocationDay);
	}
	
	/** Set Liter Allocation.
	@param LiterAllocation 
	Liter Allocation
	*/
	public void setLiterAllocation (BigDecimal LiterAllocation)
	{
		set_Value (COLUMNNAME_LiterAllocation, LiterAllocation);
	}
	
	/** Set Total Liters.
	@param TotalLiters Total Liters	  */
	public void setTotalLiters (BigDecimal TotalLiters)
	{
		set_Value (COLUMNNAME_TotalLiters, TotalLiters);
	}
	
	/** Set BBM Usage Report.
	@param BBMUsage BBM Usage Report	  */
	public void setBBMUsage (BigDecimal BBMUsage)
	{
		set_Value (COLUMNNAME_BBMUsage, BBMUsage);
	}
	
	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException
    {
		return (I_HBC_Tugboat)MTable.get(getCtx(), I_HBC_Tugboat.Table_Name)
			.getPO(getHBC_Tugboat_ID(), get_TrxName());	
	}
	
	/** Get Liter Allocation.
	@return Liter Allocation
	*/
	public BigDecimal getLiterAllocation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LiterAllocation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Get Fuel Balance.
	@return Fuel Balance	  */
	public BigDecimal getFuelBalance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelBalance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Set Liter.
	@param Liter Liter	  */
	public void setLiter (BigDecimal Liter)
	{
		set_Value (COLUMNNAME_Liter, Liter);
	}
	
	/** Set Liter Calculation.
	@param LiterCalculation Liter Calculation	  */
	public void setLiterCalculation (BigDecimal LiterCalculation)
	{
		set_Value (COLUMNNAME_LiterCalculation, LiterCalculation);
	}
	
	/** Get Liter Calculation.
	@return Liter Calculation	  */
	public BigDecimal getLiterCalculation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LiterCalculation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Set Fuel Balance New.
	@param FuelBalanceNew Fuel Balance New	  */
	public void setFuelBalanceNew (BigDecimal FuelBalanceNew)
	{
		set_Value (COLUMNNAME_FuelBalanceNew, FuelBalanceNew);
	}
	
	/** Set Ship Fuel Usage Report Diff.
	@param ShipFuelUsage Ship Fuel Usage Report Diff	  */
	public void setShipFuelUsage (BigDecimal ShipFuelUsage)
	{
		set_Value (COLUMNNAME_ShipFuelUsage, ShipFuelUsage);
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
	
	/** Get Ship Fuel Usage Report Diff.
	@return Ship Fuel Usage Report Diff	  */
	public BigDecimal getShipFuelUsage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShipFuelUsage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Get Liter.
	@return Liter	  */
	public BigDecimal getLiter () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Liter);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Set Fuel Adjustment.
	@param FuelAdjustment Fuel Adjustment	  */
	public void setFuelAdjustment (BigDecimal FuelAdjustment)
	{
		set_Value (COLUMNNAME_FuelAdjustment, FuelAdjustment);
	}
	
	/** Set Fuel Actual.
	@param FuelActual Fuel Actual	  */
	public void setFuelActual (BigDecimal FuelActual)
	{
		set_Value (COLUMNNAME_FuelActual, FuelActual);
	}
	
	/** Set Fuel Usage Discrepancy.
	@param FuelUsageDiscrepancy Fuel Usage Discrepancy	  */
	public void setFuelUsageDiscrepancy (BigDecimal FuelUsageDiscrepancy)
	{
		set_Value (COLUMNNAME_FuelUsageDiscrepancy, FuelUsageDiscrepancy);
	}
	
	/** Set Total Trip Day (Sail & Berth).
	@param Day Total Trip Day (Sail & Berth)	  */
	public void setDay (BigDecimal Day)
	{
		set_Value (COLUMNNAME_Day, Day);
	}
	
	/** Set Total Sail Day (Sail).
	@param Day Total Sail Day (Sail)	  */
	public void setTotalSailDay (BigDecimal Day)
	{
		set_Value (COLUMNNAME_SailDay, Day);
	}
	
	/** Set Total Berth Day (Berth).
	@param Day Total Berth Day (Berth)	  */
	public void setTotalBerthDay (BigDecimal Day)
	{
		set_Value (COLUMNNAME_BerthDay, Day);
	}
	
	/** Get BBM Usage Report.
	@return BBM Usage Report	  */
	public BigDecimal getBBMUsage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BBMUsage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}