package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MMatchCostActivity extends X_M_MatchCostActivity {
	
	private static final long serialVersionUID = -6974401172469369593L;
	
	public MMatchCostActivity(Properties ctx, int M_MatchCostActivity_ID,
			String trxName) {
		super(ctx, M_MatchCostActivity_ID, trxName);
	
	}
	
	public MMatchCostActivity(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	
	}


}
