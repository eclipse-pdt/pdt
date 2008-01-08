/*******************************************************************************
 * Copyright (c) 2007 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences.stepFilters;

/**
 * This class represents a Debug Step Filter object.
 * @author yaronm
 */
public class DebugStepFilter {

	private String fPath;
	private boolean fIsActive;
	private boolean fIsReadOnly;
	private String fType;

	/**
	 * Constructs a new Debug Step Filter
	 */
	public DebugStepFilter(String type, boolean active, boolean isReadOnly, String path) {
		setPath(path);
		setActive(active);
		setType(type);
		fIsReadOnly = isReadOnly;
	}

	public String getPath() {
		return fPath;
	}

	public void setPath(String path) {
		fPath = path;
	}

	public boolean isActive() {
		return fIsActive;
	}

	public void setActive(boolean active) {
		fIsActive = active;
	}

	public void setType(String type) {
		this.fType = type;
	}

	public String getType() {
		return fType;
	}

	public boolean equals(Object o) {
		if (o instanceof DebugStepFilter) {
			DebugStepFilter other = (DebugStepFilter) o;
			if (getPath().equals(other.getPath())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return getPath().hashCode();
	}

	public boolean isReadOnly() {
		return fIsReadOnly;
	}
}
