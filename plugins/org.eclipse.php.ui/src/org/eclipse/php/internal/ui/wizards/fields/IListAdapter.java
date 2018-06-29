/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.fields;

/**
 * Change listener used by <code>ListDialogField</code> and
 * <code>CheckedListDialogField</code>
 */
public interface IListAdapter<E> {

	/**
	 * A button from the button bar has been pressed.
	 */
	void customButtonPressed(ListDialogField<E> field, int index);

	/**
	 * The selection of the list has changed.
	 */
	void selectionChanged(ListDialogField<E> field);

	/**
	 * En entry in the list has been double clicked
	 */
	void doubleClicked(ListDialogField<E> field);

}
