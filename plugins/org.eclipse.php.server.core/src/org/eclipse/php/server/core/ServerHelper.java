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
package org.eclipse.php.server.core;

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

	public ServerHelper() {
		this.map = new HashMap();
	}

	public void setAttribute(String attributeName, int value) {
		int current = getAttribute(attributeName, 0);
		if (current != 0 && current == value)
			return;

		map.put(attributeName, Integer.toString(value));
		firePropertyChangeEvent(attributeName, new Integer(current), new Integer(value));
	}

	public void setAttribute(String attributeName, boolean value) {
		boolean current = getAttribute(attributeName, false);

		map.put(attributeName, Boolean.toString(value));
		firePropertyChangeEvent(attributeName, new Boolean(current), new Boolean(value));
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

	public void setAttribute(String attributeName, List value) {
		List current = getAttribute(attributeName, (List) null);
		if (current != null && current.equals(value))
			return;

		if (value == null)
			map.remove(attributeName);
		else
			map.put(attributeName, value);
		firePropertyChangeEvent(attributeName, current, value);
	}

	public void setAttribute(String attributeName, Map value) {
		Map current = getAttribute(attributeName, (Map) null);
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
	 * @param listener java.beans.PropertyChangeListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners == null) {
			propertyListeners = new ArrayList(2);
			propertyListeners.add(listener);
		}
	}

	/**
	 * Remove a property change listener from this server.
	 *
	 * @param listener java.beans.PropertyChangeListener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners != null) {
			propertyListeners.remove(listener);
		}
	}

	/**
	 * Fire a property change event.
	 * 
	 * @param propertyName a property name
	 * @param oldValue the old value
	 * @param newValue the new value
	 */
	public void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
		if (propertyListeners == null)
			return;

		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		try {
			Iterator iterator = propertyListeners.iterator();
			while (iterator.hasNext()) {
				try {
					PropertyChangeListener listener = (PropertyChangeListener) iterator.next();
					listener.propertyChange(event);
				} catch (Exception e) {
					Logger.logException("Error firing property change event", e);
				}
			}
		} catch (Exception e) {
			Logger.logException("Error in property event", e);
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

	public int getAttribute(String attributeName, int defaultValue) {
		try {
			Object obj = map.get(attributeName);
			if (obj == null)
				return defaultValue;
			return Integer.parseInt((String) obj);
		} catch (Exception e) {
			// ignore
		}
		return defaultValue;
	}

	public boolean getAttribute(String attributeName, boolean defaultValue) {
		try {
			Object obj = map.get(attributeName);
			if (obj == null)
				return defaultValue;
			return Boolean.valueOf((String) obj).booleanValue();
		} catch (Exception e) {
			// ignore
		}
		return defaultValue;
	}

	public List getAttribute(String attributeName, List defaultValue) {
		try {
			Object obj = map.get(attributeName);
			if (obj == null)
				return defaultValue;
			List list = (List) obj;
			if (list != null)
				return list;
		} catch (Exception e) {
			// ignore
		}
		return defaultValue;
	}

	public Map getAttribute(String attributeName, Map defaultValue) {
		try {
			Object obj = map.get(attributeName);
			if (obj == null)
				return defaultValue;
			Map map2 = (Map) obj;
			if (map2 != null)
				return map2;
		} catch (Exception e) {
			// ignore
		}
		return defaultValue;
	}
}
