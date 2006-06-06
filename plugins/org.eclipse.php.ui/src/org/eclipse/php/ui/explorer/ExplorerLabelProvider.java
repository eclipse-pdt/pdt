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
package org.eclipse.php.ui.explorer;

import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.ui.util.TreeHierarchyLayoutProblemsDecorator;

/**
 * Provides the labels for the PHP Explorer.
 * <p>
 * It provides labels for the folders in hierarchical layout and in all
 * other cases delegates it to its super class.
 * </p>
 * @since 2.1
 */
class ExplorerLabelProvider extends AppearanceAwareLabelProvider {

	private ITreeContentProvider fContentProvider;

	private TreeHierarchyLayoutProblemsDecorator fProblemDecorator;

	ExplorerLabelProvider(int textFlags, int imageFlags, ITreeContentProvider cp) {
		super(textFlags, imageFlags);
		fProblemDecorator = new TreeHierarchyLayoutProblemsDecorator();
		addLabelDecorator(fProblemDecorator);
		Assert.isNotNull(cp);
		fContentProvider = cp;
	}

	public String getText(Object element) {
		String label = super.getText(element);
		if (label != null && label.startsWith("_lzx_")) {
			label = label.substring(5);
			if (label.startsWith("Lz"))
				label = "<" + label.substring(2) + ">";
			else
				label = "<_____   id=" + label + ">";
		}

		return label;
	}

}
