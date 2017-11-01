package org.toba.habco.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class HBC_RefreshMatView extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		// add materialized view in string array
		String[] views = { "tcs_fact_daily_mv" };

		for (String view : views) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("REFRESH MATERIALIZED VIEW " + view);
				DB.executeUpdate(sb.toString(), get_TrxName());
			} catch (Exception e) {
				log.severe("Error refresh view " + e.toString());
			}
		}
		
		return null;
	}

}
