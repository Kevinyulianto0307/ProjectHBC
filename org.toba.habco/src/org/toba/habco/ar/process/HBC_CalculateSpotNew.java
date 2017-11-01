package org.toba.habco.ar.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MProjectMilestone;
import org.toba.habco.model.MProjectTypeMilestone;

/**
 * @author Stephan
 * calculation for spot
 */
public class HBC_CalculateSpotNew extends SvrProcess{

	final static protected String LOADING = "LOA";
	final static protected String DISCHARGING = "DIS";
	final static protected String CARGO_ADDITION = "CAD";
	final static protected String CARGO_REDUCTION = "CRE";
	final static protected String CARGO_TRANSFER = "TRC";
	
	/**parameter*/
	protected int p_HBC_Contract_ID = 0;
	protected int line = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("HBC_Contract_ID")){
				p_HBC_Contract_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_Contract_ID <= 0)
			return "Contract is mandatory";
		
		MContract contract = new MContract(getCtx(), p_HBC_Contract_ID, get_TrxName());
		
		//Validate contract must be either SPAL / Spot
		if ((!contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment)) &&
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
	protected MARCalculation calculationForSpot(int HBC_Trip_ID) throws AdempiereException {
		
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		MProjectTypeMilestone typeMilestone = (MProjectTypeMilestone) contract.getC_ProjectTypeMilestone();
		
		if(!typeMilestone.get_ValueAsBoolean("isConfirm"))
			throw new AdempiereException("This Project Type Milestone is not confirmed..");
		
		// create AR Calculation here
		// create calculation header
		StringBuilder where = new StringBuilder();
		where.append("HBC_Trip_ID="+trip.getHBC_Trip_ID()).append(" AND HBC_Contract_ID="+contract.getHBC_Contract_ID());
		
		//Create AR Calculation or use existing
		int current_id = new Query(getCtx(), MARCalculation.Table_Name, where.toString(), get_TrxName())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.firstId();
		
		MARCalculation calc = null;
		if(current_id > 0){
			calc = new MARCalculation(getCtx(), current_id, get_TrxName());
			log.info("Use Current ID Calculation");
			
			//Delete unprocessed document
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
		
		//Cek tidak boleh ada 2 DP
		
		//Check for unprocessed project milestone
		String whereMilestone = "C_ProjectTypeMilestone_ID=? AND C_ProjectMilestone_ID NOT IN "
				+ "(SELECT C_ProjectMilestone_ID FROM HBC_ARCalculationLine WHERE HBC_Trip_ID=?)";
		
		List<MProjectMilestone> listProjectMilestone = new Query(getCtx(), MProjectMilestone.Table_Name, whereMilestone, get_TrxName())
									.setParameters(new Object[]{typeMilestone.getC_ProjectTypeMilestone_ID(), trip.getHBC_Trip_ID()})
									.setOnlyActiveRecords(true)
									.setOrderBy("SeqNo")
									.list();
		
		if (listProjectMilestone.isEmpty())
			throw new AdempiereException("Process aborted.. No Unprocessed Project Milestone");
				
		
		//Loop through project milestone to calculate AR calculation line
		int count = 0;
		//boolean existsDP = false;
		boolean AfterDP_finalMilestone = false;
		for (MProjectMilestone milestone : listProjectMilestone) {
			
			//Match Ar Calc Line?
			
			count++;	
			final BigDecimal milestonePercentage = milestone.getMilestonePercentage();
			final BigDecimal minimumCargo = contract.getAmountOfCargo();
			BigDecimal qtyTimeSheet = Env.ZERO;
			BigDecimal totalCargoQty = Env.ZERO;
			BigDecimal qtyToInvoiced = Env.ZERO;
			BigDecimal price = Env.ZERO;
			BigDecimal totalAmt = Env.ZERO;
			String description = "";
			
			if (contract.isLumpsum()) {
				//calculate cargo, price, qty to invoice, totalamt for lumpsum contract
				qtyToInvoiced = Env.ONE;
				totalCargoQty = Env.ONE; //TODO: @win : 0? 1? or totalCargoQty based on flag? (timesheet/min cargo)
				
				//Get price based on percentage * Contract Amount (on Field Unit Price)
				price = contract.getUnitPrice().multiply(milestonePercentage.divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP));
				
				if (milestone.getCommittedAmt()!=null && milestone.getCommittedAmt().compareTo(Env.ZERO) > 0) {
					if(count != 1)
						throw new AdempiereException("Milestone DP must be in the first record.. (smallest sequence number)");
					
					price = milestone.getCommittedAmt();
					description = "[Lumpsum]: DP = "+price;
				}
				
				//Milestone After DP
				else if (existsDP(typeMilestone.getC_ProjectTypeMilestone_ID())) 
				{
					if(milestone.getC_ProjectMilestone_ID() == getMilestoneAfterDP(typeMilestone.getC_ProjectTypeMilestone_ID()))
					{
						String sqlDPMilestone = "SELECT C_ProjectMilestone_ID FROM C_ProjectMilestone WHERE C_ProjectTypeMilestone_ID=? AND SeqNo < "
								+ milestone.getSeqNo() + " AND CommittedAmt > 0";
	
						int prevDPMilestoneID = DB.getSQLValueEx(get_TrxName(), sqlDPMilestone, milestone.getC_ProjectTypeMilestone_ID());
						if (prevDPMilestoneID <=0)
							throw new AdempiereException("No Down Payment Milestone Found");
						
						MProjectMilestone prevDPMilestone = new MProjectMilestone(getCtx(), prevDPMilestoneID, get_TrxName());
						price = price.subtract(prevDPMilestone.getCommittedAmt());
						description ="[Lumpsum-Termin After DP]: ("+contract.getUnitPrice()+" * "+milestonePercentage+"%) - "+prevDPMilestone.getCommittedAmt()+" = "+price;
					}
				}
				else
					description = "[Lumpsum]: "+contract.getUnitPrice()+" * "+milestonePercentage+"%) = "+price;
				
				totalAmt = price;
				
			} else {
				//calculate for freight
				price = contract.getUnitPrice();
				if(milestone.isPositionFrom()){
					totalCargoQty = contract.getAmountOfCargo();
					qtyToInvoiced = milestonePercentage.multiply(totalCargoQty).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
					description = "[Freight - Posisi Muat - Use Minimum Cargo]: "+price +" * "+totalCargoQty + " * " + milestonePercentage + "%";
				} else if (milestone.getCommittedAmt()!=null && milestone.getCommittedAmt().compareTo(Env.ZERO) > 0) {
					if(count != 1)
						throw new AdempiereException("Milestone DP must be in the first record.. (smallest sequence number)");
					totalCargoQty = Env.ONE;
					qtyToInvoiced = Env.ONE;
					price = milestone.getCommittedAmt();
					description = "[Freight]: DP = "+price;			
				} else if(milestone.isFinalMilestone() && !existsDP(typeMilestone.getC_ProjectTypeMilestone_ID())){
					/**NOTE : Why prevTotalQty is useful?
							  Case without DP
							  25% position from, 50%, 25% final
							  25% * 1000 = 250
							  50% * 1200 = 600
							  25% * 1200 = 300
							  total = 1150 -> missing 50 qty bcause percentage is not from 1 same value
							  So, we still need prev qty in this case
					*/
					BigDecimal prevTotalQty = getPreviousTotalQty(HBC_Trip_ID, milestone);
					totalCargoQty = getQtyTimeSheet(HBC_Trip_ID);
					//Check, IF Minimum Cargo greater than qty time sheet (actual), use minimum cargo
					BigDecimal qtyUsed = (totalCargoQty.compareTo(minimumCargo) >= 0) ? 
							totalCargoQty : minimumCargo;
					qtyToInvoiced = qtyUsed.subtract(prevTotalQty); //RemainingQty
					description = "[Freight-Final Milestone-Remaining qty]: "+price+" * " + qtyToInvoiced+"";
				}else{
					totalCargoQty = getQtyTimeSheet(HBC_Trip_ID);
					//Check, IF Minimum Cargo greater than qty time sheet (actual), use minimum cargo
					BigDecimal qtyUsed = (totalCargoQty.compareTo(minimumCargo) >= 0) ? 
							totalCargoQty : minimumCargo;
					qtyToInvoiced = qtyUsed.multiply(milestonePercentage.divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP));
					description = "[Freight]: "+price + " * " +qtyUsed + " * "+milestonePercentage+"%";
				}
				
				if(qtyToInvoiced.compareTo(Env.ZERO) <= 0 || price.compareTo(Env.ZERO) <= 0)
					continue;
				
				totalAmt = qtyToInvoiced.multiply(price);
				
				if (existsDP(typeMilestone.getC_ProjectTypeMilestone_ID())){
					if(milestone.getC_ProjectMilestone_ID() == getMilestoneAfterDP(typeMilestone.getC_ProjectTypeMilestone_ID()))
					{
						String sqlDPMilestone = "SELECT C_ProjectMilestone_ID FROM C_ProjectMilestone WHERE C_ProjectTypeMilestone_ID=? AND SeqNo < "
								+ milestone.getSeqNo() + " AND CommittedAmt > 0";
	
						int prevDPMilestoneID = DB.getSQLValueEx(get_TrxName(), sqlDPMilestone, milestone.getC_ProjectTypeMilestone_ID());
						if (prevDPMilestoneID <=0)
							throw new AdempiereException("No Down Payment Milestone Found");
						
						MProjectMilestone prevDPMilestone = new MProjectMilestone(getCtx(), prevDPMilestoneID, get_TrxName());
						if(!milestone.isFinalMilestone())
						{
							description = "[Freight-Termin After DP]: "+totalAmt + " - " +prevDPMilestone.getCommittedAmt() + 
									" = "+ totalAmt.subtract(prevDPMilestone.getCommittedAmt()); //Note : don't swap position of this code with calc totalAmt
							totalAmt = totalAmt.subtract(prevDPMilestone.getCommittedAmt());
							totalCargoQty = Env.ONE;
							qtyToInvoiced = Env.ONE;
							price = totalAmt;
						}
						else{
							//In this part, to anticipate termin after DP is also final milestone
							totalCargoQty = getQtyTimeSheet(HBC_Trip_ID);
							//Check, IF Minimum Cargo greater than qty time sheet (actual), use minimum cargo
							BigDecimal qtyUsed = (totalCargoQty.compareTo(minimumCargo) >= 0) ? 
									totalCargoQty : minimumCargo;
							BigDecimal RemainingAmount = (qtyUsed.multiply(price)).subtract(prevDPMilestone.getCommittedAmt());
							description = "[Freight-Termin After DP-Final Milestone]: "+(qtyUsed.multiply(price)) + " - " +prevDPMilestone.getCommittedAmt() + 
									" = " + RemainingAmount;
							
							totalCargoQty = Env.ONE;
							qtyToInvoiced = Env.ONE;
							price = RemainingAmount;
							totalAmt = RemainingAmount;
							AfterDP_finalMilestone = true;
						}
					}
				}
				
				if(existsDP(typeMilestone.getC_ProjectTypeMilestone_ID()) && milestone.isFinalMilestone() && !AfterDP_finalMilestone){
					//In this part, we need to calculate real amt to calculate remaining amt
					//Because the previous code doesn't calculate qtyToInvoiced if exists DP in milestone
					//Why? in the previous code, we calculate qty invoiced using prevTotalQty, which is it's doesn't work here.. (we manipulate prev qty to One)
					
					totalCargoQty = getQtyTimeSheet(HBC_Trip_ID);
					//Check, IF Minimum Cargo greater than qty time sheet (actual), use minimum cargo
					BigDecimal qtyUsed = (totalCargoQty.compareTo(minimumCargo) >= 0) ? 
							totalCargoQty : minimumCargo;
					//remaining = real amount - prev total amt
					BigDecimal RemainingAmount = (qtyUsed.multiply(price)).subtract(getPreviousTotalAmt(HBC_Trip_ID, milestone));
					description = "[Freight-Final Milestone]: "+ qtyUsed + " * "+ price + " - " +getPreviousTotalAmt(HBC_Trip_ID, milestone) + 
							" = " + RemainingAmount;
					
					totalCargoQty = Env.ONE;
					qtyToInvoiced = Env.ONE;
					price = RemainingAmount;
					totalAmt = RemainingAmount;
				}
			}
			
			//Create Line
			MARCalculationLine calcLine = new MARCalculationLine(getCtx(), 0, get_TrxName());
			calcLine.setLine(milestone.getSeqNo());
			calcLine.setHBC_ARCalculation_ID(calc.getHBC_ARCalculation_ID());
			calcLine.set_ValueOfColumn("Description", description);
			calcLine.setAD_Org_ID(trip.getAD_Org_ID());
			calcLine.setC_ProjectMilestone_ID(milestone.getC_ProjectMilestone_ID());
			calcLine.setAmountOfCargo(contract.getAmountOfCargo());
			calcLine.setTotalCargoQty(totalCargoQty);
			calcLine.setQtyCharge(Env.ZERO);
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
			
			if(contract.getC_Currency_ID()==Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID")))
				calcLine.setTotalAmt(totalAmt.setScale(0, RoundingMode.HALF_UP));
			else
				calcLine.setTotalAmt(totalAmt.setScale(2, RoundingMode.HALF_UP));

			calcLine.saveEx();
			
			log.info("Created Line Calculation TotalAmt#"+totalAmt+" Price#"+price+" Qty#"+totalCargoQty+" Percentage#"+milestonePercentage);
		}
		log.info("End of Spot Shipment Total Line #"+count);
		
		return calc;
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
		
		log.info("PreviousTotalAmt#"+totalAmt);
		
		return totalAmt;
	}
	
	/**
	 * get total quantity from another milestone
	 * @param HBC_Trip_ID, MProjectMilestone
	 * @return previous total quantity

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
	*/

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
	

	
	protected BigDecimal getQtyTimeSheet(int HBC_Trip_ID) throws AdempiereException {
		
		BigDecimal qty = Env.ZERO;
		/* commented by @win.. replace with a more efficient way 
		boolean isLoading = false;
		boolean isFillEndDate = false;
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		*/
		
		String activitySQL = "SELECT C_Activity_ID FROM C_Activity WHERE AD_Client_ID=? AND IsActive='Y' AND Value='"+LOADING+"'";
		int activityID = DB.getSQLValueEx(get_TrxName(), activitySQL, Env.getAD_Client_ID(getCtx()));
		
		if (activityID <= 0)
			throw new AdempiereException("No Loading Activity Defined");
		
		String whereClause ="HBC_Position_ID IN (SELECT HBC_Position_ID FROM HBC_Position WHERE HBC_Trip_ID=?) " + 
				"AND HBC_ShipActivity.C_Activity_ID=? AND FinishDate IS NOT NULL";
		
		MShipActivity shipActivity = new Query(getCtx(), MShipActivity.Table_Name, whereClause, get_TrxName())
									.setParameters(new Object[]{HBC_Trip_ID,activityID})
									.setOnlyActiveRecords(true)
									.setOrderBy(MShipActivity.COLUMNNAME_FinishDate + " ASC")
									.first();
		
		qty = shipActivity.getAmountOfCargo();
		
		/* commented by @win
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
		*/
		return qty;
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
	
	public boolean existsDP(int C_ProjectTypeMilestone_ID)
	{
		String whereClause = "C_ProjectTypeMilestone_ID=? and CommittedAmt > 0";
		boolean match = new Query(getCtx(), MProjectMilestone.Table_Name, whereClause, get_TrxName())
					.setParameters(C_ProjectTypeMilestone_ID)
					.match();
		return match;
	}
	
	public int getMilestoneAfterDP(int C_ProjectTypeMilestone_ID)
	{
		String whereClause = "C_ProjectTypeMilestone_ID=? AND CommittedAmt = 0";
		int id = new Query(getCtx(), MProjectMilestone.Table_Name, whereClause, get_TrxName())
					.setParameters(C_ProjectTypeMilestone_ID)
					.setOnlyActiveRecords(true)
					.setOrderBy("SeqNo")
					.firstId();
		return id;
	}
}
