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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsManager;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.server.core.Server;

/**
 * XDebug debugger owner settings utility class.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class XDebugDebuggerSettingsUtil {

	private XDebugDebuggerSettingsUtil() {
		// Private constructor - utility class
	}

	// TODO - will be supported soon

	// public static boolean getProxyEnabled(Server phpServer) {
	// IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
	// .findSettings(phpServer, XDebugDebuggerConfiguration.ID);
	// String proxyEnabled = null;
	// if (debuggerSettings instanceof XDebugDebuggerServerSettings) {
	// proxyEnabled = debuggerSettings
	// .getAttribute(XDebugDebuggerSettingsConstants.PROP_PROXY_ENABLE);
	// }
	// boolean debugProxyEnabled = false;
	// try {
	// debugProxyEnabled = Boolean.valueOf(proxyEnabled);
	// } catch (Exception e) {
	// // ignore
	// }
	// return debugProxyEnabled;
	// }
	//
	// public static String getProxyIdeKey(Server phpServer) {
	// IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
	// .findSettings(phpServer, XDebugDebuggerConfiguration.ID);
	//		String proxyIdeKey = ""; //$NON-NLS-1$
	// if (debuggerSettings instanceof XDebugDebuggerServerSettings) {
	// proxyIdeKey = debuggerSettings
	// .getAttribute(XDebugDebuggerSettingsConstants.PROP_PROXY_IDE_KEY);
	// }
	// return proxyIdeKey;
	// }
	//
	// public static String getProxyAddress(Server phpServer) {
	// IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
	// .findSettings(phpServer, XDebugDebuggerConfiguration.ID);
	//		String proxyAddress = ""; //$NON-NLS-1$
	// if (debuggerSettings instanceof XDebugDebuggerServerSettings) {
	// proxyAddress = debuggerSettings
	// .getAttribute(XDebugDebuggerSettingsConstants.PROP_PROXY_ADDRESS);
	// }
	// return proxyAddress;
	// }

	public static int getDebugPort(Server phpServer) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE
				.findSettings(phpServer, XDebugDebuggerConfiguration.ID);
		String debugClientPort = null;
		if (debuggerSettings instanceof XDebugDebuggerServerSettings) {
			debugClientPort = debuggerSettings
					.getAttribute(XDebugDebuggerSettingsConstants.PROP_CLIENT_PORT);
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
				.findSettings(phpExe, XDebugDebuggerConfiguration.ID);
		String debugClientPort = null;
		if (debuggerSettings instanceof XDebugDebuggerExeSettings) {
			debugClientPort = debuggerSettings
					.getAttribute(XDebugDebuggerSettingsConstants.PROP_CLIENT_PORT);
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
