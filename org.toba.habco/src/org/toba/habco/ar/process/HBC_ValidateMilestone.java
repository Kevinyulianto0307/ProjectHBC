package org.toba.habco.ar.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.model.MSequence.GetIDs;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MProjectMilestone;
import org.toba.habco.model.MProjectTypeMilestone;

public class HBC_ValidateMilestone extends SvrProcess{

	private int p_C_ProjectTypeMilestone_ID = 0;
	protected void prepare() {
		
		p_C_ProjectTypeMilestone_ID = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {
		if(p_C_ProjectTypeMilestone_ID == 0)
			throw new AdempiereException("There's no data selected..");
		
		MProjectTypeMilestone projectTypeMilestone = new MProjectTypeMilestone(getCtx(), p_C_ProjectTypeMilestone_ID, get_TrxName());
		String whereClause = "C_ProjectTypeMilestone_ID=?";
		int[] milestone_IDs = new Query(getCtx(), MProjectMilestone.Table_Name, whereClause, get_TrxName())
								.setParameters(new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID()})
								.getIDs();
		if(milestone_IDs.length == 0)
			throw new AdempiereException("There's no line");
		
		//Cek DP
		String whereClauseCheckDP = "C_ProjectTypeMilestone_ID=? AND CommittedAmt > 0";
		int[] milestoneDP_IDs = new Query(getCtx(), MProjectMilestone.Table_Name, whereClauseCheckDP, get_TrxName())
								.setParameters(new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID()})
								.getIDs();
		if(milestoneDP_IDs.length > 1)
			throw new AdempiereException("Missing setup.. There's two Down Payment Milestone..");
		
		whereClauseCheckDP = "C_ProjectTypeMilestone_ID=? AND CommittedAmt > 0 AND Exists ("
							+ "SELECT 1 FROM C_ProjectMilestone cp2 WHERE cp2.C_ProjectTypeMilestone_ID = ? "
							+ "AND cp2.seqNo < C_ProjectMilestone.seqNo)";
		boolean match = new Query(getCtx(), MProjectMilestone.Table_Name, whereClauseCheckDP, get_TrxName())
								.setParameters(new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID(), 
										projectTypeMilestone.getC_ProjectTypeMilestone_ID()})
								.match();
		if(match)
			throw new AdempiereException("DP must be the smallest sequence number..");
		
		String sumPercentage = "SELECT sum(MilestonePercentage) FROM C_ProjectMilestone WHERE C_ProjectTypeMilestone_ID = ? AND CommittedAmt = 0";
		BigDecimal percentage = DB.getSQLValueBD(get_TrxName(), sumPercentage.toString(), new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID()});
		
		if(percentage.compareTo(Env.ONEHUNDRED) != 0)
			throw new AdempiereException("Milestone Percentage must be one hundred..");
		
		whereClauseCheckDP = "C_ProjectTypeMilestone_ID=? AND isFinalMilestone='Y'";
		int[] ids = new Query(getCtx(), MProjectMilestone.Table_Name, whereClauseCheckDP, get_TrxName())
							.setParameters(new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID()})
							.getIDs();
		if(ids.length > 1)
			throw new AdempiereException("Final Milestone must be one..");
		
		
		whereClauseCheckDP = "C_ProjectTypeMilestone_ID=? AND isFinalMilestone='Y' AND Exists ("
				+ "SELECT 1 FROM C_ProjectMilestone cp2 WHERE cp2.C_ProjectTypeMilestone_ID = ? "
				+ "AND cp2.seqNo > C_ProjectMilestone.seqNo)";
		match = new Query(getCtx(), MProjectMilestone.Table_Name, whereClauseCheckDP, get_TrxName())
							.setParameters(new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID(), 
									projectTypeMilestone.getC_ProjectTypeMilestone_ID()})
							.match();
		if(match)
			throw new AdempiereException("Final Milestone must be the largest sequence number..");
		
		whereClauseCheckDP = "C_ProjectTypeMilestone_ID=?";
		int lastSeq = new Query(getCtx(), MProjectMilestone.Table_Name, whereClauseCheckDP, get_TrxName())
							.setParameters(new Object[]{projectTypeMilestone.getC_ProjectTypeMilestone_ID()})
							.setOrderBy("SeqNo DESC")
							.firstId();
		MProjectMilestone lastMilestone =  new MProjectMilestone(getCtx(), lastSeq, get_TrxName());
		if(!lastMilestone.isFinalMilestone())
			throw new AdempiereException("The largest sequence number must be final milestone");
		
		projectTypeMilestone.set_ValueOfColumn("isConfirm", true);
		projectTypeMilestone.saveEx();
		
		return "Valid..";
	}
	

}
