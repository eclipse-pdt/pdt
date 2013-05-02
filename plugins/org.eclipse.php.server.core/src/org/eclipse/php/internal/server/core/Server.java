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
package org.eclipse.php.internal.server.core;

import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;

/**
 * A generic server implementation.
 */
public class Server implements IXMLPreferencesStorable, IAdaptable {

	// Used as a root element name when saving and loading the preferences.
	public static final String SERVER_ELEMENT = "server"; //$NON-NLS-1$

	// Server properties.
	public static final String NAME = "name"; //$NON-NLS-1$
	public static final String BASE_URL = "base_url"; //$NON-NLS-1$
	public static final String DOCUMENT_ROOT = "document_root"; //$NON-NLS-1$
	public static final String PORT = "port"; //$NON-NLS-1$
	public static final String HOSTNAME = "hostname"; //$NON-NLS-1$
	public static final String FILE_NAME = "file_name"; //$NON-NLS-1$

	private static final int DEFAULT_HTTP_PORT = 80;

	public static final String LOCALSERVER = "localserver"; //$NON-NLS-1$

	private ServerHelper helper;

	/**
	 * Constructs a new Server
	 */
	public Server() {
		helper = new ServerHelper(this);
	}

	/**
	 * Constructs a new Server.
	 * 
	 * @param name
	 * @param hostName
	 * @param baseURL
	 * @param documentRoot
	 * @param publish
	 * @throws MalformedURLException
	 */
	public Server(String name, String host, String baseURL, String documentRoot)
			throws MalformedURLException {
		this();
		setName(name);
		setHost(host);
		setBaseURL(baseURL);
		setDocumentRoot(documentRoot);
	}

	/**
	 * Add a property change listener to this server.
	 * 
	 * @param listener
	 *            java.beans.PropertyChangeListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		helper.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a property change listener from this server.
	 * 
	 * @param listener
	 *            java.beans.PropertyChangeListener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		helper.removePropertyChangeListener(listener);
	}

	/**
	 * Sets an arbitrary attribute to this Server.
	 * 
	 * @param attributeName
	 *            The attribute name
	 * @param value
	 *            The String value of this attribute.
	 */
	public void setAttribute(String attributeName, String value) {
		helper.setAttribute(attributeName, value);
	}

	/**
	 * Returns an arbitrary attribute from this Server according to a given
	 * attribute name.
	 * 
	 * @param attributeName
	 *            The attribute name
	 * @param defaultValue
	 *            A default value to use if the attribute was not found
	 * @return The String value of this attribute
	 */
	public String getAttribute(String attributeName, String defaultValue) {
		return helper.getAttribute(attributeName, defaultValue);
	}

	/**
	 * Removed an attribute.
	 * 
	 * @param attributeName
	 *            The attribute name.
	 */
	public void removeAttribute(String attributeName) {
		helper.removeAttribute(attributeName);
	}

	public String getName() {
		return getAttribute(Server.NAME, ""); //$NON-NLS-1$
	}

	public void setName(String name) {
		setAttribute(Server.NAME, name);
	}

	public String getBaseURL() {
		String base = getAttribute(Server.BASE_URL, ""); //$NON-NLS-1$
		String port = getPortString();

		URL resultURL;
		try {
			URL baseURL = new URL(base);
			resultURL = new URL(baseURL.getProtocol(), baseURL.getHost(),
					getFormattedPort(port), ""); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			// hopefully this is not called as setBaseURL is safe
			return base;
		}
		return resultURL.toString();
	}

	private int getFormattedPort(String port) {
		int i = (port == null || port.length() == 0) ?  -1 : Integer.valueOf(port);
		if (i == DEFAULT_HTTP_PORT) {
			i = -1;
		}
		return i;
	}

	public void setBaseURL(String url) throws MalformedURLException {
		URL baseURL = new URL(url);
		if (baseURL.getPort() != -1) {
			this.setPort(String.valueOf(baseURL.getPort()));
		}
		URL url2 = new URL(baseURL.getProtocol(), baseURL.getHost(), ""); //$NON-NLS-1$
		setAttribute(Server.BASE_URL, url2.toString());
	}

	public String getHost() {
		return getAttribute(Server.HOSTNAME, "localhost"); //$NON-NLS-1$
	}

	public void setHost(String host) {
		setAttribute(Server.HOSTNAME, host);
	}

	public void setDocumentRoot(String docRoot) {
		setAttribute(Server.DOCUMENT_ROOT, docRoot);
	}

	public String getDocumentRoot() {
		return getAttribute(Server.DOCUMENT_ROOT, ""); //$NON-NLS-1$
	}

	/**
	 * Return the root URL of this server.
	 * 
	 * @return java.net.URL
	 */
	public URL getRootURL() {
		try {
			return new URL(this.getBaseURL());
		} catch (Exception e) {
			Logger.logException("Could not get root URL", e); //$NON-NLS-1$
			return null;
		}

	}

	protected static String renderCommandLine(String[] commandLine,
			String separator) {
		if (commandLine == null || commandLine.length < 1)
			return ""; //$NON-NLS-1$
		StringBuffer buf = new StringBuffer(commandLine[0]);
		for (int i = 1; i < commandLine.length; i++) {
			buf.append(separator);
			buf.append(commandLine[i]);
		}
		return buf.toString();
	}

	public int getPort() {
		int port = Integer.parseInt(getPortString());
		if (port < 0) {
			port = DEFAULT_HTTP_PORT;
		}
		return port;
	}

	public String getPortString() {
		return getAttribute(Server.PORT, "80"); //$NON-NLS-1$
	}

	public void setPort(String port) {
		try {
			if (port.equals("")) { //$NON-NLS-1$
				setAttribute(Server.PORT, "80"); //$NON-NLS-1$
			} else {
				setAttribute(Server.PORT, port);
			}
		} catch (Throwable e) {
			;
		}
	}

	public int hashCode() {
		if (getName() != null) {
			return getName().hashCode();
		}
		return 1;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Server)) {
			return false;
		}
		String name = getName();
		String otherName = ((Server) obj).getName();
		if (name == null) {
			if (otherName != null) {
				return false;
			}
		}
		return name.equals(otherName);
	}

	/**
	 * Return a string representation of this Server.
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		return "Server [" + getName() + "::" + getHost() + ']'; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable
	 * #restoreFromMap(java.util.HashMap)
	 */
	public void restoreFromMap(HashMap map) {
		HashMap properties = (HashMap) map.get(SERVER_ELEMENT);
		// This will cause for property change events to be fired on every
		// attribute set.
		Iterator keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			setAttribute(key, (String) properties.get(key));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable
	 * #storeToMap()
	 */
	public HashMap storeToMap() {
		HashMap properties = new HashMap(helper.map);
		HashMap serverMap = new HashMap(1);
		serverMap.put(SERVER_ELEMENT, properties);
		return serverMap;
	}

	/**
	 * Checks whether this server is local machine
	 * 
	 * @return
	 */
	public boolean isLocal() {
		try {
			String host = getHost();
			if (host != null) {
				InetAddress addr = InetAddress.getByName(host);
				return addr.isLoopbackAddress() || addr.isSiteLocalAddress();
			}
		} catch (Exception e) {
		}
		return false;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}
}
