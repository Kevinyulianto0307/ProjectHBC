package org.toba.habco.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class HBC_RemoveBBMPlanIDFromShipActivity extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int saID = getRecord_ID();
		
		String sql = "UPDATE HBC_ShipActivity SET TCS_BBMPlan_ID=null WHERE HBC_ShipActivity_ID=?";
		int no = DB.executeUpdate(sql, saID, get_TrxName());
		if(no<1)
			throw new AdempiereException("Failed!");
		log.info("UPDATED ShipActivity BBMPlanID Activity#"+no);
		
		return null;
	}

}
