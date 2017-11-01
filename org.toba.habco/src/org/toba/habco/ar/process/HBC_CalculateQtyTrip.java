package org.toba.habco.ar.process;

import java.math.BigDecimal;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MTrip;

public class HBC_CalculateQtyTrip extends SvrProcess{

	int p_hbc_trip_id = 0;
	/**final string activities*/
	final static protected String LOADING = "LOA";
	final static protected String DISCHARGING = "DIS";
	
	protected void prepare() {
		p_hbc_trip_id = getRecord_ID();
	}

	protected String doIt() throws Exception {
		if(p_hbc_trip_id == 0)
			return "Process aborted.. No Trip Selected..";
		
		MTrip trip = new MTrip(getCtx(), p_hbc_trip_id, get_TrxName());
		BigDecimal totalLoadingCargo = Env.ZERO;
		BigDecimal totalReturnCargo = Env.ZERO;
		BigDecimal totalDishargingCargo = Env.ZERO;
		BigDecimal totalTransferCargo = Env.ZERO;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(AmountOfCargo), 0) FROM hbc_shipactivity sa "
				+ " JOIN c_activity ca ON sa.c_activity_id = ca.c_activity_id"
				+ " WHERE hbc_position_id in ("
				+ " SELECT hbc_position_id FROM hbc_position WHERE hbc_trip_id = ?"
				+ ") and ca.value = ?");
		totalLoadingCargo = DB.getSQLValueBDEx(get_TrxName(), sb.toString(), 
				new Object[]{p_hbc_trip_id, LOADING});
		
		sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(CargoReturn), 0) FROM hbc_shipactivity sa "
				+ " JOIN c_activity ca ON sa.c_activity_id = ca.c_activity_id"
				+ " WHERE hbc_position_id in ("
				+ " SELECT hbc_position_id FROM hbc_position WHERE hbc_trip_id = ?"
				+ ") and ca.value = ?");
		totalReturnCargo = DB.getSQLValueBDEx(get_TrxName(), sb.toString(), 
				new Object[]{p_hbc_trip_id, DISCHARGING});
		
		sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(AmountOfCargo), 0) FROM hbc_shipactivity sa "
				+ " JOIN c_activity ca ON sa.c_activity_id = ca.c_activity_id"
				+ " WHERE hbc_position_id in ("
				+ " SELECT hbc_position_id FROM hbc_position WHERE hbc_trip_id = ?"
				+ ") and ca.value = ?");
		totalDishargingCargo = DB.getSQLValueBDEx(get_TrxName(), sb.toString(), 
				new Object[]{p_hbc_trip_id, DISCHARGING});
		
		//@KevinY
		sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(sa.totalTransferCargo), 0) FROM HBC_ShipActivity sa "
				+ "JOIN C_Activity ca ON sa.C_Activity_ID = ca.C_Activity_ID "
				+ "WHERE HBC_Position_ID IN ( "
				+ "SELECT HBC_Position_ID FROM HBC_Position WHERE HBC_Trip_ID = ? "
				+ ") AND ca.value = ?");
		totalTransferCargo = DB.getSQLValueBDEx(get_TrxName(), sb.toString(), 
				new Object[]{p_hbc_trip_id, "TFC"});
		trip.set_ValueOfColumn("totalTransferCargo", totalTransferCargo);
		//@KevinY end
		trip.set_ValueOfColumn("TotalLoadingCargo", totalLoadingCargo);
		trip.set_ValueOfColumn("TotalReturnCargo", totalReturnCargo);
		trip.set_ValueOfColumn("TotalDischargingCargo", totalDishargingCargo);
		trip.saveEx();
		
		return "Updated";
	}

}
