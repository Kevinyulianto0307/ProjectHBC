package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.apps.IStatusBar;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.toba.habco.model.MCostActivity;
import org.toba.habco.model.MMatchCostActivity;
import org.toba.habco.model.X_HBC_CostActivity;
import org.toba.habco.model.X_T_ReferenceCode;

public class CreateFromCostActivity extends CreateFrom {

	public CreateFromCostActivity(GridTab gridTab) {
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
        Vector<String> columnNames = new Vector<String>(7);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add(Msg.translate(Env.getCtx(), "AllocationCode"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "C_Project_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "AD_User_ID"));
        columnNames.add(Msg.translate(Env.getCtx(), "OrderReference"));
        
	    return columnNames;
	}
	
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, Integer.class, true);        //  1-AllocationCode
		miniTable.setColumnClass(2, String.class, true);        //  2-Bussiness Partner
		miniTable.setColumnClass(3, String.class, true);        //  3-Project Code
		miniTable.setColumnClass(4, String.class, true);   //  4- User
		miniTable.setColumnClass(4, String.class, true);   //  5- Order Reference
		
        //  Table UI
		miniTable.autoSize();
	}

	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		for (int i = 0; i < miniTable.getRowCount(); i++)
        {
			int C_Invoice_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Invoice_ID");
            if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
            {
            	int refcode=(Integer)miniTable.getValueAt(i, 1);
            	if(getCostActivitiesID(trxName,refcode).length>0){
        			MInvoice invoice = new MInvoice(Env.getCtx(),C_Invoice_ID,trxName);
        			String orderReference = (String) miniTable.getValueAt(i, 5);
                	invoice.set_CustomColumn("OrderReference", orderReference);
                	invoice.saveEx();
        			int Line=0;
        			for(int id:getCostActivitiesID(trxName,refcode)){
        				X_HBC_CostActivity costactivity = new X_HBC_CostActivity(Env.getCtx(), id, trxName);
        				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
    					
        				/*
    					MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
    					if(product.get_Value("HBC_Percentage_Split_TugBoat")==null){
    						throw new AdempiereException("Please fill Percentage Tugboat!");
    					}*/
    					
    					BigDecimal percentagetugboat = Env.ZERO;
    					BigDecimal percentagebarge = Env.ZERO;
    					
    					//TODO @win: Ini yakin masih pake hard code percentage seperti ini?
    					/*
    					if(costactivity.getHBC_Tugboat_ID() > 0 && costactivity.getHBC_Barge_ID() > 0){
    						percentagetugboat = new BigDecimal(70);
    						percentagebarge = new BigDecimal(30);
    					}
    					else if(costactivity.getHBC_Tugboat_ID() > 0){
    						percentagetugboat = new BigDecimal(100);
    						percentagebarge = new BigDecimal(0);
    					}
    					else if(costactivity.getHBC_Barge_ID() > 0){
    						percentagetugboat = new BigDecimal(0);
    						percentagebarge = new BigDecimal(100);
    					}
    					*/
    					
    					MProduct product = new MProduct(costactivity.getCtx(), costactivity.getM_Product_ID(), trxName);
    					percentagetugboat = (BigDecimal) product.get_Value("HBC_Percentage_Split_TugBoat");
    					percentagebarge = (BigDecimal) product.get_Value("HBC_Percentage_Split_Barge");
    					
    					
    					BigDecimal totalamount = costactivity.getAmount();
    					//@KevinY
    					BigDecimal rate = (BigDecimal)costactivity.get_Value("conversionrate");
    					if (rate == null) 
    						rate = Env.ONE;
    					//@KevinY end
    					invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
    					invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
    					invoiceLine.setLine(Line+=10);
    					invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
    					/* commented by @Stephan
    					invline.setQtyEntered(Env.ONE);
    					invline.setQty(Env.ONE);
    					*/
    					invoiceLine.setQtyEntered((BigDecimal)costactivity.get_Value("Qty"));
    					invoiceLine.setQty((BigDecimal)costactivity.get_Value("Qty"));
    					invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
    					invoiceLine.setPriceActual(totalamount);
    					invoiceLine.set_ValueOfColumn("M_Product_Version_ID", costactivity.getM_Product_Version_ID());
    					invoiceLine.setDescription(costactivity.getDescription());
    					//@KevinY
    					invoiceLine.setPriceEntered(totalamount.multiply(rate));
    					//@KevinY end
    					invoiceLine.setPriceList(totalamount);
    					invoiceLine.set_ValueOfColumn("AllocationCode", costactivity.getAllocationCode());
    					if(costactivity.getHBC_ShipActivity_ID()>0)
    					invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
    							.getHBC_Position().getHBC_Trip_ID());
    					if(costactivity.getHBC_Tugboat_ID() > 0)
    						invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
    					if(costactivity.getHBC_Barge_ID() > 0)
    						invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
    					invoiceLine.set_ValueOfColumn("InvoiceReference", costactivity.get_Value("InvoiceReference"));
    					invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_TugBoat", percentagetugboat);
    					invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_Barge", percentagebarge);
    					//phie
    					invoiceLine.set_ValueOfColumn("isAgentInvoices", costactivity.get_ValueAsBoolean("isAgentInvoices"));
    					if(costactivity.get_ValueAsInt("AR_businessPartner_ID") > 0)
    						invoiceLine.set_ValueOfColumn("AR_businessPartner_ID", costactivity.get_ValueAsInt("AR_businessPartner_ID"));
    					//endPhie
    					invoiceLine.saveEx();
    					
    					costactivity.set_ValueOfColumn("Processed", true);
    					costactivity.saveEx();
    					
    					MMatchCostActivity MatchCost= new MMatchCostActivity(Env.getCtx(), 0, trxName);
    					MatchCost.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
    					MatchCost.setAllocationCode(costactivity.getAllocationCode());
    					MatchCost.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
    					MatchCost.setHBC_CostActivity_ID(costactivity.getHBC_CostActivity_ID());
    					MatchCost.setM_Product_ID(invoiceLine.getM_Product_ID());
    					MatchCost.setQty(invoiceLine.getQtyEntered());
    					if(costactivity.getHBC_Tugboat_ID()>0)
    						MatchCost.setHBC_Tugboat_ID(costactivity.getHBC_Tugboat_ID());
    					if(costactivity.getHBC_Barge_ID()>0)
    						MatchCost.setHBC_Barge_ID(costactivity.getHBC_Barge_ID());
    					MatchCost.setAmount(invoiceLine.getPriceActual());
    					MatchCost.saveEx();
        				
        				/*
        				if(costactivity.getHBC_Barge_ID()>0 && costactivity.getHBC_Tugboat_ID()>0)
        					setpercentage(costactivity.getHBC_Tugboat_ID(), costactivity.getHBC_Barge_ID(), id, invoice.getC_Invoice_ID(), Line,trxName);
        				else {
        					
        					MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
        					
        					MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
        					if(product.get_Value("HBC_Percentage_Split_TugBoat")==null){
        						throw new AdempiereException("Please fill Percentage Tugboat!");
        					}
        					
        					BigDecimal percentagetugboat = Env.ZERO;
        					BigDecimal percentagebarge = Env.ZERO;
        					
        					if(costactivity.getHBC_Tugboat_ID() > 0 && costactivity.getHBC_Barge_ID() > 0){
        						percentagetugboat = new BigDecimal(70);
        						percentagebarge = new BigDecimal(30);
        					}
        					else if(costactivity.getHBC_Tugboat_ID() > 0){
        						percentagetugboat = new BigDecimal(100);
        						percentagebarge = new BigDecimal(0);
        					}
        					else if(costactivity.getHBC_Barge_ID() > 0){
        						percentagetugboat = new BigDecimal(0);
        						percentagebarge = new BigDecimal(100);
        					}
        					
        					BigDecimal totalamount = costactivity.getAmount();
        					
        					invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
        					invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
        					invoiceLine.setLine(Line+=10);
        					invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
        					/* commented by @Stephan
        					invline.setQtyEntered(Env.ONE);
        					invline.setQty(Env.ONE);
        					
        					invoiceLine.setQtyEntered((BigDecimal)costactivity.get_Value("Qty"));
        					invoiceLine.setQty((BigDecimal)costactivity.get_Value("Qty"));
        					invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
        					invoiceLine.setPriceActual(totalamount);
        					invoiceLine.set_ValueOfColumn("M_Product_Version_ID", costactivity.getM_Product_Version_ID());
        					invoiceLine.setDescription(costactivity.getDescription());
        					invoiceLine.setPriceEntered(totalamount);
        					invoiceLine.setPriceList(totalamount);
        					invoiceLine.set_ValueOfColumn("AllocationCode", costactivity.getAllocationCode());
        					if(costactivity.getHBC_ShipActivity_ID()>0)
        					invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
        							.getHBC_Position().getHBC_Trip_ID());
        					invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
        					invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
        					invoiceLine.set_ValueOfColumn("InvoiceReference", costactivity.get_Value("InvoiceReference"));
        					invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_TugBoat", percentagetugboat);
        					invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_Barge", percentagebarge);
        					invoiceLine.saveEx();
        					
        					costactivity.set_ValueOfColumn("Processed", true);
        					costactivity.saveEx();
        					
        					MMatchCostActivity MatchCost= new MMatchCostActivity(Env.getCtx(), 0, trxName);
        					MatchCost.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
        					MatchCost.setAllocationCode(costactivity.getAllocationCode());
        					MatchCost.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
        					MatchCost.setHBC_CostActivity_ID(costactivity.getHBC_CostActivity_ID());
        					MatchCost.setM_Product_ID(invoiceLine.getM_Product_ID());
        					MatchCost.setQty(invoiceLine.getQtyEntered());
        					if(costactivity.getHBC_Tugboat_ID()>0)
        						MatchCost.setHBC_Tugboat_ID(costactivity.getHBC_Tugboat_ID());
        					if(costactivity.getHBC_Barge_ID()>0)
        						MatchCost.setHBC_Barge_ID(costactivity.getHBC_Barge_ID());
        					MatchCost.setAmount(invoiceLine.getPriceActual());
        					MatchCost.saveEx();
        				}
        				*/
        			}
        			if(!NotInvoicedAll(refcode, trxName))
        				setIsInvoice(trxName,refcode);
        		}
            }
        }
		return true;
	}
	
	/*
	 * check if all cost activity are invoiced or not
	 */
	protected boolean NotInvoicedAll(int AllocationCode,String trxName){
		 Boolean invoiced =  new Query(Env.getCtx(),MCostActivity.Table_Name," AllocationCode="+AllocationCode+" AND Processed='N'",trxName)
		 							.setOnlyActiveRecords(true)
		 							.setClient_ID()
		 							.match();
		return invoiced;
	}
	
	protected Vector<Vector<Object>> getReferenceCode(int C_BPartner_ID)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//int C_Invoice_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "C_Invoice_ID");
		
        StringBuilder sqlStmt = new StringBuilder();
        
        sqlStmt.append("SELECT DISTINCT(a.AllocationCode), a.C_BPartner_ID, d.Name as BPartnerName, " //1..3
		        		+ "a.createdby,e.name as UserName,COALESCE(a.C_Project_ID,0) AS ProjectID, COALESCE(f.value,'-') AS ProjectName, g.OrderReference " //4..8
		        		+ "FROM HBC_CostActivity a "
		        		+ "JOIN C_BPartner d on d.C_BPartner_ID=a.C_BPartner_ID "
		        		+ "JOIN AD_User e on e.AD_User_ID=a.CreatedBy "
		        		+ "JOIN T_ReferenceCode g on g.AllocationCode=a.AllocationCode "
		        		+ "LEFT JOIN C_Project f on f.C_Project_ID=a.C_Project_ID "
		        		+ "WHERE a.AllocationCode!=0 AND a.C_BPartner_ID=? AND g.isInvoiced='N' ");
		        	//	+ "AND NOT EXISTS (SELECT 1 FROM M_MatchCostActivity mca WHERE mca.HBC_CostActivity_ID=a.HBC_CostActivity_ID)");
        				//+ "JOIN T_ReferenceCode g on g.AllocationCode=a.AllocationCode "

        try
        {
        	int count = 0;
        	PreparedStatement pstmt = DB.prepareStatement(sqlStmt.toString(), null);
            pstmt.setInt(++count, C_BPartner_ID);                               
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
				Vector<Object> line = new Vector<Object>(8);
                line.add(new Boolean(false));           //  0-Selection
                line.add(rs.getInt(1)); // ReferenceCode
                line.add(rs.getString(3));
                line.add(rs.getString(7));
                line.add(rs.getString(5));
                line.add(rs.getString(8));
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
	
	/*
	protected int setpercentage(int HBC_Tugboat_ID, int HBC_Barge_ID,int HBC_CostActivity_ID,int C_Invoice_ID,int Line,String trxName){
		MInvoice invoice = new MInvoice(Env.getCtx(),C_Invoice_ID,trxName);
		X_HBC_CostActivity costactivity = new X_HBC_CostActivity(Env.getCtx(), HBC_CostActivity_ID, trxName);
		
		MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
		
		/*
		MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
		if(product.get_Value("HBC_Percentage_Split_TugBoat")==null){
			throw new AdempiereException("Please fill Percentage Tugboat!");
		}
		
		BigDecimal percentagetugboat = Env.ZERO;
		BigDecimal percentagebarge = Env.ZERO;
		
		if(costactivity.getHBC_Tugboat_ID() > 0 && costactivity.getHBC_Barge_ID() > 0){
			percentagetugboat = new BigDecimal(70);
			percentagebarge = new BigDecimal(30);
		}
		else if(costactivity.getHBC_Tugboat_ID() > 0){
			percentagetugboat = new BigDecimal(100);
			percentagebarge = new BigDecimal(0);
		}
		else if(costactivity.getHBC_Barge_ID() > 0){
			percentagetugboat = new BigDecimal(0);
			percentagebarge = new BigDecimal(100);
		}
		
		BigDecimal totalamount = costactivity.getAmount();
		
		invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
		invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
		invoiceLine.setLine(Line+=10);
		invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
		/* commented by @Stephan
		invline.setQtyEntered(Env.ONE);
		invline.setQty(Env.ONE);
		
		invoiceLine.setQtyEntered((BigDecimal)costactivity.get_Value("Qty"));
		invoiceLine.setQty((BigDecimal)costactivity.get_Value("Qty"));
		invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
		invoiceLine.setPriceActual(totalamount);
		invoiceLine.set_ValueOfColumn("M_Product_Version_ID", costactivity.getM_Product_Version_ID());
		invoiceLine.setDescription(costactivity.getDescription());
		invoiceLine.setPriceEntered(totalamount);
		invoiceLine.setPriceList(totalamount);
		invoiceLine.set_ValueOfColumn("AllocationCode", costactivity.getAllocationCode());
		if(costactivity.getHBC_ShipActivity_ID()>0)
		invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
				.getHBC_Position().getHBC_Trip_ID());
		invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
		invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
		invoiceLine.set_ValueOfColumn("InvoiceReference", costactivity.get_Value("InvoiceReference"));
		invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_TugBoat", percentagetugboat);
		invoiceLine.set_ValueOfColumn("HBC_Percentage_Split_Barge", percentagebarge);
		invoiceLine.saveEx();
		
		costactivity.set_ValueOfColumn("Processed", true);
		costactivity.saveEx();
		
		MMatchCostActivity MatchCost= new MMatchCostActivity(Env.getCtx(), 0, trxName);
		MatchCost.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
		MatchCost.setAllocationCode(costactivity.getAllocationCode());
		MatchCost.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
		MatchCost.setHBC_CostActivity_ID(costactivity.getHBC_CostActivity_ID());
		MatchCost.setM_Product_ID(invoiceLine.getM_Product_ID());
		MatchCost.setQty(invoiceLine.getQtyEntered());
		if(costactivity.getHBC_Tugboat_ID()>0)
			MatchCost.setHBC_Tugboat_ID(costactivity.getHBC_Tugboat_ID());
		if(costactivity.getHBC_Barge_ID()>0)
			MatchCost.setHBC_Barge_ID(costactivity.getHBC_Barge_ID());
		MatchCost.setAmount(invoiceLine.getPriceActual());
		MatchCost.saveEx();
		
		/*
		for(int i =0;i<2;i++){
			if(i==0){
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
				if(product.get_Value("HBC_Percentage_Split_TugBoat")==null){
					throw new AdempiereException("Please fill Percentage Tugboat!");
				}
				BigDecimal percentagetugboat = new BigDecimal(product.get_ValueAsString("HBC_Percentage_Split_TugBoat"));
				BigDecimal totalamount = costactivity.getAmount().multiply(percentagetugboat);
				invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.setLine(Line+=10);
				invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.set_ValueOfColumn("M_Product_Version_ID", costactivity.getM_Product_Version_ID());
				invoiceLine.setDescription(costactivity.getDescription());
				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.set_ValueOfColumn("AllocationCode", costactivity.getAllocationCode());
				if(costactivity.getHBC_ShipActivity_ID()>0)
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				invoiceLine.set_ValueOfColumn("HBC_TugBoat_ID", costactivity.getHBC_Tugboat_ID());
				invoiceLine.set_ValueOfColumn("InvoiceReference", costactivity.get_Value("InvoiceReference"));
				invoiceLine.saveEx();
				

				costactivity.set_ValueOfColumn("Processed", true);
				costactivity.saveEx();
				
				MMatchCostActivity MatchCost= new MMatchCostActivity(Env.getCtx(), 0, trxName);
				MatchCost.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
				MatchCost.setAllocationCode(costactivity.getAllocationCode());
				MatchCost.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
				MatchCost.setHBC_CostActivity_ID(costactivity.getHBC_CostActivity_ID());
				MatchCost.setM_Product_ID(invoiceLine.getM_Product_ID());
				MatchCost.setQty(invoiceLine.getQtyEntered());
				if(costactivity.getHBC_Tugboat_ID()>0)
					MatchCost.setHBC_Tugboat_ID(costactivity.getHBC_Tugboat_ID());
				MatchCost.setAmount(invoiceLine.getPriceActual());
				MatchCost.saveEx();
				
			}else if(i==1){
				MProduct product= new MProduct(Env.getCtx(),costactivity.getM_Product_ID(),trxName);
				if(product.get_Value("HBC_Percentage_Split_Barge")==null){
					throw new AdempiereException("Please fill Percentage Barge!");
				}
				BigDecimal percentagebarge = new BigDecimal(product.get_ValueAsString("HBC_Percentage_Split_Barge"));
				BigDecimal totalamount = costactivity.getAmount().multiply(percentagebarge);
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setAD_Org_ID(invoice.getAD_Org_ID());
				invoiceLine.setC_Tax_ID(invoice.getC_Tax_ID());
				invoiceLine.setLine(Line+=10);
				invoiceLine.set_ValueOfColumn("M_Product_Version_ID", costactivity.getM_Product_Version_ID());
				invoiceLine.setDescription(costactivity.getDescription());
				invoiceLine.setC_Project_ID(costactivity.getHBC_Position().getC_Project_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setM_Product_ID(costactivity.getM_Product_ID());
				invoiceLine.set_ValueOfColumn("AllocationCode", costactivity.getAllocationCode());
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setPriceActual(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceEntered(totalamount.divide(Env.ONEHUNDRED));
				invoiceLine.setPriceList(totalamount.divide(Env.ONEHUNDRED));
				if(costactivity.getHBC_ShipActivity_ID()>0)
				invoiceLine.set_ValueOfColumn("HBC_Trip_ID", costactivity.getHBC_ShipActivity()
						.getHBC_Position().getHBC_Trip_ID());
				invoiceLine.set_ValueOfColumn("HBC_Barge_ID", costactivity.getHBC_Barge_ID());
				invoiceLine.set_ValueOfColumn("InvoiceReference", costactivity.get_Value("InvoiceReference"));
				invoiceLine.saveEx();
				
				MMatchCostActivity MatchCost= new MMatchCostActivity(Env.getCtx(), 0, trxName);
				MatchCost.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
				MatchCost.setAllocationCode(costactivity.getAllocationCode());
				MatchCost.setM_Product_ID(invoiceLine.getM_Product_ID());
				MatchCost.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
				MatchCost.setHBC_CostActivity_ID(costactivity.getHBC_CostActivity_ID());
				MatchCost.setQty(invoiceLine.getQtyEntered());
				if(costactivity.getHBC_Barge_ID()>0)
					MatchCost.setHBC_Barge_ID(costactivity.getHBC_Barge_ID());
				MatchCost.setAmount(invoiceLine.getPriceActual());
				MatchCost.saveEx();
			}
		}
		
		return 0;
	}*/

	
	protected void setIsInvoice(String trxName,int p_ReferenceCode){
		int refcode = new Query(Env.getCtx(),X_T_ReferenceCode.Table_Name,"AllocationCode="+p_ReferenceCode,trxName).firstId();
		X_T_ReferenceCode referencecode = new X_T_ReferenceCode(Env.getCtx(), refcode, trxName);
		referencecode.set_ValueOfColumn("IsInvoiced", true);
		referencecode.saveEx();
	}
	
	protected int[] getCostActivitiesID(String trxName,int p_ReferenceCode){
		
		String where= "AllocationCode=" +p_ReferenceCode +
				" AND NOT EXISTS (SELECT 1 FROM M_MatchCostActivity mca WHERE mca.HBC_CostActivity_ID=HBC_CostActivity.HBC_CostActivity_ID)";
		int[] HBC_CostActivity_IDs = new Query(Env.getCtx(), X_HBC_CostActivity.Table_Name, where, trxName)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.getIDs();
		
		return HBC_CostActivity_IDs;
	}
}
