package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.X_T_InvoiceSummary;

public class HBC_InvoiceSummary extends SvrProcess{

	Timestamp p_dateFrom = null;
	Timestamp p_dateTo = null;
	int p_C_BPartner_ID = 0;
	boolean p_isSOTrx = false;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(X_T_InvoiceSummary.COLUMNNAME_DateInvoiced)){
				p_dateFrom = para[i].getParameterAsTimestamp();
				p_dateTo = para[i].getParameter_ToAsTimestamp();
			}
			else if(name.equals(X_T_InvoiceSummary.COLUMNNAME_C_BPartner_ID)){
				p_C_BPartner_ID = para[i].getParameterAsInt();
			}
			else if(name.equals(X_T_InvoiceSummary.COLUMNNAME_IsSOTrx)){
				p_isSOTrx = para[i].getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		StringBuilder where = new StringBuilder();
		where.append("DateInvoiced BETWEEN ? AND ? AND DocStatus = ? AND IsSOTrx = ? ");
		
		if(p_C_BPartner_ID > 0)
			where.append("AND C_BPartner_ID = ?");
		
		Object[] params = null;
		params = new Object[]{p_dateFrom, p_dateTo, DocAction.STATUS_Completed, p_isSOTrx};
		
		if(p_C_BPartner_ID > 0)
			params = new Object[]{p_dateFrom, p_dateTo, DocAction.STATUS_Completed, p_isSOTrx, p_C_BPartner_ID};
		
		int[] invoice_IDs = new Query(getCtx(), MInvoice.Table_Name, where.toString(), get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setParameters(params)
		.setOrderBy(MInvoice.COLUMNNAME_DateInvoiced)
		.getIDs();
		
		int line = 0;
		for (int C_Invoice_ID : invoice_IDs) {
			MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
			X_T_InvoiceSummary invSum = new X_T_InvoiceSummary(getCtx(), 0, get_TrxName());
			
			invSum.setLine(line+=1);
			invSum.setAD_PInstance_ID(getAD_PInstance_ID());
			invSum.setC_Invoice_ID(C_Invoice_ID);
			invSum.setinvoiceno(invoice.getDocumentNo());
			invSum.setDateInvoiced(invoice.getDateInvoiced());
			invSum.setC_BPartner_ID(invoice.getC_BPartner_ID());
			invSum.setIsSOTrx(invoice.isSOTrx());
			invSum.setInvoiceAmt(invoice.getGrandTotal());
			invSum.setIsPaid(invoice.isPaid());
			invSum.set_ValueOfColumn("TaxAmt", invoice.getTaxAmt());
			invSum.set_ValueOfColumn("WithholdingAmt", invoice.getWithholdingAmt());
			invSum.set_ValueOfColumn("TotalLines", invoice.getTotalLines());
			
			//@phie
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT currencyrate("+invoice.getC_Currency_ID()+",303,'"+invoice.getDateAcct()+"',"+invoice.getC_ConversionType_ID()+","+getAD_Client_ID()+","+Env.getAD_Org_ID(getCtx())+")");
			
			BigDecimal kurs = DB.getSQLValueBD(get_TrxName(), sb.toString());
			invSum.set_ValueOfColumn("rate_invoice", kurs);
			if(invoice.get_Value("OrderReference")!=null)
				invSum.set_ValueOfColumn("OrderReference", invoice.get_Value("OrderReference"));
			//end phie
			
			invSum.saveEx();
			
			String whereClause = "C_Invoice_ID=? AND DateTrx BETWEEN ? AND ?";
			int[] allocationline_IDs = new Query(getCtx(), MAllocationLine.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setParameters(new Object[]{C_Invoice_ID, p_dateFrom, p_dateTo})
			.getIDs();
			
			for (int C_AllocationLine_ID : allocationline_IDs) {
				MAllocationLine allocLine = new MAllocationLine(getCtx(), C_AllocationLine_ID, get_TrxName());
				if(allocLine.getC_Payment_ID() <= 0)
					continue;
				MPayment payment = new MPayment(getCtx(), allocLine.getC_Payment_ID(), get_TrxName());
				invSum = new X_T_InvoiceSummary(getCtx(), 0, get_TrxName());
				invSum.setLine(line+=1);
				invSum.setAD_PInstance_ID(getAD_PInstance_ID());
				invSum.setC_Payment_ID(payment.get_ID());
				invSum.setpaymentno(payment.getDocumentNo());
				invSum.setDateInvoiced(allocLine.getDateTrx());
				invSum.setC_BPartner_ID(invoice.getC_BPartner_ID());
				invSum.setIsSOTrx(invoice.isSOTrx());
				invSum.setOpenAmt(allocLine.getAmount());
				invSum.saveEx();
			}
			
		}
		
		return "";
	}

}
