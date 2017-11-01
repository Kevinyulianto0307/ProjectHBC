package org.toba.habco.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.X_C_Project;
import org.compiere.util.Env;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.X_HBC_Tugboat;

public class HBC_CalloutTrip extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (mField.getColumnName().equals("HBC_Tugboat_ID")){
			return Tugboat(ctx, WindowNo, mTab, mField, value) ;
		}else if (mField.getColumnName().equals("HBC_Barge_ID")){
			//return Barge(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("C_Project_ID")){
			return project(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}
	
	private String project(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		int projectid=(Integer)value;
		
		X_C_Project project = new X_C_Project(Env.getCtx(),projectid,null);
		mTab.setValue(MTrip.COLUMNNAME_HBC_Contract_ID, project.get_ValueAsInt("HBC_Contract_ID"));
		
		mTab.setValue(MTrip.COLUMNNAME_From_PortPosition_ID, project.get_ValueAsInt("From_PortPosition_ID"));
		mTab.setValue(MTrip.COLUMNNAME_To_PortPosition_ID, project.get_ValueAsInt("To_PortPosition_ID"));
		/*
		mTab.setValue(MTrip.COLUMNNAME_PortPosition_From2_ID, project.get_ValueAsInt("PortPosition_From2_ID"));
		mTab.setValue(MTrip.COLUMNNAME_PortPosition_To2_ID, project.get_ValueAsInt("PortPosition_To2_ID"));
		
		mTab.setValue("PortPosition_From3_ID", project.get_Value("PortPosition_From3_ID"));
		mTab.setValue("PortPosition_To3_ID", project.get_Value("PortPosition_To3_ID"));
		*/
		mTab.setValue(MTrip.COLUMNNAME_HBC_Tugboat_ID, project.get_ValueAsInt("HBC_Tugboat_ID"));
		mTab.setValue(MTrip.COLUMNNAME_HBC_ShipReqType, project.get_Value("HBC_ShipReqType"));
		//mTab.setValue(MHBCTrip.COLUMNNAME_HBC_PortPosition_ID, project.getHBC_PortPosition_ID());
		
		return"";
	}

	
//	private String Barge(Properties ctx, int windowNo, GridTab mTab,
//			GridField mField, Object value) {
//		
//		String msg="";
//		if (value==null){
//			return msg;
//		}
//		
//		int projectid= (Integer) mTab.getValue("C_Project_ID");
//		X_C_Project project = new X_C_Project(ctx, projectid, null);
//		
//		mTab.setValue("HBC_Tugboat_ID", project.getHBC_Tugboat_ID());
//		
//		return "";
//	}//end of HABCO-1584
	
	
	private String Tugboat(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		
		String msg="";
		if (value==null){
			return msg;
		}
		
		if (mTab.getValue("C_Project_ID")==null)
			return msg;
		
		int projectid= (Integer) mTab.getValue("C_Project_ID");
		X_C_Project project = new X_C_Project(ctx, projectid, null);
		
		X_HBC_Tugboat tugboat = new X_HBC_Tugboat(ctx, project.get_ValueAsInt("HBC_Tugboat_ID"), null);
		
		//mTab.setValue("HBC_Barge_ID", project.getHBC_Barge_ID());
		mTab.setValue("FuelBalance", tugboat.getFuelBalance());
		
		return "";
			
	}

	
	
}
