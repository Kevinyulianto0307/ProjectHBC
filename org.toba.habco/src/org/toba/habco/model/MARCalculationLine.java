package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MARCalculationLine extends X_HBC_ARCalculationLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1144574933036656908L;
	
	public MARCalculationLine(Properties ctx, int HBC_ARCalculationLine_ID,
			String trxName) {
		super(ctx, HBC_ARCalculationLine_ID, trxName);
	}
	
	public MARCalculationLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord
	 *	@return true if save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if(!isProcessed() && (getC_Invoice_ID()>0 || getC_InvoiceLine_ID()>0))
			throw new AdempiereException("This record is still in used, Document : "+getC_Invoice().getDocumentNo());
		return true;
		//	beforeSave
	}

	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success){
		
		// set is ar calculation to false for annual only
		/*
		if(getARCalculationType().equals(MARCalculation.ARCALCULATIONTYPE_Annual)){
			
			//Delete another line with similar trip id and ar calculation id
			List<MShipActivity> lineID= new Query(getCtx(), MARCalculationLine.Table_Name, "HBC_Trip_ID=? AND HBC_ARCalculation_ID=? AND C_Invoice_ID > 0", get_TrxName())
			.setParameters(getHBC_Trip_ID(),getHBC_ARCalculation_ID())
			.list();
			
			if(lineID.size()>0)
				throw new AdempiereException("There's another line with similar trip has been invoiced!");
			
			String query = "DELETE FROM HBC_ARCalculationLine WHERE HBC_Trip_ID=? AND HBC_ARCalculation_ID=?";
			int no = DB.executeUpdate(query, new Object[]{getHBC_Trip_ID(), getHBC_ARCalculation_ID()}, true, get_TrxName());
			log.info("UPDATE IsARCalculation#"+no);
			
			
			query = "UPDATE HBC_Trip SET IsARCalculation='N' WHERE HBC_Trip_ID=?";
			no = DB.executeUpdate(query, getHBC_Trip_ID(), get_TrxName());
			log.info("UPDATE IsARCalculation#"+no);
		}
		*/
		return true;
	}
	
	/**
	 * get total quantity from another milestone
	 * @return previous total quantity
	 */
	public BigDecimal getPreviousTotalQty(){
		
		BigDecimal totalQty = Env.ZERO;
		
		MARCalculation calc = (MARCalculation) getHBC_ARCalculation();
		
		MTrip trip = new MTrip(getCtx(), calc.getHBC_Trip_ID(), get_TrxName());
		MContract contract = (MContract) trip.getHBC_Contract();
		MProjectMilestone milestone = (MProjectMilestone) getC_ProjectMilestone();
		
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
		
		return totalQty;
	}
	
	/**
	 * @return is invoiced
	 */
	public boolean isInvoiced(){
		
		if(getC_InvoiceLine_ID() > 0)
			return true;
		
		return false;
	}
	
}
