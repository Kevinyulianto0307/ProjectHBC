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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_ShipRelation
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipRelation extends PO implements I_HBC_ShipRelation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ShipRelation (Properties ctx, int HBC_ShipRelation_ID, String trxName)
    {
      super (ctx, HBC_ShipRelation_ID, trxName);
      /** if (HBC_ShipRelation_ID == 0)
        {
			setHBC_ShipRelation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipRelation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipRelation[")
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

	/** Set Effective Date From.
		@param EffectiveDateFrom Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom)
	{
		set_Value (COLUMNNAME_EffectiveDateFrom, EffectiveDateFrom);
	}

	/** Get Effective Date From.
		@return Effective Date From	  */
	public Timestamp getEffectiveDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EffectiveDateFrom);
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

	/** Set Ship Relation.
		@param HBC_ShipRelation_ID Ship Relation	  */
	public void setHBC_ShipRelation_ID (int HBC_ShipRelation_ID)
	{
		if (HBC_ShipRelation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipRelation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipRelation_ID, Integer.valueOf(HBC_ShipRelation_ID));
	}

	/** Get Ship Relation.
		@return Ship Relation	  */
	public int getHBC_ShipRelation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipRelation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipRelation_ID()));
    }

	/** Set HBC_ShipRelation_UU.
		@param HBC_ShipRelation_UU HBC_ShipRelation_UU	  */
	public void setHBC_ShipRelation_UU (String HBC_ShipRelation_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipRelation_UU, HBC_ShipRelation_UU);
	}

	/** Get HBC_ShipRelation_UU.
		@return HBC_ShipRelation_UU	  */
	public String getHBC_ShipRelation_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipRelation_UU);
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
}