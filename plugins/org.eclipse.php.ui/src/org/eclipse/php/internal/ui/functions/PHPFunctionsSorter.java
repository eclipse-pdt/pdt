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
package org.eclipse.php.internal.ui.functions;

import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.jface.viewers.Viewer;

/**
 * Elements sorter for the PHP Functions view. Inherits all its behavior from
 * the ModelElementSorter but adds special handling for the Constants Node, that
 * should appear as the first node in the view
 * 
 * @author Eden K., 2008
 * 
 */
public class PHPFunctionsSorter extends ModelElementSorter {

	/**
	 * Display the ConstantNode first in the tree, then the rest of the elements
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof ConstantNode) {
			return -1;
		} else if (e2 instanceof ConstantNode) {
			return 1;
		} else {
			return super.compare(viewer, e1, e2);
		}
	}
}
