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

/** Generated Interface for HC_JobDataChange
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HC_JobDataChange 
{

    /** TableName=HC_JobDataChange */
    public static final String Table_Name = "HC_JobDataChange";

    /** AD_Table_ID=300174 */
    public static final int Table_ID = 300174;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

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

    /** Column name EffectiveDateFrom */
    public static final String COLUMNNAME_EffectiveDateFrom = "EffectiveDateFrom";

	/** Set Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom);

	/** Get Effective Date From	  */
	public Timestamp getEffectiveDateFrom();

    /** Column name HC_Compensation1 */
    public static final String COLUMNNAME_HC_Compensation1 = "HC_Compensation1";

	/** Set Compensation 1	  */
	public void setHC_Compensation1 (BigDecimal HC_Compensation1);

	/** Get Compensation 1	  */
	public BigDecimal getHC_Compensation1();

    /** Column name HC_Compensation2 */
    public static final String COLUMNNAME_HC_Compensation2 = "HC_Compensation2";

	/** Set Compensation 2	  */
	public void setHC_Compensation2 (BigDecimal HC_Compensation2);

	/** Get Compensation 2	  */
	public BigDecimal getHC_Compensation2();

    /** Column name HC_Compensation3 */
    public static final String COLUMNNAME_HC_Compensation3 = "HC_Compensation3";

	/** Set Compensation 3	  */
	public void setHC_Compensation3 (BigDecimal HC_Compensation3);

	/** Get Compensation 3	  */
	public BigDecimal getHC_Compensation3();

    /** Column name HC_Compensation4 */
    public static final String COLUMNNAME_HC_Compensation4 = "HC_Compensation4";

	/** Set Compensation 4	  */
	public void setHC_Compensation4 (BigDecimal HC_Compensation4);

	/** Get Compensation 4	  */
	public BigDecimal getHC_Compensation4();

    /** Column name HC_EffectiveSeq */
    public static final String COLUMNNAME_HC_EffectiveSeq = "HC_EffectiveSeq";

	/** Set Effective Sequence	  */
	public void setHC_EffectiveSeq (int HC_EffectiveSeq);

	/** Get Effective Sequence	  */
	public int getHC_EffectiveSeq();

    /** Column name HC_EmployeeCategory_ID */
    public static final String COLUMNNAME_HC_EmployeeCategory_ID = "HC_EmployeeCategory_ID";

	/** Set Employee Category	  */
	public void setHC_EmployeeCategory_ID (int HC_EmployeeCategory_ID);

	/** Get Employee Category	  */
	public int getHC_EmployeeCategory_ID();

	public I_HC_EmployeeCategory getHC_EmployeeCategory() throws RuntimeException;

    /** Column name HC_EmployeeClass_ID */
    public static final String COLUMNNAME_HC_EmployeeClass_ID = "HC_EmployeeClass_ID";

	/** Set Employee Class	  */
	public void setHC_EmployeeClass_ID (int HC_EmployeeClass_ID);

	/** Get Employee Class	  */
	public int getHC_EmployeeClass_ID();

	public I_HC_EmployeeClass getHC_EmployeeClass() throws RuntimeException;

    /** Column name HC_EmployeeGrade_ID */
    public static final String COLUMNNAME_HC_EmployeeGrade_ID = "HC_EmployeeGrade_ID";

	/** Set Employee Grade	  */
	public void setHC_EmployeeGrade_ID (int HC_EmployeeGrade_ID);

	/** Get Employee Grade	  */
	public int getHC_EmployeeGrade_ID();

	public I_HC_EmployeeGrade getHC_EmployeeGrade() throws RuntimeException;

    /** Column name HC_Employee_ID */
    public static final String COLUMNNAME_HC_Employee_ID = "HC_Employee_ID";

	/** Set Employee Data	  */
	public void setHC_Employee_ID (int HC_Employee_ID);

	/** Get Employee Data	  */
	public int getHC_Employee_ID();

	public I_HC_Employee getHC_Employee() throws RuntimeException;

    /** Column name HC_JobAction */
    public static final String COLUMNNAME_HC_JobAction = "HC_JobAction";

	/** Set Job Action	  */
	public void setHC_JobAction (String HC_JobAction);

	/** Get Job Action	  */
	public String getHC_JobAction();

    /** Column name HC_JobDataChange_ID */
    public static final String COLUMNNAME_HC_JobDataChange_ID = "HC_JobDataChange_ID";

	/** Set Job Data Change	  */
	public void setHC_JobDataChange_ID (int HC_JobDataChange_ID);

	/** Get Job Data Change	  */
	public int getHC_JobDataChange_ID();

    /** Column name HC_JobDataChange_UU */
    public static final String COLUMNNAME_HC_JobDataChange_UU = "HC_JobDataChange_UU";

	/** Set HC_JobDataChange_UU	  */
	public void setHC_JobDataChange_UU (String HC_JobDataChange_UU);

	/** Get HC_JobDataChange_UU	  */
	public String getHC_JobDataChange_UU();

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

    /** Column name HC_PayGroup_ID */
    public static final String COLUMNNAME_HC_PayGroup_ID = "HC_PayGroup_ID";

	/** Set Payroll Group	  */
	public void setHC_PayGroup_ID (int HC_PayGroup_ID);

	/** Get Payroll Group	  */
	public int getHC_PayGroup_ID();

	public I_HC_PayGroup getHC_PayGroup() throws RuntimeException;

    /** Column name HC_Position_ID */
    public static final String COLUMNNAME_HC_Position_ID = "HC_Position_ID";

	/** Set Position	  */
	public void setHC_Position_ID (int HC_Position_ID);

	/** Get Position	  */
	public int getHC_Position_ID();

	public I_HC_Position getHC_Position() throws RuntimeException;

    /** Column name HC_Reason_ID */
    public static final String COLUMNNAME_HC_Reason_ID = "HC_Reason_ID";

	/** Set HC Reason	  */
	public void setHC_Reason_ID (int HC_Reason_ID);

	/** Get HC Reason	  */
	public int getHC_Reason_ID();

	public I_HC_Reason getHC_Reason() throws RuntimeException;

    /** Column name HC_Status */
    public static final String COLUMNNAME_HC_Status = "HC_Status";

	/** Set HC Status	  */
	public void setHC_Status (String HC_Status);

	/** Get HC Status	  */
	public String getHC_Status();

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

    /** Column name OriginalServiceData */
    public static final String COLUMNNAME_OriginalServiceData = "OriginalServiceData";

	/** Set Original Service Date	  */
	public void setOriginalServiceData (Timestamp OriginalServiceData);

	/** Get Original Service Date	  */
	public Timestamp getOriginalServiceData();

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
