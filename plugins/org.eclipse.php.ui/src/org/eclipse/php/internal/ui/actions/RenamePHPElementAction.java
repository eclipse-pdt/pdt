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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.ui.IWorkbenchSite;


public class RenamePHPElementAction extends SelectionDispatchAction {

	protected PHPStructuredEditor fEditor;

	public RenamePHPElementAction(IWorkbenchSite site) {
		super(site);
		update(getSelection());
	}

	public RenamePHPElementAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(SelectionConverter.canOperateOn(fEditor));
		update(getSelection());
		setEnabled(true);
	}

	//---- Structured selection ------------------------------------------------

	public void selectionChanged(IStructuredSelection selection) {
//		try {
//			if (selection.size() == 1) {
//				setEnabled(canEnable(selection));
//				return;
//			}
//		} catch (CoreException e) {
//			PHPUiPlugin.log(e);
//		}
//		setEnabled(false);
		setEnabled(true);
	}

	private static boolean canEnable(IStructuredSelection selection) throws CoreException {
		PHPCodeData element = getPHPElement(selection);
		if (element == null)
			return false;
		return isRenameAvailable(element);
	}

	private static PHPCodeData getPHPElement(IStructuredSelection selection) {
		if (selection.size() != 1)
			return null;
		Object first = selection.getFirstElement();
		if (!(first instanceof PHPCodeData))
			return null;
		return (PHPCodeData) first;
	}

	public void run(IStructuredSelection selection) {
		PHPCodeData element = getPHPElement(selection);
		if (element == null)
			return;
		try {
			run(element);
		} catch (CoreException e) {
			ExceptionHandler.handle(e, PHPUIMessages.RenamePHPElementAction_name, PHPUIMessages.RenamePHPElementAction_exception);
		}
	}

	//---- text selection ------------------------------------------------------------

	public void selectionChanged(ITextSelection selection) {
		setEnabled(true);
	}

	public void run(ITextSelection selection) {
		try {
			PHPCodeData element = getPHPElement();
			if (element != null && isRenameAvailable(element)) {
				run(element);
				return;
			}
		} catch (CoreException e) {
			ExceptionHandler.handle(e, PHPUIMessages.RenamePHPElementAction_name, PHPUIMessages.RenamePHPElementAction_exception);
		}
		MessageDialog.openInformation(getShell(), PHPUIMessages.RenamePHPElementAction_name, PHPUIMessages.RenamePHPElementAction_not_available);
	}

	public boolean canRun() {
		PHPCodeData element = getPHPElement();
		if (element == null)
			return false;
		try {
			return isRenameAvailable(element);
		} catch (CoreException e) {
			PHPUiPlugin.log(e);
		}
		return false;
	}

	private PHPCodeData getPHPElement() {
		PHPCodeData[] elements = SelectionConverter.codeResolveHandled(fEditor, getShell(), PHPUIMessages.RenamePHPElementAction_name);
		if (elements == null || elements.length != 1)
			return null;
		return elements[0];
	}

	//---- helper methods -------------------------------------------------------------------

	private void run(PHPCodeData element) throws CoreException {
		if (!ActionUtils.isProcessable(getShell(), element))
			return;
		throw new RuntimeException("implement me");
	}

	private static boolean isRenameAvailable(PHPCodeData element) throws CoreException {
		// do something here
		return false;
	}

}
