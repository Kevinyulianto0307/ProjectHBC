package org.adempiere.webui.apps.form;

import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromCostActivity;
import org.compiere.model.GridTab;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Vlayout;

/*
 * @author yonk
 */
public class WCreateFromCostActivity extends CreateFromCostActivity implements EventListener<Event>, ValueChangeListener
{
	
	private WCreateFromWindow window;
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	public WCreateFromCostActivity(GridTab tab) 
	{
		super(tab);
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
		initReferenceCode();
		return true;
	}   //  dynInit
	
	
	protected void zkInit() throws Exception{		
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
	}

	@Override
	public void valueChange(ValueChangeEvent evt) {
		
	}
	private boolean 	m_actionActive = false;
	

	@Override
	public void onEvent(Event arg0) throws Exception {
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		initReferenceCode();
		
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
	
	protected void initReferenceCode() throws Exception
	{
		int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
		loadCostActivity(C_BPartner_ID);
	}

	
	protected void loadCostActivity (int C_BPartner_ID)
	{	
		loadTableOIS(getReferenceCode(C_BPartner_ID));
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
