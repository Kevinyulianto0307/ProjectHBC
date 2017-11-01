package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MLoadUnloadDay extends X_HBC_LoadUnloadDay {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3127152388927766593L;

	public MLoadUnloadDay(Properties ctx, int HBC_LoadUnloadDay_ID,
			String trxName) {
		super(ctx, HBC_LoadUnloadDay_ID, trxName);

	}

	public MLoadUnloadDay(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);

	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(!isProrate()){
			if(getTotalLoadingDay()==0 || getTotalUnloadingDay()==0)	
				return false;
			
		} else if(getProrateDay()==0){
			return false;
		}

		return super.beforeSave(newRecord);
	}
	

}
