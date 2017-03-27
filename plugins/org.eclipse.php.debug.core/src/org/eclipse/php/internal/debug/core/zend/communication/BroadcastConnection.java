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
package org.eclipse.php.internal.debug.core.zend.communication;

import static org.eclipse.php.internal.debug.core.zend.communication.BroadcastConnection.ConnectionConstants.*;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.*;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.php.internal.core.util.VersionUtils;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsManager;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerServerSettings;
import org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsUtil;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * Broadcast connection for handling any "external" request that arrives from
 * the Zend debugger engine.
 * 
 * @author Shalom Gibly
 */
public class BroadcastConnection {

	/**
	 * Connection handler job.
	 */
	protected class ConnectionHandler extends Job {

		public ConnectionHandler() {
			super(Messages.BroadcastConnection_Broadcast_connection_handler_name);
			setSystem(true);
			setUser(false);
			setPriority(LONG);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));
				List<String> requestLines = new ArrayList<String>();
				String currentLine;
				while (true) {
					currentLine = reader.readLine();
					if (currentLine == null || currentLine.isEmpty())
						break;
					requestLines.add(currentLine);
				}
				String firstLine = requestLines.isEmpty() ? null : requestLines.get(0);
				String host;
				int port;
				Server server = lookupServer(requestLines);
				host = lookupHosts(server);
				port = lookupPort(server);
				writer.write(getStandardHTTPResponse());
				if (isRequestFromZendServer(firstLine)) {
					writer.println(getJSonResponseString(false, host, port));
				} else {
					String platform = getPlatformValue(firstLine);
					if (platform == null) {
						writer.println(getResponseString(false, host, port));
					} else {
						writer.write(getHTMLContent(platform, host, port));
					}
				}
				writer.flush();
			} catch (Exception e) {
				DebugPlugin.log(e);
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException oe) {
						DebugPlugin.log(oe);
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException ioe) {
						DebugPlugin.log(ioe);
					}
				}
			}
			return Status.OK_STATUS;
		}

	}

	/**
	 * Simple JSON object descriptor.
	 */
	protected static class JSONDescriptor {

		protected Map<String, String> properties = new HashMap<String, String>();

		public JSONDescriptor() {
		};

		public void put(String key, String value) {
			properties.put(key, value);
		}

		public String getString() {
			try {
				Iterator<String> keys = properties.keySet().iterator();
				StringBuilder sb = new StringBuilder("{"); //$NON-NLS-1$
				while (keys.hasNext()) {
					if (sb.length() > 1) {
						sb.append(',');
					}
					Object o = keys.next();
					sb.append(quote(o.toString()));
					sb.append(':');
					sb.append(properties.get(o));
				}
				sb.append('}');
				return sb.toString();
			} catch (Exception e) {
				return null;
			}
		}

		public static String quote(String string) {
			if (string == null || string.length() == 0) {
				return "''"; //$NON-NLS-1$
			}
			char b;
			char c = 0;
			int i;
			int len = string.length();
			StringBuilder sb = new StringBuilder(len + 4);
			String t;
			sb.append('\'');
			for (i = 0; i < len; i += 1) {
				b = c;
				c = string.charAt(i);
				switch (c) {
				case '\\':
				case '\'':
					sb.append('\\');
					sb.append(c);
					break;
				case '/':
					if (b == '<') {
						sb.append('\\');
					}
					sb.append(c);
					break;
				case '\b':
					sb.append("\\b"); //$NON-NLS-1$
					break;
				case '\t':
					sb.append("\\t"); //$NON-NLS-1$
					break;
				case '\n':
					sb.append("\\n"); //$NON-NLS-1$
					break;
				case '\f':
					sb.append("\\f"); //$NON-NLS-1$
					break;
				case '\r':
					sb.append("\\r"); //$NON-NLS-1$
					break;
				default:
					if (c < ' ') {
						t = "000" + Integer.toHexString(c); //$NON-NLS-1$
						sb.append("\\u" + t.substring(t.length() - 4)); //$NON-NLS-1$
					} else {
						sb.append(c);
					}
				}
			}
			sb.append('\'');
			return sb.toString();
		}

	}

	/**
	 * Set of different constant strings for broadcast connection.
	 */
	public interface ConnectionConstants {

		public static final String DEBUG_HOST = "debug_host";//$NON-NLS-1$
		public static final String DEBUG_PORT = "debug_port";//$NON-NLS-1$
		public static final String DEBUG_FASTFILE = "debug_fastfile";//$NON-NLS-1$
		public static final String USE_SSL = "use_ssl";//$NON-NLS-1$
		public static final String USE_TUNNELING = "use_tunneling";//$NON-NLS-1$
		public static final String IDE_SETTINGS = "var zendStudioSettings = "; //$NON-NLS-1$
		public static final String PLATFORM_GUI = "platform_gui="; //$NON-NLS-1$
		public static final String REFERER = "Referer:"; //$NON-NLS-1$
		public static final String STANDARD_HTTP_RESPONSE = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n"; //$NON-NLS-1$

	}

	protected Socket socket;

	/**
	 * Constructs a new BroadcastConnection with a given {@link Socket}.
	 * 
	 * @param socket
	 */
	public BroadcastConnection(Socket socket) {
		init(socket);
	}

	protected void init(Socket socket) {
		this.socket = socket;
		(new ConnectionHandler()).schedule();
	}

	protected boolean isRequestFromZendServer(String line) {
		if (line == null)
			return false;
		String zendServerString = "ZendServer="; //$NON-NLS-1$
		int index = line.indexOf(zendServerString);
		if (index == -1)
			return false;
		String value = null;
		value = line.substring(index + zendServerString.length());
		index = value.indexOf("HTTP");//$NON-NLS-1$
		if (index == -1)
			return false;
		value = value.substring(0, index);
		return isBiggerOrEqual(value, "4.0.0");//$NON-NLS-1$
	}

	protected boolean isBiggerOrEqual(String value, String baseVersion) {
		if (value == null)
			return false;
		return VersionUtils.equal(value, baseVersion, 3) || VersionUtils.greater(value, baseVersion, 3);
	}

	protected String getPlatformValue(String line) {
		if (line == null)
			return null;
		int index = line.indexOf(PLATFORM_GUI);
		if (index == -1)
			return null;
		String value = null;
		try {
			value = line.substring(index + PLATFORM_GUI.length());
			value = value.substring(0, value.indexOf(" ")); //$NON-NLS-1$
			value = URLDecoder.decode(value, "UTF-8"); //$NON-NLS-1$
		} catch (Exception e) {
			DebugPlugin.log(e);
			return null;
		}
		return value;
	}

	protected String getStandardHTTPResponse() {
		return STANDARD_HTTP_RESPONSE;
	}

	protected String getResponseString(boolean addQuestionMark, String host, int port) {
		int sslOn = isUseSSL() ? 1 : 0;
		String result = addQuestionMark ? "?" : "&"; //$NON-NLS-1$ //$NON-NLS-2$
		result += DEBUG_PORT + '=' + port;
		result += '&' + DEBUG_HOST + '=' + host;
		result += '&' + DEBUG_FASTFILE + '=' + String.valueOf(1);
		if (isUseSSL())
			result += '&' + USE_SSL + '=' + String.valueOf(sslOn);
		return result;
	}

	protected String getJSonResponseString(boolean addQuestionMark, String host, int port) {
		String result = null;
		int sslOn = isUseSSL() ? 1 : 0;
		JSONDescriptor descriptor = new JSONDescriptor();
		descriptor.put(DEBUG_HOST, JSONDescriptor.quote(host));
		descriptor.put(DEBUG_PORT, String.valueOf(port));
		descriptor.put(DEBUG_FASTFILE, String.valueOf(1));
		if (isUseSSL())
			descriptor.put(USE_SSL, String.valueOf(sslOn));
		result = IDE_SETTINGS + descriptor.getString();
		return result;
	}

	protected String getHTMLContent(String platformValue, String host, int port) {
		String platformPath = platformValue;
		// There is no http:// or https:// in the value
		if (platformValue.indexOf("://") == -1) //$NON-NLS-1$
			platformPath = "http://" + platformValue; //$NON-NLS-1$
		// Add question mark if not exists
		platformPath += getResponseString(platformValue.indexOf('?') == -1, host, port);
		String htmlContent = MessageFormat.format(Messages.BroadcastConnection_HTML_content_message, platformPath,
				platformPath);
		return htmlContent;
	}

	protected Server lookupServer(List<String> requestLines) {
		Iterator<String> iterator = requestLines.iterator();
		while (iterator.hasNext()) {
			String currentLine = iterator.next().trim();
			if (currentLine.startsWith(REFERER)) {
				String referer = currentLine.replaceFirst(REFERER, "").trim(); //$NON-NLS-1$
				return ServersManager.findByURL(referer);
			}
		}
		return null;
	}

	protected String lookupHosts(Server server) {
		if (server != null) {
			IDebuggerSettings settings = DebuggerSettingsManager.INSTANCE.findSettings(server.getUniqueId(),
					server.getDebuggerId());
			if (settings instanceof ZendDebuggerServerSettings) {
				return ZendDebuggerSettingsUtil.getDebugHosts(server.getUniqueId());
			}
		}
		return PHPDebugUtil.getZendAllHosts();
	}

	protected int lookupPort(Server server) {
		if (server != null) {
			IDebuggerSettings settings = DebuggerSettingsManager.INSTANCE.findSettings(server.getUniqueId(),
					server.getDebuggerId());
			if (settings instanceof ZendDebuggerServerSettings) {
				return ZendDebuggerSettingsUtil.getDebugPort(server.getUniqueId());
			}
		}
		return PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
	}

	private boolean isUseSSL() {
		return InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
				.getBoolean(PHPDebugCorePreferenceNames.ZEND_DEBUG_ENCRYPTED_SSL_DATA, false);
	}

}
