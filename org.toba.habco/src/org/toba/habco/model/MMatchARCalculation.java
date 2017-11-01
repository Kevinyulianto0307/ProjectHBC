package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MMatchARCalculation extends X_M_MatchARCalculation {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2963971735668824758L;
	
	public MMatchARCalculation(Properties ctx, int M_MatchARCalculation_ID,
			String trxName) {
		super(ctx, M_MatchARCalculation_ID, trxName);
	}

	public MMatchARCalculation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
