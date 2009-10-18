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
