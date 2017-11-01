package org.toba.habco.timesheet.process;

import java.math.BigDecimal;

import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.X_HBC_Position;
import org.toba.habco.model.X_HBC_ShipActivity;

public class HBC_BunkeringCreateInOut extends SvrProcess {

	int p_HBC_ShipActivity_ID;
	int p_C_DocType_ID = 0;
	int p_M_Locator_ID = 0;
	
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params) {
			if (param.getParameterName().equals("C_DocType_ID")) {
				p_C_DocType_ID = param.getParameterAsInt();
			} else if (param.getParameterName().equals("M_Locator_ID")) {
				p_M_Locator_ID = param.getParameterAsInt();
			}
		}
		
		p_HBC_ShipActivity_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		if(p_HBC_ShipActivity_ID==0){
			return "Process aborted.. No ship activity selected";
		}
		MDocType docType = MDocType.get(getCtx(), p_C_DocType_ID);
		if (!docType.getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialReceipt))
			return "Process aborted.. Wrong document type";
		
		X_HBC_ShipActivity shipActivity= new X_HBC_ShipActivity(getCtx(), p_HBC_ShipActivity_ID, get_TrxName());
		MPosition sposition = new MPosition(getCtx(), shipActivity.getHBC_Position_ID(), get_TrxName());
		
		if (shipActivity.getC_Order_ID() < 1)
			return "Process aborted.. No purchase order linked to selected ship activity";
		
		MOrder order = new MOrder(getCtx(),shipActivity.getC_Order_ID(),get_TrxName());

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
			inoutline.setC_OrderLine_ID(oLine.getC_OrderLine_ID());
			inoutline.saveEx();
		}
		
		String msg = "";
		if(inout.processIt(DocAction.ACTION_Complete)){
			msg = Msg.parseTranslation(getCtx(), "@Material Receipt@ @Created@ " + inout.getDocumentNo());
			addLog(inout.getM_InOut_ID(), null, null, msg, MInOut.Table_ID, inout.getM_InOut_ID());
		}
		
		shipActivity.setProcessed(true);
		shipActivity.setM_InOut_ID(inout.getM_InOut_ID());
		shipActivity.saveEx();
		
		return msg;
	}

}
