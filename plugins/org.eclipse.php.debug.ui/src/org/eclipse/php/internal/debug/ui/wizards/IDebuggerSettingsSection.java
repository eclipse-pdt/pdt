/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;


/**
 * Common interface for debugger settings owner sections.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IDebuggerSettingsSection {

	/**
	 * Performs special processing when parent composite fragment 'OK' button
	 * has been pressed.
	 * 
	 * @return <code>false</code> to abort the container's OK processing and
	 *         <code>true</code> to allow the OK to happen
	 */
	boolean performOK();

	/**
	 * Performs special processing when parent composite fragment 'Cancel'
	 * button has been pressed.
	 * 
	 * @return <code>true</code> if canceling was OK
	 */
	boolean performCancel();

	/**
	 * Called to validate data values in section composite.
	 */
	void validate();

	/**
	 * Returns info if this settings section can perform any kind of debugger
	 * tests (e.g. connection test).
	 * 
	 * @return <code>true</code> if can perform any tests, <code>false</code>
	 *         otherwise
	 */
	boolean canTest();

	/**
	 * Performs tests (e.g. connection test) if there are any.
	 */
	void performTest();

}
