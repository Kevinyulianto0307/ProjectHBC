package org.toba.habco.ar.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MInvoiceLine;
import org.compiere.model.X_C_Project;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MTrip;

public class HBC_CreateARInvoice extends SvrProcess{

	int p_C_Project_ID= 0;
	int p_M_PriceList_ID= 0;
	int p_M_Product_ID= 0;
	int p_C_Tax_ID= 0;
	
	int p_C_DocTypeTarget_ID = 0;
	Timestamp dateInvoice = new Timestamp(System.currentTimeMillis());
	
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
			else if (name.equals("C_DocType_ID")){
				p_C_DocTypeTarget_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(HBC_MInvoice.COLUMNNAME_DateInvoiced)){
				dateInvoice = para[i].getParameterAsTimestamp();
			
			}
			else if (name.equals("M_PriceList_ID")){
				p_M_PriceList_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("M_Product_ID")){
				p_M_Product_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_Tax_ID")){
				p_C_Tax_ID = para[i].getParameterAsInt();
			}
else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}

	@Override
	protected String doIt() throws Exception {
		
		String msg = "";
		
		if(p_C_Project_ID==0){
			return "Project not selected";
		}
		
		X_C_Project project = new X_C_Project(getCtx(), p_C_Project_ID, get_TrxName());
		boolean isCommercialProject = false;
		isCommercialProject = project.getProjectCategory().equals("ANC") ||
				project.getProjectCategory().equals("TMC") ||
				project.getProjectCategory().equals("SPC");
		
		if (!isCommercialProject)
			return "Abort.. Only Commercial Project Can Be Invoiced";

		BigDecimal invoiceAmt = Env.ZERO;
		
		if (project.getProjectCategory().equals("TMC")) {
			int tripID = DB.getSQLValue(get_TrxName(), "SELECT HBC_Trip_ID FROM HBC_Trip WHERE C_Project_ID=?", project.get_ID());
			if (tripID <= 0) {
				return "Aborted.. No Trip Associated With Time Charter Project";					
			} else {
				MTrip trip = new MTrip(getCtx(), tripID, get_TrxName());
				BigDecimal billableDay = trip.calculateDay();
				if (billableDay.compareTo(Env.ZERO) <= 0)  {
					return "Aborted.. Billable Day = 0";
				} else {
					//TODO: Add price for time charter days in project
					msg = createInvoice("TIME", project, billableDay, Env.ONE);
				}
			}
			
		} else {
			MContract contract = new MContract(getCtx(), project.get_ValueAsInt("HBC_Contract_ID"), get_TrxName());
			
			if (project.get_ValueAsBoolean("isLumpsum")) {
				invoiceAmt = contract.getPayAmt();
				msg = createInvoice("FREIGHT", project, Env.ONE, invoiceAmt);
			} else {
				//invoiceAmt = contract.getPayAmt().multiply(project.getAmountOfCargo());
				msg = createInvoice("FREIGHT", project, 
						new BigDecimal(project.get_ValueAsString("AmountOfCargo")), contract.getPayAmt());
			}
		}
		
		
		
		return msg;
	}

	private String createInvoice(String invoiceType, X_C_Project project,  BigDecimal qty, BigDecimal price) {
		String msg = "";
		
		HBC_MInvoice invoice = new HBC_MInvoice(getCtx(), 0, get_TrxName());
		invoice.setAD_Org_ID(1000050);
		invoice.setC_DocTypeTarget_ID(p_C_DocTypeTarget_ID);
		invoice.setDateInvoiced(dateInvoice);
		invoice.setDateAcct(dateInvoice);
		invoice.setIsSOTrx(true);
		invoice.setPosted(false);
		invoice.setIsPaid(false);
		invoice.setC_BPartner_ID(project.get_ValueAsInt("Customer_BPartner_ID"));
		invoice.setC_BPartner_Location_ID(project.get_ValueAsInt("Customer_Location_ID"));
		invoice.setC_PaymentTerm_ID(project.getC_PaymentTerm_ID());
		invoice.setPaymentRule(HBC_MInvoice.PAYMENTRULE_OnCredit);
		invoice.setC_Currency_ID(project.getC_Currency_ID());
		//TODO: Add conversion type and tax to project
		//invoice.setC_ConversionType_ID(project.getc_c);
		invoice.setM_PriceList_ID(p_M_PriceList_ID);
		invoice.setDocAction(DocAction.ACTION_Complete);
		invoice.setDocStatus(DocAction.STATUS_Drafted);
		invoice.saveEx();
		
		MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
		invoiceLine.setM_Product_ID(p_M_Product_ID); //temporary use parameter
		invoiceLine.setQty(qty);
		invoiceLine.setC_Tax_ID(p_C_Tax_ID); //temporary use parameter
		invoiceLine.setPriceEntered(price);
		invoiceLine.setPriceActual(price);
		invoiceLine.setPriceList(price);
		invoiceLine.saveEx();
		
		msg = Msg.parseTranslation(getCtx(), "@Generated@ " + invoice.getDocumentNo());
		addBufferLog(0, null, null, msg, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		return msg;
		
	}
}
