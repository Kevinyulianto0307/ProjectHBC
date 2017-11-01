package org.toba.habco.process;

import java.util.List;
import java.util.logging.Level;

import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.MPriceList;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;

/**
 * @author Phie Albert
 * Set M_PriceList_ID on invoice header with price list on parameter
 * The difference of isTaxIncluded between PO price list and parameter price list will affect on tax rounding
 */

public class HBC_ChangePriceList extends SvrProcess{
	
	private int p_C_Invoice_ID = 0;
	private int p_M_PriceList_ID = 0;
	
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_PriceList_ID"))
				p_M_PriceList_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_Invoice_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_C_Invoice_ID == 0)
			return "Process Aborted.. No invoice selected";
		
		HBC_MInvoice invoice = new HBC_MInvoice(getCtx(), p_C_Invoice_ID, get_TrxName());
		
		if(invoice.getDocStatus().equals("CO"))
			throw new IllegalArgumentException("Invoice has already completed");
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE C_Invoice SET M_PriceList_ID = "+p_M_PriceList_ID +" WHERE C_Invoice_ID = "+p_C_Invoice_ID);
		
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		log.info("Price List Updated - "+no);

		List<HBC_MInvoiceLine> list = new Query(getCtx(), I_C_InvoiceLine.Table_Name, "C_Invoice_ID=? ", get_TrxName())
		.setParameters(p_C_Invoice_ID)
		.setOrderBy(I_C_InvoiceLine.COLUMNNAME_Line)
		.list();
		HBC_MInvoiceLine[] invLine = list.toArray(new HBC_MInvoiceLine[list.size()]);
		
		//Re-calculate Invoice Tax with new price list
		for(HBC_MInvoiceLine sLine : invLine)
		{
			if(sLine.getC_Tax_ID() == 100006)
				continue;
				
			//temporary tax
			int tax_id = sLine.getC_Tax_ID();
			//set tax to exempt
			sLine.setC_Tax_ID(100006);
			sLine.saveEx();
			//Re Set tax
			sLine.setC_Tax_ID(tax_id);
			sLine.saveEx();
		}
		
		MPriceList priceList = new MPriceList(getCtx(), p_M_PriceList_ID, get_TrxName());
		invoice.setIsTaxIncluded(priceList.get_ValueAsBoolean("isTaxIncluded"));
		invoice.saveEx();
		
		if(no!=0)
			return "Price list changed";
		
		return "There's no change";
	}

}
