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
package org.eclipse.php.core.preferences;

import java.util.Locale;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.PHPCorePlugin;

public class CorePreferenceConstants {

	public interface Keys {
		public static final String PHP_VERSION = PHPCoreConstants.PHP_OPTIONS_PHP_VERSION;
		public static final String EDITOR_USE_ASP_TAGS= "use_asp_tags_as_php"; //$NON-NLS-1$
	}

	public static IPreferenceStore getPreferenceStore() {
		return PHPCorePlugin.getDefault().getPreferenceStore();
	}

	/**
	 * Initializes the given preference store with the default values.
	 *
	 * @param store the preference store to be initialized
	 */
	public static void initializeDefaultValues() {
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(Keys.PHP_VERSION, PHPCoreConstants.PHP5);
		
		store.setDefault(PHPCoreConstants.TASK_TAGS, PHPCoreConstants.DEFAULT_TASK_TAGS);
		store.setDefault(PHPCoreConstants.TASK_PRIORITIES, PHPCoreConstants.DEFAULT_TASK_PRIORITIES);
		store.setDefault(PHPCoreConstants.TASK_CASE_SENSITIVE, PHPCoreConstants.ENABLED);
		store.setDefault(Keys.EDITOR_USE_ASP_TAGS, true);

		store.setDefault(PHPCoreConstants.FORMATTER_USE_TABS, true);
		store.setDefault(PHPCoreConstants.FORMATTER_INDENTATION_SIZE, PHPCoreConstants.DEFAULT_INDENTATION_SIZE);
		
		if ((store.getString(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE)).equals("")) {
			store.setValue(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE,Locale.getDefault().toString());
			store.setDefault(PHPCoreConstants.WORKSPACE_LOCALE, Locale.getDefault().toString());
		}
	}

	// Don't instantiate
	private CorePreferenceConstants() {
	}
}
