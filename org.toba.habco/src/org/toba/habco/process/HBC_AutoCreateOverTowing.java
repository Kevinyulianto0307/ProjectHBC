package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMovementLine;
import org.compiere.model.MProduct;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MMovement;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;

/**
 * @author TommyAng
 * Process Create Activity Over Towing For Its Own Tug Boat Partner In The Other Position
 * Process Create New Duplicate Activity From The Activity Before Over Towing For Each Position
 */

public class HBC_AutoCreateOverTowing extends SvrProcess{
	
	int p_HBC_ShipActivity_ID=0;
	int p_TripTarget_ID=0;
	Timestamp DateNextActivity = null;
	Timestamp positionDate = null;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("HBC_Trip_ID")){
				p_TripTarget_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("StartDate")){
				DateNextActivity = para[i].getParameterAsTimestamp();
			}
			//@phie fix db column name
			else if (name.equals("DateStart")){
				positionDate = para[i].getParameterAsTimestamp();
			}
			//end phie
		}
		p_HBC_ShipActivity_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		MShipActivity shipActivity1 = new MShipActivity(getCtx(), p_HBC_ShipActivity_ID, get_TrxName());
		
		if(!shipActivity1.getC_Activity().getValue().equals("OVT") && !shipActivity1.getC_Activity().getValue().equals("FTR"))
			throw new AdempiereException("Unable to use this process! NB : Only OverTowing or FuelTransfer"); 
		
		if(shipActivity1.getC_Activity().getValue().equals("FTR") && (shipActivity1.getLiter().compareTo(Env.ZERO)>0 || shipActivity1.getLiter().equals(Env.ZERO)))
			throw new AdempiereException("Unable to use this process! NB : This Activity is a Recipient Fuel Transfer, process can only use by Donor");
		
		if(shipActivity1.getC_Activity().getValue().equals("FTR") && shipActivity1.getM_Movement_ID()>0)
			throw new AdempiereException("This Activity is still in used by intra-warehouse");
		
		MPosition position1 = (MPosition) shipActivity1.getHBC_Position();
		
		//Add validation by phie
		//create Create TugboatPartner Record only for activity overtowing
		if(shipActivity1.getC_Activity().getValue().equals("OVT"))
		{
			int HBC_Position2_ID = getLastPositionID(p_TripTarget_ID, position1.getHBC_PortPosition_ID(), shipActivity1.getTugBoatPartner_ID());
			
			MPosition position2 = new MPosition(getCtx(), HBC_Position2_ID, get_TrxName());
			MShipActivity shipActivity2 = new MShipActivity(position2.getCtx(), 0, position2.get_TrxName());
			
			//Create TugboatPartner Record
			shipActivity2.setHBC_Position_ID(HBC_Position2_ID);
			shipActivity2.setTugBoatPartner_ID(shipActivity1.getHBC_Tugboat_ID());
			shipActivity2.setHBC_Tugboat_ID(shipActivity1.getTugBoatPartner_ID());
			shipActivity2.setStartDate(shipActivity1.getStartDate());
			shipActivity2.setC_Activity_ID(shipActivity1.getC_Activity_ID());
			shipActivity2.setHBC_Barge_ID(position2.getHBC_Barge_ID());
			if(shipActivity1.getC_Activity().getValue().equals("FTR"))
				shipActivity2.setLiter(shipActivity1.getLiter().negate());
			shipActivity2.saveEx();
			//end 
			
			shipActivity1.set_ValueOfColumn("ActivityPartner", shipActivity2.get_ID());
			shipActivity1.set_ValueOfColumn("IsFuelTransfered", true);
			shipActivity1.saveEx();
			
			int lastActivityIDBeforeThis1 = getLastActivityIDBeforeThis(position1.getHBC_Position_ID(), shipActivity1.getStartDate());
			MShipActivity lastActivityBeforeThis1 = new MShipActivity(getCtx(), lastActivityIDBeforeThis1, get_TrxName());
			MShipActivity newActivity1 = new MShipActivity(position1.getCtx(), 0, position1.get_TrxName());
			
			//Create Next Activity After This For This Position
			newActivity1.setHBC_Position_ID(lastActivityBeforeThis1.getHBC_Position_ID());
			if(shipActivity1.getC_Activity().getValue().equals("OVT"))
				newActivity1.setHBC_Tugboat_ID(shipActivity1.getTugBoatPartner_ID());
			else if(shipActivity1.getC_Activity().getValue().equals("FTR"))
				newActivity1.setHBC_Tugboat_ID(shipActivity1.getHBC_Tugboat_ID());
			newActivity1.setStartDate(DateNextActivity);
			newActivity1.setC_Activity_ID(lastActivityBeforeThis1.getC_Activity_ID());
			newActivity1.setHBC_Barge_ID(lastActivityBeforeThis1.getHBC_Barge_ID());
			newActivity1.set_ValueOfColumn("Line", shipActivity1.get_ValueAsInt("Line")+10);
			newActivity1.setLoadingAgent_BPartner_ID(shipActivity1.getLoadingAgent_BPartner_ID());
			newActivity1.saveEx();
			//end
			
			int lastActivityIDBeforeThis2 = getLastActivityIDBeforeThis(position2.getHBC_Position_ID(), shipActivity2.getStartDate());
			MShipActivity lastActivityBeforeThis2 = new MShipActivity(getCtx(), lastActivityIDBeforeThis2, get_TrxName());
			MShipActivity newActivity2 = new MShipActivity(position2.getCtx(), 0, position2.get_TrxName());
			
			//Create Next Activity After This For TugboatPartner Position
			newActivity2.setHBC_Position_ID(lastActivityBeforeThis2.getHBC_Position_ID());
			if(shipActivity1.getC_Activity().getValue().equals("OVT"))
				newActivity2.setHBC_Tugboat_ID(shipActivity2.getTugBoatPartner_ID());
			else if(shipActivity1.getC_Activity().getValue().equals("FTR"))
				newActivity2.setHBC_Tugboat_ID(shipActivity2.getHBC_Tugboat_ID());
			newActivity2.setStartDate(DateNextActivity);
			newActivity2.setC_Activity_ID(lastActivityBeforeThis2.getC_Activity_ID());
			newActivity2.setHBC_Barge_ID(lastActivityBeforeThis2.getHBC_Barge_ID());
			newActivity2.saveEx();	
			//end
			
			shipActivity2.set_ValueOfColumn("Line", lastActivityBeforeThis2.get_ValueAsInt("Line")+10);
			shipActivity2.setLoadingAgent_BPartner_ID(lastActivityBeforeThis2.getLoadingAgent_BPartner_ID());
			shipActivity2.set_ValueOfColumn("ActivityPartner", shipActivity1.get_ID());
			shipActivity2.set_ValueOfColumn("IsFuelTransfered", true);
			shipActivity2.saveEx();
			
			newActivity2.set_ValueOfColumn("Line", shipActivity2.get_ValueAsInt("Line")+10);
			newActivity2.setLoadingAgent_BPartner_ID(shipActivity2.getLoadingAgent_BPartner_ID());
			newActivity2.saveEx();
		}
		
		if(shipActivity1.getC_Activity().getValue().equals("FTR")){
			int movement_ID = createIntraWarehouseMovement(shipActivity1, position1);
			shipActivity1.setM_Movement_ID(movement_ID);
			shipActivity1.saveEx();
			//shipActivity2.setM_Movement_ID(movement_ID);
			//shipActivity2.saveEx();
		}
		
		return "Partner Created";
	}
	
	/**
	 * @TommyAng
	 * Get The Last Position ID Based On TripID and PortPosition
	 */
	protected int getLastPositionID(int HBC_Trip_ID, int HBC_PortPosition_ID, int TugBoatPartner){
		
		int lastPositionID= new Query(getCtx(),MPosition.Table_Name,"HBC_Trip_ID="+HBC_Trip_ID+" and HBC_PortPosition_ID="+HBC_PortPosition_ID+" and DateStart='"+positionDate+"'",get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy("DateStart DESC")
		.setClient_ID()
		.firstId();
		
		if(lastPositionID <= 0)
			throw new AdempiereException("Targeted Trip Has No Similar Position");
		
		int lastActivityCurrentPosition= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+lastPositionID,get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate DESC")
		.setClient_ID()
		.firstId();
		
		MShipActivity sa = new MShipActivity(getCtx(), lastActivityCurrentPosition, get_TrxName());
		if(sa.getHBC_Tugboat_ID() != TugBoatPartner)
			throw new AdempiereException("Current TugBoat Partner Doesn't Match With The TugBoat From The Last Activity(Based On Match Position From Targeted Trip)");
		
		return lastPositionID;
	}
	
	/**
	 * @TommyAng
	 * Get The Last Activity ID Before This Activity Based On Position ID
	 */
	protected int getLastActivityIDBeforeThis(int HBC_Position_ID, Timestamp DateParameter){
		
		int lastActivityIDBeforeThis= new Query(getCtx(),MShipActivity.Table_Name,"HBC_Position_ID="+HBC_Position_ID+" and StartDate<'"+DateParameter+"'",get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy("StartDate DESC")
		.setClient_ID()
		.firstId();
		
		return lastActivityIDBeforeThis;
	}
	
	/**
	 * @TommyAng
	 * Create Intra Warehouse Movement Document
	 */
	protected int createIntraWarehouseMovement(MShipActivity shipActivity, MPosition position){
		
		HBC_MMovement movement = new HBC_MMovement(getCtx(), 0, get_TrxName());
		MWarehouse whFrom = new MWarehouse(getCtx(), shipActivity.getHBC_Tugboat().getM_Warehouse_ID(), get_TrxName());
		MWarehouse whTo = new MWarehouse(getCtx(), shipActivity.getTugBoatPartner().getM_Warehouse_ID(), get_TrxName());
		
		int locatorFrom= new Query(getCtx(),MLocator.Table_Name,"M_Warehouse_ID="+whFrom.get_ID(),get_TrxName())
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.firstId();
		
		int locatorTo= new Query(getCtx(),MLocator.Table_Name,"M_Warehouse_ID="+whTo.get_ID(),get_TrxName())
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.firstId();
		
		String description = "Auto Generate Fuel Transfer in Position : "+position.getHBC_PortPosition().getName() 
		//phie
				+ " | Trip Document No : " + shipActivity.getHBC_Position().getHBC_Trip().getDocumentNo();
		//end phie
		movement.setMovementDate(shipActivity.getStartDate());
		movement.setM_Warehouse_ID(whFrom.get_ID());
		movement.setM_WarehouseTo_ID(whTo.get_ID());
		movement.setDescription(description);
		movement.setC_DocType_ID(MDocType.getFirstIDOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialMovement));
		movement.setAD_Org_ID(shipActivity.getAD_Org_ID());
		movement.saveEx();
		
		MMovementLine line = new MMovementLine(movement);
		MProduct product = new MProduct(getCtx(), 1009123, get_TrxName());
		line.setM_Product_ID(product.get_ID());
		line.setM_Locator_ID(locatorFrom);
		line.setM_LocatorTo_ID(locatorTo);
		line.setAD_Org_ID(movement.getAD_Org_ID());
		line.setMovementQty(shipActivity.getLiter().negate());
		line.setQtyEntered(shipActivity.getLiter().negate());
		line.setC_UOM_ID(product.getC_UOM_ID());
		line.saveEx();
		
		movement.processIt(movement.DOCACTION_Complete);
		movement.saveEx();
		
		String message = Msg.parseTranslation(getCtx(), "@Generated@ " +  movement.getDocumentNo());
		addBufferLog(0, null, BigDecimal.ZERO, message, movement.get_Table_ID(), movement.getM_Movement_ID());
		
		shipActivity.setDescription(movement.getDocumentNo());
		shipActivity.saveEx();
		
		return movement.get_ID();
	}
}
