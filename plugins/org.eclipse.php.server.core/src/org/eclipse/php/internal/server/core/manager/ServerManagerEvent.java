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

import java.util.EventObject;

import org.eclipse.php.internal.server.core.Server;

/**
 * A server manager event is triggered when a server is added, removed or
 * modified.
 * 
 * @author shalom
 */
public class ServerManagerEvent extends EventObject {

	/** Server added event type */
	public static final int MANAGER_EVENT_ADDED = 0;
	/** Server removed event type */
	public static final int MANAGER_EVENT_REMOVED = 2;
	/** Server modified event type */
	public static final int MANAGER_EVENT_MODIFIED = 4;

	private int type;
	private Server server;
	private String attributeKey;
	private String oldAttribute;
	private String newAttribute;

	/**
	 * ServerManagerEvent constructor.
	 * 
	 * @param type
	 * @param server
	 */
	public ServerManagerEvent(int type, Server server) {
		super(ServersManager.getInstance());
		this.type = type;
		this.server = server;
	}

	/**
	 * ServerManagerEvent constructor.
	 * 
	 * @param type
	 * @param server
	 * @param modifiedAttribute
	 */
	public ServerManagerEvent(int type, Server server, String attributeKey,
			String oldAttribute, String newAttribute) {
		this(type, server);
		this.attributeKey = attributeKey;
		this.oldAttribute = oldAttribute;
		this.newAttribute = newAttribute;
	}

	/**
	 * Returns the type of this event.
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the effected Server.
	 * 
	 * @return The Server that was removed, added or modified.
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Returns the modified attribute in case this event indicates a Server
	 * modification. In any other event case, the returned value will be null.
	 * 
	 * @return The modified attribute
	 */
	public String getModifiedAttributeKey() {
		return attributeKey;
	}

	/**
	 * Returns the old attribute value that was modified.
	 * 
	 * @return
	 */
	public String getOldAttributeValue() {
		return oldAttribute;
	}

	/**
	 * Returns the new attribute value that was modified. Return null if this
	 * event does not indicate a Server modification or when the notification is
	 * for modification but the attribute was removed.
	 * 
	 * @return
	 */
	public String getNewAttributeValue() {
		return newAttribute;
	}
}
