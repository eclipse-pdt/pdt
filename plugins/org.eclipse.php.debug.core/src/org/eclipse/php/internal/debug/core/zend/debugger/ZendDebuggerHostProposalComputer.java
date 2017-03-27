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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.php.internal.core.util.NetworkUtil;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.server.core.types.IServerType;

/**
 * This class is responsible for computing the list of "best match" client IPs
 * with the use of data that can be established for particular PHP server.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerHostProposalComputer {

	private Server server;
	private String serverTypeId;
	private boolean computeAddress;
	private Inet4Address serverHostAddress;

	/**
	 * Computes and returns client IPs list proposal for provided server.
	 * 
	 * @param server
	 * @return client IPs list proposal for provided server
	 */
	public String computeProposals(Server server) {
		this.computeAddress = true;
		this.server = server;
		this.serverTypeId = server.getAttribute(IServerType.TYPE, null);
		String proposals = null;
		proposals = computeByServerType();
		if (proposals != null)
			return proposals;
		return computeByServerAddress();
	}

	private Inet4Address fetchHostAddress() {
		if (computeAddress) {
			computeAddress = false;
			URL address;
			try {
				address = new URL(server.getBaseURL());
			} catch (MalformedURLException e) {
				return null;
			}
			String serverHost = address.getHost();
			InetAddress hostAddress;
			hostAddress = NetworkUtil.getByName(serverHost, 2000);
			if (!(hostAddress instanceof Inet4Address)) {
				return null;
			}
			serverHostAddress = (Inet4Address) hostAddress;
		}
		return serverHostAddress;
	}

	private String computeByServerType() {
		if (serverTypeId == null)
			return null;
		final Inet4Address serverHostAddress = fetchHostAddress();
		if (serverHostAddress == null)
			return null;
		// Workaround for Docker specific case
		else if (NetworkUtil.TYPE_LOOPBACK == NetworkUtil.getType(serverHostAddress)) {
			return getPrivateAddressProposals(serverHostAddress);
		}
		return null;
	}

	private String computeByServerAddress() {
		final Inet4Address serverHostAddress = fetchHostAddress();
		if (serverHostAddress == null)
			return null;
		switch (NetworkUtil.getType(serverHostAddress)) {
		case NetworkUtil.TYPE_PUBLIC: {
			return getPublicAddressProposal();
		}
		case NetworkUtil.TYPE_LOOPBACK: {
			return getLocalAddressProposal();
		}
		case NetworkUtil.TYPE_PRIVATE: {
			return getPrivateAddressProposals(serverHostAddress);
		}
		default:
			return null;
		}
	}

	private String getLocalAddressProposal() {
		return NetworkUtil.LOCALHOST.getHostAddress();
	}

	private String getPrivateAddressProposals(final Inet4Address serverHostAddress) {
		List<Inet4Address> privateAddresses = NetworkUtil.getPrivateAddresses();
		if (privateAddresses.isEmpty())
			return null;
		Collections.sort(privateAddresses, new Comparator<Inet4Address>() {
			public int compare(Inet4Address a1, Inet4Address a2) {
				if (NetworkUtil.isSamePrivateClass(a1, serverHostAddress)
						&& !NetworkUtil.isSamePrivateClass(a2, serverHostAddress))
					return 1;
				if (NetworkUtil.isSamePrivateClass(a2, serverHostAddress)
						&& !NetworkUtil.isSamePrivateClass(a1, serverHostAddress))
					return -1;
				return 0;
			}
		});
		String[] hosts = new String[privateAddresses.size()];
		for (int i = 0; i < privateAddresses.size(); i++) {
			hosts[i] = privateAddresses.get(i).getHostAddress();
		}
		return PHPDebugUtil.getZendHostsString(hosts);
	}

	private String getPublicAddressProposal() {
		Inet4Address publicAddress = NetworkUtil.getPublicAddress();
		if (publicAddress != null)
			return publicAddress.getHostAddress();
		return null;
	}

}
