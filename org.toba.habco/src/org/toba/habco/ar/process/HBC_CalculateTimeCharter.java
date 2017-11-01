package org.toba.habco.ar.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MProjectMilestone;
import org.toba.habco.model.MProjectTypeMilestone;
import org.toba.habco.model.MTrip;

/**
 * @author Stephan
 * process for calculate time charter contract
 */
public class HBC_CalculateTimeCharter extends SvrProcess{

	/**parameter*/
	protected int p_HBC_Contract_ID = 0;
	
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
		
		if(p_HBC_Contract_ID <= 0)
			return "Contract is mandatory";
		
		MContract contract = new MContract(getCtx(), p_HBC_Contract_ID, get_TrxName());
		if(!contract.getContractStatus().equals(MContract.CONTRACTSTATUS_Finish))
			return "Contract Status not Finish";
		
		MTrip trip = getTrip(p_HBC_Contract_ID);
		
		// create AR Calculation here
		// create calculation header
		StringBuilder where = new StringBuilder();
		where.append("HBC_Contract_ID=" + contract.getHBC_Contract_ID());

		int current_id = new Query(getCtx(), MARCalculation.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
				
		MARCalculation calc = null;
		if(current_id > 0){
			calc = new MARCalculation(getCtx(), current_id, get_TrxName());
			log.info("Use Current ID Calculation");
		}
		else{
			calc = new MARCalculation(getCtx(), 0, get_TrxName());
			calc.setAD_Org_ID(trip.getAD_Org_ID());
			calc.setHBC_Trip_ID(trip.getHBC_Trip_ID());
			calc.setHBC_Contract_ID(p_HBC_Contract_ID);
			calc.setHBC_Tugboat_ID(trip.getHBC_Tugboat_ID());
			calc.setC_Project_ID(trip.getC_Project_ID());
			calc.setHBC_Barge_ID(trip.getHBC_Barge_ID());
			calc.setFrom_PortPosition_ID(trip.getFrom_PortPosition_ID());
			calc.setTo_PortPosition_ID(trip.getTo_PortPosition_ID());
			//calc.setC_Period_ID(getC_Period_ID());
			calc.setStartDate(trip.getDateStart());
			calc.setFinishDate(trip.getEndDate());
			calc.setARCalculationType(MARCalculation.ARCALCULATIONTYPE_TimeCharter);
			calc.saveEx();
			log.info("Create New Calculations");
		}
		
		//int line = 0;
		
		Timestamp validFrom = contract.getValidFrom();
		//Timestamp validTo = contract.getValidTo();
		int numberOfLines = contract.getNumberInvoiced().intValueExact();
		int totalActualHireDay=0;
		//if(contract.getOffHireDate()!=null && contract.getActualHireDate()!=contract.getContractDay())
		//		numberOfLines=numberOfLines+10;
		//if(contract.getActualHireDate() !=null)
		totalActualHireDay= contract.getActualHireDate().intValue();
		//totalActualHireDay=0;
		//if(contract.getOffHireDate()!=null && contract.getActualHireDate()!=contract.getContractDay())
		//		numberOfLines=numberOfLines+10;
		//if(contract.getActualHireDate()!=null)
		//	totalActualHireDay= contract.getActualHireDate().intValue();
		
		int diffDay = totalActualHireDay - contract.getContractDay().intValue();
		
		// delete old ar calculation lines
		//deleteCalculationLines(p_HBC_Contract_ID, calc.getHBC_ARCalculation_ID());
		int sum = 0;
		for (int i = 10; i <= numberOfLines ; i+=10) {
			if(validFrom == null)
				continue;
			
			where = new StringBuilder();
			where.append("HBC_Contract_ID="+p_HBC_Contract_ID)
			.append(" AND HBC_ARCalculation_ID="+calc.getHBC_ARCalculation_ID())
			.append(" AND Line = "+i);
			
			int currentLine_id = new Query(getCtx(), MARCalculationLine.Table_Name, where.toString(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.firstId();
			
			if(currentLine_id > 0)
				continue;
			
			MARCalculationLine calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
			calcLine.setAD_Org_ID(calc.getAD_Org_ID());
			calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
			calcLine.setC_BPartner_ID(contract.getCustomer_BPartner_ID());
			calcLine.setLine(i);
			calcLine.setHBC_Trip_ID(trip.getHBC_Trip_ID());
			calcLine.setHBC_Contract_ID(contract.getHBC_Contract_ID());
			calcLine.setC_Project_ID(trip.getC_Project_ID());
			calcLine.setHBC_Tugboat_ID(contract.getHBC_Tugboat_ID());
			calcLine.setHBC_Barge_ID(contract.getHBC_Barge_ID());
			calcLine.setFrom_PortPosition_ID(contract.getFrom_PortPosition_ID());
			calcLine.setTo_PortPosition_ID(contract.getTo_PortPosition_ID());
			
			
			//Modified by TommyAng
			Timestamp nextPeriod = null;
			if(contract.getValidTo().before(TimeUtil.addDays(contract.getValidFrom(), sum+(TimeUtil.getNumberOfDaysInMonth(validFrom)-1)))){
				int add = contract.getContractDay().intValue()-(sum+(i/10));
				nextPeriod=TimeUtil.addDays(validFrom, add);
			}else{
				int add = (TimeUtil.getNumberOfDaysInMonth(validFrom))-1;
				sum = sum + add;
				nextPeriod=TimeUtil.addDays(validFrom, add);
			}
			//if(totalActualHireDay!=0 && totalActualHireDay<add){
			//	nextPeriod=TimeUtil.addDays(validFrom, totalActualHireDay);
			//}else{
			//	nextPeriod=TimeUtil.addDays(validFrom, add);
			//	totalActualHireDay=totalActualHireDay-add;
			//}
				
			
			
			calcLine.setDateFrom(validFrom);
			calcLine.setDateTo(nextPeriod);
			
			calcLine.setC_Period_ID(getC_Period_ID(validFrom));
			/* comment by phie
			calcLine.setM_Product_ID(getM_Product_ID(p_HBC_Contract_ID));
			*/
			//@phie HBC 2711 set product time charter for calculate time charter
			calcLine.setM_Product_ID(1015466);
			//end phie
			calcLine.setContractDay(contract.getContractDay());
			calcLine.setActualHireDate(contract.getActualHireDate());
			
			int dayChargeCalculation = TimeUtil.getDaysBetween(validFrom, TimeUtil.addDays(nextPeriod, 1));
			calcLine.setDayChargeCalculation(new BigDecimal(dayChargeCalculation));
			
			BigDecimal dayCharge = getDayCharge(p_HBC_Contract_ID, dayChargeCalculation);
			BigDecimal dayInvoicePercentage = dayCharge.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(validFrom)),2,RoundingMode.HALF_UP);
			calcLine.setQtyToInvoiced(dayInvoicePercentage); // to invoice
			calcLine.setDayCharge(dayCharge);
			calcLine.setDayToInvoice(dayCharge);
			calcLine.setDayInvoicePercentage(dayInvoicePercentage);
			calcLine.setDateFrom(validFrom);
			calcLine.setDateTo(nextPeriod);
			calcLine.setUnitPrice(contract.getUnitPrice()); // to invoice
			
			BigDecimal totalAmt = dayInvoicePercentage.multiply(contract.getUnitPrice());
			//totalAmt = totalAmt.divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			calcLine.setTotalAmt(totalAmt);
			calcLine.setARCalculationType(MARCalculationLine.ARCALCULATIONTYPE_TimeCharter);
			calcLine.saveEx();
			
			/*
			if(i == 10){
				if(contract.getOnHireDate() == null)
					validFrom = null;
				else
					validFrom = contract.getOnHireDate();
			}
			else*/ 
			//if(i > 10){
				validFrom = TimeUtil.addDays(nextPeriod, 1);
			//}
				System.out.println("a");
		}
		
		/* comment by Phie
		//if(diffDay != 0 && contract.getActualHireDate().intValue() > 0){
		//Made by TommyAng
		if(contract.getOffHireDate()!=null){
			
			MARCalculationLine calcLine = null;
			
			String isHaveDiff = "DiffCalculated";
			where = new StringBuilder();
			where.append("HBC_Contract_ID="+p_HBC_Contract_ID)
			.append(" AND HBC_ARCalculation_ID="+calc.getHBC_ARCalculation_ID())
			.append(" AND Help='"+isHaveDiff+"'");
			
			int currentLine_id = new Query(getCtx(), MARCalculationLine.Table_Name, where.toString(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.firstId();
			
			
			if(currentLine_id > 0)
				calcLine = new MARCalculationLine(getCtx(), currentLine_id, get_TrxName());
			else
				calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
			
			
			calcLine.setAD_Org_ID(calc.getAD_Org_ID());
			calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
			calcLine.setC_BPartner_ID(contract.getCustomer_BPartner_ID());
			calcLine.setLine(numberOfLines+10);
			calcLine.setHBC_Trip_ID(trip.getHBC_Trip_ID());
			calcLine.setHBC_Contract_ID(contract.getHBC_Contract_ID());
			calcLine.setC_Project_ID(trip.getC_Project_ID());
			calcLine.setHBC_Tugboat_ID(contract.getHBC_Tugboat_ID());
			calcLine.setHBC_Barge_ID(contract.getHBC_Barge_ID());
			calcLine.setFrom_PortPosition_ID(contract.getFrom_PortPosition_ID());
			calcLine.setTo_PortPosition_ID(contract.getTo_PortPosition_ID());
			calcLine.set_ValueOfColumn("Help", isHaveDiff);
			
			//int add = (TimeUtil.getNumberOfDaysInMonth(validFrom))-1;
			Timestamp nextPeriod = null;
			
			//if(totalActualHireDay!=0 && totalActualHireDay<add){
			
			//TommyAng
			//int add = (TimeUtil.getNumberOfDaysInMonth(contract.getValidFrom()))-1;
			//nextPeriod=TimeUtil.addDays(contract.getValidFrom(), add);
			
			//}else{
			//	nextPeriod=TimeUtil.addDays(validFrom, add);
			//	totalActualHireDay=totalActualHireDay-add;
			//}
			//calcLine.setDateFrom(validFrom);
			//calcLine.setDateTo(contract.getOffHireDate());
			
			//calcLine.setC_Period_ID(getC_Period_ID(validFrom));
			calcLine.setM_Product_ID(getM_Product_ID(p_HBC_Contract_ID));
			calcLine.setContractDay(contract.getContractDay());
			calcLine.setActualHireDate(contract.getActualHireDate());
			MARCalculationLine calcLines[] = calc.getCalculationLine();
			
			for (MARCalculationLine line : calcLines) {
				validFrom = TimeUtil.addDays(line.getDateTo(), 1);
			}
			
			
			System.out.println(validFrom);
			
			int dayChargeCalculation = 0;
			BigDecimal dayCharge = Env.ZERO;
			BigDecimal dayInvoicePercentage = Env.ZERO;
			if(contract.getOffHireDate().after(contract.getValidTo())){
				calcLine.setC_Period_ID(getC_Period_ID(validFrom));
				calcLine.setDateFrom(validFrom);
				calcLine.setDateTo(contract.getOffHireDate());
				dayChargeCalculation = TimeUtil.getDaysBetween(validFrom, contract.getOffHireDate())+1;
				calcLine.setDayChargeCalculation(new BigDecimal(dayChargeCalculation));
				dayCharge = new BigDecimal(dayChargeCalculation);
				dayInvoicePercentage = dayCharge.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(contract.getOnHireDate())),2,RoundingMode.HALF_UP);
			}else if(contract.getOffHireDate().before(contract.getValidTo())){
				calcLine.setC_Period_ID(getC_Period_ID(contract.getOffHireDate()));
				validFrom = TimeUtil.addDays(validFrom, -1);
				Timestamp offHireDateSpecialCase = TimeUtil.addDays(contract.getOffHireDate(), 1);
				calcLine.setDateFrom(offHireDateSpecialCase);
				calcLine.setDateTo(validFrom);
				dayChargeCalculation = TimeUtil.getDaysBetween(validFrom, contract.getOffHireDate());
				calcLine.setDayChargeCalculation(new BigDecimal(dayChargeCalculation).negate());
				dayCharge = new BigDecimal(dayChargeCalculation);
				//dayInvoicePercentage = dayCharge.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(validFrom)),2,RoundingMode.HALF_UP);
				dayInvoicePercentage = dayCharge.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(validFrom)),2,RoundingMode.HALF_UP);
			}
			
			//if(contract.getOffHireDate().after(contract.getValidTo())){
				//calcLine.setDayChargeCalculation(new BigDecimal(dayChargeCalculation));
			//}else if(contract.getOffHireDate().before(contract.getValidTo())){
				//calcLine.setDayChargeCalculation(new BigDecimal(dayChargeCalculation).negate());
			//}
			
			//BigDecimal dayCharge = new BigDecimal(dayChargeCalculation);
			//BigDecimal dayInvoicePercentage = dayCharge.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(contract.getOnHireDate())),2,RoundingMode.HALF_UP);
			calcLine.setQtyToInvoiced(dayInvoicePercentage); // to invoice
			calcLine.setDayCharge(dayCharge);
			calcLine.setDayToInvoice(dayCharge);
			calcLine.setDayInvoicePercentage(dayInvoicePercentage);
			
			
			calcLine.setUnitPrice(contract.getUnitPrice()); // to invoice
			
			BigDecimal totalAmt = dayInvoicePercentage.multiply(contract.getUnitPrice());
			//totalAmt = totalAmt.divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			calcLine.setTotalAmt(totalAmt);
			calcLine.setARCalculationType(MARCalculationLine.ARCALCULATIONTYPE_TimeCharter);
			calcLine.saveEx();
		}
		*/
		
		String message = Msg.parseTranslation(getCtx(), "@GeneratedARCalculation@ "+calc.getDocumentNo());
		addBufferLog(0, null, null, message, calc.get_Table_ID(), calc.get_ID());
		
		return "";
	}

	/**
	 * delete calculation line
	 * @param HBC_Contract_ID
	 */
	protected void deleteCalculationLines(int HBC_Contract_ID, int HBC_ARCalculation_ID){
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM HBC_ARCalculationLine WHERE HBC_Contract_ID="+HBC_Contract_ID
				+ " AND HBC_ARCalculation_ID="+HBC_ARCalculation_ID);
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		
		log.info("deleteARCalculationLines#"+no);
	}
	
	/**
	 * get trip with parameter
	 * @return list trip id
	 */
	protected MTrip getTrip(int HBC_Contract_ID){
		
		//List<Integer> list = new ArrayList<Integer>();
		int id = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT trip.HBC_Trip_ID FROM HBC_Trip trip");
		sql.append(" WHERE trip.HBC_Contract_ID="+HBC_Contract_ID);
		
		//if(p_C_BPartner_ID > 0)
		//	sql.append(" AND trip.C_BPartner_ID="+p_C_BPartner_ID);
		
		sql.append(" AND trip.IsARCalculation = 'N' AND trip.AD_Client_ID="+getAD_Client_ID());
		//sql.append(" LIMIT 1");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
		}catch(Exception e){
			log.severe("Error Get Trip "+e.toString());
		}finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		if(id <= 0)
			log.severe("Cant find trip "+id);
		
		MTrip trip = new MTrip(getCtx(), id, get_TrxName());
		
		return trip;
	}
	
	/**
	 * get product id from milestone line
	 * @param HBC_Contract_ID
	 * @return M_Product_ID
	 */
	protected int getM_Product_ID(int HBC_Contract_ID){
		
		int M_Product_ID = 0;
		MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
		MProjectTypeMilestone milestone = (MProjectTypeMilestone) contract.getC_ProjectTypeMilestone();
		
		for (MProjectMilestone line : milestone.getProjectMilestone()) {
			if(line.getM_Product_ID() > 0){
				M_Product_ID = line.getM_Product_ID();
				break;
			}
		}
		
		return M_Product_ID;
	}
	
	/**
	 * get period id from end date trip
	 * @param HBC_Trip_ID
	 * @return C_Period_ID
	 */
	protected int getC_Period_ID(Timestamp date){
		
		String sql = "SELECT getc_period_id("+getAD_Client_ID()+",'"+date+"')";
		int C_Period_ID = DB.getSQLValue(get_TrxName(), sql);
		
		return C_Period_ID;
	}
	
	/**
	 * day charge rule
	 * @param HBC_Contract_ID, DayChargeCalculation
	 * @return day charge
	 */
	protected BigDecimal getDayCharge(int HBC_Contract_ID, int DayChargeCalculation){
		
		//MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
		//BigDecimal actualHireDay = contract.getActualHireDate();
		//BigDecimal contractDay = contract.getContractDay();
		BigDecimal dayChargeCalculation = new BigDecimal(DayChargeCalculation);
		BigDecimal dayCharge = Env.ZERO;
		
		//if(actualHireDay.compareTo(Env.ZERO)==0)
			dayCharge = dayChargeCalculation;
		
		//else if(actualHireDay.compareTo(contractDay) > 0)
		//	dayCharge = actualHireDay.subtract(contractDay);
		
		//else if(actualHireDay.compareTo(contractDay) < 0)
		//	dayCharge = contractDay.subtract(actualHireDay);
		
		log.info("Day Charge#"+dayCharge);
		
		return dayCharge;
	}
	
}
