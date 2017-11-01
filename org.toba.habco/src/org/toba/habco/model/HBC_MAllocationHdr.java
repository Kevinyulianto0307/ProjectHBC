package org.toba.habco.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MAllocationHdr;
import org.compiere.process.DocAction;

public class HBC_MAllocationHdr extends MAllocationHdr{

	public HBC_MAllocationHdr(Properties ctx, boolean IsManual,
			Timestamp DateTrx, int C_Currency_ID, String description,
			String trxName) {
		super(ctx, IsManual, DateTrx, C_Currency_ID, description, trxName);
		// TODO Auto-generated constructor stub
	}

	public HBC_MAllocationHdr(Properties ctx, int C_AllocationHdr_ID,
			String trxName) {
		super(ctx, C_AllocationHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public HBC_MAllocationHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2980935660001589581L;
	
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
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_InProgress)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_Completed)) {
			options[index++] = DocAction.ACTION_Reverse_Accrual;
			options[index++] = DocAction.ACTION_Reverse_Correct;
		} else if (docStatus.equals(DocAction.STATUS_Invalid)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		}

		
		return index;
	}

}
