package org.toba.habco.timesheet.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;

public class HBC_ProcessSisipActivity extends SvrProcess {

	int p_HBC_Position_ID;
	Timestamp p_StartDate;
	Timestamp p_EndDate;
	int p_C_Activity_ID;
	int p_TugBoatPartner_ID;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("StartDate"))
				p_StartDate = para[i].getParameterAsTimestamp();
			else if (name.equals("FinishDate"))
				p_EndDate = para[i].getParameterAsTimestamp();
			else if (name.equals("C_Activity_ID"))
				p_C_Activity_ID=para[i].getParameterAsInt();
			else if (name.equals("TugBoatPartner_ID"))
				p_TugBoatPartner_ID=para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_HBC_Position_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		if (p_HBC_Position_ID<=0){
			return "No Position Selected";
		}
		
		MPosition position = new MPosition(getCtx(),p_HBC_Position_ID,get_TrxName());
		
		MShipActivity[] shipActivitys=position.getShipActivityDesc();
		
		for(MShipActivity shipactivity : shipActivitys){
			if(shipactivity.getStartDate().getTime()>p_StartDate.getTime())
				continue;
			else {
				MShipActivity sActivity = new MShipActivity(getCtx(), 0, get_TrxName());
				sActivity.setC_Activity_ID(p_C_Activity_ID);
				sActivity.setStartDate(p_StartDate);
				sActivity.setFinishDate(p_EndDate);
				sActivity.setAD_Org_ID(position.getAD_Org_ID());
				sActivity.setLine(shipactivity.getLine()+10);
				sActivity.setHBC_Position_ID(p_HBC_Position_ID);
				sActivity.setHBC_Tugboat_ID(position.getHBC_Tugboat_ID());
				//@TommyAng
				if(p_C_Activity_ID == 1000030)
					sActivity.setTugBoatPartner_ID(p_TugBoatPartner_ID);
				//end @TommyAng
				sActivity.setHBC_Barge_ID(position.getHBC_Barge_ID());
				sActivity.saveEx();
				ReArrangeLineNo();
				
				if(!shipactivity.isLastActivity()){
					MShipActivity newSactivity = new MShipActivity(getCtx(), sActivity.getHBC_ShipActivity_ID(), get_TrxName());		
					MShipActivity nextShipActivity= getNextShipActivity(newSactivity.getLine()+10);
					if(nextShipActivity.getStartDate().getTime()<p_EndDate.getTime()){
						nextShipActivity.setStartDate(p_EndDate);
						nextShipActivity.saveEx();
					}else if (nextShipActivity.getStartDate().getTime()>p_EndDate.getTime()){
						MShipActivity beforeActivity = new MShipActivity(getCtx(), 0, get_TrxName());
						beforeActivity.setC_Activity_ID(shipactivity.getC_Activity_ID());
						beforeActivity.setStartDate(p_EndDate);
						beforeActivity.setFinishDate(nextShipActivity.getStartDate());
						beforeActivity.setAD_Org_ID(position.getAD_Org_ID());
						beforeActivity.setLine(sActivity.getLine()+10);
						beforeActivity.setHBC_Position_ID(p_HBC_Position_ID);
						beforeActivity.setHBC_Tugboat_ID(position.getHBC_Tugboat_ID());
						beforeActivity.setHBC_Barge_ID(position.getHBC_Barge_ID());
						beforeActivity.saveEx();
						ReArrangeLineNo();
					}
					break;
				}else if(shipactivity.isLastActivity()){
					if(p_EndDate.getTime()<shipactivity.getFinishDate().getTime()){
						MShipActivity shipActivity = new MShipActivity(getCtx(), 0, get_TrxName());
						shipActivity.setC_Activity_ID(shipactivity.getC_Activity_ID());
						shipActivity.setStartDate(p_EndDate);
						shipActivity.setFinishDate(shipactivity.getFinishDate());
						shipActivity.setAD_Org_ID(position.getAD_Org_ID());
						shipActivity.setLine(sActivity.getLine()+10);
						shipActivity.setHBC_Position_ID(p_HBC_Position_ID);
						shipActivity.setIsLastActivity(true);
						shipActivity.setHBC_Tugboat_ID(position.getHBC_Tugboat_ID());
						shipActivity.setHBC_Barge_ID(position.getHBC_Barge_ID());
						shipActivity.saveEx();
						
						shipactivity.setIsLastActivity(false);
						shipactivity.setFinishDate(sActivity.getStartDate());
						shipactivity.saveEx();
						ReArrangeLineNo();
					}else if(p_EndDate.getTime()>shipactivity.getFinishDate().getTime())
						return "End date can't be bigger than last activity finish date!";
					break;
				}			
			
		}
		}	
		return "";
	}
	
	protected MShipActivity getNextShipActivity(int Line){
		int ShipActivityID = new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+p_HBC_Position_ID+" AND Line="+Line,get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstId();
		MShipActivity shipactivity = new MShipActivity(getCtx(), ShipActivityID, get_TrxName());
		
		return shipactivity;
	}
	
	/**
	 * re arrange line no ship activity
	 */
	protected void ReArrangeLineNo(){
		MPosition position = new MPosition(getCtx(),p_HBC_Position_ID,get_TrxName());
		
		MShipActivity[] shipActivitys=position.getShipActivity();
		int line =0;
		for(MShipActivity shipActivity:shipActivitys){
			line=line+10;
			shipActivity.setLine(line);
			shipActivity.saveEx();
		}
		
	}

}
