package org.toba.habco.process;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Phie Albert
 * Copy from FBI by edwin handy (with minor custom)
 * this class used for Period Summary OnHand Acct
 */

public class HBC_PeriodOnHandAcct extends SvrProcess {

	
	private Timestamp p_movementDate = null;
	private Timestamp p_movementDateTo = null;
	private int p_M_Locator_ID = 0;
	private int p_AD_Org_ID = 0;
	private int[] product_ids = null;
	private int[] locator_ids = null;
	private int p_M_Product_ID = 0;
	private int p_M_ProductCategory_ID = 0;
	private int p_M_PriceList_Version_ID = 0;
	
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
			else if (name.equals("M_Product_Category_ID"))
				p_M_ProductCategory_ID = para[i].getParameterAsInt();
			else if (name.equals("M_PriceList_Version_ID"))
				p_M_PriceList_Version_ID= para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID= para[i].getParameterAsInt();
			else	
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		cleanTable();
		if(p_M_Product_ID > 0)
			createProductLine();
		else
			createProductLines();
		return "";
	
	}
	
	//single product
		private void createProductLine(){ 
			if (!(p_AD_Org_ID == 0)){
				String whereclouse2="M_locator_ID  in (select M_locator_ID from M_Transaction where M_Product_ID = " + p_M_Product_ID+ " and M_Locator_ID in (select M_Locator_ID from m_locator where AD_org_ID ="+ p_AD_Org_ID+"))";
				locator_ids= new Query(getCtx(), MLocator.Table_Name, whereclouse2, get_TrxName())
				.getIDs();
			}
			else{
				String whereclouse2="M_locator_ID  in (select M_locator_ID from M_Transaction where M_Product_ID = " + p_M_Product_ID+")";
				locator_ids= new Query(getCtx(), MLocator.Table_Name, whereclouse2, get_TrxName())
				.getIDs();
			}
			
			for(int i : locator_ids){
				
					BigDecimal firstQty = getQtyFirst(p_M_Product_ID,i);
					BigDecimal qtyIn = getQtyIn(p_M_Product_ID,i);
					BigDecimal qtyOut = getQtyOut(p_M_Product_ID,i);
					BigDecimal lastQty = firstQty.add(qtyIn).add(qtyOut);
					
					MProduct product = new MProduct(getCtx(), p_M_Product_ID, get_TrxName());
					Integer org=0;
					if(p_AD_Org_ID==0)
						org = getOrg(i);
					String warehouse= getWarehouse(i);	
					String locator =getLocator(i);
					String uom=getUom(p_M_Product_ID);
					String category=getCategory(p_M_Product_ID);
					String value = getValue(p_M_Product_ID);
					String name = getName(p_M_Product_ID);
					String warehousezone =getZone(i);
					Timestamp LastPurchaseDate =  getLastPurchaseDate(p_M_Product_ID);
					BigDecimal LastPurchasePrice = getLastPurchasePrice(p_M_Product_ID);
					BigDecimal LastCost = getLastCost(p_M_Product_ID);
					if (LastCost == null)
						LastCost = Env.ZERO;
					BigDecimal SalesPrice = getSalesPrice(p_M_Product_ID, p_M_PriceList_Version_ID);
					if(lastQty == null)
						lastQty = Env.ZERO;
					BigDecimal ProductValue = lastQty.multiply(LastCost);
		
					StringBuilder sb = new StringBuilder();
					sb.append("INSERT INTO T_PInventoryTrxAcct (AD_PInstance_ID, AD_Client_ID, AD_Org_ID ,M_Product_ID,M_Product_Category_ID,Name,Value,productcategory,Warehousename ,warehousezone, locator_name,UOMname , " //FBI-2263 fix table name to Acct @Febrian
							+ "FirstQty, QtyIn, QtyOut, LastQty, MovementDate,DateInvoiced,CurrentCostPrice,PriceEntered,ProductValueAmt,priceStd) ")
					.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					
					Object param[] = null;
					if(p_AD_Org_ID==0){
						param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
							org,p_M_Product_ID,product.getM_Product_Category_ID(),name,value,category,warehouse,warehousezone,locator,uom, firstQty, qtyIn,
							qtyOut, lastQty, p_movementDate,LastPurchaseDate,LastCost,LastPurchasePrice,ProductValue,SalesPrice};
					}
					else{
						param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
							p_AD_Org_ID,p_M_Product_ID,name,value,category,warehouse,warehousezone,locator,uom, firstQty, qtyIn,
							qtyOut, lastQty, p_movementDate,LastPurchaseDate,LastCost,LastPurchasePrice,ProductValue,SalesPrice};
					}
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
				if(!(p_AD_Org_ID == 0)){
					String whereclouse2="M_locator_ID  in (select M_locator_ID from M_Transaction where M_Product_ID = " + i+ " and M_Locator_ID in (select M_Locator_ID from m_locator where AD_org_ID ="+ p_AD_Org_ID+"))";
					locator_ids= new Query(getCtx(), MLocator.Table_Name, whereclouse2, get_TrxName())
					.getIDs();
				}
				else{
					String whereclouse2="M_locator_ID  in (select M_locator_ID from M_Transaction where M_Product_ID = " + i+ ")";
					locator_ids= new Query(getCtx(), MLocator.Table_Name, whereclouse2, get_TrxName())
					.getIDs();
					
				} 
				
					for(int j:locator_ids){
				
						BigDecimal firstQty = getQtyFirst(i,j);
						BigDecimal qtyIn = getQtyIn(i,j);
						BigDecimal qtyOut = getQtyOut(i,j);
						BigDecimal lastQty = firstQty.add(qtyIn).add(qtyOut);
						MProduct product = new MProduct(getCtx(), i, get_TrxName());
						Integer org=0;
						if(p_AD_Org_ID==0)
							org = getOrg(j);
						String warehouse= getWarehouse(j);
						String locator =getLocator(j);
						String uom=getUom(i);
						String category=getCategory(i);
						//String size = getSize(i);
						String value = getValue(i);
						String name = getName(i);
						String warehousezone =getZone(j);
						Timestamp LastPurchaseDate =  getLastPurchaseDate(i);
						BigDecimal LastPurchasePrice = getLastPurchasePrice(i);
						BigDecimal LastCost = getLastCost(i);
						if (LastCost == null)
							LastCost = Env.ZERO;
						BigDecimal SalesPrice = getSalesPrice(i, p_M_PriceList_Version_ID);
						if(lastQty == null)
							lastQty = Env.ZERO;
						BigDecimal ProductValue = lastQty.multiply(LastCost);
						StringBuilder sb = new StringBuilder();
						sb.append("INSERT INTO T_PInventoryTrxAcct (AD_PInstance_ID, AD_Client_ID, AD_Org_ID,M_Product_ID, M_Product_Category_ID,Name,Value,productcategory,warehousename,warehousezone, Locator_name ,UOMname, " //FBI-2263 fix table name to Acct @Febrian
								+ "FirstQty, QtyIn, QtyOut, LastQty, MovementDate,DateInvoiced,CurrentCostPrice,PriceEntered,ProductValueAmt,priceStd) ")
						.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						
						Object param[] = null;
						if(p_AD_Org_ID ==0){
							param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
								org,i,product.getM_Product_Category_ID() ,name,value,category,warehouse,warehousezone,locator,uom,firstQty, qtyIn,
								qtyOut, lastQty, p_movementDate,LastPurchaseDate,LastCost,LastPurchasePrice,ProductValue,SalesPrice};
						}
						else{
							param = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), 
									p_AD_Org_ID,i,product.getM_Product_Category_ID() ,name,value,category,warehouse,warehousezone,locator,uom,firstQty, qtyIn,
									qtyOut, lastQty, p_movementDate,LastPurchaseDate,LastCost,LastPurchasePrice,ProductValue,SalesPrice};
						}
						int no = DB.executeUpdate(sb.toString(), param, true, get_TrxName());
						log.fine("#" + no);
					}
				}
			}
		
		private BigDecimal getQtyFirst(int i,int j) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(MovementQty) FROM M_Transaction "
					+ "WHERE MovementDate < '" + p_movementDate + "' "
					+ "AND M_Product_ID=? ");
			if(j > 0)
				sql.append(" AND M_Locator_ID=? ");
			
			BigDecimal sumQty = new BigDecimal(0);
			
			if(j > 0)
				sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i,j});
			else
				sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i});
			
			if(sumQty == null)
				return Env.ZERO;
			
			return sumQty;
		}
		
		private BigDecimal getQtyIn(int i,int j){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(MovementQty) FROM M_Transaction "
					+ "WHERE MovementDate BETWEEN '" + p_movementDate + "' AND '"+p_movementDateTo+"' "
					+ "AND M_Product_ID=? AND MovementType LIKE '%+' ");
			if(j > 0)
				sql.append("AND M_Locator_ID=? ");
				
			BigDecimal sumQty = new BigDecimal(0);
			
			if(j > 0)
				sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i,j});
			else
				sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i});
			
			if(sumQty == null)
				return Env.ZERO;
			
			return sumQty;
		}
		
		private BigDecimal getQtyOut(int i,int j){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SUM(MovementQty) FROM M_Transaction "
					+ "WHERE MovementDate BETWEEN '" + p_movementDate + "' AND '"+p_movementDateTo+"' "
					+ "AND M_Product_ID=? AND MovementType LIKE '%-' ");
			if(j> 0)
				sql.append("AND M_Locator_ID=? ");
			
			BigDecimal sumQty = new BigDecimal(0);
			
			if(j > 0)
				sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i,j});
			else
				sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{i});
			
			if(sumQty == null)
				return Env.ZERO;
			
			return sumQty;
		}
		
		private Timestamp getLastPurchaseDate(int i){
			Timestamp LastPurchaseDate = null;
			StringBuilder sqlOnDate = new StringBuilder();
			StringBuilder sqlOutDate = new StringBuilder();
			StringBuilder whereClauseOndate = new StringBuilder();
			StringBuilder whereClauseOutdate = new StringBuilder();
			
			whereClauseOndate.append(" AND ci.DateInvoiced BETWEEN "+ "'" + p_movementDate+ "'"  + " AND " + "'"+ p_movementDateTo+ "'" + " ");
			whereClauseOutdate.append(" AND ci.DateInvoiced < "+"'"+ p_movementDate +"'"+" ");

			
			sqlOnDate.append(" SELECT ci.DateInvoiced FROM C_Invoice ci "
					+ " INNER JOIN C_InvoiceLine cil ON cil.c_invoice_id = ci.c_invoice_id "
					+ " WHERE cil.M_Product_ID = ? "
					+ " AND ci.isSotrx = 'N' "
					+ " AND ci.Docstatus = 'CO' "
					+ " "+whereClauseOndate+" "
					+ " ORDER BY ci.DateInvoiced Desc");
			
			LastPurchaseDate = DB.getSQLValueTSEx(get_TrxName(), sqlOnDate.toString(), new Object[]{i});
			
			if (LastPurchaseDate == null){
				
				sqlOutDate.append(" SELECT ci.DateInvoiced FROM C_Invoice ci "
						+ " INNER JOIN C_InvoiceLine cil ON cil.c_invoice_id = ci.c_invoice_id "
						+ " WHERE cil.M_Product_ID = ? "
						+ " AND ci.isSotrx = 'N' "
						+ " AND ci.Docstatus = 'CO' "
						+ " "+whereClauseOutdate+" "
						+ " ORDER BY ci.DateInvoiced Desc");
				
			LastPurchaseDate = DB.getSQLValueTSEx(get_TrxName(), sqlOutDate.toString(), new Object[]{i});
				
			}
			

			return LastPurchaseDate;
		}
		private BigDecimal getLastPurchasePrice (int i){
			BigDecimal LastPurchasePrice = new BigDecimal (0);
			StringBuilder sqlOnDate = new StringBuilder();
			StringBuilder sqlOutDate = new StringBuilder();
			StringBuilder whereClauseOndate = new StringBuilder();
			StringBuilder whereClauseOutdate = new StringBuilder();
			
			whereClauseOndate.append(" AND ci.DateInvoiced BETWEEN "+ "'" + p_movementDate+ "'"  + " AND " + "'"+ p_movementDateTo+ "'" + " ");
			whereClauseOutdate.append(" AND ci.DateInvoiced < "+"'"+ p_movementDate +"'"+" ");
			
			sqlOnDate.append(" SELECT cil.priceentered "
					+ " FROM C_Invoice ci "
					+ " INNER JOIN C_InvoiceLine cil ON cil.c_invoice_id = ci.c_invoice_id "
					+ " WHERE cil.M_Product_ID = ? "
					+ " AND ci.isSotrx = 'N' "
					+ " AND ci.Docstatus = 'CO' "
					+ " "+whereClauseOndate+" "
					+ " ORDER BY ci.DateInvoiced Desc");
			
			LastPurchasePrice = DB.getSQLValueBDEx(get_TrxName(), sqlOnDate.toString(), new Object[]{i});
			
			if (LastPurchasePrice == null){
				
				sqlOutDate.append(" SELECT cil.priceentered "
						+ " FROM C_Invoice ci "
						+ " INNER JOIN C_InvoiceLine cil ON cil.c_invoice_id = ci.c_invoice_id "
						+ " WHERE cil.M_Product_ID = ? "
						+ " AND ci.isSotrx = 'N' "
						+ " AND ci.Docstatus = 'CO' "
						+ " "+whereClauseOutdate+" "
						+ " ORDER BY ci.DateInvoiced Desc");
				
			LastPurchasePrice = DB.getSQLValueBDEx(get_TrxName(), sqlOutDate.toString(), new Object[]{i});
			}
			
			return LastPurchasePrice;
			
		}
		
		private BigDecimal getLastCost(int i){
			BigDecimal LastCost = new BigDecimal(0);
			
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlOutDate = new StringBuilder();
			StringBuilder whereClauseOndate = new StringBuilder();
			StringBuilder whereClauseOutdate = new StringBuilder();
			
			whereClauseOndate.append(" AND created BETWEEN "+ "'" + p_movementDate+ "'"  + " AND " + "'"+ p_movementDateTo+ "'" +"::date + interval '1 day'"+" ");
			whereClauseOutdate.append(" AND created < "+"'"+ p_movementDate +"'"+" ");
			
			sql.append("SELECT currentCostPrice "
					+ "	FROM M_CostDetail "
					+ " WHERE m_product_id = ? "
					+ " "+whereClauseOndate+" "
					+ " ORDER By created desc");
			
			LastCost = DB.getSQLValueBDEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			if (LastCost == null){
				
				sqlOutDate.append("SELECT currentCostPrice "
						+ "	FROM M_CostDetail mcd "
						+ " WHERE m_product_id = ? "
						+ " "+whereClauseOutdate+" "
						+ " ORDER By created desc");
				
				LastCost = DB.getSQLValueBDEx(get_TrxName(), sqlOutDate.toString(), new Object[]{i});

			}
			
			return LastCost;
		}
		private BigDecimal getSalesPrice (int i,int p){
			BigDecimal SalesPrice = new BigDecimal (0);
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT Pricestd "
					+ " FROM M_ProductPrice "
					+ " WHERE M_Product_ID = ?"
					+ " AND M_PriceList_Version_ID = ?");
			
			SalesPrice = DB.getSQLValueBDEx(get_TrxName(), sql.toString(), new Object[]{i,p});
			
			return SalesPrice;
			
		}
		
		private String getWarehouse(int i){
			String warehouse= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT mw.name "
					+ " FROM m_locator ml left join m_warehouse mw on ml.m_warehouse_id = mw.M_Warehouse_ID "
					+ " WHERE ml.M_Locator_ID = ?");
			
			warehouse = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return warehouse;
		}
		private String getLocator(int i){
			String locator= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ml.value "
					+ " FROM  M_Locator ml "
					+ " WHERE ml.M_locator_ID = ?");
			
			locator = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return locator;
		}
		
		private String getSize (int i){
			String size= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT size "
					+ " FROM  M_product ml "
					+ " WHERE ml.M_product_ID = ?");
			
			size = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return size;
			
		}
		
		private String getValue (int i){
			String value= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ml.Value "
					+ " FROM  M_product ml "
					+ " WHERE ml.M_product_ID = ?");
			
			value = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return value;
			
		}
		private String getName (int i){
			String name= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ml.name "
					+ " FROM  M_product ml "
					+ " WHERE ml.M_product_ID = ?");
			
			name = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return name;
			
		}
		private String getCategory(int i){
					
			String category= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT mpc.name "
					+ " FROM  M_Product_category mpc left join M_product mp on mpc.M_Product_category_id = mp.M_Product_category_ID "
					+ " WHERE mp.M_Product_ID = ?");
			
			category = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return category;
				}
		private String getUom(int i){
			
			String uom= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT cu.name "
					+ " FROM  C_UOM cu left join M_Product mp on cu.C_UOM_ID = mp.C_UOM_ID "
					+ " WHERE mp.M_Product_ID = ?");
			
			uom = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return uom;
		}
		private String getZone(int i){
			
			String zone= new String();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT mwz.name "
					+ " FROM  M_WarehouseZone mwz left join M_Warehouse mw on mwz.M_Warehouse_ID = mw.M_Warehouse_ID "
					+ " left join M_Locator ml on mw.M_Warehouse_ID = ml.M_Warehouse_ID "
					+ " WHERE ml.M_locator_ID = ?");
			
			zone = DB.getSQLValueStringEx(get_TrxName(), sql.toString(), new Object[]{i});
			
			return zone;
		}
		private Integer getOrg(int i){
			Integer org= new Integer(0);
				
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT ad_org_ID "
						+ " FROM  M_Locator ml"
						+ " WHERE ml.M_locator_ID = ?");
				
				org = DB.getSQLValueEx(get_TrxName(), sql.toString(), new Object[]{i});

				
				return org;
			}
		private void cleanTable(){
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM T_PInventoryTrxAcct");
			DB.executeUpdate(sql.toString(), get_TrxName());
		}
	

}

