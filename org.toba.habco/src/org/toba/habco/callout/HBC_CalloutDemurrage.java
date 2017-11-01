package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MDemurrage;

/*
 * @author yonk
 */

public class HBC_CalloutDemurrage extends CalloutEngine implements IColumnCallout {
	

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MDemurrage.COLUMNNAME_IsDefault)){
			return Default(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}

	private String Default(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
			String msg="";
			if (value=="N"){
				int contractid=(Integer)mTab.getValue("HBC_Contract_ID");
				
				MContract contract = new MContract(ctx,contractid,null);
				
				if(contract.getDemurrages().length<=0){
					mTab.setValue("IsDefault", true);
				}
			}

		return msg;
	}

}
