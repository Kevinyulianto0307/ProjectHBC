package org.toba.habco.model.validator;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.osgi.service.event.Event;

/**
 * HBC - 2860 
 * @author KevinY
 * Validator for M_Requisition
 */
public class HBC_RequisitionValidator {
	
	/**
	 * Main event for M_Requisition
	 * @param event
	 * @param po
	 * @return
	 * String msg
	 */
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MRequisition requisition= new MRequisition(po.getCtx(), po.get_ID(), po.get_TrxName());
		
		/*
		if(event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)){
			msg = beforeComplete(requisition);
		}
		*/
		
		if(event.getTopic().equals(IEventTopics.DOC_AFTER_PREPARE)){
			msg = getPreviousOrderLine(requisition);
		}
		return msg;
	}//executeEvent
	
	private static String getPreviousOrderLine(MRequisition requisition) {
		MRequisitionLine[] reqLines = requisition.getLines();
		for(MRequisitionLine reqLine : reqLines)
		{
			/*
			String sql = "SELECT C_OrderLine_ID FROM C_OrderLine "
					+ "JOIN C_Order ON C_Order.C_Order_ID=C_OrderLine.C_Order_ID "
					+ "WHERE M_Product_ID=? "
					+ "AND DateOrdered < ? "
					+ "AND C_Order.DocStatus IN (?, ?) ";
			*/
			String whereClause = " M_Product_ID = ? AND C_Order.M_Warehouse_ID = ? AND C_Order.DateOrdered < ? AND C_Order.DocStatus IN (?,?)";
			int LastOrderLine_ID = new Query(reqLine.getCtx(), MOrderLine.Table_Name, whereClause, reqLine.get_TrxName())
										.addJoinClause(" JOIN C_Order ON C_Order.C_Order_ID = C_OrderLine.C_Order_ID ")
										.setParameters(new Object[]{reqLine.getM_Product_ID(), reqLine.getM_Requisition().getM_Warehouse_ID(),
												reqLine.getM_Requisition().getDateDoc(), 
												DocAction.STATUS_Completed, DocAction.STATUS_Closed})
										.setOrderBy(" C_Order.dateOrdered DESC")
										.firstId();
			
			if(LastOrderLine_ID !=  -1)
			{
				MOrder lastOrder = new MOrder(reqLine.getCtx(), LastOrderLine_ID, reqLine.get_TrxName());
				reqLine.set_ValueOfColumn("LastOrderLine_ID", LastOrderLine_ID);
				reqLine.set_ValueOfColumn("DateLastOrdered"	, lastOrder.getDateOrdered());
				reqLine.saveEx();
			}
		}
		return "";
	}
	
	/**
	 * Validator before Complete
	 * @param requisition
	 * @return
	 * Empty String if Sucess
	 */
	private static String beforeComplete(MRequisition requisition) {
		MRequisitionLine[] rlines = requisition.getLines();
		
		int C_Order_ID 		= 0;
		MOrder order 		= null;
		MOrderLine[] oLines = null;
		
		String query =" col.M_Product_ID = ? AND C_Order.M_Warehouse_ID = ? AND C_Order.DocStatus = '"+ order.DOCSTATUS_Completed+"'";
		for(int i=0; i< rlines.length; i++)
		{
			C_Order_ID = 0;
			MRequisitionLine rLine = rlines[i];
			
			C_Order_ID = new Query(requisition.getCtx(), MOrder.Table_Name, query, requisition.get_TrxName())
						.addJoinClause(" INNER JOIN C_OrderLine col ON col.C_Order_ID = C_Order.C_Order_ID")
						.setParameters(rLine.getM_Product_ID(), requisition.getM_Warehouse_ID())
						.setOrderBy(" C_Order.DateOrdered DESC, C_Order.Created DESC")
						.firstId();
			
			order = new MOrder(requisition.getCtx(), C_Order_ID, requisition.get_TrxName());
			oLines = order.getLines();
			
			for(MOrderLine oLine: oLines){
				//set lastpricePo, lastOrderline_ID in requisitionline based on last Purchase orderline 
				if(oLine.getM_Product_ID() == rLine.getM_Product_ID()){
					rLine.set_ValueOfColumn("DateLastOrdered"	, order.getDateOrdered());
					rLine.set_ValueOfColumn("lastpricepo"	 	, oLine.getPriceEntered());
					rLine.set_ValueOfColumn("lastorderline_id"	, oLine.getC_OrderLine_ID());
					rLine.saveEx();
				}//endIf
			}//endFor
		}//endFor
		return "";
	}//aftercomplete
}//endClass
