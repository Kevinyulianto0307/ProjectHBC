package org.toba.habco.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.Query;

public class MShipDocumentLine extends X_HBC_ShipDocumentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -134686213072467242L;

	public MShipDocumentLine(Properties ctx, int HBC_ShipDocumentLine_ID,
			String trxName) {
		super(ctx, HBC_ShipDocumentLine_ID, trxName);
	}
	
	public MShipDocumentLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
		
	/*
	 * get doctype valid from
	 */
	public int[] validFromDocType(){
		int[] validfromdoctype = new Query(getCtx(),X_HBC_ShipDocumentType.Table_Name,"IsValidFrom='Y'",get_TrxName())
								.setOnlyActiveRecords(true)
								.getIDs();
		return validfromdoctype;
	}
		
	public Timestamp addYear (Timestamp getyear,int totalyear){
		Timestamp ts = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(getyear);
		cal.add(Calendar.YEAR, totalyear);
		ts = new Timestamp(cal.getTime().getTime());
		return ts;
	}
	
}
