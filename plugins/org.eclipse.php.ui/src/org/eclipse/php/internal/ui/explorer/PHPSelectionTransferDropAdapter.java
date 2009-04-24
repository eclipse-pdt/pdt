/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.corext.refactoring.reorg.ReorgUtils;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.dltk.internal.ui.refactoring.reorg.ReorgCopyStarter;
import org.eclipse.dltk.internal.ui.scriptview.ScriptMessages;
import org.eclipse.dltk.internal.ui.scriptview.SelectionTransferDropAdapter;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.php.internal.ui.actions.ReorgMoveAction;
import org.eclipse.swt.dnd.DND;
import org.eclipse.ui.PlatformUI;

public class PHPSelectionTransferDropAdapter extends SelectionTransferDropAdapter {

	public PHPSelectionTransferDropAdapter(StructuredViewer viewer) {
		super(viewer);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean performDrop(Object data) {
		try {
			switch (getCurrentOperation()) {
				case DND.DROP_MOVE:
					handleDropMove(getCurrentTarget());
					break;
				case DND.DROP_COPY:
					handleDropCopy(getCurrentTarget());
					break;
			}
		} catch (ModelException e) {
			ExceptionHandler.handle(e, ScriptMessages.SelectionTransferDropAdapter_error_title, ScriptMessages.SelectionTransferDropAdapter_error_message);
		} catch (InvocationTargetException e) {
			ExceptionHandler.handle(e, RefactoringMessages.OpenRefactoringWizardAction_refactoring, RefactoringMessages.OpenRefactoringWizardAction_exception);
		} catch (InterruptedException e) {
			// ok
		}
		// The drag source listener must not perform any operation
		// since this drop adapter did the remove of the source even
		// if we moved something.
		return false;

	}

	private void handleDropMove(final Object target) throws ModelException, InvocationTargetException, InterruptedException {
		List elements = ((IStructuredSelection) getSelection()).toList();
		IModelElement[] modelElements = ReorgUtils.getModelElements(elements);
		IResource[] resources = ReorgUtils.getResources(modelElements);

		ReorgMoveAction action = new ReorgMoveAction();
		action.init(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		StructuredSelection selection = new StructuredSelection(resources);
		action.selectionChanged(null, selection);
		action.run((Action) null);
	}

	private void handleDropCopy(final Object target) throws ModelException, InvocationTargetException, InterruptedException {
		List elements = ((IStructuredSelection) getSelection()).toList();
		IModelElement[] modelElements = ReorgUtils.getModelElements(elements);
		IResource[] resources = ReorgUtils.getResources(modelElements);
		ReorgCopyStarter starter = null;
		if (target instanceof IModelElement) {
			starter = ReorgCopyStarter.create(modelElements, resources, (IModelElement) target);
		} else if (target instanceof IResource) {
			starter = ReorgCopyStarter.create(modelElements, resources, (IResource) target);
		}

		if (starter != null)
			starter.run(this.getViewer().getControl().getShell());
	}

}
