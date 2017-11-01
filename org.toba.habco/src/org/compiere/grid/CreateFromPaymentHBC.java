package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.apps.IStatusBar;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;


/*
 * Created by yonk
 */

public class CreateFromPaymentHBC extends CreateFrom {

	public CreateFromPaymentHBC(GridTab mTab) {
		super(mTab);
		if (log.isLoggable(Level.INFO)) log.info(mTab.toString());
	}

	@Override
	public Object getWindow() {
		
		return null;
	}

	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "C_Payment_ID", false) + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}
	
	protected ArrayList<KeyNamePair> loadInvoiceData ()
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();

		return list;
	}
	
	protected Vector<Vector<Object>> getInvoiceData()
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_BPartner_ID");
			int C_Payment_ID =Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Payment_ID");
			
			MPayment payment = new MPayment(Env.getCtx(),C_Payment_ID,null);
			String isreceipt="";
            if(payment.get_ValueAsBoolean("IsReceipt")){
            	isreceipt="Y";
            }else if(!payment.get_ValueAsBoolean("IsReceipt")){
            	isreceipt="N";
            }
			
		 StringBuilder sql = new StringBuilder();
		 
		 sql.append("SELECT c.DocumentNO,c.C_DocType_ID,d.name as DoctypeName,c.C_BPartner_ID,"//1,2,3,4
		 		+ "b.Name as BPName,c.C_Currency_ID,cc.iso_code as CurrencyName,c.C_Tax_ID,t.Name as TaxName," //6,7,8,9,10,11
		 		+ "c.totallines,c.grandtotal,c.C_Invoice_ID, c.DateAcct, c.POReference "//12,13,14
		 		+ " FROM C_Invoice c"
				+ " JOIN C_BPartner b on b.C_BPartner_ID=c.C_BPartner_ID"
				+ " JOIN C_Currency cc on cc.C_Currency_ID=c.C_Currency_ID"
				+ " JOIN C_Doctype d on d.C_Doctype_ID=c.C_DocType_ID"
		 		+ " JOIN C_Tax t ON t.C_Tax_ID=c.C_Tax_ID"
				+ " WHERE C_Payment_ID is null and c.AD_Client_ID=? AND c.IsSOTrx='"+isreceipt+"'"); //TODO @win: gw ga ingat ada column C_Payment_ID di table2 di atas. Please fix this
		 
		 	if (C_BPartner_ID>0){
		 		sql.append(" AND c.C_BPartner_ID=?");
		 	}
		 try
	        {
	        	int count = 0;
	        	PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
	            pstmt.setInt(++count, Env.getAD_Client_ID(Env.getCtx()));
	            if(C_BPartner_ID>0){
	            	pstmt.setInt(++count, C_BPartner_ID);
	            }
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next())
	            {
	            	Vector<Object> line = new Vector<Object>(8);
	            	KeyNamePair lineKNPair = new KeyNamePair(rs.getInt(12), rs.getString(1));
	                line.add(new Boolean(false));           //  0-Selection
	                line.add(lineKNPair); // 1-Document no
	                line.add(rs.getString(3));
	                line.add(rs.getString(5));
	                line.add(rs.getString(7));
	                line.add(rs.getString(9));
	                line.add(rs.getBigDecimal(10));
	                line.add(rs.getBigDecimal(11));
	                line.add(rs.getTimestamp(13));
	                line.add(rs.getString(14));
	                int allocateid= new Query(Env.getCtx(),MPaymentAllocate.Table_Name,"C_Invoice_ID="+rs.getInt(12),null)
	                				.firstId();
	                if(allocateid<0)
	                	data.add(line);
	            }
	            
	            rs.close();
	            pstmt.close();
	        }
	        catch (SQLException e)
	        {
	            log.log(Level.SEVERE, sql.toString(), e);
	        }
		
		return data;
	}

	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
        Vector<String> columnNames = new Vector<String>(7);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add(Msg.getMsg(Env.getCtx(), "DocumentNo"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_DocType_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_Currency_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_Tax_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "TotalLines"));
        columnNames.add(Msg.translate(Env.getCtx(), "Grandtotal"));
        columnNames.add(Msg.translate(Env.getCtx(), "DateAcct"));
        columnNames.add(Msg.translate(Env.getCtx(), "POReference"));
	    return columnNames;
	}
	
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, String.class, true);        //  1-DocumentNo
		miniTable.setColumnClass(2, String.class, true);        //  2-DocType 
		miniTable.setColumnClass(3, String.class, true);        //  3-BP
		miniTable.setColumnClass(4, String.class, true);   //  4-Currency
		miniTable.setColumnClass(5, String.class, true);   //  5-Tax
		miniTable.setColumnClass(6, BigDecimal.class, true);   //  6-Totalline
		miniTable.setColumnClass(7, BigDecimal.class, true);        //  7-Grandtotal
		miniTable.setColumnClass(8, Timestamp.class, true);
		miniTable.setColumnClass(9, String.class, true);
		 
        //  Table UI
		miniTable.autoSize();
	}


	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		
		log.config("");
		int C_Payment_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Payment_ID");
		MPayment payment = new MPayment(Env.getCtx(),C_Payment_ID,null);
		int AD_Org_ID=Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "AD_Org_ID");
		BigDecimal totalamount = Env.ZERO;
		for (int i = 0; i < miniTable.getRowCount(); i++)
	    {
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
	        {
	          	MPaymentAllocate allocate = new MPaymentAllocate(Env.getCtx(), 0, trxName);
	           	KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 1);   //  1-Line
	           	int invoiceid=pp.getKey();
	           	BigDecimal amount = (BigDecimal)miniTable.getValueAt(i, 7);
	           	allocate.setC_Payment_ID(C_Payment_ID);
	           	allocate.setC_Invoice_ID(invoiceid);
	           	allocate.setAD_Org_ID(AD_Org_ID);
	           	allocate.setAmount(amount);
	           	allocate.setInvoiceAmt(amount);
	           	allocate.saveEx();
	           	totalamount=totalamount.add(amount);
	        }     	
	    }
		payment.setPayAmt(totalamount);
      	payment.saveEx();
      	
      	if(payment.getC_Currency().getISO_Code().equalsIgnoreCase("IDR")){
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_Payment SET PayAmt=ROUND(PayAmt,0) WHERE C_Payment_ID=?");
			int no = DB.executeUpdate(sb.toString(), payment.get_ID(), null);
			log.info("UPDATED PayAmt C_Payment#"+no);			
			
		}
		else{
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE C_Payment SET PayAmt=ROUND(PayAmt,2) WHERE C_Payment_ID=?");
			int no = DB.executeUpdate(sb.toString(), payment.get_ID(), null);
			log.info("UPDATED PayAmt C_Payment#"+no);
			
		}
      	
		return true;
	}

}
