package org.toba.habco.project.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.toba.habco.model.X_C_ProjectMilestone;

public class HBC_ProjectGenOrder extends SvrProcess{

	/**	Project ID from project directly		*/
	private int		m_C_Project_ID = 0;
//	
//	private int		p_M_PriceList_ID = 0;
//	
//	private int 	p_M_Warehouse_ID = 0;
	
	private int		p_C_DocType_ID = 0;
//	
//	private int 	p_C_Tax_ID = 0;

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
//			else if (name.equals("M_PriceList_ID"))
//				p_M_PriceList_ID = para[i].getParameterAsInt();
//			else if (name.equals("M_Warehouse_ID"))
//				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
//			else if (name.equals("C_Tax_ID"))
//				p_C_Tax_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_C_Project_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (log.isLoggable(Level.INFO)) log.info("C_Project_ID=" + m_C_Project_ID);
		if (m_C_Project_ID == 0)
			throw new IllegalArgumentException("C_Project_ID == 0");
		MProject fromProject = getProject (getCtx(), m_C_Project_ID, get_TrxName());
		Env.setSOTrx(getCtx(), true);	//	Set SO context

		/** @todo duplicate invoice prevention */

		MDocType docType = new MDocType(getCtx(), p_C_DocType_ID, get_TrxName());
		
		MOrder order = new MOrder (fromProject, true, MOrder.DocSubTypeSO_OnCredit);
		order.setM_PriceList_ID(fromProject.get_ValueAsInt("M_PriceList_ID"));
		order.setM_Warehouse_ID(fromProject.getM_Warehouse_ID());
		order.setC_Tax_ID(fromProject.get_ValueAsInt("C_Tax_ID"));
		order.setC_DocType_ID(p_C_DocType_ID);
		order.setC_DocTypeTarget_ID(p_C_DocType_ID);
		order.setIsSOTrx(docType.isSOTrx());
		order.saveEx();
		//		if (!order.save())
//			throw new Exception("Could not create Order");

		//	***	Lines ***
		int count = 0;
		
		//	Service Project
		/*
		if (MProject.PROJECTCATEGORY_ServiceChargeProject.equals(fromProject.getProjectCategory()))
		{
			/** @todo service project invoicing */
			//throw new Exception("Service Charge Projects are on the TODO List");
		//}
		//	Service Lines
		

		//else	//	Order Lines
		//{
			//MProjectLine[] lines = fromProject.getLines ();
			int line = 0;
			for (int id : getMilestoneIDs(m_C_Project_ID))
			{
				X_C_ProjectMilestone milestone = new X_C_ProjectMilestone(getCtx(), id, get_TrxName());
				MOrderLine ol = new MOrderLine(order);
				ol.setLine(line+=10);
				ol.setDescription(milestone.getDescription());
				//
				ol.setM_Product_ID(milestone.getM_Product_ID(), true);
				ol.setQty(Env.ONE);
				//ol.setPrice();
				BigDecimal projectBalance = fromProject.getProjectBalanceAmt();
				BigDecimal percentage = milestone.getMilestonePercentage();
				BigDecimal price = projectBalance.multiply(percentage).divide(Env.ONEHUNDRED,2,RoundingMode.HALF_UP);
				ol.setPriceEntered(price);
				ol.setPriceActual(price);
				ol.setPriceList(price);
//				if (milestone.getPlannedPrice() != null && milestone.getPlannedPrice().compareTo(Env.ZERO) != 0)
//					ol.setPrice(milestone.getPlannedPrice());
				ol.setDiscount();
				ol.setC_Tax_ID(order.getC_Tax_ID());
				if (ol.save())
					count++;
			}	//	for all lines
//			if (milestone.length != count)
//				log.log(Level.SEVERE, "Lines difference - ProjectLines=" + milestone.length + " <> Saved=" + count);
		//}	//	Order Lines

		StringBuilder msgreturn = new StringBuilder("@C_Order_ID@ ").append(order.getDocumentNo()).append(" (").append(count).append(")");
		return msgreturn.toString();
	}	//	doIt

	/**
	 * 	Get and validate Project
	 * 	@param ctx context
	 * 	@param C_Project_ID id
	 * 	@return valid project
	 * 	@param trxName transaction
	 */
	static protected MProject getProject (Properties ctx, int C_Project_ID, String trxName)
	{
		MProject fromProject = new MProject (ctx, C_Project_ID, trxName);
		if (fromProject.getC_Project_ID() == 0)
			throw new IllegalArgumentException("Project not found C_Project_ID=" + C_Project_ID);
		//if (fromProject.getM_PriceList_Version_ID() == 0)
			//throw new IllegalArgumentException("Project has no Price List");
		//if (fromProject.getM_Warehouse_ID() == 0)
			//throw new IllegalArgumentException("Project has no Warehouse");
		if (fromProject.getC_BPartner_ID() == 0 || fromProject.getC_BPartner_Location_ID() == 0)
			throw new IllegalArgumentException("Project has no Business Partner/Location");
		return fromProject;
	}	//	getProject

	protected int[] getMilestoneIDs(int C_Project_ID){
		
		String where = "C_Project_ID="+C_Project_ID;
		int[] ids = new Query(getCtx(), X_C_ProjectMilestone.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.getIDs();
		
		return ids;
	}
	
}
