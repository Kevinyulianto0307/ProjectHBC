package org.toba.habco.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

public class MDemurrage extends X_HBC_Demurrage {

	/**
	 * @author yonk
	 */
	private static final long serialVersionUID = -199483759465368362L;

	public MDemurrage(Properties ctx, int HBC_Demurrage_ID, String trxName) {
		super(ctx, HBC_Demurrage_ID, trxName);
	}

	public MDemurrage(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(validateDefaultDemurrage()==true){
			if(isDefault())
			throw new AdempiereException("Save error.. default demurrage already exists");
		}
		
		if (newRecord && getActiveFrom()==null)
			setActiveFrom(this.getHBC_Contract().getDateContract());
		
		return super.beforeSave(newRecord);
	}
	
	private boolean validateDefaultDemurrage(){
	
		String sqlWhere = "IsDefault='Y' AND HBC_Contract_ID="+getHBC_Contract_ID()
				+" AND HBC_Demurrage_ID!="+getHBC_Demurrage_ID();
		
		boolean match = new Query(getCtx(),MDemurrage.Table_Name,sqlWhere,get_TrxName())
					.setOnlyActiveRecords(true)
					.match();
	
		return match;
	}
	
}
