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

/** Generated Model for HBC_PriceList_BBM
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_PriceList_BBM extends PO implements I_HBC_PriceList_BBM, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161012L;

    /** Standard Constructor */
    public X_HBC_PriceList_BBM (Properties ctx, int HBC_PriceList_BBM_ID, String trxName)
    {
      super (ctx, HBC_PriceList_BBM_ID, trxName);
      /** if (HBC_PriceList_BBM_ID == 0)
        {
			setHBC_PriceList_BBM_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_PriceList_BBM (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_PriceList_BBM[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_TaxRatePBBKB() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_TaxRatePBBKB_ID(), get_TrxName());	}

	/** Set Tax Rate PBBKB.
		@param C_TaxRatePBBKB_ID 
		Tax PBBKB identifier
	  */
	public void setC_TaxRatePBBKB_ID (int C_TaxRatePBBKB_ID)
	{
		if (C_TaxRatePBBKB_ID < 1) 
			set_Value (COLUMNNAME_C_TaxRatePBBKB_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxRatePBBKB_ID, Integer.valueOf(C_TaxRatePBBKB_ID));
	}

	/** Get Tax Rate PBBKB.
		@return Tax PBBKB identifier
	  */
	public int getC_TaxRatePBBKB_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxRatePBBKB_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_TaxRatePPH() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_TaxRatePPH_ID(), get_TrxName());	}

	/** Set Tax Rate PPH.
		@param C_TaxRatePPH_ID 
		Tax PPH identifier
	  */
	public void setC_TaxRatePPH_ID (int C_TaxRatePPH_ID)
	{
		if (C_TaxRatePPH_ID < 1) 
			set_Value (COLUMNNAME_C_TaxRatePPH_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxRatePPH_ID, Integer.valueOf(C_TaxRatePPH_ID));
	}

	/** Get Tax Rate PPH.
		@return Tax PPH identifier
	  */
	public int getC_TaxRatePPH_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxRatePPH_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_TaxRatePPN() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_TaxRatePPN_ID(), get_TrxName());	}

	/** Set Tax Rate PPN.
		@param C_TaxRatePPN_ID 
		Tax PPN identifier
	  */
	public void setC_TaxRatePPN_ID (int C_TaxRatePPN_ID)
	{
		if (C_TaxRatePPN_ID < 1) 
			set_Value (COLUMNNAME_C_TaxRatePPN_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxRatePPN_ID, Integer.valueOf(C_TaxRatePPN_ID));
	}

	/** Get Tax Rate PPN.
		@return Tax PPN identifier
	  */
	public int getC_TaxRatePPN_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxRatePPN_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_PriceList_BBM_ID()));
    }

	/** Set HBC_PriceList_BBM_UU.
		@param HBC_PriceList_BBM_UU HBC_PriceList_BBM_UU	  */
	public void setHBC_PriceList_BBM_UU (String HBC_PriceList_BBM_UU)
	{
		set_Value (COLUMNNAME_HBC_PriceList_BBM_UU, HBC_PriceList_BBM_UU);
	}

	/** Get HBC_PriceList_BBM_UU.
		@return HBC_PriceList_BBM_UU	  */
	public String getHBC_PriceList_BBM_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_PriceList_BBM_UU);
	}

	/** Set Index BBM.
		@param IndexBBM 
		Index for BBM
	  */
	public void setIndexBBM (BigDecimal IndexBBM)
	{
		set_Value (COLUMNNAME_IndexBBM, IndexBBM);
	}

	/** Get Index BBM.
		@return Index for BBM
	  */
	public BigDecimal getIndexBBM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IndexBBM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Set Second Index BBM.
	@param IndexBBM 
	Index for BBM
      */
	public void setSecondIndexBBM (BigDecimal IndexBBM)
	{
		set_Value (COLUMNNAME_SecondIndexBBM, IndexBBM);
	}
	
	/** Get Second Index BBM.
		@return Index for BBM
	  */
	public BigDecimal getSecondIndexBBM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SecondIndexBBM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set PBBKB Tax Amount.
		@param PBBKBTaxAmt 
		Tax Amount for PBBKB
	  */
	public void setPBBKBTaxAmt (BigDecimal PBBKBTaxAmt)
	{
		set_Value (COLUMNNAME_PBBKBTaxAmt, PBBKBTaxAmt);
	}

	/** Get PBBKB Tax Amount.
		@return Tax Amount for PBBKB
	  */
	public BigDecimal getPBBKBTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PBBKBTaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PPH Tax Amount.
		@param PPHTaxAmt 
		Tax Amount for PPH
	  */
	public void setPPHTaxAmt (BigDecimal PPHTaxAmt)
	{
		set_Value (COLUMNNAME_PPHTaxAmt, PPHTaxAmt);
	}

	/** Get PPH Tax Amount.
		@return Tax Amount for PPH
	  */
	public BigDecimal getPPHTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PPHTaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PPN Tax Amount.
		@param PPNTaxAmt 
		Tax Amount for PPN
	  */
	public void setPPNTaxAmt (BigDecimal PPNTaxAmt)
	{
		set_Value (COLUMNNAME_PPNTaxAmt, PPNTaxAmt);
	}

	/** Get PPN Tax Amount.
		@return Tax Amount for PPN
	  */
	public BigDecimal getPPNTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PPNTaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Previous Index BBM.
		@param PreviousIndexBBM 
		Previous Index for BBM
	  */
	public void setPreviousIndexBBM (BigDecimal PreviousIndexBBM)
	{
		set_Value (COLUMNNAME_PreviousIndexBBM, PreviousIndexBBM);
	}

	/** Get Previous Index BBM.
		@return Previous Index for BBM
	  */
	public BigDecimal getPreviousIndexBBM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreviousIndexBBM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_ValueNoCheck (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SubTotal.
		@param SubTotal 
		SubTotal amount of document header
	  */
	public void setSubTotal (BigDecimal SubTotal)
	{
		set_Value (COLUMNNAME_SubTotal, SubTotal);
	}

	/** Get SubTotal.
		@return SubTotal amount of document header
	  */
	public BigDecimal getSubTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SubTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax base Amount.
		@param TaxBaseAmt 
		Base for calculating the tax amount
	  */
	public void setTaxBaseAmt (BigDecimal TaxBaseAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	/** Get Tax base Amount.
		@return Base for calculating the tax amount
	  */
	public BigDecimal getTaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}