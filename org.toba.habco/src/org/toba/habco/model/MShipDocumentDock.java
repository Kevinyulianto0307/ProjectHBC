package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.Adempiere;
import org.compiere.util.Msg;

public class MShipDocumentDock extends X_HBC_ShipDocument_Dock{

	public MShipDocumentDock(Properties ctx, int HBC_ShipDocument_Dock_ID,
			String trxName) {
		super(ctx, HBC_ShipDocument_Dock_ID, trxName);
	}

	public MShipDocumentDock(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5481426584143769062L;
	
	@Override
	protected boolean beforeDelete(){
		
		if(get_ValueAsInt("C_Invoice_ID")>0 || get_ValueAsInt("C_Invoice_ID")>0 || get_ValueAsBoolean("isInvoiced")){
			log.saveError("Error", Msg.getMsg(getCtx(), "isInvoiced"));
			return false;
		}
		
		return true;
	}
}
