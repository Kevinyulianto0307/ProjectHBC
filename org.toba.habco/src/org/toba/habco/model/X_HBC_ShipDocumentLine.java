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
/** Generated Model - DO NOT CHANGE */
package org.toba.habco.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for HBC_ShipDocumentLine
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_HBC_ShipDocumentLine extends PO implements I_HBC_ShipDocumentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20161017L;

    /** Standard Constructor */
    public X_HBC_ShipDocumentLine (Properties ctx, int HBC_ShipDocumentLine_ID, String trxName)
    {
      super (ctx, HBC_ShipDocumentLine_ID, trxName);
      /** if (HBC_ShipDocumentLine_ID == 0)
        {
			setHBC_ShipDoctype (0);
			setHBC_ShipDocumentLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HBC_ShipDocumentLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_HBC_ShipDocumentLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocTypeTarget_ID(), get_TrxName());	}

	/** Set Target Document Type.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Target Document Type.
		@return Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Issuing Region (Province).
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Issuing Region (Province).
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Effective Date From.
		@param EffectiveDateFrom Effective Date From	  */
	public void setEffectiveDateFrom (Timestamp EffectiveDateFrom)
	{
		set_Value (COLUMNNAME_EffectiveDateFrom, EffectiveDateFrom);
	}

	/** Get Effective Date From.
		@return Effective Date From	  */
	public Timestamp getEffectiveDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EffectiveDateFrom);
	}

	/** Set Effective Date To.
		@param EffectiveDateTo Effective Date To	  */
	public void setEffectiveDateTo (Timestamp EffectiveDateTo)
	{
		set_Value (COLUMNNAME_EffectiveDateTo, EffectiveDateTo);
	}

	/** Get Effective Date To.
		@return Effective Date To	  */
	public Timestamp getEffectiveDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EffectiveDateTo);
	}

	public I_HBC_PortPosition getFrom_PortPosition() throws RuntimeException
    {
		return (I_HBC_PortPosition)MTable.get(getCtx(), I_HBC_PortPosition.Table_Name)
			.getPO(getFrom_PortPosition_ID(), get_TrxName());	}

	/** Set Port/Position From.
		@param From_PortPosition_ID Port/Position From	  */
	public void setFrom_PortPosition_ID (int From_PortPosition_ID)
	{
		if (From_PortPosition_ID < 1) 
			set_Value (COLUMNNAME_From_PortPosition_ID, null);
		else 
			set_Value (COLUMNNAME_From_PortPosition_ID, Integer.valueOf(From_PortPosition_ID));
	}

	/** Get Port/Position From.
		@return Port/Position From	  */
	public int getFrom_PortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_From_PortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Barge getHBC_Barge() throws RuntimeException
    {
		return (I_HBC_Barge)MTable.get(getCtx(), I_HBC_Barge.Table_Name)
			.getPO(getHBC_Barge_ID(), get_TrxName());	}

	/** Set Barge.
		@param HBC_Barge_ID Barge	  */
	public void setHBC_Barge_ID (int HBC_Barge_ID)
	{
		if (HBC_Barge_ID < 1) 
			set_Value (COLUMNNAME_HBC_Barge_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Barge_ID, Integer.valueOf(HBC_Barge_ID));
	}

	/** Get Barge.
		@return Barge	  */
	public int getHBC_Barge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Barge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_ShipDocumentType getHBC_ShipDoct() throws RuntimeException
    {
		return (I_HBC_ShipDocumentType)MTable.get(getCtx(), I_HBC_ShipDocumentType.Table_Name)
			.getPO(getHBC_ShipDoctype(), get_TrxName());	}

	/** Set Ship Document Type.
		@param HBC_ShipDoctype Ship Document Type	  */
	public void setHBC_ShipDoctype (int HBC_ShipDoctype)
	{
		set_ValueNoCheck (COLUMNNAME_HBC_ShipDoctype, Integer.valueOf(HBC_ShipDoctype));
	}

	/** Get Ship Document Type.
		@return Ship Document Type	  */
	public int getHBC_ShipDoctype () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDoctype);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_ShipDocument getHBC_ShipDocument() throws RuntimeException
    {
		return (I_HBC_ShipDocument)MTable.get(getCtx(), I_HBC_ShipDocument.Table_Name)
			.getPO(getHBC_ShipDocument_ID(), get_TrxName());	}

	/** Set Ship Document.
		@param HBC_ShipDocument_ID Ship Document	  */
	public void setHBC_ShipDocument_ID (int HBC_ShipDocument_ID)
	{
		if (HBC_ShipDocument_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocument_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocument_ID, Integer.valueOf(HBC_ShipDocument_ID));
	}

	/** Get Ship Document.
		@return Ship Document	  */
	public int getHBC_ShipDocument_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocument_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ship Document Line.
		@param HBC_ShipDocumentLine_ID Ship Document Line	  */
	public void setHBC_ShipDocumentLine_ID (int HBC_ShipDocumentLine_ID)
	{
		if (HBC_ShipDocumentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentLine_ID, Integer.valueOf(HBC_ShipDocumentLine_ID));
	}

	/** Get Ship Document Line.
		@return Ship Document Line	  */
	public int getHBC_ShipDocumentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocumentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHBC_ShipDocumentLine_ID()));
    }

	/** Set HBC_ShipDocumentLine_UU.
		@param HBC_ShipDocumentLine_UU HBC_ShipDocumentLine_UU	  */
	public void setHBC_ShipDocumentLine_UU (String HBC_ShipDocumentLine_UU)
	{
		set_Value (COLUMNNAME_HBC_ShipDocumentLine_UU, HBC_ShipDocumentLine_UU);
	}

	/** Get HBC_ShipDocumentLine_UU.
		@return HBC_ShipDocumentLine_UU	  */
	public String getHBC_ShipDocumentLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_HBC_ShipDocumentLine_UU);
	}

	public I_HBC_ShipDocumentType getHBC_ShipDocumentType() throws RuntimeException
    {
		return (I_HBC_ShipDocumentType)MTable.get(getCtx(), I_HBC_ShipDocumentType.Table_Name)
			.getPO(getHBC_ShipDocumentType_ID(), get_TrxName());	}

	/** Set Ship Document Type.
		@param HBC_ShipDocumentType_ID Ship Document Type	  */
	public void setHBC_ShipDocumentType_ID (int HBC_ShipDocumentType_ID)
	{
		if (HBC_ShipDocumentType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HBC_ShipDocumentType_ID, Integer.valueOf(HBC_ShipDocumentType_ID));
	}

	/** Get Ship Document Type.
		@return Ship Document Type	  */
	public int getHBC_ShipDocumentType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_ShipDocumentType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_HBC_Tugboat getHBC_Tugboat() throws RuntimeException
    {
		return (I_HBC_Tugboat)MTable.get(getCtx(), I_HBC_Tugboat.Table_Name)
			.getPO(getHBC_Tugboat_ID(), get_TrxName());	}

	/** Set TugBoat.
		@param HBC_Tugboat_ID TugBoat	  */
	public void setHBC_Tugboat_ID (int HBC_Tugboat_ID)
	{
		if (HBC_Tugboat_ID < 1) 
			set_Value (COLUMNNAME_HBC_Tugboat_ID, null);
		else 
			set_Value (COLUMNNAME_HBC_Tugboat_ID, Integer.valueOf(HBC_Tugboat_ID));
	}

	/** Get TugBoat.
		@return TugBoat	  */
	public int getHBC_Tugboat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HBC_Tugboat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Issuing Region (District).
		@param IsDistrict Issuing Region (District)	  */
	public void setIsDistrict (boolean IsDistrict)
	{
		set_Value (COLUMNNAME_IsDistrict, Boolean.valueOf(IsDistrict));
	}

	/** Get Issuing Region (District).
		@return Issuing Region (District)	  */
	public boolean isDistrict () 
	{
		Object oo = get_Value(COLUMNNAME_IsDistrict);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 1.
		@param IsLegalized1 Is Legalized 1	  */
	public void setIsLegalized1 (boolean IsLegalized1)
	{
		set_Value (COLUMNNAME_IsLegalized1, Boolean.valueOf(IsLegalized1));
	}

	/** Get Is Legalized 1.
		@return Is Legalized 1	  */
	public boolean isLegalized1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 10.
		@param IsLegalized10 Is Legalized 10	  */
	public void setIsLegalized10 (boolean IsLegalized10)
	{
		set_Value (COLUMNNAME_IsLegalized10, Boolean.valueOf(IsLegalized10));
	}

	/** Get Is Legalized 10.
		@return Is Legalized 10	  */
	public boolean isLegalized10 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized10);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 2.
		@param IsLegalized2 Is Legalized 2	  */
	public void setIsLegalized2 (boolean IsLegalized2)
	{
		set_Value (COLUMNNAME_IsLegalized2, Boolean.valueOf(IsLegalized2));
	}

	/** Get Is Legalized 2.
		@return Is Legalized 2	  */
	public boolean isLegalized2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 3.
		@param IsLegalized3 Is Legalized 3	  */
	public void setIsLegalized3 (boolean IsLegalized3)
	{
		set_Value (COLUMNNAME_IsLegalized3, Boolean.valueOf(IsLegalized3));
	}

	/** Get Is Legalized 3.
		@return Is Legalized 3	  */
	public boolean isLegalized3 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized3);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 4.
		@param IsLegalized4 Is Legalized 4	  */
	public void setIsLegalized4 (boolean IsLegalized4)
	{
		set_Value (COLUMNNAME_IsLegalized4, Boolean.valueOf(IsLegalized4));
	}

	/** Get Is Legalized 4.
		@return Is Legalized 4	  */
	public boolean isLegalized4 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized4);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 5.
		@param IsLegalized5 Is Legalized 5	  */
	public void setIsLegalized5 (boolean IsLegalized5)
	{
		set_Value (COLUMNNAME_IsLegalized5, Boolean.valueOf(IsLegalized5));
	}

	/** Get Is Legalized 5.
		@return Is Legalized 5	  */
	public boolean isLegalized5 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized5);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 6.
		@param IsLegalized6 Is Legalized 6	  */
	public void setIsLegalized6 (boolean IsLegalized6)
	{
		set_Value (COLUMNNAME_IsLegalized6, Boolean.valueOf(IsLegalized6));
	}

	/** Get Is Legalized 6.
		@return Is Legalized 6	  */
	public boolean isLegalized6 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized6);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 7.
		@param IsLegalized7 Is Legalized 7	  */
	public void setIsLegalized7 (boolean IsLegalized7)
	{
		set_Value (COLUMNNAME_IsLegalized7, Boolean.valueOf(IsLegalized7));
	}

	/** Get Is Legalized 7.
		@return Is Legalized 7	  */
	public boolean isLegalized7 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized7);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 8.
		@param IsLegalized8 Is Legalized 8	  */
	public void setIsLegalized8 (boolean IsLegalized8)
	{
		set_Value (COLUMNNAME_IsLegalized8, Boolean.valueOf(IsLegalized8));
	}

	/** Get Is Legalized 8.
		@return Is Legalized 8	  */
	public boolean isLegalized8 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized8);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Legalized 9.
		@param IsLegalized9 Is Legalized 9	  */
	public void setIsLegalized9 (boolean IsLegalized9)
	{
		set_Value (COLUMNNAME_IsLegalized9, Boolean.valueOf(IsLegalized9));
	}

	/** Get Is Legalized 9.
		@return Is Legalized 9	  */
	public boolean isLegalized9 () 
	{
		Object oo = get_Value(COLUMNNAME_IsLegalized9);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Issuing Region (Province).
		@param IsProvince Issuing Region (Province)	  */
	public void setIsProvince (boolean IsProvince)
	{
		set_Value (COLUMNNAME_IsProvince, Boolean.valueOf(IsProvince));
	}

	/** Get Issuing Region (Province).
		@return Issuing Region (Province)	  */
	public boolean isProvince () 
	{
		Object oo = get_Value(COLUMNNAME_IsProvince);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** HUBLA = HB */
	public static final String ISSUEDBY_HUBLA = "HB";
	/** SYAHBANDAR = SB */
	public static final String ISSUEDBY_SYAHBANDAR = "SB";
	/** BIRO KLASIFIKASI INDONESIA = BKI */
	public static final String ISSUEDBY_BIROKLASIFIKASIINDONESIA = "BKI";
	/** KOMINFO = KI */
	public static final String ISSUEDBY_KOMINFO = "KI";
	/** Set Issued By.
		@param IssuedBy Issued By	  */
	public void setIssuedBy (String IssuedBy)
	{

		set_Value (COLUMNNAME_IssuedBy, IssuedBy);
	}

	/** Get Issued By.
		@return Issued By	  */
	public String getIssuedBy () 
	{
		return (String)get_Value(COLUMNNAME_IssuedBy);
	}

	/** Set Issued Date.
		@param IssuedDate Issued Date	  */
	public void setIssuedDate (Timestamp IssuedDate)
	{
		set_Value (COLUMNNAME_IssuedDate, IssuedDate);
	}

	/** Get Issued Date.
		@return Issued Date	  */
	public Timestamp getIssuedDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_IssuedDate);
	}

//	public I_IssuedInstitution getIssuedInstitution() throws RuntimeException
//    {
//		return (I_IssuedInstitution)MTable.get(getCtx(), I_IssuedInstitution.Table_Name)
//			.getPO(getIssuedInstitution_ID(), get_TrxName());	}

	/** Set Issued by.
		@param IssuedInstitution_ID Issued by	  */
	public void setIssuedInstitution_ID (int IssuedInstitution_ID)
	{
		if (IssuedInstitution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IssuedInstitution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IssuedInstitution_ID, Integer.valueOf(IssuedInstitution_ID));
	}

	/** Get Issued by.
		@return Issued by	  */
	public int getIssuedInstitution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IssuedInstitution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Endorse Date.
		@param LastEndorse Last Endorse Date	  */
	public void setLastEndorse (Timestamp LastEndorse)
	{
		set_Value (COLUMNNAME_LastEndorse, LastEndorse);
	}

	/** Get Last Endorse Date.
		@return Last Endorse Date	  */
	public Timestamp getLastEndorse () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastEndorse);
	}

	/** Set Legalized Date 12.
		@param Legalized12 Legalized Date 12	  */
	public void setLegalized12 (Timestamp Legalized12)
	{
		set_Value (COLUMNNAME_Legalized12, Legalized12);
	}

	/** Get Legalized Date 12.
		@return Legalized Date 12	  */
	public Timestamp getLegalized12 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized12);
	}

	/** Set Legalized Date 13.
		@param Legalized13 Legalized Date 13	  */
	public void setLegalized13 (Timestamp Legalized13)
	{
		set_Value (COLUMNNAME_Legalized13, Legalized13);
	}

	/** Get Legalized Date 13.
		@return Legalized Date 13	  */
	public Timestamp getLegalized13 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized13);
	}

	/** Set Legalized Date 14.
		@param Legalized14 Legalized Date 14	  */
	public void setLegalized14 (Timestamp Legalized14)
	{
		set_Value (COLUMNNAME_Legalized14, Legalized14);
	}

	/** Get Legalized Date 14.
		@return Legalized Date 14	  */
	public Timestamp getLegalized14 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized14);
	}

	/** Set Legalized Date 15.
		@param Legalized15 Legalized Date 15	  */
	public void setLegalized15 (Timestamp Legalized15)
	{
		set_Value (COLUMNNAME_Legalized15, Legalized15);
	}

	/** Get Legalized Date 15.
		@return Legalized Date 15	  */
	public Timestamp getLegalized15 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized15);
	}

	/** Set Legalized Date 16.
		@param Legalized16 Legalized Date 16	  */
	public void setLegalized16 (Timestamp Legalized16)
	{
		set_Value (COLUMNNAME_Legalized16, Legalized16);
	}

	/** Get Legalized Date 16.
		@return Legalized Date 16	  */
	public Timestamp getLegalized16 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized16);
	}

	/** Set Legalized Date 17.
		@param Legalized17 Legalized Date 17	  */
	public void setLegalized17 (Timestamp Legalized17)
	{
		set_Value (COLUMNNAME_Legalized17, Legalized17);
	}

	/** Get Legalized Date 17.
		@return Legalized Date 17	  */
	public Timestamp getLegalized17 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized17);
	}

	/** Set Legalized Date 18.
		@param Legalized18 Legalized Date 18	  */
	public void setLegalized18 (Timestamp Legalized18)
	{
		set_Value (COLUMNNAME_Legalized18, Legalized18);
	}

	/** Get Legalized Date 18.
		@return Legalized Date 18	  */
	public Timestamp getLegalized18 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized18);
	}

	/** Set Legalized Date 19.
		@param Legalized19 Legalized Date 19	  */
	public void setLegalized19 (Timestamp Legalized19)
	{
		set_Value (COLUMNNAME_Legalized19, Legalized19);
	}

	/** Get Legalized Date 19.
		@return Legalized Date 19	  */
	public Timestamp getLegalized19 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized19);
	}

	/** Set Legalized Date 20.
		@param Legalized20 Legalized Date 20	  */
	public void setLegalized20 (Timestamp Legalized20)
	{
		set_Value (COLUMNNAME_Legalized20, Legalized20);
	}

	/** Get Legalized Date 20.
		@return Legalized Date 20	  */
	public Timestamp getLegalized20 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Legalized20);
	}

	/** Set Legalized Annualy.
		@param LegalizedAnnualy Legalized Annualy	  */
	public void setLegalizedAnnualy (boolean LegalizedAnnualy)
	{
		set_Value (COLUMNNAME_LegalizedAnnualy, Boolean.valueOf(LegalizedAnnualy));
	}

	/** Get Legalized Annualy.
		@return Legalized Annualy	  */
	public boolean isLegalizedAnnualy () 
	{
		Object oo = get_Value(COLUMNNAME_LegalizedAnnualy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Legalized By.
		@param LegalizedBy Legalized By	  */
	public void setLegalizedBy (String LegalizedBy)
	{
		set_Value (COLUMNNAME_LegalizedBy, LegalizedBy);
	}

	/** Get Legalized By.
		@return Legalized By	  */
	public String getLegalizedBy () 
	{
		return (String)get_Value(COLUMNNAME_LegalizedBy);
	}

	/** Set Legalized Date 1.
		@param LegalizedDate1 Legalized Date 1	  */
	public void setLegalizedDate1 (Timestamp LegalizedDate1)
	{
		set_Value (COLUMNNAME_LegalizedDate1, LegalizedDate1);
	}

	/** Get Legalized Date 1.
		@return Legalized Date 1	  */
	public Timestamp getLegalizedDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate1);
	}

	/** Set Legalized Date 10.
		@param LegalizedDate10 Legalized Date 10	  */
	public void setLegalizedDate10 (Timestamp LegalizedDate10)
	{
		set_Value (COLUMNNAME_LegalizedDate10, LegalizedDate10);
	}

	/** Get Legalized Date 10.
		@return Legalized Date 10	  */
	public Timestamp getLegalizedDate10 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate10);
	}

	/** Set Legalized Date 11.
		@param LegalizedDate11 Legalized Date 11	  */
	public void setLegalizedDate11 (Timestamp LegalizedDate11)
	{
		set_Value (COLUMNNAME_LegalizedDate11, LegalizedDate11);
	}

	/** Get Legalized Date 11.
		@return Legalized Date 11	  */
	public Timestamp getLegalizedDate11 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate11);
	}

	/** Set Legalized Date 2.
		@param LegalizedDate2 Legalized Date 2	  */
	public void setLegalizedDate2 (Timestamp LegalizedDate2)
	{
		set_Value (COLUMNNAME_LegalizedDate2, LegalizedDate2);
	}

	/** Get Legalized Date 2.
		@return Legalized Date 2	  */
	public Timestamp getLegalizedDate2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate2);
	}

	/** Set Legalized Date 3.
		@param LegalizedDate3 Legalized Date 3	  */
	public void setLegalizedDate3 (Timestamp LegalizedDate3)
	{
		set_Value (COLUMNNAME_LegalizedDate3, LegalizedDate3);
	}

	/** Get Legalized Date 3.
		@return Legalized Date 3	  */
	public Timestamp getLegalizedDate3 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate3);
	}

	/** Set Legalized Date 4.
		@param LegalizedDate4 Legalized Date 4	  */
	public void setLegalizedDate4 (Timestamp LegalizedDate4)
	{
		set_Value (COLUMNNAME_LegalizedDate4, LegalizedDate4);
	}

	/** Get Legalized Date 4.
		@return Legalized Date 4	  */
	public Timestamp getLegalizedDate4 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate4);
	}

	/** Set Legalized Date 5.
		@param LegalizedDate5 Legalized Date 5	  */
	public void setLegalizedDate5 (Timestamp LegalizedDate5)
	{
		set_Value (COLUMNNAME_LegalizedDate5, LegalizedDate5);
	}

	/** Get Legalized Date 5.
		@return Legalized Date 5	  */
	public Timestamp getLegalizedDate5 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate5);
	}

	/** Set Legalized Date 6.
		@param LegalizedDate6 Legalized Date 6	  */
	public void setLegalizedDate6 (Timestamp LegalizedDate6)
	{
		set_Value (COLUMNNAME_LegalizedDate6, LegalizedDate6);
	}

	/** Get Legalized Date 6.
		@return Legalized Date 6	  */
	public Timestamp getLegalizedDate6 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate6);
	}

	/** Set Legalized Date 7.
		@param LegalizedDate7 Legalized Date 7	  */
	public void setLegalizedDate7 (Timestamp LegalizedDate7)
	{
		set_Value (COLUMNNAME_LegalizedDate7, LegalizedDate7);
	}

	/** Get Legalized Date 7.
		@return Legalized Date 7	  */
	public Timestamp getLegalizedDate7 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate7);
	}

	/** Set Legalized Date 8.
		@param LegalizedDate8 Legalized Date 8	  */
	public void setLegalizedDate8 (Timestamp LegalizedDate8)
	{
		set_Value (COLUMNNAME_LegalizedDate8, LegalizedDate8);
	}

	/** Get Legalized Date 8.
		@return Legalized Date 8	  */
	public Timestamp getLegalizedDate8 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate8);
	}

	/** Set Legalized Date 9.
		@param LegalizedDate9 Legalized Date 9	  */
	public void setLegalizedDate9 (Timestamp LegalizedDate9)
	{
		set_Value (COLUMNNAME_LegalizedDate9, LegalizedDate9);
	}

	/** Get Legalized Date 9.
		@return Legalized Date 9	  */
	public Timestamp getLegalizedDate9 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LegalizedDate9);
	}

	/** Set Physical Document Number.
		@param ShipDocNumber Physical Document Number	  */
	public void setShipDocNumber (String ShipDocNumber)
	{
		set_Value (COLUMNNAME_ShipDocNumber, ShipDocNumber);
	}

	/** Get Physical Document Number.
		@return Physical Document Number	  */
	public String getShipDocNumber () 
	{
		return (String)get_Value(COLUMNNAME_ShipDocNumber);
	}

	/** Active = AC */
	public static final String SHIPDOCUMENTSTATUS_Active = "AC";
	/** Expired = EX */
	public static final String SHIPDOCUMENTSTATUS_Expired = "EX";
	/** Legalized In-Progress = LP */
	public static final String SHIPDOCUMENTSTATUS_LegalizedIn_Progress = "LP";
	/** Renewal In-Progress = RP */
	public static final String SHIPDOCUMENTSTATUS_RenewalIn_Progress = "RP";
	/** Set Ship Document Status.
		@param ShipDocumentStatus Ship Document Status	  */
	public void setShipDocumentStatus (String ShipDocumentStatus)
	{

		set_Value (COLUMNNAME_ShipDocumentStatus, ShipDocumentStatus);
	}

	/** Get Ship Document Status.
		@return Ship Document Status	  */
	public String getShipDocumentStatus () 
	{
		return (String)get_Value(COLUMNNAME_ShipDocumentStatus);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}