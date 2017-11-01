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

/** Generated Interface for HBC_LegalizedLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_LegalizedLine 
{

    /** TableName=HBC_LegalizedLine */
    public static final String Table_Name = "HBC_LegalizedLine";

    /** AD_Table_ID=1100129 */
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

    /** Column name Agent_BPartner_ID */
    public static final String COLUMNNAME_Agent_BPartner_ID = "Agent_BPartner_ID";

	/** Set Agent	  */
	public void setAgent_BPartner_ID (int Agent_BPartner_ID);

	/** Get Agent	  */
	public int getAgent_BPartner_ID();

	public org.compiere.model.I_C_BPartner getAgent_BPartner() throws RuntimeException;

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

    /** Column name EndorseDate */
    public static final String COLUMNNAME_EndorseDate = "EndorseDate";

	/** Set Endorse Date	  */
	public void setEndorseDate (Timestamp EndorseDate);

	/** Get Endorse Date	  */
	public Timestamp getEndorseDate();

    /** Column name From_PortPosition_ID */
    public static final String COLUMNNAME_From_PortPosition_ID = "From_PortPosition_ID";

	/** Set Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID);

	/** Get Port/Position From	  */
	public int getFrom_PortPosition_ID();

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException;

    /** Column name HBC_LegalizedLine_ID */
    public static final String COLUMNNAME_HBC_LegalizedLine_ID = "HBC_LegalizedLine_ID";

	/** Set HBC_LegalizedLine	  */
	public void setHBC_LegalizedLine_ID (int HBC_LegalizedLine_ID);

	/** Get HBC_LegalizedLine	  */
	public int getHBC_LegalizedLine_ID();

    /** Column name HBC_LegalizedLine_UU */
    public static final String COLUMNNAME_HBC_LegalizedLine_UU = "HBC_LegalizedLine_UU";

	/** Set HBC_LegalizedLine_UU	  */
	public void setHBC_LegalizedLine_UU (String HBC_LegalizedLine_UU);

	/** Get HBC_LegalizedLine_UU	  */
	public String getHBC_LegalizedLine_UU();

    /** Column name HBC_ShipDocumentLine_ID */
    public static final String COLUMNNAME_HBC_ShipDocumentLine_ID = "HBC_ShipDocumentLine_ID";

	/** Set Ship Document Line	  */
	public void setHBC_ShipDocumentLine_ID (int HBC_ShipDocumentLine_ID);

	/** Get Ship Document Line	  */
	public int getHBC_ShipDocumentLine_ID();

	public I_HBC_ShipDocumentLine getHBC_ShipDocumentLine() throws RuntimeException;

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

	/** Set Is District	  */
	public void setIsDistrict (boolean IsDistrict);

	/** Get Is District	  */
	public boolean isDistrict();

    /** Column name IsLegalized */
    public static final String COLUMNNAME_IsLegalized = "IsLegalized";

	/** Set Legalized	  */
	public void setIsLegalized (boolean IsLegalized);

	/** Get Legalized	  */
	public boolean isLegalized();

    /** Column name PicName */
    public static final String COLUMNNAME_PicName = "PicName";

	/** Set PIC	  */
	public void setPicName (String PicName);

	/** Get PIC	  */
	public String getPicName();

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
