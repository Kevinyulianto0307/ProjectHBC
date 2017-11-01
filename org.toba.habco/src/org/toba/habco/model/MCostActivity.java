
package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

public class MCostActivity extends X_HBC_CostActivity {


	/**
	 * @author yonk
	 */
	private static final long serialVersionUID = 6086570508282592779L;

	
	public MCostActivity(Properties ctx, int HBC_CostActivity_ID, String trxName) {
		super(ctx, HBC_CostActivity_ID, trxName);
	}

	
	public MCostActivity(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	protected boolean beforeSave(boolean newRecord){
		
		if(getPriceList().compareTo(Env.ZERO)==0){
			throw new AdempiereException("This Product Doesn't Have PriceList");
		}
		return true;
	}
	
	protected boolean beforeDelete(){
		
		if(getAD_User_ID()!=Env.getContextAsInt(getCtx(), COLUMNNAME_AD_User_ID)){
			throw new AdempiereException("This Record Is Made By Other User");
		}
		return true;
	}
}
