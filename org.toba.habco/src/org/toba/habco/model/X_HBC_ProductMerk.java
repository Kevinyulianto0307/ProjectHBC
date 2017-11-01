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

/** Generated Model for HBC_ProductMerk
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ProductMerk extends PO implements I_HBC_ProductMerk, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ProductMerk (Properties ctx, int HBC_ProductMerk_ID, String trxName)
    {
      super (ctx, HBC_ProductMerk_ID, trxName);
      /** if (HBC_ProductMerk_ID == 0)
        {
			setHBC_ProductMerk_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ProductMerk (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ProductMerk[")
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

	/** Set Product Merk.
		@param HBC_ProductMerk_ID Product Merk	  */
	public void setHBC_ProductMerk_ID (int HBC_ProductMerk_ID)
	{
		if (HBC_ProductMerk_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ProductMerk_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ProductMerk_ID, Integer.valueOf(HBC_ProductMerk_ID));
	}

	/** Get Product Merk.
		@return Product Merk	  */
	public int getHBC_ProductMerk_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ProductMerk_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_ProductMerk_UU.
		@param HBC_ProductMerk_UU HBC_ProductMerk_UU	  */
	public void setHBC_ProductMerk_UU (String HBC_ProductMerk_UU)
	{
		set_Value (COLUMNNAME_HBC_ProductMerk_UU, HBC_ProductMerk_UU);
	}

	/** Get HBC_ProductMerk_UU.
		@return HBC_ProductMerk_UU	  */
	public String getHBC_ProductMerk_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ProductMerk_UU);
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