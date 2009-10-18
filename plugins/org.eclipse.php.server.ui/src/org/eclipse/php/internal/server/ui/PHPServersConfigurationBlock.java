/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.IListAdapter;
import org.eclipse.php.internal.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPServersConfigurationBlock implements
		IPreferenceConfigurationBlock {

	private static final int IDX_ADD = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;
	private static final int IDX_DEFAULT = 3;

	private IStatus fServersStatus;
	private ListDialogField fServersList;
	private PreferencePage fMainPreferencePage;

	public PHPServersConfigurationBlock(PreferencePage mainPreferencePage,
			OverlayPreferenceStore store) {
		Assert.isNotNull(mainPreferencePage);
		Assert.isNotNull(store);
		fMainPreferencePage = mainPreferencePage;
	}

	public Control createControl(Composite parent) {

		ServerAdapter adapter = new ServerAdapter();
		String buttons[] = new String[] {
				PHPServerUIMessages
						.getString("PHPServersConfigurationBlock.new"), PHPServerUIMessages.getString("PHPServersConfigurationBlock.edit"), PHPServerUIMessages.getString("PHPServersConfigurationBlock.remove"), PHPServerUIMessages.getString("PHPServersConfigurationBlock.setDefault") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		fServersList = new ListDialogField(adapter, buttons,
				new PHPServersLabelProvider()) {
			protected boolean managedButtonPressed(int index) {
				if (index == getRemoveButtonIndex()) {
					handleRemoveServer();
				}
				return super.managedButtonPressed(index);
			}
		};
		fServersList.setDialogFieldListener(adapter);
		fServersList.setRemoveButtonIndex(IDX_REMOVE);

		String[] columnsHeaders = new String[] {
				PHPServerUIMessages
						.getString("PHPServersConfigurationBlock.name"), PHPServerUIMessages.getString("PHPServersConfigurationBlock.url") }; //$NON-NLS-1$ //$NON-NLS-2$
		ColumnLayoutData[] layoutDatas = new ColumnLayoutData[] {
				new ColumnWeightData(40), new ColumnWeightData(40) };
		fServersList.setTableColumns(new ListDialogField.ColumnsDescription(
				layoutDatas, columnsHeaders, true));

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

		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(
				parent, SWT.V_SCROLL | SWT.H_SCROLL);
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
		buttonsControl.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_BEGINNING));

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
		} else if (index == IDX_DEFAULT) {
			List selectedElements = fServersList.getSelectedElements();
			if (selectedElements.size() > 0) {
				Server server = (Server) selectedElements.get(0);
				ServersManager.setDefaultServer(null, server);
				ServersManager.save();
				fServersList.refresh();
			}
		}
	}

	protected Server getServerFromWizard() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		NullProgressMonitor monitor = new NullProgressMonitor();
		Server theServer = null;
		ServerWizard wizard = new ServerWizard();
		ClosableWizardDialog dialog = new ClosableWizardDialog(shell, wizard);
		if (dialog.open() == Window.CANCEL) {
			monitor.setCanceled(true);
			return null;
		}
		theServer = (Server) wizard.getRootFragment().getWizardModel()
				.getObject(WizardModel.SERVER);
		return theServer;
	}

	protected void handleEditServerButtonSelected() {
		Server server = (Server) fServersList.getSelectedElements().get(0);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
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
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fServersList.refresh();
			}
		});
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

	protected boolean isDefault(Server element) {
		return ServersManager.getDefaultServer(null) == element;
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
			// Do not allow the removal of the last element
			field.enableButton(IDX_REMOVE, hasActiveSelection(selectedElements)
					&& field.getElements().size() > 1);

			// handle default button enablement
			if (selectedElements.size() == 1) {
				field.enableButton(IDX_DEFAULT, !selectedElements.get(0)
						.equals(ServersManager.getDefaultServer(null)));
			} else {
				field.enableButton(IDX_DEFAULT, false);
			}
		}
	}

	private class PHPServersLabelProvider extends LabelProvider implements
			ITableLabelProvider, IFontProvider {

		public Font getFont(Object element) {
			if (isDefault((Server) element)) {
				return JFaceResources.getFontRegistry().getBold(
						JFaceResources.DIALOG_FONT);
			}
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return ServersPluginImages.get(ServersPluginImages.IMG_SERVER);
			}
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
				String serverName = server.getName();
				if (isDefault((Server) element)) {
					serverName += PHPServerUIMessages
							.getString("PHPServersConfigurationBlock.workspaceDefault"); //$NON-NLS-1$
				}
				return serverName;
			} else if (columnIndex == 1) {
				return server.getBaseURL();
			}
			return element.toString();
		}
	}

}
