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

/** Generated Model for HBC_APPriceList
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_APPriceList extends PO implements I_HBC_APPriceList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161121L;

    /** Standard Constructor */
    public X_HBC_APPriceList (Properties ctx, int HBC_APPriceList_ID, String trxName)
    {
      super (ctx, HBC_APPriceList_ID, trxName);
      /** if (HBC_APPriceList_ID == 0)
        {
			setHBC_APPriceList_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_APPriceList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_APPriceList[")
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_APPriceList_ID()));
    }

	/** Set HBC_APPriceList_UU.
		@param HBC_APPriceList_UU HBC_APPriceList_UU	  */
	public void setHBC_APPriceList_UU (String HBC_APPriceList_UU)
	{
		set_Value (COLUMNNAME_HBC_APPriceList_UU, HBC_APPriceList_UU);
	}

	/** Get HBC_APPriceList_UU.
		@return HBC_APPriceList_UU	  */
	public String getHBC_APPriceList_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_APPriceList_UU);
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
}