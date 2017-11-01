package org.toba.habco.factory;

import java.sql.Timestamp;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.base.event.LoginEventData;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GenericPO;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MFactAcct;
import org.compiere.model.MInventory;
import org.compiere.model.MMatchInv;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MTCSAmortizationRun;
import org.compiere.model.PO;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.osgi.service.event.Event;
import org.toba.habco.model.I_LCO_InvoiceWithholding;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.X_HBC_ARCalculationLine;
import org.toba.habco.model.X_HBC_ProductMerk;
import org.toba.habco.model.X_LCO_InvoiceWithholding;
import org.toba.habco.model.validator.HBC_ARCalculation_Validator;
import org.toba.habco.model.validator.HBC_AmortizationRunValidator;
import org.toba.habco.model.validator.HBC_AssetAdditionValidator;
import org.toba.habco.model.validator.HBC_InOutValidator;
import org.toba.habco.model.validator.HBC_InvoiceValidator;
import org.toba.habco.model.validator.HBC_MovementValidator;
import org.toba.habco.model.validator.HBC_OrderValidator;
import org.toba.habco.model.validator.HBC_PaymentValidator;
import org.toba.habco.model.validator.HBC_ProductMerkValidator;
import org.toba.habco.model.validator.HBC_RequisitionValidator;
import org.toba.habco.model.validator.HBC_ShipActivityValidator;
import org.toba.habco.model.validator.HBC_WithholdingValidator;

public class HBC_ValidatorFactory extends AbstractEventHandler {
	private CLogger log = CLogger.getCLogger(HBC_ValidatorFactory.class);


	@Override
	protected void initialize() {
		registerEvent(IEventTopics.AFTER_LOGIN);
		registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, I_C_Order.Table_Name);
		
		//MOrder
		registerTableEvent(IEventTopics.DOC_AFTER_COMPLETE, I_C_Order.Table_Name);
		//@KevinY HBC - 2860
		//registerTableEvent(IEventTopics.DOC_AFTER_CLOSE, I_C_Order.Table_Name);
		//@KevinY end
		
		//MOrderLine
		registerTableEvent(IEventTopics.PO_BEFORE_NEW, I_C_OrderLine.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, I_C_OrderLine.Table_Name);
		
		//MInvoice
		registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_COMPLETE, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_REVERSEACCRUAL, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_REVERSECORRECT, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_REVERSEACCRUAL, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_REVERSECORRECT, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_PREPARE, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_PREPARE, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_VOID, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_POST, I_C_Invoice.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_POST, I_C_Invoice.Table_Name);
		
		//MInvoiceWithholding
		registerTableEvent(IEventTopics.PO_AFTER_NEW, X_LCO_InvoiceWithholding.Table_Name);	
		
		//MInvoiceLine
		registerTableEvent(IEventTopics.PO_BEFORE_NEW, I_C_InvoiceLine.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, I_C_InvoiceLine.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, I_C_InvoiceLine.Table_Name);
		
		//MInOut
		registerTableEvent(IEventTopics.DOC_BEFORE_REVERSEACCRUAL, I_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_REVERSECORRECT, I_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_REVERSEACCRUAL, I_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_REVERSECORRECT, I_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_POST, I_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_COMPLETE, I_M_InOut.Table_Name);
		
		//MARCalculationLine
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, X_HBC_ARCalculationLine.Table_Name);
		
		//MPayment
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, X_C_Payment.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_POST, X_C_Payment.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_PREPARE, X_C_Payment.Table_Name);
		
		//MMovement
		registerTableEvent(IEventTopics.DOC_AFTER_REVERSEACCRUAL, I_M_Movement.Table_Name);
		registerTableEvent(IEventTopics.DOC_AFTER_REVERSECORRECT, I_M_Movement.Table_Name);
		
		//MInventory
		registerTableEvent(IEventTopics.DOC_AFTER_POST, MInventory.Table_Name);
		
		//MMatchInv
		registerTableEvent(IEventTopics.DOC_AFTER_POST, MMatchInv.Table_Name);
		
		//MAllocationHdr
		registerTableEvent(IEventTopics.DOC_AFTER_POST, MAllocationHdr.Table_Name);
		
		//@phie
		//AssetAddition
		registerTableEvent(IEventTopics.DOC_AFTER_COMPLETE, MAssetAddition.Table_Name);
		
		//MTCSAmortizationRun
		registerTableEvent(IEventTopics.DOC_AFTER_POST, MTCSAmortizationRun.Table_Name);
		//end phie
		
		//@KevinY HBC - 2860 
		//MRequisition
		//registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, MRequisition.Table_Name);
		//@KevinY end
		registerTableEvent(IEventTopics.DOC_AFTER_PREPARE, MRequisition.Table_Name);
		
		//@Phie
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, X_HBC_ProductMerk.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, X_HBC_ProductMerk.Table_Name);
		//end phie
		
		//Ship Activity
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MShipActivity.Table_Name);
		log.info("PROJECT MANAGEMENT EVENT MANAGER // INITIALIZED");
	}

	
	
	@Override
	protected void doHandleEvent(Event event) {
		String msg = "";
		if (event.getTopic().equals(IEventTopics.AFTER_LOGIN)) {
			LoginEventData eventData = getEventData(event);
			log.info(" topic="+event.getTopic()+" AD_Client_ID="+eventData.getAD_Client_ID()
					+" AD_Org_ID="+eventData.getAD_Org_ID()+" AD_Role_ID="+eventData.getAD_Role_ID()
					+" AD_User_ID="+eventData.getAD_User_ID());

		}
		
		//@phie
		else if (getPO(event).get_TableName().equals(X_HBC_ProductMerk.Table_Name)){
			msg = HBC_ProductMerkValidator.executeEvent(event, getPO(event));
		}
		//end phie
		
		//@phie
		else if (getPO(event).get_TableName().equals(MTCSAmortizationRun.Table_Name)){
			msg = HBC_AmortizationRunValidator.executeEvent(event, getPO(event));
		}
		else if (getPO(event).get_TableName().equals(MShipActivity.Table_Name)){
			msg = HBC_ShipActivityValidator.executeEvent(event, getPO(event));
		}
		//end phie
		
		//  delete fact after post if reverse correct
		else if (event.getTopic().equals(IEventTopics.DOC_AFTER_POST)){
			//msg = afterPosted(getPO(event));
		}
		
		else if (getPO(event).get_TableName().equals(I_C_Order.Table_Name)) {
			msg = HBC_OrderValidator.executeEvent(event, getPO(event));
		} 
		
		else if (getPO(event).get_TableName().equals(I_C_OrderLine.Table_Name)) {
			msg = HBC_OrderValidator.executeLineEvent(event, getPO(event));	
		} 
		
		else if (getPO(event).get_TableName().equals(I_C_Invoice.Table_Name)){
			msg = HBC_InvoiceValidator.executeEvent(event, getPO(event));
		}
		
		else if (getPO(event).get_TableName().equals(I_C_InvoiceLine.Table_Name)) {
			msg = HBC_InvoiceValidator.executeLineEvent(event, getPO(event));
		} 
		
		else if (getPO(event).get_TableName().equals(I_M_InOut.Table_Name)){
			msg = HBC_InOutValidator.executeEvent(event, getPO(event));
		} 
	
		else if (getPO(event).get_TableName().equals(I_LCO_InvoiceWithholding.Table_Name)) {
			msg = HBC_WithholdingValidator.executeEvent(event, getPO(event));	
		} 
		/*
		else if (getPO(event).get_TableName().equals(X_HBC_ARCalculationLine.Table_Name)){
			msg = HBC_ARCalculation_Validator.executeEvent(event, getPO(event));
		}
		*/
		else if (getPO(event).get_TableName().equals(X_C_Payment.Table_Name)){
			msg = HBC_PaymentValidator.executeEvent(event, getPO(event));
		}
		
		else if (getPO(event).get_TableName().equals(I_M_Movement.Table_Name)){
			msg = HBC_MovementValidator.executeEvent(event, getPO(event));
		}
		
		//@phie
		else if (getPO(event).get_TableName().equals(MAssetAddition.Table_Name)){
			msg = HBC_AssetAdditionValidator.executeEvent(event, getPO(event));
		}
		//end phie
		
		//@KevinY HBC - 2860
		else if(getPO(event).get_TableName().equals(MRequisition.Table_Name)){
			msg = HBC_RequisitionValidator.executeEvent(event, getPO(event));
		}
		//end KevinY
		
		if (msg.length() > 0)
			throw new AdempiereException(msg);

	}

	/**
	 * Group All Validators Related to C_Order
	 */
	/*
	protected String executeOrderEvent(Event event) {
		String msgOrder = "";
		MOrder order = (MOrder) getPO(event);
		if (!order.isSOTrx() && order.getC_Project_ID() > 0) {
			if (event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)) {
				//msgOrder = validateProjectBudget(order);
				MOrderLine line = new MOrderLine(order.getCtx(), order.getC_Order_ID(), null);
				BigDecimal priceEntered = line.getPriceEntered();
				BigDecimal priceList = line.getPriceList();
				
				if(priceEntered.compareTo(BigDecimal.ZERO) <= 0){
					msgOrder = "PriceEntered cannot be empty";
				}else if(priceList.compareTo(BigDecimal.ZERO) <= 0){
					msgOrder = "Pricelist cannot be empty";
				}
			} 
		}
		return msgOrder;
	}*/
	
	/**
	 * Group All Validators Related to C_OrderLine
	 */
	/*
	protected String executeOrderLineEvent(Event event) {
		String msgOrderLine = "";
		MOrderLine orderLine = (MOrderLine) getPO(event);
		if (!orderLine.getC_Order().isSOTrx() && orderLine.getC_Order().getC_Project_ID() > 0) {
			if (event.getTopic().equals(IEventTopics.PO_AFTER_NEW) ||
					event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE)) {
				//msgOrderLine = validateProjectActivity(orderLine);
			} 
		}
		return msgOrderLine;
	}
	
	/**
	 * Group All Validators Related to C_Invoice
	 */
	/*
	protected String executeInvoiceEvent(Event event) {
		String msgInvoice = "";
		MInvoice invoice = (MInvoice) getPO(event);
		if (!invoice.isSOTrx() && invoice.getC_Project_ID() > 0) {
			if (event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)) {
				//msgInvoice = validateProjectBudget(invoice);
			}
		}
		return msgInvoice;
	}
	
	/**
	 * Group All Validators Related to C_InvoiceLine
	 */
	/*  @Stephan temporary comment
	protected String executeInvoiceLineEvent(Event event) {
		String msgInvoiceLine = "";
		MInvoiceLine invoiceLine = (MInvoiceLine) getPO(event);
		if (!invoiceLine.getC_Invoice().isSOTrx() && invoiceLine.getC_Invoice().getC_Project_ID() > 0) {
			if (event.getTopic().equals(IEventTopics.PO_AFTER_NEW) ||
					event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE)) {
				//msgInvoiceLine = validateProjectActivity(invoiceLine);
			} 
		}
		return msgInvoiceLine;
	}
	*/

	private String afterPosted(PO po){
		GenericPO document = new GenericPO(po.get_TableName(), po.getCtx(), 
				po.get_ID(), po.get_TrxName());
		
		String docStatus = document.get_ValueAsString("DocStatus");
		Timestamp dateAcct = (Timestamp) document.get_Value("DateAcct");
		if(po.get_TableName().equals(MInventory.Table_Name))
			dateAcct = (Timestamp) document.get_Value("MovementDate");
		dateAcct = Util.removeTime(dateAcct);
		
		//  delete fact if date acct before 2017-01-01
		if(docStatus.equals(DocAction.STATUS_Reversed) && dateAcct.before(TimeUtil.getDay(2017, 01, 01))){
			
			int reversal_id = document.get_ValueAsInt("Reversal_ID");
			GenericPO reversal = new GenericPO(document.get_TableName(), 
					document.getCtx(), reversal_id, document.get_TrxName());
			
			Timestamp dateAcctRev = (Timestamp) reversal.get_Value("DateAcct");
			if(po.get_TableName().equals(MInventory.Table_Name))
				dateAcctRev = (Timestamp) document.get_Value("MovementDate");
			dateAcctRev = Util.removeTime(dateAcctRev);
			
			//  if reverse correct
			if(dateAcct.equals(dateAcctRev)){
				MFactAcct.deleteEx(document.get_Table_ID(), document.get_ID(), document.get_TrxName());
				MFactAcct.deleteEx(reversal.get_Table_ID(), reversal.get_ID(), reversal.get_TrxName());
			}
		}
		return "";
	}

}
