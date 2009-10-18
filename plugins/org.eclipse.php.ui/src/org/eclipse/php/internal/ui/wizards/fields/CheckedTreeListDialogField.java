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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

public class CheckedTreeListDialogField extends TreeListDialogField {

	private int fCheckAllButtonIndex;
	private int fUncheckAllButtonIndex;

	private List fCheckElements;

	public CheckedTreeListDialogField(ITreeListAdapter adapter,
			String[] buttonLabels, ILabelProvider lprovider) {
		super(adapter, buttonLabels, lprovider);

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
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.wizards.fields.TreeListDialogField#
	 * createTreeViewer(org.eclipse.swt.widgets.Composite)
	 */
	protected TreeViewer createTreeViewer(Composite parent) {
		// TODO Auto-generated method stub
		Tree tree = new Tree(parent, SWT.CHECK + getTreeStyle());
		tree.setFont(parent.getFont());
		ContainerCheckedTreeViewer treeViewer = new ContainerCheckedTreeViewer(
				tree);
		treeViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent e) {
				doCheckStateChanged(e);
			}
		});
		return treeViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.wizards.fields.TreeListDialogField#getTreeControl
	 * (org.eclipse.swt.widgets.Composite)
	 */
	public Control getTreeControl(Composite parent) {
		Control control = super.getTreeControl(parent);
		if (parent != null) {
			((ContainerCheckedTreeViewer) fTree)
					.setCheckedElements(fCheckElements.toArray());
		}
		return control;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.wizards.fields.TreeListDialogField#
	 * dialogFieldChanged()
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
		if (isOkToUse(fTreeControl)) {
			// workaround for bug 53853
			Object[] checked = ((ContainerCheckedTreeViewer) fTree)
					.getCheckedElements();
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
		if (isOkToUse(fTreeControl)) {
			return ((ContainerCheckedTreeViewer) fTree).getChecked(obj);
		}

		return fCheckElements.contains(obj);
	}

	/**
	 * Sets the checked elements.
	 */
	public void setCheckedElements(Collection list) {
		fCheckElements = new ArrayList();
		if (list != null && list.size() > 0) {
			fCheckElements.addAll(list);
		}
		if (isOkToUse(fTreeControl)) {
			((ContainerCheckedTreeViewer) fTree).setCheckedElements(list
					.toArray());
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
		if (isOkToUse(fTreeControl)) {
			((ContainerCheckedTreeViewer) fTree).setChecked(object, state);
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
		if (isOkToUse(fTreeControl)) {
			((ContainerCheckedTreeViewer) fTree).setAllChecked(state);
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
	 * @see
	 * org.eclipse.php.internal.ui.wizards.fields.TreeListDialogField#replaceElement
	 * (java.lang.Object, java.lang.Object)
	 */
	public void replaceElement(Object oldElement, Object newElement)
			throws IllegalArgumentException {
		boolean wasChecked = isChecked(oldElement);
		super.replaceElement(oldElement, newElement);
		setChecked(newElement, wasChecked);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.wizards.fields.TreeListDialogField#
	 * getManagedButtonState(org.eclipse.jface.viewers.ISelection, int)
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
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.wizards.fields.TreeListDialogField#
	 * managedButtonPressed(int)
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