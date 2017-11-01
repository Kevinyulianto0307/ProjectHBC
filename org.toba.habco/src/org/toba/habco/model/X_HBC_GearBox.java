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

/** Generated Model for HBC_GearBox
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_GearBox extends PO implements I_HBC_GearBox, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_GearBox (Properties ctx, int HBC_GearBox_ID, String trxName)
    {
      super (ctx, HBC_GearBox_ID, trxName);
      /** if (HBC_GearBox_ID == 0)
        {
			setHBC_GearBox_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_GearBox (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_GearBox[")
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

	public I_HBC_EngineBrand getHBC_EngineBrand() throws RuntimeException
    {
		return (I_HBC_EngineBrand)MTable.get(getCtx(), I_HBC_EngineBrand.Table_Name)
			.getPO(getHBC_EngineBrand_ID(), get_TrxName());	}

	/** Set Engine Brand.
		@param HBC_EngineBrand_ID Engine Brand	  */
	public void setHBC_EngineBrand_ID (int HBC_EngineBrand_ID)
	{
		if (HBC_EngineBrand_ID < 1) 
			set_Value (COLUMNNAME_HBC_EngineBrand_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_EngineBrand_ID, Integer.valueOf(HBC_EngineBrand_ID));
	}

	/** Get Engine Brand.
		@return Engine Brand	  */
	public int getHBC_EngineBrand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_EngineBrand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gear Box.
		@param HBC_GearBox_ID Gear Box	  */
	public void setHBC_GearBox_ID (int HBC_GearBox_ID)
	{
		if (HBC_GearBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_GearBox_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_GearBox_ID, Integer.valueOf(HBC_GearBox_ID));
	}

	/** Get Gear Box.
		@return Gear Box	  */
	public int getHBC_GearBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_GearBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_GearboxType getHBC_GearboxType() throws RuntimeException
    {
		return (I_HBC_GearboxType)MTable.get(getCtx(), I_HBC_GearboxType.Table_Name)
			.getPO(getHBC_GearboxType_ID(), get_TrxName());	}

	/** Set Gear Box Type.
		@param HBC_GearboxType_ID Gear Box Type	  */
	public void setHBC_GearboxType_ID (int HBC_GearboxType_ID)
	{
		if (HBC_GearboxType_ID < 1) 
			set_Value (COLUMNNAME_HBC_GearboxType_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_GearboxType_ID, Integer.valueOf(HBC_GearboxType_ID));
	}

	/** Get Gear Box Type.
		@return Gear Box Type	  */
	public int getHBC_GearboxType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_GearboxType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_GearBox_UU.
		@param HBC_GearBox_UU HBC_GearBox_UU	  */
	public void setHBC_GearBox_UU (String HBC_GearBox_UU)
	{
		set_Value (COLUMNNAME_HBC_GearBox_UU, HBC_GearBox_UU);
	}

	/** Get HBC_GearBox_UU.
		@return HBC_GearBox_UU	  */
	public String getHBC_GearBox_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_GearBox_UU);
	}

	/** Set Oil Consumption.
		@param OilConsumptionGearbox Oil Consumption	  */
	public void setOilConsumptionGearbox (BigDecimal OilConsumptionGearbox)
	{
		set_Value (COLUMNNAME_OilConsumptionGearbox, OilConsumptionGearbox);
	}

	/** Get Oil Consumption.
		@return Oil Consumption	  */
	public BigDecimal getOilConsumptionGearbox () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OilConsumptionGearbox);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ratio.
		@param RatioGearBox Ratio	  */
	public void setRatioGearBox (String RatioGearBox)
	{
		set_Value (COLUMNNAME_RatioGearBox, RatioGearBox);
	}

	/** Get Ratio.
		@return Ratio	  */
	public String getRatioGearBox () 
	{
		return (String)get_Value(COLUMNNAME_RatioGearBox);
	}
}