package org.toba.habco.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
/**
 * @author TommyAng
 * Mass Recalculate TotalCargoQty
 * Able to do error trace such as "FinishDate & StartDate issue on position", "Total Cargo Qty minus issue", etc.
 * NB : To allow tracing, just press ctrl+f and copy //TRACE@TommyAng then paste it on field find and left it empty on field replace then press replace all.
 */
public class HBC_RefreshTotalCargoQty extends SvrProcess {
	public final String LOADING="LOA";
	public final String UNLOADING="DIS";
	public final String ADDCARGO="CAD";
	public final String REDCARGO="CRE";
	public final String OFF_HIRED="OFH";
	public final String ON_HIRED="ONH";
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int[] p_HBC_Trip_IDs = new Query(getCtx(), MTrip.Table_Name, null, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setOrderBy("HBC_Trip_ID")
		.getIDs();
		
		for(int tripID : p_HBC_Trip_IDs){
			
			//TRACE@TommyAngif(tripID==)			//insert the last trip id that shown on your console to continue tracing before fixing issue
			//TRACE@TommyAngcontinue;
			//TRACE@TommyAngSystem.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			//TRACE@TommyAngSystem.out.println("Trip: "+tripID);
			MTrip trip = new MTrip(getCtx(),tripID,get_TrxName());
			BigDecimal totalCargoQty = Env.ZERO;
		
			for (MPosition position : trip.getPosition()) {
				//TRACE@TommyAngSystem.out.println("===========================================================================");
				//TRACE@TommyAngSystem.out.println("Position: "+position.get_ID());
				for (MShipActivity shipActivity : position.getShipActivity()) {
					//TRACE@TommyAngSystem.out.println("Activity: "+shipActivity.get_ID());
					if(shipActivity.getC_Activity().getValue()!=null){
						if(shipActivity.getC_Activity().getValue().equals(LOADING) 
								|| shipActivity.getC_Activity().getValue().equals(ADDCARGO)){
							totalCargoQty = totalCargoQty.add(shipActivity.getAmountOfCargo());
							//TRACE@TommyAngSystem.out.println("Cargo tambah : "+shipActivity.getAmountOfCargo());
						}
						else if(shipActivity.getC_Activity().getValue().equals(UNLOADING) 
								|| shipActivity.getC_Activity().getValue().equals(REDCARGO)){
							totalCargoQty = totalCargoQty.subtract(shipActivity.getAmountOfCargo());
							//TRACE@TommyAngSystem.out.println("Cargo kurang : "+shipActivity.getAmountOfCargo());
						}
					}
					//@TommyAng
					String sql = "UPDATE HBC_ShipActivity SET TotalCargoQty = "+totalCargoQty+" WHERE HBC_ShipActivity_ID=?";
					int no = DB.executeUpdate(sql, shipActivity.get_ID(), get_TrxName());
					log.info("UPDATE TotalCargoQty in ShipActivity#"+no+" "+shipActivity.get_ID());
					//TRACE@TommyAngSystem.out.println("ActivityCargo: "+shipActivity.getTotalCargoQty());
				}
				position.set_ValueOfColumn("TotalCargoQty", totalCargoQty);
				position.saveEx();
				//TRACE@TommyAngSystem.out.println("PositionCargo "+position.getTotalCargoQty());
			}
			if(totalCargoQty.compareTo(Env.ZERO)>=0){
				trip.setTotalCargoQty(totalCargoQty);
				trip.saveEx();
				//TRACE@TommyAngSystem.out.println("TripCargo: "+trip.getTotalCargoQty());
			}else
				throw new AdempiereException("TotalCargoQty is Minus!");
		}
		
		return "";
	}
	
}
