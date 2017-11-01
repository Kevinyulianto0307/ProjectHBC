package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.X_C_ProjectLine;
import org.compiere.util.Env;

public class HBC_CalloutProjectLine extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals(X_C_ProjectLine.COLUMNNAME_PlannedQty) || mField.getColumnName().equals(X_C_ProjectLine.COLUMNNAME_PlannedPrice))
			return planned(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals(X_C_ProjectLine.COLUMNNAME_CommittedQty) || mField.getColumnName().equals("CommittedPrice"))
			return commited(ctx, WindowNo, mTab, mField, oldValue);
		
		return "";
	}
	
	private String commited(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		if(value==null)
			return "";
		BigDecimal CommittedQty,CommitedPrice;
		
		CommittedQty=(BigDecimal)mTab.getValue("CommittedQty");
		CommitedPrice=(BigDecimal)mTab.getValue("CommittedPrice");
		
		if(CommittedQty==null)
			CommittedQty=Env.ZERO;
		if(CommitedPrice==null)
			CommitedPrice=Env.ZERO;
		
		mTab.setValue("CommittedAmt", CommitedPrice.multiply(CommittedQty));
		return "";
	}

	public  String planned (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		BigDecimal PlannedQty, PlannedPrice;
		int StdPrecision = Env.getContextAsInt(ctx, WindowNo, "StdPrecision");


		//	get values
		PlannedQty = (BigDecimal)mTab.getValue("PlannedQty");
		if (PlannedQty == null)
			PlannedQty = Env.ONE;
		PlannedPrice = ((BigDecimal)mTab.getValue("PlannedPrice"));
		if (PlannedPrice == null)
			PlannedPrice = Env.ZERO;
		//
		BigDecimal PlannedAmt = PlannedQty.multiply(PlannedPrice);
		if (PlannedAmt.scale() > StdPrecision)
			PlannedAmt = PlannedAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		//
		if (log.isLoggable(Level.FINE)) log.fine("PlannedQty=" + PlannedQty + " * PlannedPrice=" + PlannedPrice + " -> PlannedAmt=" + PlannedAmt + " (Precision=" + StdPrecision+ ")");
		mTab.setValue("PlannedAmt", PlannedAmt);
		return "";
	}	//	planned
}
