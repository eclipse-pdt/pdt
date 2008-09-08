/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.preferences;

import java.util.Locale;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class CorePreferenceConstants {

	public interface Keys {
		public static final String PHP_VERSION = PHPCoreConstants.PHP_OPTIONS_PHP_VERSION;
		public static final String EDITOR_USE_ASP_TAGS= "use_asp_tags_as_php"; //$NON-NLS-1$
	}

	public static Preferences getPreferenceStore() {
		return PHPCorePlugin.getDefault().getPluginPreferences();
	}

	/**
	 * Initializes the given preference store with the default values.
	 *
	 * @param store the preference store to be initialized
	 */
	public static void initializeDefaultValues() {
		Preferences store = getPreferenceStore();
		store.setDefault(Keys.PHP_VERSION, PHPCoreConstants.PHP5);

		store.setDefault(PHPCoreConstants.TASK_TAGS, PHPCoreConstants.DEFAULT_TASK_TAGS);
		store.setDefault(PHPCoreConstants.TASK_PRIORITIES, PHPCoreConstants.DEFAULT_TASK_PRIORITIES);
		store.setDefault(PHPCoreConstants.TASK_CASE_SENSITIVE, PHPCoreConstants.ENABLED);
		store.setDefault(Keys.EDITOR_USE_ASP_TAGS, false);

		store.setDefault(PHPCoreConstants.FORMATTER_USE_TABS, true);
		store.setDefault(PHPCoreConstants.FORMATTER_INDENTATION_SIZE, PHPCoreConstants.DEFAULT_INDENTATION_SIZE);

		store.setDefault(PHPCoreConstants.CODEGEN_ADD_COMMENTS, true);
		
		if ((store.getString(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE)).equals("")) { //$NON-NLS-1$
			store.setValue(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE,Locale.getDefault().toString());
			store.setDefault(PHPCoreConstants.WORKSPACE_LOCALE, Locale.getDefault().toString());
		}
		
		store.setDefault(PHPCoreConstants.CODEASSIST_ADDIMPORT, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_FILL_ARGUMENT_NAMES, false);
		store.setDefault(PHPCoreConstants.CODEASSIST_GUESS_METHOD_ARGUMENTS, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_PREFIX_COMPLETION, false);
		// implemented:
		store.setDefault(PHPCoreConstants.CODEASSIST_AUTOINSERT, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_INSERT_COMPLETION, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES, false);
		store.setDefault(PHPCoreConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS, false);
		store.setDefault(PHPCoreConstants.CODEASSIST_GROUP_OPTIONS, false);
		store.setDefault(PHPCoreConstants.CODEASSIST_CUT_COMMON_PREFIX, false);
		store.setDefault(PHPCoreConstants.CODEASSIST_CONSTANTS_CASE_SENSITIVE, false);
		store.setDefault(PHPCoreConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_AUTOACTIVATION, true);
		store.setDefault(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY, 200);
		store.setDefault(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP, "$:>"); //$NON-NLS-1$
		store.setDefault(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC, "@"); //$NON-NLS-1$
	}

	// Don't instantiate
	private CorePreferenceConstants() {
	}
}
