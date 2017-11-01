package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MTugboat;

public class HBC_CalloutBBMPlan extends CalloutEngine implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (mField.getColumnName().equals("HBC_Trip_ID")){
			return trip(ctx, WindowNo, mTab, mField, value);
		}
		//commented by @TommyAng requested by Bella
		/*
		//@KevinY habco - 2718
		else if(mField.getColumnName().equals("CurrentTrip_ID")){
			return currentTrip(ctx, WindowNo, mTab, mField, value);
		}
		*/
		//@KevinY end
		else if(mField.getColumnName().equals("CurrentTrip_ID")){
			return currentTrip(ctx, WindowNo, mTab, mField, value);
		}
		else if(mField.getColumnName().equals("HBC_Tugboat_ID")){
			return firstTrip(ctx, WindowNo, mTab, mField, value);
		}
		return null;
	}
	
	//@phie
	/**
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return null message
	 * Set Fuel Balance
	 */
	private String currentTrip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		if(value == null){
			return "";
		}
		
		int currentTrip_ID = (int) value;
		
		if (mTab.getValue("TCS_BBMPlan_ID")==null)
			return "";
		
		int tcs_bbmplan_id = (int) mTab.getValue("TCS_BBMPlan_ID");
		MTrip currentTrip = new MTrip(ctx, currentTrip_ID , null);
		
		String whereClause = "TCS_BBMPlan_ID = ? "
						+ "AND HBC_Position_ID IN ( "
						+ "SELECT HBC_Position_ID "
						+ "FROM HBC_Position WHERE HBC_Trip_ID = ?)";
		int HBC_ShipActivity_ID = new Query(ctx, MShipActivity.Table_Name,whereClause, currentTrip.get_TrxName())
									.setParameters(new Object[]{tcs_bbmplan_id, currentTrip_ID})
									.setOrderBy(MShipActivity.COLUMNNAME_FinishDate+" DESC")
									.firstId();
		
		if(HBC_ShipActivity_ID == -1){
			mTab.setValue("FuelBalanceNew", Env.ZERO);
		} else {
		MShipActivity sa = new MShipActivity(ctx, HBC_ShipActivity_ID, currentTrip.get_TrxName());
		mTab.setValue("FuelBalanceNew", sa.getFuelBalance());
		}
		
		return "";
	}
	//end phie

	private String trip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		if(value==null){
			mTab.setValue("FuelBalance", Env.ZERO);
			return "";
		}
		/*
		int tripID = (int) value;
		
		String fb = "select fuelbalance from hbc_shipactivity where hbc_position_id in (select hbc_position_id from hbc_position where hbc_trip_id = ?) and fuelbalance>0";
		BigDecimal fuelBalance = DB.getSQLValueBD(null, fb, tripID);
		*/
		int HBC_Trip_ID = (int) value;
		MTrip trip = new MTrip(ctx, HBC_Trip_ID, null);
		mTab.setValue("FuelBalance", trip.get_Value("FuelBalanceBBMPlan"));
		
		return "";
	}
	
	private String firstTrip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		if(value==null){
			mTab.setValue("FuelBalance", Env.ZERO);
			return "";
		}
		
		int HBC_Tugboat_ID = (int) value;

		MTugboat tugboat = new MTugboat(ctx, HBC_Tugboat_ID, null);
		int lastTrip_ID = tugboat.get_ValueAsInt("LastTrip_ID");
		if(lastTrip_ID>0){		
			mTab.setValue("HBC_Trip_ID", lastTrip_ID);
			//@PhieAlbert comment by Phie
			//trip(ctx, windowNo, mTab, mField, lastTrip_ID);
			
			int TCS_BBMPlan_ID = getBBMPlanBefore(ctx,HBC_Tugboat_ID);
			
			if(TCS_BBMPlan_ID == -1)
			{
				mTab.setValue("FuelBalance", Env.ZERO);
				return "";
			}
			
			MBBMPlan bbmPlan = new MBBMPlan(ctx, TCS_BBMPlan_ID, null);
			mTab.setValue("FuelBalance", bbmPlan.get_Value("CurrentBalanceActual"));
		
		}
		return "";
	}

	private int getBBMPlanBefore(Properties ctx, int HBC_Tugboat_ID) {
		String whereClause = "HBC_Tugboat_ID = ?";
		int TCS_BBMPlan_ID = new Query(ctx, MBBMPlan.Table_Name, whereClause, null)
							.setParameters(new Object[]{HBC_Tugboat_ID})
							.setOrderBy("Created DESC")					
							.firstId();
		return TCS_BBMPlan_ID;
	}
	
	
	//@KevinY habco - 2718
	/**
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * set Tug Boat of HBC_BBMPlan from Tug Boat of HBC_Trip 
	 */
	/*
	private String currentTrip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		if(value == null){
			return "";
		}
		
		int currentTrip_ID = (int) value;
		MTrip currentTrip = new MTrip(ctx, currentTrip_ID , null);
		
		if(currentTrip.getHBC_Tugboat_ID() > 0)
			mTab.setValue("HBC_Tugboat_ID", currentTrip.getHBC_Tugboat_ID());
		
		return "";
	}//currentTrip
	//@KevinY end
	*/
	
}
