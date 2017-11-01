package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderPaySchedule;
import org.compiere.model.MPeriod;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

public class HBC_MOrder extends MOrder {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2940387276260360968L;
	public HBC_MOrder(MProject project, boolean IsSOTrx, String DocSubTypeSO) {
		super(project, IsSOTrx, DocSubTypeSO);
	}

	public HBC_MOrder(Properties ctx, int C_Order_ID, String trxName) {
		super(ctx, C_Order_ID, trxName);
	}

	public HBC_MOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_InProgress)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_Completed)) {
			options[index++] = DocAction.ACTION_Void;
			options[index++] = DocAction.ACTION_Close;
			options[index++] = DocAction.ACTION_ReActivate;
			//options[index++] = DocAction.ACTION_Reverse_Accrual;
		} else if (docStatus.equals(DocAction.STATUS_Invalid)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		}
		//@Edwin
		else if (docStatus.equals(DocAction.STATUS_NotApproved)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		}
		//end @Edwin

		return index;
	}

	protected boolean beforeSave (boolean newRecord)
	{

		if(newRecord){
			if(isSOTrx()==false)
				setDocumentNo(getDocumentNoRomanFormat());
		}

		//	Client/Org Check
		if (getAD_Org_ID() == 0)
		{
			int context_AD_Org_ID = Env.getAD_Org_ID(getCtx());
			if (context_AD_Org_ID != 0)
			{
				setAD_Org_ID(context_AD_Org_ID);
				log.warning("Changed Org to Context=" + context_AD_Org_ID);
			}
		}
		if (getAD_Client_ID() == 0)
		{
			m_processMsg = "AD_Client_ID = 0";
			return false;
		}

		//	New Record Doc Type - make sure DocType set to 0
		if (newRecord && getC_DocType_ID() == 0)
			setC_DocType_ID (0);

		//	Default Warehouse
		if (getM_Warehouse_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
			if (ii != 0)
				setM_Warehouse_ID(ii);
			else
			{
				throw new FillMandatoryException(COLUMNNAME_M_Warehouse_ID);
			}
		}
		MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		//	Warehouse Org
		if (newRecord 
				|| is_ValueChanged("AD_Org_ID") || is_ValueChanged("M_Warehouse_ID"))
		{
			if (wh.getAD_Org_ID() != getAD_Org_ID())
				log.saveWarning("WarehouseOrgConflict", "");
		}

		boolean disallowNegInv = wh.isDisallowNegativeInv();
		String DeliveryRule = getDeliveryRule();
		if((disallowNegInv && DELIVERYRULE_Force.equals(DeliveryRule)) ||
				(DeliveryRule == null || DeliveryRule.length()==0))
			setDeliveryRule(DELIVERYRULE_Availability);

		//	Reservations in Warehouse
		if (!newRecord && is_ValueChanged("M_Warehouse_ID"))
		{
			MOrderLine[] lines = getLines(false,null);
			for (int i = 0; i < lines.length; i++)
			{
				if (!lines[i].canChangeWarehouse())
					return false;
			}
		}

		//	No Partner Info - set Template
		if (getC_BPartner_ID() == 0)
			setBPartner(MBPartner.getTemplate(getCtx(), getAD_Client_ID()));
		if (getC_BPartner_Location_ID() == 0)
			setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), null));
		//	No Bill - get from Ship
		if (getBill_BPartner_ID() == 0)
		{
			setBill_BPartner_ID(getC_BPartner_ID());
			setBill_Location_ID(getC_BPartner_Location_ID());
		}
		if (getBill_Location_ID() == 0)
			setBill_Location_ID(getC_BPartner_Location_ID());

		//  @Stephan add validation is no price list
		boolean isNoPrice = get_ValueAsBoolean("IsNoPriceList");

		//	Default Price List
		if (getM_PriceList_ID() == 0 && !isNoPrice)
		{
			int ii = DB.getSQLValueEx(null,
					"SELECT M_PriceList_ID FROM M_PriceList "
							+ "WHERE AD_Client_ID=? AND IsSOPriceList=? AND IsActive=?"
							+ "ORDER BY IsDefault DESC", getAD_Client_ID(), isSOTrx(), true);
			if (ii != 0)
				setM_PriceList_ID (ii);
		}
		//	Default Currency
		if (getC_Currency_ID() == 0)
		{
			String sql = "SELECT C_Currency_ID FROM M_PriceList WHERE M_PriceList_ID=?";
			int ii = DB.getSQLValue (null, sql, getM_PriceList_ID());
			if (ii != 0)
				setC_Currency_ID (ii);
			else
				setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_Currency_ID"));
		}

		//	Default Sales Rep
		if (getSalesRep_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#SalesRep_ID");
			if (ii != 0)
				setSalesRep_ID (ii);
		}

		//	Default Document Type
		if (getC_DocTypeTarget_ID() == 0)
			setC_DocTypeTarget_ID(DocSubTypeSO_Standard);

		//	Default Payment Term
		if (getC_PaymentTerm_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#C_PaymentTerm_ID");
			if (ii != 0)
				setC_PaymentTerm_ID(ii);
			else
			{
				String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
					setC_PaymentTerm_ID (ii);
			}
		}

		// IDEMPIERE-63
		// for documents that can be reactivated we cannot allow changing 
		// C_DocTypeTarget_ID or C_DocType_ID if they were already processed and isOverwriteSeqOnComplete
		// neither change the Date if isOverwriteDateOnComplete
		BigDecimal previousProcessedOn = (BigDecimal) get_ValueOld(COLUMNNAME_ProcessedOn);
		if (! newRecord && previousProcessedOn != null && previousProcessedOn.signum() > 0) {
			int previousDocTypeID = (Integer) get_ValueOld(COLUMNNAME_C_DocTypeTarget_ID);
			MDocType previousdt = MDocType.get(getCtx(), previousDocTypeID);
			if (is_ValueChanged(COLUMNNAME_C_DocType_ID) || is_ValueChanged(COLUMNNAME_C_DocTypeTarget_ID)) {
				if (previousdt.isOverwriteSeqOnComplete()) {
					log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangeProcessedDocType"));
					return false; 
				}
			}
			if (is_ValueChanged(COLUMNNAME_DateOrdered)) {
				if (previousdt.isOverwriteDateOnComplete()) {
					log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangeProcessedDate"));
					return false; 
				}
			}
		}

		// IDEMPIERE-1597 Price List and Date must be not-updateable
		if (!newRecord && (is_ValueChanged(COLUMNNAME_M_PriceList_ID) || is_ValueChanged(COLUMNNAME_DateOrdered))) {
			int cnt = DB.getSQLValueEx(get_TrxName(), "SELECT COUNT(*) FROM C_OrderLine WHERE C_Order_ID=? AND M_Product_ID>0", getC_Order_ID());
			if (cnt > 0) {
				if (is_ValueChanged(COLUMNNAME_M_PriceList_ID)) {
					log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangePl"));
					return false;
				}
				if (is_ValueChanged(COLUMNNAME_DateOrdered)) {
					MPriceList pList =  MPriceList.get(getCtx(), getM_PriceList_ID(), null);
					MPriceListVersion plOld = pList.getPriceListVersion((Timestamp)get_ValueOld(COLUMNNAME_DateOrdered));
					MPriceListVersion plNew = pList.getPriceListVersion((Timestamp)get_Value(COLUMNNAME_DateOrdered));
					if (plNew == null || !plNew.equals(plOld)) {
						log.saveError("Error", Msg.getMsg(getCtx(), "CannotChangeDateOrdered"));
						return false;
					}
				}
			}
		}

		return true;
	}	//	beforeSave

	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		MDocType dt = MDocType.get(getCtx(), getC_DocTypeTarget_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}

		if (isSOTrx() && getDeliveryViaRule().equals(DELIVERYVIARULE_Shipper))
		{
			if (getM_Shipper_ID() == 0)
			{
				m_processMsg = "@FillMandatory@" + Msg.getElement(getCtx(), COLUMNNAME_M_Shipper_ID);
				return DocAction.STATUS_Invalid;
			}

			if (!calculateFreightCharge())
				return DocAction.STATUS_Invalid;
		}

		//	Lines
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		// Bug 1564431
		if (getDeliveryRule() != null && getDeliveryRule().equals(MOrder.DELIVERYRULE_CompleteOrder)) 
		{
			for (int i = 0; i < lines.length; i++) 
			{
				MOrderLine line = lines[i];
				MProduct product = line.getProduct();
				if (product != null && product.isExcludeAutoDelivery())
				{
					m_processMsg = "@M_Product_ID@ "+product.getValue()+" @IsExcludeAutoDelivery@";
					return DocAction.STATUS_Invalid;
				}
			}
		}

		//	Convert DocType to Target
		if (getC_DocType_ID() != getC_DocTypeTarget_ID() )
		{
			//	Cannot change Std to anything else if different warehouses
			if (getC_DocType_ID() != 0)
			{
				MDocType dtOld = MDocType.get(getCtx(), getC_DocType_ID());
				if (MDocType.DOCSUBTYPESO_StandardOrder.equals(dtOld.getDocSubTypeSO())		//	From SO
						&& !MDocType.DOCSUBTYPESO_StandardOrder.equals(dt.getDocSubTypeSO()))	//	To !SO
				{
					for (int i = 0; i < lines.length; i++)
					{
						if (lines[i].getM_Warehouse_ID() != getM_Warehouse_ID())
						{
							log.warning("different Warehouse " + lines[i]);
							m_processMsg = "@CannotChangeDocType@";
							return DocAction.STATUS_Invalid;
						}
					}
				}
			}

			//	New or in Progress/Invalid
			if (DOCSTATUS_Drafted.equals(getDocStatus()) 
					|| DOCSTATUS_InProgress.equals(getDocStatus())
					|| DOCSTATUS_Invalid.equals(getDocStatus())
					|| getC_DocType_ID() == 0)
			{
				setC_DocType_ID(getC_DocTypeTarget_ID());
			}
			else	//	convert only if offer
			{
				if (dt.isOffer())
					setC_DocType_ID(getC_DocTypeTarget_ID());
				else
				{
					m_processMsg = "@CannotChangeDocType@";
					return DocAction.STATUS_Invalid;
				}
			}
		}	//	convert DocType

		//	Mandatory Product Attribute Set Instance
		for (MOrderLine line : getLines()) {
			if (line.getM_Product_ID() > 0 && line.getM_AttributeSetInstance_ID() == 0) {
				MProduct product = line.getProduct();
				if (product.isASIMandatory(isSOTrx())) {
					if(product.getAttributeSet()==null){
						m_processMsg = "@NoAttributeSet@=" + product.getValue();
						return DocAction.STATUS_Invalid;

					}
					if (! product.getAttributeSet().excludeTableEntry(MOrderLine.Table_ID, isSOTrx())) {
						StringBuilder msg = new StringBuilder("@M_AttributeSet_ID@ @IsMandatory@ (@Line@ #")
						.append(line.getLine())
						.append(", @M_Product_ID@=")
						.append(product.getValue())
						.append(")");
						m_processMsg = msg.toString();
						return DocAction.STATUS_Invalid;
					}
				}
			}
		}

		//	Lines
		/* commented by @win
		if (explodeBOM())
			lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		 */

		/*	@Stephan temporary comment
		if (!reserveStock(dt, lines))
		{
			m_processMsg = "Cannot reserve Stock";
			return DocAction.STATUS_Invalid;
		}
		 */

		//TEMPORARY COMMENTED BY @TommyAng
		/* Comment by @phie, sync with isSummary on Invoice, check tax isSummary in all line
		//add is summary by @Stephan
		//TODO ask functional for rounding mode, if tax summary, then can't rounding
		if (getC_Tax_ID() > 0 && getC_Tax().isSummary()){
			if (!calculateTaxTotal())
			{
				m_processMsg = "Error calculating tax";
				return DocAction.STATUS_Invalid;
			}
		}
		 */


		if (getGrandTotal().signum() != 0
				&& (PAYMENTRULE_OnCredit.equals(getPaymentRule()) || PAYMENTRULE_DirectDebit.equals(getPaymentRule()))) {
			if (!createPaySchedule())
			{
				m_processMsg = "@ErrorPaymentSchedule@";
				return DocAction.STATUS_Invalid;
			}
		} else {
			if (MOrderPaySchedule.getOrderPaySchedule(getCtx(), getC_Order_ID(), 0, get_TrxName()).length > 0) 
			{
				m_processMsg = "@ErrorPaymentSchedule@";
				return DocAction.STATUS_Invalid;
			}
		}

		//	Credit Check
		if (isSOTrx())
		{
			if (   MDocType.DOCSUBTYPESO_POSOrder.equals(dt.getDocSubTypeSO())
					&& PAYMENTRULE_Cash.equals(getPaymentRule())
					&& !MSysConfig.getBooleanValue(MSysConfig.CHECK_CREDIT_ON_CASH_POS_ORDER, true, getAD_Client_ID(), getAD_Org_ID())) {
				// ignore -- don't validate for Cash POS Orders depending on sysconfig parameter
			} else if (MDocType.DOCSUBTYPESO_PrepayOrder.equals(dt.getDocSubTypeSO())
					&& !MSysConfig.getBooleanValue(MSysConfig.CHECK_CREDIT_ON_PREPAY_ORDER, true, getAD_Client_ID(), getAD_Org_ID())) {
				// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			} else {
				MBPartner bp = new MBPartner (getCtx(), getBill_BPartner_ID(), get_TrxName()); // bill bp is guaranteed on beforeSave

				if (getGrandTotal().signum() > 0)  // IDEMPIERE-365 - just check credit if is going to increase the debt
				{		 

					if (MBPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus()))
					{
						m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@=" 
								+ bp.getTotalOpenBalance()
								+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
						return DocAction.STATUS_Invalid;
					}
					if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus()))
					{
						m_processMsg = "@BPartnerCreditHold@ - @TotalOpenBalance@=" 
								+ bp.getTotalOpenBalance() 
								+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
						return DocAction.STATUS_Invalid;
					}
					BigDecimal grandTotal = MConversionRate.convertBase(getCtx(), 
							getGrandTotal(), getC_Currency_ID(), getDateOrdered(), 
							getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
					if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus(grandTotal)))
					{
						m_processMsg = "@BPartnerOverOCreditHold@ - @TotalOpenBalance@=" 
								+ bp.getTotalOpenBalance() + ", @GrandTotal@=" + grandTotal
								+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
						return DocAction.STATUS_Invalid;
					}
				}
			}  
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		//	if (!DOCACTION_Complete.equals(getDocAction()))		don't set for just prepare 
		//		setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		if(m_processMsg == null)
			m_processMsg = super.getProcessMsg();

		return m_processMsg;
	}	//	getProcessMsg

	public String getDocumentNoRomanFormat(){
		String DocumentNo="";
		String romanmonth=RomanNumber(getMonthDate(getDateOrdered()));
		String yeardate = String.valueOf(getYearDate(getDateOrdered()));
		MDocType doctype=new MDocType(getCtx(),getC_DocTypeTarget_ID(),get_TrxName());
		MSequence sequence = new MSequence(getCtx(),doctype.getDocNoSequence_ID(),get_TrxName());
		DocumentNo=sequence.getCurrentNext()+doctype.get_ValueAsString("TypeDoc")+romanmonth+"/"+yeardate;
		sequence.setCurrentNext(sequence.getCurrentNext()+1);
		sequence.saveEx();
		return DocumentNo;
	}

	protected int getYearDate(Timestamp Date){
		int year =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		year= calender.get(Calendar.YEAR);
		return year;
	}


	protected int getMonthDate(Timestamp Date){
		int month =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		month = calender.get(Calendar.MONTH)+1;
		return month;
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

	public boolean reActivateIt()
	{
		return super.reActivateIt();
	}	//	reActivateIt

	// AZ Goodwill
	protected String deleteMatchPOCostDetail(MOrderLine line) {
		return super.deleteMatchPOCostDetail(line);
	}	

	//@KevinY Habco - 2717

	/**	Order Lines					*/
	protected HBC_MOrderLine[] 	m_lines = null;

	/**
	 * 	Get Lines of Order.
	 * 	(used by web store)
	 * 	@return lines
	 */
	public HBC_MOrderLine[] getLines()
	{
		return getLines(false, null);
	}	//	getLines


	/**
	 * 	Get Lines of Order
	 * 	@param requery requery
	 * 	@param orderBy optional order by column
	 * 	@return lines
	 */
	public HBC_MOrderLine[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines


	/**************************************************************************
	 * 	Get Lines of Order
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@param orderClause order clause
	 * 	@return lines
	 */
	public HBC_MOrderLine[] getLines (String whereClause, String orderClause)
	{
		//red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal = new StringBuilder(MOrderLine.COLUMNNAME_C_Order_ID+"=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MOrderLine.COLUMNNAME_Line;
		//
		List<MOrderLine> list = new Query(getCtx(), I_C_OrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
		.setParameters(get_ID())
		.setOrderBy(orderClause)
		.list();
		for (MOrderLine ol : list) {
			ol.setHeaderInfo(this);
		}
		//
		return list.toArray(new HBC_MOrderLine[list.size()]);		
	}	//	getLines
	//@KevinY end
}
