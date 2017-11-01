package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.toba.habco.model.X_HBC_ProductTrx;

public class HBC_CalloutProductApproval extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {

		if (mField.getColumnName().equals(X_HBC_ProductTrx.COLUMNNAME_M_Product_ID)){
			return Product(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals("C_DocType_ID")){
			return docType(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return "";
	}

	private String Product(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
			
		String msg="";
		if (value==null){
			return msg;
		}
		
		int productid = (Integer) value;
		
		MProduct product = new MProduct(ctx,productid,null);
		
		mTab.setValue("Value", product.getValue());
		mTab.setValue("Name", product.getName());
		mTab.setValue("VersionNos", product.getVersionNo());
		mTab.setValue("Description", product.getDescription());
		mTab.setValue("UPC", product.getUPC());
		mTab.setValue("ProductType", product.getProductType());
		mTab.setValue("M_Product_Category_ID", product.getM_Product_Category_ID());
		mTab.setValue("C_TaxCategory_ID", product.getC_TaxCategory_ID());
		mTab.setValue("HBC_ProductMerk_ID", product.get_Value("HBC_ProductMerk_ID"));
		mTab.setValue("M_FreightCategory_ID", product.getM_FreightCategory_ID());
		mTab.setValue("IsPurchased", product.get_Value("IsPurchased"));
		mTab.setValue("IsSold", product.get_Value("IsSold"));
		mTab.setValue("IsPhantom", product.get_Value("IsPhantom"));
		mTab.setValue("DescriptionURL", product.getDescriptionURL());
		mTab.setValue("ImageURL", product.getImageURL());
		
		return "";
	}

	public String docType(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		String msg = "";
		
		int C_DocType_ID = (int) value;
		mTab.setValue("C_DocTypeTarget_ID", C_DocType_ID);
		
		return msg;
	}
	
}
