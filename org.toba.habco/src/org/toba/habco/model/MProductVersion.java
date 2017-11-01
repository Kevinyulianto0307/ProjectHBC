package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MProductVersion extends X_M_Product_Version {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4601704762455313035L;

	public MProductVersion(Properties ctx, int M_Product_Version_ID,
			String trxName) {
		super(ctx, M_Product_Version_ID, trxName);
	}
	
	public MProductVersion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
