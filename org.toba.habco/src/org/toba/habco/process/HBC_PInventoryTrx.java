package org.toba.habco.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MCharge;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author stephan
 * inventory transaction report with param date and locator
 * @created Phie Albert
 * Custom Process add new column product category and charge
 */
public class HBC_PInventoryTrx extends SvrProcess{

	private Timestamp p_movementDate = null;
	private Timestamp p_movementDateTo = null;
	private int p_M_Locator_ID = 0;
	private int[] product_ids = null;
	private int p_M_Product_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("MovementDate")){
				p_movementDate = para[i].getParameterAsTimestamp();
				p_movementDateTo = para[i].getParameter_ToAsTimestamp();
			}
			else if (name.equals("M_Locator_ID"))
				p_M_Locator_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		if(p_movementDateTo == null)
			p_movementDateTo = new Timestamp(System.currentTimeMillis());
		cleanTable();
		if(p_M_Product_ID > 0)
			createProductLine();
		else
			createProductLines();
		
		//return "P_Instance_ID = "+getAD_PInstance_ID();
		return "";
	}

	//single product
	private void createProductLine(){
		
		//List<Integer> locatorIDs = new ArrayList<Integer>();
		/*
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT(M_Locator_ID) FROM M_Transaction WHERE M_Product_ID=? AND AD_Client_ID=?"
				+ "AND MovementDate BETWEEN '"+p_movementDate+"' AND '"+p_movementDateTo+"'");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, p_M_Product_ID);
			pstmt.setInt(2, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next()){
				locatorIDs.add(rs.getInt(1));
			}
		}catch(Exception e){
			log.severe("TaoPInventoryTrx: "+e.toString());
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		*/
		
		//@phie
		String p_categoryName = "";
		String pc_charge = "";
		MProduct p = new MProduct(getCtx(), p_M_Product_ID, get_TrxName());
		if(p.getM_Product_Category_ID()>0)
		{
			MProductCategory mpc = new MProductCategory(getCtx(), p.getM_Product_Category_ID(), get_TrxName());
			p_categoryName = mpc.getName();
			if(mpc.get_ValueAsInt("C_Charge_ID") > 0)
			{
				MCharge charge = new MCharge(getCtx(), mpc.get_ValueAsInt("C_Charge_ID") , get_TrxName());
				pc_charge = charge.getName();
			}
		}
		//end phie
		
		String whereClause = "AD_Client_ID = ? AND isActive='Y'";
		int[] locatorIDs = new Query(getCtx(), MLocator.Table_Name, whereClause, get_TrxName())
							.setParameters(new Object[]{getAD_Client_ID()})
							.getIDs();
		
		if(p_M_Locator_ID <= 0){
			for (Integer locatorID : locatorIDs) {
				BigDecimal firstQty = getQtyFirst(p_M_Product_ID, locatorID);
				BigDecimal qtyIn = getQtyIn(p_M_Product_ID, locatorID);
				BigDecimal qtyOut = getQtyOut(p_M_Product_ID, locatorID);
				BigDecimal lastQty = firstQty.add(qtyIn).add(qtyOut);
				
				if(firstQty.compareTo(Env.ZERO)==0 && qtyIn.compareTo(Env.ZERO)==0 
						&& qtyOut.compareTo(Env.ZERO)==0)
					continue;
				
				if(lastQty == null)
					lastQty = Env.ZERO;
				
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO T_PInventoryTrx (AD_PInstance_ID, AD_Client_ID, AD_Org_ID, M_Locator_ID ,M_Product_ID, "
						+ "FirstQty, QtyIn, QtyOut, LastQty, MovementDate, ProductCategoryName, Charge) ")
				.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				
				Object param[] = null;
				
				param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
						Env.getAD_Org_ID(getCtx()), locatorID ,p_M_Product_ID ,firstQty, qtyIn,
						qtyOut, lastQty, p_movementDate, p_categoryName, pc_charge}; //add pc_name and charge
					
				int no = DB.executeUpdate(sb.toString(), param, true, get_TrxName());
				log.fine("#" + no);
			}
		}
		else{
			BigDecimal firstQty = getQtyFirst(p_M_Product_ID,0);
			BigDecimal qtyIn = getQtyIn(p_M_Product_ID,0);
			BigDecimal qtyOut = getQtyOut(p_M_Product_ID,0);
			BigDecimal lastQty = firstQty.add(qtyIn).add(qtyOut);
			if(lastQty == null)
				lastQty = Env.ZERO;
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO T_PInventoryTrx (AD_PInstance_ID, AD_Client_ID, AD_Org_ID, M_Locator_ID ,M_Product_ID, "
					+ "FirstQty, QtyIn, QtyOut, LastQty, MovementDate, ProductCategoryName, Charge) ")
			.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			
			Object param[] = null;
			
			if(p_M_Locator_ID > 0)
				param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
					Env.getAD_Org_ID(getCtx()), p_M_Locator_ID ,p_M_Product_ID ,firstQty, qtyIn,
					qtyOut, lastQty, p_movementDate, p_categoryName, pc_charge}; //add pc_name and charge
			else
				param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
					Env.getAD_Org_ID(getCtx()), null ,p_M_Product_ID, firstQty, qtyIn,
					qtyOut, lastQty, p_movementDate, p_categoryName, pc_charge}; //add pc_name and charge
				
			int no = DB.executeUpdate(sb.toString(), param, true, get_TrxName());
			log.fine("#" + no);
		}
	}
	
	//all product
	private void createProductLines(){
		product_ids= new Query(getCtx(), MProduct.Table_Name, null, get_TrxName())
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.getIDs();
		
		for (int i : product_ids) {
			BigDecimal firstQty = getQtyFirst(i,0);
			BigDecimal qtyIn = getQtyIn(i,0);
			BigDecimal qtyOut = getQtyOut(i,0);
			BigDecimal lastQty = firstQty.add(qtyIn).add(qtyOut);
			
			if(firstQty.compareTo(Env.ZERO)==0 && qtyIn.compareTo(Env.ZERO)==0 
					&& qtyOut.compareTo(Env.ZERO)==0)
				continue;
			
			if(lastQty == null)
				lastQty = Env.ZERO;
			
			//@phie
			String p_categoryName = "";
			String pc_charge = "";
			MProduct p = new MProduct(getCtx(), i, get_TrxName());
			if(p.getM_Product_Category_ID()>0)
			{
				MProductCategory mpc = new MProductCategory(getCtx(), p.getM_Product_Category_ID(), get_TrxName());
				p_categoryName = mpc.getName();
				if(mpc.get_ValueAsInt("C_Charge_ID") > 0)
				{
					MCharge charge = new MCharge(getCtx(), mpc.get_ValueAsInt("C_Charge_ID") , get_TrxName());
					pc_charge = charge.getName();
				}
			}
			//end phie
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO T_PInventoryTrx (AD_PInstance_ID, AD_Client_ID, AD_Org_ID, M_Locator_ID ,M_Product_ID, "
					+ "FirstQty, QtyIn, QtyOut, LastQty, MovementDate, ProductCategoryName, Charge) ")
			.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			
			Object param[] = null;
			
			if(p_M_Locator_ID > 0)
				param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
					Env.getAD_Org_ID(getCtx()), p_M_Locator_ID ,i ,firstQty, qtyIn,
					qtyOut, lastQty, p_movementDate, p_categoryName, pc_charge}; //add pc_name and charge
			else
				param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
					Env.getAD_Org_ID(getCtx()), null ,i ,firstQty, qtyIn,
					qtyOut, lastQty, p_movementDate, p_categoryName, pc_charge}; //add pc_name and charge
				
			int no = DB.executeUpdate(sb.toString(), param, true, get_TrxName());
			log.fine("#" + no);
		}
	}
	
	private BigDecimal getQtyFirst(int i, int locator) {
		if(locator <= 0)
			locator = p_M_Locator_ID;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(MovementQty) FROM M_Transaction "
				+ "WHERE MovementDate < '" + p_movementDate + "' "
				+ "AND M_Product_ID=? ");
		if(locator > 0)
			sql.append("AND M_Locator_ID=? ");
		
		BigDecimal sumQty = new BigDecimal(0);
		
		if(locator > 0)
			sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i,locator});
		else
			sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i});
		
		if(sumQty == null)
			return Env.ZERO;
		
		return sumQty;
	}
	
	private BigDecimal getQtyIn(int i, int locator){
		if(locator <= 0)
			locator = p_M_Locator_ID;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(MovementQty) FROM M_Transaction "
				+ "WHERE MovementDate BETWEEN '" + p_movementDate + "' AND '"+p_movementDateTo+"' "
				+ "AND M_Product_ID=? AND MovementType LIKE '%+' ");
		if(locator > 0)
			sql.append("AND M_Locator_ID=? ");
			
		BigDecimal sumQty = new BigDecimal(0);
		
		if(locator > 0)
			sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i,locator});
		else
			sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i});
		
		if(sumQty == null)
			return Env.ZERO;
		
		return sumQty;
	}
	
	private BigDecimal getQtyOut(int i, int locator){
		if(locator <= 0)
			locator = p_M_Locator_ID;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(MovementQty) FROM M_Transaction "
				+ "WHERE MovementDate BETWEEN '" + p_movementDate + "' AND '"+p_movementDateTo+"' "
				+ "AND M_Product_ID=? AND MovementType LIKE '%-' ");
		if(locator > 0)
			sql.append("AND M_Locator_ID=? ");
		
		BigDecimal sumQty = new BigDecimal(0);
		
		if(locator > 0)
			sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i,locator});
		else
			sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i});
		
		if(sumQty == null)
			return Env.ZERO;
		
		return sumQty;
	}
	
	private void cleanTable(){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM T_PInventoryTrx");
		DB.executeUpdate(sql.toString(), get_TrxName());
	}
	
}
