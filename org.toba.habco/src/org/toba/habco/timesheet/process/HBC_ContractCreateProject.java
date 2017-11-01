package org.toba.habco.timesheet.process;

import java.math.BigDecimal;
import java.util.HashMap;

import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.model.X_C_Project;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MContractTerm;
import org.toba.habco.model.MDemurrage;
import org.toba.habco.model.MGeneralTerm;
import org.toba.habco.model.MPortDistance;

/**
 * 
 * @author yonk
 * HABCO-1480 Process Create Project
 */

public class HBC_ContractCreateProject extends SvrProcess {

	int p_HBC_Contract_ID;
	String BargeOnly="BOL";
	String TugBoatOnly="TOL";
	String NoShip ="NOS";

	@Override
	protected void prepare() {
		p_HBC_Contract_ID = getRecord_ID();

	}

	@Override
	protected String doIt() throws Exception {

		String msg = "";
		if (p_HBC_Contract_ID==0){
			return "No Contract Selected";
		}

		MContract contract = new MContract(Env.getCtx(), p_HBC_Contract_ID, get_TrxName());

		boolean isCommercialContract = isSpotShipmentContract(contract)|| isAnnualContract(contract) ||
				isTimeCharterContract(contract) || isSPHJContract(contract);
		
		boolean isFreightContract = isSpotShipmentContract(contract)|| isAnnualContract(contract) || isSPHJContract(contract);
				
		if (isFreightContract) {
			msg = checkDemurrage();
			if (msg.length() > 0)
				return msg;
		}


		MContractTerm[] cterm = contract.getContractTerm();
		MGeneralTerm[] gterm = contract.getGeneralTerm();

		if((cterm.length==0 || gterm.length==0) && isCommercialContract){
			msg="Error - Contract Term / General Term has no value";
			return msg;
		}

		int projectID = new Query(getCtx(),MProject.Table_Name,"HBC_Contract_ID=?",get_TrxName())
		.setParameters(p_HBC_Contract_ID)
		.setOnlyActiveRecords(true)
		.firstId();

		//@TommyAng
		if(projectID > 0 && !isHasManyProject(contract)){
			return "Contract Release Already Generated";
		}

		//end @TommyAng
		int docno = contract.getNextID()+1;

		X_C_Project project = new X_C_Project(Env.getCtx(),0,get_TrxName());
		project.setValue(contract.getDocumentNo()+"-"+docno);
		project.setName(contract.getDocumentNo()+"-"+docno);
		project.set_CustomColumn("HBC_Contract_ID",contract.getHBC_Contract_ID());
		project.set_CustomColumn("HBC_ContractType",contract.getHBC_ContractType());
		project.set_CustomColumn("HBC_ShipReqType",contract.getHBC_ShipReqType());

		if(isCommercialContract || isDockingContract(contract)){
			project.setDateContract(contract.getDateContract());
			project.setC_PaymentTerm_ID(contract.getC_PaymentTerm_ID());
			project.setC_Currency_ID(contract.getC_Currency_ID());
		}

		if (!isAnnualContract(contract)) {
			project.set_CustomColumn("HBC_PortPosition_ID",contract.getHBC_PortPosition_ID());
			if(contract.getHBC_ShipReqType().equals(BargeOnly))
				project.set_CustomColumn("HBC_Barge_ID",contract.getHBC_Barge_ID());
			else if(contract.getHBC_ShipReqType().equals(TugBoatOnly))
				project.set_CustomColumn("HBC_Tugboat_ID",contract.getHBC_Tugboat_ID());
			else {
				project.set_CustomColumn("HBC_Barge_ID",contract.getHBC_Barge_ID());
				project.set_CustomColumn("HBC_Tugboat_ID",contract.getHBC_Tugboat_ID());
			}
		}

		if(isCommercialContract){
			project.set_CustomColumn("Customer_BPartner_ID",contract.getCustomer_BPartner_ID());
			project.set_CustomColumn("Customer_Location_ID",contract.getCustomer_Location_ID());
			project.set_CustomColumn("Customer_User_ID",contract.getCustomer_User_ID());

			if (!isTimeCharterContract(contract)) {
				BigDecimal distance = MPortDistance.calculateDistance(contract.getFrom_PortPosition_ID(), 
						contract.getTo_PortPosition_ID(), get_TrxName());

				project.set_CustomColumn("LoadingDate",contract.getLoadingDate());
				project.set_CustomColumn("Distance",distance);		
				project.set_CustomColumn("C_UOM_ID",contract.getC_UOM_ID());
				project.set_CustomColumn("AmountOfCargo",contract.getAmountOfCargo());
				project.set_CustomColumn("CargoCondition",contract.getCargoCondition());
				project.set_CustomColumn("From_PortPosition_ID",contract.getFrom_PortPosition_ID());
				project.set_CustomColumn("To_PortPosition_ID",contract.getTo_PortPosition_ID());
				project.set_CustomColumn("LoadingAgent_BPartner_ID",contract.getLoadingAgent_BPartner_ID());
				project.set_CustomColumn("UnLoadingAgent_BPartner_ID",contract.getUnLoadingAgent_BPartner_ID());
				project.set_CustomColumn("Shipper_BPartner_ID",contract.getShipper_BPartner_ID());
				project.set_CustomColumn("Receiver_BPartner_ID",contract.getReceiver_BPartner_ID());
				project.set_CustomColumn("IsLumpsum",contract.isLumpsum());
				project.set_ValueOfColumn("HBC_CargoName_ID", contract.get_Value("HBC_CargoName_ID"));
				project.set_ValueOfColumn("CargoName1_ID", contract.get_Value("CargoName1_ID"));
				project.set_ValueOfColumn("CargoName2_ID", contract.get_Value("CargoName2_ID"));
				project.set_ValueOfColumn("UOM1_ID", contract.get_Value("UOM1_ID"));
				project.set_ValueOfColumn("UOM2_ID", contract.get_Value("UOM2_ID"));
				project.set_ValueOfColumn("MinimumCargo2", contract.get_Value("MinimumCargo2"));
				project.set_ValueOfColumn("MinimumCargo3", contract.get_Value("MinimumCargo3"));
				project.set_ValueOfColumn("PortPosition_From2_ID", contract.get_Value("PortPosition_From2_ID"));
				project.set_ValueOfColumn("PortPosition_From3_ID", contract.get_Value("PortPosition_From3_ID"));
				project.set_ValueOfColumn("PortPosition_To2_ID", contract.get_Value("PortPosition_To2_ID"));
				project.set_ValueOfColumn("PortPosition_To3_ID", contract.get_Value("PortPosition_To3_ID"));
				//TODO: Add payAmt in Project. Temporary query from contract.PayAmt()
			}

			project.saveEx();

			if(!isDockingContract(contract)){

				//			MLoadUnloadDay[] list = contract.getLoadUnloadDays();
				//		
				//			if (list.length > 0) {
				//				for (MLoadUnloadDay day : list) {
				//					MLoadUnloadDay projectDay = new MLoadUnloadDay(getCtx(), 0, get_TrxName());
				//					projectDay.setC_Project_ID(project.get_ID());
				//					projectDay.setActiveFrom(day.getActiveFrom());
				//					projectDay.setActiveTo(day.getActiveTo());
				//					projectDay.setLine(day.getLine());
				//					projectDay.setTotalLoadingDay(day.getTotalLoadingDay());
				//					projectDay.setTotalUnloadingDay(day.getTotalUnloadingDay());
				//					projectDay.setIsProrate(day.isProrate());
				//					projectDay.setProrateDay(day.getProrateDay());
				//					projectDay.setAD_Org_ID(day.getAD_Org_ID());
				//					projectDay.set_ValueOfColumn("BargeCategory",day.get_Value("BargeCategory"));
				//					projectDay.setHBC_Contract_ID(day.getHBC_Contract_ID());
				//					projectDay.setDescription(day.getDescription());
				//					projectDay.saveEx();
				//
				//				}
				//			}
				// temp comment double demurrage by yonk
				//			if (listDemurrage.length > 0) {
				//				for (MDemurrage demurrage : listDemurrage) {
				//					MDemurrage projectDemurrage = new MDemurrage(getCtx(), 0, get_TrxName());
				//					projectDemurrage.setLine(demurrage.getLine());
				//					projectDemurrage.setAD_Org_ID(demurrage.getAD_Org_ID());
				//					projectDemurrage.setHBC_Contract_ID(demurrage.getHBC_Contract_ID());
				//					projectDemurrage.setC_Project_ID(project.get_ID());
				//					projectDemurrage.setDescription(demurrage.getDescription());
				//					projectDemurrage.setDemurrageAmt(demurrage.getDemurrageAmt());
				//					projectDemurrage.setActiveFrom(demurrage.getActiveFrom());
				//					projectDemurrage.setActiveTo(demurrage.getActiveTo());
				//					projectDemurrage.set_ValueOfColumn("BargeCategory", demurrage.get_Value("BargeCategory"));
				//					projectDemurrage.setFreightPercentage(demurrage.getFreightPercentage());
				//					projectDemurrage.setWeatherPercentage(demurrage.getWeatherPercentage());
				//					projectDemurrage.saveEx();
				//
				//				}
				//			}
			}
		}

		if(!isNewShipContract(contract) && !isStandByContract(contract)){
			project.set_CustomColumn("LoadingDate",contract.getLoadingDate());
			project.set_ValueOfColumn("LaycanDateTo", contract.get_Value("LaycanDateTo"));
			project.set_ValueOfColumn("C_Tax_ID", contract.get_Value("C_Tax_ID"));
			project.set_ValueOfColumn("C_ProjectTypeMilestone_ID", contract.get_Value("C_ProjectTypeMilestone_ID"));
		}

		HashMap<String, String> contractTypeMap = new HashMap<String, String>();
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_AnnualContract, "ANC");
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_SpotShipment, "SPC");
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_TimeCharter, "TMC");
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_Docking, "DOC");
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_NewShip, "NES");
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_StandBy, "STN");
		contractTypeMap.put(MContract.HBC_CONTRACTTYPE_SPAL, "SHJ");

		project.setProjectCategory(contractTypeMap.get(contract.getHBC_ContractType()));
		project.saveEx();

		contract.setNextID(docno);
		contract.saveEx();

		String message = Msg.parseTranslation(getCtx(), "@Generated@ " + project.getValue());
		addBufferLog(0, null, null, message, project.get_Table_ID(), project.getC_Project_ID());

		return "";
	}

	private String checkDemurrage() {		
		boolean	match = new Query(getCtx(), MDemurrage.Table_Name, "HBC_Contract_ID=?", get_TrxName())
		.setParameters(p_HBC_Contract_ID)
		.setOnlyActiveRecords(true)
		.match();

		if (!match)
			return "Cannot Create Project - Demurrage Tab Has No Line";

		return "";

	}

	private boolean isSpotShipmentContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment); //many
	}

	private boolean isAnnualContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_AnnualContract); //many	
	}

	private boolean isDockingContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_Docking); //one
	}

	private boolean isNewShipContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_NewShip); //one	
	}

	private boolean isStandByContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_StandBy); //many	
	}

	private boolean isSPHJContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SPAL);	//many
	}

	private boolean isTimeCharterContract(MContract contract) {
		return contract.getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_TimeCharter); //one	
	}

	private boolean isHasManyProject (MContract contract) {
		return isSpotShipmentContract(contract) || isSPHJContract(contract) || 
				isAnnualContract(contract) || isStandByContract(contract);
				
	}

}