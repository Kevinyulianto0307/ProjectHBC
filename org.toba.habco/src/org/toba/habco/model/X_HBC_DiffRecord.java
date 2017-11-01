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

/** Generated Model for HBC_DiffRecord
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_DiffRecord extends PO implements I_HBC_DiffRecord, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161230L;

    /** Standard Constructor */
    public X_HBC_DiffRecord (Properties ctx, int HBC_DiffRecord_ID, String trxName)
    {
      super (ctx, HBC_DiffRecord_ID, trxName);
      /** if (HBC_DiffRecord_ID == 0)
        {
			setHBC_DiffRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_DiffRecord (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_DiffRecord[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Fuel Allocation.
		@param FuelAllocation Fuel Allocation	  */
	public void setFuelAllocation (BigDecimal FuelAllocation)
	{
		set_Value (COLUMNNAME_FuelAllocation, FuelAllocation);
	}

	/** Get Fuel Allocation.
		@return Fuel Allocation	  */
	public BigDecimal getFuelAllocation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelAllocation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_FuelUsage getFuelUsage() throws RuntimeException
    {
		return (I_FuelUsage)MTable.get(getCtx(), I_FuelUsage.Table_Name)
			.getPO(getFuelUsage_ID(), get_TrxName());	}

	/** Set Fuel Usage.
		@param FuelUsage_ID Fuel Usage	  */
	public void setFuelUsage_ID (int FuelUsage_ID)
	{
		if (FuelUsage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_FuelUsage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_FuelUsage_ID, Integer.valueOf(FuelUsage_ID));
	}

	/** Get Fuel Usage.
		@return Fuel Usage	  */
	public int getFuelUsage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FuelUsage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC DiffRecord.
		@param HBC_DiffRecord_ID HBC DiffRecord	  */
	public void setHBC_DiffRecord_ID (int HBC_DiffRecord_ID)
	{
		if (HBC_DiffRecord_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_DiffRecord_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_DiffRecord_ID, Integer.valueOf(HBC_DiffRecord_ID));
	}

	/** Get HBC DiffRecord.
		@return HBC DiffRecord	  */
	public int getHBC_DiffRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_DiffRecord_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_DiffRecord_ID()));
    }

	/** Set HBC_DiffRecord_UU.
		@param HBC_DiffRecord_UU HBC_DiffRecord_UU	  */
	public void setHBC_DiffRecord_UU (String HBC_DiffRecord_UU)
	{
		set_Value (COLUMNNAME_HBC_DiffRecord_UU, HBC_DiffRecord_UU);
	}

	/** Get HBC_DiffRecord_UU.
		@return HBC_DiffRecord_UU	  */
	public String getHBC_DiffRecord_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_DiffRecord_UU);
	}
}