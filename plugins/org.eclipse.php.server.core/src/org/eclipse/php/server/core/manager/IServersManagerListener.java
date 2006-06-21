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

/**
 * A server manager listener interfaces for any listener that needs notifications about servers additions, 
 * removals or modifications.
 */
public interface IServersManagerListener {

	/**
	 * Called when a server is added to the ServersManager.
	 * 
	 * @param event
	 */
	public void serverAdded(ServerManagerEvent event);
	
	/**
	 * Called when a server is removed from the ServersManager.
	 * 
	 * @param event
	 */
	public void serverRemoved(ServerManagerEvent event);

	/**
	 * Called when a server is modified.
	 * A modification event will be fired for any attribute change in the server.
	 * 
	 * @param event
	 */
	public void serverModified(ServerManagerEvent event);
}
