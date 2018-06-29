/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs.openType.generic.filter;

import java.util.ArrayList;
import java.util.List;

public class ElementSpecificFilter extends SimpleFilter {

	@Override
	public Object[] filter(Object[] elements) {
		List<Object> out = new ArrayList<>(elements.length);
		for (int i = 0; i < elements.length; ++i) {
			Object element = elements[i];
			if (select(element)) {
				out.add(element);
			}
		}
		return out.toArray();
	}

	public boolean select(Object element) {
		return true;
	}

}
