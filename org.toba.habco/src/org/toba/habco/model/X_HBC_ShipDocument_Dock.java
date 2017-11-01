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

/** Generated Model for HBC_ShipDocument_Dock
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipDocument_Dock extends PO implements I_HBC_ShipDocument_Dock, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170318L;

    /** Standard Constructor */
    public X_HBC_ShipDocument_Dock (Properties ctx, int HBC_ShipDocument_Dock_ID, String trxName)
    {
      super (ctx, HBC_ShipDocument_Dock_ID, trxName);
      /** if (HBC_ShipDocument_Dock_ID == 0)
        {
			setHBC_ShipDocument_Dock_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipDocument_Dock (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipDocument_Dock[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getAgent_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getAgent_BPartner_ID(), get_TrxName());	}

	/** Set Agent.
		@param Agent_BPartner_ID Agent	  */
	public void setAgent_BPartner_ID (int Agent_BPartner_ID)
	{
		if (Agent_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Agent_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Agent_BPartner_ID, Integer.valueOf(Agent_BPartner_ID));
	}

	/** Get Agent.
		@return Agent	  */
	public int getAgent_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Agent_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Ship Document Dock.
		@param HBC_ShipDocument_Dock_ID Ship Document Dock	  */
	public void setHBC_ShipDocument_Dock_ID (int HBC_ShipDocument_Dock_ID)
	{
		if (HBC_ShipDocument_Dock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocument_Dock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocument_Dock_ID, Integer.valueOf(HBC_ShipDocument_Dock_ID));
	}

	/** Get Ship Document Dock.
		@return Ship Document Dock	  */
	public int getHBC_ShipDocument_Dock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocument_Dock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipDocument_Dock_ID()));
    }

	/** Set HBC_ShipDocument_Dock_UU.
		@param HBC_ShipDocument_Dock_UU HBC_ShipDocument_Dock_UU	  */
	public void setHBC_ShipDocument_Dock_UU (String HBC_ShipDocument_Dock_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipDocument_Dock_UU, HBC_ShipDocument_Dock_UU);
	}

	/** Get HBC_ShipDocument_Dock_UU.
		@return HBC_ShipDocument_Dock_UU	  */
	public String getHBC_ShipDocument_Dock_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipDocument_Dock_UU);
	}

	/** Set Ship Document Type.
		@param HBC_ShipDocumentType_ID Ship Document Type	  */
	public void setHBC_ShipDocumentType_ID (int HBC_ShipDocumentType_ID)
	{
		if (HBC_ShipDocumentType_ID < 1) 
			set_Value (COLUMNNAME_HBC_ShipDocumentType_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_ShipDocumentType_ID, Integer.valueOf(HBC_ShipDocumentType_ID));
	}

	/** Get Ship Document Type.
		@return Ship Document Type	  */
	public int getHBC_ShipDocumentType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocumentType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IssuedInstitution.
		@param IssuedInstitution_ID IssuedInstitution	  */
	public void setIssuedInstitution_ID (int IssuedInstitution_ID)
	{
		if (IssuedInstitution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IssuedInstitution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IssuedInstitution_ID, Integer.valueOf(IssuedInstitution_ID));
	}

	/** Get IssuedInstitution.
		@return IssuedInstitution	  */
	public int getIssuedInstitution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IssuedInstitution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}