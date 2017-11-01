package org.toba.habco.timesheet.process;

import java.sql.Timestamp;

import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;

/**
 * @author TommyAng
 * Process Refresh/Fill EndDate/FinishDate Per Trip Based On Activity
 */
public class HBC_ProcessRefreshTrip extends SvrProcess{

	int p_HBC_Trip_ID=0;
	
	@Override
	protected void prepare() {
		p_HBC_Trip_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		int lastPositionID = getLastPositionID(p_HBC_Trip_ID);
		//int veryLastShipActivityID = getLastShipActivityIDFromLastPosition(lastPositionID);
		MPosition lastPosition = new MPosition(getCtx(), lastPositionID, get_TrxName());
		//MShipActivity lastShipActivity = new MShipActivity(getCtx(), veryLastShipActivityID, get_TrxName());
		
		MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MPosition[] positions=trip.getPositionDesc();
		
		for(MPosition position:positions){
			MShipActivity lastShipActivityPerPosition = new MShipActivity(getCtx(), getLastShipActivityIDPerPosition(position.get_ID()), get_TrxName());
			deleteEndDatePerPosition(position.get_ID());
			refreshActivityPerPosition(position.get_ID());
			position.setEndDate(lastShipActivityPerPosition.getFinishDate());
			position.saveEx();
		}
		trip.setEndDate(lastPosition.getEndDate());
		trip.saveEx();
		
		return null;
	}
	
	/**
	 * @TommyAng
	 * DELETE Activity End Date Per Position (Except Last Activity)
	 */
	protected void deleteEndDatePerPosition(int currentPositionID){
		
		MPosition position = new MPosition(getCtx(),currentPositionID,get_TrxName());
		
		MShipActivity[] shipActivitys=position.getShipActivityDesc();
		
		for(MShipActivity shipActivity:shipActivitys){
			//phie
			if(shipActivity.get_ValueAsBoolean("isSecondActivity"))
				continue;
			//end phie
			
			int LastShipActivityID= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+currentPositionID,get_TrxName())
					.setOnlyActiveRecords(true)
					.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
					.setClient_ID()
					.firstId();
			
			if(shipActivity.getHBC_ShipActivity_ID()==LastShipActivityID)
				continue;
			else{
				String sql = "UPDATE "+MShipActivity.Table_Name+" SET FinishDate = NULL WHERE "+MShipActivity.COLUMNNAME_HBC_ShipActivity_ID+"=?";
				int no = DB.executeUpdate(sql, shipActivity.get_ID(), get_TrxName());
				log.info("UPDATED FinishDate Ship Activity#"+no);
			}
		}
		
	}
	
	/**
	 * @TommyAng
	 * Refresh/Fill Activity End Date Per Position (Except Last Activity)
	 */
	public String refreshActivityPerPosition(int currentPositionID){
		MPosition position = new MPosition(getCtx(),currentPositionID,get_TrxName());
		
		MShipActivity[] shipActivitys=position.getShipActivityDesc();
		Timestamp enddate = null;
		int count = 0;
		for(MShipActivity shipActivity:shipActivitys){
			if(count == 0){
				enddate=shipActivity.getStartDate();
				count++;
				continue;
			}else{
				//phie
				if(shipActivity.get_ValueAsBoolean("isSecondActivity"))
					continue;
				//end phie
				
				shipActivity.setFinishDate(enddate);
				shipActivity.saveEx();
				enddate=shipActivity.getStartDate();
			}				
		}	
		return "Refreshed";
	}
	
	/**
	 * @TommyAng
	 * Get The Last Activity ID Per Position
	 */
	protected int getLastShipActivityIDPerPosition(int currPositionID){
		
		int lastShipActivityIDFromLastPosition= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+currPositionID,get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
		.setClient_ID()
		.firstId();
		
		return lastShipActivityIDFromLastPosition;
	}
	
	/**
	 * @TommyAng
	 * Get The Last Position ID Based On TripID (Record ID)
	 */
	protected int getLastPositionID(int HBC_Trip_ID){
		
		int lastPositionID= new Query(getCtx(),MPosition.Table_Name,"HBC_Trip_ID="+HBC_Trip_ID,get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy("DateStart DESC, EndDate DESC, HBC_Position_ID DESC")
		.setClient_ID()
		.firstId();
		
		return lastPositionID;
	}
	
	/*
	protected int getLastShipActivityIDFromLastPosition(int lastPositionID){
		
		int lastShipActivityIDFromLastPosition= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+lastPositionID,get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate DESC")
		.setClient_ID()
		.firstId();
		
		return lastShipActivityIDFromLastPosition;
	}
	*/
}
