package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;
import org.compiere.model.X_C_Charge;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.X_HBC_CostActivity;

public class HBC_CostActivityToInvoice extends SvrProcess{

	int p_HBC_TugBoat_ID = 0;
	String p_ReferenceCode = "";
	
	
	int C_BPartner_ID = 0;
	int C_BPartner_Location_ID = 0;
	int M_PriceList_ID = 0;
	int C_Tax_ID = 0;
	int C_Currency_ID = 0;
	int C_Project_ID = 0;
	int C_PaymentTerm_ID = 0;
	String PaymentRule = "";
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (para[i].getParameterName().equalsIgnoreCase("HBC_TugBoat_ID")) {
				p_HBC_TugBoat_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("ReferenceCode")) {
				p_ReferenceCode = para[i].getParameterAsString();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_BPartner_ID")) {
				C_BPartner_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_BPartner_Location_ID")) {
				C_BPartner_Location_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("M_PriceList_ID")) {
				M_PriceList_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_Tax_ID")) {
				C_Tax_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_Currency_ID")) {
				C_Currency_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_Project_ID")) {
				C_Project_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_PaymentTerm_ID")) {
				C_PaymentTerm_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("PaymentRule")) {
				PaymentRule = para[i].getParameterAsString();
			}
			else {
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_HBC_TugBoat_ID <= 0 && p_ReferenceCode.isEmpty())
			return "Fill the parameter";
		
		int doctypeid = new Query(getCtx(),MDocType.Table_Name,"Name='AP Invoice' AND AD_Client_ID="+getAD_Client_ID(),get_TrxName()).firstId();
		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
		invoice.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		invoice.setC_BPartner_ID(C_BPartner_ID);
		invoice.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		invoice.setC_Currency_ID(C_Currency_ID);
		invoice.setC_DocType_ID(doctypeid);
		invoice.setC_DocTypeTarget_ID(doctypeid);
		invoice.setC_PaymentTerm_ID(C_PaymentTerm_ID);
		invoice.setDateAcct(new Timestamp(System.currentTimeMillis()));
		invoice.setM_PriceList_ID(M_PriceList_ID);
		invoice.setPaymentRule(PaymentRule);
		invoice.setIsSOTrx(false);
		invoice.saveEx();
		
		int[] HBC_CostActivity_IDs = getCostActivities();
		int Line = 0;
		int isAgentInvoice=0;
		for(int HBC_CostActivity_ID : HBC_CostActivity_IDs){
			X_HBC_CostActivity costActivity = new X_HBC_CostActivity(getCtx(), HBC_CostActivity_ID, get_TrxName());
			
			if(costActivity.getHBC_Barge_ID()>0 && costActivity.getHBC_Tugboat_ID()>0)
				setpercentage(costActivity.getHBC_Tugboat_ID(), costActivity.getHBC_Barge_ID(), HBC_CostActivity_ID, invoice.getC_Invoice_ID(), Line);
			else {
			MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
			invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
			invoiceLine.setC_Tax_ID(C_Tax_ID);
			invoiceLine.setLine(Line+=10);
			if(costActivity.getC_Charge_ID()>0)
			invoiceLine.setC_Charge_ID(costActivity.getC_Charge_ID());
			if(costActivity.get_ValueAsInt("M_Product_ID")>0)
			invoiceLine.setM_Product_ID(costActivity.get_ValueAsInt("M_Product_ID"));
			invoiceLine.setQtyEntered(Env.ONE);
			invoiceLine.setQtyInvoiced(Env.ONE);
			invoiceLine.setPriceActual(costActivity.getAmount());
			invoiceLine.setPriceEntered(costActivity.getAmount());
			invoiceLine.setPriceList(costActivity.getAmount());
			if(costActivity.getHBC_ShipActivity()
					.getHBC_Position().getHBC_Trip_ID()>0)
			invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costActivity.getHBC_ShipActivity()
					.getHBC_Position().getHBC_Trip_ID());
			if(costActivity.getHBC_Barge_ID()>0)
				invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costActivity.getHBC_Barge_ID());
			if(costActivity.getHBC_Tugboat_ID()>0)
				invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costActivity.getHBC_Tugboat_ID());
			invoiceLine.saveEx();
			}
			if(costActivity.get_ValueAsBoolean("IsAgentInvoices")){
				isAgentInvoice=1;
			}
		}
		
		if(isAgentInvoice>0)
			CreateARInvoice();
		
		String message = Msg.parseTranslation(getCtx(), "@GeneratedInvoice@ " + invoice.getDocumentNo());
		addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		
		return "";
	}
	
	public int CreateARInvoice(){
		int doctypeid = new Query(getCtx(),MDocType.Table_Name,"Name='AR Invoice' AND AD_Client_ID="+getAD_Client_ID(),get_TrxName()).firstId();
		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
		invoice.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		invoice.setC_BPartner_ID(C_BPartner_ID);
		invoice.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		invoice.setC_Currency_ID(C_Currency_ID);
		invoice.setC_DocType_ID(doctypeid);
		invoice.setC_DocTypeTarget_ID(doctypeid);
		invoice.setC_PaymentTerm_ID(C_PaymentTerm_ID);
		invoice.setDateAcct(new Timestamp(System.currentTimeMillis()));
		invoice.setM_PriceList_ID(M_PriceList_ID);
		invoice.setPaymentRule(PaymentRule);
		invoice.setIsSOTrx(true);
		invoice.saveEx();
		
		int[] HBC_CostActivity_IDs = getCostActivities();
		int Line = 0;
		
		for(int HBC_CostActivity_ID : HBC_CostActivity_IDs){
			X_HBC_CostActivity costActivity = new X_HBC_CostActivity(getCtx(), HBC_CostActivity_ID, get_TrxName());
			if(costActivity.get_ValueAsBoolean("IsAgentInvoices")){
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
				invoiceLine.setC_Tax_ID(C_Tax_ID);
				invoiceLine.setLine(Line+=10);
				invoiceLine.setC_Charge_ID(costActivity.getC_Charge_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setPriceActual(costActivity.getAmount());
				invoiceLine.setPriceEntered(costActivity.getAmount());
				invoiceLine.setPriceList(costActivity.getAmount());
				if(costActivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID()>0)
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costActivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				if(costActivity.getHBC_Barge_ID()>0)
					invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costActivity.getHBC_Barge_ID());
				if(costActivity.getHBC_Tugboat_ID()>0)
					invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costActivity.getHBC_Tugboat_ID());
				invoiceLine.saveEx();
			}
		}
		
		String message = Msg.parseTranslation(getCtx(), "@Generated AR Invoice@ " + invoice.getDocumentNo());
		addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		
		return 0;
	}

	protected int[] getCostActivities(){
		
		String where = "";
		
		if(p_HBC_TugBoat_ID > 0 && !p_ReferenceCode.isEmpty())
			where = "HBC_TugBoat_ID="+p_HBC_TugBoat_ID
			+" AND ReferenceCode='"+p_ReferenceCode+"'";
		
		else if(p_HBC_TugBoat_ID > 0)
			where = "HBC_TugBoat_ID="+p_HBC_TugBoat_ID;
		
		else if(!p_ReferenceCode.isEmpty())
			where = "ReferenceCode='"+p_ReferenceCode+"'";
			
		int[] HBC_CostActivity_IDs = new Query(getCtx(), X_HBC_CostActivity.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.getIDs();
		
		return HBC_CostActivity_IDs;
	}
	
	
	protected int setpercentage(int HBC_Tugboat_ID, int HBC_Barge_ID,int HBC_CostActivity_ID,int C_Invoice_ID,int Line){
		MInvoice invoice = new MInvoice(getCtx(),C_Invoice_ID,get_TrxName());
		X_HBC_CostActivity costactivity = new X_HBC_CostActivity(getCtx(), HBC_CostActivity_ID, get_TrxName());
		
		for(int i =0;i<2;i++){
			if(i==0){
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				X_C_Charge charge = new X_C_Charge(getCtx(), costactivity.getC_Charge_ID(), get_TrxName());
				BigDecimal percentagetugboat = new BigDecimal(charge.get_ValueAsString("HBC_Percentage_Split_TugBoat"));
				BigDecimal totalamount = costactivity.getAmount().multiply(percentagetugboat);
				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
				invoiceLine.setC_Tax_ID(C_Tax_ID);
				invoiceLine.setLine(Line+=10);
				invoiceLine.setC_Charge_ID(costactivity.getC_Charge_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
				invoiceLine.saveEx();
				
			}else if(i==1){
				X_C_Charge charge = new X_C_Charge(getCtx(), costactivity.getC_Charge_ID(), get_TrxName());
				BigDecimal percentagebarge = new BigDecimal(charge.get_ValueAsString("HBC_Percentage_Split_Barge"));
				BigDecimal totalamount = costactivity.getAmount().multiply(percentagebarge);
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
				invoiceLine.setC_Tax_ID(C_Tax_ID);
				invoiceLine.setLine(Line+=10);
				invoiceLine.setC_Charge_ID(costactivity.getC_Charge_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
				invoiceLine.saveEx();
				
			}
		}
		
		
		return 0;
	}
	
}
