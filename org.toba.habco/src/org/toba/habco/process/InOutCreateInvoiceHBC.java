package org.toba.habco.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MCurrency;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderPaySchedule;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
 
/**
 *	Create (Generate) Invoice from Shipment
 *	
 *  @author Jorg Janke
 *  @version $Id: InOutCreateInvoice.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class InOutCreateInvoiceHBC extends SvrProcess
{
	/**	Shipment					*/
	private int 	p_M_InOut_ID = 0;
	/**	Price List Version			*/
	private int		p_M_PriceList_ID = 0;
	/* Document No					*/
	private String	p_InvoiceDocumentNo = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_PriceList_ID"))
				p_M_PriceList_ID = para[i].getParameterAsInt();
			else if (name.equals("InvoiceDocumentNo"))
				p_InvoiceDocumentNo = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_M_InOut_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Create Invoice.
	 *	@return document no
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		if (log.isLoggable(Level.INFO)) log.info("M_InOut_ID=" + p_M_InOut_ID 
			+ ", M_PriceList_ID=" + p_M_PriceList_ID
			+ ", InvoiceDocumentNo=" + p_InvoiceDocumentNo);
		if (p_M_InOut_ID == 0)
			throw new IllegalArgumentException("No Shipment");
		//
		MInOut ship = new MInOut (getCtx(), p_M_InOut_ID, get_TrxName());
		if (ship.get_ID() == 0)
			throw new IllegalArgumentException("Shipment not found");
		if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
			throw new IllegalArgumentException("Shipment not completed");
		
		MInvoice invoice = new MInvoice (ship, null);
		// Should not override pricelist for RMA
		if (p_M_PriceList_ID != 0 && ship.getM_RMA_ID() == 0)
			invoice.setM_PriceList_ID(p_M_PriceList_ID);
		if (p_InvoiceDocumentNo != null && p_InvoiceDocumentNo.length() > 0)
			invoice.setDocumentNo(p_InvoiceDocumentNo);
		if (!invoice.save())
			throw new IllegalArgumentException("Cannot save Invoice");
		MInOutLine[] shipLines = ship.getLines(false);
		for (int i = 0; i < shipLines.length; i++)
		{
			MInOutLine sLine = shipLines[i];
			MInvoiceLine line = new MInvoiceLine(invoice);
			line.setShipLine(sLine);
			if (sLine.sameOrderLineUOM())
				line.setQtyEntered(sLine.getQtyEntered());
			else
				line.setQtyEntered(sLine.getMovementQty());
			line.setQtyInvoiced(sLine.getMovementQty());
			
			//@phie 2703
			line.setIsTrackAsAsset(sLine.get_ValueAsBoolean("isTrackAsAsset"));
			if(line.get_ValueAsBoolean("isTrackAsAsset"))
			{
				line.setA_CapvsExp("Cap");
				line.setA_CreateAsset(true);
				line.setA_Asset_Group_ID(line.getM_Product().getM_Product_Category().getA_Asset_Group_ID());
			}//end phie
			
			if (!line.save())
				throw new IllegalArgumentException("Cannot save Invoice Line");
		}
				
		if (invoice.getC_Order_ID() > 0) {
			MOrder order = new MOrder(getCtx(), invoice.getC_Order_ID(), get_TrxName());
			invoice.setPaymentRule(order.getPaymentRule());
			invoice.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
			invoice.saveEx();
			invoice.load(invoice.get_TrxName()); // refresh from DB
			// copy payment schedule from order if invoice doesn't have a current payment schedule
			MOrderPaySchedule[] opss = MOrderPaySchedule.getOrderPaySchedule(getCtx(), order.getC_Order_ID(), 0, get_TrxName());
			MInvoicePaySchedule[] ipss = MInvoicePaySchedule.getInvoicePaySchedule(getCtx(), invoice.getC_Invoice_ID(), 0, get_TrxName());
			if (ipss.length == 0 && opss.length > 0) {
				BigDecimal ogt = order.getGrandTotal();
				BigDecimal igt = invoice.getGrandTotal();
				BigDecimal percent = Env.ONE;
				if (ogt.compareTo(igt) != 0)
					percent = igt.divide(ogt, 10, BigDecimal.ROUND_HALF_UP);
				MCurrency cur = MCurrency.get(order.getCtx(), order.getC_Currency_ID());
				int scale = cur.getStdPrecision();
			
				for (MOrderPaySchedule ops : opss) {
					MInvoicePaySchedule ips = new MInvoicePaySchedule(getCtx(), 0, get_TrxName());
					PO.copyValues(ops, ips);
					if (percent != Env.ONE) {
						BigDecimal propDueAmt = ops.getDueAmt().multiply(percent);
						if (propDueAmt.scale() > scale)
							propDueAmt = propDueAmt.setScale(scale, BigDecimal.ROUND_HALF_UP);
						ips.setDueAmt(propDueAmt);
					}
					ips.setC_Invoice_ID(invoice.getC_Invoice_ID());
					ips.setAD_Org_ID(ops.getAD_Org_ID());
					ips.setProcessing(ops.isProcessing());
					ips.setIsActive(ops.isActive());
					ips.saveEx();
				}
			}
			invoice.validatePaySchedule();
			invoice.saveEx();
		}
		
		addLog(invoice.getC_Invoice_ID(), invoice.getDateInvoiced(), invoice.getGrandTotal(), invoice.getDocumentNo(), invoice.get_Table_ID(), invoice.getC_Invoice_ID());
		
		return invoice.getDocumentNo();
	}	//	InOutCreateInvoice
	
}	//	InOutCreateInvoice

