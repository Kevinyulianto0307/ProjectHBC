package org.toba.habco.process;

import java.math.BigDecimal;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.HBC_MInvoice;

/**
 * @author Stephan
 * process update tax amount and withholding amount
 */
public class HBC_SetTaxAmount extends SvrProcess{

	/** process parameter */
	private int p_C_Tax_ID = 0;
	private BigDecimal p_TaxAmt = Env.ZERO;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(MInvoiceTax.COLUMNNAME_C_Tax_ID)){
				p_C_Tax_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(MInvoiceTax.COLUMNNAME_TaxAmt)){
				p_TaxAmt = para[i].getParameterAsBigDecimal();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		int C_Invoice_ID = getRecord_ID();
		//MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		
		// update invoice tax tax amount
		String query = "";
		int no = 0;
		
		if(isWithholding()){
			// update withholding tax amount
			BigDecimal withholdingAmt = p_TaxAmt;
			if(withholdingAmt.compareTo(Env.ZERO) < 0)
				withholdingAmt = withholdingAmt.abs();
			
			query = "UPDATE LCO_InvoiceWithholding SET TaxAmt="+p_TaxAmt
					+" WHERE C_Invoice_ID="+C_Invoice_ID+" AND C_Tax_ID="+p_C_Tax_ID;
			no = DB.executeUpdate(query, get_TrxName());
			
			log.info("UPDATE LCO_InvoiceWithholding (C_Invoice_ID,C_tax_ID) "
					+C_Invoice_ID+","+p_C_Tax_ID+" #"+no);
			
			// update invoice header withholding amount
			query = "UPDATE C_Invoice SET WithholdingAmt="+p_TaxAmt+" WHERE C_Invoice_ID="+C_Invoice_ID;
			no = DB.executeUpdate(query, get_TrxName());
		
			log.info("UPDATE C_Invoice TaxAmt (C_Invoice_ID) "+C_Invoice_ID+" #"+no);
			
			withholdingAmt = p_TaxAmt;
			if(withholdingAmt.compareTo(Env.ZERO) > 0)
				withholdingAmt = withholdingAmt.negate();
			
			query = "UPDATE C_InvoiceTax SET TaxAmt="+withholdingAmt
					+ " WHERE C_Invoice_ID="+C_Invoice_ID+" AND C_Tax_ID="+p_C_Tax_ID;
			no = DB.executeUpdate(query, get_TrxName());
			
			log.info("UPDATE C_InvoiceTax (C_Invoice_ID,C_tax_ID) "
					+C_Invoice_ID+","+p_C_Tax_ID+" #"+no);
		}
		else{
			// update invoice tax, tax amount
			BigDecimal taxAmt = p_TaxAmt;
			if(taxAmt.compareTo(Env.ZERO) < 0)
				taxAmt = taxAmt.abs();
			
			query = "UPDATE C_InvoiceTax SET TaxAmt="+taxAmt
					+ " WHERE C_Invoice_ID="+C_Invoice_ID+"AND C_Tax_ID="+p_C_Tax_ID;
			no = DB.executeUpdate(query, get_TrxName());
			
			log.info("UPDATE C_InvoiceTax (C_Invoice_ID,C_tax_ID) "
					+C_Invoice_ID+","+p_C_Tax_ID+" #"+no);
			
			// update invoice header tax amount
			query = "UPDATE C_Invoice SET TaxAmt="+taxAmt+" WHERE C_Invoice_ID="+C_Invoice_ID;
			no = DB.executeUpdate(query, get_TrxName());
					
			log.info("UPDATE C_Invoice TaxAmt (C_Invoice_ID) "+C_Invoice_ID+" #"+no);
		}
		
		// update invoice header grand total
		query = "UPDATE C_Invoice SET GrandTotal=TotalLines+COALESCE(TaxAmt,0)-COALESCE(WithholdingAmt,0) "
				+ "WHERE C_Invoice_ID="+C_Invoice_ID;
		no = DB.executeUpdate(query, get_TrxName());
				
		log.info("UPDATE C_Invoice GrandTotal (C_Invoice_ID) "+C_Invoice_ID+" #"+no);
		
		// delete fact and update posted N
		query = "DELETE FROM Fact_Acct WHERE Record_ID="+C_Invoice_ID
				+ " AND AD_Table_ID="+MInvoice.Table_ID;
		no = DB.executeUpdate(query, get_TrxName());
		
		log.info("DELETE FROM Fact_Acct Invoice #"+no);
		
		query = "UPDATE C_Invoice SET Posted = 'N' WHERE C_Invoice_ID="+C_Invoice_ID;
		no = DB.executeUpdate(query, get_TrxName());
		
		log.info("UPDATE C_Invoice Posted N #"+no);
		
		//@Phie set flag
		no = DB.executeUpdate("UPDATE C_Invoice SET isUseTaxAmt = 'Y' WHERE C_Invoice_ID= "+C_Invoice_ID, get_TrxName());
		log.info("UPDATE C_Invoice isUseTaxAmt Y #"+no);
		//end phie
		HBC_MInvoice invoice = new HBC_MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		
		if(invoice.get_ValueAsBoolean("isSeparateTaxInvoice")){
			no = DB.executeUpdate("UPDATE C_Invoice SET GrandTotal = TotalLines-WithholdingAmt WHERE C_Invoice_ID= "+C_Invoice_ID, get_TrxName());
			log.info("UPDATE C_Invoice GrandTotal Y #"+no);
		}
		return "Updated to "+p_TaxAmt;
	}

	/**
	 * @return true if withholding
	 */
	private boolean isWithholding(){
		
		int C_Invoice_ID = getRecord_ID();
		
		String query = "SELECT COUNT(LCO_InvoiceWithholding_ID) FROM LCO_InvoiceWithholding "
				+ "WHERE C_Invoice_ID="+C_Invoice_ID+" AND C_Tax_ID="+p_C_Tax_ID;
		int count = DB.getSQLValue(get_TrxName(), query);
		
		if(count > 0)
			return true;
		
		return false;
	}
	
}
