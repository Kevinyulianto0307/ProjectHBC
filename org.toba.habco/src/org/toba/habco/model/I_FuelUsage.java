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

/** Generated Interface for FuelUsage
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_FuelUsage 
{

    /** TableName=FuelUsage */
    public static final String Table_Name = "FuelUsage";

    /** AD_Table_ID=1100194 */
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

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

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

    /** Column name FuelEstimation */
    public static final String COLUMNNAME_FuelEstimation = "FuelEstimation";

	/** Set Fuel Estimation	  */
	public void setFuelEstimation (BigDecimal FuelEstimation);

	/** Get Fuel Estimation	  */
	public BigDecimal getFuelEstimation();

    /** Column name FuelShipBalance */
    public static final String COLUMNNAME_FuelShipBalance = "FuelShipBalance";

	/** Set Fuel Ship Balance	  */
	public void setFuelShipBalance (BigDecimal FuelShipBalance);

	/** Get Fuel Ship Balance	  */
	public BigDecimal getFuelShipBalance();

    /** Column name FuelUsage_ID */
    public static final String COLUMNNAME_FuelUsage_ID = "FuelUsage_ID";

	/** Set Fuel Usage	  */
	public void setFuelUsage_ID (int FuelUsage_ID);

	/** Get Fuel Usage	  */
	public int getFuelUsage_ID();

    /** Column name FuelUsage_UU */
    public static final String COLUMNNAME_FuelUsage_UU = "FuelUsage_UU";

	/** Set FuelUsage_UU	  */
	public void setFuelUsage_UU (String FuelUsage_UU);

	/** Get FuelUsage_UU	  */
	public String getFuelUsage_UU();

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_HBC_Trip_ID = "HBC_Trip_ID";
    
    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_TCS_BBMPlan_ID = "TCS_BBMPlan_ID";
    
	/** Set Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID);

	/** Get Trip	  */
	public int getHBC_Trip_ID();
	
	/** Set BBMPlan	  */
	public void setTCS_BBMPlan_ID (int TCS_BBMPlan_ID);

	/** Get BBMPlan	  */
	public int getTCS_BBMPlan_ID();

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

    /** Column name UsageDiff */
    public static final String COLUMNNAME_UsageDiff = "UsageDiff";

	/** Set Usage Diff	  */
	public void setUsageDiff (BigDecimal UsageDiff);

	/** Get Usage Diff	  */
	public BigDecimal getUsageDiff();
}
