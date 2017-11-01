package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MEmployeeDataChange extends X_HC_EmployeeDataChange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 467761302531919936L;

	public MEmployeeDataChange(Properties ctx, int HC_EmployeeDataChange_ID,
			String trxName) {
		super(ctx, HC_EmployeeDataChange_ID, trxName);
	}

	public MEmployeeDataChange(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
