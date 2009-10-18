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

import java.util.EventObject;

/**
 * A PreferencesPropagatorEvent is fired by the PreferencesEventsPropagator when
 * propagating evnets that arrive as a results of some preferences changes.
 * 
 * @author shalom
 */
public class PreferencesPropagatorEvent extends EventObject {

	private Object oldValue;
	private Object newValue;
	private Object key;

	/**
	 * Constructs a new PreferencesPropagatorEvent.
	 * 
	 * @param source
	 *            The event's source (can be IProject or IPreferenceStore)
	 * @param oldValue
	 *            The preferences old value
	 * @param newValue
	 *            The preferences new value
	 * @param key
	 *            The preferences key
	 */
	public PreferencesPropagatorEvent(Object source, Object oldValue,
			Object newValue, Object key) {
		super(source);
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.key = key;
	}

	/**
	 * Returns the key.
	 * 
	 * @return The preferences key.
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * Returns the newValue.
	 * 
	 * @return The preferences newValue.
	 */
	public Object getNewValue() {
		return newValue;
	}

	/**
	 * Returns the oldValue.
	 * 
	 * @return The preferences oldValue.
	 */
	public Object getOldValue() {
		return oldValue;
	}

}
