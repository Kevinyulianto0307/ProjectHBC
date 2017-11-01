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

/** Generated Model for HBC_Position
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Position extends PO implements I_HBC_Position, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161121L;

    /** Standard Constructor */
    public X_HBC_Position (Properties ctx, int HBC_Position_ID, String trxName)
    {
      super (ctx, HBC_Position_ID, trxName);
      /** if (HBC_Position_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_HBC_Position (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_Position[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Allocation Number.
		@param AllocationCode Allocation Number	  */
	public void setAllocationCode (int AllocationCode)
	{
		set_Value (COLUMNNAME_AllocationCode, Integer.valueOf(AllocationCode));
	}

	/** Get Allocation Number.
		@return Allocation Number	  */
	public int getAllocationCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AllocationCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set allocationno.
		@param allocationno allocationno	  */
	public void setallocationno (String allocationno)
	{
		set_Value (COLUMNNAME_allocationno, allocationno);
	}

	/** Get allocationno.
		@return allocationno	  */
	public String getallocationno () 
	{
		return (String)get_Value(COLUMNNAME_allocationno);
	}

	/** Set Coordinates.
		@param Coordinates 
		Location coordinate
	  */
	public void setCoordinates (String Coordinates)
	{
		set_Value (COLUMNNAME_Coordinates, Coordinates);
	}

	/** Get Coordinates.
		@return Location coordinate
	  */
	public String getCoordinates () 
	{
		return (String)get_Value(COLUMNNAME_Coordinates);
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

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
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

	/** Set Difference Days.
		@param DifferenceDays Difference Days	  */
	public void setDifferenceDays (BigDecimal DifferenceDays)
	{
		set_Value (COLUMNNAME_DifferenceDays, DifferenceDays);
	}

	/** Get Difference Days.
		@return Difference Days	  */
	public BigDecimal getDifferenceDays () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceDays);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Difference Hour.
		@param DifferenceHour Difference Hour	  */
	public void setDifferenceHour (BigDecimal DifferenceHour)
	{
		set_Value (COLUMNNAME_DifferenceHour, DifferenceHour);
	}

	/** Get Difference Hour.
		@return Difference Hour	  */
	public BigDecimal getDifferenceHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}
	
	/** Get Total Cargo.
		@return Last Total Cargo
	*/
	public BigDecimal getTotalCargoQty () 
	{
		return (BigDecimal)get_Value(COLUMNNAME_TotalCargoQty);
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

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getHBC_PortPosition_ID(), get_TrxName());	}

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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_PortPosition_ID()));
    }

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

	/** Set HBC_Position_UU.
		@param HBC_Position_UU HBC_Position_UU	  */
	public void setHBC_Position_UU (String HBC_Position_UU)
	{
		set_Value (COLUMNNAME_HBC_Position_UU, HBC_Position_UU);
	}

	/** Get HBC_Position_UU.
		@return HBC_Position_UU	  */
	public String getHBC_Position_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Position_UU);
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

	/** Set Allocate.
		@param IsAllocates Allocate	  */
	public void setIsAllocates (boolean IsAllocates)
	{
		set_Value (COLUMNNAME_IsAllocates, Boolean.valueOf(IsAllocates));
	}

	/** Get Allocate.
		@return Allocate	  */
	public boolean isAllocates () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllocates);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSail.
		@param IsSail IsSail	  */
	public void setIsSail (boolean IsSail)
	{
		set_Value (COLUMNNAME_IsSail, Boolean.valueOf(IsSail));
	}

	/** Get IsSail.
		@return IsSail	  */
	public boolean isSail () 
	{
		Object oo = get_Value(COLUMNNAME_IsSail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Coordinate ?.
		@param IsUseCoordinate Use Coordinate ?	  */
	public void setIsUseCoordinate (boolean IsUseCoordinate)
	{
		set_Value (COLUMNNAME_IsUseCoordinate, Boolean.valueOf(IsUseCoordinate));
	}

	/** Get Use Coordinate ?.
		@return Use Coordinate ?	  */
	public boolean isUseCoordinate () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseCoordinate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (org.compiere.model.I_M_PriceList)MTable.get(getCtx(), org.compiere.model.I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
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

	/** Set Open Google Maps.
		@param ProcessOpenGM Open Google Maps	  */
	public void setProcessOpenGM (String ProcessOpenGM)
	{
		set_Value (COLUMNNAME_ProcessOpenGM, ProcessOpenGM);
	}

	/** Get Open Google Maps.
		@return Open Google Maps	  */
	public String getProcessOpenGM () 
	{
		return (String)get_Value(COLUMNNAME_ProcessOpenGM);
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
}