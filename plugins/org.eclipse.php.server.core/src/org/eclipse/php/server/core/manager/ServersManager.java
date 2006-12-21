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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.php.server.core.Activator;
import org.eclipse.php.server.core.Logger;
import org.eclipse.php.server.core.Server;
import org.osgi.service.prefs.BackingStoreException;

/**
 * A manager for PHP Servers.
 * 
 * @author shalom
 */
public class ServersManager implements PropertyChangeListener {

	/**Servers preferences key*/
	public static final String SERVERS_PREFERENCES_KEY = "phpServers";
	/**Default server name preferences key*/
	public static final String DEFAULT_SERVER_PREFERENCES_KEY = "defaultPHPServer";

	// defaultServersMap holds a mapping between an IProject to a Server.
	// We take advantage of the NULL wrapping to indicate that a null values will also 
	// be mapped to a server (the workspace server).
	private HashMap defaultServersMap = new HashMap();
	// Holds a server name to Server instance mapping.
	private HashMap servers;
	private ArrayList listeners;

	private static ServersManager instance;
	private static final String NODE_QUALIFIER = Activator.PLUGIN_ID + ".phpServersPrefs";
	private static final String BASE_URL = "http://localhost";
	public static final String Default_Server_Name = "Default PHP Web Server";

	public static ServersManager getInstance() {
		if (instance == null) {
			instance = new ServersManager();
		}
		return instance;
	}

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
	 * Note: The added server is not saved into the preferences until the {@link #save()} is called.
	 * 
	 * @param server A Server
	 * @see #save()
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
	 * In case that that given server is set to be the default server for a project, 
	 * the project will be set with the workspace default server.
	 * 
	 * @param serverName The name of the server.
	 * @return The removed Server; null if non was found to be removed.
	 */
	public static Server removeServer(String serverName) {
		// Do it the long way...
		// Check if the removed server is the workspace default, and if so, replace the default to the 
		// first in the list.
		ServersManager manager = ServersManager.getInstance();
		Server removedServer = (Server) manager.servers.remove(serverName);
		Server workspaceDefault = getDefaultServer(null);
		if (workspaceDefault == null) {
			// Should not happen
			Logger.log(IStatus.ERROR, "There is no defined default server for the workspace.");
			return null;
		}
		if (removedServer == null) {
			// Should not happen
			Logger.log(IStatus.ERROR, "The requested server could not be found, thus, will not be removed.");
			return null;
		}

		if (workspaceDefault == removedServer) {
			// If the workspace default server is the same as the one we wish to remove, 
			// we should replace it.
			Server[] servers = getServers();
			if (servers.length > 0) {
				workspaceDefault = servers[0];
				setDefaultServer(null, workspaceDefault);
			}
		}

		// Check that if any one of the mapped projects holds a reference to the removed server.
		// If so, replace it with the new default server.
		Object[] keys = manager.defaultServersMap.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			if (removedServer == manager.defaultServersMap.get(keys[i])) {
				setDefaultServer((IProject) keys[i], workspaceDefault);
			}
		}

		if (removedServer != null) {
			// Fire the event for the removal
			removedServer.removePropertyChangeListener(manager);
			ServerManagerEvent event = new ServerManagerEvent(ServerManagerEvent.MANAGER_EVENT_REMOVED, removedServer);
			manager.fireEvent(event);
		}
		return removedServer;
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
	 * Returns the dafault debug server.
	 * 
	 * @return
	 */
	public static Server getDefaultServer(IProject project) {
		ServersManager manager = getInstance();
		// First, try to get it from the memory.
		Server server = (Server) manager.defaultServersMap.get(project);
		if (project != null) {
			// In case that the project is not null, check that we have project-specific settings for it.
			// Otherwise, map it to the workspace default server.
			IScopeContext[] preferenceScopes = createPreferenceScopes(project);
			String projectSpecificServer = preferenceScopes[0].getNode(NODE_QUALIFIER).get(DEFAULT_SERVER_PREFERENCES_KEY, (String)null);
			if (projectSpecificServer == null) {
				// We do not have a project specific setting for this project. Map it to the default workspace server.
				manager.defaultServersMap.put(project, manager.defaultServersMap.get(null));
				server = (Server) manager.defaultServersMap.get(null);
			}
		}
		// If the server was no found in our hash, try to load it from the preferences.
		// This part of code should only happen one time when the first call for the 
		// getDefaultServer. Once it's done, there is no reason to re-load the servers definitions
		// from the preferences (XML).
		if (server == null) {
			String serverName = null;
			if (project == null) {
				// Get the default workspace server.
				Preferences prefs = Activator.getDefault().getPluginPreferences();
				serverName = prefs.getString(DEFAULT_SERVER_PREFERENCES_KEY);
			} else {
				// Get the projects' default server
				IScopeContext[] preferenceScopes = createPreferenceScopes(project);
				serverName = preferenceScopes[0].getNode(NODE_QUALIFIER).get(DEFAULT_SERVER_PREFERENCES_KEY, (String)null);
				if (serverName == null) {
					// Take the workspace Server and make it the project's default server
					Preferences prefs = Activator.getDefault().getPluginPreferences();
					serverName = prefs.getString(DEFAULT_SERVER_PREFERENCES_KEY);
				}
			}
			if (serverName != null && !"".equals(serverName)) {
				server = (Server) manager.servers.get(serverName);
				// Map this server as the default for the project (if not null) or for the workspace (when the project is null).
				manager.defaultServersMap.put(project, server);
			} else {
				// Create a default server and hook it as a workspace and the project server (can be the same).
				server = createServer(Default_Server_Name, BASE_URL);
				manager.defaultServersMap.put(null, server);
				manager.defaultServersMap.put(project, server);
				manager.save();
			}
		} 
		return server;
	}

	// Creates a preferences scope for the given project.
	// This scope will be used to search for preferences values.
	private static IScopeContext[] createPreferenceScopes(IProject project) {
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project), new InstanceScope(), new DefaultScope() };
		}
		return new IScopeContext[] { new InstanceScope(), new DefaultScope() };
	}

	/**
	 * Sets the default debug server.
	 * In case that the given project is null, the setting if for the workspace.
	 * In case that the given server is null, the preferences value stored for the given project
	 * will be removed.
	 * 
	 * @param project A project to assign to a default server.
	 * @param server A default server for the given project.
	 */
	public static void setDefaultServer(IProject project, Server server) {
		ServersManager manager = getInstance();
		// Get the default server for the given project.
		// In case that we need to set a new server for the project, make sure we save it as well.
		Server defaultProjectServer = (Server) manager.defaultServersMap.get(project);
		if (server != defaultProjectServer) {
			manager.defaultServersMap.put(project, server);
			manager.innerSaveDefaultServer(project, server);
		}
	}

	/**
	 * Sets the default debug server.
	 * In case that the given project is null, the setting if for the workspace.
	 * 
	 * @param project A project to assign to a default server.
	 * @param serverName A default server name for the given project.
	 */
	public static void setDefaultServer(IProject project, String serverName) {
		ServersManager manager = getInstance();
		Server server = (Server) manager.servers.get(serverName);
		setDefaultServer(project, server);
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
	 * Save the listed servers into the preferences.
	 */
	public static void save() {
		Server[] servers = getServers();
		XMLPreferencesWriter.write(Activator.getDefault().getPluginPreferences(), SERVERS_PREFERENCES_KEY, servers);
		Activator.getDefault().savePluginPreferences();
	}

	private void innerSaveDefaultServer(IProject project, Server server) {
		Preferences prefs = Activator.getDefault().getPluginPreferences();
		if (project == null && server != null) {
			prefs.setValue(DEFAULT_SERVER_PREFERENCES_KEY, server.getName());
			Activator.getDefault().savePluginPreferences();
		} else if (project != null) {
			String defaultWorkspaceServer = prefs.getString(DEFAULT_SERVER_PREFERENCES_KEY);
			IScopeContext[] scopeContexts = createPreferenceScopes(project);
			IEclipsePreferences prefsNode = scopeContexts[0].getNode(NODE_QUALIFIER);
			if (server != null && !defaultWorkspaceServer.equals(server.getName())) {
				prefsNode.put(DEFAULT_SERVER_PREFERENCES_KEY, server.getName());
			} else {
				prefsNode.remove(DEFAULT_SERVER_PREFERENCES_KEY);
			}
			try {
				prefsNode.flush();
			} catch (BackingStoreException e) {
				Logger.logException(e);
			}
		}
	}

	// Loads the servers from the preferences store.
	private void loadServers() {
		// Read all the configurations of the servers from the preferences and place them into the 
		// servers hash (map name to Server instance).
		HashMap[] serversConfigs = XMLPreferencesReader.read(Activator.getDefault().getPluginPreferences(), SERVERS_PREFERENCES_KEY);
		// Then we create the servers from their configurations...
		for (int i = 0; i < serversConfigs.length; i++) {
			HashMap serverMap = serversConfigs[i];
			Server server = new Server();
			server.restoreFromMap(serverMap);
			String serverName = server.getName();
			servers.put(serverName, server);
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
