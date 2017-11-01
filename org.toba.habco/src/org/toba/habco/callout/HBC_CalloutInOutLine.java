package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.toba.habco.model.X_HBC_Barge;
import org.toba.habco.model.X_HBC_Trip;
import org.toba.habco.model.X_HBC_Tugboat;


/*
 * Created By Yonk
 */

public class HBC_CalloutInOutLine extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("HBC_Trip_ID")){
			return Trip(ctx, WindowNo, mTab, mField, value);
		}
		return "";
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

}
