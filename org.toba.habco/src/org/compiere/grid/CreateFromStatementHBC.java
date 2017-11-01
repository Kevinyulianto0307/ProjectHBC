/******************************************************************************
 * Copyright (C) 2013 Elaine Tan                                              *
 * Copyright (C) 2013 Trek Global
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPayment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/**
 * 
 * @author Elaine
 *
 */
public abstract class CreateFromStatementHBC extends CreateFromBatch
{
	public CreateFromStatementHBC(GridTab mTab) 
	{
		super(mTab);
		if (log.isLoggable(Level.INFO)) log.info(mTab.toString());
	}

	public boolean dynInit() throws Exception
	{
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "C_BankStatement_ID") + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		
		return true;
	}
	
	protected Vector<Vector<Object>> getBankAccountData(Object BankAccount, Object BPartner, String DocumentNo, Timestamp PeriodDateFrom, Timestamp PeriodDateTo,
			Object DateFrom, Object DateTo, Object AmtFrom, Object AmtTo, Object DocType, Object TenderType, String AuthCode)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p.DateTrx,p.C_Payment_ID,p.DocumentNo, p.C_Currency_ID,c.ISO_Code, p.PayAmt,");
		sql.append("currencyConvert(p.PayAmt,p.C_Currency_ID,ba.C_Currency_ID,p.DateAcct,p.C_ConversionType_ID,p.AD_Client_ID,p.AD_Org_ID), bp.Name ");
		//	@Stephan TAOWI-446
		sql.append(",p.TenderType, p.GiroStatus, p.NoVoucher ");
		//	end
		sql.append("FROM C_BankAccount ba");
		sql.append(" INNER JOIN C_Payment_v p ON (p.C_BankAccount_ID=ba.C_BankAccount_ID)");
		sql.append(" INNER JOIN C_Currency c ON (p.C_Currency_ID=c.C_Currency_ID)");
		sql.append(" LEFT OUTER JOIN C_BPartner bp ON (p.C_BPartner_ID=bp.C_BPartner_ID) ");
		sql.append(getSQLWhere(BPartner, DocumentNo, PeriodDateFrom, PeriodDateTo, DateFrom, DateTo, AmtFrom, AmtTo, DocType, TenderType, AuthCode));
		sql.append(" ORDER BY p.DateTrx");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			setParametersC(pstmt, BankAccount, PeriodDateFrom, PeriodDateTo, BPartner, DocumentNo, DateFrom, DateTo, AmtFrom, AmtTo, DocType, TenderType, AuthCode);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Vector<Object> line = new Vector<Object>(6);
				line.add(new Boolean(false));       //  0-Selection
				line.add(rs.getTimestamp(1));       //  1-DateTrx
				KeyNamePair pp = new KeyNamePair(rs.getInt(2), rs.getString(3));
				line.add(pp);                       //  2-C_Payment_ID
				pp = new KeyNamePair(rs.getInt(4), rs.getString(5));
				line.add(pp);                       //  3-Currency
				line.add(rs.getBigDecimal(6));      //  4-PayAmt
				line.add(rs.getBigDecimal(7));      //  5-Conv Amt
				line.add(rs.getString(8));      	//  6-BParner
				line.add(rs.getString(11));
				//	@Stephan TAOWI-446
				//	data.add(line);
				if(rs.getString(9).equals("G")){
					if(rs.getString(10).equals("RMT") || rs.getString(10).equals("BON"))
						data.add(line);
				}
				else
					data.add(line);
				//	end
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return data;
	}
	
	protected void configureMiniTable(IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, Timestamp.class, false);    //  1-TrxDate / nmicoud - idempiere 240 Let user choose the 'Statement Line Date'
		miniTable.setColumnClass(2, String.class, true);        //  2-Payment
		miniTable.setColumnClass(3, String.class, true);        //  3-Currency
		miniTable.setColumnClass(4, BigDecimal.class, true);    //  4-Amount
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5-ConvAmount
		miniTable.setColumnClass(6, String.class, true);    	//  6-BPartner
		miniTable.setColumnClass(7, String.class, true);
		//  Table UI
		miniTable.autoSize();
	}

	public boolean save(IMiniTable miniTable, String trxName)
	{
		//  fixed values
		int C_BankStatement_ID = ((Integer) getGridTab().getValue("C_BankStatement_ID")).intValue();
		MBankStatement bs = new MBankStatement (Env.getCtx(), C_BankStatement_ID, trxName);
		if (log.isLoggable(Level.CONFIG)) log.config(bs.toString());

		//  Lines
		for(int i = 0; i < miniTable.getRowCount(); i++)
		{
			if(((Boolean) miniTable.getValueAt(i, 0)).booleanValue())
			{
				Timestamp trxDate = (Timestamp) miniTable.getValueAt(i, 1);  //  1-DateTrx
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 2);   //  2-C_Payment_ID
				int C_Payment_ID = pp.getKey();
				pp = (KeyNamePair) miniTable.getValueAt(i, 3);               //  3-Currency
				int C_Currency_ID = pp.getKey();
				BigDecimal TrxAmt = (BigDecimal) miniTable.getValueAt(i, 5); //  5- Conv Amt

				if (log.isLoggable(Level.FINE)) log.fine("Line Date=" + trxDate
					+ ", Payment=" + C_Payment_ID + ", Currency=" + C_Currency_ID + ", Amt=" + TrxAmt);
				//	
				MBankStatementLine bsl = new MBankStatementLine (bs);
				bsl.setStatementLineDate(trxDate);
				bsl.setPayment(new MPayment(Env.getCtx(), C_Payment_ID, trxName));
				
				bsl.setTrxAmt(TrxAmt);
				bsl.setStmtAmt(TrxAmt);
				bsl.setC_Currency_ID(bs.getBankAccount().getC_Currency_ID()); 
				
				if (!bsl.save())
					log.log(Level.SEVERE, "Line not created #" + i);
			}   //   if selected
		}   //  for all rows
		return true;
	}   //  save
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(7);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Msg.getElement(Env.getCtx(), "C_Payment_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "Amount"));
		columnNames.add(Msg.translate(Env.getCtx(), "ConvertedAmount"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		columnNames.add("NoVoucher");
	    
	    return columnNames;
	}
	
	void setParametersC(PreparedStatement pstmt, Object BankAccount, Timestamp PeriodDateFrom, Timestamp PeriodDateTo, 
			Object BPartner, String DocumentNo, Object DateFrom, Object DateTo, 
			Object AmtFrom, Object AmtTo, Object DocType, Object TenderType, String AuthCode)
	throws SQLException
	{
		//  Get StatementDate
		//Timestamp ts = (Timestamp) getGridTab().getValue("StatementDate");
		//if (ts == null)
			//ts = new Timestamp(System.currentTimeMillis());

		int index = 1;
		
		//pstmt.setTimestamp(index++, ts);		
		pstmt.setInt(index++, BankAccount != null ? (Integer) BankAccount : (Integer) getGridTab().getValue("C_BankAccount_ID"));
		pstmt.setTimestamp(index++, PeriodDateFrom);
		pstmt.setTimestamp(index++, PeriodDateTo);
		
		if(DocType != null)
			pstmt.setInt(index++, (Integer) DocType);
		
		if(TenderType != null && TenderType.toString().length() > 0)
			pstmt.setString(index++, (String) TenderType);
		
		if(BPartner != null)
			pstmt.setInt(index++, (Integer) BPartner);
		
		if(DocumentNo.length() > 0)
			pstmt.setString(index++, getSQLTextC(DocumentNo));
		
		if(AuthCode.length() > 0)
			pstmt.setString(index++, getSQLTextC(AuthCode));
		
		if(AmtFrom != null || AmtTo != null)
		{
			BigDecimal from = (BigDecimal) AmtFrom;
			BigDecimal to = (BigDecimal) AmtTo;
			if (log.isLoggable(Level.FINE)) log.fine("Amt From=" + from + ", To=" + to);
			if(from == null && to != null)
				pstmt.setBigDecimal(index++, to);
			else if(from != null && to == null)
				pstmt.setBigDecimal(index++, from);
			else if(from != null && to != null)
			{
				pstmt.setBigDecimal(index++, from);
				pstmt.setBigDecimal(index++, to);
			}
		}
		
		if(DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (log.isLoggable(Level.FINE)) log.fine("Date From=" + from + ", To=" + to);
			if(from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if(from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if(from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
	}
	
	private String getSQLTextC(String text)
	{
		String s = text.toUpperCase();
		if(!s.endsWith("%"))
			s += "%";
		if (log.isLoggable(Level.FINE)) log.fine( "String=" + s);
		return s;
	}
	
	public String getSQLWhereC(Object BPartner, String DocumentNo, Timestamp PeriodDateFrom, Timestamp PeriodDateTo, Object DateFrom, Object DateTo, 
			Object AmtFrom, Object AmtTo, Object DocType, Object TenderType, String AuthCode)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("WHERE p.Processed='Y' AND p.IsReconciled='N'");
		//@win - only reconcile Payment with status CO and CL
		//sql.append(" AND p.DocStatus IN ('CO','CL','RE','VO') AND p.PayAmt<>0"); 
		
		//@edwin handy TAOWI-1350
		sql.append(" AND p.DocStatus IN ('CO','CL','RE') AND p.PayAmt<>0"); 
		//@edwin handy end
		
		sql.append(" AND p.C_BankAccount_ID = ?");
		//@win - only select payment with the bank statement period 
		sql.append(" AND TRUNC(p.DateAcct) BETWEEN ? AND ?");
		
	    sql.append(" AND NOT EXISTS (SELECT * FROM C_BankStatementLine l WHERE p.C_Payment_ID=l.C_Payment_ID AND l.StmtAmt <> 0)");
	    	    
	    if(DocType != null)
			sql.append(" AND p.C_DocType_ID=?");
	    if(TenderType != null && TenderType.toString().length() > 0)
			sql.append(" AND p.TenderType=?");
		if(BPartner != null)
			sql.append(" AND p.C_BPartner_ID=?");
		
		if(DocumentNo.length() > 0)
			sql.append(" AND UPPER(p.DocumentNo) LIKE ?");
		if(AuthCode.length() > 0)
			sql.append(" AND p.R_AuthCode LIKE ?");
		
		if(AmtFrom != null || AmtTo != null)
		{
			BigDecimal from = (BigDecimal) AmtFrom;
			BigDecimal to = (BigDecimal) AmtTo;
			if(from == null && to != null)
				sql.append(" AND p.PayAmt <= ?");
			else if(from != null && to == null)
				sql.append(" AND p.PayAmt >= ?");
			else if(from != null && to != null)
				sql.append(" AND p.PayAmt BETWEEN ? AND ?");
		}
		
		if(DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if(from == null && to != null)
				sql.append(" AND TRUNC(p.DateTrx) <= ?");
			else if(from != null && to == null)
				sql.append(" AND TRUNC(p.DateTrx) >= ?");
			else if(from != null && to != null)
				sql.append(" AND TRUNC(p.DateTrx) BETWEEN ? AND ?");
		}

		if (log.isLoggable(Level.FINE)) log.fine(sql.toString());
		return sql.toString();
	}
	
}