package org.toba.habco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MSequence;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

public class HBC_MRequisition extends MRequisition{

	/**
	 * 
	 */
	private static final long serialVersionUID = -283399668566165539L;

	public HBC_MRequisition(Properties ctx, int M_Requisition_ID, String trxName) {
		super(ctx, M_Requisition_ID, trxName);
	}
	
	public HBC_MRequisition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**	Process Message 			*/
	private String			m_processMsg = null;
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		if(m_processMsg == null)
			m_processMsg = super.getProcessMsg();
		
		return m_processMsg;
	}	//	getProcessMsg
	
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		for (int i = 0; i < options.length; i++) {
			options[i] = null;
		}

		index = 0;

		if (docStatus.equals(DocAction.STATUS_Drafted)) {
			options[index++] = DocAction.ACTION_Complete;
			//options[index++] = DocAction.ACTION_Prepare;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_InProgress)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_Completed)) {
			options[index++] = DocAction.ACTION_Close;
			options[index++] = DocAction.ACTION_Void;
			options[index++] = DocAction.ACTION_ReActivate;
		} else if (docStatus.equals(DocAction.STATUS_Invalid)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		}

		return index;
	}
	
	
	protected boolean beforeSave (boolean newRecord)
	{
		/* @win price list not mandatory
		if (getM_PriceList_ID() == 0)
			setM_PriceList_ID();
		*/
		
		if(newRecord){
			setDocumentNo(getDocumentNoRomanFormat());
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		//	Close Not delivered Qty
		MRequisitionLine[] lines = getLines();
		BigDecimal totalLines = Env.ZERO;
		for (int i = 0; i < lines.length; i++)
		{
			MRequisitionLine line = lines[i];
			
			//@TommyAng
			//BigDecimal finalQty = line.getQty();
			BigDecimal finalQty = Env.ZERO;
			/*
			if (line.getC_OrderLine_ID() == 0)
				finalQty = Env.ZERO;				
			else
			{
				MOrderLine ol = new MOrderLine (getCtx(), line.getC_OrderLine_ID(), get_TrxName());
				finalQty = ol.getQtyOrdered();
			}
			*/
			
			if(getTempIDs(line.get_ID()).length>0){
				
				for(int matchPRID : getTempIDs(line.get_ID())){
					MMatchPR matchPR = new MMatchPR(getCtx(), matchPRID, get_TrxName());
					finalQty = finalQty.add(matchPR.getQtyOrdered());
				}
				finalQty = finalQty.setScale(2, RoundingMode.HALF_UP);
			}
			//end @TommyAng
			
			//	final qty is not line qty
			if (finalQty.compareTo(line.getQty()) != 0)
			{
				String description = line.getDescription();
				if (description == null)
					description = "";
				
				//@TommyAng
				//description += " [" + line.getQty() + "]"; 
				description += " Closed Qty = " + line.getQty().subtract(finalQty) + ", Ordered Qty = " + finalQty; 
				//end @TommyAng
				
				line.setDescription(description);
				//@TommyAng
				line.setQty(Env.ZERO);
				line.setLineNetAmt();
				line.saveEx();
			}
			totalLines = totalLines.add (line.getLineNetAmt());
		}
		if (totalLines.compareTo(getTotalLines()) != 0)
		{
			setTotalLines(totalLines);
			saveEx();
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	closeIt
	
	
	public String getDocumentNoRomanFormat(){
		String DocumentNo="";
		String romanmonth=RomanNumber(getMonthDate(getDateDoc()));
		String yeardate = String.valueOf(getYearDate(getDateDoc()));
		MDocType doctype=new MDocType(getCtx(),getC_DocType_ID(),get_TrxName());
		MSequence sequence = new MSequence(getCtx(),doctype.getDocNoSequence_ID(),get_TrxName());
		MWarehouse warehouse = new MWarehouse(getCtx(),getM_Warehouse_ID(),get_TrxName());
		DocumentNo=sequence.getCurrentNext()+doctype.get_ValueAsString("TypeDoc")+romanmonth+"/"+warehouse.getName()+"/"+yeardate;
		sequence.setCurrentNext(sequence.getCurrentNext()+1);
		sequence.saveEx();
		return DocumentNo;
	}
	
	protected int getYearDate(Timestamp Date){
		int year =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		year= calender.get(Calendar.YEAR);
		return year;
	}

	
	protected int getMonthDate(Timestamp Date){
		int month =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		month = calender.get(Calendar.MONTH)+1;
		return month;
	}

	/**
	 * get roman number format
	 */
	public static String RomanNumber(int input) {
		if (input < 1 || input > 3999)
			return "Invalid Roman Number Value";
		String s = "";
		while (input >= 1000) {
			s += "M";
			input -= 1000;        }
		while (input >= 900) {
			s += "CM";
			input -= 900;
		}
		while (input >= 500) {
			s += "D";
			input -= 500;
		}
		while (input >= 400) {
			s += "CD";
			input -= 400;
		}
		while (input >= 100) {
			s += "C";
			input -= 100;
		}
		while (input >= 90) {
			s += "XC";
			input -= 90;
		}
		while (input >= 50) {
			s += "L";
			input -= 50;
		}
		while (input >= 40) {
			s += "XL";
			input -= 40;
		}
		while (input >= 10) {
			s += "X";
			input -= 10;
		}
		while (input >= 9) {
			s += "IX";
			input -= 9;
		}
		while (input >= 5) {
			s += "V";
			input -= 5;
		}
		while (input >= 4) {
			s += "IV";
			input -= 4;
		}
		while (input >= 1) {
			s += "I";
			input -= 1;
		}    
		return s;
	}
	
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		boolean match = new Query(getCtx(), MMatchPR.Table_Name, "M_Requisition_ID=?",get_TrxName())
					.setParameters(get_ID())
					.match();
		
		if (match) {
			m_processMsg = "Abort.. Void related Order first";
			return false;
		}	
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}	
	

	/**
	 * @param HBC_Trip_ID
	 * @return temporary table for calculation
	 */
	protected int[] getTempIDs(int M_RequisitionLine_ID){
		String where = "M_RequisitionLine_ID="+M_RequisitionLine_ID;
		int[] matchPRIDs = new Query(getCtx(), MMatchPR.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			//.setOrderBy("Line")
			.getIDs();
		/*
		if(matchPRIDs.length <=0 )
			log.severe("There is no record");
		*/
		return matchPRIDs;
	}

}
