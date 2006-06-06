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
package org.eclipse.php.debug.core.debugger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;

public class PHPWebServerDebuggerInitializer {

	private ILaunch launch;

	public PHPWebServerDebuggerInitializer(ILaunch launch) {
		this.launch = launch;
	}

	public void debug(String url, int port, boolean stopAtFirstLine, int debugSessionID) throws DebugException {
		IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry.getBestMatchDebugParametersInitializer(launch.getLaunchMode());
		parametersInitializer.addParameter(IDebugParametersKeys.PORT, new Integer(port));
		parametersInitializer.addParameter(IDebugParametersKeys.WEB_SERVER_DEBUGGER, Boolean.TRUE);
		parametersInitializer.addParameter(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, Boolean.valueOf(stopAtFirstLine));
		parametersInitializer.addParameter(IDebugParametersKeys.ORIGINAL_URL, url);
		parametersInitializer.addParameter(IDebugParametersKeys.SESSION_ID, new Integer(debugSessionID));

		String debugQuery = url + "?" + parametersInitializer.generateQuery();
		try {
			connect(new URL(debugQuery), port, false);
		} catch (java.net.MalformedURLException e) {
			Logger.logException("Malformed URL Exception " + debugQuery, e); //Debugger_Unexpected_Error
			String errorMessage = PHPDebugCoreMessages.Debugger_Unexpected_Error_1;
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, e));
		}
	}

	private void connect(URL url, int port, boolean isDebugPassive) throws DebugException {
		try {
			URLConnection connection = url.openConnection();
			String headerKey = connection.getHeaderFieldKey(1);
			if (headerKey == null) {
				Logger.log(Logger.WARNING, "No HeaderKey returned by server. Most likely not started");
				String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_1;
				throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null));
			}

			for (int i = 1; (headerKey = connection.getHeaderFieldKey(i)) != null; i++) {
				if (headerKey.equals("X-Zend-Debug-Server")) {
					String headerValue = connection.getHeaderField(headerKey);
					if (!headerValue.equals("OK")) {
						Logger.log(Logger.WARNING, "Unexpected Header Value returned by Server. " + headerValue);
						String errorMessage = PHPDebugCoreMessages.DebuggerConnection_Problem_2 + " - " + headerValue;
						throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null));
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
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, exc));
		} catch (ConnectException exc) {
			Logger.logException("Unable to connect to URL " + url, exc);
			String errorMessage = MessageFormat.format(PHPDebugCoreMessages.DebuggerConnection_Failed_1, new String[] { url.toString() });
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null));
		} catch (IOException exc) {
			Logger.logException("Unable to connect to URL " + url, exc);
			String errorMessage = MessageFormat.format(PHPDebugCoreMessages.DebuggerConnection_Failed_1, new String[] { url.toString() });
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null));
		} catch (Exception exc) {
			Logger.logException("Unexpected exception communicating with server", exc);
			String errorMessage = exc.getMessage();
			throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, exc));
		}
	}

}