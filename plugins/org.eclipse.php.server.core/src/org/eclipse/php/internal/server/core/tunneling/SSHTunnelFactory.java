/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Aptana Inc.
 *******************************************************************************/
package org.eclipse.php.internal.server.core.tunneling;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A SSH tunnels (port forwarding) factory class, which also caches the
 * generated factories and act as SSHTunnels manager.
 * 
 * @author Shalom Gibly
 */
public class SSHTunnelFactory {
	private static HashMap<SSHTunnel, SSHTunnel> tunnels = new HashMap<SSHTunnel, SSHTunnel>();

	/**
	 * Returns a {@link SSHTunnel} for port-forwarding between a remote host to
	 * this localhost. The returned tunnel may be connected or disconnected.
	 * 
	 * @param remoteHost
	 * @param userName
	 * @param password
	 * @param localPort
	 * @param remotePort
	 * @param cacheTunnel
	 *            Load/save the result tunnel from/to an inner cache for further
	 *            use (note that the tunnel is not comparing passwords, so make
	 *            sure that password is not changing when you are using the
	 *            cache)
	 * @return A {@link SSHTunnel} instance.
	 */
	public static SSHTunnel getSSHTunnel(String remoteHost, String userName,
			String password, int localPort, int remotePort, boolean cacheTunnel) {
		SSHTunnel tunnel = createSSHTunnel(remoteHost, userName, password,
				localPort, remotePort);
		if (cacheTunnel) {
			if (tunnels.containsKey(tunnel)) {
				tunnel = tunnels.get(tunnel);
			} else {
				tunnels.put(tunnel, tunnel);
			}
		}
		return tunnel;
	}

	/**
	 * A convenient call to returned a possibly cached SSHTunnel. In case it was
	 * not cached, a new tunnel will be returned and cached for further use. The
	 * returned tunnel may be connected or disconnected.
	 * 
	 * @param remoteHost
	 * @param userName
	 * @param password
	 * @param localPort
	 * @param remotePort
	 * @return An SSHTunnel
	 * @see #getSSHTunnel(String, String, String, int, int, boolean)
	 */
	public static SSHTunnel getSSHTunnel(String remoteHost, String userName,
			String password, int localPort, int remotePort) {
		return getSSHTunnel(remoteHost, userName, password, localPort,
				remotePort, true);
	}

	/**
	 * Returns whether or not there is a cached tunnel in this tunnel factory.
	 * The cached tunnel may be in a connected or a disconnected state.
	 * 
	 * @param remoteHost
	 * @param userName
	 * @param password
	 * @param localPort
	 * @param remotePort
	 * @return True, if there is a cached tunnel with the given parameters;
	 *         False, otherwise.
	 */
	public static boolean hasSSHTunnel(String remoteHost, String userName,
			String password, int localPort, int remotePort) {
		SSHTunnel tunnel = createSSHTunnel(remoteHost, userName, password,
				localPort, remotePort);
		return tunnels.containsKey(tunnel);
	}

	/*
	 * Constructs and returns a new SSHTunnel instance.
	 * 
	 * @param remoteHost
	 * 
	 * @param userName
	 * 
	 * @param password
	 * 
	 * @param localPort
	 * 
	 * @param remotePort
	 * 
	 * @return A new SSHTunnel
	 */
	private static SSHTunnel createSSHTunnel(String remoteHost,
			String userName, String password, int localPort, int remotePort) {
		String localHost = "localhost"; //$NON-NLS-1$
		try {
			localHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
		}
		SSHTunnel tunnel = new SSHTunnel(localHost, remoteHost, userName,
				password, localPort, remotePort);
		return tunnel;
	}

	/**
	 * Closes all the SSHTunnel connections that were initiated and caches in
	 * this factory.
	 */
	public static void closeAllConnections() {
		for (SSHTunnel tunnel : tunnels.values()) {
			tunnel.disconnect();
		}
	}

	/**
	 * Returns an unmodifiable List of the SSHTunnels that were created and
	 * cached using this factory. The returned SSHTunnels are the 'real'
	 * reference to the one that might be in use currently (so careful about
	 * disconnecting them).
	 * 
	 * @return An unmodifiable List of the SSHTunnels
	 */
	public static List<SSHTunnel> getAllTunnels() {
		return Collections.unmodifiableList(Arrays.asList(tunnels.values()
				.toArray(new SSHTunnel[tunnels.size()])));
	}
}
