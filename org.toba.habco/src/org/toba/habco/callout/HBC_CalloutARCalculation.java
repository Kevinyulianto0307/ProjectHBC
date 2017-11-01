package org.toba.habco.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MProjectMilestone;

/**
 * @author TommyAng
 * Callout ARCalculation
 */

public class HBC_CalloutARCalculation extends CalloutEngine implements IColumnCallout{
	
	int currencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
	
	private String qtyCharge(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(value == null)
			return "";
		
		int AR_CalculationLine_ID = Env.getContextAsInt(ctx, WindowNo, "HBC_ARCalculationLine_ID");
		
		MARCalculationLine calcLine = new MARCalculationLine(ctx, AR_CalculationLine_ID, null);
		MARCalculation calc = new MARCalculation(ctx, calcLine.getHBC_ARCalculation_ID(), null);
		MContract contract = (MContract) calc.getHBC_Contract();
		MProjectMilestone milestone = (MProjectMilestone) calcLine.getC_ProjectMilestone();
		
		//@phie
		//request from bella, user can input qty
		//all contract can have ar calc type demmurage, so this validation must check first
		if(calcLine.getARCalculationType().equals(MARCalculationLine.ARCALCULATIONTYPE_Demurrage)){
			
			BigDecimal qtyCharge = (BigDecimal) value;
			if (qtyCharge==null)
				qtyCharge = Env.ZERO;
			BigDecimal demPercentage = (BigDecimal) mTab.getValue("DemurragePercentage");
			if (demPercentage==null)
				demPercentage = Env.ZERO;
			
			BigDecimal qtyToInvoiced = qtyCharge.multiply(demPercentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			BigDecimal unitPrice = (BigDecimal) mTab.getValue(MARCalculationLine.COLUMNNAME_UnitPrice);
			if (unitPrice==null)
				unitPrice=Env.ZERO;
			BigDecimal totalAmt = qtyToInvoiced.multiply(unitPrice);
			
			mTab.setValue(MARCalculationLine.COLUMNNAME_QtyToInvoiced, qtyToInvoiced);
			//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, qtyToInvoiced.multiply(unitPrice));
			if(contract.getC_Currency_ID()==currencyID)
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
			else
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
			
		}
		//end phie
		
		else if(contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_AnnualContract)){
		
			BigDecimal qtyCharge = (BigDecimal) value;
			if (qtyCharge==null)
				qtyCharge = Env.ZERO;

			mTab.setValue(MARCalculationLine.COLUMNNAME_QtyToInvoiced, qtyCharge);
			
			BigDecimal qtyToInvoiced = qtyCharge;
			BigDecimal unitPrice = (BigDecimal) mTab.getValue(MARCalculationLine.COLUMNNAME_UnitPrice);
			if (unitPrice==null)
				unitPrice=Env.ZERO;
			
			BigDecimal totalAmt = qtyToInvoiced.multiply(unitPrice);
			
			//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
			if(contract.getC_Currency_ID()==currencyID)
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
			else
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
		
		}
		
		/*
		else if(contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment) && milestone.isFinalMilestone()){
			
			BigDecimal qtyCharge = (BigDecimal) value;
			if (qtyCharge==null)
				qtyCharge = Env.ZERO;
			BigDecimal qtyPrevious = calcLine.getPreviousTotalQty();
			BigDecimal qtyToInvoiced = qtyCharge.subtract(qtyPrevious);
			BigDecimal unitPrice = (BigDecimal) mTab.getValue(MARCalculationLine.COLUMNNAME_UnitPrice);
			BigDecimal totalAmt = qtyToInvoiced.multiply(unitPrice);
			
			mTab.setValue(MARCalculationLine.COLUMNNAME_QtyToInvoiced, qtyToInvoiced);
			//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
			if(contract.getC_Currency_ID()==currencyID)
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
			else
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
			
		}
		*/
		
		else if(contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment)){
			
			BigDecimal qtyCharge = (BigDecimal) value;
			if (qtyCharge==null)
				qtyCharge = Env.ZERO;
			//BigDecimal milestonePercentage = (BigDecimal) mTab.getValue(MARCalculationLine.COLUMNNAME_MilestonePercentage);
			//if (milestonePercentage==null)
				//milestonePercentage = Env.ZERO;
			
			//BigDecimal qtyToInvoiced = qtyCharge.multiply(milestonePercentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			BigDecimal unitPrice = (BigDecimal) mTab.getValue(MARCalculationLine.COLUMNNAME_UnitPrice);
			if (unitPrice==null)
				unitPrice = Env.ZERO;
			
			BigDecimal totalAmt = qtyCharge.multiply(unitPrice);
			
			mTab.setValue(MARCalculationLine.COLUMNNAME_QtyToInvoiced, qtyCharge);
			//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
			if(contract.getC_Currency_ID()==currencyID)
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
			else
				mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
		
		}
		
		return "";
	}

	private String qtyToInvoiced(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null)
			return "";
		
		int contractID = -1;
		if  (mTab.getValue("HBC_Contract_ID")==null)
			return "";
		
		contractID = (int) mTab.getValue("HBC_Contract_ID");
		MContract contract = new MContract(ctx, contractID, null);
		
		BigDecimal qtyToInvoiced = (BigDecimal) value;
		if (qtyToInvoiced==null)
			qtyToInvoiced=Env.ZERO;
		
		BigDecimal unitPrice = (BigDecimal) mTab.getValue(MARCalculationLine.COLUMNNAME_UnitPrice);
		if (unitPrice==null)
			unitPrice=Env.ZERO;
		
		BigDecimal totalAmt = qtyToInvoiced.multiply(unitPrice);
		
		//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
		if(contract.getC_Currency_ID()==currencyID)
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
		else
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
		
		return "";
	}
	
	private String dayToInvoice(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null)
			return "";

		int contractID = -1;
		if  (mTab.getValue("HBC_Contract_ID")==null)
			return "";
		
		contractID = (int) mTab.getValue("HBC_Contract_ID");
		MContract contract = new MContract(ctx, contractID, null);
		
		BigDecimal dayToInvoice = (BigDecimal) value;
		if (dayToInvoice==null)
			dayToInvoice=Env.ZERO;
		
		BigDecimal dayInvoicePercentage = Env.ZERO;
		BigDecimal totalAmt = Env.ZERO;
		Timestamp dateFrom = (Timestamp) mTab.getValue("DateFrom");
		if (dateFrom==null)
			return "";
		
		BigDecimal unitPrice = (BigDecimal) mTab.getValue("UnitPrice");
		if (unitPrice==null)
			unitPrice=Env.ZERO;
		
		dayInvoicePercentage = dayToInvoice.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(dateFrom)),2,RoundingMode.HALF_UP);
		totalAmt = dayInvoicePercentage.multiply(unitPrice);
		
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayInvoicePercentage, dayInvoicePercentage);
		//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
		if(contract.getC_Currency_ID()==currencyID)
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
		else
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
		
		return "";
	}
	
	private String dateFrom(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null)
			return "";
		
		int contractID = (int) mTab.getValue("HBC_Contract_ID");
		MContract contract = new MContract(ctx, contractID, null);
		
		Timestamp dateFrom = (Timestamp) value;
		Timestamp dateTo = (Timestamp) mTab.getValue("DateTo");
		
		BigDecimal dayInvoicePercentage = Env.ZERO;
		BigDecimal totalAmt = Env.ZERO;
		BigDecimal dayChargeCalculation = new BigDecimal(TimeUtil.getDaysBetween(dateFrom, dateTo)+1);
		BigDecimal dayCharge = (BigDecimal) mTab.getValue("DayCharge");
		BigDecimal dayToInvoice = Env.ZERO;
		
		
		if (dayCharge.compareTo(BigDecimal.ZERO)<0){
			dayCharge = dayChargeCalculation.negate();
			dayToInvoice = dayCharge;
		}else{
			dayCharge = dayChargeCalculation;
			dayToInvoice = dayCharge;
		}
		
		dayInvoicePercentage = dayToInvoice.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(dateFrom)),2,RoundingMode.HALF_UP);
		totalAmt = dayInvoicePercentage.multiply((BigDecimal) mTab.getValue("UnitPrice"));
		
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayChargeCalculation, dayChargeCalculation);
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayCharge, dayCharge);
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayToInvoice, dayToInvoice);
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayInvoicePercentage, dayInvoicePercentage);
		//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
		if(contract.getC_Currency_ID()==currencyID)
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
		else
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
		
		return "";
	}
	
	private String dateTo(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null)
			return "";		
		
		int contractID = (int) mTab.getValue("HBC_Contract_ID");
		MContract contract = new MContract(ctx, contractID, null);
		
		Timestamp dateTo = (Timestamp) value;
		Timestamp dateFrom = (Timestamp) mTab.getValue("DateFrom");
		
		BigDecimal dayInvoicePercentage = Env.ZERO;
		BigDecimal totalAmt = Env.ZERO;
		BigDecimal dayChargeCalculation = new BigDecimal(TimeUtil.getDaysBetween(dateFrom, dateTo)+1);
		BigDecimal dayCharge = (BigDecimal) mTab.getValue("DayCharge");
		BigDecimal dayToInvoice = Env.ZERO;
			
		if (dayCharge.compareTo(BigDecimal.ZERO)<0){
			dayCharge = dayChargeCalculation.negate();
			dayToInvoice = dayCharge;
		}else{
			dayCharge = dayChargeCalculation;
			dayToInvoice = dayCharge;
		}
		
		dayInvoicePercentage = dayToInvoice.divide(new BigDecimal(TimeUtil.getNumberOfDaysInMonth(dateFrom)),2,RoundingMode.HALF_UP);
		totalAmt = dayInvoicePercentage.multiply((BigDecimal) mTab.getValue("UnitPrice"));
		
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayChargeCalculation, dayChargeCalculation);
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayCharge, dayCharge);
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayToInvoice, dayToInvoice);
		mTab.setValue(MARCalculationLine.COLUMNNAME_DayInvoicePercentage, dayInvoicePercentage);
		//mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt);
		
		if(contract.getC_Currency_ID()==currencyID)
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt, totalAmt.setScale(0, RoundingMode.HALF_UP));
		else
			mTab.setValue(MARCalculationLine.COLUMNNAME_TotalAmt,totalAmt.setScale(2, RoundingMode.HALF_UP));
		
		return "";
	}
	
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MARCalculationLine.COLUMNNAME_QtyCharge))
			return qtyCharge(ctx, WindowNo, mTab, mField, value, oldValue);
		
		else if(mField.getColumnName().equals(MARCalculationLine.COLUMNNAME_QtyToInvoiced))
			return qtyToInvoiced(ctx, WindowNo, mTab, mField, value, oldValue);
		
		else if(mField.getColumnName().equals(MARCalculationLine.COLUMNNAME_DayToInvoice))
			return dayToInvoice(ctx, WindowNo, mTab, mField, value, oldValue);
		
		else if(mField.getColumnName().equals(MARCalculationLine.COLUMNNAME_DateFrom))
			return dateFrom(ctx, WindowNo, mTab, mField, value, oldValue);
		
		else if(mField.getColumnName().equals(MARCalculationLine.COLUMNNAME_DateTo))
			return dateTo(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return "";
	}
}
