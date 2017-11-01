package org.toba.habco.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

public class HBC_CalloutProjectTask extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals("DateStart")||mField.getColumnName().equals("DateFinish"))
			return diffdate(ctx, WindowNo, mTab, mField, value);
		return "";
	}

	private String diffdate(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		if(value==null)
			return "";
		
		Timestamp datestart=(Timestamp)mTab.getValue("DateStart");
		Timestamp datefinish=(Timestamp)mTab.getValue("DateFinish");
		
		if (datestart==null || datefinish==null)
			return"";
		
		int cal= (1000 * 60 * 60 * 24);
		BigDecimal diffdays = new BigDecimal (datefinish.getTime()-datestart.getTime()) ;
		diffdays= diffdays.divide(new BigDecimal(cal));
		
		mTab.setValue("DifferenceDays", diffdays);
				
		return "";
	}

}
