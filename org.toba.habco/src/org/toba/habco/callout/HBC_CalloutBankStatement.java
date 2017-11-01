package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBankAccount;

public class HBC_CalloutBankStatement extends CalloutEngine implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("C_BankAccount_ID")){
			return bankAccount(ctx, WindowNo, mTab, mField, value);
		}
		
		return "";
	}

	private String bankAccount(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		//@phie
		String msg="";
		if (value==null)
			return msg;
		
		int C_BankAccount_ID = ((Integer)value).intValue();
		MBankAccount ba  = new MBankAccount(ctx, C_BankAccount_ID, null);
		System.out.println(C_BankAccount_ID);
		System.out.println(ba.getName());
		System.out.println(ba.get_ValueAsInt("c_doctype_id"));
		mTab.setValue("c_doctype_id", ba.get_ValueAsInt("c_doctype_id"));
		//end phie
		return "";
	}

}
