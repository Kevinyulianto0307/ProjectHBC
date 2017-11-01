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

/** Generated Interface for AR_CalculationTemp
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_AR_CalculationTemp 
{

    /** TableName=AR_CalculationTemp */
    public static final String Table_Name = "AR_CalculationTemp";

    /** AD_Table_ID=1100093 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

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

    /** Column name AmountOfCargo */
    public static final String COLUMNNAME_AmountOfCargo = "AmountOfCargo";

	/** Set Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo);

	/** Get Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo();

    /** Column name AR_CalculationTemp_ID */
    public static final String COLUMNNAME_AR_CalculationTemp_ID = "AR_CalculationTemp_ID";

	/** Set AR_CalculationTemp	  */
	public void setAR_CalculationTemp_ID (int AR_CalculationTemp_ID);

	/** Get AR_CalculationTemp	  */
	public int getAR_CalculationTemp_ID();

    /** Column name AR_CalculationTemp_UU */
    public static final String COLUMNNAME_AR_CalculationTemp_UU = "AR_CalculationTemp_UU";

	/** Set AR_CalculationTemp_UU	  */
	public void setAR_CalculationTemp_UU (String AR_CalculationTemp_UU);

	/** Get AR_CalculationTemp_UU	  */
	public String getAR_CalculationTemp_UU();

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

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

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

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException;

    /** Column name HBC_Position_ID */
    public static final String COLUMNNAME_HBC_Position_ID = "HBC_Position_ID";

	/** Set Ship Position	  */
	public void setHBC_Position_ID (int HBC_Position_ID);

	/** Get Ship Position	  */
	public int getHBC_Position_ID();

	public I_HBC_Position getHBC_Position() throws RuntimeException;

    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_HBC_Trip_ID = "HBC_Trip_ID";

	/** Set Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID);

	/** Get Trip	  */
	public int getHBC_Trip_ID();

	public I_HBC_Trip getHBC_Trip() throws RuntimeException;

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name TotalCargoQty */
    public static final String COLUMNNAME_TotalCargoQty = "TotalCargoQty";

	/** Set Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty);

	/** Get Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty();

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
