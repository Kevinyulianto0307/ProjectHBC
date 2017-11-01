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

/** Generated Model for HBC_PortPosition
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_PortPosition extends PO implements I_HBC_PortPosition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_PortPosition (Properties ctx, int HBC_PortPosition_ID, String trxName)
    {
      super (ctx, HBC_PortPosition_ID, trxName);
      /** if (HBC_PortPosition_ID == 0)
        {
			setHBC_PortPosition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_PortPosition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_PortPosition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getAgent_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getAgent_BPartner_ID(), get_TrxName());	}

	/** Set Agent.
		@param Agent_BPartner_ID Agent	  */
	public void setAgent_BPartner_ID (int Agent_BPartner_ID)
	{
		if (Agent_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Agent_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Agent_BPartner_ID, Integer.valueOf(Agent_BPartner_ID));
	}

	/** Get Agent.
		@return Agent	  */
	public int getAgent_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Agent_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Coordinates.
		@param Coordinates 
		Location coordinate
	  */
	public void setCoordinates (String Coordinates)
	{
		set_Value (COLUMNNAME_Coordinates, Coordinates);
	}

	/** Get Coordinates.
		@return Location coordinate
	  */
	public String getCoordinates () 
	{
		return (String)get_Value(COLUMNNAME_Coordinates);
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

	/** Set District.
		@param District District	  */
	public void setDistrict (String District)
	{
		set_Value (COLUMNNAME_District, District);
	}

	/** Get District.
		@return District	  */
	public String getDistrict () 
	{
		return (String)get_Value(COLUMNNAME_District);
	}

	/** Set KM (From Banjarmasin).
		@param HBC_Coordinates KM (From Banjarmasin)	  */
	public void setHBC_Coordinates (BigDecimal HBC_Coordinates)
	{
		set_Value (COLUMNNAME_HBC_Coordinates, HBC_Coordinates);
	}

	/** Get KM (From Banjarmasin).
		@return KM (From Banjarmasin)	  */
	public BigDecimal getHBC_Coordinates () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HBC_Coordinates);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_HBC_Jetty getHBC_Jetty() throws RuntimeException
    {
		return (I_HBC_Jetty)MTable.get(getCtx(), I_HBC_Jetty.Table_Name)
			.getPO(getHBC_Jetty_ID(), get_TrxName());	}

	/** Set Jetty.
		@param HBC_Jetty_ID Jetty	  */
	public void setHBC_Jetty_ID (int HBC_Jetty_ID)
	{
		if (HBC_Jetty_ID < 1) 
			set_Value (COLUMNNAME_HBC_Jetty_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Jetty_ID, Integer.valueOf(HBC_Jetty_ID));
	}

	/** Get Jetty.
		@return Jetty	  */
	public int getHBC_Jetty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Jetty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set HBC_PortPosition_UU.
		@param HBC_PortPosition_UU HBC_PortPosition_UU	  */
	public void setHBC_PortPosition_UU (String HBC_PortPosition_UU)
	{
		set_Value (COLUMNNAME_HBC_PortPosition_UU, HBC_PortPosition_UU);
	}

	/** Get HBC_PortPosition_UU.
		@return HBC_PortPosition_UU	  */
	public String getHBC_PortPosition_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_PortPosition_UU);
	}

	/** Barito's River = R */
	public static final String HBC_POSITIONTYPE_BaritoSRiver = "R";
	/** Sea = S */
	public static final String HBC_POSITIONTYPE_Sea = "S";
	/** Set Position Type.
		@param HBC_PositionType Position Type	  */
	public void setHBC_PositionType (String HBC_PositionType)
	{

		set_Value (COLUMNNAME_HBC_PositionType, HBC_PositionType);
	}

	/** Get Position Type.
		@return Position Type	  */
	public String getHBC_PositionType () 
	{
		return (String)get_Value(COLUMNNAME_HBC_PositionType);
	}

	/** Set Latitude.
		@param Latitude Latitude	  */
	public void setLatitude (String Latitude)
	{
		set_Value (COLUMNNAME_Latitude, Latitude);
	}

	/** Get Latitude.
		@return Latitude	  */
	public String getLatitude () 
	{
		return (String)get_Value(COLUMNNAME_Latitude);
	}

	/** Set Longitude.
		@param Longitude Longitude	  */
	public void setLongitude (String Longitude)
	{
		set_Value (COLUMNNAME_Longitude, Longitude);
	}

	/** Get Longitude.
		@return Longitude	  */
	public String getLongitude () 
	{
		return (String)get_Value(COLUMNNAME_Longitude);
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

	/** Set Province.
		@param Province Province	  */
	public void setProvince (String Province)
	{
		set_Value (COLUMNNAME_Province, Province);
	}

	/** Get Province.
		@return Province	  */
	public String getProvince () 
	{
		return (String)get_Value(COLUMNNAME_Province);
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