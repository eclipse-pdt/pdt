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
import java.net.UnknownHostException;
import java.util.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;

/**
 * Sets default values for PHP Debug preferences
 */
public class PHPDebugCorePreferenceInitializer extends AbstractPreferenceInitializer {

	private static Inet4Address CLASS_A_NETWORK;
	private static Inet4Address CLASS_B_NETWORK;
	private static Inet4Address CLASS_C_NETWORK;
	private static Inet4Address LOCALHOST;

	static {
		try {
			CLASS_A_NETWORK = (Inet4Address) InetAddress.getByName("10.0.0.0"); //$NON-NLS-1$
			CLASS_B_NETWORK = (Inet4Address) InetAddress.getByName("172.16.0.0"); //$NON-NLS-1$
			CLASS_C_NETWORK = (Inet4Address) InetAddress.getByName("192.168.0.0"); //$NON-NLS-1$
			LOCALHOST = (Inet4Address) InetAddress.getByName("127.0.0.1"); //$NON-NLS-1$
		} catch (UnknownHostException e) {
			// cannot occur in this particular case because all IPs are valid
		}
	}

	public void initializeDefaultPreferences() {
		IEclipsePreferences node = PHPDebugPlugin.getDefaultPreferences();

		// formatting preferences
		node.putBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, true);
		node.putBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, true);
		node.putBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, true);
		node.putBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, true);
		node.putBoolean(PHPDebugCorePreferenceNames.ZEND_NEW_PROTOCOL, true);
		node.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_PORT, 10137);
		node.putInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT, 20080);
		node.put(PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "dummy.php"); //$NON-NLS-1$
		node.putInt(PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT, 60000); // 60
																				// seconds
		node.put(PHPDebugCorePreferenceNames.TRANSFER_ENCODING, "UTF-8"); //$NON-NLS-1$
		node.put(PHPDebugCorePreferenceNames.OUTPUT_ENCODING, "UTF-8"); //$NON-NLS-1$
		node.put(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, PHPExecutableLaunchDelegate.class.getName());
		node.put(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID); // The
																												// default
																												// is
																												// Zend's
																												// debugger
		node.put(IPHPDebugConstants.PHP_DEBUG_PARAMETERS_INITIALIZER, "org.eclipse.php.debug.core.defaultInitializer"); //$NON-NLS-1$

		node.putBoolean(PHPDebugCorePreferenceNames.SORT_BY_NAME, false);

		node.putBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false);
		node.put(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_ALL_PAGES);

		List<Inet4Address> clientHosts = new ArrayList<Inet4Address>();
		clientHosts.add(LOCALHOST);
		try {
			Enumeration<NetworkInterface> ii = NetworkInterface.getNetworkInterfaces();
			while (ii.hasMoreElements()) {
				NetworkInterface i = ii.nextElement();
				if (i.getDisplayName().contains("VMware")) { //$NON-NLS-1$
					continue;
				}
				Enumeration<InetAddress> aa = i.getInetAddresses();
				while (aa.hasMoreElements()) {
					InetAddress a = aa.nextElement();
					if (a instanceof Inet4Address && !a.isLoopbackAddress()) {
						clientHosts.add((Inet4Address) a);
					}
				}
			}
		} catch (Exception e) {
			// in this case continue with already detected hosts
		}
		Inet4Address[] hosts = clientHosts.toArray(new Inet4Address[clientHosts.size()]);
		Arrays.sort(hosts, new Comparator<Inet4Address>() {

			@Override
			public int compare(Inet4Address first, Inet4Address second) {
				return getNetworkClass(second) - getNetworkClass(first);
			}
		});
		StringBuilder clientIPs = new StringBuilder();
		for (Inet4Address host : hosts) {
			if (clientIPs.length() > 0) {
				clientIPs.append(',');
			}
			clientIPs.append(host.getHostAddress());
		}
		node.put(PHPDebugCorePreferenceNames.CLIENT_IP, clientIPs.toString());
	}

	/**
	 * Checks a network class of specified address. It returns integer from 0 to
	 * 4.
	 * 
	 * @param address
	 * @return integer associated with particular network class with following
	 *         rules:
	 *         <ul>
	 *         <li>0 - loopback address</li>
	 *         <li>1 - private class A network</li>
	 *         <li>2 - private class B network</li>
	 *         <li>3 - private class C network</li>
	 *         <li>4 - other networks</li>
	 *         </ul>
	 */
	private int getNetworkClass(Inet4Address address) {
		if (address.isLoopbackAddress()) {
			return 0;
		}
		if (isPrivateClassA(address)) {
			return 1;
		}
		if (isPrivateClassB(address)) {
			return 2;
		}
		if (isPrivateClassC(address)) {
			return 3;
		}
		return 4;
	}

	/**
	 * Checks if specified address is in the range of class A private network
	 * (RFC 1918) with 10.0.0.0/8 mask.
	 * 
	 * @param address
	 * @return <code>true</code> if it is in the range; otherwise return
	 *         <code>false</code>
	 */
	private boolean isPrivateClassA(Inet4Address address) {
		return isInRange(address, CLASS_A_NETWORK, 8);
	}

	/**
	 * Checks if specified address is in the range of class B private network
	 * (RFC 1918) with 172.16.0.0/12 mask.
	 * 
	 * @param address
	 * @return <code>true</code> if it is in the range; otherwise return
	 *         <code>false</code>
	 */
	private boolean isPrivateClassB(Inet4Address address) {
		return isInRange(address, CLASS_B_NETWORK, 12);
	}

	/**
	 * Checks if specified address is in the range of class C private network
	 * (RFC 1918) with 192.168.0.0/16 mask.
	 * 
	 * @param address
	 * @return <code>true</code> if it is in the range; otherwise return
	 *         <code>false</code>
	 */
	private boolean isPrivateClassC(Inet4Address address) {
		return isInRange(address, CLASS_C_NETWORK, 16);
	}

	private boolean isInRange(Inet4Address address, Inet4Address subnet, int mask) {
		int maskValue = 0xFFFFFFF << (32 - mask);
		int subnetValue = getAddress(subnet.getAddress());
		int addressValue = getAddress(address.getAddress());
		return (subnetValue & subnetValue) == (addressValue & maskValue);
	}

	private int getAddress(byte[] bytesAddress) {
		return ((((int) bytesAddress[0]) & 0xFF) << 24) | ((((int) bytesAddress[1]) & 0xFF) << 16)
				| ((((int) bytesAddress[2]) & 0xFF) << 8) | ((((int) bytesAddress[3]) & 0xFF) << 0);
	}

}
