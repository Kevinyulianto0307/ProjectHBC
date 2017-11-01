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

/** Generated Interface for HBC_CostActivity
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_CostActivity 
{

    /** TableName=HBC_CostActivity */
    public static final String Table_Name = "HBC_CostActivity";

    /** AD_Table_ID=1100082 */
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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name AllocationCode */
    public static final String COLUMNNAME_AllocationCode = "AllocationCode";

	/** Set Allocation Number	  */
	public void setAllocationCode (int AllocationCode);

	/** Get Allocation Number	  */
	public int getAllocationCode();

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

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

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Reference Code.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Reference Code.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name Endorse_Activity_ID */
    public static final String COLUMNNAME_Endorse_Activity_ID = "Endorse_Activity_ID";

	/** Set Endorse Activity	  */
	public void setEndorse_Activity_ID (int Endorse_Activity_ID);

	/** Get Endorse Activity	  */
	public int getEndorse_Activity_ID();

	public I_HBC_LegalizedLine getEndorse_Activity() throws RuntimeException;

    /** Column name FinishDate */
    public static final String COLUMNNAME_FinishDate = "FinishDate";

	/** Set Finish Date	  */
	public void setFinishDate (Timestamp FinishDate);

	/** Get Finish Date	  */
	public Timestamp getFinishDate();

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_CostActivity_ID */
    public static final String COLUMNNAME_HBC_CostActivity_ID = "HBC_CostActivity_ID";

	/** Set HBC_CostActivity	  */
	public void setHBC_CostActivity_ID (int HBC_CostActivity_ID);

	/** Get HBC_CostActivity	  */
	public int getHBC_CostActivity_ID();

    /** Column name HBC_CostActivity_UU */
    public static final String COLUMNNAME_HBC_CostActivity_UU = "HBC_CostActivity_UU";

	/** Set HBC_CostActivity_UU	  */
	public void setHBC_CostActivity_UU (String HBC_CostActivity_UU);

	/** Get HBC_CostActivity_UU	  */
	public String getHBC_CostActivity_UU();

    /** Column name HBC_CrewOnOff_ID */
    public static final String COLUMNNAME_HBC_CrewOnOff_ID = "HBC_CrewOnOff_ID";

	/** Set Crew On/Off	  */
	public void setHBC_CrewOnOff_ID (int HBC_CrewOnOff_ID);

	/** Get Crew On/Off	  */
	public int getHBC_CrewOnOff_ID();

	public I_HBC_CrewOnOff getHBC_CrewOnOff() throws RuntimeException;

    /** Column name HBC_LegalizedLine_ID */
    public static final String COLUMNNAME_HBC_LegalizedLine_ID = "HBC_LegalizedLine_ID";

	/** Set HBC_LegalizedLine	  */
	public void setHBC_LegalizedLine_ID (int HBC_LegalizedLine_ID);

	/** Get HBC_LegalizedLine	  */
	public int getHBC_LegalizedLine_ID();

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

    /** Column name HBC_ShipDocumentLine_ID */
    public static final String COLUMNNAME_HBC_ShipDocumentLine_ID = "HBC_ShipDocumentLine_ID";

	/** Set Ship Document Line	  */
	public void setHBC_ShipDocumentLine_ID (int HBC_ShipDocumentLine_ID);

	/** Get Ship Document Line	  */
	public int getHBC_ShipDocumentLine_ID();

	public I_HBC_ShipDocumentLine getHBC_ShipDocumentLine() throws RuntimeException;

    /** Column name HBC_Tugboat_ID */
    public static final String COLUMNNAME_HBC_Tugboat_ID = "HBC_Tugboat_ID";

	/** Set TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID);

	/** Get TugBoat	  */
	public int getHBC_Tugboat_ID();

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException;

    /** Column name HC_Employee_ID */
    public static final String COLUMNNAME_HC_Employee_ID = "HC_Employee_ID";

	/** Set Employee Data	  */
	public void setHC_Employee_ID (int HC_Employee_ID);

	/** Get Employee Data	  */
	public int getHC_Employee_ID();

	public I_HC_Employee getHC_Employee() throws RuntimeException;

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

    /** Column name IsAgentInvoices */
    public static final String COLUMNNAME_IsAgentInvoices = "IsAgentInvoices";

	/** Set Agent Invoice	  */
	public void setIsAgentInvoices (boolean IsAgentInvoices);

	/** Get Agent Invoice	  */
	public boolean isAgentInvoices();

    /** Column name IsAllocates */
    public static final String COLUMNNAME_IsAllocates = "IsAllocates";

	/** Set Allocate	  */
	public void setIsAllocates (boolean IsAllocates);

	/** Get Allocate	  */
	public boolean isAllocates();

    /** Column name IsChargeEmployee */
    public static final String COLUMNNAME_IsChargeEmployee = "IsChargeEmployee";

	/** Set Charge To Employee	  */
	public void setIsChargeEmployee (boolean IsChargeEmployee);

	/** Get Charge To Employee	  */
	public boolean isChargeEmployee();

    /** Column name IsInvoicedAR */
    public static final String COLUMNNAME_IsInvoicedAR = "IsInvoicedAR";

	/** Set Invoice AR	  */
	public void setIsInvoicedAR (boolean IsInvoicedAR);

	/** Get Invoice AR	  */
	public boolean isInvoicedAR();

    /** Column name Legalized_activity */
    public static final String COLUMNNAME_Legalized_activity = "Legalized_activity";

	/** Set Legalized Activity	  */
	public void setLegalized_activity (int Legalized_activity);

	/** Get Legalized Activity	  */
	public int getLegalized_activity();

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

    /** Column name LoadingAgent_BPartner_ID */
    public static final String COLUMNNAME_LoadingAgent_BPartner_ID = "LoadingAgent_BPartner_ID";

	/** Set Loading Agent	  */
	public void setLoadingAgent_BPartner_ID (int LoadingAgent_BPartner_ID);

	/** Get Loading Agent	  */
	public int getLoadingAgent_BPartner_ID();

	public org.compiere.model.I_C_BPartner getLoadingAgent_BPartner() throws RuntimeException;

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

    /** Column name M_Product_Version_ID */
    public static final String COLUMNNAME_M_Product_Version_ID = "M_Product_Version_ID";

	/** Set M_Product_Version	  */
	public void setM_Product_Version_ID (int M_Product_Version_ID);

	/** Get M_Product_Version	  */
	public int getM_Product_Version_ID();

	public I_M_Product_Version getM_Product_Version() throws RuntimeException;

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

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

    /** Column name TotalCargoQty */
    public static final String COLUMNNAME_TotalCargoQty = "TotalCargoQty";

	/** Set Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty);

	/** Get Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty();

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
