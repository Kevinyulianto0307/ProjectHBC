package org.toba.habco.ar.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MDemurrage;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;

public class HBC_CalculateDemurrage extends SvrProcess {


	static protected int ACTIVITY_LOADING_ID = 0;
	static protected int ACTIVITY_DISCHARGING_ID = 0;
	int p_HBC_Contract_ID;
	//boolean created =false;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(MTrip.COLUMNNAME_HBC_Contract_ID)){
				p_HBC_Contract_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		String msg="";
		if(p_HBC_Contract_ID<=0){
			msg = "No Contract Selected";
			return msg;
		}
		
		MContract contract = new MContract(getCtx(),p_HBC_Contract_ID,get_TrxName());
		int demurrageid = new Query(getCtx(),MDemurrage.Table_Name,"HBC_Contract_ID="+p_HBC_Contract_ID,get_TrxName())
									.setOnlyActiveRecords(true)
									.setClient_ID()
									.firstId();
		if(demurrageid == -1)
			throw new AdempiereException("Process aborted.. Please setup demurrage for related contract");
		
		MDemurrage demurrage = new MDemurrage(getCtx(),demurrageid,get_TrxName());
		MTrip[] trips = getAllTrip();
		
		if(trips.length == 0)
			throw new AdempiereException("Process aborted.. No Trip for selected contract");
		
		MARCalculation ARCalc = null;
		
		String whereClause = "Name = 'Loading'";
		ACTIVITY_LOADING_ID = new Query(getCtx(), "C_Activity", whereClause, get_TrxName())
								.firstId(); 
		
		whereClause = "Name = 'Discharging'";
		ACTIVITY_DISCHARGING_ID = new Query(getCtx(), "C_Activity", whereClause, get_TrxName())
		.firstId();
		
		if(ACTIVITY_LOADING_ID == 0 || ACTIVITY_DISCHARGING_ID == 0)
			throw new AdempiereException("There's no loading or discharging activity in data master");
		
		if(getARCalculationDMG_ID()>0){	
			ARCalc = new MARCalculation(getCtx(), getARCalculationDMG_ID(), get_TrxName());
			log.info("Use Current  AR Calculation");
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ShipActivity SET DMG_ARCalculationLine_ID = NULL "
					+ "WHERE DMG_ARCalculationLine_ID IN ("
					+ "SELECT HBC_ARCalculationLine_ID FROM HBC_ARCalculationLine WHERE HBC_ARCalculation_ID = ? AND Processed = 'N') ");
			int n = DB.executeUpdateEx(sb.toString(), new Object[]{ARCalc.getHBC_ARCalculation_ID()}, get_TrxName());
			log.info("UPDATED Ship Activity :"+n);
			
			sb = new StringBuilder();
			sb.append("DELETE FROM HBC_ARCalculationLine WHERE HBC_ARCalculation_ID = ? AND Processed = 'N'");
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{ARCalc.getHBC_ARCalculation_ID()}, get_TrxName());
			log.info("DELETE AR Calc Line :"+no);
		}
		else{
			ARCalc = new MARCalculation(getCtx(), 0, get_TrxName());	
			ARCalc.setAD_Org_ID(contract.getAD_Org_ID());
			ARCalc.setHBC_Contract_ID(contract.getHBC_Contract_ID());
			//ARCalc.setC_Project_ID(contract.getC_Project_ID());
			ARCalc.setC_BPartner_ID(contract.getCustomer_BPartner_ID());
			if(contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SPAL) || contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment))
			{
				ARCalc.setHBC_Tugboat_ID(contract.getHBC_Tugboat_ID());
				ARCalc.setHBC_Barge_ID(contract.getHBC_Barge_ID());
			}
			ARCalc.setFrom_PortPosition_ID(contract.getFrom_PortPosition_ID());
			ARCalc.setTo_PortPosition_ID(contract.getTo_PortPosition_ID());
			//calc.setC_Period_ID(getC_Period_ID());
			//ARCalc.setStartDate(contract.getDateStart());
			//ARCalc.setFinishDate(contract.getEndDate());
			ARCalc.setARCalculationType(MARCalculation.ARCALCULATIONTYPE_Demurrage);
			ARCalc.saveEx();
			log.info("New Current AR Calculation");
		}
			
		BigDecimal totalDemurrageFreight=Env.ZERO;
		BigDecimal totalDemurrageWeather=Env.ZERO;
		BigDecimal totalDay=Env.ZERO;
		BigDecimal DemurrageDay=Env.ZERO;
		BigDecimal totalChargedDay=Env.ZERO;	
		BigDecimal realFreightCharge = Env.ZERO;
		BigDecimal realWeatherCharge = Env.ZERO;

		if(!demurrage.isProrate()){
			//@Phie new version of prorate
			for(MTrip trip:trips){	
				//created=false;
				int id = 0;
				log.info("Calculate Trip #"+trip.getDocumentNo());
				//RESET, make sure
				totalDay=Env.ZERO;
				totalDemurrageWeather=Env.ZERO;
				totalDemurrageFreight=Env.ZERO;
				totalChargedDay=Env.ZERO;
				
				//if(demurrage.getFreightPercentage().compareTo(Env.ZERO) == 0)
					//continue; //skip, no demurrage charge
				
				//LOADING
				totalDemurrageFreight = getTotalDemurrageDay(true,trip.getHBC_Trip_ID(), "Loading");
				totalDemurrageWeather = getTotalDemurrageDay(false, trip.getHBC_Trip_ID(), "Loading");
				totalDay = totalDemurrageFreight.add(totalDemurrageWeather);
				DemurrageDay = demurrage.getTotalLoadingDay(); //prefer this variable change to loading day
				totalChargedDay = totalDay.subtract(DemurrageDay);
				int hbc_tugboat_id = getTugboat(trip.getHBC_Trip_ID(), "Loading");
				int hbc_barge_id = getBarge(trip.getHBC_Trip_ID(), "Loading");
				
				if(totalChargedDay.compareTo(Env.ZERO)>0){ //total day > loading day
					//IF Total demurrage freight less or quals than loading day, there's no charge for freight
					realFreightCharge = (totalDemurrageFreight.compareTo(DemurrageDay) <= 0) ? 
											(Env.ZERO) : (totalDemurrageFreight.subtract(DemurrageDay));
					realWeatherCharge = totalChargedDay.subtract(realFreightCharge);			
					
					//NOTE : In this case, there's a condition when both freight and weather's day charge greater than zero
					//and percentage freight 100 and percentage weather 0, but system create ar calc line for weather charge 
					//with weather day charge greater than zero and amount charge equals zero
					//--> BUT if this situation happen, there's wrong data ship activity 
					
					if(realFreightCharge.compareTo(Env.ZERO) > 0){
						id = CreateARCalcLine(0, ARCalc,totalDay,totalChargedDay,realFreightCharge,DemurrageDay,
													demurrage,false,trip.getHBC_Trip_ID(), hbc_tugboat_id, hbc_barge_id);
						if(id>0)//CREATE MATCHING
							createMatching(id, trip.getHBC_Trip_ID(), true, "Loading");
					}
						
					if(realWeatherCharge.compareTo(Env.ZERO) > 0){
						id = CreateARCalcLine(0, ARCalc,totalDay,totalChargedDay,realWeatherCharge,DemurrageDay,
													demurrage,true,trip.getHBC_Trip_ID(), hbc_tugboat_id, hbc_barge_id);
						if(id>0)//CREATE MATCHING
							createMatching(id, trip.getHBC_Trip_ID(), false, "Loading");
					}
				}//END LOADING
				
				//RESET, make sure
				totalDay=Env.ZERO;
				totalDemurrageWeather=Env.ZERO;
				totalDemurrageFreight=Env.ZERO;
				totalChargedDay=Env.ZERO;
				int hbc_tugboat_id_dis = getTugboat(trip.getHBC_Trip_ID(), "Loading"); //TODO dari user
				int hbc_barge_id_dis = getBarge(trip.getHBC_Trip_ID(), "Loading"); //TODO dari user
				
				//UNLOADING
				totalDemurrageFreight = getTotalDemurrageDay(true,trip.getHBC_Trip_ID(), "Discharging");
				totalDemurrageWeather = getTotalDemurrageDay(false,trip.getHBC_Trip_ID(), "Discharging");
				totalDay = totalDemurrageFreight.add(totalDemurrageWeather);
				DemurrageDay= demurrage.getTotalUnloadingDay();
				totalChargedDay=totalDay.subtract(DemurrageDay);
				
				if(totalChargedDay.compareTo(Env.ZERO)>0){
					//IF Total demurrage freight less or quals than unloading day, there's no charge for freight
					realFreightCharge = (totalDemurrageFreight.compareTo(DemurrageDay) <= 0) ? 
											(Env.ZERO) : (totalDemurrageFreight.subtract(DemurrageDay));
					realWeatherCharge = totalChargedDay.subtract(realFreightCharge);			
					
					if(realFreightCharge.compareTo(Env.ZERO) > 0){
						id = CreateARCalcLine(1, ARCalc,totalDay,totalChargedDay,realFreightCharge,DemurrageDay,
													demurrage,false,trip.getHBC_Trip_ID(),hbc_tugboat_id_dis, hbc_barge_id_dis);
						if(id>0)//CREATE MATCHING
							createMatching(id, trip.getHBC_Trip_ID(), true, "Discharging");
					}
						
					if(realWeatherCharge.compareTo(Env.ZERO) > 0){
						id = CreateARCalcLine(1, ARCalc,totalDay,totalChargedDay,realWeatherCharge,DemurrageDay,
													demurrage,true,trip.getHBC_Trip_ID(), hbc_tugboat_id_dis, hbc_barge_id_dis);
						if(id>0)//CREATE MATCHING
							createMatching(id, trip.getHBC_Trip_ID(), false, "Discharging");
					}
				}//end UNLOADING
				
				if(id > 0){
					String message = Msg.parseTranslation(getCtx(), "@GeneratedARCalculation@ "+ARCalc.getDocumentNo());
					addBufferLog(0, null, null, message, ARCalc.get_Table_ID(), ARCalc.get_ID());
				}else{
					String message = Msg.parseTranslation(getCtx(), "Trip No = "+trip.getDocumentNo()+" Has No Demurrage Charge");
					addBufferLog(0, null, null, message,  trip.get_Table_ID(), trip.get_ID());
				}
			}
		}else {
			for(MTrip trip:trips){	
				int id = 0;
				//MPosition[] positions=null;
				totalDay=Env.ZERO;
				totalChargedDay=Env.ZERO;
				totalDemurrageWeather=Env.ZERO;
				totalDemurrageFreight=Env.ZERO;
				//positions = getAllPositionLoadingUnloading(trip.getHBC_Trip_ID());
				DemurrageDay= new BigDecimal (demurrage.getProrateDay());
				totalDemurrageFreight = getTotalDemurrageDay(true,trip.getHBC_Trip_ID(),"LoadingDischarging");
				totalDemurrageWeather = getTotalDemurrageDay(false,trip.getHBC_Trip_ID(),"LoadingDischarging");
				totalDay = totalDemurrageFreight.add(totalDemurrageWeather);
				/*
				for(MPosition position:positions){
					MShipActivity[] sActivitys = getAllShipActivity(position.getHBC_Position_ID());
					for(MShipActivity sActivity:sActivitys){	
						if(sActivity.isDemurrageFreight())
							totalDemurrageFreight=totalDemurrageFreight.add(sActivity.getDay());
						else if(sActivity.isDemurrageWeather())
							totalDemurrageWeather=totalDemurrageWeather.add(sActivity.getDay());
						
						totalDay=totalDay.add(sActivity.getDay());
					}
				}
				*/
				int hbc_tugboat_id = getTugboat(trip.getHBC_Trip_ID(), "Loading");
				int hbc_barge_id = getBarge(trip.getHBC_Trip_ID(), "Loading");
				
				totalChargedDay=totalDay.subtract(DemurrageDay);
				if(totalChargedDay.compareTo(Env.ZERO)>0){
					if(totalDemurrageFreight.compareTo(Env.ZERO)>0 && totalChargedDay.subtract(totalDemurrageWeather).compareTo(Env.ZERO)>0){
						id=CreateARCalcLine(2, ARCalc,totalDay,totalChargedDay,totalChargedDay.subtract(totalDemurrageWeather),DemurrageDay,
								demurrage,false,trip.getHBC_Trip_ID(), hbc_tugboat_id, hbc_barge_id);
						if(id>0)
							createMatching(id, trip.getHBC_Trip_ID(), true, "LoadingDischarging");
					}
					
					if(totalDemurrageWeather.compareTo(Env.ZERO)>0 ){
						if(totalDemurrageWeather.compareTo(totalChargedDay)>0)
							totalDemurrageWeather=totalChargedDay;
						id=CreateARCalcLine(2, ARCalc,totalDay,totalChargedDay,totalDemurrageWeather,DemurrageDay,
								demurrage,true,trip.getHBC_Trip_ID(), hbc_tugboat_id, hbc_barge_id);
						//CREATE MATCHING
						if(id>0)
							createMatching(id, trip.getHBC_Trip_ID(), false, "LoadingDischarging");
					}
				}
				
				if(id > 0){
					String message = Msg.parseTranslation(getCtx(), "@GeneratedARCalculation@ "+ARCalc.getDocumentNo());
					addBufferLog(0, null, null, message, ARCalc.get_Table_ID(), ARCalc.get_ID());
				}else{
					String message = Msg.parseTranslation(getCtx(), "Trip No = "+trip.getDocumentNo()+" Has No Demurrage Charge");
					addBufferLog(0, null, null, message, trip.get_Table_ID(), trip.get_ID());
				}
		}
		}
		
		return msg;
	}
	
	private int getTugboat(int hbc_Trip_ID, String activity) {
		StringBuilder whereClause= new StringBuilder("HBC_ShipActivity.HBC_Position_ID IN (SELECT HBC_Position_ID FROM HBC_Position WHERE HBC_Trip_ID = ?)");
		if(activity.equals("Loading"))
				whereClause.append(" AND ca.name = 'Loading'");
		else
				whereClause.append(" AND ca.name = 'Discharging'");
		
		int hbc_shipActivity_id = new Query(getCtx(), MShipActivity.Table_Name, whereClause.toString(), get_TrxName())
									.addJoinClause("JOIN C_Activity ca ON ca.C_Activity_ID = HBC_ShipActivity.C_Activity_ID")
									.setParameters(new Object[]{hbc_Trip_ID})
									.setOrderBy("StartDate, isLastActivity, isSecondActivity, FinishDate, HBC_Shipactivity_ID")
									.firstId();
		
		if(hbc_shipActivity_id == 1)
			throw new AdempiereException("Trying to get tugboat for AR Calculation.. There's no loading activity..");
		MShipActivity shipActivity = new MShipActivity(getCtx(), hbc_shipActivity_id, get_TrxName());
		
		return shipActivity.getHBC_Tugboat_ID();
	}
	
	private int getBarge(int hbc_Trip_ID, String activity) {
		StringBuilder whereClause= new StringBuilder("HBC_ShipActivity.HBC_Position_ID IN (SELECT HBC_Position_ID FROM HBC_Position WHERE HBC_Trip_ID = ?)");
		if(activity.equals("Loading"))
				whereClause.append(" AND ca.name = 'Loading'");
		else
				whereClause.append(" AND ca.name = 'Discharging'");
		
		int hbc_shipActivity_id = new Query(getCtx(), MShipActivity.Table_Name, whereClause.toString(), get_TrxName())
									.addJoinClause("JOIN C_Activity ca ON ca.C_Activity_ID = HBC_ShipActivity.C_Activity_ID")
									.setParameters(new Object[]{hbc_Trip_ID})
									.setOrderBy("StartDate, isLastActivity, isSecondActivity, FinishDate, HBC_Shipactivity_ID")
									.firstId();
		
		if(hbc_shipActivity_id == 1)
			throw new AdempiereException("Trying to get tugboat for AR Calculation.. There's no loading activity..");
		MShipActivity shipActivity = new MShipActivity(getCtx(), hbc_shipActivity_id, get_TrxName());
		
		return shipActivity.getHBC_Barge_ID();
	}

	/*
	 *  get id ar calculation per contract
	 */
	protected int getARCalculationDMG_ID(){
		int ARCalculationID = new Query(getCtx(),MARCalculation.Table_Name,"HBC_Contract_ID="+p_HBC_Contract_ID+" AND ARCalculationType = '"+MARCalculation.ARCALCULATIONTYPE_Demurrage+"'",get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstId();
		
		return ARCalculationID;
	}
	
	public int CreateARCalcLine(int i, MARCalculation calc, BigDecimal totalday, BigDecimal totalChargedDay,
			BigDecimal RealCharged, BigDecimal DemurrageDay, MDemurrage demurrage,
			boolean isWeather, int HBC_Trip_ID, int hbc_tugboat_id, int hbc_barge_id){
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MARCalculationLine calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
		MContract contract = new MContract(getCtx(), calc.getHBC_Contract_ID(), get_TrxName());
		if(contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SPAL) || contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment))
		{
			calcLine.setHBC_Tugboat_ID(calc.getHBC_Tugboat_ID());
			calcLine.setHBC_Barge_ID(calc.getHBC_Barge_ID());
		}
		else if(contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_AnnualContract))
		{
			calcLine.setHBC_Tugboat_ID(hbc_tugboat_id);
			calcLine.setHBC_Barge_ID(hbc_barge_id);
		}
		calcLine.setFrom_PortPosition_ID(calc.getFrom_PortPosition_ID());
		calcLine.setTo_PortPosition_ID(calc.getTo_PortPosition_ID());
		calcLine.setHBC_Trip_ID(trip.getHBC_Trip_ID());
		calcLine.setM_Product_ID(demurrage.get_ValueAsInt("M_Product_ID"));
		calcLine.setHBC_Contract_ID(calc.getHBC_Contract_ID());
		calcLine.setC_BPartner_ID(calc.getC_BPartner_ID());
		calcLine.setC_Project_ID(trip.getC_Project_ID());
		calcLine.set_ValueOfColumn("TotalChargedDay", totalChargedDay);
		if(isWeather){
			calcLine.setDayToInvoice(demurrage.getWeatherPercentage().multiply(RealCharged).divide(Env.ONEHUNDRED));
			calcLine.set_ValueOfColumn("DemurragePercentage", demurrage.getWeatherPercentage());
			calcLine.setTotalAmt((demurrage.getWeatherPercentage().multiply(RealCharged).divide(Env.ONEHUNDRED)).multiply(demurrage.getDemurrageAmt()));
			calcLine.setQtyToInvoiced(demurrage.getWeatherPercentage().multiply(RealCharged).divide(Env.ONEHUNDRED)); // to invoice
		}
		else{
			calcLine.setDayToInvoice(demurrage.getFreightPercentage().multiply(RealCharged).divide(Env.ONEHUNDRED));
			calcLine.set_ValueOfColumn("DemurragePercentage", demurrage.getFreightPercentage());
			calcLine.setTotalAmt((demurrage.getFreightPercentage().multiply(RealCharged).divide(Env.ONEHUNDRED)).multiply(demurrage.getDemurrageAmt()));
			calcLine.setQtyToInvoiced(demurrage.getFreightPercentage().multiply(RealCharged).divide(Env.ONEHUNDRED)); // to invoice
		}
		calcLine.setDayCharge(RealCharged);
		calcLine.set_ValueOfColumn("TotalDay", totalday);
		calcLine.set_ValueOfColumn("DemurrageAmt", demurrage.getDemurrageAmt()); //TODO : confirm diffent between demurrage amt and unit price
		calcLine.set_ValueOfColumn("DemurrageDay", DemurrageDay);
		calcLine.setUnitPrice(demurrage.getDemurrageAmt()); // to invoice //TODO : confirm diffent between demurrage amt and unit price
		calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
		if(isWeather)
			calcLine.set_ValueOfColumn("IsWeather", true);
		else
			calcLine.set_ValueOfColumn("IsFreight", true);
		if(i>=1){
			calcLine.set_ValueOfColumn("IsLoading", false);
			if(i==1)
				calcLine.setC_Activity_ID(ACTIVITY_DISCHARGING_ID); //TEMPORARY SOLVED
		}
		else{
			calcLine.set_ValueOfColumn("IsLoading", true);
			calcLine.setC_Activity_ID(ACTIVITY_LOADING_ID); //TEMPORARY SOLVED
		}
		calcLine.setAD_Org_ID(calc.getAD_Org_ID());
		calcLine.setARCalculationType(MARCalculationLine.ARCALCULATIONTYPE_Demurrage);
		
		if (calcLine.save())
			return calcLine.getHBC_ARCalculationLine_ID();
		
		return -1;
	}
	/**
	 *  get all contract trip for calculate demurrage
	 */
	
	public MTrip[] getAllTrip(){
		List<MTrip> trips = new Query(getCtx(),MTrip.Table_Name,"HBC_Contract_ID="+p_HBC_Contract_ID,get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list();
		
		return trips.toArray(new MTrip[trips.size()]);
	}
	
	
	/**
	 *  get all position from trip where the position has loading and unloading
	 */
	/*
	public MPosition[] getAllPositionLoadingUnloading(int HBC_Trip_ID){
		
		List<MPosition> positions = new Query(getCtx(),MPosition.Table_Name,"HBC_Trip_ID="+HBC_Trip_ID+" AND ac.Name IN ('Loading','Discharging')",get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.addJoinClause("JOIN HBC_ShipActivity sa on HBC_Position.HBC_Position_ID=sa.HBC_Position_ID")
				.addJoinClause("JOIN C_Activity ac on sa.C_Activity_ID=ac.C_Activity_ID")
				.list();
		
		return positions.toArray(new MPosition[positions.size()]);
	}
	*/
	
	/**
	 *  get all position from trip where the position has loading
	 */
	/*
	public MPosition[] getAllPositionLoading(int HBC_Trip_ID){
		List<MPosition> positions = new Query(getCtx(),MPosition.Table_Name,"HBC_Position.HBC_Trip_ID="+HBC_Trip_ID+" AND ac.Name='Loading' ",get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.addJoinClause("JOIN HBC_ShipActivity sa on HBC_Position.HBC_Position_ID=sa.HBC_Position_ID")
				.addJoinClause("JOIN C_Activity ac on sa.C_Activity_ID=ac.C_Activity_ID")
				.list();
		
		return positions.toArray(new MPosition[positions.size()]);
	}
	*/
	
	/**
	 *  get all position from trip where the position has unloading
	 */
	/*
	public MPosition[] getAllPositionUnLoading(int HBC_Trip_ID){
		List<MPosition> positions = new Query(getCtx(),MPosition.Table_Name,"HBC_Position.HBC_Trip_ID="+HBC_Trip_ID+" AND ac.Name='Discharging' ",get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.addJoinClause("JOIN HBC_ShipActivity sa on HBC_Position.HBC_Position_ID=sa.HBC_Position_ID")
				.addJoinClause("JOIN C_Activity ac on sa.C_Activity_ID=ac.C_Activity_ID")
				.list();
		
		return positions.toArray(new MPosition[positions.size()]);
	}
	*/

	
	/**
	 *  get all ship activity from each position where the activity is demurrage weather or demurrage freight
	 */
	/*
	public MShipActivity[] getAllShipActivity(int HBC_Position_ID){
		List <MShipActivity> ShipActivitys= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+HBC_Position_ID+" AND (isDemurrageWeather='Y' OR isDemurrageFreight='Y')"
				,get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list();
		
		return ShipActivitys.toArray(new MShipActivity[ShipActivitys.size()]);
	}
	*/
	
	public BigDecimal getTotalDemurrageDay(boolean isFreight, int hbc_trip_id, String activity)
	{
		StringBuilder sb = new StringBuilder();
		BigDecimal totalDemurrageDay = Env.ZERO;
		sb.append("SELECT COALESCE(SUM(day),0) FROM HBC_ShipActivity sa WHERE ");
		if(isFreight)
			sb.append("isDemurrageFreight ");
		else
			sb.append("isDemurrageWeather ");
		sb.append("='Y' AND sa.HBC_Position_ID IN (SELECT HBC_Position.HBC_Position_ID FROM HBC_Position ");
		sb.append("JOIN HBC_ShipActivity sa2 on HBC_Position.HBC_Position_ID=sa2.HBC_Position_ID ");
		sb.append("JOIN C_Activity ac on sa2.C_Activity_ID=ac.C_Activity_ID ");
		sb.append("WHERE HBC_Trip_ID = ? "); 
		if(activity.equals("Loading"))
			sb.append("AND ac.Name = 'Loading'");
		else if (activity.equals("Discharging"))
			sb.append("AND ac.Name = 'Discharging'");
		else if(activity.equals("LoadingDischarging"))
			sb.append("AND ac.Name IN ('Loading','Discharging')");
		sb.append(")");
		
		totalDemurrageDay = DB.getSQLValueBD(get_TrxName(),sb.toString(), 
				new Object[]{hbc_trip_id});
		return totalDemurrageDay;
	}
	
	public void createMatching(int DMG_ARCalculation_ID, int HBC_Trip_ID, boolean isDemurrageFreight, String Activity)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE HBC_ShipActivity SET DMG_ARCalculationLine_ID = ? ");
		sb.append("WHERE HBC_ShipActivity_ID IN (SELECT HBC_ShipActivity_ID FROM HBC_ShipActivity sa WHERE ");
		if(isDemurrageFreight)
			sb.append("isDemurrageFreight='Y' ");
		else
			sb.append("isDemurrageWeather='Y' ");
		sb.append("AND HBC_Position_ID IN (SELECT HBC_Position.HBC_Position_ID FROM HBC_Position ");
		sb.append("JOIN HBC_ShipActivity sa2 on HBC_Position.HBC_Position_ID=sa2.HBC_Position_ID ");
		sb.append("JOIN C_Activity ac on sa2.C_Activity_ID=ac.C_Activity_ID ");
		sb.append("WHERE HBC_Trip_ID = ? ");
		if(Activity.equals("Loading"))
			sb.append("AND ac.Name = 'Loading'");
		else if(Activity.equals("Discharging"))
			sb.append("AND ac.Name = 'Discharging'");
		else if(Activity.equals("LoadingDischarging"))
			sb.append("AND ac.Name IN ('Loading','Discharging')");
		sb.append("))");
		DB.executeUpdateEx(sb.toString(), new Object[]{DMG_ARCalculation_ID,HBC_Trip_ID}, get_TrxName());
	}
}
