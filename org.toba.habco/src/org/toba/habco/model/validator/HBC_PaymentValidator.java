package org.toba.habco.model.validator;

import java.math.BigDecimal;

import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.osgi.service.event.Event;

public class HBC_PaymentValidator {
	
	//Payment
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MPayment payment = (MPayment)po;
		if (event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE))
			msg = afterSave(payment);
		//@Phie Temporary Comment
		//if(event.getTopic().equals(IEventTopics.DOC_BEFORE_PREPARE))
			//msg = checkChargeHeader(payment);
		return msg;
		}
		
	private static String checkChargeHeader(MPayment payment) {
	
		if(payment.getC_Charge_ID() > 0)
			throw new AdempiereException("Aborted.. Please fill charge in Allocate Charge Tab..");
		return "";
	}

	public static String afterSave(MPayment payment){
		
		String msg = "";
		BigDecimal payAmt = payment.getPayAmt();
		
		if(payAmt == null )
			payAmt = Env.ZERO;
		//Temporary Comment by @TommyAng (based on reverse issue)
		/*
		if(payAmt.compareTo(Env.ZERO) < 0)
			msg = "Payment Amount cannot be less than 0";
		*/
		if(payment.getC_Currency().getISO_Code().equals("IDR")){
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_Payment SET PayAmt=ROUND(PayAmt,0) WHERE C_Payment_ID=?");
			DB.executeUpdate(sb.toString(), payment.get_ID(), payment.get_TrxName());
		}
		else{
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_Payment SET PayAmt=ROUND(PayAmt,2) WHERE C_Payment_ID=?");
			DB.executeUpdate(sb.toString(), payment.get_ID(), payment.get_TrxName());
		}
		
		return msg;
	}
}
