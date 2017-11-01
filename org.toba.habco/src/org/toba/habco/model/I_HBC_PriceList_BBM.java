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

/** Generated Interface for HBC_PriceList_BBM
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
@SuppressWarnings("all")
public interface I_HBC_PriceList_BBM 
{

    /** TableName=HBC_PriceList_BBM */
    public static final String Table_Name = "HBC_PriceList_BBM";

    /** AD_Table_ID=1100101 */
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

    /** Column name C_TaxRatePBBKB_ID */
    public static final String COLUMNNAME_C_TaxRatePBBKB_ID = "C_TaxRatePBBKB_ID";

	/** Set Tax Rate PBBKB.
	  * Tax PBBKB identifier
	  */
	public void setC_TaxRatePBBKB_ID (int C_TaxRatePBBKB_ID);

	/** Get Tax Rate PBBKB.
	  * Tax PBBKB identifier
	  */
	public int getC_TaxRatePBBKB_ID();

	public org.compiere.model.I_C_Tax getC_TaxRatePBBKB() throws RuntimeException;

    /** Column name C_TaxRatePPH_ID */
    public static final String COLUMNNAME_C_TaxRatePPH_ID = "C_TaxRatePPH_ID";

	/** Set Tax Rate PPH.
	  * Tax PPH identifier
	  */
	public void setC_TaxRatePPH_ID (int C_TaxRatePPH_ID);

	/** Get Tax Rate PPH.
	  * Tax PPH identifier
	  */
	public int getC_TaxRatePPH_ID();

	public org.compiere.model.I_C_Tax getC_TaxRatePPH() throws RuntimeException;

    /** Column name C_TaxRatePPN_ID */
    public static final String COLUMNNAME_C_TaxRatePPN_ID = "C_TaxRatePPN_ID";

	/** Set Tax Rate PPN.
	  * Tax PPN identifier
	  */
	public void setC_TaxRatePPN_ID (int C_TaxRatePPN_ID);

	/** Get Tax Rate PPN.
	  * Tax PPN identifier
	  */
	public int getC_TaxRatePPN_ID();

	public org.compiere.model.I_C_Tax getC_TaxRatePPN() throws RuntimeException;

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

    /** Column name HBC_PriceList_BBM_ID */
    public static final String COLUMNNAME_HBC_PriceList_BBM_ID = "HBC_PriceList_BBM_ID";

	/** Set Price List BBM	  */
	public void setHBC_PriceList_BBM_ID (int HBC_PriceList_BBM_ID);

	/** Get Price List BBM	  */
	public int getHBC_PriceList_BBM_ID();

    /** Column name HBC_PriceList_BBM_UU */
    public static final String COLUMNNAME_HBC_PriceList_BBM_UU = "HBC_PriceList_BBM_UU";

	/** Set HBC_PriceList_BBM_UU	  */
	public void setHBC_PriceList_BBM_UU (String HBC_PriceList_BBM_UU);

	/** Get HBC_PriceList_BBM_UU	  */
	public String getHBC_PriceList_BBM_UU();

    /** Column name IndexBBM */
    public static final String COLUMNNAME_IndexBBM = "IndexBBM";
    
    /** Column name SecondIndexBBM */
    public static final String COLUMNNAME_SecondIndexBBM = "SecondCoalIndex";

	/** Set Index BBM.
	  * Index for BBM
	  */
	public void setIndexBBM (BigDecimal IndexBBM);

	/** Get Index BBM.
	  * Index for BBM
	  */
	public BigDecimal getIndexBBM();

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

    /** Column name PBBKBTaxAmt */
    public static final String COLUMNNAME_PBBKBTaxAmt = "PBBKBTaxAmt";

	/** Set PBBKB Tax Amount.
	  * Tax Amount for PBBKB
	  */
	public void setPBBKBTaxAmt (BigDecimal PBBKBTaxAmt);

	/** Get PBBKB Tax Amount.
	  * Tax Amount for PBBKB
	  */
	public BigDecimal getPBBKBTaxAmt();

    /** Column name PPHTaxAmt */
    public static final String COLUMNNAME_PPHTaxAmt = "PPHTaxAmt";

	/** Set PPH Tax Amount.
	  * Tax Amount for PPH
	  */
	public void setPPHTaxAmt (BigDecimal PPHTaxAmt);

	/** Get PPH Tax Amount.
	  * Tax Amount for PPH
	  */
	public BigDecimal getPPHTaxAmt();

    /** Column name PPNTaxAmt */
    public static final String COLUMNNAME_PPNTaxAmt = "PPNTaxAmt";

	/** Set PPN Tax Amount.
	  * Tax Amount for PPN
	  */
	public void setPPNTaxAmt (BigDecimal PPNTaxAmt);

	/** Get PPN Tax Amount.
	  * Tax Amount for PPN
	  */
	public BigDecimal getPPNTaxAmt();

    /** Column name PreviousIndexBBM */
    public static final String COLUMNNAME_PreviousIndexBBM = "PreviousIndexBBM";

	/** Set Previous Index BBM.
	  * Previous Index for BBM
	  */
	public void setPreviousIndexBBM (BigDecimal PreviousIndexBBM);

	/** Get Previous Index BBM.
	  * Previous Index for BBM
	  */
	public BigDecimal getPreviousIndexBBM();

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered);

	/** Get Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered();

    /** Column name SubTotal */
    public static final String COLUMNNAME_SubTotal = "SubTotal";

	/** Set SubTotal.
	  * SubTotal amount of document header
	  */
	public void setSubTotal (BigDecimal SubTotal);

	/** Get SubTotal.
	  * SubTotal amount of document header
	  */
	public BigDecimal getSubTotal();

    /** Column name TaxBaseAmt */
    public static final String COLUMNNAME_TaxBaseAmt = "TaxBaseAmt";

	/** Set Tax base Amount.
	  * Base for calculating the tax amount
	  */
	public void setTaxBaseAmt (BigDecimal TaxBaseAmt);

	/** Get Tax base Amount.
	  * Base for calculating the tax amount
	  */
	public BigDecimal getTaxBaseAmt();

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
