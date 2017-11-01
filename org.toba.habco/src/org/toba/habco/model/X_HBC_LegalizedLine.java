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

/** Generated Model for HBC_LegalizedLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_LegalizedLine extends PO implements I_HBC_LegalizedLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161101L;

    /** Standard Constructor */
    public X_HBC_LegalizedLine (Properties ctx, int HBC_LegalizedLine_ID, String trxName)
    {
      super (ctx, HBC_LegalizedLine_ID, trxName);
      /** if (HBC_LegalizedLine_ID == 0)
        {
			setHBC_LegalizedLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_LegalizedLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_LegalizedLine[")
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

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
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

	/** Set Endorse Date.
		@param EndorseDate Endorse Date	  */
	public void setEndorseDate (Timestamp EndorseDate)
	{
		set_Value (COLUMNNAME_EndorseDate, EndorseDate);
	}

	/** Get Endorse Date.
		@return Endorse Date	  */
	public Timestamp getEndorseDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndorseDate);
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

	/** Set HBC_LegalizedLine.
		@param HBC_LegalizedLine_ID HBC_LegalizedLine	  */
	public void setHBC_LegalizedLine_ID (int HBC_LegalizedLine_ID)
	{
		if (HBC_LegalizedLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_LegalizedLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_LegalizedLine_ID, Integer.valueOf(HBC_LegalizedLine_ID));
	}

	/** Get HBC_LegalizedLine.
		@return HBC_LegalizedLine	  */
	public int getHBC_LegalizedLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_LegalizedLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_LegalizedLine_ID()));
    }

	/** Set HBC_LegalizedLine_UU.
		@param HBC_LegalizedLine_UU HBC_LegalizedLine_UU	  */
	public void setHBC_LegalizedLine_UU (String HBC_LegalizedLine_UU)
	{
		set_Value (COLUMNNAME_HBC_LegalizedLine_UU, HBC_LegalizedLine_UU);
	}

	/** Get HBC_LegalizedLine_UU.
		@return HBC_LegalizedLine_UU	  */
	public String getHBC_LegalizedLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_LegalizedLine_UU);
	}

	public I_HBC_ShipDocumentLine getHBC_ShipDocumentLine() throws RuntimeException
    {
		return (I_HBC_ShipDocumentLine)MTable.get(getCtx(), I_HBC_ShipDocumentLine.Table_Name)
			.getPO(getHBC_ShipDocumentLine_ID(), get_TrxName());	}

	/** Set Ship Document Line.
		@param HBC_ShipDocumentLine_ID Ship Document Line	  */
	public void setHBC_ShipDocumentLine_ID (int HBC_ShipDocumentLine_ID)
	{
		if (HBC_ShipDocumentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentLine_ID, Integer.valueOf(HBC_ShipDocumentLine_ID));
	}

	/** Get Ship Document Line.
		@return Ship Document Line	  */
	public int getHBC_ShipDocumentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocumentLine_ID);
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

	/** Set Is District.
		@param IsDistrict Is District	  */
	public void setIsDistrict (boolean IsDistrict)
	{
		set_Value (COLUMNNAME_IsDistrict, Boolean.valueOf(IsDistrict));
	}

	/** Get Is District.
		@return Is District	  */
	public boolean isDistrict () 
	{
		Object oo = get_Value(COLUMNNAME_IsDistrict);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Legalized.
		@param IsLegalized Legalized	  */
	public void setIsLegalized (boolean IsLegalized)
	{
		set_Value (COLUMNNAME_IsLegalized, Boolean.valueOf(IsLegalized));
	}

	/** Get Legalized.
		@return Legalized	  */
	public boolean isLegalized () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PIC.
		@param PicName PIC	  */
	public void setPicName (String PicName)
	{
		set_Value (COLUMNNAME_PicName, PicName);
	}

	/** Get PIC.
		@return PIC	  */
	public String getPicName () 
	{
		return (String)get_Value(COLUMNNAME_PicName);
	}
}