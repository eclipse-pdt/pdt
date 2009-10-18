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
package org.eclipse.php.internal.debug.core.preferences.stepFilters;

import org.eclipse.php.internal.core.util.FileUtils;

/**
 * This class represents a Debug Step Filter object. A Debug Step filter handles
 * filtering files when the debugger performs a 'Step Into' action
 * 
 * @author yaronm
 */
public class DebugStepFilter {

	private String fPath;
	private boolean fIsEnabled;
	private boolean fIsReadOnly;
	private int fType;

	public static final String FILTERS_PREF_LIST_DELIM = "<>"; //$NON-NLS-1$
	public static final String FILTER_TOKENS_DELIM = "?"; //$NON-NLS-1$

	/**
	 * Constructs a new Debug Step Filter
	 * 
	 * @param type
	 *            - This Step Filter Type, use IStepFilterTypes constants
	 * @param enabled
	 *            - Whether this filter is enabled
	 * @param isReadOnly
	 *            - Whether this filter is built-in thus cannot be deleted
	 * @param path
	 *            - The path string representation (can have '*' as a prefix
	 *            and/or postfix)
	 */
	public DebugStepFilter(int type, boolean enabled, boolean isReadOnly,
			String path) {
		fPath = path;
		fIsEnabled = enabled;
		fType = type;
		fIsReadOnly = isReadOnly;
	}

	/**
	 * Returns the Path string representation of this filter
	 * 
	 * @return
	 */
	public String getPath() {
		return fPath;
	}

	/**
	 * Sets the path string representation (can have '*' as a prefix and/or
	 * postfix)
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		fPath = path;
	}

	/**
	 * Denotes whether this filter is enabled
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return fIsEnabled;
	}

	/**
	 * Sets whether this filter is enabled
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		fIsEnabled = enabled;
	}

	/**
	 * Returns this filter's type.
	 * 
	 * @return - an IStepFilterTypes constants
	 */
	public int getType() {
		return fType;
	}

	public boolean equals(Object o) {
		if (o instanceof DebugStepFilter) {
			DebugStepFilter other = (DebugStepFilter) o;

			if ((FileUtils.checkIfEqualFilePaths(getPath(), other.getPath()))
					&& (getType() == other.getType())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return getPath().hashCode() + getType();
	}

	/**
	 * Denotes whether this filter is a Read Only one
	 * 
	 * @return
	 */
	public boolean isReadOnly() {
		return fIsReadOnly;
	}
}
