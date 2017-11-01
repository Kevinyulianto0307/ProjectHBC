package org.toba.habco.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.model.MConversionType;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MFactAcct;
import org.compiere.model.MGLCategory;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 
 * @author Phie Albert
 *	Jurnal Balik Akhir Tahun
 */
public class HBC_CreateGLJournal extends SvrProcess {
	
	/**	Mandatory Acct Schema			*/
	private int	p_C_AcctSchema_ID = 0;

	/** Mandatory Org		*/
	private int	p_AD_Org_ID = 0;

	/** Revaluation Period				*/
	private int	p_C_Period_ID = 0;

	/** GL Document Type				*/
	private int	p_C_DocType_ID = 0;
	private Timestamp	p_DateStart = null;
	private Timestamp	p_DateEnd = null;
	private int	p_account_earning_id = 0;
	
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Period_ID"))
				p_C_Period_ID = para[i].getParameterAsInt();
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("Account_ID"))
				p_account_earning_id = para[i].getParameterAsInt();
			else if (name.equals("Date")){
				p_DateStart = para[i].getParameterAsTimestamp();
				p_DateEnd = para[i].getParameter_ToAsTimestamp();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}
	
	protected String doIt() throws Exception {
		if (p_C_AcctSchema_ID == 0)
			return "Process aborted.. Accounting Schema is mandatory";
		
		if (p_C_Period_ID == 0)
			return "Aborted.. Period is mandatory";

		if (p_account_earning_id == 0)
			return "Aborted.. Account Earning is mandatory";
		
		if (p_C_AcctSchema_ID == 0)
			return "Process aborted.. Accounting Schema is mandatory";

		if (p_C_DocType_ID== 0)
			return "Process aborted.. Document Type is mandatory";
		
		if (p_DateStart == null || p_DateEnd == null)
			return "Aborted.. Date is mandatory";
		
		String whereClause = "C_ElementValue_ID IN (SELECT Account_ID FROM Fact_Acct fa "
				+ "JOIN C_ElementValue ce ON ce.C_ElementValue_ID = fa.Account_ID "
				+ "WHERE fa.AD_Client_ID = ? AND ce.AccountType IN ('E','R','M') "
				+ "AND DateAcct BETWEEN ? AND ? Group by Account_ID Having sum(amtacctdr-amtacctcr) != 0)";
		int[] accountIDs = new Query(getCtx(), MElementValue.Table_Name, whereClause, get_TrxName())
							.setParameters(new Object[]{getAD_Client_ID(), p_DateStart, p_DateEnd})				
							.getIDs();

		whereClause= "isDefault='Y' AND AD_Client_ID = ?";
		int c_conversionType_id = new Query(getCtx(), MConversionType.Table_Name, whereClause, get_TrxName())
									.setParameters(new Object[]{getAD_Client_ID()})
									.firstId();
		
		if(c_conversionType_id == -1)
			throw new AdempiereException("There's no default conversion type");
		
		MPeriod period = new MPeriod(getCtx(), p_C_Period_ID, get_TrxName());
		
		MAcctSchema as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
		 		
		MDocType docType = MDocType.get(getCtx(), p_C_DocType_ID);
		MGLCategory cat = MGLCategory.get(getCtx(), docType.getGL_Category_ID());
		
		MJournal journal = new MJournal (getCtx(), 0, get_TrxName());
		journal.setAD_Org_ID(p_AD_Org_ID);
		journal.setC_DocType_ID(p_C_DocType_ID);
		journal.setPostingType(MJournal.POSTINGTYPE_Actual);
		journal.setDateDoc(period.getEndDate());
		journal.setDateAcct(period.getEndDate()); // sets the period too
		journal.setC_Period_ID(period.getC_Period_ID());
		journal.setC_Currency_ID(as.getC_Currency_ID());
		journal.setC_AcctSchema_ID (as.getC_AcctSchema_ID());
		journal.setGL_Category_ID (cat.getGL_Category_ID());
		journal.setC_ConversionType_ID(c_conversionType_id);
		journal.setDescription("Jurnal Balik Akhir Tahun");
		if (!journal.save())
			return " - Could not create Journal";
		
		int lineCount = 1;
		BigDecimal total = Env.ZERO;
		for(int id : accountIDs)
		{
			//MElementValue elementValue = new MElementValue(getCtx(), id, get_TrxName());
			String sqlFactAcctBalance = "SELECT COALESCE(SUM(AmtAcctDR-AmtAcctCR),0) FROM Fact_Acct "
					+ "WHERE Account_ID=? AND DateAcct BETWEEN ? AND ?";
			BigDecimal factAcctBalance = DB.getSQLValueBD(get_TrxName(), sqlFactAcctBalance, new Object[]{id, p_DateStart, p_DateEnd});
			
			//if(factAcctBalance.compareTo(Env.ZERO) == 0)
				//continue;
			
			MJournalLine line = new MJournalLine(journal);
			line.setLine(lineCount*10);
			line.setC_Currency_ID(as.getC_Currency_ID());
			line.setAccount_ID(id);
			if(factAcctBalance.compareTo(Env.ZERO) > 0)
			{
				line.setAmtSourceDr (Env.ZERO);
				line.setAmtAcctDr (Env.ZERO);
				line.setAmtSourceCr (factAcctBalance);
				line.setAmtAcctCr (factAcctBalance);
			}
			else
			{
				line.setAmtSourceDr (factAcctBalance.abs());
				line.setAmtAcctDr (factAcctBalance.abs());
				line.setAmtSourceCr (Env.ZERO);
				line.setAmtAcctCr (Env.ZERO);
			}
			line.saveEx();
			total = total.add(factAcctBalance);
			
			lineCount++;
		}
		
		//Retain Earning
		MJournalLine line = new MJournalLine(journal);
		line.setLine(lineCount*10);
		line.setC_Currency_ID(as.getC_Currency_ID());
		line.setAccount_ID(p_account_earning_id);
		if(total.compareTo(Env.ZERO) > 0)
		{
			line.setAmtSourceDr (total);
			line.setAmtAcctDr (total);
			line.setAmtSourceCr (Env.ZERO);
			line.setAmtAcctCr (Env.ZERO);

		}
		else
		{
			line.setAmtSourceDr (Env.ZERO);
			line.setAmtAcctDr (Env.ZERO);
			line.setAmtSourceCr (total.abs());
			line.setAmtAcctCr (total.abs());
		}
		
		line.saveEx();
		StringBuilder msgreturn = new StringBuilder("Generated: "+journal.getDocumentNo());
		addLog(journal.getGL_Journal_ID(), null, null, msgreturn.toString(), MJournal.Table_ID, journal.getGL_Journal_ID());
		
		return "success";
	}
}
