package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class MBBMActivity extends X_HBC_BBMActivity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -640903343265427272L;

	public MBBMActivity(Properties ctx, int HBC_BBMActivity_ID,
			String trxName) {
		super(ctx, HBC_BBMActivity_ID, trxName);
	}
	
	public MBBMActivity(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return true if can be saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		MTrip trip = (MTrip) getHBC_Trip();
		
		BigDecimal totalLiters = Env.ZERO;
		for (MBBMActivity activity : trip.getBBMActivity()) {
			totalLiters = totalLiters.add(activity.getTotalLiters());
		}
		
		trip.setTotalLiters(totalLiters);
		
		return true;
	}
	
	/**
	 *  Before Delete
	 */
	
	@Override
	protected boolean beforeDelete(){
	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE HBC_FuelTripAllocation SET HBC_BBMActivity_ID=null WHERE HBC_BBMActivity_ID=?");
		DB.executeUpdate(sql.toString(), get_ID(), get_TrxName());
		
		return true;
	}
	
}
