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
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
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

	/**
	 * 
	 */
	private ZendDebuggerSettingsUtil() {
		// Private constructor - utility class
	}

	public static String getDebugHosts(Server phpServer) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
				.findSettings(phpServer, ZendDebuggerConfiguration.ID);
		String debugHosts = ""; //$NON-NLS-1$
		if (debuggerSettings instanceof ZendDebuggerServerSettings) {
			debugHosts = debuggerSettings
					.getAttribute(ZendDebuggerSettingsConstants.PROP_CLIENT_IP);
		}
		return debugHosts;
	}

	public static int getResponseTimeout(
			DebugSessionStartedNotification startedNotification) {
		/*
		 * Check if we have related server based on original URL host name, if
		 * yes then set timeout from its debugger settings
		 */
		String debugQuery = startedNotification.getQuery();
		String originalURLTrigger = "&original_url="; //$NON-NLS-1$
		int originalURLStart = debugQuery.indexOf(originalURLTrigger);
		String originalURL = debugQuery.substring(originalURLStart
				+ originalURLTrigger.length());
		int responseTimeout = -1;
		try {
			URL url = new URL(originalURL);
			String host = url.getHost();
			Server server = ServersManager.getServerByHost(host);
			if (server != null) {
				IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
						.findSettings(server, ZendDebuggerConfiguration.ID);
				String debugClientPort = null;
				if (debuggerSettings instanceof ZendDebuggerServerSettings) {
					debugClientPort = debuggerSettings
							.getAttribute(ZendDebuggerSettingsConstants.PROP_RESPONSE_TIMEOUT);
				}
				try {
					responseTimeout = Integer.valueOf(debugClientPort);
				} catch (Exception e) {
					// ignore
				}
			}
		} catch (MalformedURLException e) {
			// ignore
		}
		return responseTimeout;
	}

	public static int getDebugPort(Server phpServer) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
				.findSettings(phpServer, ZendDebuggerConfiguration.ID);
		String debugClientPort = null;
		if (debuggerSettings instanceof ZendDebuggerServerSettings) {
			debugClientPort = debuggerSettings
					.getAttribute(ZendDebuggerSettingsConstants.PROP_CLIENT_PORT);
		}
		int debugPort = -1;
		try {
			debugPort = Integer.valueOf(debugClientPort);
		} catch (Exception e) {
			// ignore
		}
		return debugPort;
	}

	public static int getDebugPort(PHPexeItem phpExe) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
				.findSettings(phpExe, ZendDebuggerConfiguration.ID);
		String debugClientPort = null;
		if (debuggerSettings instanceof ZendDebuggerExeSettings) {
			debugClientPort = debuggerSettings
					.getAttribute(ZendDebuggerSettingsConstants.PROP_CLIENT_PORT);
		}
		int debugPort = -1;
		try {
			debugPort = Integer.valueOf(debugClientPort);
		} catch (Exception e) {
			// ignore
		}
		return debugPort;
	}

}
