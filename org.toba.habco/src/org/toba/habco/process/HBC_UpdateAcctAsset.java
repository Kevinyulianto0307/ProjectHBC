package org.toba.habco.process;

import org.compiere.model.MAsset;
import org.compiere.model.MAssetAcct;
import org.compiere.model.MAssetGroup;
import org.compiere.model.MAssetGroupAcct;
import org.compiere.model.MDepreciationExp;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;

public class HBC_UpdateAcctAsset extends SvrProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		int[] A_Asset_Acct_IDs = new Query(getCtx(), MAssetAcct.Table_Name, "", get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		int count = 0;
		for (int A_Asset_Acct_ID : A_Asset_Acct_IDs) {
			int A_Asset_Group_Acct_ID = getA_Asset_Group_Acct_ID(A_Asset_Acct_ID);
			MAssetGroupAcct groupAcct = new MAssetGroupAcct(getCtx(), A_Asset_Group_Acct_ID, get_TrxName());
			
			MAssetAcct assetAcct = new MAssetAcct(getCtx(), A_Asset_Acct_ID, get_TrxName());
			assetAcct.setA_Depreciation_ID(groupAcct.getA_Depreciation_ID());
			assetAcct.setPostingType(groupAcct.getPostingType());
			assetAcct.setA_Asset_Acct(groupAcct.getA_Asset_Acct());
			if(groupAcct.getA_Asset_Clearing_Acct() > 0)
				assetAcct.setA_Asset_Clearing_Acct(groupAcct.getA_Asset_Clearing_Acct());
			assetAcct.setA_Accumdepreciation_Acct(groupAcct.getA_Accumdepreciation_Acct());
			assetAcct.setA_Depreciation_Acct(groupAcct.getA_Depreciation_Acct());
			assetAcct.setA_Disposal_Revenue_Acct(groupAcct.getA_Disposal_Revenue_Acct());
			assetAcct.setA_Disposal_Loss_Acct(groupAcct.getA_Disposal_Loss_Acct());
			assetAcct.saveEx();
			
			for (int expense_ID : getExpense_IDs(A_Asset_Acct_ID)) {
				MDepreciationExp exp = new MDepreciationExp(getCtx(), expense_ID, get_TrxName());
				exp.setDR_Account_ID(groupAcct.getA_Depreciation_Acct());
				exp.setCR_Account_ID(groupAcct.getA_Accumdepreciation_Acct());
				exp.saveEx();
			}
			
			count+=1;
		}
		
		return ""+count;
	}

	protected int getA_Asset_Group_Acct_ID(int A_Asset_Acct_ID){
		
		MAssetAcct assetAcct = new MAssetAcct(getCtx(), A_Asset_Acct_ID, get_TrxName());
		MAsset asset = new MAsset(getCtx(), assetAcct.getA_Asset_ID(), get_TrxName());
		MAssetGroup assetGroup = new MAssetGroup(getCtx(), asset.getA_Asset_Group_ID(), get_TrxName());
		
		int A_Asset_Group_Acct_ID = new Query(getCtx(), MAssetGroupAcct.Table_Name, "A_Asset_Group_ID=?", get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setParameters(new Object[]{assetGroup.get_ID()})
		.firstId();
		
		return A_Asset_Group_Acct_ID;
	}

	protected int[] getExpense_IDs(int A_Asset_Acct_ID){
		
		MAssetAcct assetAcct = new MAssetAcct(getCtx(), A_Asset_Acct_ID, get_TrxName());
		MAsset asset = new MAsset(getCtx(), assetAcct.getA_Asset_ID(), get_TrxName());
		
		int[] A_Depreciation_Exp_IDs = new Query(getCtx(), MDepreciationExp.Table_Name, "A_Asset_ID=?", get_TrxName())
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.setParameters(new Object[]{asset.get_ID()})
		.getIDs();
		
		return A_Depreciation_Exp_IDs;
	}
	
}
