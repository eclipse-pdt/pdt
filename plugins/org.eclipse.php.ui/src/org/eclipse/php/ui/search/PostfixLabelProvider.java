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
package org.eclipse.php.ui.search;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.util.PHPElementLabels;

/**
 * This label provider will add the full path text to the label of an element with no parent
 * (not including the IProject element)
 * 
 * @author shalom
 *
 */
public class PostfixLabelProvider extends SearchLabelProvider {

	public PostfixLabelProvider(PHPSearchResultPage page, int flags) {
		super(page, flags);
	}

	public String getText(Object element) {
		String labelWithCounts = getLabelWithCounts(element, super.getText(element));

		StringBuffer res = new StringBuffer(labelWithCounts);

		IStructuredContentProvider provider = (IStructuredContentProvider) fPage.getViewer().getContentProvider();
		Object visibleParent = null;
		if (provider instanceof ITreeContentProvider) {
			visibleParent = ((ITreeContentProvider) provider).getParent(element);
		}
		if (visibleParent == null && !(element instanceof IProject) && !(element instanceof PHPFileData)) {
			String text = ((PHPCodeData) element).getUserData().getFileName();
			if (text != null && text.length() > 0) {
				res.append(PHPElementLabels.CONCAT_STRING).append(text);
			}
		}
		return res.toString();
	}

	protected boolean hasChildren(Object element) {
		IStructuredContentProvider cp = (IStructuredContentProvider) fPage.getViewer().getContentProvider();
		return cp.getElements(element).length > 0;
	}

}
