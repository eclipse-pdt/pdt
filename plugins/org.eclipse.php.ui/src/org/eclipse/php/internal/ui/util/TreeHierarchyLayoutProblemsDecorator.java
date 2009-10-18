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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ui.ProblemsLabelDecorator;

/**
 * Special problem decorator for hierarchical Folder layout.
 * <p>
 * It only decorates php folders which are not covered by the
 * <code>ProblemsLabelDecorator</code>.
 * </p>
 */
public class TreeHierarchyLayoutProblemsDecorator extends
		ProblemsLabelDecorator {

	private boolean fIsFlatLayout;

	public TreeHierarchyLayoutProblemsDecorator() {
		this(false);
	}

	public TreeHierarchyLayoutProblemsDecorator(boolean isFlatLayout) {
		fIsFlatLayout = isFlatLayout;
	}

	protected int computeFolderAdornmentFlags(IFolder folder) {
		if (!fIsFlatLayout && !(folder instanceof IProject)) {
			return super.computeAdornmentFlags(folder);
		}
		return super.computeAdornmentFlags(folder);
	}

	protected int computeAdornmentFlags(Object element) {
		if (element instanceof IFolder) {
			return computeFolderAdornmentFlags((IFolder) element);
		}
		return super.computeAdornmentFlags(element);
	}

	public void setIsFlatLayout(boolean state) {
		fIsFlatLayout = state;
	}

}
