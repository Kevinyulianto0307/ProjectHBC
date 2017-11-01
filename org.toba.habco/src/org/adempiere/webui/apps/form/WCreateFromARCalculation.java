package org.adempiere.webui.apps.form;


import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromARCalculation;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Vlayout;

public class WCreateFromARCalculation extends CreateFromARCalculation implements EventListener<Event>, ValueChangeListener
{
	/*
	 * @Author yonk
	 */
	
	protected Label contractLabel = new Label();
	protected WEditor contractField ;
	protected Label periodLabel = new Label();
	protected WEditor periodField ;
	protected Label typeLabel = new Label();
	protected WEditor typeField ;
	
	private WCreateFromWindow window;
	/** Window No               */
	private int p_WindowNo;
	
	int HBC_Contract_ID=0;
	int C_Period_ID = 0;
	String calculationType = "";
	private boolean 	m_actionActive = false;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	

	public WCreateFromARCalculation(GridTab gridTab) {
		super(gridTab);
		log.info(getGridTab().toString());
		
		window = new WCreateFromWindow(this, getGridTab().getWindowNo());
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			zkInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.showWindow(window);
	}
	
	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		
		super.dynInit();
		
		window.setTitle(getTitle());
		
		//initBPartner(true);
		initARCalculationLine();
		return true;
	}   //  dynInit
	
	
	protected void zkInit() throws Exception{		
		contractLabel.setText(Msg.getElement(Env.getCtx(),"HBC_Contract_ID"));
		periodLabel.setText(Msg.getElement(Env.getCtx(),"C_Period_ID"));
		typeLabel.setText("Type");
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
		Borderlayout parameterLayout = new Borderlayout();
		parameterLayout.setHeight("50px");
		parameterLayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(parameterLayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	Panel parameterStdPanel = new Panel();
		parameterStdPanel.appendChild(parameterStdLayout);

		Center center = new Center();
		parameterLayout.appendChild(center);
		center.appendChild(parameterStdPanel);
		
		Rows rows = (Rows) parameterStdLayout.newRows();
		
		Row row = rows.newRow();
		
		row.appendChild(contractLabel.rightAlign());
		row.appendChild(contractField.getComponent());
		row.appendChild(periodLabel.rightAlign());
		row.appendChild(periodField.getComponent());
		row.appendChild(typeLabel.rightAlign());
		row.appendChild(typeField.getComponent());
	}

	@Override
	public void valueChange(ValueChangeEvent evt) {
		if (log.isLoggable(Level.CONFIG)) log.config(evt.getPropertyName() + "=" + evt.getNewValue());

		if (evt.getPropertyName().equals("HBC_Contract_ID"))
		{
			
			if (evt.getNewValue() != null){
				HBC_Contract_ID = ((Integer)evt.getNewValue()).intValue();
			}

			int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
			loadARCalculationLine(C_BPartner_ID,HBC_Contract_ID, C_Period_ID, calculationType);
		}
		
		else if (evt.getPropertyName().equals("C_Period_ID"))
		{
			
			if (evt.getNewValue() != null){
				C_Period_ID = ((Integer)evt.getNewValue()).intValue();
			}

			int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
			loadARCalculationLine(C_BPartner_ID,HBC_Contract_ID, C_Period_ID, calculationType);
		}
		
		else if (evt.getPropertyName().equals("Value"))
		{
			
			if (evt.getNewValue() != null){
				calculationType = evt.getNewValue().toString();
			}

			int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
			loadARCalculationLine(C_BPartner_ID,HBC_Contract_ID, C_Period_ID, calculationType);
		}
		window.tableChanged(null);
	}

	@Override
	public void onEvent(Event arg0) throws Exception {
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		initARCalculationLine();
		
	}
	
	public void showWindow()
	{
		window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}

	@Override
	public Object getWindow() {
		return window;
	}
	
	protected void initARCalculationLine() throws Exception
	{
		// Column HBC_Contract_ID on table HBC_Contract = 1000002
		
		MLookup lookupContract = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 1000002, DisplayType.Search);
		contractField = new WSearchEditor("HBC_Contract_ID",true,false,true,lookupContract);
		//
		// Column C_Period_ID on table C_Period = 837
		MLookup lookupPeriod = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, 837, DisplayType.Search);
		periodField = new WSearchEditor("C_Period_ID", false, false, true, lookupPeriod);
		//
		
		//MLookup lookupType = MLookupFactory.getLookup_List(Env.getLanguage(Env.getCtx()), 1100078);
		//MLookup lookupType = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, 1105024, DisplayType.List);
		MLookupInfo info = MLookupFactory.getLookup_List(Env.getLanguage(Env.getCtx()), 1100078);
		MLookup lookupType = new MLookup(info, 0);
		typeField = new WTableDirEditor(lookupType, "Type", "", true, false, true);
		//
		int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
		loadARCalculationLine(C_BPartner_ID,0,0,"");
		
		contractField.addValueChangeListener(this);
		periodField.addValueChangeListener(this);
		typeField.addValueChangeListener(this);
	}

	
	protected void loadARCalculationLine (int C_BPartner_ID,int HBC_Contract_ID, int C_Period_ID, String CalculationType)
	{	
		loadTableOIS(getARCalculationLine(C_BPartner_ID,HBC_Contract_ID, C_Period_ID, CalculationType));
	}   
	
	protected void loadTableOIS (Vector<?> data)
	{
		window.getWListbox().clear();
		
		//  Remove previous listeners
		window.getWListbox().getModel().removeTableModelListener(window);
		//  Set Model
		ListModelTable model = new ListModelTable(data);
		model.addTableModelListener(window);
		window.getWListbox().setData(model, getOISColumnNames());
		//
		
		configureMiniTable(window.getWListbox());
	}   //  loadOrder

}
