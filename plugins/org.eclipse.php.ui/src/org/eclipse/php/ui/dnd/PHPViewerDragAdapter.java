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
package org.eclipse.php.ui.dnd;

import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;

public class PHPViewerDragAdapter extends DelegatingDragAdapter {

	private StructuredViewer fViewer;

	public PHPViewerDragAdapter(StructuredViewer viewer, TransferDragSourceListener[] listeners) {
		super(listeners);
		Assert.isNotNull(viewer);
		fViewer = viewer;
	}

	public void dragStart(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
		if (selection.isEmpty()) {
			event.doit = false;
			return;
		}
		super.dragStart(event);
	}
}
