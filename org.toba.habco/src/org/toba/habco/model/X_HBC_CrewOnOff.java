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

/** Generated Model for HBC_CrewOnOff
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_CrewOnOff extends PO implements I_HBC_CrewOnOff, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170111L;

    /** Standard Constructor */
    public X_HBC_CrewOnOff (Properties ctx, int HBC_CrewOnOff_ID, String trxName)
    {
      super (ctx, HBC_CrewOnOff_ID, trxName);
      /** if (HBC_CrewOnOff_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setHBC_CrewOnOff_ID (0);
			setHBC_CrewOnOffType (null);
			setHC_Employee_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_HBC_CrewOnOff (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_HBC_CrewOnOff[")
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Crew On/Off.
		@param HBC_CrewOnOff_ID Crew On/Off	  */
	public void setHBC_CrewOnOff_ID (int HBC_CrewOnOff_ID)
	{
		if (HBC_CrewOnOff_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_CrewOnOff_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_CrewOnOff_ID, Integer.valueOf(HBC_CrewOnOff_ID));
	}

	/** Get Crew On/Off.
		@return Crew On/Off	  */
	public int getHBC_CrewOnOff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_CrewOnOff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HBC_CrewOnOff_UU.
		@param HBC_CrewOnOff_UU HBC_CrewOnOff_UU	  */
	public void setHBC_CrewOnOff_UU (String HBC_CrewOnOff_UU)
	{
		set_Value (COLUMNNAME_HBC_CrewOnOff_UU, HBC_CrewOnOff_UU);
	}

	/** Get HBC_CrewOnOff_UU.
		@return HBC_CrewOnOff_UU	  */
	public String getHBC_CrewOnOff_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_CrewOnOff_UU);
	}

	/** Offboarding = OFB */
	public static final String HBC_CREWONOFFTYPE_Offboarding = "OFB";
	/** Onboarding = ONB */
	public static final String HBC_CREWONOFFTYPE_Onboarding = "ONB";
	/** Transfer = TRF */
	public static final String HBC_CREWONOFFTYPE_Transfer = "TRF";
	/** On Leave = ONL */
	public static final String HBC_CREWONOFFTYPE_OnLeave = "ONL";
	/** On Duty = OND */
	public static final String HBC_CREWONOFFTYPE_OnDuty = "OND";
	/** Set Transaction Type.
		@param HBC_CrewOnOffType Transaction Type	  */
	public void setHBC_CrewOnOffType (String HBC_CrewOnOffType)
	{

		set_Value (COLUMNNAME_HBC_CrewOnOffType, HBC_CrewOnOffType);
	}

	/** Get Transaction Type.
		@return Transaction Type	  */
	public String getHBC_CrewOnOffType () 
	{
		return (String)get_Value(COLUMNNAME_HBC_CrewOnOffType);
	}

	/** Set Base Salary.
		@param HC_Compensation1 Base Salary	  */
	public void setHC_Compensation1 (BigDecimal HC_Compensation1)
	{
		set_Value (COLUMNNAME_HC_Compensation1, HC_Compensation1);
	}

	/** Get Base Salary.
		@return Base Salary	  */
	public BigDecimal getHC_Compensation1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HC_Compensation1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tunjangan.
		@param HC_Compensation2 Tunjangan	  */
	public void setHC_Compensation2 (BigDecimal HC_Compensation2)
	{
		set_Value (COLUMNNAME_HC_Compensation2, HC_Compensation2);
	}

	/** Get Tunjangan.
		@return Tunjangan	  */
	public BigDecimal getHC_Compensation2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HC_Compensation2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_HC_Org getHC_OrgTo() throws RuntimeException
    {
		return (I_HC_Org)MTable.get(getCtx(), I_HC_Org.Table_Name)
			.getPO(getHC_OrgTo_ID(), get_TrxName());	}

	/** Set HC Organization To.
		@param HC_OrgTo_ID HC Organization To	  */
	public void setHC_OrgTo_ID (int HC_OrgTo_ID)
	{
		if (HC_OrgTo_ID < 1) 
			set_Value (COLUMNNAME_HC_OrgTo_ID, null);
		else 
			set_Value (COLUMNNAME_HC_OrgTo_ID, Integer.valueOf(HC_OrgTo_ID));
	}

	/** Get HC Organization To.
		@return HC Organization To	  */
	public int getHC_OrgTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_OrgTo_ID);
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

	/** Set Leave End Date.
		@param LeaveEndDate Leave End Date	  */
	public void setLeaveEndDate (Timestamp LeaveEndDate)
	{
		set_Value (COLUMNNAME_LeaveEndDate, LeaveEndDate);
	}

	/** Get Leave End Date.
		@return Leave End Date	  */
	public Timestamp getLeaveEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LeaveEndDate);
	}

	/** Set Leave Start Date.
		@param LeaveStartDate Leave Start Date	  */
	public void setLeaveStartDate (Timestamp LeaveStartDate)
	{
		set_Value (COLUMNNAME_LeaveStartDate, LeaveStartDate);
	}

	/** Get Leave Start Date.
		@return Leave Start Date	  */
	public Timestamp getLeaveStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LeaveStartDate);
	}

	public I_HC_Job getPreviousJob() throws RuntimeException
    {
		return (I_HC_Job)MTable.get(getCtx(), I_HC_Job.Table_Name)
			.getPO(getPreviousJob_ID(), get_TrxName());	}

	/** Set Previous Job.
		@param PreviousJob_ID Previous Job	  */
	public void setPreviousJob_ID (int PreviousJob_ID)
	{
		if (PreviousJob_ID < 1) 
			set_Value (COLUMNNAME_PreviousJob_ID, null);
		else 
			set_Value (COLUMNNAME_PreviousJob_ID, Integer.valueOf(PreviousJob_ID));
	}

	/** Get Previous Job.
		@return Previous Job	  */
	public int getPreviousJob_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PreviousJob_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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