package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.toba.habco.model.X_HBC_Contract;


public class HC_CalloutJob extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(X_HBC_Contract.COLUMNNAME_C_BPartner_ID)){
			return BPartner(ctx, WindowNo, mTab, mField, value);
		}
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
