package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MMatchInv;
import org.compiere.model.MMatchPO;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MOrder;

/*
 * Author @phie
 * Habco 
 */

public class TCS_GenerateInvoiceFromInOut extends SvrProcess{

	private int			p_C_DocType_ID = 0;
	private int			p_M_InOut_ID = 0;
	private Timestamp	p_DateInvoiced = null;
	private String		p_docAction = null;
	private int		p_M_PriceList_ID = 0;
	private int 	p_C_Tax_ID = 0;
	private boolean p_isNoPriceList = false;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("DateInvoiced"))
				p_DateInvoiced = para[i].getParameterAsTimestamp();
			else if (name.equals("DocAction"))
				p_docAction = para[i].getParameterAsString();
			else if (name.equals("M_PriceList_ID"))
				p_M_PriceList_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Tax_ID"))
				p_C_Tax_ID = para[i].getParameterAsInt();
			else if (name.equals("IsNoPriceList"))
				p_isNoPriceList = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}

	@Override
	protected String doIt() throws Exception {
		p_M_InOut_ID = getRecord_ID();

		if (p_M_InOut_ID==0)
			return "No Material Receipt";

		if (log.isLoggable(Level.INFO)) log.info("M_InOut_ID=" + p_M_InOut_ID 
				+ ", C_DocType_ID=" + p_C_DocType_ID);
		
		if(p_isNoPriceList && p_M_PriceList_ID > 0)
			throw new AdempiereException("Please don't fill price list, if IsNoPriceList is true");
		
		MInOut inout = new MInOut (getCtx(), p_M_InOut_ID, get_TrxName());
		if (!MInOut.DOCSTATUS_Completed.equals(inout.getDocStatus()))
			throw new IllegalArgumentException("Material Receipt not completed");
		
		HBC_MOrder order = new HBC_MOrder(getCtx(), inout.getC_Order_ID(), get_TrxName());
		boolean isNoPriceList = order.get_ValueAsBoolean("isNoPriceList");
		if((p_isNoPriceList && p_isNoPriceList != isNoPriceList) || 
				(!p_isNoPriceList && p_M_PriceList_ID != order.getM_PriceList_ID()))
			throw new IllegalArgumentException("Price list must be same with price list in PO");
		
		String where = "M_InOutLine_ID IN (SELECT M_InOutLine_ID FROM M_InOutLine WHERE M_InOut_ID=?)";

		boolean matchInv = new Query(getCtx(),MMatchInv.Table_Name,where,null)
		.setParameters(p_M_InOut_ID)
		.setOnlyActiveRecords(true)
		.match();

		if (matchInv)
		{		
			//@phie cek kalau ada match inv yg reversal_id masih null berarti masih connect ke suatu invoice
			MInOutLine[] shipLines = inout.getLines(false);

			for (MInOutLine sLine: shipLines)
			{
				String where2 = "EXISTS(SELECT * FROM M_MatchInv WHERE M_InOutLine_ID = ? AND COALESCE(reversal_id,NULL,0) = 0)";

				boolean check = new Query(getCtx(),MMatchInv.Table_Name,where2,null)
				.setParameters(sLine.getM_InOutLine_ID())
				.setOnlyActiveRecords(true)
				.match();
				
				if(check)
					return "Process aborted.. This document has been converted to invoice";
			}
		}
			

		boolean matchPO = new Query(getCtx(),MMatchPO.Table_Name,where,null)
		.setParameters(p_M_InOut_ID)
		.setOnlyActiveRecords(true)
		.match();

		if (!matchPO)
			return "Process aborted.. Material receipt is not matched to any Purchase Order";

		HBC_MInvoice invoice = new HBC_MInvoice (inout, p_DateInvoiced);
		invoice.setDocAction(p_docAction);
		invoice.set_ValueOfColumn("C_Campaign_ID", inout.get_Value("C_Campaign_ID"));
		if(p_M_PriceList_ID > 0)
			invoice.setM_PriceList_ID(p_M_PriceList_ID);
		if(p_isNoPriceList)
			invoice.set_ValueOfColumn("isNoPriceList", true);
		invoice.setC_Tax_ID(p_C_Tax_ID);
		invoice.setC_Currency_ID(order.getC_Currency_ID());
		invoice.saveEx();
		
		MInOutLine[] inoutLine = inout.getLines(false);
		BigDecimal valuePercentage = null;
		for (MInOutLine sLine: inoutLine)
		{
			MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
			invoiceLine.setShipLine(sLine);
			invoiceLine.set_ValueOfColumn("C_Campaign_ID", sLine.get_Value("C_Campaign_ID"));
			invoiceLine.setQtyEntered(sLine.getQtyEntered());
			invoiceLine.setQtyInvoiced(sLine.getMovementQty());
			//@KevinY HBC - 2802
			if(sLine.get_ValueAsInt("HBC_Tugboat_ID") > 0 || sLine.get_Value("HBC_Tugboat_ID")!= null)
				invoiceLine.set_ValueOfColumn("HBC_Tugboat_ID"	, (Integer) sLine.get_Value("HBC_Tugboat_ID"));
			else if(sLine.get_ValueAsInt("HBC_Barge_ID") > 0 || sLine.get_Value("HBC_Barge_ID")!= null)
				invoiceLine.set_ValueOfColumn("HBC_Barge_ID"	, (Integer) sLine.get_Value("HBC_Barge_ID"));
			
			MProduct product = new MProduct(getCtx(), sLine.getM_Product_ID(), get_TrxName());
			valuePercentage = (BigDecimal)product.get_Value("HBC_Percentage_Split_TugBoat");
			invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_TugBoat", valuePercentage);
			valuePercentage = (BigDecimal)product.get_Value("HBC_Percentage_Split_Barge");
			invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_Barge", valuePercentage);
			//@KevinY end
			
			//@phie 2703
			invoiceLine.setIsTrackAsAsset(sLine.get_ValueAsBoolean("isTrackAsAsset"));
			if(invoiceLine.get_ValueAsBoolean("isTrackAsAsset"))
			{
				invoiceLine.setA_CapvsExp("Cap");
				invoiceLine.setA_CreateAsset(true);
				invoiceLine.setA_Asset_Group_ID(invoiceLine.getM_Product().getM_Product_Category().getA_Asset_Group_ID());
			}//end phie
			
			invoiceLine.saveEx();
		}

		invoice.processIt(p_docAction);
		invoice.saveEx();

		addLog(invoice.getC_Invoice_ID(), invoice.getDateInvoiced(), invoice.getGrandTotal(), invoice.getDocumentNo(), invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		return invoice.getDocumentNo();
	}

}
