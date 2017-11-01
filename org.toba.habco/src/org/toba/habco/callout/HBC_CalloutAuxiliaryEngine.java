package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

public class HBC_CalloutAuxiliaryEngine extends CalloutEngine implements IColumnCallout {
	
	/*
	 * Created By Yonk
	 */

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals("HorsePower") || mField.getColumnName().equals("QtyOfMachine") ){
			return CalculateHP(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}

	private String CalculateHP(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		BigDecimal HP=(BigDecimal)mTab.getValue("HorsePower");
		BigDecimal Qty=(BigDecimal)mTab.getValue("QtyOfMachine");
		mTab.setValue("TotalHorsePower",HP.multiply(Qty));
		return "";
	}

}
