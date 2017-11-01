package org.toba.habco.ar.process;

import org.compiere.process.SvrProcess;
import org.toba.habco.model.MARCalculationLine;

public class HBC_DeleteARCalculationLine extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int ARCalculationLine_ID = getRecord_ID();
		MARCalculationLine calcLine = new MARCalculationLine(getCtx(), ARCalculationLine_ID, get_TrxName());
		calcLine.deleteEx(true, get_TrxName());
		
		return "Deleted";
	}

}
