package org.toba.habco.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class HBC_MassInvoiceCustomerRounding extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {

		String sql = "select distinct ci.c_invoice_id from c_invoiceline cl join c_invoice ci on ci.c_invoice_id = cl.c_invoice_id where ci.issotrx='Y'";
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				MInvoice invoice = new MInvoice(getCtx(), id, get_TrxName());
				if(invoice.getC_Currency().getISO_Code().equals("IDR")){
					
					//  rounding line net amt
					for (MInvoiceLine invoiceLine : invoice.getLines()) {
						
						// line round line net amt, rounding half up 0, half_up
						StringBuilder sb = new StringBuilder();
						sb.append("UPDATE C_InvoiceLine SET LineNetAmt=ROUND(LineNetAmt,0) WHERE C_InvoiceLine_ID=?");
						int no = DB.executeUpdate(sb.toString(), invoiceLine.get_ID(), get_TrxName());
						log.info("UPDATED LineNetAmt C_InvoiceLine#"+no);
					}
					
					//  set total lines header
					StringBuilder sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET TotalLines = (SELECT SUM(LineNetAmt) FROM C_InvoiceLine WHERE C_Invoice_ID=?) WHERE C_Invoice_ID=?");
					int no = DB.executeUpdate(sb.toString(), new Object[]{id, id}, true, get_TrxName());
					log.info("UPDATED TotalLines C_Invoice#"+no);
					
					//	round down ,00 C_InvoiceTax tax amt
					sb = new StringBuilder();
					sb.append("UPDATE C_InvoiceTax SET TaxAmt=TRUNC(TaxAmt) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED C_InvoiceTax TaxAmt C_Invoice#"+no);
					
					//	round down ,00 LCO_InvoiceWithholding withholding amt
					sb = new StringBuilder();
					sb.append("UPDATE LCO_InvoiceWithholding SET TaxAmt=TRUNC(TaxAmt) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED LCO_InvoiceWithholding TaxAmt C_Invoice#"+no);
					
					//	round down ,00 C_Invoice tax amt
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET TaxAmt=TRUNC(TaxAmt) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED C_Invoice TaxAmt C_Invoice#"+no);
					
					//	round down ,00 C_Invoice withholding amt
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET WithholdingAmt=TRUNC(WithholdingAmt) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED C_Invoice TaxAmt C_Invoice#"+no);
					
					//  recalculate grand total
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET GrandTotal=COALESCE(TotalLines,0)+COALESCE(TaxAmt,0)-COALESCE(WithholdingAmt,0) "
							+ "WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED GrandTotal C_Invoice#"+no);
					
					//  recalculate outstanding invoice
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET OutstandingInv=COALESCE(TotalLines,0)+COALESCE(TaxAmt,0) "
							+ "WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED OutstandingInv C_Invoice#"+no);
					
					
					
				}else{
					
					//  rounding line net amt
					for (MInvoiceLine invoiceLine : invoice.getLines()) {
						
						// line round line net amt, rounding half up 0, half_up
						StringBuilder sb = new StringBuilder();
						sb.append("UPDATE C_InvoiceLine SET LineNetAmt=ROUND(LineNetAmt,2) WHERE C_InvoiceLine_ID=?");
						int no = DB.executeUpdate(sb.toString(), invoiceLine.get_ID(), get_TrxName());
						log.info("UPDATED LineNetAmt C_InvoiceLine#"+no);
					}
		
					//  set total lines header
					StringBuilder sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET TotalLines = (SELECT SUM(LineNetAmt) FROM C_InvoiceLine WHERE C_Invoice_ID=?) WHERE C_Invoice_ID=?");
					int no = DB.executeUpdate(sb.toString(), new Object[]{id, id}, true, get_TrxName());
					log.info("UPDATED TotalLines C_Invoice#"+no);
					
					//	round half up ,2 C_InvoiceTax tax amt
					sb = new StringBuilder();
					sb.append("UPDATE C_InvoiceTax SET TaxAmt=TRUNC(TaxAmt,2) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED C_InvoiceTax TaxAmt C_Invoice#"+no);
					
					//	round half up ,2 LCO_InvoiceWithholding withholding amt
					sb = new StringBuilder();
					sb.append("UPDATE LCO_InvoiceWithholding SET TaxAmt=TRUNC(TaxAmt,2) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED LCO_InvoiceWithholding TaxAmt C_Invoice#"+no);
					
					//	round half up ,2 C_Invoice tax amt
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET TaxAmt=TRUNC(TaxAmt,2) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED C_Invoice TaxAmt C_Invoice#"+no);
					
					//	round half up ,2 C_Invoice withholding amt
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET WithholdingAmt=TRUNC(WithholdingAmt,2) WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED C_Invoice TaxAmt C_Invoice#"+no);
					
					//  recalculate grand total
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET GrandTotal=COALESCE(TotalLines,2)+COALESCE(TaxAmt,2)-COALESCE(WithholdingAmt,2) "
							+ "WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED GrandTotal C_Invoice#"+no);
					
					//  recalculate outstanding invoice
					sb = new StringBuilder();
					sb.append("UPDATE C_Invoice SET OutstandingInv=COALESCE(TotalLines,2)+COALESCE(TaxAmt,2) "
							+ "WHERE C_Invoice_ID=?");
					no = DB.executeUpdate(sb.toString(), id, get_TrxName());
					log.info("UPDATED OutstandingInv C_Invoice#"+no);
					
				}
		}
		return "";
	}

}
