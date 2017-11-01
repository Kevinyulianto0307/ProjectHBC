package org.toba.habco.timesheet.process;


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;

public class HBC_ProcessCompleteTrip extends SvrProcess {

	int p_HBC_Trip_ID=0;
	@Override
	protected void prepare() {
		p_HBC_Trip_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		String msg="";
		MTrip trip = new MTrip(getCtx(),p_HBC_Trip_ID,get_TrxName());
		if (trip.get_ValueAsString("CompleteTrip").equals("C")){
			return CompleteTrip();
		}else{
			trip.set_ValueOfColumn("CompleteTrip", "C");
			trip.setProcessed(false);
			trip.saveEx();
		}
		return msg;
	}
	
	protected String CompleteTrip(){
		
		validateEndDate();
		validateTripDate(p_HBC_Trip_ID);
		
		MTrip trip = new MTrip(getCtx(),p_HBC_Trip_ID,get_TrxName());
		MPosition[] positions=trip.getPosition();
		BigDecimal totalcargo= Env.ZERO;
			for(MPosition position:positions){
				MShipActivity[] sactivitys= position.getShipActivity();
					for(MShipActivity sactivity:sactivitys){
						if(sactivity.isLastRecord()){
							position.setEndDate(sactivity.getFinishDate());
							position.saveEx();
							trip.setEndDate(position.getEndDate());
							trip.saveEx();
						}
						if((sactivity.getC_Activity().getValue().equals(sactivity.LOADING)|| sactivity.getC_Activity().getValue().equals(sactivity.ADDCARGO) ||sactivity.getC_Activity().getValue().equals(sactivity.UNLOADING)|| sactivity.getC_Activity().getValue().equals(sactivity.REDCARGO))){
									
							if(sactivity.getC_Activity().getValue().equals(sactivity.LOADING)|| sactivity.getC_Activity().getValue().equals(sactivity.ADDCARGO)){
								totalcargo=totalcargo.add(sactivity.getAmountOfCargo());
							}else if(sactivity.getC_Activity().getValue().equals(sactivity.UNLOADING)|| sactivity.getC_Activity().getValue().equals(sactivity.REDCARGO)){
								totalcargo=totalcargo.subtract(sactivity.getAmountOfCargo());
							}
							
							if(totalcargo.compareTo(Env.ZERO)>=0){
								trip.set_ValueOfColumn("TotalCargoQty", totalcargo);
								trip.saveEx();
								sactivity.setTotalCargoQty(totalcargo);
								sactivity.saveEx();
							}
						}
					}
			}
		trip.set_ValueOfColumn("CompleteTrip", "O");
		trip.setProcessed(true);
		trip.saveEx();
		return "";
	}
	
	/**
	 * validate end date
	 */
	protected void validateEndDate(){
		MTrip trip = new MTrip(getCtx(),p_HBC_Trip_ID,get_TrxName());
		for (MPosition position : trip.getPosition()) {
			
			// must delete end related with sorting activity
			deleteEndDate();
			
			MShipActivity[] shipActivitys=position.getShipActivityDesc();
			Timestamp enddate = null;
			int count = 0;
			for(MShipActivity shipActivity:shipActivitys){
				count += 1;
				if(count == 1){
					enddate=shipActivity.getStartDate();
					continue;
				}else{
					shipActivity.setFinishDate(enddate);
					shipActivity.saveEx();
					enddate=shipActivity.getStartDate();
				}
					
			}
			
		}
	}

	/**
	 * validate end date trip
	 * @param HBC_Trip_ID
	 */
	protected void validateTripDate(int HBC_Trip_ID){
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		
		Timestamp endDate = null;
		for (MPosition position : trip.getPositionDesc()) {
			if(position.getEndDate() != null){
				endDate = position.getEndDate();
			}
			break;
		}
		
		trip.setEndDate(endDate);
		trip.saveEx();
	}
	
	/**
	 * delete end date
	 */
	protected void deleteEndDate(){
		
		int HBC_Trip_ID = getRecord_ID();
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		
		for (MPosition position : trip.getPosition()) {
		
			MShipActivity[] shipActivitys=position.getShipActivityDesc();
			
			for(MShipActivity shipActivity:shipActivitys){
				//shipActivity.setFinishDate(null);
				//shipActivity.saveEx();
				
				int LastShipActivityID= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+shipActivity.getHBC_Position_ID(),get_TrxName())
						.setOnlyActiveRecords(true)
						.setOrderBy("StartDate DESC")
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
		
	}
	
}
