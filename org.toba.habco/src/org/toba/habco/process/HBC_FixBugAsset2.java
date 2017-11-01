package org.toba.habco.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MDepreciationEntry;
import org.compiere.model.MDepreciationExp;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

public class HBC_FixBugAsset2 extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		int A_Depreciation_Entry_ID = getRecord_ID();
		MDepreciationEntry entry = new MDepreciationEntry(getCtx(), A_Depreciation_Entry_ID, get_TrxName());
		selectLines(entry);
		return "Success Select Lines";
	}

	private void selectLines(MDepreciationEntry entry)
	{
		// Reset selected lines:
		unselectLines(entry);
		
		// Select lines:
		final String sql = "UPDATE " + MDepreciationExp.Table_Name + " SET "
				+ MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID + "=?"
				+ " WHERE "
					+ MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID + " IS NULL"
					+ " AND TRUNC("+MDepreciationExp.COLUMNNAME_DateAcct+",'MONTH') = ?"
					+ " AND AD_Client_ID=? AND AD_Org_ID=?";
		
		Timestamp dateAcct = TimeUtil.trunc(entry.getDateAcct(), TimeUtil.TRUNC_MONTH);
		int no = DB.executeUpdateEx(sql, new Object[]{entry.get_ID(), dateAcct, getAD_Client_ID(), entry.getAD_Org_ID()}, get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Updated #" + no);
	}
	
	private void unselectLines(MDepreciationEntry entry)
	{
		String sql = "UPDATE " + MDepreciationExp.Table_Name + " SET "
						+ MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID + "=NULL "
					+ " WHERE "
						+ MDepreciationExp.COLUMNNAME_A_Depreciation_Entry_ID + "=?";
		int id = entry.get_ID();
		if (id <= 0) 
		{ // Use old ID is current ID is missing (i.e. object was deleted)
			id = entry.get_IDOld();
		}
		int no = DB.executeUpdateEx(sql, new Object[]{id}, get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Updated #" + no);
	}
	
}
