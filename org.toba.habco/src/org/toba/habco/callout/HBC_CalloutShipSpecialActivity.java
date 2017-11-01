package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.toba.habco.model.X_HBC_ShipSpecialActivity;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_CalloutShipSpecialActivity extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (mField.getColumnName().equals(X_HBC_ShipSpecialActivity.COLUMNNAME_TugBoatPartner_ID)){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		/*	
		}else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("C_Project_ID")){
			return project(ctx, WindowNo, mTab, mField, value);
		*/
		}
		return "";
	}
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int tugBoatPartner_ID= (Integer) value;
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugBoatPartner_ID, null);
		
		mTab.setValue(X_HBC_ShipSpecialActivity.COLUMNNAME_BargePartner_ID, tugboat.getHBC_Barge_ID());
		
		return "";
		
		
	}

	
	
}
