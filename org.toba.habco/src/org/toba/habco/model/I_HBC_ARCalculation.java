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

/** Generated Interface for HBC_ARCalculation
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_ARCalculation 
{

    /** TableName=HBC_ARCalculation */
    public static final String Table_Name = "HBC_ARCalculation";

    /** AD_Table_ID=1100091 */
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

    /** Column name AmountCharge */
    public static final String COLUMNNAME_AmountCharge = "AmountCharge";

	/** Set Amount Charge	  */
	public void setAmountCharge (BigDecimal AmountCharge);

	/** Get Amount Charge	  */
	public BigDecimal getAmountCharge();

    /** Column name AmountOfCargo */
    public static final String COLUMNNAME_AmountOfCargo = "AmountOfCargo";

	/** Set Amount Of Cargo	  */
	public void setAmountOfCargo (BigDecimal AmountOfCargo);

	/** Get Amount Of Cargo	  */
	public BigDecimal getAmountOfCargo();

    /** Column name ARCalculationType */
    public static final String COLUMNNAME_ARCalculationType = "ARCalculationType";

	/** Set AR Calculation Type	  */
	public void setARCalculationType (String ARCalculationType);

	/** Get AR Calculation Type	  */
	public String getARCalculationType();

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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name CreateInvoice */
    public static final String COLUMNNAME_CreateInvoice = "CreateInvoice";

	/** Set CreateInvoice	  */
	public void setCreateInvoice (String CreateInvoice);

	/** Get CreateInvoice	  */
	public String getCreateInvoice();

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

    /** Column name HBC_ARCalculation_ID */
    public static final String COLUMNNAME_HBC_ARCalculation_ID = "HBC_ARCalculation_ID";

	/** Set AR Calculation	  */
	public void setHBC_ARCalculation_ID (int HBC_ARCalculation_ID);

	/** Get AR Calculation	  */
	public int getHBC_ARCalculation_ID();

    /** Column name HBC_ARCalculation_UU */
    public static final String COLUMNNAME_HBC_ARCalculation_UU = "HBC_ARCalculation_UU";

	/** Set HBC_ARCalculation_UU	  */
	public void setHBC_ARCalculation_UU (String HBC_ARCalculation_UU);

	/** Get HBC_ARCalculation_UU	  */
	public String getHBC_ARCalculation_UU();

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

    /** Column name HBC_Position_ID */
    public static final String COLUMNNAME_HBC_Position_ID = "HBC_Position_ID";

	/** Set Ship Position	  */
	public void setHBC_Position_ID (int HBC_Position_ID);

	/** Get Ship Position	  */
	public int getHBC_Position_ID();

	public I_HBC_Position getHBC_Position() throws RuntimeException;

    /** Column name HBC_ShipActivity_ID */
    public static final String COLUMNNAME_HBC_ShipActivity_ID = "HBC_ShipActivity_ID";

	/** Set Ship Activity	  */
	public void setHBC_ShipActivity_ID (int HBC_ShipActivity_ID);

	/** Get Ship Activity	  */
	public int getHBC_ShipActivity_ID();

	public I_HBC_ShipActivity getHBC_ShipActivity() throws RuntimeException;

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name QtyCharge */
    public static final String COLUMNNAME_QtyCharge = "QtyCharge";

	/** Set Qty Charge.
	  * Quantity Charge
	  */
	public void setQtyCharge (BigDecimal QtyCharge);

	/** Get Qty Charge.
	  * Quantity Charge
	  */
	public BigDecimal getQtyCharge();

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

    /** Column name TotalCargoQty */
    public static final String COLUMNNAME_TotalCargoQty = "TotalCargoQty";

	/** Set Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty);

	/** Get Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty();

    /** Column name UnitPrice */
    public static final String COLUMNNAME_UnitPrice = "UnitPrice";

	/** Set Unit Price	  */
	public void setUnitPrice (BigDecimal UnitPrice);

	/** Get Unit Price	  */
	public BigDecimal getUnitPrice();

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
