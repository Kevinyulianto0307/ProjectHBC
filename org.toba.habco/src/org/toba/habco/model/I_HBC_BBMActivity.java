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

/** Generated Interface for HBC_BBMActivity
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_BBMActivity 
{

    /** TableName=HBC_BBMActivity */
    public static final String Table_Name = "HBC_BBMActivity";

    /** AD_Table_ID=1100106 */
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

    /** Column name Day */
    public static final String COLUMNNAME_Day = "Day";

	/** Set Day	  */
	public void setDay (BigDecimal Day);

	/** Get Day	  */
	public BigDecimal getDay();

    /** Column name FuelConsumptionPerHour */
    public static final String COLUMNNAME_FuelConsumptionPerHour = "FuelConsumptionPerHour";

	/** Set Fuel Consumption Per Hour	  */
	public void setFuelConsumptionPerHour (BigDecimal FuelConsumptionPerHour);

	/** Get Fuel Consumption Per Hour	  */
	public BigDecimal getFuelConsumptionPerHour();

    /** Column name HBC_BBMActivity_ID */
    public static final String COLUMNNAME_HBC_BBMActivity_ID = "HBC_BBMActivity_ID";

	/** Set HBC_BBMActivity	  */
	public void setHBC_BBMActivity_ID (int HBC_BBMActivity_ID);

	/** Get HBC_BBMActivity	  */
	public int getHBC_BBMActivity_ID();

    /** Column name HBC_BBMActivity_UU */
    public static final String COLUMNNAME_HBC_BBMActivity_UU = "HBC_BBMActivity_UU";

	/** Set HBC_BBMActivity_UU	  */
	public void setHBC_BBMActivity_UU (String HBC_BBMActivity_UU);

	/** Get HBC_BBMActivity_UU	  */
	public String getHBC_BBMActivity_UU();

    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_HBC_Trip_ID = "HBC_Trip_ID";

	/** Set Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID);

	/** Get Trip	  */
	public int getHBC_Trip_ID();

	public I_HBC_Trip getHBC_Trip() throws RuntimeException;

    /** Column name HBC_Tugboat_ID */
    public static final String COLUMNNAME_HBC_Tugboat_ID = "HBC_Tugboat_ID";

	/** Set TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID);

	/** Get TugBoat	  */
	public int getHBC_Tugboat_ID();

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException;

    /** Column name Hour */
    public static final String COLUMNNAME_Hour = "Hour";

	/** Set Hour	  */
	public void setHour (BigDecimal Hour);

	/** Get Hour	  */
	public BigDecimal getHour();

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

    /** Column name IsProcess */
    public static final String COLUMNNAME_IsProcess = "IsProcess";

	/** Set Processed	  */
	public void setIsProcess (boolean IsProcess);

	/** Get Processed	  */
	public boolean isProcess();

    /** Column name Percentage */
    public static final String COLUMNNAME_Percentage = "Percentage";

	/** Set Percentage.
	  * Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage);

	/** Get Percentage.
	  * Percent of the entire amount
	  */
	public BigDecimal getPercentage();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name TotalLiters */
    public static final String COLUMNNAME_TotalLiters = "TotalLiters";

	/** Set Total Liters	  */
	public void setTotalLiters (BigDecimal TotalLiters);

	/** Get Total Liters	  */
	public BigDecimal getTotalLiters();

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
