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

/** Generated Model for HBC_ShipDocumentType
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipDocumentType extends PO implements I_HBC_ShipDocumentType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ShipDocumentType (Properties ctx, int HBC_ShipDocumentType_ID, String trxName)
    {
      super (ctx, HBC_ShipDocumentType_ID, trxName);
      /** if (HBC_ShipDocumentType_ID == 0)
        {
			setHBC_ShipDocumentType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipDocumentType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipDocumentType[")
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

	/** Set Ship Document Type.
		@param HBC_ShipDocumentType_ID Ship Document Type	  */
	public void setHBC_ShipDocumentType_ID (int HBC_ShipDocumentType_ID)
	{
		if (HBC_ShipDocumentType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentType_ID, Integer.valueOf(HBC_ShipDocumentType_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipDocumentType_ID()));
    }

	/** Set HBC_ShipDocumentType_UU.
		@param HBC_ShipDocumentType_UU HBC_ShipDocumentType_UU	  */
	public void setHBC_ShipDocumentType_UU (String HBC_ShipDocumentType_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipDocumentType_UU, HBC_ShipDocumentType_UU);
	}

	/** Get HBC_ShipDocumentType_UU.
		@return HBC_ShipDocumentType_UU	  */
	public String getHBC_ShipDocumentType_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipDocumentType_UU);
	}

	/** HUBLA = HB */
	public static final String ISSUEDBY_HUBLA = "HB";
	/** SYAHBANDAR = SB */
	public static final String ISSUEDBY_SYAHBANDAR = "SB";
	/** BIRO KLASIFIKASI INDONESIA = BKI */
	public static final String ISSUEDBY_BIROKLASIFIKASIINDONESIA = "BKI";
	/** KOMINFO = KI */
	public static final String ISSUEDBY_KOMINFO = "KI";
	/** Set Issued By.
		@param IssuedBy Issued By	  */
	public void setIssuedBy (String IssuedBy)
	{

		set_Value (COLUMNNAME_IssuedBy, IssuedBy);
	}

	/** Get Issued By.
		@return Issued By	  */
	public String getIssuedBy () 
	{
		return (String)get_Value(COLUMNNAME_IssuedBy);
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