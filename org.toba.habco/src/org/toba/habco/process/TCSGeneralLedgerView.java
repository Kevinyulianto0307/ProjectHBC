package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class TCSGeneralLedgerView extends SvrProcess {
	
	
	private int p_start_Account_id=0;
	private int p_end_Account_id=0;
	private int p_C_BPartner_ID=0;
	private int p_HBC_Tugboat_ID=0;
	private int p_HBC_Barge_ID=0;
	private int p_AD_Org_ID=0;
	
	//search between account_no
	//private BigDecimal p_start_Account_NO= Env.ZERO; ; //account_no
	//private BigDecimal p_end_Account_NO= Env.ZERO;  ; //account_no 
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
			else if(name.equals("AccountFrom"))
			{
				p_start_Account_id = para[i].getParameterAsInt();
			}
			else if(name.equals("AccountTo"))
			{
				p_end_Account_id = para[i].getParameterAsInt();
			}
			else if(name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = para[i].getParameterAsInt();
			}
			else if(name.equals("C_BPartner_ID"))
			{
				p_C_BPartner_ID = para[i].getParameterAsInt();
			}
			else if(name.equals("HBC_Barge_ID"))
			{
				p_HBC_Barge_ID = para[i].getParameterAsInt();
			}
			else if(name.equals("HBC_Tugboat_ID"))
			{
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
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
        setAllAccount(); // to array list account_id_lists
		for(int i=0;i<account_id_lists.size();i++)
		{
			
			BigDecimal initialBalance = getinitialBalance(account_id_lists.get(i), p_DateFrom, p_C_BPartner_ID, p_HBC_Tugboat_ID, p_HBC_Barge_ID);
			
			//insert initial balance to temporary table
			createInitialBalanceLine(initialBalance, account_id_lists.get(i), account_name_lists.get(i), account_no_lists.get(i));
			//insert all request to temporary table
			createReconciledLines(initialBalance, account_id_lists.get(i));
		}  
		return null;
	}
	
	protected BigDecimal getinitialBalance(int account_ID, Timestamp dateAcct, int C_BPartner_ID, int HBC_Tugboat_ID, int HBC_Barge_ID) { 
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(account_ID);
		params.add(dateAcct);
		
		boolean debit = false;
		MElementValue ev = new MElementValue(getCtx(), account_ID, get_TrxName());
		if (ev.getAccountType().equalsIgnoreCase(MElementValue.ACCOUNTTYPE_Asset) ||
				ev.getAccountType().equalsIgnoreCase(MElementValue.ACCOUNTTYPE_Expense) ||
				ev.getAccountType().equalsIgnoreCase(MElementValue.ACCOUNTTYPE_Memo)) {
			
			debit = true;
		}
		
		StringBuilder sql = new StringBuilder();
		if (debit) {
			sql.append("SELECT COALESCE(SUM(AmtAcctDr-AmtAcctCr),0) FROM Fact_Acct fa ");
		} else {
			sql.append("SELECT COALESCE(SUM(AmtAcctCr-AmtAcctDr),0) FROM Fact_Acct fa ");
		}
		
		sql.append("WHERE fa.Account_ID = ? and fa.DateAcct < ? ");
		
		if (C_BPartner_ID > 0) {
			sql.append("AND fa.C_BPartner_ID=? ");
			params.add(C_BPartner_ID);
			
		}
		if (HBC_Tugboat_ID > 0) {
			sql.append("AND fa.UserElement1_ID=? ");
			params.add(HBC_Tugboat_ID);
			
		}
		if (HBC_Barge_ID > 0) {
			sql.append("AND fa.UserElement2_ID=? ");
			params.add(HBC_Barge_ID);
			
		}
		if (p_AD_Org_ID > 0) {
			sql.append("AND fa.AD_Org_ID=? ");
			params.add(p_AD_Org_ID);
			
		}

		BigDecimal returnValue = DB.getSQLValueBD(get_TrxName(), sql.toString(), params);
		if (returnValue==null)
			returnValue = Env.ZERO;
		
		return returnValue;
	}
	protected void setAllAccount()
	{
		//get semua account yang ada di antara account dan end.. isi ke array atau list
		//loop isi dari step 1
		
		MElementValue elementStart = new MElementValue(getCtx(), p_start_Account_id, get_TrxName());
		MElementValue elementTo = new MElementValue(getCtx(), p_end_Account_id, get_TrxName());
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C_ElementValue_ID, Name, Value FROM C_ElementValue fa "
				+ "WHERE Value = '"+elementStart.getValue()+"' ");
				//+ "WHERE cast(cev.value AS INT) BETWEEN "+elementStart.getValue()+" AND "+elementTo.getValue()+ " ORDER By cev.value ASC");
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
		sql.append("DELETE FROM T_TCSGeneralLedgerView");
		DB.executeUpdate(sql.toString(), get_TrxName());
	}
	
	protected void createInitialBalanceLine(BigDecimal initialBalance, int account_id, String account_name, String account_no){
		
		StringBuilder sb = new StringBuilder();
		//@phie add column gl_category_name HABCO 2596
		sb.append("INSERT INTO T_TCSGeneralLedgerView ")
		.append("(fact_acct_id, ad_client_id, ad_org_id, org_name, c_acctschema_id, acct_schema_name, account_id, account_no, account_name, ")
		.append("dateacct, c_period_id, period_name, postingType, amtacctdr, amtacctcr, amtsourcecr, amtsourcedr, amtacctbalance, ")
		.append("iso_code, description, AD_PInstance_ID, sequence, gl_category_name, accountfrom, accountto, DocumentNo, Line, ")
		.append("C_BPartner_ID, M_Product_ID, HBC_Tugboat_ID, HBC_Barge_ID ) VALUES(null,"
				+getAD_Client_ID()+","+p_AD_Org_ID+",'-',null,null,"+account_id+",'"+account_no+"','"+account_name+
				"','"+p_DateFrom+"',null,null,null,null,null,null,null,"+initialBalance+",null,'Beginning Balance',"+getAD_PInstance_ID()+
				",1, null, "+p_start_Account_id+","+p_end_Account_id+", null, null,");
		
		if (p_C_BPartner_ID > 0)
			sb.append(p_C_BPartner_ID);
		else sb.append("null");
		
		sb.append(", null,");
		
		if (p_HBC_Tugboat_ID > 0)
			sb.append(p_HBC_Tugboat_ID);
		else sb.append("null");
		sb.append(", ");
		
		if (p_HBC_Barge_ID > 0)
			sb.append(p_HBC_Barge_ID);
		else sb.append("null");
		
		sb.append(")"); 
		//end phie
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	protected void createReconciledLines(BigDecimal initialBalance,int account_id){
		
        StringBuffer sb = new StringBuffer("INSERT INTO T_TCSGeneralLedgerView "
				+ "(ad_client_id, ad_org_id, org_name, c_acctschema_id, acct_schema_name, account_id, account_no, account_name, dateacct, c_period_id, period_name, "
				+ "postingType, amtacctdr, amtacctcr, amtsourcecr, amtsourcedr, amtacctbalance, iso_code, description, AD_PInstance_ID, sequence, gl_category_name, "
				+ "accountfrom, accountto, DocumentNo, Line, C_BPartner_ID, M_Product_ID, HBC_Tugboat_ID, HBC_Barge_ID, Fact_Acct_ID) ");
	
        /*
        sb.append("SELECT ad_client_id, ad_org_id, org_name, c_acctschema_id, acct_schema_name, account_id, account_no, account_name, ")
        .append("dateacct, c_period_id, period_name, postingtype, amtacctdr, amtacctcr, amtsourcedr, amtsourcecr, (")
        .append(initialBalace)
        .append("+(SUM(amtacctbalance*multiplier) OVER (ORDER BY dateacct, fact_acct_id))), ")
        .append("iso_code, description, ")
        .append(getAD_PInstance_ID())
        .append(", 2, gl_category_name, ")
        .append(p_start_Account_id).append(" , ")
        .append(p_end_Account_id)
        .append(" FROM tcs_fact_daily_mv ") //@phie add column gl_category_name HABCO 2596
        .append("WHERE account_id="+account_id)
        .append(" AND (dateacct BETWEEN '"+p_DateFrom+"' AND '"+p_DateTo+"') ")
        .append("ORDER BY dateacct, fact_acct_id");
        */
        
        sb.append("SELECT fa.ad_client_id, fa.ad_org_id, ao.name, fa.c_acctschema_id, ")
        .append("cas.name, fa.account_id, cev.value, cev.name , ")
        .append("fa.dateacct, fa.c_period_id, cp.name, fa.postingtype, fa.amtacctdr, fa.amtacctcr, fa.amtsourcedr, fa.amtsourcecr, (")
        .append(initialBalance)
        .append("+(SUM((fa.amtacctdr-fa.amtacctcr)*(case when cev.accounttype IN ('A','E','M') then 1 else -1 end)) OVER (ORDER BY dateacct, fact_acct_id))), ")
        .append("cc.iso_code, fa.description, ")
        .append(getAD_PInstance_ID())
        .append(", 2, gc.name, ")
        .append(p_start_Account_id).append(" , ")
        
        .append(p_end_Account_id)
        .append(" ,fa.DocumentNo, fa.Line, C_BPartner_ID, M_Product_ID, UserElement1_ID, UserElement2_ID, fa.Fact_Acct_ID FROM fact_acct fa ")
        .append("JOIN ad_org ao ON ao.ad_org_id=fa.ad_org_id ")
        .append("JOIN c_acctschema cas ON cas.c_acctschema_id=fa.c_acctschema_id ")
        .append("JOIN c_elementvalue cev ON cev.c_elementvalue_id=fa.account_id ")
        .append("JOIN c_period cp ON cp.c_period_id=fa.c_period_id ")
        .append("JOIN c_currency cc ON fa.c_currency_id = cc.c_currency_id ")
        .append("JOIN gl_category gc ON gc.gl_category_id = fa.gl_category_id ")
        .append("WHERE account_id="+account_id)
        .append(" AND (dateacct BETWEEN '"+p_DateFrom+"' AND '"+p_DateTo+"') ");
        
        if (p_C_BPartner_ID > 0)
			sb.append("AND fa.C_BPartner_ID=").append(p_C_BPartner_ID).append(" ");
		
		if (p_HBC_Tugboat_ID > 0)
			sb.append("AND fa.UserElement1_ID=").append(p_HBC_Tugboat_ID).append(" ");
		
		if (p_HBC_Barge_ID > 0)
			sb.append("AND fa.UserElement2_ID=").append(p_HBC_Barge_ID).append(" ");
		
		if (p_AD_Org_ID > 0)
			sb.append("AND fa.AD_Org_ID=").append(p_AD_Org_ID).append(" ");
        
        sb.append("GROUP BY fa.ad_client_id, fa.ad_org_id, ao.name, fa.c_acctschema_id, fa.account_id, ")
        .append("cev.value, cev.name, fa.dateacct, fa.c_period_id, fa.postingtype, cev.accounttype, ")
        .append("cas.name, cp.name, cc.iso_code, fa.description, gc.name,fa.fact_acct_id ");
        
        if (p_C_BPartner_ID > 0)
			sb.append(", fa.c_bpartner_id ");
		
		if (p_HBC_Tugboat_ID > 0)
			sb.append(", fa.UserElement1_ID ");

		if (p_HBC_Barge_ID > 0)
			sb.append(", fa.UserElement2_ID ");

		if (p_AD_Org_ID > 0)
			sb.append(", fa.AD_Org_ID ");

        sb.append("ORDER BY dateacct, fact_acct_id");
        
        int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());

	
	}
}
