package org.toba.habco.callout;


import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;


public class HBC_CalloutTemplate extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		//String test = mField.getColumnName();
		if (mField.getColumnName().equals("ColumnName")) {
			return exampleMethod(ctx, WindowNo, mTab, mField, oldValue);
		}
		
		return null;
	}
	
	public String exampleMethod(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		//TODO: Put your logic here
		return "";
	}
	
	

}
