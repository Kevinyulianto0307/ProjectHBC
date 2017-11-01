package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MOrder;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.toba.habco.model.X_HBC_Trip;

public class HBC_CalloutInvoice extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			//return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}
		
		else if (mField.getColumnName().equals("HBC_Barge_ID")){
			//return Barge(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (mField.getColumnName().equals("HBC_Trip_ID")){
			return Trip(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (mField.getColumnName().equals("IsNoPrice")){
			return noPrice(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals("C_Order_ID")){
			return poFuel(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals("C_Bpartner_ID")){
			return bpartner(ctx, WindowNo, mTab, mField, value);
		}
		
		return "";
	}
	

	private String bpartner(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		String whereClause = MUser.COLUMNNAME_C_BPartner_ID+"=?";
		int AD_User_ID = new Query(ctx, MUser.Table_Name, whereClause, null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setParameters(new Object[]{value})
		.firstId();
		
		if(AD_User_ID > 0)
			mTab.setValue("AD_User_ID", AD_User_ID);
		
		return msg;
	}


	private String Trip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int tripid= (Integer)value;
		X_HBC_Trip trip = new X_HBC_Trip(ctx,tripid,null);
		mTab.setValue("HBC_Tugboat_ID", trip.getHBC_Tugboat_ID());
		mTab.setValue("HBC_Barge_ID", trip.getHBC_Barge_ID());
		
		return msg;
	}

//	private String Barge(Properties ctx, int windowNo, GridTab mTab,
//			GridField mField, Object value) {
//		
//		String msg="";
//		if (value==null){
//			return msg;
//		}
//		
//		int bargeid= (Integer) value;
//		X_HBC_Barge barge = new X_HBC_Barge (Env.getCtx(),bargeid,null);
//		
//		mTab.setValue("HBC_Tugboat_ID", barge.getHBC_Tugboat_ID());
//		
//		return "";
//	}//end of HABCO-1584
//	
//	
//	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
//			GridField mField, Object value) {
//		
//		String msg="";
//		if (value==null){
//			return msg;
//		}
//		
//		int tugboatid= (Integer) value;
//		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugboatid, null);
//		
//		mTab.setValue("HBC_Barge_ID", tugboat.getHBC_Barge_ID());
//		
//		return "";
//		
//		
//	}

	private String noPrice(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		
		if(value == null)
			return "";
		
		boolean isNoPrice = (boolean) value;
		if(isNoPrice)
			mTab.setValue("M_PriceList_ID", null);
		
		return "";
	}
	private String poFuel(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		
		String msg="";
		if (value==null){
			return msg;
		}
		int orderid= (Integer)value;
		MOrder po = new MOrder(ctx,orderid,null);
		mTab.setValue("HBC_Tugboat_ID", po.get_Value("HBC_Tugboat_ID"));
		mTab.setValue("HBC_Barge_ID", po.get_Value("HBC_Barge_ID"));
		mTab.setValue("C_BPartner_ID", po.getC_BPartner_ID());
		mTab.setValue("C_BPartner_Location_ID", po.getC_BPartner_Location_ID());
		mTab.setValue("c_tax_id", po.getC_Tax_ID());
		mTab.setValue("c_currency_id", po.getC_Currency_ID());
		mTab.setValue("paymentrule", po.getPaymentRule());
		mTab.setValue("ad_user_id", po.getAD_User_ID());
		mTab.setValue("m_pricelist_id", po.getM_PriceList_ID());
		mTab.setValue("ad_user_id", po.getAD_User_ID());
		mTab.setValue("ad_user_id", po.getAD_User_ID());
		mTab.setValue("C_Conversiontype_id", po.getC_ConversionType_ID());
		mTab.setValue("c_paymentterm_ID", po.getC_PaymentTerm_ID());
		//@PhieAlbert
		mTab.setValue("isNoPriceList", po.get_ValueAsBoolean("isNoPriceList"));
		//end @PhieAlbert
		return msg;
	}
	
}
