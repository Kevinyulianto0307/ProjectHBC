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

/** Generated Interface for HBC_GearBox
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_GearBox 
{

    /** TableName=HBC_GearBox */
    public static final String Table_Name = "HBC_GearBox";

    /** AD_Table_ID=1100044 */
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

    /** Column name HBC_EngineBrand_ID */
    public static final String COLUMNNAME_HBC_EngineBrand_ID = "HBC_EngineBrand_ID";

	/** Set Engine Brand	  */
	public void setHBC_EngineBrand_ID (int HBC_EngineBrand_ID);

	/** Get Engine Brand	  */
	public int getHBC_EngineBrand_ID();

	public I_HBC_EngineBrand getHBC_EngineBrand() throws RuntimeException;

    /** Column name HBC_GearBox_ID */
    public static final String COLUMNNAME_HBC_GearBox_ID = "HBC_GearBox_ID";

	/** Set Gear Box	  */
	public void setHBC_GearBox_ID (int HBC_GearBox_ID);

	/** Get Gear Box	  */
	public int getHBC_GearBox_ID();

    /** Column name HBC_GearboxType_ID */
    public static final String COLUMNNAME_HBC_GearboxType_ID = "HBC_GearboxType_ID";

	/** Set Gear Box Type	  */
	public void setHBC_GearboxType_ID (int HBC_GearboxType_ID);

	/** Get Gear Box Type	  */
	public int getHBC_GearboxType_ID();

	public I_HBC_GearboxType getHBC_GearboxType() throws RuntimeException;

    /** Column name HBC_GearBox_UU */
    public static final String COLUMNNAME_HBC_GearBox_UU = "HBC_GearBox_UU";

	/** Set HBC_GearBox_UU	  */
	public void setHBC_GearBox_UU (String HBC_GearBox_UU);

	/** Get HBC_GearBox_UU	  */
	public String getHBC_GearBox_UU();

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

    /** Column name OilConsumptionGearbox */
    public static final String COLUMNNAME_OilConsumptionGearbox = "OilConsumptionGearbox";

	/** Set Oil Consumption	  */
	public void setOilConsumptionGearbox (BigDecimal OilConsumptionGearbox);

	/** Get Oil Consumption	  */
	public BigDecimal getOilConsumptionGearbox();

    /** Column name RatioGearBox */
    public static final String COLUMNNAME_RatioGearBox = "RatioGearBox";

	/** Set Ratio	  */
	public void setRatioGearBox (String RatioGearBox);

	/** Get Ratio	  */
	public String getRatioGearBox();

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
