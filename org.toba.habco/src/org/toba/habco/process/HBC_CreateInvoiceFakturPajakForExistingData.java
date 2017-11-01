package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;

public class HBC_CreateInvoiceFakturPajakForExistingData extends SvrProcess{

	final int functionalCurrencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
	
	int p_C_Invoice_ID = 0;
	
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params) {
			if (param.getParameterName().equals("C_Invoice_ID")) {
				p_C_Invoice_ID = param.getParameterAsInt();
			}
		}
		
	}

	protected String doIt() throws Exception {
		String whereClause = "C_Currency_ID != ? AND DocStatus IN ('CO','CL') AND AD_Client_ID = ? AND Issotrx='Y'";
		
		if (p_C_Invoice_ID > 0)
			whereClause = whereClause + " AND C_Invoice_ID="+p_C_Invoice_ID;
		
		int[] invoiceIDs = new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
								.setParameters(new Object[]{functionalCurrencyID, getAD_Client_ID()})						
								.getIDs();
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE C_Invoice Set IsSeparateTaxInvoice='Y' WHERE C_Invoice_ID IN "
				+ "(SELECT C_Invoice_ID FROM C_Invoice WHERE C_Currency_ID != ? AND DocStatus IN ('CO','CL') AND AD_Client_ID = ?  AND Issotrx='Y'");
		
		if (p_C_Invoice_ID > 0)
			sb.append(" AND C_Invoice_ID=").append(p_C_Invoice_ID);
		
		sb.append(")");
		
		int no = DB.executeUpdateEx(sb.toString(), new Object[]{functionalCurrencyID, getAD_Client_ID()}, get_TrxName());
		
		System.out.println(invoiceIDs.length);
		System.out.println(no);
		for(int id : invoiceIDs)
		{
			HBC_MInvoice invoice = new HBC_MInvoice(getCtx(), id, get_TrxName());
			System.out.println(invoice.getDocumentNo());
			for(MInvoiceTax invTax : invoice.getTaxes(true))
			{
				MTax tax = new MTax(invoice.getCtx(), invTax.getC_Tax_ID(), invoice.get_TrxName());
				MTaxCategory taxCategory = new MTaxCategory(invoice.getCtx(), tax.getC_TaxCategory_ID(), invoice.get_TrxName());
				if(taxCategory.get_ValueAsBoolean("isWithholding") || tax.getRate().compareTo(Env.ZERO) == 0)
					continue;
				
				if(tax.get_ValueAsInt("C_Charge_ID") == 0)
					throw new AdempiereException("Aborted.. Missing setup.. Please fill charge for this tax :"+tax.getName());
				if(tax.get_ValueAsString("shortName").equals(""))
					throw new AdempiereException("Aborted.. Missing setup.. Please fill short name for this tax :"+tax.getName());
			}
			
			for(MInvoiceTax invTax : invoice.getTaxes(true))
			{
				MTax tax = new MTax(invoice.getCtx(), invTax.getC_Tax_ID(), invoice.get_TrxName());
				MTaxCategory taxCategory = new MTaxCategory(invoice.getCtx(), tax.getC_TaxCategory_ID(), invoice.get_TrxName());
				if(taxCategory.get_ValueAsBoolean("isWithholding") || tax.getRate().compareTo(Env.ZERO) == 0)
					continue;
				
				HBC_MInvoice invoiceFP = new HBC_MInvoice(invoice.getCtx(), 0, invoice.get_TrxName());
				invoiceFP.setAD_Org_ID(invoice.getAD_Org_ID());
				invoiceFP.setC_Campaign_ID(invoice.getC_Campaign_ID());
				invoiceFP.setDocumentNo(invoice.getDocumentNo().concat("-").concat(tax.get_ValueAsString("shortName"))); //DocumentNo
				invoiceFP.set_ValueOfColumn("isTaxInvoice", true); //SET isTaxInvoice
				invoiceFP.setIsSOTrx(invoice.isSOTrx());
				invoiceFP.setC_DocType_ID(invoice.getC_DocType_ID());
				invoiceFP.setC_DocTypeTarget_ID(invoice.getC_DocTypeTarget_ID());
				invoiceFP.set_ValueOfColumn("OrderReference", invoice.get_Value("OrderReference"));
				invoiceFP.setC_BPartner_ID(invoice.getC_BPartner_ID());
				invoiceFP.setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID());
				invoiceFP.setIsTaxIncluded(invoice.isTaxIncluded());
				invoiceFP.setIsTrackAsAsset(invoice.isTrackAsAsset());
				invoiceFP.setDateAcct(invoice.getDateAcct());
				invoiceFP.setDateInvoiced(invoice.getDateInvoiced());
				invoiceFP.setAD_User_ID(invoice.getAD_User_ID());
				invoiceFP.setSalesRep_ID(invoice.getSalesRep_ID());
				invoiceFP.setIsDownPaymentInvoice(invoice.isDownPaymentInvoice());
				invoiceFP.setDescription("Invoice Faktur Pajak from "+ invoice.getDocumentNo());
				invoiceFP.set_ValueOfColumn("isNoPriceList", invoice.get_ValueAsBoolean("isNoPriceList"));
				invoiceFP.setM_PriceList_ID(1000053); //TODO PRICE LIST AP DAN AR
				invoiceFP.setC_Tax_ID(1000006); //EXEMPT
				invoiceFP.setC_Currency_ID(functionalCurrencyID); //functional currency
				invoiceFP.setC_ConversionType_ID(invoice.getC_ConversionType_ID());
				invoiceFP.setC_Payment_ID(invoice.getC_Payment_ID());
				invoiceFP.setPaymentRule(invoice.getPaymentRule());
				invoiceFP.setC_PaymentTerm_ID(invoice.getC_PaymentTerm_ID());
				invoiceFP.setC_Project_ID(invoice.getC_Project_ID());
				if(invoice.get_ValueAsInt("HBC_Tugboat_ID") != 0)
					invoiceFP.set_ValueOfColumn("HBC_Tugboat_ID", invoice.get_ValueAsInt("HBC_Tugboat_ID"));
				if(invoice.get_ValueAsInt("HBC_Barge_ID") != 0)
					invoiceFP.set_ValueOfColumn("HBC_Barge_ID", invoice.get_ValueAsInt("HBC_Barge_ID"));
				invoiceFP.setDocStatus(DocAction.STATUS_Drafted);	
				invoiceFP.saveEx();
				
				//CREATE LINE
				
				sb = new StringBuilder();
				sb.append("SELECT CurrencyRate(?,?,?,?,?,?)");
				BigDecimal kurs = DB.getSQLValueBD(invoice.get_TrxName(), sb.toString(), new Object[]{invoice.getC_Currency_ID(), 
					functionalCurrencyID, invoice.getDateAcct(), invoice.getC_ConversionType_ID(),invoice.getAD_Client_ID(), invoice.getAD_Org_ID()});
				
				BigDecimal price = invTax.getTaxBaseAmt().multiply(kurs).multiply(tax.getRate()).divide(Env.ONEHUNDRED).setScale(0, RoundingMode.DOWN);
				
				HBC_MInvoiceLine invLineFP = new HBC_MInvoiceLine(invoice.getCtx(), 0, invoice.get_TrxName());
				invLineFP.setAD_Org_ID(invoiceFP.getAD_Org_ID());
				invLineFP.setC_Invoice_ID(invoiceFP.getC_Invoice_ID());
				if(invoice.isSOTrx())
					invLineFP.setC_Charge_ID(tax.get_ValueAsInt("C_Charge_ID")); //Charge from Tax
				else
					invLineFP.setC_Charge_ID(tax.get_ValueAsInt("PO_Charge_ID")); //Charge from Tax
				invLineFP.setQtyEntered(Env.ONE);
				invLineFP.setQtyInvoiced(Env.ONE);
				invLineFP.setC_Tax_ID(1000006); //Exempt Tax
				invLineFP.setPriceEntered(price);
				invLineFP.setPriceActual(price);
				invLineFP.setPriceList(price);
				invLineFP.set_ValueOfColumn("OriginalPriceList", price);
				invLineFP.setDescription(tax.getName()+"-"+tax.get_ValueAsString("shortName"));
				invLineFP.saveEx();
				
				invTax.set_ValueOfColumn("Tax_Invoice_ID", invoiceFP.getC_Invoice_ID());
				invTax.saveEx();
				
				//if(!invoiceFP.processIt(DocAction.ACTION_Complete))
					//throw new AdempiereException("Cannot complete invoice faktur pajak "+ invoiceFP.getDocumentNo());
				//invoiceFP.saveEx();
				System.out.println(invoiceFP.getDocumentNo());
				invoice.set_ValueOfColumn("outstandinginv", invoice.getTotalLines());
				invoice.setGrandTotal(invoice.getTotalLines().subtract(invoice.getWithholdingAmt()));
				invoice.setPosted(false);
				invoice.saveEx();
			}
		}
		return "";
	}
}
