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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

/**
 * A debug session initializer.
 */
public class PHPWebServerDebuggerInitializer implements IDebuggerInitializer {
	private DebugException exception;
	private static boolean isDebugMode = System.getProperty("loggingDebug") != null;

	public void debug(ILaunch launch) throws DebugException {
		exception = null;
		IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry.getBestMatchDebugParametersInitializer(launch);
		final String encodedURL = parametersInitializer.getRequestURL(launch).replaceAll(" ", "%20");
		boolean runWithDebug = true;
		try {
			runWithDebug = launch.getLaunchConfiguration().getAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
		} catch (CoreException e1) {
		}

		final String debugQuery;
		if (!runWithDebug && ILaunchManager.RUN_MODE.equals(launch.getLaunchMode())) {
			debugQuery = encodedURL;
		} else {
			String query = parametersInitializer.generateQuery(launch);
			if (encodedURL.indexOf('?') == -1) {
				debugQuery = encodedURL + '?' + query;
			} else {
				debugQuery = encodedURL + '&' + query;
			}
		}

		if (isDebugMode) {
			System.out.println("debugQuery = " + debugQuery);
		}
		boolean openInBrowser = false;
		try {
			openInBrowser = launch.getLaunchConfiguration().getAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, false);
		} catch (Throwable t) {
			Logger.logException("Error obtaining the 'openInBrowser' configuration.", t);
		}
		if (openInBrowser) {
			// Start the debug session by openning a browser that will actually trigger the URL connection
			// to the debug server.

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						int browserStyle = IWorkbenchBrowserSupport.LOCATION_BAR | IWorkbenchBrowserSupport.NAVIGATION_BAR | IWorkbenchBrowserSupport.STATUS;
						PlatformUI.getWorkbench().getBrowserSupport().createBrowser(browserStyle, null, encodedURL, debugQuery).openURL(new URL(debugQuery));
					} catch (Throwable t) {
						Logger.logException("Error initializing the web browser.", t);
						String errorMessage = PHPDebugCoreMessages.Debugger_Unexpected_Error_1;
						exception = new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.INTERNAL_ERROR, errorMessage, t));
					}
				}
			});
			if (exception != null) {
				throw exception;
			}
		} else {
			try {
				connect(new URL(debugQuery));
			} catch (java.net.MalformedURLException e) {
				Logger.logException("Malformed URL Exception " + debugQuery, e); //Debugger_Unexpected_Error
				String errorMessage = PHPDebugCoreMessages.Debugger_Unexpected_Error_1;
				throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.INTERNAL_ERROR, errorMessage, e));
			}
		}
	}

	public static void connect(URL url) throws DebugException {
		try {
			URLConnection connection = url.openConnection();
			String headerKey = connection.getHeaderFieldKey(1);
			if (headerKey == null) {
				Logger.log(Logger.WARNING, "No HeaderKey returned by server. Most likely not started");
				String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_1;
				throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.INTERNAL_ERROR, errorMessage, null));
			}

			for (int i = 1; (headerKey = connection.getHeaderFieldKey(i)) != null; i++) {
				if (headerKey.equals("X-Zend-Debug-Server")) {
					String headerValue = connection.getHeaderField(headerKey);
					if (!headerValue.equals("OK")) {
						Logger.log(Logger.WARNING, "Unexpected Header Value returned by Server. " + headerValue);
						String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_2 + " - " + headerValue;
						throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.INTERNAL_ERROR, errorMessage, null));
					}
					break;
				}
			}

			InputStream inputStream = connection.getInputStream();
			while (inputStream.read() != -1)
				;

		} catch (UnknownHostException exc) {
			Logger.log(Logger.WARNING, "Unknown Host Exception.");
			String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_3;
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.INTERNAL_ERROR, errorMessage, exc));
		} catch (ConnectException exc) {
			Logger.logException("Unable to connect to URL " + url, exc);
			String errorMessage = MessageFormat.format(PHPDebugCoreMessages.DebuggerConnection_Failed_1, new String[] { url.toString() });
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.DEBUG_CONNECTION_ERROR, errorMessage, null));
		} catch (IOException exc) {
			Logger.logException("Unable to connect to URL " + url, exc);
			String baseURL = url.toString();
			int queryPartIndex = baseURL.indexOf('?');
			if (queryPartIndex > -1) {
				baseURL = baseURL.substring(0, queryPartIndex);
			}
			String filePath = url.getPath();
			if (filePath.startsWith("/")) {
				filePath = filePath.substring(1);
			}
			String errorMessage = MessageFormat.format(PHPDebugCoreMessages.DebuggerConnection_Failed_1, new String[] { baseURL, filePath });
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.DEBUG_CONNECTION_ERROR, errorMessage, null));
		} catch (Exception exc) {
			Logger.logException("Unexpected exception communicating with server", exc);
			String errorMessage = exc.getMessage();
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPDebugConstants.INTERNAL_ERROR, errorMessage, exc));
		}
	}

}