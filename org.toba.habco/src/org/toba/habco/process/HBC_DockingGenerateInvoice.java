package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectPhase;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class HBC_DockingGenerateInvoice extends SvrProcess {

	/**
	 * @author yonk
	 *
	 */
	int p_C_Project_ID=0;
	BigDecimal p_percentageService = Env.ZERO;
	BigDecimal p_percentageMaterial = Env.ZERO;
	int p_C_DocType_ID=0;
	Timestamp p_dateInvoiced = null;
	Boolean p_UpdateFlag = false;
	
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if(name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else if(name.equals("DateInvoiced"))
				p_dateInvoiced = para[i].getParameterAsTimestamp();
			else if(name.equals("isUpdate"))							//TODO Rename Columnname
				p_UpdateFlag = para[i].getParameterAsBoolean();
			
			//
			/*
			else if(name.equals("HBC_Percentage_DockingService"))
				p_percentageService=para[i].getParameterAsBigDecimal();
			else if(name.equals("HBC_Percentage_DockingMaterial"))
				p_percentageMaterial=para[i].getParameterAsBigDecimal();
				*/
		}
		p_C_Project_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
	
		//@TommyAng
		if (p_UpdateFlag) {
			MProject project = new MProject(getCtx(), getRecord_ID(), get_TrxName());
			if (project.get_ValueAsInt("C_Invoice_ID") < 1)
				return "Process aborted.. No invoice related to current docking project";
			
			MInvoice invoice = new MInvoice(getCtx(), project.get_ValueAsInt("C_Invoice_ID"), get_TrxName());
			
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM C_InvoiceLine ")
			.append("WHERE C_Invoice_ID=")
			.append(project.get_ValueAsInt("C_Invoice_ID"));
			
			int no = DB.executeUpdate(sb.toString(), get_TrxName());
			log.fine("#" + no);
			log.finest(sb.toString());
			
			
			BigDecimal PctMaterial = new BigDecimal(project.get_ValueAsString("HBC_Percentage_DockingMaterial"), new MathContext(0, RoundingMode.HALF_DOWN));
			BigDecimal PctService = new BigDecimal(project.get_ValueAsString("HBC_Percentage_DockingService"), new MathContext(0, RoundingMode.HALF_DOWN));
			
			int line = 10;
			if (project.get_ValueAsInt("M_Product_ID")>0 && project.get_ValueAsInt("HBC_Percentage_DockingMaterial")> 0) {
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setLine(line);
				invoiceLine.setDescription("Docking Material Charge");
				invoiceLine.setM_Product_ID(project.get_ValueAsInt("M_Product_ID"));
				invoiceLine.setQty(Env.ONE);
				BigDecimal projectBalance = new BigDecimal(project.get_ValueAsString("TotalAmt"));
				BigDecimal price = projectBalance.multiply(PctMaterial).divide(Env.ONEHUNDRED,0,RoundingMode.HALF_UP);
				invoiceLine.setPriceEntered(price);
				invoiceLine.setPriceActual(price);
				invoiceLine.setPriceList(price);
				invoiceLine.setC_Project_ID(project.getC_Project_ID());
				invoiceLine.setDiscount();
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.saveEx();
				line=line+10;
			}
			if (project.get_ValueAsInt("M_Product2_ID")>0 && project.get_ValueAsInt("HBC_Percentage_DockingService")> 0) {
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setLine(line);
				invoiceLine.setDescription("Docking Service Charge");
				invoiceLine.setM_Product_ID(project.get_ValueAsInt("M_Product2_ID"));
				invoiceLine.setQty(Env.ONE);
				BigDecimal projectBalance = new BigDecimal(project.get_ValueAsString("TotalAmt"));
				BigDecimal price = projectBalance.multiply(PctService).divide(Env.ONEHUNDRED,0,RoundingMode.HALF_UP);
				invoiceLine.setPriceEntered(price);
				invoiceLine.setPriceActual(price);
				invoiceLine.setPriceList(price);
				invoiceLine.setC_Project_ID(project.getC_Project_ID());
				invoiceLine.setDiscount();
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.saveEx();
			}
			
			
		}
		//end @TommyAng
		
		if(p_C_Project_ID==0)
			return "No Project Selected";
		
		if (p_C_DocType_ID==0)
			return "No Document Type Selected";
		
		MProject project = new MProject(getCtx(),p_C_Project_ID,get_TrxName());
		
		//if project has been invoiced then stop process
		if (project.get_ValueAsInt("C_Invoice_ID") > 0)
			if(p_UpdateFlag)
				return "Invoice Updated";
			else
				return "Project Has Been Invoiced";
		
		boolean match = new Query(getCtx(), MProjectPhase.Table_Name, "C_Project_ID=? AND IsComplete='N'", get_TrxName())
		.setParameters(p_C_Project_ID)
		.setOnlyActiveRecords(true)
		.match();
		
		if (match)
			return "Error: Not All Phase Are Complete. Please Check";
		
		MDocType docType = MDocType.get(getCtx(), p_C_DocType_ID);
		if (!docType.getDocBaseType().equals(MDocType.DOCBASETYPE_APInvoice))
			return "Error: Document Type Must Be AP Invoice";
		
		if (project.get_ValueAsInt("M_Product_ID")==0 && project.get_ValueAsInt("M_Product2_ID")==0)
			return "Error: Product Material and Product Service Is Missing";
		
		if (project.get_ValueAsInt("M_Product_ID")>0 && project.get_ValueAsInt("HBC_Percentage_DockingMaterial")<0)
			return "Error: Product Material and Percentage Docking Material Is Missing";
		
		if (project.get_ValueAsInt("M_Product_ID")==0 && project.get_ValueAsInt("HBC_Percentage_DockingMaterial")>0)
			return "Error: Product Material Is Missing";
		
		if (project.get_ValueAsInt("M_Product2_ID")>0 && project.get_ValueAsInt("HBC_Percentage_DockingService")<0)
			return "Error: Product Service and Percentage Docking Service Is Missing";
		
		if (project.get_ValueAsInt("M_Product2_ID")==0 && project.get_ValueAsInt("HBC_Percentage_DockingService")>0)
			return "Error: Product Service Is Missing";
		
		BigDecimal PctMaterial = new BigDecimal(project.get_ValueAsString("HBC_Percentage_DockingMaterial"), new MathContext(0, RoundingMode.HALF_DOWN));
		BigDecimal PctService = new BigDecimal(project.get_ValueAsString("HBC_Percentage_DockingService"), new MathContext(0, RoundingMode.HALF_DOWN));
		
		/*
		if (!PctMaterial.add(PctService.equals(Env.ONEHUNDRED))
			return "Total Material Percentage and Service Percentage Must Equal 100";
		*/
		
		//MProjectPhase[] phases = project.getPhases();
		
		MInvoice invoice = new MInvoice (getCtx(),0,get_TrxName());
		invoice.setDateInvoiced(p_dateInvoiced);
		invoice.setDateAcct(p_dateInvoiced);
		invoice.setClientOrg(project.getAD_Client_ID(), project.getAD_Org_ID());
		invoice.setC_BPartner_ID(project.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
		invoice.setM_PriceList_ID(project.get_ValueAsInt("M_PriceList_ID"));
		invoice.setC_Tax_ID(project.get_ValueAsInt("C_Tax_ID"));
		invoice.setC_Project_ID(project.getC_Project_ID());
		invoice.setC_DocTypeTarget_ID(p_C_DocType_ID);
		invoice.setIsSOTrx(false);
		invoice.set_ValueOfColumn("HBC_Tugboat_ID", project.get_Value("HBC_Tugboat_ID"));
		invoice.set_ValueOfColumn("HBC_Barge_ID", project.get_Value("HBC_Barge_ID"));
		invoice.setAD_User_ID(project.getAD_User_ID());
		invoice.setC_PaymentTerm_ID(project.getC_PaymentTerm_ID());
		invoice.setC_Currency_ID(project.getC_Currency_ID());
		invoice.saveEx();
		
		int line = 10;
		if (project.get_ValueAsInt("M_Product_ID")>0 && project.get_ValueAsInt("HBC_Percentage_DockingMaterial")> 0) {
			MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
			invoiceLine.setLine(line);
			invoiceLine.setDescription("Docking Material Charge");
			invoiceLine.setM_Product_ID(project.get_ValueAsInt("M_Product_ID"));
			invoiceLine.setQty(Env.ONE);
			BigDecimal projectBalance = new BigDecimal(project.get_ValueAsString("TotalAmt"));
			BigDecimal price = projectBalance.multiply(PctMaterial).divide(Env.ONEHUNDRED,0,RoundingMode.HALF_UP);
			invoiceLine.setPriceEntered(price);
			invoiceLine.setPriceActual(price);
			invoiceLine.setC_Project_ID(project.getC_Project_ID());
			invoiceLine.setPriceList(price);
			invoiceLine.setDiscount();
			invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
			invoiceLine.saveEx();
			line=line+10;
		}
		if (project.get_ValueAsInt("M_Product2_ID")>0 && project.get_ValueAsInt("HBC_Percentage_DockingService")>0) {
			MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
			invoiceLine.setLine(line);
			invoiceLine.setDescription("Docking Service Charge");
			invoiceLine.setM_Product_ID(project.get_ValueAsInt("M_Product2_ID"));
			invoiceLine.setQty(Env.ONE);
			BigDecimal projectBalance = new BigDecimal(project.get_ValueAsString("TotalAmt"));
			BigDecimal price = projectBalance.multiply(PctService).divide(Env.ONEHUNDRED,0,RoundingMode.HALF_UP);
			invoiceLine.setPriceEntered(price);
			invoiceLine.setPriceActual(price);
			invoiceLine.setPriceList(price);
			invoiceLine.setC_Project_ID(project.getC_Project_ID());
			invoiceLine.setDiscount();
			invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
			invoiceLine.saveEx();
		}
		
		project.set_CustomColumn("C_Invoice_ID", invoice.get_ID());
		project.saveEx();
		
		String message = Msg.parseTranslation(getCtx(), "@Generated@ " + invoice.getDocumentNo());
		addBufferLog(0, null, null, message, invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		return message;
	}

}
