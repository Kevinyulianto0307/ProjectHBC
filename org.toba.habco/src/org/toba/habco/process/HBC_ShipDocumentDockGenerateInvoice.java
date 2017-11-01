package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;
import org.toba.habco.model.MShipDocumentDock;

/**
 * @author TommyAng
 */

public class HBC_ShipDocumentDockGenerateInvoice extends SvrProcess{
	
	int p_C_Tax_ID = 0;
	Timestamp p_DateInvoiced = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if(name.equals("C_Tax_ID"))
				p_C_Tax_ID = para[i].getParameterAsInt();
			else if(name.equals("DateInvoiced"))
				p_DateInvoiced = para[i].getParameterAsTimestamp();
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		MProject project = new MProject(getCtx(), getRecord_ID(), get_TrxName());
		
		String where = "C_Project_ID="+project.get_ID()+" and isInvoiced='N' and C_BPartner_ID is not null";
		int sDDock_ID = new Query(getCtx(), MShipDocumentDock.Table_Name, where, get_TrxName())
			.setOnlyActiveRecords(true)
			.setClient_ID()
			.firstId();
		
		MShipDocumentDock sDDock = new MShipDocumentDock(getCtx(), sDDock_ID, get_TrxName());
		HBC_MInvoice invoice = null;
		
		if(sDDock.get_ValueAsInt("C_Invoice_ID")>0){
			invoice = new HBC_MInvoice(getCtx(), sDDock.get_ValueAsInt("C_Invoice_ID"), get_TrxName());
		}else{
			//create new invoice
			invoice = new HBC_MInvoice(getCtx(), 0, get_TrxName());
			invoice.setC_BPartner_ID(sDDock.get_ValueAsInt("C_BPartner_ID"));
			invoice.setAD_Org_ID(sDDock.getAD_Org_ID());
			invoice.setC_Currency_ID(project.getC_Currency_ID());
			invoice.setM_PriceList_ID(sDDock.getM_PriceList_ID());
			invoice.setIsSOTrx(false);
			invoice.setDateInvoiced(p_DateInvoiced);
			invoice.setDateAcct(p_DateInvoiced);
			invoice.setC_Tax_ID(p_C_Tax_ID);
			invoice.saveEx();
		}
		
		for(int dockID : getTempIDs(getRecord_ID())){
			
			int line = getLastLine(invoice.get_ID());
			
			MShipDocumentDock dock = new MShipDocumentDock(getCtx(), dockID, get_TrxName());
			
			if(dock.getC_BPartner_ID() == sDDock.getC_BPartner_ID() && !dock.get_ValueAsBoolean("isInvoiced")){
				
				HBC_MInvoiceLine invoiceLine = new HBC_MInvoiceLine(invoice);
				
				//set invoiceline from shipdocumentdock
				invoiceLine.setLine(line);
				invoiceLine.setM_Product_ID(dock.getM_Product_ID());
				invoiceLine.setQty(dock.getQty());
				invoiceLine.setPriceList(dock.getPriceList());
				invoiceLine.setPriceEntered(dock.getAmt());
				invoiceLine.setPriceActual(dock.getAmt());
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				
				//set invoiceline from project docking
				if(project.get_ValueAsInt("HBC_TugBoat_ID")>0 && project.get_ValueAsBoolean("isTugBoat")){
					invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", project.get_ValueAsInt("HBC_TugBoat_ID"));
				}else if(project.get_ValueAsInt("HBC_Barge_ID")>0){
					invoiceLine.set_ValueOfColumn("HBC_Barge_ID", project.get_ValueAsInt("HBC_Barge_ID"));
				}
				
				invoiceLine.saveEx();
				
				//update shipdocument after invoiced
				dock.set_ValueOfColumn("C_Invoice_ID", invoice.get_ID());
				dock.set_ValueOfColumn("C_InvoiceLine_ID", invoiceLine.get_ID());
				dock.set_ValueOfColumn("isInvoiced", true);
				dock.saveEx();
			}
		}
		
		String message = Msg.parseTranslation(getCtx(), "@Generated@ " +  invoice.getDocumentNo());
		addBufferLog(0, null, BigDecimal.ZERO, message, invoice.get_Table_ID(), invoice.get_ID());		
		return invoice.getDocumentNo();
	}
	
	/**
	 * @param HBC_Trip_ID
	 * @return temporary table for calculation
	 */
	protected int[] getTempIDs(int C_Project_ID){
		String where = "C_Project_ID="+C_Project_ID;
		int[] ids = new Query(getCtx(), MShipDocumentDock.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			//.setOrderBy("Line")
			.getIDs();
		
		if(ids.length <=0 )
			log.severe("There is no record");
		
		return ids;
	}
	
	protected int getLastLine(int C_Invoice_ID){		
		
		String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_InvoiceLine WHERE C_Invoice_ID=?";
		int ii = DB.getSQLValue (get_TrxName(), sql, C_Invoice_ID);
		
		return ii;
	}
}
