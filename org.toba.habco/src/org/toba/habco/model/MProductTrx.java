package org.toba.habco.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MProduct;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

public class MProductTrx extends X_HBC_ProductTrx implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 378050822326653577L;

	public MProductTrx(Properties ctx, int HBC_ProductTrx_ID, String trxName) {
		super(ctx, HBC_ProductTrx_ID, trxName);
	}
	
	public MProductTrx(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**	Process Message 			*/
	protected String		m_processMsg = null;
	
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		for (int i = 0; i < options.length; i++) {
			options[i] = null;
		}

		index = 0;

		if (docStatus.equals(DocAction.STATUS_Drafted)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_InProgress)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_Completed)) {
			options[index++] = DocAction.ACTION_Void;
		} else if (docStatus.equals(DocAction.STATUS_Invalid)) {
			options[index++] = DocAction.ACTION_Complete;
			options[index++] = DocAction.ACTION_Void;
		}

		return index;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		log.warning("Processing Action=" + action + " - DocStatus=" + getDocStatus() + " - DocAction=" + getDocAction());
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		
		return false;
	}

	@Override
	public boolean invalidateIt() {
		
		return false;
	}

	@Override
	public String prepareIt() {
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		
		return true;
	}

	@Override
	public boolean rejectIt() {
		
		return false;
	}

	@Override
	public String completeIt() {
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction())) {
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}
		
		// create new product
		m_processMsg = createProduct();
		if(m_processMsg != null){
			setProcessed(true);
			return DocAction.STATUS_Invalid;
		}
		
		setProcessed(true);	
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		
		return true;
	}

	@Override
	public boolean closeIt() {
		
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		return false;
	}

	@Override
	public String getSummary() {
		
		return null;
	}

	@Override
	public String getDocumentNo() {
		
		return null;
	}

	@Override
	public String getDocumentInfo() {
		
		return null;
	}

	@Override
	public File createPDF() {
		
		return null;
	}

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	@Override
	public int getDoc_User_ID() {
		
		return 0;
	}

	@Override
	public int getC_Currency_ID() {

		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		
		return null;
	}

	/**
	 * create new product
	 * @return error message
	 */
	public String createProduct(){
		MProduct product = new MProduct(getCtx(), 0, get_TrxName());
		product.setAD_Org_ID(getAD_Org_ID());
		product.setName(getName());
		product.setDescription(getDescription());
		product.setIsSummary(isSummary());
		product.setC_UOM_ID(getC_UOM_ID());
		product.setIsStocked(isStocked());
		product.setIsPurchased(isPurchased());
		product.setIsSold(isSold());
		product.setVolume(getVolume());
		product.setWeight(getWeight());
		product.setValue(getValue());
		product.setM_Product_Category_ID(getM_Product_Category_ID());
		product.setC_TaxCategory_ID(getC_TaxCategory_ID());
		product.setUPC(getUPC());
		product.setSKU(getSKU());
		product.setShelfWidth(getShelfWidth());
		product.setShelfHeight(getShelfHeight());
		product.setShelfDepth(getShelfDepth());
		product.setUnitsPerPallet(getUnitsPerPallet());
		product.setDiscontinuedAt(getDiscontinuedAt());
		product.setDocumentNote(getDocumentNote());
		product.setHelp(getHelp());
		product.setClassification(getClassification());
		product.setSalesRep_ID(getSalesRep_ID());
		product.setC_RevenueRecognition_ID(getC_RevenueRecognition_ID());
		product.setIsBOM(isBOM());
		product.setIsInvoicePrintDetails(isInvoicePrintDetails());
		product.setIsPickListPrintDetails(isPickListPrintDetails());
		product.setIsVerified(isVerified());
		product.setS_ExpenseType_ID(getS_ExpenseType_ID());
		product.setS_Resource_ID(getS_Resource_ID());
		product.setProductType(getProductType());
		product.setImageURL(getImageURL());
		product.setDescriptionURL(getDescriptionURL());
		product.setR_MailText_ID(getR_MailText_ID());
		product.setVersionNo(getVersionNo());
		product.setGuaranteeDays(getGuaranteeDays());
		product.setM_AttributeSet_ID(getM_AttributeSet_ID());
		product.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
		product.setM_FreightCategory_ID(getM_FreightCategory_ID());
		product.setM_Locator_ID(getM_Locator_ID());
		product.setGuaranteeDaysMin(getGuaranteeDaysMin());
		product.setIsWebStoreFeatured(isWebStoreFeatured());
		product.setIsSelfService(isSelfService());
		product.setC_SubscriptionType_ID(getC_SubscriptionType_ID());
		product.setIsDropShip(isDropShip());
		product.setIsExcludeAutoDelivery(isExcludeAutoDelivery());
		product.setGroup1(getGroup1());
		product.setGroup2(getGroup2());
		product.setUnitsPerPack(getUnitsPerPack());
		product.setLowLevel(getLowLevel());
		product.setCopyFrom(getCopyFrom());
		product.setIsKanban(isKanban());
		product.setIsManufactured(isManufactured());
		product.setIsPhantom(isPhantom());
		product.setIsOwnBox(isOwnBox());
		product.saveEx();
		
		return null;
	}
	
}
