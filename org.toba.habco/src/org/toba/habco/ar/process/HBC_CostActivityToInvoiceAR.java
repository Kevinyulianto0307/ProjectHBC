package org.toba.habco.ar.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.MCostActivity;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MMatchCostActivity;
import org.toba.habco.model.X_T_ReferenceCode;

public class HBC_CostActivityToInvoiceAR extends SvrProcess {
//
//	int p_AllocationCode = 0;
	int M_PriceList_ID = 0;
	int C_Tax_ID = 0;
	int C_Currency_ID = 0;
	int C_Invoice_ID=0;
	String PaymentRule = "";
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
//			else if (para[i].getParameterName().equalsIgnoreCase("AllocationCode")) {
//				p_AllocationCode = para[i].getParameterAsInt();
//			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_Tax_ID")) {
				C_Tax_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("M_PriceList_ID")) {
				M_PriceList_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("C_Currency_ID")) {
				C_Currency_ID = para[i].getParameterAsInt();
			}
			else if (para[i].getParameterName().equalsIgnoreCase("PaymentRule")) {
				PaymentRule = para[i].getParameterAsString();
			}
			else {
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		}
		
		C_Invoice_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		String infoMsg = "";
		
//		if(p_AllocationCode > 0)
//			infoMsg = CreateSingleARInvoice();
//		else
			infoMsg = CreateMultipleARInvoice();
		
		return infoMsg;
	}
//	
//	/**
//	 * create single invoice
//	 * @return null
//	 */
//	public String CreateSingleARInvoice(){
//		int doctypeid = new Query(getCtx(),MDocType.Table_Name,"Name='AR Invoice' AND AD_Client_ID="+getAD_Client_ID(),get_TrxName()).firstId();
//		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
//		invoice.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
//		invoice.setC_Currency_ID(C_Currency_ID);
//		invoice.setC_DocType_ID(doctypeid);
//		invoice.setC_DocTypeTarget_ID(doctypeid);
//		invoice.setDateAcct(new Timestamp(System.currentTimeMillis()));
//		invoice.setM_PriceList_ID(M_PriceList_ID);
//		invoice.setPaymentRule(PaymentRule);
//		invoice.setIsSOTrx(true);
//		
//		int[] HBC_CostActivity_IDs = getCostActivities();
//		int C_BPartner_ID=0;
//		int Line = 0;
//		
//		for(int HBC_CostActivity_ID : HBC_CostActivity_IDs){
//			X_HBC_CostActivity costActivity = new X_HBC_CostActivity(getCtx(), HBC_CostActivity_ID, get_TrxName());
//			
//			if(costActivity.get_ValueAsBoolean("IsInvoicedAR"))
//				continue;
//			
//			if(costActivity.isChargeEmployee()){
//				MEmployee employee = new MEmployee(getCtx(), costActivity.getHC_Employee_ID(), get_TrxName());
//				C_BPartner_ID=employee.getC_BPartner_ID();
//			}else{
//				C_BPartner_ID=costActivity.getC_BPartner_ID();
//			}
//			
//			int BPartnerLocation_ID = new Query(getCtx(),MBPartnerLocation.Table_Name,"C_BPartner_ID="+C_BPartner_ID,get_TrxName()).firstId();
//			invoice.setC_BPartner_ID(C_BPartner_ID);
//			invoice.setC_BPartner_Location_ID(BPartnerLocation_ID);
//			invoice.saveEx();
//			costActivity.set_ValueOfColumn("IsInvoicedAR", true);
//			costActivity.saveEx();
//			if(costActivity.get_ValueAsBoolean("IsAgentInvoices")){
//				if(costActivity.getHBC_Barge_ID()>0 && costActivity.getHBC_Tugboat_ID()>0)
//					setpercentage(costActivity.getHBC_Tugboat_ID(), costActivity.getHBC_Barge_ID(), costActivity.getHBC_CostActivity_ID(), invoice.getC_Invoice_ID(), Line,get_TrxName());
//				else {
//				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
//				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
//				invoiceLine.setC_Tax_ID(C_Tax_ID);
//				invoiceLine.setLine(Line+=10);
//				invoiceLine.setC_Charge_ID(costActivity.getC_Charge_ID());
//				invoiceLine.setC_Project_ID(costActivity.getHBC_Position().getC_Project_ID());
//				invoiceLine.setQtyEntered(Env.ONE);
//				invoiceLine.setQtyInvoiced(Env.ONE);
//				invoiceLine.setM_Product_ID(costActivity.getM_Product_ID());
//				invoiceLine.setPriceActual(costActivity.getAmount());
//				invoiceLine.setPriceEntered(costActivity.getAmount());
//				invoiceLine.setPriceList(costActivity.getAmount());
//				if(costActivity.getHBC_ShipActivity()
//						.getHBC_Position().getHBC_Trip_ID()>0)
//				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costActivity.getHBC_ShipActivity()
//						.getHBC_Position().getHBC_Trip_ID());
//				if(costActivity.getHBC_Barge_ID()>0)
//					invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costActivity.getHBC_Barge_ID());
//				if(costActivity.getHBC_Tugboat_ID()>0)
//					invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costActivity.getHBC_Tugboat_ID());
//				invoiceLine.saveEx();
//				}
//			}
//		}
//		setIsInvoice();
//		String message = Msg.parseTranslation(getCtx(), "@Generated AR Invoice@ " + invoice.getDocumentNo());
//		addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
//		
//		return "";
//	}

	/**
	 * create multiple invoice
	 * @return invoice created
	 */
	public String CreateMultipleARInvoice(){
		
		StringBuilder infoMsg = new StringBuilder();
		//List<String> listInvoiceNo = new ArrayList<String>();
		
		int doctypeid = new Query(getCtx(),MDocType.Table_Name,"Name='AR Invoice' AND AD_Client_ID="+getAD_Client_ID(),get_TrxName()).firstId();
		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
		invoice.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		invoice.setC_Currency_ID(C_Currency_ID);
		invoice.setC_DocType_ID(doctypeid);
		invoice.setC_DocTypeTarget_ID(doctypeid);
		invoice.setDateAcct(new Timestamp(System.currentTimeMillis()));
		invoice.setM_PriceList_ID(M_PriceList_ID);
		invoice.setPaymentRule(PaymentRule);
		invoice.setIsSOTrx(true);
		
		
		for (int refCode : getAllReferenceCode()) {
			//int[] HBC_CostActivity_IDs = getCostActivities(refCode);
			int[] M_MatchCostActivity_IDs = getCostActivities(refCode);
			int Line = 0;
			int C_BPartner_ID=0;
			
			for(int M_MatchCostActivity_ID : M_MatchCostActivity_IDs){
//				X_HBC_CostActivity costActivity = new X_HBC_CostActivity(getCtx(), HBC_CostActivity_ID, get_TrxName());
				MMatchCostActivity matchCost = new MMatchCostActivity(getCtx(),M_MatchCostActivity_ID,get_TrxName());
				MCostActivity costActivity = new MCostActivity(getCtx(), matchCost.getHBC_CostActivity_ID(), get_TrxName());
				if(costActivity.get_ValueAsBoolean("IsInvoicedAR"))
					continue;
				
				if(costActivity.isChargeEmployee()){
					MEmployee employee = new MEmployee(getCtx(), costActivity.getHC_Employee_ID(), get_TrxName());
					C_BPartner_ID=employee.getC_BPartner_ID();
				}else{
					C_BPartner_ID=costActivity.getC_BPartner_ID();
				}
				
				int BPartnerLocation_ID = new Query(getCtx(),MBPartnerLocation.Table_Name,"C_BPartner_ID="+C_BPartner_ID,get_TrxName()).firstId();
				invoice.setC_BPartner_ID(C_BPartner_ID);
				invoice.setC_BPartner_Location_ID(BPartnerLocation_ID);
				invoice.saveEx();
				if(costActivity.get_ValueAsBoolean("IsAgentInvoices")){
//					if(costActivity.getHBC_Barge_ID()>0 && costActivity.getHBC_Tugboat_ID()>0)
//    					setpercentage(costActivity.getHBC_Tugboat_ID(), costActivity.getHBC_Barge_ID(), costActivity.getHBC_CostActivity_ID(), invoice.getC_Invoice_ID(), Line,get_TrxName(),matchCost.getM_MatchCostActivity_ID());
//					else {
					MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
					invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
					invoiceLine.setC_Tax_ID(C_Tax_ID);
					invoiceLine.setLine(Line+=10);
					invoiceLine.setC_Charge_ID(costActivity.getC_Charge_ID());
					invoiceLine.setC_Project_ID(costActivity.getHBC_Position().getC_Project_ID());
					invoiceLine.setQtyEntered(Env.ONE);
					invoiceLine.setQtyInvoiced(Env.ONE);
					invoiceLine.setM_Product_ID(matchCost.getM_Product_ID());
					invoiceLine.setPriceActual(matchCost.getAmount());
					invoiceLine.setPriceEntered(matchCost.getAmount());
					invoiceLine.setPriceList(matchCost.getAmount());
					if(costActivity.getHBC_ShipActivity()
							.getHBC_Position().getHBC_Trip_ID()>0)
					invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costActivity.getHBC_ShipActivity()
							.getHBC_Position().getHBC_Trip_ID());
					if(costActivity.getHBC_Barge_ID()>0)
						invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costActivity.getHBC_Barge_ID());
					if(costActivity.getHBC_Tugboat_ID()>0)
						invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costActivity.getHBC_Tugboat_ID());
					invoiceLine.saveEx();
//    				}
				}
			}
			String message = Msg.parseTranslation(getCtx(), "@Generated AR Invoice@ " + invoice.getDocumentNo());
			addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
			
			infoMsg.append(message);
			setMultipleIsInvoice(getAllReferenceCode());
		}
		
//		listInvoiceNo.add(invoice.getDocumentNo());
//		for (String invoiceNo : listInvoiceNo) {
//			infoMsg.append(invoiceNo);
//			infoMsg.append(", ");
//		}
		//String message = Msg.parseTranslation(getCtx(), "@Generated AR Invoice@ " + invoice.getDocumentNo());
		//addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		
		return infoMsg.toString();
	}
	
	/**
	 *split invoiceline by tugboat and barge percentage
	 */
//	protected int setpercentage(int HBC_Tugboat_ID, int HBC_Barge_ID,int HBC_CostActivity_ID,int C_Invoice_ID,int Line,String trxName,int M_MatchCostActivity_ID){
//		MInvoice invoice = new MInvoice(Env.getCtx(),C_Invoice_ID,trxName);
//		X_HBC_CostActivity costactivity = new X_HBC_CostActivity(Env.getCtx(), HBC_CostActivity_ID, trxName);
//		
//		for(int i =0;i<2;i++){
//			if(i==0){
//				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
//				MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
//				if(product.get_Value("HBC_Percentage_Split_TugBoat")==null){
//					throw new AdempiereException("Please fill Percentage Tugboat!");
//				}
//				BigDecimal percentagetugboat = new BigDecimal(product.get_ValueAsString("HBC_Percentage_Split_TugBoat"));
//				BigDecimal totalamount = costactivity.getAmount().multiply(percentagetugboat);
//				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
//				invoiceLine.setC_Tax_ID(C_Tax_ID);
//				invoiceLine.setLine(Line+=10);
//				invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
//				invoiceLine.setQtyEntered(Env.ONE);
//				invoiceLine.setQtyInvoiced(Env.ONE);
//				invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
//				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
//				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
//				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
//				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
//						.getHBC_Position().getHBC_Trip_ID());
//				invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
//				invoiceLine.saveEx();
//				
//			}else if(i==1){
//				MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
//				if(product.get_Value("HBC_Percentage_Split_Barge")==null){
//					throw new AdempiereException("Please fill Percentage Barge!");
//				}
//				BigDecimal percentagebarge = new BigDecimal(product.get_ValueAsString("HBC_Percentage_Split_Barge"));
//				BigDecimal totalamount = costactivity.getAmount().multiply(percentagebarge);
//				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
//				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
//				invoiceLine.setC_Tax_ID(C_Tax_ID);
//				invoiceLine.setLine(Line+=10);
//				invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
//				invoiceLine.setQtyEntered(Env.ONE);
//				invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
//				invoiceLine.setQtyInvoiced(Env.ONE);
//				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
//				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
//				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
//				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
//						.getHBC_Position().getHBC_Trip_ID());
//				invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
//				invoiceLine.saveEx();
//				
//			}
//		}
//		return 0;
//	}
	
	/**
	 * get cost activity with match parameter process reference code
	 * @return array of id cost activity
	 */
//	protected int[] getCostActivities(){
//		
//		String where = "AllocationCode="+p_AllocationCode;
//			
//		int[] HBC_CostActivity_IDs = new Query(getCtx(), X_HBC_CostActivity.Table_Name, where, get_TrxName())
//			.setClient_ID()
//			.setOnlyActiveRecords(true)
//			.getIDs();
//		
//		return HBC_CostActivity_IDs;
//	}
	
	/**
	 * get cost activity with match reference code
	 * @param reference code
	 * @return array if id cost activity
	 */
	protected int[] getCostActivities(int refCode){
		
		String where = "AllocationCode="+refCode;
//		comment by yonk for get cost activity from match cost activity
//		int[] HBC_CostActivity_IDs = new Query(getCtx(), X_HBC_CostActivity.Table_Name, where, get_TrxName())
//			.setClient_ID()
//			.setOnlyActiveRecords(true)
//			.getIDs();
		
		int[] M_MatchCostActivity_IDs= new Query(getCtx(),MMatchCostActivity.Table_Name,where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.getIDs();
		
		return M_MatchCostActivity_IDs;
	}
	
//	/**
//	 * set is invoiced
//	 */
//	protected void setIsInvoice(){
//		int refcode = new Query(getCtx(),X_T_ReferenceCode.Table_Name,"AllocationCode="+p_AllocationCode,get_TrxName()).firstId();
//		X_T_ReferenceCode referencecode = new X_T_ReferenceCode(getCtx(), refcode, get_TrxName());
//		referencecode.set_ValueOfColumn("IsInvoicedAR", true);
//		referencecode.saveEx();
//	}
	
	
	/**
	 * set multiple is invoiced
	 */
	protected void setMultipleIsInvoice(List<Integer> AllocCodes){
//	 	int[] refCodeIDs = new Query(getCtx(), X_T_ReferenceCode.Table_Name, "IsInvoicedAR='N'", get_TrxName())
//		.setClient_ID()
//		.setOnlyActiveRecords(true)
//		.getIDs();
//	 		for(int refcode:refCodeIDs){
//			 	X_T_ReferenceCode referencecode = new X_T_ReferenceCode(getCtx(), refcode, get_TrxName());
//				referencecode.set_ValueOfColumn("IsInvoicedAR", true);
//				referencecode.saveEx();
//		 	}
		for(int AllocCode:AllocCodes){
			int refcodeId = new Query(getCtx(), X_T_ReferenceCode.Table_Name, "AllocationCode="+AllocCode, get_TrxName())
					.setClient_ID()
					.setOnlyActiveRecords(true)
					.firstId();
			X_T_ReferenceCode referencecode = new X_T_ReferenceCode(getCtx(), refcodeId, get_TrxName());
			referencecode.set_ValueOfColumn("IsInvoicedAR", true);
			referencecode.saveEx();
		}
	}

	/**
	 * get all reference code and not invoiced
	 * @return list reference code
	 */
	protected List<Integer> getAllReferenceCode(){
// comment by yonk
//		int[] refCodeIDs = new Query(getCtx(), X_T_ReferenceCode.Table_Name, "IsInvoicedAR='N'", get_TrxName())
//		.setClient_ID()
//		.setOnlyActiveRecords(true)
//		.getIDs();
		
		int[] MatchCodeIds = new Query(getCtx(), MMatchCostActivity.Table_Name,"C_Invoice_ID="+C_Invoice_ID,get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.getIDs();
		
		List<Integer> listRef = new ArrayList<Integer>();
		int i=0;
		for (int id : MatchCodeIds) {
			MMatchCostActivity matchcost = new MMatchCostActivity(getCtx(), id, get_TrxName());
			
			if(listRef.size()==0){
				listRef.add(matchcost.getAllocationCode());
			}else if(listRef.get(i)!=matchcost.getAllocationCode()){
				listRef.add(matchcost.getAllocationCode());
				i++;
			}
			
		}
		
		return listRef;
	}
	
}
