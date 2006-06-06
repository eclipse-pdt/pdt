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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.php.ui.dnd.PHPViewerDropAdapter;
import org.eclipse.php.ui.dnd.TransferDropTargetListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

public class SelectionTransferDropAdapter extends PHPViewerDropAdapter implements TransferDropTargetListener {

	private List fElements;
	private int fCanMoveElements;
	private int fCanCopyElements;
	private ISelection fSelection;

	private static final long DROP_TIME_DIFF_TRESHOLD = 150;

	public SelectionTransferDropAdapter(StructuredViewer viewer) {
		super(viewer, DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND);
	}

	//---- TransferDropTargetListener interface ---------------------------------------

	public Transfer getTransfer() {
		return LocalSelectionTransfer.getInstance();
	}

	//---- Actual DND -----------------------------------------------------------------

	public void dragEnter(DropTargetEvent event) {
		clear();
		super.dragEnter(event);
	}

	public void dragLeave(DropTargetEvent event) {
		clear();
		super.dragLeave(event);
	}

	private void clear() {
		fElements = null;
		fSelection = null;
		//		fMoveProcessor= null;
		fCanMoveElements = 0;
		//		fCopyRefactoring2= null;
		fCanCopyElements = 0;
	}

	public void validateDrop(Object target, DropTargetEvent event, int operation) {
		event.detail = DND.DROP_NONE;

		if (tooFast(event))
			return;

		initializeSelection();

		switch (operation) {
			case DND.DROP_DEFAULT:
				event.detail = handleValidateDefault(target, event);
				break;
			case DND.DROP_COPY:
				event.detail = handleValidateCopy(target, event);
				break;
			case DND.DROP_MOVE:
				event.detail = handleValidateMove(target, event);
				break;
			case DND.DROP_LINK:
				event.detail = handleValidateLink(target, event);
				break;
		}
	}

	protected void initializeSelection() {
		if (fElements != null)
			return;
		ISelection s = LocalSelectionTransfer.getInstance().getSelection();
		if (!(s instanceof IStructuredSelection))
			return;
		fSelection = s;
		fElements = ((IStructuredSelection) s).toList();
	}

	protected ISelection getSelection() {
		return fSelection;
	}

	private boolean tooFast(DropTargetEvent event) {
		return Math.abs(LocalSelectionTransfer.getInstance().getSelectionSetTime() - (event.time & 0xFFFFFFFFL)) < DROP_TIME_DIFF_TRESHOLD;
	}

	public void drop(Object target, DropTargetEvent event) {
		try {
			switch (event.detail) {
				case DND.DROP_MOVE:
					handleDropMove(target, event);
					break;
				case DND.DROP_COPY:
					handleDropCopy(target, event);
					break;
				case DND.DROP_LINK:
					handleDropLink(target, event);
					break;
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			//ok
		} finally {
			// The drag source listener must not perform any operation
			// since this drop adapter did the remove of the source even
			// if we moved something.
			event.detail = DND.DROP_NONE;
		}
	}

	private int handleValidateDefault(Object target, DropTargetEvent event) {
		if (target == null)
			return DND.DROP_NONE;

		return handleValidateMove(target, event);
	}

	private int handleValidateMove(Object target, DropTargetEvent event) {
		if (target == null)
			return DND.DROP_NONE;

		if (!canMoveElements())
			return DND.DROP_NONE;

		return DND.DROP_NONE;
	}

	private boolean canMoveElements() {
		if (fCanMoveElements == 0) {
			fCanMoveElements = 2;
		}
		return fCanMoveElements == 2;
	}

	private void handleDropLink(Object target, DropTargetEvent event) {
	}

	private int handleValidateLink(Object target, DropTargetEvent event) {
		return DND.DROP_NONE;
	}

	private void handleDropMove(final Object target, DropTargetEvent event) throws InvocationTargetException, InterruptedException {
	}

	private int handleValidateCopy(Object target, DropTargetEvent event) {

		if (!canCopyElements())
			return DND.DROP_NONE;

		return DND.DROP_NONE;
	}

	private boolean canCopyElements() {
		if (fCanCopyElements == 0) {
			fCanCopyElements = 2;
		}
		return fCanCopyElements == 2;
	}

	private void handleDropCopy(final Object target, DropTargetEvent event) throws InvocationTargetException, InterruptedException {
	}

	private Shell getShell() {
		return getViewer().getControl().getShell();
	}
}
