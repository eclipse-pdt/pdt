/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
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

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsManager;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.launching.XDebugLaunchListener;
import org.eclipse.php.internal.debug.core.preferences.*;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandlersManager;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * The PHP Debug plug-in class.
 */
public class PHPDebugPlugin extends Plugin {

	/**
	 * This class is used to start separate non-UI job just right after
	 * encompassing bundle is started to perform "right-after startup"
	 * operations. Everything that doesn't need UI thread and is not required to
	 * be initialized only while starting bundle should be placed here.
	 */
	private class PostStart implements BundleListener {

		@Override
		public void bundleChanged(BundleEvent event) {
			if (event.getBundle() == getBundle() && event.getType() == BundleEvent.STARTED) {
				Job handler = new Job(PHPDebugCoreMessages.PHPDebugPlugin_PostStartup) {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						// Perform 'Post Startup'
						try {
							monitor.beginTask(PHPDebugCoreMessages.PHPDebugPlugin_PerformingPostStartupOperations,
									IProgressMonitor.UNKNOWN);
							perform();
						} catch (Exception e) {
							Logger.logException(MessageFormat.format(
									"Errors occurred while performing ''{0}'' bundle post startup.", //$NON-NLS-1$
									ID), e);
						} finally {
							// Unregister itself from listeners
							getBundle().getBundleContext().removeBundleListener(PostStart.this);
							monitor.done();
						}
						return Status.OK_STATUS;
					}
				};
				// Hide from the user
				handler.setUser(false);
				handler.setSystem(true);
				handler.schedule();
			}
		}

		private void perform() {
			// Set the auto-remove old launches listener
			IPreferenceStore preferenceStore = DebugUIPlugin.getDefault().getPreferenceStore();
			fInitialAutoRemoveLaunches = preferenceStore.getBoolean(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES);
			preferenceStore.addPropertyChangeListener(new AutoRemoveOldLaunchesListener());
			org.eclipse.php.internal.server.core.Activator.getDefault();
			XDebugPreferenceMgr.setDefaults();
			XDebugLaunchListener.getInstance();
			DaemonPlugin.getDefault();
			DebuggerSettingsManager.INSTANCE.startup();
			DBGpProxyHandlersManager.INSTANCE.startup();
		}

	}

	private static class ExtDirSupport {

		private static final String EXTENSION_POINT_NAME = "phpExe"; //$NON-NLS-1$
		private static final String LOCATION_ATTRIBUTE = "location"; //$NON-NLS-1$
		private static final String PHPEXE_TAG = "phpExe"; //$NON-NLS-1$

		/**
		 * Sets extension_dir in php.ini file for all php executables (i.e.
		 * resources/php53, resources/php54) provided by extension point
		 * "phpExe", for OSs different than Windows.
		 * 
		 * @throws IOException
		 * @throws URISyntaxException
		 */
		public static void setExtDirInPHPIniFile() {

			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			final IConfigurationElement[] elements = registry.getConfigurationElementsFor("org.eclipse.php.debug.core",
					EXTENSION_POINT_NAME);
			Matcher matcher = null;
			Pattern pattern = null;
			for (final IConfigurationElement element : elements) {
				if (PHPEXE_TAG.equals(element.getName())) {
					try {
						String location = substitudeVariables(element.getAttribute(LOCATION_ATTRIBUTE));
						final String pluginId = element.getDeclaringExtension().getNamespaceIdentifier();
						location = location.substring(0, location.lastIndexOf("/")) //$NON-NLS-1$
								.concat("/php.ini"); //$NON-NLS-1$

						File phpIni = getFileFromLocation(location, pluginId);
						if (phpIni == null)
							continue;

						File phpIniDir = phpIni.getParentFile();
						String fileName = phpIni.getAbsolutePath();
						String iniContent = readFile(fileName);
						String correctDir = phpIniDir.getAbsolutePath().concat(File.separator).replaceAll("\\\\",
								"\\\\\\\\");
						pattern = Pattern.compile("(extension_dir=.*)(ext[^\\s]*)");
						matcher = pattern.matcher(iniContent);
						if (matcher.find()) {
							Path iniPath = new Path(matcher.group(1).replaceAll("extension_dir=", ""));
							Path currentPath = new Path(correctDir);
							if (!currentPath.isPrefixOf(iniPath)) {
								iniContent = iniContent.replaceAll(matcher.group(0).replaceAll("\\\\", "\\\\\\\\"),
										"extension_dir=\"" + correctDir
												+ matcher.group(2).replace("\"", "").replaceAll("\\\\", "\\\\\\\\")
												+ "\"");
								pattern = Pattern.compile("(zend_extension=.*)(ext[^\\s]ZendDebugger[^\\s]*)");
								matcher = pattern.matcher(iniContent);
								if (matcher.find()) {
									iniContent = iniContent.replaceAll(matcher.group(0).replaceAll("\\\\", "\\\\\\\\"),
											"zend_extension=\"" + correctDir
													+ matcher.group(2).replace("\"", "").replaceAll("\\\\", "\\\\\\\\")
													+ "\"");
								}
								pattern = Pattern.compile("(zend_extension=.*)(ext[^\\s]xdebug[^\\s]*)");
								matcher = pattern.matcher(iniContent);
								if (matcher.find()) {
									iniContent = iniContent.replaceAll(matcher.group(0).replaceAll("\\\\", "\\\\\\\\"),
											"zend_extension=\"" + correctDir
													+ matcher.group(2).replace("\"", "").replaceAll("\\\\", "\\\\\\\\")
													+ "\"");
								}
								pattern = Pattern.compile("(openssl.cafile=.*)(ca-bundle.crt[^\\s]*)");
								matcher = pattern.matcher(iniContent);
								if (matcher.find()) {
									iniContent = iniContent.replaceAll(matcher.group(0).replaceAll("\\\\", "\\\\\\\\"),
											"openssl.cafile=\"" + correctDir + matcher.group(2).replace("\"", "")
													+ "\"");
								}
								BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
								writer.write(iniContent);
								writer.flush();
								writer.close();
							}
						}
					} catch (Exception ex) {
						log(ex);
					}
				}
			}
		}

		private static String substitudeVariables(String expression) throws CoreException {
			return VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(expression, true);
		}

		private static File getFileFromLocation(String location, String pluginId) throws IOException {
			if (Paths.get(location).isAbsolute()) {
				return new File(location);
			} else {
				URL url = FileLocator.find(Platform.getBundle(pluginId), new Path(location), null);
				if (url != null) {
					url = FileLocator.resolve(url);
					String filename = url.getFile();
					return new File(filename);
				}
			}
			return null;
		}

		private static String readFile(String file) throws IOException {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line = null;
				StringBuilder stringBuilder = new StringBuilder();
				String ls = System.getProperty("line.separator");

				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}

				return stringBuilder.toString();
			}
		}

	}

	public static final String ID = "org.eclipse.php.debug.core"; //$NON-NLS-1$
	public static final int INTERNAL_ERROR = 10001;
	public static final int INTERNAL_WARNING = 10002;

	// The shared instance.
	private static PHPDebugPlugin plugin;
	private static boolean fIsSupportingMultipleDebugAllPages = true;
	private boolean fInitialAutoRemoveLaunches;
	private static boolean fLaunchChangedAutoRemoveLaunches;

	/**
	 * The constructor.
	 */
	public PHPDebugPlugin() {
		plugin = this;
		getBundle().getBundleContext().addBundleListener(new PostStart());
	}

	public static final boolean DEBUG = Boolean.valueOf(Platform.getDebugOption("org.eclipse.php.debug.core/debug")) //$NON-NLS-1$
			.booleanValue();

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		ExtDirSupport.setExtDirInPHPIniFile();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		XDebugLaunchListener.shutdown();
		DBGpProxyHandlersManager.INSTANCE.shutdown();
		InstanceScope.INSTANCE.getNode(ID).flush();
		DebuggerSettingsManager.INSTANCE.shutdown();
		super.stop(context);
		plugin = null;
		DebugUIPlugin.getDefault().getPreferenceStore().setValue(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES,
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
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, false, null);

	}

	public static boolean getDebugInfoOption() {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, false, null);

	}

	public static boolean getOpenInBrowserOption() {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, false, null);
	}

	/**
	 * Return default debugger id.
	 * 
	 * @return default debugger id
	 */
	public static String getCurrentDebuggerId() {
		// For backward compatibility try to get default debugger from
		// preferences
		String id = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, null, null);
		if (id == null || id.isEmpty()) {
			return DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID;
		}
		return id;
	}

	/**
	 * Return id of debugger associated with specified server.
	 * 
	 * @param serverName
	 * @return debugger id
	 */
	public static String getDebuggerId(String serverName) {
		if (serverName != null) {
			Server server = ServersManager.getServer(serverName);
			if (server != null) {
				String serverDebuggerId = server.getDebuggerId();
				if (serverDebuggerId != null) {
					return serverDebuggerId;
				}
			}
		}
		return PHPDebugPlugin.getCurrentDebuggerId();
	}

	public static boolean getOpenDebugViewsOption() {
		return Platform.getPreferencesService().getBoolean(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, false, null);

	}

	/**
	 * Returns the debugger port for the given debugger id. Return -1 if the
	 * debuggerId does not exist, or the debugger does not have a debug port.
	 * 
	 * @param debuggerId
	 * @return The debug port, or -1.
	 */
	public static int getDebugPort(String debuggerId) {
		IDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry.getDebuggerConfiguration(debuggerId);
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
		return Platform.getPreferencesService().getString(PHPDebugPlugin.ID, PHPDebugCorePreferenceNames.CLIENT_IP,
				null, null);
	}

	public static String getWorkspaceDefaultServer() {
		return org.eclipse.php.internal.server.core.Activator.getWorkspaceDefaultServer();

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
	public static void setDisableAutoRemoveLaunches(boolean disableAutoRemoveLaunches) {
		if (DebugUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES) == disableAutoRemoveLaunches) {
			fLaunchChangedAutoRemoveLaunches = true;
			DebugUIPlugin.getDefault().getPreferenceStore().setValue(IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES,
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
			IDebugElement element = (IDebugElement) adaptable.getAdapter(IDebugElement.class);
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
	private class AutoRemoveOldLaunchesListener implements IPropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			if (IDebugUIConstants.PREF_AUTO_REMOVE_OLD_LAUNCHES.equals(event.getProperty())) {
				if (fLaunchChangedAutoRemoveLaunches) {
					fLaunchChangedAutoRemoveLaunches = false;// We got the
					// event, so
					// reset the
					// flag.
				} else {
					// The event was triggered from some other source - e.g. The
					// user changed the preferences manually.
					fInitialAutoRemoveLaunches = Boolean.valueOf(event.getNewValue().toString());
				}
			}
		}
	}

	public static String getCurrentDebuggerId(IProject project) {
		if (project != null) {
			PHPVersion phpVersion = ProjectOptions.getPHPVersion(project);
			if (phpVersion != null) {
				return getCurrentDebuggerId(phpVersion);
			}
		}
		return getCurrentDebuggerId();
	}

	public static String getCurrentDebuggerId(PHPVersion phpVersion) {
		PHPexeItem item = PHPexes.getInstance().getDefaultItemForPHPVersion(phpVersion);
		if (item != null) {
			return item.getDebuggerID();
		}
		return getCurrentDebuggerId();
	}

	public static PHPexeItem getPHPexeItem(IProject project) {
		if (project != null) {

			IEclipsePreferences node = createPreferenceScopes(project)[0]
					.getNode(PHPProjectPreferences.getPreferenceNodeQualifier());
			if (node != null) {
				// Replace the workspace defaults with the project-specific
				// settings.
				String phpExe = node.get(PHPDebugCorePreferenceNames.DEFAULT_PHP, null);
				if (phpExe != null) {
					return PHPexes.getInstance().getItem(phpExe);
				}
			}
			PHPVersion phpVersion = ProjectOptions.getPHPVersion(project);
			if (phpVersion != null) {
				return getPHPexeItem(phpVersion);
			}
		}

		return getWorkspaceDefaultExe();
	}

	public static PHPexeItem getPHPexeItem(PHPVersion phpVersion) {
		PHPexeItem item = PHPexes.getInstance().getDefaultItemForPHPVersion(phpVersion);
		if (item != null) {
			return item;
		}
		return getWorkspaceDefaultExe();
	}

	public static PHPexeItem getWorkspaceDefaultExe() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID);
		if (prefs != null) {
			String exeName = prefs.get(PHPDebugCorePreferenceNames.DEFAULT_PHP, null);
			if (exeName != null && !exeName.isEmpty()) {
				return PHPexes.getInstance().getItem(exeName);
			}
		}
		return null;
	}

	// Creates a preferences scope for the given project.
	// This scope will be used to search for preferences values.
	public static IScopeContext[] createPreferenceScopes(IProject project) {
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		}
		return new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
	}

	/**
	 * Returns if the current file name is actually a dummy file.
	 */
	public static boolean isDummyFile(String fileName) {
		if (fileName != null) {
			String dummyFile = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
					PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", //$NON-NLS-1$
					null);
			int idx = Math.max(dummyFile.lastIndexOf('/'), dummyFile.lastIndexOf('\\'));
			if (idx != -1) {
				dummyFile = dummyFile.substring(idx + 1);
			}

			idx = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
			if (idx != -1) {
				fileName = fileName.substring(idx + 1); // strip everything but
														// last segment
			}
			return fileName.equals(dummyFile);
		}
		return false;
	}

	public static IEclipsePreferences getInstancePreferences() {
		return InstanceScope.INSTANCE.getNode(ID);
	}

	public static IEclipsePreferences getDefaultPreferences() {
		return DefaultScope.INSTANCE.getNode(ID);
	}

}
