package org.toba.habco.factory;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MAssetDisposed;
import org.compiere.model.MBankStatement;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MRMA;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MTCSAmortizationPlan;
import org.compiere.model.X_C_Project;
import org.compiere.model.X_C_ProjectLine;
import org.compiere.model.X_C_ProjectTask;
import org.toba.habco.callout.HBC_CalloutARCalculation;
import org.toba.habco.callout.HBC_CalloutAmortizationPlan;
import org.toba.habco.callout.HBC_CalloutAssetAddition;
import org.toba.habco.callout.HBC_CalloutAssetDisposal;
import org.toba.habco.callout.HBC_CalloutAuxiliaryEngine;
import org.toba.habco.callout.HBC_CalloutBBMActivity;
import org.toba.habco.callout.HBC_CalloutBBMPlan;
import org.toba.habco.callout.HBC_CalloutBankStatement;
import org.toba.habco.callout.HBC_CalloutContract;
import org.toba.habco.callout.HBC_CalloutContractTerm;
import org.toba.habco.callout.HBC_CalloutCostActivity;
import org.toba.habco.callout.HBC_CalloutCrewOnOff;
import org.toba.habco.callout.HBC_CalloutDemurrage;
import org.toba.habco.callout.HBC_CalloutFuelTripAllocation;
import org.toba.habco.callout.HBC_CalloutFuelUsage;
import org.toba.habco.callout.HBC_CalloutGeneralTerm;
import org.toba.habco.callout.HBC_CalloutInOut;
import org.toba.habco.callout.HBC_CalloutInOutLine;
import org.toba.habco.callout.HBC_CalloutInventory;
import org.toba.habco.callout.HBC_CalloutInvoice;
import org.toba.habco.callout.HBC_CalloutInvoiceLine;
import org.toba.habco.callout.HBC_CalloutMainEngine;
import org.toba.habco.callout.HBC_CalloutOrder;
import org.toba.habco.callout.HBC_CalloutOrderLine;
import org.toba.habco.callout.HBC_CalloutPayment;
import org.toba.habco.callout.HBC_CalloutPosition;
import org.toba.habco.callout.HBC_CalloutPriceListBBM;
import org.toba.habco.callout.HBC_CalloutProductApproval;
import org.toba.habco.callout.HBC_CalloutProject;
import org.toba.habco.callout.HBC_CalloutProjectLine;
import org.toba.habco.callout.HBC_CalloutProjectTask;
import org.toba.habco.callout.HBC_CalloutRMA;
import org.toba.habco.callout.HBC_CalloutRequisitionLine;
import org.toba.habco.callout.HBC_CalloutShipActivity;
import org.toba.habco.callout.HBC_CalloutShipDocumentDock;
import org.toba.habco.callout.HBC_CalloutShipDocumentLine;
import org.toba.habco.callout.HBC_CalloutShipPosition;
import org.toba.habco.callout.HBC_CalloutTemplate;
import org.toba.habco.callout.HBC_CalloutTrip;
import org.toba.habco.callout.HBC_CalloutTugBoat;
import org.toba.habco.callout.HC_CalloutEmployee;
import org.toba.habco.callout.HC_CalloutEmployeeDataChange;
import org.toba.habco.callout.HC_CalloutJob;
import org.toba.habco.callout.HC_CalloutJobDataChange;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MBBMPlan;
import org.toba.habco.model.MContractTerm;
import org.toba.habco.model.MDemurrage;
import org.toba.habco.model.MEmployee;
import org.toba.habco.model.MEmployeeDataChange;
import org.toba.habco.model.MFuelUsage;
import org.toba.habco.model.MGeneralTerm;
import org.toba.habco.model.MJob;
import org.toba.habco.model.MJobDataChange;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MPriceListBBM;
import org.toba.habco.model.MShipDocumentDock;
import org.toba.habco.model.X_HBC_AuxiliaryEngine;
import org.toba.habco.model.X_HBC_BBMActivity;
import org.toba.habco.model.X_HBC_Contract;
import org.toba.habco.model.X_HBC_CostActivity;
import org.toba.habco.model.X_HBC_CrewOnOff;
import org.toba.habco.model.X_HBC_FuelTripAllocation;
import org.toba.habco.model.X_HBC_MainEngine;
import org.toba.habco.model.X_HBC_ProductTrx;
import org.toba.habco.model.X_HBC_ShipActivity;
import org.toba.habco.model.X_HBC_ShipDocumentLine;
import org.toba.habco.model.X_HBC_ShipPosition;
import org.toba.habco.model.X_HBC_Trip;
import org.toba.habco.model.X_HBC_Tugboat;


public class HBC_CalloutFactory implements IColumnCalloutFactory {

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName,
			String columnName) {
		List<IColumnCallout> list = new ArrayList<IColumnCallout>();
		
		if (tableName.equals("Table_Name")){
			list.add(new HBC_CalloutTemplate());
		}else if (tableName.equals(X_HBC_Tugboat.Table_Name)){
			list.add(new HBC_CalloutTugBoat());
		}else if (tableName.equals(X_HBC_Contract.Table_Name)){
			list.add(new HBC_CalloutContract());
		}else if (tableName.equals(X_C_Project.Table_Name)){
			list.add(new HBC_CalloutProject());
		}else if (tableName.equals(X_HBC_ShipPosition.Table_Name)){
			list.add(new HBC_CalloutShipPosition());
		}else if (tableName.equals(X_HBC_MainEngine.Table_Name)){
			list.add(new HBC_CalloutMainEngine());
		}else if (tableName.equals(X_HBC_AuxiliaryEngine.Table_Name)){
			list.add(new HBC_CalloutAuxiliaryEngine());
		}else if (tableName.equals(X_HBC_ShipDocumentLine.Table_Name)){
			list.add(new HBC_CalloutShipDocumentLine());
		}else if (tableName.equals(X_HBC_Trip.Table_Name)){
			list.add(new HBC_CalloutTrip());
		}else if (tableName.equals(MOrder.Table_Name)){
			list.add(new HBC_CalloutOrder());
		}else if (tableName.equals(MInOut.Table_Name)){
			list.add(new HBC_CalloutInOut());
		}else if (tableName.equals(X_HBC_ProductTrx.Table_Name)){
			list.add(new HBC_CalloutProductApproval());
		}else if (tableName.equals(MInOutLine.Table_Name)){
			list.add(new HBC_CalloutInOutLine());
		}else if (tableName.equals(MOrderLine.Table_Name)){
			list.add(new HBC_CalloutOrderLine());
		}else if (tableName.equals(MInvoice.Table_Name)){
			list.add(new HBC_CalloutInvoice());
		}else if (tableName.equals(MInvoiceLine.Table_Name)){
			list.add(new HBC_CalloutInvoiceLine());
		}else if (tableName.equals(MEmployee.Table_Name)){
			list.add(new HC_CalloutEmployee());
		}else if (tableName.equals(MJob.Table_Name)){
			list.add(new HC_CalloutJob());
		}else if (tableName.equals(MEmployeeDataChange.Table_Name)){
			list.add(new HC_CalloutEmployeeDataChange());
		}else if (tableName.equals(MJobDataChange.Table_Name)){
			list.add(new HC_CalloutJobDataChange());
		}else if (tableName.equals(MContractTerm.Table_Name)){
			list.add(new HBC_CalloutContractTerm());
		}else if (tableName.equals(MGeneralTerm.Table_Name)){
			list.add(new HBC_CalloutGeneralTerm());
		}else if (tableName.equals(X_HBC_ShipActivity.Table_Name)){
			list.add(new HBC_CalloutShipActivity());		        
		}else if (tableName.equals(X_HBC_CrewOnOff.Table_Name)){
			list.add(new HBC_CalloutCrewOnOff());	
		}else if (tableName.equals(X_HBC_BBMActivity.Table_Name)){
			list.add(new HBC_CalloutBBMActivity());
		}else if (tableName.equals(MPosition.Table_Name)){
			list.add(new HBC_CalloutPosition());
		}else if (tableName.equals(X_HBC_CostActivity.Table_Name)){
			list.add(new HBC_CalloutCostActivity());
		}else if(tableName.equals(MDemurrage.Table_Name)){
			list.add(new HBC_CalloutDemurrage());
		}else if(tableName.equals(MARCalculationLine.Table_Name)){
			list.add(new HBC_CalloutARCalculation());
		}else if (tableName.equals(X_C_ProjectLine.Table_Name)){
			list.add(new HBC_CalloutProjectLine());
		}else if (tableName.equals(X_C_ProjectTask.Table_Name)){
			list.add(new HBC_CalloutProjectTask());
		}else if (tableName.equals(MPriceListBBM.Table_Name)){
			list.add(new HBC_CalloutPriceListBBM());
		}else if (tableName.equals(MRequisitionLine.Table_Name)){
			list.add(new HBC_CalloutRequisitionLine());
		}else if (tableName.equals(MRequisition.Table_Name)){
			list.add(new HBC_CalloutRequisitionLine());
		}else if(tableName.equals(X_HBC_FuelTripAllocation.Table_Name)){
			list.add(new HBC_CalloutFuelTripAllocation());
		}else if (tableName.equals(MFuelUsage.Table_Name)){
			list.add(new HBC_CalloutFuelUsage());
 		}else if (tableName.equals(MRMA.Table_Name)){
 			list.add(new HBC_CalloutRMA());
 		}else if (tableName.equals(MPayment.Table_Name)){
 			list.add(new HBC_CalloutPayment());
 		}else if (tableName.equals(MInventoryLine.Table_Name)){
 			list.add(new HBC_CalloutInventory());
 		}else if (tableName.equals(MShipDocumentDock.Table_Name)){
 			list.add(new HBC_CalloutShipDocumentDock());
 		}else if (tableName.equals(MBBMPlan.Table_Name)){
 			list.add(new HBC_CalloutBBMPlan());
 		}else if (tableName.equals(MAssetAddition.Table_Name)){
 			list.add(new HBC_CalloutAssetAddition());
 		}else if (tableName.equals(MBankStatement.Table_Name)){
 			list.add(new HBC_CalloutBankStatement());
 		}else if (tableName.equals(MAssetDisposed.Table_Name)){
 			list.add(new HBC_CalloutAssetDisposal());
 		}else if (tableName.equals(MTCSAmortizationPlan.Table_Name)){
 			list.add(new HBC_CalloutAmortizationPlan());
 		}
		
		
		
		return list != null ? list.toArray(new IColumnCallout[0]) : new IColumnCallout[0];
	}

}
