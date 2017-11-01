package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.MBBMPlan;

public class HBC_CalculateBBMEstimation extends SvrProcess{

	private int TCS_BBMPlan_ID = 0;
	protected void prepare() {
		TCS_BBMPlan_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		BigDecimal bbmEstimation= Env.ZERO;
		
		if (TCS_BBMPlan_ID==0) 
			return "No BBMPlan selected";
		
		MBBMPlan bbm_plan = new MBBMPlan(getCtx(), TCS_BBMPlan_ID, get_TrxName());
		
		BigDecimal liter = bbm_plan.getLiter();
		BigDecimal totalLiters = bbm_plan.getTotalLiters();
		bbmEstimation = liter.subtract(totalLiters).setScale(2, RoundingMode.HALF_UP);
		
		String message = Msg.parseTranslation(getCtx(), "BBM Estimation :"+bbmEstimation);
		addBufferLog(0, null, BigDecimal.ZERO, message, 0, 0);
		
		return "";
	}

}
