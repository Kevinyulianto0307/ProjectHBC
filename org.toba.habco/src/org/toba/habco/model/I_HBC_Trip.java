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

/** Generated Interface for HBC_Trip
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Trip 
{

    /** TableName=HBC_Trip */
    public static final String Table_Name = "HBC_Trip";

    /** AD_Table_ID=1100068 */
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

    /** Column name BBMUsage */
    public static final String COLUMNNAME_BBMUsage = "BBMUsage";

	/** Set BBM Usage Report	  */
	public void setBBMUsage (BigDecimal BBMUsage);

	/** Get BBM Usage Report	  */
	public BigDecimal getBBMUsage();

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

    /** Column name CompleteTrip */
    public static final String COLUMNNAME_CompleteTrip = "CompleteTrip";

	/** Set Complete Trip	  */
	public void setCompleteTrip (String CompleteTrip);

	/** Get Complete Trip	  */
	public String getCompleteTrip();

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

    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/** Set Date Start.
	  * Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart);

	/** Get Date Start.
	  * Date Start for this Order
	  */
	public Timestamp getDateStart();

    /** Column name Day */
    public static final String COLUMNNAME_Day = "Day";

	/** Set Total Trip Day (Sail & Berth)	  */
	public void setDay (BigDecimal Day);

	/** Get Total Trip Day (Sail & Berth)	  */
	public BigDecimal getDay();

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name From_PortPosition_ID */
    public static final String COLUMNNAME_From_PortPosition_ID = "From_PortPosition_ID";

	/** Set Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID);

	/** Get Port/Position From	  */
	public int getFrom_PortPosition_ID();

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException;

    /** Column name FuelActual */
    public static final String COLUMNNAME_FuelActual = "FuelActual";

	/** Set Fuel Actual	  */
	public void setFuelActual (BigDecimal FuelActual);

	/** Get Fuel Actual	  */
	public BigDecimal getFuelActual();

    /** Column name FuelAdjustment */
    public static final String COLUMNNAME_FuelAdjustment = "FuelAdjustment";

	/** Set Fuel Adjustment	  */
	public void setFuelAdjustment (BigDecimal FuelAdjustment);

	/** Get Fuel Adjustment	  */
	public BigDecimal getFuelAdjustment();

    /** Column name FuelBalance */
    public static final String COLUMNNAME_FuelBalance = "FuelBalance";

	/** Set Fuel Balance	  */
	public void setFuelBalance (BigDecimal FuelBalance);

	/** Get Fuel Balance	  */
	public BigDecimal getFuelBalance();

    /** Column name FuelBalanceNew */
    public static final String COLUMNNAME_FuelBalanceNew = "FuelBalanceNew";

	/** Set Fuel Balance New	  */
	public void setFuelBalanceNew (BigDecimal FuelBalanceNew);

	/** Get Fuel Balance New	  */
	public BigDecimal getFuelBalanceNew();

    /** Column name FuelUsageDiscrepancy */
    public static final String COLUMNNAME_FuelUsageDiscrepancy = "FuelUsageDiscrepancy";

	/** Set Fuel Usage Discrepancy	  */
	public void setFuelUsageDiscrepancy (BigDecimal FuelUsageDiscrepancy);

	/** Get Fuel Usage Discrepancy	  */
	public BigDecimal getFuelUsageDiscrepancy();

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_Contract_ID */
    public static final String COLUMNNAME_HBC_Contract_ID = "HBC_Contract_ID";

	/** Set Contract	  */
	public void setHBC_Contract_ID (int HBC_Contract_ID);

	/** Get Contract	  */
	public int getHBC_Contract_ID();

	public I_HBC_Contract getHBC_Contract() throws RuntimeException;

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException;

    /** Column name HBC_ShipReqType */
    public static final String COLUMNNAME_HBC_ShipReqType = "HBC_ShipReqType";

	/** Set Ship Required Type	  */
	public void setHBC_ShipReqType (String HBC_ShipReqType);

	/** Get Ship Required Type	  */
	public String getHBC_ShipReqType();

    /** Column name HBC_Trip_ID */
    public static final String COLUMNNAME_HBC_Trip_ID = "HBC_Trip_ID";

	/** Set Trip	  */
	public void setHBC_Trip_ID (int HBC_Trip_ID);

	/** Get Trip	  */
	public int getHBC_Trip_ID();

    /** Column name HBC_Trip_UU */
    public static final String COLUMNNAME_HBC_Trip_UU = "HBC_Trip_UU";

	/** Set HBC_Trip_UU	  */
	public void setHBC_Trip_UU (String HBC_Trip_UU);

	/** Get HBC_Trip_UU	  */
	public String getHBC_Trip_UU();

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

    /** Column name IsARCalculation */
    public static final String COLUMNNAME_IsARCalculation = "IsARCalculation";

	/** Set AR Calculation	  */
	public void setIsARCalculation (boolean IsARCalculation);

	/** Get AR Calculation	  */
	public boolean isARCalculation();

    /** Column name IsUseCoordinate */
    public static final String COLUMNNAME_IsUseCoordinate = "IsUseCoordinate";

	/** Set Use Coordinate ?	  */
	public void setIsUseCoordinate (boolean IsUseCoordinate);

	/** Get Use Coordinate ?	  */
	public boolean isUseCoordinate();

    /** Column name Liter */
    public static final String COLUMNNAME_Liter = "Liter";

	/** Set Liter	  */
	public void setLiter (BigDecimal Liter);

	/** Get Liter	  */
	public BigDecimal getLiter();

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

    /** Column name LiterCalculation */
    public static final String COLUMNNAME_LiterCalculation = "LiterCalculation";

	/** Set Liter Calculation	  */
	public void setLiterCalculation (BigDecimal LiterCalculation);

	/** Get Liter Calculation	  */
	public BigDecimal getLiterCalculation();

    /** Column name OriginalCoordinate */
    public static final String COLUMNNAME_OriginalCoordinate = "OriginalCoordinate";

	/** Set Original Coordinate	  */
	public void setOriginalCoordinate (String OriginalCoordinate);

	/** Get Original Coordinate	  */
	public String getOriginalCoordinate();

    /** Column name PortPosition_From2_ID */
    public static final String COLUMNNAME_PortPosition_From2_ID = "PortPosition_From2_ID";

	/** Set Port / Position From 2	  */
	public void setPortPosition_From2_ID (int PortPosition_From2_ID);

	/** Get Port / Position From 2	  */
	public int getPortPosition_From2_ID();

	public I_HBC_PortPosition getPortPosition_From2() throws RuntimeException;

    /** Column name PortPosition_To2_ID */
    public static final String COLUMNNAME_PortPosition_To2_ID = "PortPosition_To2_ID";

	/** Set Port / Position To 2	  */
	public void setPortPosition_To2_ID (int PortPosition_To2_ID);

	/** Get Port / Position To 2	  */
	public int getPortPosition_To2_ID();

	public I_HBC_PortPosition getPortPosition_To2() throws RuntimeException;

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ShipFuelUsage */
    public static final String COLUMNNAME_ShipFuelUsage = "ShipFuelUsage";

	/** Set Ship Fuel Usage Report Diff	  */
	public void setShipFuelUsage (BigDecimal ShipFuelUsage);

	/** Get Ship Fuel Usage Report Diff	  */
	public BigDecimal getShipFuelUsage();

    /** Column name SINumber */
    public static final String COLUMNNAME_SINumber = "SINumber";

	/** Set SI Number	  */
	public void setSINumber (String SINumber);

	/** Get SI Number	  */
	public String getSINumber();

    /** Column name To_PortPosition_ID */
    public static final String COLUMNNAME_To_PortPosition_ID = "To_PortPosition_ID";

	/** Set Port/Position To	  */
	public void setTo_PortPosition_ID (int To_PortPosition_ID);

	/** Get Port/Position To	  */
	public int getTo_PortPosition_ID();

	public I_HBC_PortPosition getTo_PortPosition() throws RuntimeException;

    /** Column name TotalCargoQty */
    public static final String COLUMNNAME_TotalCargoQty = "TotalCargoQty";

	/** Set Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty);

	/** Get Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty();

    /** Column name TotalLiters */
    public static final String COLUMNNAME_TotalLiters = "TotalLiters";

	/** Set Total Liters	  */
	public void setTotalLiters (BigDecimal TotalLiters);

	/** Get Total Liters	  */
	public BigDecimal getTotalLiters();

    /** Column name TripAllocationDay */
    public static final String COLUMNNAME_TripAllocationDay = "TripAllocationDay";

	/** Set Trip Allocation Day	  */
	public void setTripAllocationDay (BigDecimal TripAllocationDay);

	/** Get Trip Allocation Day	  */
	public BigDecimal getTripAllocationDay();

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
