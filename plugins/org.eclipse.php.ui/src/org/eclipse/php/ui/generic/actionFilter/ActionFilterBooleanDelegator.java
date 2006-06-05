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
package org.eclipse.php.ui.generic.actionFilter;

public abstract class ActionFilterBooleanDelegator implements IActionFilterDelegator {

	public boolean test(Object target, String value) {
		boolean b = Boolean.valueOf(value).booleanValue();
		boolean result = test(target);
		return b ?  result : !result;
	}

	protected abstract boolean test(Object target);
}
