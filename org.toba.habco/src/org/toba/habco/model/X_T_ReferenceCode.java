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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for T_ReferenceCode
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_T_ReferenceCode extends PO implements I_T_ReferenceCode, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161019L;

    /** Standard Constructor */
    public X_T_ReferenceCode (Properties ctx, int T_ReferenceCode_ID, String trxName)
    {
      super (ctx, T_ReferenceCode_ID, trxName);
      /** if (T_ReferenceCode_ID == 0)
        {
			setT_ReferenceCode_ID (0);
        } */
    }

    /** Load Constructor */
    public X_T_ReferenceCode (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_T_ReferenceCode[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Reference Code.
		@param ReferenceCode Reference Code	  */
	public void setReferenceCode (int ReferenceCode)
	{
		set_Value (COLUMNNAME_ReferenceCode, Integer.valueOf(ReferenceCode));
	}

	/** Get Reference Code.
		@return Reference Code	  */
	public int getReferenceCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReferenceCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference Code.
		@param T_ReferenceCode_ID Reference Code	  */
	public void setT_ReferenceCode_ID (int T_ReferenceCode_ID)
	{
		if (T_ReferenceCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_T_ReferenceCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_T_ReferenceCode_ID, Integer.valueOf(T_ReferenceCode_ID));
	}

	/** Get Reference Code.
		@return Reference Code	  */
	public int getT_ReferenceCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_ReferenceCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getT_ReferenceCode_ID()));
    }

	/** Set T_ReferenceCode_UU.
		@param T_ReferenceCode_UU T_ReferenceCode_UU	  */
	public void setT_ReferenceCode_UU (String T_ReferenceCode_UU)
	{
		set_Value (COLUMNNAME_T_ReferenceCode_UU, T_ReferenceCode_UU);
	}

	/** Get T_ReferenceCode_UU.
		@return T_ReferenceCode_UU	  */
	public String getT_ReferenceCode_UU () 
	{
		return (String)get_Value(COLUMNNAME_T_ReferenceCode_UU);
	}
}