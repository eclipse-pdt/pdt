/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Simple utility class for obtaining & managing workstation network addresses.
 * 
 * @author Bartlomiej Laczkowski
 */
public class NetworkUtil {

	private static class DNSJob extends Job {

		private String host;
		private Inet4Address address;

		public DNSJob(String host) {
			super(""); //$NON-NLS-1$
			this.host = host;
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				setAddress((Inet4Address) Inet4Address.getByName(host));
			} catch (UnknownHostException e) {
				// ignore
			}
			return Status.OK_STATUS;
		}

		synchronized Inet4Address getAddress() {
			return address;
		}

		synchronized void setAddress(Inet4Address address) {
			this.address = address;
		}

	}

	private static Inet4Address CLASS_A_NETWORK;
	private static Inet4Address CLASS_B_NETWORK;
	private static Inet4Address CLASS_C_NETWORK;
	private static Inet4Address LOOPBACK_NETWORK;
	public static Inet4Address LOCALHOST;

	static {
		try {
			CLASS_A_NETWORK = (Inet4Address) InetAddress.getByName("10.0.0.0"); //$NON-NLS-1$
			CLASS_B_NETWORK = (Inet4Address) InetAddress.getByName("172.16.0.0"); //$NON-NLS-1$
			CLASS_C_NETWORK = (Inet4Address) InetAddress.getByName("192.168.0.0"); //$NON-NLS-1$
			LOOPBACK_NETWORK = (Inet4Address) InetAddress.getByName("127.0.0.0"); //$NON-NLS-1$
			LOCALHOST = (Inet4Address) InetAddress.getByName("127.0.0.1"); //$NON-NLS-1$
		} catch (UnknownHostException e) {
			// cannot occur in this particular case because all IPs are valid
		}
	}

	private static final String[] WHAT_IS_MY_IP_CALLBACKS = new String[] { "http://checkip.amazonaws.com", //$NON-NLS-1$
			"http://icanhazip.com", //$NON-NLS-1$
			"http://www.trackip.net/ip" //$NON-NLS-1$
	};

	private static final int WHAT_IS_MY_IP_CONNECTION_TIMEOUT = 1000;
	private static final int WHAT_IS_MY_IP_READ_TIMEOUT = 5000;

	public static final int TYPE_PUBLIC = 0x01;
	public static final int TYPE_PRIVATE = 0x02;
	public static final int TYPE_LOOPBACK = 0x04;

	private static final Pattern IPV4_PATTERN = Pattern
			.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

	private NetworkUtil() {
		// Private constructor - utility class
	}

	/**
	 * Returns all of the workstation network addresses
	 * 
	 * @return all available network addresses
	 */
	public static List<Inet4Address> getAllAddresses() {
		final List<Inet4Address> descriptors = new ArrayList<Inet4Address>();
		Inet4Address publicAddress = getPublicAddress();
		if (publicAddress != null)
			descriptors.add(getPublicAddress());
		descriptors.addAll(getPrivateAddresses());
		descriptors.add(LOCALHOST);
		return descriptors;
	}

	/**
	 * Tries to obtain workstation public IP address.
	 * 
	 * @return workstation public IP address or <code>null</code> if can not be
	 *         obtained
	 */
	public static Inet4Address getPublicAddress() {
		for (String callback : WHAT_IS_MY_IP_CALLBACKS) {
			BufferedReader bufferedReader = null;
			try {
				URL whatismyip = new URL(callback);
				URLConnection connection = whatismyip.openConnection();
				connection.setConnectTimeout(WHAT_IS_MY_IP_CONNECTION_TIMEOUT);
				connection.setReadTimeout(WHAT_IS_MY_IP_READ_TIMEOUT);
				bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String publicIP = bufferedReader.readLine();
				return getByName(publicIP, 2000);
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
	public static List<Inet4Address> getPrivateAddresses() {
		List<Inet4Address> descriptors = new ArrayList<Inet4Address>();
		try {
			// Add localhost first
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				// Skip VMware's
				if (networkInterface.getDisplayName().contains("VMware")) { //$NON-NLS-1$
					continue;
				}
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
						descriptors.add((Inet4Address) inetAddress);
					}
				}
			}
		} catch (Exception e) {
			// in this case continue with already detected hosts
		}
		return descriptors;
	}

	/**
	 * Returns type of address (e.g. local, private, public).
	 * 
	 * @param address
	 * @return type of address
	 */
	public static int getType(Inet4Address address) {
		if (address.isLoopbackAddress()) {
			return TYPE_LOOPBACK;
		} else if (isPrivateClassA(address) || isPrivateClassB(address) || isPrivateClassC(address)) {
			return TYPE_PRIVATE;
		} else {
			return TYPE_PUBLIC;
		}
	}

	/**
	 * Does the same what {@link InetAddress#getByName(String)} but with
	 * possibility to provide custom timeout.
	 * 
	 * @param host
	 * @param timeout
	 * @return IP address of a host
	 */
	public static Inet4Address getByName(String host, int timeout) {
		DNSJob resolver = new DNSJob(host);
		resolver.schedule();
		try {
			resolver.join(timeout, new NullProgressMonitor());
		} catch (OperationCanceledException e) {
			// ignore
		} catch (InterruptedException e) {
			// ignore
		}
		return resolver.getAddress();
	}

	/**
	 * Checks if specified address is in the range of class A private network
	 * (RFC 1918) with 10.0.0.0/8 mask.
	 * 
	 * @param address
	 * @return <code>true</code> if it is in the range; otherwise return
	 *         <code>false</code>
	 */
	public static boolean isPrivateClassA(Inet4Address address) {
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
	public static boolean isPrivateClassB(Inet4Address address) {
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
	public static boolean isPrivateClassC(Inet4Address address) {
		return isInRange(address, CLASS_C_NETWORK, 16);
	}

	/**
	 * Checks if specified address is in the range of class A private network
	 * (RFC 1918) with 127.0.0.0/8 mask.
	 * 
	 * @param address
	 * @return <code>true</code> if it is in the range; otherwise return
	 *         <code>false</code>
	 */
	public static boolean isLoopbackAddress(Inet4Address address) {
		return isInRange(address, LOOPBACK_NETWORK, 8);
	}

	/**
	 * Checks if both of provided addresses belong to the same private network
	 * class range.
	 * 
	 * @param address1
	 * @param address2
	 * @return <code>true</code> if both addresses belong to the same class
	 *         range, <code>false</code> otherwise
	 */
	public static boolean isSamePrivateClass(Inet4Address address1, Inet4Address address2) {
		if ((isPrivateClassA(address1) && isPrivateClassA(address2))
				|| (isPrivateClassB(address1) && isPrivateClassB(address2))
				|| (isPrivateClassC(address1) && isPrivateClassC(address2)))
			return true;
		return false;
	}

	/**
	 * Checks if provided addresses are in the same sub-network with the use of
	 * given mask.
	 * 
	 * @param address1
	 * @param address2
	 * @param mask
	 * @return <code>true</code> if both addresses belong to the same
	 *         sub-network, <code>false</code> otherwise
	 */
	public static boolean isSameNetwork(Inet4Address address1, Inet4Address address2, int mask) {
		int maskValue = 0xFFFFFFF << (32 - mask);
		int address1Value = getAddress(address1.getAddress());
		int address2Value = getAddress(address2.getAddress());
		return (address1Value & maskValue) == (address2Value & maskValue);
	}

	/**
	 * Checks if provided address is IPv4 address.
	 * 
	 * @param input
	 * @return <code>true</code> if address is IPv4, <code>false</code>
	 *         otherwise
	 */
	public static boolean isIPv4Address(final String input) {
		return IPV4_PATTERN.matcher(input).matches();
	}

	private static boolean isInRange(Inet4Address address, Inet4Address subnet, int mask) {
		int maskValue = 0xFFFFFFF << (32 - mask);
		int subnetValue = getAddress(subnet.getAddress());
		int addressValue = getAddress(address.getAddress());
		return (subnetValue & subnetValue) == (addressValue & maskValue);
	}

	private static int getAddress(byte[] bytesAddress) {
		return ((((int) bytesAddress[0]) & 0xFF) << 24) | ((((int) bytesAddress[1]) & 0xFF) << 16)
				| ((((int) bytesAddress[2]) & 0xFF) << 8) | ((((int) bytesAddress[3]) & 0xFF) << 0);
	}

}
