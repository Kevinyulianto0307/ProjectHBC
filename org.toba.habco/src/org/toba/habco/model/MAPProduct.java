package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MAPProduct extends X_HBC_APProduct{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6702674361739199384L;

	public MAPProduct(Properties ctx, int HBC_APProduct_ID, String trxName) {
		super(ctx, HBC_APProduct_ID, trxName);
	}
	
	public MAPProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	

}
