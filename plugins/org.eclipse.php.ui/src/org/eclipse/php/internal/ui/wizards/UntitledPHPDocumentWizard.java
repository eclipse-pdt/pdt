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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.ui.*;

/**
 * This "Pageless" Wizard is the main entry point for creating a new Untitled PHP Document.
 * This Wizard has no UI but the Wizard shortcut that is added to the New-> wizards.
 * @author yaronm
 */
public class UntitledPHPDocumentWizard extends Wizard implements INewWizard {

	private IWorkbenchWindow fWindow;
	private final static String UNTITLED_EDITOR_ID = "org.eclipse.php.untitledPhpEditor";
	private final static String UNTITLED_FOLDER_PATH = "/Untitled_Documents";
	private final static String UNTITLED_PHP_DOC_PREFIX = "PHPDocument";

	public UntitledPHPDocumentWizard() {
	}

	/**
	 * Overloaded constructor to be called from the Toolbar's Action button
	 * @param window
	 */
	public UntitledPHPDocumentWizard(IWorkbenchWindow window) {
		fWindow = window;
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		fWindow = null;
	}

	/*
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		IPath stateLocation = PHPUiPlugin.getDefault().getStateLocation();
		IPath path = stateLocation.append(UNTITLED_FOLDER_PATH);
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(path);
		IEditorInput input = new NonExistingPHPFileEditorInput(fileStore, UNTITLED_PHP_DOC_PREFIX);
		IWorkbenchPage page = fWindow.getActivePage();
		try {
			page.openEditor(input, UNTITLED_EDITOR_ID);
		} catch (PartInitException e) {
			Logger.logException(e);
			return false;
		}
		return true;
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		fWindow = workbench.getActiveWorkbenchWindow();
	}
}