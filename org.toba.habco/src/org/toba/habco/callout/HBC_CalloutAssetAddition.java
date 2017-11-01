package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MAssetGroupAcct;

public class HBC_CalloutAssetAddition extends CalloutEngine implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("A_Asset_Group_ID")){
			return assetGroup(ctx, WindowNo, mTab, mField, value) ;
		}
		
		return "";
	}

	private String assetGroup(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int assetGroup_ID= (Integer)value;
		//MAssetGroup assetGroup = new MAssetGroup(ctx, assetGroup_ID, null);
		MAssetGroupAcct AGacct =  MAssetGroupAcct.
				forA_Asset_Group_ID(ctx, assetGroup_ID, MAssetGroupAcct.POSTINGTYPE_Actual);
		
		mTab.setValue(MAssetAddition.COLUMNNAME_UseLifeYears, AGacct.getUseLifeYears());
		
		return "";
	}

}
