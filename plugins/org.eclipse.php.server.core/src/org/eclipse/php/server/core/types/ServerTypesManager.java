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
package org.eclipse.php.server.core.types;

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.types.ServerType;

/**
 * Manager for all available server types.
 */
public class ServerTypesManager {

	private static ServerTypesManager manager;
	private Map<String, IServerType> types;

	private ServerTypesManager() {
	}

	/**
	 * Returns singleton instance.
	 * 
	 * @return singleton instance
	 */
	public static synchronized ServerTypesManager getInstance() {
		if (manager == null) {
			manager = new ServerTypesManager();
			manager.init();
		}
		return manager;
	}

	/**
	 * Gets {@link IServerType} instance base on specified id. If id is
	 * <code>null</code> then return Generic PHP Server type.
	 * 
	 * @param id
	 * @return server type instance
	 */
	public IServerType getType(String id) {
		return id != null ? types.get(id) : types.get(ServerType.GENERIC_PHP_SERVER_ID);
	}

	/**
	 * Finds and returns the type that corresponds to provided server.
	 * 
	 * @param server
	 * @return type that corresponds to provided server
	 */
	public IServerType getType(Server server) {
		return getType(server.getAttribute(IServerType.TYPE, null));
	}

	/**
	 * Returns all available server types.
	 * 
	 * @return all available server types
	 */
	public Collection<IServerType> getAll() {
		return types.values();
	}

	private void init() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.php.server.core.serverType"); //$NON-NLS-1$
		IServerType genericType = null;
		List<IServerType> result = new ArrayList<IServerType>();
		for (IConfigurationElement element : elements) {
			IServerType type = null;
			if ("type".equals(element.getName())) { //$NON-NLS-1$
				type = ServerType.create(element);
			}
			if (type != null) {
				if (type.getId().equals(ServerType.GENERIC_PHP_SERVER_ID)) {
					genericType = type;
				} else {
					result.add(type);
				}
			}
		}
		types = new LinkedHashMap<String, IServerType>();
		for (IServerType type : result) {
			types.put(type.getId(), type);
		}
		// Generic type always at the end
		if (genericType != null) {
			types.put(genericType.getId(), genericType);
		}
	}

}