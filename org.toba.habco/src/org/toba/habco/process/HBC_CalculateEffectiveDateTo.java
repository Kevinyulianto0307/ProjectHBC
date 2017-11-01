package org.toba.habco.process;

import java.sql.Timestamp;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.toba.habco.model.X_HBC_ShipDocumentLine;

public class HBC_CalculateEffectiveDateTo extends SvrProcess {
	
	int p_HBC_ShipDocumentLine_ID=0;
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		p_HBC_ShipDocumentLine_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		X_HBC_ShipDocumentLine documentline = new X_HBC_ShipDocumentLine(getCtx(), p_HBC_ShipDocumentLine_ID, get_TrxName());
		if(p_HBC_ShipDocumentLine_ID==0){
			return "Error-No Ship Document Line Selected!";
		}
		
		
		if(documentline.isLegalized1() !=true){
			if(documentline.getLegalizedDate1()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized2() !=true){
			if(documentline.getLegalizedDate2()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized3() !=true){
			if(documentline.getLegalizedDate3()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized4() !=true){
			if(documentline.getLegalizedDate4()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized5() !=true){
			if(documentline.getLegalizedDate5()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized6() !=true){
			if(documentline.getLegalizedDate6()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized7() !=true){
			if(documentline.getLegalizedDate7()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized8() !=true){
			if(documentline.getLegalizedDate8()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized9() !=true){
			if(documentline.getLegalizedDate9()==null){
				return "Error-Must fill Lagelized first";
			}
		}else if(documentline.isLegalized10() !=true){
			if(documentline.getLegalizedDate10()==null){
				return "Error-Must fill Lagelized first";
			}
		}
		
		
		String sql2 = "SELECT sdl.EffectiveDateto + cast('1 year' as interval)"
				+ " FROM HBC_ShipDocumentLine sdl "
				+ " where sdl.HBC_ShipDocumentLine_ID = ?";
		Timestamp date =DB.getSQLValueTS(null, sql2, new Object[]{p_HBC_ShipDocumentLine_ID});
		
		if(date==null)
			return"Error Effective date to is null";
		
		documentline.setEffectiveDateTo(date);
		if(documentline.isLegalized1() !=true){
			documentline.setIsLegalized1(true);
		}else if(documentline.isLegalized2() !=true){
			documentline.setIsLegalized2(true);
		}else if(documentline.isLegalized3() !=true){
			documentline.setIsLegalized3(true);
		}else if(documentline.isLegalized4() !=true){
			documentline.setIsLegalized4(true);
		}else if(documentline.isLegalized5() !=true){
			documentline.setIsLegalized5(true);
		}else if(documentline.isLegalized6() !=true){
			documentline.setIsLegalized6(true);
		}else if(documentline.isLegalized7() !=true){
			documentline.setIsLegalized7(true);
		}else if(documentline.isLegalized8() !=true){
			documentline.setIsLegalized8(true);
		}else if(documentline.isLegalized9() !=true){
			documentline.setIsLegalized9(true);
		}else if(documentline.isLegalized10() !=true){
			documentline.setIsLegalized10(true);
		}
		documentline.saveEx();
		return null;
	}
	
	
}
