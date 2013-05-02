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

package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr.AcceptRemoteSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.swt.widgets.Display;

/**
 * This class handles the management of DBGp proxy interaction.
 */
public class DBGpProxyHandler {

	private boolean registered = false;
	private String currentIdeKey = null;
	private String proxyHost = ""; //$NON-NLS-1$
	private int proxyPort = DEFAULT_PROXY_PORT;
	private int idePort = -1;
	private int errorCode = 0;
	private String errorMsg = ""; //$NON-NLS-1$
	private boolean multisession = false;

	private static final int DEFAULT_PROXY_PORT = 9001;
	private static final int PROXY_CONNECT_TIMEOUT = 3000; // 3 seconds

	public static DBGpProxyHandler instance = new DBGpProxyHandler();

	private DBGpProxyHandler() {
	}

	/**
	 * Register with the proxy if we are not already registered.
	 * 
	 * @return true if registration successful
	 */
	public boolean registerWithProxy() {

		// proxyinit -a ip:port -k ide_key -m [0|1]
		//
		// -p the port that the IDE listens for debugging on. The address is
		// retrieved from the connection information.
		// -k a IDE key, which the debugger engine will also use in it's
		// debugging init command. this allows the proxy to match request to
		// IDE. Typically the user will provide the session key as a
		// configuration item.
		// -m this tells the demon that the IDE supports (or doesn't) multiple
		// debugger sessions. if -m is missing, zero or no support is default.

		// response
		// <proxyinit success="[0|1]"
		// idekey="{ID}"
		// address="{IP_ADDRESS}"
		// port="{NUM}>
		// <error id="app_specific_error_code">
		// <message>UI Usable Message</message>
		// </error>
		// </proxyinit>
		if (!registered) {
			DBGpResponse resp = sendcmd("proxyinit -p " + idePort + " -k " + currentIdeKey + " -m " + (multisession ? "1" : "0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			if (resp != null) {
				if (resp.getType() == DBGpResponse.PROXY_INIT
						&& resp.getErrorCode() == DBGpResponse.ERROR_OK) {
					registered = true;
				} else {
					errorCode = resp.getErrorCode();
					errorMsg = resp.getErrorMessage();
				}
			}
		}
		return registered;
	}

	/**
	 * unregister from the proxy.
	 */
	public void unregister() {
		// IDE command
		//
		// proxystop -k ide_key
		if (registered) {
			DBGpResponse resp = sendcmd("proxystop" + " -k " + currentIdeKey); //$NON-NLS-1$ //$NON-NLS-2$
			registered = false;
			String isOk = DBGpResponse.getAttribute(resp.getParentNode(),
					"success"); //$NON-NLS-1$
			if (isOk == null || !isOk.equals("1")) { //$NON-NLS-1$
				DBGpLogger
						.logWarning(
								"Unexpected response from proxystop. ErrorCode=" + resp.getErrorCode() + ". msg=" + resp.getErrorMessage(), this, null); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	/**
	 * return true if registered with a proxy.
	 * 
	 * @return
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * get the last received error code.
	 * 
	 * @return error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * get the last received error message.
	 * 
	 * @return error message
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * configure the Proxy Handler from the preferences.
	 */
	public void configure() {
		// TODO: move to using preference manager completely.
		Preferences prefs = XDebugPreferenceMgr.getPreferences();

		if (useProxy() == false) {
			unregister();
		} else {
			int idePort = XDebugPreferenceMgr.getPort(prefs);
			String ideKey = prefs
					.getString(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY);
			boolean multisession = XDebugPreferenceMgr.useMultiSession();
			String proxy = prefs
					.getString(XDebugPreferenceMgr.XDEBUG_PREF_PROXY);
			String proxyHost = proxy;
			int proxyPort = DEFAULT_PROXY_PORT;
			int split = proxy.indexOf(':');
			if (split != -1) {
				proxyHost = proxy.substring(0, split);
				if (split + 1 < proxy.length()) {
					String portStr = proxy.substring(split + 1);
					try {
						proxyPort = Integer.parseInt(portStr);
					} catch (NumberFormatException e) {
					}
				}
			}

			if (proxyPort == XDebugPreferenceMgr.getPort()) {
				displayErrorMessage(PHPDebugCoreMessages.XDebug_DBGpProxyHandler_0); 
				XDebugPreferenceMgr.setUseProxy(false);
			} else {
				setProxyInfo(proxyHost, proxyPort, ideKey, idePort,
						multisession);
				if (XDebugPreferenceMgr.getAcceptRemoteSession() != AcceptRemoteSession.off) {
					// if jit we must register with the proxy straight away
					// rather than wait
					// for the first launch

					Job job = new Job("register with proxy") { //$NON-NLS-1$

						@Override
						protected IStatus run(IProgressMonitor monitor) {
							if (registerWithProxy() == false) {
								displayErrorMessage(PHPDebugCoreMessages.XDebug_DBGpProxyHandler_1
										+ getErrorMsg()); 
								XDebugPreferenceMgr.setUseProxy(false);
							}
							return Status.OK_STATUS;
						}
					};
					job.schedule();
				}

			}
		}
	}

	/**
	 * store the proxy information.
	 * 
	 * @param host
	 * @param port
	 * @param idekey
	 * @param listeningPort
	 * @param multisession
	 */
	private void setProxyInfo(String host, int port, String idekey,
			int listeningPort, boolean multisession) {
		if (!host.equalsIgnoreCase(proxyHost) || port != proxyPort
				|| !idekey.equals(currentIdeKey) || idePort != listeningPort
				|| this.multisession != multisession) {
			unregister(); // checks for connection
			proxyHost = host;
			proxyPort = port;
			idePort = listeningPort;
			currentIdeKey = idekey;
			this.multisession = multisession;
		}
	}

	/**
	 * returns true a proxy should be used.
	 * 
	 * @return
	 */
	public boolean useProxy() {
		return XDebugPreferenceMgr.useProxy();
	}

	/**
	 * send a command to the proxy and get a response.
	 * 
	 * @param cmd
	 *            the command to send
	 * @return the response.
	 */
	private DBGpResponse sendcmd(String cmd) {
		DBGpResponse dbgpResp = null;
		try {
			// TODO: look at reducing the timeout for connection failure.
			// Socket s = new Socket(proxyHost, proxyPort);
			Socket s = new Socket();
			InetSocketAddress server = new InetSocketAddress(proxyHost,
					proxyPort);
			InetSocketAddress local = new InetSocketAddress(0);
			s.bind(local);
			s.connect(server, PROXY_CONNECT_TIMEOUT);

			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			if (DBGpLogger.debugCmd()) {
				DBGpLogger.debug("cmd: " + cmd); //$NON-NLS-1$
			}
			os.write(cmd.getBytes("ASCII")); //command will always be ASCII //$NON-NLS-1$
			os.flush();
			byte[] resp = readResponse(is);
			dbgpResp = new DBGpResponse();
			dbgpResp.parseResponse(resp);
			s.shutdownInput();
			s.shutdownOutput();
			s.close();
			return dbgpResp;
		} catch (IOException ioe) {
			if (dbgpResp == null) {
				errorCode = 9999;
				if (ioe instanceof EOFException) {
					errorMsg = PHPDebugCoreMessages.XDebug_DBGpProxyHandler_2; 
				} else {
					errorMsg = ioe.getMessage();
					if (errorMsg == null) {
						errorMsg = ioe.getClass().getName();
					}
				}
			}
		}
		return dbgpResp;

	}

	/**
	 * read from the socket input stream and create the response.
	 * 
	 * @param is
	 *            socket input stream
	 * @return a response
	 * @throws IOException
	 */
	private byte[] readResponse(InputStream is) throws IOException {
		byte byteArray[];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		while ((b = is.read()) != -1) {
			baos.write(b);
		}
		byteArray = baos.toByteArray();
		if (DBGpLogger.debugResp()) {
			DBGpLogger.debug("Response: " + new String(byteArray, "ASCII")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return byteArray;
	}

	/**
	 * generate an IDE Key for this system.
	 * 
	 * @return a generated ide key.
	 */
	public String generateIDEKey() {
		String toAppend = null;
		try {
			toAppend = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			toAppend = java.util.UUID.randomUUID().toString();

		}
		return DBGpSessionHandler.IDEKEY_PREFIX + "_" + toAppend; //$NON-NLS-1$
	}

	/**
	 * return the current ide key if we are configured to use the proxy. if
	 * useProxy is false, you should use the ide key here. It may or may not be
	 * null.
	 * 
	 * @return
	 */
	public String getCurrentIdeKey() {
		return currentIdeKey;
	}

	private void displayErrorMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog
						.openError(Display.getDefault().getActiveShell(),
								PHPDebugCoreMessages.XDebug_DBGpProxyHandler_3,
								message); 
			}
		});
	}

}
