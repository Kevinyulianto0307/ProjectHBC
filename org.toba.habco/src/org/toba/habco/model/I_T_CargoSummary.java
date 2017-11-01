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

/** Generated Interface for T_CargoSummary
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_T_CargoSummary 
{

    /** TableName=T_CargoSummary */
    public static final String Table_Name = "T_CargoSummary";

    /** AD_Table_ID=1100293 */
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

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Process Instance.
	  * Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Process Instance.
	  * Instance of the process
	  */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name bargename */
    public static final String COLUMNNAME_bargename = "bargename";

	/** Set bargename	  */
	public void setbargename (String bargename);

	/** Get bargename	  */
	public String getbargename();

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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name completed_loading */
    public static final String COLUMNNAME_completed_loading = "completed_loading";

	/** Set completed_loading	  */
	public void setcompleted_loading (Timestamp completed_loading);

	/** Get completed_loading	  */
	public Timestamp getcompleted_loading();

    /** Column name completed_unloading */
    public static final String COLUMNNAME_completed_unloading = "completed_unloading";

	/** Set completed_unloading	  */
	public void setcompleted_unloading (Timestamp completed_unloading);

	/** Get completed_unloading	  */
	public Timestamp getcompleted_unloading();

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

    /** Column name customerlocation */
    public static final String COLUMNNAME_customerlocation = "customerlocation";

	/** Set customerlocation	  */
	public void setcustomerlocation (String customerlocation);

	/** Get customerlocation	  */
	public String getcustomerlocation();

    /** Column name customername */
    public static final String COLUMNNAME_customername = "customername";

	/** Set customername	  */
	public void setcustomername (String customername);

	/** Get customername	  */
	public String getcustomername();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name dischargingportname */
    public static final String COLUMNNAME_dischargingportname = "dischargingportname";

	/** Set dischargingportname	  */
	public void setdischargingportname (String dischargingportname);

	/** Get dischargingportname	  */
	public String getdischargingportname();

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

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

    /** Column name LoadingDraftSurvey */
    public static final String COLUMNNAME_LoadingDraftSurvey = "LoadingDraftSurvey";

	/** Set LoadingDraftSurvey	  */
	public void setLoadingDraftSurvey (BigDecimal LoadingDraftSurvey);

	/** Get LoadingDraftSurvey	  */
	public BigDecimal getLoadingDraftSurvey();

    /** Column name loadingportname */
    public static final String COLUMNNAME_loadingportname = "loadingportname";

	/** Set loadingportname	  */
	public void setloadingportname (String loadingportname);

	/** Get loadingportname	  */
	public String getloadingportname();

    /** Column name T_CargoSummary_ID */
    public static final String COLUMNNAME_T_CargoSummary_ID = "T_CargoSummary_ID";

	/** Set T_CargoSummary	  */
	public void setT_CargoSummary_ID (int T_CargoSummary_ID);

	/** Get T_CargoSummary	  */
	public int getT_CargoSummary_ID();

    /** Column name T_CargoSummary_UU */
    public static final String COLUMNNAME_T_CargoSummary_UU = "T_CargoSummary_UU";

	/** Set T_CargoSummary_UU	  */
	public void setT_CargoSummary_UU (String T_CargoSummary_UU);

	/** Get T_CargoSummary_UU	  */
	public String getT_CargoSummary_UU();

    /** Column name tonnage */
    public static final String COLUMNNAME_tonnage = "tonnage";

	/** Set tonnage	  */
	public void settonnage (BigDecimal tonnage);

	/** Get tonnage	  */
	public BigDecimal gettonnage();

    /** Column name TotalCargoQty */
    public static final String COLUMNNAME_TotalCargoQty = "TotalCargoQty";

	/** Set Total Cargo Qty	  */
	public void setTotalCargoQty (BigDecimal TotalCargoQty);

	/** Get Total Cargo Qty	  */
	public BigDecimal getTotalCargoQty();

    /** Column name tugboatname */
    public static final String COLUMNNAME_tugboatname = "tugboatname";

	/** Set tugboatname	  */
	public void settugboatname (String tugboatname);

	/** Get tugboatname	  */
	public String gettugboatname();

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

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set User Name	  */
	public void setUserName (String UserName);

	/** Get User Name	  */
	public String getUserName();

    /** Column name userposition */
    public static final String COLUMNNAME_userposition = "userposition";

	/** Set userposition	  */
	public void setuserposition (String userposition);

	/** Get userposition	  */
	public String getuserposition();
}
