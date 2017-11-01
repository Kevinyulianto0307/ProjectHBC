package org.toba.habco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MPosition extends X_HBC_Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6858105022587422422L;

	public MPosition(Properties ctx, int HBC_Position_ID, String trxName) {
		super(ctx, HBC_Position_ID, trxName);
	}

	public MPosition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MShipActivity[] getShipActivity() {
		List<MShipActivity> list = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "HBC_Position_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate ASC, isLastActivity ASC, isSecondActivity ASC, FinishDate ASC, HBC_Shipactivity_ID ASC")
		.list();
		
		return list.toArray(new MShipActivity[list.size()]);
		
	}
	
	public int getShipActivityIDDesc() {
		int shipactivityid = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "b.HBC_Trip_ID="+getHBC_Trip_ID()+" AND a.HBC_Position_ID="+getHBC_Position_ID(), get_TrxName())
		.addJoinClause("JOIN HBC_Position a on a.HBC_Position_ID =HBC_ShipActivity.HBC_Position_ID")
		.addJoinClause("JOIN HBC_Trip b ON b.HBC_Trip_ID=a.HBC_Trip_ID")
		.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
		.firstId();
		return shipactivityid;
	}
	
	/**
	 * get all ship activity from position order by desc
	 */
	public MShipActivity[] getShipActivityDesc(){
		List<MShipActivity> ShipActivitys= new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "b.HBC_Trip_ID="+getHBC_Trip_ID()+" AND a.HBC_Position_ID="+getHBC_Position_ID(), get_TrxName())
				.addJoinClause("JOIN HBC_Position a on a.HBC_Position_ID =HBC_ShipActivity.HBC_Position_ID")
				.addJoinClause("JOIN HBC_Trip b ON b.HBC_Trip_ID=a.HBC_Trip_ID")
				.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
				.list();
		return ShipActivitys.toArray(new MShipActivity[ShipActivitys.size()]);
	}
	
	/**
	 * get last ship activity where the activity is load or unload
	 * 
	 */
	public int getShipActivityLoadUnload() {
		int shipactivityid = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "b.HBC_Trip_ID="+getHBC_Trip_ID()+" AND d.name IN ('Loading','Discharging','Cargo Reduction','Cargo Addition')", get_TrxName())
		.addJoinClause("JOIN HBC_Position a on a.HBC_Position_ID =HBC_ShipActivity.HBC_Position_ID")
		.addJoinClause("JOIN HBC_Trip b ON b.HBC_Trip_ID=a.HBC_Trip_ID")
		.addJoinClause("JOIN C_Activity d on d.C_Activity_ID=HBC_ShipActivity.C_Activity_ID")
		.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
		.firstId();
		return shipactivityid;
	}
	
	/*
	 * get last position id from each trip
	 */
	public int lastPosition(){
		int positionid= new Query(getCtx(),Table_Name,"HBC_Trip_ID="+getHBC_Trip_ID(),get_TrxName())
					.setOrderBy("DateStart DESC, EndDate DESC, HBC_Position_ID DESC")
					.setOnlyActiveRecords(true)
					.firstId();
		return positionid;
	}
	/*
	public List<MShipActivity> getListShipActivity() {
		List<MShipActivity> list = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "HBC_Position_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.list();
		
		return list;
	}
	*/
	public X_HBC_ShipSpecialActivity[] getShipSpecialActivity() {
		List<X_HBC_ShipSpecialActivity> list = new Query(getCtx(), X_HBC_ShipSpecialActivity.Table_Name, "HBC_Position_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.list();
		
		return list.toArray(new X_HBC_ShipSpecialActivity[list.size()]);
		
	}
	
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		MTrip trip = new MTrip(getCtx(),getHBC_Trip_ID(),get_TrxName());
		MPosition[] position = trip.getPosition();
		
		if(position.length==1){
			trip.setHBC_PortPosition_ID(getHBC_PortPosition_ID());
			trip.saveEx();
		}
		
		/* Temporary Comment by @TommyAng
		if(getEndDate()!=null){
			trip.setEndDate(getEndDate());
			trip.saveEx();
		}
		*/
		
//		StringBuilder sql = new StringBuilder();
//		if(isFirstRecord()){
//			sql= new StringBuilder();
//			sql.append("UPDATE HBC_Position SET DifferenceHour="+Env.ZERO+" WHERE HBC_Position_ID="+getHBC_Position_ID());
//			DB.executeUpdate(sql.toString(), get_TrxName());
//		}else if(!isFirstRecord()){
//			if(getAnotherHour().getTime()> getDateStart().getTime()){
//				throw new AdempiereException("Start Date can't be smaller than another Last Date");
//			}else{
//				BigDecimal difference=getDifferenceStartFinishDate(getDateStart(),
//						getAnotherHour()).divide(new BigDecimal(24), 4,
//					RoundingMode.HALF_UP);
//				sql= new StringBuilder();
//				sql.append("UPDATE HBC_Position SET DifferenceHour="+difference.multiply(new BigDecimal(24))+" ,DifferenceDays= "+difference+" WHERE HBC_Position_ID="+getHBC_Position_ID());
//				DB.executeUpdate(sql.toString(), get_TrxName());
//			}
//		}			
		return true;
		
	}
	
	
	protected boolean beforeSave(boolean newRecord){
		
		if(getHBC_PortPosition_ID() <= 0)
			setHBC_PortPosition_ID(-1);
		
		if(!isSail() && getHBC_PortPosition_ID() <= 0)
			throw new AdempiereException("Port/Position is mandatory");
		
		try {
			if (getEndDate() != null) {
				if (getEndDate().getTime() < getDateStart().getTime())
					throw new AdempiereException(
							"Finish date can't be smaller than Start Date");
					BigDecimal dayinseconds = new BigDecimal(24);
				BigDecimal day = getDifferenceStartFinishDate(getEndDate(),
						getDateStart()).divide(dayinseconds, 4,
						RoundingMode.HALF_UP);
			
				if(!get_ValueAsBoolean("IsSail")){
					setHour(getDifferenceStartFinishDate(getEndDate(),
							getDateStart()));
					setDay(day);
				}else{
					setDifferenceHour(getDifferenceStartFinishDate(getEndDate(),
							getDateStart()));
					setDifferenceDays(day);
				}
			}
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		/* commented by @Stephan related with complete trip issue;
		if(!isFirstRecord()){
			if(getAnotherHour().getTime()> getDateStart().getTime())
				throw new AdempiereException("Start Date can't be smaller than another Last Date");
		}
		*/
		
		return true;
	}
	//Useless code comment by TommyAng
	/*
	public Timestamp getAnotherHour(){
		
		Timestamp anotherhour= null;
		int positionid[] = new Query(getCtx(),MPosition.Table_Name,"HBC_Trip_ID="
								+getHBC_Trip_ID(),get_TrxName())
							.setOrderBy("HBC_Position_ID")
							.getIDs();
		
		for(int i=0;i<positionid.length;i++){
			MPosition position = new MPosition(getCtx(),positionid[i],get_TrxName());
			
			if(positionid[i]==getHBC_Position_ID()){
				break;
			}else{
				anotherhour= position.getEndDate();	
			}
			
		}
		return anotherhour;
	}
	*/
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
	
	protected boolean isFirstRecord(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT HBC_Position_ID FROM HBC_Position WHERE HBC_Trip_ID="+getHBC_Trip_ID()+
					" ORDER BY HBC_Position_ID ASC");
		int positionid = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(positionid==getHBC_Position_ID() || positionid<0){
				return true;
			}
		return false;
	}
	
}
