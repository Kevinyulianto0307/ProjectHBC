package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MPriceList;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.toba.habco.model.HBC_MOrder;
import org.toba.habco.model.HBC_MOrderLine;

/**
 * 
 * @author Phie Albert
 * Re price order
 * Custom from Jorg Janke's OrderRePrice
 */
public class HBC_OrderRePrice extends SvrProcess
{

	/**	Order to re-price		*/
	private int 	p_C_Order_ID = 0;
	private int 	p_C_BPartner_ID = 0;
	private Timestamp p_dateOrderedFrom = null;
	private Timestamp p_dateOrderedTo = null;
	private Timestamp p_datePriceList = null;
	StringBuilder retValue = new StringBuilder();
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
			else if (name.equals("C_Order_ID"))
				p_C_Order_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DateOrdered")){
				p_dateOrderedFrom = para[i].getParameterAsTimestamp();
				p_dateOrderedTo = para[i].getParameter_ToAsTimestamp();
			}
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("Date"))
				p_datePriceList = para[i].getParameterAsTimestamp();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		StringBuilder msg = new StringBuilder();
		if (log.isLoggable(Level.INFO)) log.info("C_Order_ID=" + p_C_Order_ID);
		//if (p_C_Order_ID == 0)
			//throw new IllegalArgumentException("Nothing to do");
		
		if(p_datePriceList == null)
			throw new AdempiereException("Please fill date period price list");
		
		if(p_C_Order_ID > 0)
		{
			HBC_MOrder order = new HBC_MOrder(getCtx(), p_C_Order_ID, get_TrxName());
			if(!order.getDocStatus().equals(DocAction.ACTION_Complete))
				throw new AdempiereException("Order is not complete");
			if(order.hasMatchPO())
				throw new AdempiereException("Order has already receipt/invoicing");
			if(p_datePriceList.before(order.getDateOrdered()))
				throw new AdempiereException("Cannot Reprice Purchase Order because Date Ordered > Date Price List");
			msg.append(reCalcThis(p_C_Order_ID));
		}
		else if(p_C_BPartner_ID > 0)
		{
			String whereClause = " isSoTrx=? AND docStatus = ? AND AD_Client_ID = ? AND C_BPartner_ID = ? AND dateOrdered <= ?"
					+ " AND NOT EXISTS (SELECT  1 FROM M_MatchPO mpo "
					+ " JOIN C_OrderLine cil ON cil.C_OrderLine_ID = mpo.C_OrderLine_ID "
					+ " WHERE cil.C_Order_ID = C_Order.C_Order_ID)";
					
			int[] c_order_ids = new Query(getCtx(), MOrder.Table_Name, whereClause, get_TrxName())
								.setParameters(new Object[]{false, DocAction.ACTION_Complete, 
										getAD_Client_ID(),p_C_BPartner_ID, p_datePriceList})
								.getIDs();
			if(c_order_ids.length > 0)
			{
				for(int order_id : c_order_ids)
				{
					msg.append(reCalcThis(order_id));
				}
			}
		}
		/*
		else
		{
			if(p_dateOrderedFrom != null && p_dateOrderedTo == null)
				throw new AdempiereException("Please fill date ordered to..");
			if(p_dateOrderedFrom == null && p_dateOrderedTo != null)
				throw new AdempiereException("Please fill date ordered from..");
			
			if(p_dateOrderedFrom != null && p_dateOrderedTo != null)
			{
				
				String whereClause = " isSoTrx=? AND docStatus = ? AND AD_Client_ID = ? AND "
									+ "(DateOrdered BETWEEN ? AND ?) "
									+ " AND NOT EXISTS (SELECT  1 FROM M_MatchPO mpo "
									+ " JOIN C_OrderLine cil ON cil.C_OrderLine_ID = mpo.C_OrderLine_ID "
									+ " WHERE cil.C_Order_ID = C_Order.C_Order_ID)";
									
				int[] c_order_ids = new Query(getCtx(), MOrder.Table_Name, whereClause, get_TrxName())
									.setParameters(new Object[]{false, DocAction.ACTION_Complete, 
											getAD_Client_ID(), p_dateOrderedFrom, p_dateOrderedTo})
									.getIDs();
				if(c_order_ids.length > 0)
				{
					for(int order_id : c_order_ids)
					{
						msg.append(reCalcThis(order_id));
					}
				}
			}
			else
			{
				String whereClause = " isSoTrx=? AND docStatus = ? AND AD_Client_ID = ?"
									+ "AND NOT EXISTS (SELECT  1 FROM M_MatchPO mpo "
									+ "JOIN C_OrderLine cil ON cil.C_OrderLine_ID = mpo.C_OrderLine_ID "
									+ "WHERE cil.C_Order_ID = C_Order.C_Order_ID)";
						
				int[] c_order_ids = new Query(getCtx(), MOrder.Table_Name, whereClause, get_TrxName())
									.setParameters(new Object[]{false, DocAction.ACTION_Complete,
											getAD_Client_ID()})
									.getIDs();
				
				if(c_order_ids.length > 0)
				{
					for(int order_id : c_order_ids)
					{
						msg.append(reCalcThis(order_id));
					}
				}
			}
			
		}
		*/
		return msg.toString();
	}

	private String reCalcThis(int c_order_id) {

		HBC_MOrder order = new HBC_MOrder (getCtx(), c_order_id, get_TrxName());
		MPriceList pl = new MPriceList(getCtx(), order.getM_PriceList_ID(), get_TrxName());
		BigDecimal oldPrice = order.getGrandTotal();
		order.processIt(DocAction.ACTION_ReActivate);
		order.saveEx();
		HBC_MOrderLine[] lines = order.getLines();
		for (int i = 0; i < lines.length; i++)
		{
			HBC_MOrderLine oLine = lines[i];
			oLine.setProcessed(false);
			oLine.setPriceBasedOnDate(order.getM_PriceList_ID(), p_datePriceList);
			oLine.saveEx(get_TrxName());
			oLine.setLineNetAmt(oLine.getPriceEntered().multiply(oLine.getQtyOrdered()));
			oLine.saveEx(get_TrxName());
			oLine.setProcessed(true);
		}
		order.processIt(DocAction.ACTION_Complete);
		BigDecimal newPrice = order.getGrandTotal();
		order.setDescription(((order.getDescription() == null) ? "": order.getDescription())+" | "+oldPrice+"->"+newPrice);
		order.saveEx();
		retValue.append(order.getDocumentNo()+" -- ");
		//.append(":  ").
		//append(oldPrice).append(" -> ").append(newPrice).append(")");
		
		return retValue.toString();
	}
}
