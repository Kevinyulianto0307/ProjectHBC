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

/** Generated Model for HBC_Tugboat
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Tugboat extends PO implements I_HBC_Tugboat, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161017L;

    /** Standard Constructor */
    public X_HBC_Tugboat (Properties ctx, int HBC_Tugboat_ID, String trxName)
    {
      super (ctx, HBC_Tugboat_ID, trxName);
      /** if (HBC_Tugboat_ID == 0)
        {
			setBuildYear (null);
			setHBC_Tugboat_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_Tugboat (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_Tugboat[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Biro Klasifikasi Indonesia = BKI */
	public static final String BUILDCLASS_BiroKlasifikasiIndonesia = "BKI";
	/** Set Build Class.
		@param BuildClass Build Class	  */
	public void setBuildClass (String BuildClass)
	{

		set_Value (COLUMNNAME_BuildClass, BuildClass);
	}

	/** Get Build Class.
		@return Build Class	  */
	public String getBuildClass () 
	{
		return (String)get_Value(COLUMNNAME_BuildClass);
	}

	/** Set Build Year.
		@param BuildYear Build Year	  */
	public void setBuildYear (String BuildYear)
	{
		set_Value (COLUMNNAME_BuildYear, BuildYear);
	}

	/** Get Build Year.
		@return Build Year	  */
	public String getBuildYear () 
	{
		return (String)get_Value(COLUMNNAME_BuildYear);
	}

	/** Set Buy Date.
		@param BuyDate Buy Date	  */
	public void setBuyDate (Timestamp BuyDate)
	{
		set_Value (COLUMNNAME_BuyDate, BuyDate);
	}

	/** Get Buy Date.
		@return Buy Date	  */
	public Timestamp getBuyDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_BuyDate);
	}

	/** Set Call Sign.
		@param CallSign Call Sign	  */
	public void setCallSign (String CallSign)
	{
		set_Value (COLUMNNAME_CallSign, CallSign);
	}

	/** Get Call Sign.
		@return Call Sign	  */
	public String getCallSign () 
	{
		return (String)get_Value(COLUMNNAME_CallSign);
	}

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
    {
		return (org.compiere.model.I_C_Country)MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());	}

	/** Set Country.
		@param C_Country_ID 
		Country 
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocTypeTarget_ID(), get_TrxName());	}

	/** Set Target Document Type.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Target Document Type.
		@return Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
    {
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Region_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_ShipActivity getCurrent_ShipActivity() throws RuntimeException
    {
		return (I_HBC_ShipActivity)MTable.get(getCtx(), I_HBC_ShipActivity.Table_Name)
			.getPO(getCurrent_ShipActivity_ID(), get_TrxName());	}

	/** Set Current Ship Activity.
		@param Current_ShipActivity_ID Current Ship Activity	  */
	public void setCurrent_ShipActivity_ID (int Current_ShipActivity_ID)
	{
		if (Current_ShipActivity_ID < 1) 
			set_Value (COLUMNNAME_Current_ShipActivity_ID, null);
		else 
			set_Value (COLUMNNAME_Current_ShipActivity_ID, Integer.valueOf(Current_ShipActivity_ID));
	}

	/** Get Current Ship Activity.
		@return Current Ship Activity	  */
	public int getCurrent_ShipActivity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Current_ShipActivity_ID);
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

	/** Set Design Draft.
		@param DesignDraft Design Draft	  */
	public void setDesignDraft (BigDecimal DesignDraft)
	{
		set_Value (COLUMNNAME_DesignDraft, DesignDraft);
	}

	/** Get Design Draft.
		@return Design Draft	  */
	public BigDecimal getDesignDraft () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DesignDraft);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Diameter.
		@param DiameterPropeller Diameter	  */
	public void setDiameterPropeller (BigDecimal DiameterPropeller)
	{
		set_Value (COLUMNNAME_DiameterPropeller, DiameterPropeller);
	}

	/** Get Diameter.
		@return Diameter	  */
	public BigDecimal getDiameterPropeller () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiameterPropeller);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Flag.
		@param Flag Flag	  */
	public void setFlag (String Flag)
	{
		set_Value (COLUMNNAME_Flag, Flag);
	}

	/** Get Flag.
		@return Flag	  */
	public String getFlag () 
	{
		return (String)get_Value(COLUMNNAME_Flag);
	}

	/** Set Fresh Water.
		@param FreshWater Fresh Water	  */
	public void setFreshWater (BigDecimal FreshWater)
	{
		set_Value (COLUMNNAME_FreshWater, FreshWater);
	}

	/** Get Fresh Water.
		@return Fresh Water	  */
	public BigDecimal getFreshWater () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FreshWater);
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

	/** Set Fuel Capacity per Day.
		@param FuelCapacityperDay Fuel Capacity per Day	  */
	public void setFuelCapacityperDay (BigDecimal FuelCapacityperDay)
	{
		set_Value (COLUMNNAME_FuelCapacityperDay, FuelCapacityperDay);
	}

	/** Get Fuel Capacity per Day.
		@return Fuel Capacity per Day	  */
	public BigDecimal getFuelCapacityperDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelCapacityperDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//Temporary Comment by @TommyAng
	/** Set Fuel Consumption Main.
		@param FuelConsumptionMain Fuel Consumption Main	  *//*
	public void setFuelConsumptionMain (String FuelConsumptionMain)
	{
		set_Value (COLUMNNAME_FuelConsumptionMain, FuelConsumptionMain);
	}

	*//** Get Fuel Consumption Main.
		@return Fuel Consumption Main	  *//*
	public String getFuelConsumptionMain () 
	{
		return (String)get_Value(COLUMNNAME_FuelConsumptionMain);
	}*/
	
	public void setFuelConsumptionMain (String FuelConsumptionMain)
	{
		set_Value (COLUMNNAME_FuelConsumptionMain, FuelConsumptionMain);
	}
	
	public BigDecimal getFuelConsumptionMain () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelConsumptionMain);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//end @TommyAng
	
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

	/** Set Gross Tonnage.
		@param GrossTonnage Gross Tonnage	  */
	public void setGrossTonnage (BigDecimal GrossTonnage)
	{
		set_Value (COLUMNNAME_GrossTonnage, GrossTonnage);
	}

	/** Get Gross Tonnage.
		@return Gross Tonnage	  */
	public BigDecimal getGrossTonnage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrossTonnage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_HBC_AuxiliaryEngine getHBC_AuxiliaryEngine() throws RuntimeException
    {
		return (I_HBC_AuxiliaryEngine)MTable.get(getCtx(), I_HBC_AuxiliaryEngine.Table_Name)
			.getPO(getHBC_AuxiliaryEngine_ID(), get_TrxName());	}

	/** Set Auxiliary Engine.
		@param HBC_AuxiliaryEngine_ID Auxiliary Engine	  */
	public void setHBC_AuxiliaryEngine_ID (int HBC_AuxiliaryEngine_ID)
	{
		if (HBC_AuxiliaryEngine_ID < 1) 
			set_Value (COLUMNNAME_HBC_AuxiliaryEngine_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_AuxiliaryEngine_ID, Integer.valueOf(HBC_AuxiliaryEngine_ID));
	}

	/** Get Auxiliary Engine.
		@return Auxiliary Engine	  */
	public int getHBC_AuxiliaryEngine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_AuxiliaryEngine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_HBC_GearBox getHBC_GearBox() throws RuntimeException
    {
		return (I_HBC_GearBox)MTable.get(getCtx(), I_HBC_GearBox.Table_Name)
			.getPO(getHBC_GearBox_ID(), get_TrxName());	}

	/** Set Gear Box.
		@param HBC_GearBox_ID Gear Box	  */
	public void setHBC_GearBox_ID (int HBC_GearBox_ID)
	{
		if (HBC_GearBox_ID < 1) 
			set_Value (COLUMNNAME_HBC_GearBox_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_GearBox_ID, Integer.valueOf(HBC_GearBox_ID));
	}

	/** Get Gear Box.
		@return Gear Box	  */
	public int getHBC_GearBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_GearBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_MainEngine getHBC_MainEngine() throws RuntimeException
    {
		return (I_HBC_MainEngine)MTable.get(getCtx(), I_HBC_MainEngine.Table_Name)
			.getPO(getHBC_MainEngine_ID(), get_TrxName());	}

	/** Set Main Engine.
		@param HBC_MainEngine_ID Main Engine	  */
	public void setHBC_MainEngine_ID (int HBC_MainEngine_ID)
	{
		if (HBC_MainEngine_ID < 1) 
			set_Value (COLUMNNAME_HBC_MainEngine_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_MainEngine_ID, Integer.valueOf(HBC_MainEngine_ID));
	}

	/** Get Main Engine.
		@return Main Engine	  */
	public int getHBC_MainEngine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_MainEngine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Shipyard getHBC_Shipyard() throws RuntimeException
    {
		return (I_HBC_Shipyard)MTable.get(getCtx(), I_HBC_Shipyard.Table_Name)
			.getPO(getHBC_Shipyard_ID(), get_TrxName());	}

	/** Set Shipyard.
		@param HBC_Shipyard_ID Shipyard	  */
	public void setHBC_Shipyard_ID (int HBC_Shipyard_ID)
	{
		if (HBC_Shipyard_ID < 1) 
			set_Value (COLUMNNAME_HBC_Shipyard_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Shipyard_ID, Integer.valueOf(HBC_Shipyard_ID));
	}

	/** Get Shipyard.
		@return Shipyard	  */
	public int getHBC_Shipyard_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Shipyard_ID);
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

	/** Set HBC_Tugboat_UU.
		@param HBC_Tugboat_UU HBC_Tugboat_UU	  */
	public void setHBC_Tugboat_UU (String HBC_Tugboat_UU)
	{
		set_Value (COLUMNNAME_HBC_Tugboat_UU, HBC_Tugboat_UU);
	}

	/** Get HBC_Tugboat_UU.
		@return HBC_Tugboat_UU	  */
	public String getHBC_Tugboat_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Tugboat_UU);
	}

	/** Set Feet.
		@param IsFeet Feet	  */
	public void setIsFeet (boolean IsFeet)
	{
		set_Value (COLUMNNAME_IsFeet, Boolean.valueOf(IsFeet));
	}

	/** Get Feet.
		@return Feet	  */
	public boolean isFeet () 
	{
		Object oo = get_Value(COLUMNNAME_IsFeet);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Jumbo.
		@param IsJumbo Jumbo	  */
	public void setIsJumbo (boolean IsJumbo)
	{
		set_Value (COLUMNNAME_IsJumbo, Boolean.valueOf(IsJumbo));
	}

	/** Get Jumbo.
		@return Jumbo	  */
	public boolean isJumbo () 
	{
		Object oo = get_Value(COLUMNNAME_IsJumbo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Length.
		@param Length Length	  */
	public void setLength (BigDecimal Length)
	{
		set_Value (COLUMNNAME_Length, Length);
	}

	/** Get Length.
		@return Length	  */
	public BigDecimal getLength () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Length);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Length Between Perpendicular.
		@param LengthBetweenPerpendicular Length Between Perpendicular	  */
	public void setLengthBetweenPerpendicular (BigDecimal LengthBetweenPerpendicular)
	{
		set_Value (COLUMNNAME_LengthBetweenPerpendicular, LengthBetweenPerpendicular);
	}

	/** Get Length Between Perpendicular.
		@return Length Between Perpendicular	  */
	public BigDecimal getLengthBetweenPerpendicular () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LengthBetweenPerpendicular);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Length (Feet).
		@param LengthFeet Length (Feet)	  */
	public void setLengthFeet (int LengthFeet)
	{
		set_Value (COLUMNNAME_LengthFeet, Integer.valueOf(LengthFeet));
	}

	/** Get Length (Feet).
		@return Length (Feet)	  */
	public int getLengthFeet () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LengthFeet);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Length Over All.
		@param LengthOverAll Length Over All	  */
	public void setLengthOverAll (BigDecimal LengthOverAll)
	{
		set_Value (COLUMNNAME_LengthOverAll, LengthOverAll);
	}

	/** Get Length Over All.
		@return Length Over All	  */
	public BigDecimal getLengthOverAll () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LengthOverAll);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Minimum Draft (M).
		@param MinDraft Minimum Draft (M)	  */
	public void setMinDraft (BigDecimal MinDraft)
	{
		set_Value (COLUMNNAME_MinDraft, MinDraft);
	}

	/** Get Minimum Draft (M).
		@return Minimum Draft (M)	  */
	public BigDecimal getMinDraft () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinDraft);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Moulded Depth.
		@param MouldedDepth Moulded Depth	  */
	public void setMouldedDepth (BigDecimal MouldedDepth)
	{
		set_Value (COLUMNNAME_MouldedDepth, MouldedDepth);
	}

	/** Get Moulded Depth.
		@return Moulded Depth	  */
	public BigDecimal getMouldedDepth () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MouldedDepth);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Moulded Depth (Feet).
		@param MouldedDepthFeet Moulded Depth (Feet)	  */
	public void setMouldedDepthFeet (int MouldedDepthFeet)
	{
		set_Value (COLUMNNAME_MouldedDepthFeet, Integer.valueOf(MouldedDepthFeet));
	}

	/** Get Moulded Depth (Feet).
		@return Moulded Depth (Feet)	  */
	public int getMouldedDepthFeet () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MouldedDepthFeet);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Nett Tonnage.
		@param NettTonnage Nett Tonnage	  */
	public void setNettTonnage (BigDecimal NettTonnage)
	{
		set_Value (COLUMNNAME_NettTonnage, NettTonnage);
	}

	/** Get Nett Tonnage.
		@return Nett Tonnage	  */
	public BigDecimal getNettTonnage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NettTonnage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Number of Blade.
		@param NumberofBlade Number of Blade	  */
	public void setNumberofBlade (int NumberofBlade)
	{
		set_Value (COLUMNNAME_NumberofBlade, Integer.valueOf(NumberofBlade));
	}

	/** Get Number of Blade.
		@return Number of Blade	  */
	public int getNumberofBlade () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumberofBlade);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Original Class.
		@param OriginalClass Original Class	  */
	public void setOriginalClass (String OriginalClass)
	{
		set_Value (COLUMNNAME_OriginalClass, OriginalClass);
	}

	/** Get Original Class.
		@return Original Class	  */
	public String getOriginalClass () 
	{
		return (String)get_Value(COLUMNNAME_OriginalClass);
	}

	/** Set Particular Sign.
		@param ParticularSign Particular Sign	  */
	public void setParticularSign (String ParticularSign)
	{
		set_Value (COLUMNNAME_ParticularSign, ParticularSign);
	}

	/** Get Particular Sign.
		@return Particular Sign	  */
	public String getParticularSign () 
	{
		return (String)get_Value(COLUMNNAME_ParticularSign);
	}

	/** Set Port of Registry.
		@param PortofRegistry Port of Registry	  */
	public void setPortofRegistry (String PortofRegistry)
	{
		set_Value (COLUMNNAME_PortofRegistry, PortofRegistry);
	}

	/** Get Port of Registry.
		@return Port of Registry	  */
	public String getPortofRegistry () 
	{
		return (String)get_Value(COLUMNNAME_PortofRegistry);
	}

	/** Set Qty Of Machine.
		@param QtyOfMachine Qty Of Machine	  */
	public void setQtyOfMachine (int QtyOfMachine)
	{
		set_Value (COLUMNNAME_QtyOfMachine, Integer.valueOf(QtyOfMachine));
	}

	/** Get Qty Of Machine.
		@return Qty Of Machine	  */
	public int getQtyOfMachine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyOfMachine);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Qty of Machine .
		@param QtyofMachineGear Qty of Machine 	  */
	public void setQtyofMachineGear (int QtyofMachineGear)
	{
		set_Value (COLUMNNAME_QtyofMachineGear, Integer.valueOf(QtyofMachineGear));
	}

	/** Get Qty of Machine .
		@return Qty of Machine 	  */
	public int getQtyofMachineGear () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyofMachineGear);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Qty of Machine.
		@param QtyofMachineMain Qty of Machine	  */
	public void setQtyofMachineMain (int QtyofMachineMain)
	{
		set_Value (COLUMNNAME_QtyofMachineMain, Integer.valueOf(QtyofMachineMain));
	}

	/** Get Qty of Machine.
		@return Qty of Machine	  */
	public int getQtyofMachineMain () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyofMachineMain);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ratio.
		@param Ratio 
		Relative Ratio for Distributions
	  */
	public void setRatio (String Ratio)
	{
		set_Value (COLUMNNAME_Ratio, Ratio);
	}

	/** Get Ratio.
		@return Relative Ratio for Distributions
	  */
	public String getRatio () 
	{
		return (String)get_Value(COLUMNNAME_Ratio);
	}

	/** Set Registration Sign.
		@param RegistrationSign Registration Sign	  */
	public void setRegistrationSign (String RegistrationSign)
	{
		set_Value (COLUMNNAME_RegistrationSign, RegistrationSign);
	}

	/** Get Registration Sign.
		@return Registration Sign	  */
	public String getRegistrationSign () 
	{
		return (String)get_Value(COLUMNNAME_RegistrationSign);
	}

	/** Set Reserve Fuel Capacity.
		@param ReserveFuelCapacity Reserve Fuel Capacity	  */
	public void setReserveFuelCapacity (BigDecimal ReserveFuelCapacity)
	{
		set_Value (COLUMNNAME_ReserveFuelCapacity, ReserveFuelCapacity);
	}

	/** Get Reserve Fuel Capacity.
		@return Reserve Fuel Capacity	  */
	public BigDecimal getReserveFuelCapacity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReserveFuelCapacity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Fuel Tank Capacity.
		@param TotalFuelTankCapacity Total Fuel Tank Capacity	  */
	public void setTotalFuelTankCapacity (BigDecimal TotalFuelTankCapacity)
	{
		set_Value (COLUMNNAME_TotalFuelTankCapacity, TotalFuelTankCapacity);
	}

	/** Get Total Fuel Tank Capacity.
		@return Total Fuel Tank Capacity	  */
	public BigDecimal getTotalFuelTankCapacity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalFuelTankCapacity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Horse Power.
		@param TotalHorsePower Total Horse Power	  */
	public void setTotalHorsePower (BigDecimal TotalHorsePower)
	{
		set_Value (COLUMNNAME_TotalHorsePower, TotalHorsePower);
	}

	/** Get Total Horse Power.
		@return Total Horse Power	  */
	public BigDecimal getTotalHorsePower () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalHorsePower);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Horse Power.
		@param TotalHorsePowerAux Total Horse Power	  */
	public void setTotalHorsePowerAux (BigDecimal TotalHorsePowerAux)
	{
		set_Value (COLUMNNAME_TotalHorsePowerAux, TotalHorsePowerAux);
	}

	/** Get Total Horse Power.
		@return Total Horse Power	  */
	public BigDecimal getTotalHorsePowerAux () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalHorsePowerAux);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Propeller.
		@param TotalPropeller Total Propeller	  */
	public void setTotalPropeller (int TotalPropeller)
	{
		set_Value (COLUMNNAME_TotalPropeller, Integer.valueOf(TotalPropeller));
	}

	/** Get Total Propeller.
		@return Total Propeller	  */
	public int getTotalPropeller () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalPropeller);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Habco Primatama = HBC */
	public static final String TUGBOATOWNER_HabcoPrimatama = "HBC";
	/** Set TugBoat Owner.
		@param TugBoatOwner TugBoat Owner	  */
	public void setTugBoatOwner (String TugBoatOwner)
	{

		set_Value (COLUMNNAME_TugBoatOwner, TugBoatOwner);
	}

	/** Get TugBoat Owner.
		@return TugBoat Owner	  */
	public String getTugBoatOwner () 
	{
		return (String)get_Value(COLUMNNAME_TugBoatOwner);
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

	/** Set Width.
		@param Width Width	  */
	public void setWidth (BigDecimal Width)
	{
		set_Value (COLUMNNAME_Width, Width);
	}

	/** Get Width.
		@return Width	  */
	public BigDecimal getWidth () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Width);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Width (Feet).
		@param WidthFeet Width (Feet)	  */
	public void setWidthFeet (int WidthFeet)
	{
		set_Value (COLUMNNAME_WidthFeet, Integer.valueOf(WidthFeet));
	}

	/** Get Width (Feet).
		@return Width (Feet)	  */
	public int getWidthFeet () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WidthFeet);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}