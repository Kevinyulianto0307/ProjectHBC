package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.toba.habco.model.X_HBC_MainEngine;

public class HBC_CalloutMainEngine extends CalloutEngine implements IColumnCallout {
	/*
	 * Created By Yonk
	 */

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals("HPMainEngine") || mField.getColumnName().equals(X_HBC_MainEngine.COLUMNNAME_QtyofMachineMain) ){
			return CalculateHP(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}

	private String CalculateHP (Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		BigDecimal HP=(BigDecimal)mTab.getValue("HPMainEngine");
		BigDecimal Qty=(BigDecimal)mTab.getValue("QtyofMachineMain");
		mTab.setValue("TotalHorsePower",HP.multiply(Qty));
		return "";
	}

}
