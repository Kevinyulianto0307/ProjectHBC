package org.toba.habco.cm.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MPeriod;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;

public class HBC_BankRegister extends SvrProcess{

	/**	Bank Account Parameter				*/
	private int					p_C_BankAccount_ID = 0;
	/**	Period Parameter				*/
	//private int					p_C_Period_ID = 0;
	private Timestamp			p_DateAcct_From = null;
	private Timestamp			p_DateAcct_To = null;
	/**	BPartner Parameter				*/
	private int					p_C_BPartner_ID = 0;
	/** Parameter Where Clause */
	private StringBuffer m_parameterWhere = new StringBuffer();
	/**	Start Time						*/
	private long 				m_start = System.currentTimeMillis();
	
	//  custom parameter
	//private String noVoucher = "";
	
	//private int					AD_Org_ID = 0;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	
	@Override
	protected void prepare() {
		StringBuffer sb = new StringBuffer("Record_ID=").append(getRecord_ID());
		// Parameter
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null
					&& para[i].getParameter_To() == null)
				;
			else if (name.equals("C_BankAccount_ID"))
				p_C_BankAccount_ID = ((BigDecimal) para[i].getParameter())
						.intValue();
			// else if (name.equals("C_Period_ID"))
			// p_C_Period_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DateAcct")) {
				p_DateAcct_From = (Timestamp) para[i].getParameter();
				p_DateAcct_To = (Timestamp) para[i].getParameter_To();
			} else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = ((BigDecimal) para[i].getParameter())
						.intValue();
			//else if (name.equals("NoVoucher"))
			//	noVoucher = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_parameterWhere
				.append(" fa.AD_Table_ID = (Select AD_Table_ID From AD_Table Where TableName = 'C_Payment') ");
		// m_parameterWhere.append(" And b.C_Bank_ID = " + p_C_Bank_ID);
		m_parameterWhere.append(" AND ba.C_BankAccount_ID = "
				+ p_C_BankAccount_ID);

		setDateAcct();
		sb.append(" - DateAcct ").append(p_DateAcct_From).append("-")
				.append(p_DateAcct_To);
		sb.append(" - Where=").append(m_parameterWhere);
		log.fine(sb.toString());
	}

	@Override
	protected String doIt() throws Exception {
		setDateAcct();
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO T_BankRegister2 "
				+ "(AD_PInstance_ID, TotalOpenBalance, AccountNo, BankName, paymentNo, DateAcct, Description, Balance, C_BankAccount_ID, "
				+ "AD_Client_ID, AD_Org_ID, C_Bpartner_ID, IsReconciled, partnerName, NoVoucher) ");
		
		sql.append("SELECT "+getAD_PInstance_ID()+","+"sum(balance) over (order by accountno,dateacct,paymentno) as totalbalance"+", * "
				+ "FROM ( "
				+ "SELECT bankact.AccountNo,bank.name as bank, payment.DocumentNo "				//	select from payment
				+ "as paymentno, payment.DateAcct, payment.Description, "
				+ "CASE WHEN payment.isreceipt = 'N' THEN payment.payamt*-1 else payment.payamt END AS balance, "
				+ "payment.C_BankAccount_ID, payment.AD_Client_ID, payment.ad_org_id, payment.C_BPartner_ID, "
				+ "payment.IsReconciled, partner.name, payment.NoVoucher "
				+ "FROM C_Payment payment "
				+ "INNER JOIN C_BankAccount bankact ON bankact.C_BankAccount_ID = payment.C_BankAccount_ID "
				+ "INNER JOIN C_Bank bank ON bank.C_Bank_ID = bankact.C_Bank_ID "
				+ "INNER JOIN C_BPartner partner on partner.C_BPartner_ID = payment.C_BPartner_ID "
				+ "WHERE payment.IsReconciled = 'N' AND payment.DocStatus IN('CO', 'RE') "
				+ "AND payment.C_BankAccount_ID = "+p_C_BankAccount_ID+" "
				+ "AND payment.AD_Client_ID = "+getAD_Client_ID()+" ")
				//+ "AND payment.AD_Org_ID = "+AD_Org_ID+" ")
				.append(" AND TRUNC(payment.DateAcct) BETWEEN ").append(DB.TO_DATE(p_DateAcct_From))
				.append(" AND ").append(DB.TO_DATE(p_DateAcct_To));
				if(p_C_BPartner_ID>0)
					sql.append("AND payment.C_BPartner_ID = "+p_C_BPartner_ID+" ");
				sql.append("UNION ALL "	//TAOWI-1274
				+ "SELECT bankact.AccountNo,bank.name, payment.DocumentNo, "					//	select from bankstatementline
				+ "payment.DateAcct, payment.Description, "
				+ "bankstatline.stmtamt AS balance, payment.C_BankAccount_ID, payment.AD_Client_ID, payment.ad_org_id, "
				+ "payment.C_BPartner_ID, payment.IsReconciled, payment.name, null "
				+ "FROM C_BankStatementLine bankstatline "
				+ "INNER JOIN C_BankStatement bankstat ON bankstat.C_BankStatement_ID = bankstatline.C_BankStatement_ID "
				+ "INNER JOIN C_BankAccount bankact ON bankact.C_BankAccount_ID = bankstat.C_BankAccount_ID "
				+ "INNER JOIN C_Bank bank ON bank.C_Bank_ID = bankact.C_Bank_ID "
				+ "LEFT JOIN c_payment_partner payment ON payment.C_Payment_ID = bankstatline.C_Payment_ID "
				+ "WHERE payment.IsReconciled = 'Y' AND bankstat.docstatus IN('CO', 'RE') "
				+ "AND payment.C_BankAccount_ID = "+p_C_BankAccount_ID+" "
				+ "AND payment.AD_Client_ID = "+getAD_Client_ID()+" ")
				//+ "AND payment.AD_Org_ID = "+AD_Org_ID+" ")
				.append(" AND TRUNC(payment.DateAcct) BETWEEN ").append(DB.TO_DATE(p_DateAcct_From))
				.append(" AND ").append(DB.TO_DATE(p_DateAcct_To));
				if(p_C_BPartner_ID>0)
					sql.append("AND payment.C_BPartner_ID = "+p_C_BPartner_ID+" ");
				sql.append("UNION ALL "	//TAOWI-1274
				+ "SELECT '0','.Beginning Balance', null, '"+p_DateAcct_From+"' "
				+ ", null, SUM(balance), "+p_C_BankAccount_ID+" "
				+ ", "+getAD_Client_ID()+" "
				+ ", "+Env.getAD_Org_ID(Env.getCtx())+" "
				+ ", null, null, null, null "
				+ "FROM( "
				+ "SELECT null, null, null, null, null, "										//	select beginning balance from payment
				+ "SUM(CASE WHEN payment.isreceipt = 'N' THEN payment.payamt*-1 ELSE payment.payamt END) as balance, "
				+ "null, null, null, null, null, null, null "
				+ "FROM C_Payment payment "
				+ "INNER JOIN C_BankAccount bankact ON bankact.C_BankAccount_ID = payment.C_BankAccount_ID "
				+ "INNER JOIN C_Bank bank ON bank.C_Bank_ID = bankact.C_Bank_ID "
				+ "INNER JOIN C_BPartner partner on partner.C_BPartner_ID = payment.C_BPartner_ID "
				+ "WHERE payment.IsReconciled = 'N' AND payment.DocStatus IN('CO', 'RE') "
				+ "AND payment.C_BankAccount_ID = "+p_C_BankAccount_ID+" "
				+ "AND payment.AD_Client_ID = "+getAD_Client_ID()+" "
				//+ "AND payment.AD_Org_ID = "+AD_Org_ID+" "
				+ "AND TRUNC(payment.DateAcct) < ").append(DB.TO_DATE(p_DateAcct_From));
				if(p_C_BPartner_ID>0)
					sql.append("AND payment.C_BPartner_ID = "+p_C_BPartner_ID+" ");
				sql.append("UNION "
				+ "SELECT null, null, null, null, null, "										//	select beginning balance from bankstatementline
				+ "SUM(bankstatline.stmtamt)as balance, "
				+ "null, null, null, null, null, null, null "
				+ "FROM C_BankStatementLine bankstatline "
				+ "INNER JOIN C_BankStatement bankstat ON bankstat.C_BankStatement_ID = bankstatline.C_BankStatement_ID "
				+ "INNER JOIN C_BankAccount bankact ON bankact.C_BankAccount_ID = bankstat.C_BankAccount_ID "
				+ "INNER JOIN C_Bank bank ON bank.C_Bank_ID = bankact.C_Bank_ID "
				+ "LEFT JOIN c_payment_partner payment ON payment.C_Payment_ID = bankstatline.C_Payment_ID "
				+ "WHERE payment.IsReconciled = 'Y' AND bankstat.docstatus IN('CO', 'RE') "
				+ "AND payment.C_BankAccount_ID = "+p_C_BankAccount_ID+" "
				+ "AND payment.AD_Client_ID = "+getAD_Client_ID()+" "
				//+ "AND payment.AD_Org_ID = "+AD_Org_ID+" "
				+ "AND TRUNC(payment.DateAcct) < ").append(DB.TO_DATE(p_DateAcct_From));
				if(p_C_BPartner_ID>0)
					sql.append("AND payment.C_BPartner_ID = "+p_C_BPartner_ID+" ");
				sql.append(""
				+ ") "
				+ "totalbalance ) "
				+ "bank ORDER BY bank.accountno, bank.dateacct");
		
		int no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("#" + no);
		log.finest(sql.toString());	
		
		int AD_PrintFormat_ID = DB.getSQLValue(get_TrxName(), "Select AD_PrintFormat_ID from AD_PrintFormat Where name = 'TGS_BankRegister'");
		if (AD_PrintFormat_ID > 0) {
			if (Ini.isClient())
				getProcessInfo().setTransientObject (MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false));
			else
				getProcessInfo().setSerializableObject(MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false));
		}

		log.fine((System.currentTimeMillis() - m_start) + " ms");
		return "";
	}

	private void setDateAcct()
	{
		//	Date defined
		if (p_DateAcct_From == null && p_DateAcct_To == null) {
			p_DateAcct_From = new Timestamp (System.currentTimeMillis());
			p_DateAcct_To = new Timestamp (System.currentTimeMillis());
			//return;
		}
		else if (p_DateAcct_From != null && p_DateAcct_To == null)
		{
			p_DateAcct_To = new Timestamp (System.currentTimeMillis());
			//return;
		}
		else if (p_DateAcct_From == null && p_DateAcct_To != null)
		{
			MPeriod first = MPeriod.getFirstInYear (getCtx(), p_DateAcct_To, Env.getAD_Org_ID(getCtx()));
			p_DateAcct_From = first.getStartDate();
			//return;
		}
	}	//	setDateAcct
	
}
