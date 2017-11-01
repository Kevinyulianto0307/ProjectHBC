package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MBarge extends X_HBC_Barge {

	private static final long serialVersionUID = 811976101770395639L;
	
  	

	/**
	 * by yonk
	 */
	
	
	//HABCO-1647 Validasi Build Year
	  @Override
	  protected boolean beforeSave (boolean newRecord)
		{   	
	  	if(Integer.parseInt(getBuildYear()) <1950){
	  		throw new AdempiereException("Build Year Cannot Under 1950");
	  	}
	  	//HABCO-1652 Max Cargo > Min Cargo
	  	if(getMaxCargo().compareTo(getMinCargo())<0){
	  		throw new AdempiereException("Maximum Cargo Must Bigger than Minimum Cargo");
	  	}//END OF HABCO-1652
	  	
	  	return true;
		}//end of HABCO-1647
	  
	  public MBarge(Properties ctx, int HBC_Barge_ID, String trxName) {
			super(ctx, HBC_Barge_ID, trxName);
		
		}
	   public MBarge(Properties ctx, ResultSet rs, String trxName) {
			super(ctx, rs, trxName);
		
		}



}
