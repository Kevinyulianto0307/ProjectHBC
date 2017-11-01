package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInventory;
import org.toba.habco.model.MBBMAdjustment;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MFuelUsage;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MTugboat;


/*
 * @Author Phie Albert
 * Generate Internal Use from BBM Plan (Trip)
 */

public class GenerateInternalUseFromBBMTrip extends SvrProcess{

	private int C_DocType_ID = 0;
	private int C_Charge_ID = 0;
	private int TCS_BBMPlan_ID = 0;
	private Timestamp movementDate = null;
	private String DocAction;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
				C_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Charge_ID"))
				C_Charge_ID = para[i].getParameterAsInt();
			else if (name.equals("MovementDate"))
				movementDate = para[i].getParameterAsTimestamp();
			else if(name.equals("DocAction"))
				DocAction = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		TCS_BBMPlan_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		if (TCS_BBMPlan_ID==0) 
			return "No BBMPlan selected";
		
		MBBMPlan bbm_plan = new MBBMPlan(getCtx(), TCS_BBMPlan_ID, get_TrxName());
		//get BBM Usage line
		MFuelUsage[] fuelUsages = bbm_plan.getFuelUsageID();
		
		List<MBBMAdjustment> list = new Query(getCtx(), MBBMAdjustment.Table_Name, "TCS_BBMPlan_ID=?", get_TrxName())
		.setParameters(TCS_BBMPlan_ID)
		.list();
		
		//get BBM Adjustment
		MBBMAdjustment[] adjustment = list.toArray(new MBBMAdjustment[list.size()]);
		
		//validate there's no warehouse in adjustment line that not exists in BBM Usage Line
		boolean valid = false;
		for(int a=0; a<adjustment.length;a++)
		{
			valid = false;
			MTugboat tugboat_in_adjustment = new MTugboat(getCtx(), adjustment[a].get_ValueAsInt("HBC_Tugboat_ID"), get_TrxName());
			for(int b=0;b<fuelUsages.length;b++)
			{
				MTugboat tugboat_in_bbm_usage = new MTugboat(getCtx(), fuelUsages[b].get_ValueAsInt("HBC_Tugboat_ID"), get_TrxName());
				if(tugboat_in_adjustment.getM_Warehouse_ID() == tugboat_in_bbm_usage.getM_Warehouse_ID())
				{
					valid = true;
					break;
				}
			}
			
			if(!valid)
				throw new IllegalArgumentException("This tugboat "+tugboat_in_adjustment.getName()+" doesn't match with tugboat in BBM Usage Line ");
		}
		
		ArrayList<Integer> Warehouse_ID_list = new ArrayList<Integer>(); //List warehouse that will create internal use
		//Get all the warehouses (unique) that we need to create internal use documents 
		//and validate all tugboat in BBM Usage line and adjusment line must have warehouse
		for(int i=0;i<fuelUsages.length;i++)
		{
			MTugboat tugboat = new MTugboat(getCtx(), fuelUsages[i].get_ValueAsInt("HBC_Tugboat_ID"), get_TrxName());
			if(tugboat.getM_Warehouse_ID()==0)
				throw new IllegalArgumentException("This tugboat "+tugboat.getName()+" doesn't have warehouse ");
			
			if(fuelUsages[i].get_ValueAsInt("M_Inventory_ID") == 0)
			{
				if(!Warehouse_ID_list.contains(tugboat.getM_Warehouse_ID()))
					Warehouse_ID_list.add(tugboat.getM_Warehouse_ID());
			}
		}
	
		for(int i=0;i<adjustment.length;i++)
		{
			MTugboat tugboat = new MTugboat(getCtx(), adjustment[i].get_ValueAsInt("HBC_Tugboat_ID"), get_TrxName());
			if(tugboat.getM_Warehouse_ID()==0)
				throw new IllegalArgumentException("This tugboat "+tugboat.getName()+" doesn't have warehouse ");
			
			if(adjustment[i].get_ValueAsInt("M_Inventory_ID") == 0)
			{
				if(!Warehouse_ID_list.contains(tugboat.getM_Warehouse_ID()))
					Warehouse_ID_list.add(tugboat.getM_Warehouse_ID());
			}
		}
		//end get
		
		//create internal use
		BigDecimal qtyInternalUse = Env.ZERO;	
		for(int i=0;i<Warehouse_ID_list.size();i++)
		{
			qtyInternalUse = Env.ZERO;
			MWarehouse warehouse = new MWarehouse(getCtx(), Warehouse_ID_list.get(i) , get_TrxName());
			HBC_MInventory inventory = new HBC_MInventory(warehouse);
			MTrip trip = new MTrip(getCtx(), bbm_plan.get_ValueAsInt("CurrentTrip_ID"), get_TrxName());
			
			inventory.setDescription("Generated from BBM Plan (Trip) no: "+bbm_plan.get_Value("DocumentNo"));
			inventory.setC_Project_ID(trip.getC_Project_ID());
			inventory.setMovementDate(movementDate);
			inventory.setC_DocType_ID(C_DocType_ID);
			inventory.setDocStatus(org.compiere.process.DocAction.STATUS_Drafted);
			inventory.setDocAction(DocAction);
			inventory.saveEx();
			
			for(int j=0;j<fuelUsages.length;j++)
			{
				MTugboat tugboat = new MTugboat(getCtx(), fuelUsages[j].get_ValueAsInt("HBC_Tugboat_ID"), get_TrxName());
				if(Warehouse_ID_list.get(i) == tugboat.getM_Warehouse_ID() && fuelUsages[j].get_ValueAsInt("M_Inventory_ID") == 0)
				{
					fuelUsages[j].set_ValueOfColumn("M_Inventory_ID", inventory.getM_Inventory_ID());
					fuelUsages[j].saveEx();
					qtyInternalUse = qtyInternalUse.add((BigDecimal) fuelUsages[j].get_Value("ActualShipUsage"));
				}
			}
			
			for(int k=0;k<adjustment.length;k++)
			{
				MTugboat tugboat = new MTugboat(getCtx(), adjustment[k].get_ValueAsInt("HBC_Tugboat_ID"), get_TrxName());
				if(Warehouse_ID_list.get(i) == tugboat.getM_Warehouse_ID() && adjustment[k].get_ValueAsInt("M_Inventory_ID") == 0)
				{
					adjustment[k].set_ValueOfColumn("M_Inventory_ID", inventory.getM_Inventory_ID());
					adjustment[k].saveEx();
					//qtyInternalUse = qtyInternalUse.subtract(adjustment[k].getFuelAdjustment());
				}
			}
			
			if(qtyInternalUse.compareTo(Env.ZERO) <= 0)
			{
				throw new IllegalArgumentException("Difference between total fuel Ship balance in BBM Usage line and total fuel adjustment in adjustment line must greater than zero");
			}
			
			String where2 = "M_Warehouse_ID = ?";
			int Locator_ID = new Query(getCtx(),MLocator.Table_Name,where2,null)
			.setParameters(warehouse.getM_Warehouse_ID())
			.setOnlyActiveRecords(true)
			.firstId();
			
			MInventoryLine invLine = new MInventoryLine(inventory, Locator_ID, 1009123, 0, Env.ZERO, Env.ZERO, qtyInternalUse);
			invLine.setQtyEntered(qtyInternalUse);
			invLine.setC_Charge_ID(C_Charge_ID);
			invLine.set_ValueOfColumn("HBC_Tugboat_ID", warehouse.get_ValueAsInt("HBC_Tugboat_ID"));
			invLine.saveEx();
			
			inventory.processIt(DocAction);
			inventory.saveEx();
			
			String message = Msg.parseTranslation(getCtx(), "@Generated@ " +  inventory.getDocumentNo());
			addBufferLog(0, null, BigDecimal.ZERO, message, inventory.get_Table_ID(), inventory.getM_Inventory_ID());
		}
		return "";
	}
}
