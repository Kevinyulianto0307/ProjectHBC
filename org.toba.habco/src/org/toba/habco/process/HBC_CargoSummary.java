package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MBarge;
import org.toba.habco.model.MMatchARCalculation;
import org.toba.habco.model.MPosition;
import org.toba.habco.model.MShipActivity;
import org.toba.habco.model.MTrip;
import org.toba.habco.model.MTugboat;
import org.toba.habco.model.X_HBC_PortPosition;
import org.toba.habco.model.X_T_CargoSummary;

public class HBC_CargoSummary extends SvrProcess{

	/**final string activities*/
	final static protected String LOADING = "LOA";
	final static protected String DISCHARGING = "DIS";
	//final static protected int report_id = 1100295;
	
	//String username = "";
	int p_C_BPartner_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_BPartner_ID")){
				p_C_BPartner_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		int C_Invoice_ID = getRecord_ID();
		
		// delete old calculation by invoice
		deleteOldCalculation(C_Invoice_ID);
		
		MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		
		BigDecimal total_loading_draft_survey = Env.ZERO;
		
		for (MInvoiceLine invoiceLine : invoice.getLines()) {
			
			// get ar calculation line
			String where = "C_InvoiceLine_ID=?";
			int M_MatchARCalculation_ID = new Query(getCtx(), MMatchARCalculation.Table_Name, where, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setParameters(new Object[]{invoiceLine.get_ID()})
			.firstId();
			
			MMatchARCalculation match = new MMatchARCalculation(getCtx(), M_MatchARCalculation_ID, get_TrxName());
			int HBC_ARCalculationLine_ID = match.getHBC_ARCalculationLine_ID();
			
			MARCalculationLine arCalcLine = new MARCalculationLine(getCtx(), HBC_ARCalculationLine_ID, get_TrxName());
			int from_position_id = arCalcLine.getFrom_PortPosition_ID();
			int to_position_id = arCalcLine.getTo_PortPosition_ID();
			
			// initialize variable
			Timestamp completeLoading = null;
			Timestamp completeUnloading = null;
			int HBC_Tugboat_ID = 0;
			int HBC_Barge_ID = 0;
			int loading_port_id = 0;
			int discharging_port_id = 0;
			BigDecimal loading_draft_survey = arCalcLine.getQtyCharge();
			BigDecimal tonnage = arCalcLine.getQtyCharge();
			BigDecimal unitPrice = arCalcLine.getUnitPrice();
			total_loading_draft_survey = total_loading_draft_survey.add(loading_draft_survey);
			
			// get from ship activity loading and discharging
			MTrip trip = (MTrip) arCalcLine.getHBC_Trip();
			for (MPosition position : trip.getPosition()) {
				if(position.getHBC_PortPosition_ID() == from_position_id){
					for (MShipActivity shipActivity : position.getShipActivity()) {
						if(shipActivity.getC_Activity().getValue().equals(LOADING)){
							completeLoading = shipActivity.getFinishDate();
							HBC_Tugboat_ID = shipActivity.getHBC_Tugboat_ID();
							HBC_Barge_ID = shipActivity.getHBC_Barge_ID();
							loading_port_id = position.getHBC_PortPosition_ID();
						}
					}
				}
				else if(position.getHBC_PortPosition_ID() == to_position_id){
					for (MShipActivity shipActivity : position.getShipActivity()){
						if(shipActivity.getC_Activity().getValue().equals(DISCHARGING)){
							completeUnloading = shipActivity.getFinishDate();
							discharging_port_id = position.getHBC_PortPosition_ID();
		 				}
					}
				}
			}
			
			// get all location of partner
			MBPartner partner = new MBPartner(getCtx(), invoice.getC_BPartner_ID(), get_TrxName());
			MBPartnerLocation[] locations = partner.getLocations(true);
			StringBuilder customerLocation = new StringBuilder();
			for (MBPartnerLocation location : locations) {
				customerLocation.append(location.getName());
				customerLocation.append("\n");
			}
			
			// get user position
			String whereClause = MUser.COLUMNNAME_C_BPartner_ID+"=?";
			int AD_User_ID = new Query(getCtx(), MUser.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.setParameters(new Object[]{p_C_BPartner_ID})
			.firstId();
			MUser user = new MUser(getCtx(), AD_User_ID, get_TrxName());
			String userposition = user.getC_Job().getName();
			
			// initialize tugboat barge and position
			MTugboat tugboat = new MTugboat(getCtx(), HBC_Tugboat_ID, get_TrxName());
			MBarge barge = new MBarge(getCtx(), HBC_Barge_ID, get_TrxName());
			X_HBC_PortPosition loadingPort = new X_HBC_PortPosition(getCtx(), loading_port_id, get_TrxName());
			X_HBC_PortPosition dischargingPort = new X_HBC_PortPosition(getCtx(), discharging_port_id, get_TrxName());
			
			MLocation location = MLocation.getBPLocation(getCtx(), invoice.getC_BPartner_Location_ID(), get_TrxName());
			
			// insert into temporary table
			X_T_CargoSummary cargoSum = new X_T_CargoSummary(getCtx(), 0, get_TrxName());
			cargoSum.setAD_PInstance_ID(getAD_PInstance_ID());
			cargoSum.setAD_Org_ID(invoiceLine.getAD_Org_ID());
			cargoSum.setcustomername(partner.getName());
			cargoSum.setcompleted_loading(completeLoading);
			cargoSum.setcompleted_unloading(completeUnloading);
			cargoSum.settugboatname(tugboat.getName());
			cargoSum.setbargename(barge.getName());
			cargoSum.setloadingportname(loadingPort.getName());
			cargoSum.setdischargingportname(dischargingPort.getName());
			cargoSum.setLoadingDraftSurvey(loading_draft_survey);
			cargoSum.setcustomerlocation(customerLocation.toString()); //keep
			cargoSum.settonnage(tonnage);
			cargoSum.setuserposition(userposition);
			cargoSum.setDateInvoiced(invoice.getDateInvoiced());
			cargoSum.setUserName(user.getName());
			System.out.println(arCalcLine.getUnitPrice());
			cargoSum.set_ValueOfColumn(MARCalculationLine.COLUMNNAME_UnitPrice, arCalcLine.getUnitPrice());
			cargoSum.set_ValueOfColumn("C_Invoice_ID", invoice.getC_Invoice_ID());
			//@phie
			cargoSum.set_ValueOfColumn("Address1", location.getAddress1());
			cargoSum.set_ValueOfColumn("Address2", location.getAddress2());
			cargoSum.set_ValueOfColumn("Address3", location.getAddress3());
			cargoSum.set_ValueOfColumn("Address4", location.getAddress4());
			cargoSum.set_ValueOfColumn("City", location.getCity());
			cargoSum.set_ValueOfColumn("Postal", location.getPostal());
			//end phie
			cargoSum.saveEx();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE C_Invoice SET TotalLoadingDraftSurvey="+total_loading_draft_survey+" WHERE C_Invoice_ID=?");
		int no = DB.executeUpdate(sb.toString(), invoice.get_ID(), get_TrxName());
		log.info("UPDATED INVOICE#"+no);
		
		/*
		MProcess proc = new MProcess(Env.getCtx(), report_id, get_TrxName());
		MPInstance instance = new MPInstance(proc, proc.getAD_Process_ID());

		ProcessInfo pi = new ProcessInfo("CARGO_SUMMARY", report_id);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());
		
		ArrayList<ProcessInfoParameter> list = new ArrayList<ProcessInfoParameter>();
		list.add(new ProcessInfoParameter(X_T_CargoSummary.COLUMNNAME_AD_PInstance_ID, getAD_PInstance_ID(), null, null, null));

		ProcessInfoParameter[] pars = new ProcessInfoParameter[list.size()];
		list.toArray(pars);
		pi.setParameter(pars);
		//
		ProcessUtil.startJavaProcess(Env.getCtx(), pi, Trx.get(get_TrxName(), true));
		*/
		return "";
	}

	private void deleteOldCalculation(int C_Invoice_ID){
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM T_CargoSummary WHERE C_Invoice_ID = ?");
		int no = DB.executeUpdate(sb.toString(), C_Invoice_ID, get_TrxName());
		log.info("DELETE T_CargoSummary "+no);
	}
	
}
