package org.toba.habco.ar.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MProjectMilestone;
import org.toba.habco.model.MProjectTypeMilestone;

/**
 * @author Stephan
 * calculation for spot
 */
public class HBC_CalculateSpot extends SvrProcess{

	final static protected String LOADING = "LOA";
	final static protected String DISCHARGING = "DIS";
	final static protected String CARGO_ADDITION = "CAD";
	final static protected String CARGO_REDUCTION = "CRE";
	final static protected String CARGO_TRANSFER = "TRC";
	
	/**parameter*/
	//protected int p_HBC_Trip_ID = 0;
	protected int p_HBC_Contract_ID = 0;
	/*
	protected int p_HBC_Tugboat_ID = 0;
	protected int p_HBC_Barge_ID = 0;
	protected int p_C_BPartner_ID = 0;
	*/
	
	protected int line = 0;
	//protected int HBC_Trip_ID = 0;
	protected int currencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			//else if (name.equals("HBC_Trip_ID")){
			//	p_HBC_Trip_ID = para[i].getParameterAsInt();
			//}
			else if (name.equals("HBC_Contract_ID")){
				p_HBC_Contract_ID = para[i].getParameterAsInt();
			}
			/*
			else if (name.equals("HBC_Tugboat_ID")){
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("HBC_Barge_ID")){
				p_HBC_Barge_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_BPartner_ID")){
				p_C_BPartner_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("HBC_ContractType")){
				p_HBC_ContractType = para[i].getParameterAsString();
			}*/
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_Contract_ID <= 0)
			return "Contract is mandatory";
		
		MContract contract = new MContract(getCtx(), p_HBC_Contract_ID, get_TrxName());
		
		//Validate contract must be either SPAL / Spot
		if ((!contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment)) |
				(!contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SPAL)))
		return "Process aborted, Contract Must Be SPAL";

		MARCalculation calc = null;
		
		//only one trip per contract for spot shipment / spal
		
		String whereClause = "IsARCalculation = 'N' AND HBC_Contract_ID="+p_HBC_Contract_ID;
		
		int HBC_Trip_ID = new Query(getCtx(), MTrip.Table_Name, whereClause, get_TrxName())
							.setOnlyActiveRecords(true)
							.firstIdOnly();
		
		if (HBC_Trip_ID < 0)
			return "No Unprocessed Trip for Contract " + contract.getDocumentNo();
				
		calc = calculationForSpot(HBC_Trip_ID);
		
		if(calc == null)
			return "Cant create AR Calculation - NO TRIP";
		
		String message = Msg.parseTranslation(getCtx(), "@GeneratedARCalculation@ "+calc.getDocumentNo());
		addBufferLog(0, null, null, message, calc.get_Table_ID(), calc.get_ID());
		
		return "";
	}
	
	/**
	 * method for create calculation if category spot shipment
	 * @param HBC_Trip_ID
	 * @return MARCalculation
	 */
	protected MARCalculation calculationForSpot(int HBC_Trip_ID){
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		MProjectTypeMilestone typeMilestone = (MProjectTypeMilestone) contract.getC_ProjectTypeMilestone();
		
		// create AR Calculation here
		// create calculation header
		StringBuilder where = new StringBuilder();
		where.append("HBC_Trip_ID="+trip.getHBC_Trip_ID()).append(" AND HBC_Contract_ID="+contract.getHBC_Contract_ID());
		
		int current_id = new Query(getCtx(), MARCalculation.Table_Name, where.toString(), get_TrxName())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.firstId();
		
		MARCalculation calc = null;
		if(current_id > 0){
			calc = new MARCalculation(getCtx(), current_id, get_TrxName());
			log.info("Use Current ID Calculation");
			
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM HBC_ARCalculationLine WHERE HBC_ARCalculation_ID = ? AND Processed = 'N'");
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{calc.getHBC_ARCalculation_ID()}, get_TrxName());
			log.info("DELETE AR CalculationLine: "+no);
		}
		else{
			calc = new MARCalculation(getCtx(), 0, get_TrxName());
			calc.setAD_Org_ID(trip.getAD_Org_ID());
			calc.setHBC_Trip_ID(trip.getHBC_Trip_ID());
			calc.setHBC_Contract_ID(trip.getHBC_Contract_ID());
			calc.setHBC_Tugboat_ID(trip.getHBC_Tugboat_ID());
			calc.setC_Project_ID(trip.getC_Project_ID());
			calc.setHBC_Barge_ID(trip.getHBC_Barge_ID());
			calc.setFrom_PortPosition_ID(trip.getFrom_PortPosition_ID());
			calc.setTo_PortPosition_ID(trip.getTo_PortPosition_ID());
			calc.setStartDate(trip.getDateStart());
			calc.setFinishDate(trip.getEndDate());
			calc.setARCalculationType(contract.getHBC_ContractType());
			calc.saveEx();
			log.info("Create New Calculations");
		}
		
		//Loop through project milestone to calculate AR calculation line
		int count = 0;
		for (MProjectMilestone milestone : typeMilestone.getProjectMilestone()) {
			
			count+=1;
			
			//Search for previous AR calculation line created for the current value of project milestone
			//TODO: @Phie: should there be any recalculate for unprocessed AR calculation line?
			//Done, Line 142
			StringBuilder where2 = new StringBuilder();
			where2.append("C_ProjectMilestone_ID="+milestone.getC_ProjectMilestone_ID())
				  .append(" AND HBC_Trip_ID="+trip.getHBC_Trip_ID());
			
			boolean match = new Query(getCtx(), MARCalculationLine.Table_Name, where2.toString(), get_TrxName())
							.setClient_ID()
							.setOnlyActiveRecords(true)
							.match();
			
			// skip if already created ar calculation line
			if(match)
				continue;
			
			BigDecimal price = contract.getUnitPrice();
			BigDecimal percentage = milestone.getMilestonePercentage();
			BigDecimal totalAmt = Env.ZERO;
			
			//@win: start here, this is the most important part
			BigDecimal totalCargoQty = getTotalCargoQty(HBC_Trip_ID, milestone);
			BigDecimal qtyToInvoiced = getQtyToInvoiced(HBC_Trip_ID, milestone, contract);
			BigDecimal qtyCharge = (totalCargoQty.compareTo(contract.getAmountOfCargo()) > 0) ? 
											totalCargoQty : contract.getAmountOfCargo();
			if(qtyToInvoiced.compareTo(Env.ZERO) <= 0 || price.compareTo(Env.ZERO) <= 0)
				continue;
			
			//@win: end here
			
			totalAmt = qtyToInvoiced.multiply(price);
			
			/*
			BigDecimal qtytoinvoiced=totalCargoQty.multiply(percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			BigDecimal totalCargoQty = totalCargoqty;
			
			if(milestone.getSubtractLine() > 0){
				qtytoinvoiced = qtytoinvoiced.subtract(getAmtSubtractLine(milestone));
			}
			*/
			
			//
			MARCalculationLine calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
			calcLine.setLine(milestone.getSeqNo());
			calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
			calcLine.setAD_Org_ID(trip.getAD_Org_ID());
			calcLine.setC_ProjectMilestone_ID(milestone.getC_ProjectMilestone_ID());
			calcLine.setAmountOfCargo(contract.getAmountOfCargo());
			calcLine.setTotalCargoQty(totalCargoQty);
			calcLine.setQtyCharge(qtyCharge);
			calcLine.setQtyToInvoiced(qtyToInvoiced);
			calcLine.setUnitPrice(price);
			calcLine.setHBC_Contract_ID(trip.getHBC_Contract_ID());
			calcLine.setC_Project_ID(trip.getC_Project_ID());
			calcLine.setHBC_Tugboat_ID(trip.getHBC_Tugboat_ID());
			calcLine.setC_BPartner_ID(contract.getCustomer_BPartner_ID());
			calcLine.setHBC_Barge_ID(trip.getHBC_Barge_ID());
			calcLine.setHBC_Trip_ID(trip.getHBC_Trip_ID());
			calcLine.setMilestonePercentage(milestone.getMilestonePercentage());
			calcLine.setFrom_PortPosition_ID(contract.getFrom_PortPosition_ID());
			calcLine.setTo_PortPosition_ID(contract.getTo_PortPosition_ID());
			calcLine.setARCalculationType(contract.getHBC_ContractType());

			if(milestone.getM_Product_ID()>0)
				calcLine.set_ValueOfColumn("M_Product_ID", milestone.getM_Product_ID());
			
			if(contract.getC_Currency_ID()==currencyID)
				calcLine.setTotalAmt(totalAmt.setScale(0, RoundingMode.HALF_UP));
			else
				calcLine.setTotalAmt(totalAmt.setScale(2, RoundingMode.HALF_UP));

			if(!milestone.isMinimumCargo() && milestone.getCommittedAmt().compareTo(Env.ZERO) <= 0){
				//TODO: note from @win. If there should be any calculation of totalamt then it should be done on previous segment.
				//this part should only be reserved for setting totalamt..
				calcLine.setTotalAmt(totalAmt.subtract(getPreviousTotalAmt(HBC_Trip_ID, milestone)));

				/*
				totalCargoQty=(BigDecimal)trip.get_Value("TotalCargoQty");
				totalAmt=price.multiply(totalCargoQty);
				calcLine.setQtyCharge(totalCargoQty);
				calcLine.setQtyToInvoiced(totalCargoQty.multiply(percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP));
				*/
			}
			//commented by @win.. it basically reverting  anything set at upper code
			//calcLine.setTotalAmt(totalAmt);
			calcLine.saveEx();
			
			/*
			if(milestone.isFinalMilestone()){
				totalAmt = price.multiply(totalCargoQty).subtract(getPreviousTotalAmt(milestone));
				calcLine.setQtyToInvoiced(totalCargoQty.subtract(getPreviousTotalQty(milestone)));
			}
			else if(contract.isLumpsum())
				totalAmt = price;
			else if(milestone.getCommittedAmt().compareTo(Env.ZERO) > 0){
				totalAmt = price.multiply(percentage);
				totalAmt = totalAmt.divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
			}
			*/
			
			log.info("Created Line Calculation TotalAmt#"+totalAmt+" Price#"+price+" Qty#"+totalCargoQty+" Percentage#"+percentage);
		}
		log.info("End of Spot Shipment Total Line #"+count);
		
		return calc;
	}
	
	/**
	 * get total amount for subtract line
	 * @param HBC_Trip_ID, MProjectMilestone
	 * @return total amount subtract line
	 */
	protected BigDecimal getSubtractLineQty(int HBC_Trip_ID, MProjectMilestone milestone){
		
		StringBuilder where = new StringBuilder();
		where.append("SeqNo = "+milestone.getSubtractLine());
		where.append(" AND C_ProjectTypeMilestone_ID="+milestone.getC_ProjectTypeMilestone_ID());
		
		int otherMilestone = new Query(getCtx(), MProjectMilestone.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		
		MProjectMilestone subtractMilestone = new MProjectMilestone(getCtx(), otherMilestone, get_TrxName());
		
		BigDecimal subtractQty = subtractMilestone.getCommittedAmt()
				.divide(getPricePostShipment(HBC_Trip_ID, subtractMilestone),2,RoundingMode.HALF_UP);
		
		log.info("SubtractLineQty#"+subtractQty);
		
		return subtractQty;
	}
	
	/**
	 * get total amount from another milestone
	 * @param HBC_Trip_ID, MProjectMilestone
	 * @return previous total amount
	 */
	protected BigDecimal getPreviousTotalAmt(int HBC_Trip_ID, MProjectMilestone milestone){
		
		BigDecimal totalAmt = Env.ZERO;
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		
		StringBuilder where = new StringBuilder();
		where.append("SeqNo < "+milestone.getSeqNo());
		where.append(" AND C_ProjectTypeMilestone_ID="+milestone.getC_ProjectTypeMilestone_ID());
		
		int[] otherMilestone = new Query(getCtx(), MProjectMilestone.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		for (int other_id : otherMilestone) {
			where = new StringBuilder();
			where.append("C_ProjectMilestone_ID="+other_id)
			.append(" AND HBC_Trip_ID="+trip.getHBC_Trip_ID())
			.append(" AND HBC_Contract_ID="+contract.getHBC_Contract_ID());
			
			int id = new Query(getCtx(), MARCalculationLine.Table_Name, where.toString(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.firstId();
			
			MARCalculationLine calcLine = new MARCalculationLine(getCtx(), id, get_TrxName());
			totalAmt = totalAmt.add(calcLine.getTotalAmt());
			
		}
		
		log.info("PreviouseTotalAmt#"+totalAmt);
		
		return totalAmt;
	}
	
	/**
	 * get total quantity from another milestone
	 * @param HBC_Trip_ID, MProjectMilestone
	 * @return previous total quantity
	 */
	protected BigDecimal getPreviousTotalQty(int HBC_Trip_ID, MProjectMilestone milestone){
		
		BigDecimal totalQty = Env.ZERO;
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		
		StringBuilder where = new StringBuilder();
		where.append("SeqNo < "+milestone.getSeqNo());
		where.append(" AND C_ProjectTypeMilestone_ID="+milestone.getC_ProjectTypeMilestone_ID());
		
		int[] otherMilestone = new Query(getCtx(), MProjectMilestone.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		for (int other_id : otherMilestone) {
			where = new StringBuilder();
			where.append("C_ProjectMilestone_ID="+other_id)
			.append(" AND HBC_Trip_ID="+trip.getHBC_Trip_ID())
			.append(" AND HBC_Contract_ID="+contract.getHBC_Contract_ID());
			
			int id = new Query(getCtx(), MARCalculationLine.Table_Name, where.toString(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.firstId();
			
			MARCalculationLine calcLine = new MARCalculationLine(getCtx(), id, get_TrxName());
			totalQty = totalQty.add(calcLine.getQtyToInvoiced());
			
		}
		
		log.info("PreviousTotalQty#"+totalQty);
		
		return totalQty;
	}
	
	/**
	 * get price for contract type post shipment
	 * @param HBC_Trip_ID, MProjectMilestone
	 * @return unit price
	 */
	
	protected BigDecimal getPricePostShipment(int HBC_Trip_ID, MProjectMilestone milestone){
		
		BigDecimal price = Env.ZERO;
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		//if(milestone.getCommittedAmt().compareTo(Env.ZERO) > 0)
			//price = milestone.getCommittedAmt().divide(contract.getUnitPrice(),2,RoundingMode.HALF_UP);
		//else
			price = contract.getUnitPrice();
		
		log.info("Price Post Shipment #"+price);
		
		return price;
	}
	
	/**
	 * get quantity for contract type post shipment
	 * @param HBC_Trip_ID, MProjectMilestone
	 * @return total cargo quantity
	 */
	protected BigDecimal getTotalCargoQty(int HBC_Trip_ID, MProjectMilestone milestone){
		
		BigDecimal qty = Env.ZERO;
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		
		//Jika milestone yang bersangkutan dinyatakan dalam nilai lump sum, maka nilai yang ditagih adalah sesuai committed amt.
		if(milestone.getCommittedAmt().compareTo(Env.ZERO) > 0){
			BigDecimal committedAmt = milestone.getCommittedAmt();
			BigDecimal price = contract.getUnitPrice();
			qty = committedAmt.divide(price,2,RoundingMode.HALF_UP);
			//qty = milestone.getCommittedAmt().divide(getPricePostShipment(milestone),2,RoundingMode.HALF_UP);
			
			String info = "TotalCargoQty CommitedAmt#"+committedAmt+" | ContractPrice#"+price+" | Qty#"+qty;
			createLogEx(qty, info);
			
		}
		else if(milestone.isMinimumCargo() && milestone.isPositionFrom()){
			boolean isSamePosition = false;
			boolean isFillStartDate = false;
			
			MPosition[] positions = trip.getPosition();
			for(MPosition position : positions){
				isSamePosition = false;
				isFillStartDate = false;
				
				if(position.getHBC_PortPosition_ID() == trip.getFrom_PortPosition_ID())
					isSamePosition = true;
				if(position.getDateStart()!= null)
					isFillStartDate = true;
				if(isSamePosition && isFillStartDate){
					qty = contract.getAmountOfCargo();
					break;
				}
			}
			
			String info = "TotalCargoQty isMinimumCargo & isPositionFrom | SamePosition#"+isSamePosition
					+" |FillStartDate#"+isFillStartDate+" | Qty#"+qty;
			createLogEx(qty, info);
			
		}
		else if(!milestone.isMinimumCargo() || milestone.isFinalMilestone()){
			boolean isLoading = false;
			boolean isFillEndDate = false;
			
			MPosition[] positions = trip.getPosition();
			for(MPosition position : positions){
				MShipActivity[] activities = position.getShipActivity();
				for (MShipActivity shipActivity : activities) {
					
					if(shipActivity.getC_Activity().getValue().equals(LOADING))
						isLoading = true;
					if(shipActivity.getFinishDate() != null)
						isFillEndDate = true;
					
					if(isLoading && isFillEndDate){
						qty = shipActivity.getAmountOfCargo();
						break;
					}
				}
				if(isLoading && isFillEndDate)
					break;
			}
			
			String info = "TotalCargoQty !isMinimumCargo OR isFinalMilestone | Loading#"+isLoading
					+" |FillEndDate#"+isFillEndDate+" | Qty#"+qty;
			createLogEx(qty, info);
			
		}
		
		
		else if(contract.getContractStatus().equals(MContract.CONTRACTSTATUS_Finish)){
				qty = contract.getAmountOfCargo();
				String info = "TotalCargoQty ContractStatus#Finish"+" | Qty#"+qty;
				createLogEx(qty, info);
				
		}
		
		//log.info("Qty Post Shipment #"+qty);
		
		return qty;
	}

	/**
	 * get quantity to invoice
	 * @param HBC_Trip_ID, MProjectMilestone, MContract
	 * @return quantity to invoice
	 */
	protected BigDecimal getQtyToInvoiced(int HBC_Trip_ID, MProjectMilestone milestone, MContract contract){
		
		BigDecimal qtyToInvoiced = Env.ZERO;
		
		if(contract.isLumpsum()){
			qtyToInvoiced = Env.ONE;
			
			String info = "QtyToInvoiced isLumpsump | Qty#"+qtyToInvoiced;
			createLogEx(qtyToInvoiced, info);
			
		}
		
		else if(milestone.getSubtractLine() > 0){
			qtyToInvoiced = getTotalCargoQty(HBC_Trip_ID, milestone).multiply(milestone.getMilestonePercentage().divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP));
			qtyToInvoiced = qtyToInvoiced.subtract(getSubtractLineQty(HBC_Trip_ID, milestone));
			
			String info = "QtyToInvoiced getSubtractLine | Qty#"+qtyToInvoiced;
			createLogEx(qtyToInvoiced, info);
			
		}
		
		else if(milestone.isFinalMilestone()){
			BigDecimal totalCargoQty = getTotalCargoQty(HBC_Trip_ID, milestone);
			BigDecimal prevTotalQty = getPreviousTotalQty(HBC_Trip_ID, milestone);
			qtyToInvoiced = totalCargoQty.subtract(prevTotalQty);
			
			String info = "QtyToInvoiced isFinalMilestone | TotalCargoQty#"
			+totalCargoQty+" | PrevTotalQty#"+prevTotalQty+" | QtyToInvoiced#"+qtyToInvoiced;
			createLogEx(qtyToInvoiced, info);
			
		}
		
		else{
			BigDecimal totalCargoQty = getTotalCargoQty(HBC_Trip_ID, milestone);
			BigDecimal milestonePercentage = milestone.getMilestonePercentage();
			qtyToInvoiced = totalCargoQty.multiply(milestonePercentage.divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP));
			
			String info = "QtyToInvoiced Else Condition | TotalCargoQty#"
			+totalCargoQty+" | milestonePercent#"+milestonePercentage+" | QtyToInvoiced#"+qtyToInvoiced;
			createLogEx(qtyToInvoiced, info);
			
		}
		
		//log.info("Qty to Invoice #"+qtyToInvoiced);
		
		return qtyToInvoiced;
	}
	
	/**
	 * create log throw some exception
	 * @throws Exception if <=0
	 */
	private void createLogEx(BigDecimal value, String msg){
		
		log.info(msg);
		
		if(value.compareTo(Env.ZERO) <= 0){
			log.severe(msg);
			//throw new AdempiereException(msg);
		}
		
	}
	
}
