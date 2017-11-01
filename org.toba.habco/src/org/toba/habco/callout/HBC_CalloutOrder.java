package org.toba.habco.callout;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.X_HBC_Barge;
import org.toba.habco.model.X_HBC_Trip;
import org.toba.habco.model.X_HBC_Tugboat;


/*
 * Created By Yonk
 */

public class HBC_CalloutOrder extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}
		
		else if (mField.getColumnName().equals("HBC_Barge_ID")){
			return Barge(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (mField.getColumnName().equals("HBC_Trip_ID")){
			return Trip(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (mField.getColumnName().equals("M_Warehouse_ID")){
			return warehouse(ctx,WindowNo,mTab,mField,value);
		}
		
		else if (mField.getColumnName().equals("IsNoPrice")){
			return noPrice(ctx, WindowNo, mTab, mField, value);
		}
		
		else if (mField.getColumnName().equals("DateOrdered")){
			return dateOrdered(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		else if (mField.getColumnName().equals("PersonInCharge_ID")){
			return personInCharge(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		/*@KevinY Habco - 2704 Called out TransportPrice & C_Tax_ID*/
		else if (mField.getColumnName().equals("C_BPartner_ID")){
			return BPartner(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		else if(mField.getColumnName().equals("C_BPartner_Location_ID")){
			return BPartner(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		/*@KevinY end*/
		
		return "";
	}
	
	private String warehouse(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		
		String msg="";
		if (value==null)
			return msg;
		
		int warehouseid=(Integer)value;
		MWarehouse warehouse= new MWarehouse(ctx,warehouseid,null);
		
		//modified by TommyAng
		//if((Integer)warehouse.get_Value("HBC_Tugboat_ID")!=0){
		
		//@KevinY HBC - 2802
		if(warehouse.get_Value("HBC_TugBoat_ID") != null || warehouse.get_ValueAsInt("HBC_TugBoat_ID") > 0){
				mTab.setValue("HBC_Tugboat_ID", (Integer)warehouse.get_Value("HBC_Tugboat_ID"));	
				String phone= new String();
				
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT phone "
						+ " FROM  Hbc_tugboat "
						+ " WHERE hbc_tugboat_ID = ?");
				
				phone = DB.getSQLValueStringEx(null, sql.toString(), new Object[]{warehouse.get_Value("HBC_Tugboat_ID")});
				mTab.setValue("phone", phone);
		}
		/*else if (warehouse.get_Value("HBC_Tugboat_ID") != null){
			Integer barge= new Integer(0);
			
			StringBuilder sql2 = new StringBuilder();
			sql2.append("SELECT HBC_Barge_ID "
					+ " FROM  Hbc_tugboat "
					+ " WHERE hbc_tugboat_ID = ?");
			
			barge = DB.getSQLValue(null, sql2.toString(), new Object[]{warehouse.get_Value("HBC_Tugboat_ID")});
			mTab.setValue("HBC_Barge_ID", barge);
			
		}
		//TommyAng
		else
			mTab.setValue("HBC_Barge_ID", null);
		*/
		else if(warehouse.get_ValueAsInt("HBC_Barge_ID") > 0 || warehouse.get_Value("HBC_Barge_ID")!= null){
			mTab.setValue("HBC_Barge_ID", (Integer)warehouse.get_Value("HBC_Barge_ID"));
		}
		
		//@KevinY end
		
		
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
	
	private String Barge(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int bargeid= (Integer) value;
		X_HBC_Barge barge = new X_HBC_Barge (Env.getCtx(),bargeid,null);
		//temporary commented by TommyAng
		//mTab.setValue("HBC_Tugboat_ID", barge.getHBC_Tugboat_ID());
		
		return "";
	}//end of HABCO-1584
	
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int tugboatid= (Integer) value;
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, tugboatid, null);
		//temporary commented by TommyAng
		//mTab.setValue("HBC_Barge_ID", tugboat.getHBC_Barge_ID());
		
		return "";
		
		
	}

	private String noPrice(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value){
		
		if(value == null)
			return "";
		
		boolean isNoPrice = (boolean) value;
		if(isNoPrice)
			mTab.setValue("M_PriceList_ID", null);
		
		return "";
	}
	
	private String dateOrdered(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null)
			return "";
		
		Timestamp dateOrdered = (Timestamp) value;
		mTab.setValue("DatePromised", dateOrdered);
		
		return "";
	}
	//@TommyAng callout fuel Phone2
	private String personInCharge(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null){
			mTab.setValue("Phone2", null);
			return "";
		}
		
		int HC_Employee_ID = (int) value;
		MEmployee employee = new MEmployee(ctx, HC_Employee_ID, null);
		
		mTab.setValue("Phone2", employee.getPhone());
		
		return "";
	}
	//end @TommyAng callout fuel Phone2
	
	/*@KevinY Habco - 2704 Called out TransportPrice & C_Tax_ID*/
	private String BPartner(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue){
		
		if(value == null){
			return "";
		}
		int BPartnerLocation =(int)mTab.getValue("C_BPartner_Location_ID");
		if(BPartnerLocation > 0){
			MBPartnerLocation location = new MBPartnerLocation(ctx, BPartnerLocation, null);
			mTab.setValue("TransportPrice", location.get_Value("TransportPrice"));
			//mTab.setValue("C_Tax_ID", location.get_ValueAsInt("C_Tax_ID"));
		}
		return "";
	}
	/*@KevinY end*/
}
