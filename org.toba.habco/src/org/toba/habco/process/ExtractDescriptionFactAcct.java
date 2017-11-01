package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MFactAcct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.MTrip;

/**
 * 
 * @author Phie Albert
 * Extract description on Fact Acct
 */

public class ExtractDescriptionFactAcct extends SvrProcess{
	Timestamp p_dateAcctFrom = null;
	Timestamp p_dateAcctTo = null;
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(MFactAcct.COLUMNNAME_DateAcct)){
				p_dateAcctFrom = para[i].getParameterAsTimestamp();
				p_dateAcctTo = para[i].getParameter_ToAsTimestamp();
			}
		}
	}
	
	protected String doIt() throws Exception
	{
		if(p_dateAcctFrom == null || p_dateAcctTo == null)
			throw new AdempiereException("Date Acct is mandatory..");
		
		String whereClause= "DateAcct BETWEEN ? AND ? AND DocumentNo IS NULL";
		int[] fact_acct_id = new Query(getCtx(), MFactAcct.Table_Name, whereClause, get_TrxName())
								.setParameters(new Object[]{p_dateAcctFrom, p_dateAcctTo})					
								.getIDs();
		
		for(int i=0;i<fact_acct_id.length;i++)
		{
			MFactAcct fa = new MFactAcct(getCtx(), fact_acct_id[i], get_TrxName());
			String desc = fa.getDescription();
			String regex = "[#(]";
			String[] words = desc.split(regex);
			if(words.length < 1)
				return "";
			
			fa.set_ValueOfColumn("DocumentNo", words[0]);
			
			if(fa.getLine_ID() > 0){
				
				if(words[1] != null){
					Object obj = words[1].trim();
					if(obj instanceof Integer){
						BigDecimal bd = new BigDecimal(words[1].trim());
						fa.set_ValueOfColumn("Line", bd.intValue());
					}
				}
				if(words.length > 2){
					StringBuilder description = new StringBuilder();
					for(int j=2 ; j<words.length ; j++){
						description.append(words[j]);
					}
					description.deleteCharAt(description.length()-1);
					fa.set_ValueOfColumn("Description", description.toString());
				}
			}
			else if(words.length > 1){				
				StringBuilder description = new StringBuilder();
				for(int j=1 ; j<words.length ; j++){
					description.append(words[j]);
				}
				description.deleteCharAt(description.length()-1);
				fa.set_ValueOfColumn("Description", description.toString());
			}
			fa.save();
		}
		return "";
	}
}
