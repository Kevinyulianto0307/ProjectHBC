package org.toba.habco.model.validator;

import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.osgi.service.event.Event;
import org.toba.habco.model.X_HBC_ProductMerk;

public class HBC_ProductMerkValidator {

	public static String executeEvent(Event event, PO po) {
		String msg = "";
		X_HBC_ProductMerk productMerk = new X_HBC_ProductMerk(po.getCtx(), po.get_ID(), po.get_TrxName());
		if (event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE) 
				|| event.getTopic().equals(IEventTopics.PO_AFTER_NEW)){
			msg = checkExistsSameName(productMerk);
		}
		return msg;
	}

	//@phie
	/**
	 * check existing data with the same name
	 * @param Event, PO
	 * @return null message
	 */
	private static String checkExistsSameName(X_HBC_ProductMerk productMerk) {
		String whereClause = "lower(name) = ? AND HBC_ProductMerk_ID != ? ";
		
		boolean match = new Query(productMerk.getCtx(), X_HBC_ProductMerk.Table_Name, whereClause, productMerk.get_TrxName())
		.setParameters(new Object[]{productMerk.getName().toLowerCase(), productMerk.getHBC_ProductMerk_ID()})
		.setOnlyActiveRecords(true)
		.match();
		
		if(match)
			throw new AdempiereException("There's existing data with that name");
		
		return "";
	}
	//end phie
}
