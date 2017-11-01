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

/** Generated Model for HC_Reason
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_Reason extends PO implements I_HC_Reason, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HC_Reason (Properties ctx, int HC_Reason_ID, String trxName)
    {
      super (ctx, HC_Reason_ID, trxName);
      /** if (HC_Reason_ID == 0)
        {
			setHC_Reason_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_Reason (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_HC_Reason[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set HC Reason.
		@param HC_Reason_ID HC Reason	  */
	public void setHC_Reason_ID (int HC_Reason_ID)
	{
		if (HC_Reason_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Reason_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Reason_ID, Integer.valueOf(HC_Reason_ID));
	}

	/** Get HC Reason.
		@return HC Reason	  */
	public int getHC_Reason_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Reason_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_Reason_ID()));
    }

	/** Set HC_Reason_UU.
		@param HC_Reason_UU HC_Reason_UU	  */
	public void setHC_Reason_UU (String HC_Reason_UU)
	{
		set_Value (COLUMNNAME_HC_Reason_UU, HC_Reason_UU);
	}

	/** Get HC_Reason_UU.
		@return HC_Reason_UU	  */
	public String getHC_Reason_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_Reason_UU);
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