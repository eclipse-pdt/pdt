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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.*;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.IServersManagerListener;
import org.eclipse.php.internal.server.core.manager.ServerManagerEvent;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.preferences.util.XMLPreferencesReaderUI;
import org.eclipse.php.internal.ui.preferences.util.XMLPreferencesWriterUI;

public class PathMapperRegistry implements IXMLPreferencesStorable,
		IServersManagerListener, IPHPExesListener {

	private static final String PATH_MAPPER_PREF_KEY = PHPDebugPlugin.getID()
			+ ".pathMapper"; //$NON-NLS-1$

	private static PathMapperRegistry instance;
	private HashMap<Server, PathMapper> serverPathMapper;
	private HashMap<PHPexeItem, PathMapper> phpExePathMapper;

	private PathMapperRegistry() {
		serverPathMapper = new HashMap<Server, PathMapper>();
		phpExePathMapper = new HashMap<PHPexeItem, PathMapper>();
		loadFromPreferences();
	}

	public synchronized static PathMapperRegistry getInstance() {
		if (instance == null) {
			instance = new PathMapperRegistry();
		}
		return instance;
	}

	/**
	 * Return path mapper which corresponding to the given PHPexe item
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
			PHPexes.getInstance().addPHPExesListener(getInstance());
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
			// create the link to servers manager here in order not to create
			// tightly coupled relationship
			ServersManager.addManagerListener(getInstance());
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
	public static PathMapper getByLaunchConfiguration(
			ILaunchConfiguration launchConfiguration) {
		PathMapper pathMapper = null;
		try {
			String serverName = launchConfiguration.getAttribute(Server.NAME,
					(String) null);
			if (serverName != null) {
				pathMapper = getByServer(ServersManager.getServer(serverName));
			}/*
			 * else { String phpExe =
			 * launchConfiguration.getAttribute(PHPCoreConstants
			 * .ATTR_EXECUTABLE_LOCATION, (String) null); String phpIni =
			 * launchConfiguration
			 * .getAttribute(PHPCoreConstants.ATTR_INI_LOCATION, (String) null);
			 * if (phpExe != null) { pathMapper =
			 * getByPHPExe(PHPexes.getInstance().getItemForFile(phpExe,
			 * phpIni)); } }
			 */
		} catch (CoreException e) {
			PHPDebugPlugin.log(e);
		}
		return pathMapper;
	}

	@SuppressWarnings("unchecked")
	public void loadFromPreferences() {
		HashMap[] elements = XMLPreferencesReaderUI.read(
				PHPProjectPreferences.getModelPreferences(),
				PATH_MAPPER_PREF_KEY);
		if (elements.length == 1) {
			restoreFromMap(elements[0]);
		}
	}

	public static void storeToPreferences() {
		XMLPreferencesWriterUI.write(
				PHPProjectPreferences.getModelPreferences(),
				PATH_MAPPER_PREF_KEY, getInstance());
	}

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
			Map<String, Object> entryMap = (Map<String, Object>) map.get(i
					.next());
			String serverName = (String) entryMap.get("server"); //$NON-NLS-1$
			String phpExeFile = (String) entryMap.get("phpExe"); //$NON-NLS-1$
			String phpIniFile = (String) entryMap.get("phpIni"); //$NON-NLS-1$
			PathMapper pathMapper = new PathMapper();
			pathMapper.restoreFromMap((Map<String, Object>) entryMap
					.get("mapper")); //$NON-NLS-1$
			if (serverName != null) {
				Server server = ServersManager.getServer(serverName);
				if (server != null) {
					serverPathMapper.put(server, pathMapper);
				}
			} else if (phpExeFile != null) {
				PHPexeItem phpExeItem = PHPexes.getInstance().getItemForFile(
						phpExeFile, phpIniFile);
				if (phpExeItem != null) {
					phpExePathMapper.put(phpExeItem, pathMapper);
				}
			}
		}
	}

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

	public void serverAdded(ServerManagerEvent event) {
		if (!serverPathMapper.containsKey(event.getServer())) {
			serverPathMapper.put(event.getServer(), new PathMapper());
		}
	}

	public void serverModified(ServerManagerEvent event) {
	}

	public void serverRemoved(ServerManagerEvent event) {
		serverPathMapper.remove(event.getServer());
	}

	public void phpExeAdded(PHPExesEvent event) {
		if (!phpExePathMapper.containsKey(event.getPHPExeItem())) {
			phpExePathMapper.put(event.getPHPExeItem(), new PathMapper());
		}
	}

	public void phpExeRemoved(PHPExesEvent event) {
		phpExePathMapper.remove(event.getPHPExeItem());
	}
}
