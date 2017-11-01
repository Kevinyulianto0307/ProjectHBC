package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class TCSGeneralLedgerDailyView extends SvrProcess{
	private int p_start_Account_id=0;
	private int p_end_Account_id=0;
	//search between account_no
	private BigDecimal p_start_Account_NO= Env.ZERO; //account_no
	private BigDecimal p_end_Account_NO = Env.ZERO; //account_no 
	private Timestamp p_DateFrom = null;
	private Timestamp p_DateTo = null;	
	private ArrayList<Integer> account_id_lists = new ArrayList<Integer>();
	private ArrayList<String> account_no_lists = new ArrayList<String>();
	private ArrayList<String> account_name_lists = new ArrayList<String>();
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null && para[i].getParameter_To() == null)
				;
			else if(name.equals("Account_ID"))
			{
				p_start_Account_id = para[i].getParameterAsInt();
				p_end_Account_id = para[i].getParameter_ToAsInt();
			}
			else if(name.equals("DateAcct")){
				p_DateFrom = para[i].getParameterAsTimestamp();
				p_DateTo = para[i].getParameter_ToAsTimestamp();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		cleanTable(); //clear temporary table
		
		//convert start account_id to account_no
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT value FROM c_elementvalue cev WHERE c_elementvalue_id=?");
		p_start_Account_NO = DB.getSQLValueBD(get_TrxName(), sb.toString(), p_start_Account_id);
		
		//convert end account_id to account_no
		StringBuilder sb2 = new StringBuilder();
		sb2.append("SELECT DISTINCT value FROM c_elementvalue cev WHERE c_elementvalue_id=?");
		p_end_Account_NO = DB.getSQLValueBD(get_TrxName(), sb2.toString(), p_end_Account_id);
		
        setAllAccount(); // to array list account_id_lists
        
		for(int i=0;i<account_id_lists.size();i++)
		{
			//Timestamp before_p_dateFrom = TimeUtil.addDays(p_DateFrom, -1);
			

			StringBuilder getLastTotalBalance = new StringBuilder();
			getLastTotalBalance.append("(SELECT tcs_initial(date'"+p_DateFrom+"',?))");
			
			BigDecimal initialBalance = DB.getSQLValueBD(get_TrxName(), getLastTotalBalance.toString(), account_id_lists.get(i));
			
			//insert initial balance to temporary table
			createInitialBalanceLine(initialBalance, account_id_lists.get(i), account_name_lists.get(i), account_no_lists.get(i));
			//insert all request to temporary table
			createReconciledLines(account_id_lists.get(i));
		}  
		 
		return null;
	}
	
	protected void setAllAccount()
	{
		//get semua account yang ada di antara account dan end.. isi ke array atau list
		//loop isi dari step 1
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT distinct account_id, account_name, account_no FROM tcs_fact_daily_mv WHERE cast(account_no AS INT) BETWEEN "+p_start_Account_NO+" AND "+p_end_Account_NO);
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
        try{
            pstmt = DB.prepareStatement(sb.toString(), get_TrxName());
            rs = pstmt.executeQuery();
             
            while(rs.next())
            {
            	account_id_lists.add(rs.getInt(1));
            	account_name_lists.add(rs.getString(2));
            	account_no_lists.add(rs.getString(3));
            }
             
        }catch(Exception e){
            log.severe(e.toString());
        }finally{
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
	}

	protected void cleanTable(){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM t_tcsgeneralledgerdailyview");
		DB.executeUpdate(sql.toString(), get_TrxName());
	
	}
	
	protected void createInitialBalanceLine(BigDecimal initialBalance, int account_id, String account_name, String account_no){
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO t_tcsgeneralledgerdailyview VALUES("
				+getAD_Client_ID()+","+Env.getAD_Org_ID(getCtx())+",'-',null,null,"+account_id+",'"+account_no+"','"+account_name+"',null,null,null,null,null,null,null,"+initialBalance+","+getAD_PInstance_ID()+",1)");
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	protected void createReconciledLines(int account_id){
		
        StringBuffer sb = new StringBuffer("INSERT INTO t_tcsgeneralledgerdailyview "
				+ "(ad_client_id, ad_org_id, org_name, c_acctschema_id, acct_schema_name, account_id, account_no, account_name, dateacct, c_period_id, period_name, "
				+ "amtacctdr, amtacctcr, amtsourcecr, amtsourcedr, amtacctbalance, AD_PInstance_ID, sequence) ");
	
        sb.append("SELECT ad_client_id, ad_org_id, org_name, c_acctschema_id, acct_schema_name, account_id, account_no, account_name, ")
        .append("dateacct, c_period_id, period_name, ")
        .append("(SUM(amtacctdr)),")
        .append("(SUM(amtacctcr)),")
        .append("(SUM(amtsourcecr)),")
        .append("(SUM(amtsourcedr)),(")
        .append("+(SUM(amtacctbalance*multiplier))), ")
        .append(getAD_PInstance_ID())
        .append(",2 FROM tcs_fact_daily_mv ")
        .append("WHERE account_id="+account_id)
        .append(" AND (dateacct BETWEEN '"+p_DateFrom+"' AND '"+p_DateTo+"') ")
        .append("GROUP BY dateacct, ad_client_id, ad_org_id, org_name, c_acctschema_id, acct_schema_name, account_id, account_no,account_name,  c_period_id, period_name ")
        .append("ORDER BY dateacct");
        
        int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());

	
	}
}
