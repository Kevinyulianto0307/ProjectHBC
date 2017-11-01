package org.toba.habco.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.HBC_MInvoice;

public class HBC_InvoiceAddNoFaktur extends SvrProcess {

	/**
	 * created by yonk
	 */
	int p_C_Invoice_ID=0;
	String FakturNo="";
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("InvoiceTaxNo"))
				FakturNo=para[i].getParameterAsString();
		}
		p_C_Invoice_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		String msg="";
		
		if(FakturNo==""){
			msg= "Invalid no Faktur";
			return msg;
		}
		
		HBC_MInvoice invoice = new HBC_MInvoice(getCtx(),p_C_Invoice_ID,get_TrxName());
		invoice.set_ValueOfColumn("InvoiceTaxNo", FakturNo);
		if(invoice.save())
			msg="No Faktur Successfuly added !";
		
		return msg;
	}

}
