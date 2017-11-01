package org.toba.habco.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MSequence;
import org.compiere.model.MWarehouse;
import org.compiere.util.Msg;

public class HBC_MMovement extends MMovement{

	public HBC_MMovement(Properties ctx, int M_Movement_ID, String trxName) {
		super(ctx, M_Movement_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3245226299287096628L;
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (getC_DocType_ID() == 0)
		{
			MDocType types[] = MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialMovement);
			if (types.length > 0)	//	get first
				setC_DocType_ID(types[0].getC_DocType_ID());
			else
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), "@NotFound@ @C_DocType_ID@"));
				return false;
			}
		}
		else
		{
			if (newRecord)
			{	
					setDocumentNo(getDocumentNoRomanFormat());
			}
		}
		return true;
	}	//	beforeSave
	
	public String getDocumentNoRomanFormat(){
		String DocumentNo="";
		
		String romanmonth=RomanNumber(getMonthDate(getMovementDate()));
		String yeardate = String.valueOf(getYearDate(getMovementDate()));
		
		MWarehouse whFrom = new MWarehouse(getCtx(), getM_Warehouse_ID(), get_TrxName());
		MWarehouse whTo = new MWarehouse(getCtx(), getM_WarehouseTo_ID(), get_TrxName());
		
		String nameFrom = whFrom.getName();
		String nameTo = whTo.getName();
		
		MDocType doctype=new MDocType(getCtx(),getC_DocType_ID(),get_TrxName());
		MSequence sequence = new MSequence(getCtx(),doctype.getDocNoSequence_ID(),get_TrxName());
		DocumentNo=sequence.getCurrentNext()+"/"+doctype.get_ValueAsString("TypeDoc")+"/"+romanmonth+"/"+yeardate+"/"+nameFrom+"/"+nameTo;
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
}
