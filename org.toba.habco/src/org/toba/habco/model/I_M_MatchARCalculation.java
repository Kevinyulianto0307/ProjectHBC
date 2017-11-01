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

/** Generated Interface for M_MatchARCalculation
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_M_MatchARCalculation 
{

    /** TableName=M_MatchARCalculation */
    public static final String Table_Name = "M_MatchARCalculation";

    /** AD_Table_ID=1100143 */
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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name C_ProjectMilestone_ID */
    public static final String COLUMNNAME_C_ProjectMilestone_ID = "C_ProjectMilestone_ID";

	/** Set Project Milestone	  */
	public void setC_ProjectMilestone_ID (int C_ProjectMilestone_ID);

	/** Get Project Milestone	  */
	public int getC_ProjectMilestone_ID();

	public I_C_ProjectMilestone getC_ProjectMilestone() throws RuntimeException;

    /** Column name C_ProjectTypeMilestone_ID */
    public static final String COLUMNNAME_C_ProjectTypeMilestone_ID = "C_ProjectTypeMilestone_ID";

	/** Set Project Type Milestone	  */
	public void setC_ProjectTypeMilestone_ID (int C_ProjectTypeMilestone_ID);

	/** Get Project Type Milestone	  */
	public int getC_ProjectTypeMilestone_ID();

	public I_C_ProjectTypeMilestone getC_ProjectTypeMilestone() throws RuntimeException;

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

    /** Column name HBC_ARCalculation_ID */
    public static final String COLUMNNAME_HBC_ARCalculation_ID = "HBC_ARCalculation_ID";

	/** Set AR Calculation	  */
	public void setHBC_ARCalculation_ID (int HBC_ARCalculation_ID);

	/** Get AR Calculation	  */
	public int getHBC_ARCalculation_ID();

	public I_HBC_ARCalculation getHBC_ARCalculation() throws RuntimeException;

    /** Column name HBC_ARCalculationLine_ID */
    public static final String COLUMNNAME_HBC_ARCalculationLine_ID = "HBC_ARCalculationLine_ID";

	/** Set HBC_ARCalculationLine	  */
	public void setHBC_ARCalculationLine_ID (int HBC_ARCalculationLine_ID);

	/** Get HBC_ARCalculationLine	  */
	public int getHBC_ARCalculationLine_ID();

	public I_HBC_ARCalculationLine getHBC_ARCalculationLine() throws RuntimeException;

    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_HBC_Trip_ID = "HBC_Trip_ID";

	/** Set Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID);

	/** Get Trip	  */
	public int getHBC_Trip_ID();

	public I_HBC_Trip getHBC_Trip() throws RuntimeException;

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

    /** Column name M_MatchARCalculation_ID */
    public static final String COLUMNNAME_M_MatchARCalculation_ID = "M_MatchARCalculation_ID";

	/** Set Match AR Calculation	  */
	public void setM_MatchARCalculation_ID (int M_MatchARCalculation_ID);

	/** Get Match AR Calculation	  */
	public int getM_MatchARCalculation_ID();

    /** Column name M_MatchARCalculation_UU */
    public static final String COLUMNNAME_M_MatchARCalculation_UU = "M_MatchARCalculation_UU";

	/** Set M_MatchARCalculation_UU	  */
	public void setM_MatchARCalculation_UU (String M_MatchARCalculation_UU);

	/** Get M_MatchARCalculation_UU	  */
	public String getM_MatchARCalculation_UU();

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
