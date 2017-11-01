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

/** Generated Interface for HBC_ShipDocumentLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_ShipDocumentLine 
{

    /** TableName=HBC_ShipDocumentLine */
    public static final String Table_Name = "HBC_ShipDocumentLine";

    /** AD_Table_ID=1100012 */
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

	/** Set Issuing Region (Province).
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Issuing Region (Province).
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

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

    /** Column name EffectiveDateFrom */
    public static final String COLUMNNAME_EffectiveDateFrom = "EffectiveDateFrom";

	/** Set Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom);

	/** Get Effective Date From	  */
	public Timestamp getEffectiveDateFrom();

    /** Column name EffectiveDateTo */
    public static final String COLUMNNAME_EffectiveDateTo = "EffectiveDateTo";

	/** Set Effective Date To	  */
	public void setEffectiveDateTo (Timestamp EffectiveDateTo);

	/** Get Effective Date To	  */
	public Timestamp getEffectiveDateTo();

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

    /** Column name HBC_ShipDoctype */
    public static final String COLUMNNAME_HBC_ShipDoctype = "HBC_ShipDoctype";

	/** Set Ship Document Type	  */
	public void setHBC_ShipDoctype (int HBC_ShipDoctype);

	/** Get Ship Document Type	  */
	public int getHBC_ShipDoctype();

	public I_HBC_ShipDocumentType getHBC_ShipDoct() throws RuntimeException;

    /** Column name HBC_ShipDocument_ID */
    public static final String COLUMNNAME_HBC_ShipDocument_ID = "HBC_ShipDocument_ID";

	/** Set Ship Document	  */
	public void setHBC_ShipDocument_ID (int HBC_ShipDocument_ID);

	/** Get Ship Document	  */
	public int getHBC_ShipDocument_ID();

	public I_HBC_ShipDocument getHBC_ShipDocument() throws RuntimeException;

    /** Column name HBC_ShipDocumentLine_ID */
    public static final String COLUMNNAME_HBC_ShipDocumentLine_ID = "HBC_ShipDocumentLine_ID";

	/** Set Ship Document Line	  */
	public void setHBC_ShipDocumentLine_ID (int HBC_ShipDocumentLine_ID);

	/** Get Ship Document Line	  */
	public int getHBC_ShipDocumentLine_ID();

    /** Column name HBC_ShipDocumentLine_UU */
    public static final String COLUMNNAME_HBC_ShipDocumentLine_UU = "HBC_ShipDocumentLine_UU";

	/** Set HBC_ShipDocumentLine_UU	  */
	public void setHBC_ShipDocumentLine_UU (String HBC_ShipDocumentLine_UU);

	/** Get HBC_ShipDocumentLine_UU	  */
	public String getHBC_ShipDocumentLine_UU();

    /** Column name HBC_ShipDocumentType_ID */
    public static final String COLUMNNAME_HBC_ShipDocumentType_ID = "HBC_ShipDocumentType_ID";

	/** Set Ship Document Type	  */
	public void setHBC_ShipDocumentType_ID (int HBC_ShipDocumentType_ID);

	/** Get Ship Document Type	  */
	public int getHBC_ShipDocumentType_ID();

	public I_HBC_ShipDocumentType getHBC_ShipDocumentType() throws RuntimeException;

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

    /** Column name IsDistrict */
    public static final String COLUMNNAME_IsDistrict = "IsDistrict";

	/** Set Issuing Region (District)	  */
	public void setIsDistrict (boolean IsDistrict);

	/** Get Issuing Region (District)	  */
	public boolean isDistrict();

    /** Column name IsLegalized1 */
    public static final String COLUMNNAME_IsLegalized1 = "IsLegalized1";

	/** Set Is Legalized 1	  */
	public void setIsLegalized1 (boolean IsLegalized1);

	/** Get Is Legalized 1	  */
	public boolean isLegalized1();

    /** Column name IsLegalized10 */
    public static final String COLUMNNAME_IsLegalized10 = "IsLegalized10";

	/** Set Is Legalized 10	  */
	public void setIsLegalized10 (boolean IsLegalized10);

	/** Get Is Legalized 10	  */
	public boolean isLegalized10();

    /** Column name IsLegalized2 */
    public static final String COLUMNNAME_IsLegalized2 = "IsLegalized2";

	/** Set Is Legalized 2	  */
	public void setIsLegalized2 (boolean IsLegalized2);

	/** Get Is Legalized 2	  */
	public boolean isLegalized2();

    /** Column name IsLegalized3 */
    public static final String COLUMNNAME_IsLegalized3 = "IsLegalized3";

	/** Set Is Legalized 3	  */
	public void setIsLegalized3 (boolean IsLegalized3);

	/** Get Is Legalized 3	  */
	public boolean isLegalized3();

    /** Column name IsLegalized4 */
    public static final String COLUMNNAME_IsLegalized4 = "IsLegalized4";

	/** Set Is Legalized 4	  */
	public void setIsLegalized4 (boolean IsLegalized4);

	/** Get Is Legalized 4	  */
	public boolean isLegalized4();

    /** Column name IsLegalized5 */
    public static final String COLUMNNAME_IsLegalized5 = "IsLegalized5";

	/** Set Is Legalized 5	  */
	public void setIsLegalized5 (boolean IsLegalized5);

	/** Get Is Legalized 5	  */
	public boolean isLegalized5();

    /** Column name IsLegalized6 */
    public static final String COLUMNNAME_IsLegalized6 = "IsLegalized6";

	/** Set Is Legalized 6	  */
	public void setIsLegalized6 (boolean IsLegalized6);

	/** Get Is Legalized 6	  */
	public boolean isLegalized6();

    /** Column name IsLegalized7 */
    public static final String COLUMNNAME_IsLegalized7 = "IsLegalized7";

	/** Set Is Legalized 7	  */
	public void setIsLegalized7 (boolean IsLegalized7);

	/** Get Is Legalized 7	  */
	public boolean isLegalized7();

    /** Column name IsLegalized8 */
    public static final String COLUMNNAME_IsLegalized8 = "IsLegalized8";

	/** Set Is Legalized 8	  */
	public void setIsLegalized8 (boolean IsLegalized8);

	/** Get Is Legalized 8	  */
	public boolean isLegalized8();

    /** Column name IsLegalized9 */
    public static final String COLUMNNAME_IsLegalized9 = "IsLegalized9";

	/** Set Is Legalized 9	  */
	public void setIsLegalized9 (boolean IsLegalized9);

	/** Get Is Legalized 9	  */
	public boolean isLegalized9();

    /** Column name IsProvince */
    public static final String COLUMNNAME_IsProvince = "IsProvince";

	/** Set Issuing Region (Province)	  */
	public void setIsProvince (boolean IsProvince);

	/** Get Issuing Region (Province)	  */
	public boolean isProvince();

    /** Column name IssuedBy */
    public static final String COLUMNNAME_IssuedBy = "IssuedBy";

	/** Set Issued By	  */
	public void setIssuedBy (String IssuedBy);

	/** Get Issued By	  */
	public String getIssuedBy();

    /** Column name IssuedDate */
    public static final String COLUMNNAME_IssuedDate = "IssuedDate";

	/** Set Issued Date	  */
	public void setIssuedDate (Timestamp IssuedDate);

	/** Get Issued Date	  */
	public Timestamp getIssuedDate();

    /** Column name IssuedInstitution_ID */
    public static final String COLUMNNAME_IssuedInstitution_ID = "IssuedInstitution_ID";

	/** Set Issued by	  */
	public void setIssuedInstitution_ID (int IssuedInstitution_ID);

	/** Get Issued by	  */
	public int getIssuedInstitution_ID();

	//public I_IssuedInstitution getIssuedInstitution() throws RuntimeException;

    /** Column name LastEndorse */
    public static final String COLUMNNAME_LastEndorse = "LastEndorse";

	/** Set Last Endorse Date	  */
	public void setLastEndorse (Timestamp LastEndorse);

	/** Get Last Endorse Date	  */
	public Timestamp getLastEndorse();

    /** Column name Legalized12 */
    public static final String COLUMNNAME_Legalized12 = "Legalized12";

	/** Set Legalized Date 12	  */
	public void setLegalized12 (Timestamp Legalized12);

	/** Get Legalized Date 12	  */
	public Timestamp getLegalized12();

    /** Column name Legalized13 */
    public static final String COLUMNNAME_Legalized13 = "Legalized13";

	/** Set Legalized Date 13	  */
	public void setLegalized13 (Timestamp Legalized13);

	/** Get Legalized Date 13	  */
	public Timestamp getLegalized13();

    /** Column name Legalized14 */
    public static final String COLUMNNAME_Legalized14 = "Legalized14";

	/** Set Legalized Date 14	  */
	public void setLegalized14 (Timestamp Legalized14);

	/** Get Legalized Date 14	  */
	public Timestamp getLegalized14();

    /** Column name Legalized15 */
    public static final String COLUMNNAME_Legalized15 = "Legalized15";

	/** Set Legalized Date 15	  */
	public void setLegalized15 (Timestamp Legalized15);

	/** Get Legalized Date 15	  */
	public Timestamp getLegalized15();

    /** Column name Legalized16 */
    public static final String COLUMNNAME_Legalized16 = "Legalized16";

	/** Set Legalized Date 16	  */
	public void setLegalized16 (Timestamp Legalized16);

	/** Get Legalized Date 16	  */
	public Timestamp getLegalized16();

    /** Column name Legalized17 */
    public static final String COLUMNNAME_Legalized17 = "Legalized17";

	/** Set Legalized Date 17	  */
	public void setLegalized17 (Timestamp Legalized17);

	/** Get Legalized Date 17	  */
	public Timestamp getLegalized17();

    /** Column name Legalized18 */
    public static final String COLUMNNAME_Legalized18 = "Legalized18";

	/** Set Legalized Date 18	  */
	public void setLegalized18 (Timestamp Legalized18);

	/** Get Legalized Date 18	  */
	public Timestamp getLegalized18();

    /** Column name Legalized19 */
    public static final String COLUMNNAME_Legalized19 = "Legalized19";

	/** Set Legalized Date 19	  */
	public void setLegalized19 (Timestamp Legalized19);

	/** Get Legalized Date 19	  */
	public Timestamp getLegalized19();

    /** Column name Legalized20 */
    public static final String COLUMNNAME_Legalized20 = "Legalized20";

	/** Set Legalized Date 20	  */
	public void setLegalized20 (Timestamp Legalized20);

	/** Get Legalized Date 20	  */
	public Timestamp getLegalized20();

    /** Column name LegalizedAnnualy */
    public static final String COLUMNNAME_LegalizedAnnualy = "LegalizedAnnualy";

	/** Set Legalized Annualy	  */
	public void setLegalizedAnnualy (boolean LegalizedAnnualy);

	/** Get Legalized Annualy	  */
	public boolean isLegalizedAnnualy();

    /** Column name LegalizedBy */
    public static final String COLUMNNAME_LegalizedBy = "LegalizedBy";

	/** Set Legalized By	  */
	public void setLegalizedBy (String LegalizedBy);

	/** Get Legalized By	  */
	public String getLegalizedBy();

    /** Column name LegalizedDate1 */
    public static final String COLUMNNAME_LegalizedDate1 = "LegalizedDate1";

	/** Set Legalized Date 1	  */
	public void setLegalizedDate1 (Timestamp LegalizedDate1);

	/** Get Legalized Date 1	  */
	public Timestamp getLegalizedDate1();

    /** Column name LegalizedDate10 */
    public static final String COLUMNNAME_LegalizedDate10 = "LegalizedDate10";

	/** Set Legalized Date 10	  */
	public void setLegalizedDate10 (Timestamp LegalizedDate10);

	/** Get Legalized Date 10	  */
	public Timestamp getLegalizedDate10();

    /** Column name LegalizedDate11 */
    public static final String COLUMNNAME_LegalizedDate11 = "LegalizedDate11";

	/** Set Legalized Date 11	  */
	public void setLegalizedDate11 (Timestamp LegalizedDate11);

	/** Get Legalized Date 11	  */
	public Timestamp getLegalizedDate11();

    /** Column name LegalizedDate2 */
    public static final String COLUMNNAME_LegalizedDate2 = "LegalizedDate2";

	/** Set Legalized Date 2	  */
	public void setLegalizedDate2 (Timestamp LegalizedDate2);

	/** Get Legalized Date 2	  */
	public Timestamp getLegalizedDate2();

    /** Column name LegalizedDate3 */
    public static final String COLUMNNAME_LegalizedDate3 = "LegalizedDate3";

	/** Set Legalized Date 3	  */
	public void setLegalizedDate3 (Timestamp LegalizedDate3);

	/** Get Legalized Date 3	  */
	public Timestamp getLegalizedDate3();

    /** Column name LegalizedDate4 */
    public static final String COLUMNNAME_LegalizedDate4 = "LegalizedDate4";

	/** Set Legalized Date 4	  */
	public void setLegalizedDate4 (Timestamp LegalizedDate4);

	/** Get Legalized Date 4	  */
	public Timestamp getLegalizedDate4();

    /** Column name LegalizedDate5 */
    public static final String COLUMNNAME_LegalizedDate5 = "LegalizedDate5";

	/** Set Legalized Date 5	  */
	public void setLegalizedDate5 (Timestamp LegalizedDate5);

	/** Get Legalized Date 5	  */
	public Timestamp getLegalizedDate5();

    /** Column name LegalizedDate6 */
    public static final String COLUMNNAME_LegalizedDate6 = "LegalizedDate6";

	/** Set Legalized Date 6	  */
	public void setLegalizedDate6 (Timestamp LegalizedDate6);

	/** Get Legalized Date 6	  */
	public Timestamp getLegalizedDate6();

    /** Column name LegalizedDate7 */
    public static final String COLUMNNAME_LegalizedDate7 = "LegalizedDate7";

	/** Set Legalized Date 7	  */
	public void setLegalizedDate7 (Timestamp LegalizedDate7);

	/** Get Legalized Date 7	  */
	public Timestamp getLegalizedDate7();

    /** Column name LegalizedDate8 */
    public static final String COLUMNNAME_LegalizedDate8 = "LegalizedDate8";

	/** Set Legalized Date 8	  */
	public void setLegalizedDate8 (Timestamp LegalizedDate8);

	/** Get Legalized Date 8	  */
	public Timestamp getLegalizedDate8();

    /** Column name LegalizedDate9 */
    public static final String COLUMNNAME_LegalizedDate9 = "LegalizedDate9";

	/** Set Legalized Date 9	  */
	public void setLegalizedDate9 (Timestamp LegalizedDate9);

	/** Get Legalized Date 9	  */
	public Timestamp getLegalizedDate9();

    /** Column name ShipDocNumber */
    public static final String COLUMNNAME_ShipDocNumber = "ShipDocNumber";

	/** Set Physical Document Number	  */
	public void setShipDocNumber (String ShipDocNumber);

	/** Get Physical Document Number	  */
	public String getShipDocNumber();

    /** Column name ShipDocumentStatus */
    public static final String COLUMNNAME_ShipDocumentStatus = "ShipDocumentStatus";

	/** Set Ship Document Status	  */
	public void setShipDocumentStatus (String ShipDocumentStatus);

	/** Get Ship Document Status	  */
	public String getShipDocumentStatus();

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

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();
}
