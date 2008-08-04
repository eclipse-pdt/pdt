/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.debug.core.xdebug.IDELayer;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;

/**
 * This class handles the management of DBGp proxy interaction.
 */
public class DBGpProxyHandler {

	private boolean registered = false;
	private String currentIdeKey = null;
	private String proxyHost = "";
	private int proxyPort = -1;
	private int idePort = -1;
	private int errorCode = 0;
	private String errorMsg = "";
	private boolean useProxy = false;
	private boolean multisession = false;
	
	private static final int DEFAULT_PROXY_PORT = 9001;
	
	public static DBGpProxyHandler instance = new DBGpProxyHandler();
	
	private DBGpProxyHandler() {	
		configure();
	}
	
	

	/**
	 * Register with the proxy if we are not already registered. 
	 * @return true if registration successful
	 */
	public boolean registerWithProxy() {
		
		
		//proxyinit -a ip:port -k ide_key -m [0|1]
		//
		//-p 	the port that the IDE listens for debugging on. The address is retrieved from the connection information.
		//-k 	a IDE key, which the debugger engine will also use in it's debugging init command. this allows the proxy to match request to IDE. Typically the user will provide the session key as a configuration item.
		//		-m 	this tells the demon that the IDE supports (or doesn't) multiple debugger sessions. if -m is missing, zero or no support is default.
		
		//		response
		//	<proxyinit success="[0|1]"
		//	           idekey="{ID}"
		//	           address="{IP_ADDRESS}"
		//	           port="{NUM}>
		//	    <error id="app_specific_error_code">
		//	        <message>UI Usable Message</message>
		//	    </error>
		//	</proxyinit>
		if (!registered) {
			DBGpResponse resp = sendcmd("proxyinit -p " + idePort + " -k " + currentIdeKey + " -m " + (multisession ? "1" : "0"));
			if (resp != null) {
				if (resp.getType() == DBGpResponse.PROXY_INIT && resp.getErrorCode() == DBGpResponse.ERROR_OK) {
					registered = true;
				}
				else {
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
		//	IDE command
		//
		//	proxystop -k ide_key
		if (registered) {
			DBGpResponse resp = sendcmd("proxystop" + " -k " + currentIdeKey);
			registered = false;
			String isOk = DBGpResponse.getAttribute(resp.getParentNode(), "success");
			if (isOk == null || !isOk.equals("1")) {
				DBGpLogger.logWarning("Unexpected response from proxystop. ErrorCode=" + resp.getErrorCode() + ". msg=" + resp.getErrorMessage(), this, null);
			}
		}
	}

	/**
	 * return true if registered with a proxy.
	 * @return
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * get the last received error code.
	 * @return error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * get the last received error message.
	 * @return error message
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * configure the Proxy Handler from the preferences.
	 */
	public void configure() {
		Preferences prefs = XDebugPreferenceMgr.getPreferences();
		
		useProxy = prefs.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_USEPROXY);
		if (useProxy == false) {
			unregister();
		}
		else {
			int idePort = prefs.getInt(XDebugPreferenceMgr.XDEBUG_PREF_PORT);			
			String ideKey = prefs.getString(XDebugPreferenceMgr.XDEBUG_PREF_IDEKEY);
			boolean multisession = prefs.getBoolean(XDebugPreferenceMgr.XDEBUG_PREF_MULTISESSION); 
			String proxy = prefs.getString(XDebugPreferenceMgr.XDEBUG_PREF_PROXY);
			String proxyHost = proxy;
			int proxyPort = -1;
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
			
			setProxyInfo(proxyHost, proxyPort, ideKey, idePort, multisession);
		}
	}
	
	/**
	 * store the proxy information.
	 * @param host
	 * @param port
	 * @param idekey
	 * @param listeningPort
	 * @param multisession
	 */
	private void setProxyInfo(String host, int port, String idekey, int listeningPort, boolean multisession) {
		if (port < 1) {
			port = DEFAULT_PROXY_PORT;
		}
		if (!host.equalsIgnoreCase(proxyHost) || port != proxyPort || !idekey.equals(currentIdeKey)
									|| idePort != listeningPort || this.multisession != multisession) {
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
	 * @return
	 */
	public boolean useProxy() {
		return useProxy;
	}

	/**
	 * send a command to the proxy and get a response.
	 * @param cmd the command to send
	 * @return the response.
	 */
	private DBGpResponse sendcmd(String cmd) {
		DBGpResponse dbgpResp = null;
		try {
			Socket s = new Socket(proxyHost, proxyPort);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			if (DBGpLogger.debugCmd()) {
				DBGpLogger.debug("cmd: " + cmd);
			}
			os.write(cmd.getBytes("ASCII")); //command will always be ASCII
			os.flush();
			byte[] resp = readResponse(is);
			dbgpResp = new DBGpResponse();
			dbgpResp.parseResponse(resp);
			s.shutdownInput();
			s.shutdownOutput();
			s.close();
			return dbgpResp;
		}
		catch(IOException ioe) {
			if (dbgpResp == null) {
				errorCode = 9999;
				if (ioe instanceof EOFException) {
					errorMsg = "invalid response from proxy.";
				}
				else {
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
	 * @param is socket input stream
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
		String rr = new String(byteArray, "ASCII");
		if (DBGpLogger.debugResp()) {
			DBGpLogger.debug("Response: " + new String(byteArray, "ASCII"));
		}
		return byteArray;
	}	
	
	/**
	 * generate an IDE Key for this system.
	 * @return a generated ide key.
	 */
	public String generateIDEKey() {
		String toAppend = null;
		try {
			toAppend = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			toAppend = java.util.UUID.randomUUID().toString();
			
		}
		return DBGpSessionHandler.IDEKEY_PREFIX + "_" + toAppend;
	}


    /**
     * return the current ide key if we are configured to use the proxy.
     * if useProxy is false, you should use the ide key here. It may or may not be null.
     * @return
     */
	public String getCurrentIdeKey() {
		return currentIdeKey;
	}
	
}
