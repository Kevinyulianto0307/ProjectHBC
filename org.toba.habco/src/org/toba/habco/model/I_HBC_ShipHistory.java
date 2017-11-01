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

/** Generated Interface for HBC_ShipHistory
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_ShipHistory 
{

    /** TableName=HBC_ShipHistory */
    public static final String Table_Name = "HBC_ShipHistory";

    /** AD_Table_ID=1100061 */
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

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date.
	  * Date when business is not conducted
	  */
	public Timestamp getDate1();

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

    /** Column name HBC_ShipHistory_ID */
    public static final String COLUMNNAME_HBC_ShipHistory_ID = "HBC_ShipHistory_ID";

	/** Set Ship History	  */
	public void setHBC_ShipHistory_ID (int HBC_ShipHistory_ID);

	/** Get Ship History	  */
	public int getHBC_ShipHistory_ID();

    /** Column name HBC_ShipHistory_UU */
    public static final String COLUMNNAME_HBC_ShipHistory_UU = "HBC_ShipHistory_UU";

	/** Set HBC_ShipHistory_UU	  */
	public void setHBC_ShipHistory_UU (String HBC_ShipHistory_UU);

	/** Get HBC_ShipHistory_UU	  */
	public String getHBC_ShipHistory_UU();

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
