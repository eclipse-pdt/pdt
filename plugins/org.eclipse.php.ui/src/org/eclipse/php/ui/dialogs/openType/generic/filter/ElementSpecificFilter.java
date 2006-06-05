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
package org.eclipse.php.ui.dialogs.openType.generic.filter;

import java.util.ArrayList;

public class ElementSpecificFilter extends SimpleFilter {

	public Object[] filter(Object[] elements) {
        ArrayList out = new ArrayList(elements.length);
        for (int i = 0; i < elements.length; ++i) {
            Object element = elements[i];
            if (select(element))
                out.add(element);
        }
        return out.toArray();
	}

	public boolean select(Object element) {
		return true;
	}

}
