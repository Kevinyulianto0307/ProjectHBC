package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MDiffRecord extends X_HBC_DiffRecord {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7428140061026818601L;

	public MDiffRecord(Properties ctx, int HBC_DiffRecord_ID, String trxName) {
		super(ctx, HBC_DiffRecord_ID, trxName);
	}
	
	public MDiffRecord(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	protected boolean beforeDelete(){
		
		if(get_ValueAsInt("HBC_FuelTripAllocation_ID")>0){
			MFuelTripAllocation tripAlloc = new MFuelTripAllocation(getCtx(), get_ValueAsInt("HBC_FuelTripAllocation_ID"), get_TrxName());
			tripAlloc.setIsProcess(false);
			tripAlloc.saveEx();
		}else if (get_ValueAsInt("HBC_BBMActivity_ID")>0){
			MBBMActivity bbmActivity = new MBBMActivity(getCtx(), get_ValueAsInt("HBC_BBMActivity_ID"), get_TrxName());
			bbmActivity.setIsProcess(false);
			bbmActivity.saveEx();
		}
		
		return true;
	}

}
