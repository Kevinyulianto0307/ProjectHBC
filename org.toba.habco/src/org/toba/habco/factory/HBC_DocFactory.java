package org.toba.habco.factory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.base.IDocFactory;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MInventory;
import org.compiere.model.MInvoice;
import org.compiere.model.MMatchInv;
import org.compiere.model.MTable;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.acct.HBC_Doc_AllocationHdr;
import org.toba.habco.acct.HBC_Doc_Inventory;
import org.toba.habco.acct.HBC_Doc_Invoice;
import org.toba.habco.acct.HBC_Doc_MatchInv;

public class HBC_DocFactory implements IDocFactory {
	private final static CLogger s_log = CLogger.getCLogger(HBC_DocFactory.class);

	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, int Record_ID,
			String trxName) {
		
		String tableName = MTable.getTableName(Env.getCtx(), AD_Table_ID);
		
		Doc doc = null;	
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ").append(tableName)
								.append(" WHERE ").append(tableName)
								.append("_ID=? AND Processed='Y' AND Posted='N'");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement (sql.toString(), trxName);
			pstmt.setInt (1, Record_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				doc = getDocument(as, AD_Table_ID, rs, trxName);
			else
				s_log.severe("Not Found: " + tableName + "_ID=" + Record_ID);
		}
		catch (Exception e) {
			s_log.log (Level.SEVERE, sql.toString(), e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return doc;
	
	}

	@Override
	public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs,
			String trxName) {
		
		String tableName = MTable.getTableName(Env.getCtx(), AD_Table_ID);
		
		if(tableName.equals(MInvoice.Table_Name))
			return new HBC_Doc_Invoice(as, rs, trxName);
		else if(tableName.equals(MAllocationHdr.Table_Name))
			return new HBC_Doc_AllocationHdr(as, rs, trxName);
		else if(tableName.equals(MMatchInv.Table_Name))
			return new HBC_Doc_MatchInv(as, rs, trxName);
		//@phie
		else if(tableName.equals(MInventory.Table_Name))
			return new HBC_Doc_Inventory(as, rs, trxName);
		//end phie
		return null;

	}

}
