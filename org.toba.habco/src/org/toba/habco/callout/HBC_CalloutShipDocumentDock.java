package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class HBC_CalloutShipDocumentDock extends CalloutEngine implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals("Agent_BPartner_ID")){
			return agent(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals("M_Product_ID")){
			return product(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals("Qty")){
			return totalAmt(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals("Amt")){
			return totalAmt(ctx, WindowNo, mTab, mField, value);
		}
		
		return null;
	}
	
	private String agent(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		int agentID = (int) value;
		MBPartner bp = new MBPartner(ctx, agentID, null);
		
		mTab.setValue("C_BPartner_ID", value);
		mTab.setValue("M_PriceList_ID", bp.getPO_PriceList_ID());
		
		return "";
	}
	
	private String product(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		
		//MPriceList pl = new MPriceList(ctx, (int) mTab.getValue("M_PriceList_ID"), null);
		int M_PriceList_ID = (int) mTab.getValue("M_PriceList_ID");
		//int C_BParter_ID = (int) mTab.getValue("C_BPartner_ID");
		
		int lastActiveVersionID= new Query(ctx,MPriceListVersion.Table_Name,"M_PriceList_ID="+M_PriceList_ID+" and IsActive='Y'",null)
		.setOnlyActiveRecords(true)
		.setOrderBy("ValidFrom DESC")
		.setClient_ID()
		.firstId();
		
		String sql = "SELECT PriceList"
				+ " FROM M_ProductPrice"
				+ " where M_Product_ID = ? and M_PriceList_Version_ID = ?";
		BigDecimal listPrice =DB.getSQLValueBD(null, sql.toString(), new Object[]{value, lastActiveVersionID});
		
		mTab.setValue("PriceList", listPrice);
		mTab.setValue("Amt", listPrice);
		mTab.setValue("TotalAmt", listPrice.multiply((BigDecimal) mTab.getValue("Qty")));
		
		return "";
	}
	
	private String totalAmt(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		String msg="";
		if (value==null){
			return msg;
		}
		
		BigDecimal amt = (BigDecimal) mTab.getValue("Amt");
		BigDecimal qty = (BigDecimal) mTab.getValue("Qty");
		if(amt.compareTo(Env.ZERO)==0 || qty.compareTo(Env.ZERO)==0)
			mTab.setValue("TotalAmt", Env.ZERO);
		else
			mTab.setValue("TotalAmt", amt.multiply(qty));
		
		return "";
	}
}
