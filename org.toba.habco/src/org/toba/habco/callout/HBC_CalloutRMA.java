package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;
import org.compiere.model.MRMA;

/**
 * @author TommyAng
 * Callout RMA
 */

public class HBC_CalloutRMA extends CalloutEngine implements IColumnCallout{

	private String InOut_ID(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		if(value == null)
			return "";
		
		int M_InOut_ID = (int) value;
		MInOut io = new MInOut(ctx, M_InOut_ID, null);
		
		mTab.setValue("C_BPartner_ID", io.getC_BPartner_ID());
		mTab.setValue("C_Order_ID", io.getC_Order_ID());
		
		return "";
	}
	
	
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		
		if(mField.getColumnName().equals(MRMA.COLUMNNAME_InOut_ID))
			return InOut_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return null;
	}

}
