package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.toba.habco.model.MGeneralTerm;
import org.toba.habco.model.X_HBC_TemplateGeneralTerm;

/*
 * @author yonk
 */
public class HBC_CalloutGeneralTerm extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals(MGeneralTerm.COLUMNNAME_HBC_TemplateGeneralTerm_ID)){
			return generalterm(ctx, WindowNo, mTab, mField, value);
		}
		
		return "";
	}

	private String generalterm(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int gtermid= (Integer)value;
		X_HBC_TemplateGeneralTerm gterm = new X_HBC_TemplateGeneralTerm(ctx, gtermid, null);
		mTab.setValue("Description", gterm.getDescription());
		return msg;
	}

}
