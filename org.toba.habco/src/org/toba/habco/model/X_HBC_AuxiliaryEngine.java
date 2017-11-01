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

/** Generated Model for HBC_AuxiliaryEngine
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_AuxiliaryEngine extends PO implements I_HBC_AuxiliaryEngine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_AuxiliaryEngine (Properties ctx, int HBC_AuxiliaryEngine_ID, String trxName)
    {
      super (ctx, HBC_AuxiliaryEngine_ID, trxName);
      /** if (HBC_AuxiliaryEngine_ID == 0)
        {
			setHBC_AuxiliaryEngine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_AuxiliaryEngine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_AuxiliaryEngine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Fuel Consumption Per Hour.
		@param FuelConsumptionPerHour Fuel Consumption Per Hour	  */
	public void setFuelConsumptionPerHour (BigDecimal FuelConsumptionPerHour)
	{
		set_Value (COLUMNNAME_FuelConsumptionPerHour, FuelConsumptionPerHour);
	}

	/** Get Fuel Consumption Per Hour.
		@return Fuel Consumption Per Hour	  */
	public BigDecimal getFuelConsumptionPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelConsumptionPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Auxiliary Engine.
		@param HBC_AuxiliaryEngine_ID Auxiliary Engine	  */
	public void setHBC_AuxiliaryEngine_ID (int HBC_AuxiliaryEngine_ID)
	{
		if (HBC_AuxiliaryEngine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_AuxiliaryEngine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_AuxiliaryEngine_ID, Integer.valueOf(HBC_AuxiliaryEngine_ID));
	}

	/** Get Auxiliary Engine.
		@return Auxiliary Engine	  */
	public int getHBC_AuxiliaryEngine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_AuxiliaryEngine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_AuxiliaryEngineType getHBC_AuxiliaryEngineType() throws RuntimeException
    {
		return (I_HBC_AuxiliaryEngineType)MTable.get(getCtx(), I_HBC_AuxiliaryEngineType.Table_Name)
			.getPO(getHBC_AuxiliaryEngineType_ID(), get_TrxName());	}

	/** Set Auxiliary Engine Type.
		@param HBC_AuxiliaryEngineType_ID Auxiliary Engine Type	  */
	public void setHBC_AuxiliaryEngineType_ID (int HBC_AuxiliaryEngineType_ID)
	{
		if (HBC_AuxiliaryEngineType_ID < 1) 
			set_Value (COLUMNNAME_HBC_AuxiliaryEngineType_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_AuxiliaryEngineType_ID, Integer.valueOf(HBC_AuxiliaryEngineType_ID));
	}

	/** Get Auxiliary Engine Type.
		@return Auxiliary Engine Type	  */
	public int getHBC_AuxiliaryEngineType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_AuxiliaryEngineType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_AuxiliaryEngine_UU.
		@param HBC_AuxiliaryEngine_UU HBC_AuxiliaryEngine_UU	  */
	public void setHBC_AuxiliaryEngine_UU (String HBC_AuxiliaryEngine_UU)
	{
		set_Value (COLUMNNAME_HBC_AuxiliaryEngine_UU, HBC_AuxiliaryEngine_UU);
	}

	/** Get HBC_AuxiliaryEngine_UU.
		@return HBC_AuxiliaryEngine_UU	  */
	public String getHBC_AuxiliaryEngine_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_AuxiliaryEngine_UU);
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

	/** Set Horse Power.
		@param HorsePower Horse Power	  */
	public void setHorsePower (BigDecimal HorsePower)
	{
		set_Value (COLUMNNAME_HorsePower, HorsePower);
	}

	/** Get Horse Power.
		@return Horse Power	  */
	public BigDecimal getHorsePower () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HorsePower);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Oil Consumption.
		@param OilConsumption Oil Consumption	  */
	public void setOilConsumption (BigDecimal OilConsumption)
	{
		set_Value (COLUMNNAME_OilConsumption, OilConsumption);
	}

	/** Get Oil Consumption.
		@return Oil Consumption	  */
	public BigDecimal getOilConsumption () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OilConsumption);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Of Machine.
		@param QtyOfMachine Qty Of Machine	  */
	public void setQtyOfMachine (BigDecimal QtyOfMachine)
	{
		set_Value (COLUMNNAME_QtyOfMachine, QtyOfMachine);
	}

	/** Get Qty Of Machine.
		@return Qty Of Machine	  */
	public BigDecimal getQtyOfMachine () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOfMachine);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Horse Power.
		@param TotalHorsePower Total Horse Power	  */
	public void setTotalHorsePower (BigDecimal TotalHorsePower)
	{
		set_Value (COLUMNNAME_TotalHorsePower, TotalHorsePower);
	}

	/** Get Total Horse Power.
		@return Total Horse Power	  */
	public BigDecimal getTotalHorsePower () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalHorsePower);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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