package org.adempiere.webui.apps.form;

import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromBBMPlan;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vlayout;

public class WCreateFromBBMPlan extends CreateFromBBMPlan implements EventListener<Event>, ValueChangeListener{

	public WCreateFromBBMPlan(GridTab gridTab) {
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

	protected Label tripLabel = new Label();
	protected WEditor tripField ;
	protected Checkbox deleteActivityCb = new Checkbox();
	
	
	private WCreateFromWindow window;
	/** Window No               */
	private int p_WindowNo;
	
	int HBC_Trip_ID=0;
	//private boolean 	m_actionActive = false;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	protected void zkInit() throws Exception{		
		tripLabel.setText(Msg.getElement(Env.getCtx(),"HBC_Trip_ID"));
        deleteActivityCb.setText(Msg.getMsg(Env.getCtx(), "DeleteBBMActivity", true));
        deleteActivityCb.setTooltiptext(Msg.getMsg(Env.getCtx(), "DeleteBBMActivity", false));

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
		
		row.appendChild(tripLabel.rightAlign());
		row.appendChild(tripField.getComponent());
		row.appendChild(deleteActivityCb);
		
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
		
		deleteActivityCb.setSelected(false);
		deleteActivityCb.addActionListener(this);
		
		//initBPartner(true);
		initTrip(false);
		return true;
	}   //  dynInit
	
	protected void initTrip(boolean isDelete) throws Exception
	{
		int AD_Column_ID=1101895;
		
		MLookup lookupTrip = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		tripField = new WSearchEditor("HBC_Trip_ID",true,false,true,lookupTrip);
		
		//
		int HBC_Trip_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "HBC_Trip_ID");
		loadActivity(HBC_Trip_ID, isDelete);
		tripField.setValue(HBC_Trip_ID);
		tripField.addValueChangeListener(this);
	}

	protected void loadActivity (int HBC_Trip_ID, boolean isDelete)
	{	
		loadTableOIS(getActivity(HBC_Trip_ID, isDelete));
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
	
	@Override
	public Object getWindow() {
		return window;
	}
	
	@Override
	public void valueChange(ValueChangeEvent evt) {
		if (log.isLoggable(Level.CONFIG)) log.config(evt.getPropertyName() + "=" + evt.getNewValue());

		if (evt.getPropertyName().equals("HBC_Trip_ID"))
		{
			
			if (evt.getNewValue() != null){
				HBC_Trip_ID = ((Integer)evt.getNewValue()).intValue();
			}

			loadActivity(HBC_Trip_ID,deleteActivityCb.isChecked());
		}
        //TODO: Add condition for deleteActivity checkbox
		
		window.tableChanged(null);
	}

	@Override
	public void onEvent(Event arg0) throws Exception {
		/*
		if (m_actionActive)
			return;
		m_actionActive = true;
		*/
        //TODO: Add condition for deleteActivity checkbox			
		initTrip(deleteActivityCb.isChecked());
		
	}
	
}
