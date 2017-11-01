package org.toba.habco.model.validator;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MAsset;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MAssetGroup;
import org.compiere.model.PO;
import org.osgi.service.event.Event;

public class HBC_AssetAdditionValidator {
	public static String executeEvent(Event event, PO po) {
		String msg = "";
		MAssetAddition addition = (MAssetAddition) po;
		if (event.getTopic().equals(IEventTopics.DOC_AFTER_COMPLETE))
				msg = afterComplete(addition);
		return msg;
	}
	
	//@phie
	private static String afterComplete(MAssetAddition addition) {
		MAsset asset = new MAsset(addition.getCtx(), addition.getA_Asset_ID(), addition.get_TrxName());
		MAssetGroup assetGroup = new MAssetGroup(addition.getCtx(), addition.getA_Asset_Group_ID(), addition.get_TrxName());
		
		asset.setDescription(addition.getDescription());
		if(addition.get_Value("C_Campaign_ID")!=null)
			asset.set_ValueOfColumn("C_Campaign_ID", addition.get_Value("C_Campaign_ID"));
		if(addition.get_Value("HBC_Tugboat_ID")!=null)
			asset.set_ValueOfColumn("HBC_Tugboat_ID", addition.get_Value("HBC_Tugboat_ID"));
		if(addition.get_Value("HBC_Barge_ID")!=null)
			asset.set_ValueOfColumn("HBC_Barge_ID", addition.get_Value("HBC_Barge_ID"));
		asset.set_ValueOfColumn("LocationComment", addition.get_Value("LocationComment"));
		if(assetGroup.get_ValueAsBoolean("isLowValueAsset"))
			asset.set_ValueOfColumn("isInventarisKapal", "Y");
		asset.set_ValueOfColumn("JenisHarta", assetGroup.get_Value("JenisHarta"));
		asset.set_ValueOfColumn("KelompokHarta", assetGroup.get_Value("KelompokHarta"));
		asset.set_ValueOfColumn("JenisUsaha", assetGroup.get_Value("JenisUsaha"));
		asset.set_ValueOfColumn("NamaHarta", assetGroup.get_Value("NamaHarta"));
		asset.set_ValueOfColumn("JenisPenyusutanKomersial", assetGroup.get_Value("JenisHarta"));
		asset.set_ValueOfColumn("JenisPenyusutanFiskal", assetGroup.get_Value("JenisPenyusutanFiskal"));
		asset.saveEx();
		
		return "";
	}
	//end phie
}
