/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.server.internal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.core.ServersManager;
import org.eclipse.php.server.ui.ServerEditDialog;
import org.eclipse.php.server.ui.wizard.WizardModel;
import org.eclipse.php.ui.preferences.ui.IPreferenceConfigurationBlock;
import org.eclipse.php.ui.preferences.ui.ScrolledCompositeImpl;
import org.eclipse.php.ui.util.StatusInfo;
import org.eclipse.php.ui.util.StatusUtil;
import org.eclipse.php.ui.wizards.fields.DialogField;
import org.eclipse.php.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.ui.wizards.fields.IListAdapter;
import org.eclipse.php.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPServersConfigurationBlock implements IPreferenceConfigurationBlock {

	private static final int IDX_ADD = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;

	private IStatus fServersStatus;
	private ListDialogField fServersList;
	private PreferencePage fMainPreferencePage;

	public PHPServersConfigurationBlock(PreferencePage mainPreferencePage, OverlayPreferenceStore store) {
		Assert.isNotNull(mainPreferencePage);
		Assert.isNotNull(store);
		fMainPreferencePage = mainPreferencePage;
	}

	public Control createControl(Composite parent) {

		ServerAdapter adapter = new ServerAdapter();
		String buttons[] = new String[] { "New", "Edit", "Remove" };
		fServersList = new ListDialogField(adapter, buttons, new PHPServersLabelProvider()) {
			protected boolean managedButtonPressed(int index) {
				if (index == getRemoveButtonIndex()) {
					handleRemoveServer();
				}
				return super.managedButtonPressed(index);
			}
		};
		fServersList.setDialogFieldListener(adapter);
		fServersList.setRemoveButtonIndex(IDX_REMOVE);

		String[] columnsHeaders = new String[] { "Name", "URL", "Publish" };
		ColumnLayoutData[] layoutDatas = new ColumnLayoutData[] { new ColumnWeightData(40), new ColumnWeightData(40), new ColumnWeightData(20) };
		fServersList.setTableColumns(new ListDialogField.ColumnsDescription(layoutDatas, columnsHeaders, true));

		if (fServersList.getSize() > 0) {
			fServersList.selectFirstElement();
		} else {
			fServersList.enableButton(IDX_EDIT, false);
		}

		fServersStatus = new StatusInfo();

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;

		PixelConverter conv = new PixelConverter(parent);

		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite composite = new Composite(scrolledCompositeImpl, SWT.NONE);
		composite.setLayout(layout);
		scrolledCompositeImpl.setContent(composite);

		scrolledCompositeImpl.setLayout(layout);
		scrolledCompositeImpl.setFont(parent.getFont());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = conv.convertWidthInCharsToPixels(50);
		Control listControl = fServersList.getListControl(composite);
		listControl.setLayoutData(data);

		Control buttonsControl = fServersList.getButtonBox(composite);
		buttonsControl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));

		addFiller(composite);

		Point size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledCompositeImpl.setMinSize(size.x, size.y);

		return scrolledCompositeImpl;
	}

	private void addFiller(Composite composite) {
		PixelConverter pixelConverter = new PixelConverter(composite);
		Label filler = new Label(composite, SWT.LEFT);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		gd.heightHint = pixelConverter.convertHeightInCharsToPixels(1) / 2;
		filler.setLayoutData(gd);
	}

	protected void sideButtonPressed(int index) {
		if (index == IDX_ADD) {
			Server newServer = getServerFromWizard();
			if (newServer != null) {
				fServersList.addElement(newServer);
				ServersManager.addServer(newServer);
				ServersManager.save();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						fServersList.refresh();
					}
				});
			}
		} else if (index == IDX_EDIT) {
			handleEditServerButtonSelected();
			fServersList.refresh();
		}
	}

	protected Server getServerFromWizard() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		NullProgressMonitor monitor = new NullProgressMonitor();
		Server theServer = null;
		ServerWizard wizard = new ServerWizard();
		ClosableWizardDialog dialog = new ClosableWizardDialog(shell, wizard);
		if (dialog.open() == Window.CANCEL) {
			monitor.setCanceled(true);
			return null;
		}
		theServer = (Server) wizard.getRootFragment().getWizardModel().getObject(WizardModel.SERVER);
		return theServer;
	}

	protected void handleEditServerButtonSelected() {
		Server server = (Server) fServersList.getSelectedElements().get(0);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		NullProgressMonitor monitor = new NullProgressMonitor();
		ServerEditDialog dialog = new ServerEditDialog(shell, server);
		if (dialog.open() == Window.CANCEL) {
			monitor.setCanceled(true);
			return;
		}
		ServersManager.save();
	}

	protected void handleRemoveServer() {
		Server server = (Server) fServersList.getSelectedElements().get(0);
		ServersManager.removeServer(server.getName());
		ServersManager.save();
	}

	protected void updateStatus() {
		fMainPreferencePage.setValid(fServersStatus.isOK());
		StatusUtil.applyToStatusLine(fMainPreferencePage, fServersStatus);
	}

	public void initialize() {
		List servers = new ArrayList();
		populateServerList(servers);
		fServersList.setElements(servers);
	}

	public void dispose() {
		// nothing to dispose
	}

	public void performDefaults() {
		// Do nothing - We do not have the defaults button on this page.
	}

	public void performOk() {
		// Do nothing
	}

	protected void populateServerList(List serverList) {
		Server[] servers = ServersManager.getServers();

		if (serverList == null)
			serverList = new ArrayList();

		if (servers != null) {
			int size = servers.length;
			for (int i = 0; i < size; i++) {
				Server server = servers[i];
				serverList.add(server);
			}
		}
	}

	private class ServerAdapter implements IListAdapter, IDialogFieldListener {

		private boolean hasActiveSelection(List selectedElements) {
			return selectedElements.size() == 1;
		}

		public void dialogFieldChanged(DialogField field) {
		}

		public void customButtonPressed(ListDialogField field, int index) {
			sideButtonPressed(index);
		}

		public void doubleClicked(ListDialogField field) {
			sideButtonPressed(IDX_EDIT);
		}

		public void selectionChanged(ListDialogField field) {
			List selectedElements = field.getSelectedElements();
			field.enableButton(IDX_EDIT, hasActiveSelection(selectedElements));
			field.enableButton(IDX_REMOVE, hasActiveSelection(selectedElements));
		}
	}

	private class PHPServersLabelProvider extends LabelProvider implements ITableLabelProvider, IFontProvider {
		public Font getFont(Object element) {
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			Server server = null;
			if (element instanceof Server) {
				server = (Server) element;
			} else {
				return super.getText(element);
			}
			if (columnIndex == 0) {
				return server.getName();
			} else if (columnIndex == 1) {
				return server.getHost();
			} else if (columnIndex == 2) {

				return server.canPublish() ? "Yes" : "No";
			}
			return element.toString();
		}
	}

}
