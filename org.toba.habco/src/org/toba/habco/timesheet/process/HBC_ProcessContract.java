package org.toba.habco.timesheet.process;

import org.compiere.process.SvrProcess;
import org.toba.habco.model.MContract;

public class HBC_ProcessContract extends SvrProcess{

	protected int HBC_Contract_ID = 0;
	
	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		HBC_Contract_ID = getRecord_ID();
		MContract contract = new MContract(getCtx(), HBC_Contract_ID, get_TrxName());
		
		if(contract.getContractStatus().equals(MContract.CONTRACTSTATUS_Draft)){
			contract.setContractStatus(MContract.CONTRACTSTATUS_Complete);
		}
		
		else if(contract.getContractStatus().equals(MContract.CONTRACTSTATUS_Complete)){
			contract.setContractStatus(MContract.CONTRACTSTATUS_Finish);
			contract.setProcessed(true);
		}
		
		contract.saveEx();
		
		return "";
	}

}
