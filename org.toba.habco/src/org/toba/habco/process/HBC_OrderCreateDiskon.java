package org.toba.habco.process;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.toba.habco.model.HBC_MOrder;

/**
 * @author Phie Albert
 */

public class HBC_OrderCreateDiskon extends SvrProcess {

	int p_C_Order_ID;
	int p_C_Charge_ID;;
	
	BigDecimal p_discountAmt=Env.ZERO;
	BigDecimal p_discountPercentage=Env.ZERO;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if(name.equals("DiscountPO")){
				p_discountPercentage=para[i].getParameterAsBigDecimal();
			}
			else if(name.equals("DiscountAmt")){
				p_discountAmt=para[i].getParameterAsBigDecimal();
			}
			//else if(name.equals("C_Charge_ID")){
				//p_C_Charge_ID=para[i].getParameterAsInt();
			//}
		}
		p_C_Order_ID=getRecord_ID();		
	}

	@Override
	protected String doIt() throws Exception {
		String msg="";
		
		HBC_MOrder order = new HBC_MOrder(getCtx(),p_C_Order_ID,get_TrxName());
		
		if(order.getDocStatus().equals("CO"))
			throw new IllegalArgumentException("Purchase order has already completed");
		
		if(p_discountAmt.compareTo(Env.ZERO) != 0)
		{
			if(p_discountAmt.compareTo(order.getTotalLines()) > 0)
				throw new IllegalArgumentException("Discount Amount greater than Total Lines");
			createDiskonWithAmount();
		}
		else if(p_discountPercentage.compareTo(Env.ZERO) != 0)
		{
			createDiskonWithPercentage();
		}
		
		msg="PO Line Discount Updated!";
		return msg;
	}
	
	public void createDiskonWithAmount()
	{
		HBC_MOrder order = new HBC_MOrder(getCtx(),p_C_Order_ID,get_TrxName());
		
		BigDecimal total = Env.ZERO;
		BigDecimal amount = Env.ZERO;
		BigDecimal disc = Env.ZERO;
		BigDecimal discAmt = Env.ZERO;
		BigDecimal priceEntered = Env.ZERO;
		BigDecimal lineNetAmt = Env.ZERO;
		BigDecimal sum_disc = Env.ZERO;
		BigDecimal sum_discAmt = Env.ZERO;
		BigDecimal sum_totalLines = Env.ZERO;
		MOrderLine[] line = order.getLines();
		
		//calculate sum of amount (pricelist * qty)
		for(int i=0 ; i<line.length ; i++)
		{
			amount = ((BigDecimal)line[i].getPriceEntered()).multiply(((BigDecimal)line[i].getQtyEntered()));
			total = total.add(amount);
		}
		
		/*
		 * Amount discount will be divided based on percentage LineNetAmt in PO line
		 */
		
		MathContext mc = new MathContext(10, RoundingMode.HALF_UP);;
		for(int i=0 ; i<line.length-1; i++)
		{
			MOrderLine sLine = line[i];
			amount = ((BigDecimal)sLine.getPriceList()).multiply(((BigDecimal)sLine.getQtyEntered()));
			disc = (amount.multiply(Env.ONEHUNDRED).divide(total,mc).setScale(5, RoundingMode.HALF_UP));
			discAmt = (disc.multiply(p_discountAmt).divide(Env.ONEHUNDRED,mc).setScale(5, RoundingMode.HALF_UP));
			priceEntered = amount.subtract(discAmt).divide(sLine.getQtyEntered(),mc).setScale(5, RoundingMode.HALF_UP);
			if(order.getC_Currency().getISO_Code().equals("IDR"))
				lineNetAmt = priceEntered.multiply(sLine.getQtyEntered()).setScale(0, RoundingMode.HALF_UP);
			else
				lineNetAmt = priceEntered.multiply(sLine.getQtyEntered()).setScale(2, RoundingMode.HALF_UP);
			
			sLine.setDiscount(disc);
			sLine.set_ValueOfColumn("DiscAmt", discAmt);
			sLine.setPriceEntered(priceEntered);
			sLine.setLineNetAmt(lineNetAmt);
			
			sum_disc = sum_disc.add(disc);
			sum_discAmt = sum_discAmt.add(discAmt);
			sum_totalLines = sum_totalLines.add(lineNetAmt);
			sLine.saveEx();
		}
		
		int lastIdx = line.length-1;
		amount = ((BigDecimal)line[lastIdx].getPriceList()).multiply(((BigDecimal)line[lastIdx].getQtyEntered()));
		BigDecimal LastDataDisc = Env.ONEHUNDRED.subtract(sum_disc);
		BigDecimal LastDataDiscAmt = p_discountAmt.subtract(sum_discAmt);
		BigDecimal LastDataPriceEntered = (amount.subtract(p_discountAmt.subtract(sum_discAmt))).divide(line[lastIdx].getQtyEntered(), new MathContext(10)).setScale(5, RoundingMode.HALF_UP);
		BigDecimal lastDataLineNetAmt = Env.ZERO;
		
		if(order.getC_Currency().getISO_Code().equals("IDR"))
			lastDataLineNetAmt = LastDataPriceEntered.multiply(line[lastIdx].getQtyEntered()).setScale(0, RoundingMode.HALF_UP);
		else
			lastDataLineNetAmt = LastDataPriceEntered.multiply(line[lastIdx].getQtyEntered()).setScale(2, RoundingMode.HALF_UP);
		
		line[lastIdx].setDiscount(LastDataDisc);
		line[lastIdx].set_ValueOfColumn("DiscAmt", LastDataDiscAmt);
		line[lastIdx].setPriceEntered(LastDataPriceEntered);
		line[lastIdx].setLineNetAmt(lastDataLineNetAmt);
		sum_totalLines = sum_totalLines.add(lastDataLineNetAmt);
		line[lastIdx].saveEx();
		
		for(int i=0 ; i<line.length; i++)
		{
			line[i].setDiscount(Env.ONEHUNDRED.subtract(line[i].getPriceEntered().multiply(Env.ONEHUNDRED).divide(line[i].getPriceList(),mc).setScale(5, RoundingMode.HALF_UP)));
			line[i].saveEx();
		}
		
		//set header
		order.setTotalLines(sum_totalLines);
		order.set_ValueOfColumn("DiscountAmt", p_discountAmt);
		order.set_ValueOfColumn("DiscountPO", p_discountAmt.multiply(Env.ONEHUNDRED).divide(total, mc).setScale(2, RoundingMode.HALF_UP));
        order.saveEx();
	}
	
	public void createDiskonWithPercentage()
	{
		HBC_MOrder order = new HBC_MOrder(getCtx(),p_C_Order_ID,get_TrxName());
		
		BigDecimal amount = Env.ZERO;
		BigDecimal disc = p_discountPercentage;
		BigDecimal discAmt = Env.ZERO;
		BigDecimal priceEntered = Env.ZERO;
		BigDecimal lineNetAmt = Env.ZERO;
		BigDecimal sum_discAmt = Env.ZERO;
		BigDecimal sum_totalLines = Env.ZERO;
		MOrderLine[] line = order.getLines();
		
		MathContext mc = new MathContext(10, RoundingMode.HALF_UP);;
		for(int i=0 ; i<line.length; i++)
		{
			MOrderLine sLine = line[i];
			amount = ((BigDecimal)sLine.getPriceList()).multiply(((BigDecimal)sLine.getQtyEntered()));
			discAmt = (disc.multiply(amount).divide(Env.ONEHUNDRED,mc).setScale(5, RoundingMode.HALF_UP));
			priceEntered = amount.subtract(discAmt).divide(sLine.getQtyEntered(),mc).setScale(5, RoundingMode.HALF_UP);
			
			if(order.getC_Currency().getISO_Code().equals("IDR"))
				lineNetAmt = priceEntered.multiply(sLine.getQtyEntered()).setScale(0, RoundingMode.HALF_UP);
			else
				lineNetAmt = priceEntered.multiply(sLine.getQtyEntered()).setScale(2, RoundingMode.HALF_UP);
			
			sLine.setDiscount(disc);
			sLine.set_ValueOfColumn("DiscAmt", discAmt);
			sLine.setPriceEntered(priceEntered);
			sLine.setLineNetAmt(lineNetAmt);
				
			sum_discAmt = sum_discAmt.add(discAmt);
			sum_totalLines = sum_totalLines.add(lineNetAmt);
			sLine.saveEx();
		}
		
		//set header
		order.setTotalLines(sum_totalLines);
		order.set_ValueOfColumn("DiscountAmt", sum_discAmt);
		order.set_ValueOfColumn("DiscountPO", p_discountPercentage);
        order.saveEx();
	}
}
