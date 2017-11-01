package org.toba.habco.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBankAccount;
import org.compiere.model.MConversionRate;
import org.compiere.util.Env;

/**
 * @author TommyAng
 * Callout Payment
 */

public class HBC_CalloutPayment extends CalloutEngine implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals("C_Currency_ID")){
			return Currency(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals("C_BankAccount_ID")){
			return BankAccount(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}
	
	private String BankAccount(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		String msg="";
		if (value==null){
			return msg;
		}
		
		int C_BankAccount_ID = (int) value;
		
		MBankAccount bank = new MBankAccount(ctx, C_BankAccount_ID, null);
		mTab.setValue("C_Currency_ID", bank.getC_Currency_ID());
		Currency(ctx, windowNo, mTab, mField, bank.getC_Currency_ID());
		return msg;
	}
	
	private String Currency(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		String msg="";
		if (value.equals(Env.ZERO)){
			mTab.setValue("ConversionRate", Env.ZERO);
			return msg;
		}
		
		int C_Currency_ID = (int) mTab.getValue("C_Currency_ID");
		int C_ConversionType_ID = (int) mTab.getValue("C_ConversionType_ID");
		Timestamp DateAcct = (Timestamp) mTab.getValue("DateAcct"); 
		
		BigDecimal rate = MConversionRate.getRate(C_Currency_ID, 303, DateAcct, C_ConversionType_ID, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		
		if (rate==null)
			rate = Env.ZERO;
		
		BigDecimal PayAmt = (BigDecimal) mTab.getValue("PayAmt");
		BigDecimal TotalinIDR = rate.multiply(PayAmt);
		
		mTab.setValue("ConversionRate", rate);
		mTab.setValue("TotalinIDR", TotalinIDR);
		
		return msg;
	}
}
