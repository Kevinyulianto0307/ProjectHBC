package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

public class MProjectMilestone extends X_C_ProjectMilestone{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5182604533391339335L;

	public MProjectMilestone(Properties ctx, int C_ProjectMilestone_ID,
			String trxName) {
		super(ctx, C_ProjectMilestone_ID, trxName);
	}
	
	public MProjectMilestone(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord || is_ValueChanged("CommitedAmt") || is_ValueChanged("SeqNo")
				|| is_ValueChanged("MilestonePercentage") || is_ValueChanged("isFinalMilestone") || is_ValueChanged("isPositionFrom"))
		{
			MProjectTypeMilestone projectTypeMilestone = new MProjectTypeMilestone(null, getC_ProjectTypeMilestone_ID(), get_TrxName());
			projectTypeMilestone.set_ValueOfColumn("isConfirm", false);
			projectTypeMilestone.saveEx();
		}
		return true;
	}
}
