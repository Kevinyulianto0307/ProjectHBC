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

/** Generated Interface for HBC_FuelTripAllocation
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_FuelTripAllocation 
{

    /** TableName=HBC_FuelTripAllocation */
    public static final String Table_Name = "HBC_FuelTripAllocation";

    /** AD_Table_ID=1100108 */
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
	
	 /** Column name Hour */
    public static final String COLUMNNAME_Hour = "Hour";

	/** Set Hour  */
	public void setHour (BigDecimal Hour);

	/** Get Hour  */
	public BigDecimal getHour();

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

    /** Column name From_PortPosition_ID */
    public static final String COLUMNNAME_From_PortPosition_ID = "From_PortPosition_ID";

	/** Set Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID);

	/** Get Port/Position From	  */
	public int getFrom_PortPosition_ID();

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException;

    /** Column name FuelAllocation */
    public static final String COLUMNNAME_FuelAllocation = "FuelAllocation";

	/** Set Fuel Allocation	  */
	public void setFuelAllocation (BigDecimal FuelAllocation);

	/** Get Fuel Allocation	  */
	public BigDecimal getFuelAllocation();

    /** Column name HBC_BargeCategory_ID */
    public static final String COLUMNNAME_HBC_BargeCategory_ID = "HBC_BargeCategory_ID";

	/** Set Barge Category	  */
	public void setHBC_BargeCategory_ID (int HBC_BargeCategory_ID);

	/** Get Barge Category	  */
	public int getHBC_BargeCategory_ID();

	public I_HBC_BargeCategory getHBC_BargeCategory() throws RuntimeException;

    /** Column name HBC_FuelTripAllocation_ID */
    public static final String COLUMNNAME_HBC_FuelTripAllocation_ID = "HBC_FuelTripAllocation_ID";

	/** Set Fuel Trip Allocation	  */
	public void setHBC_FuelTripAllocation_ID (int HBC_FuelTripAllocation_ID);

	/** Get Fuel Trip Allocation	  */
	public int getHBC_FuelTripAllocation_ID();

    /** Column name HBC_FuelTripAllocation_UU */
    public static final String COLUMNNAME_HBC_FuelTripAllocation_UU = "HBC_FuelTripAllocation_UU";

	/** Set HBC_FuelTripAllocation_UU	  */
	public void setHBC_FuelTripAllocation_UU (String HBC_FuelTripAllocation_UU);

	/** Get HBC_FuelTripAllocation_UU	  */
	public String getHBC_FuelTripAllocation_UU();

    /** Column name HBC_TripAllocation_ID */
    public static final String COLUMNNAME_HBC_TripAllocation_ID = "HBC_TripAllocation_ID";

	/** Set Trip Allocation	  */
	public void setHBC_TripAllocation_ID (int HBC_TripAllocation_ID);

	/** Get Trip Allocation	  */
	public int getHBC_TripAllocation_ID();

	public I_HBC_TripAllocation getHBC_TripAllocation() throws RuntimeException;

    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_HBC_Trip_ID = "HBC_Trip_ID";

	/** Set Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID);

	/** Get Trip	  */
	public int getHBC_Trip_ID();

	public I_HBC_Trip getHBC_Trip() throws RuntimeException;

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
	
	/** Column name HBC_TugboatCategory_ID */
    public static final String COLUMNNAME_HBC_TripType_ID = "HBC_TripType_ID";

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

    /** Column name IsProcess */
    public static final String COLUMNNAME_IsProcess = "IsProcess";

	/** Set Processed	  */
	public void setIsProcess (boolean IsProcess);

	/** Get Processed	  */
	public boolean isProcess();

    /** Column name IsTripDalam */
    public static final String COLUMNNAME_IsTripDalam = "IsTripDalam";

	/** Set Trip Dalam?	  */
	public void setIsTripDalam (boolean IsTripDalam);

	/** Get Trip Dalam?	  */
	public boolean isTripDalam();

    /** Column name LiterAllocation */
    public static final String COLUMNNAME_LiterAllocation = "LiterAllocation";

	/** Set Liter Allocation.
	  * Liter Allocation
	  */
	public void setLiterAllocation (BigDecimal LiterAllocation);

	/** Get Liter Allocation.
	  * Liter Allocation
	  */
	public BigDecimal getLiterAllocation();

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

    /** Column name Speed */
    public static final String COLUMNNAME_Speed = "Speed";

	/** Set Speed (Knot)	  */
	public void setSpeed (BigDecimal Speed);

	/** Get Speed (Knot)	  */
	public BigDecimal getSpeed();
	
	/** Column name Distance */
    public static final String COLUMNNAME_Distance = "Distance";

    /** Column name To_PortPosition_ID */
    public static final String COLUMNNAME_To_PortPosition_ID = "To_PortPosition_ID";

	/** Set Port/Position To	  */
	public void setTo_PortPosition_ID (int To_PortPosition_ID);

	/** Get Port/Position To	  */
	public int getTo_PortPosition_ID();

	public I_HBC_PortPosition getTo_PortPosition() throws RuntimeException;

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
	
	/** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set ValidFrom  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get ValidFrom  */
	public Timestamp getValidFrom();
	
	/** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set ValidTo  */
	public void setValidTo (Timestamp ValidTo);

	/** Get ValidTo  */
	public Timestamp getValidTo();
}
