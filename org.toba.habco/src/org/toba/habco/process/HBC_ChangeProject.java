package org.toba.habco.process;

import org.compiere.model.MProject;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MPosition;

import org.toba.habco.model.MTrip;
public class HBC_ChangeProject extends SvrProcess{

	int p_C_Project_ID = 0;
	int HBC_Trip_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Project_ID")){
				p_C_Project_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		HBC_Trip_ID = getRecord_ID();
		MTrip trip = new MTrip(getCtx(), HBC_Trip_ID, get_TrxName());
		MProject project = new MProject(getCtx(), p_C_Project_ID, get_TrxName());
		int HBC_Contract_ID = project.get_ValueAsInt("HBC_Contract_ID");
		//@KevinY
		trip.set_ValueOfColumn("From_PortPosition_ID", project.get_Value("From_PortPosition_ID"));
		trip.set_ValueOfColumn("To_PortPosition_ID"  , project.get_Value("To_PortPosition_ID"));
		trip.set_ValueOfColumn("HBC_Tugboat_ID"		 , project.get_Value("HBC_Tugboat_ID"));
		//@KevinY end
		trip.setC_Project_ID(p_C_Project_ID);
		trip.setHBC_Contract_ID(HBC_Contract_ID);
		trip.saveEx();
		
		for (MPosition position : trip.getPosition()) {
			position.setC_Project_ID(p_C_Project_ID);
			//@KevinY
			position.set_ValueOfColumn("From_PortPosition_ID", project.get_Value("From_PortPosition_ID"));
			position.set_ValueOfColumn("To_PortPosition_ID"  , project.get_Value("To_PortPosition_ID"));
			//@KevinY end
			position.saveEx();
		}
		
		return "Success change project and contract";
	}

}
