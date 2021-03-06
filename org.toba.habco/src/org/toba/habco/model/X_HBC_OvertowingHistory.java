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

/** Generated Model for HBC_OvertowingHistory
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_OvertowingHistory extends PO implements I_HBC_OvertowingHistory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_OvertowingHistory (Properties ctx, int HBC_OvertowingHistory_ID, String trxName)
    {
      super (ctx, HBC_OvertowingHistory_ID, trxName);
      /** if (HBC_OvertowingHistory_ID == 0)
        {
			setHBC_OvertowingHistory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_OvertowingHistory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_OvertowingHistory[")
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

	/** Set Overtowing History.
		@param HBC_OvertowingHistory_ID Overtowing History	  */
	public void setHBC_OvertowingHistory_ID (int HBC_OvertowingHistory_ID)
	{
		if (HBC_OvertowingHistory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_OvertowingHistory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_OvertowingHistory_ID, Integer.valueOf(HBC_OvertowingHistory_ID));
	}

	/** Get Overtowing History.
		@return Overtowing History	  */
	public int getHBC_OvertowingHistory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_OvertowingHistory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_OvertowingHistory_ID()));
    }

	/** Set HBC_OvertowingHistory_UU.
		@param HBC_OvertowingHistory_UU HBC_OvertowingHistory_UU	  */
	public void setHBC_OvertowingHistory_UU (String HBC_OvertowingHistory_UU)
	{
		set_Value (COLUMNNAME_HBC_OvertowingHistory_UU, HBC_OvertowingHistory_UU);
	}

	/** Get HBC_OvertowingHistory_UU.
		@return HBC_OvertowingHistory_UU	  */
	public String getHBC_OvertowingHistory_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_OvertowingHistory_UU);
	}

	public I_HBC_ShipPosition getHBC_ShipPosition() throws RuntimeException
    {
		return (I_HBC_ShipPosition)MTable.get(getCtx(), I_HBC_ShipPosition.Table_Name)
			.getPO(getHBC_ShipPosition_ID(), get_TrxName());	}

	/** Set Ship Position.
		@param HBC_ShipPosition_ID Ship Position	  */
	public void setHBC_ShipPosition_ID (int HBC_ShipPosition_ID)
	{
		if (HBC_ShipPosition_ID < 1) 
			set_Value (COLUMNNAME_HBC_ShipPosition_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_ShipPosition_ID, Integer.valueOf(HBC_ShipPosition_ID));
	}

	/** Get Ship Position.
		@return Ship Position	  */
	public int getHBC_ShipPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_ShipPositionLine getHBC_ShipPositionLine() throws RuntimeException
    {
		return (I_HBC_ShipPositionLine)MTable.get(getCtx(), I_HBC_ShipPositionLine.Table_Name)
			.getPO(getHBC_ShipPositionLine_ID(), get_TrxName());	}

	/** Set Ship Position Line.
		@param HBC_ShipPositionLine_ID Ship Position Line	  */
	public void setHBC_ShipPositionLine_ID (int HBC_ShipPositionLine_ID)
	{
		if (HBC_ShipPositionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipPositionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipPositionLine_ID, Integer.valueOf(HBC_ShipPositionLine_ID));
	}

	/** Get Ship Position Line.
		@return Ship Position Line	  */
	public int getHBC_ShipPositionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipPositionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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