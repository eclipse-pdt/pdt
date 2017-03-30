/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring;
import org.eclipse.php.internal.ui.actions.AbstractMoveDelegator;
import org.eclipse.php.internal.ui.actions.ActionUtils;
import org.eclipse.php.refactoring.core.move.PHPMoveProcessor;
import org.eclipse.php.refactoring.core.move.PHPProjectMoveProcessor;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.php.refactoring.ui.utils.RefactoringStarter;
import org.eclipse.php.refactoring.ui.wizard.PHPMoveWizard;
import org.eclipse.php.refactoring.ui.wizard.PHPProjectMoveWizard;
import org.eclipse.ui.*;

/**
 * Action called when the user selects Refactoring->Move the action triggers the
 * refactoring process.
 * 
 * @author Eden K., 2007,Qiangsheng Wang 2009.
 */
public class RefactoringMoveAction extends AbstractMoveDelegator {

	private IStructuredSelection selectedResources;
	private IShellProvider fShellProvider;
	private IContainer target;

	/**
	 * Starts the refactoring process
	 */
	public void run(IStructuredSelection selection) {

		if (fShellProvider == null) {
			fShellProvider = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}

		if (selection == null) {
			unableToRunMoveAction();
			return;
		}
		// first check if the selection is a project
		if (ActionUtils.containsOnlyProjects(selection.toList())) {
			// createWorkbenchAction(selection).run();
			IProject project = null;
			if (selection.toList().get(0) instanceof IProject) {
				project = (IProject) selection.toList().get(0);
			} else if (selection.toList().get(0) instanceof IScriptProject) {
				project = ((IScriptProject) selection.toList().get(0)).getProject();
			} else {
				return;
			}
			PHPProjectMoveProcessor processor = new PHPProjectMoveProcessor(project);

			MoveRefactoring refactoring = new MoveRefactoring(processor);
			PHPProjectMoveWizard wizard = new PHPProjectMoveWizard(refactoring, project);
			new RefactoringStarter().activate(refactoring, wizard, fShellProvider.getShell(),
					PHPRefactoringUIMessages.getString("RefactoringMoveAction.0"), true); //$NON-NLS-1$
			return;
		}

		// if the selection is empty and the run process started notify the user
		if (selectedResources != null && selectedResources.isEmpty()) {
			unableToRunMoveAction();
			return;
		}

		// check for external files
		final IResource[] resources = PHPMoveProcessor.getResources(selectedResources);
		if (checkForExternalFiles(resources) || resources.length == 0) {
			unableToRunMoveAction();
			return;
		}

		// run the move operation on the resources
		PHPMoveProcessor processor = new PHPMoveProcessor(resources);
		if (target != null) {
			processor.setDestination(target);
		}

		MoveRefactoring refactoring = new MoveRefactoring(processor);
		PHPMoveWizard wizard = new PHPMoveWizard(refactoring);
		new RefactoringStarter().activate(refactoring, wizard, fShellProvider.getShell(),
				PHPRefactoringUIMessages.getString("RefactoringMoveAction.0"), true); //$NON-NLS-1$
	}

	/**
	 * Check if one of the resources are external files
	 * 
	 * @param resources
	 * @return
	 */
	private boolean checkForExternalFiles(IResource[] resources) {
		// for (IResource resource : resources) {
		// if (resource instanceof ExternalFileWrapper) {
		// return true;
		// }
		// }
		return false;
	}

	private final void unableToRunMoveAction() {
		MessageDialog.openInformation(fShellProvider.getShell(),
				PHPRefactoringUIMessages.getString("RefactoringMoveAction.1"), //$NON-NLS-1$
				PHPRefactoringUIMessages.getString("RefactoringMoveAction.2")); //$NON-NLS-1$
	}

	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {
		fShellProvider = window;
	}

	public void run(IAction action) {
		run(selectedResources);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			if (selection instanceof ITextSelection) {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IEditorPart editor = workbenchWindow.getActivePage().getActiveEditor();
				setActiveEditor(action, editor);
			} else {
				selectedResources = (IStructuredSelection) selection;
			}
		}
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor == null) {
			return;
		}
		IEditorInput input = targetEditor.getEditorInput();
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			selectedResources = new StructuredSelection(file);
		}
	}

	public void setTarget(IContainer target) {
		this.target = target;
	}
}
