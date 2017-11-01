package org.toba.habco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MActivity;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MShipPositionLine extends X_HBC_ShipPositionLine {
	/*
	 *created by yonk 
	 */

	public MShipPositionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MShipPositionLine(Properties ctx, int HBC_ShipPositionLine_ID,
			String trxName) {
		super(ctx, HBC_ShipPositionLine_ID, trxName);
	}
	
	public BigDecimal getDifferenceStartFinishDate(Timestamp finishtime,
			Timestamp starttime) {
		BigDecimal diff = Env.ZERO;
		long different = finishtime.getTime() - starttime.getTime();

		int diffSeconds = (int) (long) different / 1000 % 60;
		int diffMinutestoSeconds = (int) (long) (different / (60 * 1000) % 60) * 60;
		int diffHourstoSeconds = (int) (long) (different / (60 * 60 * 1000) % 24) * 3600;
		int diffDaysSeconds = (int) (long) (different / (24 * 60 * 60 * 1000)) * 24 * 3600;
		int totalseconds = diffSeconds + diffMinutestoSeconds
				+ diffHourstoSeconds + diffDaysSeconds;
		BigDecimal hoursinseconds = new BigDecimal(3600);
		diff = new BigDecimal(totalseconds).divide(hoursinseconds, 4,
				RoundingMode.HALF_UP);
		return diff;
	}
	 
	    
	protected boolean beforeSave(boolean newRecord) {
		// HABCO-1669 Jam pada Timesheet Line by yonk
		if (getFinishDate() != null) {
			int finishdate = (int) (long) getFinishDate().getTime();
			int startdate = (int) (long) getStartDate().getTime();
			//HABCO-1682 validate date finishdate > startdate
			if (finishdate < startdate)
				throw new AdempiereException(
						"Finish date can't be smaller than Start Date");
			BigDecimal dayinseconds = new BigDecimal(24);
			BigDecimal day = getDifferenceStartFinishDate(getFinishDate(),
					getStartDate()).divide(dayinseconds, 4,
					RoundingMode.HALF_UP);
			set_ValueOfColumn(
					"Hour",
					getDifferenceStartFinishDate(getFinishDate(),
							getStartDate()));
			set_ValueOfColumn("Day", day);
		}// END OF HABCO-1669

		int overtowingid = new Query(getCtx(), MActivity.Table_Name,
				"Name='Overtowing'", get_TrxName()).firstId();
		if (get_ValueAsInt("C_Activity_ID") == overtowingid) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT c.ActiveFrom FROM HBC_ShipRelationLine c "
					+ " LEFT JOIN HBC_ShipRelation a ON a.HBC_ShipRelation_ID = c.HBC_ShipRelation_ID "
					+ " WHERE HBC_Barge_ID="
					+ getHBC_ShipPosition().getHBC_Barge_ID()
					+ " AND c.Activeto is null");
			Timestamp activefrom = DB.getSQLValueTS(null, sql.toString());

			if (getDifferenceStartFinishDate(getStartDate(), activefrom)
					.compareTo(new BigDecimal(24)) < 0) {
				sql = new StringBuilder();
				sql.append("SELECT c.HBC_Tugboat_ID FROM HBC_ShipRelationLine c "
						+ " LEFT JOIN HBC_ShipRelation a ON a.HBC_ShipRelation_ID = c.HBC_ShipRelation_ID "
						+ " WHERE HBC_Barge_ID="
						+ getHBC_ShipPosition().getHBC_Barge_ID()
						+ " AND c.Activeto is not null"
						+ " ORDER BY c.HBC_Tugboat_ID DESC");
				int tugboatid = DB.getSQLValue(get_TrxName(), sql.toString());
				setHBC_Tugboat_ID(tugboatid);

				sql = new StringBuilder();
				sql.append("SELECT HBC_Barge_ID FROM HBC_Shiprelation WHERE HBC_Tugboat_ID="
						+ getTugBoatPartner_ID());
				int bargerelationid = DB.getSQLValue(get_TrxName(),
						sql.toString());

				if (getHBC_Barge_ID() != bargerelationid) {
					throw new AdempiereException(
							"Tugboat Partner Didn't match with Ship Relation");
				}
			}
		}

		return true;
	}
	    
	    
	    // HABCO-1568 Auto Create Cargo document where position line activity are loading , unloading , draft survey by yonk
		@Override
	    protected boolean afterSave (boolean newRecord, boolean success)
		{		
			//HABCO-1607 callout Tugboat di window Ship Position
			setHBC_Tugboat_ID(getHBC_ShipPosition().getHBC_Barge().getHBC_Tugboat_ID());
			// end of HABCO=1607
//			int loadingid[] = new Query(Env.getCtx(),MActivity.Table_Name,"Name='Loading' OR  Name='Discharging'",get_TrxName()).getIDs();
//			int cargoid= new Query(getCtx(),X_HBC_Cargo.Table_Name,"HBC_ShipPositionLine_ID="+getHBC_ShipPositionLine_ID(),get_TrxName()).firstId();
//			for(int i=0;i<loadingid.length;i++){
//				if(get_ValueAsInt("C_Activity_ID")==loadingid[i] && cargoid==-1 ){
//				X_HBC_Cargo cargo = new X_HBC_Cargo(Env.getCtx(),0,get_TrxName());
//				cargo.setHBC_PortPosition_ID(getTo_PortPosition_ID());
//				cargo.set_ValueOfColumn("HBC_ShipPositionLine_ID", getHBC_ShipPositionLine_ID());
//				cargo.setAmountOfCargo(getAmountOfCargo());
//				cargo.set_ValueOfColumn("HBC_ShipPosition_ID", getHBC_ShipPosition_ID());
//				cargo.saveEx();
//				m_processMsg="Cargo Document Created!";
//				log.saveInfo(null, m_processMsg);
//				}
//			}
			//temp by yonk
//			//HABCO-1600 Update Tugboat di Window Barge
//			if(getHBC_Activity().getName()=="Overtowing"){
//				int bargeid = new Query(Env.getCtx(),X_HBC_Barge.Table_Name,"HBC_Tugboat_ID="+getHBC_Tugboat_ID(),get_TrxName()).firstId();
//				X_HBC_Barge barges = new X_HBC_Barge(Env.getCtx(), bargeid, get_TrxName());
//				barges.setHBC_Tugboat_ID(getHBC_ShipPosition().getHBC_Tugboat_ID());
//				barges.saveEx();
//				
//				X_HBC_Barge barge = new X_HBC_Barge(Env.getCtx(),getHBC_ShipPosition().getHBC_Barge_ID(),get_TrxName());
//				barge.setHBC_Tugboat_ID(getHBC_Tugboat_ID());
//				barge.saveEx();
//			}// end of HABCO-1600
					
			
//			temp by yonk	
//			// HABCO-1567 set fuel balance from ship position line to tugboat by yonk
//			if(getHBC_Tugboat_ID()!=0 && getFuelBalance().compareTo(Env.ZERO)>0){
//				X_HBC_Tugboat tugboat= new X_HBC_Tugboat(Env.getCtx(), getHBC_Tugboat_ID(), get_TrxName());
//				tugboat.setFuelBalance(getFuelBalance());
//				tugboat.saveEx();
//			}// end of HABCO-1567
//			
			
			return true;
		}
		
		
		// end of HABCO-1568


	/**
	 * 
	 */
	private static final long serialVersionUID = -3091541062193358062L;

}
