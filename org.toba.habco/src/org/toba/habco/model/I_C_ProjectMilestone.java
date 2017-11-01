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

/** Generated Interface for C_ProjectMilestone
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_C_ProjectMilestone 
{

    /** TableName=C_ProjectMilestone */
    public static final String Table_Name = "C_ProjectMilestone";

    /** AD_Table_ID=1100081 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

    /** Column name CommittedAmt */
    public static final String COLUMNNAME_CommittedAmt = "CommittedAmt";

	/** Set Committed Amount.
	  * The (legal) commitment amount
	  */
	public void setCommittedAmt (BigDecimal CommittedAmt);

	/** Get Committed Amount.
	  * The (legal) commitment amount
	  */
	public BigDecimal getCommittedAmt();

    /** Column name ContractStatus */
    public static final String COLUMNNAME_ContractStatus = "ContractStatus";

	/** Set Contract Status	  */
	public void setContractStatus (String ContractStatus);

	/** Get Contract Status	  */
	public String getContractStatus();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

    /** Column name C_ProjectMilestone_ID */
    public static final String COLUMNNAME_C_ProjectMilestone_ID = "C_ProjectMilestone_ID";

	/** Set Project Milestone	  */
	public void setC_ProjectMilestone_ID (int C_ProjectMilestone_ID);

	/** Get Project Milestone	  */
	public int getC_ProjectMilestone_ID();

    /** Column name C_ProjectMilestone_UU */
    public static final String COLUMNNAME_C_ProjectMilestone_UU = "C_ProjectMilestone_UU";

	/** Set C_ProjectMilestone_UU	  */
	public void setC_ProjectMilestone_UU (String C_ProjectMilestone_UU);

	/** Get C_ProjectMilestone_UU	  */
	public String getC_ProjectMilestone_UU();

    /** Column name C_ProjectPhase_ID */
    public static final String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/** Set Project Phase.
	  * Phase of a Project
	  */
	public void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/** Get Project Phase.
	  * Phase of a Project
	  */
	public int getC_ProjectPhase_ID();

	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase() throws RuntimeException;

    /** Column name C_ProjectTypeMilestone_ID */
    public static final String COLUMNNAME_C_ProjectTypeMilestone_ID = "C_ProjectTypeMilestone_ID";

	/** Set Project Type Milestone	  */
	public void setC_ProjectTypeMilestone_ID (int C_ProjectTypeMilestone_ID);

	/** Get Project Type Milestone	  */
	public int getC_ProjectTypeMilestone_ID();

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

    /** Column name HBC_Contract_ID */
    public static final String COLUMNNAME_HBC_Contract_ID = "HBC_Contract_ID";

	/** Set Contract	  */
	public void setHBC_Contract_ID (int HBC_Contract_ID);

	/** Get Contract	  */
	public int getHBC_Contract_ID();

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

    /** Column name IsFinalMilestone */
    public static final String COLUMNNAME_IsFinalMilestone = "IsFinalMilestone";

	/** Set Final Milestone.
	  * Identify Final Milestone
	  */
	public void setIsFinalMilestone (boolean IsFinalMilestone);

	/** Get Final Milestone.
	  * Identify Final Milestone
	  */
	public boolean isFinalMilestone();

    /** Column name IsMinimumCargo */
    public static final String COLUMNNAME_IsMinimumCargo = "IsMinimumCargo";

	/** Set Minimum Cargo.
	  * Identify Minimum Cargo 
	  */
	public void setIsMinimumCargo (boolean IsMinimumCargo);

	/** Get Minimum Cargo.
	  * Identify Minimum Cargo 
	  */
	public boolean isMinimumCargo();

    /** Column name IsPositionFrom */
    public static final String COLUMNNAME_IsPositionFrom = "IsPositionFrom";

	/** Set Position From.
	  * Position From
	  */
	public void setIsPositionFrom (boolean IsPositionFrom);

	/** Get Position From.
	  * Position From
	  */
	public boolean isPositionFrom();

    /** Column name MilestonePercentage */
    public static final String COLUMNNAME_MilestonePercentage = "MilestonePercentage";

	/** Set Milestone Percentage	  */
	public void setMilestonePercentage (BigDecimal MilestonePercentage);

	/** Get Milestone Percentage	  */
	public BigDecimal getMilestonePercentage();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name SubtractLine */
    public static final String COLUMNNAME_SubtractLine = "SubtractLine";

	/** Set Subtract Line	  */
	public void setSubtractLine (int SubtractLine);

	/** Get Subtract Line	  */
	public int getSubtractLine();

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
