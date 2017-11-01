package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;

public class HBC_CalloutInventory extends CalloutEngine implements IColumnCallout {

	private String product(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null)
			return "";
		
		int M_Product_ID = (int) value;
		MProduct product = new MProduct(ctx, M_Product_ID, null);
		int M_Product_Category_ID = product.get_ValueAsInt("M_Product_Category_ID");
		MProductCategory productCategory = new MProductCategory(ctx, M_Product_Category_ID, null);
		
		mTab.setValue("C_Charge_ID", productCategory.get_Value("C_Charge_ID"));
		
		return "";
	}
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MInventoryLine.COLUMNNAME_M_Product_ID))
			return product(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return "";
	}

}
