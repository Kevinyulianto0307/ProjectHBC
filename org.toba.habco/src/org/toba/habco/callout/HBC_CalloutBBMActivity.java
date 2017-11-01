package org.toba.habco.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MActivity;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.toba.habco.model.MTugboat;
import org.toba.habco.model.X_HBC_AuxiliaryEngine;
import org.toba.habco.model.X_HBC_BBMActivity;
import org.toba.habco.model.X_HBC_MainEngine;

public class HBC_CalloutBBMActivity extends CalloutEngine implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MTugboat.COLUMNNAME_HBC_Tugboat_ID)){
			return tugboat(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID))
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if (mField.getColumnName().equals(X_HBC_BBMActivity.COLUMNNAME_Hour))
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if (mField.getColumnName().equals(X_HBC_BBMActivity.COLUMNNAME_Day))
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if (mField.getColumnName().equals(X_HBC_BBMActivity.COLUMNNAME_Percentage))
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals(X_HBC_BBMActivity.COLUMNNAME_FuelConsumptionPerHour))				
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals("FuelPercentageQuota"))
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals("FuelConsumptionMain"))
			return totalliters(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals("DateFrom"))
			return getDay(ctx, WindowNo, mTab, mField, value);
		else if(mField.getColumnName().equals("DateTo"))
			return getDay(ctx, WindowNo, mTab, mField, value);
		return "";
		
	}
	
	
	
	// @KevinY
	/**
	 * Callout day from DateFrom and DateTo
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * Empty String if Success
	 */
	public String getDay(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		
		if(value == null)
			return "";
		
		int days = 0;
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		if(mField.getColumnName().equals("DateFrom")) {
			if(mTab.getValue("DateTo") != null){
				dateFrom = (Timestamp) value;
				dateTo   = (Timestamp) mTab.getValue("DateTo");
				days 	 = TimeUtil.getDaysBetween(dateFrom,dateTo);
			}
		}else{
			if(mTab.getValue("DateFrom") != null){
				dateTo 		= (Timestamp) value;
				dateFrom 	= (Timestamp) mTab.getValue("DateFrom");
				days 		= TimeUtil.getDaysBetween(dateFrom, dateTo);
			}
		}
		
		mTab.setValue(X_HBC_BBMActivity.COLUMNNAME_Day, new BigDecimal(days));
		
		if(mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_Day) != null && 
				mTab.getValue("FuelPercentageQuota") != null &&
				mTab.getValue("FuelConsumptionMain")!= null && 
				mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_Hour)!= null &&
				mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_FuelConsumptionPerHour)!= null) {
			int StanbyEngineActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Standby Engine'",null)
										.setClient_ID()
										.setOnlyActiveRecords(true)
										.firstId();
			
			BigDecimal Hours 		= (BigDecimal)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_Hour);
			BigDecimal Percentage 	= (BigDecimal)mTab.getValue("FuelPercentageQuota");
			BigDecimal Main 		= (BigDecimal)mTab.getValue("FuelConsumptionMain");
			BigDecimal FuelConsumptionPerHour=(BigDecimal)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_FuelConsumptionPerHour);	
			BigDecimal totalliters=Env.ZERO;
			
			int C_Activity_ID= (Integer)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID);
			
			if(C_Activity_ID==StanbyEngineActivityID) {
				BigDecimal Days = new BigDecimal(days);
				totalliters=Days.multiply(Hours).multiply(FuelConsumptionPerHour);
			}else {
				totalliters=Hours.multiply(Main).multiply(Percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			}
			
			mTab.setValue(X_HBC_BBMActivity.COLUMNNAME_TotalLiters, totalliters);
		}
		return "";
	}
	/*
	 * @KevinY end
	 */
	
	
	public String tugboat(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		
		if (mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_HBC_Tugboat_ID)==null)
			return "";
		
		int HBC_Tugboat_ID=(Integer)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_HBC_Tugboat_ID);
		
		MTugboat tugboat = new MTugboat(ctx, HBC_Tugboat_ID, null);
		
		int StanbyEngineActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Standby Engine'",null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstId();
		
		if(mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID)!=null){
			int ActivityID=(Integer)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID);
			if(ActivityID==StanbyEngineActivityID){
				X_HBC_AuxiliaryEngine auxEngine = new X_HBC_AuxiliaryEngine(ctx, tugboat.getHBC_AuxiliaryEngine_ID(), null);
				mTab.setValue(X_HBC_BBMActivity.COLUMNNAME_FuelConsumptionPerHour, auxEngine.getFuelConsumptionPerHour());
			}else{
				X_HBC_MainEngine mainEngine= new X_HBC_MainEngine(ctx, tugboat.getHBC_MainEngine_ID(), null);
				mTab.setValue(X_HBC_BBMActivity.COLUMNNAME_FuelConsumptionPerHour, mainEngine.getFuelConsumptionMain());
			}
				
		}		
		
		return "";
	}
	
	private String totalliters(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
		
		if(value==null || value==Env.ZERO)
			return msg;
		
		if(mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID)==null)
			return msg;
		
		int C_Activity_ID=(Integer)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID);
		
		int StanbyEngineActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Standby Engine'",null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstId();
		//@KevinY
		//BigDecimal Days = new BigDecimal((Integer)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_Day));
		//@KevinY end
		BigDecimal Hours = (BigDecimal)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_Hour);
		BigDecimal Percentage =(BigDecimal)mTab.getValue("FuelPercentageQuota");
		BigDecimal Main =(BigDecimal)mTab.getValue("FuelConsumptionMain");
		BigDecimal FuelConsumptionPerHour=(BigDecimal)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_FuelConsumptionPerHour);
		
		if(FuelConsumptionPerHour == null)
			msg=tugboat(ctx, windowNo, mTab, mField, value);
		
		BigDecimal totalliters=Env.ZERO;
		
		if(C_Activity_ID==StanbyEngineActivityID){
			//@KevinY
			BigDecimal Days = (BigDecimal)mTab.getValue(X_HBC_BBMActivity.COLUMNNAME_Day);
			//@KevinY end
			totalliters=Days.multiply(Hours).multiply(FuelConsumptionPerHour);
		}else{
			//Modified by TommyAng totalliters
			//totalliters=Hours.multiply(FuelConsumptionPerHour).multiply(Percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			totalliters=Hours.multiply(Main).multiply(Percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
		}
		mTab.setValue(X_HBC_BBMActivity.COLUMNNAME_TotalLiters, totalliters);
		
		return msg;
	}

}
