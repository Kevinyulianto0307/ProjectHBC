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

/** Generated Model for HBC_ShipActivity
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipActivity extends PO implements I_HBC_ShipActivity, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161123L;

    /** Standard Constructor */
    public X_HBC_ShipActivity (Properties ctx, int HBC_ShipActivity_ID, String trxName)
    {
      super (ctx, HBC_ShipActivity_ID, trxName);
      /** if (HBC_ShipActivity_ID == 0)
        {
			setAmountOfCargo (Env.ZERO);
			setDraftBack (Env.ZERO);
			setDraftFront (Env.ZERO);
			setHBC_ShipActivity_ID (0);
			setStartDate (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipActivity (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipActivity[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount Of Cargo.
		@param AmountOfCargo Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo)
	{
		set_Value (COLUMNNAME_AmountOfCargo, AmountOfCargo);
	}

	/** Get Amount Of Cargo.
		@return Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountOfCargo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_HBC_Barge getBargePartner() throws RuntimeException
    {
		return (I_HBC_Barge)MTable.get(getCtx(), I_HBC_Barge.Table_Name)
			.getPO(getBargePartner_ID(), get_TrxName());	}

	/** Set Barge Partner.
		@param BargePartner_ID Barge Partner	  */
	public void setBargePartner_ID (int BargePartner_ID)
	{
		if (BargePartner_ID < 1) 
			set_Value (COLUMNNAME_BargePartner_ID, null);
		else 
			set_Value (COLUMNNAME_BargePartner_ID, Integer.valueOf(BargePartner_ID));
	}

	/** Get Barge Partner.
		@return Barge Partner	  */
	public int getBargePartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BargePartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Complete Bunkering.
		@param CompleteBunkering Complete Bunkering	  */
	public void setCompleteBunkering (String CompleteBunkering)
	{
		set_Value (COLUMNNAME_CompleteBunkering, CompleteBunkering);
	}

	/** Get Complete Bunkering.
		@return Complete Bunkering	  */
	public String getCompleteBunkering () 
	{
		return (String)get_Value(COLUMNNAME_CompleteBunkering);
	}

	/** Set Complete Fuel Transfer.
		@param CompleteFuelTransfer Complete Fuel Transfer	  */
	public void setCompleteFuelTransfer (String CompleteFuelTransfer)
	{
		set_Value (COLUMNNAME_CompleteFuelTransfer, CompleteFuelTransfer);
	}

	/** Get Complete Fuel Transfer.
		@return Complete Fuel Transfer	  */
	public String getCompleteFuelTransfer () 
	{
		return (String)get_Value(COLUMNNAME_CompleteFuelTransfer);
	}

	/** Complete Overtowing = O */
	public static final String COMPLETEOVERTOWING_CompleteOvertowing = "O";
	/** Cancel Overtowing = C */
	public static final String COMPLETEOVERTOWING_CancelOvertowing = "C";
	/** Set Complete Overtowing.
		@param CompleteOvertowing Complete Overtowing	  */
	public void setCompleteOvertowing (String CompleteOvertowing)
	{

		set_Value (COLUMNNAME_CompleteOvertowing, CompleteOvertowing);
	}

	/** Get Complete Overtowing.
		@return Complete Overtowing	  */
	public String getCompleteOvertowing () 
	{
		return (String)get_Value(COLUMNNAME_CompleteOvertowing);
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
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

	/** Set Complete Discharging.
		@param DischargingComplete Complete Discharging	  */
	public void setDischargingComplete (String DischargingComplete)
	{
		set_Value (COLUMNNAME_DischargingComplete, DischargingComplete);
	}

	/** Get Complete Discharging.
		@return Complete Discharging	  */
	public String getDischargingComplete () 
	{
		return (String)get_Value(COLUMNNAME_DischargingComplete);
	}

	/** Set Draft (Back).
		@param DraftBack Draft (Back)	  */
	public void setDraftBack (BigDecimal DraftBack)
	{
		set_Value (COLUMNNAME_DraftBack, DraftBack);
	}

	/** Get Draft (Back).
		@return Draft (Back)	  */
	public BigDecimal getDraftBack () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DraftBack);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Draft (Front).
		@param DraftFront Draft (Front)	  */
	public void setDraftFront (BigDecimal DraftFront)
	{
		set_Value (COLUMNNAME_DraftFront, DraftFront);
	}

	/** Get Draft (Front).
		@return Draft (Front)	  */
	public BigDecimal getDraftFront () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DraftFront);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Finish Date.
		@param FinishDate Finish Date	  */
	public void setFinishDate (Timestamp FinishDate)
	{
		set_Value (COLUMNNAME_FinishDate, FinishDate);
	}

	/** Get Finish Date.
		@return Finish Date	  */
	public Timestamp getFinishDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FinishDate);
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

	/** Set Fuel Consumption Main.
		@param FuelConsumptionMain Fuel Consumption Main	  */
	public void setFuelConsumptionMain (BigDecimal FuelConsumptionMain)
	{
		set_Value (COLUMNNAME_FuelConsumptionMain, FuelConsumptionMain);
	}

	/** Get Fuel Consumption Main.
		@return Fuel Consumption Main	  */
	public BigDecimal getFuelConsumptionMain () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelConsumptionMain);
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

	/** Set Fuel Percentage Quota.
		@param FuelPercentageQuota Fuel Percentage Quota	  */
	public void setFuelPercentageQuota (BigDecimal FuelPercentageQuota)
	{
		set_Value (COLUMNNAME_FuelPercentageQuota, FuelPercentageQuota);
	}

	/** Get Fuel Percentage Quota.
		@return Fuel Percentage Quota	  */
	public BigDecimal getFuelPercentageQuota () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelPercentageQuota);
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

	public I_HBC_Jetty getHBC_Jetty() throws RuntimeException
    {
		return (I_HBC_Jetty)MTable.get(getCtx(), I_HBC_Jetty.Table_Name)
			.getPO(getHBC_Jetty_ID(), get_TrxName());	}

	/** Set Jetty.
		@param HBC_Jetty_ID Jetty	  */
	public void setHBC_Jetty_ID (int HBC_Jetty_ID)
	{
		if (HBC_Jetty_ID < 1) 
			set_Value (COLUMNNAME_HBC_Jetty_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Jetty_ID, Integer.valueOf(HBC_Jetty_ID));
	}

	/** Get Jetty.
		@return Jetty	  */
	public int getHBC_Jetty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Jetty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Position getHBC_Position() throws RuntimeException
    {
		return (I_HBC_Position)MTable.get(getCtx(), I_HBC_Position.Table_Name)
			.getPO(getHBC_Position_ID(), get_TrxName());	}

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

	/** Set Ship Activity.
		@param HBC_ShipActivity_ID Ship Activity	  */
	public void setHBC_ShipActivity_ID (int HBC_ShipActivity_ID)
	{
		if (HBC_ShipActivity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipActivity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipActivity_ID, Integer.valueOf(HBC_ShipActivity_ID));
	}

	/** Get Ship Activity.
		@return Ship Activity	  */
	public int getHBC_ShipActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipActivity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_ShipActivity_UU.
		@param HBC_ShipActivity_UU HBC_ShipActivity_UU	  */
	public void setHBC_ShipActivity_UU (String HBC_ShipActivity_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipActivity_UU, HBC_ShipActivity_UU);
	}

	/** Get HBC_ShipActivity_UU.
		@return HBC_ShipActivity_UU	  */
	public String getHBC_ShipActivity_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipActivity_UU);
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

	/** Set Demurrage Freight.
		@param IsDemurrageFreight Demurrage Freight	  */
	public void setIsDemurrageFreight (boolean IsDemurrageFreight)
	{
		set_Value (COLUMNNAME_IsDemurrageFreight, Boolean.valueOf(IsDemurrageFreight));
	}

	/** Get Demurrage Freight.
		@return Demurrage Freight	  */
	public boolean isDemurrageFreight () 
	{
		Object oo = get_Value(COLUMNNAME_IsDemurrageFreight);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Demurrage Weather.
		@param IsDemurrageWeather Demurrage Weather	  */
	public void setIsDemurrageWeather (boolean IsDemurrageWeather)
	{
		set_Value (COLUMNNAME_IsDemurrageWeather, Boolean.valueOf(IsDemurrageWeather));
	}

	/** Get Demurrage Weather.
		@return Demurrage Weather	  */
	public boolean isDemurrageWeather () 
	{
		Object oo = get_Value(COLUMNNAME_IsDemurrageWeather);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Activity.
		@param IsLastActivity Last Activity	  */
	public void setIsLastActivity (boolean IsLastActivity)
	{
		set_Value (COLUMNNAME_IsLastActivity, Boolean.valueOf(IsLastActivity));
	}

	/** Get Last Activity.
		@return Last Activity	  */
	public boolean isLastActivity () 
	{
		Object oo = get_Value(COLUMNNAME_IsLastActivity);
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
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
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

	public org.compiere.model.I_C_BPartner getLoadingAgent_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getLoadingAgent_BPartner_ID(), get_TrxName());	}

	/** Set Loading Agent.
		@param LoadingAgent_BPartner_ID Loading Agent	  */
	public void setLoadingAgent_BPartner_ID (int LoadingAgent_BPartner_ID)
	{
		if (LoadingAgent_BPartner_ID < 1) 
			set_Value (COLUMNNAME_LoadingAgent_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_LoadingAgent_BPartner_ID, Integer.valueOf(LoadingAgent_BPartner_ID));
	}

	/** Get Loading Agent.
		@return Loading Agent	  */
	public int getLoadingAgent_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LoadingAgent_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException
    {
		return (org.compiere.model.I_M_Inventory)MTable.get(getCtx(), org.compiere.model.I_M_Inventory.Table_Name)
			.getPO(getM_Inventory_ID(), get_TrxName());	}

	/** Set Phys.Inventory.
		@param M_Inventory_ID 
		Parameters for a Physical Inventory
	  */
	public void setM_Inventory_ID (int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1) 
			set_Value (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_Value (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	/** Get Phys.Inventory.
		@return Parameters for a Physical Inventory
	  */
	public int getM_Inventory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Inventory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
    {
		return (org.compiere.model.I_M_Movement)MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
			.getPO(getM_Movement_ID(), get_TrxName());	}

	/** Set Inventory Move.
		@param M_Movement_ID 
		Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Inventory Move.
		@return Movement of Inventory
	  */
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mother Vessel.
		@param MotherVessel Mother Vessel	  */
	public void setMotherVessel (String MotherVessel)
	{
		set_Value (COLUMNNAME_MotherVessel, MotherVessel);
	}

	/** Get Mother Vessel.
		@return Mother Vessel	  */
	public String getMotherVessel () 
	{
		return (String)get_Value(COLUMNNAME_MotherVessel);
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

	public I_HBC_ShipPositionLine getShipPositionLinePartner() throws RuntimeException
    {
		return (I_HBC_ShipPositionLine)MTable.get(getCtx(), I_HBC_ShipPositionLine.Table_Name)
			.getPO(getShipPositionLinePartner_ID(), get_TrxName());	}

	/** Set Timesheet Line Partner.
		@param ShipPositionLinePartner_ID Timesheet Line Partner	  */
	public void setShipPositionLinePartner_ID (int ShipPositionLinePartner_ID)
	{
		if (ShipPositionLinePartner_ID < 1) 
			set_Value (COLUMNNAME_ShipPositionLinePartner_ID, null);
		else 
			set_Value (COLUMNNAME_ShipPositionLinePartner_ID, Integer.valueOf(ShipPositionLinePartner_ID));
	}

	/** Get Timesheet Line Partner.
		@return Timesheet Line Partner	  */
	public int getShipPositionLinePartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShipPositionLinePartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Start Date.
		@param StartDate 
		The Start Date indicates the first or starting date
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return The Start Date indicates the first or starting date
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
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

	public I_HBC_Tugboat getTugBoatPartner() throws RuntimeException
    {
		return (I_HBC_Tugboat)MTable.get(getCtx(), I_HBC_Tugboat.Table_Name)
			.getPO(getTugBoatPartner_ID(), get_TrxName());	}

	/** Set TugBoat Partner.
		@param TugBoatPartner_ID TugBoat Partner	  */
	public void setTugBoatPartner_ID (int TugBoatPartner_ID)
	{
		if (TugBoatPartner_ID < 1) 
			set_Value (COLUMNNAME_TugBoatPartner_ID, null);
		else 
			set_Value (COLUMNNAME_TugBoatPartner_ID, Integer.valueOf(TugBoatPartner_ID));
	}

	/** Get TugBoat Partner.
		@return TugBoat Partner	  */
	public int getTugBoatPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TugBoatPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set DiffDaySail.
	@param DiffDaySail	  */
	public void setDiffDaySail (BigDecimal Day)
	{
		set_Value (COLUMNNAME_DiffDaySail, Day);
	}
	
	/** Get DiffDaySail.
		@return DiffDaySail	  */
	public BigDecimal getDiffDaySail () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiffDaySail);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Set DiffDaySail.
	@param DiffDaySail	  */
	public void setBerthDays (BigDecimal Day)
	{
		set_Value (COLUMNNAME_BerthDays, Day);
	}
	
	/** Get DiffDaySail.
		@return DiffDaySail	  */
	public BigDecimal getBerthDays () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BerthDays);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}