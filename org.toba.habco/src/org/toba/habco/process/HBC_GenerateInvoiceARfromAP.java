package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;

/**
 * @author Phie Albert
 */

public class HBC_GenerateInvoiceARfromAP extends SvrProcess{

	private int p_C_Invoice_ID = 0;
	private int p_C_DocType_ID = 0;
	private int p_C_Tax_ID = 0 ;
	private Timestamp p_dateInvoiced = null;
	private ArrayList<Integer> businessPartnerAR_ID = new ArrayList<Integer>();
	
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Tax_ID"))
				p_C_Tax_ID = para[i].getParameterAsInt();
			else if (name.equals("DateInvoiced"))
				p_dateInvoiced = para[i].getParameterAsTimestamp();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_Invoice_ID = getRecord_ID();
	}
	
	protected String doIt() throws Exception {
	
		if (p_C_Invoice_ID==0) 
			throw new AdempiereException("No Invoice AP selected..");
		
		if (p_C_DocType_ID==0) 
			throw new AdempiereException("Document Type is mandatory..");
		
		if (p_dateInvoiced==null)
			throw new AdempiereException("Date Invoiced is mandatory..");
		
		HBC_MInvoice invAP = new HBC_MInvoice(getCtx(), p_C_Invoice_ID, get_TrxName());
		
		if(!invAP.getDocStatus().equals(DocAction.ACTION_Complete))
			throw new AdempiereException("Invoice AP must be complete..");
		
		String where = "EXISTS(SELECT 1 FROM C_InvoiceLine "
				+ "WHERE C_Invoice_ID = ? AND isAgentInvoices = 'Y')";
		boolean exists = new Query(getCtx(),MInvoiceLine.Table_Name,where,get_TrxName())
		.setParameters(p_C_Invoice_ID)
		.setOnlyActiveRecords(true)
		.match();
		
		if(!exists)
			throw new AdempiereException("There's no data on invoice line that Agent Invoices is selected.. ");
		
		where = "EXISTS(SELECT 1 FROM C_InvoiceLine "
				+ "WHERE C_Invoice_ID = ? AND isAgentInvoices = 'Y' AND AR_Invoice_ID is null)";
		boolean allowCreated = new Query(getCtx(),MInvoiceLine.Table_Name,where,get_TrxName())
		.setParameters(p_C_Invoice_ID)
		.setOnlyActiveRecords(true)
		.match();
		
		if(!allowCreated)
			throw new AdempiereException("All lines have already created AR Debit Memo Invoice..");
			
		for(MInvoiceLine lineAP : invAP.getLines())
		{
			if(lineAP.get_ValueAsBoolean("isAgentInvoices") && lineAP.get_Value("AR_Invoice_ID") == null){
				if(lineAP.get_ValueAsInt("AR_businessPartner_ID") == 0)
					throw new AdempiereException("Agent Invoice is selected, but Bussiness Partner AR is empty");
				
				if(!businessPartnerAR_ID.contains(lineAP.get_ValueAsInt("AR_businessPartner_ID")))
					businessPartnerAR_ID.add(lineAP.get_ValueAsInt("AR_businessPartner_ID"));
			}
		}
		
		//if(businessPartnerAR_ID.size() == 0)
			//throw new AdempiereException("There's no data to create Invoice AR");
		
		String Desc = "Generate AR Invoice : ";
		boolean set = false;
		
		for(int i=0 ; i < businessPartnerAR_ID.size() ; i++)
		{
			HBC_MInvoice invAR = new HBC_MInvoice(getCtx(), 0, get_TrxName());
			//user
			String whereClause = MUser.COLUMNNAME_C_BPartner_ID+"=?";
			int AD_User_ID = new Query(getCtx(), MUser.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setParameters(new Object[]{businessPartnerAR_ID.get(i)})
			.firstId();
			//bp location
			whereClause = MBPartnerLocation.COLUMNNAME_C_BPartner_ID+"=?";
			int bp_location_id = new Query(getCtx(), MBPartnerLocation.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setParameters(new Object[]{businessPartnerAR_ID.get(i)})
			.firstId();
			
			invAR.setAD_Org_ID(invAP.getAD_Org_ID());
			invAR.setC_BPartner_ID(businessPartnerAR_ID.get(i));
			invAR.setAD_User_ID(AD_User_ID);
			invAR.setC_BPartner_Location_ID(bp_location_id);
			invAR.setC_DocTypeTarget_ID(p_C_DocType_ID);
			invAR.setIsSOTrx(true);
			invAR.setDateInvoiced(p_dateInvoiced);
			invAR.setDateAcct(p_dateInvoiced);
			invAR.setC_Tax_ID(p_C_Tax_ID); //default exempt and only exempt
			invAR.setDescription("Generated from AP Invoice "+invAP.getDocumentNo());
			if(invAP.get_ValueAsBoolean("isNoPriceList"))
				invAR.set_ValueOfColumn("isNoPriceList", true);
			else
				invAR.setM_PriceList_ID(invAP.getM_PriceList_ID());
			invAR.setC_Currency_ID(invAP.getC_Currency_ID());
			invAR.setSalesRep_ID(invAP.getSalesRep_ID());
			invAR.setPaymentRule(invAP.getPaymentRule());
			invAR.setC_PaymentTerm_ID(invAP.getC_PaymentTerm_ID());
			invAR.setC_Campaign_ID(invAP.getC_Campaign_ID());
			invAR.saveEx();
			
			for(MInvoiceLine invLineAP : invAP.getLines())
			{
				if(invLineAP.get_ValueAsInt("AR_businessPartner_ID") 
						!= businessPartnerAR_ID.get(i))
					continue;
				
				/*
				 * FROM Functional
				 * Note : Tax always exempt, NO PPN, NO PPH (withholding), product on line not asset
				 */
				HBC_MInvoiceLine invLineAR = new HBC_MInvoiceLine(getCtx(), 0, get_TrxName());
				invLineAR.setC_Invoice_ID(invAR.getC_Invoice_ID());
				invLineAR.setAD_Org_ID(invLineAP.getAD_Org_ID());
				invLineAR.setC_Campaign_ID(invLineAP.getC_Campaign_ID());
				if(invLineAP.getM_Product_ID() > 0)
					invLineAR.setM_Product_ID(invLineAP.getM_Product_ID());
				else if(invLineAP.getC_Charge_ID() > 0)
					invLineAR.setC_Charge_ID(invLineAP.getC_Charge_ID());
				invLineAR.setLine(invLineAP.getLine());
				invLineAR.setQtyEntered(invLineAP.getQtyEntered());
				invLineAR.setQtyInvoiced(invLineAP.getQtyInvoiced());
				invLineAR.setC_UOM_ID(invLineAP.getC_UOM_ID());
				invLineAR.setPriceActual(invLineAP.getPriceActual());
				invLineAR.setPriceEntered(invLineAP.getPriceEntered());
				invLineAR.setPriceList(invLineAP.getPriceList());
				invLineAR.setC_Tax_ID(p_C_Tax_ID);
				invLineAR.set_ValueOfColumn("OriginalPriceList", (invLineAP.get_Value("OriginalPriceList") == null) ? Env.ZERO : invLineAP.get_Value("OriginalPriceList"));
				invLineAR.setDiscount(invLineAP.getDiscount());
				invLineAR.set_ValueOfColumn("HBC_Tugboat_ID", invLineAP.get_Value("HBC_Tugboat_ID"));
				invLineAR.set_ValueOfColumn("HBC_Barge_ID", invLineAP.get_Value("HBC_Barge_ID"));
				invLineAR.set_ValueOfColumn("HBC_Percentage_Split_Tugboat", invLineAP.get_Value("HBC_Percentage_Split_Tugboat"));
				invLineAR.set_ValueOfColumn("HBC_Percentage_Split_Barge", invLineAP.get_Value("HBC_Percentage_Split_Barge"));
				invLineAR.saveEx();
				
				//match - delete when reverse inv AR 
				invLineAP.set_ValueOfColumn("AR_Invoice_ID", invAR.getC_Invoice_ID());
				invLineAP.saveEx();
			}
			
			if(!set)
			{
				invAP.setDescription(Desc);
				set = true;
			}
			
			invAR.processIt(DocAction.ACTION_Complete);
			invAR.saveEx();
			invAP.setDescription(invAP.getDescription().concat(invAR.getDocumentNo()+"|"));
			invAP.saveEx();
			String message = Msg.parseTranslation(getCtx(), "@Generated@ " +  invAR.getDocumentNo());
			addBufferLog(0, null, BigDecimal.ZERO, message, invAR.get_Table_ID(), invAR.getC_Invoice_ID());
		}
		return "";
	}
}
