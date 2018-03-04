/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PHPRefactoringCoreMessages {
	private static final String BUNDLE_NAME = "org.eclipse.php.refactoring.core.PHPRefactoringCoreMessages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private PHPRefactoringCoreMessages() {
		// private constructor
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Gets formatted translation for current local
	 * 
	 * @param key
	 *            the key
	 * @return translated value string
	 */
	public static String format(String key, Object[] arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
}
