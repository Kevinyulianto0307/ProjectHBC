package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MProjectTypeMilestone extends X_C_ProjectTypeMilestone{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5389494150891901645L;

	public MProjectTypeMilestone(Properties ctx, int C_ProjectTypeMilestone_ID,
			String trxName) {
		super(ctx, C_ProjectTypeMilestone_ID, trxName);
	}
	
	public MProjectTypeMilestone(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MProjectMilestone[] getProjectMilestone(){
		
		String where = "C_ProjectTypeMilestone_ID="+getC_ProjectTypeMilestone_ID();
		List<MProjectMilestone> list = new Query(getCtx(), MProjectMilestone.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setOrderBy("SeqNo")
		.list();
		
		MProjectMilestone[] lines = new MProjectMilestone[list.size()];
		list.toArray(lines);
		return lines;
	}
	
}
