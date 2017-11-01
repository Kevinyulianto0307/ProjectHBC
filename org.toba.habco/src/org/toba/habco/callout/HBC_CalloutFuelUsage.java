package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MActivity;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MBBMActivity;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MFuelUsage;
import org.toba.habco.model.X_FuelUsage;
import org.toba.habco.model.X_HBC_BBMActivity;

public class HBC_CalloutFuelUsage  extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		/*
		if (mField.getColumnName().equals(X_FuelUsage.COLUMNNAME_FuelEstimation)){
			return fuelestimation(ctx, WindowNo, mTab, mField, value);
		}
		*/
		if (mField.getColumnName().equals("ActualShipBalance")){
			return actualShipBalance(ctx, WindowNo, mTab, mField, value);
		}
		return null;
	}

	private String actualShipBalance(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		BigDecimal actualBalance= (BigDecimal)value;
		if (actualBalance==null)
			actualBalance=Env.ZERO;
		
		BigDecimal bbmactivity= Env.ZERO;
		
		/*
		if (actualBalance.compareTo(Env.ZERO)<=0){
			return msg;
		}
		*/
//		int fuelusageid=(Integer)mTab.getValue("FuelUsage_ID");
//		MFuelUsage fuel = new MFuelUsage(ctx, fuelusageid, null);
		//int tripid = (Integer)mTab.getValue("HBC_Trip_ID");
		int bbmid = (Integer)mTab.getValue("TCS_BBMPlan_ID");
		
		//MTrip trip = new MTrip(null, tripid, null);
		MBBMPlan bbm = new MBBMPlan(null, bbmid, null);
		
		int bunkeringActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Bunkering'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		int fuelreceiptActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Fuel Receipt'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		int fueltransferActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Fuel Transfer'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
				
		//BigDecimal fuelAdj = Env.ZERO;
		
			//for (MBBMActivity shipActivity : trip.getBBMActivity()) {
			for (MBBMActivity shipActivity : bbm.getBBMActivity()) {	
				int C_Activity_ID=shipActivity.get_ValueAsInt(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID);
				
				/* Comment By @phie
				 * bbm activity calculate all bunkering, fuel transfer in this loop, not only the last data
				 * fuel transfer : calculate liter with the real value from get_Value("Liter") (negative)
				 * 
				if(C_Activity_ID==bunkeringActivityID){
					//Commented by TommyAng
					//bbmactivity=shipActivity.getTotalLiters();
					bbmactivity=(BigDecimal) shipActivity.get_Value("Liter");
				}
				else if(C_Activity_ID==fuelreceiptActivityID){
					bbmactivity=shipActivity.getTotalLiters();
				}
				else if(C_Activity_ID==fueltransferActivityID){
					//Commented by TommyAng
					//bbmactivity=shipActivity.getTotalLiters().negate();
					bbmactivity=((BigDecimal) shipActivity.get_Value("Liter")).negate();
				}else{
					continue;
				}
				*/
				
				//@phie
				if(C_Activity_ID==bunkeringActivityID)
					bbmactivity = bbmactivity.add((BigDecimal) shipActivity.get_Value("Liter"));
				else if(C_Activity_ID==fuelreceiptActivityID)
					bbmactivity = bbmactivity.add(shipActivity.getTotalLiters());
				else if(C_Activity_ID==fueltransferActivityID)
					bbmactivity = bbmactivity.add((BigDecimal) shipActivity.get_Value("Liter"));
				else
					continue;
				//end phie
			}	
		
		int line = (int) mTab.getValue("Line");
		int lastFuelID = new Query(Env.getCtx(), X_FuelUsage.Table_Name, "TCS_BBMPlan_ID = "+bbm.get_ID(), null)
		.setOrderBy("FuelUsage_ID DESC")
		.firstId();
		
		MFuelUsage fu = new MFuelUsage(Env.getCtx(), lastFuelID, null);
		int lineNo = fu.getLine();
		
		if(line==10 && lineNo<=line){
			BigDecimal liter= new BigDecimal(0);
			
			StringBuilder sql = new StringBuilder();
			/*sql.append("SELECT Liter "
					+ " FROM  Hbc_trip "
					+ " WHERE hbc_trip_ID = ?");*/
			
			
			sql.append("SELECT Liter "
					+ " FROM  TCS_BBMPlan "
					+ " WHERE TCS_BBMPlan_ID = ?");
			
			liter = DB.getSQLValueBD(null, sql.toString(), new Object[]{mTab.getValue("TCS_BBMPlan_ID")});
			
			liter=liter.subtract(actualBalance);
			
			//liter=actualBalance.subtract(liter);
			mTab.setValue("ActualShipUsage", liter);
		}else if(line>10 && lineNo<=line){
			
			StringBuilder sql = new StringBuilder();
			BigDecimal actualShipBalance= new BigDecimal(0);
			/*sql.append("SELECT FuelEstimation "
					+ " FROM  FuelUsage "
					+ " WHERE hbc_trip_ID = ?"
					+ " order by FuelUsage_ID desc");*/
			
			sql.append("SELECT ActualShipBalance "
					+ " FROM  FuelUsage "
					+ " WHERE TCS_BBMPlan_ID = ?"
					+ " AND Line != ?"
					+ " order by FuelUsage_ID desc");
			
			actualShipBalance=DB.getSQLValueBD(null, sql.toString(), new Object[]{mTab.getValue("TCS_BBMPlan_ID"),mTab.getValue("Line")});
			
			//actualShipBalance=actualBalance.subtract(actualShipBalance);
			actualShipBalance=actualShipBalance.subtract(actualBalance);
			actualShipBalance=actualShipBalance.add(bbmactivity);
			mTab.setValue("ActualShipUsage", actualShipBalance);
		
		
		}
		/*
		else{
			
			StringBuilder sql = new StringBuilder();
			BigDecimal actualShipBalance= new BigDecimal(0);
			sql.append("SELECT FuelEstimation "
					+ " FROM  FuelUsage "
					+ " WHERE hbc_trip_ID = ?"
					+ " order by FuelUsage_ID desc");
			
			sql.append("SELECT ActualShipBalance "
					+ " FROM  FuelUsage "
					+ " WHERE TCS_BBMPlan_ID = ?"
					+ " AND Line != ?"
					+ " order by FuelUsage_ID desc");
			
			actualShipBalance=DB.getSQLValueBD(null, sql.toString(), new Object[]{mTab.getValue("TCS_BBMPlan_ID"),mTab.getValue("Line")});
			
			//actualShipBalance=actualBalance.subtract(actualShipBalance);
			actualShipBalance=actualShipBalance.subtract(actualBalance);
			actualShipBalance=actualShipBalance.add(bbmactivity);
			mTab.setValue("ActualShipUsage", actualShipBalance);
		}
		*/
		
		
		return msg;
	}
	
	//@TommyAng
	//public boolean isFirstRecord(int tripid){
	/*
	public boolean isFirstRecord(int bbmid){	
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FuelUsage_ID FROM FuelUsage WHERE HBC_Trip_ID="+tripid+
					" ORDER BY fuelusage_ID ASC");
		sql.append("SELECT count(FuelUsage_ID) FROM FuelUsage WHERE TCS_BBMPlan_ID="+bbmid+
				" ORDER BY fuelusage_ID ASC");
		int fuelusageid = DB.getSQLValue(null, sql.toString());
			
			if(fuelusageid<2){
				return true;
			}
		return false;
	}
	*/
	//end @TommyAng
	
	protected int[] getBBMActivity(int i){
		
		//@TommyAng
		//String where = "HBC_Trip_ID="+i;
		String where = "TCS_BBMPlan_ID="+i;
		int[] ids = new Query(null, X_HBC_BBMActivity.Table_Name, where,null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}
	
}
