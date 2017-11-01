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

/** Generated Model for HBC_ShipDocument
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipDocument extends PO implements I_HBC_ShipDocument, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ShipDocument (Properties ctx, int HBC_ShipDocument_ID, String trxName)
    {
      super (ctx, HBC_ShipDocument_ID, trxName);
      /** if (HBC_ShipDocument_ID == 0)
        {
			setHBC_ShipDocument_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipDocument (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ShipDocument[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_HBC_Barge getHBC_Barge() throws RuntimeException
    {
		return (I_HBC_Barge)MTable.get(getCtx(), I_HBC_Barge.Table_Name)
			.getPO(getHBC_Barge_ID(), get_TrxName());	}

	/** Set Barge.
		@param HBC_Barge_ID Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID)
	{
		if (HBC_Barge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_Barge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_Barge_ID, Integer.valueOf(HBC_Barge_ID));
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

	/** Set Ship Document.
		@param HBC_ShipDocument_ID Ship Document	  */
	public void setHBC_ShipDocument_ID (int HBC_ShipDocument_ID)
	{
		if (HBC_ShipDocument_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocument_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocument_ID, Integer.valueOf(HBC_ShipDocument_ID));
	}

	/** Get Ship Document.
		@return Ship Document	  */
	public int getHBC_ShipDocument_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocument_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipDocument_ID()));
    }

	/** Set HBC_ShipDocument_UU.
		@param HBC_ShipDocument_UU HBC_ShipDocument_UU	  */
	public void setHBC_ShipDocument_UU (String HBC_ShipDocument_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipDocument_UU, HBC_ShipDocument_UU);
	}

	/** Get HBC_ShipDocument_UU.
		@return HBC_ShipDocument_UU	  */
	public String getHBC_ShipDocument_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipDocument_UU);
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

	/** Set Legalized Annually.
		@param IsLegalizedAnnually Legalized Annually	  */
	public void setIsLegalizedAnnually (boolean IsLegalizedAnnually)
	{
		set_Value (COLUMNNAME_IsLegalizedAnnually, Boolean.valueOf(IsLegalizedAnnually));
	}

	/** Get Legalized Annually.
		@return Legalized Annually	  */
	public boolean isLegalizedAnnually () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalizedAnnually);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}