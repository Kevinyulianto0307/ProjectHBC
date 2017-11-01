package org.toba.habco.ar.process;

import org.compiere.process.SvrProcess;
import org.toba.habco.model.MARCalculation;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;

/**
 * @author Stephan
 * Process for complete AR Calculation
 */
public class HBC_CompleteARCalculation extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int HBC_ARCalculation_ID = getRecord_ID();
		
		// set header read only
		MARCalculation calc = new MARCalculation(getCtx(), HBC_ARCalculation_ID, get_TrxName());
		calc.setProcessed(true);
		calc.saveEx();
		
		// set line read only
		MARCalculationLine[] calcLines = calc.getCalculationLine();
		for (MARCalculationLine calcLine : calcLines) {
			calcLine.setProcessed(true);
			calcLine.saveEx();
		}
		
		// set trip read only
		MTrip trip = (MTrip) calc.getHBC_Trip();
		trip.setIsARCalculation(true);
		trip.setProcessed(true);
		trip.saveEx();
		
		// set position and activity read only
		MPosition[] positions = trip.getPosition();
		for (MPosition position : positions) {
			position.setProcessed(true);
			position.saveEx();
			
			MShipActivity[] shipActivities = position.getShipActivity();
			for (MShipActivity shipActivity : shipActivities) {
				shipActivity.setProcessed(true);
				shipActivity.saveEx();
			}
		}
		
		return "";
	}

}
