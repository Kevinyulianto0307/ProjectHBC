package org.toba.habco.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.X_HBC_PortPosition;

public class HBC_UpdateAgent extends SvrProcess{

	int p_hbc_ShipActivity_ID = 0;
	protected void prepare() {
		p_hbc_ShipActivity_ID = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {
		
		StringBuilder msg = new StringBuilder();
		
		if(p_hbc_ShipActivity_ID == 0)
			throw new AdempiereException("There's no data selected..");
		
		MShipActivity sa = new MShipActivity(getCtx(), p_hbc_ShipActivity_ID, get_TrxName());
		if(sa.getHBC_Position().getHBC_Trip().getC_Project_ID() == 0)
			throw new AdempiereException("Trip has no project..");
		
		if(sa.getLoadingAgent_BPartner_ID() == 0)
			throw new AdempiereException("Activity has no agent..");
		
		MProject project = new MProject(getCtx(), sa.getHBC_Position().getHBC_Trip().getC_Project_ID(), get_TrxName());
		
		String whereClause = "HBC_Barge_ID = ? AND StartDate >= ? AND "
				+ "HBC_ShipActivity_ID IN (SELECT HBC_ShipActivity_ID FROM HBC_ShipActivity WHERE "
				+ "HBC_Position_ID IN (SELECT HBC_Position_ID FROM HBC_Position WHERE HBC_Trip_ID = ?))";
		int[] shipActivity_IDs = new Query(sa.getCtx(), MShipActivity.Table_Name, whereClause, sa.get_TrxName())
									.setParameters(new Object[]{sa.getHBC_Position().getHBC_Barge_ID(),
											sa.getStartDate(), sa.getHBC_Position().getHBC_Trip_ID()})
									.setOrderBy("StartDate ASC, isLastActivity ASC, isSecondActivity ASC, "
											+ "FinishDate ASC, HBC_Shipactivity_ID ASC")
									.getIDs();
		
		if(shipActivity_IDs.length == 0)
			return "";
		
		for(int id : shipActivity_IDs)
		{
			MShipActivity potentialUpdatedSA = new MShipActivity(sa.getCtx(), id, sa.get_TrxName());
			//if(potentialUpdatedSA.getHBC_Position().getHBC_PortPosition_ID() == 0)
				//throw new AdempiereException("Try to update agent, please fill port position..");
			MPosition pos = new MPosition(getCtx(), potentialUpdatedSA.getHBC_Position_ID(), get_TrxName());
			int port_position_id = pos.get_ValueAsInt("HBC_PortPosition_ID");
			
			if((port_position_id == project.get_ValueAsInt("From_PortPosition_ID")
				|| port_position_id == project.get_ValueAsInt("To_PortPosition_ID")) && pos.getHBC_Position_ID() != sa.getHBC_Position_ID())
				break;
			
			//get agent before
			/*
			String agentNameBefore = "";
			if(potentialUpdatedSA.getLoadingAgent_BPartner_ID() == 0)
				agentNameBefore = "-";
			else{
				MBPartner agentBefore = new MBPartner(sa.getCtx(), 
						potentialUpdatedSA.getLoadingAgent_BPartner_ID(), sa.get_TrxName());
				agentNameBefore = agentBefore.getName();
			}
			
			//get agent after
			String agentNameAfter = "";
			MBPartner agentAfter = new MBPartner(sa.getCtx(), sa.getLoadingAgent_BPartner_ID(), sa.get_TrxName());
			agentNameAfter = agentAfter.getName();
			*/
			
			//Update Agent
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ShipActivity SET LoadingAgent_BPartner_ID = ? WHERE HBC_ShipActivity_ID = ?");
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{sa.getLoadingAgent_BPartner_ID(),
				potentialUpdatedSA.getHBC_ShipActivity_ID()}, sa.get_TrxName());
			
			//Return Message
			/*
			if(no == 1){
				X_HBC_PortPosition portPosition = new X_HBC_PortPosition(sa.getCtx(), 
						potentialUpdatedSA.getHBC_Position().getHBC_PortPosition_ID(), sa.get_TrxName());
				msg.append("[Position "+ portPosition.getName()+
						" - Activity Line No "+potentialUpdatedSA.getLine()+" : "+agentNameBefore+"->"+agentNameAfter+"]");
			}
			*/
		}
		
		return "";
	}
}
