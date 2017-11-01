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

/** Generated Model for HBC_Trip
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Trip extends PO implements I_HBC_Trip, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161205L;

    /** Standard Constructor */
    public X_HBC_Trip (Properties ctx, int HBC_Trip_ID, String trxName)
    {
      super (ctx, HBC_Trip_ID, trxName);
      /** if (HBC_Trip_ID == 0)
        {
			setC_Project_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_Trip (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_Trip[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BBM Usage Report.
		@param BBMUsage BBM Usage Report	  */
	public void setBBMUsage (BigDecimal BBMUsage)
	{
		set_Value (COLUMNNAME_BBMUsage, BBMUsage);
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
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
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

	/** Complete Trip = C */
	public static final String COMPLETETRIP_CompleteTrip = "C";
	/** Open Trip = O */
	public static final String COMPLETETRIP_OpenTrip = "O";
	/** Set Complete Trip.
		@param CompleteTrip Complete Trip	  */
	public void setCompleteTrip (String CompleteTrip)
	{

		set_Value (COLUMNNAME_CompleteTrip, CompleteTrip);
	}

	/** Get Complete Trip.
		@return Complete Trip	  */
	public String getCompleteTrip () 
	{
		return (String)get_Value(COLUMNNAME_CompleteTrip);
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

	/** Set Total Trip Day (Sail & Berth).
		@param Day Total Trip Day (Sail & Berth)	  */
	public void setDay (BigDecimal Day)
	{
		set_Value (COLUMNNAME_Day, Day);
	}

	/** Get Total Trip Day (Sail & Berth).
		@return Total Trip Day (Sail & Berth)	  */
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	/** Set Fuel Actual.
		@param FuelActual Fuel Actual	  */
	public void setFuelActual (BigDecimal FuelActual)
	{
		set_Value (COLUMNNAME_FuelActual, FuelActual);
	}

	/** Get Fuel Actual.
		@return Fuel Actual	  */
	public BigDecimal getFuelActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelActual);
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

	/** Get Fuel Adjustment.
		@return Fuel Adjustment	  */
	public BigDecimal getFuelAdjustment () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelAdjustment);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fuel Balance.
		@param FuelBalance Fuel Balance	  */
	public void setFuelBalance (BigDecimal FuelBalance)
	{
		set_Value (COLUMNNAME_FuelBalance, FuelBalance);
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

	/** Set Fuel Balance New.
		@param FuelBalanceNew Fuel Balance New	  */
	public void setFuelBalanceNew (BigDecimal FuelBalanceNew)
	{
		set_Value (COLUMNNAME_FuelBalanceNew, FuelBalanceNew);
	}

	/** Get Fuel Balance New.
		@return Fuel Balance New	  */
	public BigDecimal getFuelBalanceNew () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelBalanceNew);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fuel Usage Discrepancy.
		@param FuelUsageDiscrepancy Fuel Usage Discrepancy	  */
	public void setFuelUsageDiscrepancy (BigDecimal FuelUsageDiscrepancy)
	{
		set_Value (COLUMNNAME_FuelUsageDiscrepancy, FuelUsageDiscrepancy);
	}

	/** Get Fuel Usage Discrepancy.
		@return Fuel Usage Discrepancy	  */
	public BigDecimal getFuelUsageDiscrepancy () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelUsageDiscrepancy);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Tugboat & Barge = TNB */
	public static final String HBC_SHIPREQTYPE_TugboatBarge = "TNB";
	/** Tugboat Only = TOL */
	public static final String HBC_SHIPREQTYPE_TugboatOnly = "TOL";
	/** Set Ship Required Type.
		@param HBC_ShipReqType Ship Required Type	  */
	public void setHBC_ShipReqType (String HBC_ShipReqType)
	{

		set_Value (COLUMNNAME_HBC_ShipReqType, HBC_ShipReqType);
	}

	/** Get Ship Required Type.
		@return Ship Required Type	  */
	public String getHBC_ShipReqType () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipReqType);
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

	/** Set HBC_Trip_UU.
		@param HBC_Trip_UU HBC_Trip_UU	  */
	public void setHBC_Trip_UU (String HBC_Trip_UU)
	{
		set_Value (COLUMNNAME_HBC_Trip_UU, HBC_Trip_UU);
	}

	/** Get HBC_Trip_UU.
		@return HBC_Trip_UU	  */
	public String getHBC_Trip_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Trip_UU);
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
			set_ValueNoCheck (COLUMNNAME_HBC_Tugboat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Tugboat_ID, Integer.valueOf(HBC_Tugboat_ID));
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

	/** Set AR Calculation.
		@param IsARCalculation AR Calculation	  */
	public void setIsARCalculation (boolean IsARCalculation)
	{
		set_Value (COLUMNNAME_IsARCalculation, Boolean.valueOf(IsARCalculation));
	}

	/** Get AR Calculation.
		@return AR Calculation	  */
	public boolean isARCalculation () 
	{
		Object oo = get_Value(COLUMNNAME_IsARCalculation);
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

	/** Set Liter.
		@param Liter Liter	  */
	public void setLiter (BigDecimal Liter)
	{
		set_Value (COLUMNNAME_Liter, Liter);
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

	/** Set Original Coordinate.
		@param OriginalCoordinate Original Coordinate	  */
	public void setOriginalCoordinate (String OriginalCoordinate)
	{
		set_Value (COLUMNNAME_OriginalCoordinate, OriginalCoordinate);
	}

	/** Get Original Coordinate.
		@return Original Coordinate	  */
	public String getOriginalCoordinate () 
	{
		return (String)get_Value(COLUMNNAME_OriginalCoordinate);
	}

	public I_HBC_PortPosition getPortPosition_From2() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getPortPosition_From2_ID(), get_TrxName());	}

	/** Set Port / Position From 2.
		@param PortPosition_From2_ID Port / Position From 2	  */
	public void setPortPosition_From2_ID (int PortPosition_From2_ID)
	{
		if (PortPosition_From2_ID < 1) 
			set_Value (COLUMNNAME_PortPosition_From2_ID, null);
		else 
			set_Value (COLUMNNAME_PortPosition_From2_ID, Integer.valueOf(PortPosition_From2_ID));
	}

	/** Get Port / Position From 2.
		@return Port / Position From 2	  */
	public int getPortPosition_From2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PortPosition_From2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_PortPosition getPortPosition_To2() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getPortPosition_To2_ID(), get_TrxName());	}

	/** Set Port / Position To 2.
		@param PortPosition_To2_ID Port / Position To 2	  */
	public void setPortPosition_To2_ID (int PortPosition_To2_ID)
	{
		if (PortPosition_To2_ID < 1) 
			set_Value (COLUMNNAME_PortPosition_To2_ID, null);
		else 
			set_Value (COLUMNNAME_PortPosition_To2_ID, Integer.valueOf(PortPosition_To2_ID));
	}

	/** Get Port / Position To 2.
		@return Port / Position To 2	  */
	public int getPortPosition_To2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PortPosition_To2_ID);
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

	/** Set Ship Fuel Usage Report Diff.
		@param ShipFuelUsage Ship Fuel Usage Report Diff	  */
	public void setShipFuelUsage (BigDecimal ShipFuelUsage)
	{
		set_Value (COLUMNNAME_ShipFuelUsage, ShipFuelUsage);
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

	/** Set SI Number.
		@param SINumber SI Number	  */
	public void setSINumber (String SINumber)
	{
		set_Value (COLUMNNAME_SINumber, SINumber);
	}

	/** Get SI Number.
		@return SI Number	  */
	public String getSINumber () 
	{
		return (String)get_Value(COLUMNNAME_SINumber);
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

	/** Set Total Cargo Qty.
		@param TotalCargoQty Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty)
	{
		set_Value (COLUMNNAME_TotalCargoQty, TotalCargoQty);
	}

	/** Get Total Cargo Qty.
		@return Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalCargoQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Trip Allocation Day.
		@param TripAllocationDay Trip Allocation Day	  */
	public void setTripAllocationDay (BigDecimal TripAllocationDay)
	{
		set_Value (COLUMNNAME_TripAllocationDay, TripAllocationDay);
	}

	/** Get Trip Allocation Day.
		@return Trip Allocation Day	  */
	public BigDecimal getTripAllocationDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TripAllocationDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}