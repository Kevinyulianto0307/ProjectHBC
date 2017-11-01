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
import org.compiere.model.MOrder;
import org.compiere.model.MProductPricing;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MUOMConversion;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.toba.habco.model.HBC_MOrderLine;

public class CreateFromOrderHBC extends CreateFrom {
	/*
	 * create by yonk
	 * 
	 */

	public CreateFromOrderHBC(GridTab mTab) {
		super(mTab);
		if (log.isLoggable(Level.INFO)) log.info(mTab.toString());
	}

	@Override
	public Object getWindow() {
		return null;
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
        Vector<String> columnNames = new Vector<String>(7);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add("Line");
        columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_Charge_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "Quantity"));
        columnNames.add(Msg.translate(Env.getCtx(), "DateRequired"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_Project_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_UOM_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "QtyRequired"));
	    return columnNames;
	}

	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "C_Order_ID", false) + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}

	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		
	}
	
	protected Vector<Vector<Object>> getRequisitionData(int M_Requisition_ID, int M_Product_ID, Timestamp dateRequired, int C_Charge_ID, int salesRepID)
	{
		//int C_Order_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Order_ID");
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		/**
         * 1 M_RequisitionLine_ID
         * 2 Line
         * 3 Product Name
         * 4 Qty Entered
         */
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append("SELECT rl.M_RequisitionLine_ID, rl.Line, "); //1..2
        sqlStmt.append("CASE WHEN rl.M_Product_ID IS NOT NULL THEN (SELECT p.Value||'-'||p.Name FROM M_Product p WHERE p.M_Product_ID = rl.M_Product_ID) END as ProductName, "); //3
        sqlStmt.append("tcs_remainingprqty(rl.M_RequisitionLine_ID)as Qty, mr.DateRequired, "); //4..5
        sqlStmt.append("CASE WHEN mr.C_Project_ID IS NOT NULL THEN (SELECT pj.Value||'-'||pj.Name FROM C_Project pj WHERE pj.C_Project_ID = mr.C_Project_ID) END as Project, "); //6 
        sqlStmt.append("CASE WHEN rl.C_UOM_ID IS NOT NULL THEN (SELECT u.Name FROM C_UOM u WHERE u.C_UOM_ID = rl.C_UOM_ID) END AS uomName, "); //7
       // sqlStmt.append("COALESCE(rl.Qty,0)-(SELECT COALESCE(SUM(QtyEntered),0) FROM C_OrderLine WHERE M_RequisitionLine_ID = rl.M_RequisitionLine_ID), COALESCE(rl.C_UOM_ID, 0) AS C_UOM_ID, "); //8 ..9
        sqlStmt.append("CASE WHEN rl.C_Charge_ID IS NOT NULL THEN (SELECT c.Name FROM C_Charge c WHERE c.C_Charge_ID = rl.C_Charge_ID) END as ChargeName "); 
        sqlStmt.append("FROM M_RequisitionLine rl ");
        sqlStmt.append("INNER JOIN M_Requisition mr ON mr.M_Requisition_ID=rl.M_Requisition_ID ");
        sqlStmt.append("WHERE rl.AD_Client_ID=? ");
        
        if (M_Requisition_ID > 0) {
        	sqlStmt.append("AND rl.M_Requisition_ID=? ");
        }
        
        if (M_Product_ID > 0) {
        	sqlStmt.append("AND rl.M_Product_ID=? ");
        }
        
        if (dateRequired != null) {
        	sqlStmt.append("AND mr.DateRequired=? ");
        }
              
        if (C_Charge_ID > 0) {
        	sqlStmt.append("AND rl.C_Charge_ID=? ");
   
        }
        try
        {
        	int count = 1;
        	PreparedStatement pstmt = DB.prepareStatement(sqlStmt.toString(), null);
            pstmt.setInt(count, Env.getAD_Client_ID(Env.getCtx()));
            if (M_Requisition_ID > 0) {
            	count++;
            	pstmt.setInt(count, M_Requisition_ID);
            }
            
            if (M_Product_ID > 0) {
            	count++;
            	pstmt.setInt(count, M_Product_ID);
            }
  
            if (dateRequired != null) {
            	count++;
            	pstmt.setTimestamp(count, dateRequired);
            }
            
            if (C_Charge_ID > 0) {
            	count++;
            	pstmt.setInt(count, C_Charge_ID);
            }
                                 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {

				Vector<Object> line = new Vector<Object>(7);
                line.add(new Boolean(false));           //  0-Selection
                
                KeyNamePair lineKNPair = new KeyNamePair(rs.getInt(1), rs.getString(2)); // 1-Line
                line.add(lineKNPair);
                line.add(rs.getString(3)); //2-Product
                line.add(rs.getString(8)); //3-Charge                
                BigDecimal qty = rs.getBigDecimal(4); 
               // BigDecimal qtyRequired = rs.getBigDecimal(8); 
               /* MRequisitionLine reqLine = new MRequisitionLine(Env.getCtx(), rs.getInt(1), null);
                int C_UOM_To_ID = rs.getInt(9);
                if (C_UOM_To_ID > 0 && reqLine.getM_Product_ID() > 0 
                		&& reqLine.getC_UOM_ID()!=reqLine.getM_Product().getC_UOM_ID()) {
        			qtyRequired = MUOMConversion.convertProductTo (Env.getCtx(), reqLine.getM_Product_ID(), 
        					C_UOM_To_ID, qtyRequired);
        			if (qtyRequired == null)
        					qtyRequired = Env.ZERO;
                }*/
                line.add(qty);  // 4 - Qty
                Timestamp p_dateRequired = rs.getTimestamp(5);
                line.add(p_dateRequired); //5 - DateRequired
                line.add(rs.getString(6)); //6 - Project
                line.add(rs.getString(7)); // 7 - UOM
              //  line.add(qtyRequired);
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
	
	protected ArrayList<KeyNamePair> loadRequisitionData (int C_BPartner_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		int AD_Client_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "AD_Client_ID");
        
		//	Display
		StringBuilder display = new StringBuilder("r.DocumentNo");
			//.append(DB.TO_CHAR("r.TotaLines", DisplayType.Amount, Env.getAD_Language(Env.getCtx())));
		
		StringBuilder sql = new StringBuilder("SELECT DISTINCT r.M_Requisition_ID,").append(display)
			.append(" FROM M_Requisition r ")
			.append(" WHERE EXISTS (SELECT 1 FROM M_RequisitionLine l WHERE r.M_Requisition_ID=l.M_Requisition_ID")
			.append(" AND l.AD_Client_ID=? AND r.DocStatus=? AND l.qtyRequired > 0 )");
		
		sql = sql.append(" ORDER BY r.DocumentNo");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			int count = 0;
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(++count, AD_Client_ID);
			pstmt.setString(++count, DocAction.STATUS_Completed);
				        
			rs = pstmt.executeQuery();
			while (rs.next())
			 	list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
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

		return list;
	}   //  initBPartnerOIS
	
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, String.class, true);        //  1-Line
		miniTable.setColumnClass(2, String.class, true);        //  2-Product 
		miniTable.setColumnClass(3, String.class, true);        //  3-Charge
		miniTable.setColumnClass(4, BigDecimal.class, false);   //  4-Qty
		miniTable.setColumnClass(5, Timestamp.class, true);   	//  5-DateRequired
		miniTable.setColumnClass(6, String.class, true);   		//  6-Project
		miniTable.setColumnClass(7, String.class, true);        //  7-UOM
		//miniTable.setColumnClass(8, BigDecimal.class, true);   //  8-QtyRequired
		 
        //  Table UI
		miniTable.autoSize();
	}

	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		log.config("");
		int C_Order_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Order_ID");
		MOrder order = new MOrder(Env.getCtx(), C_Order_ID, null);
		
		for (int i = 0; i < miniTable.getRowCount(); i++)
        {
		   if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
            {
            	HBC_MOrderLine orderLine = new HBC_MOrderLine(order);
		    	BigDecimal qty = (BigDecimal)miniTable.getValueAt(i, 4); 	// 4 - Qty
		    	BigDecimal qtyOrdered = qty; // 8 - QtyRequired
		        KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 1);   //  1-Line
		         
		        int requisitionLineID = pp.getKey();
		        MRequisitionLine reqLine = new MRequisitionLine(Env.getCtx(), requisitionLineID, null);
		        
		        int C_UOM_To_ID = reqLine.getM_Product().getC_UOM_ID();
		        if (C_UOM_To_ID > 0 && reqLine.getM_Product_ID() > 0 
		        		&& reqLine.getC_UOM_ID()!=reqLine.getM_Product().getC_UOM_ID()) {
		        	qtyOrdered = MUOMConversion.convertProductTo (Env.getCtx(), reqLine.getM_Product_ID(), C_UOM_To_ID, qty);
		 			if (qtyOrdered == null)
		 				qtyOrdered = Env.ZERO;
		        }
                 
                 //@TommyAng (useless)
                 //int productID = reqLine.getM_Product_ID();
                 
                BigDecimal Qty = reqLine.getQty();
         		boolean IsSOTrx = order.isSOTrx();
         		boolean isNoPriceList = (boolean) order.get_Value("isNoPriceList");
         		//boolean isTrackAsAsset = (boolean) order.get_Value("isTrackAsAsset");
         		MProductPricing pricing = new MProductPricing (reqLine.getM_Product_ID(), order.getC_BPartner_ID(), Qty, IsSOTrx);
         		
         		//edited by TommyAng
         		//if(!isNoPriceList && !isTrackAsAsset){
         		if(!isNoPriceList){
	         		int M_PriceList_ID = order.getM_PriceList_ID();
	         		pricing.setM_PriceList_ID(M_PriceList_ID);
	         		
	         		String sqlString = "SELECT plv.M_PriceList_Version_ID "
	         				+ "FROM M_PriceList_Version plv "
	         				+ "WHERE plv.M_PriceList_ID=? "						//	1
	         				+ " AND plv.ValidFrom <= ? "
	         				+ "ORDER BY plv.ValidFrom DESC";
	         			//	Use newest price list - may not be future
	
	         		int M_PriceList_Version_ID = DB.getSQLValueEx(null, sqlString,order.getM_PriceList_ID(), order.getDateOrdered());
	         			
	         		pricing.setM_PriceList_Version_ID(M_PriceList_Version_ID);         		
	         		pricing.setPriceDate(order.getDateOrdered());
	         		
	         		//@TommyAng (useless)
		         	//orderLine.setPrice(pricing.getPriceStd());
	         		
		            orderLine.setPriceEntered(pricing.getPriceStd().subtract(pricing.getPriceStd().multiply(orderLine.getDiscount())));
		            orderLine.setPriceActual(pricing.getPriceStd());
		            orderLine.setPriceList(pricing.getPriceList());
		            orderLine.setPriceLimit(pricing.getPriceLimit());
		            
	                //orderLine.set_ValueOfColumn("PriceActual",reqLine.get_Value("PriceActual"));
	                orderLine.setLineNetAmt();
	                orderLine.setM_Product_ID(reqLine.getM_Product_ID());
         		}
         		else{
         			//@TommyAng (useless)
         			//orderLine.setPrice(Env.ONE);
		            orderLine.setPriceEntered(Env.ZERO);
		            orderLine.setPriceActual(Env.ZERO);
		            orderLine.setPriceList(Env.ZERO);
		            orderLine.setPriceLimit(Env.ZERO);
         		}
         		
         		if(reqLine.getC_BPartner_ID()>0)
                	orderLine.set_ValueOfColumn("C_BPartner_Requisition_ID",reqLine.getC_BPartner_ID());
         		orderLine.setM_Product_ID(reqLine.getM_Product_ID());
         		orderLine.setC_UOM_ID(reqLine.getC_UOM_ID());
                orderLine.setQtyEntered(qty);
                orderLine.setC_Tax_ID(order.getC_Tax_ID());
                orderLine.setQtyOrdered(qtyOrdered);
                orderLine.setLine(reqLine.getLine());
                orderLine.set_ValueOfColumn("PriceRequisition", reqLine.getPriceActual());
         		
                if(order.get_Value("HBC_Tugboat_ID")!=null){
                	orderLine.set_ValueOfColumn("HBC_Tugboat_ID", order.get_Value("HBC_Tugboat_ID"));
                }
                
                if(order.get_Value("HBC_Barge_ID")!=null){
                	orderLine.set_ValueOfColumn("HBC_Barge_ID", order.get_Value("HBC_Barge_ID"));
                }
                
                
                if(order.get_Value("HBC_Trip_ID")!=null){
                	orderLine.set_ValueOfColumn("HBC_Trip_ID", order.get_Value("HBC_Trip_ID"));
                }
                
                if(order.get_Value("C_Project_ID")!=null){
                	orderLine.set_ValueOfColumn("C_Project_ID", order.get_Value("C_Project_ID"));
                }
                orderLine.set_ValueOfColumn("DiscAmt", Env.ZERO);
                orderLine.set_ValueOfColumn("M_RequisitionLine_ID", reqLine.get_ID());
                orderLine.saveEx();
                
                MRequisition requisition = new MRequisition(Env.getCtx(),reqLine.getM_Requisition_ID(),trxName);
                if(requisition.get_Value("HBC_Tugboat_ID") != null)
                	order.set_ValueOfColumn("HBC_Tugboat_ID", requisition.get_Value("HBC_Tugboat_ID"));
                if(requisition.get_Value("HBC_Barge_ID") != null)
                	order.set_ValueOfColumn("HBC_Barge_ID", requisition.get_Value("HBC_Barge_ID"));
                order.saveEx();
                
                //TODO: Ask bella whether this requirement will be fulfilled
                /*
                MRequisition requisition = new MRequisition(Env.getCtx(),reqLine.getM_Requisition_ID(),trxName);
                order.setM_Warehouse_ID(requisition.getM_Warehouse_ID());
                order.saveEx();
                
                /*
                MMatchPR matchPR = new MMatchPR(Env.getCtx(),0,trxName);
                matchPR.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
                matchPR.setM_Requisition_ID(reqLine.getM_Requisition_ID());
                matchPR.setM_RequisitionLine_ID(reqLine.getM_RequisitionLine_ID());
                matchPR.setC_Order_ID(orderLine.getC_Order_ID());
                matchPR.setDateTrx(new Timestamp(System.currentTimeMillis()));
                matchPR.setQtyOrdered(orderLine.getQtyOrdered());
                matchPR.saveEx();
                */
                	
            }
        }
        return true;
	}

}
