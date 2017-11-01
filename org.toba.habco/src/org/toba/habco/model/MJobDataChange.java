package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MJobDataChange extends X_HC_JobDataChange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2454447704433471248L;

	public MJobDataChange(Properties ctx, int HC_JobDataChange_ID,
			String trxName) {
		super(ctx, HC_JobDataChange_ID, trxName);
	}

	public MJobDataChange(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
