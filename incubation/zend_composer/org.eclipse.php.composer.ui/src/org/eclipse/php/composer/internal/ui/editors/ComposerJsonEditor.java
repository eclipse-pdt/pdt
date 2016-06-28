/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerJsonEditor extends FormEditor implements IResourceChangeListener {

	public final static String ID = "org.eclipse.php.composer.internal.ui.editors.ComposerJsonEditor"; //$NON-NLS-1$

	private GeneralPage generalPage;
	private SourcePage sourcePage;

	private IDocumentProvider documentProvider;

	public ComposerJsonEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		documentProvider = new FileDocumentProvider();
	}

	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		try {
			documentProvider.connect(editorInput);
		} catch (CoreException e) {
			throw new PartInitException(new Status(IStatus.ERROR, ComposerUIPlugin.PLUGIN_ID, e.getMessage(), e));
		}
		IFileEditorInput input = (IFileEditorInput) editorInput;
		setInput(input);
		setPartName(input.getFile().getParent().getName());
		super.init(site, editorInput);
	}

	public void doSave(IProgressMonitor monitor) {
		sourcePage.doSave(monitor);
		generalPage.doSave(monitor);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the
	 * text for page 0's tab, and updates this multi-page editor's input to
	 * correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(1);
		editor.doSaveAs();
		setPageText(1, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) getEditorInput()).getFile().getProject().equals(event.getResource())) {
							close(false);
							ResourcesPlugin.getWorkspace().removeResourceChangeListener(generalPage);
						}
					}
				}
			});
		}
	}

	@Override
	protected void addPages() {
		IFileEditorInput input = (IFileEditorInput) getEditorInput();
		generalPage = new GeneralPage(this, input);
		sourcePage = new SourcePage(this);
		try {
			addPage(generalPage);
			addPage(sourcePage, getEditorInput());
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), Messages.ComposerJsonEditor_SourceEditorError, null,
					e.getStatus());
		}
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	protected IDocumentProvider getDocumentProvider() {
		return documentProvider;
	}

}
