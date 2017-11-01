package org.toba.habco.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.toba.habco.model.X_HBC_AuxiliaryEngine;
import org.toba.habco.model.X_HBC_GearBox;
import org.toba.habco.model.X_HBC_MainEngine;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_CalloutTugBoat extends CalloutEngine implements IColumnCallout {
	/**
	 * created by ayonk
	 * 
	 */
	
	//HABCO-1428 Callout MainEngine data and AuxiliaryEngine data
	private String AuxEngine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		String msg="";
		if (value==null){
			return msg;
		}
		int AuxEngineID=(Integer)value;
		
		X_HBC_AuxiliaryEngine AuxEngine = new X_HBC_AuxiliaryEngine(Env.getCtx(), AuxEngineID, null);
		
		//mTab.setValue("merkauxiliary", AuxEngine.getHBC
		mTab.setValue("QtyOfMachine", 1);
		mTab.setValue("TotalHorsePowerAux", AuxEngine.getHorsePower());
		mTab.setValue("FuelConsumptionPerHour", AuxEngine.getFuelConsumptionPerHour());
		return msg;
	}

	private String MainEngine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		String msg="";
		if (value==null){
			return msg;
		}
		int MainEngineID=(Integer)value;
		
		X_HBC_MainEngine mainEngine=new X_HBC_MainEngine(Env.getCtx(), MainEngineID, null);
		mTab.setValue("QtyofMachineMain", 1);
		mTab.setValue("TotalHorsePower", mainEngine.getHPMainEngine());
		mTab.setValue("FuelConsumptionMain", mainEngine.getFuelConsumptionMain());
		return msg;
	}
	// end of HABCO-1428

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_HBC_MainEngine_ID)){
			return MainEngine(ctx, WindowNo, mTab, mField, value);
		}else if(mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_HBC_AuxiliaryEngine_ID)){
			return AuxEngine(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_FuelCapacityperDay)|| mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_ReserveFuelCapacity)){
			return TotalFuelCapacity(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_HBC_GearBox_ID)){
			return gearbox(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_QtyOfMachine)){
			return qtyofmachineaux(ctx,WindowNo,mTab,mField,value);
		}else if (mField.getColumnName().equals(X_HBC_Tugboat.COLUMNNAME_QtyofMachineMain)){
			return qtyofmachinemain(ctx,WindowNo,mTab,mField,value);
		}
		return "";
	}
	
	private String qtyofmachinemain(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		int qty = (Integer)value;
		BigDecimal totalhp = (BigDecimal)mTab.getValue("TotalHorsePower");
		mTab.setValue("TotalHorsePower", totalhp.multiply(new BigDecimal(qty)));
		
		return "";
	}
	
	private String qtyofmachineaux(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		int qty = (Integer)value;
		BigDecimal totalhp = (BigDecimal)mTab.getValue("TotalHorsePowerAux");
		mTab.setValue("TotalHorsePowerAux", totalhp.multiply(new BigDecimal(qty)));
		
		return "";
	}

	private String gearbox(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		int gearboxid = (Integer)value;
		X_HBC_GearBox gearbox= new X_HBC_GearBox(ctx, gearboxid, null);
		mTab.setValue("Ratio", gearbox.getRatioGearBox());
		
		return "";
	}

	//HABCO-1648 Total Fuel Tank Capacity
	private String TotalFuelCapacity(Properties ctx, int windowNo,
			GridTab mTab, GridField mField, Object value) {
			
		BigDecimal fuelcapacityperday = (BigDecimal) mTab.getValue("FuelCapacityperDay");
		BigDecimal Reservercapacityperday= (BigDecimal)mTab.getValue("ReserveFuelCapacity");
		
		mTab.setValue("TotalFuelTankCapacity", fuelcapacityperday.add(Reservercapacityperday));

		return "";
	}// END OF HABCO-1648

}
