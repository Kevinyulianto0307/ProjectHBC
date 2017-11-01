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

/** Generated Model for HBC_TripAllocation
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_TripAllocation extends PO implements I_HBC_TripAllocation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170809L;

    /** Standard Constructor */
    public X_HBC_TripAllocation (Properties ctx, int HBC_TripAllocation_ID, String trxName)
    {
      super (ctx, HBC_TripAllocation_ID, trxName);
      /** if (HBC_TripAllocation_ID == 0)
        {
			setHBC_TripAllocation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_TripAllocation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_TripAllocation[")
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

	/** Set Distance.
		@param Distance Distance	  */
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_TripAllocation_ID()));
    }

	/** Set HBC_TripAllocation_UU.
		@param HBC_TripAllocation_UU HBC_TripAllocation_UU	  */
	public void setHBC_TripAllocation_UU (String HBC_TripAllocation_UU)
	{
		set_Value (COLUMNNAME_HBC_TripAllocation_UU, HBC_TripAllocation_UU);
	}

	/** Get HBC_TripAllocation_UU.
		@return HBC_TripAllocation_UU	  */
	public String getHBC_TripAllocation_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_TripAllocation_UU);
	}

	/** Dalam = D */
	public static final String HBC_TRIPTYPE_ID_Dalam = "D";
	/** Dalam - Muatan = DM */
	public static final String HBC_TRIPTYPE_ID_Dalam_Muatan = "DM";
	/** Dalam - Kosong = DK */
	public static final String HBC_TRIPTYPE_ID_Dalam_Kosong = "DK";
	/** Dalam - Lenggang = DL */
	public static final String HBC_TRIPTYPE_ID_Dalam_Lenggang = "DL";
	/** Luar - Muatan = LM */
	public static final String HBC_TRIPTYPE_ID_Luar_Muatan = "LM";
	/** Luar - Kosong = LK */
	public static final String HBC_TRIPTYPE_ID_Luar_Kosong = "LK";
	/** Pulang - Muatan = PM */
	public static final String HBC_TRIPTYPE_ID_Pulang_Muatan = "PM";
	/** Pulang - Kosong = PK */
	public static final String HBC_TRIPTYPE_ID_Pulang_Kosong = "PK";
	/** Pulang - Lenggang = PL */
	public static final String HBC_TRIPTYPE_ID_Pulang_Lenggang = "PL";
	/** Luar - Lenggang = LL */
	public static final String HBC_TRIPTYPE_ID_Luar_Lenggang = "LL";
	/** Set Trip Type.
		@param HBC_TripType_ID Trip Type	  */
	public void setHBC_TripType_ID (String HBC_TripType_ID)
	{

		set_Value (COLUMNNAME_HBC_TripType_ID, HBC_TripType_ID);
	}

	/** Get Trip Type.
		@return Trip Type	  */
	public String getHBC_TripType_ID () 
	{
		return (String)get_Value(COLUMNNAME_HBC_TripType_ID);
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
			set_Value (COLUMNNAME_HBC_TugboatCategory_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_TugboatCategory_ID, Integer.valueOf(HBC_TugboatCategory_ID));
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

	/** Set Standby Hour.
		@param Standby_Hour Standby Hour	  */
	public void setStandby_Hour (BigDecimal Standby_Hour)
	{
		set_Value (COLUMNNAME_Standby_Hour, Standby_Hour);
	}

	/** Get Standby Hour.
		@return Standby Hour	  */
	public BigDecimal getStandby_Hour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Standby_Hour);
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

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
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