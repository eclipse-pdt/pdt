/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PHPUIMessages {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.PHPUIMessages"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static ResourceBundle fResourceBundle;
	private static final String BUNDLE_FOR_CONSTRUCTED_KEYS = "org.eclipse.ui.texteditor.ConstructedTextEditorMessages"; //$NON-NLS-1$
	private static ResourceBundle fgBundleForConstructedKeys = ResourceBundle.getBundle(BUNDLE_FOR_CONSTRUCTED_KEYS);

	private PHPUIMessages() {
	}

	/**
	 * Returns the message bundle which contains constructed keys.
	 *
	 * @since 3.1
	 * @return the message bundle
	 */
	public static ResourceBundle getBundleForConstructedKeys() {
		return fgBundleForConstructedKeys;
	}

	public static ResourceBundle getResourceBundle() {
		try {
			if (fResourceBundle == null)
				fResourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (MissingResourceException x) {
			fResourceBundle = null;
		}
		return fResourceBundle;
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return ""; //$NON-NLS-1$
		}
	}
}
