/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
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
package org.eclipse.php.internal.core;

/**
 * Common interface for objects with unique identity.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IUniqueIdentityElement {

	/**
	 * Returns element's unique identifier.
	 * 
	 * @return element's unique identifier
	 */
	String getUniqueId();

}
