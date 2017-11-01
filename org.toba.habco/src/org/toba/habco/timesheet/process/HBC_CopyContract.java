package org.toba.habco.timesheet.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.toba.habco.model.MContract;
import org.toba.habco.model.MContractTerm;
import org.toba.habco.model.MDemurrage;
import org.toba.habco.model.MGeneralTerm;

public class HBC_CopyContract extends SvrProcess{

	protected int p_HBC_Contract_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("HBC_Contract_ID")){
				p_HBC_Contract_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		p_HBC_Contract_ID = getRecord_ID();
		
		MContract oldContract = new MContract(getCtx(), p_HBC_Contract_ID, get_TrxName());
		MContract newContract = new MContract(getCtx(), 0, get_TrxName());
		newContract.setAD_Org_ID(oldContract.getAD_Org_ID());
		newContract.setC_DocTypeTarget_ID(oldContract.getC_DocTypeTarget_ID());
		newContract.setDateContract(oldContract.getDateContract());
		newContract.setValidFrom(oldContract.getValidFrom());
		newContract.setValidTo(oldContract.getValidTo());
		newContract.setSigningPlace(oldContract.getSigningPlace());
		newContract.setDescription(oldContract.getDescription());
		newContract.setC_BPartner_ID(oldContract.getC_BPartner_ID());
		newContract.setC_BPartner_Location_ID(oldContract.getC_BPartner_Location_ID());
		newContract.setCargoName(oldContract.getCargoName());
		newContract.setCustomer_BPartner_ID(oldContract.getCustomer_BPartner_ID());
		newContract.setCustomer_Location_ID(oldContract.getCustomer_Location_ID());
		newContract.setCustomer_User_ID(oldContract.getCustomer_User_ID());
		newContract.setCargoCondition(oldContract.getCargoCondition());
		newContract.setAmountOfCargo(oldContract.getAmountOfCargo());
		newContract.setC_UOM_ID(oldContract.getC_UOM_ID());
		newContract.setLoadingDate(oldContract.getLoadingDate());
		newContract.setC_BankAccount_ID(oldContract.getC_BankAccount_ID());
		newContract.setC_Currency_ID(oldContract.getC_Currency_ID());
		newContract.setC_PaymentTerm_ID(oldContract.getC_PaymentTerm_ID());
		newContract.setC_ConversionType_ID(oldContract.getC_ConversionType_ID());
		newContract.setHBC_PortPosition_ID(oldContract.getHBC_PortPosition_ID());
		newContract.setFrom_PortPosition_ID(oldContract.getFrom_PortPosition_ID());
		newContract.setTo_PortPosition_ID(oldContract.getTo_PortPosition_ID());
		newContract.setLoadingAgent_BPartner_ID(oldContract.getLoadingAgent_BPartner_ID());
		newContract.setUnLoadingAgent_BPartner_ID(oldContract.getUnLoadingAgent_BPartner_ID());
		newContract.setShipper_BPartner_ID(oldContract.getShipper_BPartner_ID());
		newContract.setReceiver_BPartner_ID(oldContract.getReceiver_BPartner_ID());
		newContract.setShipBorne(oldContract.getShipBorne());
		newContract.setHBC_Tugboat_ID(oldContract.getHBC_Tugboat_ID());
		newContract.setHBC_Barge_ID(oldContract.getHBC_Barge_ID());
		newContract.setIsLumpsum(oldContract.isLumpsum());
		newContract.setPayAmt(oldContract.getPayAmt());
		newContract.setHBC_ShipReqType(oldContract.getHBC_ShipReqType());
		newContract.setHBC_ContractType(oldContract.getHBC_ContractType());
		newContract.setShipInsurance(oldContract.getShipInsurance());
		newContract.setUnitPrice(oldContract.getUnitPrice());
		newContract.setC_ProjectTypeMilestone_ID(oldContract.getC_ProjectTypeMilestone_ID());
		newContract.setC_Tax_ID(oldContract.getC_Tax_ID());
		newContract.setLaycanDateTo(oldContract.getLaycanDateTo());
		newContract.setSINumber(oldContract.getSINumber());
		newContract.setOwner_User_ID(oldContract.getOwner_User_ID());
		newContract.setAddendumNumber(oldContract.getAddendumNumber());
		newContract.setContractStatus(MContract.CONTRACTSTATUS_Draft);
		newContract.setPortPosition_From2_ID(oldContract.getPortPosition_From2_ID());
		newContract.setPortPosition_To2_ID(oldContract.getPortPosition_To2_ID());
		newContract.setProcessed(false);
		newContract.set_ValueOfColumn("IsCopy", true);
		newContract.saveEx();
		
		for (MContractTerm oldContractTerm : oldContract.getContractTerm()) {
			MContractTerm newContractTerm = new MContractTerm(getCtx(), 0, get_TrxName());
			newContractTerm.setAD_Org_ID(oldContractTerm.getAD_Org_ID());
			newContractTerm.setName(oldContractTerm.getName());
			newContractTerm.setDescription(oldContractTerm.getDescription());
			newContractTerm.setHBC_Contract_ID(newContract.getHBC_Contract_ID());
			newContractTerm.setSeqNo(oldContractTerm.getSeqNo());
			newContractTerm.setHBC_TemplateContractTerm_ID(oldContractTerm.getHBC_TemplateContractTerm_ID());
			newContractTerm.saveEx();
		}
		
		for (MGeneralTerm oldGeneralTerm : oldContract.getGeneralTerm()) {
			MGeneralTerm newGeneralTerm = new MGeneralTerm(getCtx(), 0, get_TrxName());
			newGeneralTerm.setAD_Org_ID(oldGeneralTerm.getAD_Org_ID());
			newGeneralTerm.setDescription(oldGeneralTerm.getDescription());
			newGeneralTerm.setName(oldGeneralTerm.getName());
			newGeneralTerm.setHBC_Contract_ID(newContract.getHBC_Contract_ID());
			newGeneralTerm.setSeqNo(oldGeneralTerm.getSeqNo());
			newGeneralTerm.setHBC_TemplateGeneralTerm_ID(oldGeneralTerm.getHBC_TemplateGeneralTerm_ID());
			newGeneralTerm.saveEx();
		}
		
		for (MDemurrage oldDem : oldContract.getDemurrages()) {
			MDemurrage newDem = new MDemurrage(getCtx(), 0, get_TrxName());
			newDem.setAD_Org_ID(oldDem.getAD_Org_ID());
			newDem.setDemurrageAmt(oldDem.getDemurrageAmt());
			newDem.setFreightPercentage(oldDem.getFreightPercentage());
			newDem.setWeatherPercentage(oldDem.getWeatherPercentage());
			newDem.setActiveFrom(oldDem.getActiveFrom());
			newDem.setActiveTo(oldDem.getActiveTo());
			newDem.setHBC_Contract_ID(newContract.getHBC_Contract_ID());
			newDem.setLine(oldDem.getLine());
			newDem.setDescription(oldDem.getDescription());
			newDem.setC_Project_ID(oldDem.getC_Project_ID());
			newDem.set_ValueOfColumn("BargeCategory", oldDem.get_Value("BargeCategory"));
			newDem.set_ValueOfColumn("TotalLoadingDay", oldDem.get_Value("TotalLoadingDay"));
			newDem.set_ValueOfColumn("TotalUnloadingDay", oldDem.get_Value("TotalUnloadingDay"));
			newDem.set_ValueOfColumn("IsProrate", oldDem.get_Value("IsProrate"));
			newDem.set_ValueOfColumn("ProrateDay", oldDem.get_Value("ProrateDay"));
			newDem.saveEx();
		}
		
		String message = Msg.parseTranslation(getCtx(), "@GeneratedContract@ " + newContract.getDocumentNo());
		addBufferLog(0, null, null, message, newContract.get_Table_ID(), newContract.getHBC_Contract_ID());
		
		return "";
	}

}
