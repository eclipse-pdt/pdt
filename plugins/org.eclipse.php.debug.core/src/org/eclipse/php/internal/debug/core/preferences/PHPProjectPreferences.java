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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.server.core.manager.ServersManager;

public class PHPProjectPreferences {

	public static String getPreferenceNodeQualifier() {
		return IPHPDebugConstants.DEBUG_QUALIFIER;
	}

	public static IScopeContext getProjectScope(IProject project) {
		return new ProjectScope(project);
	}

	public static boolean getElementSettingsForProject(IProject project) {
		IScopeContext pScope = getProjectScope(project);
		return pScope.getNode(getPreferenceNodeQualifier()).getBoolean(getProjectSettingsKey(), false);
	}

	public static String getProjectSettingsKey() {
		return IPHPDebugConstants.DEBUG_PER_PROJECT;
	}

	public static boolean getStopAtFirstLine(IProject project) {
		boolean stop = Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, true, null);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			stop = projectScope.getNode(getPreferenceNodeQualifier())
					.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, stop);
		}
		return stop;

	}

	public static boolean isSortByName() {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.SORT_BY_NAME,
				true, null);

	}

	public static void changeSortByNameStatus() {
		PHPDebugPlugin.getInstancePreferences().putBoolean(PHPDebugCorePreferenceNames.SORT_BY_NAME,
				!Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
						PHPDebugCorePreferenceNames.SORT_BY_NAME, true, null));
	}

	@Nullable
	public static String getDefaultBasePath(IProject project) {
		String basePath = null;
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			basePath = projectScope.getNode(getPreferenceNodeQualifier())
					.get(PHPDebugCorePreferenceNames.DEFAULT_BASE_PATH, basePath);
		}
		if (project != null && basePath == null) {
			basePath = '/' + project.getName();
		}
		if (basePath != null && !basePath.startsWith("/")) { //$NON-NLS-1$
			basePath = '/' + basePath;
		}
		return basePath;
	}

	public static void setDefaultBasePath(IProject project, String basePath) {
		if (basePath != null && !basePath.startsWith("/")) { //$NON-NLS-1$
			basePath = '/' + basePath;
		}
		// PHPDebugPlugin.getInstancePreferences().put(PHPDebugCorePreferenceNames.DEFAULT_BASE_PATH,
		// basePath);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			projectScope.getNode(getPreferenceNodeQualifier()).put(PHPDebugCorePreferenceNames.DEFAULT_BASE_PATH,
					basePath);
		}
	}

	public static String getDefaultServerName(IProject project) {
		String serverName = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				ServersManager.DEFAULT_SERVER_PREFERENCES_KEY, null, null);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			serverName = projectScope.getNode(getPreferenceNodeQualifier())
					.get(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY, serverName);
		}
		return serverName;
	}

	public static String getDefaultDebuggerID(IProject project) {
		String debuggerID = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, null, null);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			debuggerID = projectScope.getNode(getPreferenceNodeQualifier())
					.get(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerID);
		}
		return debuggerID;
	}

	public static String getTransferEncoding(IProject project) {
		String encoding = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.TRANSFER_ENCODING, null, null);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			encoding = projectScope.getNode(getPreferenceNodeQualifier())
					.get(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, encoding);
		}
		return encoding;
	}

	public static String getOutputEncoding(IProject project) {
		String encoding = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.OUTPUT_ENCODING, null, null);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			encoding = projectScope.getNode(getPreferenceNodeQualifier())
					.get(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, encoding);
		}
		return encoding;
	}

}
