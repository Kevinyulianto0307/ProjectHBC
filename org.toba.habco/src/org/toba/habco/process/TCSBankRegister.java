package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MBankAccount;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author TommyAng
 * TCSBankRegister Report&View Process
 */
public class TCSBankRegister extends SvrProcess{
	
	private int p_C_BankAccount_ID = 0;										//C_BankAccount_ID	First Parameter
	private Timestamp p_DateFrom = null;									//DateFrom			Second Parameter
	private Timestamp p_DateTo = null;										//DateTo			Third Parameter

	
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null && para[i].getParameter_To() == null)
				;
			else if(name.equals("C_BankAccount_ID"))
				p_C_BankAccount_ID = para[i].getParameterAsInt();
			/*
			else if(name.equals("DateAcct"))
				p_DateFrom = para[i].getParameterAsTimestamp();
			else if(name.equals("DateTo"))
				p_DateTo = para[i].getParameterAsTimestamp();
			*/
			else if(name.equals("DateAcct")){
				p_DateFrom = para[i].getParameterAsTimestamp();
				p_DateTo = para[i].getParameter_ToAsTimestamp();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}
	
	protected String doIt() throws Exception {
		
		Timestamp p_DateSaldo = TimeUtil.addDays(p_DateFrom, -1);			//DateSaldo			Parameter for TCS_BankInitialBalance Function
		BigDecimal balance = Env.ZERO;										//Initial Balance
		
		StringBuilder sb = new StringBuilder();
		sb.append("(SELECT tcs_bankinitialbalance(date'"+p_DateSaldo+"',?))");					//Initial Balance @param p_DateSaldo
		balance = DB.getSQLValueBD(get_TrxName(), sb.toString(), p_C_BankAccount_ID);
		
		cleanTable();							//Remove Table(View Requirement)[TEMPORARY] TODO Remove This Later!
		createTitleLine();						//Reconciled Transactions Header
		createInitialBalanceLine(balance);		//Start Balance
		createReconciledLines(balance);					//Reconciled Transactions
		
		//Get Last Balance After CreateLines
		sb = new StringBuilder();
		sb.append("SELECT BALANCE FROM T_TCSBankReport WHERE AD_PInstance_ID=? and c_bankstatementline_id>0 order by dateacct desc, c_bankstatementline_id desc");
		balance = DB.getSQLValueBD(get_TrxName(), sb.toString(), getAD_PInstance_ID());
		
		createTitleLineforUnreconciled();		//UnReconciled Transactions Header
		createUnreconciledLines(balance);					//UnReconciled Transactions
		
		return "";
	}
	
	/**
	 * Create Beginning Line as Header for Reconciled Transactions
	 */
	protected void createTitleLine(){

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO T_TCSBankReport "
				+ "(AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_BankAccount_ID, C_BankStatementLine_ID, DateAcct, Description, "
				+ "AmtSourceDR, AmtSourceCR, Balance, AD_PInstance_ID, T_TCSBankReport_UU, BankAccountName, CurrencyName, Reference, Sequence, voucher, DocumentNo, BP_value, BP_name) " 
				+ "VALUES("+getAD_Client_ID()+","+Env.getAD_Org_ID(getCtx())+",null,null,null,null,null,"+p_C_BankAccount_ID+",null,'"+p_DateFrom+"','Reconciled Transactions',null,null,null,"+getAD_PInstance_ID()+",null,null,null,null,1, null, null, null, null)");
		
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	/**
	 * Create Second Line to Show Start Balance
	 */
	protected void createInitialBalanceLine(BigDecimal balance){
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO T_TCSBankReport "
				+ "(AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_BankAccount_ID, C_BankStatementLine_ID, DateAcct, Description, "
				+ "AmtSourceDR, AmtSourceCR, Balance, AD_PInstance_ID, T_TCSBankReport_UU, BankAccountName, CurrencyName, Reference, Sequence, voucher, DocumentNo, BP_value, BP_name) "
				+ "VALUES("+getAD_Client_ID()+","+Env.getAD_Org_ID(getCtx())+",null,null,null,null,null,"+p_C_BankAccount_ID+",null,'"+p_DateFrom+"','Beginning Balance',null,null,"+balance+","+getAD_PInstance_ID()+",null,null,null,null,2, null, null, null, null)");
		
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	/**
	 * Create Lines for Reconciled Transactions
	 */
	protected void createReconciledLines(BigDecimal balance){
		
		MBankAccount bankAcc = new MBankAccount(getCtx(), p_C_BankAccount_ID, get_TrxName());
		String currencyName = bankAcc.getC_Currency().getISO_Code();
		String bankAccountName = bankAcc.getName();
		StringBuffer sb = new StringBuffer("INSERT INTO T_TCSBankReport "
				+ "(AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_BankAccount_ID, C_BankStatementLine_ID, DateAcct, Description, "
				+ "AmtSourceDR, AmtSourceCR, Balance, AD_PInstance_ID, T_TCSBankReport_UU, BankAccountName, CurrencyName, Reference, Sequence, voucher, DocumentNo, BP_value, BP_name) ");
				
		sb.append("select bs.AD_Client_ID, bs.AD_Org_ID, bs.IsActive, bs.Created, bs.CreatedBy, bs.Updated, bs.UpdatedBy, bs.c_bankaccount_id, ")
		.append("bsl.c_bankstatementline_id, bsl.dateacct, bs.Description, ")
		.append("CASE ")
		.append("when bsl.stmtamt>0 ")
		.append("then bsl.stmtamt ")
		.append("else null ")
		.append("end, ")
		.append("CASE ")
		.append("when bsl.stmtamt<0 ")
		.append("then bsl.stmtamt*-1 ")
		.append("else null ")
		.append("end, ")
		.append("(").append(balance).append("+(sum(bsl.stmtamt) OVER (ORDER BY bsl.dateacct, cp.noVoucher, bsl.c_bankstatementline_id))), ")
		.append(""+getAD_PInstance_ID()+", null, '")
		.append(bankAccountName).append("', '").append(currencyName).append("', ")
		.append("COALESCE((SELECT cp.documentno || ' - ' || bp.value || ' - ' || bp.name from C_Payment cp JOIN C_BPartner bp ON cp.C_BPartner_ID=bp.C_BPartner_ID ")
		.append("WHERE cp.C_Payment_ID=bsl.C_Payment_ID), (SELECT name FROM C_Charge cc WHERE cc.C_Charge_ID=bsl.C_Charge_ID)), ")
		.append("3, cp.noVoucher, cp.documentno, bp.value, bp.name ") // add column voucher @phie // HBC 2529 Reference -> documentNo, bp_value, bp_name
		.append("from c_bankstatement bs ") 
		//.append("join c_bankaccount ba on ba.c_bankaccount_id=bs.c_bankaccount_id ") 
		.append("join c_bankstatementline bsl on bs.c_bankstatement_id=bsl.c_bankstatement_id ")
		.append("join c_payment cp on cp.c_payment_id=bsl.c_payment_id ") // add column voucher @phie
		.append("join c_bpartner bp on cp.c_bpartner_id = bp.c_bpartner_id ")
		.append("where bs.c_bankaccount_id="+p_C_BankAccount_ID) 
		.append(" and bs.docstatus IN ('CO','CL') ") 
		.append("and bsl.dateAcct between '"+p_DateFrom+"' and '"+p_DateTo+"'")
		.append(" group by bs.ad_client_id, bs.ad_org_id, bs.isactive, bs.created, bs.createdby, bs.updated, bs.updatedby, bs.c_bankaccount_id,  bsl.c_bankstatementline_id, bsl.dateacct, bs.Description, cp.noVoucher, cp.documentno, bp.value, bp.name ")
		.append("order by bsl.dateacct, cp.noVoucher, bsl.c_bankstatementline_id ");
		
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	/**
	 * Create Beginning Line as Header for UnReconciled Transactions
	 */
	protected void createTitleLineforUnreconciled(){

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO T_TCSBankReport "
				+ "(AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_BankAccount_ID, C_BankStatementLine_ID, DateAcct, Description, "
				+ "AmtSourceDR, AmtSourceCR, Balance, AD_PInstance_ID, T_TCSBankReport_UU, BankAccountName, CurrencyName, Reference, Sequence, voucher, DocumentNo, BP_value, BP_name) "
				+ "VALUES("+getAD_Client_ID()+","+Env.getAD_Org_ID(getCtx())+",null,null,null,null,null,"+p_C_BankAccount_ID+",null,'"+p_DateFrom+"','UnReconciled Transactions',null,null,null,"+getAD_PInstance_ID()+",null,null,null,null,4, null, null,null,null)");
		
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	/**
	 * Create Lines for UnReconciled Transactions
	 */
	protected void createUnreconciledLines(BigDecimal balance){
		
		MBankAccount bankAcc = new MBankAccount(getCtx(), p_C_BankAccount_ID, get_TrxName());
		String currencyName = bankAcc.getC_Currency().getISO_Code();
		String bankAccountName = bankAcc.getName();
		StringBuffer sb = new StringBuffer("INSERT INTO T_TCSBankReport "
				+ "(AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_BankAccount_ID, C_BankStatementLine_ID, DateAcct, Description, "
				+ "AmtSourceDR, AmtSourceCR, Balance, AD_PInstance_ID, T_TCSBankReport_UU, BankAccountName, CurrencyName, Reference, Sequence, voucher, DocumentNo, BP_value, BP_name) ");
				
		sb.append("select cp.AD_Client_ID, cp.AD_Org_ID, cp.IsActive, cp.Created, cp.CreatedBy, cp.Updated, cp.UpdatedBy, cp.c_bankaccount_id, ")
		.append("null, cp.dateacct, cp.Description, ")
		.append("CASE ")
		.append("when cp.payamt<0 ")
		.append("then cp.payamt*-1")
		.append("else null ")
		.append("end, ")
		.append("CASE ")
		.append("when cp.payamt>0 ")
		.append("then cp.payamt ")
		.append("else null ")
		.append("end, ")
		.append("null, ")
		.append(getAD_PInstance_ID())
		.append(", null, '")
		.append(bankAccountName).append("', '").append(currencyName).append("', ")
		.append("cp.documentno ||' - '|| bp.value || ' - ' || bp.name, ")
		.append("5, cp.noVoucher, cp.documentno, bp.value, bp.name ") // add column voucher @phie
		.append("from c_payment cp ") 
		.append("join c_bpartner bp on bp.c_bpartner_id=cp.c_bpartner_id ")
		//.append("join c_bankaccount ba on ba.c_bankaccount_id=cp.c_bankaccount_id ")
		.append("where cp.c_bankaccount_id="+p_C_BankAccount_ID) 
		.append(" and cp.docstatus IN ('CO','CL','RE') AND cp.isreconciled='N'") 
		.append(" and cp.dateacct between '"+p_DateFrom+"' and '"+p_DateTo+"'")
		.append(" and cp.payamt != 0")
		.append(" order by cp.dateacct, cp.c_payment_id ");
		
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sb.toString());
	}
	
	protected void cleanTable(){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM T_TCSBankReport");
		DB.executeUpdate(sql.toString(), get_TrxName());
	}
}
	
