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
package org.eclipse.php.internal.ui.util;

import org.eclipse.jface.viewers.IElementComparer;

public class DefaultElementComparer implements IElementComparer {

	public static final DefaultElementComparer INSTANCE = new DefaultElementComparer();

	public boolean equals(Object a, Object b) {
		return a.equals(b);
	}

	public int hashCode(Object element) {
		return element.hashCode();
	}
}
