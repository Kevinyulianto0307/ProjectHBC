package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.toba.habco.model.X_HBC_ContractTerm;

public class MContractTerm extends X_HBC_ContractTerm {

	/**
	 * @author by yonk
	 */
	private static final long serialVersionUID = -5895378129900788079L;
	
	public MContractTerm(Properties ctx, int HBC_ContractTerm_ID,
			String trxName) {
		super(ctx, HBC_ContractTerm_ID, trxName);
	}

	public MContractTerm(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


}
