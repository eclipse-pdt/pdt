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
package org.eclipse.php.internal.core.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A base class for all the preferences propagators.
 * 
 * @author shalom
 */
public abstract class AbstractPreferencesPropagator {

	protected HashMap listenersMap;
	protected boolean isInstalled;
	protected Object lock = new Object();

	/**
	 * Constructs an AbstractPreferencesPropagator.
	 */
	public AbstractPreferencesPropagator() {
	}

	/**
	 * Adds an IPreferencesPropagatorListener with a preferences key to listen
	 * to.
	 * 
	 * @param listener
	 *            An IPreferencesPropagatorListener.
	 * @param preferencesKey
	 *            The preferences key that will screen the relevant changes.
	 */
	public void addPropagatorListener(IPreferencesPropagatorListener listener,
			String preferencesKey) {
		List list = (List) listenersMap.get(preferencesKey);
		if (list == null) {
			list = new ArrayList(5);
			listenersMap.put(preferencesKey, list);
		}
		if (!list.contains(listener)) {
			list.add(listener);
		}
	}

	/**
	 * Removes an IPreferencesPropagatorListener that was assigned to listen to
	 * the given preferences key.
	 * 
	 * @param listener
	 *            An IPreferencesPropagatorListener.
	 * @param preferencesKey
	 *            The preferences key that is the screening key for the
	 *            IPreferencesPropagatorListener.
	 */
	public void removePropagatorListener(
			IPreferencesPropagatorListener listener, String preferencesKey) {
		List list = (List) listenersMap.get(preferencesKey);
		if (list != null) {
			list.remove(listener);
		}
	}

	/**
	 * Sets a list of listeners for the given preferences key. This list will
	 * replace any previous list of listeners for the key.
	 * 
	 * @param listeners
	 *            A List of listeners.
	 * @param preferencesKey
	 *            The preferences key that will screen the relevant changes.
	 */
	public void setPropagatorListeners(List listeners, String preferencesKey) {
		listenersMap.put(preferencesKey, listeners);
	}

	/**
	 * Returns the list of listeners assigned to the preferences key, or null if
	 * non exists.
	 * 
	 * @param preferencesKey
	 *            The key that the listeners listen to.
	 * @return The list of listeners assigned for the key, or null if non
	 *         exists.
	 */
	protected List getPropagatorListeners(String preferencesKey) {
		synchronized (lock) {
			return (List) listenersMap.get(preferencesKey);
		}
	}

	/**
	 * Install the preferences propagator.
	 */
	protected synchronized void install() {
		if (isInstalled) {
			return;
		}
		listenersMap = new HashMap();
		isInstalled = true;
	}

	/**
	 * Uninstall the preferences propagator.
	 */
	protected synchronized void uninstall() {
		if (!isInstalled) {
			return;
		}
		listenersMap = null;
		isInstalled = false;
	}
}
