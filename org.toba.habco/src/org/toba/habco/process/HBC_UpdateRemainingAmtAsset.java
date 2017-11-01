package org.toba.habco.process;

import java.math.BigDecimal;

import org.compiere.model.MAsset;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MDepreciationExp;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;


/**
 * @author TommyAng
 * Process Mass Update Remaining Amt
 * Window Asset, Table A_Asset
 * Tab Expense, Table A_Depreciation_Exp
 * Source Field A_Asset_Cost, Expense, A_Asset_Remaining
 * Result Field A_Asset_Remaining
 */
public class HBC_UpdateRemainingAmtAsset extends SvrProcess{
	
	private BigDecimal cost = Env.ZERO;
	private BigDecimal remainAmt = Env.ZERO;
	
	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		for(int assetID : getAssetIDs()){
			int count = 0;
			for(int expenseID : getExpenseIDs(assetID)){
				
				MDepreciationExp dexp = new MDepreciationExp(getCtx(), expenseID, get_TrxName());
				
				if(cost.compareTo(Env.ZERO)==0)
					cost = dexp.getA_Asset_Cost();
				
				if(count==0)
					remainAmt = cost.subtract(dexp.getExpense()).subtract(getAccumulatedDepreciation(assetID));
				else
					remainAmt = remainAmt.subtract(dexp.getExpense());
				
				dexp.setA_Asset_Remaining(remainAmt);
				dexp.saveEx();
				
				count++;
			}
			
			cost = Env.ZERO;
			
		}
		return null;
	}
	
	/**
	 * @TommyAng
	 * @return array of Asset_ID
	 * 
	 */
	protected int[] getAssetIDs(){
		
		String where = "";
		int[] ids = new Query(getCtx(), MAsset.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return ids;
	}
	
	/**
	 * @TommyAng
	 * @return array of Expense_ID
	 * 
	 */
	protected int[] getExpenseIDs(int assetID){
		
		String where = "A_Asset_ID="+assetID;
		int[] ids = new Query(getCtx(), MDepreciationExp.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setOrderBy("A_Period")
		.getIDs();
		
		return ids;
	}
	
	/**
	 * @TommyAng
	 * @return Accumulated Depreciation
	 * 
	 */
	protected BigDecimal getAccumulatedDepreciation(int assetID){
		
		String where = "A_Asset_ID="+assetID;
		int id = new Query(getCtx(), MAssetAddition.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		
		MAssetAddition additionnew = new MAssetAddition(getCtx(), id, get_TrxName());
		BigDecimal AccumulatedDepreciation = additionnew.getA_Accumulated_Depr();
				
		return AccumulatedDepreciation;
	}
}
