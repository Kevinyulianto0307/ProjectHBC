package org.toba.habco.process;

import java.util.ArrayList;

import org.compiere.model.MAssetDisposed;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;

public class HBC_InvoiceCreateDisposal extends SvrProcess{

	private int p_C_Invoice_ID = 0;
	@Override
	protected void prepare() {

		p_C_Invoice_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {

		if (p_C_Invoice_ID <= 0)
			return "Error: No Invoice selected";

		MInvoice invoice = new MInvoice(getCtx(), p_C_Invoice_ID, get_TrxName());
		/*
		if (invoice.isA_Processed())
			return "Invoice has been processed";
		 */
		if (!invoice.isSOTrx() )
			return "Error: Not AP Invoice";

		if (!invoice.isTrackAsAsset())
			return "Error: Not Fixed Assets Invoice";

		MInvoiceLine[] lines = invoice.getLines(true);

		//ArrayList<MAssetAddition> assetAdditions = new ArrayList<MAssetAddition>();
		ArrayList<MAssetDisposed> assetDisposed = new ArrayList<MAssetDisposed>();
		
		for (MInvoiceLine line : lines) {
			
			/*if (!line.isTrackAsAsset() || !line.isA_CreateAsset() || line.isA_Processed())
				continue;*/
			if (!line.isTrackAsAsset() || line.isA_Processed())
				continue;

			/*if (line.getA_Asset_Group_ID()<=0 && !line.getA_CapvsExp().equalsIgnoreCase(MInvoiceLine.A_CAPVSEXP_Capital))
				return "Cannot Create New Asset, Missing Asset Group or Not Capital Type";*/

			
			MAssetDisposed assetDis = MAssetDisposed.createAssetDisposed(line);
			
			/*assetDis.setA_Asset_ID(line.getA_Asset_ID());
			assetDis.setC_DocType_ID(1000235);
			assetDis.setAD_Org_ID(invoice.getAD_Org_ID());
			assetDis.setDateDoc(invoice.getDateAcct());	
			assetDis.setC_Invoice_ID(invoice.get_ID());
			assetDis.saveEx();*/
			
			assetDis.setC_DocType_ID(1000235);
			assetDis.saveEx();
			
			assetDisposed.add(assetDis);
			
			line.setA_Processed(true);
			line.saveEx();
		}

		//@win //invoice.setA_Processed(true);
		invoice.saveEx();

		if (assetDisposed.isEmpty())
			return "No Asset Disposed Generated";
		else {
			
			for (MAssetDisposed assetDis : assetDisposed) {
				String message = Msg.parseTranslation(getCtx(), "@GeneratedAssetDisposed@ - " + assetDis.getDocumentNo());
				addBufferLog(0, null, null, message, assetDis.get_Table_ID(),
						assetDis.getA_Asset_Disposed_ID());
			}
			
		}
		


		return "";
	}


}
