package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProject;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;

public class HBC_CalloutPosition extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MPosition.COLUMNNAME_DateStart)){
			return StartDate(ctx, WindowNo, mTab, mField, value);
		}
		
		else if(mField.getColumnName().equals(MPosition.COLUMNNAME_IsUseCoordinate)){
			return coordinate(ctx, WindowNo, mTab, mField, value);
		}
		/*
		else if(mField.getColumnName().equals(MPosition.COLUMNNAME_HBC_Tugboat_ID)){
			return tugboat(ctx, WindowNo, mTab, mField, value);
		}
		*/
		else if(mField.getColumnName().equals("HBC_PortPosition_ID")){
			return agent(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals("C_Project_ID")){
			return portPositionFromTo(ctx, WindowNo, mTab, mField, value);
		}
		
		return "";
	}
	
	private String portPositionFromTo(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		
		if (value==null){
			return msg="";
		}
		
			//position
			int C_Project_ID = (int) value;
			int From_PortPosition_ID = 0;
			int To_PortPosition_ID = 0;
			MProject project = new MProject(ctx, C_Project_ID, null);
			From_PortPosition_ID = project.get_ValueAsInt("From_PortPosition_ID");
			To_PortPosition_ID = project.get_ValueAsInt("To_PortPosition_ID");
			
			mTab.setValue("From_PortPosition_ID", From_PortPosition_ID);
			mTab.setValue("To_PortPosition_ID", To_PortPosition_ID);			
						
		return "";
	}
	
	private String agent(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		/*
		if (value==null){
			return msg="";
		}
		*/
			//position
			int C_Project_ID = (int) mTab.getValue("C_Project_ID");
			int HBC_PortPosition_ID = 0;
			if(value==null){
				int HBC_Trip_ID = (int) mTab.getValue("HBC_Trip_ID");
				MTrip trip = new MTrip(ctx, HBC_Trip_ID, null);
				HBC_PortPosition_ID = trip.getHBC_PortPosition_ID();
			}else
				HBC_PortPosition_ID = (int) value;
			
			//project
			MProject project = new MProject(ctx, C_Project_ID, null);
			int To_PortPosition_ID = project.get_ValueAsInt("To_PortPosition_ID");
			
			if(HBC_PortPosition_ID == To_PortPosition_ID)
				mTab.setValue("LoadingAgent_BPartner_ID", project.get_Value("UnLoadingAgent_BPartner_ID"));
			else
				mTab.setValue("LoadingAgent_BPartner_ID", project.get_Value("LoadingAgent_BPartner_ID"));
						
		return "";
	}
	
	private String coordinate(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		boolean iscoordinates=(boolean)value;
		if(iscoordinates){
			mTab.setValue("HBC_PortPosition_ID", null);
		}else if(!iscoordinates){
			mTab.setValue("Coordinates", "0");
		}
		return msg;
	}
	
	private String StartDate(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		
		int tripid=(Integer)mTab.getValue("HBC_Trip_ID");
		
		if (value==null){	
			
			MTrip trip = new MTrip(ctx,tripid,null);
			
			if(trip.getPosition().length<=0){
				mTab.setValue("DateStart", trip.getDateStart());
				mTab.setValue("HBC_PortPosition_ID", trip.getHBC_PortPosition_ID());
			}else{
				mTab.setValue("DateStart", trip.LastPositionEndDate());
				//@TommyAng
				mTab.setValue("TotalCargoQty", trip.LastPositionCargo());
			}
		}
		
		/*//  get tugboat from last activity or trip
		int HBC_Tugboat_ID = 0;
		MTrip trip = new MTrip(ctx, tripid, null);
		for (MPosition position : trip.getPositionDesc()) {
			for (MShipActivity shipActivity : position.getShipActivityDesc()) {
				if(shipActivity.getC_Activity().getValue().equals("TOW"))
					HBC_Tugboat_ID = shipActivity.getTugBoatPartner_ID();
				else
					HBC_Tugboat_ID = shipActivity.getHBC_Tugboat_ID();
				break;
			}
			break;
		}
		if(HBC_Tugboat_ID == 0)
			HBC_Tugboat_ID = trip.getHBC_Tugboat_ID();
		*/
		
		//@TommyAng
		MTrip trip = new MTrip(ctx, tripid, null);		
		int t_TugBoat_Over = trip.getHBC_Tugboat_ID();
		for (MPosition position : trip.getPosition()) {
			//@TommyAng temporary comment in case needed.
			//t_TugBoat_Over = position.getHBC_Tugboat_ID(); 
			MShipActivity[] shipActivitys=position.getShipActivity();
			for(MShipActivity shipActivity:shipActivitys){
				if(shipActivity.getTugBoatPartner_ID() > 0 && shipActivity.getC_Activity_ID()!=1000023 && shipActivity.getC_Activity_ID()!=1000006){
					t_TugBoat_Over = shipActivity.getTugBoatPartner_ID();
				}
			}
		}
		
		//end @TommyAng

		mTab.setValue("HBC_Tugboat_ID", t_TugBoat_Over);
		
		return msg;
	}


}
