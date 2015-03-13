/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.util.NetworkUtil.NetworkAddress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * Dialog for choosing Zend debugger client IPs.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ConfigureHostsDialog extends MessageDialog {

	private class LabelProvider extends BaseLabelProvider implements
			ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return ((NetworkAddress) element).getIP();
			case 1: {
				switch (((NetworkAddress) element).getType()) {
				case NetworkAddress.TYPE_PUBLIC:
					return "public"; //$NON-NLS-1$
				case NetworkAddress.TYPE_PRIVATE:
					return "private"; //$NON-NLS-1$
				case NetworkAddress.TYPE_LOOPBACK:
					return "localhost"; //$NON-NLS-1$
				default:
					break;
				}
				return null;
			}
			default:
				break;
			}
			return null;
		}

	}

	private List<NetworkAddress> availableIPs;
	private CheckboxTableViewer ipsTableViewer;
	private List<NetworkAddress> selectedIPs = new ArrayList<NetworkAddress>();

	/**
	 * Opens configure IPs dialog.
	 * 
	 * @param availableIPs
	 */
	protected ConfigureHostsDialog(List<NetworkAddress> availableIPs) {
		super(
				PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				Messages.ConfigureIPsDialog_Configure_client_IPs,
				null,
				Messages.ConfigureIPsDialog_The_following_IPs_have_been_detected,
				MessageDialog.INFORMATION, new String[] {
						Messages.ConfigureIPsDialog_OK_button,
						Messages.ConfigureIPsDialog_Cancel_button }, 0);
		this.availableIPs = availableIPs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		getShell().pack(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.
	 * swt.widgets.Composite)
	 */
	@Override
	protected Control createCustomArea(Composite parent) {
		((GridData) messageLabel.getLayoutData()).widthHint = 350;
		return createResultTable(parent);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		// OK pressed
		if (buttonId == 0) {
			Object[] selected = ipsTableViewer.getCheckedElements();
			for (Object s : selected) {
				selectedIPs.add((NetworkAddress) s);
			}
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private Control createResultTable(Composite composite) {
		// Parent composite layout
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.marginHeight = 0;
		compositeLayout.marginWidth = 0;
		compositeLayout.numColumns = 2;
		composite.setLayout(compositeLayout);
		// Result table composite
		Composite tableComposite = new Composite(composite, SWT.NONE);
		GridLayout tableCompositeLayout = new GridLayout();
		tableCompositeLayout.marginHeight = 0;
		tableCompositeLayout.marginWidth = 0;
		tableComposite.setLayout(tableCompositeLayout);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		// Buttons composite
		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		GridLayout buttonsCompositeLayout = new GridLayout();
		buttonsCompositeLayout.marginHeight = 0;
		buttonsCompositeLayout.marginWidth = 0;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false));
		GridData tableGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING,
				false, false);
		Table ipsTable = new Table(tableComposite, SWT.CHECK | SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ipsTable.setLayoutData(tableGridData);
		ipsTable.setHeaderVisible(true);
		ipsTable.setLinesVisible(true);
		ipsTableViewer = new CheckboxTableViewer(ipsTable);
		ipsTableViewer.setLabelProvider(new LabelProvider());
		ipsTableViewer.setContentProvider(new ArrayContentProvider());
		TableColumn ipColumn = new TableColumn(ipsTable, SWT.LEFT);
		ipColumn.setText(Messages.ConfigureIPsDialog_Address_column);
		TableColumn typeColumn = new TableColumn(ipsTable, SWT.LEFT);
		typeColumn.setText(Messages.ConfigureIPsDialog_Type_column);
		TableColumnLayout clayout = new TableColumnLayout();
		clayout.setColumnData(ipColumn, new ColumnWeightData(50, true));
		clayout.setColumnData(typeColumn, new ColumnWeightData(50, true));
		ipsTable.getParent().setLayout(clayout);
		ipsTableViewer.setInput(availableIPs);
		ipsTableViewer.setAllChecked(false);
		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText(Messages.ConfigureIPsDialog_Select_all_button);
		selectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		selectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ipsTableViewer.setAllChecked(true);
			}
		});
		Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setText(Messages.ConfigureIPsDialog_Deselect_all_button);
		deselectAll
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		deselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ipsTableViewer.setAllChecked(false);
			}
		});
		return composite;
	}

	/**
	 * Returns chosen IP addresses.
	 * 
	 * @return chosen IP addresses
	 */
	public List<NetworkAddress> getSelectedIPs() {
		return selectedIPs;
	}

}
