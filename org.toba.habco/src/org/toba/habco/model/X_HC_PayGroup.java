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

/** Generated Model for HC_PayGroup
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_PayGroup extends PO implements I_HC_PayGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HC_PayGroup (Properties ctx, int HC_PayGroup_ID, String trxName)
    {
      super (ctx, HC_PayGroup_ID, trxName);
      /** if (HC_PayGroup_ID == 0)
        {
			setHC_PayGroup_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_PayGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HC_PayGroup[")
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

	/** Set Payroll Group.
		@param HC_PayGroup_ID Payroll Group	  */
	public void setHC_PayGroup_ID (int HC_PayGroup_ID)
	{
		if (HC_PayGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_PayGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_PayGroup_ID, Integer.valueOf(HC_PayGroup_ID));
	}

	/** Get Payroll Group.
		@return Payroll Group	  */
	public int getHC_PayGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_PayGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_PayGroup_ID()));
    }

	/** Set HC_PayGroup_UU.
		@param HC_PayGroup_UU HC_PayGroup_UU	  */
	public void setHC_PayGroup_UU (String HC_PayGroup_UU)
	{
		set_Value (COLUMNNAME_HC_PayGroup_UU, HC_PayGroup_UU);
	}

	/** Get HC_PayGroup_UU.
		@return HC_PayGroup_UU	  */
	public String getHC_PayGroup_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_PayGroup_UU);
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