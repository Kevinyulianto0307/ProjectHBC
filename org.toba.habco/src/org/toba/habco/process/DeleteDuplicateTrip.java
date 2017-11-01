package org.toba.habco.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.X_HBC_CostActivity;
import org.toba.habco.model.X_HBC_Position;
import org.toba.habco.model.X_HBC_ShipActivity;

public class DeleteDuplicateTrip extends SvrProcess{

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else {
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		int count =0;

		StringBuilder sql = new StringBuilder();
		sql.append("select pos.hbc_trip_id, pos.hbc_portposition_id, date_trunc('day',pos.datestart), ")
		.append("pos.hbc_barge_id, pos.hbc_tugboat_id, count(1) from hbc_trip tri ")
		.append("join hbc_position pos on pos.hbc_trip_id=tri.hbc_trip_id where tri.ad_client_id=1000013 ")
		.append("and tri.documentno like '900%' ")
		.append("group by pos.hbc_portposition_id, date_trunc('day',pos.datestart), ")
		.append("pos.hbc_barge_id, pos.hbc_tugboat_id, pos.hbc_trip_id ")
		.append("having count(1) > 1");

		try {
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int p_HBC_Trip_ID = rs.getInt(1);
				int p_HBC_PortPosition_ID = rs.getInt(2);
				String p_DateStart = rs.getString(3);
				int p_HBC_Barge_ID=rs.getInt(4);
				int p_HBC_Tugboat_ID=rs.getInt(5);

				String where = "HBC_Trip_ID=" + p_HBC_Trip_ID + " AND HBC_PortPosition_ID=" + p_HBC_PortPosition_ID +
						" AND HBC_Barge_ID=" + p_HBC_Barge_ID + " AND HBC_Tugboat_ID=" + p_HBC_Tugboat_ID +
						" and date_trunc('day',datestart)=date' "+ p_DateStart + "'";

				List<X_HBC_Position> positions = new Query(getCtx(),  X_HBC_Position.Table_Name, where, get_TrxName())
				.list();
				int i = 1;
				boolean isNoMatch = true;
				for (X_HBC_Position position : positions) {
					i++;
					//check if position is linked to cost activity or ship activity
					String whereCost = "HBC_Position_ID=?";
					boolean match = new Query(getCtx(), X_HBC_CostActivity.Table_Name, whereCost, get_TrxName())
					.setParameters(position.get_ID())
					.match();

					boolean matchShipActivity = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, whereCost, get_TrxName())
					.setParameters(position.get_ID())
					.match();

					if (match || matchShipActivity) {
						if (isNoMatch)
							isNoMatch = false;
						continue;
					} 
					else {
						DB.executeUpdate("DELETE FROM HBC_Position WHERE HBC_Position_ID=" + position.get_ID(), get_TrxName());
						count++;
					}

					if (positions.size()==i && isNoMatch)
						break;
				}
			}	
		}
		catch (Exception e) {
			return "error";
		}
		finally {
			
		}
		return "Success deleted records: " + count;
	}
}
