package org.toba.habco.model.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderTax;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.osgi.service.event.Event;

/**
 * @author TommyAng
 * PurchaseOrder/OrderLine Validator
 */

public class HBC_OrderValidator {

	//Order
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MOrder order = (MOrder)po;
		if (event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)){
			msg = checkPriceList(order);
		}
		//@phie
		else if(event.getTopic().equals(IEventTopics.DOC_AFTER_COMPLETE)){
			msg = afterComplete(order);
		}//end phie
		//@KevinY
		/*if (event.getTopic().equals(IEventTopics.DOC_AFTER_CLOSE)){
			msg = afterClose(order);
		}*/
		//@KevinY end
		return msg;
	}

	//@KevinY HBC - 2860
	/**
	 * Validator Before Close for C_Order
	 * @param order
	 * @return
	 * Empty String if Success
	 */
	public static String afterClose(MOrder order) {
		String docTemp = order.getDocStatus();
		order.setDocStatus(order.DOCSTATUS_Closed);
		order.saveEx();
		if(!order.isSOTrx()){
			MOrderLine[] oLines = order.getLines();
			for(int i = 0 ; i < oLines.length ; i++){
				MOrderLine oLine = oLines[i];
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//1352
				String query = "SELECT M_RequisitionLine_ID from M_RequisitionLine "
						+ "WHERE lastorderline_id =?";
				List<MRequisitionLine> listLines = new ArrayList<MRequisitionLine>();
				try{
					pstmt = DB.prepareStatement(query, order.get_TrxName());
					pstmt.setInt(1, oLine.getC_OrderLine_ID());
					rs = pstmt.executeQuery();
					while(rs.next()){
						MRequisitionLine newLine = new MRequisitionLine(order.getCtx(), rs.getInt(1), order.get_TrxName());
						listLines.add(newLine);
					}
				}catch(Exception e){
					return "";
				}finally{
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
				}

				for(int j = 0 ; j < listLines.size(); j++) {
					MRequisitionLine rLine = new MRequisitionLine(order.getCtx(), 
							listLines.get(j).getM_RequisitionLine_ID(), order.get_TrxName());
					MRequisition requisition = new MRequisition(order.getCtx(), rLine.getM_Requisition_ID(), order.get_TrxName());
					int C_Order_ID = 0;
					query =" col.M_Product_ID = ? AND C_Order.M_Warehouse_ID = ? AND C_Order.DocStatus='CO' ";
					C_Order_ID = new Query(order.getCtx(), MOrder.Table_Name, query, order.get_TrxName())
					.addJoinClause(" INNER JOIN C_OrderLine col ON col.C_Order_ID = C_Order.C_Order_ID")
					.setParameters(rLine.getM_Product_ID(), requisition.getM_Warehouse_ID())
					.setOrderBy(" C_Order.DateOrdered DESC, C_Order.C_Order_ID DESC ")
					.firstId();
					if(C_Order_ID <= 0){
						rLine.set_ValueOfColumn("DateLastOrdered", null);
						rLine.set_ValueOfColumn("lastpricepo", null);
						rLine.set_ValueOfColumn("lastorderline_id", null);
					}else{
						MOrder newOrder = new MOrder(order.getCtx(), C_Order_ID, order.get_TrxName());
						MOrderLine[] newoLines = newOrder.getLines();
						for(MOrderLine newoLine: newoLines) {
							//set lastpricePo, lastOrderline_ID in requisitionline based on last Purchase orderline 
							if(newoLine.getM_Product_ID() == rLine.getM_Product_ID()){
								rLine.set_ValueOfColumn("DateLastOrdered"	, newOrder.getDateOrdered());
								rLine.set_ValueOfColumn("lastpricepo"	 	, newoLine.getPriceEntered());
								rLine.set_ValueOfColumn("lastorderline_id"	, newoLine.getC_OrderLine_ID());
							}//endIf
						}//endFor
					}//endElse
					rLine.saveEx();
				}//endFor
			}//endFor
		}//endIf
		order.setDocStatus(docTemp);
		order.saveEx();
		return "";
	}//beforeClose
	//@KevinY end

	//@phie 2703
	private static String afterComplete(MOrder order) {
		//phie
		for(MOrderLine oLine : order.getLines()) {
			if (oLine.getC_Tax_ID() > 0 && oLine.getC_Tax().isSummary()) {
				if (!order.calculateTaxTotal())	//	setTotals
					return "Error calculating Tax";
			}
		}
		
		int precision = order.getC_Currency().getStdPrecision();
		
		for(MOrderTax tax: order.getTaxes(true)){
			tax.setTaxAmt(tax.getTaxAmt().setScale(precision, RoundingMode.DOWN));
			tax.saveEx();
		}
		
		//end phie
		
		//@phie 
		String whereClause = "C_Order_ID=? AND IsTrackAsAsset='Y'";
		boolean match = new Query(order.getCtx(), MOrderLine.Table_Name, whereClause, order.get_TrxName())
		.setParameters(order.get_ID())
		.setOnlyActiveRecords(true)
		.match();
		
		if (match){
			order.setIsTrackAsAsset(true);
			order.saveEx();
		}
		//end phie
		
		return "";
	}//end phie

	//OrderLine
	public static String executeLineEvent(Event event, PO po){
		String msg = "";
		MOrderLine line = (MOrderLine)po;
		if (event.getTopic().equals(IEventTopics.PO_BEFORE_CHANGE)){
			msg = beforeSave(line);
		}else if (event.getTopic().equals(IEventTopics.PO_BEFORE_NEW)){
			msg = beforeSave(line);
		}
		return msg;
	}

	public static String checkPriceList(MOrder order){

		String msg = "";

		for (MOrderLine line : order.getLines()) {
			BigDecimal priceEntered = line.getPriceEntered();
			BigDecimal priceList = line.getPriceList();
			int C_Charge_ID = line.getC_Charge_ID();

			if (line.getM_Product_ID() > 0 && line.getC_Charge_ID() <=0){
				if(priceEntered.compareTo(BigDecimal.ZERO) <= 0){
					msg = "Price cannot be empty";
					return msg;
				}else if(priceList.compareTo(BigDecimal.ZERO) <= 0 && !order.get_ValueAsBoolean("IsNoPriceList")){
					msg = "Pricelist cannot be empty";
					return msg;
				}
			}
		}
		return msg;
		//@TommyAng
		/* commented by @Stephan
		BigDecimal DiscountAmt = (BigDecimal) order.get_Value("DiscountAmt");
		BigDecimal TotalLines = (BigDecimal) order.get_Value("TotalLines");
		BigDecimal DiscountPO = Env.ZERO;
		if(DiscountAmt.compareTo(Env.ZERO)>0 && TotalLines.compareTo(Env.ZERO)>0)
			DiscountPO = DiscountAmt.divide(TotalLines,4,RoundingMode.HALF_UP).multiply(Env.ONEHUNDRED);
		order.set_ValueOfColumn("DiscountPO", DiscountPO);
		order.saveEx();
		 */
		//end @TommyAng


	}

	public static String beforeSave(MOrderLine line){

		String msg = "";
		BigDecimal Discount = line.getDiscount();
		BigDecimal DiscAmt = (BigDecimal) line.get_Value("DiscAmt");

		//@TommyAng Discount Amount & Discount
		if(DiscAmt == null )
			DiscAmt = Env.ZERO;
		if(Discount == null)
			Discount = Env.ZERO;

		if(Discount.compareTo(Env.ZERO) < 0 || DiscAmt.compareTo(Env.ZERO) <0)
			msg = "Discount cannot be less than 0";
		//end Discount Amount & Discount

		//@TommyAng Rounding PriceEntered & LineNetAmt half up (IDR ,00 || ELSE ,**)
		BigDecimal priceEntered = line.getPriceEntered();
		BigDecimal lineNetAmt = Env.ZERO;
		BigDecimal qty = line.getQtyEntered();

		if(line.getC_Currency().getISO_Code().equals("IDR")){
			line.setPriceEntered(priceEntered.setScale(5, RoundingMode.HALF_UP));
			line.setPriceList(line.getPriceList().setScale(5, RoundingMode.HALF_UP));
			lineNetAmt = (priceEntered.multiply(qty)).setScale(0, RoundingMode.HALF_UP);
			line.setLineNetAmt(lineNetAmt);
		}
		else{
			line.setPriceEntered(priceEntered.setScale(5, RoundingMode.HALF_UP));
			line.setPriceList(line.getPriceList().setScale(5, RoundingMode.HALF_UP));
			lineNetAmt = (priceEntered.multiply(qty)).setScale(2, RoundingMode.HALF_UP);
			line.setLineNetAmt(lineNetAmt);
		}
		//end Rounding

		return msg;
	}
}
