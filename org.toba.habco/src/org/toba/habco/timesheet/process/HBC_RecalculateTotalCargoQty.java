package org.toba.habco.timesheet.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;

/**
 * @author Stephan
 * recalculate total cargo qty
 */
public class HBC_RecalculateTotalCargoQty extends SvrProcess{

	private final String LOADING="LOA";
	private final String UNLOADING="DIS";
	private final String ADDCARGO="CAD";
	private final String REDCARGO="CRE";
	
	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int HBC_Trip_ID = getRecord_ID();
		//@TommyAng
		/*MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		BigDecimal totalCargoQty = Env.ZERO;
		
		// get total cargo qty from each activity
		for (MPosition position : trip.getPosition()) {
			for (MShipActivity shipActivity : position.getShipActivity()) {
				if(shipActivity.getC_Activity().getValue().equals(LOADING) 
						|| shipActivity.getC_Activity().getValue().equals(ADDCARGO)){
					totalCargoQty = totalCargoQty.add(shipActivity.getAmountOfCargo());
				}
				else if(shipActivity.getC_Activity().getValue().equals(UNLOADING) 
						|| shipActivity.getC_Activity().getValue().equals(REDCARGO)){
					totalCargoQty = totalCargoQty.subtract(shipActivity.getAmountOfCargo());
				}
			}
		}
		
		// update total cargo qty each ship activity
		for (MPosition position : trip.getPosition()) {
			for (MShipActivity shipActivity : position.getShipActivity()) {
				shipActivity.setTotalCargoQty(totalCargoQty);
				shipActivity.saveEx();
			}
		}
		
		trip.setTotalCargoQty(totalCargoQty);
		trip.saveEx();*/
		
			MTrip trip = new MTrip(getCtx(),HBC_Trip_ID,get_TrxName());
			BigDecimal totalCargoQty = Env.ZERO;
		
			for (MPosition position : trip.getPosition()) {
				//System.out.println("===========================================================================");
				//System.out.println("Position: "+position.get_ID());
				for (MShipActivity shipActivity : position.getShipActivity()) {
					//System.out.println("Activity: "+shipActivity.get_ID());
					if(shipActivity.getC_Activity().getValue()!=null){
						if(shipActivity.getC_Activity().getValue().equals(LOADING) 
								|| shipActivity.getC_Activity().getValue().equals(ADDCARGO)){
							totalCargoQty = totalCargoQty.add(shipActivity.getAmountOfCargo());
							//System.out.println("Cargo tambah : "+shipActivity.getAmountOfCargo());
						}
						else if(shipActivity.getC_Activity().getValue().equals(UNLOADING) 
								|| shipActivity.getC_Activity().getValue().equals(REDCARGO)){
							totalCargoQty = totalCargoQty.subtract(shipActivity.getAmountOfCargo());
							//System.out.println("Cargo kurang : "+shipActivity.getAmountOfCargo());
						}
					}
					//@TommyAng
					String sql = "UPDATE HBC_ShipActivity SET TotalCargoQty = "+totalCargoQty+" WHERE HBC_ShipActivity_ID=?";
					int no = DB.executeUpdate(sql, shipActivity.get_ID(), get_TrxName());
					log.info("UPDATE TotalCargoQty in ShipActivity#"+no+" "+shipActivity.get_ID());
					//System.out.println("ActivityCargo: "+shipActivity.getTotalCargoQty());
				}
				position.set_ValueOfColumn("TotalCargoQty", totalCargoQty);
				position.saveEx();
				//System.out.println("PositionCargo "+position.getTotalCargoQty());
			}
			if(totalCargoQty.compareTo(Env.ZERO)>=0){
				trip.setTotalCargoQty(totalCargoQty);
				trip.saveEx();
				//System.out.println("TripCargo: "+trip.getTotalCargoQty());
			}else
				throw new AdempiereException("TotalCargoQty is Minus!");
		//end @TommyAng
		return "Recalculate Total Cargo Qty";
	}

}
