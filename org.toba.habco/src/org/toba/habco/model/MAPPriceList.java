package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MAPPriceList extends X_HBC_APPriceList{

	/**
	 * 
	 */
	private static final long serialVersionUID = 193865790941540101L;

	public MAPPriceList(Properties ctx, int HBC_APPriceList_ID, String trxName) {
		super(ctx, HBC_APPriceList_ID, trxName);
	}
	
	public MAPPriceList(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
}
