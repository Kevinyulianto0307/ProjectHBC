package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MLocator;
import org.compiere.model.MMatchPO;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInOut;

/**
 * @Author Kevin Yulianto
 * Habco #2461
 * Generate In out From Order
 */
public class TCS_GenerateInOutFromOrder extends SvrProcess{

	int p_C_DocType_ID 			= 0;
	int p_C_Order_ID 			= 0;
	int p_M_Warehouse_ID 		= 0;
	int p_M_Locator_ID 			= 0;
	String p_DocumentAction 	= null;
	Timestamp p_MovementDate 	= null;
	

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (para[i].getParameterName().equalsIgnoreCase(
					"C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else if (para[i].getParameterName().equalsIgnoreCase(
					"M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (para[i].getParameterName().equalsIgnoreCase(
					"M_Locator_ID"))
				p_M_Locator_ID = para[i].getParameterAsInt();
			else if (para[i].getParameterName().equalsIgnoreCase(
					"MovementDate"))
				p_MovementDate = para[i].getParameterAsTimestamp();
			else if(para[i].getParameterName().equalsIgnoreCase(
					"DocAction")){
				p_DocumentAction = para[i].getParameterAsString();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}//Prepare

	@Override
	protected String doIt() throws Exception {

		p_C_Order_ID = getRecord_ID();

		if (p_C_Order_ID==0) 
			return "No Purchase order selected";

		MOrder order = new MOrder(getCtx(), p_C_Order_ID, get_TrxName());

		//doctype
		if (p_C_DocType_ID==0) 
			return "Error: DocType is Not Selected";

		MDocType docType = new MDocType(getCtx(), p_C_DocType_ID, get_TrxName());
		
		if (!docType.getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialReceipt))
			return "Error: DocType is not Material Receipt return";

		//warehouse
		if (p_M_Warehouse_ID ==0)
			return "Error: Warehouse is blank";

		//locator must be same with selected warehouse on previous parameter
		if (p_M_Locator_ID==0) 
			return "Error: Locator To is Not Selected";

		MLocator locator = MLocator.get(getCtx(), p_M_Locator_ID);
		
		if (locator.getM_Warehouse_ID()!=p_M_Warehouse_ID)
			return "Process aborted.. Locator must be inside the selected warehouse";
				
		//MovementDate
		if(p_MovementDate== null){
			return "Error: Movement Date is empty";
		}

		//DocAction
		if(p_DocumentAction == ""){
			return "Error: Document Action is empty";
		}
		
		if(!order.getDocStatus().equals("CO")){
			return "Error : Document Must be Complete";
		}

		MOrderLine[] mOrderLines = order.getLines();
		
		String whereClause = "C_OrderLine_ID IN (SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID=?)";
		boolean checkMatchPO = new Query(getCtx(), MMatchPO.Table_Name, whereClause, get_TrxName())
		.setParameters(p_C_Order_ID)
		.setOnlyActiveRecords(true)
		.match();
		
		int flag = 0;
		if (checkMatchPO){
			BigDecimal QtyProduct;
			flag = 0;
			for(int i = 0 ; i < mOrderLines.length ; i++){
				StringBuilder sb = new StringBuilder();
				//@PhieAlbert check only match po from MR
				sb.append("SELECT SUM(qty) FROM M_MatchPO WHERE C_OrderLine_ID= ? AND M_InoutLine_ID is NOT NULL ");
				//sb.append("SELECT SUM(qty) FROM M_MatchPO WHERE C_OrderLine_ID= ?");
				//end @PhieAlbert
				/*QtyProduct.add( DB.getSQLValue( get_TrxName(), sb.toString() ) ); */
				QtyProduct = DB.getSQLValueBD( get_TrxName(), sb.toString(), mOrderLines[i].getC_OrderLine_ID() );
				if(QtyProduct == null ){
					QtyProduct = new BigDecimal(0);
				}
				if( (mOrderLines[i].getQtyEntered().subtract(QtyProduct)).intValue() <= 0){
					flag++;
				}
			}
		}
		
		if(flag == mOrderLines.length){
			return "Process aborted .. Material Receipt Line Product quantity more than Purchase Order Line Product Quantity ";
		}

		HBC_MInOut inout = new HBC_MInOut(getCtx(), 0, get_TrxName());
		inout.setAD_Org_ID(order.getAD_Org_ID());
		inout.setC_BPartner_ID(order.getC_BPartner_ID());
		inout.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		inout.setC_DocType_ID(p_C_DocType_ID);
		inout.setC_Order_ID(p_C_Order_ID);
		inout.setDateAcct(p_MovementDate);
		inout.setDateReceived(p_MovementDate);
		inout.setDescription("Converted from Purchase Order #"+ order.getDocumentNo());
		inout.setDocAction(p_DocumentAction);
		inout.setDeliveryRule(order.getDeliveryRule());
		inout.setFreightCostRule(order.getFreightCostRule());
		inout.setM_Warehouse_ID(p_M_Warehouse_ID);
		inout.setM_Locator_ID(p_M_Locator_ID);
		inout.setMovementDate(p_MovementDate);
		inout.setMovementType(MInOut.MOVEMENTTYPE_VendorReceipts);
		inout.setPriorityRule(order.getPriorityRule());
		inout.setSendEMail(false);
		//@phie
		inout.set_ValueOfColumn("C_Campaign_ID", order.get_Value("C_Campaign_ID"));
		//end phie
		//@KevinY HBC - 2802
		if(order.get_ValueAsInt("HBC_Tugboat_ID") > 0 || order.get_Value("HBC_Tugboat_ID")!= null)
			inout.set_ValueOfColumn("HBC_Tugboat_ID", (Integer)order.get_Value("HBC_Tugboat_ID"));
		else if(order.get_ValueAsInt("HBC_Barge_ID") > 0 || order.get_Value("HBC_Barge_ID")!= null)
			inout.set_ValueOfColumn("HBC_Barge_ID"	, (Integer)order.get_Value("HBC_Barge_ID"));
		//@KevinY end
		inout.saveEx();

		for(MOrderLine orderline: mOrderLines ){
			MInOutLine inoutLine = new MInOutLine(inout);
			//@kevinf HBC-2665
			//inoutLine.setM_Product_ID(orderline.getM_Product_ID());
			int M_Product_ID = orderline.getM_Product_ID();
			if(M_Product_ID>0)
				inoutLine.setM_Product_ID(orderline.getM_Product_ID());
			else
				inoutLine.setC_Charge_ID(orderline.getC_Charge_ID());
			
			//@kevinf end
			
			//@KevinY HBC - 2802
			if(order.get_ValueAsInt("HBC_Tugboat_ID") > 0 || order.get_Value("HBC_Tugboat_ID")!= null )
				inoutLine.set_ValueOfColumn("HBC_Tugboat_ID", (Integer)order.get_Value("HBC_Tugboat_ID"));
			else if(order.get_ValueAsInt("HBC_Barge_ID") > 0 || order.get_Value("HBC_Barge_ID")!= null)
				inoutLine.set_ValueOfColumn("HBC_Barge_ID"	, (Integer)order.get_Value("HBC_Barge_ID"));
			//@KevinY end
			
			BigDecimal qtyEntered = orderline.getQtyEntered()
					.setScale(MUOM.getPrecision(getCtx(), orderline.getC_UOM_ID()), BigDecimal.ROUND_HALF_UP);
			
			inoutLine.setQtyEntered(qtyEntered);
			
			BigDecimal movementQty = MUOMConversion.convertProductFrom (getCtx(), orderline.getM_Product_ID(),
					orderline.getC_UOM_ID(), qtyEntered);
			
			if (movementQty == null)
				movementQty = qtyEntered;
			
			
			inoutLine.setAD_Org_ID(orderline.getAD_Org_ID());
			inoutLine.setC_UOM_ID(orderline.getC_UOM_ID());
			inoutLine.setC_OrderLine_ID(orderline.getC_OrderLine_ID());
			//@phie
			inoutLine.set_ValueOfColumn("C_Campaign_ID", orderline.get_Value("C_Campaign_ID"));
			//end phie
			inoutLine.setMovementQty(movementQty);
			inoutLine.setM_Locator_ID(p_M_Locator_ID);
			//@phie habco 2640
			inoutLine.setIsTrackAsAsset(orderline.get_ValueAsBoolean("isTrackAsAsset"));
			//end phie
			inoutLine.saveEx();
		}
		
		//generate match PO
		inout.processIt(p_DocumentAction);
		inout.saveEx();
		
		
		String message = Msg.parseTranslation(getCtx(), "@Generated@ " +  inout.getDocumentNo());
		addBufferLog(0, null, BigDecimal.ZERO, message, inout.get_Table_ID(), inout.getM_InOut_ID());

		return inout.getDocumentNo();
	}//doIt
}//endClass
