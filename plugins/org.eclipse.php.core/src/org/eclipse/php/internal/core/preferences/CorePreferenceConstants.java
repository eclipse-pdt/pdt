/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;

import com.ibm.icu.util.ULocale;

public class CorePreferenceConstants {

	public interface Keys {
		public static final String PHP_VERSION = PHPCoreConstants.PHP_OPTIONS_PHP_VERSION;
		public static final String EDITOR_USE_ASP_TAGS = "use_asp_tags_as_php"; //$NON-NLS-1$
		public final static String EDITOR_USE_SHORT_TAGS = "useShortTags"; //$NON-NLS-1$
	}

	/**
	 * Initializes the given preference store with the default values.
	 * 
	 * @param store
	 *            the preference store to be initialized
	 */
	public static void initializeDefaultValues() {
		IEclipsePreferences node = new DefaultScope().getNode(PHPCorePlugin.ID);

		node.put(Keys.PHP_VERSION, PHPVersion.PHP5_4.getAlias());
		node.put(PHPCoreConstants.TASK_TAGS, PHPCoreConstants.DEFAULT_TASK_TAGS);
		node.put(PHPCoreConstants.TASK_PRIORITIES,
				PHPCoreConstants.DEFAULT_TASK_PRIORITIES);
		node.put(PHPCoreConstants.TASK_CASE_SENSITIVE, PHPCoreConstants.ENABLED);
		node.putBoolean(Keys.EDITOR_USE_ASP_TAGS, false);
		node.putBoolean(Keys.EDITOR_USE_SHORT_TAGS, true);
		node.putBoolean(PHPCoreConstants.FORMATTER_USE_TABS, true);
		node.put(PHPCoreConstants.FORMATTER_INDENTATION_SIZE,
				PHPCoreConstants.DEFAULT_INDENTATION_SIZE);
		node.put(PHPCoreConstants.FORMATTER_TAB_SIZE,
				PHPCoreConstants.DEFAULT_TAB_SIZE);
		node.put(PHPCoreConstants.FORMATTER_INDENTATION_WRAPPED_LINE_SIZE,
				PHPCoreConstants.DEFAULT_INDENTATION_WRAPPED_LINE_SIZE);
		node.put(PHPCoreConstants.FORMATTER_INDENTATION_ARRAY_INIT_SIZE,
				PHPCoreConstants.DEFAULT_INDENTATION_ARRAY_INIT_SIZE);
		node.putBoolean(PHPCoreConstants.CODEGEN_ADD_COMMENTS, false);
		node.put(PHPCoreConstants.WORKSPACE_DEFAULT_LOCALE, ULocale
				.getDefault().toString());
		node.put(PHPCoreConstants.WORKSPACE_LOCALE, ULocale.getDefault()
				.toString());
		node.putBoolean(PHPCoreConstants.CODEASSIST_ADDIMPORT, true);
		node.putBoolean(PHPCoreConstants.CODEASSIST_FILL_ARGUMENT_NAMES, true);
		node.putBoolean(PHPCoreConstants.CODEASSIST_GUESS_METHOD_ARGUMENTS,
				true);
		node.putBoolean(PHPCoreConstants.CODEASSIST_AUTOINSERT, true);
		node.putBoolean(PHPCoreConstants.CODEASSIST_INSERT_COMPLETION, true);
		node.putBoolean(
				PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES,
				false);
		node.putBoolean(
				PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_REFERENCED_FILES,
				true);
		node.putBoolean(
				PHPCoreConstants.CODEASSIST_INSERT_FULL_QUALIFIED_NAME_FOR_NAMESPACE,
				true);
		node.putBoolean(PHPCoreConstants.CODEASSIST_SHOW_STRICT_OPTIONS, false);
		node.putBoolean(PHPCoreConstants.CODEASSIST_AUTOACTIVATION, true);
		node.putInt(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY, 200);
	}

	// Don't instantiate
	private CorePreferenceConstants() {
	}
}
