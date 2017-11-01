package org.toba.habco.ar.process;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;

/**
 * @author Stephan
 * process for create invoice line from result calculate trip
 */
public class HBC_CalculateToInvoiceLine extends SvrProcess{

	protected int C_Invoice_ID = 0;
	protected int p_HBC_Trip_ID = 0;
	protected int p_C_Period_ID = 0;
	protected int p_M_Product_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("HBC_Trip_ID")){
				p_HBC_Trip_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("C_Period_ID")){
				p_C_Period_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("M_Product_ID")){
				p_M_Product_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		C_Invoice_ID = getRecord_ID();
		MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		
		String where = "";
		if(p_HBC_Trip_ID > 0)
			where = "HBC_Trip_ID="+p_HBC_Trip_ID;
		else
			where = "C_Period_ID="+p_C_Period_ID;
		
		int[] calc_ids = new Query(getCtx(), MARCalculation.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		if(calc_ids.length <= 0)
			return "Cant find result trip";
		
		int count = 0;
		for (int calc_id : calc_ids) {
			
			MARCalculation calc = new MARCalculation(getCtx(), calc_id, get_TrxName());
			
			for(MARCalculationLine calcLine : calc.getCalculationLine()){
				
				MInvoiceLine invoiceLine = new MInvoiceLine(getCtx(), 0, get_TrxName());
				invoiceLine.setC_Invoice_ID(C_Invoice_ID);
				invoiceLine.setQtyInvoiced(calcLine.getQtyCharge());
				invoiceLine.setQtyEntered(calcLine.getQtyCharge());
				invoiceLine.setPriceEntered(calcLine.getUnitPrice());
				invoiceLine.setPriceActual(calcLine.getUnitPrice());
				invoiceLine.setPriceList(calcLine.getUnitPrice());
				invoiceLine.setM_AttributeSetInstance_ID(0);
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.setM_Product_ID(p_M_Product_ID);
				invoiceLine.setLineNetAmt();
				invoiceLine.saveEx();
				count+=1;
			
			}//end for 2
			
		}//end for 1
		
		return "Created "+count;
	}

}