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
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.core.UniqueIdentityElementUtil;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;

/**
 * A generic server implementation.
 */
public class Server implements IXMLPreferencesStorable, IAdaptable, IUniqueIdentityElement {

	// Used as a root element name when saving and loading the preferences.
	public static final String SERVER_ELEMENT = "server"; //$NON-NLS-1$
	// Server properties.
	public static final String UNIQUE_ID = "id"; //$NON-NLS-1$
	public static final String NAME = "name"; //$NON-NLS-1$
	public static final String BASE_URL = "base_url"; //$NON-NLS-1$
	public static final String DOCUMENT_ROOT = "document_root"; //$NON-NLS-1$
	public static final String PORT = "port"; //$NON-NLS-1$
	public static final String HOSTNAME = "hostname"; //$NON-NLS-1$
	public static final String FILE_NAME = "file_name"; //$NON-NLS-1$
	public static final String DEBUGGER = "debuggerId"; //$NON-NLS-1$

	public static final String LOCALSERVER = "localserver"; //$NON-NLS-1$
	public static final String ID_PREFIX = "php-server"; //$NON-NLS-1$
	public static final String NONE_DEBUGGER_ID = "org.eclipse.php.debug.core.noneDebugger"; //$NON-NLS-1$
	public static final int DEFAULT_HTTP_PORT = 80;

	private ServerHelper helper;

	/**
	 * Constructs a new Server
	 */
	public Server() {
		helper = new ServerHelper(this);
		createUniqueId();
		setDebuggerId(NONE_DEBUGGER_ID);
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
	public Server(String name, String host, String baseURL, String documentRoot) throws MalformedURLException {
		this();
		setName(name);
		setHost(host);
		setBaseURL(baseURL);
		setDocumentRoot(documentRoot);
	}

	@Override
	public String getUniqueId() {
		return getAttribute(UNIQUE_ID, null);
	}

	private void createUniqueId() {
		setAttribute(UNIQUE_ID, UniqueIdentityElementUtil.generateId(ID_PREFIX));
	}

	/**
	 * Add a property change listener to this server. The same listener will not
	 * be added twice.
	 * 
	 * @param listener
	 *            java.beans.PropertyChangeListener; cannot be <code>null</code>
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		helper.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a property change listener from this server.
	 * 
	 * @param listener
	 *            java.beans.PropertyChangeListener; cannot be <code>null</code>
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
			resultURL = new URL(baseURL.getProtocol(), baseURL.getHost(), getFormattedPort(port), ""); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			// hopefully this is not called as setBaseURL is safe
			return base;
		}
		return resultURL.toString();
	}

	private int getFormattedPort(String port) {
		int i = (port == null || port.length() == 0) ? -1 : Integer.valueOf(port);
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

	protected static String renderCommandLine(String[] commandLine, String separator) {
		if (commandLine == null || commandLine.length < 1)
			return ""; //$NON-NLS-1$
		StringBuilder buf = new StringBuilder(commandLine[0]);
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

	public void setDebuggerId(String debuggerId) {
		setAttribute(Server.DEBUGGER, debuggerId);
	}

	public String getDebuggerId() {
		return getAttribute(Server.DEBUGGER, null);
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
		} else {
			return name.equals(otherName);
		}
		return false;
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
	public void restoreFromMap(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		Map<String, Object> properties = (Map<String, Object>) map.get(SERVER_ELEMENT);
		// This will cause for property change events to be fired on every
		// attribute set.
		for (Entry<?, ?> entry : properties.entrySet()) {
			setAttribute((String) entry.getKey(), (String) entry.getValue());
		}
		// Backward check (older releases didn't have unique ID for servers)
		if (!properties.containsKey(UNIQUE_ID)) {
			createUniqueId();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable
	 * #storeToMap()
	 */
	public Map<String, Object> storeToMap() {
		Map<String, Object> properties = new HashMap<String, Object>(helper.map);
		Map<String, Object> serverMap = new HashMap<String, Object>(1);
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

	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	/**
	 * Creates and returns working copy of this server.
	 * 
	 * @return working copy of this server
	 */
	public Server makeCopy() {
		Server copy = new Server();
		copy.helper.map = new HashMap<String, String>(helper.map);
		return copy;
	}

	/**
	 * Updates original server with given working copy data.
	 * 
	 * @param copy
	 */
	public void update(Server copy) {
		// Copy unique ID must be the same as the original
		Assert.isTrue(getUniqueId().equals(copy.getUniqueId()));
		Set<String> keys = copy.helper.map.keySet();
		// Update all attributes of original server
		for (String key : keys) {
			if (key.equals(UNIQUE_ID))
				continue;
			helper.setAttribute(key, copy.helper.map.get(key));
		}
		// Remove the attributes that were removed in a copy
		List<String> attributesToRemove = new ArrayList<String>();
		for (String key : helper.map.keySet()) {
			if (!copy.helper.map.containsKey(key))
				attributesToRemove.add(key);
		}
		for (String key : attributesToRemove)
			helper.removeAttribute(key);
	}

}