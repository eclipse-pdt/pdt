/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * Dialog for managing PHP executable search results.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class PHPsSearchResultDialog extends MessageDialog {

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
				return ((PHPexeItem) element).getName();
			case 1:
				return ((PHPexeItem) element).getExecutable().getAbsolutePath();
			default:
				break;
			}
			return null;
		}

	}

	private List<PHPexeItem> results;
	private CheckboxTableViewer resultTableViewer;
	private ArrayList<PHPexeItem> phpExecs = new ArrayList<PHPexeItem>();

	protected PHPsSearchResultDialog(List<PHPexeItem> results, String message) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				Messages.PHPsSearchResultDialog_PHP_executables_search, null,
				message, MessageDialog.INFORMATION, new String[] {
						Messages.PHPsSearchResultDialog_Add,
						Messages.PHPsSearchResultDialog_Cancel }, 0);
		this.results = results;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		return createResultTable(parent);
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
		GridData tableCompositeGridData = new GridData(SWT.FILL, SWT.FILL,
				true, true);
		tableCompositeGridData.widthHint = 480;
		Table resultTable = new Table(tableComposite, SWT.CHECK | SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		resultTable.setLayoutData(tableCompositeGridData);
		resultTable.setHeaderVisible(true);
		resultTable.setLinesVisible(true);
		resultTableViewer = new CheckboxTableViewer(resultTable);
		resultTableViewer.setLabelProvider(new LabelProvider());
		resultTableViewer.setContentProvider(new ArrayContentProvider());
		TableColumn nameColumn = new TableColumn(resultTable, SWT.LEFT);
		nameColumn.setText(Messages.PHPsSearchResultDialog_Name);
		TableColumn locationColumn = new TableColumn(resultTable, SWT.LEFT);
		locationColumn.setText(Messages.PHPsSearchResultDialog_Location);
		TableColumnLayout clayout = new TableColumnLayout();
		clayout.setColumnData(nameColumn, new ColumnWeightData(40, true));
		clayout.setColumnData(locationColumn, new ColumnWeightData(60, true));
		resultTable.getParent().setLayout(clayout);
		resultTableViewer.setInput(results);
		resultTableViewer.setAllChecked(true);

		Button selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setText(Messages.PHPsSearchResultDialog_Select_all);
		selectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		selectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultTableViewer.setAllChecked(true);
			}
		});
		Button deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setText(Messages.PHPsSearchResultDialog_Deselect_all);
		deselectAll
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		deselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultTableViewer.setAllChecked(false);
			}
		});
		return composite;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		// OK pressed
		if (buttonId == 0) {
			Object[] selected = resultTableViewer.getCheckedElements();
			for (Object s : selected)
				phpExecs.add((PHPexeItem) s);
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Returns chosen PHP executables.
	 * 
	 * @return selected PHP executables
	 */
	public List<PHPexeItem> getPHPExecutables() {
		return phpExecs;
	}

}
