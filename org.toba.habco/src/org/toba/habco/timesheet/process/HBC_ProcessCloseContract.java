package org.toba.habco.timesheet.process;

import org.compiere.process.SvrProcess;
import org.toba.habco.model.MContract;

public class HBC_ProcessCloseContract extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int HBC_Contract_ID = getRecord_ID();
		
		if (HBC_Contract_ID < 1)
			return "Process aborted.. No contract has been selected";
		
		MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
		
		if (contract.getContractStatus().equals(MContract.CONTRACTSTATUS_Cancel))
			return "Process aborted.. Contract has been cancelled";
		
		if (contract.getContractStatus().equals(MContract.CONTRACTSTATUS_Closed))
			return "Process aborted.. Contract has been closed";
		
		contract.setContractStatus(MContract.CONTRACTSTATUS_Closed);
		contract.saveEx();
		
		return "Success.. Contract has been closed";
	}

}
