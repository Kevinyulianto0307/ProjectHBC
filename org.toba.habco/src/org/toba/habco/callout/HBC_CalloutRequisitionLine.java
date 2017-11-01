package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;

public class HBC_CalloutRequisitionLine extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
			
			System.out.println(mField);
			if(mField.getColumnName().equals(MRequisitionLine.COLUMNNAME_PriceActual)|| mField.getColumnName().equals(MRequisitionLine.COLUMNNAME_Qty))
				return LineAmount(ctx,WindowNo,mTab,mField,value);
			else if(mField.getColumnName().equals(MRequisition.COLUMNNAME_M_Warehouse_ID))
				return Warehouse(ctx,WindowNo,mTab,mField,value);
		return "";
	}

	private String LineAmount(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		String msg="";
		
		if(value==null){
			return msg;
		}
		
		BigDecimal qty = (BigDecimal)mTab.getValue("Qty");
		BigDecimal PriceActual = (BigDecimal)mTab.getValue("PriceActual");
		
		mTab.setValue("LineNetAmt", PriceActual.multiply(qty));
		
	  return msg;
	}
	
	private String Warehouse(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value){
		String msg="";
		
		if(value==null){
			return msg;
		}
		
		MWarehouse wh = new MWarehouse(ctx, (int) value, null);
		//@TommyAng
		Integer barge= new Integer(0);
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("SELECT HBC_Barge_ID "
				+ " FROM  Hbc_tugboat "
				+ " WHERE hbc_tugboat_ID = ?");
		
		mTab.setValue("HBC_Tugboat_ID", wh.get_Value("HBC_Tugboat_ID"));
		barge = DB.getSQLValue(null, sql2.toString(), new Object[]{wh.get_Value("HBC_Tugboat_ID")});
		mTab.setValue("HBC_Barge_ID", barge);
		//end @TommyAng
		//mTab.setValue("HBC_Barge_ID", wh.get_Value("HBC_Barge_ID"));
		
		return msg;
	}
	
}
