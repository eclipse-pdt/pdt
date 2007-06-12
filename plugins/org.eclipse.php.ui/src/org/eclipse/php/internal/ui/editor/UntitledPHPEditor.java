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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.dialogs.SaveAsDialog;
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
	 * Overrides the call to wst model and simply returns the Dummy document's file
	 */
	public IFile getFile() {
		NonExistingPHPFileEditorInput editorInput = (NonExistingPHPFileEditorInput) getEditorInput();
		return ExternalFilesRegistry.getInstance().getFileEntry(editorInput.getPath().toString());
	}

	/**
	 * Overrides
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		final IEditorInput input = getEditorInput();

		IDocumentProvider provider = getDocumentProvider();
		final IEditorInput newInput;

		IPath filePath = null;

		SaveUntitledDialog dialog = new SaveUntitledDialog(shell);

		String originalName = ((NonExistingPHPFileEditorInput)input).getPath().lastSegment();
		dialog.setOriginalName(originalName);
		dialog.create();

		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(filePath);
		newInput = new FileEditorInput(file);

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
			final IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				String title = "Save Error";
				String msg = NLSUtility.format("The document cannot be saved", x.getMessage());
				MessageDialog.openError(shell, title, msg);
			}
		} finally {
			//close the untitled document and open the save one from its target project
			if (filePath != null) {
				close(false);
				try {
					EditorUtility.openInEditor(filePath.toString(), 0);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
		}

		if (progressMonitor != null)
			progressMonitor.setCanceled(!success);

		return;
	}

	/**
	 * The "Save As" dialog for the untitled document
	 * @author yaronm
	 */
	class SaveUntitledDialog extends SaveAsDialog {
		public SaveUntitledDialog(Shell parentShell) {
			super(parentShell);
		}

		/**
		 * Simply overrides its base class's method to change the image
		 */
		protected Control createContents(Composite parent) {
			Control control = super.createContents(parent);
			setTitleImage(PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_WIZBAN_ADD_PHP_FILE));
			return control;
		}

	}
}
