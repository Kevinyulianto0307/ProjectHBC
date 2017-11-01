package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MShipDocument extends X_HBC_ShipDocument{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MShipDocument(Properties ctx, int HBC_ShipDocument_ID,
			String trxName) {
		super(ctx, HBC_ShipDocument_ID, trxName);
	}

	public MShipDocument(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	protected boolean beforeSave(boolean newRecord) {
		int bargeID =(Integer)getHBC_Barge_ID();
		int tugboatID=(Integer)getHBC_Tugboat_ID();
		if (bargeID == 0 && tugboatID == 0 ) {
			throw new AdempiereException("Error- Must fill tugboat or barge");
		}
		return true;
	}

}
