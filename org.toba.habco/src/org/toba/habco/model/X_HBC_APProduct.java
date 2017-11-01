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

/** Generated Model for HBC_APProduct
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_APProduct extends PO implements I_HBC_APProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161121L;

    /** Standard Constructor */
    public X_HBC_APProduct (Properties ctx, int HBC_APProduct_ID, String trxName)
    {
      super (ctx, HBC_APProduct_ID, trxName);
      /** if (HBC_APProduct_ID == 0)
        {
			setHBC_APProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_APProduct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_APProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_HBC_APPriceList getHBC_APPriceList() throws RuntimeException
    {
		return (I_HBC_APPriceList)MTable.get(getCtx(), I_HBC_APPriceList.Table_Name)
			.getPO(getHBC_APPriceList_ID(), get_TrxName());	}

	/** Set HBC_APPriceList.
		@param HBC_APPriceList_ID HBC_APPriceList	  */
	public void setHBC_APPriceList_ID (int HBC_APPriceList_ID)
	{
		if (HBC_APPriceList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_APPriceList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_APPriceList_ID, Integer.valueOf(HBC_APPriceList_ID));
	}

	/** Get HBC_APPriceList.
		@return HBC_APPriceList	  */
	public int getHBC_APPriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_APPriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_APProduct.
		@param HBC_APProduct_ID HBC_APProduct	  */
	public void setHBC_APProduct_ID (int HBC_APProduct_ID)
	{
		if (HBC_APProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_APProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_APProduct_ID, Integer.valueOf(HBC_APProduct_ID));
	}

	/** Get HBC_APProduct.
		@return HBC_APProduct	  */
	public int getHBC_APProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_APProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_APProduct_ID()));
    }

	/** Set HBC_APProduct_UU.
		@param HBC_APProduct_UU HBC_APProduct_UU	  */
	public void setHBC_APProduct_UU (String HBC_APProduct_UU)
	{
		set_Value (COLUMNNAME_HBC_APProduct_UU, HBC_APProduct_UU);
	}

	/** Get HBC_APProduct_UU.
		@return HBC_APProduct_UU	  */
	public String getHBC_APProduct_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_APProduct_UU);
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

	public I_M_Product_Version getM_Product_Version() throws RuntimeException
    {
		return (I_M_Product_Version)MTable.get(getCtx(), I_M_Product_Version.Table_Name)
			.getPO(getM_Product_Version_ID(), get_TrxName());	}

	/** Set M_Product_Version.
		@param M_Product_Version_ID M_Product_Version	  */
	public void setM_Product_Version_ID (int M_Product_Version_ID)
	{
		if (M_Product_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Version_ID, Integer.valueOf(M_Product_Version_ID));
	}

	/** Get M_Product_Version.
		@return M_Product_Version	  */
	public int getM_Product_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
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
}