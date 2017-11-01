package org.toba.habco.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAsset;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MDepreciationEntry;
import org.compiere.model.MDepreciationExp;
import org.compiere.model.MDepreciationWorkfile;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

public class HBC_FixBugAsset extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int A_Asset_IDs[] = new Query(getCtx(), MAsset.Table_Name, "", get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		for (int A_Asset_ID : A_Asset_IDs) {
			
			int WorkFile_ID = getWorkFile(A_Asset_ID);
			int Addition_ID = getAddition(A_Asset_ID);
			
			if(WorkFile_ID <= 0 || Addition_ID <= 0)
				continue;
			
			MAssetAddition addition = new MAssetAddition(getCtx(), Addition_ID, get_TrxName());
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE A_Depreciation_Workfile SET A_Accumulated_Depr = ? "
					+ "WHERE A_Depreciation_Workfile_ID = ? ");
			int no = DB.executeUpdate(sb.toString(), new Object[]{addition.getA_Accumulated_Depr(),WorkFile_ID}, true, get_TrxName());
			log.info("UPDATED WorkFile#"+no);
			
			sb = new StringBuilder();
			sb.append("UPDATE A_Depreciation_Workfile SET A_Current_Period = ? "
					+ "WHERE A_Depreciation_Workfile_ID = ? ");
			no = DB.executeUpdate(sb.toString(), new Object[]{addition.getA_Period_Start(),WorkFile_ID}, true, get_TrxName());
			log.info("UPDATED WorkFile#"+no);
			
			sb = new StringBuilder();
			sb.append("UPDATE A_Depreciation_Workfile SET A_Asset_Remaining = A_Asset_Cost "
					+ "WHERE A_Depreciation_Workfile_ID = ? ");
			no = DB.executeUpdate(sb.toString(), new Object[]{WorkFile_ID}, true, get_TrxName());
			log.info("UPDATED WorkFile#"+no);
			
			sb = new StringBuilder();
			sb.append("UPDATE A_Depreciation_Workfile SET DateAcct = ? "
					+ "WHERE A_Depreciation_Workfile_ID = ? ");
			no = DB.executeUpdate(sb.toString(), new Object[]{addition.getDateAcct(),WorkFile_ID}, true, get_TrxName());
			log.info("UPDATED WorkFile#"+no);
			
			MDepreciationWorkfile wk = new MDepreciationWorkfile(getCtx(), WorkFile_ID, get_TrxName());
			
			deleteExpense(wk.getA_Asset_ID());
			
			wk.buildDepreciation();
			wk.saveEx();
		}
		
		/*
		int entry_IDs[] = new Query(getCtx(), MDepreciationEntry.Table_Name, "", get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		for (int entry_ID : entry_IDs) {
			MDepreciationEntry entry = new MDepreciationEntry(getCtx(), entry_ID, get_TrxName());
			selectLines(entry);
		}
		*/
		
		return "";
	}

	private int getWorkFile(int A_Asset_ID){
		
		String where = "A_Asset_ID=?";
		int id = new Query(getCtx(), MDepreciationWorkfile.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setParameters(new Object[]{A_Asset_ID})
		.firstId();
		
		return id;
	}
	
	private int getAddition(int A_Asset_ID){
		
		String where = "A_Asset_ID=?";
		int id = new Query(getCtx(), MAssetAddition.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setParameters(new Object[]{A_Asset_ID})
		.firstId();
		
		return id;
	}
	
	/**
	 * remove depreciation entry on expense
	 * @param A_Asset_ID
	 */
	private void deleteExpense(int A_Asset_ID){
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM A_Depreciation_Exp WHERE A_Asset_ID=?");
		int no = DB.executeUpdate(sb.toString(), A_Asset_ID, get_TrxName());
		log.info("DELETED A_Depreciation_Exp #"+no);
		
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
