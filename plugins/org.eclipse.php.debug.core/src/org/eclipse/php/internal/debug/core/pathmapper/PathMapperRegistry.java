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
package org.eclipse.php.internal.debug.core.pathmapper;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.IPHPExesListener;
import org.eclipse.php.internal.debug.core.preferences.PHPExesEvent;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.IServersManagerListener;
import org.eclipse.php.internal.server.core.manager.ServerManagerEvent;
import org.eclipse.php.internal.server.core.manager.ServersManager;

@SuppressWarnings("restriction")
public class PathMapperRegistry implements IXMLPreferencesStorable {

	private class MapperOwnerListener implements IServersManagerListener, IPHPExesListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.php.internal.server.core.manager.IServersManagerListener#
		 * serverAdded(org.eclipse.php.internal.server.core.manager.
		 * ServerManagerEvent)
		 */
		public void serverAdded(ServerManagerEvent event) {
			if (!serverPathMapper.containsKey(event.getServer())) {
				serverPathMapper.put(event.getServer(), new PathMapper());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.php.internal.server.core.manager.IServersManagerListener#
		 * serverModified(org.eclipse.php.internal.server.core.manager.
		 * ServerManagerEvent)
		 */
		public void serverModified(ServerManagerEvent event) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.php.internal.server.core.manager.IServersManagerListener#
		 * serverRemoved(org.eclipse.php.internal.server.core.manager.
		 * ServerManagerEvent)
		 */
		public void serverRemoved(ServerManagerEvent event) {
			serverPathMapper.remove(event.getServer());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.php.internal.debug.core.preferences.IPHPExesListener#
		 * phpExeAdded(org.eclipse.php.internal.debug.core.preferences.
		 * PHPExesEvent)
		 */
		public void phpExeAdded(PHPExesEvent event) {
			if (!phpExePathMapper.containsKey(event.getPHPExeItem())) {
				phpExePathMapper.put(event.getPHPExeItem(), new PathMapper());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.php.internal.debug.core.preferences.IPHPExesListener#
		 * phpExeRemoved(org.eclipse.php.internal.debug.core.preferences.
		 * PHPExesEvent)
		 */
		public void phpExeRemoved(PHPExesEvent event) {
			phpExePathMapper.remove(event.getPHPExeItem());
		}

	}

	private static final String PATH_MAPPER_PREF_KEY = PHPDebugPlugin.getID() + ".pathMapper"; //$NON-NLS-1$

	private static PathMapperRegistry instance;
	private final Map<Server, PathMapper> serverPathMapper;
	private final Map<PHPexeItem, PathMapper> phpExePathMapper;
	private final Map<ILaunchConfiguration, PathMapper> launchConfigPathMapper;
	private final MapperOwnerListener ownerListener;

	private PathMapperRegistry() {
		// Make it weak to remove entry after dropping the launch configuration
		launchConfigPathMapper = new WeakHashMap<ILaunchConfiguration, PathMapper>();
		serverPathMapper = new HashMap<Server, PathMapper>();
		phpExePathMapper = new HashMap<PHPexeItem, PathMapper>();
		ownerListener = new MapperOwnerListener();
		// Load persistent mappings from preferences
		List<Map<String, Object>> elements = XMLPreferencesReader
				.read(InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID), PATH_MAPPER_PREF_KEY, true);
		if (elements.size() == 1) {
			restoreFromMap(elements.get(0));
		}
	}

	/**
	 * Returns singleton instance for this registry.
	 * 
	 * @return singleton instance for this registry
	 */
	public synchronized static PathMapperRegistry getInstance() {
		if (instance == null) {
			instance = new PathMapperRegistry();
		}
		return instance;
	}

	/**
	 * Returns path mapper that corresponds to the given PHPexe item
	 * 
	 * @param phpExe
	 *            PHPExe item
	 * @return path mapper, or <code>null</code> if there's no one
	 */
	public static PathMapper getByPHPExe(PHPexeItem phpExe) {
		PathMapper result = getInstance().phpExePathMapper.get(phpExe);
		if (result == null) {
			result = new PathMapper();
			getInstance().phpExePathMapper.put(phpExe, result);
			PHPexes.getInstance().addPHPExesListener(getInstance().ownerListener);
		}
		return result;
	}

	/**
	 * Return path mapper which corresponding to the given Server instance
	 * 
	 * @param server
	 *            Server instance
	 * @return path mapper, or <code>null</code> if there's no one
	 */
	public static PathMapper getByServer(Server server) {
		PathMapper result = getInstance().serverPathMapper.get(server);
		if (result == null) {
			result = new PathMapper();
			getInstance().serverPathMapper.put(server, result);
			/*
			 * Create the link to servers manager here in order not to create
			 * tightly coupled relationship.
			 */
			ServersManager.addManagerListener(getInstance().ownerListener);
		}
		return result;
	}

	/**
	 * Returns path mapper associated with the given launch configuration
	 * 
	 * @param launchConfiguration
	 *            Launch configuration
	 * @return path mapper
	 */
	public static PathMapper getByLaunchConfiguration(ILaunchConfiguration launchConfiguration) {
		PathMapper pathMapper = null;
		try {
			String serverName = launchConfiguration.getAttribute(Server.NAME, (String) null);
			if (serverName != null) {
				/*
				 * Try to find path mapper with the use of the server that might
				 * be bound to the corresponding launch configuration.
				 */
				pathMapper = getByServer(ServersManager.getServer(serverName));
			} else if (PHPLaunchUtilities.isLaunchConfigurationTypeOf(launchConfiguration,
					IPHPDebugConstants.PHPRemoteLaunchType)) {
				/*
				 * If no server could be found (launch configuration for
				 * externally triggered sessions), create temporary one and bind
				 * it with the launch configuration.
				 */
				pathMapper = getInstance().launchConfigPathMapper.get(launchConfiguration);
				if (pathMapper == null) {
					pathMapper = new PathMapper();
					getInstance().launchConfigPathMapper.put(launchConfiguration, pathMapper);
				}
			}
		} catch (CoreException e) {
			PHPDebugPlugin.log(e);
		}
		return pathMapper;
	}

	/**
	 * Persist settings to preference file.
	 */
	public static void storeToPreferences() {
		XMLPreferencesWriter.write(InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID), PATH_MAPPER_PREF_KEY,
				getInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable#
	 * restoreFromMap(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public synchronized void restoreFromMap(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		serverPathMapper.clear();
		phpExePathMapper.clear();
		map = (Map<String, Object>) map.get("pathMappers"); //$NON-NLS-1$
		if (map == null) {
			return;
		}
		Iterator<String> i = map.keySet().iterator();
		while (i.hasNext()) {
			Map<String, Object> entryMap = (Map<String, Object>) map.get(i.next());
			String serverName = (String) entryMap.get("server"); //$NON-NLS-1$
			String phpExeFile = (String) entryMap.get("phpExe"); //$NON-NLS-1$
			String phpIniFile = (String) entryMap.get("phpIni"); //$NON-NLS-1$
			PathMapper pathMapper = new PathMapper();
			pathMapper.restoreFromMap((Map<String, Object>) entryMap.get("mapper")); //$NON-NLS-1$
			if (serverName != null) {
				Server server = ServersManager.getServer(serverName);
				if (server != null) {
					serverPathMapper.put(server, pathMapper);
				}
			} else if (phpExeFile != null) {
				PHPexeItem phpExeItem = PHPexes.getInstance().getItemForFile(phpExeFile, phpIniFile);
				if (phpExeItem != null) {
					phpExePathMapper.put(phpExeItem, pathMapper);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable#
	 * storeToMap()
	 */
	public synchronized Map<String, Object> storeToMap() {
		Map<String, Object> elements = new HashMap<String, Object>();
		Iterator<?> i = serverPathMapper.keySet().iterator();
		int c = 1;
		while (i.hasNext()) {
			Map<String, Object> entry = new HashMap<String, Object>();
			Server server = (Server) i.next();
			PathMapper pathMapper = serverPathMapper.get(server);
			entry.put("server", server.getName()); //$NON-NLS-1$
			if (pathMapper != null) {
				entry.put("mapper", pathMapper.storeToMap()); //$NON-NLS-1$
			}
			elements.put("item" + (c++), entry); //$NON-NLS-1$
		}
		i = phpExePathMapper.keySet().iterator();
		while (i.hasNext()) {
			Map<String, Object> entry = new HashMap<String, Object>();
			PHPexeItem phpExeItem = (PHPexeItem) i.next();
			PathMapper pathMapper = phpExePathMapper.get(phpExeItem);
			entry.put("phpExe", phpExeItem.getExecutable().toString()); //$NON-NLS-1$
			if (phpExeItem.getINILocation() != null) {
				entry.put("phpIni", phpExeItem.getINILocation().toString()); //$NON-NLS-1$
			}
			entry.put("mapper", pathMapper.storeToMap()); //$NON-NLS-1$
			elements.put("item" + (c++), entry); //$NON-NLS-1$
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("pathMappers", elements); //$NON-NLS-1$
		return root;
	}

}
