package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class HBC_MInvoiceLine extends MInvoiceLine {

	public HBC_MInvoiceLine(MInvoice invoice) {
		super(invoice);
	}

	public HBC_MInvoiceLine(Properties ctx, int C_InvoiceLine_ID,
			String trxName) {
		super(ctx, C_InvoiceLine_ID, trxName);
	}
	
	public HBC_MInvoiceLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6244478174927923471L;

	/** 
	 * before save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		boolean isBeforeSave = super.beforeSave(newRecord);
		if(!isBeforeSave)
			return false;
		
		// custom habco start here
		if(!isDescription() && (getM_Product_ID() == 0 && getC_Charge_ID() == 0))
			throw new AdempiereException(Msg.getMsg(getCtx(), "Product or charge must be selected"));

		//@phie 2705
		if(get_Value("HBC_Percentage_Split_Tugboat") != null && get_Value("HBC_Percentage_Split_Barge") != null)
		{
			if(((BigDecimal)(get_Value("HBC_Percentage_Split_Tugboat"))).add(((BigDecimal)get_Value("HBC_Percentage_Split_Barge"))).compareTo(Env.ONEHUNDRED)>0)
				throw new AdempiereException(Msg.getMsg(getCtx(), "Total percentage tugboat and barge must be less than or equal to 100"));
		}
		//end phie

		return true;
	}
	
	/**
	 * before delete invoice line
	 * @return true if deleted
	 */
	@Override
	protected boolean beforeDelete(){
			
		if (isProcessed())
			return false;
		
		if(!getC_Invoice().isSOTrx()){
			//@TommyAng Delete MMatchCostActivity & Update T_ReferenceCode by AllocationCode
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT AllocationCode FROM "+MMatchCostActivity.Table_Name)
			.append(" WHERE C_InvoiceLine_ID=?");
			
			int allocationCode = DB.getSQLValue(get_TrxName(), sql.toString(), getC_InvoiceLine_ID());
			
			sql = new StringBuilder();
			sql.append("UPDATE T_ReferenceCode SET isInvoiced='N' WHERE allocationCode=?");
			DB.executeUpdate(sql.toString(), allocationCode, get_TrxName());
			
			sql = new StringBuilder();
			sql.append("UPDATE HBC_CostActivity SET Processed='N' WHERE EXISTS "
					+ "(SELECT 1 FROM M_MatchCostActivity WHERE M_MatchCostActivity.HBC_CostActivity_ID=HBC_CostActivity.HBC_CostActivity_ID "
					+ "AND C_InvoiceLine_ID="+getC_InvoiceLine_ID()+")");
			DB.executeUpdate(sql.toString(), get_TrxName());
			
			
			sql = new StringBuilder();
			sql.append("DELETE FROM M_MatchCostActivity WHERE C_InvoiceLine_ID="+getC_InvoiceLine_ID());
			DB.executeUpdate(sql.toString(), get_TrxName());
			
			sql = new StringBuilder();
			sql.append("UPDATE HBC_ShipDocument_Dock SET isInvoiced='N', C_InvoiceLine_ID=null WHERE C_InvoiceLine_ID="+getC_InvoiceLine_ID());
			DB.executeUpdate(sql.toString(), get_TrxName());
			
			//end @TommyAng
		}else{		
			MInvoice invoice = (MInvoice) getC_Invoice();
			boolean isCreateARCalculation = invoice.get_ValueAsBoolean("IsCreateARCalculation");
			if(isCreateARCalculation){
				
				//TODO: Please recheck the code below
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT HBC_ARCalculationLine_ID FROM "+MMatchARCalculation.Table_Name)
				.append(" WHERE C_InvoiceLine_ID=?");
				
				int AR_CalculationLine_ID = DB.getSQLValue(get_TrxName(), sql.toString(), getC_InvoiceLine_ID());
				
				//@phie add set processed = N if delete invoice line
				sql = new StringBuilder();
				sql.append("UPDATE HBC_ARCalculationLine SET C_Invoice_ID = NULL, C_InvoiceLine_ID = NULL, Processed = 'N' WHERE HBC_ARCalculationLine_ID=?");
				DB.executeUpdate(sql.toString(), AR_CalculationLine_ID, get_TrxName());
				//end phie
				
				sql = new StringBuilder();
				sql.append("DELETE FROM "+MMatchARCalculation.Table_Name+" WHERE C_InvoiceLine_ID=?");
				DB.executeUpdate(sql.toString(), getC_InvoiceLine_ID(), get_TrxName());
				//TODO: end here
				
			}
		}
		return true;
	}
	
}
