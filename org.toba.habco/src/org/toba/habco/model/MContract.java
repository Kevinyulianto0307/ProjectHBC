package org.toba.habco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

public class MContract extends X_HBC_Contract {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3323579910203740619L;
	
	private final String GENERATE_CONTRACT_NUMBER = "GENERATE_CONTRACT_NUMBER";
	
	private static String DocumentCodeSpotShipment ="HP-SPAL";
	private static String DocumentCodeAnnualContract="ANCT";
	private static String DocumentCodeStandBy="STBY";
	private static String DocumentCodeDocking="DOCK";
	private static String DocumentCodeNewShip="BARU";
	private static String DocumentCodeTimeCharter="TMCR";
	private static String DocumentCodeSpotShipmentHJ="SPHJ";
	
	public MContract(Properties ctx, int HBC_Contract_ID, String trxName) {
		super(ctx, HBC_Contract_ID, trxName);

	}

	public MContract(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);

	}
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		boolean isCopy = get_ValueAsBoolean("IsCopy");
		
		if(newRecord){
			// use system configurator for dynamic contract number
			boolean isGenerateNumber = MSysConfig.getBooleanValue(GENERATE_CONTRACT_NUMBER, true);
			if(!getHBC_ContractType().equals(DocumentCodeSpotShipmentHJ) && isGenerateNumber){
				setDocumentNo(getDocumentNumber(getDocumentNo()));
				saveEx();
			}
		}
		
		if (getContractTermIDs().length==0 && !isCopy){
			if(getTemplateContractTerm().length>0){
					int[] tempconttermid = getTemplateContractTerm();
					int Line =0;
					for(int i=0;i<getTemplateContractTerm().length;i++){
						X_HBC_TemplateContractTerm tempcontterm = new X_HBC_TemplateContractTerm(getCtx(), tempconttermid[i], get_TrxName());
						MContractTerm contterm = new MContractTerm(getCtx(), 0, get_TrxName());
						contterm.setSeqNo(Line=Line+10);
						contterm.setHBC_TemplateContractTerm_ID(tempcontterm.getHBC_TemplateContractTerm_ID());
						contterm.setHBC_Contract_ID(getHBC_Contract_ID());
						contterm.setDescription(tempcontterm.getDescription());
						contterm.saveEx();
					}
			}	
		}
		
		
		if(getGeneralTermIDs().length==0 && !isCopy){
			if(getTemplateGeneralTerm().length>0){
				int[] tempgentermid = getTemplateGeneralTerm();
				int Line=0;
				for(int i=0;i<getTemplateGeneralTerm().length;i++){
					X_HBC_TemplateGeneralTerm gentterm = new X_HBC_TemplateGeneralTerm(getCtx(), tempgentermid[i], get_TrxName());
					MGeneralTerm genterm = new MGeneralTerm(getCtx(), 0, get_TrxName());
					genterm.setSeqNo(Line=Line+10);
					genterm.setHBC_TemplateGeneralTerm_ID(gentterm.getHBC_TemplateGeneralTerm_ID());
					genterm.setHBC_Contract_ID(getHBC_Contract_ID());
					genterm.setDescription(gentterm.getDescription());
					genterm.saveEx();
				}
			}
		}
		
		
		return true;
	}
	
	
	protected int[] getGeneralTermIDs(){
		
		int[] generaltermids= new Query(getCtx(),MGeneralTerm.Table_Name,"HBC_Contract_ID="+getHBC_Contract_ID(),get_TrxName())
							.getIDs();
		
		return generaltermids;
	}
	
	
	protected int[] getContractTermIDs(){
		
		int[] contracttermids= new Query(getCtx(), MContractTerm.Table_Name , "HBC_Contract_ID="+getHBC_Contract_ID(), get_TrxName())
			 .getIDs();
		
		return contracttermids;
	}
	
	
	protected int[] getTemplateGeneralTerm(){
		int[] genterm = new Query(getCtx(), X_HBC_TemplateGeneralTerm.Table_Name, "IsTemplate='Y'",get_TrxName())
		.setOrderBy("SeqNo")
		.getIDs();
				
		return genterm;
	}
	
	

	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//HABCO-1621 amount of cargo and payment amount cant be 0 by yonk 
		int doctypeid[]=new Query(getCtx(),MDocType.Table_Name,"Name='Annual Contract' OR Name='Spot Shipment (SPAL)' OR Name='Time Charter'",get_TrxName()).getIDs();
		for(int i=0;i<doctypeid.length;i++){
			if(getC_DocTypeTarget_ID()==doctypeid[i]){
				if(getAmountOfCargo().compareTo(Env.ZERO)==0){
					throw new AdempiereException("Amount of Cargo can't be 0");
				}//else if(getPayAmt().compareTo(Env.ZERO)==0){
				//				throw new AdempiereException("Payment Amount can't be 0");
				//			}
			}
		}//HABCO-1621
		
		// calculate contract hired day
		if(getValidFrom() != null && getValidTo() != null){
			int day = TimeUtil.getDaysBetween(getValidFrom(), TimeUtil.addDays(getValidTo(), 1));
			BigDecimal contractDay = new BigDecimal (day);
			setContractDay(contractDay);
			BigDecimal floatInvoiced = contractDay.divide(new BigDecimal(30),2,RoundingMode.HALF_UP).multiply(new BigDecimal(10));
			int numberInvoiced = ((floatInvoiced.intValue() + 5) / 10) * 10;
			setNumberInvoiced(new BigDecimal(numberInvoiced));
		}
		
		// calculate actual hired day
		if(getOnHireDate() != null && getOffHireDate() != null){
			int day = TimeUtil.getDaysBetween(getOnHireDate(), TimeUtil.addDays(getOffHireDate(), 1));
			setActualHireDate(new BigDecimal(day));
		}
		
		return true;
	}
	//HABCO-1457 Contract DocumentNo Format by yonk
	protected String getDocumentNumber(String nodocument){
		String documentNo=null;
		String romanmonth=RomanNumber(getMonthDate(getDateContract()));
		int yeardate = getYearDate(getDateContract());
		int NextId= Integer.valueOf(nodocument);
		
		if (getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_SpotShipment)){
			documentNo=NextId+"/"+DocumentCodeSpotShipment+"/"+romanmonth+"/"+yeardate;
		}else if(getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_AnnualContract)){
			documentNo=NextId+"/HP-"+getCustomer_BPartner().getValue()+"/"+DocumentCodeAnnualContract+"/"+romanmonth+"/"+yeardate;
		}else if(getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_TimeCharter)){
			documentNo=NextId+"/HP-"+getCustomer_BPartner().getValue()+"/"+DocumentCodeTimeCharter+"/"+romanmonth+"/"+yeardate;
		}else if (getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_NewShip)){
			if(getHBC_ShipReqType().equals(MContract.HBC_SHIPREQTYPE_TugBoat)){
				documentNo=NextId+"/"+getHBC_Tugboat().getValue()+"/"+DocumentCodeNewShip+"/"+yeardate;	
			}else{
				documentNo=NextId+"/"+getHBC_Barge().getValue()+"/"+DocumentCodeNewShip+"/"+yeardate;
			}
		}else if (getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_StandBy)){
			if(getHBC_ShipReqType().equals(MContract.HBC_SHIPREQTYPE_TugBoat)){
				documentNo=NextId+"/"+getHBC_Tugboat().getValue()+"/"+DocumentCodeStandBy+"/"+yeardate;	
			}else if(getHBC_ShipReqType().equals(MContract.HBC_SHIPREQTYPE_Barge)){
				documentNo=NextId+"/"+getHBC_Barge().getValue()+"/"+DocumentCodeStandBy+"/"+yeardate;
			}else{
				documentNo=NextId+"/"+getHBC_Barge().getValue()+"-"+getHBC_Tugboat().getValue()+"/"+DocumentCodeStandBy+"/"+yeardate;
			}
		}else if (getHBC_ContractType().equals(MContract.HBC_CONTRACTTYPE_Docking)){
			if(getHBC_ShipReqType().equals(MContract.HBC_SHIPREQTYPE_TugBoat)){
				documentNo=NextId+"/"+getHBC_Tugboat().getValue()+"/"+DocumentCodeDocking+"/"+yeardate;	
			}else{
				documentNo=NextId+"/"+getHBC_Barge().getValue()+"/"+DocumentCodeDocking+"/"+yeardate;
			}
		}
		

		return documentNo;
	}// END OF HABCO-1457

	protected int getMonthDate(Timestamp Date){
		int month =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		month = calender.get(Calendar.MONTH)+1;
		return month;
	}

	protected int getYearDate(Timestamp Date){
		int year =0;
		Calendar calender = Calendar.getInstance();
		calender.setTime(Date);
		year= calender.get(Calendar.YEAR);
		return year;
	}


	public static String RomanNumber(int input) {
		if (input < 1 || input > 3999)
			return "Invalid Roman Number Value";
		String s = "";
		while (input >= 1000) {
			s += "M";
			input -= 1000;        }
		while (input >= 900) {
			s += "CM";
			input -= 900;
		}
		while (input >= 500) {
			s += "D";
			input -= 500;
		}
		while (input >= 400) {
			s += "CD";
			input -= 400;
		}
		while (input >= 100) {
			s += "C";
			input -= 100;
		}
		while (input >= 90) {
			s += "XC";
			input -= 90;
		}
		while (input >= 50) {
			s += "L";
			input -= 50;
		}
		while (input >= 40) {
			s += "XL";
			input -= 40;
		}
		while (input >= 10) {
			s += "X";
			input -= 10;
		}
		while (input >= 9) {
			s += "IX";
			input -= 9;
		}
		while (input >= 5) {
			s += "V";
			input -= 5;
		}
		while (input >= 4) {
			s += "IV";
			input -= 4;
		}
		while (input >= 1) {
			s += "I";
			input -= 1;
		}    
		return s;
	}
	
	
	protected int[] getTemplateContractTerm(){
		int[] contterm = new Query(getCtx(), X_HBC_TemplateContractTerm.Table_Name, "IsTemplate='Y'",get_TrxName())
		.setOrderBy("SeqNo")
		.getIDs();
				
		return contterm;
	}

	public MLoadUnloadDay[] getLoadUnloadDays() {
		List<MLoadUnloadDay> list = new Query(getCtx(), MLoadUnloadDay.Table_Name, "HBC_Contract_ID=? AND C_Project_ID IS NULL", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.list();
		
		ArrayList<MLoadUnloadDay> listt= new ArrayList<MLoadUnloadDay>();
		for(int i=0;i<list.size();i++){
			listt.add(list.get(i));
		}

		return listt.toArray(new MLoadUnloadDay[listt.size()]);
	}

	public MDemurrage[] getDemurrages() {
		List<MDemurrage> list = new Query(getCtx(), MDemurrage.Table_Name, "HBC_Contract_ID=? AND C_Project_ID IS NULL", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.list();
		
		ArrayList<MDemurrage> listt= new ArrayList<MDemurrage>();
		for(int i=0;i<list.size();i++){
			listt.add(list.get(i));
		}

		return listt.toArray(new MDemurrage[listt.size()]);
	}
	
	public MContractTerm[] getContractTerm(){
		List<MContractTerm> list = new Query(getCtx(), MContractTerm.Table_Name, "HBC_Contract_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.list();
		
		ArrayList<MContractTerm> listt = new ArrayList<MContractTerm>();
		
		for(int i=0;i<list.size();i++){
			listt.add(list.get(i));
		}
		
		return listt.toArray(new MContractTerm[listt.size()]);		
		
		
	}
	
	public MGeneralTerm[] getGeneralTerm(){
		List<MGeneralTerm> list = new Query(getCtx(), MGeneralTerm.Table_Name, "HBC_Contract_ID=?", get_TrxName())
		.setParameters(get_ID())
		.setOnlyActiveRecords(true)
		.list();
		
		ArrayList<MGeneralTerm> listt = new ArrayList<MGeneralTerm>();
		
		for(int i=0;i<list.size();i++){
			listt.add(list.get(i));
		}
		
		
		return listt.toArray(new MGeneralTerm[listt.size()]);		
		
	}
}
