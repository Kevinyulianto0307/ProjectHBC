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

/** Generated Model for HBC_ProductForApproval
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ProductForApproval extends PO implements I_HBC_ProductForApproval, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161004L;

    /** Standard Constructor */
    public X_HBC_ProductForApproval (Properties ctx, int HBC_ProductForApproval_ID, String trxName)
    {
      super (ctx, HBC_ProductForApproval_ID, trxName);
      /** if (HBC_ProductForApproval_ID == 0)
        {
			setHBC_ProductForApproval_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ProductForApproval (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HBC_ProductForApproval[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Request Approval = RA */
	public static final String DOCSTATUS_RequestApproval = "RA";
	/** Feedback = FD */
	public static final String DOCSTATUS_Feedback = "FD";
	/** Reject = RJ */
	public static final String DOCSTATUS_Reject = "RJ";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Product For Approval.
		@param HBC_ProductForApproval_ID Product For Approval	  */
	public void setHBC_ProductForApproval_ID (int HBC_ProductForApproval_ID)
	{
		if (HBC_ProductForApproval_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ProductForApproval_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ProductForApproval_ID, Integer.valueOf(HBC_ProductForApproval_ID));
	}

	/** Get Product For Approval.
		@return Product For Approval	  */
	public int getHBC_ProductForApproval_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ProductForApproval_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ProductForApproval_ID()));
    }

	/** Set HBC_ProductForApproval_UU.
		@param HBC_ProductForApproval_UU HBC_ProductForApproval_UU	  */
	public void setHBC_ProductForApproval_UU (String HBC_ProductForApproval_UU)
	{
		set_Value (COLUMNNAME_HBC_ProductForApproval_UU, HBC_ProductForApproval_UU);
	}

	/** Get HBC_ProductForApproval_UU.
		@return HBC_ProductForApproval_UU	  */
	public String getHBC_ProductForApproval_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ProductForApproval_UU);
	}
}