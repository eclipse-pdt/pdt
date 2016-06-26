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
package org.eclipse.php.internal.debug.core.launching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.core.debugger.launching.ILaunchDelegateListener;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.*;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.communication.XDebugCommunicationDaemon;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandlersManager;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsUtil;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpMultiSessionTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.IDBGpDebugTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.IDBGpSessionListener;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.server.core.tunneling.SSHTunnel;
import org.eclipse.swt.widgets.Display;

@SuppressWarnings("restriction")
public class XDebugWebLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	private static final String LAUNCH_LISTENERS_EXTENSION_ID = "org.eclipse.php.debug.core.phpLaunchDelegateListener"; //$NON-NLS-1$

	/*
	 * list of registered ILaunchDelegateListeners
	 */
	private List<ILaunchDelegateListener> preLaunchListeners = new ArrayList<ILaunchDelegateListener>();

	public XDebugWebLaunchConfigurationDelegate() {
		registerLaunchListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.LaunchConfigurationDelegate#getLaunch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String)
	 */
	@Override
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
		return new XDebugLaunch(configuration, mode, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		// Notify all listeners of a pre-launch event.
		int resultCode = notifyPreLaunch(configuration, mode, launch, monitor);
		if (resultCode != 0) { // cancel launch
			monitor.setCanceled(true);
			monitor.done();
			return; // canceled
		}
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			if (XDebugLaunchListener.getInstance().isWebLaunchActive()) {
				displayErrorMessage(PHPDebugCoreMessages.XDebug_WebLaunchConfigurationDelegate_0);
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				return;
			}
			PHPLaunchUtilities.showDebugView();
		}
		// Resolve the server
		Server server = ServersManager.getServer(configuration.getAttribute(Server.NAME, "")); //$NON-NLS-1$
		if (server == null) {
			Logger.log(Logger.ERROR, "Launch configuration could not find server"); //$NON-NLS-1$
			displayErrorMessage(PHPDebugCoreMessages.XDebug_WebLaunchConfigurationDelegate_1);
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			return;
		}
		// Get the project from the file name
		String fileName = configuration.getAttribute(Server.FILE_NAME, (String) null);
		IPath filePath = new Path(fileName);
		IProject proj = null;
		try {
			proj = ResourcesPlugin.getWorkspace().getRoot().getProject(filePath.segment(0));
		} catch (Throwable t) {
			if (proj == null) {
				Logger.logException("Could not execute the debug (Project is null).", t); //$NON-NLS-1$
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				return;
			}
		}
		// Save the project name for source lookup
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		String project = proj.getFullPath().toString();
		wc.setAttribute(IPHPDebugConstants.PHP_Project, project);
		wc.setAttribute(IDebugParametersKeys.TRANSFER_ENCODING, PHPProjectPreferences.getTransferEncoding(proj));
		wc.setAttribute(IDebugParametersKeys.OUTPUT_ENCODING, PHPProjectPreferences.getOutputEncoding(proj));
		wc.doSave();
		/*
		 * Determine stop at first line (first calculate the default and then
		 * try to extract the configuration attribute).
		 */
		boolean stopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(proj);
		stopAtFirstLine = wc.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, stopAtFirstLine);
		/*
		 * Generate a session id for this launch and start the listener then
		 * create the start and stop debug URLs
		 */
		String[] startStopURLs;
		String baseURL = new String(configuration.getAttribute(Server.BASE_URL, "").getBytes()); //$NON-NLS-1$
		IDBGpDebugTarget target = null;
		SSHTunnel tunnel = null;
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			String sessionId = DBGpSessionHandler.getInstance().generateSessionId();
			String ideKey = null;
			DBGpProxyHandler proxyHandler = DBGpProxyHandlersManager.INSTANCE.getHandler(server.getUniqueId());
			if (proxyHandler != null && proxyHandler.useProxy()) {
				ideKey = proxyHandler.getCurrentIdeKey();
				if (proxyHandler.registerWithProxy() == false) {
					displayErrorMessage(
							PHPDebugCoreMessages.XDebug_WebLaunchConfigurationDelegate_2 + proxyHandler.getErrorMsg());
					DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
					return;
				}
			} else {
				ideKey = DBGpSessionHandler.getInstance().getIDEKey();
			}
			startStopURLs = generateStartStopDebugURLs(baseURL, sessionId, ideKey);
			String launchScript = configuration.getAttribute(Server.FILE_NAME, (String) null);
			// Check if a tunneled connection is needed and create request for a
			// tunnel if needed.
			tunnel = PHPLaunchUtilities.getSSHTunnel(configuration);
			// determine if we should use the multisession manager or the single
			// session manager
			if (XDebugPreferenceMgr.useMultiSession() == true) {
				target = new DBGpMultiSessionTarget(launch, launchScript, startStopURLs[1], ideKey, stopAtFirstLine);
				target.setPathMapper(PathMapperRegistry.getByServer(server));
				launch.addDebugTarget(target); // has to be added now, not
			} else {
				target = new DBGpTarget(launch, launchScript, startStopURLs[1], ideKey, null, stopAtFirstLine);
				target.setPathMapper(PathMapperRegistry.getByServer(server));
				IProcess process = new PHPProcess(launch,
						PHPDebugCoreMessages.XDebugWebLaunchConfigurationDelegate_PHP_process);
				process.setAttribute(IProcess.ATTR_PROCESS_TYPE, IPHPDebugConstants.PHPProcessType);
				((DBGpTarget) target).setProcess(process);
				((PHPProcess) process).setDebugTarget(target);
				launch.addProcess(process);
			}
			DBGpSessionHandler.getInstance().addSessionListener((IDBGpSessionListener) target);
			int requestPort = getDebugPort(server);
			if (!PHPLaunchUtilities.isDebugDaemonActive(requestPort, XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID)) {
				PHPLaunchUtilities.showLaunchErrorMessage(NLS.bind(
						PHPDebugCoreMessages.WebLaunchConfigurationDelegate_PortInUse, requestPort, server.getName()));
				monitor.setCanceled(true);
				monitor.done();
				return;
			}

		} else {
			startStopURLs = new String[] { baseURL, null };
		}
		final String startURL = startStopURLs[0];
		// Will be used in the future?
		@SuppressWarnings("unused")
		final SSHTunnel sshTunnel = tunnel;
		// load the URL into the appropriate web browser
		monitor.beginTask("", 10); //$NON-NLS-1$
		monitor.subTask(PHPDebugCoreMessages.XDebug_WebLaunchConfigurationDelegate_3);
		try {
			PHPDebugUtil.openLaunchURL(startURL);
		} catch (Exception e) {
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				DBGpSessionHandler.getInstance().removeSessionListener((IDBGpSessionListener) target);
			}
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			monitor.done();
			return;
		}
		monitor.worked(5);
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			launch.addDebugTarget(target);
			monitor.subTask(PHPDebugCoreMessages.XDebug_WebLaunchConfigurationDelegate_4);
			if (target != null) {
				target.waitForInitialSession((DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
						XDebugPreferenceMgr.createSessionPreferences(), monitor);
			}
		} else {
			/*
			 * launched is not in debug mode, so remove the launch from the
			 * debug view as we are not debugging anything.
			 */
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
		}
		monitor.worked(5);
		monitor.done();
	}

	/**
	 * generate the URLS that start the debug environment and stop the debug
	 * environment.
	 * 
	 * @param baseURL
	 *            the base URL
	 * @param sessionId
	 *            the DBGp session Id
	 * @param ideKey
	 *            the DBGp IDE Key
	 * @return start and stop queries
	 */
	protected String[] generateStartStopDebugURLs(String baseURL, String sessionId, String ideKey) {
		String[] startStopURLs = new String[2];
		if (baseURL.indexOf("?") > -1) { //$NON-NLS-1$
			baseURL += "&"; //$NON-NLS-1$
		} else {
			baseURL += "?"; //$NON-NLS-1$
		}
		startStopURLs[0] = baseURL + "XDEBUG_SESSION_START=" + ideKey + "&KEY=" + sessionId; //$NON-NLS-1$ //$NON-NLS-2$
		startStopURLs[1] = baseURL + "XDEBUG_SESSION_STOP_NO_EXEC=" + ideKey + "&KEY=" + sessionId; //$NON-NLS-1$ //$NON-NLS-2$
		return startStopURLs;
	}

	protected int getDebugPort(Server server) {
		// Set custom port if any from debugger's owner settings
		int customRequestPort = XDebugDebuggerSettingsUtil.getDebugPort(server.getUniqueId());
		if (customRequestPort != -1)
			return customRequestPort;
		return PHPDebugPlugin.getDebugPort(XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID);
	}

	/**
	 * Displays a dialog with an error message.
	 * 
	 * @param message
	 *            The error to display.
	 */
	protected void displayErrorMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(),
						PHPDebugCoreMessages.XDebugMessage_debugError, message);
			}
		});
	}

	protected int notifyPreLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) {
		for (ILaunchDelegateListener listener : preLaunchListeners) {
			int returnCode = listener.preLaunch(configuration, mode, launch, monitor);
			if (returnCode != 0) {
				return returnCode;
			}
		}
		return 0;
	}

	/**
	 * Registers all pre-launch listeners.
	 */
	private void registerLaunchListeners() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(LAUNCH_LISTENERS_EXTENSION_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof ILaunchDelegateListener) {
					ISafeRunnable runnable = new ISafeRunnable() {
						public void run() throws Exception {
							ILaunchDelegateListener listener = (ILaunchDelegateListener) o;
							Assert.isNotNull(listener);
							preLaunchListeners.add(listener);
						}

						public void handleException(Throwable exception) {
							Logger.logException(exception);
						}
					};
					SafeRunner.run(runnable);
				}
			}
		} catch (CoreException ex) {
			Logger.logException(ex);
		}
	}

}
