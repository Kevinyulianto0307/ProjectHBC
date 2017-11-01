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

/** Generated Interface for HBC_Position
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Position 
{

    /** TableName=HBC_Position */
    public static final String Table_Name = "HBC_Position";

    /** AD_Table_ID=1100069 */
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

    /** Column name AllocationCode */
    public static final String COLUMNNAME_AllocationCode = "AllocationCode";

	/** Set Allocation Number	  */
	public void setAllocationCode (int AllocationCode);

	/** Get Allocation Number	  */
	public int getAllocationCode();

    /** Column name allocationno */
    public static final String COLUMNNAME_allocationno = "allocationno";

	/** Set allocationno	  */
	public void setallocationno (String allocationno);

	/** Get allocationno	  */
	public String getallocationno();

    /** Column name Coordinates */
    public static final String COLUMNNAME_Coordinates = "Coordinates";

	/** Set Coordinates.
	  * Location coordinate
	  */
	public void setCoordinates (String Coordinates);

	/** Get Coordinates.
	  * Location coordinate
	  */
	public String getCoordinates();

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

    /** Column name DifferenceDays */
    public static final String COLUMNNAME_DifferenceDays = "DifferenceDays";

	/** Set Difference Days	  */
	public void setDifferenceDays (BigDecimal DifferenceDays);

	/** Get Difference Days	  */
	public BigDecimal getDifferenceDays();

    /** Column name DifferenceHour */
    public static final String COLUMNNAME_DifferenceHour = "DifferenceHour";

	/** Set Difference Hour	  */
	public void setDifferenceHour (BigDecimal DifferenceHour);

	/** Get Difference Hour	  */
	public BigDecimal getDifferenceHour();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";
    
    /** Column name TotalCargoQty */
    public static final String COLUMNNAME_TotalCargoQty = "TotalCargoQty";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException;

    /** Column name HBC_Position_ID */
    public static final String COLUMNNAME_HBC_Position_ID = "HBC_Position_ID";

	/** Set Ship Position	  */
	public void setHBC_Position_ID (int HBC_Position_ID);

	/** Get Ship Position	  */
	public int getHBC_Position_ID();

    /** Column name HBC_Position_UU */
    public static final String COLUMNNAME_HBC_Position_UU = "HBC_Position_UU";

	/** Set HBC_Position_UU	  */
	public void setHBC_Position_UU (String HBC_Position_UU);

	/** Get HBC_Position_UU	  */
	public String getHBC_Position_UU();

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

    /** Column name IsAllocates */
    public static final String COLUMNNAME_IsAllocates = "IsAllocates";

	/** Set Allocate	  */
	public void setIsAllocates (boolean IsAllocates);

	/** Get Allocate	  */
	public boolean isAllocates();

    /** Column name IsSail */
    public static final String COLUMNNAME_IsSail = "IsSail";

	/** Set IsSail	  */
	public void setIsSail (boolean IsSail);

	/** Get IsSail	  */
	public boolean isSail();

    /** Column name IsUseCoordinate */
    public static final String COLUMNNAME_IsUseCoordinate = "IsUseCoordinate";

	/** Set Use Coordinate ?	  */
	public void setIsUseCoordinate (boolean IsUseCoordinate);

	/** Get Use Coordinate ?	  */
	public boolean isUseCoordinate();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

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

    /** Column name ProcessOpenGM */
    public static final String COLUMNNAME_ProcessOpenGM = "ProcessOpenGM";

	/** Set Open Google Maps	  */
	public void setProcessOpenGM (String ProcessOpenGM);

	/** Get Open Google Maps	  */
	public String getProcessOpenGM();

    /** Column name Speed */
    public static final String COLUMNNAME_Speed = "Speed";

	/** Set Speed (Knot)	  */
	public void setSpeed (BigDecimal Speed);

	/** Get Speed (Knot)	  */
	public BigDecimal getSpeed();

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
