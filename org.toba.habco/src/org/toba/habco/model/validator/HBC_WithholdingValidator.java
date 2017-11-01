package org.toba.habco.model.validator;

import java.math.BigDecimal;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.osgi.service.event.Event;
import org.toba.habco.model.X_LCO_InvoiceWithholding;

/**
 * @author TommyAng
 * withholding validator
 */
public class HBC_WithholdingValidator {
	
	/**
	 * execute invoice withholding event
	 * @param Event, PO
	 * @return error message
	 */
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		X_LCO_InvoiceWithholding invWithholding = new X_LCO_InvoiceWithholding(po.getCtx(), po.get_ID(), po.get_TrxName());
		
		if (event.getTopic().equals(IEventTopics.PO_AFTER_NEW)){
			msg = afterNew(invWithholding);
		}
		
		return msg;
	}
	
	/**
	 * method afterNew 
	 * @param X_LCO_InvoiceWithholding
	 * @return empty string
	 */
	public static String afterNew(X_LCO_InvoiceWithholding invWithholding){
		
		BigDecimal amount = invWithholding.getTaxAmt();
		int type = invWithholding.getLCO_WithholdingType_ID();
		MInvoice invoice = new MInvoice(invWithholding.getCtx(), invWithholding.getC_Invoice_ID(), invWithholding.get_TrxName());
		String sql = "UPDATE C_InvoiceTax "
				   + "SET WithholdingAmt = '"+amount+"', LCO_WithholdingType_ID = "+type+" "
				   + "WHERE C_Invoice_ID = "+invWithholding.getC_Invoice_ID()+" AND C_Tax_ID = "+invoice.getC_Tax_ID()+"";
		DB.executeUpdate(sql, invWithholding.get_TrxName());
		return "";
	}
}
