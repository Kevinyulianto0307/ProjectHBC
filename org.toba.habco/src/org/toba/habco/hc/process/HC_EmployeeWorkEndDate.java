package org.toba.habco.hc.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MEmployee;

/**
 *@author TommyAng 
 *Process to set Work End Date from HC_Employee with Validation(Work End Date Should Be Later Than Work Start Date)
 */
public class HC_EmployeeWorkEndDate extends SvrProcess{

	private int p_HC_Employee_ID = 0;
	private Timestamp p_WorkEndDate = null;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++) {
			
			String name = para[i].getParameterName();
			
			if (para[i].getParameter() == null)
				;
			else if (name.equals("HC_WorkEndDate")){
				p_WorkEndDate = para[i].getParameterAsTimestamp();
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		
		p_HC_Employee_ID = getRecord_ID();		
		MEmployee employee = new MEmployee(getCtx(), p_HC_Employee_ID, get_TrxName());
		Timestamp p_WorkStartDate = (Timestamp) employee.get_Value("HC_WorkStartDate");
		
		if (p_WorkEndDate.getTime() < p_WorkStartDate.getTime()){			
			return "Work End Date Should Be Later Than Work Start Date!";
		} else {
			employee.set_ValueOfColumn("HC_WorkEndDate", p_WorkEndDate);
			employee.saveEx();
		}
		
		return null;
	}

}
