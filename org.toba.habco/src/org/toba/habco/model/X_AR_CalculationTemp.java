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

/** Generated Model for AR_CalculationTemp
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_AR_CalculationTemp extends PO implements I_AR_CalculationTemp, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161012L;

    /** Standard Constructor */
    public X_AR_CalculationTemp (Properties ctx, int AR_CalculationTemp_ID, String trxName)
    {
      super (ctx, AR_CalculationTemp_ID, trxName);
      /** if (AR_CalculationTemp_ID == 0)
        {
			setAR_CalculationTemp_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AR_CalculationTemp (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_AR_CalculationTemp[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set AR_CalculationTemp.
		@param AR_CalculationTemp_ID AR_CalculationTemp	  */
	public void setAR_CalculationTemp_ID (int AR_CalculationTemp_ID)
	{
		if (AR_CalculationTemp_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AR_CalculationTemp_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AR_CalculationTemp_ID, Integer.valueOf(AR_CalculationTemp_ID));
	}

	/** Get AR_CalculationTemp.
		@return AR_CalculationTemp	  */
	public int getAR_CalculationTemp_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AR_CalculationTemp_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAR_CalculationTemp_ID()));
    }

	/** Set AR_CalculationTemp_UU.
		@param AR_CalculationTemp_UU AR_CalculationTemp_UU	  */
	public void setAR_CalculationTemp_UU (String AR_CalculationTemp_UU)
	{
		set_Value (COLUMNNAME_AR_CalculationTemp_UU, AR_CalculationTemp_UU);
	}

	/** Get AR_CalculationTemp_UU.
		@return AR_CalculationTemp_UU	  */
	public String getAR_CalculationTemp_UU () 
	{
		return (String)get_Value(COLUMNNAME_AR_CalculationTemp_UU);
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

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
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

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getHBC_PortPosition_ID(), get_TrxName());	}

	/** Set Port / Position.
		@param HBC_PortPosition_ID Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID)
	{
		if (HBC_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_HBC_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_PortPosition_ID, Integer.valueOf(HBC_PortPosition_ID));
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

	public I_HBC_Position getHBC_Position() throws RuntimeException
    {
		return (I_HBC_Position)MTable.get(getCtx(), I_HBC_Position.Table_Name)
			.getPO(getHBC_Position_ID(), get_TrxName());	}

	/** Set Ship Position.
		@param HBC_Position_ID Ship Position	  */
	public void setHBC_Position_ID (int HBC_Position_ID)
	{
		if (HBC_Position_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Position_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Position_ID, Integer.valueOf(HBC_Position_ID));
	}

	/** Get Ship Position.
		@return Ship Position	  */
	public int getHBC_Position_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Position_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Trip getHBC_Trip() throws RuntimeException
    {
		return (I_HBC_Trip)MTable.get(getCtx(), I_HBC_Trip.Table_Name)
			.getPO(getHBC_Trip_ID(), get_TrxName());	}

	/** Set Trip.
		@param HBC_Trip_ID Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID)
	{
		if (HBC_Trip_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Trip_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Trip_ID, Integer.valueOf(HBC_Trip_ID));
	}

	/** Get Trip.
		@return Trip	  */
	public int getHBC_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Trip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
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

	/** Set Total Cargo Qty.
		@param TotalCargoQty Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty)
	{
		set_Value (COLUMNNAME_TotalCargoQty, TotalCargoQty);
	}

	/** Get Total Cargo Qty.
		@return Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalCargoQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}