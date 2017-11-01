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

/** Generated Interface for HBC_Barge
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Barge 
{

    /** TableName=HBC_Barge */
    public static final String Table_Name = "HBC_Barge";

    /** AD_Table_ID=1100001 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException;

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

    /** Column name Barge270 */
    public static final String COLUMNNAME_Barge270 = "Barge270";

	/** Set Barge > 270	  */
	public void setBarge270 (boolean Barge270);

	/** Get Barge > 270	  */
	public boolean isBarge270();

    /** Column name BargeOwner */
    public static final String COLUMNNAME_BargeOwner = "BargeOwner";

	/** Set Barge Owner	  */
	public void setBargeOwner (String BargeOwner);

	/** Get Barge Owner	  */
	public String getBargeOwner();

    /** Column name BuildClass */
    public static final String COLUMNNAME_BuildClass = "BuildClass";

	/** Set Build Class	  */
	public void setBuildClass (String BuildClass);

	/** Get Build Class	  */
	public String getBuildClass();

    /** Column name BuildYear */
    public static final String COLUMNNAME_BuildYear = "BuildYear";

	/** Set Build Year	  */
	public void setBuildYear (String BuildYear);

	/** Get Build Year	  */
	public String getBuildYear();

    /** Column name BuyDate */
    public static final String COLUMNNAME_BuyDate = "BuyDate";

	/** Set Buy Date	  */
	public void setBuyDate (Timestamp BuyDate);

	/** Get Buy Date	  */
	public Timestamp getBuyDate();

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Country.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Country.
	  * Country 
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

    /** Column name CallSign */
    public static final String COLUMNNAME_CallSign = "CallSign";

	/** Set Call Sign	  */
	public void setCallSign (String CallSign);

	/** Get Call Sign	  */
	public String getCallSign();

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

    /** Column name DesignDraft */
    public static final String COLUMNNAME_DesignDraft = "DesignDraft";

	/** Set Design Draft	  */
	public void setDesignDraft (BigDecimal DesignDraft);

	/** Get Design Draft	  */
	public BigDecimal getDesignDraft();

    /** Column name Feet */
    public static final String COLUMNNAME_Feet = "Feet";

	/** Set Feet	  */
	public void setFeet (BigDecimal Feet);

	/** Get Feet	  */
	public BigDecimal getFeet();

    /** Column name GrossTonnage */
    public static final String COLUMNNAME_GrossTonnage = "GrossTonnage";

	/** Set Gross Tonnage	  */
	public void setGrossTonnage (BigDecimal GrossTonnage);

	/** Get Gross Tonnage	  */
	public BigDecimal getGrossTonnage();

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

    /** Column name HBC_Barge_UU */
    public static final String COLUMNNAME_HBC_Barge_UU = "HBC_Barge_UU";

	/** Set HBC_Barge_UU	  */
	public void setHBC_Barge_UU (String HBC_Barge_UU);

	/** Get HBC_Barge_UU	  */
	public String getHBC_Barge_UU();

    /** Column name HBC_BargeCategory_ID */
    public static final String COLUMNNAME_HBC_BargeCategory_ID = "HBC_BargeCategory_ID";

	/** Set Barge Category	  */
	public void setHBC_BargeCategory_ID (int HBC_BargeCategory_ID);

	/** Get Barge Category	  */
	public int getHBC_BargeCategory_ID();

	public I_HBC_BargeCategory getHBC_BargeCategory() throws RuntimeException;

    /** Column name HBC_Shipyard_ID */
    public static final String COLUMNNAME_HBC_Shipyard_ID = "HBC_Shipyard_ID";

	/** Set Shipyard	  */
	public void setHBC_Shipyard_ID (int HBC_Shipyard_ID);

	/** Get Shipyard	  */
	public int getHBC_Shipyard_ID();

	public I_HBC_Shipyard getHBC_Shipyard() throws RuntimeException;

    /** Column name HBC_Tugboat_ID */
    public static final String COLUMNNAME_HBC_Tugboat_ID = "HBC_Tugboat_ID";

	/** Set TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID);

	/** Get TugBoat	  */
	public int getHBC_Tugboat_ID();

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException;

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

    /** Column name IsFeet */
    public static final String COLUMNNAME_IsFeet = "IsFeet";

	/** Set Feet	  */
	public void setIsFeet (boolean IsFeet);

	/** Get Feet	  */
	public boolean isFeet();

    /** Column name IsJumbo */
    public static final String COLUMNNAME_IsJumbo = "IsJumbo";

	/** Set Jumbo	  */
	public void setIsJumbo (boolean IsJumbo);

	/** Get Jumbo	  */
	public boolean isJumbo();

    /** Column name Length */
    public static final String COLUMNNAME_Length = "Length";

	/** Set Length	  */
	public void setLength (BigDecimal Length);

	/** Get Length	  */
	public BigDecimal getLength();

    /** Column name LengthFeet */
    public static final String COLUMNNAME_LengthFeet = "LengthFeet";

	/** Set Length (Feet)	  */
	public void setLengthFeet (int LengthFeet);

	/** Get Length (Feet)	  */
	public int getLengthFeet();

    /** Column name LengthofParticular */
    public static final String COLUMNNAME_LengthofParticular = "LengthofParticular";

	/** Set Length of Particular (M)	  */
	public void setLengthofParticular (BigDecimal LengthofParticular);

	/** Get Length of Particular (M)	  */
	public BigDecimal getLengthofParticular();

    /** Column name MaxCargo */
    public static final String COLUMNNAME_MaxCargo = "MaxCargo";

	/** Set Max Cargo	  */
	public void setMaxCargo (BigDecimal MaxCargo);

	/** Get Max Cargo	  */
	public BigDecimal getMaxCargo();

    /** Column name MaxDraft */
    public static final String COLUMNNAME_MaxDraft = "MaxDraft";

	/** Set Max Draft	  */
	public void setMaxDraft (BigDecimal MaxDraft);

	/** Get Max Draft	  */
	public BigDecimal getMaxDraft();

    /** Column name MinCargo */
    public static final String COLUMNNAME_MinCargo = "MinCargo";

	/** Set Min Cargo	  */
	public void setMinCargo (BigDecimal MinCargo);

	/** Get Min Cargo	  */
	public BigDecimal getMinCargo();

    /** Column name MinDraft */
    public static final String COLUMNNAME_MinDraft = "MinDraft";

	/** Set Minimum Draft (M)	  */
	public void setMinDraft (BigDecimal MinDraft);

	/** Get Minimum Draft (M)	  */
	public BigDecimal getMinDraft();

    /** Column name MouldedDepth */
    public static final String COLUMNNAME_MouldedDepth = "MouldedDepth";

	/** Set Moulded Depth	  */
	public void setMouldedDepth (BigDecimal MouldedDepth);

	/** Get Moulded Depth	  */
	public BigDecimal getMouldedDepth();

    /** Column name MouldedDepthFeet */
    public static final String COLUMNNAME_MouldedDepthFeet = "MouldedDepthFeet";

	/** Set Moulded Depth (Feet)	  */
	public void setMouldedDepthFeet (int MouldedDepthFeet);

	/** Get Moulded Depth (Feet)	  */
	public int getMouldedDepthFeet();

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

    /** Column name NettTonnage */
    public static final String COLUMNNAME_NettTonnage = "NettTonnage";

	/** Set Nett Tonnage	  */
	public void setNettTonnage (BigDecimal NettTonnage);

	/** Get Nett Tonnage	  */
	public BigDecimal getNettTonnage();

    /** Column name OriginalClass */
    public static final String COLUMNNAME_OriginalClass = "OriginalClass";

	/** Set Original Class	  */
	public void setOriginalClass (String OriginalClass);

	/** Get Original Class	  */
	public String getOriginalClass();

    /** Column name ParticularSign */
    public static final String COLUMNNAME_ParticularSign = "ParticularSign";

	/** Set Particular Sign	  */
	public void setParticularSign (String ParticularSign);

	/** Get Particular Sign	  */
	public String getParticularSign();

    /** Column name PortofRegistry */
    public static final String COLUMNNAME_PortofRegistry = "PortofRegistry";

	/** Set Port of Registry	  */
	public void setPortofRegistry (String PortofRegistry);

	/** Get Port of Registry	  */
	public String getPortofRegistry();

    /** Column name RegistrationSign */
    public static final String COLUMNNAME_RegistrationSign = "RegistrationSign";

	/** Set Registration Sign	  */
	public void setRegistrationSign (String RegistrationSign);

	/** Get Registration Sign	  */
	public String getRegistrationSign();

    /** Column name SideBoardTall */
    public static final String COLUMNNAME_SideBoardTall = "SideBoardTall";

	/** Set Side Board Tall	  */
	public void setSideBoardTall (BigDecimal SideBoardTall);

	/** Get Side Board Tall	  */
	public BigDecimal getSideBoardTall();

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

    /** Column name Width */
    public static final String COLUMNNAME_Width = "Width";

	/** Set Width	  */
	public void setWidth (BigDecimal Width);

	/** Get Width	  */
	public BigDecimal getWidth();

    /** Column name WidthFeet */
    public static final String COLUMNNAME_WidthFeet = "WidthFeet";

	/** Set Width (Feet)	  */
	public void setWidthFeet (int WidthFeet);

	/** Get Width (Feet)	  */
	public int getWidthFeet();
}
