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
package org.eclipse.php.internal.ui.actions;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPhp;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

public class ReorgMoveAction extends AbstractMoveDelegator {

	private static final String MOVE_ELEMENT_ACTION_ID = "org.eclipse.php.ui.actions.Move";
	private IStructuredSelection selectedResources;
	private Shell fShell;
	private AbstractMoveDelegator moveActionDelegate;
	private IContainer target;

	public void run(IStructuredSelection selection) {
		if (ActionUtils.containsOnlyProjects(selection.toList())) {
			createWorkbenchAction(selection).run();
			return;
		}
		if (selectedResources != null && !selectedResources.isEmpty()) {
			createWorkbenchAction(selectedResources).run();
		}
	}

	private SelectionListenerAction createWorkbenchAction(
			IStructuredSelection selection) {
		List<?> list = selection.toList();
		SelectionListenerAction action = null;
		if (fShell == null) {
			fShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell();
		}

		if (list.size() == 0
				|| list.get(0) instanceof IProject
				|| (list.get(0) instanceof IAdaptable && ((IAdaptable) list
						.get(0)).getAdapter(IResource.class) instanceof IProject)) {
			action = new PHPMoveProjectAction(fShell);
			action.selectionChanged(selection);
		} else if (selectedResources != null) {
			action = new MoveResourceAction(fShell);
			if (list.size() == 1) {
				Object object = list.get(0);
				if (object instanceof ElementImplForPhp
						&& ((ElementImplForPhp) object).getModelElement() != null) {
					IResource resource = ((ElementImplForPhp) object)
							.getModelElement().getResource();
					if (resource != null) {
						selection = new StructuredSelection(resource);
					}
				}
			}

			action.selectionChanged(selection);

		}
		return action;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor != null) {
			if (moveActionDelegate == null) {
				init(targetEditor.getSite().getWorkbenchWindow());
			}

			if (moveActionDelegate != null
					&& !(moveActionDelegate instanceof ReorgMoveAction)) {
				moveActionDelegate.setActiveEditor(action, targetEditor);
			} else {
				IEditorInput editorInput = targetEditor.getEditorInput();
				if (editorInput instanceof IFileEditorInput) {
					IFileEditorInput input = (IFileEditorInput) editorInput;
					IFile file = input.getFile();
					selectedResources = new StructuredSelection(file);
				}
			}

		}
	}

	public void run(IAction action) {
		if (moveActionDelegate != null) {
			if (target != null) {
				moveActionDelegate.setTarget(target);
			}
			moveActionDelegate.run(action);
		} else {
			run(selectedResources);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (moveActionDelegate != null) {
			moveActionDelegate.selectionChanged(action, selection);
		} else {
			if (selection instanceof IStructuredSelection) {
				selectedResources = (IStructuredSelection) selection;
			}
		}
	}

	public void dispose() {

	}

	public void setTarget(IContainer target) {
		this.target = target;
	}

	public void init(IWorkbenchWindow window) {
		fShell = window.getShell();

		IPHPActionDelegator action = PHPActionDelegatorRegistry
				.getActionDelegator(MOVE_ELEMENT_ACTION_ID);

		if (action instanceof AbstractMoveDelegator) {
			moveActionDelegate = (AbstractMoveDelegator) action;
		}
	}

}