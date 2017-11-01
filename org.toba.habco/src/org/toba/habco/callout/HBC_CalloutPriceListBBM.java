package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPeriod;
import org.compiere.model.MYear;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.toba.habco.model.MPriceListBBM;

public class HBC_CalloutPriceListBBM extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("PriceEntered"))
			return price(ctx, WindowNo, mTab, mField, value);
		
		//@TommyAng
		/*else if(mField.getColumnName().equals(MPriceListBBM.COLUMNNAME_HBC_PriceList_BBM_ID))
			return previndex(ctx,WindowNo,mTab,mField,value);*/
		else if(mField.getColumnName().equals("C_Period_ID"))
			return period(ctx,WindowNo,mTab,mField,value);
		else if(mField.getColumnName().equals("IndexBBM"))
			return secondCoalIndex(ctx,WindowNo,mTab,mField,value);
		//end @TommyAng
		
		else if(mField.getColumnName().equals("SecondPrice"))
			return SecondPrice(ctx,WindowNo,mTab,mField,value);
		return "";
	}

	private String SecondPrice(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
		
		BigDecimal price=(BigDecimal)value;
		BigDecimal ppn = new BigDecimal(10);
		BigDecimal pbbkb = new BigDecimal(7.5);
		BigDecimal pph = new BigDecimal(0.3);
		BigDecimal PPNTaxAmt=(price.multiply(ppn)).divide(Env.ONEHUNDRED);
		BigDecimal PBBKBTaxAmt=(price.multiply(pbbkb).divide(Env.ONEHUNDRED));
		BigDecimal PPHTaxAmt=(price.multiply(pph).divide(Env.ONEHUNDRED));
		mTab.setValue("SecondPPN", PPNTaxAmt);
		mTab.setValue("SecondPBBKB", PBBKBTaxAmt);
		mTab.setValue("SecondPPH",PPHTaxAmt );
		mTab.setValue("SecondSubTotal", (price.add(PPNTaxAmt)).add(PBBKBTaxAmt));
		mTab.setValue("SecondGrandTotal",((price.add(PPNTaxAmt)).add(PBBKBTaxAmt)).add(PPHTaxAmt));
		return msg;
	}
	
	//Comment by @TommyAng
	/*private String previndex(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
			if(value==null){
				if (getPreviousIndexID()<=0)
					return"";
				else{
					MPriceListBBM prevplBBM = new MPriceListBBM(ctx,getPreviousIndexID(),null);
					mTab.setValue("PreviousIndexBBM", prevplBBM.getIndexBBM());
					mTab.setValue("SecondPreviousCoalIndex", prevplBBM.get_ValueAsInt("SecondCoalIndex"));
				}
			}
		return "";
	}*/
	
	//@TommyAng
	private String period(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
			
		if(value==null)
			return "";
		
		int currentPeriodID = (int) value;
		MPeriod currentPeriod = new MPeriod(ctx, currentPeriodID, null);
		
		int currentYearID = currentPeriod.getC_Year_ID();
		MYear currentYear = new MYear(ctx, currentYearID, null);
		
		int currentPeriodNo = currentPeriod.getPeriodNo();
		
		if (currentPeriodNo==1){
			
			int prevYear = Integer.parseInt(currentYear.getFiscalYear())-1;
			String prevYearS = Integer.toString(prevYear);
			
			int prevYearID = getPreviousYearID(prevYearS);
			int prevPeriodID = getPreviousPeriodID(12,prevYearID);
			
			MPriceListBBM prevplBBM = new MPriceListBBM(ctx,getPreviousPriceListID(prevPeriodID),null);
			mTab.setValue("PreviousIndexBBM", prevplBBM.getIndexBBM());
			mTab.setValue("SecondPreviousCoalIndex", prevplBBM.getSecondIndexBBM());
		}
		else{
			
			int prevPeriodNo = currentPeriodNo-1;
			int prevPeriodID = getPreviousPeriodID(prevPeriodNo,currentYearID);
			
			MPriceListBBM prevplBBM = new MPriceListBBM(ctx,getPreviousPriceListID(prevPeriodID),null);
			mTab.setValue("PreviousIndexBBM", prevplBBM.getIndexBBM());
			mTab.setValue("SecondPreviousCoalIndex", prevplBBM.getSecondIndexBBM());
		}
		
		
	return "";
	}
	
	//@TommyAng
	private String secondCoalIndex(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		mTab.setValue("SecondCoalIndex", value);
		
		return "";
	}
	
	private String price(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		BigDecimal price=(BigDecimal)value;
		BigDecimal ppn = new BigDecimal(10);
		BigDecimal pbbkb = new BigDecimal(7.5);
		BigDecimal pph = new BigDecimal(0.3);
		BigDecimal PPNTaxAmt=(price.multiply(ppn)).divide(Env.ONEHUNDRED);
		BigDecimal PBBKBTaxAmt=(price.multiply(pbbkb).divide(Env.ONEHUNDRED));
		BigDecimal PPHTaxAmt=(price.multiply(pph).divide(Env.ONEHUNDRED));
		mTab.setValue("PPNTaxAmt", PPNTaxAmt);
		mTab.setValue("PBBKBTaxAmt", PBBKBTaxAmt);
		mTab.setValue("PPHTaxAmt",PPHTaxAmt );
		mTab.setValue("SubTotal", (price.add(PPNTaxAmt)).add(PBBKBTaxAmt));
		mTab.setValue("GrandTotal",((price.add(PPNTaxAmt)).add(PBBKBTaxAmt)).add(PPHTaxAmt));
		return "";
	}
	
	/**
	 * 
	 * get previous index bbm id from antoher record by yonk
	 */
	/*public int getPreviousIndexID(){
		int indexbbm = new Query(Env.getCtx(),MPriceListBBM.Table_Name,null,null)
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.setOrderBy("HBC_PriceList_BBM_ID DESC")
		.firstId();
		return indexbbm	;	
	}*/
	
	/**
	 * @TommyAng
	 * get previous periodID
	 */
	public int getPreviousPeriodID(int periodNo, int yearID){
		int prevPeriodID = new Query(Env.getCtx(),MPeriod.Table_Name,"C_Year_ID="+yearID+" and PeriodNo="+periodNo,null)
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.firstId();
		return prevPeriodID;	
	}
	
	/**
	 * @TommyAng
	 * get previous HBC_PriceList_BBM_ID
	 */
	public int getPreviousPriceListID(int periodID){
		int priceListID = new Query(Env.getCtx(),MPriceListBBM.Table_Name,"C_Period_ID="+periodID,null)
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.firstId();
		return priceListID;	
	}
	
	/**
	 * @TommyAng
	 * get previous Year ID
	 */
	public int getPreviousYearID(String prevYear){
		int priceListID = new Query(Env.getCtx(),MYear.Table_Name,"FiscalYear='"+prevYear+"'",null)
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.firstId();
		return priceListID;	
	}
}
