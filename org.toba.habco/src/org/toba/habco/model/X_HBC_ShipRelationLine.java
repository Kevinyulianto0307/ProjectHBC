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

/** Generated Model for HBC_ShipRelationLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipRelationLine extends PO implements I_HBC_ShipRelationLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ShipRelationLine (Properties ctx, int HBC_ShipRelationLine_ID, String trxName)
    {
      super (ctx, HBC_ShipRelationLine_ID, trxName);
      /** if (HBC_ShipRelationLine_ID == 0)
        {
			setHBC_ShipRelationLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipRelationLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipRelationLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Active From.
		@param ActiveFrom Active From	  */
	public void setActiveFrom (Timestamp ActiveFrom)
	{
		set_Value (COLUMNNAME_ActiveFrom, ActiveFrom);
	}

	/** Get Active From.
		@return Active From	  */
	public Timestamp getActiveFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActiveFrom);
	}

	/** Set Active To.
		@param ActiveTo Active To	  */
	public void setActiveTo (Timestamp ActiveTo)
	{
		set_Value (COLUMNNAME_ActiveTo, ActiveTo);
	}

	/** Get Active To.
		@return Active To	  */
	public Timestamp getActiveTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActiveTo);
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

	public I_HBC_ShipRelation getHBC_ShipRelation() throws RuntimeException
    {
		return (I_HBC_ShipRelation)MTable.get(getCtx(), I_HBC_ShipRelation.Table_Name)
			.getPO(getHBC_ShipRelation_ID(), get_TrxName());	}

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

	/** Set Ship Relation Line.
		@param HBC_ShipRelationLine_ID Ship Relation Line	  */
	public void setHBC_ShipRelationLine_ID (int HBC_ShipRelationLine_ID)
	{
		if (HBC_ShipRelationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipRelationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipRelationLine_ID, Integer.valueOf(HBC_ShipRelationLine_ID));
	}

	/** Get Ship Relation Line.
		@return Ship Relation Line	  */
	public int getHBC_ShipRelationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipRelationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipRelationLine_ID()));
    }

	/** Set HBC_ShipRelationLine_UU.
		@param HBC_ShipRelationLine_UU HBC_ShipRelationLine_UU	  */
	public void setHBC_ShipRelationLine_UU (String HBC_ShipRelationLine_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipRelationLine_UU, HBC_ShipRelationLine_UU);
	}

	/** Get HBC_ShipRelationLine_UU.
		@return HBC_ShipRelationLine_UU	  */
	public String getHBC_ShipRelationLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipRelationLine_UU);
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

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}