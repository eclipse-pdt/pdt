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
package org.eclipse.php.ui.editor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
public final class PHPEditorMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.ui.editor.PHPEditorMessages"; //$NON-NLS-1$
	private static ResourceBundle fResourceBundle;

	private PHPEditorMessages() {
		// Do not instantiate
	}

	public static String PHP_Editor_FoldingMenu_name;
	public static String ShowPHPDoc_label;
	public static String HoverFocus_message;

	static {
		NLS.initializeMessages(BUNDLE_NAME, PHPEditorMessages.class);
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

	public static String PHPStructuredEditor_Source;
}