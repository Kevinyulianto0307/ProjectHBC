package org.toba.habco.callout;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MPortDistance;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MTugboat;
import org.toba.habco.model.X_HBC_FuelTripAllocation;
import org.toba.habco.model.X_HBC_TripAllocation;
import org.toba.habco.model.X_HBC_TugboatCategory;

public class HBC_CalloutFuelTripAllocation extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
			/*
			if (mField.getColumnName().equals(X_HBC_FuelTripAllocation.COLUMNNAME_From_PortPosition_ID) || 
					mField.getColumnName().equals(X_HBC_FuelTripAllocation.COLUMNNAME_To_PortPosition_ID) || 
					mField.getColumnName().equals(X_HBC_FuelTripAllocation.COLUMNNAME_Speed))
				return distance(ctx,WindowNo,mTab,mField,value);
			else if(mField.getColumnName().equals(X_HBC_FuelTripAllocation.COLUMNNAME_HBC_Tugboat_ID))
				return tugboat(ctx,WindowNo,mTab,mField,value);
			else if(mField.getColumnName().equals("HBC_TripAllocation_ID"))
				return tripAllocation(ctx,WindowNo,mTab,mField,value);
			else if(mField.getColumnName().equals("HBC_Position_ID"))
				return dayActual(ctx,WindowNo,mTab,mField,value);
			else if(mField.getColumnName().equals("HBC_PositionTo_ID"))
				return dayActual(ctx,WindowNo,mTab,mField,value);
			else if(mField.getColumnName().equals("IsEndDate"))
				return dayActual(ctx,WindowNo,mTab,mField,value);
			*/
		if(mField.getColumnName().equals("HBC_TripAllocation_ID"))
			return tripAllocation(ctx,WindowNo,mTab,mField,value);
		else if(mField.getColumnName().equals("HBC_Position_ID"))
			return dayActual(ctx,WindowNo,mTab,mField,value);
		else if(mField.getColumnName().equals("HBC_PositionTo_ID"))
			return dayActual(ctx,WindowNo,mTab,mField,value);
		else if(mField.getColumnName().equals("IsEndDate"))
			return dayActual(ctx,WindowNo,mTab,mField,value);
		else if(mField.getColumnName().equals("DayActual"))
			return hourActual(ctx,WindowNo,mTab,mField,value);
		
		return "";
	}
	
	
	/**
	 * Callout hour from Calculating day Actual
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * Empty String if Success
	 */
	private String hourActual(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		
		if(value == null)
			return "";
		
		BigDecimal dayActual = (BigDecimal)value;
		BigDecimal hourActual = dayActual.multiply(new BigDecimal(24));
		mTab.setValue("HourActual", hourActual);
		BigDecimal hour = (BigDecimal) mTab.getValue("Hour");
		mTab.setValue("DiffShipHour", hourActual.subtract(hour));
		
		
		
		return "";
	}//tripAllocation
	
	private String tripAllocation(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
		if(value==null)
			return msg;
		
		int HBC_TripAllocation_ID=(Integer)value;
		X_HBC_TripAllocation tripAllocation= new X_HBC_TripAllocation(ctx, HBC_TripAllocation_ID, null);
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_FuelAllocation, tripAllocation.getFuelAllocation());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_Day,tripAllocation.getDay());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_Hour, tripAllocation.getHour());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_ValidFrom, tripAllocation.getValidFrom());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_ValidTo, tripAllocation.getValidTo());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_HBC_BargeCategory_ID, tripAllocation.getHBC_BargeCategory_ID());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_HBC_TugboatCategory_ID, tripAllocation.getHBC_TugboatCategory_ID());
		
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_HBC_TripType_ID, tripAllocation.getHBC_TripType_ID());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_Speed, tripAllocation.getSpeed());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_LiterAllocation, tripAllocation.getLiterAllocation());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_Distance, tripAllocation.getDistance());
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_From_PortPosition_ID, tripAllocation.getFrom_PortPosition_ID());
		//mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_To_PortPosition_ID, tripAllocation.getTo_PortPosition_ID()); //@win commented based on yanti request
		
		//@phie
		String tripType = (String) mTab.getValue(X_HBC_TripAllocation.COLUMNNAME_HBC_TripType_ID);
		if(tripType == null)
			tripType = "";
		if(tripType.equals(X_HBC_TripAllocation.HBC_TRIPTYPE_ID_Dalam))
			mTab.setValue("Standby_Hour", tripAllocation.get_Value("Standby_Hour"));
		//end phie
		return msg;
	}


	private String tugboat(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
			if(value==null){
				return msg;
			}
			int HBC_Tugboat_ID=(Integer)value;
			MTugboat tugboat = new MTugboat(ctx,HBC_Tugboat_ID,null);
			
			//@TommyAng
			//mTab.setValue("LiterAllocation", new BigDecimal(tugboat.getFuelConsumptionMain()));
			mTab.setValue("LiterAllocation", tugboat.getFuelConsumptionMain());
			
		return msg;
	}


	private String distance(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
		
		BigDecimal speed= (BigDecimal)mTab.getValue(X_HBC_FuelTripAllocation.COLUMNNAME_Speed);
		
		if(mTab.getValue(X_HBC_FuelTripAllocation.COLUMNNAME_From_PortPosition_ID)==null|| 
				mTab.getValue(X_HBC_FuelTripAllocation.COLUMNNAME_To_PortPosition_ID)==null || 
				!(speed.compareTo(Env.ZERO)>0))
			return msg;
		
		int fromPositionID=(Integer)mTab.getValue(X_HBC_FuelTripAllocation.COLUMNNAME_From_PortPosition_ID);
		int toPositionID=(Integer)mTab.getValue(X_HBC_FuelTripAllocation.COLUMNNAME_To_PortPosition_ID);
		
		BigDecimal FuelConsumption=(BigDecimal)mTab.getValue(X_HBC_FuelTripAllocation.COLUMNNAME_LiterAllocation);
		
		int distanceID = new Query(ctx, MPortDistance.Table_Name, "From_PortPosition_ID="+fromPositionID+" AND To_PortPosition_ID="+toPositionID, null)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstId();
		
		if(distanceID<0){
			msg="There is no data in Port Distance For This Distance";
			return msg;
		}
		
		MPortDistance distance = new MPortDistance(ctx, distanceID, null);
		//@TommyAng
		//int tripid=(Integer)mTab.getValue("HBC_Trip_ID");
		//MTrip trip = new MTrip(ctx,tripid,null);
		
		int bbmPlanID = (int) mTab.getValue("TCS_BBMPlan_ID");
		MBBMPlan bbm = new MBBMPlan(ctx, bbmPlanID, null);
		
		BigDecimal fuelAllocation = (distance.getDistance().divide(speed,2,RoundingMode.HALF_UP)).multiply(FuelConsumption);
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_FuelAllocation, fuelAllocation);
		mTab.setValue(X_HBC_FuelTripAllocation.COLUMNNAME_Day, (distance.getDistance().divide(speed,2,RoundingMode.HALF_UP)).divide(new BigDecimal(24) ,2,RoundingMode.HALF_UP));
		
		/* comment by @phie
		 * Change value on header tab using callout in line, will affect error Record on parent tab was changed
		bbm.setLiterAllocation(fuelAllocation);
		bbm.saveEx();
		*/
		
		//@phie
		// Temporary solve 
		// TODO ask funcitional, set LiterAllocation when trigger speed, etc or when save line
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TCS_BBMPlan SET LiterAllocation = "+fuelAllocation+ " WHERE TCS_BBMPlan_ID = "+bbmPlanID);
		DB.executeUpdate(sb.toString(), bbm.get_TrxName());
		//end phie
		
		//end @TommyAng
		return msg;
	}
	
	/**
	 * @TommyAng
	 * Set DayActual
	 * Get DateStart from PositionFrom and EndDate from PositionTo
	 * Convert to milliseconds for Calculation
	 * Convert to day(decimal) for Result
	 */
	private String dayActual(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		
		if(mTab.getValue("HBC_Position_ID")==null || mTab.getValue("HBC_PositionTo_ID")==null)
			return "";
		
		MPosition positionFrom = new MPosition(ctx, (int) mTab.getValue("HBC_Position_ID"), null);
		MPosition positionTo = new MPosition(ctx, (int) mTab.getValue("HBC_PositionTo_ID"), null);
		
		Timestamp DateStart = positionFrom.getDateStart();
		Timestamp EndDate = positionTo.getDateStart();
		
		if((boolean) mTab.getValue("IsEndDate")){
			EndDate = positionTo.getEndDate();
		}
		
		long diff = EndDate.getTime() - DateStart.getTime();
		
		if(diff<0){
			mTab.setValue("DayActual", Env.ZERO);
			return "";
		}
		
		BigDecimal diffBD = new BigDecimal(diff);
		BigDecimal dayDivider = new BigDecimal(24 * 60 * 60 * 1000);
		BigDecimal diffDay = diffBD.divide(dayDivider, 2, RoundingMode.HALF_UP);
		
		BigDecimal hourActual = diffDay.multiply(new BigDecimal(24));
		BigDecimal hour       = (BigDecimal) mTab.getValue("Hour");
		mTab.setValue("DayActual", diffDay);
		mTab.setValue("HourActual", hourActual);
		mTab.setValue("DiffShipHour", hourActual.subtract(hour));
		
		return "";
	}

}
