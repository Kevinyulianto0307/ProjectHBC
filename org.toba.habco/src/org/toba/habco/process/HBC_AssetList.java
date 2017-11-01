package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAsset;
import org.compiere.model.MDepreciationExp;
import org.compiere.model.MDepreciationWorkfile;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author Phie Albert
 */

public class HBC_AssetList extends SvrProcess{

	private int p_A_Asset_ID = 0;
	private int p_A_Asset_Group_ID = 0;
	//private int p_C_Period_ID = 0;
	private Timestamp p_dateReflection = null;
	//private ArrayList<String> invalidAsset = new ArrayList<String>();
	private String s_insert = "INSERT INTO A_AssetListProjectionReport"
			+ "(ad_client_id, ad_org_id, a_asset_id, assetvalue, assetname, "
			+ "assetgroupname, assetServiceDate, assetAquisitionDate, assetCost, assetwkAccmDepr, assetwkRemaining, "
			+ "uselifeyears, uselifemonths, assetwk_period, assetwkDateAcct, totalAccDeprProjection, "
			+ "assetRemainingProjection, LeftAccDeprProjection, expenseOnPeriodParameter, differenceAccDepr, DateAcct, description, a_asset_group_id) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			System.out.println(para.length);
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("A_Asset_Group_ID")){
				p_A_Asset_Group_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("A_Asset_ID")){
				p_A_Asset_ID = para[i].getParameterAsInt();
			}
			else if (name.equals("DateAcct")){
				p_dateReflection = para[i].getParameterAsTimestamp();
				p_dateReflection = TimeUtil.getMonthLastDay(p_dateReflection);
			}
			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	protected String doIt() throws Exception {
		String msg="";
		if(p_dateReflection == null)
			return "Period is mandatory..";
		
		cleanTable();
		
		if(p_A_Asset_ID > 0)
			createSingleAssetlist(p_A_Asset_ID);
		else if(p_A_Asset_Group_ID > 0)
			createAssetListOnGroup();
		else 
			createMultipleAssetList();
		
		/*
		if(!invalidAsset.isEmpty())
		{
			msg = "Cannot create projection for asset ";
			for(int i=0 ; i<invalidAsset.size() ; i++)
			{
				msg = msg.concat("|"+invalidAsset.get(i)+"|");
			}
			msg = msg.concat(" because Date/Period Projection before Asset Service Date ");
		}
		*/
		
		//NEW Request, remove asset that desc not null
		//Note : one day, if want to see all asset, with it's desc, just remove this code
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM A_AssetListProjectionReport WHERE description is not null");
		DB.executeUpdate(sb.toString(), get_TrxName());
		
		return msg;
	}

	protected void cleanTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM A_AssetListProjectionReport");
		DB.executeUpdate(sql.toString(), get_TrxName());
	}
	
	protected String createSingleAssetlist(int A_Asset_ID) {
		MAsset asset = new MAsset(getCtx(), A_Asset_ID, get_TrxName());
		MDepreciationWorkfile assetwk = MDepreciationWorkfile.
				get(getCtx(), A_Asset_ID, MDepreciationWorkfile.POSTINGTYPE_Actual, get_TrxName());
		
		boolean exists = new Query(getCtx(), MDepreciationExp.Table_Name, "A_Asset_ID = ?", get_TrxName())
		.setParameters(new Object[]{A_Asset_ID})
		.setOnlyActiveRecords(true)
		.match();
		
		if(!exists || asset.get_Value("A_Asset_Status").equals("DI") || asset.get_ValueAsBoolean("isDisposed"))
			return "";
		
		//asset data
		String assetValue = asset.getValue();
		String assetName = asset.getName();
		String assetGroupName = asset.getA_Asset_Group().getName();
		Timestamp assetServiceDate = asset.getAssetServiceDate(); //from Addition dateAcct
		Timestamp assetAquisitionDate = (Timestamp) asset.get_Value("AcquisitionDate"); //from Addition dateDoc
		
		//asset workfile data
		BigDecimal assetCost = assetwk.getA_Asset_Cost();
		BigDecimal assetwkAccumDepr = assetwk.getA_Accumulated_Depr(); //total accum depr until now (last depreciation)
		BigDecimal assetwkRemaining = assetwk.getA_Asset_Remaining(); //total remaining asset value
		int useableLifeYear = assetwk.getUseLifeYears();
		int useableLifeMonth = assetwk.getUseLifeMonths();
		int a_current_period = assetwk.getA_Current_Period(); //next depr period
		Timestamp assetwkDateAcct = assetwk.getDateAcct(); //next depr period
		
		int last_exp_id = new Query(getCtx(), MDepreciationExp.Table_Name, " A_Asset_ID = ?", get_TrxName())
								.setParameters(new Object[]{A_Asset_ID})
								.setOrderBy(MDepreciationExp.COLUMNNAME_DateAcct+" DESC")
								.setOnlyActiveRecords(true)
								.firstId();
		
		MDepreciationExp exp = new MDepreciationExp(getCtx(), last_exp_id, get_TrxName());
		
		if(p_dateReflection.before(asset.getAssetServiceDate()))
		{
			//invalidAsset.add(asset.getValue());
			String Description = "Period/Date Projection "+p_dateReflection+" < Asset's Date "+asset.getAssetServiceDate();
			StringBuilder sb = new StringBuilder(s_insert);
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), A_Asset_ID,
				 						assetValue, assetName, assetGroupName, assetServiceDate, assetAquisitionDate, assetCost, 
				 						assetwkAccumDepr, assetwkRemaining, useableLifeYear, useableLifeMonth, a_current_period,
				 						assetwkDateAcct, null, null, null, null,null, p_dateReflection, Description, asset.getA_Asset_Group_ID()}, get_TrxName());
			log.fine("#" + no);
			log.finest(sb.toString());
			return "";
		}
		else if(p_dateReflection.after(exp.getDateAcct()))
		{
			String Description = "Last Depreciation of this Asset on "+exp.getDateAcct()+" (> Period/Date Projection "+p_dateReflection+")";
			StringBuilder sb = new StringBuilder(s_insert);
			int no = DB.executeUpdateEx(sb.toString(), new Object[]{getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), A_Asset_ID,
				 						assetValue, assetName, assetGroupName, assetServiceDate, assetAquisitionDate, assetCost, 
				 						assetwkAccumDepr, assetwkRemaining, useableLifeYear, useableLifeMonth, a_current_period,
				 						assetwkDateAcct, null, null, null, null,null, p_dateReflection, Description, asset.getA_Asset_Group_ID()}, get_TrxName());
			log.fine("#" + no);
			log.finest(sb.toString());
			return "";
		}
		
		/*Note*/
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(Expense),0) FROM A_Depreciation_Exp "
				+ "WHERE A_Asset_ID = ? AND processed = 'Y'");
		BigDecimal accDeprWithoutFirstAccDepr = DB.getSQLValueBD(get_TrxName(), sb.toString(), new Object[]{A_Asset_ID});
		BigDecimal firstAccDepr = assetwkAccumDepr.subtract(accDeprWithoutFirstAccDepr); //addition
		
		sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(Expense),0) FROM A_Depreciation_Exp "
				+ "WHERE A_Asset_ID = ? AND DateAcct BETWEEN '"+assetServiceDate+"' AND '"+p_dateReflection+"'");
		BigDecimal TotalAccDeprProjection = DB.getSQLValueBD(get_TrxName(), sb.toString(), new Object[]{A_Asset_ID}); //total depr between date created and date projection
		TotalAccDeprProjection = TotalAccDeprProjection.add(firstAccDepr);
		BigDecimal assetRemainingProjection = assetCost.subtract(TotalAccDeprProjection);
		BigDecimal LeftAccDeprProjection = TotalAccDeprProjection.subtract(assetwkAccumDepr); //total depr between next depr period and date projection
		
		sb = new StringBuilder();
		sb.append("SELECT COALESCE(SUM(Expense),0) FROM A_Depreciation_Exp "
				+ "WHERE A_Asset_ID = ? AND DateAcct = '"+p_dateReflection+"'");
		BigDecimal expenseOnPeriodParameter = DB.getSQLValueBD(get_TrxName(), sb.toString(), new Object[]{A_Asset_ID});
		
		//like LeftAccDeprProjection, but doesn't include depr on parameter period
		BigDecimal differenceAccDepr = LeftAccDeprProjection.subtract(expenseOnPeriodParameter);
		
		sb = new StringBuilder(s_insert);
		int no = DB.executeUpdateEx(sb.toString(), new Object[]{getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), A_Asset_ID,
			 assetValue, assetName, assetGroupName, assetServiceDate, assetAquisitionDate, assetCost, assetwkAccumDepr, assetwkRemaining,
				useableLifeYear, useableLifeMonth, a_current_period, assetwkDateAcct, TotalAccDeprProjection, 
				assetRemainingProjection, LeftAccDeprProjection, expenseOnPeriodParameter,differenceAccDepr, p_dateReflection, null, asset.getA_Asset_Group_ID()}, get_TrxName());
		
		log.fine("#" + no);
		log.finest(sb.toString());
		return "";
	}
	
	private void createMultipleAssetList() {
		int[] asset_id = MAsset.getAllIDs(MAsset.Table_Name, "isDepreciated='Y' "
				+ "AND AD_Client_ID = "+getAD_Client_ID(), get_TrxName());
		for(int i=0;i<asset_id.length;i++)
		{
			createSingleAssetlist(asset_id[i]);
		}
	}
	
	private void createAssetListOnGroup() {
		int[] asset_id = MAsset.getAllIDs(MAsset.Table_Name, "isDepreciated='Y' AND A_Asset_Group_ID = "+ p_A_Asset_Group_ID
				+ "AND AD_Client_ID = "+getAD_Client_ID(), get_TrxName());
		for(int i=0;i<asset_id.length;i++)
		{
			createSingleAssetlist(asset_id[i]);
		}
	}
}
