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
package org.eclipse.php.internal.server.core.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.php.internal.server.core.Activator;
import org.eclipse.php.internal.server.core.Logger;
import org.eclipse.php.internal.server.core.Server;
import org.osgi.service.prefs.BackingStoreException;

/**
 * A manager for PHP Servers.
 * 
 * @author shalom
 */
public class ServersManager implements PropertyChangeListener, IAdaptable {

	private class ServersUpgrade extends Job {

		private boolean upgraded = true;

		private ServersUpgrade() {
			super(Messages.ServersManager_Upgrading_server_configurations);
		}

		@SuppressWarnings("unchecked")
		void check(Map<String, Object> serverMap) {
			Map<String, String> attributes = (Map<String, String>) serverMap
					.get(Server.SERVER_ELEMENT);
			if (!attributes.containsKey(Server.UNIQUE_ID))
				upgraded = false;
		}

		void perform() {
			if (!upgraded)
				schedule();
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			monitor.beginTask(
					Messages.ServersManager_Saving_upgraded_configurations,
					IProgressMonitor.UNKNOWN);
			save();
			monitor.done();
			return Status.OK_STATUS;
		}

	}

	/** Servers preferences key */
	public static final String SERVERS_PREFERENCES_KEY = "phpServers"; //$NON-NLS-1$
	/** Default server name preferences key */
	public static final String DEFAULT_SERVER_PREFERENCES_KEY = "defaultPHPServer"; //$NON-NLS-1$

	// defaultServersMap holds a mapping between an IProject to a Server.
	// We take advantage of the NULL wrapping to indicate that a null values
	// will also
	// be mapped to a server (the workspace server).
	private Map<IProject, Server> defaultServersMap = new HashMap<IProject, Server>();
	// Holds a server name to Server instance mapping.
	private HashMap<String, Server> servers;
	private List<IServersManagerListener> listeners;

	private static ServersManager instance;
	private static final String NODE_QUALIFIER = Activator.PLUGIN_ID
			+ ".phpServersPrefs"; //$NON-NLS-1$
	private static final String BASE_URL = "http://localhost"; //$NON-NLS-1$
	public static final String Default_Server_Name = "Default PHP Web Server"; //$NON-NLS-1$

	public static ServersManager getInstance() {
		if (instance == null) {
			instance = new ServersManager();
		}
		return instance;
	}

	private ServersManager() {
		servers = new HashMap<String, Server>();
		listeners = new ArrayList<IServersManagerListener>();
		loadServers();
	}

	/**
	 * Adds an IServersManagerListener.
	 * 
	 * @param listener
	 */
	public static void addManagerListener(IServersManagerListener listener) {
		List<IServersManagerListener> listeners = getInstance().listeners;
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
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
	 * Adds a Server to the manager. If a server with the same name exists in
	 * the manager, the existing server will be overidden with the new one and
	 * event notifications will be fired for the removal and for the addition.
	 * Note: The added server is not saved into the preferences until the
	 * {@link #save()} is called.
	 * 
	 * @param server
	 *            A Server
	 * @see #save()
	 */
	public static void addServer(Server server) {
		if (server == null) {
			return;
		}
		ServersManager manager = getInstance();
		manager.servers.put(server.getName(), server);
		ServerManagerEvent event = new ServerManagerEvent(
				ServerManagerEvent.MANAGER_EVENT_ADDED, server);
		manager.fireEvent(event);
		// Register the manager as a Server lister to get nofitications about
		// attribute changes.
		server.addPropertyChangeListener(manager);
	}

	/**
	 * Removes a Server from the manager. In case that that given server is set
	 * to be the default server for a project, the project will be set with the
	 * workspace default server.
	 * 
	 * @param serverName
	 *            The name of the server.
	 * @return The removed Server; null if non was found to be removed.
	 */
	public static Server removeServer(String serverName) {
		// Do it the long way...
		// Check if the removed server is the workspace default, and if so,
		// replace the default to the
		// first in the list.
		ServersManager manager = ServersManager.getInstance();
		Server removedServer = (Server) manager.servers.remove(serverName);
		Server workspaceDefault = getDefaultServer(null);
		// if (workspaceDefault == null) {
		// // Should not happen
		// Logger.log(IStatus.ERROR,
		// "There is no defined default server for the workspace.");
		// return null;
		// }
		if (removedServer == null) {
			// if the name is not existing, just quit.
			return null;
		}

		if (workspaceDefault == removedServer) {
			// If the workspace default server is the same as the one we wish to
			// remove,
			// we should replace it.
			Server[] servers = getServers();
			if (servers.length > 0) {
				workspaceDefault = servers[0];
				setDefaultServer(null, workspaceDefault);
			}
		}

		// Check that if any one of the mapped projects holds a reference to the
		// removed server.
		// If so, replace it with the new default server.
		Object[] keys = manager.defaultServersMap.keySet().toArray();
		for (Object element : keys) {
			if (removedServer == manager.defaultServersMap.get(element)) {
				setDefaultServer((IProject) element, workspaceDefault);
			}
		}

		// Fire the event for the removal
		removedServer.removePropertyChangeListener(manager);
		ServerManagerEvent event = new ServerManagerEvent(
				ServerManagerEvent.MANAGER_EVENT_REMOVED, removedServer);
		manager.fireEvent(event);
		return removedServer;
	}

	/**
	 * Returns a Server from the manager.
	 * 
	 * @param serverName
	 *            The name of the server.
	 * @return The Server; null if non was found.
	 */
	public static Server getServer(String serverName) {
		ServersManager manager = getInstance();
		return (Server) manager.servers.get(serverName);
	}

	public static Server getServer(Server oldServer) {
		ServersManager manager = getInstance();
		for (Iterator<Server> iterator = manager.servers.values().iterator(); iterator
				.hasNext();) {
			Server server = iterator.next();
			if (server.getBaseURL().equals(oldServer.getBaseURL())) {
				return server;
			}
		}
		return oldServer;
	}

	/**
	 * Finds and returns a server with given unique ID.
	 * 
	 * @param serverId
	 * @return server with given unique ID
	 */
	public static Server findServer(String serverId) {
		ServersManager manager = getInstance();
		for (Iterator<Server> iterator = manager.servers.values().iterator(); iterator
				.hasNext();) {
			Server server = iterator.next();
			if (server.getUniqueId().equals(serverId)) {
				return server;
			}
		}
		return null;
	}

	/**
	 * Tries to find and return a server that corresponds to given URL. If
	 * perfect match flag is set to <code>false</code> then only host address is
	 * being used to find the server, otherwise a port and protocol are being
	 * taken into account to find appropriate match.
	 * 
	 * @param url
	 * @param perfectMatch
	 * @return server that corresponds to given URL
	 */
	public static Server findServer(URL url, boolean perfectMatch) {
		String urlHostAddress = url.getHost();
		try {
			InetAddress urlHostInetAddress = InetAddress
					.getByName(urlHostAddress);
			// Resolve it to IP address
			urlHostAddress = urlHostInetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			// may not exists - ignore
		}
		for (Server server : getServers()) {
			URL serverURL;
			try {
				serverURL = new URL(server.getBaseURL());
			} catch (MalformedURLException ex) {
				// Should not happen
				continue;
			}
			String serverHost = serverURL.getHost();
			try {
				InetAddress serverHostAddress = InetAddress
						.getByName(serverHost);
				if (urlHostAddress.equals(serverHostAddress.getHostAddress())) {
					if (!perfectMatch)
						return server;
					if (url.getPort() == serverURL.getPort()
							&& url.getProtocol()
									.equals(serverURL.getProtocol()))
						return server;
				}
			} catch (UnknownHostException e) {
				// ignore
			}
		}
		return null;
	}

	/**
	 * Returns all the Servers that are managed by this manager.
	 * 
	 * @return An array of Servers.
	 */
	public static Server[] getServers() {
		ServersManager manager = getInstance();
		Collection<Server> values = manager.servers.values();
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
			// In case that the project is not null, check that we have
			// project-specific settings for it.
			// Otherwise, map it to the workspace default server.
			IScopeContext[] preferenceScopes = createPreferenceScopes(project);
			String projectSpecificServer = preferenceScopes[0].getNode(
					NODE_QUALIFIER).get(DEFAULT_SERVER_PREFERENCES_KEY,
					(String) null);
			if (projectSpecificServer == null) {
				// We do not have a project specific setting for this project.
				// Map it to the default workspace server.
				manager.defaultServersMap.put(project,
						manager.defaultServersMap.get(null));
				server = (Server) manager.defaultServersMap.get(null);
			}
		}
		// If the server was no found in our hash, try to load it from the
		// preferences.
		// This part of code should only happen one time when the first call for
		// the
		// getDefaultServer. Once it's done, there is no reason to re-load the
		// servers definitions
		// from the preferences (XML).
		if (server == null) {
			String serverName = null;
			IEclipsePreferences preferences = InstanceScope.INSTANCE
					.getNode(Activator.PLUGIN_ID);
			if (project == null) {
				// Get the default workspace server.
				serverName = preferences.get(DEFAULT_SERVER_PREFERENCES_KEY,
						(String) null);
			} else {
				// Get the projects' default server
				IScopeContext[] preferenceScopes = createPreferenceScopes(project);
				serverName = preferenceScopes[0].getNode(NODE_QUALIFIER).get(
						DEFAULT_SERVER_PREFERENCES_KEY, (String) null);
				if (serverName == null) {
					// Take the workspace Server and make it the project's
					// default server
					serverName = preferences.get(
							DEFAULT_SERVER_PREFERENCES_KEY, (String) null);
				}
			}
			if (serverName != null && !"".equals(serverName)) { //$NON-NLS-1$
				server = (Server) manager.servers.get(serverName);
				// Map this server as the default for the project (if not null)
				// or for the workspace (when the project is null).
				manager.defaultServersMap.put(project, server);
			} else {
				// Create a default server and hook it as a workspace and the
				// project server (can be the same).
				try {
					server = createServer(Default_Server_Name, BASE_URL);

				} catch (MalformedURLException e) {
					// safe server creation
				}
				manager.defaultServersMap.put(null, server);
				manager.defaultServersMap.put(project, server);
				manager.innerSaveDefaultServer(project, server);
				ServersManager.save();
			}
		}

		// fixed bug 197579 - in case of no default server mark the first server
		// as default
		if (server == null) {
			if (manager.servers.size() > 0) {
				server = (Server) manager.servers.values().iterator().next();
				setDefaultServer(null, server);
			}
		}
		return server;
	}

	/**
	 * Sets the default debug server. In case that the given project is null,
	 * the setting if for the workspace. In case that the given server is null,
	 * the preferences value stored for the given project will be removed.
	 * 
	 * @param project
	 *            A project to assign to a default server.
	 * @param server
	 *            A default server for the given project.
	 */
	public static void setDefaultServer(IProject project, Server server) {
		ServersManager manager = getInstance();
		// Get the default server for the given project.
		// In case that we need to set a new server for the project, make sure
		// we save it as well.
		Server defaultProjectServer = (Server) manager.defaultServersMap
				.get(project);
		if (server != defaultProjectServer) {
			manager.defaultServersMap.put(project, server);
			manager.innerSaveDefaultServer(project, server);
		}
	}

	/**
	 * Sets the default debug server. In case that the given project is null,
	 * the setting if for the workspace.
	 * 
	 * @param project
	 *            A project to assign to a default server.
	 * @param serverName
	 *            A default server name for the given project.
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
	 * @throws MalformedURLException
	 */
	public static Server createServer(String name, String baseURL)
			throws MalformedURLException {
		Server server = new Server(name, "localhost", baseURL, ""); //$NON-NLS-1$ //$NON-NLS-2$
		server.setDebuggerId("org.eclipse.php.debug.core.zendDebugger"); //$NON-NLS-1$
		server = ServersManager.getServer(server);
		addServer(server);
		return server;
	}

	/**
	 * Save the listed servers into the preferences.
	 */
	public static void save() {
		IXMLPreferencesStorable[] servers = getServers();
		IEclipsePreferences preferences = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		XMLPreferencesWriter.write(preferences, SERVERS_PREFERENCES_KEY,
				Arrays.asList(servers));
	}

	/**
	 * Finds the local server corresponding to the given project
	 * 
	 * @param project
	 */
	public final static Server getLocalServer(IProject project) {
		for (Server server : getServers()) {
			final String documentRoot = server.getDocumentRoot();
			if (documentRoot != null && documentRoot.length() > 0) {
				final Path path = new Path(documentRoot);
				final IPath fullPath = project.getLocation();
				if (fullPath != null && path.isPrefixOf(fullPath)) {
					return server;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		// Listen to any attribute change in the Servers
		Server server = (Server) evt.getSource();
		String oldValue = (String) evt.getOldValue();
		String newValue = (String) evt.getNewValue();
		// Replace renamed server
		if (evt.getPropertyName().equals(Server.NAME)) {
			getInstance().servers.remove(oldValue);
			getInstance().servers.put(newValue, server);
		}
		ServerManagerEvent event = new ServerManagerEvent(
				ServerManagerEvent.MANAGER_EVENT_MODIFIED, server,
				evt.getPropertyName(), oldValue, newValue);
		fireEvent(event);
	}

	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	/**
	 * Fires a ServerManagerEvent to all the registered
	 * IServersManagerListeners.
	 * 
	 * @param event
	 */
	public void fireEvent(ServerManagerEvent event) {
		IServersManagerListener[] allListeners = new IServersManagerListener[listeners
				.size()];
		listeners.toArray(allListeners);
		if (event.getType() == ServerManagerEvent.MANAGER_EVENT_ADDED) {
			fireAddEvent(event, allListeners);
		} else if (event.getType() == ServerManagerEvent.MANAGER_EVENT_REMOVED) {
			fireRemoveEvent(event, allListeners);
		} else if (event.getType() == ServerManagerEvent.MANAGER_EVENT_MODIFIED) {
			fireModifiedEvent(event, allListeners);
		}
	}

	// Creates a preferences scope for the given project.
	// This scope will be used to search for preferences values.
	private static IScopeContext[] createPreferenceScopes(IProject project) {
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project),
					InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		}
		return new IScopeContext[] { InstanceScope.INSTANCE,
				DefaultScope.INSTANCE };
	}

	private void innerSaveDefaultServer(IProject project, Server server) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		if (project == null && server != null) {
			preferences.put(DEFAULT_SERVER_PREFERENCES_KEY, server.getName());
			try {
				preferences.flush();
			} catch (BackingStoreException e) {
				Logger.logException(e);
			}
		} else if (project != null) {
			String defaultWorkspaceServer = preferences.get(
					DEFAULT_SERVER_PREFERENCES_KEY, (String) null);
			IScopeContext[] scopeContexts = createPreferenceScopes(project);
			IEclipsePreferences prefsNode = scopeContexts[0]
					.getNode(NODE_QUALIFIER);
			if (server != null
					&& !defaultWorkspaceServer.equals(server.getName())) {
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
		// Read all the configurations of the servers from the preferences and
		// place them into the
		// servers hash (map name to Server instance).
		IEclipsePreferences preferences = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		List<Map<String, Object>> serversConfigs = XMLPreferencesReader.read(
				preferences, SERVERS_PREFERENCES_KEY, true);
		ServersUpgrade upgrader = new ServersUpgrade();
		// Then we create the servers from their configurations...
		for (Map<String, Object> serverMap : serversConfigs) {
			Server server = new Server();
			server.restoreFromMap(serverMap);
			String serverName = server.getName();
			servers.put(serverName, server);
			// Register the manager as a Server lister to get nofitications
			// about attribute changes.
			server.addPropertyChangeListener(this);
			upgrader.check(serverMap);
		}
		upgrader.perform();
	}

	private void fireAddEvent(ServerManagerEvent event,
			IServersManagerListener[] allListeners) {
		for (IServersManagerListener element : allListeners) {
			element.serverAdded(event);
		}
	}

	private void fireRemoveEvent(ServerManagerEvent event,
			IServersManagerListener[] allListeners) {
		for (IServersManagerListener element : allListeners) {
			element.serverRemoved(event);
		}
	}

	private void fireModifiedEvent(ServerManagerEvent event,
			IServersManagerListener[] allListeners) {
		for (IServersManagerListener element : allListeners) {
			element.serverModified(event);
		}
	}

}
