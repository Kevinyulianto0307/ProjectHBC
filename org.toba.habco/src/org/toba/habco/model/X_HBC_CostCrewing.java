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

/** Generated Model for HBC_CostCrewing
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_CostCrewing extends PO implements I_HBC_CostCrewing, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161013L;

    /** Standard Constructor */
    public X_HBC_CostCrewing (Properties ctx, int HBC_CostCrewing_ID, String trxName)
    {
      super (ctx, HBC_CostCrewing_ID, trxName);
      /** if (HBC_CostCrewing_ID == 0)
        {
			setHBC_CostCrewing_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_CostCrewing (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_CostCrewing[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
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

	/** Set HBC_CostCrewing.
		@param HBC_CostCrewing_ID HBC_CostCrewing	  */
	public void setHBC_CostCrewing_ID (int HBC_CostCrewing_ID)
	{
		if (HBC_CostCrewing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_CostCrewing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_CostCrewing_ID, Integer.valueOf(HBC_CostCrewing_ID));
	}

	/** Get HBC_CostCrewing.
		@return HBC_CostCrewing	  */
	public int getHBC_CostCrewing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_CostCrewing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_CostCrewing_ID()));
    }

	/** Set HBC_CostCrewing_UU.
		@param HBC_CostCrewing_UU HBC_CostCrewing_UU	  */
	public void setHBC_CostCrewing_UU (String HBC_CostCrewing_UU)
	{
		set_Value (COLUMNNAME_HBC_CostCrewing_UU, HBC_CostCrewing_UU);
	}

	/** Get HBC_CostCrewing_UU.
		@return HBC_CostCrewing_UU	  */
	public String getHBC_CostCrewing_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_CostCrewing_UU);
	}

	public I_HBC_CrewOnOff getHBC_CrewOnOff() throws RuntimeException
    {
		return (I_HBC_CrewOnOff)MTable.get(getCtx(), I_HBC_CrewOnOff.Table_Name)
			.getPO(getHBC_CrewOnOff_ID(), get_TrxName());	}

	/** Set Crew On/Off.
		@param HBC_CrewOnOff_ID Crew On/Off	  */
	public void setHBC_CrewOnOff_ID (int HBC_CrewOnOff_ID)
	{
		if (HBC_CrewOnOff_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_CrewOnOff_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_CrewOnOff_ID, Integer.valueOf(HBC_CrewOnOff_ID));
	}

	/** Get Crew On/Off.
		@return Crew On/Off	  */
	public int getHBC_CrewOnOff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_CrewOnOff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Agent Invoice.
		@param IsAgentInvoices Agent Invoice	  */
	public void setIsAgentInvoices (boolean IsAgentInvoices)
	{
		set_Value (COLUMNNAME_IsAgentInvoices, Boolean.valueOf(IsAgentInvoices));
	}

	/** Get Agent Invoice.
		@return Agent Invoice	  */
	public boolean isAgentInvoices () 
	{
		Object oo = get_Value(COLUMNNAME_IsAgentInvoices);
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

	/** Set Reference Code.
		@param ReferenceCode Reference Code	  */
	public void setReferenceCode (String ReferenceCode)
	{
		set_Value (COLUMNNAME_ReferenceCode, ReferenceCode);
	}

	/** Get Reference Code.
		@return Reference Code	  */
	public String getReferenceCode () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceCode);
	}
}