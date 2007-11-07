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

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.ui.*;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This "Pageless" Wizard is the main entry point for creating a new Untitled PHP Document.
 * This Wizard has no UI but the Wizard shortcut that is added to the New-> wizards.
 * @author yaronm
 */
public class UntitledPHPDocumentWizard extends Wizard implements INewWizard {

	private IWorkbenchWindow fWindow;
	private final static String UNTITLED_EDITOR_ID = "org.eclipse.php.untitledPhpEditor"; //$NON-NLS-1$
	private final static String UNTITLED_FOLDER_PATH = "/Untitled_Documents"; //$NON-NLS-1$
	private final static String UNTITLED_PHP_DOC_PREFIX = "PHPDocument"; //$NON-NLS-1$

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
		NonExistingPHPFileEditorInput input = new NonExistingPHPFileEditorInput(fileStore, UNTITLED_PHP_DOC_PREFIX);
		File f = input.getPath().toFile();
		f.deleteOnExit();
		IWorkbenchPage page = fWindow.getActivePage();
		try {
			IEditorPart editor = page.openEditor(input, UNTITLED_EDITOR_ID);
			ITextEditor textEditor = null;
			if (editor instanceof ITextEditor) {
				textEditor = (ITextEditor) editor;
			}
			PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(f.getAbsolutePath());
			if (fileData == null || textEditor == null) {
				return true;
			}
			//find first php block
			if ((fileData.getPHPBlocks() != null) && (fileData.getPHPBlocks().length > 0)) {
				// calculate length of the start tag
				UserData startTag = fileData.getPHPBlocks()[0].getPHPStartTag();
				int startTagEndPosition = startTag.getEndPosition() - startTag.getStartPosition();
				// handle short tag - '<?'
				if (startTagEndPosition == 2) {
					int startTagLineNum = startTag.getStopLine();
					IEditorInput editorInput = textEditor.getEditorInput();
					IDocument document = textEditor.getDocumentProvider().getDocument(editorInput);
					// calculate the length of the line delimiter for the start tag line
					int lineDelimLength = document.getLineDelimiter(startTagLineNum).length();
					// add to the tag length, so we jump to the next line
					startTagEndPosition += lineDelimLength;
					
				}
				// position the cursor at the end of the start tag
				textEditor.selectAndReveal(startTagEndPosition, 0);
			}
		} catch (PartInitException e) {
			Logger.logException(e);
			return false;
		} catch (BadLocationException e) {
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