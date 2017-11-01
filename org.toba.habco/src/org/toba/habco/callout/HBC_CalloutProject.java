package org.toba.habco.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.toba.habco.model.X_HBC_Barge;
//import org.compiere.util.Env;
//import org.toba.habco.model.X_C_Project;
//import org.toba.habco.model.X_HBC_Barge;
//import org.toba.habco.model.X_HBC_Tugboat;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_CalloutProject extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
	
		if(mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		} else if (mField.getColumnName().equals("HBC_Tugboat_ID")){
//			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		} else if (mField.getColumnName().equals("Discount")){
			return discount(ctx, WindowNo, mTab, mField, value) ;
		}
		
		return "";
	}
	
//	//HABCO-1584 callout tugboat to window project by yonk
	private String Barge(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			//@phie
			mTab.setValue("AmountOfCargo", Env.ZERO);
			//end phie
			return msg;
		}
		
		if(mTab.getValue("HBC_ContractType")=="DOCK" ||
				mTab.getValue("HBC_ContractType")=="NEWS"||
				mTab.getValue("HBC_ContractType")=="STAN")
			return msg;
		
		int bargeid= (Integer) value;
		X_HBC_Barge barge = new X_HBC_Barge (Env.getCtx(),bargeid,null);
		
		mTab.setValue("HBC_Tugboat_ID", barge.getHBC_Tugboat_ID());
		//@phie
		mTab.setValue("AmountOfCargo", barge.getMinCargo()); 
		//end phie	
		return "";
	}//end of HABCO-1584
	
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		

		if(mTab.getValue("HBC_ContractType")=="DOCK"||
				mTab.getValue("HBC_ContractType")=="NEWS"||
				mTab.getValue("HBC_ContractType")=="STAN")
			return msg;
		
		int tugboatid= (Integer) value;
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugboatid, null);
		
		mTab.setValue("HBC_Barge_ID", tugboat.getHBC_Barge_ID());
		
		return "";
		
		
	}

	private String discount(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}

		BigDecimal discount = new BigDecimal (value.toString());
		BigDecimal projectBalanceAmt = new BigDecimal (mTab.getValue("ProjectBalanceAmt").toString());
		//@phie change rounding scale 0, request by hadi
		BigDecimal totalAmt = projectBalanceAmt.multiply((Env.ONEHUNDRED.subtract(discount))).divide(Env.ONEHUNDRED,0,RoundingMode.HALF_UP);
		mTab.setValue("TotalAmt", totalAmt);
		
		return "";
		
		
	}
	
}
