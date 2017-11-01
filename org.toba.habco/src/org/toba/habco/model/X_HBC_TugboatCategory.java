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

/** Generated Model for HBC_TugboatCategory
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_TugboatCategory extends PO implements I_HBC_TugboatCategory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161016L;

    /** Standard Constructor */
    public X_HBC_TugboatCategory (Properties ctx, int HBC_TugboatCategory_ID, String trxName)
    {
      super (ctx, HBC_TugboatCategory_ID, trxName);
      /** if (HBC_TugboatCategory_ID == 0)
        {
			setHBC_TugboatCategory_ID (0);
			setMaxHP (0);
// 0
			setMinHP (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_HBC_TugboatCategory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_TugboatCategory[")
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

	/** Set Tugboat Category.
		@param HBC_TugboatCategory_ID Tugboat Category	  */
	public void setHBC_TugboatCategory_ID (int HBC_TugboatCategory_ID)
	{
		if (HBC_TugboatCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_TugboatCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_TugboatCategory_ID, Integer.valueOf(HBC_TugboatCategory_ID));
	}

	/** Get Tugboat Category.
		@return Tugboat Category	  */
	public int getHBC_TugboatCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_TugboatCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_TugboatCategory_ID()));
    }

	/** Set HBC_TugboatCategory_UU.
		@param HBC_TugboatCategory_UU HBC_TugboatCategory_UU	  */
	public void setHBC_TugboatCategory_UU (String HBC_TugboatCategory_UU)
	{
		set_Value (COLUMNNAME_HBC_TugboatCategory_UU, HBC_TugboatCategory_UU);
	}

	/** Get HBC_TugboatCategory_UU.
		@return HBC_TugboatCategory_UU	  */
	public String getHBC_TugboatCategory_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_TugboatCategory_UU);
	}

	/** Set Max (HP).
		@param MaxHP Max (HP)	  */
	public void setMaxHP (int MaxHP)
	{
		set_Value (COLUMNNAME_MaxHP, Integer.valueOf(MaxHP));
	}

	/** Get Max (HP).
		@return Max (HP)	  */
	public int getMaxHP () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxHP);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Min (HP).
		@param MinHP Min (HP)	  */
	public void setMinHP (int MinHP)
	{
		set_Value (COLUMNNAME_MinHP, Integer.valueOf(MinHP));
	}

	/** Get Min (HP).
		@return Min (HP)	  */
	public int getMinHP () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MinHP);
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