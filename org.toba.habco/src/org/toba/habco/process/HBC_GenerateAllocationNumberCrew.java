package org.toba.habco.process;

import java.util.logging.Level;

import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.toba.habco.model.X_HBC_CostActivity;
import org.toba.habco.model.X_T_ReferenceCode;

/*
 * @author yonk
 */
public class HBC_GenerateAllocationNumberCrew extends SvrProcess {

	int p_C_BPartner_ID=0;
	int p_HC_Employee_ID=0;
	String p_InvoiceReference = "";
	String p_OrderReference = "";

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("InvoiceReference")){
				p_InvoiceReference = para[i].getParameterAsString();
			}else if (name.equals("OrderReference")){
				p_OrderReference = para[i].getParameterAsString();
			}else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

		p_HC_Employee_ID = getRecord_ID();	
	}

	@Override
	protected String doIt() throws Exception {
		
		//Temporary Commented by @TommyAng TODO could it be bug for further? @win
		/*
		if(getCostActivities().length==0){
			return "Process aborted.. No Unallocated Cost Activities";
		}
		*/
		
		int refCode=getReferenceCode();
		int flag=0;		
		for(int id : getCostActivities()){
			X_HBC_CostActivity costactivity = new X_HBC_CostActivity(getCtx(), id, get_TrxName());
			costactivity.set_ValueOfColumn("AllocationCode", refCode);
			costactivity.set_ValueOfColumn("IsAllocates", false);
			if(p_InvoiceReference != null)
				costactivity.set_ValueOfColumn("InvoiceReference", p_InvoiceReference);
			costactivity.saveEx();
			p_C_BPartner_ID=costactivity.getC_BPartner_ID();
			flag++;
		}
		if(flag>0){
			X_T_ReferenceCode refcode= new X_T_ReferenceCode(getCtx(), 0, get_TrxName());
			refcode.set_ValueOfColumn("AllocationCode", refCode);
			if(p_OrderReference != null)
				refcode.set_ValueOfColumn("OrderReference", p_OrderReference);
	
			refcode.setC_BPartner_ID(p_C_BPartner_ID);	
			refcode.saveEx();
			
			String message = Msg.parseTranslation(getCtx(), "@Generated@ " + refCode);
			addBufferLog(0, null, null, message, 0, 0);
			return message;
		}
		
		return "";
		
	}

	protected int[] getCostActivities(){
		String where="HBC_CostActivity.IsAllocates='Y' AND HBC_CostActivity.HC_Employee_ID="+p_HC_Employee_ID
				+"AND HBC_CostActivity.AllocationCode=0 AND HBC_CostActivity.CreatedBy="+getAD_User_ID();

		int[] HBC_CostActivity_IDs = new Query(getCtx(), X_HBC_CostActivity.Table_Name, where, get_TrxName())
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
