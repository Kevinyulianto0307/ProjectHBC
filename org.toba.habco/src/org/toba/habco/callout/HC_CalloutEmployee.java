package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.util.Env;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.X_HBC_Contract;


public class HC_CalloutEmployee extends CalloutEngine implements IColumnCallout {

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
		
		int bpartnerID= (Integer)value;
		if (bpartnerID > 0) {
			MBPartner partner = new MBPartner(Env.getCtx(), bpartnerID, null);
			mTab.setValue(MEmployee.COLUMNNAME_Value, partner.getValue());
			mTab.setValue(MEmployee.COLUMNNAME_Name, partner.getName());
			mTab.setValue(MEmployee.COLUMNNAME_Name2, partner.getName2());
			
		}
		return msg;
	}
	//end of HABCO-1588

}
