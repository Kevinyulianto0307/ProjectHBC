package org.toba.habco.timesheet.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MBarge;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MTugboat;
import org.toba.habco.model.X_HBC_ShipRelationLine;

public class HBC_ProcessChangeShipTrip extends SvrProcess {


	/*
	 * created by yonk
	 * Process Pergantian kapal trip
	 */
	
	int p_HBC_Trip_ID= 0;
	int p_HBC_Tugboat_ID=0;
	

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("HBC_Tugboat_ID")){
				p_HBC_Tugboat_ID = para[i].getParameterAsInt();
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_HBC_Trip_ID=getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_Trip_ID==0){
			return "Trip not selected";
		}
		
		MTrip trip=  new MTrip (getCtx(),p_HBC_Trip_ID,get_TrxName());	
	
		MTugboat tugboat1 = new MTugboat(getCtx(), p_HBC_Tugboat_ID, get_TrxName());
		MTugboat tugboat2 = new MTugboat(getCtx(), trip.getHBC_Tugboat_ID(), get_TrxName());
		MBarge barge1 = new MBarge(getCtx(), trip.getHBC_Barge_ID(), get_TrxName());
		MBarge barge2 = new MBarge(getCtx(), tugboat1.getHBC_Barge_ID(), get_TrxName());
				
		tugboat2.setHBC_Barge_ID(tugboat1.getHBC_Barge_ID());
		tugboat2.saveEx();
		
		tugboat1.setHBC_Barge_ID(trip.getHBC_Barge_ID());
		tugboat1.saveEx();
		
		
		barge1.setHBC_Tugboat_ID(p_HBC_Tugboat_ID);
		barge1.saveEx();
		
		barge2.setHBC_Tugboat_ID(trip.getHBC_Tugboat_ID());
		barge2.saveEx();
		
		trip.setHBC_Tugboat_ID(p_HBC_Tugboat_ID);
		trip.saveEx();
		
		
		X_HBC_ShipRelationLine srelation1 = new X_HBC_ShipRelationLine(getCtx(),0,get_TrxName());
		srelation1.setHBC_Barge_ID(barge1.getHBC_Barge_ID());
		srelation1.setHBC_Tugboat_ID(barge1.getHBC_Tugboat_ID());
		srelation1.setActiveFrom(trip.getDateStart());
		srelation1.saveEx();
		
		
		X_HBC_ShipRelationLine srelation2 = new X_HBC_ShipRelationLine(getCtx(), 0, get_TrxName());
		srelation2.setHBC_Barge_ID(barge2.getHBC_Barge_ID());
		srelation2.setHBC_Tugboat_ID(barge2.getHBC_Tugboat_ID());
		srelation2.setActiveFrom(trip.getDateStart());
		srelation2.saveEx();
		
		
		
		
		return "";
	}

}
