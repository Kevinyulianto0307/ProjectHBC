package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
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
import org.toba.habco.model.MFuelTripAllocation;
import org.toba.habco.model.MFuelUsage;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.X_HBC_BBMActivity;
import org.toba.habco.model.X_HBC_BBMAdjustment;
import org.toba.habco.model.X_HBC_FuelTripAllocation;
import org.toba.habco.model.X_HBC_ShipActivity;

public class HBC_CalculateBBM extends SvrProcess{
	
	//@TommyAng
	//protected int p_HBC_Trip_ID = 0; 
	
	public final String SAILING="SAL";
	
	//TripType
	public final String DALAM			=	"D";
	public final String DALAM_KOSONG	=	"DK";
	public final String DALAM_LENGGANG	=	"DL";
	public final String DALAM_MUATAN	=	"DM";
	public final String LUAR_KOSONG		=	"LK";
	public final String LUAR_LENGGANG	=	"LL";
	public final String LUAR_MUATAN		=	"LM";
	public final String PULANG_KOSONG	=	"PK";
	public final String PULANG_LENGGANG	=	"PL";
	public final String PULANG_MUATAN	=	"PM";
	
	protected int p_TCS_BBMPlan_ID = 0;
	protected int p_C_Activity_ID = 0;
	protected BigDecimal p_Hour = Env.ZERO;
	protected Timestamp p_Date = null;
	
	protected BigDecimal dayForBBM = Env.ZERO;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			//@TommyAng
			/*
			else if (name.equals("HBC_Trip_ID")){
				p_HBC_Trip_ID = para[i].getParameterAsInt();
			}
			*/
			else if (name.equals("C_Activity_ID")){
				p_C_Activity_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("Hour")){
				p_Hour = para[i].getParameterAsBigDecimal();
			}
			else if (name.equals("StartDate")){
				p_Date = para[i].getParameterAsTimestamp();
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		
		//@TommyAng
		//p_HBC_Trip_ID = getRecord_ID();
		p_TCS_BBMPlan_ID = getRecord_ID();
		
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

		return "Created";
	}

	/**
	 * calculate fuel allocation for 1 trip
	 */
	protected void calculateFuelAllocation(){
		/*MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());		
		BigDecimal totalDay = Env.ZERO;
		for (MPosition position : trip.getPosition()) {
			totalDay = totalDay.add(position.getDay().add(position.getDifferenceDays()));
		}
		
		trip.setDay(totalDay);
		trip.saveEx();*/
		
		//@TommyAng
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		BigDecimal totalDay = Env.ZERO;
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
			sql.append("SELECT SUM(FuelAllocation) FROM HBC_FuelTripAllocation WHERE TCS_BBMPlan_ID="+bbm.get_ID());
			BigDecimal literAllocation = DB.getSQLValueBD(getName(), sql.toString());
			
			if(literAllocation==null)
				literAllocation=Env.ZERO;
			
			sql = new StringBuilder();
			sql.append("SELECT SUM(TotalLiters) FROM HBC_BBMActivity WHERE TCS_BBMPlan_ID="+bbm.get_ID());
			BigDecimal literBBMActivity=DB.getSQLValueBD(get_TrxName(), sql.toString());
			
			if(literBBMActivity==null)
				literBBMActivity=Env.ZERO;
			
			sql= new StringBuilder();
			sql.append("SELECT SUM(ActualShipUsage) FROM FuelUsage WHERE TCS_BBMPlan_ID="+bbm.get_ID());
			BigDecimal BBMUsage = DB.getSQLValueBD(get_TrxName(), sql.toString());
			
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
	}
	
	/**
	 * calculate bbm activity
	 */
	protected void calculateBBMActivity(){
		//@TommyAng
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
		
		//for (MHBCBBMActivity bbmActivity : trip.getBBMActivity()) {
			//BigDecimal fuel = trip.getHBC_Tugboat().getHBC_AuxiliaryEngine().getFuelConsumptionPerHour();
			//BigDecimal fuel = bbmActivity.getFuelConsumptionPerHour();
			//if(p_Hour == null)
				//p_Hour = Env.ZERO;
			
			//BigDecimal hour = bbmActivity.getHour();
		
		/*
		BigDecimal totalDay = Env.ZERO;
		for (MPosition position : trip.getPosition()) {
			totalDay = totalDay.add(position.getDay().add(position.getDifferenceDays()));
		}
		
		trip.setDay(totalDay);
		trip.saveEx();
		*/
		
		/*
		for (MBBMActivity bbmActivity : trip.getBBMActivity()) {
			//BigDecimal fuel = trip.getHBC_Tugboat().getHBC_AuxiliaryEngine().getFuelConsumptionPerHour();
			BigDecimal fuel = bbmActivity.getFuelConsumptionPerHour();
			//if(p_Hour == null)
				//p_Hour = Env.ZERO;
			
			BigDecimal hour = bbmActivity.getHour();
			BigDecimal day = bbmActivity.getDay();
			BigDecimal percentage = (BigDecimal) bbmActivity.get_Value("Percentage");
			
			BigDecimal totalLiters = day.multiply(hour).multiply(fuel);
			if(percentage.compareTo(Env.ZERO) > 0)
				totalLiters = totalLiters.multiply(percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			
			bbmActivity.setTotalLiters(totalLiters);
			bbmActivity.saveEx();
		}
		*/
		
		/*
		if(getFuelTripAllocation() > 0){
			X_HBC_FuelTripAllocation fuelAllocation = new X_HBC_FuelTripAllocation(getCtx(), getFuelTripAllocation(), get_TrxName());
			totalDay = totalDay.subtract(fuelAllocation.getDay());
			
			trip.setTripAllocationDay(fuelAllocation.getDay());
			trip.setLiterAllocation(fuelAllocation.getLiterAllocation());
			trip.saveEx();
		}*/
		
		
		BigDecimal fuel = bbm.getHBC_Tugboat().getHBC_AuxiliaryEngine()
				.getFuelConsumptionPerHour();

		if (p_Hour == null)
			p_Hour = Env.ZERO;

		BigDecimal hour = (p_Hour.compareTo(Env.ZERO) <= 0) ? new BigDecimal(15)
				: p_Hour;

		//BigDecimal totalLiters = totalDay.multiply(hour).multiply(fuel);
		
		//@TommyAng
		//BigDecimal totalLiters = dayForBBM.multiply(hour).multiply(fuel);
		
		for(int i : getFuelAllocationIDs()){
			
			//@TommyAng
			/*boolean match = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, "TCS_BBMPlan_ID="+bbm.get_ID(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.match();
			if(!match){*/
				
			//@TommyAng
			MFuelTripAllocation fta = new MFuelTripAllocation(getCtx(), i, get_TrxName());
			
			//if(fta.get_ValueAsBoolean("IsTripPulang") || fta.get_ValueAsInt("HBC_BBMActivity_ID")>0)
			if(fta.getHBC_TripType_ID().equals(PULANG_KOSONG) 
					|| fta.getHBC_TripType_ID().equals(PULANG_LENGGANG)
					|| fta.getHBC_TripType_ID().equals(PULANG_MUATAN)
					|| fta.get_ValueAsInt("HBC_BBMActivity_ID")>0)
				continue;
			
			X_HBC_BBMActivity bbmActivity = new X_HBC_BBMActivity(getCtx(), 0, get_TrxName());
			
			
			BigDecimal dayBBM = (BigDecimal) fta.get_Value("DayActual");
			
			//if(fta.get_ValueAsBoolean("IsTripDalam"))
			if(fta.getHBC_TripType_ID().equals(DALAM)
					|| fta.getHBC_TripType_ID().equals(DALAM_KOSONG)
					|| fta.getHBC_TripType_ID().equals(DALAM_LENGGANG)
					|| fta.getHBC_TripType_ID().equals(DALAM_MUATAN))
				dayBBM = dayBBM.subtract(fta.getDay());
			/*
			else
				dayBBM = fta.getDay();
			*/
			BigDecimal totalLiters = dayBBM.multiply(hour).multiply(fuel);
			
			bbmActivity.setAD_Org_ID(bbm.getAD_Org_ID());
			//bbmActivity.setHBC_Trip_ID(bbm.getHBC_Trip_ID());
			bbmActivity.set_ValueOfColumn("TCS_BBMPlan_ID", bbm.get_ID());
			bbmActivity.setC_Activity_ID(p_C_Activity_ID);
			bbmActivity.setHBC_Tugboat_ID(bbm.getHBC_Tugboat_ID());
			//bbmActivity.setDay(dayForBBM);
			bbmActivity.setDay(dayBBM);
			bbmActivity.setFuelConsumptionPerHour(fuel);
			bbmActivity.setHour(hour);
			bbmActivity.setTotalLiters(totalLiters);
			bbmActivity.saveEx();
			
			fta.set_ValueOfColumn("HBC_BBMActivity_ID", bbmActivity.get_ID());
			fta.saveEx();
			//}
		}
	}
	
	/**
	 * calculate if activity is have fuel quota
	 */
	protected void calculateShifting(){
		
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());

		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		//for (MPosition position : bbm.getPosition()) {
			for (MShipActivity shipActivity : getShipActivityByDate(bbm.get_ID())) {
				
				MActivity activity = (MActivity) shipActivity.getC_Activity();
				boolean isFuelQuota = activity.get_ValueAsBoolean("IsFuelQuota");
				
				if(isFuelQuota || isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
				
					boolean match = new Query(getCtx(), MBBMActivity.Table_Name, "TCS_BBMPlan_ID="+bbm.get_ID()
							+" AND HBC_ShipActivity_ID="+shipActivity.getHBC_ShipActivity_ID(), get_TrxName())
					.setClient_ID()
					.setOnlyActiveRecords(true)
					.match();
					
					if(match)
						continue;
					
					MBBMActivity bbmActivity = new MBBMActivity(getCtx(), 0, get_TrxName());
					bbmActivity.setAD_Org_ID(shipActivity.getAD_Org_ID());
					bbmActivity.set_ValueOfColumn("TCS_BBMPlan_ID", bbm.get_ID());
					//bbmActivity.setHBC_Trip_ID(trip.getHBC_Trip_ID());
					bbmActivity.setC_Activity_ID(p_C_Activity_ID);
					bbmActivity.setHBC_Tugboat_ID(bbm.getHBC_Tugboat_ID());
					bbmActivity.set_ValueOfColumn("HBC_ShipActivity_ID", shipActivity.get_ID());
					bbmActivity.setDay(shipActivity.getDay());
					bbmActivity.setHour(shipActivity.getHour());
					//bbmActivity.set_CustomColumn("DateFrom", shipActivity.get_Value("DateFrom"));
					//bbmActivity.set_CustomColumn("DateTo", shipActivity.get_Value("DateTo"));
					bbmActivity.set_CustomColumn("DateFrom", shipActivity.get_Value("StartDate"));
					bbmActivity.set_CustomColumn("DateTo", shipActivity.get_Value("FinishDate"));
					bbmActivity.set_CustomColumn("From_PortPosition_ID", shipActivity.getHBC_Position().getHBC_PortPosition_ID());
					//@KevinY 
					bbmActivity.set_CustomColumn("Comments", shipActivity.getDescription());
					//@KevinY end
					if(shipActivity.get_ValueAsInt("TugboatPartner_ID") > 0)
						bbmActivity.set_ValueOfColumn("TugboatPartner_ID", shipActivity.get_ValueAsInt("TugboatPartner_ID"));
					
					BigDecimal fuelConsump = bbm.getHBC_Tugboat().getHBC_MainEngine().getFuelConsumptionMain();
					BigDecimal fuelQuota = shipActivity.getFuelPercentageQuota();
					BigDecimal hour = shipActivity.getHour();
					//fuelQuota = fuelQuota.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
					
					//@phie
					// Request from Hadi
					// Note : Running AE is Fuel Quota, but if activity is RAE, get fuelComsumptionMain from auxiliaryEngine, not mainEngine
					if(shipActivity.getC_Activity().getValue().equals("RAE"))
						fuelConsump = bbm.getHBC_Tugboat().getHBC_AuxiliaryEngine().getFuelConsumptionPerHour();
					//end phie
					
					if(isFuelQuota || isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
						bbmActivity.setC_Activity_ID(shipActivity.getC_Activity_ID());
						bbmActivity.set_ValueOfColumn("FuelPercentageQuota", fuelQuota);
						bbmActivity.set_ValueOfColumn("FuelConsumptionMain", fuelConsump);
						fuelQuota = fuelQuota.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
						//@phie if activity is bunkering or fuel transfer, it doesn't have to set total liters
						if(!isBunkering(shipActivity) && !isFuelTransfer(shipActivity)){
							bbmActivity.setTotalLiters(fuelQuota.multiply(fuelConsump).multiply(hour));
						}//end phie
						if(isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
							bbmActivity.set_ValueOfColumn("Liter", shipActivity.getLiter());
						}
					}
					bbmActivity.saveEx();
					
					if(isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
						/*
						shipActivity.setProcessed(true);
						shipActivity.saveEx();
						*/
						StringBuilder sb = new StringBuilder();
						sb.append("UPDATE HBC_ShipActivity SET Processed = 'Y' WHERE HBC_ShipActivity_ID=?");
						int no = DB.executeUpdate(sb.toString(), new Object[]{shipActivity.getHBC_ShipActivity_ID()}, true, get_TrxName());
						log.info("UPDATED Processed HBC_ShipActivity#"+no);
					}
					
				}//end if
			}//end for 2
		//}//end for 1
		
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
		int tripID = bbm.get_ValueAsInt("CurrentTrip_ID");
		int firstTrip_ID = bbm.get_ValueAsInt("HBC_Trip_ID");
		//for (MPosition position : trip.getPosition()) {
			for (MShipActivity shipActivity : bbm.getShipActivity()) {
				//jika bunkering dan fuel transfer, copy ke tab bbm activity, taro di shifting - DONE
				// tambah column liter di bbm activity - DONE
				// stelah copy, fuel transfer dan bunkering, process = 'Y'
				liter = liter.add(shipActivity.getLiter());
				/*Comment by @Phie total liter just get from bbm activity
				totalLiter = totalLiter.add(shipActivity.getTotalLiters());
				*/
				//fuelBalanceNew = fuelBalanceNew.add(shipActivity.getFuelBalance());
			}
		//}
		
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
			String fb = "select fuelbalance from hbc_shipactivity where hbc_position_id in (select hbc_position_id from hbc_position where hbc_trip_id = ?) and fuelbalance>0";
			fuelBalanceNew = DB.getSQLValueBD(null, fb, tripID);
		}	
		*/
		//end phie
				
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
		//BigDecimal BBMUsage = trip.getBBMUsage();
		//BigDecimal totalStandBy = getSumTotalLiterActivity();
		BigDecimal shipFuelUsage=Env.ZERO;
		for(MFuelUsage fuel:bbm.getFuelUsageID()){
			 shipFuelUsage=shipFuelUsage.add(fuel.getUsageDiff());
		}
		//BigDecimal shipFuelUsage = BBMUsage.subtract(totalLiter);
		bbm.setShipFuelUsage(shipFuelUsage);
		bbm.saveEx();
				
		// set summary fuel adjustment
		BigDecimal sumFuelAdj = getSumFuelAdjustment();
		BigDecimal bbmusagecal=bbm.getTotalLiters().add(bbm.getShipFuelUsage());
		//BigDecimal fuelDisc = bbm.getLiter().subtract(bbmusagecal).subtract(fuelBalanceNew);
		//BigDecimal fuelDisc = bbm.getLiter().subtract(bbmusagecal).subtract(fuelBalanceNew).subtract(sumFuelAdj);
		BigDecimal fuelDisc = bbm.getLiter().subtract(bbmusagecal).subtract(fuelBalanceNew).add(sumFuelAdj);
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
		
		String where = "TCS_BBMPlan_ID="+p_TCS_BBMPlan_ID;
		int[] ids = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}
	
	/**
	 * @TommyAng
	 * @return array of HBC_BBMActivity_ID
	 * 
	 */
	protected int[] getFuelAllocationIDs(){
		
		String where = "TCS_BBMPlan_ID="+p_TCS_BBMPlan_ID;
		int[] ids = new Query(getCtx(), X_HBC_FuelTripAllocation.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}
	
	/**
	 * @return fuel trip allocation id
	 */
	protected int getFuelTripAllocation(){
		
		//@TommyAng
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		StringBuilder where = new StringBuilder();
//		where.append("HBC_TugboatCategory_ID="+trip.getHBC_Tugboat().getHBC_TugboatCategory_ID())
//		.append(" AND From_PortPosition_ID="+trip.getFrom_PortPosition_ID())
//		.append(" AND To_PortPosition_ID="+trip.getTo_PortPosition_ID());
		where.append("TCS_BBMPlan_ID="+bbm.get_ID());
		
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
		
		String where = "TCS_BBMPlan_ID="+p_TCS_BBMPlan_ID;
		int[] ids = new Query(getCtx(), X_HBC_BBMAdjustment.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}

	/**
	 * @param MShipActivity
	 * @return true if activity fuel transfer
	 */
	protected boolean isFuelTransfer(MShipActivity shipActivity){
		
		boolean retValue = false;
		if(shipActivity.getC_Activity().getValue().equals("FTR"))
			retValue = true;
		
		return retValue;
	}
	
	/**
	 * @param MShipActivity
	 * @return true if bunkering
	 */
	protected boolean isBunkering(MShipActivity shipActivity){
		
		boolean retValue = false;
		if(shipActivity.getC_Activity().getValue().equals("BUN"))
			retValue = true;
		
		return retValue;
		
	}
	
	public MShipActivity[] getShipActivityByDate(int BBM_ID) {
		List<MShipActivity> list = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "TCS_BBMPlan_ID=? AND StartDate >= ?", get_TrxName())
		.setParameters(BBM_ID, p_Date)
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate ASC, FinishDate, HBC_ShipActivity_ID")
		.list();
		
		return list.toArray(new MShipActivity[list.size()]);
		
	}
}
