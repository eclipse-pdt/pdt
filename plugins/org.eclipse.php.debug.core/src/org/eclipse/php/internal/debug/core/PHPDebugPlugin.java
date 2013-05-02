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
package org.eclipse.php.internal.debug.core;

import java.net.MalformedURLException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.launching.XDebugLaunchListener;
import org.eclipse.php.internal.debug.core.preferences.*;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandler;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPDebugPlugin extends Plugin {

	public static final String ID = "org.eclipse.php.debug.core"; //$NON-NLS-1$
	public static final int INTERNAL_ERROR = 10001;
	public static final int INTERNAL_WARNING = 10002;

	// The shared instance.
	private static PHPDebugPlugin plugin;
	private static final String BASE_URL = "http://localhost"; //$NON-NLS-1$
	private static boolean fIsSupportingMultipleDebugAllPages = true;
	private boolean fInitialAutoRemoveLaunches;
	private static boolean fLaunchChangedAutoRemoveLaunches;

	/**
	 * The constructor.
	 */
	public PHPDebugPlugin() {
		plugin = this;
	}

	public static final boolean DEBUG = Boolean
			.valueOf(
					Platform.getDebugOption("org.eclipse.php.debug.core/debug")).booleanValue(); //$NON-NLS-1$

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		DaemonPlugin.getDefault();
		// Set the AutoRemoveOldLaunchesListener
		IPreferenceStore preferenceStore = DebugUIPlugin.getDefault()
				.getPreferenceStore();
		fInitialAutoRemoveLaunches = preferenceStore
				.getBoolean(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES);
		preferenceStore
				.addPropertyChangeListener(new AutoRemoveOldLaunchesListener());
		org.eclipse.php.internal.server.core.Activator.getDefault(); // TODO -
		// Check
		// if
		// getInstance
		// is
		// needed
		// check for default server
		createDefaultPHPServer();

		// TODO - XDebug - See if this can be removed and use a preferences
		// initializer.
		// It's important the the default setting will occur before loading the
		// daemons.
		XDebugPreferenceMgr.setDefaults();

		// Start all the daemons. CODE MOVED TO DAEMON PLUGIN
		// DaemonPlugin.getDefault().startDaemons(null);

		// TODO - XDebug - See if this can be removed
		XDebugLaunchListener.getInstance();
		DBGpProxyHandler.instance.configure();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		XDebugLaunchListener.shutdown();
		DBGpProxyHandler.instance.unregister();
		savePluginPreferences();

		super.stop(context);
		plugin = null;
		DebugUIPlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES,
						fInitialAutoRemoveLaunches);
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
		return IPHPDebugConstants.ID_PHP_DEBUG_CORE;
	}

	public static boolean getStopAtFirstLine() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);

	}

	public static boolean getDebugInfoOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs
				.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO);

	}

	public static boolean getOpenInBrowserOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER);
	}

	/**
	 * Returns the debugger id that is currently in use.
	 * 
	 * @return The debugger id that is in use.
	 * @since PDT 1.0
	 */
	public static String getCurrentDebuggerId() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getString(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID);
	}

	/**
	 * Returns true if the auto-save is on for any dirty file that exists when a
	 * Run/Debug launch is triggered.
	 * 
	 * @deprecated since PDT 1.0, this method simply extracts the value of
	 *             IInternalDebugUIConstants
	 *             .PREF_SAVE_DIRTY_EDITORS_BEFORE_LAUNCH from the
	 *             {@link DebugUIPlugin}
	 */
	public static boolean getAutoSaveDirtyOption() {
		String saveDirty = DebugUIPlugin
				.getDefault()
				.getPreferenceStore()
				.getString(
						IInternalDebugUIConstants.PREF_SAVE_DIRTY_EDITORS_BEFORE_LAUNCH);
		if (saveDirty == null) {
			return true;
		}
		return Boolean.valueOf(saveDirty).booleanValue();
	}

	public static boolean getOpenDebugViewsOption() {
		Preferences prefs = getDefault().getPluginPreferences();
		return prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS);

	}

	/**
	 * Returns the debugger port for the given debugger id. Return -1 if the
	 * debuggerId does not exist, or the debugger does not have a debug port.
	 * 
	 * @param debuggerId
	 * @return The debug port, or -1.
	 */
	public static int getDebugPort(String debuggerId) {
		AbstractDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry
				.getDebuggerConfiguration(debuggerId);
		if (debuggerConfiguration == null) {
			return -1;
		}
		return debuggerConfiguration.getPort();
	}

	/**
	 * Returns debug hosts
	 * 
	 * @return debug hosts suitable for URL parameter
	 */
	public static String getDebugHosts() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		return prefs.getString(PHPDebugCorePreferenceNames.CLIENT_IP);
	}

	public static String getWorkspaceDefaultServer() {
		Preferences serverPrefs = org.eclipse.php.internal.server.core.Activator
				.getDefault().getPluginPreferences();
		return serverPrefs
				.getString(ServersManager.DEFAULT_SERVER_PREFERENCES_KEY);

	}

	/**
	 * Creates a default server in case the ServersManager does not hold any
	 * defined server.
	 */
	public static void createDefaultPHPServer() {
		if (ServersManager.getServers().length == 0) {
			Server server = null;
			try {
				server = ServersManager.createServer(
						IPHPDebugConstants.Default_Server_Name, BASE_URL);
			} catch (MalformedURLException e) {
				// safe - no exception
			}
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
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR,
				"PHPDebug plugin internal error", e)); //$NON-NLS-1$
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

	public static void logWarningMessage(String message) {
		log(new Status(IStatus.WARNING, ID, INTERNAL_WARNING, message, null));
	}

	/**
	 * Returns if multiple sessions of debug launches are allowed when one of
	 * the launches contains a 'debug all pages' attribute.
	 * 
	 * @return True, the multiple sessions are allowed; False, otherwise.
	 */
	public static boolean supportsMultipleDebugAllPages() {
		return fIsSupportingMultipleDebugAllPages;
	}

	/**
	 * Allow or disallow the multiple debug sessions that has a launch attribute
	 * of 'debug all pages'.
	 * 
	 * @param supported
	 */
	public static void setMultipleDebugAllPages(boolean supported) {
		fIsSupportingMultipleDebugAllPages = supported;
	}

	//
	// /**
	// * Returns true if the auto remove launches was disabled by a PHP launch.
	// * The auto remove flag is usually disabled when a PHP server launch was
	// triggered and a
	// * 'debug all pages' flag was on.
	// * Note that this method will return true only if a php launch set it and
	// the debug preferences has a 'true'
	// * value for IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES.
	// *
	// * @return True iff the auto remove old launches was disabled.
	// */
	// public static boolean isDisablingAutoRemoveLaunches() {
	// return fDisableAutoRemoveLaunches;
	// }

	/**
	 * Enable or disable the auto remove old launches flag. The auto remove flag
	 * is usually disabled when a PHP server launch was triggered and a 'debug
	 * all pages' flag was on. Note that this method actually sets the
	 * IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES preferences key for the
	 * {@link DebugUIPlugin}.
	 * 
	 * @param disableAutoRemoveLaunches
	 */
	public static void setDisableAutoRemoveLaunches(
			boolean disableAutoRemoveLaunches) {
		if (DebugUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES) == disableAutoRemoveLaunches) {
			fLaunchChangedAutoRemoveLaunches = true;
			DebugUIPlugin
					.getDefault()
					.getPreferenceStore()
					.setValue(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES,
							!disableAutoRemoveLaunches);
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

	/**
	 * Get active debug target
	 */
	public static IDebugTarget getActiveDebugTarget() {
		IDebugTarget debugTarget = null;
		IAdaptable adaptable = DebugUITools.getDebugContext();
		if (adaptable != null) {
			IDebugElement element = (IDebugElement) adaptable
					.getAdapter(IDebugElement.class);
			if (element != null) {
				debugTarget = element.getDebugTarget();
			}
		}
		if (debugTarget == null) {
			IProcess process = DebugUITools.getCurrentProcess();
			if (process instanceof PHPProcess) {
				debugTarget = ((PHPProcess) process).getDebugTarget();
			}
		}
		return debugTarget;
	}

	/**
	 * Get active remote debugger
	 */
	public static IRemoteDebugger getActiveRemoteDebugger() {
		IDebugTarget debugTarget = getActiveDebugTarget();
		if (debugTarget != null && debugTarget instanceof PHPDebugTarget) {
			PHPDebugTarget phpDebugTarget = (PHPDebugTarget) debugTarget;
			return phpDebugTarget.getRemoteDebugger();
		}
		return null;
	}

	//
	private class AutoRemoveOldLaunchesListener implements
			IPropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			if (IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES.equals(event
					.getProperty())) {
				if (fLaunchChangedAutoRemoveLaunches) {
					fLaunchChangedAutoRemoveLaunches = false;// We got the
					// event, so
					// reset the
					// flag.
				} else {
					// The event was triggered from some other source - e.g. The
					// user changed the preferences manually.
					fInitialAutoRemoveLaunches = Boolean.valueOf(event
							.getNewValue().toString());
				}
			}
		}
	}

	public static String getCurrentDebuggerId(IProject project) {
		if (project != null) {
			PHPVersion phpVersion = ProjectOptions.getPhpVersion(project);
			if (phpVersion != null) {
				return getCurrentDebuggerId(phpVersion);
			}
		}
		return getCurrentDebuggerId();
	}

	public static String getCurrentDebuggerId(PHPVersion phpVersion) {
		PHPexeItem item = PHPexes.getInstance().getDefaultItemForPHPVersion(
				phpVersion);
		if (item != null) {
			return item.getDebuggerID();
		}
		return getCurrentDebuggerId();
	}

	// public static PHPexeItem getPHPexeItem(IProject project) {
	//
	// }
	public static PHPexeItem getPHPexeItem(IProject project) {
		if (project != null) {

			IEclipsePreferences node = createPreferenceScopes(project)[0]
					.getNode(PHPProjectPreferences.getPreferenceNodeQualifier());
			if (node != null) {
				// Replace the workspace defaults with the project-specific
				// settings.
				String phpDebuggerId = node.get(
						PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, null);
				String phpExe = node.get(
						PHPDebugCorePreferenceNames.DEFAULT_PHP, null);
				if (phpDebuggerId != null && phpExe != null) {
					return PHPexes.getInstance().getItem(phpDebuggerId, phpExe);
				}
			}
			PHPVersion phpVersion = ProjectOptions.getPhpVersion(project);
			if (phpVersion != null) {
				return getPHPexeItem(phpVersion);
			}
		}

		return getWorkspaceDefaultExe();
	}

	public static PHPexeItem getPHPexeItem(PHPVersion phpVersion) {
		PHPexeItem item = PHPexes.getInstance().getDefaultItemForPHPVersion(
				phpVersion);
		if (item != null) {
			return item;
		}
		return getWorkspaceDefaultExe();
	}

	public static PHPexeItem getWorkspaceDefaultExe() {
		String phpDebuggerId = PHPDebugPlugin.getCurrentDebuggerId();
		return PHPexes.getInstance().getDefaultItem(phpDebuggerId);
	}

	// Creates a preferences scope for the given project.
	// This scope will be used to search for preferences values.
	public static IScopeContext[] createPreferenceScopes(IProject project) {
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project),
					new InstanceScope(), new DefaultScope() };
		}
		return new IScopeContext[] { new InstanceScope(), new DefaultScope() };
	}
}
