package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MTugboat extends X_HBC_Tugboat {

	public MTugboat(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

	public MTugboat(Properties ctx, int HBC_Tugboat_ID, String trxName) {
		super(ctx, HBC_Tugboat_ID, trxName);
		
	}

	/**
	 * created by yonk
	 * 
	 */
	private static final long serialVersionUID = -1754405679706655223L;
	
	
	//HABCO-1647 Validasi Build Year
	@Override
    protected boolean beforeSave (boolean newRecord)
  	{   	
    	if(Integer.parseInt(getBuildYear()) <1950){
    		throw new AdempiereException("Build Year Cannot Under 1950");
    	}
    	
    	return true;
  	}//end of HABCO-1647

}
