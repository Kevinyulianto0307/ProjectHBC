package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MPortDistance extends X_HBC_PortDistance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5860987307225993486L;

	public MPortDistance(Properties ctx, int HBC_PortDistance_ID,
			String trxName) {
		super(ctx, HBC_PortDistance_ID, trxName);

	}

	public MPortDistance(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public static BigDecimal calculateDistance(int positionFromID, int positionToID, String trxName) {
		
		X_HBC_PortPosition positionFrom = new X_HBC_PortPosition(Env.getCtx(), positionFromID, trxName);
		X_HBC_PortPosition positionTo = new X_HBC_PortPosition(Env.getCtx(), positionToID, trxName);
		
		BigDecimal kmFrom = Env.ZERO;
		BigDecimal kmTo = Env.ZERO;
		BigDecimal totalDistance = Env.ZERO;
		boolean positionTypeFromRiver = false;
		boolean positionTypeToRiver = false;
	
		if(positionFrom.getHBC_PositionType()==null || positionTo.getHBC_PositionType()==null){
			throw new AdempiereException("Error Position Type in Position From / To not set");
		}
		
		if (positionFrom.getHBC_PositionType().equals(X_HBC_PortPosition.HBC_POSITIONTYPE_BaritoSRiver)) {
			positionTypeFromRiver = true;
			kmFrom = positionFrom.getHBC_Coordinates();
		}

		if (positionTo.getHBC_PositionType().equals(X_HBC_PortPosition.HBC_POSITIONTYPE_BaritoSRiver)) {
			positionTypeToRiver = true;
			kmTo = positionTo.getHBC_Coordinates();
		}
		
		if (positionTypeFromRiver == true) {
			if (positionTypeFromRiver == positionTypeToRiver) {
				totalDistance = kmTo.subtract(kmFrom).abs();
			} else {
				//TODO: Change Hardcode for Banjarmasin Port 
				StringBuffer sql = new StringBuffer("SELECT Distance FROM HBC_PortDistance ")
						.append("WHERE From_PortPosition_ID=? AND To_PortPosition_ID=?");

				kmTo = DB.getSQLValueBD(trxName, sql.toString(), new Object[]{positionToID, 1000038});
				if (kmTo == null) {
					kmTo = DB.getSQLValueBD(trxName, sql.toString(), new Object[]{1000038, positionToID});	
					if (kmTo == null)
						return null;
				}
				
				totalDistance = kmFrom.add(kmTo);
			}
		} else {
			if (positionTypeFromRiver == positionTypeToRiver) {
				StringBuffer sql = new StringBuffer("SELECT Distance FROM HBC_PortDistance ")
						.append("WHERE From_PortPosition_ID=? AND To_PortPosition_ID=?");

				totalDistance = DB.getSQLValueBD(trxName, sql.toString(), new Object[]{positionFromID, positionToID});
				if (totalDistance == null) {
					totalDistance = DB.getSQLValueBD(trxName, sql.toString(), new Object[]{positionToID, positionFromID});	
					if (totalDistance == null)
						return null;
				}
				
			} else {
				StringBuffer sql = new StringBuffer("SELECT Distance FROM HBC_PortDistance ")
				.append("WHERE From_PortPosition_ID=? AND To_PortPosition_ID=?");

				kmFrom = DB.getSQLValueBD(trxName, sql.toString(), new Object[]{positionFromID, 1000038});
				if (kmFrom == null) {
					kmFrom = DB.getSQLValueBD(trxName, sql.toString(), new Object[]{1000038, positionFromID});	
					if (kmFrom == null)
						return null;
				}
				
				totalDistance = kmTo.add(kmFrom);
			}
		}
		
		return totalDistance;
		
	}

}
