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

/** Generated Interface for HC_Job
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HC_Job 
{

    /** TableName=HC_Job */
    public static final String Table_Name = "HC_Job";

    /** AD_Table_ID=300164 */
    public static final int Table_ID = 300164;

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

    /** Column name HC_GradeMax_ID */
    public static final String COLUMNNAME_HC_GradeMax_ID = "HC_GradeMax_ID";

	/** Set Grade Max	  */
	public void setHC_GradeMax_ID (int HC_GradeMax_ID);

	/** Get Grade Max	  */
	public int getHC_GradeMax_ID();

	public I_HC_EmployeeGrade getHC_GradeMax() throws RuntimeException;

    /** Column name HC_GradeMid_ID */
    public static final String COLUMNNAME_HC_GradeMid_ID = "HC_GradeMid_ID";

	/** Set Grade Mid	  */
	public void setHC_GradeMid_ID (int HC_GradeMid_ID);

	/** Get Grade Mid	  */
	public int getHC_GradeMid_ID();

	public I_HC_EmployeeGrade getHC_GradeMid() throws RuntimeException;

    /** Column name HC_GradeMin_ID */
    public static final String COLUMNNAME_HC_GradeMin_ID = "HC_GradeMin_ID";

	/** Set Grade Min	  */
	public void setHC_GradeMin_ID (int HC_GradeMin_ID);

	/** Get Grade Min	  */
	public int getHC_GradeMin_ID();

	public I_HC_EmployeeGrade getHC_GradeMin() throws RuntimeException;

    /** Column name HC_JobFamily_ID */
    public static final String COLUMNNAME_HC_JobFamily_ID = "HC_JobFamily_ID";

	/** Set Job Family	  */
	public void setHC_JobFamily_ID (int HC_JobFamily_ID);

	/** Get Job Family	  */
	public int getHC_JobFamily_ID();

	public I_HC_JobFamily getHC_JobFamily() throws RuntimeException;

    /** Column name HC_JobFunction_ID */
    public static final String COLUMNNAME_HC_JobFunction_ID = "HC_JobFunction_ID";

	/** Set Job Function	  */
	public void setHC_JobFunction_ID (int HC_JobFunction_ID);

	/** Get Job Function	  */
	public int getHC_JobFunction_ID();

	public I_HC_JobFunction getHC_JobFunction() throws RuntimeException;

    /** Column name HC_Job_ID */
    public static final String COLUMNNAME_HC_Job_ID = "HC_Job_ID";

	/** Set Job	  */
	public void setHC_Job_ID (int HC_Job_ID);

	/** Get Job	  */
	public int getHC_Job_ID();

    /** Column name HC_JobLevel */
    public static final String COLUMNNAME_HC_JobLevel = "HC_JobLevel";

	/** Set HC Job Level	  */
	public void setHC_JobLevel (String HC_JobLevel);

	/** Get HC Job Level	  */
	public String getHC_JobLevel();

    /** Column name HC_Job_UU */
    public static final String COLUMNNAME_HC_Job_UU = "HC_Job_UU";

	/** Set HC_Job_UU	  */
	public void setHC_Job_UU (String HC_Job_UU);

	/** Get HC_Job_UU	  */
	public String getHC_Job_UU();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
