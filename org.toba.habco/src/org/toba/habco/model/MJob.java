package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MJob extends X_HC_Job {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096754915843109134L;

	public MJob(Properties ctx, int HC_Job_ID, String trxName) {
		super(ctx, HC_Job_ID, trxName);
	}

	public MJob(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
