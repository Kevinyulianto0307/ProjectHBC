package org.toba.habco.process;


import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.toba.habco.model.X_HBC_CostActivity;
import org.toba.habco.model.X_T_ReferenceCode;

public class HBC_GenerateAllocationNumber extends SvrProcess {

	String p_InvoiceReference = "";
	String p_OrderReference = "";
	int p_C_BPartner_ID=0;
	int p_HBC_Trip_ID=0;

	/*
	 * @author yonk
	 */

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("InvoiceReference")){
				p_InvoiceReference = para[i].getParameterAsString();
				//@win
			}else if (name.equals("OrderReference")){
				p_OrderReference = para[i].getParameterAsString();
				//@win
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_HBC_Trip_ID = getRecord_ID();

	}

	@Override
	protected String doIt() throws Exception {
		
		//Temporary Commented by @TommyAng TODO could it be bug for further? @win
		/*
		if(getCostActivities().length==0){
			return "Process aborted.. No Unallocated Cost Activities";
		}
		*/
		
		int refcodes=getReferenceCode();
		int flag=0;
		for(int id : getCostActivities()){
			X_HBC_CostActivity costactivity = new X_HBC_CostActivity(getCtx(), id, get_TrxName());
			costactivity.set_ValueOfColumn("AllocationCode", refcodes);
			costactivity.set_ValueOfColumn("IsAllocates", false);
			costactivity.set_ValueOfColumn("C_Project_ID", costactivity.getHBC_Position().getC_Project_ID());
			if(p_InvoiceReference != null)
				costactivity.set_ValueOfColumn("InvoiceReference", p_InvoiceReference);
			costactivity.saveEx();
			p_C_BPartner_ID=(Integer)costactivity.get_Value("C_BPartner_ID");
			flag++;
		}
		
		if(flag>0){
			X_T_ReferenceCode refCode= new X_T_ReferenceCode(getCtx(), 0, get_TrxName());
			refCode.set_ValueOfColumn("AllocationCode", refcodes);
			if(p_OrderReference != null)
				refCode.set_ValueOfColumn("OrderReference", p_OrderReference);
	
			refCode.setC_BPartner_ID(p_C_BPartner_ID);	
			refCode.saveEx();
			
			String message = Msg.parseTranslation(getCtx(), "@Allocation Number Created@ " + refcodes );
			addBufferLog(0, null, BigDecimal.ZERO, message, 0, 0);
			return "Allocation Number Created ="+refcodes;
		}
		
		return "";

	}


	protected int[] getCostActivities(){
		String where="HBC_CostActivity.IsAllocates='Y' AND b.HBC_Trip_ID="+p_HBC_Trip_ID
				+" AND HBC_CostActivity.AllocationCode=0 AND HBC_CostActivity.CreatedBy="+getAD_User_ID();

		int[] HBC_CostActivity_IDs = new Query(getCtx(), X_HBC_CostActivity.Table_Name, where, get_TrxName())
		.addJoinClause("JOIN HBC_Position b ON HBC_CostActivity.HBC_Position_ID=b.HBC_Position_ID")
		.setOnlyActiveRecords(true)
		.getIDs();

		return HBC_CostActivity_IDs;
	}

	protected int getReferenceCode(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COALESCE(MAX(AllocationCode),0)+1 AS ReferenceCode FROM T_ReferenceCode");
		int refCode = DB.getSQLValue(get_TrxName(), sql.toString());		

		if(refCode ==1){
			refCode=100001;
		}
		return refCode;		
	}

}
