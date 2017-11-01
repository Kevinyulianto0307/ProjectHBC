package org.toba.habco.model;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MTrip extends X_HBC_Trip {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1375936944161676815L;

	public MTrip(Properties ctx, int HBC_Trip_ID, String trxName) {
		super(ctx, HBC_Trip_ID, trxName);
	}

	public MTrip(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * get position lines
	 * @return array of MPosition
	 */
	public MPosition[] getPosition() {
		List<MPosition> list = new Query(getCtx(), MPosition.Table_Name, "HBC_Trip_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.setOrderBy("DateStart, EndDate, HBC_Position_ID")
		.list();
		
		return list.toArray(new MPosition[list.size()]);
		
	}
	
	/**
	 * get position lines
	 * @return array of MPosition
	 */
	public MPosition[] getPositionDesc() {
		List<MPosition> list = new Query(getCtx(), MPosition.Table_Name, "HBC_Trip_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.setOrderBy("DateStart DESC, EndDate DESC, HBC_Position_ID DESC")
		.list();
		
		return list.toArray(new MPosition[list.size()]);
		
	}
	
	/**
	 * get list position
	 * @return list of position
	 */
	public List<MPosition> getListPosition() {
		String where = "HBC_Trip_ID="+getHBC_Trip_ID();
		List<MPosition> list = new Query(getCtx(), MPosition.Table_Name, where, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		return list;
		
	}
	
	public BigDecimal calculateDay() {
		BigDecimal totalDay = Env.ZERO;
		StringBuffer sqlStart = new StringBuffer("SELECT Min(DateStart) FROM HBC_Position WHERE HBC_Trip_ID=?");
		StringBuffer sqlEnd = new StringBuffer("SELECT Max(EndDate) FROM HBC_Position WHERE HBC_Trip_ID=?");
		
		String dateStart = DB.getSQLValueTS(get_TrxName(), sqlStart.toString(), get_ID()).toString();
		String dateEnd = DB.getSQLValueTS(get_TrxName(), sqlEnd.toString(), get_ID()).toString();
		Calendar cal1 = new GregorianCalendar();
	    Calendar cal2 = new GregorianCalendar();

	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	    Date date;
		try {
			date = sdf.parse(dateStart);
		    cal1.setTime(date);
		    date = sdf.parse(dateEnd);
		    cal2.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}				
		
		totalDay = new BigDecimal(daysBetween(cal1.getTime(), cal2.getTime()));
		
		return totalDay;
	}
	
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    
    /**
     * get bbm adjustment lines
     * @return array of MBBMAdjsutment
     */
    public MBBMAdjustment[] getBBMAdjustment(){
    	
    	String where = "HBC_Trip_ID="+getHBC_Trip_ID();
    	List<MBBMAdjustment> list = new Query(getCtx(), MBBMAdjustment.Table_Name, where, get_TrxName())
    	.setClient_ID()
    	.setOnlyActiveRecords(true)
    	.list();
    	
    	MBBMAdjustment[] adj = new MBBMAdjustment[list.size()];
    	list.toArray(adj);
    	
    	return adj;
    }
    
    /**
     * get bbm activity lines
     * @return array of MBBMActivity
     */
    public MBBMActivity[] getBBMActivity(){
    	
    	String where = "IsProcess='N' AND HBC_Trip_ID="+getHBC_Trip_ID();
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
    	
    	String where = " HBC_Trip_ID="+getHBC_Trip_ID();
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
     * get trip allocation lines
     * @return array of MFuelTripAllocation
     */
    public MFuelTripAllocation[] getFuelTripAllocation(){
    	
    	String where = " isProcess='N' AND HBC_Trip_ID="+getHBC_Trip_ID();
    	List<MFuelTripAllocation> list = new Query(getCtx(), MFuelTripAllocation.Table_Name, where, get_TrxName())
    	.setClient_ID()
    	.setOnlyActiveRecords(true)
    	.list();
    	
    	MFuelTripAllocation[] allocations = new MFuelTripAllocation[list.size()];
    	list.toArray(allocations);
    	
    	return allocations;
    	
    }
    
    /**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//@TommyAng requested by irene (BARGE ONLY, VALUE="BOL")
		if(newRecord){
			if (!get_Value("HBC_ShipReqType").equals("BOL")){
				MTugboat tugboat = new MTugboat(getCtx(), getHBC_Tugboat_ID(), get_TrxName());
				setFuelBalance(tugboat.getFuelBalance());
			}
			else {
				setHBC_Tugboat_ID(0);
			}
			if(matchContract())
				throw new AdempiereException("Already have Trip with same Contract");
			
		}
		//end @TommyAng
		return true;
	}
	
	public Timestamp LastPositionEndDate(){
		int positionid = new Query(getCtx(),MPosition.Table_Name,"HBC_Trip_ID="+getHBC_Trip_ID(),get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy("DateStart DESC, EndDate DESC, HBC_Position_ID DESC")
				.firstId();
		MPosition position = new MPosition(getCtx(),positionid,get_TrxName());
		return position.getEndDate();
	}
    
	
	//@TommyAng
	public BigDecimal LastPositionCargo(){
		int positionid = new Query(getCtx(),MPosition.Table_Name,"HBC_Trip_ID="+getHBC_Trip_ID(),get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy("DateStart DESC, EndDate DESC, HBC_Position_ID DESC")
				.firstId();
		MPosition position = new MPosition(getCtx(),positionid,get_TrxName());
		return position.getTotalCargoQty();
	}
	//end @TommyAng
	/**
	 * check match contract just for spal and time charter
	 * @return true if match
	 */
	protected boolean matchContract(){
		
		if(getHBC_Contract().getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SPAL) 
			|| getHBC_Contract().getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_TimeCharter)){
				
			String where = "HBC_Contract_ID="+getHBC_Contract_ID();
			boolean match = new Query(getCtx(), MTrip.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.match();
			
			if(match)
				return true;
		}
		
		return false;
	}
	public MFuelUsage[] getFuelUsageID() {
		List<MFuelUsage> list = new Query(getCtx(), MFuelUsage.Table_Name, "HBC_Trip_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.setOrderBy("FuelUsage")
		.list();
		
		return list.toArray(new MFuelUsage[list.size()]);
		
	}
	
}
