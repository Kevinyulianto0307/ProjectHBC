package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MGeneralTerm extends X_HBC_GeneralTerm {

	/**
	 * 
	 *@author yonk 
	 */
	
	private static final long serialVersionUID = -832930633121097311L;

	public MGeneralTerm(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	public MGeneralTerm(Properties ctx, int HBC_GeneralTerm_ID,
			String trxName) {
		super(ctx, HBC_GeneralTerm_ID, trxName);

	}

	

}
