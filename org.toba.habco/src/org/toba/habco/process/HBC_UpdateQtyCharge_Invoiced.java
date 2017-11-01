package org.toba.habco.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.X_HBC_ARCalculation;
import org.toba.habco.model.X_HBC_Contract;

/**
 * 
 * @author Phie Albert
 *
 */
public class HBC_UpdateQtyCharge_Invoiced extends SvrProcess {

	private boolean p_isFollowQtyMinCargo = false;
	private boolean p_isFollowQtyActivity = false;
	private int p_ARCalculation_ID = 0;
	
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("isFollowQtyMinCargo")) //amount of cargo
				p_isFollowQtyMinCargo = para[i].getParameterAsBoolean();
			else if (name.equals("isFollowQtyActivity")) //total cargo qty
				p_isFollowQtyActivity = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		p_ARCalculation_ID = getRecord_ID();
		if(p_ARCalculation_ID == 0)
			throw new AdempiereException("There's no data selected");
		
		if((p_isFollowQtyMinCargo && p_isFollowQtyActivity) 
				|| (!p_isFollowQtyMinCargo && !p_isFollowQtyActivity))
			throw new AdempiereException("Please choose one..");
		
		X_HBC_ARCalculation arc = new X_HBC_ARCalculation(getCtx(), p_ARCalculation_ID, get_TrxName());
		X_HBC_Contract contract = new X_HBC_Contract(getCtx(), arc.getHBC_Contract_ID(), get_TrxName());
		if(!contract.getHBC_ContractType().equals(X_HBC_Contract.HBC_CONTRACTTYPE_SpotShipment)
				&& !contract.getHBC_ContractType().equals(X_HBC_Contract.HBC_CONTRACTTYPE_AnnualContract))
			throw new AdempiereException("This process only if contract annual or spot");
		
		int no =0;
		
		if(p_isFollowQtyActivity){ // total cargo qty
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ARCalculationLine SET QtyCharge = TotalCargoQty , QtyToInvoiced = TotalCargoQty, TotalAmt = UnitPrice * TotalCargoQty "
					+ "WHERE HBC_ARCalculation_ID = ? AND Processed = 'N'");
			no = DB.executeUpdateEx(sb.toString(), new Object[]{p_ARCalculation_ID}, get_TrxName());
		}
		
		if(p_isFollowQtyMinCargo){ // Amount Of Cargo
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ARCalculationLine SET QtyCharge = AmountOfCargo , QtyToInvoiced = AmountOfCargo, TotalAmt = UnitPrice * AmountOfCargo "
					+ "WHERE HBC_ARCalculation_ID = ? AND Processed = 'N'");
			no = DB.executeUpdateEx(sb.toString(), new Object[]{p_ARCalculation_ID}, get_TrxName());
		}
		
		return "Updated : "+no;
	}
}
