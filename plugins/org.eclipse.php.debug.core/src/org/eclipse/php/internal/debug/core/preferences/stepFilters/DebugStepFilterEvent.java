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
 * Use this event class when the Debug Step Filter Preferences are changed
 * @author yaronm
 */
public class DebugStepFilterEvent {
	private DebugStepFilter[] newFilters;
	private boolean isDebugStepFilterEnabled;

	public DebugStepFilterEvent(DebugStepFilter[] newFilters, boolean isDebugStepFilterEnabled) {
		this.newFilters = newFilters;
		this.isDebugStepFilterEnabled = isDebugStepFilterEnabled;
	}

	public DebugStepFilter[] getNewFilters() {
		return newFilters;
	}

	public boolean isDebugStepFilterEnabled() {
		return isDebugStepFilterEnabled;
	}
}
