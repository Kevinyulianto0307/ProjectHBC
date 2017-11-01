package org.toba.habco.process;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;
 




import org.compiere.model.MCharge;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrder;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MProduction;
import org.compiere.model.MProductionLine;
import org.compiere.model.MTransaction;
import org.compiere.model.Query;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.zkoss.idom.DocType;
 
/**
 * @author stephan
 * stock card report
 */
public class HBC_TaoStockCard extends SvrProcess{
 
    private long m_start = System.currentTimeMillis();
    //parameter
    private Timestamp p_movementDate = null;
    private Timestamp p_movementDateTo = null;
    private int p_M_Locator_ID = 0;
    private int p_M_Product_ID = 0;
    private BigDecimal onhandQty = new BigDecimal(0);
   
    //@kevinf HBC-2643
    private int cur_M_Product_ID = 0;
    private int cur_M_Locator_ID = 0;
    //@kevinf end
   
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
        cleanTable();
        //@kevinf HBC-2643
        //createFirstLine();
        //@kevinf end
        createTransaction();
       
        String whereClause = "Name = 'T_StockCard'";
        int AD_PrintFormat_ID = new Query(getCtx(), MPrintFormat.Table_Name, whereClause, get_TrxName())
        .setOnlyActiveRecords(true)
        .firstId();
       
        if (AD_PrintFormat_ID > 0) {
            if (Ini.isClient())
                getProcessInfo().setTransientObject (MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false));
            else
                getProcessInfo().setSerializableObject(MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false));
        }
 
        log.fine((System.currentTimeMillis() - m_start) + " ms");
       
        return "";
    }
 
    protected void cleanTable(){
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM T_StockCard");
        DB.executeUpdate(sql.toString(), get_TrxName());
    }
   
    protected void createTransaction(){
       
        //@kevinf HBC-2643
        //StringBuilder where = new StringBuilder();
    	//@kevinf HBC-2763
        StringBuilder sql = new StringBuilder();
        //where.append("M_Product_ID="+p_M_Product_ID+" AND ");
        sql.append("select mt.m_transaction_id, minv.description, fa1.amtsourcecr, fa2.amtsourcecr, fa3.amtsourcecr, mio.c_order_id, mio.c_bpartner_id, cvb.Account_ID, mt.M_Product_ID, mt.M_Locator_ID, mt.created, cvb2.Account_ID ");
        sql.append("from m_transaction mt ");
        sql.append("left join m_inventoryline minv on mt.m_inventoryline_id=minv.m_inventoryline_id ");
        sql.append("left join fact_acct fa1 on mt.m_inoutline_id=fa1.line_id ");
        sql.append("left join fact_acct fa2 on mt.m_movementline_id=fa2.line_id ");
        sql.append("left join fact_acct fa3 on mt.m_inventoryline_id=fa3.line_id ");
        sql.append("left join m_product mp on mt.m_product_id=mp.m_product_id ");
        sql.append("left join m_inoutline miol on mt.m_inoutline_id=miol.m_inoutline_id ");
        sql.append("left join m_inout mio on miol.m_inout_id=mio.m_inout_id ");
        sql.append("left join M_Product_Acct mpa on mt.m_product_id=mpa.m_product_id ");
        sql.append("left join C_ValidCombination cvb on mpa.p_asset_acct=cvb.C_ValidCombination_ID ");
        sql.append("left join M_Product_Category_Acct mpca on mp.M_Product_Category_ID=mpca.M_Product_Category_ID ");
        sql.append("left join C_ValidCombination cvb2 on mpca.p_asset_acct=cvb2.C_ValidCombination_ID ");
        sql.append("where ");
        if(p_M_Product_ID > 0)
            sql.append("mt.M_Product_ID="+p_M_Product_ID+" AND ");
            //where.append("M_Product_ID="+p_M_Product_ID+" AND ");
        if(p_M_Locator_ID > 0)
            sql.append("mt.M_Locator_ID="+p_M_Locator_ID+" AND ");
            //where.append("M_Locator_ID="+p_M_Locator_ID+" AND ");
       
        sql.append("mp.isactive='Y' ");
        sql.append("AND ((fa1.ad_table_id=319 and fa1.amtsourcecr>0) or (fa2.ad_table_id = 323 and fa2.amtsourcecr>0) or (fa3.ad_table_id = 321 and fa3.amtsourcecr>0)) ");
        sql.append("AND mt.MovementDate BETWEEN '" + p_movementDate + "' AND '"+p_movementDateTo+"' ");
        sql.append(" order by mt.M_Product_ID, mt.M_Locator_ID, mt.created");
        //@kevinf HBC-2763 end
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        /*
        where.append("MovementDate BETWEEN '" + p_movementDate + "' AND '"+p_movementDateTo+"'");
        int[] transactionIDs = new Query(getCtx(), MTransaction.Table_Name, where.toString(), get_TrxName())
        .setOnlyActiveRecords(true)
        .setClient_ID()
        .setOrderBy("M_Product_ID, M_Locator_ID, Created")
        .getIDs();
        */
        try{
            pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
            rs = pstmt.executeQuery();
           
            //for (int i : transactionIDs) {
            while(rs.next()){  
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO T_StockCard(AD_PInstance_ID, AD_Client_ID, AD_Org_ID, "
                        + "MovementDate, MovementQty, MovementType, "
                        + "M_InventoryLine_ID, M_MovementLine_ID, M_InoutLine_ID, M_ProductionLine_ID, "
                        + "LastQty, DocumentNo, M_Locator_ID, M_Product_ID, Description, CurrentCostPrice, "
                        + "C_Order_ID, C_BPartner_ID, Account_ID, ProductCategory_Account_ID, DateOrdered, "
                        + "M_LocatorTo_ID, TotalCost, M_LocatorFrom_ID, ProductCategoryName, Charge) ");
                sb.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
               
                MTransaction transaction = new MTransaction(getCtx(), rs.getInt(1), get_TrxName());
                
                //@phie
        		String p_categoryName = "";
        		String pc_charge = "";
        		MProduct p = new MProduct(getCtx(), transaction.getM_Product_ID(), get_TrxName());
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
                
                //@kevinf HBC-2643
                if(cur_M_Product_ID!=transaction.getM_Product_ID() || cur_M_Locator_ID!=transaction.getM_Locator_ID())
                {
                    if(cur_M_Product_ID!=transaction.getM_Product_ID())
                    {
                        cur_M_Product_ID=transaction.getM_Product_ID();
                    }
                    if(cur_M_Locator_ID!=transaction.getM_Locator_ID())
                    {
                        cur_M_Locator_ID=transaction.getM_Locator_ID();
                    }
                    createFirstLine(cur_M_Product_ID, cur_M_Locator_ID);
                }
                //@kevinf end
               
                String documentNo = null;
                Integer m_locatorto_id = null;
                Integer m_locatorfrom_id = null;
                BigDecimal qtyLine = Env.ONE;
                if(transaction.getM_InventoryLine_ID() > 0){
                    MInventoryLine invLine = (MInventoryLine) transaction.getM_InventoryLine();
                    MInventory inv = invLine.getParent();
                    documentNo = inv.getDocumentNo();
                    MDocType docType = new MDocType(Env.getCtx(), inv.getC_DocType_ID(), null);
                    if(docType.getDocSubTypeInv().equals(MDocType.DOCSUBTYPEINV_MiscReceipt))
                    {
                    	qtyLine = invLine.getQtyMiscReceipt();
                    }
                    else if(docType.getDocSubTypeInv().equals(MDocType.DOCSUBTYPEINV_InternalUseInventory))
                    {
                    	qtyLine = invLine.getQtyInternalUse();
                    }
                    else if(docType.getDocSubTypeInv().equals(MDocType.DOCSUBTYPEINV_PhysicalInventory))
                    {
                    	qtyLine = invLine.getQtyBook().subtract(invLine.getQtyCount());
                    }
                }
               
                else if(transaction.getM_MovementLine_ID() > 0){
                    MMovementLine moveLine = (MMovementLine) transaction.getM_MovementLine();
                    MMovement mov = moveLine.getParent();
                    documentNo = mov.getDocumentNo();
                    qtyLine = moveLine.getMovementQty();
                    if(transaction.getMovementType().equals("M-"))
                    	m_locatorto_id = moveLine.getM_LocatorTo_ID();
                    else
                    	m_locatorfrom_id = moveLine.getM_Locator_ID();
                }
               
                else if(transaction.getM_InOutLine_ID() > 0){
                    MInOutLine inoutLine = (MInOutLine) transaction.getM_InOutLine();
                    MInOut inout = inoutLine.getParent();
                    documentNo = inout.getDocumentNo();
                    qtyLine = inoutLine.getMovementQty();
                }
               
                else if(transaction.getM_ProductionLine_ID() > 0){
                    MProductionLine prodLine = (MProductionLine) transaction.getM_ProductionLine();
                    MProduction prod = new MProduction(getCtx(), prodLine.getM_Production_ID(), get_TrxName());
                    documentNo = prod.getDocumentNo();
                }
               
                onhandQty = onhandQty.add(transaction.getMovementQty());
                Object invLine = null, moveLine = null, inoutLine = null, prodLine = null;
               
                if(transaction.getM_InventoryLine_ID() > 0)
                    invLine = transaction.getM_InventoryLine_ID();
                else if(transaction.getM_MovementLine_ID() > 0)
                    moveLine = transaction.getM_MovementLine_ID();
                else if(transaction.getM_InOutLine_ID() > 0)
                    inoutLine = transaction.getM_InOutLine_ID();
                else if(transaction.getM_ProductionLine_ID() > 0)
                    prodLine = transaction.getM_ProductionLine_ID();
               
                String description = rs.getString(2);
                if(description==null)
                    description = "";
               
                BigDecimal costprice = rs.getBigDecimal(3);
                if(costprice==null)
                {
                    costprice = rs.getBigDecimal(4);
                    if(costprice==null)
                    {
                        costprice = rs.getBigDecimal(5);
                        if(costprice==null)
                        {
                            costprice=Env.ZERO;
                        }
                    }
                }
                      
                costprice = costprice.divide(qtyLine,2,RoundingMode.HALF_UP).abs();
                
                BigDecimal totalcost = costprice.multiply(transaction.getMovementQty());
               
                Integer c_order_id = rs.getInt(6);
                Timestamp dateordered=null;
                if(c_order_id==0)
                {
                    c_order_id=null;
                }
                else
                {
                    MOrder order = new MOrder(getCtx(), c_order_id, get_TrxName());
                    dateordered = order.getDateOrdered();
                }
               
                Integer c_bpartner_id = rs.getInt(7);
                if(c_bpartner_id==0)
                {
                    c_bpartner_id=null;
                }
               
                Integer account_id = rs.getInt(8); 
                if(account_id==0)
                {
                    account_id=null;
                }          
               
                Integer productcategory_account_id = rs.getInt(12);
                if(productcategory_account_id==0)
                {
                    productcategory_account_id=null;
                }  
               
                /*Object[] params = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), Env.getAD_Org_ID(getCtx()),
                        transaction.getMovementDate(), transaction.getMovementQty(), transaction.getMovementType(),
                        invLine, moveLine, inoutLine, prodLine,
                        onhandQty, documentNo, transaction.getM_Locator_ID(), transaction.getM_Product_ID()};*/
                Object[] params = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(), Env.getAD_Org_ID(getCtx()),
                        transaction.getMovementDate(), transaction.getMovementQty(), transaction.getMovementType(),
                        invLine, moveLine, inoutLine, prodLine,
                        onhandQty, documentNo, transaction.getM_Locator_ID(), transaction.getM_Product_ID(), 
                        description, costprice, c_order_id, c_bpartner_id, account_id, productcategory_account_id, 
                        dateordered, m_locatorto_id, totalcost, m_locatorfrom_id, p_categoryName, pc_charge};
               
                int no = DB.executeUpdate(sb.toString(), params, true, get_TrxName());
                log.fine("#" + no);
            }
        }
        catch(Exception e)
        {
           System.out.println(e.getMessage());
        }
        //@kevinf end
    }
   
    //@kevinf HBC-2643
    //protected void createFirstLine(){
    protected void createFirstLine(int p_M_Product_ID, int p_M_Locator_ID) {
    //@kevinf end
   
        Object[] params = null;
        onhandQty = getQtyFirst(p_M_Product_ID,p_M_Locator_ID);
        StringBuilder sb = new StringBuilder();
       
        //@kevinf HBC-2643
        //if(p_M_Locator_ID > 0){
            sb.append("INSERT INTO T_StockCard (AD_PInstance_ID, AD_Client_ID, AD_Org_ID ,"
                    + "MovementDate, DocumentNo, M_Product_ID, M_Locator_ID, LastQty) "
                    + "VALUES(?,?,?,'"+p_movementDate+"'");
            sb.append(",'Saldo Awal',?,?,?)");
            params = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(),
                    Env.getAD_Org_ID(getCtx()), p_M_Product_ID, p_M_Locator_ID, onhandQty};
        /*}
        else{
            sb.append("INSERT INTO T_StockCard (AD_PInstance_ID, AD_Client_ID, AD_Org_ID ,"
                    + "MovementDate, DocumentNo, M_Product_ID, LastQty) "
                    + "VALUES(?,?,?,'"+p_movementDate+"'");
            sb.append(",'Saldo Awal',?,?)");
            params = new Object[]{getAD_PInstance_ID(), getAD_Client_ID(),
                    Env.getAD_Org_ID(getCtx()), p_M_Product_ID,onhandQty};
        }*/
        //@kevinf end
       
        int no = DB.executeUpdate(sb.toString(), params, true, get_TrxName());
        log.fine("#" + no);
    }
   
    //@kevinf HBC-2643
    //private BigDecimal getQtyFirst() {
    private BigDecimal getQtyFirst(int p_M_Product_ID, int p_M_Locator_ID) {
    //@kevinf end
        BigDecimal sumQty = new BigDecimal(0);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COALESCE(SUM(MovementQty),0) FROM M_Transaction "
                + "WHERE MovementDate < '" + p_movementDate + "' "
                + "AND M_Product_ID=? ");
       
        if(p_M_Locator_ID > 0){
            sql.append("AND M_Locator_ID=?");
            sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(),
                    new Object[]{p_M_Product_ID, p_M_Locator_ID});
        }else{
            sumQty = DB.getSQLValueBD(get_TrxName(), sql.toString(),
                    new Object[]{p_M_Product_ID});
        }
       
        if(sumQty == null)
            return Env.ZERO;
       
        return sumQty;
    }
   
}