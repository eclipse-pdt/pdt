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

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveAsDialog;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.internal.editors.text.NLSUtility;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * This editor was created to handle editing of untitled PHP documents (with no existing files)
 * @author yaronm
 */
public class UntitledPHPEditor extends PHPStructuredEditor {

	/**
	 * This static member holds the history of saved Untitled documents
	 * You can retrieve the saved workspace file's path (real) by giving its old (dummy) one
	 * It is recommended that once you use it, delete the entry from this map
	 * to prevent memory increase. The reason we use a map is since the user can save
	 * multiple Untitled documents when performing Saving All
	 */
	public static HashMap<IPath, IPath> latestSavedUntitled = new HashMap<IPath, IPath>();

	/**
	 * Overrides
	 */
	public void doSave(IProgressMonitor progressMonitor) {
		performSaveAs(progressMonitor);
	}

	/**
	 * This is an override in order to provide the PHP template content
	 * for the Untitled PHP document
	 */
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		final TextFileDocumentProvider documentProvider = (TextFileDocumentProvider) getDocumentProvider();
		if (documentProvider == null){
			return;
		}
		final IDocument document = documentProvider.getDocument(input);
		String content = loadPHPTemplate();
		document.set(content);
		documentProvider.saveDocument(null, input, document, true);
	}

	//Load the last template name used in New HTML File wizard.
	private String loadPHPTemplate() {
		String templateName = getPreferenceStore().getString(PreferenceConstants.NEW_PHP_FILE_TEMPLATE);
		if (templateName == null || templateName.length() == 0) {
			return ""; //$NON-NLS-1$
		}

		PHPTemplateStore fTemplateStore = (PHPTemplateStore) PHPUiPlugin.getDefault().getTemplateStore();
		Template template = fTemplateStore.findTemplate(templateName);
		if (template != null) {
			return template.getPattern();
		}
		return "";
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

		IPath newPath = null;

		SaveAsDialog dialog = new SaveAsDialog(shell);

		IPath oldPath = ((NonExistingPHPFileEditorInput) input).getPath();
		String originalName = oldPath.lastSegment();
		dialog.setOriginalName(originalName);
		dialog.create();

		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		newPath = dialog.getResult();
		if (newPath == null) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(newPath);
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
			Logger.logException(x);
			final IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				String title = PHPUIMessages.getString("UntitledPHPEditor_saveError"); //$NON-NLS-1$
				String msg = NLSUtility.format(PHPUIMessages.getString("UntitledPHPEditor_documentCannotBeSaved"), x.getMessage()); //$NON-NLS-1$
				MessageDialog.openError(shell, title, msg);
			}
		} finally {
			latestSavedUntitled.put(oldPath, newPath);
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

		if (progressMonitor != null)
			progressMonitor.setCanceled(!success);

		return;
	}
}
