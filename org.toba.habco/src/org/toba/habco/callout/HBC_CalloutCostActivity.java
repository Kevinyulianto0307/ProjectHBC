package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.webui.component.Messagebox;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MShipDocumentLine;
import org.toba.habco.model.X_HBC_APProduct;
import org.toba.habco.model.X_HBC_CrewOnOff;
import org.toba.habco.model.X_HBC_LegalizedLine;
import org.toba.habco.model.X_HC_Org;

public class HBC_CalloutCostActivity extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
	
		/*
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}
		*/
		if (mField.getColumnName().equals("HBC_ShipActivity_ID")){
			return Activity(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("M_Product_Version_ID")) {
			return product(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals("HBC_LegalizedLine_ID")) {
			return endorse(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals("HBC_CrewOnOff_ID")) {
			return crew(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals("LegalizedBy")) {
			return legalizedby(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals("Qty")) {
			return Qty(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("Amount")) {
			return Amount(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("conversionrate")) {
			return conversionRate(ctx, WindowNo, mTab, mField, value);
		}
		
		return "";
	}
	
	//@KevinY
	private String conversionRate(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg = "";
		if(value == null) {
			return msg;
		}	
		BigDecimal amt = (BigDecimal) mTab.getValue("Amount");
		BigDecimal qty = (BigDecimal) mTab.getValue("Qty");
		BigDecimal rate = (BigDecimal) value;
		BigDecimal totalAmt = (amt.multiply(qty)).multiply(rate);
		
		mTab.setValue("TotalAmt", totalAmt);
		
		return msg;
	}//conversionRate
	//@KevinY end
	
	private String legalizedby(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		if(value==null){
			int shipdocumentlineid=(Integer)mTab.getValue("HBC_ShipDocumentLine_ID");
			MShipDocumentLine shipdocumentline = new MShipDocumentLine(ctx,shipdocumentlineid,null);
			mTab.setValue("LegalizedBy", shipdocumentline.getLegalizedBy());
			mTab.setValue("EffectiveDateTo", shipdocumentline.getEffectiveDateTo());
		}
		
		return "";
	}

	private String crew(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int CrewOnOffID=(Integer)value;
		X_HBC_CrewOnOff crewonoff=new X_HBC_CrewOnOff(ctx, CrewOnOffID, null);
		
		if(crewonoff.get_ValueAsInt("Agent_BPartner_ID")<=0){
			msg="Error ! Crew On/Off Doesn't have Agent";
			return msg;
		}
		X_HC_Org hcorg= new X_HC_Org(ctx, crewonoff.getHC_Org_ID(), null);
		if(hcorg.get_ValueAsInt("HBC_Tugboat_ID")<=0){
			msg="Error ! Please Fill Tugboat in HC Organization";
			return msg;
		}
		mTab.setValue("C_BPartner_ID", (Integer)crewonoff.get_Value("Agent_BPartner_ID"));
		mTab.setValue("HBC_Tugboat_ID", hcorg.get_ValueAsInt("HBC_Tugboat_ID"));
		mTab.setValue("DateTrx", crewonoff.getDateTrx());
		mTab.setValue("HBC_CrewOnOffType", crewonoff.getHBC_CrewOnOffType());
		mTab.setValue("HC_Org_ID", crewonoff.get_Value("HC_Org_ID"));
		mTab.setValue("HC_OrgTo_ID", crewonoff.get_Value("HC_OrgTo_ID"));
		mTab.setValue("PreviousJob_ID", crewonoff.get_Value("PreviousJob_ID"));
		mTab.setValue("C_Job_ID", crewonoff.get_Value("HC_Job_ID"));
		
		return msg;
	}

	private String endorse(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int EndorseDetailID=(Integer)value;
		X_HBC_LegalizedLine legalizedline= new X_HBC_LegalizedLine(ctx, EndorseDetailID, null);
		if(legalizedline.getAgent_BPartner_ID()<=0){
			msg="Error ! Endorse Detail Doesn't have Agent";
			return msg;
		}
		mTab.setValue("C_BPartner_ID", legalizedline.getAgent_BPartner_ID());
		mTab.setValue("EndorseDate", legalizedline.getEndorseDate());
		
		return msg;
	}

	//callout pricelist by yonk

	private String product(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";

		if (value==null){
			return msg;
		}
		
		int C_BPartner_ID=0;
		int M_Product_Version_ID=(Integer)value;
		if(mTab.getValue("C_BPartner_ID")!=null)
			C_BPartner_ID=(Integer)mTab.getValue("C_BPartner_ID");
//		int C_BPartner_ID=(Integer)mTab.getValue("C_BPartner_ID");
//		MBPartner bpartner=new MBPartner(ctx,C_BPartner_ID,null);
//		if(bpartner.getPO_PriceList_ID()<=0){
//			msg="Error! Please Set PriceList in Business Partner";
//			return msg;
//		}
//		boolean issotrx = true;
//		MProductPricing pp = new MProductPricing (M_Product_ID, C_BPartner_ID, Env.ONE, issotrx);
//		Timestamp date = new Timestamp(System.currentTimeMillis());
//		String sql = "SELECT plv.M_PriceList_Version_ID "
//				+ "FROM M_PriceList_Version plv "
//				+ "WHERE plv.M_PriceList_ID=? "						//	1
//				+ " AND plv.ValidFrom <= ? "
//				+ "ORDER BY plv.ValidFrom DESC";
//		int M_PriceList_Version_ID = DB.getSQLValueEx(null, sql, bpartner.getPO_PriceList_ID(), date);
//		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
//		pp.setPriceDate(date);
//		
//		if (pp.getPriceList().compareTo(Env.ZERO)==0){
//			Messagebox msgbox = new Messagebox();
//			msgbox.show("Error, This Product Doesn't Have PriceList", "Error", Messagebox.OK, Messagebox.ERROR);
//			return msg;
//		}else{
//			mTab.setValue("PriceList", pp.getPriceLimit());
//			mTab.setValue("Amount", pp.getPriceLimit());
//		}
		if(C_BPartner_ID>0){
			if(mTab.getValue("M_Product_ID")==null)
				return "Please Insert Product";
			
			int M_Product_ID = (Integer)mTab.getValue("M_Product_ID");
			
			BigDecimal price=getPriceListFromAP(C_BPartner_ID, M_Product_ID, M_Product_Version_ID, ctx);
			if(price.compareTo(Env.ZERO)>0){
				mTab.setValue("PriceList",price);
				mTab.setValue("Amount",price);
				BigDecimal qty = (BigDecimal) mTab.getValue("Qty");
				BigDecimal rate = (BigDecimal) mTab.getValue("conversionrate");
				if(rate == null)
					rate = Env.ONE;
				//@KevinY
				BigDecimal totalAmt = (price.multiply(qty)).multiply(rate);
				//@KevinY end
				mTab.setValue("TotalAmt", totalAmt);
			}
			else{
				Messagebox msgbox = new Messagebox();
				msgbox.show("Error, This Product Doesn't Have PriceList", "Error", Messagebox.OK, Messagebox.ERROR);			
			}
		}
		
		return "";
	}
//
//	private int getM_Product_Version_ID(int M_Product_ID, Properties ctx){
//		
//		String where = "M_Product_ID="+M_Product_ID;
//		int M_Product_Version_ID = new Query(ctx, X_HBC_APProduct.Table_Name, where, null)
//		.setClient_ID()
//		.setOnlyActiveRecords(true)
//		.firstId();
//		
//		return M_Product_Version_ID;
//	}
	
	private BigDecimal getPriceListFromAP(int C_BPartner_ID,int M_Product_ID,int M_ProductVersion_ID,Properties ctx){
		
		String where ="HBC_APProduct.M_Product_ID="+M_Product_ID+" AND a.Agent_BPartner_ID="
				+C_BPartner_ID+" AND HBC_APProduct.M_Product_Version_ID="+M_ProductVersion_ID;
		
		int HBC_APProduct_ID = new Query(ctx,X_HBC_APProduct.Table_Name,where,null)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.addJoinClause("JOIN HBC_APPriceList a ON a.HBC_APPriceList_ID=HBC_APProduct.HBC_APPriceList_ID")
				.firstId();
		X_HBC_APProduct approduct= new X_HBC_APProduct(ctx, HBC_APProduct_ID, null);
		
		return approduct.getPriceList();
	}
	
	private String Activity(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int shipactivityid= (Integer)value;
		MShipActivity shipactivity = new MShipActivity(ctx, shipactivityid, null);
		if(shipactivity.getLoadingAgent_BPartner_ID()<=0){
			msg="Error ! This Ship Activity Doesn't Have Agent";
			return msg;
		}
		// set if id not null
		if(shipactivity.getHBC_Tugboat_ID() > 0)
			mTab.setValue("HBC_Tugboat_ID", shipactivity.getHBC_Tugboat_ID());
		if(shipactivity.getHBC_Barge_ID() > 0)
			mTab.setValue("HBC_Barge_ID", shipactivity.getHBC_Barge_ID());
		mTab.setValue("Day", shipactivity.getDay());
		mTab.setValue("TotalCargoQty", shipactivity.getTotalCargoQty());
		mTab.setValue("C_BPartner_ID", shipactivity.getLoadingAgent_BPartner_ID());
		mTab.setValue("StartDate", shipactivity.getStartDate());
		mTab.setValue("FinishDate", shipactivity.getFinishDate());
		mTab.setValue("LoadingAgent_BPartner_ID",shipactivity.getLoadingAgent_BPartner_ID());
		mTab.setValue("Hour", shipactivity.getHour());
		
		return msg;
	}

	/*
	private String Barge(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int bargeid= (Integer) value;
		X_HBC_Barge barge = new X_HBC_Barge (Env.getCtx(),bargeid,null);
		
		mTab.setValue("HBC_Tugboat_ID", barge.getHBC_Tugboat_ID());
		
		return "";
	}//end of HABCO-1584
	
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int tugboatid= (Integer) value;
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugboatid, null);
		
		mTab.setValue("HBC_Barge_ID", tugboat.getHBC_Barge_ID());
		
		return msg;
	}
	*/
	
	/**
	 *@author TommyAng
	 *Callout TotalAmount from Quantity
	 *@param Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value
	 *@return empty String
	 */
	private String Qty(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		BigDecimal amt = (BigDecimal) mTab.getValue("Amount");
		if (amt==null)
			amt = Env.ZERO;
		
		BigDecimal qty = (BigDecimal) value;
		if (qty==null)
			qty = Env.ZERO;

		//@KevinY
		BigDecimal rate = (BigDecimal) mTab.getValue("conversionrate");
		if (rate==null)
			rate = Env.ONE;
		
		BigDecimal totalAmt = (amt.multiply(qty)).multiply(rate);
		//@KevinY end
		mTab.setValue("TotalAmt", totalAmt);
		
		return msg;
	}
	
	/**
	 *@author TommyAng
	 *Callout TotalAmount from Amount
	 *@param Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value
	 *@return empty String
	 */
	private String Amount(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		BigDecimal amt = (BigDecimal) value;
		if (amt==null)
			amt = Env.ZERO;

		BigDecimal qty = (BigDecimal) mTab.getValue("Qty");
		if (qty==null)
			qty = Env.ZERO;

		//@KevinY
		BigDecimal rate = (BigDecimal) mTab.getValue("conversionrate");
		if (rate==null)
			rate = Env.ONE;

		BigDecimal totalAmt = (amt.multiply(qty)).multiply(rate);
		//@KevinY end
		mTab.setValue("TotalAmt", totalAmt);
		
		return msg;
	}
}
