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

/** Generated Model for HBC_Demurrage
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Demurrage extends PO implements I_HBC_Demurrage, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170802L;

    /** Standard Constructor */
    public X_HBC_Demurrage (Properties ctx, int HBC_Demurrage_ID, String trxName)
    {
      super (ctx, HBC_Demurrage_ID, trxName);
      /** if (HBC_Demurrage_ID == 0)
        {
			setHBC_Demurrage_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_Demurrage (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_Demurrage[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Active From.
		@param ActiveFrom Active From	  */
	public void setActiveFrom (Timestamp ActiveFrom)
	{
		set_Value (COLUMNNAME_ActiveFrom, ActiveFrom);
	}

	/** Get Active From.
		@return Active From	  */
	public Timestamp getActiveFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActiveFrom);
	}

	/** Set Active To.
		@param ActiveTo Active To	  */
	public void setActiveTo (Timestamp ActiveTo)
	{
		set_Value (COLUMNNAME_ActiveTo, ActiveTo);
	}

	/** Get Active To.
		@return Active To	  */
	public Timestamp getActiveTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActiveTo);
	}

	/** 250 Feet = 250 */
	public static final String BARGECATEGORY_250Feet = "250";
	/** 300 Feet = 300 */
	public static final String BARGECATEGORY_300Feet = "300";
	/** 180 Feet = 180 */
	public static final String BARGECATEGORY_180Feet = "180";
	/** 210 Feet = 210 */
	public static final String BARGECATEGORY_210Feet = "210";
	/** 230 Feet = 230 */
	public static final String BARGECATEGORY_230Feet = "230";
	/** 240 Feet = 240 */
	public static final String BARGECATEGORY_240Feet = "240";
	/** 270 Feet = 270 */
	public static final String BARGECATEGORY_270Feet = "270";
	/** Set BargeCategory.
		@param BargeCategory BargeCategory	  */
	public void setBargeCategory (String BargeCategory)
	{

		set_ValueNoCheck (COLUMNNAME_BargeCategory, BargeCategory);
	}

	/** Get BargeCategory.
		@return BargeCategory	  */
	public String getBargeCategory () 
	{
		return (String)get_Value(COLUMNNAME_BargeCategory);
	}

	public I_HBC_BargeCategory getBargeCategoryTo() throws RuntimeException
    {
		return (I_HBC_BargeCategory)MTable.get(getCtx(), I_HBC_BargeCategory.Table_Name)
			.getPO(getBargeCategoryTo_ID(), get_TrxName());	}

	/** Set Barge Category To.
		@param BargeCategoryTo_ID Barge Category To	  */
	public void setBargeCategoryTo_ID (int BargeCategoryTo_ID)
	{
		if (BargeCategoryTo_ID < 1) 
			set_Value (COLUMNNAME_BargeCategoryTo_ID, null);
		else 
			set_Value (COLUMNNAME_BargeCategoryTo_ID, Integer.valueOf(BargeCategoryTo_ID));
	}

	/** Get Barge Category To.
		@return Barge Category To	  */
	public int getBargeCategoryTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BargeCategoryTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Demurrage Amount.
		@param DemurrageAmt Demurrage Amount	  */
	public void setDemurrageAmt (BigDecimal DemurrageAmt)
	{
		set_Value (COLUMNNAME_DemurrageAmt, DemurrageAmt);
	}

	/** Get Demurrage Amount.
		@return Demurrage Amount	  */
	public BigDecimal getDemurrageAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DemurrageAmt);
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

	/** Set Freight Percentage.
		@param FreightPercentage Freight Percentage	  */
	public void setFreightPercentage (BigDecimal FreightPercentage)
	{
		set_Value (COLUMNNAME_FreightPercentage, FreightPercentage);
	}

	/** Get Freight Percentage.
		@return Freight Percentage	  */
	public BigDecimal getFreightPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FreightPercentage);
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

	/** Set Demurrage.
		@param HBC_Demurrage_ID Demurrage	  */
	public void setHBC_Demurrage_ID (int HBC_Demurrage_ID)
	{
		if (HBC_Demurrage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Demurrage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Demurrage_ID, Integer.valueOf(HBC_Demurrage_ID));
	}

	/** Get Demurrage.
		@return Demurrage	  */
	public int getHBC_Demurrage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Demurrage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_Demurrage_ID()));
    }

	/** Set HBC_Demurrage_UU.
		@param HBC_Demurrage_UU HBC_Demurrage_UU	  */
	public void setHBC_Demurrage_UU (String HBC_Demurrage_UU)
	{
		set_Value (COLUMNNAME_HBC_Demurrage_UU, HBC_Demurrage_UU);
	}

	/** Get HBC_Demurrage_UU.
		@return HBC_Demurrage_UU	  */
	public String getHBC_Demurrage_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Demurrage_UU);
	}

	/** Set Demurrage Category.
		@param HBC_DemurrageCategory_ID Demurrage Category	  */
	public void setHBC_DemurrageCategory_ID (int HBC_DemurrageCategory_ID)
	{
		if (HBC_DemurrageCategory_ID < 1) 
			set_Value (COLUMNNAME_HBC_DemurrageCategory_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_DemurrageCategory_ID, Integer.valueOf(HBC_DemurrageCategory_ID));
	}

	/** Get Demurrage Category.
		@return Demurrage Category	  */
	public int getHBC_DemurrageCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_DemurrageCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Prorate.
		@param IsProrate Prorate	  */
	public void setIsProrate (boolean IsProrate)
	{
		set_Value (COLUMNNAME_IsProrate, Boolean.valueOf(IsProrate));
	}

	/** Get Prorate.
		@return Prorate	  */
	public boolean isProrate () 
	{
		Object oo = get_Value(COLUMNNAME_IsProrate);
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

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prorate Day.
		@param ProrateDay Prorate Day	  */
	public void setProrateDay (int ProrateDay)
	{
		set_Value (COLUMNNAME_ProrateDay, Integer.valueOf(ProrateDay));
	}

	/** Get Prorate Day.
		@return Prorate Day	  */
	public int getProrateDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProrateDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Loading Day.
		@param TotalLoadingDay Total Loading Day	  */
	public void setTotalLoadingDay (BigDecimal TotalLoadingDay)
	{
		set_Value (COLUMNNAME_TotalLoadingDay, TotalLoadingDay);
	}

	/** Get Total Loading Day.
		@return Total Loading Day	  */
	public BigDecimal getTotalLoadingDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLoadingDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Unloading Day.
		@param TotalUnloadingDay Total Unloading Day	  */
	public void setTotalUnloadingDay (BigDecimal TotalUnloadingDay)
	{
		set_Value (COLUMNNAME_TotalUnloadingDay, TotalUnloadingDay);
	}

	/** Get Total Unloading Day.
		@return Total Unloading Day	  */
	public BigDecimal getTotalUnloadingDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalUnloadingDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weather Percentage.
		@param WeatherPercentage Weather Percentage	  */
	public void setWeatherPercentage (BigDecimal WeatherPercentage)
	{
		set_Value (COLUMNNAME_WeatherPercentage, WeatherPercentage);
	}

	/** Get Weather Percentage.
		@return Weather Percentage	  */
	public BigDecimal getWeatherPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WeatherPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}