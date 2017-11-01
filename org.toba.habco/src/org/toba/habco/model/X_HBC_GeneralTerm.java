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

/** Generated Model for HBC_GeneralTerm
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_GeneralTerm extends PO implements I_HBC_GeneralTerm, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_GeneralTerm (Properties ctx, int HBC_GeneralTerm_ID, String trxName)
    {
      super (ctx, HBC_GeneralTerm_ID, trxName);
      /** if (HBC_GeneralTerm_ID == 0)
        {
			setHBC_GeneralTerm_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_GeneralTerm (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_GeneralTerm[")
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

	/** Set General Term.
		@param HBC_GeneralTerm_ID General Term	  */
	public void setHBC_GeneralTerm_ID (int HBC_GeneralTerm_ID)
	{
		if (HBC_GeneralTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_GeneralTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_GeneralTerm_ID, Integer.valueOf(HBC_GeneralTerm_ID));
	}

	/** Get General Term.
		@return General Term	  */
	public int getHBC_GeneralTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_GeneralTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_GeneralTerm_ID()));
    }

	/** Set HBC_GeneralTerm_UU.
		@param HBC_GeneralTerm_UU HBC_GeneralTerm_UU	  */
	public void setHBC_GeneralTerm_UU (String HBC_GeneralTerm_UU)
	{
		set_Value (COLUMNNAME_HBC_GeneralTerm_UU, HBC_GeneralTerm_UU);
	}

	/** Get HBC_GeneralTerm_UU.
		@return HBC_GeneralTerm_UU	  */
	public String getHBC_GeneralTerm_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_GeneralTerm_UU);
	}

	public I_HBC_TemplateGeneralTerm getHBC_TemplateGeneralTerm() throws RuntimeException
    {
		return (I_HBC_TemplateGeneralTerm)MTable.get(getCtx(), I_HBC_TemplateGeneralTerm.Table_Name)
			.getPO(getHBC_TemplateGeneralTerm_ID(), get_TrxName());	}

	/** Set Template General Term.
		@param HBC_TemplateGeneralTerm_ID Template General Term	  */
	public void setHBC_TemplateGeneralTerm_ID (int HBC_TemplateGeneralTerm_ID)
	{
		if (HBC_TemplateGeneralTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_TemplateGeneralTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_TemplateGeneralTerm_ID, Integer.valueOf(HBC_TemplateGeneralTerm_ID));
	}

	/** Get Template General Term.
		@return Template General Term	  */
	public int getHBC_TemplateGeneralTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_TemplateGeneralTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}