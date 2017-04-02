/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - Extract to separate file	
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider.IncludePathContainer;
import org.eclipse.php.internal.ui.util.NamespaceNode;

public class PHPExplorerElementSorter extends ModelElementSorter {
	private static final int INCLUDE_PATH_CONTAINER = 59;

	@Override
	public int category(Object element) {
		if (element instanceof IncludePathContainer) {
			return INCLUDE_PATH_CONTAINER;
		} else {
			return super.category(element);
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof NamespaceNode && e2 instanceof NamespaceNode) {
			return ((NamespaceNode) e1).getElementName().compareTo(((NamespaceNode) e2).getElementName());
		}

		// Fix #256585 - sort by resource name
		Object c1 = e1;
		if (e1 instanceof ISourceModule) {
			c1 = ((ISourceModule) e1).getResource();
		}
		Object c2 = e2;
		if (e2 instanceof ISourceModule) {
			c2 = ((ISourceModule) e2).getResource();
		}

		if (e1 instanceof IProjectFragment && e2 instanceof IProjectFragment) {
			c1 = ((IProjectFragment) e1).getResource();
			c2 = ((IProjectFragment) e2).getResource();
		}

		if (c1 != null && c2 != null) {
			return super.compare(viewer, c1, c2);
		}

		return super.compare(viewer, e1, e2);
	}
}
