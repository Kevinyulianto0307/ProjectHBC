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

/** Generated Interface for HC_Position
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HC_Position 
{

    /** TableName=HC_Position */
    public static final String Table_Name = "HC_Position";

    /** AD_Table_ID=300165 */
    public static final int Table_ID = 300165;

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

    /** Column name HC_Job_ID */
    public static final String COLUMNNAME_HC_Job_ID = "HC_Job_ID";

	/** Set Job	  */
	public void setHC_Job_ID (int HC_Job_ID);

	/** Get Job	  */
	public int getHC_Job_ID();

	public I_HC_Job getHC_Job() throws RuntimeException;

    /** Column name HC_Position_ID */
    public static final String COLUMNNAME_HC_Position_ID = "HC_Position_ID";

	/** Set Position	  */
	public void setHC_Position_ID (int HC_Position_ID);

	/** Get Position	  */
	public int getHC_Position_ID();

    /** Column name HC_PositionReportTo_ID */
    public static final String COLUMNNAME_HC_PositionReportTo_ID = "HC_PositionReportTo_ID";

	/** Set Position Reports To	  */
	public void setHC_PositionReportTo_ID (int HC_PositionReportTo_ID);

	/** Get Position Reports To	  */
	public int getHC_PositionReportTo_ID();

	public I_HC_Position getHC_PositionReportTo() throws RuntimeException;

    /** Column name HC_Position_UU */
    public static final String COLUMNNAME_HC_Position_UU = "HC_Position_UU";

	/** Set HC_Position_UU	  */
	public void setHC_Position_UU (String HC_Position_UU);

	/** Get HC_Position_UU	  */
	public String getHC_Position_UU();

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

    /** Column name Title */
    public static final String COLUMNNAME_Title = "Title";

	/** Set Title.
	  * Name this entity is referred to as
	  */
	public void setTitle (String Title);

	/** Get Title.
	  * Name this entity is referred to as
	  */
	public String getTitle();

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
