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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * 
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public enum DBGpProxyHandlersManager {

	INSTANCE;

	private final Map<String, DBGpProxyHandler> handlers = new HashMap<String, DBGpProxyHandler>();

	public synchronized void startup() {
		// Cache all possible proxy handlers
		for (PHPexeItem owner : PHPexes.getInstance().getAllItems()) {
			if (XDebugDebuggerConfiguration.ID.equals(owner.getDebuggerID())) {
				registerHandler(owner.getUniqueId());
			}
		}
		for (Server owner : ServersManager.getServers()) {
			if (XDebugDebuggerConfiguration.ID.equals(owner.getDebuggerId())) {
				registerHandler(owner.getUniqueId());
			}
		}
	}

	public synchronized void shutdown() {
		for (DBGpProxyHandler handler : handlers.values()) {
			handler.unregister();
			handler.dispose();
		}
	}

	public DBGpProxyHandler getHandler(String ownerId) {
		return handlers.get(ownerId);
	}

	public boolean registerHandler(String ownerId) {
		DBGpProxyHandler handler = handlers.get(ownerId);
		if (handler == null) {
			handler = new DBGpProxyHandler(ownerId);
			handler.configure();
			handlers.put(ownerId, handler);
			return false;
		}
		return true;
	}

}
