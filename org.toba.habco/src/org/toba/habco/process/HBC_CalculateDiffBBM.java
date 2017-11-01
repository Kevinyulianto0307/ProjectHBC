package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MBBMActivity;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MFuelTripAllocation;
import org.toba.habco.model.MFuelUsage;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.X_FuelUsage;
import org.toba.habco.model.X_HBC_BBMActivity;
import org.toba.habco.model.X_HBC_BBMAdjustment;
import org.toba.habco.model.X_HBC_DiffRecord;
import org.toba.habco.model.X_HBC_FuelTripAllocation;

public class HBC_CalculateDiffBBM extends SvrProcess{
	
	//TommyAng
	//public int p_HBC_Trip_ID = 0;
	protected int p_TCS_BBMPlan_ID = 0;
	protected int p_C_Activity_ID = 0;
	protected BigDecimal p_Hour = Env.ZERO;
	
	protected BigDecimal dayForBBM = Env.ZERO;
	//end TommyAng
	@Override
	protected void prepare() {
		//TommyAng
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			/*else if (name.equals("HBC_Trip_ID")){
				p_HBC_Trip_ID = para[i].getParameterAsInt();
			}*/
			else if (name.equals("TCS_BBMPlan_ID")){
				p_TCS_BBMPlan_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_Activity_ID")){
				p_C_Activity_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("Hour")){
				p_Hour = para[i].getParameterAsBigDecimal();
			}
		}
		//end TommyAng
	}

	@Override
	protected String doIt() throws Exception {
		
		int FuelUsage_ID = getRecord_ID();
		X_FuelUsage fuelUsage = new X_FuelUsage(getCtx(), FuelUsage_ID, get_TrxName());
		
		
		
		//MTrip trip = new MTrip(getCtx(), fuelUsage.getHBC_Trip_ID(), get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), fuelUsage.getTCS_BBMPlan_ID(), get_TrxName());
		
		BigDecimal fuelAllocations = Env.ZERO;
		BigDecimal totalLiters = Env.ZERO;
		int i =0;
		
		//
		int lastFuelID = new Query(getCtx(), X_FuelUsage.Table_Name, "TCS_BBMPlan_ID = "+bbm.get_ID(), get_TrxName())
		.setOrderBy("FuelUsage_ID DESC")
		.firstId();
		//
		
		if(FuelUsage_ID != lastFuelID)
			return "";
			
		//for (MFuelTripAllocation tripAlloc : trip.getFuelTripAllocation()) {
		for (MFuelTripAllocation tripAlloc : bbm.getFuelTripAllocation()) {
			//TODO @ayonk ask bella for this validation
			if(tripAlloc.isProcess() || tripAlloc.getFuelAllocation().compareTo(Env.ZERO) < 0)
				continue;
			
			fuelAllocations = fuelAllocations.add(tripAlloc.getFuelAllocation());
			tripAlloc.setIsProcess(true);
			tripAlloc.saveEx();
			
			X_HBC_DiffRecord diffRec = new X_HBC_DiffRecord(getCtx(), 0, get_TrxName());
			diffRec.setFuelUsage_ID(FuelUsage_ID);
			diffRec.setFuelAllocation(tripAlloc.getFuelAllocation());
			diffRec.set_ValueOfColumn("HBC_FuelTripAllocation_ID", tripAlloc.getHBC_FuelTripAllocation_ID());
			diffRec.saveEx();
			i++;
		}
		
		//for (MBBMActivity bbmActivity : trip.getBBMActivity()) {
		for (MBBMActivity bbmActivity : bbm.getBBMActivity()) {	
			//TODO @ayonk ask bella for this validation
			if(bbmActivity.isProcess() || bbmActivity.getTotalLiters().compareTo(Env.ZERO) < 0)
				continue;
			
			if(!isBunkering(bbmActivity) || !isFuelTransfer(bbmActivity))
				totalLiters = totalLiters.add(bbmActivity.getTotalLiters());
			bbmActivity.setIsProcess(true);
			bbmActivity.saveEx();
			
			X_HBC_DiffRecord diffRec = new X_HBC_DiffRecord(getCtx(), 0, get_TrxName());
			diffRec.setFuelUsage_ID(FuelUsage_ID);
			diffRec.setFuelAllocation(bbmActivity.getTotalLiters());
			diffRec.set_ValueOfColumn("HBC_BBMActivity_ID", bbmActivity.getHBC_BBMActivity_ID());
			diffRec.set_ValueOfColumn("C_Activity_ID", bbmActivity.get_Value("C_Activity_ID"));
			diffRec.set_ValueOfColumn("DateFrom", bbmActivity.get_Value("DateFrom"));
			diffRec.saveEx();
			i++;
			
			bbmActivity.setIsProcess(true);
			bbmActivity.saveEx();
		}
		

		BigDecimal sumFuel = fuelAllocations.add(totalLiters);
		
		
		if (i==0)
			sumFuel = (BigDecimal) fuelUsage.get_Value("EstFuelUsage");
		else 
			fuelUsage.set_ValueOfColumn("EstFuelUsage", sumFuel);
		//BigDecimal bbmUsageDiff = fuelUsage.getFuelShipBalance().subtract(sumFuel);
		BigDecimal bbmUsageDiff = ((BigDecimal) fuelUsage.get_Value("ActualShipUsage")).subtract(sumFuel);
		
		
		//fuelUsage.set_ValueOfColumn("TotalBBMUsage", sumFuel);
		//fuelUsage.set_ValueOfColumn("EstFuelUsage", sumFuel);
		fuelUsage.setUsageDiff(bbmUsageDiff);
		fuelUsage.saveEx();
		
		//TommyAng
		//p_HBC_Trip_ID = fuelUsage.getHBC_Trip_ID();
		p_TCS_BBMPlan_ID = fuelUsage.getTCS_BBMPlan_ID();
		
		calculateFuelAllocation();
		calculateBBMActivity();
		calculateShifting();
		calculateLiter();
		
		int HBC_Trip_ID = bbm.get_ValueAsInt("CurrentTrip_ID");
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		BigDecimal actualShipBalance = (BigDecimal) fuelUsage.get_Value("ActualShipBalance");
		BigDecimal currentBalanceActual = actualShipBalance.add(getSumFuelAdjustment());
		BigDecimal fuelActual = currentBalanceActual.add((BigDecimal) bbm.get_Value("FuelAdjustment"));
		bbm.set_CustomColumn("CurrentBalanceActual", currentBalanceActual);
		bbm.setFuelActual(fuelActual);
		bbm.saveEx();
		trip.set_CustomColumn("FuelBalanceBBMPlan", fuelActual);
		trip.saveEx();
		
		return "Created";
		//end TommyAng
		
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
		if(bbmActivity.getC_Activity_ID() <= 0)
			return false;
		
		if(bbmActivity.getC_Activity().getValue().equals("BUN"))
			retValue = true;
		
		return retValue;
		
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	/**
	 * calculate fuel allocation for 1 trip
	 */
	protected void calculateFuelAllocation(){
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		BigDecimal totalDay = Env.ZERO;
		//for (MPosition position : trip.getPosition()) {
		for (MShipActivity ship : bbm.getShipActivity()) {
			totalDay = totalDay.add(ship.getDay());
		}
		
		/*trip.setDay(totalDay);
		trip.saveEx();*/
		
		bbm.setDay(totalDay);
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
			sql.append("SELECT SUM(FuelShipBalance) FROM FuelUsage WHERE TCS_BBMPlan_ID="+bbm.get_ID());
			BigDecimal BBMUsage = DB.getSQLValueBD(get_TrxName(), sql.toString());
			
			bbm.setTripAllocationDay(fuelAllocation.getDay());
			bbm.setLiterAllocation(literAllocation);
			bbm.setTotalLiters(literAllocation.add(literBBMActivity));
			bbm.setBBMUsage(BBMUsage);
			bbm.saveEx();
		}
		//comment by @TommyAng req by Hadi
		/*
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
		
		
		/*BigDecimal fuel = trip.getHBC_Tugboat().getHBC_AuxiliaryEngine()
				.getFuelConsumptionPerHour();*/
		BigDecimal fuel = bbm.getHBC_Tugboat().getHBC_AuxiliaryEngine()
				.getFuelConsumptionPerHour();

		if (p_Hour == null)
			p_Hour = Env.ZERO;

		BigDecimal hour = (p_Hour.compareTo(Env.ZERO) <= 0) ? new BigDecimal(15)
				: p_Hour;

		//BigDecimal totalLiters = totalDay.multiply(hour).multiply(fuel);
		BigDecimal totalLiters = dayForBBM.multiply(hour).multiply(fuel);
		
		boolean match = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, "TCS_BBMPlan_ID="+bbm.getTCS_BBMPlan_ID(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.match();
		/*
		if(!match){
			X_HBC_BBMActivity bbmActivity = new X_HBC_BBMActivity(getCtx(), 0, get_TrxName());
			bbmActivity.setAD_Org_ID(bbm.getAD_Org_ID());
			//bbmActivity.setHBC_Trip_ID(bbm.getTCS_BBMPlan_ID());
			bbmActivity.set_ValueOfColumn("TCS_BBMPlan_ID", bbm.get_ID());
			bbmActivity.setC_Activity_ID(p_C_Activity_ID);
			bbmActivity.setHBC_Tugboat_ID(bbm.getHBC_Tugboat_ID());
			bbmActivity.setDay(dayForBBM);
			bbmActivity.setFuelConsumptionPerHour(fuel);
			bbmActivity.setHour(hour);
			bbmActivity.setTotalLiters(totalLiters);
			bbmActivity.saveEx();
		}
		*/
	}
	
	/**
	 * calculate if activity is have fuel quota
	 */
	protected void calculateShifting(){
		
		//MTrip trip = new MTrip(getCtx(), p_HBC_Trip_ID, get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), p_TCS_BBMPlan_ID, get_TrxName());
		
		//for (MPosition position : trip.getPosition()) {
			//for (MShipActivity shipActivity : position.getShipActivity()) {
			for (MShipActivity shipActivity : bbm.getShipActivity()) {
				
				//MActivity activity = (MActivity) shipActivity.getC_Activity();
				//boolean isFuelQuota = activity.get_ValueAsBoolean("IsFuelQuota");
				
				if(isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
				
					boolean match = new Query(getCtx(), MBBMActivity.Table_Name, "TCS_BBMPlan_ID="+bbm.getTCS_BBMPlan_ID()
							+" AND HBC_ShipActivity_ID="+shipActivity.getHBC_ShipActivity_ID(), get_TrxName())
					.setClient_ID()
					.setOnlyActiveRecords(true)
					.match();
					
					if(match)
						continue;

					MBBMActivity bbmActivity = new MBBMActivity(getCtx(), 0, get_TrxName());
					bbmActivity.setAD_Org_ID(shipActivity.getAD_Org_ID());
					bbmActivity.setHBC_Trip_ID(bbm.getTCS_BBMPlan_ID());
					bbmActivity.setC_Activity_ID(p_C_Activity_ID);
					bbmActivity.setHBC_Tugboat_ID(bbm.getHBC_Tugboat_ID());
					bbmActivity.set_ValueOfColumn("HBC_ShipActivity_ID", shipActivity.get_ID());
					bbmActivity.setDay(shipActivity.getDay());
					bbmActivity.setHour(shipActivity.getHour());
					bbmActivity.set_CustomColumn("DateFrom", shipActivity.get_Value("DateFrom"));
					bbmActivity.set_CustomColumn("DateTo", shipActivity.get_Value("DateTo"));
					//bbmActivity.set_CustomColumn("HBC_PortPosition_ID", position.getHBC_PortPosition_ID());
					bbmActivity.set_CustomColumn("From_PortPosition_ID", shipActivity.getHBC_Position().getHBC_PortPosition_ID());
					
					BigDecimal fuelConsump = bbm.getHBC_Tugboat().getHBC_MainEngine().getFuelConsumptionMain();
					BigDecimal fuelQuota = shipActivity.getFuelPercentageQuota();
					BigDecimal hour = shipActivity.getHour();
					//fuelQuota = fuelQuota.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
					
					//if(isFuelQuota || isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
					//if(isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
					bbmActivity.setC_Activity_ID(shipActivity.getC_Activity_ID());
					bbmActivity.set_ValueOfColumn("FuelPercentageQuota", fuelQuota);
					bbmActivity.set_ValueOfColumn("FuelConsumptionMain", fuelConsump);
					fuelQuota = fuelQuota.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
					bbmActivity.setTotalLiters(fuelQuota.multiply(fuelConsump).multiply(hour));
						//if(isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
					bbmActivity.set_ValueOfColumn("Liter", shipActivity.getLiter());
						//}
					//}
					bbmActivity.saveEx();
					
					//if(isBunkering(shipActivity) || isFuelTransfer(shipActivity)){
					shipActivity.setProcessed(true);
					shipActivity.saveEx();
					//}
					
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
		//for (MPosition position : trip.getPosition()) {
			for (MShipActivity shipActivity : bbm.getShipActivity()) {
				//jika bunkering dan fuel transfer, copy ke tab bbm activity, taro di shifting - DONE
				// tambah column liter di bbm activity - DONE
				// stelah copy, fuel transfer dan bunkering, process = 'Y'
				liter = liter.add(shipActivity.getLiter());
				totalLiter = totalLiter.add(shipActivity.getTotalLiters());
				//fuelBalanceNew = fuelBalanceNew.add(shipActivity.getFuelBalance());
			}
		//}
	
		String fb = "select fuelbalance from hbc_shipactivity where hbc_position_id in (select hbc_position_id from hbc_position where hbc_trip_id = ?) and fuelbalance>0";
		fuelBalanceNew = DB.getSQLValueBD(null, fb, tripID);	
			
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
		bbm.setFuelBalanceNew(fuelBalanceNew);
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
		//BigDecimal bbmusagecal=bbm.getTotalLiters().add(bbm.getShipFuelUsage());
		//BigDecimal fuelDisc = bbm.getLiter().subtract(bbmusagecal).subtract(fuelBalanceNew);
		//BigDecimal fuelDisc = bbm.getLiter().subtract(bbmusagecal).subtract(fuelBalanceNew).subtract(sumFuelAdj);
		bbm.setFuelAdjustment(sumFuelAdj);
		//bbm.setFuelActual(fuelBalanceNew.add(sumFuelAdj));
		//bbm.setFuelUsageDiscrepancy(fuelDisc);
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
}
