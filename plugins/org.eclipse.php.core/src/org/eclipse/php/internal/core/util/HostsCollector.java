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
package org.eclipse.php.internal.core.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class HostsCollector {

	private HostsCollector() {
	}

	public static String getHosts() {
		String result = ""; //$NON-NLS-1$
		try {
			ArrayList localHosts = new ArrayList();
			ArrayList allIPs = new ArrayList();

			Enumeration networkInterfacesEnumeration = NetworkInterface.getNetworkInterfaces();
			while (networkInterfacesEnumeration.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) networkInterfacesEnumeration.nextElement();
				String name = networkInterface.getName();

				Enumeration InetAddressesEnumeration = networkInterface.getInetAddresses();
				while (InetAddressesEnumeration.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) InetAddressesEnumeration.nextElement();

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
				for (int i = 0; i < allIPs.size(); i++) {
					if (i > 0) {
						result += "%2C"; //$NON-NLS-1$
					}
					InetAddress inetAddress = (InetAddress) allIPs.get(i);
					String s = inetAddress.toString();
					if (s.startsWith("/")) { //$NON-NLS-1$
						s = s.substring(1);
					}
					result += s;
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //$NON-NLS-1$

	}

}