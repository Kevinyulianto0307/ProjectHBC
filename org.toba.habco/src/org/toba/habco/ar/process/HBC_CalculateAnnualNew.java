package org.toba.habco.ar.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MPeriod;
import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MBarge;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MProjectMilestone;
import org.toba.habco.model.MProjectTypeMilestone;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MTugboat;
import org.toba.habco.model.X_AR_CalculationTemp;
import org.toba.habco.model.X_HBC_BargeCategory;
import org.toba.habco.model.X_HBC_PortPosition;
import org.toba.habco.model.X_HBC_PriceList_BBM;
import org.toba.habco.model.X_HBC_PriceList_BBMDetail;


/**
 * @author TommyAng 
 */
public class HBC_CalculateAnnualNew extends SvrProcess{
	
	/**final string activities*/
	final static protected String LOADING = "LOA";
	final static protected String DISCHARGING = "DIS";
	final static protected String CARGO_ADDITION = "CAD";
	final static protected String CARGO_REDUCTION = "CRE";
	final static protected String CARGO_TRANSFER = "TRC";
	final static protected String CARGO_RETURN = "RCG";
	
	/**global variable*/
	protected int M_Product_ID = 0;
	protected boolean isLoading = false;
	
	/**parameter*/
	protected int p_HBC_Contract_ID = 0;
	protected int p_HBC_Trip_ID = 0;
	protected int p_HBC_Tugboat_ID = 0;
	protected int p_HBC_Barge_ID = 0;
	protected int p_C_BPartner_ID = 0;
	protected Timestamp p_StartDate = null;
	protected Timestamp p_EndDate = null;
	protected int p_C_Activity_ID = 0;
	protected int currencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(MTrip.COLUMNNAME_HBC_Contract_ID)){
				p_HBC_Contract_ID = para[i].getParameterAsInt();
			}else if (name.equals(MShipActivity.COLUMNNAME_FinishDate)){
				p_EndDate = para[i].getParameterAsTimestamp();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_Contract_ID <= 0)
			return "Contract is mandatory";
		
		checkContractType(p_HBC_Contract_ID);
		
		MARCalculation calc = calculation(p_HBC_Contract_ID);
		
		if(calc == null)
			return "Cant create AR Calculation";
		
		String message = Msg.parseTranslation(getCtx(), "@GeneratedARCalculation@ "+calc.getDocumentNo());
		addBufferLog(0, null, null, message, calc.get_Table_ID(), calc.get_ID());
		
		cleanTemp();
		return "";
	}
	
	/**
	 * create ar calculation for loading only
	 * @param HBC_Contract_ID
	 * @return MARCalculation
	 */
	protected MARCalculation calculation(int HBC_Contract_ID){
		
		// create AR Calculation here
		// create calculation header
		
		MARCalculation calc = null;
		int existingARCalculationID = existingARCalculation(HBC_Contract_ID);
		
		if(existingARCalculationID > 0){
			calc = new MARCalculation(getCtx(), existingARCalculationID, get_TrxName());
			log.info("Use Current Calculation AR No : "+calc.getDocumentNo());
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_Trip SET isARCalculation = 'N' "
					+ "WHERE HBC_Trip_ID IN (SELECT DISTINCT HBC_Trip_ID "
					+ "FROM HBC_ARCalculationLine WHERE HBC_ARCalculation_ID = ? AND Processed = 'N')");
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{calc.getHBC_ARCalculation_ID()}, get_TrxName());
			log.info("Updated Trip set isARCalculation false: "+no);
			
			sb = new StringBuilder();
			sb.append("DELETE FROM HBC_ARCalculationLine WHERE HBC_ARCalculation_ID = ? AND Processed = 'N'");
			no = DB.executeUpdateEx(sb.toString(), new Object[]{calc.getHBC_ARCalculation_ID()}, get_TrxName());
			log.info("DELETE AR CalculationLine: "+no);
			
		}
		else{
			calc = new MARCalculation(getCtx(), 0, get_TrxName());
			calc.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
			calc.setHBC_Contract_ID(HBC_Contract_ID);
			calc.setARCalculationType(MARCalculation.ARCALCULATIONTYPE_Annual);
			
			MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
			calc.setC_BPartner_ID(contract.getCustomer_BPartner_ID());
			
			calc.saveEx();			
			log.info("Create New Calculations");
		}
		getTemp(HBC_Contract_ID);
		if(isLoading)
			createARCalculationLineLoadingOnly(calc);
		else
			createARCalculationLineAnnual(calc);
		return calc;
	}
	
	protected void createARCalculationLineLoadingOnly(MARCalculation calc){
		
		MContract hbccontract = new MContract(getCtx(), calc.getHBC_Contract_ID(), get_TrxName());
		
		StringBuilder infoMsg = new StringBuilder();
		int line = getLineNo(calc.getHBC_ARCalculation_ID());
		
		for (int HBC_Trip_ID : getTripIDs()) {
			int[] calcTempID = getTempIDs(HBC_Trip_ID);
			MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
			BigDecimal minCargo = trip.getHBC_Barge().getMinCargo();
			
			BigDecimal currentQty = Env.ZERO;
			int biggestQtyID = 0;
			for (int id : calcTempID) {
				X_AR_CalculationTemp temp = new X_AR_CalculationTemp(getCtx(), id, get_TrxName());
				BigDecimal loadQty = temp.getAmountOfCargo();
				
				if(loadQty.compareTo(currentQty)>0){
					currentQty = loadQty;
					biggestQtyID = id;
				}
				
			}
			
			for (int id : calcTempID) {
				X_AR_CalculationTemp temp = new X_AR_CalculationTemp(getCtx(), id, get_TrxName());
				
				MARCalculationLine calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
				calcLine.setAD_Org_ID(temp.getAD_Org_ID());
				calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
				calcLine.setLine(line+=10);
				calcLine.setHBC_Trip_ID(temp.getHBC_Trip_ID());
				calcLine.set_ValueOfColumn("DateStart", temp.get_Value("StartDate"));
				calcLine.set_ValueOfColumn("EndDate", temp.get_Value("FinishDate"));
				calcLine.setC_BPartner_ID(hbccontract.getCustomer_BPartner_ID());
				calcLine.setAmountOfCargo(minCargo);
				calcLine.setC_Activity_ID(temp.getC_Activity_ID());
				calcLine.setHBC_Barge_ID(temp.get_ValueAsInt("HBC_Barge_ID"));
				calcLine.setHBC_Tugboat_ID(temp.get_ValueAsInt("HBC_Tugboat_ID"));
				
				calcLine.setM_Product_ID(M_Product_ID);
				
				BigDecimal qtyActivity = temp.getAmountOfCargo();
				BigDecimal qtyCharge = qtyActivity;
				if(id == biggestQtyID && minCargo.compareTo(qtyCharge)>0)
					qtyCharge = minCargo;
				calcLine.setTotalCargoQty(qtyActivity);
				calcLine.setQtyCharge(qtyCharge);
				calcLine.setQtyToInvoiced(qtyCharge);
				
				int HBC_Position_ID = temp.getHBC_Position_ID();
				MPosition position = new MPosition(getCtx(), HBC_Position_ID, get_TrxName());
				int From_PortPosition_ID = position.get_ValueAsInt("From_PortPosition_ID");
				int To_PortPosition_ID = position.get_ValueAsInt("To_PortPosition_ID");
				calcLine.setFrom_PortPosition_ID(From_PortPosition_ID);
				calcLine.setTo_PortPosition_ID(To_PortPosition_ID);
				
				
				BigDecimal price = Env.ZERO;
				price = getPrice(temp.getHBC_Trip_ID(), From_PortPosition_ID, To_PortPosition_ID, (Timestamp) temp.get_Value("FinishDate"), temp.get_ValueAsInt("HBC_Tugboat_ID"), temp.get_ValueAsInt("HBC_Barge_ID"));
				BigDecimal totalAmt = Env.ZERO;
				totalAmt = qtyCharge.multiply(price);
				
				calcLine.setUnitPrice(price);
				calcLine.setHBC_Contract_ID(hbccontract.get_ID());
				if(calcLine.getHBC_Contract().getC_Currency_ID()==currencyID)
					calcLine.setTotalAmt(totalAmt.setScale(0, RoundingMode.HALF_UP));
				else
					calcLine.setTotalAmt(totalAmt.setScale(2, RoundingMode.HALF_UP));
				calcLine.setARCalculationType(MARCalculationLine.ARCALCULATIONTYPE_Annual);
				calcLine.setC_Project_ID(temp.getHBC_Position().getC_Project_ID());
				
				calcLine.saveEx();
				
				infoMsg.append(temp.get_Value("DocumentNo")+", ");
			}
			
			calc.setComments(infoMsg.toString());
			calc.saveEx();

			trip.setIsARCalculation(true);
			trip.saveEx();
		}
	}
	
	protected void createARCalculationLineAnnual(MARCalculation calc){
		int HBC_Contract_ID = calc.getHBC_Contract_ID();
		MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
		int line = getLineNo(calc.getHBC_ARCalculation_ID());
		for (int HBC_Trip_ID : getTripIDs()) {
			
			int[] calcTempID = getTempIDs(HBC_Trip_ID);			
			
			if(calcTempID.length<2){
				continue;								//cannot create ar calculation line without couple
			}
						
			MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
			BigDecimal minCargo = trip.getHBC_Barge().getMinCargo();
			
			//TOMMYANGKASA
			ArrayList<Integer> loadTempID = new ArrayList<Integer>();
			ArrayList<Integer> dischargeTempID = new ArrayList<Integer>();

			BigDecimal currentQty = Env.ZERO;
			int biggestQtyID = 0;
			for (int id : calcTempID) {
				X_AR_CalculationTemp temp = new X_AR_CalculationTemp(getCtx(), id, get_TrxName());
				if(temp.getC_Activity().getValue().equals(LOADING))
					loadTempID.add(id);
				else{
					dischargeTempID.add(id);
					BigDecimal dischargeQty = temp.getAmountOfCargo();
					BigDecimal returnCargo = (BigDecimal) temp.get_Value("CargoReturn");
					BigDecimal totalQty = dischargeQty.add(returnCargo);
					if(totalQty.compareTo(currentQty)>0){
						currentQty = totalQty;
						biggestQtyID = id;
					}
				}
			}
			
			for(int id : loadTempID){
				X_AR_CalculationTemp loadTemp = new X_AR_CalculationTemp(getCtx(), id, get_TrxName());
				Timestamp loadStartDate = (Timestamp) loadTemp.get_Value("StartDate");
				int fromPortID = loadTemp.getHBC_PortPosition_ID();
				BigDecimal loadQty = loadTemp.getAmountOfCargo();
				for(int id1 : dischargeTempID){
					X_AR_CalculationTemp dischargingTemp = new X_AR_CalculationTemp(getCtx(), id1, get_TrxName());
					Timestamp dischargeStartDate = (Timestamp) dischargingTemp.get_Value("StartDate");
					Timestamp dischargeFinishDate = (Timestamp) dischargingTemp.get_Value("FinishDate");
					int toPortID = dischargingTemp.getHBC_PortPosition_ID();
					
					if(fromPortID==toPortID)
						continue;
					
					BigDecimal dischargeQty = dischargingTemp.getAmountOfCargo();
					BigDecimal returnCargo = (BigDecimal) dischargingTemp.get_Value("CargoReturn");						
					int HBC_Tugboat_ID = dischargingTemp.get_ValueAsInt("HBC_Tugboat_ID");
					int HBC_Barge_ID = dischargingTemp.get_ValueAsInt("HBC_Barge_ID");
					
					if(returnCargo==null)
						returnCargo = Env.ZERO;
					
					if(loadStartDate.after(dischargeStartDate))
						continue;
					if(loadQty.compareTo(dischargeQty.add(returnCargo)) == 0){
						BigDecimal price = Env.ZERO;
						
						MARCalculationLine calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
						calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
						calcLine.setAD_Org_ID(dischargingTemp.getAD_Org_ID());
						calcLine.setLine(line+=10);
						calcLine.setAmountOfCargo(minCargo);
						
						calcLine.setFrom_PortPosition_ID(fromPortID);
						calcLine.setTo_PortPosition_ID(toPortID);
						
						BigDecimal qtyCharge = loadQty;	
						BigDecimal qtyActivity = qtyCharge;
						
						if(id1==biggestQtyID && minCargo.compareTo(qtyCharge)>0)
							qtyCharge = minCargo;
						
						BigDecimal qtyToInvoice = qtyCharge;
						price = getPrice(HBC_Trip_ID, fromPortID, toPortID, dischargeFinishDate, HBC_Tugboat_ID, HBC_Barge_ID);
						BigDecimal totalAmt = qtyToInvoice.multiply(price);
					
						calcLine.setUnitPrice(price);
						calcLine.setQtyCharge(qtyCharge);
						calcLine.setQtyToInvoiced(qtyCharge);
					
						if(contract.getC_Currency_ID()==currencyID)
							calcLine.setTotalAmt(totalAmt.setScale(0, RoundingMode.HALF_UP));
						else
							calcLine.setTotalAmt(totalAmt.setScale(2, RoundingMode.HALF_UP));

						calcLine.setTotalCargoQty(qtyActivity);
						
						calcLine.set_ValueOfColumn("DateStart", dischargeStartDate);
						calcLine.set_ValueOfColumn("EndDate", dischargeFinishDate);
						calcLine.setHBC_Position_ID(dischargingTemp.getHBC_Position_ID());
						calcLine.setHBC_Contract_ID(HBC_Contract_ID);
						
						calcLine.setC_Project_ID(dischargingTemp.getHBC_Position().getC_Project_ID());
						calcLine.setHBC_Tugboat_ID(HBC_Tugboat_ID > 0 ? HBC_Tugboat_ID : trip.getHBC_Tugboat_ID());
						calcLine.setHBC_Barge_ID(HBC_Barge_ID > 0 ? HBC_Barge_ID : trip.getHBC_Barge_ID());
						calcLine.setC_BPartner_ID(contract.getCustomer_BPartner_ID());
						calcLine.setHBC_Trip_ID(trip.getHBC_Trip_ID());
						calcLine.setC_Activity_ID(dischargingTemp.getC_Activity_ID());
						calcLine.setARCalculationType(MARCalculationLine.ARCALCULATIONTYPE_Annual);
						calcLine.setM_Product_ID(M_Product_ID);
						calcLine.saveEx();
					}
				}
			}
			
			//TOMMYANGKASA
			
			trip.setIsARCalculation(true);
			trip.saveEx();
					
		}
	}
	
	/**
	 * get trip with parameter
	 * @return list trip id
	 */
	protected List<X_AR_CalculationTemp> getTemp(int HBC_Contract_ID){

		boolean loading = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT s.AD_Org_ID, s.HBC_ShipActivity_ID, s.AmountOfCargo, s.HBC_Tugboat_ID, s.HBC_Barge_ID, s.FinishDate, s.HBC_Position_ID, t.HBC_Trip_ID, t.From_PortPosition_ID, t.To_PortPosition_ID, t.DocumentNo, t.DateStart, t.EndDate, s.StartDate, s.C_Activity_ID, t.TotalCargoQty, p.HBC_PortPosition_ID, s.CargoReturn");
		sql.append(" FROM HBC_Trip t");
		sql.append(" LEFT JOIN HBC_Position p on p.HBC_Trip_ID = t.HBC_Trip_ID");
		sql.append(" LEFT JOIN HBC_ShipActivity s on s.HBC_Position_ID = p.HBC_Position_ID");
		sql.append(" LEFT JOIN C_Activity c on s.C_Activity_ID = c.C_Activity_ID");
		if(isLoading){
			sql.append(" WHERE c.Value = '"+LOADING+"'");
			loading = true;
		}
		else{
			sql.append(" WHERE c.Value in ('"+LOADING+"','"+DISCHARGING+"','"+CARGO_ADDITION+"','"+CARGO_REDUCTION+"','"+CARGO_RETURN+"','"+CARGO_TRANSFER+"')");			
		}
		sql.append(" AND t.HBC_Contract_ID="+HBC_Contract_ID);
		sql.append(" AND t.IsARCalculation = 'N' AND t.AD_Client_ID="+getAD_Client_ID());
		sql.append(" AND s.FinishDate is not null");
		
		if(p_EndDate!=null)
			sql.append(" AND s.FinishDate<'"+TimeUtil.addDays(p_EndDate,1)+"'");
		
		sql.append(" Order By t.DateStart, s.StartDate");
			
		
		List<X_AR_CalculationTemp> list = new ArrayList<X_AR_CalculationTemp>();
		int line = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next()){
				X_AR_CalculationTemp temp = new X_AR_CalculationTemp(getCtx(), 0, get_TrxName());
				temp.setAD_Org_ID(rs.getInt(1));
				temp.set_ValueOfColumn("HBC_ShipActivity_ID", rs.getInt(2));
				temp.set_ValueOfColumn("HBC_Tugboat_ID", rs.getInt(4));
				temp.set_ValueOfColumn("HBC_Barge_ID", rs.getInt(5));
				if(rs.getTimestamp(6)==null)
					throw new AdempiereException("Activity Has No End Date, Trip :"+rs.getString(11));
				else
					temp.set_ValueOfColumn("FinishDate", rs.getTimestamp(6));
				temp.setHBC_Position_ID(rs.getInt(7));
				temp.setHBC_Trip_ID(rs.getInt(8));
				
				temp.set_ValueOfColumn("DocumentNo", rs.getString(11));
				temp.set_ValueOfColumn("DateStart", rs.getTimestamp(12));
				temp.set_ValueOfColumn("EndDate", rs.getTimestamp(13));
				temp.set_ValueOfColumn("StartDate", rs.getTimestamp(14));
				temp.setC_Activity_ID(rs.getInt(15));
				if(loading){
					temp.set_ValueOfColumn("From_PortPosition_ID", rs.getInt(9));
					temp.set_ValueOfColumn("To_PortPosition_ID", rs.getInt(10));
				}else{
					temp.set_ValueOfColumn("CargoReturn", rs.getBigDecimal(18));
				}
				temp.setHBC_PortPosition_ID(rs.getInt(17));
				//temp.setTotalCargoQty(rs.getBigDecimal(16));
				temp.setAmountOfCargo(rs.getBigDecimal(3));
				temp.setLine(line+=10);
				temp.saveEx();
				list.add(temp);
			}
		}catch(Exception e){
			log.severe("Error Get Trip, "+e.toString());
		}finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return list;
	}
	
	/**
	 * @param HBC_Trip_ID
	 * @return temporary table for calculation
	 */
	protected List<Integer> getTripIDs(){
		
		List<Integer> list = new ArrayList<Integer>();		
		String sql = "SELECT DISTINCT HBC_Trip_ID From AR_CalculationTemp";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
			}
		}catch(Exception e){
			
		}finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return list;
	}
	
	/**
	 * @param HBC_Trip_ID
	 * @return temporary table for calculation
	 */
	protected int[] getTempIDs(int HBC_Trip_ID){
		String where = "HBC_Trip_ID="+HBC_Trip_ID;
		int[] ids = new Query(getCtx(), X_AR_CalculationTemp.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setOrderBy("StartDate")
			.getIDs();
		
		if(ids.length <=0 )
			log.severe("There is no date for AR Calculation");
		
		return ids;
	}
	
	/**
	 * @param HBC_Trip_ID
	 * @return first activity while loading
	 */
	protected int getTempFirstID(int HBC_Trip_ID){
		String where = "HBC_Trip_ID="+HBC_Trip_ID;
		int id = new Query(getCtx(), X_AR_CalculationTemp.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setOrderBy("StartDate")
			.firstId();
		
		if(id <= 0)
			log.severe("There is no data for FIRST AR Calculation");
		
		return id;
	}
	
	/**
	 * @param HBC_Trip_ID
	 * @return last activity while discharging
	 */
	protected int getTempLastID(int HBC_Trip_ID){
		String where = "HBC_Trip_ID="+HBC_Trip_ID;
		int id = new Query(getCtx(), X_AR_CalculationTemp.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setOrderBy("StartDate DESC")
			.firstId();
		
		if(id <= 0)
			log.severe("There is no data for LAST AR Calculation");
		
		return id;
	}
	
	/**
	 * get price standard with multiple parameter like from position, 
	 * to position, barge category and customer
	 * @return Price Standard
	 * @throws error if no price
	 */
	protected BigDecimal getPrice(int HBC_Trip_ID, int fromPosition, int toPosition, Timestamp finishDate, int HBC_Tugboat_ID, int HBC_Barge_ID){
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		
		int C_Period_ID = getC_Period_ID(HBC_Trip_ID, finishDate);
		String where = "C_Period_ID="+C_Period_ID;
		int priceListbbm_id = new Query(getCtx(), X_HBC_PriceList_BBM.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.firstId();
		
		StringBuilder sb = new StringBuilder();
		sb.append("HBC_PriceList_BBM_ID="+priceListbbm_id);
		sb.append(" AND From_PortPosition_ID="+fromPosition);
		sb.append(" AND hbc_pricelist_bbmdetail.to_portposition_id="+toPosition);
		sb.append(" AND CAST(fm.Value AS INT) <="+trip.getHBC_Barge().getHBC_BargeCategory().getValue());
		sb.append(" AND CAST(too.Value AS INT) >="+trip.getHBC_Barge().getHBC_BargeCategory().getValue());
		sb.append(" AND C_BPartner_ID="+trip.getHBC_Contract().getCustomer_BPartner_ID());
		sb.append(" AND '"+finishDate+"' BETWEEN StartDate AND FinishDate");
		
		int line_id = new Query(getCtx(), X_HBC_PriceList_BBMDetail.Table_Name, sb.toString(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.addJoinClause("JOIN HBC_BargeCategory fm ON HBC_PriceList_BBMDetail.HBC_BargeCategory_ID=fm.HBC_BargeCategory_ID")
			.addJoinClause("JOIN HBC_BargeCategory too ON HBC_PriceList_BBMDetail.BargeCategoryTo_ID=too.HBC_BargeCategory_ID")
			.firstId();
		
		X_HBC_PriceList_BBMDetail priceDetail = new X_HBC_PriceList_BBMDetail(getCtx(), line_id, get_TrxName());
		
		BigDecimal priceStd = priceDetail.getPriceStd();
		if(priceStd == null || priceStd.compareTo(Env.ZERO) <= 0){
			priceStd = Env.ZERO;
			X_HBC_PortPosition fromPortPosition = new X_HBC_PortPosition(getCtx(), fromPosition, get_TrxName());
			X_HBC_PortPosition toPortPosition = new X_HBC_PortPosition(getCtx(), toPosition, get_TrxName());
			MPeriod period = MPeriod.get(getCtx(), C_Period_ID);
			MBPartner partner = (MBPartner) trip.getHBC_Contract().getCustomer_BPartner();
			X_HBC_BargeCategory bargeCat = new X_HBC_BargeCategory(getCtx(), trip.getHBC_Barge().getHBC_BargeCategory_ID(), get_TrxName());
			MBarge barge = new MBarge(getCtx(), HBC_Barge_ID, get_TrxName());
			MTugboat tugboat = new MTugboat(getCtx(), HBC_Tugboat_ID, get_TrxName());
			
			throw new AdempiereException("There is no price from "+fromPortPosition.getName()+" to "+toPortPosition.getName()
					+", Trip "+trip.getDocumentNo()+", Period "+period.getName()+", Customer "+partner.getName()
					+", BargeCategory "+bargeCat.getName()+", Barge : "+barge.getName()+", Tugboat : "+tugboat.getName());
		}
		
		log.info("Get Price Annual From#"+fromPosition+" To#"+toPosition+" Price#"+priceStd);
		
		return priceDetail.getPriceStd();
	}
	
	/**
	 * get period id from end date trip
	 * @param HBC_Trip_ID
	 * @return C_Period_ID
	 */
	protected int getC_Period_ID(int HBC_Trip_ID, Timestamp finishDate){
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		
		Timestamp date = finishDate;
		
		date = Util.removeTime(date);
		
		MPeriod period = null;
		try{
			period = MPeriod.get(getCtx(), date, 0, get_TrxName());
		}
		catch(Exception e){
			period = null;
		}
		
		int C_Period_ID = 0;
		if(period != null)
			C_Period_ID = period.getC_Period_ID();
		
		if(C_Period_ID <= 0)
			throw new AdempiereException("Cant find period for "+date +"and Trip "+trip.getDocumentNo());
		
		return C_Period_ID;
	}
	
	/**
	 * Loading or Annual
	 * @param HBC_Contract_ID
	 * @return is loading
	 */
	protected void checkContractType(int HBC_Contract_ID){
		
		MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
		
		MProjectTypeMilestone milestone = (MProjectTypeMilestone) contract.getC_ProjectTypeMilestone(); //tambahin exception kalau null
		
		for (MProjectMilestone line : milestone.getProjectMilestone()) {
			if(line.getC_Activity_ID() > 0){
				if(line.getC_Activity().getValue().equals(LOADING)){
					isLoading = true;
					break;
				}
			}
		}
		for (MProjectMilestone line : milestone.getProjectMilestone()) {
			if(line.getM_Product_ID() > 0){
				M_Product_ID = line.getM_Product_ID();
				break;
			}
		}
	}
	
	/**
	 * AR Calculation Existing
	 * @param HBC_Contract_ID
	 * @return AR Calculation ID
	 */
	protected int existingARCalculation(int HBC_Contract_ID){
		
		StringBuilder where = new StringBuilder();
		where.append("HBC_Contract_ID="+HBC_Contract_ID);
		
		int id = new Query(getCtx(), MARCalculation.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		
		if(id>0)
			return id;
		
		return 0;
	}
	
	protected int getLineNo(int HBC_ARCalculation_ID){
		
		StringBuilder where = new StringBuilder();
		where.append("HBC_ARCalculation_ID="+HBC_ARCalculation_ID);
		
		int lineID = new Query(getCtx(), MARCalculationLine.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setOrderBy("Line Desc")
		.firstId();
		
		if(lineID>0){
			MARCalculationLine line = new MARCalculationLine(getCtx(), lineID, get_TrxName());
			return line.getLine();
		}
		
		return 0;
	}
	
	protected void cleanTemp(){
		String sql = "DELETE FROM AR_CalculationTemp";
		DB.executeUpdate(sql, get_TrxName());
		log.info("Deleted all AR_CalculationTemp");
	}
}



