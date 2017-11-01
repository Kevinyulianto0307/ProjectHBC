package org.toba.habco.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

public class FelixOnly extends SvrProcess{
	
	Timestamp p_Created = null;
	int p_CreatedBy = 0;
	String p_DocStatus = "";
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if(para[i].getParameterName().equalsIgnoreCase("Created")){
				p_Created = para[i].getParameterAsTimestamp();
			}
			else if(para[i].getParameterName().equalsIgnoreCase("CreatedBy")){
				p_CreatedBy = para[i].getParameterAsInt();
			}
			else if(para[i].getParameterName().equalsIgnoreCase("DocStatus")){
				p_DocStatus = para[i].getParameterAsString();
			}
			else {
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		String msg="";
		String doc="";
		int sum=0;
		Timestamp CreatedTo = TimeUtil.addDays(p_Created, 1);
		//get InvoiceID by Parameter
		String whereClause = "Created>='"+Util.removeTime(p_Created)+"' and Created<='"+CreatedTo+"' and CreatedBy="+p_CreatedBy+" and DocStatus='"+p_DocStatus+"'";
		int[] invoice_IDs = new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.getIDs();
		
		//set Complete by selected ID above
		for (int invoice_ID : invoice_IDs) {
			whereClause = "C_Invoice_ID = "+invoice_ID;
			int C_Invoice_ID = new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstId();
			
			MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
			if(!invoice.processIt(DocAction.ACTION_Complete)){
				log.severe("Cant Complete Invoice#"+invoice.getDocumentNo());
			}
			else {
				doc = doc+" "+invoice.getDocumentNo();
				sum++;
			}
		}
		if (doc == ""){
			msg = "No Invoice completed.";
		}
		else {
			msg = "DocumentNo."+doc+". Total Completed="+sum;
		}
		return msg;
	}

}
