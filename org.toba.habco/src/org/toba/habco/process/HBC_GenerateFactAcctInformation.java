package org.toba.habco.process;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MAssetDisposed;
import org.compiere.model.MMatchInv;
import org.compiere.model.MPayment;
import org.compiere.model.MTCSAmortizationRun;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * 
 * @author Phie Albert
 * Generate description, documentNo, and line
 */

public class HBC_GenerateFactAcctInformation extends SvrProcess{
	ArrayList<Integer> ad_table_ids = new ArrayList<Integer>();
	ArrayList<String> tableNames = new ArrayList<String>();
	
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT DISTINCT fa.AD_Table_ID, adt.tableName "
        		+ "FROM Fact_Acct fa "
        		+ "JOIN AD_Table adt ON adt.AD_Table_ID = fa.AD_Table_ID");
        PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	pstmt = DB.prepareStatement(sqlStmt.toString(), null);  
            rs = pstmt.executeQuery();
            while (rs.next()){
            	ad_table_ids.add(rs.getInt(1));
            	tableNames.add(rs.getString(2));
            }
        }catch(Exception e){
        	log.severe("");
        }finally{
        	DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
        }
		
        for(int i=0 ; i<ad_table_ids.size() ; i++)
        {	
        	int ad_table_id = ad_table_ids.get(i);
        	String tableName = tableNames.get(i);
        	System.out.println(tableName);
        	//define table name line
        	String tableNameLine = tableName + "Line";
        	if(ad_table_id == 735) //allocation hdr
        		tableNameLine = "C_AllocationLine";
        	if(ad_table_id == 53121) //depre entry
        		tableNameLine = "A_Depreciation_Exp";
        	if(ad_table_id == 300191) //amortization run
        		tableNameLine = "TCS_AmortizationLine";
        	
        	//UPDATE DOCUMENTNO
        	System.out.println("Trying to create document no...");
        	StringBuilder sb = new StringBuilder();
        	sb.append("UPDATE FACT_ACCT SET DocumentNo = "
        			+ "(SELECT DocumentNo FROM "+tableName+" WHERE FACT_ACCT.Record_ID = "+tableName+"_ID) "
        			+ "WHERE FACT_ACCT.AD_Table_ID = "+ad_table_id);
        	int no = DB.executeUpdate(sb.toString(), get_TrxName());
        	System.out.println(tableName+" : Document No -"+no);
        	
        	//UPDATE LINE
        	System.out.println("Trying to create line...");
        	if(ad_table_id != MPayment.Table_ID && 
        			ad_table_id != MMatchInv.Table_ID && 
        			ad_table_id != MAssetDisposed.Table_ID && 
        			ad_table_id != MAssetAddition.Table_ID)
        	{
        		sb = new StringBuilder();
            	sb.append("UPDATE FACT_ACCT SET Line = "
            			+ "(SELECT Line FROM "+tableNameLine+" WHERE FACT_ACCT.Line_ID = "+tableNameLine+"_ID) "
            			+ "WHERE FACT_ACCT.AD_Table_ID = "+ad_table_id);
            	int no2 = DB.executeUpdate(sb.toString(), get_TrxName());
            	System.out.println(tableName+" : Line -"+no2);
        	} else {
        		sb = new StringBuilder();
            	sb.append("UPDATE FACT_ACCT SET Line = NULL "
            			+ "WHERE FACT_ACCT.AD_Table_ID = "+ad_table_id);
            	int no2 = DB.executeUpdate(sb.toString(), get_TrxName());
            	System.out.println(tableName+" : Line -"+no2);
        	}
        	
        	//UPDATE DESCRIPTION
        	if(ad_table_id == MPayment.Table_ID || ad_table_id == MMatchInv.Table_ID 
        			|| ad_table_id == MAllocationHdr.Table_ID || ad_table_id == MAssetDisposed.Table_ID 
        			|| ad_table_id == MAssetAddition.Table_ID || ad_table_id == MTCSAmortizationRun.Table_ID)
        	{
        		// fix get description from document header, some table doesn't have detail line
        		System.out.println("Trying to description con 1...");
        		sb = new StringBuilder();
            	sb.append("UPDATE FACT_ACCT SET Description = "
            			+ "(SELECT Description FROM "+tableName+" WHERE FACT_ACCT.Record_ID = "+tableName+"_ID) "
            			+ "WHERE FACT_ACCT.AD_Table_ID = "+ad_table_id);
            	int no2 = DB.executeUpdate(sb.toString(), get_TrxName());
            	System.out.println(tableName+" : Description Con1 -"+no2);
        	}
        	else{
        		System.out.println("Trying to description 2...");
        		sb = new StringBuilder();
            	sb.append("UPDATE FACT_ACCT SET Description = "
            			+ "(SELECT COALESCE((SELECT Description FROM "+tableNameLine+" WHERE "+tableNameLine+"_ID = FACT_ACCT.Line_ID),(SELECT Description FROM "+tableName+" WHERE "+tableName+"_ID = FACT_ACCT.Record_ID)) FROM dual) "
            			+ "WHERE FACT_ACCT.AD_Table_ID = "+ad_table_id);
            	int no2 = DB.executeUpdateEx(sb.toString(), get_TrxName());
            	System.out.println(tableName+" : Description Con2-"+no2);
        	}
        		
        		
        }
		
		return "";
	}
	

}
