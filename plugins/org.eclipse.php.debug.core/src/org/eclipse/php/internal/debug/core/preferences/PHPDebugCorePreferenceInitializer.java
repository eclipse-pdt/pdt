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
package org.eclipse.php.internal.debug.core.preferences;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;

/**
 * Sets default values for PHP Debug preferences
 */
public class PHPDebugCorePreferenceInitializer extends
		AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(PHPDebugPlugin
				.getDefault().getBundle().getSymbolicName());

		// formatting preferences
		node.putBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, true);
		node.putBoolean(PHPDebugCorePreferenceNames.ENABLE_CLI_DEBUG, false);
		node.putBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, true);
		node.putBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, true);
		node.putBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, true);
		node.putBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, true);
		node.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 10000);
		node.putInt(PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT, 50000); // 50
																				// seconds
		node.put(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, "UTF-8"); //$NON-NLS-1$
		node.put(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, "UTF-8"); //$NON-NLS-1$
		node.put(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
				PHPExecutableLaunchDelegate.class.getName());
		node.put(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
				DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID); // The default is
																// Zend's
																// debugger
		node.put(IPHPDebugConstants.PHP_DEBUG_PARAMETERS_INITIALIZER,
				"org.eclipse.php.debug.core.defaultInitializer"); //$NON-NLS-1$

		node.putBoolean(PHPDebugCorePreferenceNames.SORT_BY_NAME, false);

		try {
			StringBuilder b = new StringBuilder();
			Enumeration<NetworkInterface> ii = NetworkInterface
					.getNetworkInterfaces();
			while (ii.hasMoreElements()) {
				NetworkInterface i = ii.nextElement();
				if (i.getDisplayName().contains("VMware")) { //$NON-NLS-1$
					continue;
				}
				Enumeration<InetAddress> aa = i.getInetAddresses();
				while (aa.hasMoreElements()) {
					InetAddress a = aa.nextElement();
					if (a instanceof Inet4Address && !a.isLoopbackAddress()) {
						b.append(a.getHostAddress()).append(","); //$NON-NLS-1$
					}
				}
			}
			b.append("127.0.0.1"); //$NON-NLS-1$
			node.put(PHPDebugCorePreferenceNames.CLIENT_IP, b.toString());
		} catch (Exception e) {
		}
	}
}
