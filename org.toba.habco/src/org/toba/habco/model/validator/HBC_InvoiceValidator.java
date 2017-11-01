package org.toba.habco.model.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MProject;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.MSequence.GetIDs;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jfree.util.Log;
import org.osgi.service.event.Event;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;
import org.toba.habco.model.MCostActivity;
import org.toba.habco.model.MMatchARCalculation;
import org.toba.habco.model.MMatchCostActivity;
import org.toba.habco.model.MShipDocumentDock;
import org.toba.habco.model.X_T_ReferenceCode;

public class HBC_InvoiceValidator {

	/**
	 * execute invoice event
	 * @param Event, PO
	 * @return error message
	 */
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		//MInvoice invoice=(MInvoice)po;
		HBC_MInvoice invoice = new HBC_MInvoice(po.getCtx(), po.get_ID(), po.get_TrxName());
		if ((event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSEACCRUAL)) ||
				(event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSECORRECT))) {
			msg = afterreverse(invoice);
			//@PhieAlbert reverse invoice faktur pajak
			//if(event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSEACCRUAL))
				//msg = reverseInvoiceTaxFP(invoice, true);
			//else 
				//msg = reverseInvoiceTaxFP(invoice, false);
		}
		//@PhieAlbert
		else if ((event.getTopic().equals(IEventTopics.DOC_BEFORE_REVERSEACCRUAL)) ||
				(event.getTopic().equals(IEventTopics.DOC_BEFORE_REVERSECORRECT))) {
			msg = checkAllocation(invoice);
			msg = checkIsInvoiceTax(invoice);
		}
		else if(event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)){
			if (!invoice.isSOTrx() && !invoice.isReversal())
				msg = checkMR(invoice);
		}		
		else if(event.getTopic().equals(IEventTopics.DOC_BEFORE_PREPARE)){
			msg = separateTax(invoice);
		}
		//end @PhieAlbert
		
		else if(event.getTopic().equals(IEventTopics.DOC_AFTER_COMPLETE)){
			msg = afterComplete(invoice);
			msg = createInvoiceTaxFP(invoice);
		}

		else if(event.getTopic().equals(IEventTopics.DOC_AFTER_PREPARE)){
			msg = afterPrepare(invoice);
		}
		
		//@TommyAng
		if ((event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSEACCRUAL)) ||
				(event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSECORRECT)) ||
				(event.getTopic().equals(IEventTopics.DOC_AFTER_VOID))) {
			msg = docking(invoice);
		}
		
		if ((event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSEACCRUAL)) ||
				(event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSECORRECT)) ||
				(event.getTopic().equals(IEventTopics.DOC_AFTER_VOID))) {
			msg = shipDocumentDock(invoice);
		}
		
		/*if(event.getTopic().equals(IEventTopics.DOC_AFTER_COMPLETE)){
			msg = withholdingHdr(inv);
		}*/
		//end @TommyAng
		return msg;
	}

	//@PhieAlbert After Reverse
	private static String reverseInvoiceTaxFP(HBC_MInvoice invoice, boolean acrual) {
		for(MInvoiceTax invTax : invoice.getTaxes(true)){
			if(invTax.get_ValueAsInt("Tax_Invoice_ID") > 0){	
				HBC_MInvoice TaxInvoice = new HBC_MInvoice(invoice.getCtx(), invTax.get_ValueAsInt("Tax_Invoice_ID"), invoice.get_TrxName());
				/*
				if(acrual){
					if(!TaxInvoice.processIt(DocAction.ACTION_Reverse_Accrual))
						throw new AdempiereException("Cannot reverse invoice faktur pajak");
				}
				else{ 
					if(!TaxInvoice.processIt(DocAction.ACTION_Reverse_Correct))
						throw new AdempiereException("Cannot reverse invoice faktur pajak");
				}
				*/
			}
		}

		return "";
	}
	//end @PhieAlbert

	//@PhieAlbert Before Reverse
	private static String checkIsInvoiceTax(HBC_MInvoice invoice) {	
		if(invoice.get_ValueAsBoolean("isTaxInvoice")){
			String whereClause = "EXISTS (SELECT 1 from C_InvoiceTax WHERE Tax_Invoice_ID = ? AND C_InvoiceTax.C_Invoice_ID = C_Invoice.C_Invoice_ID)";
			int invoiceOri_ID = new Query(invoice.getCtx(), MInvoice.Table_Name, whereClause, invoice.get_TrxName())
								.setParameters(new Object[]{invoice.getC_Invoice_ID()})
								.firstId();
			
			if(invoiceOri_ID == -1)//maybe useless
				throw new AdempiereException("There's no link between this Invoice Tax (Faktur Pajak) and Original Invoice");
			
			HBC_MInvoice oriInvoice = new HBC_MInvoice(invoice.getCtx(), invoiceOri_ID, invoice.get_TrxName());
			if(oriInvoice.getDocStatus().equals(DocAction.STATUS_Completed) && !oriInvoice.getDocAction().equals(DocAction.ACTION_Reverse_Correct))
				throw new AdempiereException("Cannot reverse Invoice Tax(Faktur Pajak) because Invoice Ori still completed.. Please reverse Original Invoice, Document No in description..");
		}
		return "";
	}//end @PhieAlbert

	//@PhieAlbert Before Prepare
	private static String separateTax(HBC_MInvoice invoice) {
		int functionalCurrencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
		//if(invoice.getC_Currency_ID() != functionalCurrencyID && !invoice.get_ValueAsBoolean("IsSeparateTaxInvoice") && invoice.getReversal_ID()==0)
			//throw new AdempiereException("Aborted.. This invoice has to generate invoice tax (invoice faktur pajak)");
		
		if(invoice.get_ValueAsBoolean("IsSeparateTaxInvoice") && invoice.getC_Currency_ID() != functionalCurrencyID && !invoice.isReversal()){
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
		}
		return "";
	}//end @PhieAlbert
	
	//@PhieAlbert After Complete
	private static String createInvoiceTaxFP(HBC_MInvoice invoice) {
		int functionalCurrencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
		System.out.println(invoice.isReversal());
		if(invoice.get_ValueAsBoolean("IsSeparateTaxInvoice") && invoice.getC_Currency_ID() != functionalCurrencyID && invoice.getReversal_ID()==0){
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
				
				StringBuilder sb = new StringBuilder();
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
				
				invoiceFP.processIt(DocAction.ACTION_Complete);
				invoiceFP.saveEx();
			}
		}		
		return "";
	}//end @PhieAlbert
	

	private static String checkAllocation(HBC_MInvoice invoice) {
		String whereClause = "C_Invoice_ID = ? AND cdr.docStatus='CO'";
		boolean match = new Query(invoice.getCtx(), MAllocationLine.Table_Name, whereClause, invoice.get_TrxName())
						.addJoinClause("JOIN C_AllocationHdr cdr ON cdr.c_AllocationHdr_ID = C_AllocationLine.C_AllocationHdr_ID")
						.setParameters(new Object[]{invoice.getC_Invoice_ID()})
						.match();
		
		if(match)
			throw new AdempiereException("There is completed allocation for this invoice..");
		return "";
	}

	/**
	 * execute invoice line event
	 * @param Event, PO
	 * @return error message
	 */
	public static String executeLineEvent(Event event, PO po){
		
		String msg = "";
		/*
		MInvoiceLine invoiceLine = (MInvoiceLine) po;
		
		if(event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE)){
			msg = afterChangeLine(invoiceLine);
		}
		
		return msg;
		
		String msg = "";
		MInvoiceLine line = (MInvoiceLine) po;
		if (event.getTopic().equals(IEventTopics.PO_BEFORE_CHANGE)){
			msg = beforeSave(line);
		}else if (event.getTopic().equals(IEventTopics.PO_BEFORE_NEW)){
			msg = beforeSave(line);
		}
		*/
		return msg;
		
	}
	
	
	//@TommyAng
	/*public static String withholdingHdr(HBC_MInvoice invoice){
		//phie Split invoice summary
		for(MInvoiceLine invoiceLine : invoice.getLines()){
			if (invoiceLine.getC_Tax_ID() > 0 && invoiceLine.getC_Tax().isSummary()){
				if (!invoice.calculateTaxTotal())	//	setTotals
				{
					return "Error calculating Tax";
				}
			}
		}
		
		int precision = invoice.getC_Currency().getStdPrecision();
		for(MInvoiceTax tax: invoice.getTaxes(true)){
			tax.setTaxAmt(tax.getTaxAmt().setScale(precision, RoundingMode.DOWN));
			tax.saveEx();
		}
		invoice.ReCalcGrandTotal();
		//end phie Split invoice summary
		
		//@phie fix reversal invoice that the original invoice use set tax amount process 
		if(invoice.isReversal())
		{
			HBC_MInvoice oriInvoice = new HBC_MInvoice(invoice.getCtx(), invoice.getReversal_ID(), invoice.get_TrxName());
			if(oriInvoice.get_ValueAsBoolean("isUseTaxAmt"))
			{
				invoice.set_ValueOfColumn("isUseTaxAmt", "Y");
				invoice.setWithholdingAmt(oriInvoice.getWithholdingAmt().negate());
				
				MInvoiceTax[] invTaxReversal = invoice.getTaxes(true);
				MInvoiceTax[] invTaxOri = oriInvoice.getTaxes(true);
				
				for(MInvoiceTax invTaxLineOri : invTaxOri)
				{
					for(MInvoiceTax invTaxLineReversal : invTaxReversal)
					{
						if(invTaxLineReversal.getC_Tax_ID() == invTaxLineOri.getC_Tax_ID())
						{
							invTaxLineReversal.setTaxAmt(invTaxLineOri.getTaxAmt().negate());
							invTaxLineReversal.saveEx();
						}	
					}
				}
				invoice.ReCalcGrandTotal();
			}
		}
		//end phie
		
		//@phie
		if(invoice.getReversal_ID() > 0)
		{
			StringBuilder query =  new StringBuilder();
			query.append("UPDATE C_Invoice SET TaxAmt = ")
			.append("case when (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt < 0) is null then 0 ")
			.append("else (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt < 0) end ")
			.append("WHERE C_Invoice_ID = ?");
			
			int n = DB.executeUpdateEx(query.toString(), new Object[] {invoice.get_ID(),invoice.get_ID(),invoice.get_ID()}, invoice.get_TrxName());
			Log.info("UPDATED TaxAmt Reversal Invoice#"+n);
		}
		else
		{
			StringBuilder query =  new StringBuilder();
			query.append("UPDATE C_Invoice SET TaxAmt = ")
			.append("case when (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt > 0) is null then 0 ")
			.append("else (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt > 0) end ")
			.append("WHERE C_Invoice_ID = ?");
			
			int n = DB.executeUpdateEx(query.toString(), new Object[] {invoice.get_ID(),invoice.get_ID(),invoice.get_ID()}, invoice.get_TrxName());
			Log.info("UPDATED TaxAmt Invoice#"+n);
		}
		//end phie
		
		return "";
	}*/
	//end @TommyAng
	
	/*
	public static String beforeSave(MInvoiceLine line){
		String msg = "";
		
		//@TommyAng Rounding PriceEntered & LineNetAmt half up (IDR ,00 || ELSE ,**)
		BigDecimal priceEntered = line.getPriceEntered();
		BigDecimal lineNetAmt = Env.ZERO;
		BigDecimal qty = line.getQtyEntered();
		
		MInvoice invoice = line.getParent();
		
		if(invoice.getC_Currency().getISO_Code().equals("IDR")){
			line.setPriceEntered(priceEntered.setScale(5, RoundingMode.HALF_UP));
			line.setPriceActual(priceEntered.setScale(5, RoundingMode.HALF_UP));
			lineNetAmt = (line.getPriceEntered().multiply(qty)).setScale(0, RoundingMode.HALF_UP);
			line.setLineNetAmt(lineNetAmt);
			line.setTaxAmt(line.getTaxAmt().setScale(0, RoundingMode.DOWN));
			line.setLineTotalAmt(lineNetAmt.add(line.getTaxAmt()));
		}
		else{
			line.setPriceEntered(priceEntered.setScale(5, RoundingMode.HALF_UP));
			line.setPriceActual(priceEntered.setScale(5, RoundingMode.HALF_UP));
			lineNetAmt = (line.getPriceEntered().multiply(qty)).setScale(2, RoundingMode.HALF_UP);
			line.setLineNetAmt(lineNetAmt);
			line.setTaxAmt(line.getTaxAmt().setScale(0, RoundingMode.DOWN));
			line.setLineTotalAmt(lineNetAmt.add(line.getTaxAmt()));
		}
		//end Rounding
		
		return msg;
	}
	*/
	
	/**
	 * after prepare
	 * @param MInvoice
	 * @return empty string
	 */
	private static String afterPrepare(HBC_MInvoice inv){
		
		//HBC_MInvoice inv = (HBC_MInvoice) invoice;
		String sb = HBC_MInvoice.COLUMNNAME_C_Invoice_ID+"="+inv.get_ID();
		BigDecimal sumWithholding = new Query(Env.getCtx(), "LCO_InvoiceWithholding", sb.toString(), inv.get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.sum("TaxAmt");
		
		sumWithholding = sumWithholding.setScale(inv.getPrecision(), RoundingMode.DOWN);
		
		//@win menurut gw sih ini cukup buat gantiin code di bawah. please review
		//@phie review balik
		StringBuilder query =  new StringBuilder();
		query.append("UPDATE C_Invoice SET TaxAmt = (")
		.append("SELECT COALESCE(SUM(TaxAmt),0) FROM C_InvoiceTax cit WHERE C_Invoice_ID=?"
				+ " AND EXISTS ( SELECT C_TaxCategory_ID FROM C_TaxCategory "
				+ " WHERE isWithholding = ? AND C_TaxCategory_ID IN "
				+ " (SELECT C_TaxCategory_ID FROM C_Tax ct WHERE ct.c_tax_id = cit.c_tax_id))) ")
		.append("WHERE C_Invoice_ID = ?");
		
		int n = DB.executeUpdateEx(query.toString(), new Object[] {inv.get_ID(),false,inv.get_ID()}, inv.get_TrxName());
		Log.info("UPDATED TaxAmt Invoice#"+n);
		
		inv.set_ValueOfColumn("OutstandingInv", inv.getTaxAmt().add(inv.getTotalLines()));
		inv.setWithholdingAmt(sumWithholding);
		inv.saveEx();
		
		/*
		if(inv.getReversal_ID() > 0)
		{
			StringBuilder query =  new StringBuilder();
			query.append("UPDATE C_Invoice SET TaxAmt = ")
			.append("case when (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt < 0) is null then 0 ")
			.append("else (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt < 0) end ")
			.append("WHERE C_Invoice_ID = ?");
			
			int n = DB.executeUpdateEx(query.toString(), new Object[] {inv.get_ID(),inv.get_ID(),inv.get_ID()}, inv.get_TrxName());
			Log.info("UPDATED TaxAmt Reversal Invoice#"+n);
		}
		else
		{
			StringBuilder query =  new StringBuilder();
			query.append("UPDATE C_Invoice SET TaxAmt = ")
			.append("case when (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt > 0) is null then 0 ")
			.append("else (SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=? AND TaxAmt > 0) end ")
			.append("WHERE C_Invoice_ID = ?");
			
			int n = DB.executeUpdateEx(query.toString(), new Object[] {inv.get_ID(),inv.get_ID(),inv.get_ID()}, inv.get_TrxName());
			Log.info("UPDATED TaxAmt Invoice#"+n);
		}
		*/
		return "";
	}
	
	/**
	 * after reverse
	 * @param MInvoice
	 * @return empty string
	 */
	private static String afterreverse(MInvoice invoice) {
		String msg="";

		List<HBC_MInvoiceLine> invoicelines= new Query(Env.getCtx(),HBC_MInvoiceLine.Table_Name,"C_Invoice_ID="+invoice.getC_Invoice_ID(),invoice.get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list();
		
		for(HBC_MInvoiceLine invoiceline:invoicelines){
			int referencecode = new Query(Env.getCtx(),X_T_ReferenceCode.Table_Name,"AllocationCode="+invoiceline.get_ValueAsInt("AllocationCode"),invoiceline.get_TrxName())
			.firstId();
			X_T_ReferenceCode refcode= new X_T_ReferenceCode(Env.getCtx(), referencecode, invoiceline.get_TrxName());
			refcode.set_ValueOfColumn("IsInvoiced", false);
			refcode.saveEx();
		}
		
		if(!invoice.isSOTrx()){
			String sqlUpdate = "UPDATE HBC_CostActivity SET Processed=? WHERE HBC_CostActivity_ID IN " +
					"(SELECT HBC_CostActivity_ID FROM M_MatchCostActivity WHERE C_Invoice_ID=?)";
			
			DB.executeUpdate(sqlUpdate, new Object[]{"N",invoice.getC_Invoice_ID()},false, invoice.get_TrxName());
			
			String sqlDelete = "DELETE FROM M_MatchCostActivity WHERE C_Invoice_ID="+invoice.getC_Invoice_ID();
			DB.executeUpdate(sqlDelete, invoice.get_TrxName());
			
			/*//@win commented
			for (MInvoiceLine invoiceLine : invoice.getLines()) {
				
				String where = MInvoiceLine.COLUMNNAME_C_InvoiceLine_ID+"="+invoiceLine.getC_InvoiceLine_ID();
				
				//@TommyAng CostActivity
				int M_MatchCostActivity_ID = new Query(invoice.getCtx(), 
						MMatchCostActivity.Table_Name, where, invoice.get_TrxName())
				.setOnlyActiveRecords(true)
				.firstId();
				
				MMatchCostActivity matchCA = new MMatchCostActivity(invoice.getCtx(), M_MatchCostActivity_ID, invoice.get_TrxName());
				int HBC_CostActivity_ID = matchCA.getHBC_CostActivity_ID();
				
				MCostActivity ca = new MCostActivity(invoice.getCtx(), HBC_CostActivity_ID, invoice.get_TrxName());
				ca.set_ValueOfColumn("IsProcess", false);
				ca.saveEx();
				//end @TommyAng CostActivity
				
			}
			*/
		}else{
			for (MInvoiceLine invoiceLine : invoice.getLines()) {
				
				String where = MInvoiceLine.COLUMNNAME_C_InvoiceLine_ID+"="+invoiceLine.getC_InvoiceLine_ID();

				//@TommyAng ARCalculationLine
				int M_MatchARCalculation_ID = new Query(invoice.getCtx(), 
						MMatchARCalculation.Table_Name, where, invoice.get_TrxName())
				.setOnlyActiveRecords(true)
				.firstId();
								
				MMatchARCalculation matchAR = new MMatchARCalculation(invoice.getCtx(), M_MatchARCalculation_ID, invoice.get_TrxName());
				int ARCalculationLine_ID = matchAR.getHBC_ARCalculationLine_ID();
				
				String sql = "UPDATE HBC_ARCalculationLine SET Processed='N', C_Invoice_ID=null, C_InvoiceLine_ID=null WHERE HBC_ARCalculationLine_ID="+ARCalculationLine_ID;
				String sql1 = "DELETE FROM M_MatchARCalculation WHERE M_MatchARCalculation_ID="+M_MatchARCalculation_ID;
				DB.executeUpdate(sql, invoice.get_TrxName());
				DB.executeUpdate(sql1, invoice.get_TrxName());
				//end @TommyAng ARCalculationLine
			}
		}
		//end @TommyAng
		
		/* comment by @phie
		//@TommyAng
		//TODO: Ini code buat apa? Kayanya ga perlu
		String where = MInvoice.COLUMNNAME_C_Invoice_ID+"="+invoice.getC_Invoice_ID();
		boolean isCheckAR = new Query(invoice.getCtx(), MMatchARCalculation.Table_Name, 
				where, invoice.get_TrxName())
		//.setClient_ID()
		.setOnlyActiveRecords(true)
		.match();
		
		if(!isCheckAR)
			return "";		
		*/

		return msg;
	}
	
	/**
	 * after prepare
	 * @param MInvoice
	 * @return error if fail save ar calculation line
	 */
	private static String afterComplete(MInvoice invoice){		
		//@phie 
		String whereClause = "C_Invoice_ID=? AND IsTrackAsAsset='Y'";
		boolean match = new Query(invoice.getCtx(), MInvoiceLine.Table_Name, whereClause, invoice.get_TrxName())
		.setParameters(invoice.get_ID())
		.setOnlyActiveRecords(true)
		.match();
		
		if (match){
			invoice.setIsTrackAsAsset(true);
			invoice.saveEx();
		}
		//end phie

		/* comment by @phie
		//@win TODO: Ini juga kayanya code ga jelas
		String where = MInvoice.COLUMNNAME_C_Invoice_ID+"="+invoice.getC_Invoice_ID();
		boolean isCheckAR = new Query(invoice.getCtx(), MMatchARCalculation.Table_Name, 
				where, invoice.get_TrxName())
		//.setClient_ID()
		.setOnlyActiveRecords(true)
		.match();
		
		if(!isCheckAR)
			return "";
		//@win
		*/
		
		//@TommyAng
		if(!invoice.isSOTrx()){
			for (MInvoiceLine invoiceLine : invoice.getLines()) {
				
				String where = "HBC_CostActivity_ID IN (SELECT HBC_CostActivity_ID FROM M_MatchCostActivity WHERE C_InvoiceLine_ID=?)";
				
				int HBC_CostActivity_ID = new Query(invoice.getCtx(), 
						MCostActivity.Table_Name, where, invoice.get_TrxName())
				//.setClient_ID()
				.setParameters(invoiceLine.getC_InvoiceLine_ID())
				.setOnlyActiveRecords(true)
				.firstId();
				
				if (HBC_CostActivity_ID > 0) {
					MCostActivity ca = new MCostActivity(invoice.getCtx(), HBC_CostActivity_ID, invoice.get_TrxName());					
					ca.set_ValueOfColumn("IsProcess", true);
					ca.saveEx();
				}
			}
			/*
			for (MInvoiceLine invoiceLine : invoice.getLines()) {
				
				where = MInvoiceLine.COLUMNNAME_C_InvoiceLine_ID+"="+invoiceLine.getC_InvoiceLine_ID();
				int AR_CalculationLine_ID = new Query(invoice.getCtx(), 
						MMatchARCalculation.Table_Name, where, invoice.get_TrxName())
				//.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstId();
				
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT HBC_ARCalculationLine_ID FROM "+MMatchARCalculation.Table_Name)
				.append(" WHERE C_InvoiceLine_ID=?");
				
				int AR_CalculationLine_ID = DB.getSQLValue(invoice.get_TrxName(), sql.toString(), invoiceLine.get_ID());
				
				MARCalculationLine calcLine = new MARCalculationLine(invoice.getCtx(), AR_CalculationLine_ID, invoice.get_TrxName());
				calcLine.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
				calcLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
				calcLine.saveEx();
				
			}*/
			//end @TommyAng
		}
		return "";
	}
	
	/**
	 * after change invoice line
	 * @param MInvoiceLine
	 * @return error message
	 */
	/* comment by @phie unnecessary metho
	private static String afterChangeLine(MInvoiceLine invoiceLine){
		
		//  update tax amount invoice
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE C_Invoice SET TaxAmt = "
				+ "(SELECT SUM(TaxAmt) FROM C_InvoiceTax WHERE C_Invoice_ID=?) "
				+ "WHERE C_Invoice_ID="+invoiceLine.getC_Invoice_ID());
		int no = DB.executeUpdate(sb.toString(), invoiceLine.getC_Invoice_ID(), invoiceLine.get_TrxName());
		Log.info("UPDATED TaxAmt Invoice#"+no);
		
		//  update tax base amount invoice withholding
		sb = new StringBuilder();
		sb.append("UPDATE LCO_InvoiceWithholding SET TaxBaseAmt = "+invoiceLine.getPriceEntered()
				+" WHERE C_Invoice_ID = ?");
		no = DB.executeUpdate(sb.toString(), invoiceLine.getC_Invoice_ID(), invoiceLine.get_TrxName());
		Log.info("UPDATED TaxBaseAmt InvoiceWithholding#"+no);
		
		//  update tax amount invoice withholding
		sb = new StringBuilder();
		sb.append("UPDATE LCO_InvoiceWithholding SET TaxAmt = ROUND(TaxBaseAmt*Percent/100,2) "
				+ "WHERE C_Invoice_ID=?");
		no = DB.executeUpdate(sb.toString(), invoiceLine.getC_Invoice_ID(), invoiceLine.get_TrxName());
		Log.info("UPDATED TaxAmt InvoiceWithholding#"+no);
		
		//  update withholding amount invoice
		sb = new StringBuilder();
		sb.append("UPDATE C_Invoice SET WithholdingAmt = "
				+ "(SELECT SUM(TaxAmt) FROM LCO_InvoiceWithholding WHERE C_Invoice_ID=?) "
				+ "WHERE C_Invoice_ID="+invoiceLine.getC_Invoice_ID());
		no = DB.executeUpdate(sb.toString(), invoiceLine.getC_Invoice_ID(), invoiceLine.get_TrxName());
		Log.info("UPDATED WithholdingAmt Invoice#"+no);
		
		//  update withholding invoice
		sb = new StringBuilder();
		sb.append("UPDATE C_Invoice SET OutstandingInv = TotalLines+TaxAmt WHERE C_Invoice_ID=?");
		no = DB.executeUpdate(sb.toString(), invoiceLine.getC_Invoice_ID(), invoiceLine.get_TrxName());
		Log.info("UPDATED OutstandingInv Invoice#"+no);
		
		return "";
	}
	*/
	
	//@TommyAng
	private static String docking(MInvoice invoice){
		
		if (invoice.getC_Project_ID() > 0) {
			MProject project = new MProject(invoice.getCtx(), invoice.getC_Project_ID(), invoice.get_TrxName());
			
			if(project.get_ValueAsInt("C_Invoice_ID")>0 && (project.get_Value("ProjectCategory").equals("DOC") || project.get_Value("ProjectCategory").equals("M"))){
				project.set_ValueOfColumn("C_Invoice_ID", null);
				project.saveEx();
			}
		}
		
		return "";
	}
	
	private static String shipDocumentDock(MInvoice invoice){
		
		String where = "C_Invoice_ID="+invoice.get_ID();
		int[] ids = new Query(invoice.getCtx(), MShipDocumentDock.Table_Name, where, invoice.get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			//.setOrderBy("Line")
			.getIDs();
		
		if(ids.length>0)
			for(int dockID : ids){
				MShipDocumentDock dock = new MShipDocumentDock(invoice.getCtx(), dockID, invoice.get_TrxName());
				dock.set_ValueOfColumn("C_Invoice_ID", null);
				dock.set_ValueOfColumn("C_InvoiceLine_ID", null);
				dock.set_ValueOfColumn("isInvoiced", false);
				dock.saveEx();
			}
		
		return "";
	}
	//end @TommyAng
	
	//@Phie hbc 2514 check when complete invoice, mr must be complete (bisa aja mr nya di reverse)
	private static String checkMR(MInvoice invoice)
	{		
		StringBuilder whereClause = new StringBuilder().append("EXISTS (SELECT 1 FROM M_InoutLine mil ")
				.append("JOIN C_InvoiceLine cil ON cil.M_InOutLine_ID=mil.M_InOutLine_ID ")
				.append("WHERE mil.M_InOut_ID=M_InOut.M_InOut_ID AND cil.C_Invoice_ID=?) ")
				.append("AND DocStatus!='CO'");

		boolean match = new Query(invoice.getCtx(), MInOut.Table_Name, whereClause.toString(), invoice.get_TrxName())
		.setParameters(invoice.get_ID())
		.setOnlyActiveRecords(true)
		.match();
		
		if (match)
			throw new IllegalArgumentException("Material Receipt is ");
		
		/*
		MInvoiceLine[] invLine = invoice.getLines();
		for(MInvoiceLine sLine: invLine)
		{
			if (sLine.getM_InOutLine_ID() > 0)

			if (MInOut.DOCSTATUS_Reversed.equals(sLine.getM_InOutLine().getM_InOut().getDocStatus()))
				throw new IllegalArgumentException("Material Receipt is Reversed");
		}
		*/
		return "";
	}//end @phie
}
