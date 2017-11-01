package org.adempiere.webui.apps.form;

import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromPaymentHBC;
import org.compiere.model.GridTab;
import org.compiere.util.CLogger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Vlayout;

public class WCreateFromPayment extends CreateFromPaymentHBC implements EventListener<Event>, ValueChangeListener {

	/** Window No               */
	//private int p_WindowNo;
	private WCreateFromWindow window;
	
	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	public boolean dynInit() throws Exception
	{
		log.config("");
		super.dynInit();		
		window.setTitle(getTitle());
		initInvoice();
		loadInvoice();
		return true;
	}   //  dynInit
	
	public WCreateFromPayment(GridTab mTab) {
		super(mTab);
		log.info(getGridTab().toString());
		window = new WCreateFromWindow(this, getGridTab().getWindowNo());
		
		//	p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			zkInit();
			setInitOK(true);
		//	initBPartner(false);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
			throw new AdempiereException(e.getMessage());
		}
		AEnv.showWindow(window);
		
	}
	
	protected void zkInit() throws Exception{		
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
	}

	@Override
	public void valueChange(ValueChangeEvent evt) {
		loadInvoice();
	}
	private boolean 	m_actionActive = false;
	
	@Override
	public void onEvent(Event arg0) throws Exception {
		if (m_actionActive)
			return;
		m_actionActive = true;	
		loadInvoice();
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
	
	protected void initInvoice() throws Exception
	{

	//  load Product
//		int AD_Column_ID = COLUMN_C_INVOICELINE_M_PRODUCT_ID;
//		
//		MLookup lookupProject = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 1349, DisplayType.Search);
//		//projectField = new WSearchEditor ("C_Project_ID", false, true, false, lookupProject);
//		   //	
//		int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
//
//		int C_Project_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Project_ID");
//		//projectField.setValue(new Integer(C_Project_ID));
//
//		MLookup lookupCharge = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 3050, DisplayType.Search);
//		//chargeField = new WSearchEditor ("C_Charge_ID", true, false, true, lookupCharge);
//   
//		int salesRepID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "SalesRep_ID");
		//  initial loading
		
	}  

	protected void loadInvoice ()
	{
		loadTableOIS(getInvoiceData());
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
