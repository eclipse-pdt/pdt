/*******************************************************************************
 * Copyright (c) 2007 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveAsDialog;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.internal.editors.text.NLSUtility;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * This editor was created to handle editing of untitled PHP documents (with no existing files)
 * @author yaronm
 */
public class UntitledPHPEditor extends PHPStructuredEditor {

	/**
	 * Overrides
	 */
	public void doSave(IProgressMonitor progressMonitor) {
		performSaveAs(progressMonitor);
	}

	/**
	 * Overrides
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		final IEditorInput input = getEditorInput();

		IDocumentProvider provider = getDocumentProvider();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalName(input.getName());
		dialog.create();

		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		IPath newPath = dialog.getResult();
		if (newPath == null) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(newPath);
		final IEditorInput newInput = new FileEditorInput(file);

		if (provider == null) {
			// editor has programmatically been  closed while the dialog was open
			return;
		}

		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			provider.saveDocument(progressMonitor, newInput, provider.getDocument(input), true);
			success = true;

		} catch (CoreException x) {
			Logger.logException(x);
			final IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				String title = PHPUIMessages.getString("UntitledPHPEditor_saveError"); //$NON-NLS-1$
				String msg = NLSUtility.format(PHPUIMessages.getString("UntitledPHPEditor_documentCannotBeSaved"), x.getMessage()); //$NON-NLS-1$
				MessageDialog.openError(shell, title, msg);
			}
		} finally {
			// 1. close the untitled document 
			// 2. open the saved file from its new path
			if (newPath != null) {
				close(false);
				try {
					EditorUtility.openInEditor(newPath.toString(), 0);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
		}
		
		deleteUntitledStorageFile();
		
		if (progressMonitor != null)
			progressMonitor.setCanceled(!success);

		return;
	}
	
	@Override
	public void dispose() {
		deleteUntitledStorageFile();
		super.dispose();
	}

	/**
	 * This method removes file created as an input for untitled php file, from external storage
	 */
	private void deleteUntitledStorageFile() {
		IPath oldPath = ((NonExistingPHPFileEditorInput) getEditorInput()).getPath(getEditorInput());
		File oldFile = new File(oldPath.toOSString());
		if (oldFile.exists() && oldFile.canWrite()) {
			if (!oldFile.delete()) {
				Logger.log(Logger.WARNING, PHPUIMessages.getString("UntitledPHPEditor_deleteFailed")); //$NON-NLS-1$
			}
		}
	}
}