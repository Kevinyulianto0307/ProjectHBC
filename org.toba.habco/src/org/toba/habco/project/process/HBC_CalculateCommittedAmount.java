package org.toba.habco.project.process;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.Query;
import org.compiere.model.X_C_Project;
import org.compiere.model.X_C_ProjectLine;
import org.compiere.model.X_C_ProjectPhase;
import org.compiere.model.X_C_ProjectTask;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

public class HBC_CalculateCommittedAmount extends SvrProcess {
	/**
	 *@author yonk 
	 */
	int p_C_Project_ID=0;
	
	@Override
	protected void prepare() {
		p_C_Project_ID=getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		
		if(p_C_Project_ID==0)
			return"No Project Selected!";
		
		BigDecimal commitedAmt=Env.ZERO;
		X_C_Project project = new X_C_Project(getCtx(),p_C_Project_ID,get_TrxName());
		
		X_C_ProjectPhase[] projectPhases=getProjectPhase();
		
		for(X_C_ProjectPhase projectPhase:projectPhases){
			X_C_ProjectTask[] projectTasks=getProjectTask(projectPhase.getC_ProjectPhase_ID());
			for(X_C_ProjectTask projectTask:projectTasks){
				X_C_ProjectLine[] projectLines =getTaskDetail(projectTask.getC_ProjectTask_ID());
				for(X_C_ProjectLine projectLine:projectLines){
					if(commitedAmt!=null)
					commitedAmt=commitedAmt.add(projectLine.getCommittedAmt());
				}
			}
		}
		
		project.setProjectBalanceAmt(commitedAmt);
		project.saveEx();
		return "Calculated Successful ! Project Balance="+commitedAmt;
	}
	
	/**
	 * get project phase from the project
	 */
	protected X_C_ProjectPhase[] getProjectPhase(){
		
		List<X_C_ProjectPhase> ProjectPhase= new Query(getCtx(),X_C_ProjectPhase.Table_Name,"C_Project_ID="+p_C_Project_ID,get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list();

		ArrayList<X_C_ProjectPhase> list = new ArrayList<X_C_ProjectPhase>();
		for(int a=0;a<ProjectPhase.size();a++){
			list.add(ProjectPhase.get(a));
		}
		
		return list.toArray(new X_C_ProjectPhase[list.size()]);
	}

	
	/**
	 * get project task from each project phase
	 */
	protected X_C_ProjectTask[] getProjectTask(int ProjectPhaseID){
		
		List<X_C_ProjectTask> ProjectTask= new Query(getCtx(),X_C_ProjectTask.Table_Name,"C_ProjectPhase_ID="+ProjectPhaseID,get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list();
	
		ArrayList<X_C_ProjectTask> list = new ArrayList<X_C_ProjectTask>();
		for(int a=0;a<ProjectTask.size();a++){
			list.add(ProjectTask.get(a));
		}
		return list.toArray(new X_C_ProjectTask[list.size()]);
	}
	
	/**
	 * get Task Detail from each project Task
	 */
	protected X_C_ProjectLine[] getTaskDetail(int ProjectTaskID){
		
		List<X_C_ProjectLine> ProjectLine= new Query(getCtx(),X_C_ProjectLine.Table_Name,"C_ProjectTask_ID="+ProjectTaskID,get_TrxName())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list();

		ArrayList<X_C_ProjectLine> list = new ArrayList<X_C_ProjectLine>();
		for(int a=0;a<ProjectLine.size();a++){
			list.add(ProjectLine.get(a));
		}
		return list.toArray(new X_C_ProjectLine[list.size()]);
	}

}
