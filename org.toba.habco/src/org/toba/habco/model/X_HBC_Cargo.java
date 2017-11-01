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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_Cargo
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_Cargo extends PO implements I_HBC_Cargo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_Cargo (Properties ctx, int HBC_Cargo_ID, String trxName)
    {
      super (ctx, HBC_Cargo_ID, trxName);
      /** if (HBC_Cargo_ID == 0)
        {
			setHBC_Cargo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_Cargo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_Cargo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Activity getActivityCargo() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getActivityCargo_ID(), get_TrxName());	}

	/** Set Activity.
		@param ActivityCargo_ID Activity	  */
	public void setActivityCargo_ID (int ActivityCargo_ID)
	{
		if (ActivityCargo_ID < 1) 
			set_Value (COLUMNNAME_ActivityCargo_ID, null);
		else 
			set_Value (COLUMNNAME_ActivityCargo_ID, Integer.valueOf(ActivityCargo_ID));
	}

	/** Get Activity.
		@return Activity	  */
	public int getActivityCargo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ActivityCargo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount Of Cargo.
		@param AmountOfCargo Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo)
	{
		set_Value (COLUMNNAME_AmountOfCargo, AmountOfCargo);
	}

	/** Get Amount Of Cargo.
		@return Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountOfCargo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount of Cargo.
		@param AmtofCargo Amount of Cargo	  */
	public void setAmtofCargo (BigDecimal AmtofCargo)
	{
		set_Value (COLUMNNAME_AmtofCargo, AmtofCargo);
	}

	/** Get Amount of Cargo.
		@return Amount of Cargo	  */
	public BigDecimal getAmtofCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtofCargo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Balance Cargo.
		@param BalanceCargo Balance Cargo	  */
	public void setBalanceCargo (BigDecimal BalanceCargo)
	{
		set_Value (COLUMNNAME_BalanceCargo, BalanceCargo);
	}

	/** Get Balance Cargo.
		@return Balance Cargo	  */
	public BigDecimal getBalanceCargo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BalanceCargo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getFrom_PortPosition_ID(), get_TrxName());	}

	/** Set Port/Position From.
		@param From_PortPosition_ID Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID)
	{
		if (From_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_From_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_From_PortPosition_ID, Integer.valueOf(From_PortPosition_ID));
	}

	/** Get Port/Position From.
		@return Port/Position From	  */
	public int getFrom_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_From_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cargo.
		@param HBC_Cargo_ID Cargo	  */
	public void setHBC_Cargo_ID (int HBC_Cargo_ID)
	{
		if (HBC_Cargo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Cargo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Cargo_ID, Integer.valueOf(HBC_Cargo_ID));
	}

	/** Get Cargo.
		@return Cargo	  */
	public int getHBC_Cargo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Cargo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_Cargo_ID()));
    }

	/** Set HBC_Cargo_UU.
		@param HBC_Cargo_UU HBC_Cargo_UU	  */
	public void setHBC_Cargo_UU (String HBC_Cargo_UU)
	{
		set_Value (COLUMNNAME_HBC_Cargo_UU, HBC_Cargo_UU);
	}

	/** Get HBC_Cargo_UU.
		@return HBC_Cargo_UU	  */
	public String getHBC_Cargo_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_Cargo_UU);
	}

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getHBC_PortPosition_ID(), get_TrxName());	}

	/** Set Port / Position.
		@param HBC_PortPosition_ID Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID)
	{
		if (HBC_PortPosition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_PortPosition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_PortPosition_ID, Integer.valueOf(HBC_PortPosition_ID));
	}

	/** Get Port / Position.
		@return Port / Position	  */
	public int getHBC_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_HBC_ShipPosition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipPosition_ID, Integer.valueOf(HBC_ShipPosition_ID));
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

	public I_HBC_PortPosition getTo_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getTo_PortPosition_ID(), get_TrxName());	}

	/** Set Port/Position To.
		@param To_PortPosition_ID Port/Position To	  */
	public void setTo_PortPosition_ID (int To_PortPosition_ID)
	{
		if (To_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_To_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_To_PortPosition_ID, Integer.valueOf(To_PortPosition_ID));
	}

	/** Get Port/Position To.
		@return Port/Position To	  */
	public int getTo_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}