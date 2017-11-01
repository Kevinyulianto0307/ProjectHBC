package org.toba.habco.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionType;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * 
 * @author Phie Albert
 *	Jurnal Balik Akhir Bulan
 */
public class HBC_CreateJurnalBalik extends SvrProcess{
	
	private int p_GL_Journal_ID = 0;
	private Timestamp	p_DateAcct = null;
	private int	p_C_Period_ID = 0;
	private String description = "";
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DateAcct")){
				p_DateAcct = para[i].getParameterAsTimestamp();
			}
			else if (name.equals("Description")){
				description = para[i].getParameterAsString();
			}
			else if (name.equals("C_Period_ID"))
				p_C_Period_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		p_GL_Journal_ID = getRecord_ID();
	}
	
	protected String doIt() throws Exception {
	
		if(p_GL_Journal_ID == 0)
			throw new AdempiereException("There's no data selected..");
		
		String whereClause= "isDefault='Y' AND AD_Client_ID = ?";
		int c_conversionType_id = new Query(getCtx(), MConversionType.Table_Name, whereClause, get_TrxName())
									.setParameters(new Object[]{getAD_Client_ID()})
									.firstId();
		
		if(c_conversionType_id == -1)
			throw new AdempiereException("There's no default conversion type");
		
		MJournal old = new MJournal(getCtx(), p_GL_Journal_ID, get_TrxName());
		MJournal newJournal = new MJournal(getCtx(), 0, get_TrxName());
		
		newJournal.setAD_Org_ID(old.getAD_Org_ID());
		newJournal.setC_DocType_ID(old.getC_DocType_ID());
		newJournal.setC_AcctSchema_ID(old.getC_AcctSchema_ID());
		newJournal.setPostingType(old.getPostingType());
		newJournal.setDateDoc(p_DateAcct);
		newJournal.setDateAcct(p_DateAcct);
		newJournal.setGL_Category_ID(old.getGL_Category_ID());
		newJournal.setC_Currency_ID(old.getC_Currency_ID());
		newJournal.setDescription(description);
		newJournal.setC_Period_ID(p_C_Period_ID);
		newJournal.setC_ConversionType_ID(c_conversionType_id);
		//newJournal.setDocAction(DocAction.ACTION_Prepare);
		
		if (!newJournal.save())
			return " - Could not create Journal";

		for(MJournalLine line : old.getLines(true))
		{
			MJournalLine newJournalLine = new MJournalLine(getCtx(), 0, get_TrxName());
			newJournalLine.setAD_Org_ID(line.getAD_Org_ID());
			newJournalLine.setGL_Journal_ID(newJournal.getGL_Journal_ID());
			newJournalLine.setLine(line.getLine());
			newJournalLine.setC_Currency_ID(line.getC_Currency_ID());
			newJournalLine.setAccount_ID(line.getAccount_ID());
			newJournalLine.setC_BPartner_ID(line.getC_BPartner_ID());
			newJournalLine.setC_Campaign_ID(line.getC_Campaign_ID());
			newJournalLine.setC_ValidCombination_ID(line.getC_ValidCombination_ID());
			if(line.get_ValueAsInt("HBC_Tugboat_ID") > 0)
				newJournalLine.set_ValueOfColumn("HBC_Tugboat_ID", line.get_ValueAsInt("HBC_Tugboat_ID"));
			if(line.get_ValueAsInt("HBC_Barge_ID") > 0)
				newJournalLine.set_ValueOfColumn("HBC_Barge_ID", line.get_ValueAsInt("HBC_Barge_ID"));
			newJournalLine.setAlias_ValidCombination_ID(line.getAlias_ValidCombination_ID());
			newJournalLine.setAmtSourceDr(line.getAmtSourceCr());
			newJournalLine.setAmtSourceCr(line.getAmtSourceDr());
			newJournalLine.setAmtAcctDr(line.getAmtAcctCr());
			newJournalLine.setAmtAcctCr(line.getAmtAcctDr());
			newJournalLine.setQty(line.getQty());
			newJournalLine.setC_UOM_ID(line.getC_UOM_ID());
			newJournalLine.saveEx();
		}
		
		StringBuilder msgreturn = new StringBuilder("Generated: "+newJournal.getDocumentNo());
		addLog(newJournal.getGL_Journal_ID(), null, null, msgreturn.toString(), MJournal.Table_ID, newJournal.getGL_Journal_ID());
		
		return "";
	}

}
