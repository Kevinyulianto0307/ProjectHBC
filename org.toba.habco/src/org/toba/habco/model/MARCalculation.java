package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MARCalculation extends X_HBC_ARCalculation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -17926621082409359L;
	
	public MARCalculation(Properties ctx, int HBC_ARCalculation_ID,
			String trxName) {
		super(ctx, HBC_ARCalculation_ID, trxName);
	}
	
	public MARCalculation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MARCalculationLine[] getCalculationLine(){
		
		String where = "HBC_ARCalculation_ID="+getHBC_ARCalculation_ID();
		List<MARCalculationLine> list = new Query(getCtx(), MARCalculationLine.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.list();
		
		ArrayList<MARCalculationLine> list2 = new ArrayList<MARCalculationLine>();
		
		for(int i=0;i<list.size();i++){
			list2.add(list.get(i));
		}
		
		return list2.toArray(new MARCalculationLine[list2.size()]);
	}
	
}
