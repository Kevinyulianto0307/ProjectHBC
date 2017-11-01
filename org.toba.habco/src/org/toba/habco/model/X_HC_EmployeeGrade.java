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
import org.compiere.util.KeyNamePair;

/** Generated Model for HC_EmployeeGrade
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_EmployeeGrade extends PO implements I_HC_EmployeeGrade, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HC_EmployeeGrade (Properties ctx, int HC_EmployeeGrade_ID, String trxName)
    {
      super (ctx, HC_EmployeeGrade_ID, trxName);
      /** if (HC_EmployeeGrade_ID == 0)
        {
			setHC_EmployeeGrade_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_EmployeeGrade (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HC_EmployeeGrade[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
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

	/** Set Employee Grade.
		@param HC_EmployeeGrade_ID Employee Grade	  */
	public void setHC_EmployeeGrade_ID (int HC_EmployeeGrade_ID)
	{
		if (HC_EmployeeGrade_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeGrade_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeGrade_ID, Integer.valueOf(HC_EmployeeGrade_ID));
	}

	/** Get Employee Grade.
		@return Employee Grade	  */
	public int getHC_EmployeeGrade_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_EmployeeGrade_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_EmployeeGrade_ID()));
    }

	/** Set HC_EmployeeGrade_UU.
		@param HC_EmployeeGrade_UU HC_EmployeeGrade_UU	  */
	public void setHC_EmployeeGrade_UU (String HC_EmployeeGrade_UU)
	{
		set_Value (COLUMNNAME_HC_EmployeeGrade_UU, HC_EmployeeGrade_UU);
	}

	/** Get HC_EmployeeGrade_UU.
		@return HC_EmployeeGrade_UU	  */
	public String getHC_EmployeeGrade_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_EmployeeGrade_UU);
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

	/** Set Salary Max.
		@param SalaryMax Salary Max	  */
	public void setSalaryMax (BigDecimal SalaryMax)
	{
		set_Value (COLUMNNAME_SalaryMax, SalaryMax);
	}

	/** Get Salary Max.
		@return Salary Max	  */
	public BigDecimal getSalaryMax () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryMax);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Salary Min.
		@param SalaryMin Salary Min	  */
	public void setSalaryMin (BigDecimal SalaryMin)
	{
		set_Value (COLUMNNAME_SalaryMin, SalaryMin);
	}

	/** Get Salary Min.
		@return Salary Min	  */
	public BigDecimal getSalaryMin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SalaryMin);
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