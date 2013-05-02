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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.server.core.Activator;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * An SSH tunnel (port forwarding). This class creates a tunnel between a port
 * on a remote host and a port on the local host.
 * 
 * @author Shalom Gibly
 */
public class SSHTunnel {
	public static final int CONNECTION_ERROR_CODE = 100;
	public static final int CONNECTION_WARNING_CODE = 200;
	public static final int CONNECTION_PASSWORD_CHANGED_CODE = 300;

	private static final int SSH_DEFAULT_PORT = 22;
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private String remoteHost;
	private String localHost;
	private String userName;
	private String password;
	private int localPort;
	private int remotePort;
	private Session session;

	/**
	 * Constructs a new SSH tunnel.
	 * 
	 * @param localHost
	 * @param remoteHost
	 * @param userName
	 *            (may be null or empty)
	 * @param password
	 *            (may be null or empty)
	 * @param localPort
	 * @param remotePort
	 * @throws IllegalArgumentException
	 *             In case one of the variables is null or off limits (null
	 *             passwords are not verified).
	 */
	public SSHTunnel(String localHost, String remoteHost, String userName,
			String password, int localPort, int remotePort)
			throws IllegalArgumentException {
		validateInput(localHost, remoteHost, localPort, remotePort);
		this.remoteHost = remoteHost;
		this.localHost = localHost;
		this.localPort = localPort;
		this.remotePort = remotePort;
		if (userName == null) {
			this.userName = EMPTY_STRING;
		} else {
			this.userName = userName;
		}
		if (password == null) {
			this.password = EMPTY_STRING;
		} else {
			this.password = password;
		}
	}

	/**
	 * Create the tunnel connection. If the tunnel is already connected, nothing
	 * happens. This call will potentially change the password value of this
	 * class in case the user is prompted to enter a valid one. There are
	 * several return values possibilities to this call (as IStatus instances):<br>
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
	 * 
	 * @return An IStatus that holds the creation result.
	 */
	@SuppressWarnings("unchecked")
	public IStatus connect() {
		IStatus status = Status.OK_STATUS;
		if (isConnected()) {
			return status;
		}
		// ssh -R 9000:localhost:9000 you@example.aptanacloud.com
		int retry = 1;
		String newPassword = null;
		while (true) {
			try {
				session = SSHTunnelSession.getSession(userName, password,
						remoteHost, SSH_DEFAULT_PORT, null).getSession();
				// This might throw exception because of binary compatibility
				// issues.
				// The JSCH API for the port forwarding was different in eclipse
				// 3.2 and 3.4
				Exception ex = null;

				String actualPassword = session.getUserInfo().getPassword();
				if (!password.equals(actualPassword)) {
					newPassword = actualPassword;
				}
				Class sessionClass = session.getClass();
				Class[] parameterTypes = new Class[] { int.class, String.class,
						int.class };
				try {
					// session.setPortForwardingR(rport, host, lport)
					Object[] values = new Object[] { new Integer(remotePort),
							localHost, new Integer(localPort) };
					java.lang.reflect.Method mSetPortForwarding = sessionClass
							.getMethod("setPortForwardingR", //$NON-NLS-1$
									parameterTypes);
					mSetPortForwarding.invoke(session, values);
				} catch (NoSuchMethodException nsme) {
					// it will not be thrown.
				} catch (InvocationTargetException e) {
					// this can be thrown if we already binded the remote port.
					// if this is the case, ignore it. If not, log it.
					if (e.getCause() != null
							&& e.getCause().getMessage() != null
							&& e.getCause().getMessage().toLowerCase().indexOf(
									Messages.SSHTunnel_2) == -1) 
					{
						ex = e;
					}
				} catch (Exception e) {
					ex = e;
				}
				if (ex != null) {
					if (isConnected()) {
						status = new Status(
								IStatus.WARNING,
								Activator.PLUGIN_ID,
								CONNECTION_WARNING_CODE,
								Messages.SSHTunnel_3, ex); 
					} else {
						status = new Status(
								IStatus.ERROR,
								Activator.PLUGIN_ID,
								CONNECTION_ERROR_CODE,
								Messages.SSHTunnel_4, ex); 
					}
				}
			} catch (JSchException ee) {
				retry--;
				if (retry < 0) {
					status = new Status(
							IStatus.ERROR,
							Activator.PLUGIN_ID,
							CONNECTION_ERROR_CODE,
							Messages.SSHTunnel_5 + remoteHost, ee); 
					break;
				}
				if (session != null && session.isConnected()) {
					// Make sure we disconnect before retrying
					session.disconnect();
				}
				continue;
			}
			break;
		}
		if (newPassword != null) {
			password = newPassword;
		}
		if (status.isOK() && newPassword != null) {
			// Change the Status to INFO and deliver the accurate password to
			// the caller.
			status = new Status(IStatus.INFO, Activator.PLUGIN_ID,
					CONNECTION_PASSWORD_CHANGED_CODE, newPassword, null); 
		}

		return status;
	}

	/**
	 * Disconnect the session.
	 */
	public void disconnect() {
		if (session != null) {
			session.disconnect();
		}
	}

	/**
	 * Returns true if the session is alive; False, if it's not.
	 * 
	 * @return True, iff the session is active.
	 */
	public boolean isConnected() {
		if (session != null) {
			return session.isConnected();
		}
		return false;
	}

	/**
	 * Returns the remote host.
	 * 
	 * @return The remote host.
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * Returns the local host.
	 * 
	 * @return The local host.
	 */
	public String getLocalHost() {
		return localHost;
	}

	/**
	 * Returns the user name.
	 * 
	 * @return The user name.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the password. The returned password might differ from the
	 * original given one in case that the user was requested to change it while
	 * connecting.
	 * 
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the local port.
	 * 
	 * @return The local port.
	 */
	public int getLocalPort() {
		return localPort;
	}

	/**
	 * Returns the remote port.
	 * 
	 * @return The remote port.
	 */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return remoteHost.hashCode() * localHost.hashCode()
				* userName.hashCode() * (localPort + 1) * (remotePort + 1);
	}

	/**
	 * Check for SSHTunnel equality. Note that we do no compare passwords, as
	 * the password itself can be modified during connections.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof SSHTunnel) {
			SSHTunnel other = (SSHTunnel) o;
			return remoteHost.equals(other.remoteHost)
					&& localHost.equals(other.localHost)
					&& userName.equals(other.userName)
					&& localPort == other.localPort
					&& remotePort == other.remotePort;
		}
		return false;
	}

	/*
	 * Validate the constructor's input.
	 * 
	 * @param localHost
	 * 
	 * @param remoteHost
	 * 
	 * @param localPort
	 * 
	 * @param remotePort
	 * 
	 * @throws IllegalArgumentException
	 */
	private void validateInput(String localHost, String remoteHost,
			int localPort, int remotePort) throws IllegalArgumentException {
		if (localHost == null || remoteHost == null) {
			throw new IllegalArgumentException(
					"Null arument was passed to the SSHTunnel"); //$NON-NLS-1$
		}
		if (localPort < 0 || localPort > 65535 || remotePort < 0
				|| remotePort > 65535) {
			throw new IllegalArgumentException(
					"Illegal port was passed to the SSHTunnel"); //$NON-NLS-1$
		}
		// user names and password may be empty, so we do not check it here
	}
}
