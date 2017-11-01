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
package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HBC_CrewOnOff
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_CrewOnOff 
{

    /** TableName=HBC_CrewOnOff */
    public static final String Table_Name = "HBC_CrewOnOff";

    /** AD_Table_ID=1100095 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Agent_BPartner_ID */
    public static final String COLUMNNAME_Agent_BPartner_ID = "Agent_BPartner_ID";

	/** Set Agent	  */
	public void setAgent_BPartner_ID (int Agent_BPartner_ID);

	/** Get Agent	  */
	public int getAgent_BPartner_ID();

	public org.compiere.model.I_C_BPartner getAgent_BPartner() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name HBC_CrewOnOff_ID */
    public static final String COLUMNNAME_HBC_CrewOnOff_ID = "HBC_CrewOnOff_ID";

	/** Set Crew On/Off	  */
	public void setHBC_CrewOnOff_ID (int HBC_CrewOnOff_ID);

	/** Get Crew On/Off	  */
	public int getHBC_CrewOnOff_ID();

    /** Column name HBC_CrewOnOff_UU */
    public static final String COLUMNNAME_HBC_CrewOnOff_UU = "HBC_CrewOnOff_UU";

	/** Set HBC_CrewOnOff_UU	  */
	public void setHBC_CrewOnOff_UU (String HBC_CrewOnOff_UU);

	/** Get HBC_CrewOnOff_UU	  */
	public String getHBC_CrewOnOff_UU();

    /** Column name HBC_CrewOnOffType */
    public static final String COLUMNNAME_HBC_CrewOnOffType = "HBC_CrewOnOffType";

	/** Set Transaction Type	  */
	public void setHBC_CrewOnOffType (String HBC_CrewOnOffType);

	/** Get Transaction Type	  */
	public String getHBC_CrewOnOffType();

    /** Column name HC_Compensation1 */
    public static final String COLUMNNAME_HC_Compensation1 = "HC_Compensation1";

	/** Set Base Salary	  */
	public void setHC_Compensation1 (BigDecimal HC_Compensation1);

	/** Get Base Salary	  */
	public BigDecimal getHC_Compensation1();

    /** Column name HC_Compensation2 */
    public static final String COLUMNNAME_HC_Compensation2 = "HC_Compensation2";

	/** Set Tunjangan	  */
	public void setHC_Compensation2 (BigDecimal HC_Compensation2);

	/** Get Tunjangan	  */
	public BigDecimal getHC_Compensation2();

    /** Column name HC_Employee_ID */
    public static final String COLUMNNAME_HC_Employee_ID = "HC_Employee_ID";

	/** Set Employee Data	  */
	public void setHC_Employee_ID (int HC_Employee_ID);

	/** Get Employee Data	  */
	public int getHC_Employee_ID();

	public I_HC_Employee getHC_Employee() throws RuntimeException;

    /** Column name HC_Job_ID */
    public static final String COLUMNNAME_HC_Job_ID = "HC_Job_ID";

	/** Set Job	  */
	public void setHC_Job_ID (int HC_Job_ID);

	/** Get Job	  */
	public int getHC_Job_ID();

	public I_HC_Job getHC_Job() throws RuntimeException;

    /** Column name HC_Manager_ID */
    public static final String COLUMNNAME_HC_Manager_ID = "HC_Manager_ID";

	/** Set Manager Name	  */
	public void setHC_Manager_ID (int HC_Manager_ID);

	/** Get Manager Name	  */
	public int getHC_Manager_ID();

	public I_HC_Employee getHC_Manager() throws RuntimeException;

    /** Column name HC_ManagerPosition_ID */
    public static final String COLUMNNAME_HC_ManagerPosition_ID = "HC_ManagerPosition_ID";

	/** Set Manager Position	  */
	public void setHC_ManagerPosition_ID (int HC_ManagerPosition_ID);

	/** Get Manager Position	  */
	public int getHC_ManagerPosition_ID();

	public I_HC_Position getHC_ManagerPosition() throws RuntimeException;

    /** Column name HC_Org_ID */
    public static final String COLUMNNAME_HC_Org_ID = "HC_Org_ID";

	/** Set HC Organization	  */
	public void setHC_Org_ID (int HC_Org_ID);

	/** Get HC Organization	  */
	public int getHC_Org_ID();

	public I_HC_Org getHC_Org() throws RuntimeException;

    /** Column name HC_OrgTo_ID */
    public static final String COLUMNNAME_HC_OrgTo_ID = "HC_OrgTo_ID";

	/** Set HC Organization To	  */
	public void setHC_OrgTo_ID (int HC_OrgTo_ID);

	/** Get HC Organization To	  */
	public int getHC_OrgTo_ID();

	public I_HC_Org getHC_OrgTo() throws RuntimeException;

    /** Column name HC_Reason_ID */
    public static final String COLUMNNAME_HC_Reason_ID = "HC_Reason_ID";

	/** Set HC Reason	  */
	public void setHC_Reason_ID (int HC_Reason_ID);

	/** Get HC Reason	  */
	public int getHC_Reason_ID();

	public I_HC_Reason getHC_Reason() throws RuntimeException;

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name LeaveEndDate */
    public static final String COLUMNNAME_LeaveEndDate = "LeaveEndDate";

	/** Set Leave End Date	  */
	public void setLeaveEndDate (Timestamp LeaveEndDate);

	/** Get Leave End Date	  */
	public Timestamp getLeaveEndDate();

    /** Column name LeaveStartDate */
    public static final String COLUMNNAME_LeaveStartDate = "LeaveStartDate";

	/** Set Leave Start Date	  */
	public void setLeaveStartDate (Timestamp LeaveStartDate);

	/** Get Leave Start Date	  */
	public Timestamp getLeaveStartDate();

    /** Column name PreviousJob_ID */
    public static final String COLUMNNAME_PreviousJob_ID = "PreviousJob_ID";

	/** Set Previous Job	  */
	public void setPreviousJob_ID (int PreviousJob_ID);

	/** Get Previous Job	  */
	public int getPreviousJob_ID();

	public I_HC_Job getPreviousJob() throws RuntimeException;

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
