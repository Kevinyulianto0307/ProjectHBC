package org.toba.habco.ar.process;

import java.sql.Timestamp;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.MARCalculation;

public class HBC_CalculateToInvoice extends SvrProcess{

	//protected int p_HBC_Trip_ID = 0;
	//protected int p_M_Product_ID = 0;
	
	/**parameter*/
	protected int p_C_DocType_ID = 0;
	protected int p_C_PaymentTerm_ID = 0;
	protected int p_C_Currency_ID = 0;
	protected int p_M_PriceList_ID = 0;
	protected String p_PaymentRule = "";
	protected int p_C_Tax_ID = 0;
	protected int p_C_Project_ID = 0;
	protected int p_C_ConversionType_ID = 0;
	protected Timestamp p_dateInvoiced = null;	
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			//else if (name.equals("HBC_Trip_ID")){
			//	p_HBC_Trip_ID = para[i].getParameterAsInt();
			//}
			//else if (name.equals("M_Product_ID")){
			//	p_M_Product_ID = para[i].getParameterAsInt();
			//}
			//@phie
			else if(name.equals("DateInvoiced")){
				p_dateInvoiced = para[i].getParameterAsTimestamp();
			}//end phie
			else if (name.equals("C_DocType_ID")){
				p_C_DocType_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_PaymentTerm_ID")){
				p_C_PaymentTerm_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_Currency_ID")){
				p_C_Currency_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("M_PriceList_ID")){
				p_M_PriceList_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("PaymentRule")){
				p_PaymentRule = para[i].getParameterAsString();
			}
			else if (name.equals("C_Tax_ID")){
				p_C_Tax_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_Project_ID")){
				p_C_Project_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_ConversionType_ID")){
				p_C_ConversionType_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		/*
		String where = "HBC_Trip_ID="+p_HBC_Trip_ID;
		
		int calc_id = new Query(getCtx(), MARCalculation.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstId();
		*/
		
		int HBC_ARCalculation_ID = getRecord_ID();
		
		MARCalculation calc = new MARCalculation(getCtx(), HBC_ARCalculation_ID, get_TrxName());
		/*
		MContract contract = (MContract) calc.getHBC_Contract();
		if(!contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment)){
			if(!calc.isProcessed())
				return "AR Calculation must be completed";
		}*/
		
		HBC_MInvoice invoice = new HBC_MInvoice(getCtx(), 0, get_TrxName());
		invoice.setAD_Org_ID(calc.getAD_Org_ID());
		invoice.setC_DocType_ID(p_C_DocType_ID);
		invoice.setC_DocTypeTarget_ID(p_C_DocType_ID);
		invoice.setC_PaymentTerm_ID(p_C_PaymentTerm_ID);
		invoice.setC_Currency_ID(p_C_Currency_ID);
		invoice.setM_PriceList_ID(p_M_PriceList_ID);
		invoice.setPaymentRule(p_PaymentRule);
		invoice.setC_Tax_ID(p_C_Tax_ID);
		invoice.setC_Project_ID(p_C_Project_ID);
		invoice.setC_BPartner_ID(calc.getHBC_Contract().getCustomer_BPartner_ID());
		
		//@phie set user contact
		String whereClause = MUser.COLUMNNAME_C_BPartner_ID+"=?";
		int AD_User_ID = new Query(getCtx(), MUser.Table_Name, whereClause, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setParameters(new Object[]{invoice.getC_BPartner_ID()})
		.firstId();
		
		if(AD_User_ID > 0)
			invoice.setAD_User_ID(AD_User_ID);
		//end phie
		
		//@phie set hardcode campaign id pekan baru
		invoice.setC_Campaign_ID(1000004);
		//end phie
		if(!invoice.getCurrencyISO().equals("IDR")){
			invoice.setC_ConversionType_ID(p_C_ConversionType_ID);
		}
		
		MBPartner partner = new MBPartner(getCtx(), calc.getHBC_Contract().getCustomer_BPartner_ID(), get_TrxName());
		MBPartnerLocation[] locations = partner.getLocations(true);
		
		invoice.setC_BPartner_Location_ID(locations[0].getC_BPartner_Location_ID());
		
		/* comment by phie
		Timestamp date = calc.getFinishDate();
		if(date == null){
			date = new Timestamp(System.currentTimeMillis());
		}
		invoice.setDateAcct(date);
		invoice.setDateInvoiced(date);
		*/
		
		//@phie
		invoice.setDateAcct(p_dateInvoiced);
		invoice.setDateInvoiced(p_dateInvoiced);
		//end phie
		
		MDocType docType = new MDocType(getCtx(), p_C_DocType_ID, get_TrxName());
		
		invoice.setC_Project_ID(calc.getC_Project_ID());
		invoice.setIsSOTrx(docType.isSOTrx());
		//invoice.set_ValueOfColumn("HBC_Trip_ID", calc.getHBC_Trip_ID());
		invoice.saveEx();

		/*
		for (MARCalculationLine calcLine : calc.getCalculationLine()) {

			MInvoiceLine invoiceLine = new MInvoiceLine(getCtx(), 0,get_TrxName());
			invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
			invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
			invoiceLine.setQtyInvoiced(calcLine.getQtyCharge());
			invoiceLine.setQtyEntered(calcLine.getQtyCharge());
			invoiceLine.setPriceEntered(calcLine.getUnitPrice());
			invoiceLine.setPriceActual(calcLine.getUnitPrice());
			invoiceLine.setPriceList(calcLine.getUnitPrice());
			invoiceLine.setM_AttributeSetInstance_ID(0);
			invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
			invoiceLine.setM_Product_ID(p_M_Product_ID);
			invoiceLine.setLineNetAmt();
			invoiceLine.saveEx();
			
			calcLine.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
			calcLine.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
			calcLine.saveEx();
		}// end for 2
		*/
		
		String message = Msg.parseTranslation(getCtx(), "@GeneratedInvoice@ " + invoice.getDocumentNo());
		addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		
		return "";
	}

}
