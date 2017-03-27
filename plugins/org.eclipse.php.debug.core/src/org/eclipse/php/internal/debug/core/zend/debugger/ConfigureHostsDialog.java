/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.util.NetworkUtil;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Dialog for choosing Zend debugger client IPs.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ConfigureHostsDialog extends MessageDialog {

	private class AddressLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			Inet4Address address = (Inet4Address) element;
			return address.getHostAddress();
		}

		@Override
		public Image getImage(Object element) {
			Inet4Address address = (Inet4Address) element;
			if (!detectedIPs.contains(address))
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			Inet4Address address = (Inet4Address) element;
			if (!detectedIPs.contains(address))
				return PHPDebugCoreMessages.ConfigureHostsDialog_Address_could_not_be_detected;
			return null;
		}

	}

	private class TypeLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			Inet4Address address = (Inet4Address) element;
			switch (NetworkUtil.getType(address)) {
			case NetworkUtil.TYPE_PUBLIC:
				return "public"; //$NON-NLS-1$
			case NetworkUtil.TYPE_PRIVATE:
				return "private"; //$NON-NLS-1$
			case NetworkUtil.TYPE_LOOPBACK:
				return "localhost"; //$NON-NLS-1$
			default:
				break;
			}
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			Inet4Address address = (Inet4Address) element;
			if (!detectedIPs.contains(address))
				return PHPDebugCoreMessages.ConfigureHostsDialog_Address_could_not_be_detected;
			return null;
		}

	}

	private List<Inet4Address> inputIPs;
	private List<Inet4Address> detectedIPs;
	private List<Inet4Address> mergedIPs;
	private Map<Inet4Address, Boolean> initialState = new LinkedHashMap<Inet4Address, Boolean>();
	private CheckboxTableViewer ipsTableViewer;
	private Button resetButton;
	private List<Inet4Address> selectedIPs = new ArrayList<Inet4Address>();

	/**
	 * Opens configure IPs dialog.
	 * 
	 * @param inputIPs
	 */
	public ConfigureHostsDialog(List<Inet4Address> inputIPs, List<Inet4Address> detectedIPs) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				PHPDebugCoreMessages.ConfigureHostsDialog_Configure_client_IPs, null,
				PHPDebugCoreMessages.ConfigureHostsDialog_Select_addresses, MessageDialog.INFORMATION,
				new String[] { PHPDebugCoreMessages.ConfigureHostsDialog_OK_button,
						PHPDebugCoreMessages.ConfigureHostsDialog_Cancel_button },
				0);
		mergeAddresses(inputIPs, detectedIPs);
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
		((GridData) messageLabel.getLayoutData()).widthHint = 400;
		return createResultTable(parent);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		// OK pressed
		if (buttonId == 0) {
			Object[] selected = ipsTableViewer.getCheckedElements();
			for (Object s : selected) {
				selectedIPs.add((Inet4Address) s);
			}
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void mergeAddresses(List<Inet4Address> allIPs, List<Inet4Address> detectedIPs) {
		this.inputIPs = allIPs;
		this.detectedIPs = detectedIPs;
		mergedIPs = new ArrayList<Inet4Address>();
		mergedIPs.addAll(allIPs);
		for (Inet4Address ip : detectedIPs) {
			if (!mergedIPs.contains(ip))
				mergedIPs.add(ip);
		}
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
		tableComposite.setLayout(new TableColumnLayout());
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// Buttons composite
		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		GridLayout buttonsCompositeLayout = new GridLayout();
		buttonsCompositeLayout.marginHeight = 0;
		buttonsCompositeLayout.marginWidth = 0;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		GridData tableGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		Table ipsTable = new Table(tableComposite,
				SWT.CHECK | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ipsTable.setLayoutData(tableGridData);
		ipsTable.setHeaderVisible(true);
		ipsTable.setLinesVisible(true);
		ipsTableViewer = new CheckboxTableViewer(ipsTable);
		ipsTableViewer.setLabelProvider(new LabelProvider());
		ipsTableViewer.setContentProvider(new ArrayContentProvider());
		ColumnViewerToolTipSupport.enableFor(ipsTableViewer);
		createColumn(tableComposite, ipsTableViewer, PHPDebugCoreMessages.ConfigureHostsDialog_Address_column, 60,
				new AddressLabelProvider());
		createColumn(tableComposite, ipsTableViewer, PHPDebugCoreMessages.ConfigureHostsDialog_Type_column, 40,
				new TypeLabelProvider());
		ipsTableViewer.setInput(mergedIPs);
		setSelection();
		setInitialState();
		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText(PHPDebugCoreMessages.ConfigureHostsDialog_Select_all_button);
		selectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		selectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ipsTableViewer.setAllChecked(true);
				updateReset();
			}
		});
		Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setText(PHPDebugCoreMessages.ConfigureHostsDialog_Deselect_all_button);
		deselectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		deselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ipsTableViewer.setAllChecked(false);
				updateReset();
			}
		});
		resetButton = new Button(buttonsComposite, SWT.PUSH);
		resetButton.setText(PHPDebugCoreMessages.ConfigureHostsDialog_Reset_button);
		resetButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		resetButton.setEnabled(false);
		resetButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				restoreInitialState();
			}
		});
		final Button moveUp = new Button(buttonsComposite, SWT.PUSH);
		moveUp.setText(PHPDebugCoreMessages.ConfigureHostsDialog_Up_button);
		moveUp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		moveUp.setEnabled(false);
		moveUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveSelectionUp();
			}
		});
		final Button moveDown = new Button(buttonsComposite, SWT.PUSH);
		moveDown.setText(PHPDebugCoreMessages.ConfigureHostsDialog_Down_button);
		moveDown.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		moveDown.setEnabled(false);
		moveDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveSelectionDown();
			}
		});
		ipsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					if (structuredSelection.getFirstElement() != null) {
						moveUp.setEnabled(true);
						moveDown.setEnabled(true);
					} else {
						moveUp.setEnabled(false);
						moveDown.setEnabled(false);
					}
				}
				updateReset();
			}
		});
		return composite;
	}

	private void createColumn(Composite tableComposite, TableViewer viewer, String title, int weight,
			CellLabelProvider labelProvider) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setResizable(true);
		column.setMoveable(false);
		TableColumnLayout tableLayout = (TableColumnLayout) tableComposite.getLayout();
		ColumnLayoutData columnLayoutData = new ColumnWeightData(weight);
		tableLayout.setColumnData(column, columnLayoutData);
		if (labelProvider != null) {
			viewerColumn.setLabelProvider(labelProvider);
		}
	}

	private void updateReset() {
		boolean enable = false;
		Set<Inet4Address> initialOrder = initialState.keySet();
		int ordinal = 0;
		for (Inet4Address address : initialOrder) {
			if (!address.equals(mergedIPs.get(ordinal))
					|| ipsTableViewer.getChecked(address) != initialState.get(address)) {
				enable = true;
				break;
			}
			ordinal++;
		}
		resetButton.setEnabled(enable);
	}

	private void setSelection() {
		for (int i = 0; i < mergedIPs.size(); i++) {
			Inet4Address next = mergedIPs.get(i);
			if (inputIPs.contains(next))
				ipsTableViewer.setChecked(next, true);
			else
				ipsTableViewer.setChecked(next, false);
		}
	}

	/**
	 * Move the current selection in the build list down.
	 */
	private void moveSelectionDown() {
		ISelection selection = ipsTableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection) selection).getFirstElement();
			int index = mergedIPs.indexOf(selected);
			mergedIPs.remove(index);
			mergedIPs.add(index == mergedIPs.size() ? index : index + 1, (Inet4Address) selected);
			ipsTableViewer.setSelection(new StructuredSelection(selected));
			ipsTableViewer.setInput(mergedIPs);
		}
		updateReset();
	}

	/**
	 * Move the current selection in the build list up.
	 */
	private void moveSelectionUp() {
		ISelection selection = ipsTableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection) selection).getFirstElement();
			int index = mergedIPs.indexOf(selected);
			mergedIPs.remove(index);
			mergedIPs.add(index == 0 ? index : index - 1, (Inet4Address) selected);
			ipsTableViewer.setSelection(new StructuredSelection(selected));
			ipsTableViewer.setInput(mergedIPs);
		}
		updateReset();
	}

	private void setInitialState() {
		initialState = new LinkedHashMap<Inet4Address, Boolean>();
		for (Inet4Address address : mergedIPs) {
			initialState.put(address, false);
		}
		for (Object checked : ipsTableViewer.getCheckedElements()) {
			initialState.put((Inet4Address) checked, true);
		}
	}

	private void restoreInitialState() {
		mergeAddresses(inputIPs, detectedIPs);
		ipsTableViewer.setInput(mergedIPs);
		setSelection();
		setInitialState();
		resetButton.setEnabled(false);
	}

	/**
	 * Returns chosen IP addresses.
	 * 
	 * @return chosen IP addresses
	 */
	public List<Inet4Address> getSelectedIPs() {
		return selectedIPs;
	}

}
