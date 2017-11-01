package org.toba.habco.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.util.Env;
import org.toba.habco.model.HBC_MOrder;
import org.toba.habco.model.X_HBC_Barge;
import org.toba.habco.model.X_HBC_Trip;
import org.toba.habco.model.X_HBC_Tugboat;

/*
 * Created By Yonk
 */

public class HBC_CalloutOrderLine extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}
		
		else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (mField.getColumnName().equals("HBC_Trip_ID")){
			return Trip(ctx, WindowNo, mTab, mField, value);
		}
		
		else if( mField.getColumnName().equals(MOrderLine.COLUMNNAME_Discount)){
			return Discount(ctx,WindowNo,mTab,mField,value);
		}
		
		else if(mField.getColumnName().equals("DiscAmt")){
			return Disc(ctx,WindowNo,mTab,mField,value);
		}
		//@phie
		//Habco 2637
		else if(mField.getColumnName().equals("M_Product_ID")){
			return product(ctx,WindowNo,mTab,mField,value);
		}
		
		/*
		else if(mField.getColumnName().equals("C_Tax_ID")){
			return tax(ctx,WindowNo,mTab,mField,value);
		}
		
		else if(mField.getColumnName().equals("PriceBBM")){
			return priceBBM(ctx,WindowNo,mTab,mField,value);
		}
		*/
		//end phie
		
		return "";
	}

	private String Disc(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		BigDecimal discAmt = (BigDecimal) value;
		BigDecimal Price = (BigDecimal) mTab.getValue("PriceList");
		
		//@Phie fix ArithmeticException divide by zero
		if (discAmt.compareTo(Env.ZERO)==0 || value == null || Price.compareTo(Env.ZERO) == 0){
			mTab.setValue("DiscAmt", Env.ZERO);
			return msg;
		}
		//end phie
		
		BigDecimal Discount = discAmt.multiply(Env.ONEHUNDRED);
		if (Price.compareTo(Env.ZERO) > 0) {
			mTab.setValue("Discount",Discount.divide(Price,2,RoundingMode.HALF_UP));
			mTab.setValue("PriceEntered", Price.subtract(discAmt));
		}
		return "";
	}

	private String Discount(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value.equals(Env.ZERO)){
			mTab.setValue("DiscAmt", Env.ZERO);
			return msg;
		}
		//@TommyAng
		BigDecimal list = (BigDecimal) mTab.getValue("PriceList");
		BigDecimal price = (BigDecimal) mTab.getValue("PriceEntered");
		//mTab.setValue("DiscAmt", (list.multiply(discount)).divide(Env.ONEHUNDRED));
		mTab.setValue("DiscAmt", list.subtract(price));
		//end @TommyAng
		return "";
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
	
	//@phie
	//Habco 2637
	private String product(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int product_ID = (Integer) value;
		MProduct product = new MProduct(ctx, product_ID, null);
		
		if(product.getProductType().equals("A"))
			mTab.setValue("isTrackAsAsset", "Y");
		else 
			mTab.setValue("isTrackAsAsset", "N");
		
		/* OPERATIONAL Div doesn't have otority to tax
		int c_order_id = (int) mTab.getValue("C_Order_ID");
		HBC_MOrder order = new HBC_MOrder(ctx, c_order_id, null);
		MPriceList pl = new MPriceList(ctx, order.getM_PriceList_ID(), null);
		if(pl.get_ValueAsBoolean("isPriceBBM"))
		{
			BigDecimal price = (BigDecimal) mTab.getValue("PriceEntered"); //get from price entered
			MTax tax = new MTax(ctx, (int) mTab.getValue("C_Tax_ID"), null);
			BigDecimal divisor = tax.getRate().add(Env.ONEHUNDRED);
			mTab.setValue("PriceBBM", price);
			mTab.setValue("PriceEntered", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
			mTab.setValue("PriceList", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
			mTab.setValue("PriceActual", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
		}
		*/
		return "";
	}
	//end phie
	
	//@phie
	private String tax(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		/*
		int c_order_id = (int) mTab.getValue("C_Order_ID");
		HBC_MOrder order = new HBC_MOrder(ctx, c_order_id, null);
		MPriceList pl = new MPriceList(ctx, order.getM_PriceList_ID(), null);
		if(pl.get_ValueAsBoolean("isPriceBBM"))
		{
			if(((BigDecimal)mTab.getValue("PriceBBM")).compareTo(Env.ZERO) > 0)
			{
				MTax tax = new MTax(ctx, (int) mTab.getValue("C_Tax_ID"), null);
				BigDecimal divisor = tax.getRate().add(Env.ONEHUNDRED);
				BigDecimal price = (BigDecimal) mTab.getValue("PriceBBM");
				mTab.setValue("PriceEntered", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));		
				mTab.setValue("PriceList", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
				mTab.setValue("PriceActual", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
			}
		}
		*/
		return "";
	}
	//end phie
	
	//@phie
	private String priceBBM(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		/*
		int c_order_id = (int) mTab.getValue("C_Order_ID");
		HBC_MOrder order = new HBC_MOrder(ctx, c_order_id, null);
		MPriceList pl = new MPriceList(ctx, order.getM_PriceList_ID(), null);
		if(pl.get_ValueAsBoolean("isPriceBBM"))
		{
			MTax tax = new MTax(ctx, (int) mTab.getValue("C_Tax_ID"), null);
			BigDecimal divisor = tax.getRate().add(Env.ONEHUNDRED);
			BigDecimal price = (BigDecimal) mTab.getValue("PriceBBM");
			mTab.setValue("PriceEntered", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));		
			mTab.setValue("PriceList", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
			mTab.setValue("PriceActual", price.multiply(Env.ONEHUNDRED).divide(divisor, 2, RoundingMode.HALF_UP));
		}
		*/
		
		return "";
	}
	//end phie
}
