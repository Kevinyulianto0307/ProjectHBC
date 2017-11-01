package org.toba.habco.model.validator;

import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MProject;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.jfree.util.Log;
import org.osgi.service.event.Event;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MShipActivity;

public class HBC_ShipActivityValidator {

	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MShipActivity sa = new MShipActivity(po.getCtx(), po.get_ID(), po.get_TrxName());
		if (event.getTopic().equals(IEventTopics.PO_AFTER_NEW)){
			msg = setThisAgent(sa);
		}
		return msg;
	}

	private static String setThisAgent(MShipActivity sa) {
		//if(sa.getHBC_Position().getHBC_PortPosition_ID() == 0)
			//return "";
		
		MProject project = new MProject(sa.getCtx(), sa.getHBC_Position().getHBC_Trip().getC_Project_ID(), sa.get_TrxName());
		
		if(sa.getHBC_Position().getHBC_PortPosition_ID() == project.get_ValueAsInt("From_PortPosition_ID"))
		{
			if(project.get_ValueAsInt("LoadingAgent_BPartner_ID") == 0)
				return"";
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ShipActivity SET LoadingAgent_BPartner_ID = ? WHERE HBC_ShipActivity_ID = ?");
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{project.get_ValueAsInt("LoadingAgent_BPartner_ID"),
				sa.getHBC_ShipActivity_ID()}, sa.get_TrxName());
			
			Log.info("UPDATE LOADING AGENT"+no);
			return"";
		}
		else if(sa.getHBC_Position().getHBC_PortPosition_ID() == project.get_ValueAsInt("To_PortPosition_ID"))
		{
			if(project.get_ValueAsInt("UnLoadingAgent_BPartner_ID") == 0)
				return"";
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ShipActivity SET LoadingAgent_BPartner_ID = ? WHERE HBC_ShipActivity_ID = ?");
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{project.get_ValueAsInt("UnLoadingAgent_BPartner_ID"),
				sa.getHBC_ShipActivity_ID()}, sa.get_TrxName());
			
			Log.info("UPDATE LOADING AGENT"+no);
			return"";
		}
			
		String whereClause = "HBC_Barge_ID = ? AND HBC_ShipActivity_ID != ? AND StartDate < ?";
		int sa_id = new Query(sa.getCtx(), MShipActivity.Table_Name, whereClause, sa.get_TrxName())
								.setParameters(new Object[]{sa.getHBC_Position().getHBC_Barge_ID(), 
										sa.getHBC_ShipActivity_ID(), sa.getStartDate()})
								.setOrderBy("StartDate DESC, isLastActivity Desc, isSecondActivity DESC, "
										+ "FinishDate DESC, HBC_Shipactivity_ID DESC")						
								.firstId();
		
		MShipActivity lastSA = new MShipActivity(sa.getCtx(), sa_id, sa.get_TrxName());
		if(lastSA.getLoadingAgent_BPartner_ID() == 0)
			return"";
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE HBC_ShipActivity SET LoadingAgent_BPartner_ID = ? WHERE HBC_ShipActivity_ID = ?");
		int no = DB.executeUpdateEx(sb.toString(), new Object[]{lastSA.getLoadingAgent_BPartner_ID(),
			sa.getHBC_ShipActivity_ID()}, sa.get_TrxName());
		
		Log.info("UPDATE LOADING AGENT"+no);
		return "";
	}
}
