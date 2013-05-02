/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Aptana Inc.
 *******************************************************************************/
package org.eclipse.php.internal.server.core.tunneling;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.php.internal.server.core.Activator;

/**
 * A class that is intended to test a tunnel connection.
 * 
 * @author Shalom Gibly
 */
public class TunnelTester {

	public static final int PASSWORD_CHANGED_CODE = SSHTunnel.CONNECTION_PASSWORD_CHANGED_CODE;

	/**
	 * Test SSH tunnel connection. This test will try to establish a connection
	 * using a SSHTunnel. In case successful, the connection will be closed at
	 * the end of the test. There are several return values possibilities to
	 * this test, which are derived from the {@link SSHTunnel#connect()} method:<br>
	 * <ul>
	 * <li>Status OK - Signals that the connection was successful with no errors
	 * or warnings</li>
	 * <li>Status ERROR - Signals that the connection was unsuccessful</li>
	 * <li>Status WARNING - Signals that the connection was successful, however
	 * there are a few warning notifications that should be reviewed</li>
	 * <li>Status INFO - Signals that the connection was successful, however
	 * there was a modification to the connection data that is expressed in the
	 * INFO code (such as a password change data)</li>
	 * </ul>
	 * <br>
	 * A MultiStatus will be returned in case the connection state could not be
	 * determined (the tunnel connection did not indicate a fatal error,
	 * however, the SSH connection returned false for an isConnected query).
	 * 
	 * @param remoteHost
	 * @param userName
	 * @param password
	 * @param localPort
	 * @param remotePort
	 * @return The IStatus for the connection creation.
	 * @see SSHTunnel#connent
	 */
	public static IStatus test(String remoteHost, String userName,
			String password, int localPort, int remotePort) {
		SSHTunnel sshTunnel = SSHTunnelFactory.getSSHTunnel(remoteHost,
				userName, password, localPort, remotePort, false);
		IStatus connectionResult = sshTunnel.connect();
		if (connectionResult.getSeverity() != IStatus.ERROR) {
			if (sshTunnel.isConnected()) {
				sshTunnel.disconnect();
				return connectionResult;
			} else {
				sshTunnel.disconnect();
				MultiStatus status = new MultiStatus(
						Activator.PLUGIN_ID,
						0,
						Messages.TunnelTester_0,
						null);
				status.add(connectionResult); // add any other statuses into the
				// multi-status, so we can track a password change, for example.
				return status;
			}
		}
		return connectionResult;
	}
}
