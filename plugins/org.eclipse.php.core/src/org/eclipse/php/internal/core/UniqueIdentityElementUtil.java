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
package org.eclipse.php.internal.core;

import java.util.UUID;

/**
 * Utility class with methods that can generate unique identifiers for
 * implementors of {@link IUniqueIdentityElement} interface.
 * 
 * @author Bartlomiej Laczkowski
 */
public class UniqueIdentityElementUtil {

	private UniqueIdentityElementUtil() {
		// Utility class - no private constructor
	}

	/**
	 * Generates and returns unique identifier with provided prefix.
	 * 
	 * @param prefix
	 * @return unique identifier with provided prefix
	 */
	public static String generateId(String prefix) {
		return prefix + '-' + UUID.randomUUID().toString();
	}

	/**
	 * Generates and returns unique identifier.
	 * 
	 * @return unique identifier
	 */
	public static String generateId() {
		return UUID.randomUUID().toString();
	}

}
