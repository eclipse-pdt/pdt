/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * The class can be used to sort content of
 * {@link org.eclipse.swt.widgets.Table}. Compared values are labels displayed
 * in table columns.
 * 
 * Sorting is fired by clicking column header.
 */
public class TableSorter {

	private TableViewer fTableViewer = null;
	private ViewerComparator fViewerComparator = null;
	private Comparator<Object> fComparator;
	private HashMap<TableColumn, SelectionAdapter> fSelectionAdapters = null;

	/**
	 * Constructor.
	 */
	public TableSorter() {
		this(null);
	}

	/**
	 * Constructor.
	 * 
	 * @param comparator
	 */
	public TableSorter(Comparator<Object> comparator) {
		fComparator = comparator;
		fSelectionAdapters = new HashMap<TableColumn, SelectionAdapter>();
		fViewerComparator = new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return compareElements(e1, e2);
			}
		};
	}

	/**
	 * Constructor.
	 * 
	 * @param comparator
	 * @param viewer
	 *            table viewer
	 */
	public TableSorter(TableViewer viewer, Comparator<Object> comparator) {
		this(comparator);
		setTableViewer(viewer);
	}

	protected void tableColumnClicked(TableColumn column) {
		Table table = column.getParent();
		if (column.equals(table.getSortColumn())) {
			table.setSortDirection(table.getSortDirection() == SWT.UP ? SWT.DOWN : SWT.UP);
		} else {
			table.setSortColumn(column);
			table.setSortDirection(SWT.UP);
		}
		fTableViewer.refresh();
	}

	protected int compareElements(Object e1, Object e2) {
		ILabelProvider labelProvider = (ILabelProvider) fTableViewer.getLabelProvider();
		Table table = fTableViewer.getTable();
		String text1 = ""; //$NON-NLS-1$
		String text2 = ""; //$NON-NLS-1$

		if (labelProvider instanceof ITableLabelProvider) {
			ITableLabelProvider tableLabelProvider = (ITableLabelProvider) labelProvider;

			int index = Arrays.asList(table.getColumns()).indexOf(table.getSortColumn());
			if (index != -1) {
				text1 = tableLabelProvider.getColumnText(e1, index);
				text2 = tableLabelProvider.getColumnText(e2, index);
			}
		} else {
			text1 = labelProvider.getText(e1);
			text2 = labelProvider.getText(e2);
		}

		int result = getComparator().compare(text1, text2);
		return table.getSortDirection() == SWT.UP ? result : -result;
	}

	protected void registerTableViewer(TableViewer viewer) {
		if (viewer == null)
			return;

		viewer.setComparator(fViewerComparator);
		addColumnSelectionListeners(viewer);
	}

	protected void unregisterTableViewer(TableViewer viewer) {
		if (viewer == null)
			return;

		if (viewer.getComparator() == fViewerComparator)
			viewer.setComparator(null);
		removeColumnSelectionListeners(viewer);
	}

	protected void addColumnSelectionListeners(TableViewer viewer) {
		if (viewer == null)
			return;

		for (TableColumn column : viewer.getTable().getColumns()) {
			addColumnSelectionListener(column);
		}
	}

	protected void removeColumnSelectionListeners(TableViewer viewer) {
		if (viewer == null)
			return;

		for (Map.Entry<TableColumn, SelectionAdapter> entry : fSelectionAdapters.entrySet()) {
			TableColumn tableColumn = entry.getKey();
			SelectionAdapter selectionAdapter = entry.getValue();
			tableColumn.removeSelectionListener(selectionAdapter);
		}
		fSelectionAdapters.clear();
	}

	protected void addColumnSelectionListener(TableColumn column) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableColumnClicked((TableColumn) e.widget);
			}
		};
		column.addSelectionListener(selectionAdapter);
		fSelectionAdapters.put(column, selectionAdapter);
	}

	public void setTableViewer(TableViewer viewer) {
		if (fTableViewer == viewer)
			return;

		unregisterTableViewer(fTableViewer);
		registerTableViewer(viewer);
		fTableViewer = viewer;
	}

	public TableViewer getTableViewer() {
		return fTableViewer;
	}

	protected Comparator<Object> getComparator() {
		if (fComparator == null) {
			fComparator = Policy.getComparator();
		}
		return fComparator;
	}
}
