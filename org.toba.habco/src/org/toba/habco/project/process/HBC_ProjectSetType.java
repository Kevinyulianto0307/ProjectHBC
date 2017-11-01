/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.toba.habco.project.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MProject;
import org.compiere.model.MProjectType;
import org.compiere.model.Query;
import org.compiere.model.X_C_Phase;
import org.compiere.model.X_C_ProjectLine;
import org.compiere.model.X_C_ProjectPhase;
import org.compiere.model.X_C_ProjectTask;
import org.compiere.model.X_C_Task;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.toba.habco.model.X_C_ProjectTypeLine;

/**
 *  Set Project Type
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectSetType.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class HBC_ProjectSetType extends SvrProcess
{
	/**	Project directly from Project	*/
	private int				m_C_Project_ID = 0;
	/** Project Type Parameter			*/
	private int				m_C_ProjectType_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				continue;
			else if (name.equals("C_ProjectType_ID"))
				m_C_ProjectType_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		m_C_Project_ID = getRecord_ID();
		if (log.isLoggable(Level.INFO)) log.info("doIt - C_Project_ID=" + m_C_Project_ID + ", C_ProjectType_ID=" + m_C_ProjectType_ID);
		//
		MProject project = new MProject (getCtx(), m_C_Project_ID, get_TrxName());
		if (project.getC_Project_ID() == 0 || project.getC_Project_ID() != m_C_Project_ID)
			throw new IllegalArgumentException("Project not found C_Project_ID=" + m_C_Project_ID);
		if (project.getC_ProjectType_ID_Int() > 0)
			throw new IllegalArgumentException("Project already has Type (Cannot overwrite) " + project.getC_ProjectType_ID());
		//
		MProjectType type = new MProjectType (getCtx(), m_C_ProjectType_ID, get_TrxName());
		if (type.getC_ProjectType_ID() == 0 || type.getC_ProjectType_ID() != m_C_ProjectType_ID)
			throw new IllegalArgumentException("Project Type not found C_ProjectType_ID=" + m_C_ProjectType_ID);

		//@win set for docking project
		if (type.get_ValueAsInt("M_Product_ID") > 0)
			project.set_CustomColumn("M_Product_ID", type.get_ValueAsInt("M_Product_ID"));
		if (type.get_ValueAsInt("M_Product2_ID") > 0)
			project.set_CustomColumn("M_Product2_ID", type.get_ValueAsInt("M_Product2_ID"));
		if (type.get_ValueAsString("HBC_Percentage_DockingMaterial")!= null)
			project.set_CustomColumn("HBC_Percentage_DockingMaterial", type.get_Value("HBC_Percentage_DockingMaterial"));
		if (type.get_ValueAsInt("HBC_Percentage_DockingService") > 0)
			project.set_CustomColumn("HBC_Percentage_DockingService", type.get_Value("HBC_Percentage_DockingService"));

		//end @win 

		//	Set & Copy if Service
		project.setProjectType(type);
		if (!project.save())
			throw new Exception ("@Error@");

		if (getStandardPhaseIDs().length>0){
			createStandardPhase();
		}



		//
		//
		return "@OK@";
	}	//	doIt


	protected int[] getStandardPhaseIDs(){

		int[] standardPhaseIds = new Query(getCtx(),X_C_Phase.Table_Name,"C_ProjectType_ID="+m_C_ProjectType_ID,get_TrxName())
		.setOnlyActiveRecords(true)
		.getIDs();
		return standardPhaseIds;
	}

	protected int[] getStandardTaskIDs(int C_Phase_ID){

		int[] standardTaskIds = new Query(getCtx(),X_C_Task.Table_Name,"C_Phase_ID="+C_Phase_ID,get_TrxName())
		.setOnlyActiveRecords(true)
		.getIDs();

		return standardTaskIds;
	}

	protected int[] StandardTaskDetail(int C_Task_ID){
		int[] standardTaskDetailIds = new Query(getCtx(),X_C_ProjectTypeLine.Table_Name,"C_Task_ID="+C_Task_ID,get_TrxName())
		.setOnlyActiveRecords(true)
		.getIDs();

		return standardTaskDetailIds;
	}

	protected void createStandardTask(int C_Phase_ID,int projectphaseid){
		int Line=0;
		for(int i=0;i<getStandardTaskIDs(C_Phase_ID).length;i++){
			X_C_Task task = new X_C_Task(getCtx(),getStandardTaskIDs(C_Phase_ID)[i],get_TrxName());
			X_C_ProjectTask projecttask = new X_C_ProjectTask(getCtx(), 0, get_TrxName());
			projecttask.setC_ProjectPhase_ID(projectphaseid);
			projecttask.setName(task.getName());
			projecttask.setC_Task_ID(task.getC_Task_ID());
			projecttask.setSeqNo(Line=Line+10);
			projecttask.saveEx();

			if(StandardTaskDetail(task.getC_Task_ID()).length>0){
				createStandardDetailPhase(task.getC_Task_ID(),projecttask.getC_ProjectTask_ID(),projecttask.getC_ProjectPhase_ID());
			}
		}
	}

	protected void createStandardDetailPhase(int C_Task_ID,int C_ProjectTask_ID,int C_ProjectPhase_ID){
		int Line=0;
		for(int i=0;i<StandardTaskDetail(C_Task_ID).length;i++){
			X_C_ProjectTypeLine taskdetail = new X_C_ProjectTypeLine(getCtx(),StandardTaskDetail(C_Task_ID)[i],get_TrxName());
			X_C_ProjectLine projectDetail = new X_C_ProjectLine(getCtx(), 0, get_TrxName());
			projectDetail.setLine(Line=Line+10);
			projectDetail.set_CustomColumn("C_Task_ID",C_Task_ID);
			projectDetail.setC_ProjectTask_ID(C_ProjectTask_ID);
			projectDetail.setC_ProjectPhase_ID(C_ProjectPhase_ID);
			projectDetail.setM_Product_ID(taskdetail.getM_Product_ID());
			projectDetail.setC_Project_ID(m_C_Project_ID);
			projectDetail.setPlannedQty(taskdetail.getStandardQty());
			projectDetail.saveEx();
		}

	}


	protected void createStandardPhase(){
		int line=0;
		for(int i=0;i<getStandardPhaseIDs().length;i++){
			X_C_Phase phase = new X_C_Phase(getCtx(), getStandardPhaseIDs()[i], get_TrxName());
			X_C_ProjectPhase projectphase = new X_C_ProjectPhase(getCtx(), 0, get_TrxName());
			projectphase.setC_Project_ID(m_C_Project_ID);
			projectphase.setC_Phase_ID(phase.getC_Phase_ID());
			projectphase.setName(phase.getName());
			projectphase.setSeqNo(line=line+10);
			projectphase.saveEx();

			if(getStandardTaskIDs(phase.getC_Phase_ID()).length>0){
				createStandardTask(phase.getC_Phase_ID(),projectphase.getC_ProjectPhase_ID());
			}
		}
	}

}	//	ProjectSetType
