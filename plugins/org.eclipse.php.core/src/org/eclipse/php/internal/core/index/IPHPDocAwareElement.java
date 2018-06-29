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
package org.eclipse.php.internal.core.index;

/**
 * This is a kind of element that can hold some PHPDoc information
 * 
 * @author Michael
 * 
 */
public interface IPHPDocAwareElement {

	/**
	 * Returns whether the element is marked as deprecated
	 * 
	 * @return
	 */
	boolean isDeprecated();

	/**
	 * Returns array of method return types
	 * 
	 * @return return types array or <code>null</code> if there were no return types
	 *         declared
	 */
	String[] getReturnTypes();
}
