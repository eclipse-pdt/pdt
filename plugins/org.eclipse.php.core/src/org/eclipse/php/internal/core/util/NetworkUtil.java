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
package org.eclipse.php.internal.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Simple utility class for obtaining workstation network addresses.
 * 
 * @author Bartlomiej Laczkowski
 */
public class NetworkUtil {

	private static Inet4Address CLASS_A_NETWORK;
	private static Inet4Address CLASS_B_NETWORK;
	private static Inet4Address CLASS_C_NETWORK;
	private static Inet4Address LOCALHOST;

	static {
		try {
			CLASS_A_NETWORK = (Inet4Address) InetAddress.getByName("10.0.0.0"); //$NON-NLS-1$
			CLASS_B_NETWORK = (Inet4Address) InetAddress
					.getByName("172.16.0.0"); //$NON-NLS-1$
			CLASS_C_NETWORK = (Inet4Address) InetAddress
					.getByName("192.168.0.0"); //$NON-NLS-1$
			LOCALHOST = (Inet4Address) InetAddress.getByName("127.0.0.1"); //$NON-NLS-1$
		} catch (UnknownHostException e) {
			// cannot occur in this particular case because all IPs are valid
		}
	}

	private static final String[] WHAT_IS_MY_IP_CALLBACKS = new String[] {
			"http://checkip.amazonaws.com", //$NON-NLS-1$
			"http://icanhazip.com", //$NON-NLS-1$
			"http://www.trackip.net/ip" //$NON-NLS-1$
	};

	private static final int WHAT_IS_MY_IP_CONNECTION_TIMEOUT = 1000;
	private static final int WHAT_IS_MY_IP_READ_TIMEOUT = 5000;

	/**
	 * Simple network address descriptor.
	 * 
	 * @author Bartlomiej Laczkowski
	 */
	public static class NetworkAddress {

		public static final int TYPE_PUBLIC = 0x01;
		public static final int TYPE_PRIVATE = 0x02;
		public static final int TYPE_LOOPBACK = 0x04;

		private Inet4Address address;
		private int type;

		/**
		 * Creates new simple network descriptor.
		 * 
		 * @param address
		 */
		public NetworkAddress(Inet4Address address) {
			super();
			this.address = address;
			setType();
		}

		/**
		 * Returns IP address for this descriptor.
		 * 
		 * @return IP address
		 */
		public String getIP() {
			return address.getHostAddress();
		}

		/**
		 * Return type of network address descriptor.
		 * 
		 * @return type of network address descriptor
		 */
		public int getType() {
			return type;
		}

		private void setType() {
			if (address.isLoopbackAddress()) {
				type = TYPE_LOOPBACK;
			} else if (isPrivateClassA(address) || isPrivateClassB(address)
					|| isPrivateClassC(address)) {
				type = TYPE_PRIVATE;
			} else {
				type = TYPE_PUBLIC;
			}
		}

		/**
		 * Checks if specified address is in the range of class A private
		 * network (RFC 1918) with 10.0.0.0/8 mask.
		 * 
		 * @param address
		 * @return <code>true</code> if it is in the range; otherwise return
		 *         <code>false</code>
		 */
		private boolean isPrivateClassA(Inet4Address address) {
			return isInRange(address, CLASS_A_NETWORK, 8);
		}

		/**
		 * Checks if specified address is in the range of class B private
		 * network (RFC 1918) with 172.16.0.0/12 mask.
		 * 
		 * @param address
		 * @return <code>true</code> if it is in the range; otherwise return
		 *         <code>false</code>
		 */
		private boolean isPrivateClassB(Inet4Address address) {
			return isInRange(address, CLASS_B_NETWORK, 12);
		}

		/**
		 * Checks if specified address is in the range of class C private
		 * network (RFC 1918) with 192.168.0.0/16 mask.
		 * 
		 * @param address
		 * @return <code>true</code> if it is in the range; otherwise return
		 *         <code>false</code>
		 */
		private boolean isPrivateClassC(Inet4Address address) {
			return isInRange(address, CLASS_C_NETWORK, 16);
		}

		private boolean isInRange(Inet4Address address, Inet4Address subnet,
				int mask) {
			int maskValue = 0xFFFFFFF << (32 - mask);
			int subnetValue = getAddress(subnet.getAddress());
			int addressValue = getAddress(address.getAddress());
			return (subnetValue & subnetValue) == (addressValue & maskValue);
		}

		private int getAddress(byte[] bytesAddress) {
			return ((((int) bytesAddress[0]) & 0xFF) << 24)
					| ((((int) bytesAddress[1]) & 0xFF) << 16)
					| ((((int) bytesAddress[2]) & 0xFF) << 8)
					| ((((int) bytesAddress[3]) & 0xFF) << 0);
		}
	}

	private NetworkUtil() {
		// Private constructor - utility class
	}

	/**
	 * Returns all of the workstation network addresses
	 * 
	 * @return all available network addresses
	 */
	public static List<NetworkAddress> getAllAddresses() {
		final List<NetworkAddress> descriptors = new ArrayList<NetworkAddress>();
		NetworkAddress publicAddress = getPublicAddress();
		if (publicAddress != null)
			descriptors.add(getPublicAddress());
		descriptors.addAll(getPrivateAddresses());
		descriptors.add(new NetworkAddress(LOCALHOST));
		return descriptors;
	}

	/**
	 * Tries to obtain workstation public IP address.
	 * 
	 * @return workstation public IP address or <code>null</code> if can not be
	 *         obtained
	 */
	public static NetworkAddress getPublicAddress() {
		for (String callback : WHAT_IS_MY_IP_CALLBACKS) {
			BufferedReader bufferedReader = null;
			try {
				URL whatismyip = new URL(callback);
				URLConnection connection = whatismyip.openConnection();
				connection.setConnectTimeout(WHAT_IS_MY_IP_CONNECTION_TIMEOUT);
				connection.setReadTimeout(WHAT_IS_MY_IP_READ_TIMEOUT);
				bufferedReader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String publicIP = bufferedReader.readLine();
				Inet4Address publicAddress = (Inet4Address) Inet4Address
						.getByName(publicIP);
				return new NetworkAddress(publicAddress);
			} catch (Exception e) {
				// ignore
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// ignore
					}
				}
			}
		}
		return null;
	}

	/**
	 * Tries to obtain LAN private addresses.
	 * 
	 * @return LAN private addresses
	 */
	public static List<NetworkAddress> getPrivateAddresses() {
		List<NetworkAddress> descriptors = new ArrayList<NetworkAddress>();
		try {
			// Add localhost first
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces
						.nextElement();
				// Skip VMware's
				if (networkInterface.getDisplayName().contains("VMware")) { //$NON-NLS-1$
					continue;
				}
				Enumeration<InetAddress> inetAddresses = networkInterface
						.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					if (inetAddress instanceof Inet4Address
							&& !inetAddress.isLoopbackAddress()) {
						descriptors.add(new NetworkAddress(
								(Inet4Address) inetAddress));
					}
				}
			}
		} catch (Exception e) {
			// in this case continue with already detected hosts
		}
		return descriptors;
	}

}
