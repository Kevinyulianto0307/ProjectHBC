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

/** Generated Interface for TCS_BBMPlan
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_TCS_BBMPlan 
{
	
    /** TableName=TCS_BBMPlan */
    public static final String Table_Name = "TCS_BBMPlan";

    /** AD_Table_ID=1100226 */
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

    /** Column name HBC_Tugboat_ID */
    public static final String COLUMNNAME_HBC_Tugboat_ID = "HBC_Tugboat_ID";

	/** Set TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID);

	/** Get TugBoat	  */
	public int getHBC_Tugboat_ID();

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

    /** Column name TCS_BBMPlan_ID */
    public static final String COLUMNNAME_TCS_BBMPlan_ID = "TCS_BBMPlan_ID";

	/** Set BBM Plan	  */
	public void setTCS_BBMPlan_ID (int TCS_BBMPlan_ID);

	/** Get BBM Plan	  */
	public int getTCS_BBMPlan_ID();

    /** Column name TCS_BBMPlan_UU */
    public static final String COLUMNNAME_TCS_BBMPlan_UU = "TCS_BBMPlan_UU";

	/** Set TCS_BBMPlan_UU	  */
	public void setTCS_BBMPlan_UU (String TCS_BBMPlan_UU);

	/** Get TCS_BBMPlan_UU	  */
	public String getTCS_BBMPlan_UU();

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
	
	//@TommyAng
	/** Column name TripAllocationDay */
    public static final String COLUMNNAME_TripAllocationDay = "TripAllocationDay";
    
    /** Column name LiterAllocation */
    public static final String COLUMNNAME_LiterAllocation = "LiterAllocation";
    
    /** Column name TotalLiters */
    public static final String COLUMNNAME_TotalLiters = "TotalLiters";
    
    /** Column name BBMUsage */
    public static final String COLUMNNAME_BBMUsage = "BBMUsage";
    
    /** Column name FuelBalance */
    public static final String COLUMNNAME_FuelBalance = "FuelBalance";
    
    /** Column name Liter */
    public static final String COLUMNNAME_Liter = "Liter";
    
    /** Column name LiterCalculation */
    public static final String COLUMNNAME_LiterCalculation = "LiterCalculation";
    
    /** Column name FuelBalanceNew */
    public static final String COLUMNNAME_FuelBalanceNew = "FuelBalanceNew";
    
    /** Column name ShipFuelUsage */
    public static final String COLUMNNAME_ShipFuelUsage = "ShipFuelUsage";
    
    /** Column name FuelAdjustment */
    public static final String COLUMNNAME_FuelAdjustment = "FuelAdjustment";

    /** Column name FuelActual */
    public static final String COLUMNNAME_FuelActual = "FuelActual";
    
    /** Column name FuelUsageDiscrepancy */
    public static final String COLUMNNAME_FuelUsageDiscrepancy = "FuelUsageDiscrepancy";
    
    /** Column name Day */
    public static final String COLUMNNAME_Day = "Day";
    
    /** Column name SailDay */
    public static final String COLUMNNAME_SailDay = "TotalSailDay";
    
    /** Column name BerthDay */
    public static final String COLUMNNAME_BerthDay = "TotalBerthDay";
}
