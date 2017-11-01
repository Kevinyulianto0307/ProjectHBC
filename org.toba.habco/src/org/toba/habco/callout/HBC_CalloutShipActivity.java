package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MActivity;
import org.compiere.model.Query;
import org.compiere.model.X_C_Project;
import org.compiere.util.Env;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.X_HBC_ShipActivity;

/*
 * @author yonk
 */

public class HBC_CalloutShipActivity extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
			if(mField.getColumnName().equals(X_HBC_ShipActivity.COLUMNNAME_StartDate)){
				return StartDate(ctx, WindowNo, mTab, mField, value);
			}
			else if(mField.getColumnName().equals(X_HBC_ShipActivity.COLUMNNAME_C_Activity_ID)){
				return activity(ctx, WindowNo, mTab, mField, value, oldValue);
			}
			else if(mField.getColumnName().equals(X_HBC_ShipActivity.COLUMNNAME_HBC_Tugboat_ID)){
				return tugboat(ctx, WindowNo, mTab, mField, value, oldValue);
			}
			else if(mField.getColumnName().equals(X_HBC_ShipActivity.COLUMNNAME_LoadingAgent_BPartner_ID)){
				return loadingagent(ctx, WindowNo, mTab, mField, value, oldValue);
			}
			else if (mField.getColumnName().equals(X_HBC_ShipActivity.COLUMNNAME_IsLastActivity)){
				return islasactivity(ctx,WindowNo,mTab,mField,value);
			}
		
		return "";
	}
	
	
	
	private String islasactivity(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		boolean islastActivity = (boolean)value;
		if(!islastActivity){
			mTab.setValue("FinishDate", null);
		}
		return "";
	}



	private String loadingagent(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String msg="";
		if(value==null){
			int positionid=(Integer)mTab.getValue("HBC_Position_ID");
			MPosition position = new MPosition(ctx, positionid, null);
			//MTrip trip = new MTrip(ctx,position.getHBC_Trip_ID(),null);
			//X_C_Project project= new X_C_Project(ctx,trip.getC_Project_ID(),null);
				//if(trip.getPosition().length==1 && position.getShipActivity().length==0){
				/* Comment by phie, loading agent handle at after new and after change
				if(position.getShipActivity().length==0){
					if(position.get_ValueAsInt("LoadingAgent_BPartner_ID")!=0)
					mTab.setValue("LoadingAgent_BPartner_ID",position.get_ValueAsInt("LoadingAgent_BPartner_ID"));
				}else{
					MShipActivity sactivity=new MShipActivity(ctx, position.getShipActivityIDDesc(), null);
					mTab.setValue("LoadingAgent_BPartner_ID",sactivity.getLoadingAgent_BPartner_ID());
				}
				*/
			
				MShipActivity sactivity=new MShipActivity(ctx, position.getShipActivityLoadUnload(), null);
				mTab.setValue("TotalCargoQty", sactivity.getTotalCargoQty());
		}
		return msg;
	}


	private String StartDate(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			int positionid=(Integer)mTab.getValue("HBC_Position_ID");
			MPosition position = new MPosition(ctx, positionid, null);
			if (position.getShipActivity().length<=0){
				mTab.setValue("StartDate", position.getDateStart());
			}
		}
		
		return msg;
	}

	private String activity(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		String msg="";
		if (value==null){
			return msg;
		}
		int C_Activity_ID = (int) value;
		
		int OvertowingActivityID= new Query(ctx,MActivity.Table_Name,"Name='Overtowing'",null)
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.firstId();
		
		if(C_Activity_ID==OvertowingActivityID){
			MPosition position= new MPosition(ctx,(Integer)mTab.getValue("HBC_Position_ID"),null);
			mTab.setValue("HBC_Tugboat_ID", position.getHBC_Tugboat_ID());
			mTab.setValue("HBC_Barge_ID", position.getHBC_Barge_ID());
		}
		
		/*//  check if trip have activity towing or not
		int HBC_Position_ID = (Integer)mTab.getValue("HBC_Position_ID");
		MPosition position = new MPosition(ctx, HBC_Position_ID, null);
		MTrip trip = (MTrip) position.getHBC_Trip();
		
		int HBC_Tugboat_ID = 0;
		boolean isTowing = false;
		for (MPosition post : trip.getPositionDesc()) {
			HBC_Tugboat_ID = post.getHBC_Tugboat_ID();
			for (MShipActivity shipActivity : post.getShipActivityDesc()) {
				if(shipActivity.getC_Activity().getValue().equals("TOW") || shipActivity.getC_Activity().getValue().equals("OVT")){
					HBC_Tugboat_ID = shipActivity.getTugBoatPartner_ID();
					isTowing = true;
				}
				else{
					HBC_Tugboat_ID = shipActivity.getHBC_Tugboat_ID();
				}
				break;
			}
			break;
		}
		
		//  get tugboat from environment
		if(HBC_Tugboat_ID == 0){
			if(isTowing)
				HBC_Tugboat_ID = Env.getContextAsInt(Env.getCtx(), "#TEMP_Partner_Tugboat_ID");
			else
				HBC_Tugboat_ID = Env.getContextAsInt(Env.getCtx(), "#TEMP_Tugboat_ID");
		}
		if(HBC_Tugboat_ID > 0)
			mTab.setValue("HBC_Tugboat_ID", HBC_Tugboat_ID);
		//
*/		
		//@TommyAng
		int HBC_Position_ID = (Integer)mTab.getValue("HBC_Position_ID");
		MPosition position = new MPosition(ctx, HBC_Position_ID, null);
		MShipActivity[] shipActivitys=position.getShipActivity();
		int t_TugBoat_Over = position.getHBC_Tugboat_ID();
		for(MShipActivity shipActivity:shipActivitys){
			if(shipActivity.getTugBoatPartner_ID() > 0 && shipActivity.getC_Activity_ID()!=1000023 && shipActivity.getC_Activity_ID()!=1000006){
				t_TugBoat_Over = shipActivity.getTugBoatPartner_ID();
			}
		}
		mTab.setValue("HBC_Tugboat_ID", t_TugBoat_Over);
		//end @TommyAng
		
		MActivity activity = new MActivity(Env.getCtx(), C_Activity_ID, null);
		
		if(!activity.get_ValueAsBoolean("IsFuelQuota"))
			return "";
		
		BigDecimal fuelQuota = (BigDecimal) activity.get_Value("FuelPercentageQuota");
		mTab.setValue("FuelPercentageQuota", fuelQuota);
		
		return "";
	}
	
	private String tugboat (Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){

		//  set tugboat to environment
		if(value == null){
			int HBC_Position_ID = (Integer)mTab.getValue("HBC_Position_ID");
			MPosition position = new MPosition(ctx, HBC_Position_ID, null);
			MTrip trip = (MTrip) position.getHBC_Trip();
			
			for (MPosition post : trip.getPositionDesc()) {
				for (MShipActivity shipActivity : post.getShipActivityDesc()) {
					Env.setContext(Env.getCtx(), "#TEMP_Tugboat_ID", shipActivity.getHBC_Tugboat_ID());
					Env.setContext(Env.getCtx(), "#TEMP_Partner_Tugboat_ID", shipActivity.getTugBoatPartner_ID());
					break;
				}
				break;
			}
		}
		
		/*
		if (value==null){
			
			int HBC_Tugboat_ID = 0;
			int HBC_Position_ID = (Integer)mTab.getValue("HBC_Position_ID");
			MPosition position = new MPosition(ctx, HBC_Position_ID, null);
			MTrip trip = (MTrip) position.getHBC_Trip();
			
			for (MPosition post : trip.getPositionDesc()) {
				for (MShipActivity shipActivity : post.getShipActivityDesc()) {
					if(shipActivity.getC_Activity().getValue().equals("TOW"))
						HBC_Tugboat_ID = shipActivity.getTugBoatPartner_ID();
					else
						HBC_Tugboat_ID = shipActivity.getHBC_Tugboat_ID();
					break;
				}
				if(HBC_Tugboat_ID == 0)
					HBC_Tugboat_ID = post.getHBC_Tugboat_ID();
				break;
			}
			if(HBC_Tugboat_ID == 0)
				HBC_Tugboat_ID = trip.getHBC_Tugboat_ID();
			mTab.setValue("HBC_Tugboat_ID", HBC_Tugboat_ID);
			
			return "";
		}
		
		int HBC_Tugboat_ID = (int) value;
		MTugboat tugboat = new MTugboat(Env.getCtx(), HBC_Tugboat_ID, null);
		mTab.setValue("FuelConsumptionMain", tugboat.getHBC_MainEngine().getFuelConsumptionMain());
		*/
		return "";
	}
	
}
