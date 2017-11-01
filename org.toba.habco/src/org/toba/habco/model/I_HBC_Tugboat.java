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

/** Generated Interface for HBC_Tugboat
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Tugboat 
{

    /** TableName=HBC_Tugboat */
    public static final String Table_Name = "HBC_Tugboat";

    /** AD_Table_ID=1100000 */
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

    /** Column name CallSign */
    public static final String COLUMNNAME_CallSign = "CallSign";

	/** Set Call Sign	  */
	public void setCallSign (String CallSign);

	/** Get Call Sign	  */
	public String getCallSign();

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

    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/** Set Target Document Type.
	  * Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/** Get Target Document Type.
	  * Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException;

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

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

    /** Column name Current_ShipActivity_ID */
    public static final String COLUMNNAME_Current_ShipActivity_ID = "Current_ShipActivity_ID";

	/** Set Current Ship Activity	  */
	public void setCurrent_ShipActivity_ID (int Current_ShipActivity_ID);

	/** Get Current Ship Activity	  */
	public int getCurrent_ShipActivity_ID();

	public I_HBC_ShipActivity getCurrent_ShipActivity() throws RuntimeException;

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

    /** Column name DiameterPropeller */
    public static final String COLUMNNAME_DiameterPropeller = "DiameterPropeller";

	/** Set Diameter	  */
	public void setDiameterPropeller (BigDecimal DiameterPropeller);

	/** Get Diameter	  */
	public BigDecimal getDiameterPropeller();

    /** Column name Flag */
    public static final String COLUMNNAME_Flag = "Flag";

	/** Set Flag	  */
	public void setFlag (String Flag);

	/** Get Flag	  */
	public String getFlag();

    /** Column name FreshWater */
    public static final String COLUMNNAME_FreshWater = "FreshWater";

	/** Set Fresh Water	  */
	public void setFreshWater (BigDecimal FreshWater);

	/** Get Fresh Water	  */
	public BigDecimal getFreshWater();

    /** Column name FuelBalance */
    public static final String COLUMNNAME_FuelBalance = "FuelBalance";

	/** Set Fuel Balance	  */
	public void setFuelBalance (BigDecimal FuelBalance);

	/** Get Fuel Balance	  */
	public BigDecimal getFuelBalance();

    /** Column name FuelCapacityperDay */
    public static final String COLUMNNAME_FuelCapacityperDay = "FuelCapacityperDay";

	/** Set Fuel Capacity per Day	  */
	public void setFuelCapacityperDay (BigDecimal FuelCapacityperDay);

	/** Get Fuel Capacity per Day	  */
	public BigDecimal getFuelCapacityperDay();

    /** Column name FuelConsumptionMain */
    public static final String COLUMNNAME_FuelConsumptionMain = "FuelConsumptionMain";

	/** Set Fuel Consumption Main	  */
	public void setFuelConsumptionMain (String FuelConsumptionMain);

	/** Get Fuel Consumption Main	  */
	public BigDecimal getFuelConsumptionMain();

    /** Column name FuelConsumptionPerHour */
    public static final String COLUMNNAME_FuelConsumptionPerHour = "FuelConsumptionPerHour";

	/** Set Fuel Consumption Per Hour	  */
	public void setFuelConsumptionPerHour (BigDecimal FuelConsumptionPerHour);

	/** Get Fuel Consumption Per Hour	  */
	public BigDecimal getFuelConsumptionPerHour();

    /** Column name GrossTonnage */
    public static final String COLUMNNAME_GrossTonnage = "GrossTonnage";

	/** Set Gross Tonnage	  */
	public void setGrossTonnage (BigDecimal GrossTonnage);

	/** Get Gross Tonnage	  */
	public BigDecimal getGrossTonnage();

    /** Column name HBC_AuxiliaryEngine_ID */
    public static final String COLUMNNAME_HBC_AuxiliaryEngine_ID = "HBC_AuxiliaryEngine_ID";

	/** Set Auxiliary Engine	  */
	public void setHBC_AuxiliaryEngine_ID (int HBC_AuxiliaryEngine_ID);

	/** Get Auxiliary Engine	  */
	public int getHBC_AuxiliaryEngine_ID();

	public I_HBC_AuxiliaryEngine getHBC_AuxiliaryEngine() throws RuntimeException;

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_GearBox_ID */
    public static final String COLUMNNAME_HBC_GearBox_ID = "HBC_GearBox_ID";

	/** Set Gear Box	  */
	public void setHBC_GearBox_ID (int HBC_GearBox_ID);

	/** Get Gear Box	  */
	public int getHBC_GearBox_ID();

	public I_HBC_GearBox getHBC_GearBox() throws RuntimeException;

    /** Column name HBC_MainEngine_ID */
    public static final String COLUMNNAME_HBC_MainEngine_ID = "HBC_MainEngine_ID";

	/** Set Main Engine	  */
	public void setHBC_MainEngine_ID (int HBC_MainEngine_ID);

	/** Get Main Engine	  */
	public int getHBC_MainEngine_ID();

	public I_HBC_MainEngine getHBC_MainEngine() throws RuntimeException;

    /** Column name HBC_Shipyard_ID */
    public static final String COLUMNNAME_HBC_Shipyard_ID = "HBC_Shipyard_ID";

	/** Set Shipyard	  */
	public void setHBC_Shipyard_ID (int HBC_Shipyard_ID);

	/** Get Shipyard	  */
	public int getHBC_Shipyard_ID();

	public I_HBC_Shipyard getHBC_Shipyard() throws RuntimeException;

    /** Column name HBC_TugboatCategory_ID */
    public static final String COLUMNNAME_HBC_TugboatCategory_ID = "HBC_TugboatCategory_ID";

	/** Set Tugboat Category	  */
	public void setHBC_TugboatCategory_ID (int HBC_TugboatCategory_ID);

	/** Get Tugboat Category	  */
	public int getHBC_TugboatCategory_ID();

	public I_HBC_TugboatCategory getHBC_TugboatCategory() throws RuntimeException;

    /** Column name HBC_Tugboat_ID */
    public static final String COLUMNNAME_HBC_Tugboat_ID = "HBC_Tugboat_ID";

	/** Set TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID);

	/** Get TugBoat	  */
	public int getHBC_Tugboat_ID();

    /** Column name HBC_Tugboat_UU */
    public static final String COLUMNNAME_HBC_Tugboat_UU = "HBC_Tugboat_UU";

	/** Set HBC_Tugboat_UU	  */
	public void setHBC_Tugboat_UU (String HBC_Tugboat_UU);

	/** Get HBC_Tugboat_UU	  */
	public String getHBC_Tugboat_UU();

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

    /** Column name LengthBetweenPerpendicular */
    public static final String COLUMNNAME_LengthBetweenPerpendicular = "LengthBetweenPerpendicular";

	/** Set Length Between Perpendicular	  */
	public void setLengthBetweenPerpendicular (BigDecimal LengthBetweenPerpendicular);

	/** Get Length Between Perpendicular	  */
	public BigDecimal getLengthBetweenPerpendicular();

    /** Column name LengthFeet */
    public static final String COLUMNNAME_LengthFeet = "LengthFeet";

	/** Set Length (Feet)	  */
	public void setLengthFeet (int LengthFeet);

	/** Get Length (Feet)	  */
	public int getLengthFeet();

    /** Column name LengthOverAll */
    public static final String COLUMNNAME_LengthOverAll = "LengthOverAll";

	/** Set Length Over All	  */
	public void setLengthOverAll (BigDecimal LengthOverAll);

	/** Get Length Over All	  */
	public BigDecimal getLengthOverAll();

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

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

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

    /** Column name NumberofBlade */
    public static final String COLUMNNAME_NumberofBlade = "NumberofBlade";

	/** Set Number of Blade	  */
	public void setNumberofBlade (int NumberofBlade);

	/** Get Number of Blade	  */
	public int getNumberofBlade();

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

    /** Column name QtyOfMachine */
    public static final String COLUMNNAME_QtyOfMachine = "QtyOfMachine";

	/** Set Qty Of Machine	  */
	public void setQtyOfMachine (int QtyOfMachine);

	/** Get Qty Of Machine	  */
	public int getQtyOfMachine();

    /** Column name QtyofMachineGear */
    public static final String COLUMNNAME_QtyofMachineGear = "QtyofMachineGear";

	/** Set Qty of Machine 	  */
	public void setQtyofMachineGear (int QtyofMachineGear);

	/** Get Qty of Machine 	  */
	public int getQtyofMachineGear();

    /** Column name QtyofMachineMain */
    public static final String COLUMNNAME_QtyofMachineMain = "QtyofMachineMain";

	/** Set Qty of Machine	  */
	public void setQtyofMachineMain (int QtyofMachineMain);

	/** Get Qty of Machine	  */
	public int getQtyofMachineMain();

    /** Column name Ratio */
    public static final String COLUMNNAME_Ratio = "Ratio";

	/** Set Ratio.
	  * Relative Ratio for Distributions
	  */
	public void setRatio (String Ratio);

	/** Get Ratio.
	  * Relative Ratio for Distributions
	  */
	public String getRatio();

    /** Column name RegistrationSign */
    public static final String COLUMNNAME_RegistrationSign = "RegistrationSign";

	/** Set Registration Sign	  */
	public void setRegistrationSign (String RegistrationSign);

	/** Get Registration Sign	  */
	public String getRegistrationSign();

    /** Column name ReserveFuelCapacity */
    public static final String COLUMNNAME_ReserveFuelCapacity = "ReserveFuelCapacity";

	/** Set Reserve Fuel Capacity	  */
	public void setReserveFuelCapacity (BigDecimal ReserveFuelCapacity);

	/** Get Reserve Fuel Capacity	  */
	public BigDecimal getReserveFuelCapacity();

    /** Column name TotalFuelTankCapacity */
    public static final String COLUMNNAME_TotalFuelTankCapacity = "TotalFuelTankCapacity";

	/** Set Total Fuel Tank Capacity	  */
	public void setTotalFuelTankCapacity (BigDecimal TotalFuelTankCapacity);

	/** Get Total Fuel Tank Capacity	  */
	public BigDecimal getTotalFuelTankCapacity();

    /** Column name TotalHorsePower */
    public static final String COLUMNNAME_TotalHorsePower = "TotalHorsePower";

	/** Set Total Horse Power	  */
	public void setTotalHorsePower (BigDecimal TotalHorsePower);

	/** Get Total Horse Power	  */
	public BigDecimal getTotalHorsePower();

    /** Column name TotalHorsePowerAux */
    public static final String COLUMNNAME_TotalHorsePowerAux = "TotalHorsePowerAux";

	/** Set Total Horse Power	  */
	public void setTotalHorsePowerAux (BigDecimal TotalHorsePowerAux);

	/** Get Total Horse Power	  */
	public BigDecimal getTotalHorsePowerAux();

    /** Column name TotalPropeller */
    public static final String COLUMNNAME_TotalPropeller = "TotalPropeller";

	/** Set Total Propeller	  */
	public void setTotalPropeller (int TotalPropeller);

	/** Get Total Propeller	  */
	public int getTotalPropeller();

    /** Column name TugBoatOwner */
    public static final String COLUMNNAME_TugBoatOwner = "TugBoatOwner";

	/** Set TugBoat Owner	  */
	public void setTugBoatOwner (String TugBoatOwner);

	/** Get TugBoat Owner	  */
	public String getTugBoatOwner();

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
