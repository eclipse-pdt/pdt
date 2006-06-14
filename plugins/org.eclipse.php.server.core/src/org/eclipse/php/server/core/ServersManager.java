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

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.php.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.core.util.preferences.XMLPreferencesWriter;

/**
 * A manager for PHP Servers.
 * 
 */
public class ServersManager {

	private static ServersManager instance;
	private static final String PREFERENCES_KEY = "phpServers";

	private static ServersManager getInstance() {
		if (instance == null) {
			instance = new ServersManager();
		}
		return instance;
	}

	private HashMap servers;

	private ServersManager() {
		servers = new HashMap();
		loadServers();
	}

	/**
	 * Adds a Server to the manager.
	 * If a server with the same name exists in the manager, the existing server will be overidden with 
	 * the new one.
	 * 
	 * @param server A Server
	 */
	public static void addServer(Server server) {
		ServersManager manager = getInstance();
		manager.servers.put(server.getName(), server);
	}

	/**
	 * Removes a Server from the manager.
	 * 
	 * @param serverName The name of the server.
	 * @return The removed Server; null if non was found to be removed.
	 */
	public static Server removeServer(String serverName) {
		ServersManager manager = getInstance();
		return (Server) manager.servers.remove(serverName);
	}

	/**
	 * Returns a Server from the manager.
	 * 
	 * @param serverName The name of the server.
	 * @return The Server; null if non was found.
	 */
	public static Server getServer(String serverName) {
		ServersManager manager = getInstance();
		return (Server) manager.servers.get(serverName);
	}

	/**
	 * Returns all the Servers that are managed by this manager.
	 * 
	 * @return An array of Servers.
	 */
	public static Server[] getServers() {
		ServersManager manager = getInstance();
		Collection values = manager.servers.values();
		Server[] servers = new Server[values.size()];
		values.toArray(servers);
		return servers;
	}

	/**
	 * Creates and adds a server.
	 * 
	 * @param name
	 * @param baseURL
	 * @return
	 */
	public static Server createServer(String name, String baseURL) {
		Server server = new Server(name, "localhost", baseURL, "", false);
		addServer(server);
		return server;
	}

	/**
	 * Save the listed servers.
	 */
	public static void save() {
		Server[] servers = getServers();
		XMLPreferencesWriter.write(Activator.getDefault().getPluginPreferences(), PREFERENCES_KEY, servers);
		Activator.getDefault().savePluginPreferences();
	}

	// Loads the servers from the preferences store.
	private void loadServers() {
		// First, we read the configurations of the servers from the preferences.
		HashMap[] serversConfigs = XMLPreferencesReader.read(Activator.getDefault().getPluginPreferences(), PREFERENCES_KEY);
		// Then we create the servers from their configurations...
		for (int i = 0; i < serversConfigs.length; i++) {
			HashMap serverMap = serversConfigs[i];
			Server server = new Server();
			server.restoreFromMap(serverMap);
			servers.put(server.getName(), server);
		}
	}
}
