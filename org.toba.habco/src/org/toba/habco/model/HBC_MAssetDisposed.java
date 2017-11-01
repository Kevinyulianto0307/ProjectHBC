package org.toba.habco.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAsset;
import org.compiere.model.MAssetDisposed;
import org.compiere.model.MDepreciationWorkfile;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

public class HBC_MAssetDisposed extends MAssetDisposed{

	public HBC_MAssetDisposed(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public HBC_MAssetDisposed(Properties ctx, int A_Asset_Disposed_ID,
			String trxName) {
		super(ctx, A_Asset_Disposed_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2568466346028631146L;

	/**	Process Message 			*/
	private String		m_processMsg = null;
	
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;
	
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		//

		//loading asset
		MAsset asset = getAsset();
		if (log.isLoggable(Level.FINE)) log.fine("asset=" + asset);

		String method = getA_Disposed_Method();
		if (A_DISPOSED_METHOD_Preservation.equals(method)) {
			asset.changeStatus(MAsset.A_ASSET_STATUS_Preservation, getDateDoc());
			updateAssetWK();
		}
		
		else if (A_DISPOSED_METHOD_Simple.equals(method)
				|| A_DISPOSED_METHOD_Simple_.equals(method)
				|| A_DISPOSED_METHOD_Trade.equals(method))
		{
			setA_Disposal_Amt(getA_Asset_Cost());
			setA_Accumulated_Depr_Delta(getA_Accumulated_Depr());
			setExpense(getA_Disposal_Amt().subtract(getA_Accumulated_Depr_Delta()));
			//@phie
			//updateAssetWK();
			
			MDepreciationWorkfile assetwk = MDepreciationWorkfile.get(getCtx(), getA_Asset_ID(), getPostingType(), get_TrxName());
			//assetwk.adjustCost(getA_Asset_Cost(), Env.ZERO, false);
			//BigDecimal newCost = Env.ZERO;
			BigDecimal newQty = assetwk.getA_QTY_Current();
			newQty = newQty.subtract((BigDecimal) get_Value("qtyDisp"));
			
			if(newQty.compareTo(Env.ZERO) < 0 )
				throw new AdempiereException("Current qty is "+assetwk.getA_QTY_Current());
			
			assetwk.setA_Asset_Cost(assetwk.getA_Asset_Cost().subtract(getA_Asset_Cost()));
			assetwk.setA_QTY_Current(newQty);
			assetwk.adjustAccumulatedDepr(null, null, true);
			assetwk.saveEx();
			assetwk.truncDepreciation();
			
			if(assetwk.getA_QTY_Current().compareTo(Env.ZERO) == 0)
				asset.changeStatus(MAsset.A_ASSET_STATUS_Disposed, getDateDoc());
			//end phie
		}
		else if (A_DISPOSED_METHOD_PartialRetirement.equals(method))
			updateAssetWK();
		
		asset.saveEx(get_TrxName());

		//	User Validation
		valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Done
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	private void updateAssetWK()
	{
		MDepreciationWorkfile assetwk = MDepreciationWorkfile.get(getCtx(), getA_Asset_ID(), getPostingType(), get_TrxName());
		assetwk.adjustCost(getA_Asset_Cost(), Env.ZERO, false);
		assetwk.adjustAccumulatedDepr(null, null, true);
		assetwk.saveEx();
		assetwk.truncDepreciation();
		//
	}
	
}
