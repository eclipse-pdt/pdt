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
package org.eclipse.php.internal.server.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Server helper class.
 */
public class ServerHelper {
	protected Map map;

	// property change listeners
	private transient List propertyListeners;
	private Server server;

	public ServerHelper(Server server) {
		this.map = new HashMap();
		this.server = server;
	}

	public void setAttribute(String attributeName, String value) {
		String current = getAttribute(attributeName, (String) null);
		if (current != null && current.equals(value))
			return;

		if (value == null)
			map.remove(attributeName);
		else
			map.put(attributeName, value);
		firePropertyChangeEvent(attributeName, current, value);
	}

	/**
	 * Add a property change listener to this server.
	 * 
	 * @param listener
	 *            java.beans.PropertyChangeListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners == null) {
			propertyListeners = new ArrayList(2);
		}
		propertyListeners.add(listener);
	}

	/**
	 * Remove a property change listener from this server.
	 * 
	 * @param listener
	 *            java.beans.PropertyChangeListener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners != null) {
			propertyListeners.remove(listener);
		}
	}

	/**
	 * Fire a property change event.
	 * 
	 * @param propertyName
	 *            a property name
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 */
	public void firePropertyChangeEvent(String propertyName, Object oldValue,
			Object newValue) {
		if (propertyListeners == null)
			return;

		PropertyChangeEvent event = new PropertyChangeEvent(server,
				propertyName, oldValue, newValue);
		try {
			Iterator iterator = propertyListeners.iterator();
			while (iterator.hasNext()) {
				try {
					PropertyChangeListener listener = (PropertyChangeListener) iterator
							.next();
					listener.propertyChange(event);
				} catch (Exception e) {
					Logger
							.logException("Error firing property change event", //$NON-NLS-1$
									e);
				}
			}
		} catch (Exception e) {
			Logger.logException("Error in property event", e); //$NON-NLS-1$
		}
	}

	public String getAttribute(String attributeName, String defaultValue) {
		try {
			Object obj = map.get(attributeName);
			if (obj == null)
				return defaultValue;
			return (String) obj;
		} catch (Exception e) {
			// ignore
		}
		return defaultValue;
	}

	public void removeAttribute(String attributeName) {
		map.remove(attributeName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerHelper other = (ServerHelper) obj;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		return true;
	}
}
