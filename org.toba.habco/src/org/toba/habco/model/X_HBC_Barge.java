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

/** Generated Model for HBC_Barge
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Barge extends PO implements I_HBC_Barge, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161205L;

    /** Standard Constructor */
    public X_HBC_Barge (Properties ctx, int HBC_Barge_ID, String trxName)
    {
      super (ctx, HBC_Barge_ID, trxName);
      /** if (HBC_Barge_ID == 0)
        {
			setHBC_Barge_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_Barge (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_Barge[")
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

	/** Set Barge > 270.
		@param Barge270 Barge > 270	  */
	public void setBarge270 (boolean Barge270)
	{
		set_Value (COLUMNNAME_Barge270, Boolean.valueOf(Barge270));
	}

	/** Get Barge > 270.
		@return Barge > 270	  */
	public boolean isBarge270 () 
	{
		Object oo = get_Value(COLUMNNAME_Barge270);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Habco Primatama = HBC */
	public static final String BARGEOWNER_HabcoPrimatama = "HBC";
	/** Set Barge Owner.
		@param BargeOwner Barge Owner	  */
	public void setBargeOwner (String BargeOwner)
	{

		set_Value (COLUMNNAME_BargeOwner, BargeOwner);
	}

	/** Get Barge Owner.
		@return Barge Owner	  */
	public String getBargeOwner () 
	{
		return (String)get_Value(COLUMNNAME_BargeOwner);
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
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
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

	/** Set Feet.
		@param Feet Feet	  */
	public void setFeet (BigDecimal Feet)
	{
		set_Value (COLUMNNAME_Feet, Feet);
	}

	/** Get Feet.
		@return Feet	  */
	public BigDecimal getFeet () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Feet);
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

	/** Set Barge.
		@param HBC_Barge_ID Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID)
	{
		if (HBC_Barge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Barge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Barge_ID, Integer.valueOf(HBC_Barge_ID));
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

	/** Set HBC_Barge_UU.
		@param HBC_Barge_UU HBC_Barge_UU	  */
	public void setHBC_Barge_UU (String HBC_Barge_UU)
	{
		set_Value (COLUMNNAME_HBC_Barge_UU, HBC_Barge_UU);
	}

	/** Get HBC_Barge_UU.
		@return HBC_Barge_UU	  */
	public String getHBC_Barge_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Barge_UU);
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

	/** Set Length of Particular (M).
		@param LengthofParticular Length of Particular (M)	  */
	public void setLengthofParticular (BigDecimal LengthofParticular)
	{
		set_Value (COLUMNNAME_LengthofParticular, LengthofParticular);
	}

	/** Get Length of Particular (M).
		@return Length of Particular (M)	  */
	public BigDecimal getLengthofParticular () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LengthofParticular);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max Cargo.
		@param MaxCargo Max Cargo	  */
	public void setMaxCargo (BigDecimal MaxCargo)
	{
		set_Value (COLUMNNAME_MaxCargo, MaxCargo);
	}

	/** Get Max Cargo.
		@return Max Cargo	  */
	public BigDecimal getMaxCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxCargo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max Draft.
		@param MaxDraft Max Draft	  */
	public void setMaxDraft (BigDecimal MaxDraft)
	{
		set_Value (COLUMNNAME_MaxDraft, MaxDraft);
	}

	/** Get Max Draft.
		@return Max Draft	  */
	public BigDecimal getMaxDraft () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxDraft);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min Cargo.
		@param MinCargo Min Cargo	  */
	public void setMinCargo (BigDecimal MinCargo)
	{
		set_Value (COLUMNNAME_MinCargo, MinCargo);
	}

	/** Get Min Cargo.
		@return Min Cargo	  */
	public BigDecimal getMinCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinCargo);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	/** Set Side Board Tall.
		@param SideBoardTall Side Board Tall	  */
	public void setSideBoardTall (BigDecimal SideBoardTall)
	{
		set_Value (COLUMNNAME_SideBoardTall, SideBoardTall);
	}

	/** Get Side Board Tall.
		@return Side Board Tall	  */
	public BigDecimal getSideBoardTall () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SideBoardTall);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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