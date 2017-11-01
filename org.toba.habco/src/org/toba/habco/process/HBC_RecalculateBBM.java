package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MActivity;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MBBMActivity;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.X_HBC_BBMActivity;
import org.toba.habco.model.X_HBC_BBMAdjustment;
import org.toba.habco.model.X_HBC_FuelTripAllocation;

public class HBC_RecalculateBBM extends SvrProcess{

	// parameter
	//TommyAng
	protected int p_TCS_BBMPlan_ID = 0;
	protected int p_C_Activity_ID = 0;
	
	protected BigDecimal dayForBBM = Env.ZERO;
	protected List<Integer> positionIDs = new ArrayList<Integer>();
	protected List<Integer> activityIDs = new ArrayList<Integer>();
	
	public final String SAILING="SAL";
	
	@Override
	protected void prepare() {		
	}

	@Override
	protected String doIt() throws Exception {
		
		//p_HBC_Trip_ID = getRecord_ID();
		p_TCS_BBMPlan_ID = getRecord_ID();
		
		/*
		if(isValidate()){
			setValidateDate();
		}*/
		MBBMPlan bbmplan = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		String whereClause = "HBC_Tugboat_ID = ? AND Created < ?";
		int TCS_BBMPlan_ID = new Query(getCtx(), MBBMPlan.Table_Name, whereClause, null)
							.setParameters(new Object[]{bbmplan.getHBC_Tugboat_ID(), bbmplan.getCreated()})
							.setOrderBy("Created DESC")					
							.firstId();
		//First Data skip
		if(TCS_BBMPlan_ID > 0){
			MBBMPlan bbmplanBefore = new MBBMPlan(getCtx(), TCS_BBMPlan_ID, get_TrxName());
		
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE TCS_BBMPlan SET FuelBalance=? WHERE TCS_BBMPlan_ID = ?");
			DB.executeUpdateEx(sb.toString(), new Object[]{bbmplanBefore.get_Value("CurrentBalanceActual"), bbmplan.getTCS_BBMPlan_ID()}, get_TrxName());
		}
		calculateFuelAllocation();
		calculateBBMActivity();
		calculateShifting();
		calculateLiter();
		
		/*
		if(isValidate()){
			removeValidateDate();
		}*/
		
		return "Created";
	}
	
	/**
	 * calculate fuel allocation for 1 trip
	 */
	protected void calculateFuelAllocation(){
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		BigDecimal totalDay = Env.ZERO;
		/*for (MPosition position : trip.getPosition()) {
			totalDay = totalDay.add(position.getDay().add(position.getDifferenceDays()));
		}
		
		trip.setDay(totalDay);
		trip.saveEx();*/
		/*
		for (MShipActivity ship : bbm.getShipActivity()) {
			totalDay = totalDay.add(ship.getDay());
		}
		*/
		BigDecimal totalSailDay = Env.ZERO;
		BigDecimal totalBerthDay = Env.ZERO;
		for (MShipActivity ship : bbm.getShipActivity()) {
			if(!ship.get_ValueAsBoolean("issecondactivity")){
				totalDay = totalDay.add(ship.getDay());
				//requested by Bella
				if(ship.getC_Activity().getValue().equals(SAILING))
					totalSailDay = totalSailDay.add(ship.getDiffDaySail());
				else
					totalBerthDay = totalBerthDay.add(ship.getBerthDays());	
				//end
			}
		}
		bbm.setDay(totalDay);
		bbm.setTotalSailDay(totalSailDay);
		bbm.setTotalBerthDay(totalBerthDay);
		bbm.saveEx();
		
		if(getFuelTripAllocation() > 0){
			X_HBC_FuelTripAllocation fuelAllocation = new X_HBC_FuelTripAllocation(getCtx(), getFuelTripAllocation(), get_TrxName());
			totalDay = totalDay.subtract(fuelAllocation.getDay());
			dayForBBM = totalDay;
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(FuelAllocation) FROM HBC_FuelTripAllocation WHERE TCS_BBMPlan_ID="+bbm.getTCS_BBMPlan_ID());
			BigDecimal literAllocation = DB.getSQLValueBD(getName(), sql.toString());
			
			if(literAllocation==null)
				literAllocation=Env.ZERO;
			
			sql = new StringBuilder();
			sql.append("SELECT SUM(TotalLiters) FROM HBC_BBMActivity WHERE TCS_BBMPlan_ID="+bbm.getTCS_BBMPlan_ID());
			BigDecimal literBBMActivity=DB.getSQLValueBD(get_TrxName(), sql.toString());
			
			if(literBBMActivity==null)
				literBBMActivity=Env.ZERO;
			
			sql= new StringBuilder();
			sql.append("SELECT SUM(ActualShipUsage) FROM FuelUsage WHERE TCS_BBMPlan_ID="+bbm.getTCS_BBMPlan_ID());
			BigDecimal BBMUsage = DB.getSQLValueBD(get_TrxName(), sql.toString());

			if(BBMUsage==null)
				BBMUsage=Env.ZERO;
			
			bbm.setTripAllocationDay(fuelAllocation.getDay());
			bbm.setLiterAllocation(literAllocation);
			bbm.setTotalLiters(literAllocation.add(literBBMActivity));
			bbm.setBBMUsage(BBMUsage);
			bbm.saveEx();
		}
		//comment by @TommyAng request by Hadi
		/*
		else
			throw new AdempiereException("Cant find fuel trip allocation");
		 */
		/*
		MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		BigDecimal totalDay = Env.ZERO;
		for (MPosition position : trip.getPosition()) {
			totalDay = totalDay.add(position.getDay().add(position.getDifferenceDays()));
		}
		
		trip.setDay(totalDay);
		trip.saveEx();
		
		if(getFuelTripAllocation() > 0){
			X_HBC_FuelTripAllocation fuelAllocation = new X_HBC_FuelTripAllocation(getCtx(), getFuelTripAllocation(), get_TrxName());
			totalDay = totalDay.subtract(fuelAllocation.getDay());
			dayForBBM = totalDay;
			
			trip.setTripAllocationDay(fuelAllocation.getDay());
			trip.setLiterAllocation(fuelAllocation.getLiterAllocation());
			trip.saveEx();
		}
		else
			throw new AdempiereException("Cant find fuel trip allocation");
		*/
	}
	
	/**
	 * calculate bbm activity
	 */
	protected void calculateBBMActivity(){
		
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		/*
		String where = "HBC_Trip_ID = "+p_HBC_Trip_ID;
		boolean match = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.match();
		
		if(match)
			log.severe("Already have bbm activity");
		*/
		
		//for (MBBMActivity bbmActivity : trip.getBBMActivity()) {
		for (MBBMActivity bbmActivity : bbm.getBBMActivity()) {
			MActivity activity = (MActivity) bbmActivity.getC_Activity();
			boolean isFuelQuota = activity.get_ValueAsBoolean("IsFuelQuota");
			
			if(!isFuelQuota || !isBunkering(bbmActivity) || !isFuelTransfer(bbmActivity)){
				//BigDecimal fuel = trip.getHBC_Tugboat().getHBC_AuxiliaryEngine().getFuelConsumptionPerHour();
				BigDecimal fuel = bbmActivity.getFuelConsumptionPerHour();
				//if(p_Hour == null)
					//p_Hour = Env.ZERO;
				
				BigDecimal hour = bbmActivity.getHour();
				BigDecimal day = bbmActivity.getDay();
				
				//BigDecimal totalLiters = day.multiply(hour).multiply(fuel); @TommyAng
				//bbmActivity.setHBC_Tugboat_ID(trip.getHBC_Tugboat_ID());
				//bbmActivity.setDay(dayForBBM);
				//bbmActivity.setFuelConsumptionPerHour(fuel);
				//bbmActivity.setHour(hour);
				//bbmActivity.setTotalLiters(totalLiters); @TommyAng
				bbmActivity.saveEx();
			}
		}
		
	}
	
	/**
	 * calculate if activity is have fuel quota
	 */
	protected void calculateShifting(){
		
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		//for (MBBMActivity bbmActivity : trip.getBBMActivitys()) {
		for (MBBMActivity bbmActivity : bbm.getBBMActivitys()) {	
			
			MActivity activity = (MActivity) bbmActivity.getC_Activity();
			if (activity==null)
				continue;
			
			boolean isFuelQuota = activity.get_ValueAsBoolean("IsFuelQuota");
			
			if(isFuelQuota || isBunkering(bbmActivity) || isFuelTransfer(bbmActivity)){
				BigDecimal fuelQuota = (BigDecimal) bbmActivity.get_Value("FuelPercentageQuota");
				if (fuelQuota==null)
					fuelQuota = Env.ZERO;
				
				BigDecimal fuelConsump = (BigDecimal) bbmActivity.get_Value("FuelConsumptionMain");
				if (fuelConsump==null)
					fuelConsump = Env.ZERO; 
				
				BigDecimal hour = bbmActivity.getHour();
				fuelQuota = fuelQuota.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
				//BigDecimal totalLiters = fuelQuota.multiply(fuelConsump).multiply(hour);BigDecimal totalLiters = fuelQuota.multiply(fuelConsump).multiply(hour);
				//bbmActivity.setTotalLiters(bbmActivity.getTotalLiters()); @TommyAng
				bbmActivity.saveEx();
			}
		}
	}
	
	/**
	 * Calculate liter for trip
	 */
	protected void calculateLiter(){
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		BigDecimal liter = Env.ZERO;
		BigDecimal totalLiter = Env.ZERO;
		BigDecimal fuelBalanceNew = Env.ZERO;
		//int tripID = bbm.get_ValueAsInt("CurrentTrip_ID");
		//int firstTrip_ID = bbm.get_ValueAsInt("HBC_Trip_ID");
		//BigDecimal fuelAdj = Env.ZERO;
		//for (MPosition position : trip.getPosition()) {
			//for (MShipActivity shipActivity : position.getShipActivity()) {
			for (MShipActivity shipActivity : bbm.getShipActivity()) {
				liter = liter.add(shipActivity.getLiter());
				/*Comment by @Phie total liter just get from bbm activity
				totalLiter = totalLiter.add(shipActivity.getTotalLiters());
				*/
				//fuelBalanceNew = fuelBalanceNew.add(shipActivity.getFuelBalance());
			}
			
			//Comment by @TommyAng Request by Hadi
			//@Phie
			/*
			if(tripID == firstTrip_ID)
			{
				MTrip trip = new MTrip(getCtx(), tripID, get_TrxName());
				boolean check = false;
				for(MPosition position : trip.getPositionDesc())
				{
					if(check == false)
					{
						for (MShipActivity shipActivity : position.getShipActivityDesc()) {
							if(shipActivity.getFuelBalance().compareTo(Env.ZERO)>0){
								fuelBalanceNew = shipActivity.getFuelBalance();
								check = true;
								break; //loop 1
							}
						}
					}
				}
			}
			else
			{
				String fb = "select fuelbalance from hbc_shipactivity where hbc_position_id in (select hbc_position_id from hbc_position where hbc_trip_id = ?) and fuelbalance>0 order by finishdate desc";
				fuelBalanceNew = DB.getSQLValueBD(null, fb, tripID);
			}
			*/
			//end phie
		fuelBalanceNew=(BigDecimal) bbm.get_Value("FuelBalanceNew");
		
		if(fuelBalanceNew==null)
			fuelBalanceNew=Env.ZERO;
		
		for (int id : getBBMActivity()) {
			X_HBC_BBMActivity bbmActivity = new X_HBC_BBMActivity(getCtx(), id, get_TrxName());
			totalLiter = totalLiter.add(bbmActivity.getTotalLiters());
		}
		
		totalLiter = totalLiter.add(bbm.getLiterAllocation());
		liter = liter.add(bbm.getFuelBalance());
		//fuelAdj = trip.getFuelActual().subtract(fuelBalanceNew);
		bbm.setLiter(liter);
		bbm.setTotalLiters(totalLiter);
		bbm.setLiterCalculation(liter.subtract(totalLiter));
		//bbm.setFuelBalanceNew(fuelBalanceNew);
		//trip.setFuelAdjustment(fuelAdj);
		bbm.saveEx();
		
		// set ship fuel usage report
		BigDecimal BBMUsage = bbm.getBBMUsage();
		//BigDecimal totalStandBy = getSumTotalLiterActivity();
		BigDecimal shipFuelUsage = BBMUsage.subtract(totalLiter);
		bbm.setShipFuelUsage(shipFuelUsage);
		bbm.saveEx();
		
		// set summary fuel adjustment
		BigDecimal sumFuelAdj = getSumFuelAdjustment();
		//BigDecimal fuelDisc = bbm.getLiterCalculation().subtract(fuelBalanceNew).subtract(sumFuelAdj).subtract(shipFuelUsage);
		BigDecimal fuelDisc = bbm.getLiterCalculation().subtract(fuelBalanceNew).subtract(sumFuelAdj).add(shipFuelUsage);
		bbm.setFuelAdjustment(sumFuelAdj);
		bbm.setFuelActual(fuelBalanceNew.add(sumFuelAdj));
		bbm.set_ValueOfColumn("CurrentBalanceActual", fuelBalanceNew.add(sumFuelAdj));
		bbm.setFuelUsageDiscrepancy(fuelDisc);
		bbm.saveEx();
		
		
	}
	
	/**
	 * @return array of HBC_BBMActivity_ID
	 * 
	 */
	protected int[] getBBMActivity(){
		
		//String where = "HBC_Trip_ID="+p_HBC_Trip_ID;
		String where = "TCS_BBMPlan_ID="+p_TCS_BBMPlan_ID;
		int[] ids = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}
	
	/**
	 * @return fuel trip allocation id
	 */
	protected int getFuelTripAllocation(){
		
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		StringBuilder where = new StringBuilder();
//		where.append("HBC_TugboatCategory_ID="+trip.getHBC_Tugboat().getHBC_TugboatCategory_ID())
//		.append(" AND From_PortPosition_ID="+trip.getFrom_PortPosition_ID())
//		.append(" AND To_PortPosition_ID="+trip.getTo_PortPosition_ID());
		//where.append("HBC_Trip_ID="+trip.getHBC_Trip_ID());
		where.append("TCS_BBMPlan_ID="+bbm.getTCS_BBMPlan_ID());
		
		int id = new Query(getCtx(), X_HBC_FuelTripAllocation.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		
		if(id <= 0)
			id = 0;
		
		return id;
	}

	/**
	 * @return sum fuel adjustment
	 */
	protected BigDecimal getSumFuelAdjustment(){
		
		BigDecimal sumFuelAdj = Env.ZERO;
		
		for (int id : getBBMAdjustment()) {
			X_HBC_BBMAdjustment bbmAdjustment = new X_HBC_BBMAdjustment(getCtx(), id, get_TrxName());
			sumFuelAdj = sumFuelAdj.add(bbmAdjustment.getFuelAdjustment());
		}
		
		return sumFuelAdj;
	}
	
	/**
	 * @return array of id bbm adjustment
	 */
	protected int[] getBBMAdjustment(){
		
		//String where = "HBC_Trip_ID="+p_HBC_Trip_ID;
		String where = "TCS_BBMPlan_ID="+p_TCS_BBMPlan_ID;
		int[] ids = new Query(getCtx(), X_HBC_BBMAdjustment.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}
	
	protected BigDecimal getSumTotalLiterActivity(){
		
		BigDecimal totalLiter = Env.ZERO;
		for (int id : getBBMActivity()) {
			MBBMActivity activity = new MBBMActivity(getCtx(), id, get_TrxName());
			totalLiter = totalLiter.add(activity.getTotalLiters());
		}
		
		return totalLiter;
	}
	
	/**
	 * @param BBMActivity
	 * @return true if activity fuel transfer
	 */
	protected boolean isFuelTransfer(MBBMActivity bbmActivity){
		
		boolean retValue = false;
		if(bbmActivity.getC_Activity().getValue().equals("FTR"))
			retValue = true;
		
		return retValue;
	}
	
	/**
	 * @param BBMActivity
	 * @return true if bunkering
	 */
	protected boolean isBunkering(MBBMActivity bbmActivity){
		
		boolean retValue = false;
		MActivity activity = (MActivity) bbmActivity.getC_Activity();
		if (activity==null)
			return false;
		
		if(activity.getValue().equals("BUN"))
			retValue = true;
		
		return retValue;
		
	}
	
}
