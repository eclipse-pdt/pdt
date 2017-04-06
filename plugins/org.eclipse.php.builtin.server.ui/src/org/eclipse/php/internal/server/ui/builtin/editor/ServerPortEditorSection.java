/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.builtin.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.php.internal.server.core.builtin.PHPServer;
import org.eclipse.php.internal.server.core.builtin.PHPServerConfiguration;
import org.eclipse.php.internal.server.core.builtin.command.ModifyPortCommand;
import org.eclipse.php.internal.server.ui.builtin.Messages;
import org.eclipse.php.internal.server.ui.builtin.PHPServerUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.wst.server.core.ServerPort;
import org.eclipse.wst.server.ui.editor.ServerEditorSection;

public class ServerPortEditorSection extends ServerEditorSection {
	protected PHPServerConfiguration fPHPServerConfiguration;

	protected boolean updating;

	protected Table ports;
	protected TableViewer viewer;

	protected PropertyChangeListener listener;

	/**
	 * ConfigurationPortEditorSection constructor comment.
	 */
	public ServerPortEditorSection() {
		super();
	}

	protected void addChangeListener() {
		listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (PHPServerConfiguration.MODIFY_PORT_PROPERTY.equals(event.getPropertyName())) {
					String id = (String) event.getOldValue();
					Integer i = (Integer) event.getNewValue();
					changePortNumber(id, i.intValue());
				}
			}
		};
		fPHPServerConfiguration.addPropertyChangeListener(listener);
	}

	/**
	 * 
	 * @param id
	 *            java.lang.String
	 * @param port
	 *            int
	 */
	protected void changePortNumber(String id, int port) {
		TableItem[] items = ports.getItems();
		int size = items.length;
		for (int i = 0; i < size; i++) {
			ServerPort sp = (ServerPort) items[i].getData();
			if (sp.getId().equals(id)) {
				items[i].setData(new ServerPort(id, sp.getName(), port, sp.getProtocol()));
				items[i].setText(1, port + ""); //$NON-NLS-1$
				/*
				 * if (i == selection) { selectPort(); }
				 */
				return;
			}
		}
	}

	/**
	 * Creates the SWT controls for this workbench part.
	 *
	 * @param parent
	 *            the parent control
	 */
	@Override
	public void createSection(Composite parent) {
		super.createSection(parent);
		FormToolkit toolkit = getFormToolkit(parent.getDisplay());

		Section section = toolkit.createSection(parent, ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED
				| ExpandableComposite.TITLE_BAR | Section.DESCRIPTION | ExpandableComposite.FOCUS_TITLE);
		section.setText(Messages.configurationEditorPortsSection);
		section.setDescription(Messages.configurationEditorPortsDescription);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));

		// ports
		Composite composite = toolkit.createComposite(section);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 8;
		layout.marginWidth = 8;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.FILL_HORIZONTAL));
		IWorkbenchHelpSystem whs = PlatformUI.getWorkbench().getHelpSystem();
		// whs.setHelp(composite, ContextIds.CONFIGURATION_EDITOR_PORTS);
		toolkit.paintBordersFor(composite);
		section.setClient(composite);

		ports = toolkit.createTable(composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		ports.setHeaderVisible(true);
		ports.setLinesVisible(true);
		// whs.setHelp(ports, ContextIds.CONFIGURATION_EDITOR_PORTS_LIST);

		TableLayout tableLayout = new TableLayout();

		TableColumn col = new TableColumn(ports, SWT.NONE);
		col.setText(Messages.configurationEditorPortNameColumn);
		ColumnWeightData colData = new ColumnWeightData(15, 150, true);
		tableLayout.addColumnData(colData);

		col = new TableColumn(ports, SWT.NONE);
		col.setText(Messages.configurationEditorPortValueColumn);
		colData = new ColumnWeightData(8, 80, true);
		tableLayout.addColumnData(colData);

		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL);
		data.widthHint = 230;
		data.heightHint = 100;
		ports.setLayoutData(data);
		ports.setLayout(tableLayout);

		viewer = new TableViewer(ports);
		viewer.setColumnProperties(new String[] { "name", "port" }); //$NON-NLS-1$ //$NON-NLS-2$

		initialize();
	}

	protected void setupPortEditors() {
		viewer.setCellEditors(new CellEditor[] { null, new TextCellEditor(ports) });

		ICellModifier cellModifier = new ICellModifier() {
			@Override
			public Object getValue(Object element, String property) {
				ServerPort sp = (ServerPort) element;
				if (sp.getPort() < 0)
					return "-"; //$NON-NLS-1$
				return sp.getPort() + ""; //$NON-NLS-1$
			}

			@Override
			public boolean canModify(Object element, String property) {
				if ("port".equals(property)) //$NON-NLS-1$
					return true;

				return false;
			}

			@Override
			public void modify(Object element, String property, Object value) {
				try {
					Item item = (Item) element;
					ServerPort sp = (ServerPort) item.getData();
					int port = Integer.parseInt((String) value);
					execute(new ModifyPortCommand(fPHPServerConfiguration, sp.getId(), port));
				} catch (Exception ex) {
					// ignore
				}
			}
		};
		viewer.setCellModifier(cellModifier);

		// preselect second column (Windows-only)
		String os = System.getProperty("os.name"); //$NON-NLS-1$
		if (os != null && os.toLowerCase().indexOf("win") >= 0) {
			ports.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					try {
						int n = ports.getSelectionIndex();
						viewer.editElement(ports.getItem(n).getData(), 1);
					} catch (Exception e) {
						// ignore
					}
				}
			});
		}
	}

	@Override
	public void dispose() {
		if (fPHPServerConfiguration != null)
			fPHPServerConfiguration.removePropertyChangeListener(listener);
	}

	/*
	 * (non-Javadoc) Initializes the editor part with a site and input.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);

		PHPServer ts = (PHPServer) server.loadAdapter(PHPServer.class, null);
		try {
			fPHPServerConfiguration = ts.getPHPServerConfiguration();
		} catch (Exception e) {
			// ignore
		}
		addChangeListener();
		initialize();
	}

	/**
	 * Initialize the fields in this editor.
	 */
	protected void initialize() {
		if (ports == null)
			return;

		ports.removeAll();

		Iterator<ServerPort> iterator = fPHPServerConfiguration.getServerPorts().iterator();
		while (iterator.hasNext()) {
			ServerPort port = (ServerPort) iterator.next();
			TableItem item = new TableItem(ports, SWT.NONE);
			String portStr = "-"; //$NON-NLS-1$
			if (port.getPort() >= 0)
				portStr = port.getPort() + ""; //$NON-NLS-1$
			String[] s = new String[] { port.getName(), portStr };
			item.setText(s);
			item.setImage(PHPServerUIPlugin.getImage(PHPServerUIPlugin.IMG_PORT));
			item.setData(port);
		}

		if (readOnly) {
			viewer.setCellEditors(new CellEditor[] { null, null });
			viewer.setCellModifier(null);
		} else {
			setupPortEditors();
		}
	}
}
