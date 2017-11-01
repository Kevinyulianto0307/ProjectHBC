package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MHBCLegalizedLine extends X_HBC_LegalizedLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3523940680353729316L;

	public MHBCLegalizedLine(Properties ctx, int Legalized_Line_Detail_ID,
			String trxName) {
		super(ctx, Legalized_Line_Detail_ID, trxName);
		
	}
	
	public MHBCLegalizedLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	
	}

	protected boolean afterSave(boolean newRecord,boolean Success){		
		if(getEndorseDate()!=null){
			MShipDocumentLine shipdocline = new MShipDocumentLine(getCtx(), getHBC_ShipDocumentLine_ID(), get_TrxName());
			shipdocline.setLastEndorse(getEndorseDate());
			shipdocline.setEffectiveDateTo(shipdocline.addYear(shipdocline.getEffectiveDateTo(),1));
			shipdocline.saveEx();
		}
		return true;
	}
	
	protected boolean beforeDelete(){
		MShipDocumentLine shipdocline = new MShipDocumentLine(getCtx(), getHBC_ShipDocumentLine_ID(), get_TrxName());
		shipdocline.setLastEndorse(null);
		shipdocline.setEffectiveDateTo(shipdocline.addYear(shipdocline.getEffectiveDateTo(),-1));
		shipdocline.saveEx();
		return true;
	}
	
}
