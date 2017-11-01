package org.toba.habco.callout;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.toba.habco.model.X_HBC_Trip;
import org.toba.habco.model.X_HBC_Tugboat;


/*
 * Created By Yonk
 */


public class HBC_CalloutInOut extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}
		/* temporary comment @Stephan
		else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}*/
		else if (mField.getColumnName().equals("HBC_Trip_ID")){
			return Trip(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals("M_Warehouse_ID")){
			return warehouse(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals("MovementDate")){
			return movementDate(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return "";
	}
	
	private String Trip(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int tripid= (Integer)value;
		X_HBC_Trip trip = new X_HBC_Trip(ctx,tripid,null);
		mTab.setValue("HBC_Tugboat_ID", trip.getHBC_Tugboat_ID());
		mTab.setValue("HBC_Barge_ID", trip.getHBC_Barge_ID());
		
		return msg;
	}
	private String warehouse(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int warehouseid= (Integer)value;
		MWarehouse warehouse = new MWarehouse(ctx,warehouseid,null);
		mTab.setValue("HBC_Tugboat_ID", warehouse.get_Value("HBC_Tugboat_ID"));
		
		//@TommyAng
		Integer barge= new Integer(0);
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("SELECT HBC_Barge_ID "
				+ " FROM  Hbc_tugboat "
				+ " WHERE hbc_tugboat_ID = ?");
		
		barge = DB.getSQLValue(null, sql2.toString(), new Object[]{warehouse.get_Value("HBC_Tugboat_ID")});
		mTab.setValue("HBC_Barge_ID", barge);
		//end @TommyAng
		
		Integer locator= new Integer(0);
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ml.m_locator_ID "
				+ " FROM  M_Locator ml "
				+ " join M_warehouse mw on ml.m_warehouse_ID = mw.m_warehouse_ID"
				+ " WHERE ml.M_warehouse_ID = ?");
		
		locator = DB.getSQLValue(warehouse.get_TrxName(), sql.toString(), new Object[]{warehouse.getM_Warehouse_ID()});
		
		mTab.setValue("M_Locator_ID", locator);
		
		return msg;
	}
	
	/* temporary comment @Stephan
	 private String Barge(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int bargeid= (Integer) value;
		X_HBC_Barge barge = new X_HBC_Barge (Env.getCtx(),bargeid,null);
		
		mTab.setValue("HBC_Tugboat_ID", barge.getHBC_Tugboat_ID());
		
		return "";
	}//end of HABCO-1584
	 */	
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int tugboatid= (Integer) value;
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugboatid, null);
		
		mTab.setValue("HBC_Barge_ID", tugboat.getHBC_Barge_ID());
		
		return "";
		
		
	}

	private String movementDate(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(value == null)
			return "";
		
		Timestamp movementDate = (Timestamp) value;
		mTab.setValue("DateReceived", movementDate);
		
		return "";
	}
	
}
