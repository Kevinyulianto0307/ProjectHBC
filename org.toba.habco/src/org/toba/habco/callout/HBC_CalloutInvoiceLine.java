package org.toba.habco.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderTax;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;
import org.toba.habco.model.X_HBC_Barge;
import org.toba.habco.model.X_HBC_Trip;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_CalloutInvoiceLine extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		/*
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}else
		*/
		if (mField.getColumnName().equals("HBC_Trip_ID")){
			return Trip(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("M_Product_ID")){
			return Product(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("DateFrom")){
			return CostPerMonth(ctx,WindowNo,mTab,mField,value);
		}else if(mField.getColumnName().equals("DateTo")){
			return CostPerMonth(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals(MInvoiceLine.COLUMNNAME_C_OrderLine_ID)) {
			return order(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals("DiscountARCalc")) {
			return disc(ctx, WindowNo, mTab, mField, value);
		}
		
		/* @Stephan temporary comment
		else if (mField.getColumnName().equals("PriceList")){
			return pricelist(ctx, WindowNo, mTab, mField,value);
		}*/
		
		//@phie
		else if(mField.getColumnName().equals("A_Asset_ID")){
			return Asset(ctx,WindowNo,mTab,mField,value);
		}//end phie
		return "";
	}
	
	//@phie
	/**
	 * Callout price entered from discount ar calculation
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return empty message
	 */
	private String disc(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		//comment by TommyAng
		/*
		int C_InvoiceLine_ID = (int) mTab.getValue("C_InvoiceLine_ID");				//new record have no id
		HBC_MInvoiceLine line = new HBC_MInvoiceLine(ctx, C_InvoiceLine_ID, null);
		
		if(line.getC_Invoice().isSOTrx())
		*/
		
		int C_Invoice_ID = (int) mTab.getValue("C_Invoice_ID");
		HBC_MInvoice invoice = new HBC_MInvoice(ctx, C_Invoice_ID, null);
		if(invoice.isSOTrx()){
		//end TommyAng	
			BigDecimal disc = (BigDecimal) value;
			if(disc!=null)
			{
				if(disc.compareTo(Env.ZERO) > 0)
				{
					BigDecimal priceEntered = (BigDecimal) mTab.getValue("priceList");
					mTab.setValue("priceEntered", priceEntered.multiply((Env.ONEHUNDRED.subtract(disc))).divide(Env.ONEHUNDRED,5,RoundingMode.HALF_UP));
				}
			}
		}
		
		return "";
	}
	//end phie

	//@phie
	private String Asset(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		if(value != null)
			mTab.setValue("isTrackAsAsset", true);
		else
			mTab.setValue("isTrackAsAsset", false);
		
		return "";
	}
	//end phie
	
	private String CostPerMonth(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
		
		if(mTab.getValue("DateFrom")==null || mTab.getValue("DateTo")==null)
			return msg;
		
		Timestamp datefrom = (Timestamp)mTab.getValue("DateFrom");
		Timestamp dateto =(Timestamp)mTab.getValue("DateTo");
		
		//modified by TommyAng
		//int diffmonth=dateto.getMonth()-datefrom.getMonth();
		int diffyear = TimeUtil.getYear(dateto)-TimeUtil.getYear(datefrom);
		int diffmonth = TimeUtil.getMonth(dateto)-TimeUtil.getMonth(datefrom)+(diffyear*12);
		
		BigDecimal lineAmt=(BigDecimal)mTab.getValue("LineNetAmt");
		
		if(lineAmt.compareTo(Env.ZERO)<=0)
			return msg;
		
		int invoiceID=(Integer)mTab.getValue("C_Invoice_ID");
		
		HBC_MInvoice invoice= new HBC_MInvoice(ctx, invoiceID, null);
		
		StringBuilder where = new StringBuilder();
		where.append("C_ConversionType_ID="+invoice.getC_ConversionType_ID()+" AND C_Currency_ID="+invoice.getC_Currency_ID()+
							" AND '"+invoice.getDateInvoiced()+"' >= ValidFrom AND ValidTo >='"+invoice.getDateInvoiced()+"'");
		
		MConversionRate conversionRate = new Query(ctx,MConversionRate.Table_Name,where.toString(),invoice.get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.first();
		
		mTab.setValue("CostPerMonth", lineAmt.divide(new BigDecimal(diffmonth),2,RoundingMode.HALF_UP));
		mTab.setValue("CostPerMonthIdr", lineAmt.multiply(conversionRate.getMultiplyRate()).divide(new BigDecimal(diffmonth),2,RoundingMode.HALF_UP));
		return msg;
	}

	public String pricelist(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		BigDecimal pricelist = (BigDecimal)value;
		mTab.setValue("OriginalPriceList", pricelist);
		return msg;
	}
	
	
	private String Trip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int tripid= (Integer)value;
		X_HBC_Trip trip = new X_HBC_Trip(ctx,tripid,null);
		mTab.setValue("HBC_Tugboat_ID", trip.getHBC_Tugboat_ID());
		mTab.setValue("HBC_Barge_ID", trip.getHBC_Barge_ID());
		
		return msg;
	}

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
		
		return "";
	}
	/**
	 *@author TommyAng
	 *Callout Percentage from Product
	 *@param Properties ctx, windowNo, GridTab, GridField, value
	 *@return empty message
	 */
	private String Product(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
	
		String msg="";
		if (value==null){
			return msg;
		}
		
		int productID = (Integer) value;
		MProduct product = new MProduct(ctx, productID, null);
		
		mTab.setValue("HBC_Percentage_Split_TugBoat", product.get_Value("HBC_Percentage_Split_TugBoat"));
		mTab.setValue("HBC_Percentage_Split_Barge", product.get_Value("HBC_Percentage_Split_Barge"));
		
		
		return msg;
	}
	
	private String order(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		if(value == null)
			return "";
		
		int C_OrderLine_ID = (int) value;
		MOrderLine orderLine = new MOrderLine(ctx, C_OrderLine_ID, null);
		//@TommyAng
		BigDecimal qty = orderLine.getQtyOrdered();
		BigDecimal price = orderLine.getPriceActual();
		BigDecimal lineNetAmt = qty.multiply(price);
		BigDecimal TaxAmt = Env.ZERO;
		//end @TommyAng
		mTab.setValue("M_Product_ID", orderLine.getM_Product_ID());
		mTab.setValue("C_Charge_ID", orderLine.getC_Charge_ID());
		mTab.setValue("C_UOM_ID", orderLine.getC_UOM_ID());
		mTab.setValue("QtyEntered", orderLine.getQtyEntered());
		mTab.setValue("QtyInvoiced", orderLine.getQtyOrdered());
		mTab.setValue("PriceEntered", orderLine.getPriceEntered());
		mTab.setValue("PriceActual", orderLine.getPriceActual());
		mTab.setValue("PriceList", orderLine.getPriceList());
		
		//@TommyAng
		mTab.setValue("LineNetAmt", lineNetAmt);
		//end @TommyAng
		
		mTab.setValue("Discount", orderLine.getDiscount());
		
		//@TommyAng
		int M_PriceList_ID = Env.getContextAsInt(ctx, windowNo, "M_PriceList_ID");
		int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		Integer taxID = (Integer)mTab.getValue("C_Tax_ID");
		if (taxID != null)
		{
			int C_Tax_ID = taxID.intValue();
			MTax tax = new MTax (ctx, C_Tax_ID, null);
			TaxAmt = tax.calculateTax(lineNetAmt, isTaxIncluded(windowNo), StdPrecision);
			mTab.setValue("TaxAmt", TaxAmt);
		}
		mTab.setValue("LineTotalAmt", lineNetAmt.add(TaxAmt));
		//end @TommyAng
		
		return "";
	}
	
	//@TommyAng
	private boolean isTaxIncluded (int WindowNo)
	{
		String ss = Env.getContext(Env.getCtx(), WindowNo, "IsTaxIncluded");
		//	Not Set Yet
		if (ss.length() == 0)
		{
			int M_PriceList_ID = Env.getContextAsInt(Env.getCtx(), WindowNo, "M_PriceList_ID");
			if (M_PriceList_ID == 0)
				return false;
			ss = DB.getSQLValueString(null,
				"SELECT IsTaxIncluded FROM M_PriceList WHERE M_PriceList_ID=?", 
				M_PriceList_ID);
			if (ss == null)
				ss = "N";
			Env.setContext(Env.getCtx(), WindowNo, "IsTaxIncluded", ss);
		}
		return "Y".equals(ss);
	}
	//end @TommyAng
}
