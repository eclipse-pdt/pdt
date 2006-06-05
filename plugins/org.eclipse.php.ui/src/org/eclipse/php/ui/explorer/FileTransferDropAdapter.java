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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.dnd.PHPViewerDropAdapter;
import org.eclipse.php.ui.dnd.TransferDropTargetListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;


/**
 * Adapter to handle file drop from other applications.
 */
class FileTransferDropAdapter extends PHPViewerDropAdapter implements TransferDropTargetListener {

	FileTransferDropAdapter(AbstractTreeViewer viewer) {
		super(viewer, DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND);
	}

	//---- TransferDropTargetListener interface ---------------------------------------

	public Transfer getTransfer() {
		return FileTransfer.getInstance();
	}

	//---- Actual DND -----------------------------------------------------------------

	public void validateDrop(Object target, DropTargetEvent event, int operation) {
		event.detail = DND.DROP_NONE;

		boolean isPHPFolder = target instanceof IFolder;
		boolean isPHPProject = target instanceof PHPProjectModel;
		boolean isContainer = target instanceof IContainer;

		if (!(isPHPFolder || isPHPProject || isContainer))
			return;

		if (isContainer) {
			IContainer container = (IContainer) target;
			if (container.isAccessible() && !container.isReadOnly())
				event.detail = DND.DROP_COPY;
		} else {

			if (!PHPModelUtil.isReadOnly(target))
				event.detail = DND.DROP_COPY;
		}

		return;
	}

	public void drop(Object dropTarget, final DropTargetEvent event) {
		int operation = event.detail;

		event.detail = DND.DROP_NONE;
		final Object data = event.data;
		if (data == null || !(data instanceof String[]) || operation != DND.DROP_COPY)
			return;

		final IContainer target = getActualTarget(dropTarget);
		if (target == null)
			return;

		// Run the import operation asynchronously.
		// Otherwise the drag source (e.g., Windows Explorer) will be blocked
		// while the operation executes. 
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				getShell().forceActive();
				new CopyFilesAndFoldersOperation(getShell()).copyFiles((String[]) data, target);
				// Import always performs a copy.
				event.detail = DND.DROP_COPY;
			}
		});
	}

	private IContainer getActualTarget(Object dropTarget) {
		if (dropTarget instanceof IContainer)
			return (IContainer) dropTarget;
		else if (dropTarget instanceof PHPCodeData)
			return PHPModelUtil.getResource((PHPCodeData) dropTarget).getParent();
		return null;
	}

	private Shell getShell() {
		return getViewer().getControl().getShell();
	}
}
