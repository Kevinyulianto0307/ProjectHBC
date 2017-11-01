package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MBBMAdjustment extends X_HBC_BBMAdjustment{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3383171124872789804L;

	public MBBMAdjustment(Properties ctx, int HBC_BBMAdjustment_ID,
			String trxName) {
		super(ctx, HBC_BBMAdjustment_ID, trxName);
	}
	
	public MBBMAdjustment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
