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
package org.eclipse.php.debug.core.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.PHPDebugPlugin;

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

	public static int getDebugPort(IProject project) {
		Preferences prefs = getModelPreferences();
		int port = prefs.getInt(PHPDebugCorePreferenceNames.DEBUG_PORT);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			port = projectScope.getNode(getPreferenceNodeQualifier()).getInt(PHPDebugCorePreferenceNames.DEBUG_PORT, port);
		}
		return port;

	}

	public static String getDefaultServerURL(IProject project) {
		Preferences prefs = getModelPreferences();
		String hURL = prefs.getString(PHPDebugCorePreferenceNames.DEDAULT_URL);
		if (project != null && getElementSettingsForProject(project)) {
			IScopeContext projectScope = getProjectScope(project);
			hURL = projectScope.getNode(getPreferenceNodeQualifier()).get(PHPDebugCorePreferenceNames.DEDAULT_URL, hURL);
		}
		return hURL;
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
}
