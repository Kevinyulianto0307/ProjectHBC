package org.toba.habco.process;

import org.compiere.model.MPayment;
import org.compiere.process.SvrProcess;

public class HBC_MassCompletePayment2 extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		int[] p_C_Payment_IDs = {1002835,1002531,1002549,1002561,1002581,1002632,1002646,1002678,1002751,1002752,1002750,1002763,1002764,1002765,1002781,
				1002788,1002801,1002811,1002816,1002793,1002813,1002826,1002827,1002828,1002933,1002917,1002916,1002918,1002925,1002930,
				1002934,1002932,1002938,1002940,1002939,1002943,1002944,1002947,1002955,1002950,1002958,1002960,1002961,1002962,1002963,
				1002973,1002964,1002966,1002974,1002977,1002978,1002981,1003007,1003010,1003012,1003016,1002980};
		for(int p_C_Payment_ID : p_C_Payment_IDs){
			MPayment payment = new MPayment(getCtx(), p_C_Payment_ID, get_TrxName());
			
			if(!payment.processIt(MPayment.DOCACTION_Complete))
	        	log.severe("Cant complete payment"+payment.getDocumentNo());
		}
		return null;
	}

}
