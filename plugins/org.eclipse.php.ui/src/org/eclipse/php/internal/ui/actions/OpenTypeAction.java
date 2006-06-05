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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.dialogs.openType.OpenPhpTypeDialog;
import org.eclipse.php.ui.util.ExceptionHandler;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;


public class OpenTypeAction implements IWorkbenchWindowActionDelegate {

	//---- IWorkbenchWindowActionDelegate ------------------------------------------------

	public void run(IAction action) {
		Shell parent = PHPUiPlugin.getActiveWorkbenchShell();
		OpenPhpTypeDialog dialog = new OpenPhpTypeDialog(parent);

		int result= dialog.open();
		if (result != IDialogConstants.OK_ID) {
			return;
		}
		
		CodeData codeData = dialog.getSelectedElement();
		openEditor(codeData);
	}

	protected void openEditor(CodeData codeData) {
		UserData userData = codeData.getUserData();
		String fileName = userData.getFileName();
		IFile file = PHPUiPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
		IWorkbenchPage activePage = PHPUiPlugin.getActivePage();
		if (activePage == null) {
			return;
		}
		
		IEditorPart editorPart;
		try {
			editorPart = IDE.openEditor(activePage, file, true);
		} catch (PartInitException e) {
			ExceptionHandler.handle(e, ActionMessages.OpenTypeAction_errorTitle, ActionMessages.OpenTypeAction_errorMessage);
			return;
		}
		
		revealInEditor(editorPart, userData, codeData);
	}

	private void revealInEditor(IEditorPart editorPart, UserData userData, CodeData codeData) {
		revealInEditor(editorPart, userData.getStopPosition(), codeData.getName().length());
	}

	private void revealInEditor(IEditorPart editorPart, int startPosition, int length) {
		//assert editorPart instanceof ITextEditor;
		((ITextEditor)editorPart).selectAndReveal(startPosition, length);
	}

	public void dispose() {
		// do nothing.
	}

	public void init(IWorkbenchWindow window) {
		// do nothing.
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing. Action doesn't depend on selection.
	}

}
