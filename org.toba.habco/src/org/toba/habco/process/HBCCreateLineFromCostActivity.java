package org.toba.habco.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.toba.habco.model.X_HBC_CostActivity;
import org.toba.habco.model.X_T_ReferenceCode;

public class HBCCreateLineFromCostActivity extends SvrProcess {


	int p_C_Invoice_ID=0;
	int p_ReferenceCode=0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ReferenceCode")){
				p_ReferenceCode = para[i].getParameterAsInt();
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_Invoice_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		if(getCostActivitiesID().length>0){
			MInvoice invoice = new MInvoice(getCtx(),p_C_Invoice_ID,get_TrxName());
			int Line=0;
			for(int id:getCostActivitiesID()){
				X_HBC_CostActivity costactivity = new X_HBC_CostActivity(getCtx(), id, get_TrxName());
				if(costactivity.getHBC_Barge_ID()>0 && costactivity.getHBC_Tugboat_ID()>0)
					setpercentage(costactivity.getHBC_Tugboat_ID(), costactivity.getHBC_Barge_ID(), id, invoice.getC_Invoice_ID(), Line);
				else {
				MInvoiceLine invline = new MInvoiceLine(invoice);
				invline.set_ValueOfColumn("HBC_Tugboat_ID", costactivity.getHBC_Tugboat_ID());
				invline.set_ValueOfColumn("HBC_Barge_ID",costactivity.getHBC_Barge_ID());
				invline.setC_Tax_ID(invoice.getC_Tax_ID());
				invline.setM_Product_ID(costactivity.getM_Product_ID());
				invline.setPriceActual(costactivity.getAmount());
				invline.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
				invline.setQtyEntered(Env.ONE);
				invline.setQty(Env.ONE);
				invline.setPriceEntered(costactivity.getAmount());
				invline.saveEx();
				}
			}
			setIsInvoice();
			return "Line Created !";			
		}else{
			return "Error Cost Activities not Found";
		}
	}
	
	protected void setIsInvoice(){
		int refcode = new Query(getCtx(),X_T_ReferenceCode.Table_Name,"ReferenceCode="+p_ReferenceCode,get_TrxName()).firstId();
		X_T_ReferenceCode referencecode = new X_T_ReferenceCode(getCtx(), refcode, get_TrxName());
		referencecode.set_ValueOfColumn("IsInvoiced", true);
		referencecode.saveEx();
	}
	
	protected int[] getCostActivitiesID(){
		
		String where="ReferenceCode="+p_ReferenceCode;
		int[] HBC_CostActivity_IDs = new Query(getCtx(), X_HBC_CostActivity.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
	
		return HBC_CostActivity_IDs;
	}
	
	protected int setpercentage(int HBC_Tugboat_ID, int HBC_Barge_ID,int HBC_CostActivity_ID,int C_Invoice_ID,int Line){
		MInvoice invoice = new MInvoice(getCtx(),C_Invoice_ID,get_TrxName());
		X_HBC_CostActivity costactivity = new X_HBC_CostActivity(getCtx(), HBC_CostActivity_ID, get_TrxName());
		
		for(int i =0;i<2;i++){
			if(i==0){
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				MProduct product= new MProduct(getCtx(),costactivity.getM_Product_ID(),get_TrxName());

				BigDecimal percentagetugboat = new BigDecimal(product.get_ValueAsString("HBC_Percentage_Split_TugBoat"));
				BigDecimal totalamount = costactivity.getAmount().multiply(percentagetugboat);
				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.setLine(Line+=10);
				invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
				invoiceLine.saveEx();
				
			}else if(i==1){
				MProduct product= new MProduct(getCtx(),costactivity.getM_Product_ID(),get_TrxName());
				BigDecimal percentagebarge = new BigDecimal(product.get_ValueAsString("HBC_Percentage_Split_Barge"));
				BigDecimal totalamount = costactivity.getAmount().multiply(percentagebarge);
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.setLine(Line+=10);
				invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
				invoiceLine.saveEx();
				
			}
		}
		return 0;
	}
}
