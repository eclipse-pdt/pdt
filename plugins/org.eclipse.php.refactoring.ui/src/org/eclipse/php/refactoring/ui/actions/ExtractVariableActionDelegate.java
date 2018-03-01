/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.refactoring.core.extract.variable.ExtractVariableRefactoring;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.php.refactoring.ui.utils.RefactoringStarter;
import org.eclipse.php.refactoring.ui.wizard.ExtractVariableWizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class ExtractVariableActionDelegate implements IEditorActionDelegate, IWorkbenchWindowActionDelegate {

	private IEditorPart targetEditor;
	private Shell shell;

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor == null) {
			return;
		}

		this.targetEditor = targetEditor;
		IWorkbenchPartSite site = targetEditor.getSite();
		if (site != null) {
			shell = site.getShell();
		}

	}

	@Override
	public void run(IAction action) {
		if (targetEditor == null) {
			targetEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		}
		if (shell == null) {
			shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		}
		if (!(targetEditor instanceof PHPStructuredEditor)) {
			return;
		}

		PHPStructuredEditor phpEditor = (PHPStructuredEditor) targetEditor;

		IFile file = ((IFileEditorInput) phpEditor.getEditorInput()).getFile();

		IStructuredModel model = null;
		try {
			// get the document
			model = StructuredModelManager.getModelManager().getExistingModelForEdit(file);
			IStructuredDocument structuredDocument = model.getStructuredDocument();

			IModelElement source = phpEditor.getModelElement();

			if (!(source instanceof ISourceModule)) {
				MessageDialog.openError(this.shell, "Error", "Unexpected Error"); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}

			// get the selection offsets
			ITextSelection fTextSelection = (ITextSelection) phpEditor.getSelectionProvider().getSelection();
			int startOffset = fTextSelection.getOffset();
			int length = fTextSelection.getLength();

			ExtractVariableRefactoring refactoring;
			try {
				refactoring = new ExtractVariableRefactoring((ISourceModule) source, structuredDocument, startOffset,
						length);
				new RefactoringStarter().activate(refactoring, new ExtractVariableWizard(refactoring), shell,
						PHPRefactoringUIMessages.getString("ExtractVariableActionDelegate.0"), false); //$NON-NLS-1$

			} catch (CoreException e) {
				MessageDialog.openError(this.shell, "Error", e.getMessage()); //$NON-NLS-1$
			}

		} finally {
			if (model != null) {
				model.releaseFromEdit();
			}
			targetEditor = null;
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	public int tst() {
		return 0;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void init(IWorkbenchWindow window) {

	}

}
