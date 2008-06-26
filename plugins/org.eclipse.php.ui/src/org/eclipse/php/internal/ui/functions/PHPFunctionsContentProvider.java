/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;

public class PHPFunctionsContentProvider extends StandardModelElementContentProvider {

	public static final String CONSTANTS_NODE_NAME = "constants"; //$NON-NLS-1$
	protected static final Object[] NO_CHILDREN = new Object[0];

	public PHPFunctionsContentProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.StandardModelElementContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object element) {
		List<Object> children = new ArrayList<Object>();
		
		if (element instanceof IBuildpathEntry[]) {
			IBuildpathEntry[] entries = (IBuildpathEntry[]) element;
			for (IBuildpathEntry buildpathEntry : entries) {
				final Object[] child = super.getChildren(buildpathEntry);
				children.addAll(Arrays.asList(child));
			}
		}
		
		return children.toArray(new Object[children.size()]);
	}
	
	
}
