package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAsset;

public class HBC_CalloutAssetDisposal extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {

		if (mField.getColumnName().equals("A_Asset_ID")){
			return asset(ctx, WindowNo, mTab, mField, value) ;
		}
		return "";
	}

	//@phie
	private String asset(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			mTab.setValue("HBC_Tugboat_ID", null);
			mTab.setValue("HBC_Barge_ID", null);
			return msg;
		}
		
		int A_Asset_ID = (int) value;
		MAsset asset = new MAsset(ctx, A_Asset_ID, null);
		
		if(asset.get_ValueAsInt("HBC_Tugboat_ID") > 0)
			mTab.setValue("HBC_Tugboat_Id", asset.get_ValueAsInt("HBC_Tugboat_ID"));
		if(asset.get_ValueAsInt("HBC_Barge_ID") > 0)
			mTab.setValue("HBC_Barge_ID", asset.get_ValueAsInt("HBC_Barge_ID"));
		
		return "";
	}
	//end phie

}
