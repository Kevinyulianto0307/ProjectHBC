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

/** Generated Model for HBC_PriceList_BBMDetail
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_PriceList_BBMDetail extends PO implements I_HBC_PriceList_BBMDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161012L;

    /** Standard Constructor */
    public X_HBC_PriceList_BBMDetail (Properties ctx, int HBC_PriceList_BBMDetail_ID, String trxName)
    {
      super (ctx, HBC_PriceList_BBMDetail_ID, trxName);
      /** if (HBC_PriceList_BBMDetail_ID == 0)
        {
			setHBC_PriceList_BBMDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_PriceList_BBMDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_PriceList_BBMDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** 250 Feet = 250 */
	public static final String BARGECATEGORY_250Feet = "250";
	/** 300 Feet = 300 */
	public static final String BARGECATEGORY_300Feet = "300";
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Price List BBM Detail.
		@param HBC_PriceList_BBMDetail_ID Price List BBM Detail	  */
	public void setHBC_PriceList_BBMDetail_ID (int HBC_PriceList_BBMDetail_ID)
	{
		if (HBC_PriceList_BBMDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_PriceList_BBMDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_PriceList_BBMDetail_ID, Integer.valueOf(HBC_PriceList_BBMDetail_ID));
	}

	/** Get Price List BBM Detail.
		@return Price List BBM Detail	  */
	public int getHBC_PriceList_BBMDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_PriceList_BBMDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_PriceList_BBMDetail_ID()));
    }

	/** Set HBC_PriceList_BBMDetail_UU.
		@param HBC_PriceList_BBMDetail_UU HBC_PriceList_BBMDetail_UU	  */
	public void setHBC_PriceList_BBMDetail_UU (String HBC_PriceList_BBMDetail_UU)
	{
		set_Value (COLUMNNAME_HBC_PriceList_BBMDetail_UU, HBC_PriceList_BBMDetail_UU);
	}

	/** Get HBC_PriceList_BBMDetail_UU.
		@return HBC_PriceList_BBMDetail_UU	  */
	public String getHBC_PriceList_BBMDetail_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_PriceList_BBMDetail_UU);
	}

	public I_HBC_PriceList_BBM getHBC_PriceList_BBM() throws RuntimeException
    {
		return (I_HBC_PriceList_BBM)MTable.get(getCtx(), I_HBC_PriceList_BBM.Table_Name)
			.getPO(getHBC_PriceList_BBM_ID(), get_TrxName());	}

	/** Set Price List BBM.
		@param HBC_PriceList_BBM_ID Price List BBM	  */
	public void setHBC_PriceList_BBM_ID (int HBC_PriceList_BBM_ID)
	{
		if (HBC_PriceList_BBM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_PriceList_BBM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_PriceList_BBM_ID, Integer.valueOf(HBC_PriceList_BBM_ID));
	}

	/** Get Price List BBM.
		@return Price List BBM	  */
	public int getHBC_PriceList_BBM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_PriceList_BBM_ID);
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

	/** Set Standard Price.
		@param PriceStd 
		Standard Price
	  */
	public void setPriceStd (BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standard Price.
		@return Standard Price
	  */
	public BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
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
}