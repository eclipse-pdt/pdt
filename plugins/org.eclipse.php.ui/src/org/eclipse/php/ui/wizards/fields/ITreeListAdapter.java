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
package org.eclipse.php.ui.wizards.fields;

import org.eclipse.swt.events.KeyEvent;

/**
 * Change listener used by <code>TreeListDialogField</code>
 */
public interface ITreeListAdapter {

	/**
	 * A button from the button bar has been pressed.
	 */
	void customButtonPressed(TreeListDialogField field, int index);

	/**
	 * The selection of the list has changed.
	 */
	void selectionChanged(TreeListDialogField field);

	/**
	 * The list has been double clicked
	 */
	void doubleClicked(TreeListDialogField field);

	/**
	 * A key has been pressed
	 */
	void keyPressed(TreeListDialogField field, KeyEvent event);

	Object[] getChildren(TreeListDialogField field, Object element);

	Object getParent(TreeListDialogField field, Object element);

	boolean hasChildren(TreeListDialogField field, Object element);

}
