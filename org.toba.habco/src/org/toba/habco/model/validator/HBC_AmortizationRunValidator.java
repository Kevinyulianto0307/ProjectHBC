package org.toba.habco.model.validator;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.I_TCS_AmortizationRun;
import org.compiere.model.MAccount;
import org.compiere.model.MTCSAmortizationLine;
import org.compiere.model.MTCSAmortizationRun;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.jfree.util.Log;
import org.osgi.service.event.Event;

public class HBC_AmortizationRunValidator {

	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MTCSAmortizationRun amorRun = (MTCSAmortizationRun) po;
		if (event.getTopic().equals(IEventTopics.DOC_AFTER_POST)){
			msg = afterPost(amorRun);
		}
		
		return msg;
	}

	private static String afterPost(MTCSAmortizationRun amorRun) {
		MTCSAmortizationLine[] lines = amorRun.getLines();
		for (MTCSAmortizationLine line : lines) {
			MAccount debitAccount = new MAccount(amorRun.getCtx(), line.getDebit_Account_ID() , amorRun.get_TrxName());
			
			if(debitAccount.getAD_Org_ID() != 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE Fact_Acct SET AD_Org_ID=? WHERE AD_Table_ID=? AND Record_ID=? AND Line_ID=? AND Account_ID=?");
				List<Object> params = new ArrayList<Object>();
				params.add(debitAccount.getAD_Org_ID());
				params.add(I_TCS_AmortizationRun.Table_ID);
				params.add(amorRun.getTCS_AmortizationRun_ID());
				params.add(line.getTCS_AmortizationLine_ID());
				params.add(debitAccount.getAccount_ID());
				
				Object[] paramArray = new Object[params.size()];
				params.toArray(paramArray);
				
				int no = DB.executeUpdateEx(sb.toString(),paramArray, amorRun.get_TrxName());
				Log.info("Updated Amortization Journal Debit #"+no);
			}
			
			MAccount creditAccount = new MAccount(amorRun.getCtx(), line.getCredit_Account_ID() , amorRun.get_TrxName());
			
			if(creditAccount.getAD_Org_ID() != 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE Fact_Acct SET AD_Org_ID=? WHERE AD_Table_ID=? AND Record_ID=?  AND Line_ID=? AND Account_ID=?");
				
				List<Object> params = new ArrayList<Object>();
				params.add(creditAccount.getAD_Org_ID());
				params.add(I_TCS_AmortizationRun.Table_ID);
				params.add(amorRun.getTCS_AmortizationRun_ID());
				params.add(line.getTCS_AmortizationLine_ID());
				params.add(creditAccount.getAccount_ID());
				
				Object[] paramArray = new Object[params.size()];
				params.toArray(paramArray);
				
				int no = DB.executeUpdateEx(sb.toString(), paramArray, amorRun.get_TrxName());
				Log.info("Updated Amortization Journal Credit #"+no);
			}
			
			
		}
		
		return "";
	}
	//@phie
}
