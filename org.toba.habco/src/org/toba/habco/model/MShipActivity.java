package org.toba.habco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.model.X_C_Activity;
import org.compiere.util.DB;
import org.compiere.util.Env;


public class MShipActivity extends X_HBC_ShipActivity {

	
	/**
	 * @author yonk
	 * 
	 */
	
	private static final long serialVersionUID = -7726005875844463447L;
	public final String LOADING="LOA";
	public final String UNLOADING="DIS";
	public final String ADDCARGO="CAD";
	public final String REDCARGO="CRE";
	public final String OFF_HIRED="OFH";
	public final String ON_HIRED="ONH";
	public final String SAILING="SAL";

	public MShipActivity(Properties ctx, int HBC_ShipActivity_ID,
			String trxName) {
		super(ctx, HBC_ShipActivity_ID, trxName);
	}

	
	public MShipActivity(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeDelete(){
		if(getFinishDate()==null || getHBC_Position().getEndDate()==null)
			return true;
		
		if(getFinishDate().getTime()==getHBC_Position().getEndDate().getTime()){
			MPosition position= new MPosition(getCtx(),getHBC_Position_ID(),get_TrxName());
			position.setEndDate(lastdate());
			position.saveEx();
			if(getHBC_Position_ID()==position.lastPosition()){
				MTrip trip = new MTrip(getCtx(), position.getHBC_Trip_ID(), get_TrxName());
				trip.setEndDate(lastdate());
				trip.saveEx();
			}
		}
		return true;
	}
	
	
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		/*
		if(getFinishDate()!=null){
			MPosition position = new MPosition(getCtx(), getHBC_Position_ID(), get_TrxName());
			MTrip trip = new MTrip(getCtx(),getHBC_Position().getHBC_Trip_ID(),get_TrxName());
				if((getFinishDate().getTime()-lastdate().getTime())>0 || isLastRecord()){
					position.setEndDate(getFinishDate());
					BigDecimal dayinhour = new BigDecimal(24);
					BigDecimal day = getDifferenceStartFinishDate(getFinishDate(),
							getStartDate()).divide(dayinhour, 4,
							RoundingMode.HALF_UP);
					if(position.get_ValueAsBoolean("IsSail")){
						position.setDifferenceHour(position.getDifferenceStartFinishDate(getFinishDate(),
								position.getDateStart()));
						position.setDifferenceDays(day);
					}else{
						position.setHour(getDifferenceStartFinishDate(getFinishDate(),
								position.getDateStart()));
						position.setDay(day);
					}
					position.saveEx();
					trip.set_ValueOfColumn("EndDate", getFinishDate());
					trip.saveEx();
				}
				
		}
		*/
		
		if(isLastActivity()){
			MPosition position = new MPosition(getCtx(),getHBC_Position_ID(),get_TrxName());
			position.setEndDate(getFinishDate());
			position.saveEx();
		}
		MTugboat tugboat = new MTugboat(getCtx(), getHBC_Tugboat_ID(), get_TrxName());
		boolean overlapDate = false;
		
		if(tugboat.getCurrent_ShipActivity_ID() > 0){
			MShipActivity recordActivity = new MShipActivity(getCtx(), tugboat.getCurrent_ShipActivity_ID(), get_TrxName());
			
			long recordDate = recordActivity.getStartDate().getTime();
			long currentDate = getStartDate().getTime();
			
			if(currentDate > recordDate)
				overlapDate = true;
		}
		
		if((getFuelBalance().compareTo(Env.ZERO) > 0 && overlapDate) || 
				(getFuelBalance().compareTo(Env.ZERO) > 0 && tugboat.getCurrent_ShipActivity_ID() <= 0)){
			tugboat.setFuelBalance(getFuelBalance());
			tugboat.setCurrent_ShipActivity_ID(getHBC_ShipActivity_ID());
			tugboat.saveEx();
		}
		
		// set hired date and place in contract
		if(getC_Activity().getValue().equals(ON_HIRED)){
			MContract contract = (MContract) getHBC_Position().getHBC_Trip().getHBC_Contract();
			contract.setOnHireDate(getStartDate());
			contract.setONHired_Position_ID(getHBC_Position().getHBC_PortPosition_ID());
			contract.saveEx();
		}
		
		if(getC_Activity().getValue().equals(OFF_HIRED)){
			MContract contract = (MContract) getHBC_Position().getHBC_Trip().getHBC_Contract();
			contract.setOffHireDate(getStartDate());
			contract.setOFFHired_Position_ID(getHBC_Position().getHBC_PortPosition_ID());
			contract.saveEx();
		}
		
		//  calculate total cargo qty for trip
		MTrip trip = new MTrip(getCtx(),getHBC_Position().getHBC_Trip_ID(),get_TrxName());
		BigDecimal totalCargoQty = Env.ZERO;
		
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
				//@TommyAng
				String sql = "UPDATE HBC_ShipActivity SET TotalCargoQty = "+totalCargoQty+" WHERE HBC_ShipActivity_ID=?";
				int no = DB.executeUpdate(sql, shipActivity.get_ID(), get_TrxName());
				log.info("UPDATE TotalCargoQty in ShipActivity#"+no+" "+shipActivity.get_ID());
			}
			//@TommyAng
			position.set_ValueOfColumn("TotalCargoQty", totalCargoQty);
			position.saveEx();
			//end @TommyAng
		}
		
		//@TommyAng
		if(totalCargoQty.compareTo(Env.ZERO)>=0){
			trip.setTotalCargoQty(totalCargoQty);
			trip.saveEx();			
			
			//@TommyAng
			/*MPosition position = new MPosition(getCtx(), getHBC_Position_ID(), get_TrxName());
			position.set_ValueOfColumn("TotalCargoQty", totalCargoQty);
			position.saveEx();*/
			//end @TommyAng
			
			/*String sql = "UPDATE HBC_ShipActivity SET TotalCargoQty = "+totalCargoQty+" WHERE HBC_ShipActivity_ID=?";
			int no = DB.executeUpdate(sql, get_ID(), get_TrxName());
			log.info("UPDATE TotalCargoQty in ShipActivity#"+no);*/
		}
		/* comment by phie request by bella
		else
			throw new AdempiereException("TotalCargoQty is Minus!");
		*/
		//end @TommyAng
		
		return true;
	}
	
	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		// recalculate total cargo qty
		MTrip trip = new MTrip(getCtx(),getHBC_Position().getHBC_Trip_ID(),get_TrxName());
		BigDecimal totalCargoQty = Env.ZERO;
		
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
		
		if(totalCargoQty.compareTo(Env.ZERO)>=0){
			trip.setTotalCargoQty(totalCargoQty);
			trip.saveEx();
			
			//@TommyAng
			MPosition position = new MPosition(getCtx(), getHBC_Position_ID(), get_TrxName());
			position.set_ValueOfColumn("TotalCargoQty", totalCargoQty);
			position.saveEx();
			//end @TommyAng
			
			//@TommyAng The ShipActivity that deleted wont need to set the totalcargoqty
			/*
			String sql = "UPDATE HBC_ShipActivity SET TotalCargoQty = "+totalCargoQty+" WHERE HBC_ShipActivity_ID=?";
			int no = DB.executeUpdate(sql, get_ID(), get_TrxName());
			log.info("UPDATE TotalCargoQty in ShipActivity#"+no);
			*/
		}
		
		return true;
	}
	
	/**
	 * calculate another totalcargo qty
	 * 
	 */
	/*
	public BigDecimal AnotherTotalCargoQty(){
		MPosition position = new MPosition(getCtx(),getHBC_Position_ID(),get_TrxName());
//		int shipactivityid = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "HBC_ShipActivity_ID!="+getHBC_ShipActivity_ID()+" AND c.Name IN ('Loading','Discharging','Cargo Reduction','Cargo Addition') AND b.HBC_Trip_ID="+position.getHBC_Trip_ID(), get_TrxName())
//		.addJoinClause("JOIN HBC_Position a on a.HBC_Position_ID =HBC_ShipActivity.HBC_Position_ID")
//		.addJoinClause("JOIN HBC_Trip b ON b.HBC_Trip_ID=a.HBC_Trip_ID")
//		.addJoinClause("JOIN C_Activity c ON c.C_Activity_ID=HBC_ShipActivity.C_Activity_ID")
//		.setOrderBy("HBC_ShipActivity_ID DESC")
//		.firstId();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT HBC_ShipActivity_ID FROM HBC_ShipActivity a "
				+"LEFT JOIN HBC_Position b ON a.HBC_Position_ID=b.HBC_Position_ID "
				+"LEFT JOIN HBC_Trip c on c.HBC_Trip_ID=b.HBC_Trip_ID "
				+"JOIN C_Activity d ON d.C_Activity_ID=a.C_Activity_ID "
				+"WHERE c.HBC_Trip_ID="+position.getHBC_Trip_ID()+" AND d.Name IN ('Loading','Discharging','Cargo Reduction','Cargo Addition') AND a.HBC_ShipActivity_ID!="+getHBC_ShipActivity_ID()
				+" ORDER BY HBC_ShipActivity_ID DESC");
		int shipactivityid=DB.getSQLValue(get_TrxName(), sql.toString());
		MShipActivity sactivity = new MShipActivity(getCtx(), shipactivityid, get_TrxName());
		
		return sactivity.getTotalCargoQty();
	}
	*/
	public boolean beforeSave(boolean newRecord){
		if (getC_Activity_ID()<=0)
			throw new AdempiereException("Activity Can't Be Blank");
		
		if(newRecord || (is_ValueChanged(MShipActivity.COLUMNNAME_IsDemurrageFreight) 
				|| is_ValueChanged(MShipActivity.COLUMNNAME_IsDemurrageWeather)))
		{
			if(get_ValueAsBoolean(MShipActivity.COLUMNNAME_IsDemurrageFreight) && 
					get_ValueAsBoolean(MShipActivity.COLUMNNAME_IsDemurrageWeather))
				throw new AdempiereException("Cannot Check both Demurrage Weather and Demurrage Freight");
		}
			
		
			
		if (getFinishDate()!=null){			
			BigDecimal hoursindays = new BigDecimal(24);
			BigDecimal day = getDifferenceStartFinishDate(getFinishDate(),
					getStartDate()).divide(hoursindays, 4,
					RoundingMode.HALF_UP);
			
			/* Comment by @phie
			 * This code : getDifferenceStartFinishDate(getFinishDate(),getStartDate())
			 * will return the original value of hour
			 * if recal from day x 24, there's missing value from rounding day
			 * 
			setHour(day.multiply(hoursindays));
			*/
			//@phie
			setHour(getDifferenceStartFinishDate(getFinishDate(), getStartDate()));
			//end phie
			setDay(day);
			if(isLastActivity()){
				if (getFinishDate().getTime() < getStartDate().getTime())
				throw new AdempiereException("Finish date can't be smaller than Start Date");

			}
		}
		
		MShipActivity sActivity= new Query(Env.getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="
				+getHBC_Position_ID(),get_TrxName())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
						.first();
		
		if(sActivity!=null){
			if(sActivity.isLastActivity() && newRecord)
				throw new AdempiereException("Can't create new record ! there is last activity");
		}
		
		
		
		int SailingActivityID= new Query(getCtx(),X_C_Activity.Table_Name,"Name='Sailing'",get_TrxName()).firstId();
		if(getC_Activity_ID()==SailingActivityID && get_ValueAsInt("Coordinates")!=0){
			setFinishDate(getStartDate());
		}
		
		/* commented by @Stephan - move to after save
		MTrip trip = new MTrip(getCtx(),getHBC_Position().getHBC_Trip_ID(),get_TrxName());
		BigDecimal totalcargo= AnotherTotalCargoQty();		
		if(getC_Activity().getValue().equals(LOADING)|| getC_Activity().getValue().equals(ADDCARGO)){
			totalcargo=totalcargo.add(getAmountOfCargo());
		}else if(getC_Activity().getValue().equals(UNLOADING)|| getC_Activity().getValue().equals(REDCARGO)){
			totalcargo=totalcargo.subtract(getAmountOfCargo());
		}
		
		if(totalcargo.compareTo(Env.ZERO)>=0){
			trip.setTotalCargoQty(totalcargo);
			trip.saveEx();
			setTotalCargoQty(totalcargo);
		}
		*/
		
		//@TommyAng requested by Bella
		
		try {
			if (getFinishDate() != null) {
				if (getFinishDate().getTime() < getStartDate().getTime())
					throw new AdempiereException(
							"Finish date can't be smaller than Start Date");
					BigDecimal dayinseconds = new BigDecimal(24);
				BigDecimal day = getDifferenceStartFinishDate(getFinishDate(),
						getStartDate()).divide(dayinseconds, 4,
						RoundingMode.HALF_UP);
			
				//if(!get_ValueAsBoolean("IsSail")){
				if(getC_Activity().getValue().equals(SAILING)){
					/*
					 setHour(getDifferenceStartFinishDate(getFinishDate(),
							getStartDate()));
					setDay(day);
					*/
					setBerthDays(Env.ZERO);
					setDiffDaySail(day);
				}else{
					/*
					setDifferenceHour(getDifferenceStartFinishDate(getFinishDate(),
							getStartDate()));
					setDifferenceDays(day);
					*/
					setDiffDaySail(Env.ZERO);
					setBerthDays(day);
				}
			}
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		//end @TommyAng
		return true;
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

	
	public boolean isLastRecord(){
		
		int shipactivityid = new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+getHBC_Position_ID(),get_TrxName())
				.setOnlyActiveRecords(true)
				.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC") //TODO note by tommy : order by date not by ID
				.firstId();
		if (getHBC_ShipActivity_ID()==shipactivityid){
			return true;
		}
		return false;
	}

	public boolean isStartPosition(){
		
		if(getHBC_Position().getHBC_PortPosition_ID() == 
				getHBC_Position().getHBC_Trip().getHBC_PortPosition_ID())
			return true;
		
		return false;
	}
	
	/**
	 * get last date from all ship activity
	 */
	public Timestamp lastdate(){
		
		int activityid = new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+getHBC_Position_ID()+" AND FinishDate IS NOT NULL "
				+ "AND HBC_ShipActivity_ID!="+getHBC_ShipActivity_ID(),get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, FinishDate DESC, HBC_Shipactivity_ID DESC")
				.firstId();
		
		MShipActivity activity = new MShipActivity(getCtx(), activityid, get_TrxName());
		
		return activity.getFinishDate();
	}

}
