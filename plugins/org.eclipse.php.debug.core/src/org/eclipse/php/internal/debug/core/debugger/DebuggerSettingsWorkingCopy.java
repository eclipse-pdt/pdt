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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.core.IUniqueIdentityElement;

/**
 * Default implementation for debugger settings working copy. This class is
 * intended to be instantiated and used every time when clients would like to
 * edit the original settings. See {@link DebuggerSettingsManager} class.
 * 
 * @author Bartlomiej Laczkowski
 */
class DebuggerSettingsWorkingCopy implements IDebuggerSettingsWorkingCopy {

	private final IDebuggerSettings original;
	private final Map<String, String> attributes;
	private boolean isDirty = false;

	/**
	 * Creates a working copy for provided settings.
	 */
	DebuggerSettingsWorkingCopy(IDebuggerSettings original) {
		this.original = original;
		// Create attributes copy
		this.attributes = new HashMap<String, String>(original.getAttributes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy
	 * #getOriginal()
	 */
	@Override
	public IDebuggerSettings getOriginal() {
		return original;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getKind()
	 */
	@Override
	public DebuggerSettingsKind getKind() {
		return original.getKind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getDebuggerId
	 * ()
	 */
	@Override
	public String getDebuggerId() {
		return original.getDebuggerId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getOwner()
	 */
	@Override
	public IUniqueIdentityElement getOwner() {
		return original.getOwner();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getAttribute
	 * (java.lang.String)
	 */
	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings#getAttributes
	 * ()
	 */
	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy
	 * #setAttribute(java.lang.String, java.lang.String)
	 */
	@Override
	public void setAttribute(String key, String value) {
		attributes.put(key, value);
		isDirty = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy
	 * #isDirty()
	 */
	@Override
	public boolean isDirty() {
		return isDirty;
	}

}
