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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.IActionFilter;

public class GenericActionFilter implements IActionFilter {

	protected Map attributeName2Delegator = new HashMap();

	public boolean testAttribute(Object target, String name, String value) {
		Object object = attributeName2Delegator.get(name);
		if (object == null) {
			return false;
		}
		IActionFilterDelegator actionFilterDelegator = (IActionFilterDelegator)object;
		return actionFilterDelegator.test(target, value);
	}

}
