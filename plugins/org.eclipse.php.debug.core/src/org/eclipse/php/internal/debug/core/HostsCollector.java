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
package org.eclipse.php.internal.debug.core;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;

public class HostsCollector {

	private HostsCollector() {
	}

	public static String getHosts() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		String clientIP = prefs.getString(PHPDebugCorePreferenceNames.CLIENT_IP);
		if (clientIP.length() != 0) {
			return clientIP;
		}

		try {
			ArrayList<InetAddress> localHosts = new ArrayList<InetAddress>();
			ArrayList<InetAddress> allIPs = new ArrayList<InetAddress>();

			Enumeration<NetworkInterface> networkInterfacesEnumeration = NetworkInterface.getNetworkInterfaces();
			while (networkInterfacesEnumeration.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfacesEnumeration.nextElement();
				String name = networkInterface.getName();

				Enumeration<InetAddress> InetAddressesEnumeration = networkInterface.getInetAddresses();
				while (InetAddressesEnumeration.hasMoreElements()) {
					InetAddress inetAddress = InetAddressesEnumeration.nextElement();

					if (inetAddress instanceof Inet6Address) {
						continue;
					}

					if (name.equalsIgnoreCase("lo") || name.equalsIgnoreCase("localhost")) { //$NON-NLS-1$ //$NON-NLS-2$
						localHosts.add(inetAddress);
					} else {
						allIPs.add(inetAddress);
					}
				}
			}
			allIPs.addAll(localHosts);

			if (allIPs.size() > 0) {
				StringBuilder buf = new StringBuilder();
				for (int i = 0; i < allIPs.size(); i++) {
					if (i > 0) {
						buf.append("%2C"); //$NON-NLS-1$
					}
					InetAddress inetAddress = allIPs.get(i);
					String s = inetAddress.toString();
					if (s.startsWith("/")) { //$NON-NLS-1$
						s = s.substring(1);
					}
					buf.append(s);
				}
				return buf.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //$NON-NLS-1$
	}
}