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
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
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
	private static final String BASE_URL = "http://localhost";
	private static String fPHPDebugPerspective = "org.eclipse.php.debug.ui.PHPDebugPerspective";
	private static String fDebugPerspective = "org.eclipse.debug.ui.DebugPerspective";
	private static boolean fIsSupportingMultipleDebugAllPages = true;
	private boolean fInitialAutoRemoveLaunches;
	private static boolean fLaunchChangedAutoRemoveLaunches;

	/**
	 * The constructor.
	 */
	public PHPDebugPlugin() {
		plugin = this;
		IPreferenceStore preferenceStore = DebugUIPlugin.getDefault().getPreferenceStore();
		fInitialAutoRemoveLaunches = preferenceStore.getBoolean(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES);
		preferenceStore.addPropertyChangeListener(new AutoRemoveOldLaunchesListener());
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
		DebugUIPlugin.getDefault().getPreferenceStore().setValue(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES, fInitialAutoRemoveLaunches);
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

	public static boolean getOpenInBrowserOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER);

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

	public static String getWorkspaceDefaultServer() {
		Preferences serverPrefs = org.eclipse.php.server.core.Activator.getDefault().getPluginPreferences();
		return serverPrefs.getString(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY);

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
			Server server = ServersManager.createServer(IPHPConstants.Default_Server_Name, BASE_URL);
			ServersManager.save();
			ServersManager.setDefaultServer(null, server);
		}
	}

	public static void log(IStatus status) {
		try {
			getDefault().getLog().log(status);
		} catch (Exception e) {
		}
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "PHPDebug plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

	/**
	 * Returns if multiple sessions of debug launches are allowed when one of the launches 
	 * contains a 'debug all pages' attribute.
	 * 
	 * @return True, the multiple sessions are allowed; False, otherwise.
	 */
	public static boolean supportsMultipleDebugAllPages() {
		return fIsSupportingMultipleDebugAllPages;
	}

	/**
	 * Allow or disallow the multiple debug sessions that has a launch attribute of 'debug all pages'.
	 * 
	 * @param supported 
	 */
	public static void setMultipleDebugAllPages(boolean supported) {
		fIsSupportingMultipleDebugAllPages = supported;
	}

	//
	//	/**
	//	 * Returns true if the auto remove launches was disabled by a PHP launch.
	//	 * The auto remove flag is usually disabled when a PHP server launch was triggered and a 
	//	 * 'debug all pages' flag was on.
	//	 * Note that this method will return true only if a php launch set it and the debug preferences has a 'true'
	//	 * value for IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES.
	//	 * 
	//	 * @return True iff the auto remove old launches was disabled.
	//	 */
	//	public static boolean isDisablingAutoRemoveLaunches() {
	//		return fDisableAutoRemoveLaunches;
	//	}

	/**
	 * Enable or disable the auto remove old launches flag. 
	 * The auto remove flag is usually disabled when a PHP server launch was triggered and a 
	 * 'debug all pages' flag was on.
	 * Note that this method actually sets the IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES preferences key 
	 * for the {@link DebugUIPlugin}.
	 * 
	 * @param disableAutoRemoveLaunches
	 */
	public static void setDisableAutoRemoveLaunches(boolean disableAutoRemoveLaunches) {
		if (DebugUIPlugin.getDefault().getPreferenceStore().getBoolean(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES) == disableAutoRemoveLaunches) {
			fLaunchChangedAutoRemoveLaunches = true;
			DebugUIPlugin.getDefault().getPreferenceStore().setValue(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES, !disableAutoRemoveLaunches);
		}
	}

	/**
	 * Returns the initial value of the auto-remove-old launches.
	 * 
	 * @return
	 */
	public boolean getInitialAutoRemoveLaunches() {
		return fInitialAutoRemoveLaunches;
	}

	// 
	private class AutoRemoveOldLaunchesListener implements IPropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			if (IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES.equals(event.getProperty())) {
				if (fLaunchChangedAutoRemoveLaunches) {
					fLaunchChangedAutoRemoveLaunches = false;// We got the event, so reset the flag.
				} else {
					// The event was triggered from some other source - e.g. The user changed the preferences manually.
					fInitialAutoRemoveLaunches = ((Boolean) event.getNewValue()).booleanValue();
				}
			}
		}
	}
}
