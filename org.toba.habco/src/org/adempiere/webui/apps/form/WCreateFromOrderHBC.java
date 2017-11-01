package org.adempiere.webui.apps.form;

import static org.compiere.model.SystemIDs.COLUMN_C_INVOICELINE_M_PRODUCT_ID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromOrderHBC;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;

public class WCreateFromOrderHBC extends CreateFromOrderHBC implements EventListener<Event>, ValueChangeListener
{
	/** Window No               */
	private int p_WindowNo;
	private WCreateFromWindow window;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());

	protected Label requisitionLabel = new Label();
	protected Listbox requisitionField = ListboxFactory.newDropdownListbox();
	protected Label productLabel = new Label();
	protected WEditor productField;
	/*@win temporary commented
	protected Label chargeLabel = new Label();
	protected WEditor chargeField;
	protected Label projectLabel = new Label();
	protected WEditor projectField;
	*/
	protected Label dateRequiredLabel = new Label();
	protected WEditor dateRequiredField;

	public WCreateFromOrderHBC(GridTab tab) {
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

	public boolean dynInit() throws Exception
	{
		log.config("");

		super.dynInit();

		window.setTitle(getTitle());

		initBPartner();

		return true;
	}   //  dynInit

	protected void zkInit() throws Exception
	{
		int C_Order_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Order_ID");
		MOrder order = new MOrder(Env.getCtx(),C_Order_ID,null);

		requisitionLabel.setText(Msg.getElement(Env.getCtx(), "M_Requisition_ID", false));
		productLabel.setText(Msg.getElement(Env.getCtx(), "M_Product_ID", false));
		/* @win temporary commented
		chargeLabel.setText(Msg.getElement(Env.getCtx(), "C_Charge_ID", false));
		projectLabel.setText(Msg.getElement(Env.getCtx(), "C_Project_ID", false));
		*/
		dateRequiredLabel.setText(Msg.getElement(Env.getCtx(), "DateRequired", false));

		Borderlayout parameterLayout = new Borderlayout();
		parameterLayout.setHeight("110px");
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

		row.appendChild(dateRequiredLabel.rightAlign());
		if (dateRequiredField != null)
			row.appendChild(dateRequiredField.getComponent());	

		row.appendChild(requisitionLabel.rightAlign());
		requisitionField.setHflex("1");
		row.appendChild(requisitionField);

		if (order.isSOTrx()) {
			requisitionLabel.setVisible(false);
			requisitionField.setVisible(false);
		}		

		row = rows.newRow();

		row.appendChild(productLabel.rightAlign());
		if (productField != null)
			row.appendChild(productField.getComponent());

		/*@win temporary commented
		row.appendChild(chargeLabel.rightAlign());
		if (chargeField != null)
			row.appendChild(chargeField.getComponent());

		row = rows.newRow();
		row.appendChild(projectLabel.rightAlign());
		if (projectField != null)
			row.appendChild(projectField.getComponent());	
		*/

	}

	private boolean 	m_actionActive = false;

	/**
	 *  Action Listener
	 *  @param e event
	 * @throws Exception 
	 */
	public void onEvent(Event e) throws Exception
	{
		if (m_actionActive)
			return;
		
		m_actionActive = true;
		ListItem li = null;

		int C_Order_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Order_ID");
		if (C_Order_ID<=0) {
			return;
		}
		
		MOrder order = new MOrder(Env.getCtx(),C_Order_ID,null);
		if (order.isSOTrx())
			return;
		
		Timestamp dateRequired = null;
		int M_Requisition_ID = 0;
		int M_Product_ID = 0;
		int C_Charge_ID = 0;
		int C_Project_ID = 0;
		int salesRepID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "SalesRep_ID");

		//  Requisition
		if (e.getTarget().equals(dateRequiredField)) {
			dateRequired = (Timestamp) dateRequiredField.getValue();
		} else if (e.getTarget().equals(requisitionField)) {
			li = requisitionField.getSelectedItem();
			if (li != null && li.getValue() != null)
				M_Requisition_ID = ((Integer) li.getValue()).intValue();
		} else if (e.getTarget().equals(productField)) {
			if (productField.getValue()!=null)
				M_Product_ID = (Integer) productField.getValue();
		/*@win temporary commented	
		} else if (e.getTarget().equals(chargeField)) {
			if (chargeField.getValue()!=null)
				C_Charge_ID = (Integer) chargeField.getValue();
		} else if (e.getTarget().equals(projectField)) {
			if (projectField.getValue()!=null)
				C_Project_ID = (Integer) projectField.getValue();
		*/
		} 
		
		loadRequisition(M_Requisition_ID, M_Product_ID, dateRequired, C_Charge_ID, salesRepID);

		m_actionActive = false;
	}

	/**
	 *  Change Listener
	 *  @param e event
	 */
	public void valueChange (ValueChangeEvent e)
	{
		if (log.isLoggable(Level.CONFIG)) log.config(e.getPropertyName() + "=" + e.getNewValue());
		//  BPartner - load Order/Invoice/Shipment
		int M_Requisition_ID = 0;
		Timestamp dateRequired = null;
		int M_Product_ID = 0;
		int C_Charge_ID = 0;
		//	int C_Project_ID = 0;

		int salesRepID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "SalesRep_ID");

		if (e.getPropertyName().equals("M_Requisition_ID"))
		{
			if (e.getNewValue() != null)
				M_Requisition_ID = ((Integer)e.getNewValue()).intValue();

			if (productField.getValue() != null)
				M_Product_ID = (Integer) productField.getValue();

			dateRequired = (Timestamp) dateRequiredField.getValue();

			//	if (projectField.getValue() != null)
			//	C_Project_ID =  ((Integer)e.getNewValue()).intValue();

			//			if (chargeField.getValue() != null)
			//				C_Charge_ID = (Integer) chargeField.getValue();

			loadRequisition(M_Requisition_ID, M_Product_ID, dateRequired, C_Charge_ID, salesRepID);

		}

		else if (e.getPropertyName().equals("M_Product_ID"))
		{
			ListItem li = requisitionField.getSelectedItem();
			if (li != null && li.getValue() != null)
				M_Requisition_ID = ((Integer) li.getValue()).intValue();

			if (e.getNewValue() != null)
				M_Product_ID =  ((Integer)e.getNewValue()).intValue();

			dateRequired = (Timestamp) dateRequiredField.getValue();

			//	if (projectField.getValue() != null)
			//	C_Project_ID =  (Integer) projectField.getValue();

			//			if (chargeField.getValue() != null)
			//				C_Charge_ID = (Integer) chargeField.getValue();

			loadRequisition(M_Requisition_ID, M_Product_ID, dateRequired,  C_Charge_ID, salesRepID);
		}

		else if (e.getPropertyName().equals("DateRequired"))
		{
			ListItem li = requisitionField.getSelectedItem();
			if (li != null && li.getValue() != null)
				M_Requisition_ID = ((Integer) li.getValue()).intValue();

			if (productField.getValue() != null)
				M_Product_ID = (Integer) productField.getValue();

			dateRequired =  ((Timestamp)e.getNewValue());

			//	if (projectField.getValue() != null)
			//	C_Project_ID =  (Integer) projectField.getValue();

			//			if (chargeField.getValue() != null)
			//				C_Charge_ID = (Integer) chargeField.getValue();

			loadRequisition(M_Requisition_ID, M_Product_ID, dateRequired,  C_Charge_ID, salesRepID);
		}

		/*	else if (e.getPropertyName().equals("C_Project_ID"))
		{
			ListItem li = requisitionField.getSelectedItem();
			if (li != null && li.getValue() != null)
				M_Requisition_ID = ((Integer) li.getValue()).intValue();

			if (productField.getValue() != null)
				M_Product_ID = (Integer) productField.getValue();

			dateRequired = (Timestamp) dateRequiredField.getValue();

			if (e.getNewValue() != null)
				C_Project_ID =  ((Integer)e.getNewValue()).intValue();

//			if (chargeField.getValue() != null)
//				C_Charge_ID = (Integer) chargeField.getValue();

			loadRequisition(M_Requisition_ID, M_Product_ID, dateRequired,  C_Charge_ID, salesRepID);
		}*/

		else if (e.getPropertyName().equals("C_Charge_ID"))
		{
			ListItem li = requisitionField.getSelectedItem();
			if (li != null && li.getValue() != null)
				M_Requisition_ID = ((Integer) li.getValue()).intValue();

			if (productField.getValue() != null)
				M_Product_ID = (Integer) productField.getValue();

			dateRequired = (Timestamp) dateRequiredField.getValue();

			//	if (projectField.getValue() != null)
			//	C_Project_ID =  (Integer) projectField.getValue();

			if (e.getNewValue() != null)
				C_Charge_ID =  ((Integer)e.getNewValue()).intValue();

			loadRequisition(M_Requisition_ID, M_Product_ID, dateRequired,  C_Charge_ID, salesRepID);
		}

		window.tableChanged(null);
	}   //  vetoableChange

	/**************************************************************************
	 *  Load BPartner Field
	 *  @param forInvoice true if Invoices are to be created, false receipts
	 *  @throws Exception if Lookups cannot be initialized
	 */
	protected void initBPartner() throws Exception
	{

		//  load Product
		int AD_Column_ID = COLUMN_C_INVOICELINE_M_PRODUCT_ID;
		MLookup lookupProduct = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		productField = new WSearchEditor ("M_Product_ID", true, false, true, lookupProduct);
		//	
		dateRequiredField = new WDateEditor("DateRequired", false, false, true, "DateRequired");

		int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
		//projectField = new WSearchEditor ("C_Project_ID", false, true, false, lookupProject);
		//	

		int C_Project_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Project_ID");
		//projectField.setValue(new Integer(C_Project_ID));

		//chargeField = new WSearchEditor ("C_Charge_ID", true, false, true, lookupCharge);

		int salesRepID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "SalesRep_ID");
		//  initial loading
		initBPRequisitionDetails(C_BPartner_ID, C_Project_ID, salesRepID);
	}   //  initBPartner

	/**
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner
	 *  @param forInvoice for invoice
	 */
	protected void initBPRequisitionDetails (int C_BPartner_ID, int C_Project_ID, int salesRepID)
	{
		if (log.isLoggable(Level.CONFIG)) log.config("C_BPartner_ID=" + C_BPartner_ID);

		KeyNamePair pp = new KeyNamePair(0,"");
		requisitionField.removeActionListener(this);
		requisitionField.removeAllItems();
		requisitionField.addItem(pp);

		int C_Order_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Order_ID");
		MOrder order = new MOrder(Env.getCtx(),C_Order_ID,null);

		if (!order.isSOTrx()){
			ArrayList<KeyNamePair> list = loadRequisitionData(C_BPartner_ID);
			for(KeyNamePair knp : list)
				requisitionField.addItem(knp);
		}

		requisitionField.setSelectedIndex(0);
		requisitionField.addActionListener(this);
		productField.addValueChangeListener(this);
		dateRequiredField.addValueChangeListener(this);
		//projectField.addValueChangeListener(this);
		//chargeField.addValueChangeListener(this);


	}   //  initBPartnerOIS

	/**
	 *  Load Data - Requisition
	 *  @param M_Requisition_ID Requisition
	 */
	protected void loadRequisition (int m_Requisition_ID, int M_Product_ID, Timestamp dateRequired, int C_Charge_ID, int salesRepID)
	{
		loadTableOIS(getRequisitionData(m_Requisition_ID, M_Product_ID, dateRequired, C_Charge_ID, salesRepID));
	}   //  Load Requisition

	/**
	 *  Load Order/Invoice/Shipment data into Table
	 *  @param data data
	 */
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

}
