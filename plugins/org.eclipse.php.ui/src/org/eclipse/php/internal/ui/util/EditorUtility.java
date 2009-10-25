/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalScriptProject;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.actions.OpenLocalFileAction;

/**
 * Editor operations utility class. If some method is missing from this utility,
 * please refer to {@link org.eclipse.dltk.internal.ui.editor.EditorUtility}.
 */
public class EditorUtility {

	/**
	 * Returns an editor input according to the model element. This method
	 * differs from
	 * {@link org.eclipse.dltk.internal.ui.editor.EditorUtility#getEditorInput}
	 * by that that it supports files opened using File -> Open operation.
	 * 
	 * @throws ModelException
	 */
	public static final IEditorInput getEditorInput(IModelElement element)
			throws ModelException {
		while (element != null) {
			if (element instanceof ISourceModule) {
				final ISourceModule unit = (ISourceModule) element;
				if (unit.getScriptProject() instanceof ExternalScriptProject) {
					PHPStructuredEditor editorPart = (PHPStructuredEditor) findOpenEditor(new IEditorLookupCondition() {
						public boolean find(IEditorPart editorPart) {
							return editorPart instanceof PHPStructuredEditor
									&& ((PHPStructuredEditor) editorPart)
											.getModelElement().equals(unit);
						}
					});
					if (editorPart != null) {
						return editorPart.getEditorInput();
					}
				}
				break;
			}
			element = element.getParent();
		}
		return org.eclipse.dltk.internal.ui.editor.EditorUtility
				.getEditorInput(element);
	}

	/**
	 * Selects a PHP Element in an editor
	 * 
	 * @param editor
	 * @return the php editor (if exists) from the given editor NOTE: editors
	 *         that wants to work with PHP editor actions must implement the
	 *         getAdapter() method this way the actions pick the php editor...
	 */
	public static final PHPStructuredEditor getPHPStructuredEditor(
			final IWorkbenchPart editor) {
		return editor != null ? (PHPStructuredEditor) editor
				.getAdapter(PHPStructuredEditor.class) : null;
	}

	/**
	 * Returns PHP editor which corresponds to ITextViewer
	 * 
	 * @return php editor, or <code>null</code> if no editor found
	 */
	public static final PHPStructuredEditor getPHPEditor(
			final ITextViewer textViewer) {

		return (PHPStructuredEditor) findOpenEditor(new IEditorLookupCondition() {
			public boolean find(IEditorPart editorPart) {
				return editorPart instanceof PHPStructuredEditor
						&& ((PHPStructuredEditor) editorPart).getTextViewer()
								.getDocument() == textViewer.getDocument();
			}
		});
	}

	/**
	 * Opens local file in editor just like {@link OpenLocalFileAction} does.
	 * 
	 * @param filePath
	 *            Full path string of the local file
	 * @param lineNumber
	 *            Line number to reveal
	 * @throws CoreException
	 */
	public static IEditorPart openLocalFile(String filePath, int lineNumber)
			throws CoreException {

		IResource member = ResourcesPlugin.getWorkspace().getRoot().findMember(
				filePath);
		if (member instanceof IFile) {
			IEditorPart editor = org.eclipse.dltk.internal.ui.editor.EditorUtility
					.openInEditor(member, true);
			org.eclipse.dltk.internal.ui.editor.EditorUtility.revealInEditor(
					editor, lineNumber);
			return editor;
		}

		IPath path = new Path(filePath);
		String parentPath = path.removeLastSegments(1).toOSString();
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(
				new Path(parentPath));

		fileStore = fileStore.getChild(path.lastSegment());

		if (!fileStore.fetchInfo().isDirectory()
				&& fileStore.fetchInfo().exists()) {

			IWorkbenchPage page = DLTKUIPlugin.getActivePage();
			IEditorPart editorPart = null;
			try {
				editorPart = IDE.openEditorOnFileStore(page, fileStore);
				// if the open file request has a line number, try to set the
				// cursor on that line
				if (lineNumber >= 0) {
					org.eclipse.dltk.internal.ui.editor.EditorUtility
							.revealInEditor(editorPart, lineNumber - 1); // XXX:
					// look
					// why
					// we
					// have
					// to
					// provide
					// lineNumber
					// -1
				}
				return editorPart;

			} catch (PartInitException e) {
				Logger.logException("Failed to open local file", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	private static IEditorPart findOpenEditor(IEditorLookupCondition cond) {
		IWorkbenchPage workbenchpage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		// Check active editor, first:
		IEditorPart activeEditorPart = workbenchpage.getActiveEditor();
		if (cond.find(activeEditorPart)) {
			return activeEditorPart;
		}

		// Check other editors:
		IEditorReference[] editorReferences = workbenchpage
				.getEditorReferences();
		for (int i = 0; i < editorReferences.length; i++) {
			IEditorReference editorReference = editorReferences[i];
			IEditorPart editorPart = editorReference.getEditor(false);
			if (activeEditorPart != editorPart && cond.find(editorPart)) {
				return editorPart;
			}
		}
		return null;
	}

	private static interface IEditorLookupCondition {
		public boolean find(IEditorPart editorPart);
	}
}
