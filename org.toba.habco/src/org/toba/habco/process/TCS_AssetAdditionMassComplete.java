package org.toba.habco.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAssetAddition;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class TCS_AssetAdditionMassComplete extends SvrProcess {
	
	
	private Timestamp dateAcctFrom = null;
	private Timestamp dateAcctTo = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		
		for (ProcessInfoParameter param : params) {
			if (param.getParameterName().equals("DateAcct")) {
				dateAcctFrom = param.getParameterAsTimestamp();
				dateAcctTo = param.getParameter_ToAsTimestamp();
			} else {
				log.log(Level.SEVERE, "Prepare - Unknown Parameter: " + param.getParameterName());
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		//String sqlWhere = "DocStatus IN ('DR','IP','IN') AND DateAcct BETWEEN " + dateAcctFrom + " AND " + dateAcctTo;
		String sqlWhere = "DocStatus IN ('DR','IP','IN') AND CreatedBy=1001896";
		
		int[] ids = new Query(getCtx(), MAssetAddition.Table_Name, sqlWhere , get_TrxName())
					.setOnlyActiveRecords(true)
					.getIDs();
		
		if (ids.length == 0)
			return "No Asset Addition Selected";
		
		int count = 1;
		for (int id: ids) {
			MAssetAddition assetAdd = new MAssetAddition(getCtx(), id, get_TrxName());
			assetAdd.processIt(DocAction.ACTION_Complete);
			assetAdd.saveEx();
			count++;
		}
		return "Completed Asset Addition: " + count;
	}

}
