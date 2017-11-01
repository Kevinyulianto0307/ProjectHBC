package org.compiere.grid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.apps.IStatusBar;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.toba.habco.model.MShipActivity;

public class CreateFromBBMPlan extends CreateFrom{

	public CreateFromBBMPlan(GridTab gridTab) {
		super(gridTab);
	}

	@Override
	public Object getWindow() {
		return null;
	}

	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "TCS_BBMPlan_ID", false) + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}

	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		
	}
	/*
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
        Vector<String> columnNames = new Vector<String>(9);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add("PortPosition");
        columnNames.add("DateStart");
        columnNames.add("DateEnd");
        columnNames.add("Tugboat");
        columnNames.add("Barge");
	    return columnNames;
	}
	*/
	//@TommyAng
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
        Vector<String> columnNames = new Vector<String>(9);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add("Activity");        
        columnNames.add("PortPosition");
        columnNames.add("Activity StartDate");
        columnNames.add("Activity FinishDate");
        columnNames.add("Second Activity");
        columnNames.add("Tugboat");
        columnNames.add("Barge");
        columnNames.add("Position DateStart");
        columnNames.add("Position EndDate");
        columnNames.add("isDelete");
	    return columnNames;
	}
	//end @TommyAng
	
	/**
	 * load position line in table create from
	 * @param HBC_Trip_ID
	 * @return vector of position line
	 */
	/*
	protected Vector<Vector<Object>> getPosition(int HBC_Trip_ID)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT pos.HBC_Position_ID, port.Name, pos.DateStart, pos.EndDate, tugboat.Name, barge.Name "  //1..6
        		+ "FROM HBC_Position pos "
        		+ "LEFT JOIN HBC_PortPosition port ON port.HBC_PortPosition_ID = pos.HBC_PortPosition_ID "
        		+ "LEFT JOIN HBC_Tugboat tugboat ON tugboat.HBC_Tugboat_ID = pos.HBC_Tugboat_ID "
        		+ "LEFT JOIN HBC_Barge barge ON barge.HBC_Barge_ID = pos.HBC_Barge_ID "
        		+ "WHERE pos.AD_Client_ID=? AND pos.HBC_Trip_ID=? AND pos.TCS_BBMPlan_ID IS NULL ");
        //  port position, datestart, dateend, tugboat, barge
        
        PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	int count = 0;
        	pstmt = DB.prepareStatement(sqlStmt.toString(), null);
            pstmt.setInt(count+=1, Env.getAD_Client_ID(Env.getCtx()));
            pstmt.setInt(count+=1, HBC_Trip_ID);  
	            
            rs = pstmt.executeQuery();
            while (rs.next()){
            	Vector<Object> line = new Vector<Object>(8);
                line.add(new Boolean(false));           //  0-Selection
                KeyNamePair positionKP = new KeyNamePair(rs.getInt(1),rs.getString(2));
                line.add(positionKP);
                line.add(rs.getString(3));
                line.add(rs.getString(4));
                line.add(rs.getString(5));
                line.add(rs.getString(6));
                data.add(line);
            }
        }catch(Exception e){
        	log.severe("Error execute query get position in create from bbm plan");
        }finally{
        	DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
        }
        return data;
	}
	*/
	
	/**
	 * load activity line in table create from
	 * @param HBC_Trip_ID
	 * @return vector of position line
	 */
	protected Vector<Vector<Object>> getActivity(int HBC_Trip_ID, boolean isDelete)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
        StringBuilder sqlStmt = new StringBuilder();
        /*
        sqlStmt.append("SELECT pos.HBC_Position_ID, port.Name, pos.DateStart, pos.EndDate, tugboat.Name, barge.Name "  //1..6
        		+ "FROM HBC_Position pos "
        		+ "LEFT JOIN HBC_PortPosition port ON port.HBC_PortPosition_ID = pos.HBC_PortPosition_ID "
        		+ "LEFT JOIN HBC_Tugboat tugboat ON tugboat.HBC_Tugboat_ID = pos.HBC_Tugboat_ID "
        		+ "LEFT JOIN HBC_Barge barge ON barge.HBC_Barge_ID = pos.HBC_Barge_ID "
        		+ "WHERE pos.AD_Client_ID=? AND pos.HBC_Trip_ID=? AND pos.TCS_BBMPlan_ID IS NULL ");
        //  port position, datestart, dateend, tugboat, barge
        */
        sqlStmt.append("SELECT sa.HBC_ShipActivity_ID, ca.name, port.Name, pos.DateStart, pos.EndDate, tugboat.Name, barge.Name, sa.StartDate, sa.FinishDate, sa.isSecondActivity "  //1..10
        		+ "FROM HBC_ShipActivity sa "
        		+ "LEFT JOIN HBC_Position pos ON pos.HBC_Position_ID = sa.HBC_Position_ID "
        		+ "LEFT JOIN C_Activity ca ON ca.C_Activity_ID = sa.C_Activity_ID "
        		+ "LEFT JOIN HBC_PortPosition port ON port.HBC_PortPosition_ID = pos.HBC_PortPosition_ID "
        		+ "LEFT JOIN HBC_Tugboat tugboat ON tugboat.HBC_Tugboat_ID = sa.HBC_Tugboat_ID "
        		+ "LEFT JOIN HBC_Barge barge ON barge.HBC_Barge_ID = sa.HBC_Barge_ID "
        		+ "WHERE sa.AD_Client_ID=? ");
       if(isDelete)
    	   sqlStmt.append("AND sa.TCS_BBMPlan_ID = ?");
       else
    	   sqlStmt.append("AND sa.TCS_BBMPlan_ID IS NULL AND pos.HBC_Trip_ID=? ");
        sqlStmt.append("Order By sa.StartDate");
        //  port position, datestart, dateend, tugboat, barge
        
        PreparedStatement pstmt = null;
		ResultSet rs = null;
        try{
        	int count = 0;
        	int TCS_BBMPlan_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "TCS_BBMPlan_ID");
        	pstmt = DB.prepareStatement(sqlStmt.toString(), null);
            pstmt.setInt(count+=1, Env.getAD_Client_ID(Env.getCtx()));
            
            if(isDelete)
            	pstmt.setInt(count+=1, TCS_BBMPlan_ID);
            else
            	pstmt.setInt(count+=1, HBC_Trip_ID);
	            
            rs = pstmt.executeQuery();
            while (rs.next()){
            	Vector<Object> line = new Vector<Object>(8);
                line.add(new Boolean(false));           //  0-Selection
                KeyNamePair shipKP = new KeyNamePair(rs.getInt(1), rs.getString(2));
                line.add(shipKP);
                line.add(rs.getString(3));
                
                //@Phie
                SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd hh:mm:ss");
                SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = null;
                
                String activityStartDate = rs.getString(8); 
                if(activityStartDate != null){
                	date = dt.parse(activityStartDate); 
                	String activityStartDateNF = dt1.format(date);
                	line.add(activityStartDateNF);//Activity Start Date swap by @phie
                }
                else
                	line.add(activityStartDate);
                
                String activityFinishDate = rs.getString(9); 
                if(activityFinishDate != null){
                	date = dt.parse(activityFinishDate); 
                	String activityFinishDateNF = dt1.format(date);
                	line.add(activityFinishDateNF); //Activity Finish Date swap by @phie
                }
                else
                	line.add(activityFinishDate);
                
                String dateStart = rs.getString(4); 
                if(dateStart != null){
                	date = dt.parse(dateStart); 
                	String dateStartNF = dt1.format(date);
                	line.add(dateStartNF); //Position Date Start swap by @phie	
                }
                else
                	line.add(dateStart);
                
                String endDate = rs.getString(5);
                if(endDate != null){
                	date = dt.parse(endDate); 
                	String endDateNF = dt1.format(date);
                	line.add(endDateNF); //Position End Date swap by @phie
                }
                else
                	line.add(endDate);
                //end phie
                
                
                line.add(rs.getString(10));
                line.add(rs.getString(6));
                line.add(rs.getString(7));
                line.add(isDelete); //isDelete
                data.add(line);
            }
        }catch(Exception e){
        	log.severe("Error execute query get position in create from bbm plan");
        }finally{
        	DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
        }
        return data;
	}
	
	/**
	 * load trip data in parameter create from
	 * @param HBC_Tugboat_ID
	 * @return array list of trip data
	 */
	protected ArrayList<KeyNamePair> loadTripData (int HBC_Tugboat_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();

		int AD_Client_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "AD_Client_ID");
		StringBuilder sql = new StringBuilder("SELECT HBC_Trip_ID, DocumentNo FROM HBC_Trip "
				+ "WHERE AD_Client_ID=? AND HBC_Tugboat_ID=? ");
		sql.append("ORDER BY DocumentNo");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(count+=1, AD_Client_ID);
			pstmt.setInt(count+=1, HBC_Tugboat_ID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
		}catch(Exception e){
			log.log(Level.SEVERE, sql.toString(), e);
		}finally{
			DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
		}
		
		return list;
	}
	/*
	protected void configureMiniTable (IMiniTable miniTable)
	{
	//  port position, datestart, dateend, tugboat, barge
		miniTable.setColumnClass(0, boolean.class, false);  //0-Selection
		miniTable.setColumnClass(1, String.class, true);  //1-PortPosition
		miniTable.setColumnClass(2, Timestamp.class, true);  //2-DateStart
		miniTable.setColumnClass(3, Timestamp.class, true);  //3-DateEnd
		miniTable.setColumnClass(4, String.class, true);  //4-Tugboat
		miniTable.setColumnClass(5, String.class, true);  //5-Barge
		//  Table UI
		miniTable.autoSize();
	}
	*/
	
	//@TommyAng
	protected void configureMiniTable (IMiniTable miniTable)
	{
	//  port position, datestart, dateend, tugboat, barge
		miniTable.setColumnClass(0, boolean.class, false);  //0-Selection
		miniTable.setColumnClass(1, String.class, true);  //1-Activity
		miniTable.setColumnClass(2, String.class, true);  //2-PortPosition
		miniTable.setColumnClass(3, Timestamp.class, true);  //3-Activity Start Date
		miniTable.setColumnClass(4, Timestamp.class, true);  //4-Activity End Date
		miniTable.setColumnClass(5, Boolean.class, true);  //5-Second Activity
		miniTable.setColumnClass(6, String.class, true);  //6-Tugboat
		miniTable.setColumnClass(7, String.class, true);  //7-Barge
		miniTable.setColumnClass(8, Timestamp.class, true);  //8-Position Date Start
		miniTable.setColumnClass(9, Timestamp.class, true);  //9-Position End Date
		miniTable.setColumnClass(10, Boolean.class, true);  //10-isDelete
		//  Table UI
		miniTable.autoSize();
	}
	//end @TommyAng
	
	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		
		int TCS_BBMPlan_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "TCS_BBMPlan_ID");
		//@TommyAng
		//int currentPositionID = 0;
		for (int i = 0; i < miniTable.getRowCount(); i++)
        {
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
            {
				//KeyNamePair positionKP = (KeyNamePair) miniTable.getValueAt(i, 1);
				KeyNamePair shipKP = (KeyNamePair) miniTable.getValueAt(i, 1);
				int HBC_ShipActivity_ID = shipKP.getKey();

				MShipActivity ship = new MShipActivity(Env.getCtx(), HBC_ShipActivity_ID, trxName);
		        //TODO: Add condition for deleteActivity checkbox
				
				if(((Boolean)miniTable.getValueAt(i, 10)).booleanValue())
					ship.set_ValueOfColumn("TCS_BBMPlan_ID", null);
				else
					ship.set_ValueOfColumn("TCS_BBMPlan_ID", TCS_BBMPlan_ID);
				ship.saveEx();				
				
				/*
				if(ship.getHBC_Position_ID() != currentPositionID){
					MPosition position = new MPosition(Env.getCtx(), ship.getHBC_Position_ID(), trxName);
					position.set_ValueOfColumn("TCS_BBMPlan_ID", TCS_BBMPlan_ID);
					position.saveEx();
					currentPositionID = ship.getHBC_Position_ID();
				}
				*/
            }
        }
		//end @TommyAng
		return true;
	}

}
