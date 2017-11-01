package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MConversionRateUtil;
import org.compiere.model.MDocType;
import org.compiere.model.MDocTypeCounter;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.model.MMatchInv;
import org.compiere.model.MMatchPO;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentProcessor;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPaymentTransaction;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

public class HBC_MInvoice extends MInvoice{

	public HBC_MInvoice(MInOut ship, Timestamp invoiceDate) {
		super(ship, invoiceDate);
	}

	public HBC_MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
	}
	
	public HBC_MInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9060308397137042992L;

	protected boolean beforeSave (boolean newRecord)
	{
		boolean isBeforeSave = super.beforeSave(newRecord);
		if(!isBeforeSave)
			return false;
		
		// custom habco start here
		if (newRecord){
			if(isSOTrx()==true && !get_ValueAsBoolean("isTaxInvoice")){
				setDocumentNo(getDocumentNoRomanFormat());
				//@TommyAng
				set_CustomColumn("OrderReference", "XXXX"+getOrderReferenceRomanFormat());
			}
		}
		//@TommyAng
		String orderReference = get_ValueAsString("OrderReference");
		String whereClause = "OrderReference='"+orderReference+"' and IsActive='Y' "
				+ "and isSoTrx='N' and C_Invoice_ID!="+get_ID();
		if(!orderReference.equals(null)){
			int invoiceID = new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
			.setOnlyActiveRecords(true)
			.setClient_ID()
			.firstId();
			
			if(invoiceID>0){
				MInvoice inv = new MInvoice(getCtx(), invoiceID, get_TrxName());
				String documentNo = inv.getDocumentNo();
				log.saveError("Error", Msg.getMsg(getCtx(), "Order Reference Has Been Used By Another Invoice : "+documentNo));
				return false;
			}
		}
		//end @TommyAng
		
		System.out.println(isReversal());
		//@PhieAlbert
		if((newRecord || is_ValueChanged("C_Currency_ID") || is_ValueChanged("M_PriceList_ID")) && !isReversal()){
			int functionalCurrencyID = Integer.valueOf(Env.getContext(Env.getCtx(), "$C_Currency_ID"));
			if(getC_Currency_ID() != functionalCurrencyID)
				set_ValueOfColumn("IsSeparateTaxInvoice", true);
			else
				set_ValueOfColumn("IsSeparateTaxInvoice", false);
		}
		//end //@PhieAlbert
		
		return true;
	}
	
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		for (int i = 0; i < options.length; i++) {
			options[i] = null;
		}

		index = 0;

		if (docStatus.equals(DocAction.STATUS_Drafted)) {
			options[index++] = DocAction.ACTION_Prepare;
			//options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_InProgress)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_Completed)) {
			options[index++] = DocAction.ACTION_Reverse_Accrual;
			options[index++] = DocAction.ACTION_Reverse_Correct;
		} else if (docStatus.equals(DocAction.STATUS_Invalid)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		}

		return index;
	}
	
	/** 
	 * set document no for invoice AR
	 */
	public String getDocumentNoRomanFormat(){
	
		String DocumentNo="";
		MDocType doctype=new MDocType(getCtx(),getC_DocTypeTarget_ID(),get_TrxName());
		MSequence sequence = new MSequence(getCtx(),doctype.getDocNoSequence_ID(),get_TrxName());
		DocumentNo=sequence.getCurrentNext()+getOrderReferenceRomanFormat();
		sequence.setCurrentNext(sequence.getCurrentNext()+1);
		sequence.saveEx();
		return DocumentNo;
	}
	
	/** 
	 * set order reference for invoice AR
	 */
	public String getOrderReferenceRomanFormat(){
		String OrderReference="";
		String romanmonth=RomanNumber(getMonthDate(getDateInvoiced()));
		String yeardate = String.valueOf(getYearDate(getDateInvoiced()));
		String year =Character.toString(yeardate.charAt(2))+Character.toString(yeardate.charAt(3));
		MDocType doctype=new MDocType(getCtx(),getC_DocTypeTarget_ID(),get_TrxName());
		OrderReference=doctype.get_ValueAsString("TypeDoc")+romanmonth+"/"+year;		
		return OrderReference;
	}
	
	protected int getYearDate(Timestamp Date){
		int year =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		year= calender.get(Calendar.YEAR);
		return year;
	}

	/**
	 * get roman number format
	 */
	public static String RomanNumber(int input) {
		if (input < 1 || input > 3999)
			return "Invalid Roman Number Value";
		String s = "";
		while (input >= 1000) {
			s += "M";
			input -= 1000;        }
		while (input >= 900) {
			s += "CM";
			input -= 900;
		}
		while (input >= 500) {
			s += "D";
			input -= 500;
		}
		while (input >= 400) {
			s += "CD";
			input -= 400;
		}
		while (input >= 100) {
			s += "C";
			input -= 100;
		}
		while (input >= 90) {
			s += "XC";
			input -= 90;
		}
		while (input >= 50) {
			s += "L";
			input -= 50;
		}
		while (input >= 40) {
			s += "XL";
			input -= 40;
		}
		while (input >= 10) {
			s += "X";
			input -= 10;
		}
		while (input >= 9) {
			s += "IX";
			input -= 9;
		}
		while (input >= 5) {
			s += "V";
			input -= 5;
		}
		while (input >= 4) {
			s += "IV";
			input -= 4;
		}
		while (input >= 1) {
			s += "I";
			input -= 1;
		}    
		return s;
	}
	
	protected int getMonthDate(Timestamp Date){
		int month =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		month = calender.get(Calendar.MONTH)+1;
		return month;
	}
	
	/**	Invoice Lines			*/
	private MInvoiceLine[]	m_lines;
	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid)
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocTypeTarget_ID(), getAD_Org_ID());

		//	Lines
		MInvoiceLine[] lines = getLines(true);
		if (lines.length == 0 && !get_ValueAsBoolean("IsDownPaymentInvoice") )
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		//	No Cash Book // deprecated with IDEMPIERE-170 Complete Cash as Payment functionality 
//		if (PAYMENTRULE_Cash.equals(getPaymentRule())
//			&& MCashBook.get(getCtx(), getAD_Org_ID(), getC_Currency_ID()) == null)
//		{
//			m_processMsg = "@NoCashBook@";
//			return DocAction.STATUS_Invalid;
//		}

		//	Convert/Check DocType
		if (getC_DocType_ID() != getC_DocTypeTarget_ID() )
			setC_DocType_ID(getC_DocTypeTarget_ID());
		if (getC_DocType_ID() == 0)
		{
			m_processMsg = "No Document Type";
			return DocAction.STATUS_Invalid;
		}

		explodeBOM();
		if (!calculateTaxTotal())	//	setTotals
		{
			m_processMsg = "Error calculating Tax";
			return DocAction.STATUS_Invalid;
		}

		if (   getGrandTotal().signum() != 0
			&& (PAYMENTRULE_OnCredit.equals(getPaymentRule()) || PAYMENTRULE_DirectDebit.equals(getPaymentRule())))
		{
			if (!createPaySchedule())
			{
				m_processMsg = "@ErrorPaymentSchedule@";
				return DocAction.STATUS_Invalid;
			}
		} else {
			if (MInvoicePaySchedule.getInvoicePaySchedule(getCtx(), getC_Invoice_ID(), 0, get_TrxName()).length > 0) 
			{
				m_processMsg = "@ErrorPaymentSchedule@";
				return DocAction.STATUS_Invalid;
			}
		}

		//	Credit Status
		if (isSOTrx())
		{
			MDocType doc = (MDocType) getC_DocTypeTarget();
			// IDEMPIERE-365 - just check credit if is going to increase the debt
			if ( (doc.getDocBaseType().equals(MDocType.DOCBASETYPE_ARCreditMemo) && getGrandTotal().signum() < 0 ) ||
				(doc.getDocBaseType().equals(MDocType.DOCBASETYPE_ARInvoice) && getGrandTotal().signum() > 0 )
			   )
			{	
				MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), null);
				if ( MBPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus()) )
				{
					m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@="
							+ bp.getTotalOpenBalance()
							+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
			}  
		}

		//	Landed Costs
		if (!isSOTrx() && !get_ValueAsBoolean("IsDownPaymentInvoice"))
		{
			for (int i = 0; i < lines.length; i++)
			{
				MInvoiceLine line = lines[i];
				String error = line.allocateLandedCosts();
				if (error != null && error.length() > 0)
				{
					m_processMsg = error;
					return DocAction.STATUS_Invalid;
				}
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Add up Amounts
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		/*
		 *	@stephan
		 *	TAOWI-897 check tax in invoice line must be same with tax header
		 */
		/*//@win, handle at beforeSave 
		if(getC_Tax_ID() > 0){
			for (MInvoiceLine invoiceLine : getLines()) {
				if(getC_Tax_ID() != invoiceLine.getC_Tax_ID()){
					invoiceLine.setC_Tax_ID(getC_Tax_ID());
					invoiceLine.saveEx();
				}
			}
		}
		*/
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		StringBuilder info = new StringBuilder();
		
		// POS supports multiple payments
		boolean fromPOS = false;
		if ( getC_Order_ID() > 0 )
		{
			fromPOS = getC_Order().getC_POS_ID() > 0;
		}

  		//	Create Cash Payment
		if (PAYMENTRULE_Cash.equals(getPaymentRule()) && !fromPOS )
		{
			String whereClause = "AD_Org_ID=? AND C_Currency_ID=?";
			MBankAccount ba = new Query(getCtx(),MBankAccount.Table_Name,whereClause,get_TrxName())
				.setParameters(getAD_Org_ID(), getC_Currency_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("IsDefault DESC")
				.first();
			if (ba == null) {
				m_processMsg = "@NoAccountOrgCurrency@";
				return DocAction.STATUS_Invalid;
			}
			
			String docBaseType = "";
			if (isSOTrx())
				docBaseType=MDocType.DOCBASETYPE_ARReceipt;
			else
				docBaseType=MDocType.DOCBASETYPE_APPayment;
			
			MDocType[] doctypes = MDocType.getOfDocBaseType(getCtx(), docBaseType);
			if (doctypes == null || doctypes.length == 0) {
				m_processMsg = "No document type ";
				return DocAction.STATUS_Invalid;
			}
			MDocType doctype = null;
			for (MDocType doc : doctypes) {
				if (doc.getAD_Org_ID() == this.getAD_Org_ID()) {
					doctype = doc;
					break;
				}
			}
			if (doctype == null)
				doctype = doctypes[0];

			MPayment payment = new MPayment(getCtx(), 0, get_TrxName());
			payment.setAD_Org_ID(getAD_Org_ID());
			if(getAD_OrgTrx_ID()>0){
			payment.setAD_OrgTrx_ID(getAD_OrgTrx_ID());
			}
			payment.setTenderType(MPayment.TENDERTYPE_Cash);
			payment.setC_BankAccount_ID(ba.getC_BankAccount_ID());
			payment.setC_BPartner_ID(getC_BPartner_ID());
			payment.setC_Invoice_ID(getC_Invoice_ID());
			payment.setC_Currency_ID(getC_Currency_ID());			
			payment.setC_DocType_ID(doctype.getC_DocType_ID());
			payment.setPayAmt(getGrandTotal());
			payment.setIsPrepayment(false);					
			payment.setDateAcct(getDateAcct());
			payment.setDateTrx(getDateInvoiced());

			//	Save payment
			payment.saveEx();

			payment.setDocAction(MPayment.DOCACTION_Complete);
			if (!payment.processIt(MPayment.DOCACTION_Complete)) {
				m_processMsg = "Cannot Complete the Payment : [" + payment.getProcessMsg() + "] " + payment;
				return DocAction.STATUS_Invalid;
			}

			payment.saveEx();
			info.append("@C_Payment_ID@: " + payment.getDocumentInfo());
			
			// IDEMPIERE-2588 - add the allocation generation with the payment
			if (payment.getJustCreatedAllocInv() != null)
				addDocsPostProcess(payment.getJustCreatedAllocInv());
		}	//	Payment

		//	Update Order & Match
		if (!get_ValueAsBoolean("IsDownPaymentInvoice")) {
			int matchInv = 0;
			int matchPO = 0;
			MInvoiceLine[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				MInvoiceLine line = lines[i];
	
				//	Matching - Inv-Shipment
				//	@Stephan TAOWI-1060 remove issotrx validation
				//if (!isSOTrx()
				if ( line.getM_InOutLine_ID() != 0
					&& line.getM_Product_ID() != 0
					&& !isReversal())
				{
					MInOutLine receiptLine = new MInOutLine (getCtx(),line.getM_InOutLine_ID(), get_TrxName());
					BigDecimal matchQty = line.getQtyInvoiced();
	
					if (receiptLine.getMovementQty().compareTo(matchQty) < 0)
						matchQty = receiptLine.getMovementQty();
	
					MDocType docType = new MDocType(getCtx(), getDocTypeID(), get_TrxName());
					MMatchInv inv = new MMatchInv(line, getDateInvoiced(), matchQty, docType.getDocBaseType());
					if (!inv.save(get_TrxName()))
					{
						m_processMsg = CLogger.retrieveErrorString("Could not create Invoice Matching");
						return DocAction.STATUS_Invalid;
					}
					matchInv++;
					addDocsPostProcess(inv);
				}
						
				//	Update Order Line
				MOrderLine ol = null;
				if (line.getC_OrderLine_ID() != 0)
				{
					if (isSOTrx()
						|| line.getM_Product_ID() == 0)
					{
						ol = new MOrderLine (getCtx(), line.getC_OrderLine_ID(), get_TrxName());
						if (line.getQtyInvoiced() != null)
							ol.setQtyInvoiced(ol.getQtyInvoiced().add(line.getQtyInvoiced()));
						if (!ol.save(get_TrxName()))
						{
							m_processMsg = "Could not update Order Line";
							return DocAction.STATUS_Invalid;
						}
					}
					//	Order Invoiced Qty updated via Matching Inv-PO
					else if (!isSOTrx()
						&& line.getM_Product_ID() != 0
						&& !isReversal())
					{
						//	MatchPO is created also from MInOut when Invoice exists before Shipment
						BigDecimal matchQty = line.getQtyInvoiced();
						MMatchPO po = MMatchPO.create (line, null,
							getDateInvoiced(), matchQty);
						if (po != null) 
						{
							if (!po.save(get_TrxName()))
							{
								m_processMsg = "Could not create PO Matching";
								return DocAction.STATUS_Invalid;
							}
							matchPO++;
							if (!po.isPosted() && po.getM_InOutLine_ID() > 0) // match po don't post if receipt is not assigned, and it doesn't create avg po record
								addDocsPostProcess(po);
							
							MMatchInv[] matchInvoices = MMatchInv.getInvoiceLine(getCtx(), line.getC_InvoiceLine_ID(), get_TrxName());
							if (matchInvoices != null && matchInvoices.length > 0) 
							{
								for(MMatchInv matchInvoice : matchInvoices)
								{
									if (!matchInvoice.isPosted())
									{
										addDocsPostProcess(matchInvoice);
									}
								}
							}
						}
					}
				}
	
				//Update QtyInvoiced RMA Line
				if (line.getM_RMALine_ID() != 0)
				{
					MRMALine rmaLine = new MRMALine (getCtx(),line.getM_RMALine_ID(), get_TrxName());
					if (rmaLine.getQtyInvoiced() != null)
						rmaLine.setQtyInvoiced(rmaLine.getQtyInvoiced().add(line.getQtyInvoiced()));
					else
						rmaLine.setQtyInvoiced(line.getQtyInvoiced());
					if (!rmaLine.save(get_TrxName()))
					{
						m_processMsg = "Could not update RMA Line";
						return DocAction.STATUS_Invalid;
					}
				}
				//			
			}	//	for all lines
			if (matchInv > 0)
				info.append(" @M_MatchInv_ID@#").append(matchInv).append(" ");
			if (matchPO > 0)
				info.append(" @M_MatchPO_ID@#").append(matchPO).append(" ");
		}


		//	Update BP Statistics
		MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
		DB.getDatabase().forUpdate(bp, 0);
		//	Update total revenue and balance / credit limit (reversed on AllocationLine.processIt)
		BigDecimal invAmt = MConversionRate.convertBase(getCtx(), getGrandTotal(true),	//	CM adjusted
			getC_Currency_ID(), getDateAcct(), getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
		if (invAmt == null)
		{
			m_processMsg = MConversionRateUtil.getErrorMessage(getCtx(), "ErrorConvertingCurrencyToBaseCurrency",
					getC_Currency_ID(), MClient.get(getCtx()).getC_Currency_ID(), getC_ConversionType_ID(), getDateAcct(), get_TrxName());
			return DocAction.STATUS_Invalid;
		}
		//	Total Balance
		BigDecimal newBalance = bp.getTotalOpenBalance();
		if (newBalance == null)
			newBalance = Env.ZERO;
		if (isSOTrx())
		{
			newBalance = newBalance.add(invAmt);
			//
			if (bp.getFirstSale() == null)
				bp.setFirstSale(getDateInvoiced());
			BigDecimal newLifeAmt = bp.getActualLifeTimeValue();
			if (newLifeAmt == null)
				newLifeAmt = invAmt;
			else
				newLifeAmt = newLifeAmt.add(invAmt);
			BigDecimal newCreditAmt = bp.getSO_CreditUsed();
			if (newCreditAmt == null)
				newCreditAmt = invAmt;
			else
				newCreditAmt = newCreditAmt.add(invAmt);
			//
			if (log.isLoggable(Level.FINE)) log.fine("GrandTotal=" + getGrandTotal(true) + "(" + invAmt
				+ ") BP Life=" + bp.getActualLifeTimeValue() + "->" + newLifeAmt
				+ ", Credit=" + bp.getSO_CreditUsed() + "->" + newCreditAmt
				+ ", Balance=" + bp.getTotalOpenBalance() + " -> " + newBalance);
			bp.setActualLifeTimeValue(newLifeAmt);
			bp.setSO_CreditUsed(newCreditAmt);
		}	//	SO
		else
		{
			newBalance = newBalance.subtract(invAmt);
			if (log.isLoggable(Level.FINE)) log.fine("GrandTotal=" + getGrandTotal(true) + "(" + invAmt
				+ ") Balance=" + bp.getTotalOpenBalance() + " -> " + newBalance);
		}
		bp.setTotalOpenBalance(newBalance);
		bp.setSOCreditStatus();
		if (!bp.save(get_TrxName()))
		{
			m_processMsg = "Could not update Business Partner";
			return DocAction.STATUS_Invalid;
		}

		//	User - Last Result/Contact
		if (getAD_User_ID() != 0)
		{
			MUser user = new MUser (getCtx(), getAD_User_ID(), get_TrxName());
			user.setLastContact(new Timestamp(System.currentTimeMillis()));
			StringBuilder msgset = new StringBuilder().append(Msg.translate(getCtx(), "C_Invoice_ID")).append(": ").append(getDocumentNo());
			user.setLastResult(msgset.toString());
			if (!user.save(get_TrxName()))
			{
				m_processMsg = "Could not update Business Partner User";
				return DocAction.STATUS_Invalid;
			}
		}	//	user

		//	Update Project
		if (isSOTrx() && getC_Project_ID() != 0)
		{
			MProject project = new MProject (getCtx(), getC_Project_ID(), get_TrxName());
			BigDecimal amt = getGrandTotal(true);
			int C_CurrencyTo_ID = project.getC_Currency_ID();
			if (C_CurrencyTo_ID != getC_Currency_ID())
				amt = MConversionRate.convert(getCtx(), amt, getC_Currency_ID(), C_CurrencyTo_ID,
					getDateAcct(), 0, getAD_Client_ID(), getAD_Org_ID());
			if (amt == null)
			{
				m_processMsg = MConversionRateUtil.getErrorMessage(getCtx(), "ErrorConvertingCurrencyToProjectCurrency",
						getC_Currency_ID(), C_CurrencyTo_ID, 0, getDateAcct(), get_TrxName());
				return DocAction.STATUS_Invalid;
			}
			BigDecimal newAmt = project.getInvoicedAmt();
			if (newAmt == null)
				newAmt = amt;
			else
				newAmt = newAmt.add(amt);
			if (log.isLoggable(Level.FINE)) log.fine("GrandTotal=" + getGrandTotal(true) + "(" + amt
				+ ") Project " + project.getName()
				+ " - Invoiced=" + project.getInvoicedAmt() + "->" + newAmt);
			project.setInvoicedAmt(newAmt);
			if (!project.save(get_TrxName()))
			{
				m_processMsg = "Could not update Project";
				return DocAction.STATUS_Invalid;
			}
		}	//	project
		
		// auto delay capture authorization payment
		if (isSOTrx() && !isReversal())
		{
			StringBuilder whereClause = new StringBuilder();
			whereClause.append("C_Order_ID IN (");
			whereClause.append("SELECT C_Order_ID ");
			whereClause.append("FROM C_OrderLine ");
			whereClause.append("WHERE C_OrderLine_ID IN (");
			whereClause.append("SELECT C_OrderLine_ID ");
			whereClause.append("FROM C_InvoiceLine ");
			whereClause.append("WHERE C_Invoice_ID = ");
			whereClause.append(getC_Invoice_ID()).append("))");
			int[] orderIDList = MOrder.getAllIDs(MOrder.Table_Name, whereClause.toString(), get_TrxName());
			
			int[] ids = MPaymentTransaction.getAuthorizationPaymentTransactionIDs(orderIDList, getC_Invoice_ID(), get_TrxName());			
			if (ids.length > 0)
			{
				boolean pureCIM = true;
				ArrayList<MPaymentTransaction> ptList = new ArrayList<MPaymentTransaction>();
				BigDecimal totalPayAmt = BigDecimal.ZERO;
				for (int id : ids)
				{
					MPaymentTransaction pt = new MPaymentTransaction(getCtx(), id, get_TrxName());
					
					if (!pt.setPaymentProcessor())
					{
						if (pt.getC_PaymentProcessor_ID() > 0)
						{
							MPaymentProcessor pp = new MPaymentProcessor(getCtx(), pt.getC_PaymentProcessor_ID(), get_TrxName());
							m_processMsg = Msg.getMsg(getCtx(), "PaymentNoProcessorModel") + ": " + pp.toString();
						}
						else
							m_processMsg = Msg.getMsg(getCtx(), "PaymentNoProcessorModel");
						return DocAction.STATUS_Invalid;
					}
					
					boolean isCIM = pt.getC_PaymentProcessor_ID() > 0 && pt.getCustomerPaymentProfileID() != null && pt.getCustomerPaymentProfileID().length() > 0;
					if (pureCIM && !isCIM)
						pureCIM = false;
					
					totalPayAmt = totalPayAmt.add(pt.getPayAmt());
					ptList.add(pt);
				}
				
				// automatically void authorization payment and create a new sales payment when invoiced amount is NOT equals to the authorized amount (applied to CIM payment processor)
				if (getGrandTotal().compareTo(totalPayAmt) != 0 && ptList.size() > 0 && pureCIM)
				{
					// create a new sales payment
					MPaymentTransaction newSalesPT = MPaymentTransaction.copyFrom(ptList.get(0), new Timestamp(System.currentTimeMillis()), MPayment.TRXTYPE_Sales, "", get_TrxName());
					newSalesPT.setIsApproved(false);
					newSalesPT.setIsVoided(false);
					newSalesPT.setIsDelayedCapture(false);
					newSalesPT.setDescription("InvoicedAmt: " + getGrandTotal() + " <> TotalAuthorizedAmt: " + totalPayAmt);
					newSalesPT.setC_Invoice_ID(getC_Invoice_ID());
					newSalesPT.setPayAmt(getGrandTotal());
					
					// void authorization payment
					for (MPaymentTransaction pt : ptList)
					{
						pt.setDescription("InvoicedAmt: " + getGrandTotal() + " <> AuthorizedAmt: " + pt.getPayAmt());
						boolean ok = pt.voidOnlineAuthorizationPaymentTransaction();
						pt.saveEx();
						if (!ok)
						{
							m_processMsg = Msg.getMsg(getCtx(), "VoidAuthorizationPaymentFailed") + ": " + pt.getErrorMessage();
							return DocAction.STATUS_Invalid;
						}					
					}
					
					// process the new sales payment
					boolean ok = newSalesPT.processOnline();
					newSalesPT.saveEx();
					if (!ok)
					{
						m_processMsg = Msg.getMsg(getCtx(), "CreateNewSalesPaymentFailed") + ": " + newSalesPT.getErrorMessage();
						return DocAction.STATUS_Invalid;
					}
				}
				else if (getGrandTotal().compareTo(totalPayAmt) != 0 && ptList.size() > 0)
				{
					m_processMsg = "InvoicedAmt: " + getGrandTotal() + " <> AuthorizedAmt: " + totalPayAmt;
					return DocAction.STATUS_Invalid;
				}
				else
				{
					// delay capture authorization payment
					for (MPaymentTransaction pt : ptList)
					{
						boolean ok = pt.delayCaptureOnlineAuthorizationPaymentTransaction(getC_Invoice_ID());
						pt.saveEx();
						if (!ok)
						{
							m_processMsg = Msg.getMsg(getCtx(), "DelayCaptureAuthFailed") + ": " + pt.getErrorMessage();
							return DocAction.STATUS_Invalid;
						}					
					}
				}
			}
		}

		if (PAYMENTRULE_Cash.equals(getPaymentRule())) {
			if (testAllocation(true)) {
				saveEx();
			}
		}
		
		//	@stephan
		//	set inoutline isinvoiced true if qty in receipt equals with sum invoice qty
		MInvoiceLine invLines[] = getLines();
		for (MInvoiceLine invLine : invLines) {
			if (invLine.getM_InOutLine_ID() > 0) {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT COALESCE(SUM(QtyEntered),0) FROM C_InvoiceLine line "
						+ "LEFT JOIN C_Invoice head on head.C_Invoice_ID=line.C_Invoice_ID "
						+ "WHERE head.DocStatus='CO' AND M_InOutLine_ID=?");
				BigDecimal sumQty = DB.getSQLValueBD(get_TrxName(),
						sql.toString(), invLine.getM_InOutLine_ID());
				sumQty = sumQty.add(invLine.getQtyEntered());
				MInOutLine inoutLine = new MInOutLine(getCtx(),
						invLine.getM_InOutLine_ID(), get_TrxName());
				if (sumQty.compareTo(inoutLine.getQtyEntered()) == 0) {
					inoutLine.setIsInvoiced(true);
					inoutLine.saveEx();
				}
			}
		}
		//	end
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		//@PhieAlbert RESET GRAND TOTAL VALAS WITHOUT PPN
		if(get_ValueAsBoolean("IsSeparateTaxInvoice"))
		{
			setGrandTotal(getTotalLines().subtract(getWithholdingAmt()));
			set_ValueOfColumn("outstandinginv", getTotalLines());	
		}
		//end RESET GRAND TOTAL VALAS WITHOUT PPN
		
		
		//	Counter Documents
		MInvoice counter = createCounterDoc();
		if (counter != null)
			info.append(" - @CounterDoc@: @C_Invoice_ID@=").append(counter.getDocumentNo());

		m_processMsg = info.toString().trim();
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		if (isReversal() && ! MSysConfig.getBooleanValue(MSysConfig.Invoice_ReverseUseNewNumber, true, getAD_Client_ID())) // IDEMPIERE-1771
			return;
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateInvoiced(new Timestamp (System.currentTimeMillis()));
			if (getDateAcct().before(getDateInvoiced())) {
				setDateAcct(getDateInvoiced());
				MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
			}
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}
	
	/** Reversal Flag		*/
	private boolean m_reversal = false;
	
	/**
	 * 	Set Reversal
	 *	@param reversal reversal
	 */
	private void setReversal(boolean reversal)
	{
		m_reversal = reversal;
	}	//	setReversal
	
	/**
	 * 	Is Reversal
	 *	@return reversal
	 */
	public boolean isReversal()
	{
		return m_reversal;
	}	//	isReversal
	
	/**
	 * 	Create Counter Document
	 * 	@return counter invoice
	 */
	private MInvoice createCounterDoc()
	{
		//	Is this a counter doc ?
		if (getRef_Invoice_ID() != 0)
			return null;

		//	Org Must be linked to BPartner
		MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
			return null;
		//	Business Partner needs to be linked to Org
		MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), null);
		int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
			return null;

		MBPartner counterBP = new MBPartner (getCtx(), counterC_BPartner_ID, null);
//		MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);
		if (log.isLoggable(Level.INFO)) log.info("Counter BP=" + counterBP.getName());

		//	Document Type
		int C_DocTypeTarget_ID = 0;
		MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			if (log.isLoggable(Level.FINE)) log.fine(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
				return null;
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else	//	indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			if (log.isLoggable(Level.FINE)) log.fine("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
				return null;
		}

		//	Deep Copy
		MInvoice counter = copyFrom(this, getDateInvoiced(), getDateAcct(),
			C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName(), true);
		//
		counter.setAD_Org_ID(counterAD_Org_ID);
	//	counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
//		counter.setBPartner(counterBP);// was set on copyFrom
		//	References (Should not be required)
		counter.setSalesRep_ID(getSalesRep_ID());
		counter.saveEx(get_TrxName());

		//	Update copied lines
		if (!get_ValueAsBoolean("IsDownPaymentInvoice")) {
			MInvoiceLine[] counterLines = counter.getLines(true);
			for (int i = 0; i < counterLines.length; i++)
			{
				MInvoiceLine counterLine = counterLines[i];
				//counterLine.setClientOrg(counter); @PhieAlbert comment
				counterLine.setAD_Org_ID(counter.getAD_Org_ID()); //@PhieAlbert
				counterLine.setInvoice(counter);	//	copies header values (BP, etc.)
				counterLine.setPrice();
				counterLine.setTax();
				//
				counterLine.saveEx(get_TrxName());
			}
		}

		if (log.isLoggable(Level.FINE)) log.fine(counter.toString());

		//	Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				// added AdempiereException by zuhri
				if (!counter.processIt(counterDT.getDocAction()))
					throw new AdempiereException("Failed when processing document - " + counter.getProcessMsg());
				// end added
				counter.saveEx(get_TrxName());
			}
		}
		return counter;
	}	//	createCounterDoc
	
	/* Save array of documents to process AFTER completing this one */
	ArrayList<PO> docsPostProcess = new ArrayList<PO>();

	private void addDocsPostProcess(PO doc) {
		docsPostProcess.add(doc);
	}
	
	/**
	 * 	Reverse Correction - same date
	 * 	@return true if success
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		MInvoice reversal = reverse(false);
		if (reversal == null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		
		return true;
	}	//	reverseCorrectIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return false
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		MInvoice reversal = reverse(true);
		if (reversal == null)
			return false;
		
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		
		return true;
	}	//	reverseAccrualIt
	
	private MInvoice reverse(boolean accrual) {
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getDateAcct();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
		Timestamp reversalDateInvoiced = accrual ? reversalDate : getDateInvoiced();
		
		MPeriod.testPeriodOpen(getCtx(), reversalDate, getC_DocType_ID(), getAD_Org_ID());
		//
		reverseAllocations(accrual, getC_Invoice_ID());
		
		//	Reverse/Delete Matching
		//	@Stephan TAOWI-1060 remove issotrx validation
		//if (!isSOTrx() && !isDownPaymentInvoice())
		if (!isDownPaymentInvoice())
		{
			MMatchInv[] mInv = MMatchInv.getInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			for (int i = 0; i < mInv.length; i++)
			{
				if (mInv[i].getReversal_ID() > 0)
					continue;
				
				if (!mInv[i].reverse(reversalDate)) 
				{
					m_processMsg = "Could not Reverse MatchInv";
					return null;
				}
				addDocsPostProcess(new MMatchInv(Env.getCtx(), mInv[i].getReversal_ID(), get_TrxName()));
			}
			MMatchPO[] mPO = MMatchPO.getInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			for (int i = 0; i < mPO.length; i++)
			{
				if (mPO[i].getReversal_ID() > 0)
					continue;
				
				if (mPO[i].getM_InOutLine_ID() == 0)
				{
					if (!mPO[i].reverse(reversalDate)) 
					{
						m_processMsg = "Could not Reverse MatchPO";
						return null;
					}
					addDocsPostProcess(new MMatchPO(Env.getCtx(), mPO[i].getReversal_ID(), get_TrxName()));
				}
				else
				{
					mPO[i].setC_InvoiceLine_ID(null);
					mPO[i].saveEx(get_TrxName());
				}
			}
		}
		//
		load(get_TrxName());	//	reload allocation reversal info

		//	Deep Copy
		HBC_MInvoice reversal = null;
		if (MSysConfig.getBooleanValue(MSysConfig.Invoice_ReverseUseNewNumber, true, getAD_Client_ID()))
			reversal = copyFrom (this, reversalDateInvoiced, reversalDate, getC_DocType_ID(), isSOTrx(), false, get_TrxName(), true);
		else 
			reversal = copyFrom (this, reversalDateInvoiced, reversalDate, getC_DocType_ID(), isSOTrx(), false, get_TrxName(), true, getDocumentNo()+"^");
		if (reversal == null)
		{
			m_processMsg = "Could not create Invoice Reversal";
			return null;
		}
		reversal.setReversal(true);

		//	Reverse Line Qty
		if (!isDownPaymentInvoice()) {
			MInvoiceLine[] rLines = reversal.getLines(true);
			for (int i = 0; i < rLines.length; i++)
			{
				MInvoiceLine rLine = rLines[i];
				rLine.setQtyEntered(rLine.getQtyEntered().negate());
				rLine.setQtyInvoiced(rLine.getQtyInvoiced().negate());
				rLine.setLineNetAmt(rLine.getLineNetAmt().negate());
				if (rLine.getTaxAmt() != null && rLine.getTaxAmt().compareTo(Env.ZERO) != 0)
					rLine.setTaxAmt(rLine.getTaxAmt().negate());
				if (rLine.getLineTotalAmt() != null && rLine.getLineTotalAmt().compareTo(Env.ZERO) != 0)
					rLine.setLineTotalAmt(rLine.getLineTotalAmt().negate());
				if (!rLine.save(get_TrxName()))
				{
					m_processMsg = "Could not correct Invoice Reversal Line";
					return null;
				}
			}
		} else {
			//reversal.setTaxAmt();
			reversal.setTaxAmt(getTaxAmt().negate());
			reversal.setDownPaymentAmt(getDownPaymentAmt().negate());
		} 
		reversal.setC_Order_ID(getC_Order_ID());
		StringBuilder msgadd = new StringBuilder("{->").append(getDocumentNo()).append(")");
		reversal.addDescription(msgadd.toString());
		//FR1948157
		reversal.setReversal_ID(getC_Invoice_ID());
		//@PhieAlbert
		if(get_ValueAsBoolean("IsSeparateTaxInvoice"))
			reversal.set_ValueOfColumn("IsSeparateTaxInvoice", true);
		else
			reversal.set_ValueOfColumn("IsSeparateTaxInvoice", false);
		reversal.saveEx(get_TrxName());
		//
		reversal.docsPostProcess = this.docsPostProcess;
		this.docsPostProcess = new ArrayList<PO>();
		//
		if (!reversal.processIt(DocAction.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return null;
		}
		//
		reverseAllocations(accrual, reversal.getC_Invoice_ID());

		reversal.setC_Payment_ID(0);
		reversal.setIsPaid(true);
		reversal.closeIt();
		reversal.setProcessing (false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx(get_TrxName());
		//
		msgadd = new StringBuilder("(").append(reversal.getDocumentNo()).append("<-)");
		addDescription(msgadd.toString());

		//	Clean up Reversed (this)
		if (!isDownPaymentInvoice()) {
			MInvoiceLine[] iLines = getLines(false);
			for (int i = 0; i < iLines.length; i++)
			{
				MInvoiceLine iLine = iLines[i];
				if (iLine.getM_InOutLine_ID() != 0)
				{
					MInOutLine ioLine = new MInOutLine(getCtx(), iLine.getM_InOutLine_ID(), get_TrxName());
					ioLine.setIsInvoiced(false);
					ioLine.saveEx(get_TrxName());
					//	Reconsiliation
					iLine.setM_InOutLine_ID(0);
					iLine.saveEx(get_TrxName());
				}
	        }
		}
		setProcessed(true);
		//FR1948157
		setReversal_ID(reversal.getC_Invoice_ID());
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);
		setC_Payment_ID(0);
		setIsPaid(true);

		//	Create Allocation
		StringBuilder msgall = new StringBuilder().append(Msg.translate(getCtx(), "C_Invoice_ID")).append(": ").append(getDocumentNo()).append("/").append(reversal.getDocumentNo());
		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, reversalDate,
			getC_Currency_ID(),
			msgall.toString(),
			get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (alloc.save())
		{
			//	Amount
			BigDecimal gt = getGrandTotal(true);
			if (!isSOTrx())
				gt = gt.negate();
			//	Orig Line
			MAllocationLine aLine = new MAllocationLine (alloc, gt,
				Env.ZERO, Env.ZERO, Env.ZERO);
			aLine.setC_Invoice_ID(getC_Invoice_ID());
			aLine.saveEx();
			//	Reversal Line
			MAllocationLine rLine = new MAllocationLine (alloc, gt.negate(),
				Env.ZERO, Env.ZERO, Env.ZERO);
			rLine.setC_Invoice_ID(reversal.getC_Invoice_ID());
			rLine.saveEx();
			// added AdempiereException by zuhri
			if (!alloc.processIt(DocAction.ACTION_Complete))
				throw new AdempiereException("Failed when processing document - " + alloc.getProcessMsg());
			// end added
				alloc.saveEx();
		}
		
		// @Stephan TAOWI-1164
		MInvoiceLine invoiceLines[] = getLines();
		for (MInvoiceLine invoiceLine : invoiceLines) {
			if(invoiceLine.getM_InOutLine_ID() > 0){
				MInOutLine inoutLine = (MInOutLine) invoiceLine.getM_InOutLine();
				inoutLine.setIsInvoiced(false);
				inoutLine.saveEx();
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_InvoiceLine SET C_OrderLine_ID = NULL WHERE C_InvoiceLine_ID="+invoiceLine.getC_InvoiceLine_ID());
			DB.executeUpdate(sb.toString(), get_TrxName());
		}
		
		// TAOWI-1999
		for (MInvoiceLine invoiceLine : reversal.getLines()) {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_InvoiceLine SET C_OrderLine_ID = NULL WHERE C_InvoiceLine_ID="+invoiceLine.getC_InvoiceLine_ID());
			DB.executeUpdate(sb.toString(), get_TrxName());
		}
		// @Stephan end
		
		return reversal;
	}
	
	private void reverseAllocations(boolean accrual, int invoiceID) {
		for (MAllocationHdr allocation : MAllocationHdr.getOfInvoice(getCtx(), invoiceID, get_TrxName())) {
			if (accrual) {
				allocation.setDocAction(DocAction.ACTION_Reverse_Accrual);
				allocation.reverseAccrualIt();
			} else {
				allocation.setDocAction(DocAction.ACTION_Reverse_Correct);
				allocation.reverseCorrectIt();
			}
			allocation.saveEx(get_TrxName());
		}
	}
	
	/**
	 * 	Create new Invoice by copying
	 * 	@param from invoice
	 * 	@param dateDoc date of the document date
	 *  @param acctDate original account date 
	 * 	@param C_DocTypeTarget_ID target doc type
	 * 	@param isSOTrx sales order
	 * 	@param counter create counter links
	 * 	@param trxName trx
	 * 	@param setOrder set Order links
	 *	@return Invoice
	 */
	public static HBC_MInvoice copyFrom (MInvoice from, Timestamp dateDoc, Timestamp dateAcct,
		int C_DocTypeTarget_ID, boolean isSOTrx, boolean counter,
		String trxName, boolean setOrder)
	{
		return copyFrom (from, dateDoc, dateAcct,
				C_DocTypeTarget_ID, isSOTrx, counter,
				trxName, setOrder,null);
	}
	
	/**
	 * 	Create new Invoice by copying
	 * 	@param from invoice
	 * 	@param dateDoc date of the document date
	 *  @param acctDate original account date 
	 * 	@param C_DocTypeTarget_ID target doc type
	 * 	@param isSOTrx sales order
	 * 	@param counter create counter links
	 * 	@param trxName trx
	 * 	@param setOrder set Order links
	 *  @param Document Number for reversed invoices
	 *	@return Invoice
	 */
	public static HBC_MInvoice copyFrom (MInvoice from, Timestamp dateDoc, Timestamp dateAcct,
		int C_DocTypeTarget_ID, boolean isSOTrx, boolean counter,
		String trxName, boolean setOrder, String documentNo)
	{
		HBC_MInvoice to = new HBC_MInvoice (from.getCtx(), 0, trxName);
		PO.copyValues (from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck ("C_Invoice_ID", I_ZERO);
		to.set_ValueNoCheck ("DocumentNo", documentNo);
		//
		to.setDocStatus (DOCSTATUS_Drafted);		//	Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setC_DocTypeTarget_ID (C_DocTypeTarget_ID);
		to.setIsSOTrx(isSOTrx);
		//
		to.setDateInvoiced (dateDoc);
		to.setDateAcct (dateAcct);
		to.setDatePrinted(null);
		to.setIsPrinted (false);
		//
		to.setIsApproved (false);
		to.setC_Payment_ID(0);
		to.setC_CashLine_ID(0);
		to.setIsPaid (false);
		to.setIsInDispute(false);
		//
		//	Amounts are updated by trigger when adding lines
		to.setGrandTotal(Env.ZERO);
		to.setTotalLines(Env.ZERO);
		//
		to.setIsTransferred (false);
		to.setPosted (false);
		to.setProcessed (false);
		//[ 1633721 ] Reverse Documents- Processing=Y
		to.setProcessing(false);
		//	delete references
		to.setIsSelfService(false);
		if (!setOrder)
			to.setC_Order_ID(0);
		if (counter)
		{
			to.setRef_Invoice_ID(from.getC_Invoice_ID());
			MOrg org = MOrg.get(from.getCtx(), from.getAD_Org_ID());
			int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(trxName);
			if (counterC_BPartner_ID == 0)
				return null;
			to.setBPartner(MBPartner.get(from.getCtx(), counterC_BPartner_ID));
			//	Try to find Order link
			if (from.getC_Order_ID() != 0)
			{
				MOrder peer = new MOrder (from.getCtx(), from.getC_Order_ID(), from.get_TrxName());
				if (peer.getRef_Order_ID() != 0)
					to.setC_Order_ID(peer.getRef_Order_ID());
			}
			// Try to find RMA link
			if (from.getM_RMA_ID() != 0)
			{
				MRMA peer = new MRMA (from.getCtx(), from.getM_RMA_ID(), from.get_TrxName());
				if (peer.getRef_RMA_ID() > 0)
					to.setM_RMA_ID(peer.getRef_RMA_ID());
			}
			//
		}
		else
			to.setRef_Invoice_ID(0);

		to.saveEx(trxName);
		if (counter)
			from.setRef_Invoice_ID(to.getC_Invoice_ID());

		//	Lines
		if (!from.isDownPaymentInvoice()) {
			if (to.copyLinesFrom(from, counter, setOrder) == 0)
				throw new IllegalStateException("Could not create Invoice Lines");
		}
		return to;
	}
	
	/**
	 * 	Explode non stocked BOM.
	 */
	private void explodeBOM ()
	{
		String where = "AND IsActive='Y' AND EXISTS "
			+ "(SELECT * FROM M_Product p WHERE C_InvoiceLine.M_Product_ID=p.M_Product_ID"
			+ " AND	p.IsBOM='Y' AND p.IsVerified='Y' AND p.IsStocked='N')";
		//
		String sql = "SELECT COUNT(*) FROM C_InvoiceLine "
			+ "WHERE C_Invoice_ID=? " + where;
		int count = DB.getSQLValueEx(get_TrxName(), sql, getC_Invoice_ID());
		while (count != 0)
		{
			renumberLines (100);

			//	Order Lines with non-stocked BOMs
			MInvoiceLine[] lines = getLines (where);
			for (int i = 0; i < lines.length; i++)
			{
				MInvoiceLine line = lines[i];
				MProduct product = MProduct.get (getCtx(), line.getM_Product_ID());
				if (log.isLoggable(Level.FINE)) log.fine(product.getName());
				//	New Lines
				int lineNo = line.getLine ();

				//find default BOM with valid dates and to this product
				MPPProductBOM bom = MPPProductBOM.get(product, getAD_Org_ID(),getDateInvoiced(), get_TrxName());
				if(bom != null)
				{
					MPPProductBOMLine[] bomlines = bom.getLines(getDateInvoiced());
					for (int j = 0; j < bomlines.length; j++)
					{
						MPPProductBOMLine bomline = bomlines[j];
						MInvoiceLine newLine = new MInvoiceLine (this);
						newLine.setLine (++lineNo);
						newLine.setM_Product_ID (bomline.getM_Product_ID ());
						newLine.setC_UOM_ID (bomline.getC_UOM_ID ());
						newLine.setQty (line.getQtyInvoiced().multiply(
								bomline.getQtyBOM ()));		//	Invoiced/Entered
						if (bomline.getDescription () != null)
							newLine.setDescription (bomline.getDescription ());
						//
						newLine.setPrice ();
						newLine.saveEx (get_TrxName());
					}
				}

				/*MProductBOM[] boms = MProductBOM.getBOMLines (product);
				for (int j = 0; j < boms.length; j++)
				{
					MProductBOM bom = boms[j];
					MInvoiceLine newLine = new MInvoiceLine (this);
					newLine.setLine (++lineNo);
					newLine.setM_Product_ID (bom.getProduct().getM_Product_ID(),
						bom.getProduct().getC_UOM_ID());
					newLine.setQty (line.getQtyInvoiced().multiply(
						bom.getBOMQty ()));		//	Invoiced/Entered
					if (bom.getDescription () != null)
						newLine.setDescription (bom.getDescription ());
					//
					newLine.setPrice ();
					newLine.save (get_TrxName());
				}*/

				//	Convert into Comment Line
				line.setM_Product_ID (0);
				line.setM_AttributeSetInstance_ID (0);
				line.setPriceEntered (Env.ZERO);
				line.setPriceActual (Env.ZERO);
				line.setPriceLimit (Env.ZERO);
				line.setPriceList (Env.ZERO);
				line.setLineNetAmt (Env.ZERO);
				//
				StringBuilder description = new StringBuilder().append(product.getName ());
				if (product.getDescription () != null)
					description.append(" ").append(product.getDescription ());
				if (line.getDescription () != null)
					description.append(" ").append(line.getDescription ());
				line.setDescription (description.toString());
				line.saveEx (get_TrxName());
			} //	for all lines with BOM

			m_lines = null;
			count = DB.getSQLValue (get_TrxName(), sql, getC_Invoice_ID ());
			renumberLines (10);
		}	//	while count != 0
	}	//	explodeBOM
	
	/**
	 * 	(Re) Create Pay Schedule
	 *	@return true if valid schedule
	 */
	private boolean createPaySchedule()
	{
		if (getC_PaymentTerm_ID() == 0)
			return false;
		MPaymentTerm pt = new MPaymentTerm(getCtx(), getC_PaymentTerm_ID(), null);
		if (log.isLoggable(Level.FINE)) log.fine(pt.toString());
		
		int numSchema = pt.getSchedule(false).length;
		
		MInvoicePaySchedule[] schedule = MInvoicePaySchedule.getInvoicePaySchedule
			(getCtx(), getC_Invoice_ID(), 0, get_TrxName());

		if (schedule.length > 0) {
			if (numSchema == 0)
				return false; // created a schedule for a payment term that doesn't manage schedule
			return validatePaySchedule();
		} else {
			boolean isValid = pt.apply(this);		//	calls validate pay schedule
			if (numSchema == 0)
				return true; // no schedule, no schema, OK
			else
				return isValid;
		}
	}	//	createPaySchedule
	
	/**
	 * 	Get Invoice Lines of Invoice
	 * 	@param whereClause starting with AND
	 * 	@return lines
	 */
	private MInvoiceLine[] getLines (String whereClause)
	{
		String whereClauseFinal = "C_Invoice_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MInvoiceLine> list = new Query(getCtx(), I_C_InvoiceLine.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(getC_Invoice_ID())
										.setOrderBy(I_C_InvoiceLine.COLUMNNAME_Line)
										.list();
		return list.toArray(new MInvoiceLine[list.size()]);
	}	//	getLines
	
	/**
	 * 	Get Lines of Invoice
	 * 	@return lines
	 */
	public MInvoiceLine[] getLines()
	{
		return getLines(false);
	}	//	getLines
}
