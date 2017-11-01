package org.toba.habco.timesheet.process;


import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.toba.habco.model.MBarge;
import org.toba.habco.model.MTugboat;
import org.toba.habco.model.X_HBC_ShipActivity;
import org.toba.habco.model.X_HBC_ShipRelationLine;

public class HBC_ProcessCreateOvertowingHistory extends SvrProcess {

	int p_HBC_ShipActivity_ID=0;
	
	/*
	 * Created By Yonk
	 * HABCO-1609 Overtowing History
	 */
	
	@Override
	protected void prepare() {
		p_HBC_ShipActivity_ID=getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_ShipActivity_ID==0){
			return "No Ship Activity Selected !";
		}
		
		X_HBC_ShipActivity sactivity = new X_HBC_ShipActivity(getCtx(), p_HBC_ShipActivity_ID, get_TrxName());
		
		
		
		if (sactivity.getTugBoatPartner().getHBC_Barge_ID()<0){
			return "Error-Tugboat Partner doesn't have barge";
		}
		
		X_HBC_ShipRelationLine srelation1 = new X_HBC_ShipRelationLine(getCtx(),0,get_TrxName());
		srelation1.setHBC_Barge_ID(sactivity.getTugBoatPartner().getHBC_Barge_ID());
		srelation1.setHBC_Tugboat_ID(sactivity.getHBC_Tugboat_ID());
		srelation1.setActiveFrom(sactivity.getStartDate());
		srelation1.saveEx();
		
		
		X_HBC_ShipRelationLine srelation2 = new X_HBC_ShipRelationLine(getCtx(), 0, get_TrxName());
		srelation2.setHBC_Barge_ID(sactivity.getHBC_Barge_ID());
		srelation2.setHBC_Tugboat_ID(sactivity.getTugBoatPartner_ID());
		srelation2.setActiveFrom(sactivity.getStartDate());
		srelation2.saveEx();
		
		MTugboat tugboat1 = new MTugboat(getCtx(), sactivity.getHBC_Tugboat_ID(), get_TrxName());
		MTugboat tugboat2 = new MTugboat(getCtx(), sactivity.getTugBoatPartner_ID(), get_TrxName());
		MBarge barge1 = new MBarge(getCtx(), sactivity.getTugBoatPartner().getHBC_Barge_ID(), get_TrxName());
		MBarge barge2 = new MBarge(getCtx(), sactivity.getHBC_Barge_ID(), get_TrxName());
		
		
		tugboat1.setHBC_Barge_ID(sactivity.getTugBoatPartner().getHBC_Barge_ID());
		tugboat1.saveEx();
		
		tugboat2.setHBC_Barge_ID(sactivity.getHBC_Barge_ID());
		tugboat2.saveEx();
		
		barge1.setHBC_Tugboat_ID(sactivity.getHBC_Tugboat_ID());
		barge1.saveEx();
		
		barge2.setHBC_Tugboat_ID(sactivity.getTugBoatPartner_ID());
		barge2.saveEx();
		
		String msg = Msg.parseTranslation(getCtx(), "Overtowing Successfull");
		addLog(0, null, null, msg, 0, 0);
			
		
		
		return "";
	}
	
}
