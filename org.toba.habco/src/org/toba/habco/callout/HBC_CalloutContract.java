package org.toba.habco.callout;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.POWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.X_HBC_Barge;
import org.toba.habco.model.X_HBC_Contract;
import org.toba.habco.model.X_HBC_Tugboat;


public class HBC_CalloutContract extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_C_BPartner_ID)){
			return BPartner(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_Customer_BPartner_ID)){
			return BPartnerUser(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("HBC_Tugboat_ID")){
//			return Tugboat(ctx, WindowNo, mTab, mField, value) ; //@win
		}else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_HBC_ContractType)){
			return DocTypeTarget(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_DateContract)){
			return datecontract(ctx,WindowNo,mTab,mField,value);
		}

		//habco-1665 callout paymentterm by edwin handy
		else if(mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_C_BankAccount_ID)){
			if(value!=null)
				return bankAccount	(ctx, WindowNo, mTab, mField, oldValue);
			else if(oldValue!=null)
				return bankAccount(ctx, WindowNo, mTab, mField, oldValue);
		}

		//end habco-1665 callout paymentterm by edwin handy
				
		return "";
	}
	
	private String datecontract(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		Timestamp datecontract = (Timestamp)value;
		mTab.setValue("ValidFrom", datecontract);
		
		return "";
	}

	private String DocTypeTarget(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		String Contracttype=(String) value;
		int targetdoctype = new Query(ctx,MDocType.Table_Name,"TypeDoc='"+Contracttype+"'",null).firstId();
		mTab.setValue(X_HBC_Contract.COLUMNNAME_C_DocTypeTarget_ID, targetdoctype);
		return "";
	}

	//habco-1664 callout currency pada window contract by edwin handy
	private String bankAccount(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		Integer C_BankAccount_ID = (Integer)mTab.getValue("C_BankAccount_ID");
		if (C_BankAccount_ID == null || C_BankAccount_ID.intValue() == 0)
			return "";
		String sql = "SELECT ba.C_Currency_ID"
				+ " FROM C_BankAccount ba "
				+ " where ba.C_BankAccount_ID = ?";
		String currency =DB.getSQLValueStringEx(null, sql.toString(), new Object[]{C_BankAccount_ID});
		mTab.setValue("C_Currency_ID", currency);
		
		return "";
	}
	//end habco-1664 callout currency pada window contract
	
	
	private String Barge(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int bargeid= (Integer) value;
		X_HBC_Barge barge = new X_HBC_Barge (Env.getCtx(),bargeid,null);
		
		mTab.setValue("HBC_Tugboat_ID", barge.getHBC_Tugboat_ID());
		
		return "";
	}//end of HABCO-1584
	
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int tugboatid= (Integer) value;
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugboatid, null);
		
		mTab.setValue("HBC_Barge_ID", tugboat.getHBC_Barge_ID());
		
		return "";
		
		
	}

	
	
	//end of HABCO-1514
	
	
	
		//HABCO-1588 callout business partner pada window contract by yonk
		private String BPartnerUser(Properties ctx, int windowNo, GridTab mTab,
				GridField mField, Object value) {
			String msg="";
			if (value==null){
				return msg;
			}
			int customerID=(Integer)value;
			//@TommyAng
			//I_C_BPartner custBPartner = POWrapper.create(customerID, I_C_BPartner.class);
			MBPartner custBPartner = new MBPartner(ctx, customerID, null);
			//end @TommyAng
			
			int BPartnerLocationID= new Query(Env.getCtx(),MBPartnerLocation.Table_Name,"C_BPartner_ID="+customerID,null).firstId();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID="+customerID);
			int custuserid= DB.getSQLValue(null, sql.toString());
			mTab.setValue("Customer_Location_ID", BPartnerLocationID);
			mTab.setValue("Customer_User_ID", custuserid);
			mTab.setValue("Shipper_BPartner_ID", customerID);
			mTab.setValue("Receiver_BPartner_ID", customerID);
			mTab.setValue("C_PaymentTerm_ID", custBPartner.getC_PaymentTerm_ID());
			return "";
		}

	private String BPartner(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		int BPartnerID= (Integer)value;
		int BPartnerLocationID= new Query(Env.getCtx(),MBPartnerLocation.Table_Name,"C_BPartner_ID="+BPartnerID,null).firstId();
		mTab.setValue("C_BPartner_Location_ID", BPartnerLocationID);
		return "";
	}
	//end of HABCO-1588

}
