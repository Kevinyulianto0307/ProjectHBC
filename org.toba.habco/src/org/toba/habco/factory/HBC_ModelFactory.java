/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/

package org.toba.habco.factory;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import org.adempiere.base.IModelFactory;
import org.compiere.model.I_A_Asset_Disposed;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_RMA;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.toba.habco.model.I_C_ProjectMilestone;
import org.toba.habco.model.I_C_ProjectTypeMilestone;
import org.toba.habco.model.I_FuelUsage;
import org.toba.habco.model.I_HBC_APPriceList;
import org.toba.habco.model.I_HBC_APProduct;
import org.toba.habco.model.I_HBC_ARCalculation;
import org.toba.habco.model.I_HBC_ARCalculationLine;
import org.toba.habco.model.I_HBC_Activity;
import org.toba.habco.model.I_HBC_AuxiliaryEngine;
import org.toba.habco.model.I_HBC_BBMActivity;
import org.toba.habco.model.I_HBC_BBMAdjustment;
import org.toba.habco.model.I_HBC_Barge;
import org.toba.habco.model.I_HBC_BargeCategory;
import org.toba.habco.model.I_HBC_Contract;
import org.toba.habco.model.I_HBC_ContractTerm;
import org.toba.habco.model.I_HBC_CostActivity;
import org.toba.habco.model.I_HBC_CrewOnOff;
import org.toba.habco.model.I_HBC_Demurrage;
import org.toba.habco.model.I_HBC_DiffRecord;
import org.toba.habco.model.I_HBC_FuelTripAllocation;
import org.toba.habco.model.I_HBC_GearBox;
import org.toba.habco.model.I_HBC_GeneralTerm;
import org.toba.habco.model.I_HBC_Jetty;
import org.toba.habco.model.I_HBC_LegalizedLine;
import org.toba.habco.model.I_HBC_LoadUnloadDay;
import org.toba.habco.model.I_HBC_MainEngine;
import org.toba.habco.model.I_HBC_OvertowingHistory;
import org.toba.habco.model.I_HBC_PortDistance;
import org.toba.habco.model.I_HBC_PortPosition;
import org.toba.habco.model.I_HBC_Position;
import org.toba.habco.model.I_HBC_PriceList_BBM;
import org.toba.habco.model.I_HBC_ProductTrx;
import org.toba.habco.model.I_HBC_Region;
import org.toba.habco.model.I_HBC_ShipActivity;
import org.toba.habco.model.I_HBC_ShipDocument;
import org.toba.habco.model.I_HBC_ShipDocumentLine;
import org.toba.habco.model.I_HBC_ShipDocument_Dock;
import org.toba.habco.model.I_HBC_ShipPosition;
import org.toba.habco.model.I_HBC_ShipPositionLine;
import org.toba.habco.model.I_HBC_Trip;
import org.toba.habco.model.I_HBC_Tugboat;
import org.toba.habco.model.I_HC_Employee;
import org.toba.habco.model.I_HC_EmployeeJob;
import org.toba.habco.model.I_LCO_InvoiceWithholding;
import org.toba.habco.model.I_M_MatchARCalculation;
import org.toba.habco.model.I_M_MatchCostActivity;
import org.toba.habco.model.I_M_MatchPR;
import org.toba.habco.model.I_M_Product_Version;
import org.toba.habco.model.I_TCS_BBMPlan;
import org.toba.habco.model.MJournal;
import org.toba.habco.model.MJournalBatch;



/**
 * Generic Model Factory 
 * 
 * @author Double Click Sistemas C.A. - http://dcs.net.ve
 * @author Saúl Piña - spina@dcs.net.ve
 */
public class HBC_ModelFactory implements IModelFactory {

	private static HashMap<String, String> mapTableModels = new HashMap<String, String>();
	static
	{
		mapTableModels.put(I_HBC_Jetty.Table_Name, "org.toba.habco.model.X_HBC_Jetty");
		mapTableModels.put(I_HBC_PortDistance.Table_Name, "org.toba.habco.model.X_HBC_PortDistance");
		mapTableModels.put(I_HBC_PortPosition.Table_Name, "org.toba.habco.model.X_HBC_PortPosition");
		mapTableModels.put(I_HBC_ShipDocumentLine.Table_Name, "org.toba.habco.model.MShipDocumentLine");
		mapTableModels.put(I_HBC_ShipPosition.Table_Name, "org.toba.habco.model.X_HBC_ShipPosition");
		mapTableModels.put(I_HBC_ContractTerm.Table_Name, "org.toba.habco.model.MContractTerm");
		mapTableModels.put(I_HBC_AuxiliaryEngine.Table_Name, "org.toba.habco.model.X_HBC_AuxiliaryEngine");
		mapTableModels.put(I_HBC_MainEngine.Table_Name, "org.toba.habco.model.X_HBC_MainEngine");
		mapTableModels.put(I_HBC_Region.Table_Name, "org.toba.habco.model.X_HBC_Region");
		mapTableModels.put(I_HBC_Activity.Table_Name, "org.toba.habco.model.X_HBC_Activity");
		mapTableModels.put(I_HBC_LoadUnloadDay.Table_Name,"org.toba.habco.model.MLoadUnloadDay");
		mapTableModels.put(I_HBC_Demurrage.Table_Name, "org.toba.habco.model.MDemurrage");
		mapTableModels.put(I_HBC_OvertowingHistory.Table_Name,"org.toba.habco.model.X_HBC_OvertowingHistory");
		mapTableModels.put(I_HBC_GearBox.Table_Name, "org.toba.habco.model.X_HBC_GearBox");
		mapTableModels.put(I_HBC_GeneralTerm.Table_Name, "org.toba.habco.model.MGeneralTerm");
		mapTableModels.put(I_HBC_ShipDocument.Table_Name,"org.toba.habco.model.MShipDocument");
		mapTableModels.put(I_HBC_Contract.Table_Name, "org.toba.habco.model.MContract");
		mapTableModels.put(I_HBC_Tugboat.Table_Name, "org.toba.habco.model.MTugboat");
		mapTableModels.put(I_HBC_Barge.Table_Name, "org.toba.habco.model.MBarge");
		mapTableModels.put(I_HBC_ShipPositionLine.Table_Name, "org.toba.habco.model.MShipPositionLine");
		mapTableModels.put(I_HBC_Position.Table_Name,"org.toba.habco.model.MPosition");
		mapTableModels.put(I_HBC_ShipActivity.Table_Name, "org.toba.habco.model.MShipActivity");
		mapTableModels.put(I_HBC_ARCalculation.Table_Name, "org.toba.habco.model.MARCalculation");
		mapTableModels.put(I_HBC_ARCalculationLine.Table_Name, "org.toba.habco.model.MARCalculationLine");
		mapTableModels.put(I_HBC_LegalizedLine.Table_Name, "org.toba.habco.model.MLegalizedLineDetail");
		mapTableModels.put(I_HBC_BBMActivity.Table_Name, "org.toba.habco.model.MBBMActivity");
		mapTableModels.put(I_HBC_Trip.Table_Name, "org.toba.habco.model.MTrip");
		mapTableModels.put(I_HBC_BBMAdjustment.Table_Name, "org.toba.habco.model.MBBMAdjustment");
		mapTableModels.put(I_HBC_ProductTrx.Table_Name, "org.toba.habco.model.MProductTrx");
		mapTableModels.put(I_HBC_CostActivity.Table_Name, "org.toba.habco.model.MCostActivity");
		mapTableModels.put(I_HBC_PriceList_BBM.Table_Name, "org.toba.habco.model.MPriceListBBM");
		mapTableModels.put(I_HBC_APPriceList.Table_Name, "org.toba.habco.model.MAPPriceList");
		mapTableModels.put(I_HBC_APProduct.Table_Name, "org.toba.habco.model.MAPProduct");
		mapTableModels.put(I_HBC_BargeCategory.Table_Name,"org.toba.habco.model.X_HBC_BargeCategory");
		mapTableModels.put(I_HBC_FuelTripAllocation.Table_Name, "org.toba.habco.model.MFuelTripAllocation");
		mapTableModels.put(I_HBC_DiffRecord.Table_Name, "org.toba.habco.model.MDiffRecord");
		mapTableModels.put(I_FuelUsage.Table_Name,"org.toba.habco.model.MFuelUsage");
		mapTableModels.put(I_LCO_InvoiceWithholding.Table_Name, "org.toba.habco.model.X_LCO_InvoiceWithholding");
		mapTableModels.put(I_C_ProjectTypeMilestone.Table_Name, "org.toba.habco.model.MProjectTypeMilestone");
		mapTableModels.put(I_C_ProjectMilestone.Table_Name, "org.toba.habco.model.MProjectMilestone");
		mapTableModels.put(I_M_MatchCostActivity.Table_Name, "org.toba.habco.model.MMatchCostActivity");
		mapTableModels.put(I_M_MatchARCalculation.Table_Name,"org.toba.habco.model.MMatchARCalculation");
		mapTableModels.put(I_HC_Employee.Table_Name, "org.toba.habco.model.MEmployee");
		mapTableModels.put(I_M_Product_Version.Table_Name, "org.toba.habco.model.MProductVersion");
		mapTableModels.put(I_TCS_BBMPlan.Table_Name, "org.toba.habco.model.MBBMPlan");
		mapTableModels.put(I_HBC_ShipDocument_Dock.Table_Name, "org.toba.habco.model.MShipDocumentDock");
		// @KevinY HBC - 2395
		mapTableModels.put(I_HC_EmployeeJob.Table_Name, "org.toba.habco.model.MEmployeeJob");
		mapTableModels.put(I_HBC_CrewOnOff.Table_Name, "org.toba.habco.model.MCrewOnOff");
		mapTableModels.put(MJournal.Table_Name, "org.toba.habco.model.MJournal");
		mapTableModels.put(MJournalBatch.Table_Name, "org.toba.habco.model.MJournalBatch");
		//@KevinY end
		
		/**org.compiere.model*/
		mapTableModels.put(I_C_AllocationHdr.Table_Name, "org.toba.habco.model.HBC_MAllocationHdr");
		//@phie create temporary class
		mapTableModels.put(I_A_Asset_Disposed.Table_Name, "org.toba.habco.model.HBC_MAssetDisposed");
		//end phie		
		mapTableModels.put(I_M_InOut.Table_Name,"org.toba.habco.model.HBC_MInOut");
		mapTableModels.put(I_M_Inventory.Table_Name, "org.toba.habco.model.HBC_MInventory");
		mapTableModels.put(I_M_InventoryLine.Table_Name, "org.toba.habco.model.HBC_MInventoryLine");
		mapTableModels.put(I_C_Invoice.Table_Name,"org.toba.habco.model.HBC_MInvoice");
		mapTableModels.put(I_C_InvoiceLine.Table_Name,"org.toba.habco.model.HBC_MInvoiceLine");
		mapTableModels.put(I_M_Movement.Table_Name, "org.toba.habco.model.HBC_MMovement");
		mapTableModels.put(I_C_Order.Table_Name,"org.toba.habco.model.HBC_MOrder");
		mapTableModels.put(I_C_OrderLine.Table_Name,"org.toba.habco.model.HBC_MOrderLine");
		mapTableModels.put(I_M_Requisition.Table_Name, "org.toba.habco.model.HBC_MRequisition");
		mapTableModels.put(I_M_RMA.Table_Name, "org.toba.habco.model.HBC_MRMA");
		mapTableModels.put(I_M_MatchPR.Table_Name,"org.toba.habco.model.MMatchPR");
		
	}
	
	@Override
	public Class<?> getClass(String tableName) {
		
		if (mapTableModels.containsKey(tableName)) {
			Class<?> act = null;
			try {
				act = Class.forName(mapTableModels.get(tableName));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
				return act;
		
		} else 
			return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		
		if (mapTableModels.containsKey(tableName)) {
			Class<?> clazz = null;
			Constructor<?> ctor = null;
			PO object = null;
			try {
				clazz = Class.forName(mapTableModels.get(tableName));
				ctor = clazz.getConstructor(Properties.class, int.class, String.class);
				object = (PO) ctor.newInstance(new Object[] {Env.getCtx(), Record_ID, trxName});
				
			} catch (Exception e) {
				e.printStackTrace();
			}
				return object;
		} else 	   
		   return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
	
		if (mapTableModels.containsKey(tableName)) {
			Class<?> clazz = null;
			Constructor<?> ctor = null;
			PO object = null;
			try {
				clazz = Class.forName(mapTableModels.get(tableName));
				ctor = clazz.getConstructor(Properties.class, ResultSet.class, String.class);
				object = (PO) ctor.newInstance(new Object[] {Env.getCtx(), rs, trxName});
				
			} catch (Exception e) {
				e.printStackTrace();
			}
				return object;
				
		} else  
			return null;
	}

}
