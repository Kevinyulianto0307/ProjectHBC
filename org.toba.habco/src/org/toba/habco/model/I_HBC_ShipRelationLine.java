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

/** Generated Interface for HBC_ShipRelationLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_ShipRelationLine 
{

    /** TableName=HBC_ShipRelationLine */
    public static final String Table_Name = "HBC_ShipRelationLine";

    /** AD_Table_ID=1100057 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActiveFrom */
    public static final String COLUMNNAME_ActiveFrom = "ActiveFrom";

	/** Set Active From	  */
	public void setActiveFrom (Timestamp ActiveFrom);

	/** Get Active From	  */
	public Timestamp getActiveFrom();

    /** Column name ActiveTo */
    public static final String COLUMNNAME_ActiveTo = "ActiveTo";

	/** Set Active To	  */
	public void setActiveTo (Timestamp ActiveTo);

	/** Get Active To	  */
	public Timestamp getActiveTo();

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

    /** Column name HBC_Barge_ID */
    public static final String COLUMNNAME_HBC_Barge_ID = "HBC_Barge_ID";

	/** Set Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID);

	/** Get Barge	  */
	public int getHBC_Barge_ID();

	public I_HBC_Barge getHBC_Barge() throws RuntimeException;

    /** Column name HBC_ShipRelation_ID */
    public static final String COLUMNNAME_HBC_ShipRelation_ID = "HBC_ShipRelation_ID";

	/** Set Ship Relation	  */
	public void setHBC_ShipRelation_ID (int HBC_ShipRelation_ID);

	/** Get Ship Relation	  */
	public int getHBC_ShipRelation_ID();

	public I_HBC_ShipRelation getHBC_ShipRelation() throws RuntimeException;

    /** Column name HBC_ShipRelationLine_ID */
    public static final String COLUMNNAME_HBC_ShipRelationLine_ID = "HBC_ShipRelationLine_ID";

	/** Set Ship Relation Line	  */
	public void setHBC_ShipRelationLine_ID (int HBC_ShipRelationLine_ID);

	/** Get Ship Relation Line	  */
	public int getHBC_ShipRelationLine_ID();

    /** Column name HBC_ShipRelationLine_UU */
    public static final String COLUMNNAME_HBC_ShipRelationLine_UU = "HBC_ShipRelationLine_UU";

	/** Set HBC_ShipRelationLine_UU	  */
	public void setHBC_ShipRelationLine_UU (String HBC_ShipRelationLine_UU);

	/** Get HBC_ShipRelationLine_UU	  */
	public String getHBC_ShipRelationLine_UU();

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
