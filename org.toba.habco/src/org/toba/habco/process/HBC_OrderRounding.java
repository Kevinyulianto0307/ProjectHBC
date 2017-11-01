package org.toba.habco.process;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author TommyAng
 * Process Rounding Invoice
 */
public class HBC_OrderRounding extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int C_Order_ID = getRecord_ID();
		MOrder order = new MOrder(getCtx(), C_Order_ID, get_TrxName());
		int C_Currency_ID = order.getC_Currency_ID();							//IDR = 303
		
		if(C_Currency_ID == 303){
			
			//  rounding line net amt
			for (MOrderLine orderline : order.getLines()) {
				
				// line round line net amt, rounding half up 0, half_up
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE C_OrderLine SET LineNetAmt=ROUND(LineNetAmt,0) WHERE C_OrderLine_ID=?");
				int no = DB.executeUpdate(sb.toString(), orderline.get_ID(), get_TrxName());
				log.info("UPDATED LineNetAmt C_OrderLine#"+no);
			}
			
			//  set total lines header
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_Order SET TotalLines = (SELECT SUM(LineNetAmt) FROM C_OrderLine WHERE C_Order_ID=?) WHERE C_Order_ID=?");
			int no = DB.executeUpdate(sb.toString(), new Object[]{C_Order_ID, C_Order_ID}, true, get_TrxName());
			log.info("UPDATED TotalLines C_Order#"+no);
			
			//	round down ,00 C_OrderTax tax amt
			sb = new StringBuilder();
			sb.append("UPDATE C_OrderTax SET TaxAmt=TRUNC(TaxAmt) WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), C_Order_ID, get_TrxName());
			log.info("UPDATED C_OrderTax TaxAmt C_Order#"+no);
			
			//	round down ,00 C_Order tax amt
			sb = new StringBuilder();
			sb.append("UPDATE C_Order SET TaxAmt=TRUNC(TaxAmt) WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), C_Order_ID, get_TrxName());
			log.info("UPDATED C_Order TaxAmt C_Order#"+no);
			
			//  recalculate grand total
			sb = new StringBuilder();
			sb.append("UPDATE C_Order SET GrandTotal=COALESCE(TotalLines,0)+COALESCE((SELECT TaxAmt FROM C_OrderTax WHERE C_Order_ID=?),0) "
					+ "WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), new Object[]{C_Order_ID, C_Order_ID}, true, get_TrxName());
			log.info("UPDATED GrandTotal C_Order#"+no);
			
		}else{
			
			//  rounding line net amt
			for (MOrderLine orderline : order.getLines()) {
				
				// line round line net amt, rounding half up 0, half_up
				StringBuilder sb = new StringBuilder();
				sb.append("UPDATE C_OrderLine SET LineNetAmt=ROUND(LineNetAmt,2) WHERE C_OrderLine_ID=?");
				int no = DB.executeUpdate(sb.toString(), orderline.get_ID(), get_TrxName());
				log.info("UPDATED LineNetAmt C_OrderLine#"+no);
			}

			//  set total lines header
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_Order SET TotalLines = (SELECT SUM(LineNetAmt) FROM C_InvoiceLine WHERE C_Order_ID=?) WHERE C_Order_ID=?");
			int no = DB.executeUpdate(sb.toString(), new Object[]{C_Order_ID, C_Order_ID}, true, get_TrxName());
			log.info("UPDATED TotalLines C_Order#"+no);
			
			//	round half up ,2 C_OrderTax tax amt
			sb = new StringBuilder();
			sb.append("UPDATE C_OrderTax SET TaxAmt=TRUNC(TaxAmt,2) WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), C_Order_ID, get_TrxName());
			log.info("UPDATED C_OrderTax TaxAmt C_Order#"+no);		
			
			//	round half up ,2 C_Order tax amt
			sb = new StringBuilder();
			sb.append("UPDATE C_Order SET TaxAmt=TRUNC(TaxAmt,2) WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), C_Order_ID, get_TrxName());
			log.info("UPDATED C_Order TaxAmt C_Order#"+no);			
			
			//  recalculate grand total
			sb = new StringBuilder();
			sb.append("UPDATE C_Order SET GrandTotal=COALESCE(TotalLines,0)+COALESCE((SELECT TaxAmt FROM C_OrderTax WHERE C_Order_ID=?),0) "
					+ "WHERE C_Order_ID=?");
			no = DB.executeUpdate(sb.toString(), new Object[]{C_Order_ID, C_Order_ID}, true, get_TrxName());
			log.info("UPDATED GrandTotal C_Order#"+no);
		}
				
		return "";
	}
	
}
