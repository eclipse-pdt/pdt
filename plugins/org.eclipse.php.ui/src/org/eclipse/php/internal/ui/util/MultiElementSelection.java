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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

@Deprecated
public class MultiElementSelection extends StructuredSelection {

	private static final TreePath[] EMPTY_TREE_PATHS = new TreePath[0];

	private TreePath[] fAllTreePaths;
	private CustomHashtable fElement2TreePaths;

	public MultiElementSelection(StructuredViewer viewer, List elements, TreePath[] treePaths) {
		super(elements);
		fAllTreePaths = treePaths;
		fElement2TreePaths = createTreePathMap(viewer.getComparer());
	}

	public TreePath[] getAllTreePaths() {
		return fAllTreePaths;
	}

	public TreePath[] getTreePaths(Object element) {
		Object value = fElement2TreePaths.get(element);
		if (value == null) {
			return EMPTY_TREE_PATHS;
		} else if (value instanceof TreePath) {
			return new TreePath[] { (TreePath) value };
		} else if (value instanceof List) {
			List l = (List) value;
			return (TreePath[]) l.toArray(new TreePath[l.size()]);
		} else {
			Assert.isTrue(false, "Should not happen"); //$NON-NLS-1$
			return null;
		}
	}

	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		if (!getClass().getName().equals(o.getClass().getName()))
			return false;
		MultiElementSelection otherSelection = (MultiElementSelection) o;
		if (fAllTreePaths.length != otherSelection.fAllTreePaths.length)
			return false;
		for (int i = 0; i < fAllTreePaths.length; i++) {
			if (!fAllTreePaths[i].equals(otherSelection.fAllTreePaths[i]))
				return false;
		}
		return true;
	}

	private CustomHashtable createTreePathMap(IElementComparer comparer) {
		CustomHashtable result = new CustomHashtable(comparer);
		for (int i = 0; i < fAllTreePaths.length; i++) {
			TreePath path = fAllTreePaths[i];
			Object key = path.getLastSegment();
			if (key != null) {
				Object value = result.get(key);
				if (value == null) {
					result.put(key, path);
				} else if (value instanceof TreePath) {
					List l = new ArrayList();
					l.add(value);
					l.add(path);
					result.put(key, l);
				} else if (value instanceof List) {
					((List) value).add(path);
				} else {
					Assert.isTrue(false, "Should not happen"); //$NON-NLS-1$
				}
			}
		}
		return result;
	}
}
