package org.toba.habco.model.validator;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.osgi.service.event.Event;
import org.toba.habco.model.X_HBC_ShipPositionLine;

public class HBC_InOutValidator {

	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MInOut inout=(MInOut)po;
		if (!inout.isSOTrx()) {
			if ((event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSEACCRUAL))||
					(event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSECORRECT))){
				msg = resetActivityOnInOutReversal(inout);
				msg = CShipActivityBunkering(inout);
			}
			if ((event.getTopic().equals(IEventTopics.DOC_BEFORE_REVERSEACCRUAL))||
					(event.getTopic().equals(IEventTopics.DOC_BEFORE_REVERSECORRECT))){
				msg = checkMatchInvoice(inout);
			}
			if(event.getTopic().equals(IEventTopics.DOC_AFTER_COMPLETE)){
				setTrackAssetFlagBasedOnLines(inout);
			}
		}
		return msg;
	}

	private static String CShipActivityBunkering(MInOut inout) {
		String sqlUpdate = "UPDATE HBC_ShipActivity SET M_InOut_ID=NULL, C_Order_ID=NULL, processed='N' WHERE M_InOut_ID = ?";
		DB.executeUpdateEx(sqlUpdate,new Object[]{inout.getM_InOut_ID()}, inout.get_TrxName());
		
		return "";
	}

	private static void setTrackAssetFlagBasedOnLines(MInOut inout) {
		String whereClause = "M_InOut_ID=? AND IsTrackAsAsset='Y'";
		boolean match = new Query(inout.getCtx(), MInOutLine.Table_Name, whereClause, inout.get_TrxName())
		.setParameters(inout.get_ID())
		.setOnlyActiveRecords(true)
		.match();
		
		if (match){
			inout.setIsTrackAsAsset(true);
			inout.saveEx();
		}
	}

	//HABCO-1611 Purchase Order di Window Ship Position - Activity BUNKER
	public static String resetActivityOnInOutReversal(MInOut inout){
		//TODO: note from Edwin. This is a dangerous code. I am not a fan of using firstId(). 
		//Must confirm design to functional to complete this code

		int spositionlineid= new Query(Env.getCtx(),X_HBC_ShipPositionLine.Table_Name,
				"M_InOut_ID="+inout.getM_InOut_ID(),null).firstId();

		X_HBC_ShipPositionLine spositionline= new X_HBC_ShipPositionLine(Env.getCtx(), spositionlineid, inout.get_TrxName());
		spositionline.set_ValueOfColumn("M_InOut_ID", inout.getM_InOut_ID());
		spositionline.setTotalLiters(Env.ZERO);
		spositionline.saveEx();

		return "";
	}//end of HABCO-1611
	
	//@TommyAng
	public static String checkMatchInvoice(MInOut inout){		
		
		String whereClause = "M_InOut_ID=? AND IsInvoiced='Y'";
		boolean match = new Query(inout.getCtx(), MInOutLine.Table_Name, whereClause, inout.get_TrxName())
		.setParameters(inout.get_ID())
		.setOnlyActiveRecords(true)
		.match();
		
		if(match)
			return "Cannot Reverse while MR Line is still Invoiced";
		return"";
	}
	//end @TommyAng
}
