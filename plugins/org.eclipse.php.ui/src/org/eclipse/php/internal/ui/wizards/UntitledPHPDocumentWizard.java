/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore.CompiledTemplate;
import org.eclipse.ui.*;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * This "Pageless" Wizard is the main entry point for creating a new Untitled PHP Document.
 * This Wizard has no UI but the Wizard shortcut that is added to the New-> wizards.
 * @author ymazor, apeled
 */
public class UntitledPHPDocumentWizard extends Wizard implements INewWizard {

	private IWorkbenchWindow fWindow;
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

	private IFileStore queryFileStore() {
		IPath stateLocation = PHPUiPlugin.getDefault().getStateLocation();
		IPath path = stateLocation.append("/_" + new Object().hashCode()); //$NON-NLS-1$
		return EFS.getLocalFileSystem().getStore(path);
	}

	private IEditorInput createEditorInput(IFileStore fileStore) {
		return new NonExistingPHPFileEditorInput(fileStore, UNTITLED_PHP_DOC_PREFIX);
	}

	/*
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		IFileStore fileStore = queryFileStore();
		IEditorInput input = createEditorInput(fileStore);
		IWorkbenchPage page = fWindow.getActivePage();
		try {
			IEditorPart editor = page.openEditor(input, PHPUiConstants.PHP_EDITOR_ID);
			StructuredTextEditor textEditor = null;
			if (editor instanceof StructuredTextEditor) {
				textEditor = (StructuredTextEditor) editor;
			}
			//Load the last template name used in New PHP File wizard.
			String templateName = PHPUiPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.NEW_PHP_FILE_TEMPLATE);
			if (templateName == null || templateName.length() == 0) {
				return true;
			}
			TemplateStore templateStore = PHPUiPlugin.getDefault().getTemplateStore();
			Template template = templateStore.findTemplate(templateName);
			if (template == null) {
				return true;
			}
			// compile the template and insert the text into the new document
			CompiledTemplate compiledTemplate = PHPTemplateStore.compileTemplate(PHPUiPlugin.getDefault().getTemplateContextRegistry(), template);
			IDocumentProvider documentProvider = textEditor.getDocumentProvider();
			IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
			document.set(compiledTemplate.string);
			documentProvider.saveDocument(null, textEditor.getEditorInput(), document, true);
			textEditor.selectAndReveal(compiledTemplate.offset, 0);
		} catch (PartInitException e) {
			Logger.logException(e);
			return false;
		} catch (CoreException e) {
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