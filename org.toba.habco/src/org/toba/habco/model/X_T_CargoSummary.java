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

/** Generated Model for T_CargoSummary
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_T_CargoSummary extends PO implements I_T_CargoSummary, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170530L;

    /** Standard Constructor */
    public X_T_CargoSummary (Properties ctx, int T_CargoSummary_ID, String trxName)
    {
      super (ctx, T_CargoSummary_ID, trxName);
      /** if (T_CargoSummary_ID == 0)
        {
			setT_CargoSummary_ID (0);
        } */
    }

    /** Load Constructor */
    public X_T_CargoSummary (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_T_CargoSummary[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (org.compiere.model.I_AD_PInstance)MTable.get(getCtx(), org.compiere.model.I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Process Instance.
		@param AD_PInstance_ID 
		Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Process Instance.
		@return Instance of the process
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set bargename.
		@param bargename bargename	  */
	public void setbargename (String bargename)
	{
		set_Value (COLUMNNAME_bargename, bargename);
	}

	/** Get bargename.
		@return bargename	  */
	public String getbargename () 
	{
		return (String)get_Value(COLUMNNAME_bargename);
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set completed_loading.
		@param completed_loading completed_loading	  */
	public void setcompleted_loading (Timestamp completed_loading)
	{
		set_Value (COLUMNNAME_completed_loading, completed_loading);
	}

	/** Get completed_loading.
		@return completed_loading	  */
	public Timestamp getcompleted_loading () 
	{
		return (Timestamp)get_Value(COLUMNNAME_completed_loading);
	}

	/** Set completed_unloading.
		@param completed_unloading completed_unloading	  */
	public void setcompleted_unloading (Timestamp completed_unloading)
	{
		set_Value (COLUMNNAME_completed_unloading, completed_unloading);
	}

	/** Get completed_unloading.
		@return completed_unloading	  */
	public Timestamp getcompleted_unloading () 
	{
		return (Timestamp)get_Value(COLUMNNAME_completed_unloading);
	}

	/** Set customerlocation.
		@param customerlocation customerlocation	  */
	public void setcustomerlocation (String customerlocation)
	{
		set_Value (COLUMNNAME_customerlocation, customerlocation);
	}

	/** Get customerlocation.
		@return customerlocation	  */
	public String getcustomerlocation () 
	{
		return (String)get_Value(COLUMNNAME_customerlocation);
	}

	/** Set customername.
		@param customername customername	  */
	public void setcustomername (String customername)
	{
		set_Value (COLUMNNAME_customername, customername);
	}

	/** Get customername.
		@return customername	  */
	public String getcustomername () 
	{
		return (String)get_Value(COLUMNNAME_customername);
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set dischargingportname.
		@param dischargingportname dischargingportname	  */
	public void setdischargingportname (String dischargingportname)
	{
		set_Value (COLUMNNAME_dischargingportname, dischargingportname);
	}

	/** Get dischargingportname.
		@return dischargingportname	  */
	public String getdischargingportname () 
	{
		return (String)get_Value(COLUMNNAME_dischargingportname);
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

	/** Set LoadingDraftSurvey.
		@param LoadingDraftSurvey LoadingDraftSurvey	  */
	public void setLoadingDraftSurvey (BigDecimal LoadingDraftSurvey)
	{
		set_Value (COLUMNNAME_LoadingDraftSurvey, LoadingDraftSurvey);
	}

	/** Get LoadingDraftSurvey.
		@return LoadingDraftSurvey	  */
	public BigDecimal getLoadingDraftSurvey () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LoadingDraftSurvey);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set loadingportname.
		@param loadingportname loadingportname	  */
	public void setloadingportname (String loadingportname)
	{
		set_Value (COLUMNNAME_loadingportname, loadingportname);
	}

	/** Get loadingportname.
		@return loadingportname	  */
	public String getloadingportname () 
	{
		return (String)get_Value(COLUMNNAME_loadingportname);
	}

	/** Set T_CargoSummary.
		@param T_CargoSummary_ID T_CargoSummary	  */
	public void setT_CargoSummary_ID (int T_CargoSummary_ID)
	{
		if (T_CargoSummary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_T_CargoSummary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_T_CargoSummary_ID, Integer.valueOf(T_CargoSummary_ID));
	}

	/** Get T_CargoSummary.
		@return T_CargoSummary	  */
	public int getT_CargoSummary_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_CargoSummary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getT_CargoSummary_ID()));
    }

	/** Set T_CargoSummary_UU.
		@param T_CargoSummary_UU T_CargoSummary_UU	  */
	public void setT_CargoSummary_UU (String T_CargoSummary_UU)
	{
		set_Value (COLUMNNAME_T_CargoSummary_UU, T_CargoSummary_UU);
	}

	/** Get T_CargoSummary_UU.
		@return T_CargoSummary_UU	  */
	public String getT_CargoSummary_UU () 
	{
		return (String)get_Value(COLUMNNAME_T_CargoSummary_UU);
	}

	/** Set tonnage.
		@param tonnage tonnage	  */
	public void settonnage (BigDecimal tonnage)
	{
		set_Value (COLUMNNAME_tonnage, tonnage);
	}

	/** Get tonnage.
		@return tonnage	  */
	public BigDecimal gettonnage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_tonnage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set tugboatname.
		@param tugboatname tugboatname	  */
	public void settugboatname (String tugboatname)
	{
		set_Value (COLUMNNAME_tugboatname, tugboatname);
	}

	/** Get tugboatname.
		@return tugboatname	  */
	public String gettugboatname () 
	{
		return (String)get_Value(COLUMNNAME_tugboatname);
	}

	/** Set User Name.
		@param UserName User Name	  */
	public void setUserName (String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get User Name.
		@return User Name	  */
	public String getUserName () 
	{
		return (String)get_Value(COLUMNNAME_UserName);
	}

	/** Set userposition.
		@param userposition userposition	  */
	public void setuserposition (String userposition)
	{
		set_Value (COLUMNNAME_userposition, userposition);
	}

	/** Get userposition.
		@return userposition	  */
	public String getuserposition () 
	{
		return (String)get_Value(COLUMNNAME_userposition);
	}
}