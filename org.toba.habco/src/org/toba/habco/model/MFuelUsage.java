package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MActivity;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MFuelUsage extends X_FuelUsage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MFuelUsage(Properties ctx, int FuelUsage_ID, String trxName) {
		super(ctx, FuelUsage_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MFuelUsage(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	//@TommyAng
	/*protected boolean beforeSave(){
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT SUM(FuelShipBalance) FROM FuelUsage WHERE HBC_Trip_ID="+getHBC_Trip_ID());
		BigDecimal BBMUsage = DB.getSQLValueBD(get_TrxName(), sql.toString());
		
		MTrip trip = new MTrip(getCtx(),getHBC_Trip_ID(),get_TrxName());
		
		trip.setBBMUsage(BBMUsage);
		trip.saveEx();
		
		
		return true;
	} // beforeSave
*/
	
	protected boolean beforeSave(){
		
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT SUM(FuelShipBalance) FROM FuelUsage WHERE TCS_BBMPlan_ID="+getTCS_BBMPlan_ID());
			BigDecimal BBMUsage = DB.getSQLValueBD(get_TrxName(), sql.toString());
			
			
			MBBMPlan bbm = new MBBMPlan(getCtx(), getTCS_BBMPlan_ID(), get_TrxName());
			
			bbm.setBBMUsage(BBMUsage);
			bbm.saveEx();
				
			
			return true;
	} // beforeSave
	
	/*public boolean isFirstRecord(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FuelUsage_ID FROM FuelUsage WHERE HBC_Trip_ID="+getHBC_Trip_ID()+
					" ORDER BY fuelusage_ID ASC");
		int fuelusageid = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(fuelusageid==getFuelUsage_ID() || fuelusageid<0){
				return true;
			}
		return false;
	}*/
	
	public boolean isFirstRecord(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FuelUsage_ID FROM FuelUsage WHERE TCS_BBMPlan_ID="+getTCS_BBMPlan_ID()+
					" ORDER BY fuelusage_ID ASC");
		int fuelusageid = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(fuelusageid==getFuelUsage_ID() || fuelusageid<0){
				return true;
			}
		return false;
	}
	
	@Override
	protected boolean beforeDelete ()
	{
		StringBuilder sql = new StringBuilder();
		Integer line= new Integer(0);
		sql.append("SELECT FuelUsage_ID "
				+ " FROM  FuelUsage "
				+ " WHERE TCS_BBMPlan_ID = ?"
				+ " order by FuelUsage_ID desc");
		
		line=DB.getSQLValue(null, sql.toString(), new Object[]{getTCS_BBMPlan_ID()});
		
		if(line!=getFuelUsage_ID()){
			throw new AdempiereException("must delete from last record");
		}
		
		return true;
	}	//	beforeDelete
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		//commented by TommyAng(Requested by Bella)
		/*
		MTrip tripp= new MTrip(getCtx(), getHBC_Trip_ID(), get_TrxName());
		
		int bunkeringActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Bunkering'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		int fuelreceiptActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Fuel Receipt'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		int fueltransferActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Fuel Transfer'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
				
		//BigDecimal fuelAdj = Env.ZERO;
		
			for (MBBMActivity shipActivity : tripp.getBBMActivity()) {
				
				int C_Activity_ID=shipActivity.get_ValueAsInt(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID);
				
				if(C_Activity_ID==bunkeringActivityID){
					shipActivity.setIsProcess(true);
					shipActivity.saveEx();
				}
				else if(C_Activity_ID==fuelreceiptActivityID){
					shipActivity.setIsProcess(true);
					shipActivity.saveEx();
				}
				else if(C_Activity_ID==fueltransferActivityID){
					shipActivity.setIsProcess(true);
					shipActivity.saveEx();
				}else{
					continue;
				}
			}	
		*/
		return success;
	}
	@Override
	protected boolean afterDelete (boolean success){
		
		//MTrip tripp= new MTrip(getCtx(), getHBC_Trip_ID(), get_TrxName());
		MBBMPlan bbm = new MBBMPlan(getCtx(), getTCS_BBMPlan_ID(), get_TrxName());
		
		int bunkeringActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Bunkering'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		int fuelreceiptActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Fuel Receipt'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		int fueltransferActivityID= new Query (Env.getCtx(),MActivity.Table_Name,"Name='Fuel Transfer'",null)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
				
		//BigDecimal fuelAdj = Env.ZERO;
		
			//for (MBBMActivity shipActivity : tripp.getBBMActivitys()) {
				
			for (MBBMActivity shipActivity : bbm.getBBMActivitys()) {
				int C_Activity_ID=shipActivity.get_ValueAsInt(X_HBC_BBMActivity.COLUMNNAME_C_Activity_ID);
				
				if(C_Activity_ID==bunkeringActivityID){
					shipActivity.setIsProcess(false);
					shipActivity.saveEx();
				}
				else if(C_Activity_ID==fuelreceiptActivityID){
					shipActivity.setIsProcess(false);
					shipActivity.saveEx();
				}
				else if(C_Activity_ID==fueltransferActivityID){
					shipActivity.setIsProcess(false);
					shipActivity.saveEx();
				}else{
					continue;
				}
			}	
		
		
		return true;
	}
	

	
}
