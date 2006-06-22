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
package org.eclipse.php.server.core.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.php.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.php.server.core.Activator;
import org.eclipse.php.server.core.Server;

/**
 * A manager for PHP Servers.
 * 
 * @author shalom
 */
public class ServersManager implements PropertyChangeListener {

	private ArrayList listeners;
	private static ServersManager instance;
	private static final String PREFERENCES_KEY = "phpServers";

	public static ServersManager getInstance() {
		if (instance == null) {
			instance = new ServersManager();
		}
		return instance;
	}

	private HashMap servers;

	private ServersManager() {
		servers = new HashMap();
		listeners = new ArrayList();
		loadServers();
	}

	/**
	 * Adds an IServersManagerListener.
	 * 
	 * @param listener
	 */
	public static void addManagerListener(IServersManagerListener listener) {
		getInstance().listeners.add(listener);
	}

	/**
	 * Removes an IServersManagerListener.
	 * 
	 * @param listener
	 */
	public static void removeManagerListener(IServersManagerListener listener) {
		getInstance().listeners.remove(listener);
	}

	/**
	 * Adds a Server to the manager.
	 * If a server with the same name exists in the manager, the existing server will be overidden with 
	 * the new one and event notifications will be fired for the removal and for the addition.
	 * 
	 * @param server A Server
	 */
	public static void addServer(Server server) {
		if (server == null) {
			return;
		}
		ServersManager manager = getInstance();
		Server oldValue = (Server) manager.servers.put(server.getName(), server);
		if (oldValue != null) {
			oldValue.removePropertyChangeListener(manager);
			ServerManagerEvent event = new ServerManagerEvent(ServerManagerEvent.MANAGER_EVENT_REMOVED, oldValue);
			manager.fireEvent(event);
		}
		ServerManagerEvent event = new ServerManagerEvent(ServerManagerEvent.MANAGER_EVENT_ADDED, server);
		manager.fireEvent(event);

		// Register the manager as a Server lister to get nofitications about attribute changes.
		server.addPropertyChangeListener(manager);
	}

	/**
	 * Removes a Server from the manager.
	 * 
	 * @param serverName The name of the server.
	 * @return The removed Server; null if non was found to be removed.
	 */
	public static Server removeServer(String serverName) {
		if (serverName == null) {
			return null;
		}
		ServersManager manager = getInstance();
		Server removed = (Server) manager.servers.remove(serverName);
		if (removed != null) {
			removed.removePropertyChangeListener(manager);
			ServerManagerEvent event = new ServerManagerEvent(ServerManagerEvent.MANAGER_EVENT_REMOVED, removed);
			manager.fireEvent(event);
		}
		return removed;
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
			// Register the manager as a Server lister to get nofitications about attribute changes.
			server.addPropertyChangeListener(this);
		}
	}

	/**
	 * Fires a ServerManagerEvent to all the registered IServersManagerListeners.
	 * 
	 * @param event
	 */
	public void fireEvent(ServerManagerEvent event) {
		IServersManagerListener[] allListeners = new IServersManagerListener[listeners.size()];
		listeners.toArray(allListeners);
		if (event.getType() == ServerManagerEvent.MANAGER_EVENT_ADDED) {
			fireAddEvent(event, allListeners);
		} else if (event.getType() == ServerManagerEvent.MANAGER_EVENT_REMOVED) {
			fireRemoveEvent(event, allListeners);
		} else if (event.getType() == ServerManagerEvent.MANAGER_EVENT_MODIFIED) {
			fireModifiedEvent(event, allListeners);
		}
	}

	private void fireAddEvent(ServerManagerEvent event, IServersManagerListener[] allListeners) {
		for (int i = 0; i < allListeners.length; i++) {
			allListeners[i].serverAdded(event);
		}
	}

	private void fireRemoveEvent(ServerManagerEvent event, IServersManagerListener[] allListeners) {
		for (int i = 0; i < allListeners.length; i++) {
			allListeners[i].serverRemoved(event);
		}
	}

	private void fireModifiedEvent(ServerManagerEvent event, IServersManagerListener[] allListeners) {
		for (int i = 0; i < allListeners.length; i++) {
			allListeners[i].serverModified(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		// Listen to any attribute change in the Servers
		Server server = (Server) evt.getSource();
		String oldValue = (String) evt.getOldValue();
		String newValue = (String) evt.getNewValue();
		ServerManagerEvent event = new ServerManagerEvent(ServerManagerEvent.MANAGER_EVENT_MODIFIED, server, evt.getPropertyName(), oldValue, newValue);
		fireEvent(event);
	}
}
