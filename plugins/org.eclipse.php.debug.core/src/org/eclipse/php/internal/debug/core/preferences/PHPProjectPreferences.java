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
package org.eclipse.php.internal.debug.core.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.server.core.manager.ServersManager;

public class PHPProjectPreferences {

	public static String getPreferenceNodeQualifier() {
		return IPHPConstants.DEBUG_QUALIFIER;
	}

	public static Preferences getModelPreferences() {
		return PHPDebugPlugin.getDefault().getPluginPreferences();
	}

	public static IScopeContext getProjectScope(IProject project) {
		return new ProjectScope(project);
	}

	public static boolean getElementSettingsForProject(IProject project) {
		IScopeContext pScope = getProjectScope(project);
		return pScope.getNode(getPreferenceNodeQualifier()).getBoolean(getProjectSettingsKey(), false);
	}

	public static String getProjectSettingsKey() {
		return IPHPConstants.DEBUG_PER_PROJECT;
	}

	public static boolean getStopAtFirstLine(IProject project) {
		Preferences prefs = getModelPreferences();
		boolean stop = prefs.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			stop = projectScope.getNode(getPreferenceNodeQualifier()).getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, stop);
		}
		return stop;

	}

	public static String getDefaultServerName(IProject project) {
		Preferences prefs = getModelPreferences();
		String serverName = prefs.getString(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			serverName = projectScope.getNode(getPreferenceNodeQualifier()).get(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY, serverName);
		}
		return serverName;
	}
	
	public static String getDefaultDebuggerID(IProject project) {
		Preferences prefs = getModelPreferences();
		String debuggerID = prefs.getString(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			debuggerID = projectScope.getNode(getPreferenceNodeQualifier()).get(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerID);
		}
		return debuggerID;
	}

	public static String getTransferEncoding(IProject project) {
		Preferences prefs = getModelPreferences();
		String encoding = prefs.getString(PHPDebugCorePreferenceNames.TRANSFER_ENCODING);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			encoding = projectScope.getNode(getPreferenceNodeQualifier()).get(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, encoding);
		}
		return encoding;
	}

	public static String getOutputEncoding(IProject project) {
		Preferences prefs = getModelPreferences();
		String encoding = prefs.getString(PHPDebugCorePreferenceNames.OUTPUT_ENCODING);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			encoding = projectScope.getNode(getPreferenceNodeQualifier()).get(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, encoding);
		}
		return encoding;
	}
}
