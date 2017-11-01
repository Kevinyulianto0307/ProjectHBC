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

/** Generated Interface for HBC_PortPosition
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_PortPosition 
{

    /** TableName=HBC_PortPosition */
    public static final String Table_Name = "HBC_PortPosition";

    /** AD_Table_ID=1000170 */
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

    /** Column name District */
    public static final String COLUMNNAME_District = "District";

	/** Set District	  */
	public void setDistrict (String District);

	/** Get District	  */
	public String getDistrict();

    /** Column name HBC_Coordinates */
    public static final String COLUMNNAME_HBC_Coordinates = "HBC_Coordinates";

	/** Set KM (From Banjarmasin)	  */
	public void setHBC_Coordinates (BigDecimal HBC_Coordinates);

	/** Get KM (From Banjarmasin)	  */
	public BigDecimal getHBC_Coordinates();

    /** Column name HBC_Jetty_ID */
    public static final String COLUMNNAME_HBC_Jetty_ID = "HBC_Jetty_ID";

	/** Set Jetty	  */
	public void setHBC_Jetty_ID (int HBC_Jetty_ID);

	/** Get Jetty	  */
	public int getHBC_Jetty_ID();

	public I_HBC_Jetty getHBC_Jetty() throws RuntimeException;

    /** Column name HBC_PortPosition_ID */
    public static final String COLUMNNAME_HBC_PortPosition_ID = "HBC_PortPosition_ID";

	/** Set Port / Position	  */
	public void setHBC_PortPosition_ID (int HBC_PortPosition_ID);

	/** Get Port / Position	  */
	public int getHBC_PortPosition_ID();

    /** Column name HBC_PortPosition_UU */
    public static final String COLUMNNAME_HBC_PortPosition_UU = "HBC_PortPosition_UU";

	/** Set HBC_PortPosition_UU	  */
	public void setHBC_PortPosition_UU (String HBC_PortPosition_UU);

	/** Get HBC_PortPosition_UU	  */
	public String getHBC_PortPosition_UU();

    /** Column name HBC_PositionType */
    public static final String COLUMNNAME_HBC_PositionType = "HBC_PositionType";

	/** Set Position Type	  */
	public void setHBC_PositionType (String HBC_PositionType);

	/** Get Position Type	  */
	public String getHBC_PositionType();

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

    /** Column name Latitude */
    public static final String COLUMNNAME_Latitude = "Latitude";

	/** Set Latitude	  */
	public void setLatitude (String Latitude);

	/** Get Latitude	  */
	public String getLatitude();

    /** Column name Longitude */
    public static final String COLUMNNAME_Longitude = "Longitude";

	/** Set Longitude	  */
	public void setLongitude (String Longitude);

	/** Get Longitude	  */
	public String getLongitude();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Province */
    public static final String COLUMNNAME_Province = "Province";

	/** Set Province	  */
	public void setProvince (String Province);

	/** Get Province	  */
	public String getProvince();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
