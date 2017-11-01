package org.toba.habco.process;

import java.sql.Timestamp;

import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MShipDocumentLine;

/**
 *@author TommyAng
 *Check Expired Document
 *@return Empty String
 */
public class HBC_CheckExpiredDocument extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		Timestamp curDate = new Timestamp(System.currentTimeMillis());
		
		int[] HBC_ShipDocumentLine_IDs = new Query(getCtx(), MShipDocumentLine.Table_Name, MShipDocumentLine.COLUMNNAME_ValidTo+" < '"+curDate+"'", get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		for (int HBC_ShipDocumentLine_ID : HBC_ShipDocumentLine_IDs) {
			MShipDocumentLine sdl = new MShipDocumentLine(getCtx(), HBC_ShipDocumentLine_ID, get_TrxName());
			sdl.set_ValueOfColumn("IsExpired", true);
			sdl.saveEx();
		}
		return "";
	}

}
