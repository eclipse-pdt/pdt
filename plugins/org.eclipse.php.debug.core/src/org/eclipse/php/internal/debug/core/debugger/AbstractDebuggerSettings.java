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
package org.eclipse.php.internal.debug.core.debugger;

import java.util.Collections;
import java.util.Map;

import org.eclipse.php.internal.core.IUniqueIdentityElement;

/**
 * Abstract implementation for debugger owner settings. Debugger's settings
 * owner (e.g. PHP server or executable) which is an input for this class must
 * implement {@link IUniqueIdentityElement} interface.
 * <p>
 * Clients may extend this class (highly recommended).
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractDebuggerSettings implements IDebuggerSettings {

	protected String ownerId;
	protected Map<String, String> attributes;

	/**
	 * Creates new debugger settings for given settings owner.
	 * 
	 * @param ownerId
	 */
	public AbstractDebuggerSettings(String ownerId) {
		this.ownerId = ownerId;
		this.attributes = Collections.unmodifiableMap(createAttributes());
	}

	/**
	 * Creates new debugger settings for given settings owner with the use of
	 * provided attributes.
	 * 
	 * @param owner
	 * @param attributes
	 */
	public AbstractDebuggerSettings(String ownerId, Map<String, String> attributes) {
		this.ownerId = ownerId;
		this.attributes = attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getOwnerId
	 * ()
	 */
	@Override
	public String getOwnerId() {
		return ownerId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#
	 * getAttribute (java.lang.String)
	 */
	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#
	 * getAttributes ()
	 */
	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Implementors should create default/initial attributes map.
	 * 
	 * @return default/initial attributes map
	 */
	protected abstract Map<String, String> createAttributes();

	/**
	 * Updates this settings with the values from working copy.
	 * 
	 * @param settingsWorkingCopy
	 */
	void update(IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		attributes = Collections.unmodifiableMap(settingsWorkingCopy.getAttributes());
	}

}
