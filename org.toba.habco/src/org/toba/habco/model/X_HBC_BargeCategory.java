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

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_BargeCategory
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_BargeCategory extends PO implements I_HBC_BargeCategory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_BargeCategory (Properties ctx, int HBC_BargeCategory_ID, String trxName)
    {
      super (ctx, HBC_BargeCategory_ID, trxName);
      /** if (HBC_BargeCategory_ID == 0)
        {
			setHBC_BargeCategory_ID (0);
			setMaxLength (0);
			setMinLength (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_BargeCategory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_BargeCategory[")
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

	/** Set Barge Category.
		@param HBC_BargeCategory_ID Barge Category	  */
	public void setHBC_BargeCategory_ID (int HBC_BargeCategory_ID)
	{
		if (HBC_BargeCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_BargeCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_BargeCategory_ID, Integer.valueOf(HBC_BargeCategory_ID));
	}

	/** Get Barge Category.
		@return Barge Category	  */
	public int getHBC_BargeCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_BargeCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_BargeCategory_ID()));
    }

	/** Set HBC_BargeCategory_UU.
		@param HBC_BargeCategory_UU HBC_BargeCategory_UU	  */
	public void setHBC_BargeCategory_UU (String HBC_BargeCategory_UU)
	{
		set_Value (COLUMNNAME_HBC_BargeCategory_UU, HBC_BargeCategory_UU);
	}

	/** Get HBC_BargeCategory_UU.
		@return HBC_BargeCategory_UU	  */
	public String getHBC_BargeCategory_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_BargeCategory_UU);
	}

	/** Set Maximum Length.
		@param MaxLength 
		Maximum Length of Data
	  */
	public void setMaxLength (int MaxLength)
	{
		set_Value (COLUMNNAME_MaxLength, Integer.valueOf(MaxLength));
	}

	/** Get Maximum Length.
		@return Maximum Length of Data
	  */
	public int getMaxLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Minimum Length.
		@param MinLength Minimum Length	  */
	public void setMinLength (int MinLength)
	{
		set_Value (COLUMNNAME_MinLength, Integer.valueOf(MinLength));
	}

	/** Get Minimum Length.
		@return Minimum Length	  */
	public int getMinLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MinLength);
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