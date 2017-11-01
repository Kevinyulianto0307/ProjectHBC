package org.toba.habco.callout;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MShipDocumentLine;
import org.toba.habco.model.X_HBC_ShipDocumentLine;

public class HBC_CalloutShipDocumentLine extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {	
		if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate1)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate2)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate3)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate4)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate5)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate6)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate7)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate8)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate9)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_LegalizedDate10)){
			return Legalized(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_ValidFrom)){
			return ValidFrom(ctx,WindowNo,mTab,mField,value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_ValidTo)){
			return ValidTo(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_ShipDocumentLine.COLUMNNAME_IsDistrict)){
			return deleteregion(ctx,WindowNo,mTab,mField,value);
		}
		return "";
	}
	
	private String deleteregion(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		boolean isdistrict = (boolean)value;
		if(isdistrict){
			mTab.setValue("From_PortPosition_ID", null);
		}else{
			mTab.setValue("C_Region_ID", null);
		}
		return "";
	}

	private String ValidFrom(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int HBC_ShipDocumentLine_ID=Env.getContextAsInt(ctx, windowNo, "HBC_ShipDocumentLine_ID");
		MShipDocumentLine shipdocline= new MShipDocumentLine(ctx, HBC_ShipDocumentLine_ID, null);
		Timestamp validfrom = (Timestamp) value;
		int doctypes =(Integer)mTab.getValue("HBC_ShipDocumentType_ID");
		for(int doctype:shipdocline.validFromDocType()){
			if(doctypes==doctype){
				mTab.setValue("EffectiveDateTo", shipdocline.addYear(validfrom, 1));
				break;
			}
		}
		
		return "";
	}
	
	private String ValidTo(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int HBC_ShipDocumentLine_ID=Env.getContextAsInt(ctx, windowNo, "HBC_ShipDocumentLine_ID");
		MShipDocumentLine shipdocline= new MShipDocumentLine(ctx, HBC_ShipDocumentLine_ID, null);
		Timestamp validto = (Timestamp) value;
		int doctypes =(Integer)mTab.getValue("HBC_ShipDocumentType_ID");
		for(int doctype:shipdocline.validFromDocType()){
			if(doctypes!=doctype && mTab.getValue("EffectiveDateTo")==null){
				mTab.setValue("EffectiveDateTo",validto);
			}
		}
		
		return msg;
	}


	public String Legalized(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){	
			
		int HBC_ShipDocument_ID=Env.getContextAsInt(ctx, windowNo, "HBC_ShipDocumentLine_ID");
			
		String sql2 = "SELECT sdl.EffectiveDateto + cast('1 year' as interval)"
				+ " FROM HBC_ShipDocumentLine sdl "
				+ " where sdl.HBC_ShipDocumentLine_ID = ?";
		Timestamp date =DB.getSQLValueTS(null, sql2, new Object[]{HBC_ShipDocument_ID});
		
		if(date!=null)
			mTab.setValue("EffectiveDateTO", date);
		
		return "";
	}

}

