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
package org.eclipse.php.internal.ui.explorer;

import java.util.Iterator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.ui.dnd.BasicSelectionTransferDragAdapter;

public class SelectionTransferDragAdapter extends BasicSelectionTransferDragAdapter {

	public SelectionTransferDragAdapter(ISelectionProvider provider) {
		super(provider);
	}

	protected boolean isDragable(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator iter = ((IStructuredSelection) selection).iterator(); iter.hasNext();) {
				Object element = iter.next();
				if (element instanceof IFolder) {
					if (PHPModelUtil.isExternal(element))
						return false;
				}
			}
			return true;
		}
		return false;
	}
}
