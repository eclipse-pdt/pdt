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
package org.eclipse.php.debug.core;

import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.core.manager.ServersManager;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPDebugPlugin extends Plugin {

	public static final String ID = "org.eclipse.php.debug.core"; //$NON-NLS-1$
	public static final int INTERNAL_ERROR = 10001;

	//The shared instance.
	private static PHPDebugPlugin plugin;
	private static String fPHPDebugPerspective = "org.eclipse.php.debug.ui.PHPDebugPerspective";
	private static String fDebugPerspective = "org.eclipse.debug.ui.DebugPerspective";

	/**
	 * The constructor.
	 */
	public PHPDebugPlugin() {
		plugin = this;
	}

	public static final boolean DebugPHP;
	static {
		String value = Platform.getDebugOption("org.eclipse.php.debug.core/debug"); //$NON-NLS-1$
		DebugPHP = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		org.eclipse.php.server.core.Activator.getDefault(); // TODO - Check if getInstance is needed
		setLaunchPerspective();
		// check for default server
		createDefaultPHPServer();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static PHPDebugPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the PHP debug ID.
	 */
	public static String getID() {
		return IPHPConstants.ID_PHP_DEBUG_CORE;
	}

	public static boolean getStopAtFirstLine() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);

	}

	public static boolean getDebugInfoOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO);

	}

	public static boolean getAutoSaveDirtyOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY);

	}

	public static boolean getOpenDebugViewsOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS);

	}

	public static int getDebugPort() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getInt(PHPDebugCorePreferenceNames.DEBUG_PORT);

	}

	public static String getWorkspaceURL() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getString(PHPDebugCorePreferenceNames.DEDAULT_URL);

	}

	public void setLaunchPerspective() {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType[] types = manager.getLaunchConfigurationTypes();
		Preferences prefs = getPluginPreferences();
		boolean usePHPDebugPerspective = prefs.getBoolean(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE);
		for (int i = 0; i < types.length; i++) {
			if (types[i].getIdentifier().startsWith("org.eclipse.php.")) {
				if (usePHPDebugPerspective) {
					DebugUITools.setLaunchPerspective(types[i], ILaunchManager.DEBUG_MODE, fPHPDebugPerspective);
				} else {
					DebugUITools.setLaunchPerspective(types[i], ILaunchManager.DEBUG_MODE, fDebugPerspective);
				}
			}
		}
	}

	/**
	 * Creates a default server in case the ServersManager does not hold any defined server.
	 */
	public static void createDefaultPHPServer() {
		if (ServersManager.getServers().length == 0) {
			String baseURL = getWorkspaceURL();
			Server server = ServersManager.createServer(IPHPConstants.Default_Server_Name, baseURL);
			ServersManager.save();
			ServersManager.setDefaultServer(server);
		}
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "PHPDebug plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

}
