/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Open a test in the PHP editor and reveal a given line
 */
public class OpenEditorAtLineAction extends OpenEditorAction {

	private String fMethodName;

	public OpenEditorAtLineAction(final String label, final PHPUnitView testRunner, final String fileName,
			final int lineNumber, final String methodName) {
		super(label, testRunner, null, fileName, lineNumber);
		fMethodName = methodName;
	}

	@Override
	protected IModelElement findElement(final IProject project, final String elementName, final String fileName) {
		IModelElement element = null;
		if (elementName != null) {
			element = findClass(project, elementName, fileName);
			return element;
		}

		IWorkspaceRoot root = project.getWorkspace().getRoot();
		IFile iFile = root.getFile(new Path(fileName));
		if (iFile == null || !iFile.exists()) {
			iFile = root.getFileForLocation(new Path(fileName));
		}
		if (iFile != null && iFile.exists()) {
			element = DLTKCore.create(iFile);
		}
		if (element == null) {
			IFileHandle file = EnvironmentPathUtils.getFile(LocalEnvironment.getInstance(), new Path(fileName));
			if (file != null) {
				IPath fullPath = file.getFullPath();
				HandleFactory fac = new HandleFactory();
				IDLTKSearchScope scope = DLTKSearchScopeFactory.getInstance().createWorkspaceScope(true,
						PHPLanguageToolkit.getDefault());
				element = fac.createOpenable(fullPath.toString(), scope);
			}
		}
		return element;
	}

	@Override
	public boolean isEnabled() {
		if (fFileName == null || fFileName.isEmpty()) {
			return false;
		}

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fFileName));
		if (file != null) {
			return true;
		}
		return false;

	}

	@Override
	protected void reveal(final ITextEditor textEditor) {
		if (fLineNumber >= 0) {
			try {
				final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
				int startPosition = document.getLineOffset(fLineNumber - 1);
				int length = document.getLineLength(fLineNumber - 1);
				final String line = document.get(startPosition, length);

				int methodPosition;
				if (fMethodName != null && (methodPosition = line.indexOf(fMethodName)) == line.lastIndexOf(fMethodName)
						&& methodPosition >= 0) {
					startPosition += methodPosition;
					length = fMethodName.length();
				} else {
					final String trimmedLine = line.trim();
					startPosition += line.indexOf(trimmedLine);
					length = trimmedLine.length();
				}
				textEditor.selectAndReveal(startPosition, length);
			} catch (final BadLocationException x) {
				// marker refers to invalid text position -> do nothing
			}
		}
	}
}
