package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.toba.habco.model.MContractTerm;
import org.toba.habco.model.X_HBC_TemplateContractTerm;

/*
 * @author yonk
 */
public class HBC_CalloutContractTerm extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
			
			if (mField.getColumnName().equals(MContractTerm.COLUMNNAME_HBC_TemplateContractTerm_ID)){
				return contractterm(ctx, WindowNo, mTab, mField, value);
			}

		return "";
	}

	private String contractterm(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		
		if (value==null){
			return msg;
		}
		int ctermid= (Integer)value;
		X_HBC_TemplateContractTerm cterm = new X_HBC_TemplateContractTerm(ctx, ctermid, null);
		mTab.setValue("Description", cterm.getDescription());
		
		return msg;
	}

}
