/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsManager;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugSessionStartedNotification;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * Zend debugger settings owner utility class.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class ZendDebuggerSettingsUtil {

	private ZendDebuggerSettingsUtil() {
		// Private constructor - utility class
	}

	public static String getDebugHosts(String phpServerId) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(phpServerId,
				ZendDebuggerConfiguration.ID);
		String debugHosts = ""; //$NON-NLS-1$
		if (debuggerSettings instanceof ZendDebuggerServerSettings) {
			debugHosts = debuggerSettings.getAttribute(ZendDebuggerSettingsConstants.PROP_CLIENT_IP);
		}
		return debugHosts;
	}

	public static int getResponseTimeout(DebugSessionStartedNotification startedNotification) {
		/*
		 * Check if we have related server based on original URL host name, if
		 * yes then set timeout from its debugger settings
		 */
		String debugQuery = startedNotification.getQuery();
		String originalURLTrigger = "&original_url="; //$NON-NLS-1$
		int originalURLStart = debugQuery.indexOf(originalURLTrigger);
		String originalURL = debugQuery.substring(originalURLStart + originalURLTrigger.length());
		int responseTimeout = -1;
		try {
			URL url = new URL(originalURL);
		} catch (MalformedURLException ex) {
			// Is not valid URL - might be open file request
			return responseTimeout;
		}
		Server server = ServersManager.findByURL(originalURL);
		if (server != null) {
			IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(server.getUniqueId(),
					ZendDebuggerConfiguration.ID);
			String debugClientPort = null;
			if (debuggerSettings instanceof ZendDebuggerServerSettings) {
				debugClientPort = debuggerSettings.getAttribute(ZendDebuggerSettingsConstants.PROP_RESPONSE_TIMEOUT);
			}
			try {
				responseTimeout = Integer.valueOf(debugClientPort);
			} catch (Exception e) {
				// ignore
			}
		}
		return responseTimeout;
	}

	public static int getDebugPort(String phpServerOrExeId) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(phpServerOrExeId,
				ZendDebuggerConfiguration.ID);
		String debugClientPort = null;
		debugClientPort = debuggerSettings.getAttribute(ZendDebuggerSettingsConstants.PROP_CLIENT_PORT);
		int debugPort = -1;
		try {
			debugPort = Integer.valueOf(debugClientPort);
		} catch (Exception e) {
			// ignore
		}
		return debugPort;
	}

}
