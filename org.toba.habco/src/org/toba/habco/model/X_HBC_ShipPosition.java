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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_ShipPosition
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipPosition extends PO implements I_HBC_ShipPosition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ShipPosition (Properties ctx, int HBC_ShipPosition_ID, String trxName)
    {
      super (ctx, HBC_ShipPosition_ID, trxName);
      /** if (HBC_ShipPosition_ID == 0)
        {
			setC_Project_ID (0);
			setHBC_Barge_ID (0);
			setHBC_Contract_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipPosition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipPosition[")
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

	/** Coal = C */
	public static final String CARGONAME_Coal = "C";
	/** Wood = W */
	public static final String CARGONAME_Wood = "W";
	/** - = - */
	public static final String CARGONAME__ = "-";
	/** Set Cargo Name.
		@param CargoName Cargo Name	  */
	public void setCargoName (String CargoName)
	{

		set_Value (COLUMNNAME_CargoName, CargoName);
	}

	/** Get Cargo Name.
		@return Cargo Name	  */
	public String getCargoName () 
	{
		return (String)get_Value(COLUMNNAME_CargoName);
	}

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
    {
		return (org.compiere.model.I_C_Project)MTable.get(getCtx(), org.compiere.model.I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date.
		@param Date1 
		Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Date.
		@return Date when business is not conducted
	  */
	public Timestamp getDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Date1);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	public I_HBC_Contract getHBC_Contract() throws RuntimeException
    {
		return (I_HBC_Contract)MTable.get(getCtx(), I_HBC_Contract.Table_Name)
			.getPO(getHBC_Contract_ID(), get_TrxName());	}

	/** Set Contract.
		@param HBC_Contract_ID Contract	  */
	public void setHBC_Contract_ID (int HBC_Contract_ID)
	{
		if (HBC_Contract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Contract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Contract_ID, Integer.valueOf(HBC_Contract_ID));
	}

	/** Get Contract.
		@return Contract	  */
	public int getHBC_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipPosition_ID()));
    }

	/** Set HBC_ShipPosition_UU.
		@param HBC_ShipPosition_UU HBC_ShipPosition_UU	  */
	public void setHBC_ShipPosition_UU (String HBC_ShipPosition_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipPosition_UU, HBC_ShipPosition_UU);
	}

	/** Get HBC_ShipPosition_UU.
		@return HBC_ShipPosition_UU	  */
	public String getHBC_ShipPosition_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipPosition_UU);
	}

	/** Barge Only = BOL */
	public static final String HBC_SHIPREQTYPE_BargeOnly = "BOL";
	/** TugBoat Only = TOL */
	public static final String HBC_SHIPREQTYPE_TugBoatOnly = "TOL";
	/** TugBoat & Barge = TNB */
	public static final String HBC_SHIPREQTYPE_TugBoatBarge = "TNB";
	/** No Ship = NOS */
	public static final String HBC_SHIPREQTYPE_NoShip = "NOS";
	/** Set Ship Required Type.
		@param HBC_ShipReqType Ship Required Type	  */
	public void setHBC_ShipReqType (String HBC_ShipReqType)
	{

		set_Value (COLUMNNAME_HBC_ShipReqType, HBC_ShipReqType);
	}

	/** Get Ship Required Type.
		@return Ship Required Type	  */
	public String getHBC_ShipReqType () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipReqType);
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

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException
    {
		return (I_HBC_Tugboat)MTable.get(getCtx(), I_HBC_Tugboat.Table_Name)
			.getPO(getHBC_Tugboat_ID(), get_TrxName());	}

	/** Set TugBoat.
		@param HBC_Tugboat_ID TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID)
	{
		if (HBC_Tugboat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Tugboat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Tugboat_ID, Integer.valueOf(HBC_Tugboat_ID));
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

	/** Set Signing Date.
		@param SigningDate Signing Date	  */
	public void setSigningDate (Timestamp SigningDate)
	{
		set_Value (COLUMNNAME_SigningDate, SigningDate);
	}

	/** Get Signing Date.
		@return Signing Date	  */
	public Timestamp getSigningDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SigningDate);
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