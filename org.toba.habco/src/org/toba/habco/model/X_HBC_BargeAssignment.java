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

/** Generated Model for HBC_BargeAssignment
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_BargeAssignment extends PO implements I_HBC_BargeAssignment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HBC_BargeAssignment (Properties ctx, int HBC_BargeAssignment_ID, String trxName)
    {
      super (ctx, HBC_BargeAssignment_ID, trxName);
      /** if (HBC_BargeAssignment_ID == 0)
        {
			setHBC_BargeAssignment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_BargeAssignment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_BargeAssignment[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
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

	/** Set Effective Date From.
		@param EffectiveDateFrom Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom)
	{
		set_Value (COLUMNNAME_EffectiveDateFrom, EffectiveDateFrom);
	}

	/** Get Effective Date From.
		@return Effective Date From	  */
	public Timestamp getEffectiveDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EffectiveDateFrom);
	}

	/** Set Barge Assignment.
		@param HBC_BargeAssignment_ID Barge Assignment	  */
	public void setHBC_BargeAssignment_ID (int HBC_BargeAssignment_ID)
	{
		if (HBC_BargeAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_BargeAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_BargeAssignment_ID, Integer.valueOf(HBC_BargeAssignment_ID));
	}

	/** Get Barge Assignment.
		@return Barge Assignment	  */
	public int getHBC_BargeAssignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_BargeAssignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_BargeAssignment_ID()));
    }

	/** Set HBC_BargeAssignment_UU.
		@param HBC_BargeAssignment_UU HBC_BargeAssignment_UU	  */
	public void setHBC_BargeAssignment_UU (String HBC_BargeAssignment_UU)
	{
		set_Value (COLUMNNAME_HBC_BargeAssignment_UU, HBC_BargeAssignment_UU);
	}

	/** Get HBC_BargeAssignment_UU.
		@return HBC_BargeAssignment_UU	  */
	public String getHBC_BargeAssignment_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_BargeAssignment_UU);
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

	/** Set Complete.
		@param IsComplete 
		It is complete
	  */
	public void setIsComplete (boolean IsComplete)
	{
		set_Value (COLUMNNAME_IsComplete, Boolean.valueOf(IsComplete));
	}

	/** Get Complete.
		@return It is complete
	  */
	public boolean isComplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsComplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fixed ?.
		@param IsFixed Fixed ?	  */
	public void setIsFixed (boolean IsFixed)
	{
		set_Value (COLUMNNAME_IsFixed, Boolean.valueOf(IsFixed));
	}

	/** Get Fixed ?.
		@return Fixed ?	  */
	public boolean isFixed () 
	{
		Object oo = get_Value(COLUMNNAME_IsFixed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}