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

/** Generated Model for HBC_FuelTripAllocation
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_FuelTripAllocation extends PO implements I_HBC_FuelTripAllocation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161230L;

    /** Standard Constructor */
    public X_HBC_FuelTripAllocation (Properties ctx, int HBC_FuelTripAllocation_ID, String trxName)
    {
      super (ctx, HBC_FuelTripAllocation_ID, trxName);
      /** if (HBC_FuelTripAllocation_ID == 0)
        {
			setHBC_FuelTripAllocation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_FuelTripAllocation (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_HBC_FuelTripAllocation[")
        .append(get_ID()).append("]");
      return sb.toString();
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
	
	/** Set Hour.
	@param Hour Hour  */
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
	
	/** Set ValidFrom.
	@param ValidFrom ValidFrom Date	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}
	
	/** Get ValidFrom.
		@return ValidFrom  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
	
	/** Set ValidTo.
	@param ValidTo ValidTo Date	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}
	
	/** Get ValidTo.
		@return ValidTo  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
	
	/** Set Distance.
	@param Distance Distance  */
	public void setDistance (BigDecimal Distance)
	{
		set_Value (COLUMNNAME_Distance, Distance);
	}
	
	/** Get Distance.
		@return Distance	  */
	public BigDecimal getDistance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Distance);
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

	/** Set Fuel Allocation.
		@param FuelAllocation Fuel Allocation	  */
	public void setFuelAllocation (BigDecimal FuelAllocation)
	{
		set_Value (COLUMNNAME_FuelAllocation, FuelAllocation);
	}

	/** Get Fuel Allocation.
		@return Fuel Allocation	  */
	public BigDecimal getFuelAllocation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelAllocation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_Value (COLUMNNAME_HBC_BargeCategory_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_BargeCategory_ID, Integer.valueOf(HBC_BargeCategory_ID));
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

	/** Set Fuel Trip Allocation.
		@param HBC_FuelTripAllocation_ID Fuel Trip Allocation	  */
	public void setHBC_FuelTripAllocation_ID (int HBC_FuelTripAllocation_ID)
	{
		if (HBC_FuelTripAllocation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_FuelTripAllocation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_FuelTripAllocation_ID, Integer.valueOf(HBC_FuelTripAllocation_ID));
	}

	/** Get Fuel Trip Allocation.
		@return Fuel Trip Allocation	  */
	public int getHBC_FuelTripAllocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_FuelTripAllocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_FuelTripAllocation_UU.
		@param HBC_FuelTripAllocation_UU HBC_FuelTripAllocation_UU	  */
	public void setHBC_FuelTripAllocation_UU (String HBC_FuelTripAllocation_UU)
	{
		set_Value (COLUMNNAME_HBC_FuelTripAllocation_UU, HBC_FuelTripAllocation_UU);
	}

	/** Get HBC_FuelTripAllocation_UU.
		@return HBC_FuelTripAllocation_UU	  */
	public String getHBC_FuelTripAllocation_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_FuelTripAllocation_UU);
	}

	public I_HBC_TripAllocation getHBC_TripAllocation() throws RuntimeException
    {
		return (I_HBC_TripAllocation)MTable.get(getCtx(), I_HBC_TripAllocation.Table_Name)
			.getPO(getHBC_TripAllocation_ID(), get_TrxName());	}

	/** Set Trip Allocation.
		@param HBC_TripAllocation_ID Trip Allocation	  */
	public void setHBC_TripAllocation_ID (int HBC_TripAllocation_ID)
	{
		if (HBC_TripAllocation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_TripAllocation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_TripAllocation_ID, Integer.valueOf(HBC_TripAllocation_ID));
	}

	/** Get Trip Allocation.
		@return Trip Allocation	  */
	public int getHBC_TripAllocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_TripAllocation_ID);
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

	public I_HBC_TugboatCategory getHBC_TugboatCategory() throws RuntimeException
    {
		return (I_HBC_TugboatCategory)MTable.get(getCtx(), I_HBC_TugboatCategory.Table_Name)
			.getPO(getHBC_TugboatCategory_ID(), get_TrxName());	}

	/** Set Tugboat Category.
		@param HBC_TugboatCategory_ID Tugboat Category	  */
	public void setHBC_TugboatCategory_ID (int HBC_TugboatCategory_ID)
	{
		if (HBC_TugboatCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_TugboatCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_TugboatCategory_ID, Integer.valueOf(HBC_TugboatCategory_ID));
	}

	/** Get Tugboat Category.
		@return Tugboat Category	  */
	public int getHBC_TugboatCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_TugboatCategory_ID);
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

	/** Set Trip Dalam?.
		@param IsTripDalam Trip Dalam?	  */
	public void setIsTripDalam (boolean IsTripDalam)
	{
		set_Value (COLUMNNAME_IsTripDalam, Boolean.valueOf(IsTripDalam));
	}

	/** Get Trip Dalam?.
		@return Trip Dalam?	  */
	public boolean isTripDalam () 
	{
		Object oo = get_Value(COLUMNNAME_IsTripDalam);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liter Allocation.
		@param LiterAllocation 
		Liter Allocation
	  */
	public void setLiterAllocation (BigDecimal LiterAllocation)
	{
		set_Value (COLUMNNAME_LiterAllocation, LiterAllocation);
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

	/** Set Speed (Knot).
		@param Speed Speed (Knot)	  */
	public void setSpeed (BigDecimal Speed)
	{
		set_Value (COLUMNNAME_Speed, Speed);
	}

	/** Get Speed (Knot).
		@return Speed (Knot)	  */
	public BigDecimal getSpeed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Speed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
	
	/** Set HBC_TripType_ID.
	@param HBC_TripType_ID HBC_TripType_ID	  */
	public void setHBC_TripType_ID (String HBC_TripType_ID)
	{
		set_Value (COLUMNNAME_HBC_TripType_ID, HBC_TripType_ID);
	}
	
	/** Get HBC_TripType_ID.
		@return HBC_TripType_ID	  */
	public String getHBC_TripType_ID () 
	{
		return (String)get_Value(COLUMNNAME_HBC_TripType_ID);
	}
}