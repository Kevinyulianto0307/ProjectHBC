package org.toba.habco.factory;

import org.adempiere.webui.apps.form.WCreateFromARCalculation;
import org.adempiere.webui.apps.form.WCreateFromBBMPlan;
import org.adempiere.webui.apps.form.WCreateFromCostActivity;
import org.adempiere.webui.apps.form.WCreateFromInvoiceHBC;
import org.adempiere.webui.apps.form.WCreateFromOrderHBC;
import org.adempiere.webui.apps.form.WCreateFromPayment;
import org.adempiere.webui.apps.form.WCreateFromRMAHBC;
import org.adempiere.webui.apps.form.WCreateFromShipmentHBC;
import org.adempiere.webui.apps.form.WCreateFromStatementHBC;
import org.compiere.grid.ICreateFrom;
import org.compiere.grid.ICreateFromFactory;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_RMA;
import org.compiere.model.MBankStatement;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.util.Env;
import org.toba.habco.model.MBBMPlan;

public class HBC_CreateFromFactory implements ICreateFromFactory {

	@Override
	public ICreateFrom create(GridTab mTab) {

		String tableName = mTab.getTableName();
		if (tableName.equals(I_C_Order.Table_Name))
			return new WCreateFromOrderHBC(mTab);
		else if (tableName.equals(I_C_Invoice.Table_Name)){
			
			MInvoice invoice = new MInvoice(Env.getCtx(),(Integer)mTab.getValue("C_Invoice_ID"),null);		
			
			/* temporary comment @Stephan
			
			if(invoice.isSOTrx()){
					return new WCreateFromARCalculation(mTab);
				}else{
					if(invoice.get_ValueAsBoolean("IsCreateAllocate")){
						return new WCreateFromCostActivity(mTab);
					}else{
						return new WCreateFromInvoiceHBC(mTab);
					}
				}
			}*/
			
			if(invoice.get_ValueAsBoolean("IsCreateARCalculation"))
				return new WCreateFromARCalculation(mTab);
			else if(invoice.get_ValueAsBoolean("IsCreateAllocate"))
				return new WCreateFromCostActivity(mTab);
			else
				return new WCreateFromInvoiceHBC(mTab);
			
		}
		else if(tableName.equals(I_C_Payment.Table_Name))
			return new WCreateFromPayment(mTab);
		else if(tableName.equals(I_M_RMA.Table_Name))
			return new WCreateFromRMAHBC(mTab);
		else if(tableName.equals(MInOut.Table_Name))
			return new WCreateFromShipmentHBC(mTab);
		else if(tableName.equals(MBankStatement.Table_Name))
			return new WCreateFromStatementHBC(mTab);
		else if(tableName.equals(MBBMPlan.Table_Name))
			return new WCreateFromBBMPlan(mTab);
		return null;
		
		
	}

}
