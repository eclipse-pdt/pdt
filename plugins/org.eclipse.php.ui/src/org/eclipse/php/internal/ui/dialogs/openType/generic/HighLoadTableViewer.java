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
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.CompositeFilter;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.IFilter;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.IFilterChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class HighLoadTableViewer extends Composite {

	private TableViewer tableViewer;

	private Object[] elements = new Object[] {};
	private Object[] sortedElements = elements;
	private Object[] tableElements = elements;
	private ISorter sorter = new ISorter() {
		public Object[] sort(Object[] elements) {
			return elements;
		}
	};
	private CompositeFilter compositeFilter = new CompositeFilter();

	private boolean defaultElementSelection = true;

	private ElementAddition elementAddition;

	public HighLoadTableViewer(Composite parent, int style) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout());
		tableViewer = new TableViewer(this, style);
		compositeFilter.addFilterChangeListener(new IFilterChangeListener() {
			public void notifyFilterChanged() {
				applyFilter2TableViewer();
			}
		});
	}

	public void setElements(Object[] elements) {
		// stopTableUpdater();
		this.elements = elements;
		sortedElements = sorter.sort(elements);
		tableElements = compositeFilter.filter(sortedElements);
		addElementsToTableInNewThread();
	}

	public void setSorter(ISorter sorter) {
		// assert sorter != null;

		// stopTableUpdater();
		this.sorter = sorter;
		sortedElements = sorter.sort(elements);
		tableElements = sorter.sort(tableElements);
		addElementsToTableInNewThread();
	}

	public void addFilter(IFilter filter) {
		this.compositeFilter.addFilter(filter);

	}

	private void applyFilter2TableViewer() {
		// stopTableUpdater();
		tableElements = compositeFilter.filter(sortedElements);
		addElementsToTableInNewThread();
	}

	private void addElementsToTableInNewThread() {

		if (elementAddition == null) {
			elementAddition = new ElementAddition(tableViewer,
					defaultElementSelection);
			Thread tableUpdater = new Thread(elementAddition);
			tableUpdater.start();
		}

		elementAddition.setElements(tableElements);
	}

	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		tableViewer.setLabelProvider(labelProvider);
	}

	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	public Control getControl() {
		return tableViewer.getControl();
	}

	public Object getElementAt(int index) {
		return tableViewer.getElementAt(index);
	}

	public void setSelection(IStructuredSelection selection) {
		tableViewer.setSelection(selection);
	}

	public Object[] getTableElements() {
		return tableElements;
	}

	public void addDoubleClickListener(IDoubleClickListener listener) {
		tableViewer.addDoubleClickListener(listener);
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		tableViewer.addSelectionChangedListener(listener);
	}

	public void setDefaultElementSelection(boolean defaultElementSelection) {
		this.defaultElementSelection = defaultElementSelection;
	}

	public void close() {
		stopTableUpdater();
	}

	private void stopTableUpdater() {
		this.elementAddition.stop();
	}
}
