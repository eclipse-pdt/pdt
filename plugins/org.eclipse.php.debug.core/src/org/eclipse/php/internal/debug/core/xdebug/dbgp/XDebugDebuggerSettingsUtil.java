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

/**
 * XDebug debugger owner settings utility class.
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugDebuggerSettingsUtil {

	private XDebugDebuggerSettingsUtil() {
		// Private constructor - utility class
	}

	public static boolean getProxyEnabled(String phpServerOrExeId) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(phpServerOrExeId,
				XDebugDebuggerConfiguration.ID);
		String proxyEnabled = null;
		proxyEnabled = debuggerSettings.getAttribute(XDebugDebuggerSettingsConstants.PROP_PROXY_ENABLE);
		boolean debugProxyEnabled = false;
		try {
			debugProxyEnabled = Boolean.valueOf(proxyEnabled);
		} catch (Exception e) {
			// ignore
		}
		return debugProxyEnabled;
	}

	public static String getProxyIdeKey(String phpServerOrExeId) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(phpServerOrExeId,
				XDebugDebuggerConfiguration.ID);
		String proxyIdeKey = ""; //$NON-NLS-1$
		proxyIdeKey = debuggerSettings.getAttribute(XDebugDebuggerSettingsConstants.PROP_PROXY_IDE_KEY);
		return proxyIdeKey;
	}

	public static String getProxyAddress(String phpServerOrExeId) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(phpServerOrExeId,
				XDebugDebuggerConfiguration.ID);
		String proxyAddress = ""; //$NON-NLS-1$
		proxyAddress = debuggerSettings.getAttribute(XDebugDebuggerSettingsConstants.PROP_PROXY_ADDRESS);
		return proxyAddress;
	}

	public static int getDebugPort(String phpServerOrExeId) {
		IDebuggerSettings debuggerSettings = DebuggerSettingsManager.INSTANCE.findSettings(phpServerOrExeId,
				XDebugDebuggerConfiguration.ID);
		String debugClientPort = null;
		debugClientPort = debuggerSettings.getAttribute(XDebugDebuggerSettingsConstants.PROP_CLIENT_PORT);
		int debugPort = -1;
		try {
			debugPort = Integer.valueOf(debugClientPort);
		} catch (Exception e) {
			// ignore
		}
		return debugPort;
	}

}
