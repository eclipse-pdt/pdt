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
package org.eclipse.php.composer.internal.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class InjectAutoloadHandler extends AbstractHandler {

	protected static final String AUTOLOAD_PHP = "autoload.php"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorInput input = HandlerUtil.getActiveEditorInput(event);
		if (!(input instanceof IFileEditorInput)) {
			return null;
		}
		IFileEditorInput fileEditor = (IFileEditorInput) input;
		IFile file = fileEditor.getFile();
		if (file == null) {
			return null;
		}
		IPath filePath = file.getFullPath();
		IProject project = file.getProject();
		IContainer vendor = ComposerService.getVendor(project);
		IFile autoload = vendor.getFile(new Path(AUTOLOAD_PHP));
		if (autoload == null || autoload.exists() == false) {
			return null;
		}
		IPath autoloadPath = autoload.getFullPath();
		IPath relativeTo = autoloadPath.makeRelativeTo(filePath);
		if (relativeTo == null || relativeTo.segmentCount() <= 1) {
			return null;
		}
		relativeTo = relativeTo.removeFirstSegments(1);
		insertText("require_once __DIR__ . '/" + relativeTo.toString() + "';"); //$NON-NLS-1$ //$NON-NLS-2$
		return null;
	}

	private void doInsert(ITextEditor editor, String text) {
		ISelectionProvider selectionProvider = editor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();

		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;

			int offset = textSelection.getOffset();
			IDocumentProvider dp = editor.getDocumentProvider();
			IDocument doc = dp.getDocument(editor.getEditorInput());
			try {
				doc.replace(offset, 0, text);
			} catch (BadLocationException e) {
				ComposerUIPlugin.logError(e);
			}
		}

	}

	private void insertText(String text) {
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (editor instanceof MultiPageEditorPart) {
			MultiPageEditorPart multiEditor = (MultiPageEditorPart) editor;
			if (multiEditor.getSelectedPage() instanceof ITextEditor) {
				doInsert((ITextEditor) multiEditor.getSelectedPage(), text);
			}
		} else if (editor instanceof ITextEditor) {
			doInsert((ITextEditor) editor, text);
		}
	}

}
