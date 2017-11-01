package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MBBMPlan extends X_TCS_BBMPlan{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8890438397490031487L;

	public MBBMPlan(Properties ctx, int TCS_BBMPlan_ID, String trxName) {
		super(ctx, TCS_BBMPlan_ID, trxName);
	}
	
	public MBBMPlan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	protected boolean beforeSave (boolean newRecord)
	{
		int currentTripID = get_ValueAsInt("CurrentTrip_ID");
		if(currentTripID>0){
			MTugboat tugboat = (MTugboat) getHBC_Tugboat();
			tugboat.set_CustomColumn("LastTrip_ID", currentTripID);
			tugboat.saveEx();
		}
		return true;
	}
	public MShipActivity[] getShipActivity() {
		List<MShipActivity> list = new Query(getCtx(), X_HBC_ShipActivity.Table_Name, "TCS_BBMPlan_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate ASC")
		.list();
		
		return list.toArray(new MShipActivity[list.size()]);
		
	}
	
	public MFuelUsage[] getFuelUsageID() {
		List<MFuelUsage> list = new Query(getCtx(), MFuelUsage.Table_Name, "TCS_BBMPlan_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.setOrderBy("FuelUsage")
		.list();
		
		return list.toArray(new MFuelUsage[list.size()]);
		
	}
	
	/**
     * get bbm activity lines
     * @return array of MBBMActivity
     */
    public MBBMActivity[] getBBMActivity(){
    	
    	String where = "IsProcess='N' AND TCS_BBMPlan_ID="+get_ID();
    	int[] ids = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, where, get_TrxName())
    	.setClient_ID()
    	.setOnlyActiveRecords(true)
    	.getIDs();
    	
    	List<MBBMActivity> list = new ArrayList<MBBMActivity>();
    	for (int id : ids) {
			MBBMActivity activity = new MBBMActivity(getCtx(), id, get_TrxName());
			list.add(activity);
		}
    	
    	MBBMActivity[] activity = new MBBMActivity[list.size()];
    	list.toArray(activity);
    	
    	return activity;
    }
    
    public MBBMActivity[] getBBMActivitys(){
    	
    	String where = " TCS_BBMPlan_ID="+getTCS_BBMPlan_ID();
    	int[] ids = new Query(getCtx(), X_HBC_BBMActivity.Table_Name, where, get_TrxName())
    	.setClient_ID()
    	.setOnlyActiveRecords(true)
    	.getIDs();
    	
    	List<MBBMActivity> list = new ArrayList<MBBMActivity>();
    	for (int id : ids) {
			MBBMActivity activity = new MBBMActivity(getCtx(), id, get_TrxName());
			list.add(activity);
		}
    	
    	MBBMActivity[] activity = new MBBMActivity[list.size()];
    	list.toArray(activity);
    	
    	return activity;
    }
    
    /**
     * get bbm allocation lines
     * @return array of MFuelTripAllocation
     */
    public MFuelTripAllocation[] getFuelTripAllocation(){
    	
    	String where = " isProcess='N' AND TCS_BBMPlan_ID="+getTCS_BBMPlan_ID();
    	List<MFuelTripAllocation> list = new Query(getCtx(), MFuelTripAllocation.Table_Name, where, get_TrxName())
    	.setClient_ID()
    	.setOnlyActiveRecords(true)
    	.list();
    	
    	MFuelTripAllocation[] allocations = new MFuelTripAllocation[list.size()];
    	list.toArray(allocations);
    	
    	return allocations;
    	
    }
}
