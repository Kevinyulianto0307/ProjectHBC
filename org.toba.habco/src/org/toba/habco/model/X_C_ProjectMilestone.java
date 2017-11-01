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

/** Generated Model for C_ProjectMilestone
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_C_ProjectMilestone extends PO implements I_C_ProjectMilestone, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161104L;

    /** Standard Constructor */
    public X_C_ProjectMilestone (Properties ctx, int C_ProjectMilestone_ID, String trxName)
    {
      super (ctx, C_ProjectMilestone_ID, trxName);
      /** if (C_ProjectMilestone_ID == 0)
        {
			setC_ProjectMilestone_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(Max(SeqNo),0) + 10 FROM C_ProjectMilestone WHERE C_ProjectTypeMilestone_ID=@C_ProjectTypeMilestone_ID@
        } */
    }

    /** Load Constructor */
    public X_C_ProjectMilestone (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_ProjectMilestone[")
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

	/** Set Committed Amount.
		@param CommittedAmt 
		The (legal) commitment amount
	  */
	public void setCommittedAmt (BigDecimal CommittedAmt)
	{
		set_Value (COLUMNNAME_CommittedAmt, CommittedAmt);
	}

	/** Get Committed Amount.
		@return The (legal) commitment amount
	  */
	public BigDecimal getCommittedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommittedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Cancel = CA */
	public static final String CONTRACTSTATUS_Cancel = "CA";
	/** Complete = CO */
	public static final String CONTRACTSTATUS_Complete = "CO";
	/** Draft = DR */
	public static final String CONTRACTSTATUS_Draft = "DR";
	/** Finish = FI */
	public static final String CONTRACTSTATUS_Finish = "FI";
	/** Set Contract Status.
		@param ContractStatus Contract Status	  */
	public void setContractStatus (String ContractStatus)
	{

		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	/** Get Contract Status.
		@return Contract Status	  */
	public String getContractStatus () 
	{
		return (String)get_Value(COLUMNNAME_ContractStatus);
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

	/** Set Project Milestone.
		@param C_ProjectMilestone_ID Project Milestone	  */
	public void setC_ProjectMilestone_ID (int C_ProjectMilestone_ID)
	{
		if (C_ProjectMilestone_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectMilestone_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectMilestone_ID, Integer.valueOf(C_ProjectMilestone_ID));
	}

	/** Get Project Milestone.
		@return Project Milestone	  */
	public int getC_ProjectMilestone_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectMilestone_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_ProjectMilestone_ID()));
    }

	/** Set C_ProjectMilestone_UU.
		@param C_ProjectMilestone_UU C_ProjectMilestone_UU	  */
	public void setC_ProjectMilestone_UU (String C_ProjectMilestone_UU)
	{
		set_Value (COLUMNNAME_C_ProjectMilestone_UU, C_ProjectMilestone_UU);
	}

	/** Get C_ProjectMilestone_UU.
		@return C_ProjectMilestone_UU	  */
	public String getC_ProjectMilestone_UU () 
	{
		return (String)get_Value(COLUMNNAME_C_ProjectMilestone_UU);
	}

	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase() throws RuntimeException
    {
		return (org.compiere.model.I_C_ProjectPhase)MTable.get(getCtx(), org.compiere.model.I_C_ProjectPhase.Table_Name)
			.getPO(getC_ProjectPhase_ID(), get_TrxName());	}

	/** Set Project Phase.
		@param C_ProjectPhase_ID 
		Phase of a Project
	  */
	public void setC_ProjectPhase_ID (int C_ProjectPhase_ID)
	{
		if (C_ProjectPhase_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, Integer.valueOf(C_ProjectPhase_ID));
	}

	/** Get Project Phase.
		@return Phase of a Project
	  */
	public int getC_ProjectPhase_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectPhase_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Final Milestone.
		@param IsFinalMilestone 
		Identify Final Milestone
	  */
	public void setIsFinalMilestone (boolean IsFinalMilestone)
	{
		set_Value (COLUMNNAME_IsFinalMilestone, Boolean.valueOf(IsFinalMilestone));
	}

	/** Get Final Milestone.
		@return Identify Final Milestone
	  */
	public boolean isFinalMilestone () 
	{
		Object oo = get_Value(COLUMNNAME_IsFinalMilestone);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Minimum Cargo.
		@param IsMinimumCargo 
		Identify Minimum Cargo 
	  */
	public void setIsMinimumCargo (boolean IsMinimumCargo)
	{
		set_Value (COLUMNNAME_IsMinimumCargo, Boolean.valueOf(IsMinimumCargo));
	}

	/** Get Minimum Cargo.
		@return Identify Minimum Cargo 
	  */
	public boolean isMinimumCargo () 
	{
		Object oo = get_Value(COLUMNNAME_IsMinimumCargo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Position From.
		@param IsPositionFrom 
		Position From
	  */
	public void setIsPositionFrom (boolean IsPositionFrom)
	{
		set_Value (COLUMNNAME_IsPositionFrom, Boolean.valueOf(IsPositionFrom));
	}

	/** Get Position From.
		@return Position From
	  */
	public boolean isPositionFrom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPositionFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Milestone Percentage.
		@param MilestonePercentage Milestone Percentage	  */
	public void setMilestonePercentage (BigDecimal MilestonePercentage)
	{
		set_Value (COLUMNNAME_MilestonePercentage, MilestonePercentage);
	}

	/** Get Milestone Percentage.
		@return Milestone Percentage	  */
	public BigDecimal getMilestonePercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MilestonePercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Subtract Line.
		@param SubtractLine Subtract Line	  */
	public void setSubtractLine (int SubtractLine)
	{
		set_Value (COLUMNNAME_SubtractLine, Integer.valueOf(SubtractLine));
	}

	/** Get Subtract Line.
		@return Subtract Line	  */
	public int getSubtractLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SubtractLine);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}