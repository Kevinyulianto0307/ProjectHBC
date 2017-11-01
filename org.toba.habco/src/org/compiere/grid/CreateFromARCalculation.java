package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.apps.IStatusBar;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MTax;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MInvoice;
import org.toba.habco.model.HBC_MInvoiceLine;
import org.toba.habco.model.MARCalculationLine;
import org.toba.habco.model.MMatchARCalculation;
import org.toba.habco.model.X_HBC_ARCalculation;
import org.toba.habco.model.X_HBC_ARCalculationLine;

public class CreateFromARCalculation extends CreateFrom {

	public CreateFromARCalculation(GridTab gridTab) {
		super(gridTab);
		
	}

	@Override
	public Object getWindow() {
		return null;
	}

	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "C_Invoice_ID", false) + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}

	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
        Vector<String> columnNames = new Vector<String>(9);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add(Msg.getMsg(Env.getCtx(), "Line"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_Project_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "HBC_Trip_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "MilestonePercentage"));
        columnNames.add(Msg.translate(Env.getCtx(), "QtyCharge"));
        columnNames.add(Msg.translate(Env.getCtx(), "TotalAmt"));
        columnNames.add(Msg.translate(Env.getCtx(), "From_PortPosition_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "To_PortPosition_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "DateStart"));
	    return columnNames;
	}
	
	protected Vector<Vector<Object>> getARCalculationLine(int C_BPartner_ID,int HBC_Contract_ID, int C_Period_ID, String CalculationType)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT (h.documentno||'_'||c.Value||'_'||d.Value) as TripNo,h.DateStart," //1,2
        		+ "a.MilestonePercentage,a.QtyCharge,a.TotalAmt,a.C_Project_ID"//3,4,5,6
        		+ ",e.value as ProjectNo,a.From_PortPosition_ID,f.Name as PortPositionFrom,a.To_PortPosition_ID,g.Name as PortPositionTo " //7,8,9,10,11
        		+ ",a.HBC_ARCalculation_ID,a.HBC_ARCalculationLine_ID,a.Line " //12,13,14
        		+ "FROM HBC_ARCalculationLine a "
        		+ "JOIN HBC_ARCalculation b ON a.HBC_ARCalculation_ID=b.HBC_ARCalculation_ID "
        		+ "JOIN HBC_Tugboat c ON c.HBC_Tugboat_ID=a.HBC_Tugboat_ID "
        		+ "JOIN HBC_Barge d ON d.HBC_Barge_ID=a.HBC_Barge_ID "
        		+ "LEFT JOIN C_Project e ON e.C_Project_ID=a.C_Project_ID "
        		+ "JOIN HBC_PortPosition f ON f.HBC_PortPosition_ID=a.From_PortPosition_ID "
        		+ "JOIN HBC_PortPosition g ON g.HBC_PortPosition_ID=a.To_PortPosition_ID "
        		+ "JOIN HBC_Trip h ON h.HBC_Trip_ID=a.HBC_Trip_ID "
        		+ "WHERE a.AD_CLient_ID=? AND a.C_BPartner_ID=? "
        		+ "AND a.HBC_ARCalculationLine_ID NOT IN (SELECT HBC_ARCalculationLine_ID FROM M_MatchARCalculation)" //TODO:@win.. this can cause performance issue and bug. Please improve the code.
        		+ "AND a.C_InvoiceLine_ID IS NULL AND a.Processed='N'");
        
        if (HBC_Contract_ID>0)
        	sqlStmt.append(" AND h.HBC_Contract_ID=?");
        if (C_Period_ID > 0)
        	sqlStmt.append(" AND a.C_Period_ID=?");
        if (CalculationType != null && !CalculationType.isEmpty())
        	sqlStmt.append(" AND a.ARCalculationType=?");
        try
        {
        	int count = 0;
        	PreparedStatement pstmt = DB.prepareStatement(sqlStmt.toString(), null);
            pstmt.setInt(++count, Env.getAD_Client_ID(Env.getCtx()));
            pstmt.setInt(++count, C_BPartner_ID);  
	        if (HBC_Contract_ID>0)
	            pstmt.setInt(++count, HBC_Contract_ID);
	        if (C_Period_ID > 0)
	        	pstmt.setInt(++count, C_Period_ID);
	        if (CalculationType != null && !CalculationType.isEmpty())
	        	pstmt.setString(++count, CalculationType);
	            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
				Vector<Object> line = new Vector<Object>(8);
                line.add(new Boolean(false));           //  0-Selection
                KeyNamePair ARHeaderKP = new KeyNamePair(rs.getInt(12),rs.getString(1));
                KeyNamePair ARLineKP   = new KeyNamePair(rs.getInt(13),rs.getString(14));
                line.add(ARLineKP);//9-ARCalculationLine ID
                line.add(rs.getString(7));//1-ProjectNo
                line.add(ARHeaderKP); // 2-AR CalculationNo
                line.add(rs.getBigDecimal(3));//4-MilestonePercentage
                line.add(rs.getBigDecimal(4));//5-QtyCharge
                line.add(rs.getBigDecimal(5));//6-TotalAmt
                line.add(rs.getString(9));//7-Port/Position From
                line.add(rs.getString(11));//8-Port/Position To
                line.add(rs.getTimestamp(2)); // 3-DateStart trip
                data.add(line);
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e)
        {
            log.log(Level.SEVERE, sqlStmt.toString(), e);
        }
        return data;
	}
	
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);    	   //  0-Selection
		miniTable.setColumnClass(1, String.class, true);  	// 9- Line NO
		miniTable.setColumnClass(2, String.class, true); 	  	  // 1-Project no
		miniTable.setColumnClass(3, String.class, true);       	 //  2-AR Calculation No
		miniTable.setColumnClass(4, BigDecimal.class, true);   //  4-Milestone Percentage
		miniTable.setColumnClass(5, BigDecimal.class, true);  //  5-Qty Charge
		miniTable.setColumnClass(6, BigDecimal.class, true); //  6-Total amount
		miniTable.setColumnClass(7, String.class, true);	// 7-Port/Position From
		miniTable.setColumnClass(8, String.class, true);   // 8-Port/Position To
		miniTable.setColumnClass(9, Timestamp.class, true);		//  3- Date Start trip
		//  Table UI
		miniTable.autoSize();
	}

	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		for (int i = 0; i < miniTable.getRowCount(); i++)
        {
			int C_Invoice_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Invoice_ID");
            HBC_MInvoice invoice = new HBC_MInvoice(Env.getCtx(),C_Invoice_ID,trxName);
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
            {
				HBC_MInvoiceLine invLine= new HBC_MInvoiceLine(invoice);
            	KeyNamePair ARheader = (KeyNamePair)miniTable.getValueAt(i, 3);
            	KeyNamePair ARLine = (KeyNamePair)miniTable.getValueAt(i, 1);
                int ARHeaderID = ARheader.getKey();
                int ARLineID = ARLine.getKey();
                X_HBC_ARCalculation ARCalc = new X_HBC_ARCalculation(Env.getCtx(), ARHeaderID, trxName);
                X_HBC_ARCalculationLine ARCalcLine = new X_HBC_ARCalculationLine(Env.getCtx(), ARLineID, trxName);
                
                invoice.set_ValueOfColumn("HBC_Contract_ID", ARCalc.getHBC_Contract_ID());
                invoice.set_ValueOfColumn("ARCalculationType", ARCalcLine.getARCalculationType());
                invoice.saveEx();
        		
                invLine.setLine(ARCalcLine.getLine());
                invLine.setM_Product_ID(ARCalcLine.get_ValueAsInt("M_Product_ID"));
                invLine.set_ValueOfColumn("HBC_Tugboat_ID", ARCalcLine.getHBC_Tugboat_ID());
                invLine.set_ValueOfColumn("HBC_Barge_ID", ARCalcLine.getHBC_Barge_ID());
                invLine.setQty(ARCalcLine.getQtyToInvoiced());
                invLine.setQtyEntered(ARCalcLine.getQtyToInvoiced());
                invLine.setQtyInvoiced(ARCalcLine.getQtyToInvoiced());
                invLine.setC_Tax_ID(invoice.getC_Tax_ID());
                invLine.setPrice(ARCalcLine.getUnitPrice());
                invLine.setPriceEntered(ARCalcLine.getUnitPrice());
                invLine.setPriceList(ARCalcLine.getUnitPrice());
                invLine.setPriceActual(ARCalcLine.getUnitPrice());
                invLine.set_ValueOfColumn("OriginalPriceList", ARCalcLine.getUnitPrice());
                if(ARCalcLine.getHBC_Trip_ID() > 0)
                	invLine.set_ValueOfColumn("HBC_Trip_ID", ARCalcLine.getHBC_Trip_ID());
                invLine.setC_Project_ID(ARCalcLine.getC_Project_ID());
                
                if((ARCalcLine.get_Value("ARCalculationType").equals(MARCalculationLine.ARCALCULATIONTYPE_Annual) || 
                		ARCalcLine.get_Value("ARCalculationType").equals(MARCalculationLine.ARCALCULATIONTYPE_SPAL) ||
                		ARCalcLine.get_Value("ARCalculationType").equals("SPHJ")))
                	invLine.set_ValueOfColumn("TotalCargoQty", ARCalcLine.getTotalCargoQty());
                
                invLine.saveEx();
                
                MMatchARCalculation MatchAR = new MMatchARCalculation(Env.getCtx(), 0, trxName);
                MatchAR.setC_Invoice_ID(invLine.getC_Invoice_ID());
                MatchAR.setC_InvoiceLine_ID(invLine.getC_InvoiceLine_ID());
                MatchAR.setHBC_Trip_ID(ARCalcLine.getHBC_Trip_ID());
                MatchAR.setHBC_ARCalculation_ID(ARHeaderID);
                MatchAR.setHBC_ARCalculationLine_ID(ARLineID);
                MatchAR.setC_ProjectMilestone_ID(ARCalcLine.getC_ProjectMilestone_ID());
                MatchAR.setC_ProjectTypeMilestone_ID(ARCalcLine.getC_ProjectMilestone().getC_ProjectTypeMilestone_ID());
                MatchAR.saveEx();
                
                ARCalcLine.setC_Invoice_ID(invLine.getC_Invoice_ID());
                ARCalcLine.setC_InvoiceLine_ID(invLine.getC_InvoiceLine_ID());
                ARCalcLine.setProcessed(true);
                ARCalcLine.saveEx();
            }
        }
		return true;
	}

}
