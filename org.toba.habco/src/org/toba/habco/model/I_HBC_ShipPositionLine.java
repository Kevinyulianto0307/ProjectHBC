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

/** Generated Interface for HBC_ShipPositionLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_ShipPositionLine 
{

    /** TableName=HBC_ShipPositionLine */
    public static final String Table_Name = "HBC_ShipPositionLine";

    /** AD_Table_ID=1100015 */
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

    /** Column name AmountOfCargo */
    public static final String COLUMNNAME_AmountOfCargo = "AmountOfCargo";

	/** Set Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo);

	/** Get Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo();

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

    /** Column name CompleteBunkering */
    public static final String COLUMNNAME_CompleteBunkering = "CompleteBunkering";

	/** Set Complete Bunkering	  */
	public void setCompleteBunkering (String CompleteBunkering);

	/** Get Complete Bunkering	  */
	public String getCompleteBunkering();

    /** Column name CompleteFuelTransfer */
    public static final String COLUMNNAME_CompleteFuelTransfer = "CompleteFuelTransfer";

	/** Set Complete Fuel Transfer	  */
	public void setCompleteFuelTransfer (String CompleteFuelTransfer);

	/** Get Complete Fuel Transfer	  */
	public String getCompleteFuelTransfer();

    /** Column name CompleteOvertowing */
    public static final String COLUMNNAME_CompleteOvertowing = "CompleteOvertowing";

	/** Set Complete Overtowing	  */
	public void setCompleteOvertowing (String CompleteOvertowing);

	/** Get Complete Overtowing	  */
	public String getCompleteOvertowing();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name DischargingComplete */
    public static final String COLUMNNAME_DischargingComplete = "DischargingComplete";

	/** Set Complete Discharging	  */
	public void setDischargingComplete (String DischargingComplete);

	/** Get Complete Discharging	  */
	public String getDischargingComplete();

    /** Column name DraftBack */
    public static final String COLUMNNAME_DraftBack = "DraftBack";

	/** Set Draft (Back)	  */
	public void setDraftBack (BigDecimal DraftBack);

	/** Get Draft (Back)	  */
	public BigDecimal getDraftBack();

    /** Column name DraftFront */
    public static final String COLUMNNAME_DraftFront = "DraftFront";

	/** Set Draft (Front)	  */
	public void setDraftFront (BigDecimal DraftFront);

	/** Get Draft (Front)	  */
	public BigDecimal getDraftFront();

    /** Column name FinishDate */
    public static final String COLUMNNAME_FinishDate = "FinishDate";

	/** Set Finish Date	  */
	public void setFinishDate (Timestamp FinishDate);

	/** Get Finish Date	  */
	public Timestamp getFinishDate();

    /** Column name From_PortPosition_ID */
    public static final String COLUMNNAME_From_PortPosition_ID = "From_PortPosition_ID";

	/** Set Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID);

	/** Get Port/Position From	  */
	public int getFrom_PortPosition_ID();

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException;

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_Jetty_ID */
    public static final String COLUMNNAME_HBC_Jetty_ID = "HBC_Jetty_ID";

	/** Set Jetty	  */
	public void setHBC_Jetty_ID (int HBC_Jetty_ID);

	/** Get Jetty	  */
	public int getHBC_Jetty_ID();

	public I_HBC_Jetty getHBC_Jetty() throws RuntimeException;

    /** Column name HBC_ShipPosition_ID */
    public static final String COLUMNNAME_HBC_ShipPosition_ID = "HBC_ShipPosition_ID";

	/** Set Ship Position	  */
	public void setHBC_ShipPosition_ID (int HBC_ShipPosition_ID);

	/** Get Ship Position	  */
	public int getHBC_ShipPosition_ID();

	public I_HBC_ShipPosition getHBC_ShipPosition() throws RuntimeException;

    /** Column name HBC_ShipPositionLine_ID */
    public static final String COLUMNNAME_HBC_ShipPositionLine_ID = "HBC_ShipPositionLine_ID";

	/** Set Ship Position Line	  */
	public void setHBC_ShipPositionLine_ID (int HBC_ShipPositionLine_ID);

	/** Get Ship Position Line	  */
	public int getHBC_ShipPositionLine_ID();

    /** Column name HBC_ShipPositionLine_UU */
    public static final String COLUMNNAME_HBC_ShipPositionLine_UU = "HBC_ShipPositionLine_UU";

	/** Set HBC_ShipPositionLine_UU	  */
	public void setHBC_ShipPositionLine_UU (String HBC_ShipPositionLine_UU);

	/** Get HBC_ShipPositionLine_UU	  */
	public String getHBC_ShipPositionLine_UU();

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

    /** Column name IsDemurrageFreight */
    public static final String COLUMNNAME_IsDemurrageFreight = "IsDemurrageFreight";

	/** Set Demurrage Freight	  */
	public void setIsDemurrageFreight (boolean IsDemurrageFreight);

	/** Get Demurrage Freight	  */
	public boolean isDemurrageFreight();

    /** Column name IsDemurrageWeather */
    public static final String COLUMNNAME_IsDemurrageWeather = "IsDemurrageWeather";

	/** Set Demurrage Weather	  */
	public void setIsDemurrageWeather (boolean IsDemurrageWeather);

	/** Get Demurrage Weather	  */
	public boolean isDemurrageWeather();

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

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name M_Inventory_ID */
    public static final String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/** Set Phys.Inventory.
	  * Parameters for a Physical Inventory
	  */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/** Get Phys.Inventory.
	  * Parameters for a Physical Inventory
	  */
	public int getM_Inventory_ID();

	public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException;

    /** Column name M_Movement_ID */
    public static final String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

	/** Set Inventory Move.
	  * Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID);

	/** Get Inventory Move.
	  * Movement of Inventory
	  */
	public int getM_Movement_ID();

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException;

    /** Column name MotherVessel */
    public static final String COLUMNNAME_MotherVessel = "MotherVessel";

	/** Set Mother Vessel	  */
	public void setMotherVessel (String MotherVessel);

	/** Get Mother Vessel	  */
	public String getMotherVessel();

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

    /** Column name ShipPositionLinePartner_ID */
    public static final String COLUMNNAME_ShipPositionLinePartner_ID = "ShipPositionLinePartner_ID";

	/** Set Timesheet Line Partner	  */
	public void setShipPositionLinePartner_ID (int ShipPositionLinePartner_ID);

	/** Get Timesheet Line Partner	  */
	public int getShipPositionLinePartner_ID();

	public I_HBC_ShipPositionLine getShipPositionLinePartner() throws RuntimeException;

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * The Start Date indicates the first or starting date
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * The Start Date indicates the first or starting date
	  */
	public Timestamp getStartDate();

    /** Column name To_PortPosition_ID */
    public static final String COLUMNNAME_To_PortPosition_ID = "To_PortPosition_ID";

	/** Set Port/Position To	  */
	public void setTo_PortPosition_ID (int To_PortPosition_ID);

	/** Get Port/Position To	  */
	public int getTo_PortPosition_ID();

	public I_HBC_PortPosition getTo_PortPosition() throws RuntimeException;

    /** Column name TotalLiters */
    public static final String COLUMNNAME_TotalLiters = "TotalLiters";

	/** Set Total Liters	  */
	public void setTotalLiters (BigDecimal TotalLiters);

	/** Get Total Liters	  */
	public BigDecimal getTotalLiters();

    /** Column name TugBoatPartner_ID */
    public static final String COLUMNNAME_TugBoatPartner_ID = "TugBoatPartner_ID";

	/** Set TugBoat Partner	  */
	public void setTugBoatPartner_ID (int TugBoatPartner_ID);

	/** Get TugBoat Partner	  */
	public int getTugBoatPartner_ID();

	public I_HBC_Tugboat getTugBoatPartner() throws RuntimeException;

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
