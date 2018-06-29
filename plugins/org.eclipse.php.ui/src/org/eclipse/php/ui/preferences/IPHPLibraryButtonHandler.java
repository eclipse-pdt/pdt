/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.preferences;

import org.eclipse.dltk.internal.ui.wizards.dialogfields.TreeListDialogField;

/**
 * Interface for extending list of available actions on PHP Libraries preference
 * page.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public interface IPHPLibraryButtonHandler {

	/**
	 * @return button's label
	 */
	String getLabel();

	/**
	 * @return position on the buttons list
	 */
	int getPosition();

	/**
	 * Handler for selection on libraries list.
	 * 
	 * @param field
	 */
	void handleSelection(TreeListDialogField field);

	/**
	 * Handler for selection change. It determines button if button should be
	 * enabled or disabled for selected element.
	 * 
	 * @param field
	 * @return <code>true</code> if button is enabled, otherwise return
	 *         <code>false</code>
	 */
	boolean selectionChanged(TreeListDialogField field);

}