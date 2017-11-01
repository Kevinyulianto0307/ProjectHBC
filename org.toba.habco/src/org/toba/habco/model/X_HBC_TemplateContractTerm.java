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

/** Generated Model for HBC_TemplateContractTerm
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_TemplateContractTerm extends PO implements I_HBC_TemplateContractTerm, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161016L;

    /** Standard Constructor */
    public X_HBC_TemplateContractTerm (Properties ctx, int HBC_TemplateContractTerm_ID, String trxName)
    {
      super (ctx, HBC_TemplateContractTerm_ID, trxName);
      /** if (HBC_TemplateContractTerm_ID == 0)
        {
			setHBC_TemplateContractTerm_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_TemplateContractTerm (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_HBC_TemplateContractTerm[")
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

	/** Set Template Contract Term.
		@param HBC_TemplateContractTerm_ID Template Contract Term	  */
	public void setHBC_TemplateContractTerm_ID (int HBC_TemplateContractTerm_ID)
	{
		if (HBC_TemplateContractTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_TemplateContractTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_TemplateContractTerm_ID, Integer.valueOf(HBC_TemplateContractTerm_ID));
	}

	/** Get Template Contract Term.
		@return Template Contract Term	  */
	public int getHBC_TemplateContractTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_TemplateContractTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_TemplateContractTerm_UU.
		@param HBC_TemplateContractTerm_UU HBC_TemplateContractTerm_UU	  */
	public void setHBC_TemplateContractTerm_UU (String HBC_TemplateContractTerm_UU)
	{
		set_Value (COLUMNNAME_HBC_TemplateContractTerm_UU, HBC_TemplateContractTerm_UU);
	}

	/** Get HBC_TemplateContractTerm_UU.
		@return HBC_TemplateContractTerm_UU	  */
	public String getHBC_TemplateContractTerm_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_TemplateContractTerm_UU);
	}

	/** Set Default Template.
		@param IsTemplate Default Template	  */
	public void setIsTemplate (boolean IsTemplate)
	{
		set_Value (COLUMNNAME_IsTemplate, Boolean.valueOf(IsTemplate));
	}

	/** Get Default Template.
		@return Default Template	  */
	public boolean isTemplate () 
	{
		Object oo = get_Value(COLUMNNAME_IsTemplate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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