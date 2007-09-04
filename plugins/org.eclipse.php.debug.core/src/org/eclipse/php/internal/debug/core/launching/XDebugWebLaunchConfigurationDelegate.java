/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.launching;

import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.xdebug.GeneralUtils;
import org.eclipse.php.internal.debug.core.xdebug.IDELayer;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpUtils;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.php.internal.server.core.PHPServerCoreMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.deploy.DeployFilter;
import org.eclipse.php.internal.server.core.deploy.FileUtil;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.internal.browser.WebBrowserPreference;

public class XDebugWebLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	public XDebugWebLaunchConfigurationDelegate() {
	}

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (!DaemonPlugin.getDefault().validateCommunicationDaemons(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID)) {
			monitor.setCanceled(true);
			monitor.done();
			return;
		}
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			if (XDebugLaunchListener.getInstance().isWebLaunchActive()) {
				displayErrorMessage("Web Launch Already Running");
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				return;
			}
			PHPLaunchUtilities.showDebugView();
		}

		// Resolve the Server
		Server server = ServersManager.getServer(configuration.getAttribute(Server.NAME, ""));
		if (server == null) {
			Logger.log(Logger.ERROR, "Launch configuration could not find server");
			displayErrorMessage("Could not launch.\nInvalid server configuration.");
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
				Logger.logException("Could not execute the debug (Project is null).", t);
				return;
			}
		}

		// Publish the files if specified.
		boolean publish = configuration.getAttribute(Server.PUBLISH, false);
		if (publish) {
			if (!FileUtil.publish(server, proj, configuration, DeployFilter.getFilterMap(), monitor)) {
				// Return if the publish failed.
				displayErrorMessage(PHPServerCoreMessages.getString("FileUtil.serverPublishError"));
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
				return;
			}
		}

		// save the project name for source lookup
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		String project = proj.getFullPath().toString();
		wc.setAttribute(IPHPConstants.PHP_Project, project);
		wc.doSave();

		// determine stop at first line (first calc the default and then try to extract the configuration attribute).
		IDELayer ide = IDELayerFactory.getIDELayer();
		boolean stopAtFirstLine = ide.getStopAtFirstLine(proj);
		stopAtFirstLine = wc.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, stopAtFirstLine);

		// determine from eclipse config whether we use an internal browser or
		// an external browser.
		final boolean openExternal = openExternal();
		final Exception[] exception = new Exception[1];
		final IWebBrowser[] browser = new IWebBrowser[1];
		if (openExternal) {
			browser[0] = PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser();
		}

		// Generate a session id for this launch and start the listener
		// then create the start and stop debug URLs
		String[] startStopURLs;
		String baseURL = new String(configuration.getAttribute(Server.BASE_URL, "").getBytes());
		DBGpTarget target = null;
		
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {		
			String ideKey = DBGpSessionHandler.getInstance().getIDEKey();
			String sessionId = DBGpSessionHandler.getInstance().generateSessionId();
			startStopURLs = generateStartStopDebugURLs(baseURL, sessionId, ideKey);
			String launchScript = configuration.getAttribute(Server.FILE_NAME, (String) null);						
			target = new DBGpTarget(launch, launchScript, startStopURLs[1], ideKey, stopAtFirstLine, browser[0]);
			DBGpSessionHandler.getInstance().addSessionListener(target);			
		}
		else {
			startStopURLs = new String[] {baseURL, null};
		}
		final String startURL = startStopURLs[0];
		
		// load the URL into the appropriate web browser
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 30);
		subMonitor.beginTask("Launching browser", 10);

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					if (openExternal) {
						browser[0].openURL(new URL(startURL));
					} else {
						DBGpUtils.openInternalBrowserView(startURL);
					}
				} catch (Exception t) {
					Logger.logException("Error initializing the web browser.", t);
					exception[0] = t;
				}
			}
		});

		subMonitor.worked(10);

		// did the external browser start ok ?
		if (exception[0] == null) {
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				launch.addDebugTarget(target);
				subMonitor.subTask("waiting for XDebug session");
				target.waitForInitialSession((DBGpBreakpointFacade) IDELayerFactory.getIDELayer(), GeneralUtils.createSessionPreferences(), monitor);
			}
			else {
				// launched ok, so remove the launch from the debug view as we are not debugging.
				DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);				
			}
		} else {
			// display an error about not being able to launch a browser
			Logger.logException("we have an exception on the browser", exception[0]);
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				DBGpSessionHandler.getInstance().removeSessionListener(target);
			}
			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
		}
		subMonitor.done();
	}

	/**
	 * Displays a dialog with an error message.
	 * 
	 * @param message The error to display.
	 */
	protected void displayErrorMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Debug Error", message);
			}
		});
	}

	/**
	 * determine whether an external browser is going to be opened
	 * @return true if external browser is required
	 */
	private boolean openExternal() {
		return (WebBrowserPreference.EXTERNAL == WebBrowserPreference.getBrowserChoice());
	}

	/**
	 * generate the URLS that start the debug environment and stop the debug environment.
	 * @param baseURL the base URL
	 * @param sessionId the DBGp session Id
	 * @param ideKey the DBGp IDE Key
	 * @return start and stop queries
	 */
	public String[] generateStartStopDebugURLs(String baseURL, String sessionId, String ideKey) {
		String[] startStopURLs = new String[2];

		if (baseURL.indexOf("?") > -1) {
			baseURL += "&";
		} else {
			baseURL += "?";
		}

		startStopURLs[0] = baseURL + "XDEBUG_SESSION_START=" + ideKey + "&KEY=" + sessionId;
		startStopURLs[1] = baseURL + "XDEBUG_SESSION_STOP=" + ideKey + "&KEY=" + sessionId;
		return startStopURLs;
	}
}
