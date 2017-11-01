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

/** Generated Model for HC_JobDataChange
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HC_JobDataChange extends PO implements I_HC_JobDataChange, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160929L;

    /** Standard Constructor */
    public X_HC_JobDataChange (Properties ctx, int HC_JobDataChange_ID, String trxName)
    {
      super (ctx, HC_JobDataChange_ID, trxName);
      /** if (HC_JobDataChange_ID == 0)
        {
			setHC_JobDataChange_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_JobDataChange (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HC_JobDataChange[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
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

	/** Set Compensation 1.
		@param HC_Compensation1 Compensation 1	  */
	public void setHC_Compensation1 (BigDecimal HC_Compensation1)
	{
		set_Value (COLUMNNAME_HC_Compensation1, HC_Compensation1);
	}

	/** Get Compensation 1.
		@return Compensation 1	  */
	public BigDecimal getHC_Compensation1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HC_Compensation1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Compensation 2.
		@param HC_Compensation2 Compensation 2	  */
	public void setHC_Compensation2 (BigDecimal HC_Compensation2)
	{
		set_Value (COLUMNNAME_HC_Compensation2, HC_Compensation2);
	}

	/** Get Compensation 2.
		@return Compensation 2	  */
	public BigDecimal getHC_Compensation2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HC_Compensation2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Compensation 3.
		@param HC_Compensation3 Compensation 3	  */
	public void setHC_Compensation3 (BigDecimal HC_Compensation3)
	{
		set_Value (COLUMNNAME_HC_Compensation3, HC_Compensation3);
	}

	/** Get Compensation 3.
		@return Compensation 3	  */
	public BigDecimal getHC_Compensation3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HC_Compensation3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Compensation 4.
		@param HC_Compensation4 Compensation 4	  */
	public void setHC_Compensation4 (BigDecimal HC_Compensation4)
	{
		set_Value (COLUMNNAME_HC_Compensation4, HC_Compensation4);
	}

	/** Get Compensation 4.
		@return Compensation 4	  */
	public BigDecimal getHC_Compensation4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HC_Compensation4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Effective Sequence.
		@param HC_EffectiveSeq Effective Sequence	  */
	public void setHC_EffectiveSeq (int HC_EffectiveSeq)
	{
		set_Value (COLUMNNAME_HC_EffectiveSeq, Integer.valueOf(HC_EffectiveSeq));
	}

	/** Get Effective Sequence.
		@return Effective Sequence	  */
	public int getHC_EffectiveSeq () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_EffectiveSeq);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_EmployeeCategory getHC_EmployeeCategory() throws RuntimeException
    {
		return (I_HC_EmployeeCategory)MTable.get(getCtx(), I_HC_EmployeeCategory.Table_Name)
			.getPO(getHC_EmployeeCategory_ID(), get_TrxName());	}

	/** Set Employee Category.
		@param HC_EmployeeCategory_ID Employee Category	  */
	public void setHC_EmployeeCategory_ID (int HC_EmployeeCategory_ID)
	{
		if (HC_EmployeeCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeCategory_ID, Integer.valueOf(HC_EmployeeCategory_ID));
	}

	/** Get Employee Category.
		@return Employee Category	  */
	public int getHC_EmployeeCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_EmployeeCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_EmployeeClass getHC_EmployeeClass() throws RuntimeException
    {
		return (I_HC_EmployeeClass)MTable.get(getCtx(), I_HC_EmployeeClass.Table_Name)
			.getPO(getHC_EmployeeClass_ID(), get_TrxName());	}

	/** Set Employee Class.
		@param HC_EmployeeClass_ID Employee Class	  */
	public void setHC_EmployeeClass_ID (int HC_EmployeeClass_ID)
	{
		if (HC_EmployeeClass_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeClass_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_EmployeeClass_ID, Integer.valueOf(HC_EmployeeClass_ID));
	}

	/** Get Employee Class.
		@return Employee Class	  */
	public int getHC_EmployeeClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_EmployeeClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_EmployeeGrade getHC_EmployeeGrade() throws RuntimeException
    {
		return (I_HC_EmployeeGrade)MTable.get(getCtx(), I_HC_EmployeeGrade.Table_Name)
			.getPO(getHC_EmployeeGrade_ID(), get_TrxName());	}

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

	public I_HC_Employee getHC_Employee() throws RuntimeException
    {
		return (I_HC_Employee)MTable.get(getCtx(), I_HC_Employee.Table_Name)
			.getPO(getHC_Employee_ID(), get_TrxName());	}

	/** Set Employee Data.
		@param HC_Employee_ID Employee Data	  */
	public void setHC_Employee_ID (int HC_Employee_ID)
	{
		if (HC_Employee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Employee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Employee_ID, Integer.valueOf(HC_Employee_ID));
	}

	/** Get Employee Data.
		@return Employee Data	  */
	public int getHC_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** HC_JobAction AD_Reference_ID=300059 */
	public static final int HC_JOBACTION_AD_Reference_ID=300059;
	/** Hire = HIR */
	public static final String HC_JOBACTION_Hire = "HIR";
	/** Data Change = DTC */
	public static final String HC_JOBACTION_DataChange = "DTC";
	/** Transfer = TRF */
	public static final String HC_JOBACTION_Transfer = "TRF";
	/** Promotion = PRO */
	public static final String HC_JOBACTION_Promotion = "PRO";
	/** Demotion = DMO */
	public static final String HC_JOBACTION_Demotion = "DMO";
	/** Salary Change = SLC */
	public static final String HC_JOBACTION_SalaryChange = "SLC";
	/** Terminate = TMN */
	public static final String HC_JOBACTION_Terminate = "TMN";
	/** Rehire = RHR */
	public static final String HC_JOBACTION_Rehire = "RHR";
	/** Set Job Action.
		@param HC_JobAction Job Action	  */
	public void setHC_JobAction (String HC_JobAction)
	{

		set_Value (COLUMNNAME_HC_JobAction, HC_JobAction);
	}

	/** Get Job Action.
		@return Job Action	  */
	public String getHC_JobAction () 
	{
		return (String)get_Value(COLUMNNAME_HC_JobAction);
	}

	/** Set Job Data Change.
		@param HC_JobDataChange_ID Job Data Change	  */
	public void setHC_JobDataChange_ID (int HC_JobDataChange_ID)
	{
		if (HC_JobDataChange_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_JobDataChange_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_JobDataChange_ID, Integer.valueOf(HC_JobDataChange_ID));
	}

	/** Get Job Data Change.
		@return Job Data Change	  */
	public int getHC_JobDataChange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_JobDataChange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHC_JobDataChange_ID()));
    }

	/** Set HC_JobDataChange_UU.
		@param HC_JobDataChange_UU HC_JobDataChange_UU	  */
	public void setHC_JobDataChange_UU (String HC_JobDataChange_UU)
	{
		set_Value (COLUMNNAME_HC_JobDataChange_UU, HC_JobDataChange_UU);
	}

	/** Get HC_JobDataChange_UU.
		@return HC_JobDataChange_UU	  */
	public String getHC_JobDataChange_UU () 
	{
		return (String)get_Value(COLUMNNAME_HC_JobDataChange_UU);
	}

	public I_HC_Job getHC_Job() throws RuntimeException
    {
		return (I_HC_Job)MTable.get(getCtx(), I_HC_Job.Table_Name)
			.getPO(getHC_Job_ID(), get_TrxName());	}

	/** Set Job.
		@param HC_Job_ID Job	  */
	public void setHC_Job_ID (int HC_Job_ID)
	{
		if (HC_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Job_ID, Integer.valueOf(HC_Job_ID));
	}

	/** Get Job.
		@return Job	  */
	public int getHC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_Employee getHC_Manager() throws RuntimeException
    {
		return (I_HC_Employee)MTable.get(getCtx(), I_HC_Employee.Table_Name)
			.getPO(getHC_Manager_ID(), get_TrxName());	}

	/** Set Manager Name.
		@param HC_Manager_ID Manager Name	  */
	public void setHC_Manager_ID (int HC_Manager_ID)
	{
		if (HC_Manager_ID < 1) 
			set_Value (COLUMNNAME_HC_Manager_ID, null);
		else 
			set_Value (COLUMNNAME_HC_Manager_ID, Integer.valueOf(HC_Manager_ID));
	}

	/** Get Manager Name.
		@return Manager Name	  */
	public int getHC_Manager_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Manager_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_Position getHC_ManagerPosition() throws RuntimeException
    {
		return (I_HC_Position)MTable.get(getCtx(), I_HC_Position.Table_Name)
			.getPO(getHC_ManagerPosition_ID(), get_TrxName());	}

	/** Set Manager Position.
		@param HC_ManagerPosition_ID Manager Position	  */
	public void setHC_ManagerPosition_ID (int HC_ManagerPosition_ID)
	{
		if (HC_ManagerPosition_ID < 1) 
			set_Value (COLUMNNAME_HC_ManagerPosition_ID, null);
		else 
			set_Value (COLUMNNAME_HC_ManagerPosition_ID, Integer.valueOf(HC_ManagerPosition_ID));
	}

	/** Get Manager Position.
		@return Manager Position	  */
	public int getHC_ManagerPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_ManagerPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_Org getHC_Org() throws RuntimeException
    {
		return (I_HC_Org)MTable.get(getCtx(), I_HC_Org.Table_Name)
			.getPO(getHC_Org_ID(), get_TrxName());	}

	/** Set HC Organization.
		@param HC_Org_ID HC Organization	  */
	public void setHC_Org_ID (int HC_Org_ID)
	{
		if (HC_Org_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Org_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Org_ID, Integer.valueOf(HC_Org_ID));
	}

	/** Get HC Organization.
		@return HC Organization	  */
	public int getHC_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_PayGroup getHC_PayGroup() throws RuntimeException
    {
		return (I_HC_PayGroup)MTable.get(getCtx(), I_HC_PayGroup.Table_Name)
			.getPO(getHC_PayGroup_ID(), get_TrxName());	}

	/** Set Payroll Group.
		@param HC_PayGroup_ID Payroll Group	  */
	public void setHC_PayGroup_ID (int HC_PayGroup_ID)
	{
		if (HC_PayGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_PayGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_PayGroup_ID, Integer.valueOf(HC_PayGroup_ID));
	}

	/** Get Payroll Group.
		@return Payroll Group	  */
	public int getHC_PayGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_PayGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_Position getHC_Position() throws RuntimeException
    {
		return (I_HC_Position)MTable.get(getCtx(), I_HC_Position.Table_Name)
			.getPO(getHC_Position_ID(), get_TrxName());	}

	/** Set Position.
		@param HC_Position_ID Position	  */
	public void setHC_Position_ID (int HC_Position_ID)
	{
		if (HC_Position_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Position_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Position_ID, Integer.valueOf(HC_Position_ID));
	}

	/** Get Position.
		@return Position	  */
	public int getHC_Position_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Position_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HC_Reason getHC_Reason() throws RuntimeException
    {
		return (I_HC_Reason)MTable.get(getCtx(), I_HC_Reason.Table_Name)
			.getPO(getHC_Reason_ID(), get_TrxName());	}

	/** Set HC Reason.
		@param HC_Reason_ID HC Reason	  */
	public void setHC_Reason_ID (int HC_Reason_ID)
	{
		if (HC_Reason_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Reason_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Reason_ID, Integer.valueOf(HC_Reason_ID));
	}

	/** Get HC Reason.
		@return HC Reason	  */
	public int getHC_Reason_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Reason_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** HC_Status AD_Reference_ID=300058 */
	public static final int HC_STATUS_AD_Reference_ID=300058;
	/** Active = A */
	public static final String HC_STATUS_Active = "A";
	/** Terminate = T */
	public static final String HC_STATUS_Terminate = "T";
	/** Pending = P */
	public static final String HC_STATUS_Pending = "P";
	/** Leave = L */
	public static final String HC_STATUS_Leave = "L";
	/** Set HC Status.
		@param HC_Status HC Status	  */
	public void setHC_Status (String HC_Status)
	{

		set_Value (COLUMNNAME_HC_Status, HC_Status);
	}

	/** Get HC Status.
		@return HC Status	  */
	public String getHC_Status () 
	{
		return (String)get_Value(COLUMNNAME_HC_Status);
	}

	/** Set Original Service Date.
		@param OriginalServiceData Original Service Date	  */
	public void setOriginalServiceData (Timestamp OriginalServiceData)
	{
		set_Value (COLUMNNAME_OriginalServiceData, OriginalServiceData);
	}

	/** Get Original Service Date.
		@return Original Service Date	  */
	public Timestamp getOriginalServiceData () 
	{
		return (Timestamp)get_Value(COLUMNNAME_OriginalServiceData);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}