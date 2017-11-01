package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.Msg;

public class MPriceListBBM extends X_HBC_PriceList_BBM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5808794094038727761L;

	public MPriceListBBM(Properties ctx, int HBC_PriceList_BBM_ID,
			String trxName) {
		super(ctx, HBC_PriceList_BBM_ID, trxName);
	}
	
	public MPriceListBBM(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**	Process Message 			*/
	protected String		m_processMsg = null;
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		
		if(!newRecord)
			return true;
		
		String where = COLUMNNAME_C_Period_ID+"="+getC_Period_ID();
		boolean match = new Query(getCtx(), MPriceListBBM.Table_Name, where, get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.match();
		
		if(match){
			log.saveError("Error", Msg.getMsg(getCtx(), "PeriodNotValid"));
			return false;
		}
		
		return true;
	}
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
}
