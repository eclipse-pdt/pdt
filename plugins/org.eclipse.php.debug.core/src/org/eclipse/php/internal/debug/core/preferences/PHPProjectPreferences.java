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
package org.eclipse.php.internal.debug.core.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.server.core.manager.ServersManager;

public class PHPProjectPreferences {

	public static String getPreferenceNodeQualifier() {
		return IPHPDebugConstants.DEBUG_QUALIFIER;
	}

	public static Preferences getModelPreferences() {
		return PHPDebugPlugin.getDefault().getPluginPreferences();
	}

	public static IScopeContext getProjectScope(IProject project) {
		return new ProjectScope(project);
	}

	public static boolean getElementSettingsForProject(IProject project) {
		IScopeContext pScope = getProjectScope(project);
		return pScope.getNode(getPreferenceNodeQualifier()).getBoolean(
				getProjectSettingsKey(), false);
	}

	public static String getProjectSettingsKey() {
		return IPHPDebugConstants.DEBUG_PER_PROJECT;
	}

	public static boolean getStopAtFirstLine(IProject project) {
		Preferences prefs = getModelPreferences();
		boolean stop = prefs
				.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			stop = projectScope.getNode(getPreferenceNodeQualifier())
					.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE,
							stop);
		}
		return stop;

	}

	public static boolean isEnableCLIDebug(IProject project) {
		Preferences prefs = getModelPreferences();
		boolean enableCLIDebug = prefs
				.getBoolean(PHPDebugCorePreferenceNames.ENABLE_CLI_DEBUG);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			enableCLIDebug = projectScope.getNode(getPreferenceNodeQualifier())
					.getBoolean(PHPDebugCorePreferenceNames.ENABLE_CLI_DEBUG,
							enableCLIDebug);
		}
		return enableCLIDebug;

	}

	public static boolean isSortByName() {
		Preferences prefs = getModelPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.SORT_BY_NAME);

	}

	public static void changeSortByNameStatus() {
		Preferences prefs = getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.SORT_BY_NAME,
				!prefs.getBoolean(PHPDebugCorePreferenceNames.SORT_BY_NAME));
	}

	public static String getDefaultBasePath(IProject project) {

		String basePath = null;
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			basePath = projectScope.getNode(getPreferenceNodeQualifier()).get(
					PHPDebugCorePreferenceNames.DEFAULT_BASE_PATH, basePath);
		}
		if (project != null && basePath == null) {
			return "/" + project.getName(); //$NON-NLS-1$
		}
		return basePath;
	}

	public static void setDefaultBasePath(IProject project, String value) {
		Preferences prefs = getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.DEFAULT_BASE_PATH, value);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			projectScope.getNode(getPreferenceNodeQualifier()).put(
					PHPDebugCorePreferenceNames.DEFAULT_BASE_PATH, value);
		}
	}

	public static String getDefaultServerName(IProject project) {
		Preferences prefs = getModelPreferences();
		String serverName = prefs
				.getString(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			serverName = projectScope.getNode(getPreferenceNodeQualifier())
					.get(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY,
							serverName);
		}
		return serverName;
	}

	public static String getDefaultDebuggerID(IProject project) {
		Preferences prefs = getModelPreferences();
		String debuggerID = prefs
				.getString(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			debuggerID = projectScope.getNode(getPreferenceNodeQualifier())
					.get(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
							debuggerID);
		}
		return debuggerID;
	}

	public static String getTransferEncoding(IProject project) {
		Preferences prefs = getModelPreferences();
		String encoding = prefs
				.getString(PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			encoding = projectScope.getNode(getPreferenceNodeQualifier()).get(
					PHPDebugCorePreferenceNames.TRANSFER_ENCODING, encoding);
		}
		return encoding;
	}

	public static String getOutputEncoding(IProject project) {
		Preferences prefs = getModelPreferences();
		String encoding = prefs
				.getString(PHPDebugCorePreferenceNames.OUTPUT_ENCODING);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			encoding = projectScope.getNode(getPreferenceNodeQualifier()).get(
					PHPDebugCorePreferenceNames.OUTPUT_ENCODING, encoding);
		}
		return encoding;
	}

}
