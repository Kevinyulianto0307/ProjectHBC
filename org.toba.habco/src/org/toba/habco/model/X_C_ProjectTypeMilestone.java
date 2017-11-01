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

/** Generated Model for C_ProjectTypeMilestone
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_C_ProjectTypeMilestone extends PO implements I_C_ProjectTypeMilestone, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161103L;

    /** Standard Constructor */
    public X_C_ProjectTypeMilestone (Properties ctx, int C_ProjectTypeMilestone_ID, String trxName)
    {
      super (ctx, C_ProjectTypeMilestone_ID, trxName);
      /** if (C_ProjectTypeMilestone_ID == 0)
        {
			setC_ProjectTypeMilestone_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_ProjectTypeMilestone (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_ProjectTypeMilestone[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Project Type Milestone.
		@param C_ProjectTypeMilestone_ID Project Type Milestone	  */
	public void setC_ProjectTypeMilestone_ID (int C_ProjectTypeMilestone_ID)
	{
		if (C_ProjectTypeMilestone_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTypeMilestone_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTypeMilestone_ID, Integer.valueOf(C_ProjectTypeMilestone_ID));
	}

	/** Get Project Type Milestone.
		@return Project Type Milestone	  */
	public int getC_ProjectTypeMilestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectTypeMilestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_ProjectTypeMilestone_UU.
		@param C_ProjectTypeMilestone_UU C_ProjectTypeMilestone_UU	  */
	public void setC_ProjectTypeMilestone_UU (String C_ProjectTypeMilestone_UU)
	{
		set_Value (COLUMNNAME_C_ProjectTypeMilestone_UU, C_ProjectTypeMilestone_UU);
	}

	/** Get C_ProjectTypeMilestone_UU.
		@return C_ProjectTypeMilestone_UU	  */
	public String getC_ProjectTypeMilestone_UU () 
	{
		return (String)get_Value(COLUMNNAME_C_ProjectTypeMilestone_UU);
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

	/** Set Validate Milestone Percentage.
		@param IsValidateMilestone 
		Validate Milestone Percentage
	  */
	public void setIsValidateMilestone (boolean IsValidateMilestone)
	{
		set_Value (COLUMNNAME_IsValidateMilestone, Boolean.valueOf(IsValidateMilestone));
	}

	/** Get Validate Milestone Percentage.
		@return Validate Milestone Percentage
	  */
	public boolean isValidateMilestone () 
	{
		Object oo = get_Value(COLUMNNAME_IsValidateMilestone);
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

	/** ProjectCategory AD_Reference_ID=288 */
	public static final int PROJECTCATEGORY_AD_Reference_ID=288;
	/** General = N */
	public static final String PROJECTCATEGORY_General = "N";
	/** Asset Project = A */
	public static final String PROJECTCATEGORY_AssetProject = "A";
	/** Work Order (Job) = W */
	public static final String PROJECTCATEGORY_WorkOrderJob = "W";
	/** Service (Charge) Project = S */
	public static final String PROJECTCATEGORY_ServiceChargeProject = "S";
	/** Docking = DOC */
	public static final String PROJECTCATEGORY_Docking = "DOC";
	/** Annual Contract Release = ANC */
	public static final String PROJECTCATEGORY_AnnualContractRelease = "ANC";
	/** SPAL Contract Release = SPC */
	public static final String PROJECTCATEGORY_SPALContractRelease = "SPC";
	/** Stand by = STN */
	public static final String PROJECTCATEGORY_StandBy = "STN";
	/** New Ship = NES */
	public static final String PROJECTCATEGORY_NewShip = "NES";
	/** Time Charter = TMC */
	public static final String PROJECTCATEGORY_TimeCharter = "TMC";
	/** Set Project Category.
		@param ProjectCategory 
		Project Category
	  */
	public void setProjectCategory (String ProjectCategory)
	{

		set_Value (COLUMNNAME_ProjectCategory, ProjectCategory);
	}

	/** Get Project Category.
		@return Project Category
	  */
	public String getProjectCategory () 
	{
		return (String)get_Value(COLUMNNAME_ProjectCategory);
	}
}