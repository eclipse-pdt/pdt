/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.server.core;

import java.beans.PropertyChangeListener;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.project.options.PHPProjectOptions;

/**
 * A generic server implementation.
 */
public class Server {

	public static final String NAME = "name";
	public static final String BASE_URL = "base_url";
	public static final String DOCUMENT_ROOT = "document_root";
	public static final String PUBLISH = "publish";
	public static final String PORT = "port";
	public static final String HOSTNAME = "hostname";
	public static final String FILE_NAME = "file_name";

	private ServerHelper helper;

	/**
	 * Constructs a new Server
	 */
	public Server() {
		helper = new ServerHelper();
	}

	/**
	 * Constructs a new Server.
	 * 
	 * @param name
	 * @param hostName
	 * @param baseURL
	 * @param documentRoot
	 * @param publish
	 */
	public Server(String name, String host, String baseURL, String documentRoot, boolean publish) {
		this();
		setName(name);
		setHost(host);
		setBaseURL(baseURL);
		setDocumentRoot(documentRoot);
		setPublish(publish);
	}
	
	/**
	 * Add a property change listener to this server.
	 *
	 * @param listener java.beans.PropertyChangeListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		helper.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a property change listener from this server.
	 *
	 * @param listener java.beans.PropertyChangeListener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		helper.removePropertyChangeListener(listener);
	}

	public String getContextRoot(IProject project) {
		PHPProjectOptions options = PHPProjectOptions.forProject(project);
		String contextRoot = (String) options.getOption(PHPCoreConstants.PHPOPTION_CONTEXT_ROOT);

		if (contextRoot == null || contextRoot.equals("")) {
			contextRoot = project.getFullPath().toString();
		}

		return contextRoot;
	}

	public String getName() {
		return helper.getAttribute(Server.NAME, "");
	}
	
	public void setName(String name) {
		helper.setAttribute(Server.NAME, name);
	}
	
	public String getBaseURL() {
		return helper.getAttribute(Server.BASE_URL, "");
	}

	public void setBaseURL(String url) {
		helper.setAttribute(Server.BASE_URL, url);
	}

	public String getHost() {
		return helper.getAttribute(Server.HOSTNAME, "localhost");
	}

	public void setHost(String host) {
		helper.setAttribute(Server.HOSTNAME, host);
	}

	public void setDocumentRoot(String docRoot) {
		helper.setAttribute(Server.DOCUMENT_ROOT, docRoot);
	}

	public String getDocumentRoot() {
		return helper.getAttribute(Server.DOCUMENT_ROOT, "");
	}

	public boolean canPublish() {
		return helper.getAttribute(Server.PUBLISH, false);
	}

	public void setPublish(boolean publish) {
		helper.setAttribute(Server.PUBLISH, publish);
	}

	/**
	 * Return the root URL of this server.
	 * 
	 * @return java.net.URL
	 */
	public URL getRootURL() {

		try {
			String port = getPortString();
			String base = getBaseURL();
			if (base.equals(""))
				base = "http://" + getHost();

			URL url = null;

			if (port.equals("80"))
				url = new URL(base + "/");
			else
				url = new URL(base + ":" + port + "/");

			return url;

		} catch (Exception e) {
			Logger.logException("Could not get root URL", e);
			return null;
		}

	}

	protected static String renderCommandLine(String[] commandLine, String separator) {
		if (commandLine == null || commandLine.length < 1)
			return "";
		StringBuffer buf = new StringBuffer(commandLine[0]);
		for (int i = 1; i < commandLine.length; i++) {
			buf.append(separator);
			buf.append(commandLine[i]);
		}
		return buf.toString();
	}

	public int getPort() {
		return Integer.parseInt(getPortString());
	}

	public String getPortString() {
		return helper.getAttribute(Server.PORT, "80");
	}

	public void setPort(String port) {
		try {
			if (port.equals("")) {
				helper.setAttribute(Server.PORT, "80");
			} else {
				helper.setAttribute(Server.PORT, port);
			}
		} catch (Throwable e) {
			;
		}
	}

	/**
	 * Return a string representation of this Server.
	 * @return java.lang.String
	 */
	public String toString() {
		return "Server [" + getHost() + ']';
	}
}
