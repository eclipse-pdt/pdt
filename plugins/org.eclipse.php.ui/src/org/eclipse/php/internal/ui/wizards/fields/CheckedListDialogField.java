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
package org.eclipse.php.internal.ui.wizards.fields;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

/**
 * A list with checkboxes and a button bar. Typical buttons are 'Check All' and
 * 'Uncheck All'. List model is independend of widget creation. DialogFields
 * controls are: Label, List and Composite containing buttons.
 */
@Deprecated
public class CheckedListDialogField extends ListDialogField {

	private int fCheckAllButtonIndex;
	private int fUncheckAllButtonIndex;

	private List fCheckElements;

	public CheckedListDialogField(IListAdapter adapter, String[] customButtonLabels, ILabelProvider lprovider) {
		super(adapter, customButtonLabels, lprovider);
		fCheckElements = new ArrayList();

		fCheckAllButtonIndex = -1;
		fUncheckAllButtonIndex = -1;
	}

	/**
	 * Sets the index of the 'check' button in the button label array passed in
	 * the constructor. The behaviour of the button marked as the check button
	 * will then be handled internally. (enable state, button invocation
	 * behaviour)
	 */
	public void setCheckAllButtonIndex(int checkButtonIndex) {
		Assert.isTrue(checkButtonIndex < fButtonLabels.length);
		fCheckAllButtonIndex = checkButtonIndex;
	}

	/**
	 * Sets the index of the 'uncheck' button in the button label array passed
	 * in the constructor. The behaviour of the button marked as the uncheck
	 * button will then be handled internally. (enable state, button invocation
	 * behaviour)
	 */
	public void setUncheckAllButtonIndex(int uncheckButtonIndex) {
		Assert.isTrue(uncheckButtonIndex < fButtonLabels.length);
		fUncheckAllButtonIndex = uncheckButtonIndex;
	}

	/*
	 * @see ListDialogField#createTableViewer
	 */
	protected TableViewer createTableViewer(Composite parent) {
		Table table = new Table(parent, SWT.CHECK + getListStyle());
		table.setFont(parent.getFont());
		CheckboxTableViewer tableViewer = new CheckboxTableViewer(table);
		tableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent e) {
				doCheckStateChanged(e);
			}
		});
		return tableViewer;
	}

	/*
	 * @see ListDialogField#getListControl
	 */
	public Control getListControl(Composite parent) {
		Control control = super.getListControl(parent);
		if (parent != null) {
			((CheckboxTableViewer) fTable).setCheckedElements(fCheckElements.toArray());
		}
		return control;
	}

	/*
	 * @see DialogField#dialogFieldChanged Hooks in to get element changes to
	 * update check model.
	 */
	public void dialogFieldChanged() {
		for (int i = fCheckElements.size() - 1; i >= 0; i--) {
			if (!fElements.contains(fCheckElements.get(i))) {
				fCheckElements.remove(i);
			}
		}
		super.dialogFieldChanged();
	}

	private void checkStateChanged() {
		// call super and do not update check model
		super.dialogFieldChanged();
	}

	/**
	 * Gets the checked elements.
	 */
	public List getCheckedElements() {
		if (isOkToUse(fTableControl)) {
			// workaround for bug 53853
			Object[] checked = ((CheckboxTableViewer) fTable).getCheckedElements();
			ArrayList res = new ArrayList(checked.length);
			for (int i = 0; i < checked.length; i++) {
				res.add(checked[i]);
			}
			return res;
		}

		return new ArrayList(fCheckElements);
	}

	/**
	 * Returns the number of checked elements.
	 */
	public int getCheckedSize() {
		return fCheckElements.size();
	}

	/**
	 * Returns true if the element is checked.
	 */
	public boolean isChecked(Object obj) {
		if (isOkToUse(fTableControl)) {
			return ((CheckboxTableViewer) fTable).getChecked(obj);
		}

		return fCheckElements.contains(obj);
	}

	/**
	 * Sets the checked elements.
	 */
	public void setCheckedElements(Collection list) {
		fCheckElements = new ArrayList(list);
		if (isOkToUse(fTableControl)) {
			((CheckboxTableViewer) fTable).setCheckedElements(list.toArray());
		}
		checkStateChanged();
	}

	/**
	 * Sets the checked state of an element.
	 */
	public void setChecked(Object object, boolean state) {
		setCheckedWithoutUpdate(object, state);
		checkStateChanged();
	}

	/**
	 * Sets the checked state of an element. No dialog changed listener is
	 * informed.
	 */
	public void setCheckedWithoutUpdate(Object object, boolean state) {
		if (state) {
			if (!fCheckElements.contains(object)) {
				fCheckElements.add(object);
			}
		} else {
			fCheckElements.remove(object);
		}
		if (isOkToUse(fTableControl)) {
			((CheckboxTableViewer) fTable).setChecked(object, state);
		}
	}

	/**
	 * Sets the check state of all elements
	 */
	public void checkAll(boolean state) {
		if (state) {
			fCheckElements = getElements();
		} else {
			fCheckElements.clear();
		}
		if (isOkToUse(fTableControl)) {
			((CheckboxTableViewer) fTable).setAllChecked(state);
		}
		checkStateChanged();
	}

	private void doCheckStateChanged(CheckStateChangedEvent e) {
		if (e.getChecked()) {
			fCheckElements.add(e.getElement());
		} else {
			fCheckElements.remove(e.getElement());
		}
		checkStateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.wizards.dialogfields.ListDialogField#
	 * replaceElement(java.lang.Object, java.lang.Object)
	 */
	public void replaceElement(Object oldElement, Object newElement) throws IllegalArgumentException {
		boolean wasChecked = isChecked(oldElement);
		super.replaceElement(oldElement, newElement);
		setChecked(newElement, wasChecked);
	}

	// ------ enable / disable management

	/*
	 * @see ListDialogField#getManagedButtonState
	 */
	protected boolean getManagedButtonState(ISelection sel, int index) {
		if (index == fCheckAllButtonIndex) {
			return !fElements.isEmpty();
		} else if (index == fUncheckAllButtonIndex) {
			return !fElements.isEmpty();
		}
		return super.getManagedButtonState(sel, index);
	}

	/*
	 * @see ListDialogField#extraButtonPressed
	 */
	protected boolean managedButtonPressed(int index) {
		if (index == fCheckAllButtonIndex) {
			checkAll(true);
		} else if (index == fUncheckAllButtonIndex) {
			checkAll(false);
		} else {
			return super.managedButtonPressed(index);
		}
		return true;
	}

}
