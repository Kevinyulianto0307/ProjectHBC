package org.toba.habco.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.X_HBC_ShipActivity;

public class HBC_ReverseAndCreateInoutBunkering extends SvrProcess{
	int p_HBC_ShipActivity_ID;
	int p_C_DocType_ID = 0;
	int p_M_Locator_ID = 0;
	protected void prepare() {
		p_C_DocType_ID = 1000251;
		
		
	}
	
	@Override
	protected String doIt() throws Exception {
		
		
		//if(p_HBC_ShipActivity_ID==0){
			//return "Process aborted.. No ship activity selected";
		//}
		MDocType docType = MDocType.get(getCtx(), p_C_DocType_ID);
		if (!docType.getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialReceipt))
			return "Process aborted.. Wrong document type";
		
		String whereClause = "iol.qtyEntered = 0";
		int[] shipActivityIDs = new Query(getCtx(), MShipActivity.Table_Name, whereClause, get_TrxName())
								.addJoinClause("JOIN C_Order co ON co.C_Order_ID = HBC_ShipActivity.C_Order_ID")
								.addJoinClause("JOIN M_InOut io ON io.M_InOut_ID = HBC_ShipActivity.M_InOut_ID")
								.addJoinClause("JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID")
								.getIDs();
		System.out.println(shipActivityIDs.length);
		
		for(int id : shipActivityIDs)
		{
			p_HBC_ShipActivity_ID= id;
			
			X_HBC_ShipActivity shipActivity= new X_HBC_ShipActivity(getCtx(), p_HBC_ShipActivity_ID, get_TrxName());
			MPosition sposition = new MPosition(getCtx(), shipActivity.getHBC_Position_ID(), get_TrxName());
			
			if (shipActivity.getC_Order_ID() < 1)
				return "Process aborted.. No purchase order linked to selected ship activity";
			
			MOrder order = new MOrder(getCtx(),shipActivity.getC_Order_ID(),get_TrxName());
			MInOut wrongInOut = new MInOut(getCtx(), shipActivity.getM_InOut_ID(), get_TrxName());
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE HBC_ShipActivity Set processed = 'N', C_Order_ID = null, M_InOut_ID = null WHERE HBC_ShipActivity_ID = ?");
			DB.executeUpdateEx(sb.toString(), new Object[]{shipActivity.getHBC_ShipActivity_ID()}, get_TrxName());
			
			//LOCATOR
			String whereClause2 = "M_Warehouse_ID IN (SELECT M_Warehouse_ID FROM C_Order WHERE C_Order_ID = ?)";
			int m_locator_id = new Query(getCtx(), MLocator.Table_Name, whereClause2, get_TrxName())
								.setParameters(new Object[]{order.getC_Order_ID()})
								.firstId();
			p_M_Locator_ID = m_locator_id;
			
			if(wrongInOut.getDocStatus().equals(DocAction.STATUS_Drafted))
			{
				if(!wrongInOut.processIt(DocAction.ACTION_Void))
					throw new AdempiereException("Cannot Void Old Material"+wrongInOut.getDocumentNo());
			}
			else if(wrongInOut.getDocStatus().equals(DocAction.STATUS_Completed))
			{
				if(!wrongInOut.processIt(DocAction.ACTION_Reverse_Correct))
					throw new AdempiereException("Cannot Reverse Old Material"+wrongInOut.getDocumentNo());
			}
			wrongInOut.saveEx();
			
			MInOut inout = new MInOut(getCtx(), 0, get_TrxName());
			inout.setC_Order_ID(shipActivity.get_ValueAsInt("C_Order_ID"));
			inout.setC_DocType_ID(p_C_DocType_ID);
			inout.setAD_Org_ID(order.getAD_Org_ID());
			inout.setMovementType(MInOut.MOVEMENTTYPE_VendorReceipts);
			inout.setC_BPartner_ID(order.getC_BPartner_ID());
			inout.setDateReceived(shipActivity.getStartDate());
			inout.setMovementDate(shipActivity.getStartDate());
			inout.setDateAcct(shipActivity.getStartDate()); //@PhieAlbert
			inout.setM_Locator_ID(p_M_Locator_ID);
			inout.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
			inout.setIsSOTrx(false);
			inout.set_ValueOfColumn("HBC_Tugboat_ID", order.get_ValueAsInt("HBC_Tugboat_ID"));
			inout.set_ValueOfColumn("HBC_Barge_ID", order.get_ValueAsInt("HBC_Barge_ID"));
			inout.setM_Warehouse_ID(order.getM_Warehouse_ID());
			inout.setC_Project_ID(sposition.get_ValueAsInt("C_Project_ID"));
			inout.setDescription("Generated for change MR "+wrongInOut.getDocumentNo()+", "
					+ "for Trip"+shipActivity.getHBC_Position().getHBC_Trip().getDocumentNo());
			inout.saveEx();		
			
			MOrderLine[] lines = order.getLines();
			for (MOrderLine oLine : lines) {
			MInOutLine inoutline = new MInOutLine(inout);
				inoutline.setM_Product_ID(oLine.getM_Product_ID());
				inoutline.setQtyEntered((BigDecimal)shipActivity.get_Value("Liter"));
				inoutline.setM_InOut_ID(inout.getM_InOut_ID());
				inoutline.setC_Project_ID(sposition.get_ValueAsInt("C_Project_ID"));
				inoutline.setC_UOM_ID(oLine.getC_UOM_ID());
				inoutline.setM_Locator_ID(p_M_Locator_ID);
				inoutline.saveEx();
			}
			
			String msg = "";
			//if(inout.processIt(DocAction.ACTION_Complete)){
				msg = Msg.parseTranslation(getCtx(), "@Material Receipt@ @Created@ " + inout.getDocumentNo());
				addLog(inout.getM_InOut_ID(), null, null, msg, MInOut.Table_ID, inout.getM_InOut_ID());
			//}
			
			shipActivity.setProcessed(true);
			shipActivity.setC_Order_ID(order.getC_Order_ID()); //@PhieAlbert
			shipActivity.setM_InOut_ID(inout.getM_InOut_ID());
			shipActivity.saveEx();
				
		}
		return "";
	}
}
