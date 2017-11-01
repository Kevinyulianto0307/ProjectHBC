package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.X_C_Project;
import org.compiere.util.Env;

/*
 * created by yonk
 * 
 */
public class HBC_CalloutShipPosition extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals("C_Project_ID")){
			return Project(ctx, WindowNo, mTab, mField, value);
		}
		return null;
	}
	//HABCO-1406 Callout From Project to Ship Position
	private String Project(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		String msg="";
		if (value==null){
			return msg;
		}
		int ProjectID= (Integer)value;
		
		X_C_Project project = new X_C_Project(Env.getCtx(), ProjectID, null);
		mTab.setValue("HBC_Contract_ID", project.get_ValueAsInt("HBC_Contract_ID"));
		mTab.setValue("AmountOfCargo", project.get_ValueAsInt("AmountOfCargo"));
		mTab.setValue("CargoName", project.getName());
		mTab.setValue("From_PortPosition_ID", project.get_ValueAsInt("From_PortPosition_ID"));
		mTab.setValue("To_PortPosition_ID",project.get_ValueAsInt("To_PortPosition_ID"));
		return "";
	}
	//end of HABCO-1406

}
