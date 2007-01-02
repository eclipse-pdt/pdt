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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.dialogs.openType.OpenPhpTypeDialog;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.php.ui.util.ExceptionHandler;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

public class OpenTypeAction implements IWorkbenchWindowActionDelegate {

	//---- IWorkbenchWindowActionDelegate ------------------------------------------------

	public void run(IAction action) {
		Shell parent = PHPUiPlugin.getActiveWorkbenchShell();
		OpenPhpTypeDialog dialog = new OpenPhpTypeDialog(parent);

		int result = dialog.open();
		if (result != IDialogConstants.OK_ID) {
			return;
		}

		PHPCodeData codeData = dialog.getSelectedElement();
		openEditor(codeData);
	}

	protected void openEditor(PHPCodeData codeData) {
		try {
			IEditorPart part = EditorUtility.openInEditor(codeData);
			EditorUtility.revealInEditor(part, codeData);
		} catch (PartInitException e) {
			ExceptionHandler.handle(e, PHPUIMessages.OpenTypeAction_errorTitle, PHPUIMessages.OpenTypeAction_errorMessage);
		}
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
