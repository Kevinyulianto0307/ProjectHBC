package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MFuelTripAllocation extends X_HBC_FuelTripAllocation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7865978376651068290L;

	public MFuelTripAllocation(Properties ctx, int HBC_FuelTripAllocation_ID,
			String trxName) {
		super(ctx, HBC_FuelTripAllocation_ID, trxName);
	}
	
	public MFuelTripAllocation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
