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

/** Generated Interface for HBC_Demurrage
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Demurrage 
{

    /** TableName=HBC_Demurrage */
    public static final String Table_Name = "HBC_Demurrage";

    /** AD_Table_ID=1100018 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActiveFrom */
    public static final String COLUMNNAME_ActiveFrom = "ActiveFrom";

	/** Set Active From	  */
	public void setActiveFrom (Timestamp ActiveFrom);

	/** Get Active From	  */
	public Timestamp getActiveFrom();

    /** Column name ActiveTo */
    public static final String COLUMNNAME_ActiveTo = "ActiveTo";

	/** Set Active To	  */
	public void setActiveTo (Timestamp ActiveTo);

	/** Get Active To	  */
	public Timestamp getActiveTo();

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

    /** Column name BargeCategory */
    public static final String COLUMNNAME_BargeCategory = "BargeCategory";

	/** Set BargeCategory	  */
	public void setBargeCategory (String BargeCategory);

	/** Get BargeCategory	  */
	public String getBargeCategory();

    /** Column name BargeCategoryTo_ID */
    public static final String COLUMNNAME_BargeCategoryTo_ID = "BargeCategoryTo_ID";

	/** Set Barge Category To	  */
	public void setBargeCategoryTo_ID (int BargeCategoryTo_ID);

	/** Get Barge Category To	  */
	public int getBargeCategoryTo_ID();

	public I_HBC_BargeCategory getBargeCategoryTo() throws RuntimeException;

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

    /** Column name DemurrageAmt */
    public static final String COLUMNNAME_DemurrageAmt = "DemurrageAmt";

	/** Set Demurrage Amount	  */
	public void setDemurrageAmt (BigDecimal DemurrageAmt);

	/** Get Demurrage Amount	  */
	public BigDecimal getDemurrageAmt();

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

    /** Column name FreightPercentage */
    public static final String COLUMNNAME_FreightPercentage = "FreightPercentage";

	/** Set Freight Percentage	  */
	public void setFreightPercentage (BigDecimal FreightPercentage);

	/** Get Freight Percentage	  */
	public BigDecimal getFreightPercentage();

    /** Column name HBC_BargeCategory_ID */
    public static final String COLUMNNAME_HBC_BargeCategory_ID = "HBC_BargeCategory_ID";

	/** Set Barge Category	  */
	public void setHBC_BargeCategory_ID (int HBC_BargeCategory_ID);

	/** Get Barge Category	  */
	public int getHBC_BargeCategory_ID();

	public I_HBC_BargeCategory getHBC_BargeCategory() throws RuntimeException;

    /** Column name HBC_Contract_ID */
    public static final String COLUMNNAME_HBC_Contract_ID = "HBC_Contract_ID";

	/** Set Contract	  */
	public void setHBC_Contract_ID (int HBC_Contract_ID);

	/** Get Contract	  */
	public int getHBC_Contract_ID();

	public I_HBC_Contract getHBC_Contract() throws RuntimeException;

    /** Column name HBC_Demurrage_ID */
    public static final String COLUMNNAME_HBC_Demurrage_ID = "HBC_Demurrage_ID";

	/** Set Demurrage	  */
	public void setHBC_Demurrage_ID (int HBC_Demurrage_ID);

	/** Get Demurrage	  */
	public int getHBC_Demurrage_ID();

    /** Column name HBC_Demurrage_UU */
    public static final String COLUMNNAME_HBC_Demurrage_UU = "HBC_Demurrage_UU";

	/** Set HBC_Demurrage_UU	  */
	public void setHBC_Demurrage_UU (String HBC_Demurrage_UU);

	/** Get HBC_Demurrage_UU	  */
	public String getHBC_Demurrage_UU();

    /** Column name HBC_DemurrageCategory_ID */
    public static final String COLUMNNAME_HBC_DemurrageCategory_ID = "HBC_DemurrageCategory_ID";

	/** Set Demurrage Category	  */
	public void setHBC_DemurrageCategory_ID (int HBC_DemurrageCategory_ID);

	/** Get Demurrage Category	  */
	public int getHBC_DemurrageCategory_ID();

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

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsProrate */
    public static final String COLUMNNAME_IsProrate = "IsProrate";

	/** Set Prorate	  */
	public void setIsProrate (boolean IsProrate);

	/** Get Prorate	  */
	public boolean isProrate();

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

    /** Column name ProrateDay */
    public static final String COLUMNNAME_ProrateDay = "ProrateDay";

	/** Set Prorate Day	  */
	public void setProrateDay (int ProrateDay);

	/** Get Prorate Day	  */
	public int getProrateDay();

    /** Column name TotalLoadingDay */
    public static final String COLUMNNAME_TotalLoadingDay = "TotalLoadingDay";

	/** Set Total Loading Day	  */
	public void setTotalLoadingDay (BigDecimal TotalLoadingDay);

	/** Get Total Loading Day	  */
	public BigDecimal getTotalLoadingDay();

    /** Column name TotalUnloadingDay */
    public static final String COLUMNNAME_TotalUnloadingDay = "TotalUnloadingDay";

	/** Set Total Unloading Day	  */
	public void setTotalUnloadingDay (BigDecimal TotalUnloadingDay);

	/** Get Total Unloading Day	  */
	public BigDecimal getTotalUnloadingDay();

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

    /** Column name WeatherPercentage */
    public static final String COLUMNNAME_WeatherPercentage = "WeatherPercentage";

	/** Set Weather Percentage	  */
	public void setWeatherPercentage (BigDecimal WeatherPercentage);

	/** Get Weather Percentage	  */
	public BigDecimal getWeatherPercentage();
}
