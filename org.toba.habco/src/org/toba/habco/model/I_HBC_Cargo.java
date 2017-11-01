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

/** Generated Interface for HBC_Cargo
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_Cargo 
{

    /** TableName=HBC_Cargo */
    public static final String Table_Name = "HBC_Cargo";

    /** AD_Table_ID=1100016 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActivityCargo_ID */
    public static final String COLUMNNAME_ActivityCargo_ID = "ActivityCargo_ID";

	/** Set Activity	  */
	public void setActivityCargo_ID (int ActivityCargo_ID);

	/** Get Activity	  */
	public int getActivityCargo_ID();

	public org.compiere.model.I_C_Activity getActivityCargo() throws RuntimeException;

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

    /** Column name AmtofCargo */
    public static final String COLUMNNAME_AmtofCargo = "AmtofCargo";

	/** Set Amount of Cargo	  */
	public void setAmtofCargo (BigDecimal AmtofCargo);

	/** Get Amount of Cargo	  */
	public BigDecimal getAmtofCargo();

    /** Column name BalanceCargo */
    public static final String COLUMNNAME_BalanceCargo = "BalanceCargo";

	/** Set Balance Cargo	  */
	public void setBalanceCargo (BigDecimal BalanceCargo);

	/** Get Balance Cargo	  */
	public BigDecimal getBalanceCargo();

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

    /** Column name HBC_Cargo_ID */
    public static final String COLUMNNAME_HBC_Cargo_ID = "HBC_Cargo_ID";

	/** Set Cargo	  */
	public void setHBC_Cargo_ID (int HBC_Cargo_ID);

	/** Get Cargo	  */
	public int getHBC_Cargo_ID();

    /** Column name HBC_Cargo_UU */
    public static final String COLUMNNAME_HBC_Cargo_UU = "HBC_Cargo_UU";

	/** Set HBC_Cargo_UU	  */
	public void setHBC_Cargo_UU (String HBC_Cargo_UU);

	/** Get HBC_Cargo_UU	  */
	public String getHBC_Cargo_UU();

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

	public I_HBC_PortPosition getHBC_PortPosition() throws RuntimeException;

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

	public I_HBC_ShipPositionLine getHBC_ShipPositionLine() throws RuntimeException;

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
}
