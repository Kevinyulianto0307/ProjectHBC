package org.toba.habco.model.validator;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MInvoice;
import org.compiere.model.MMovement;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.osgi.service.event.Event;

public class HBC_MovementValidator {
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MMovement movement = (MMovement) po;
		if ((event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSEACCRUAL)) ||
				(event.getTopic().equals(IEventTopics.DOC_AFTER_REVERSECORRECT))) {
			msg = afterreverse(movement);
		}
		
		return msg;
	}
	
	/**
	 * after reverse
	 * @param MInvoice
	 * @return empty string
	 */
	private static String afterreverse(MMovement movement) {
		String msg = "";
		String sqlUpdate = "UPDATE HBC_ShipActivity SET M_Movement_ID=? WHERE M_Movement_ID=?";
		DB.executeUpdate(sqlUpdate, new Object[]{null,movement.get_ID()},false, movement.get_TrxName());
		
		return msg;
	}
}
