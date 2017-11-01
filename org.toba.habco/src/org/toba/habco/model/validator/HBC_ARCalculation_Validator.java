package org.toba.habco.model.validator;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.util.TimeUtil;
import org.osgi.service.event.Event;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;

/**
 * @author TommyAng
 * ARCalculation Validator
 */

public class HBC_ARCalculation_Validator {
	
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MARCalculationLine calcLine = new MARCalculationLine(po.getCtx(), po.get_ID(), po.get_TrxName());
		if (event.getTopic().equals(IEventTopics.PO_BEFORE_CHANGE)){
			msg = beforeSave(calcLine);
		}
		
		return msg;
	}
	
	public static String beforeSave(MARCalculationLine calcLine){
		
		String contractType = calcLine.getHBC_Contract().getHBC_ContractType();
		if (contractType == null)
			return "";
		
		if (contractType.equals("TIME")) {
						
			MARCalculation calc = new MARCalculation(calcLine.getCtx(), calcLine.getHBC_ARCalculation_ID(), calcLine.get_TrxName());
			MARCalculationLine calcLines[] = calc.getCalculationLine();
			int count = 0;
			boolean isLastRecord = false;
			
			for (MARCalculationLine line : calcLines) { //TODO @win note: Do not use for loop to calculate something this simple. too in
				count++;
			}
			
			count *= 10;
			
			if(calcLine.getLine() == count) //TODO @win note: This is surely very risky assumption.. must use another approach 
				isLastRecord = true;
			
			Timestamp validTo = calcLine.getHBC_Contract().getValidTo();
			Timestamp dateFrom = calcLine.getDateFrom();
			Timestamp dateTo = calcLine.getDateTo();
			
			BigDecimal dealer = calcLine.getDayCharge();
			
			if (dateFrom.after(dateTo))
				return "DateFrom must be earlier than DateTo";
			else if (dealer.compareTo(BigDecimal.ZERO) > 0 && dateFrom.before(TimeUtil.addDays(validTo, 1)) && isLastRecord)
				return "DateFrom cannot be earlier or equal to Contract";
			else if (dealer.compareTo(BigDecimal.ZERO) < 0 && !dateTo.equals(validTo) && isLastRecord)
				return "DateTo cannot change";
		}
		
		return "";
	}
}
