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

/** Generated Model for HBC_MainEngine
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_MainEngine extends PO implements I_HBC_MainEngine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161014L;

    /** Standard Constructor */
    public X_HBC_MainEngine (Properties ctx, int HBC_MainEngine_ID, String trxName)
    {
      super (ctx, HBC_MainEngine_ID, trxName);
      /** if (HBC_MainEngine_ID == 0)
        {
			setHBC_MainEngine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_MainEngine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_MainEngine[")
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

	/** Set Fuel Consumption Main.
		@param FuelConsumptionMain Fuel Consumption Main	  */
	public void setFuelConsumptionMain (BigDecimal FuelConsumptionMain)
	{
		set_Value (COLUMNNAME_FuelConsumptionMain, FuelConsumptionMain);
	}

	/** Get Fuel Consumption Main.
		@return Fuel Consumption Main	  */
	public BigDecimal getFuelConsumptionMain () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FuelConsumptionMain);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Main Engine.
		@param HBC_MainEngine_ID Main Engine	  */
	public void setHBC_MainEngine_ID (int HBC_MainEngine_ID)
	{
		if (HBC_MainEngine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_MainEngine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_MainEngine_ID, Integer.valueOf(HBC_MainEngine_ID));
	}

	/** Get Main Engine.
		@return Main Engine	  */
	public int getHBC_MainEngine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_MainEngine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_MainEngineType getHBC_MainEngineType() throws RuntimeException
    {
		return (I_HBC_MainEngineType)MTable.get(getCtx(), I_HBC_MainEngineType.Table_Name)
			.getPO(getHBC_MainEngineType_ID(), get_TrxName());	}

	/** Set Main Engine Type.
		@param HBC_MainEngineType_ID Main Engine Type	  */
	public void setHBC_MainEngineType_ID (int HBC_MainEngineType_ID)
	{
		if (HBC_MainEngineType_ID < 1) 
			set_Value (COLUMNNAME_HBC_MainEngineType_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_MainEngineType_ID, Integer.valueOf(HBC_MainEngineType_ID));
	}

	/** Get Main Engine Type.
		@return Main Engine Type	  */
	public int getHBC_MainEngineType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_MainEngineType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_MainEngine_UU.
		@param HBC_MainEngine_UU HBC_MainEngine_UU	  */
	public void setHBC_MainEngine_UU (String HBC_MainEngine_UU)
	{
		set_Value (COLUMNNAME_HBC_MainEngine_UU, HBC_MainEngine_UU);
	}

	/** Get HBC_MainEngine_UU.
		@return HBC_MainEngine_UU	  */
	public String getHBC_MainEngine_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_MainEngine_UU);
	}

	/** Set Horse Power.
		@param HPMainEngine Horse Power	  */
	public void setHPMainEngine (BigDecimal HPMainEngine)
	{
		set_Value (COLUMNNAME_HPMainEngine, HPMainEngine);
	}

	/** Get Horse Power.
		@return Horse Power	  */
	public BigDecimal getHPMainEngine () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HPMainEngine);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Oil Consumption.
		@param OilConsumptionMain Oil Consumption	  */
	public void setOilConsumptionMain (String OilConsumptionMain)
	{
		set_Value (COLUMNNAME_OilConsumptionMain, OilConsumptionMain);
	}

	/** Get Oil Consumption.
		@return Oil Consumption	  */
	public String getOilConsumptionMain () 
	{
		return (String)get_Value(COLUMNNAME_OilConsumptionMain);
	}

	/** Set Qty of Machine.
		@param QtyofMachineMain Qty of Machine	  */
	public void setQtyofMachineMain (BigDecimal QtyofMachineMain)
	{
		set_Value (COLUMNNAME_QtyofMachineMain, QtyofMachineMain);
	}

	/** Get Qty of Machine.
		@return Qty of Machine	  */
	public BigDecimal getQtyofMachineMain () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyofMachineMain);
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