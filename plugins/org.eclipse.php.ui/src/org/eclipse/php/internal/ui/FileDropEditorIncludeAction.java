/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.ast.nodes.AST;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Include;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathUtils;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.sse.ui.internal.FileDropAction;

/**
 * Adds a DND capabilities for include statements
 */
public class FileDropEditorIncludeAction extends FileDropAction {

	public FileDropEditorIncludeAction() {
	}

	public boolean run(DropTargetEvent event, IEditorPart targetEditor) {

		if (!(targetEditor instanceof PHPStructuredEditor))
			return super.run(event, targetEditor);

		final String[] fileNames = (String[]) event.data;
		if (fileNames == null || fileNames.length == 0) {
			return false;
		}
		IModelElement editorElement = ((PHPStructuredEditor) targetEditor)
				.getModelElement();
		if (editorElement != null) {
			ISourceModule sourceModule = ((ModelElement) editorElement)
					.getSourceModule();
			ASTParser parser = ASTParser.newParser(sourceModule);

			Program program;
			try {
				program = parser.createAST(null);
				program.recordModifications();

				AST ast = program.getAST();
				IDocument document = ((PHPStructuredEditor) targetEditor)
						.getDocument();
				for (int i = 0; i < fileNames.length; ++i) {

					// resolve the relative path from include path
					String relativeLocationFromIncludePath = getFileName(
							fileNames[i], sourceModule);

					if (relativeLocationFromIncludePath != null) {
						Include include = ast.newInclude(ast.newScalar("'" //$NON-NLS-1$
								+ relativeLocationFromIncludePath.toString()
								+ "'"), Include.IT_REQUIRE_ONCE); //$NON-NLS-1$
						program.statements().add(i,
								ast.newExpressionStatement(include));
						TextEdit edits = program.rewrite(document, null);
						edits.apply(document);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// return false means the following drop action will be executed.
		return false;
	}

	/**
	 * @param sourceModule
	 * @return
	 */
	private String getFileName(String input, IModelElement sourceModule) {
		final IFile fileForLocation = ResourcesPlugin.getWorkspace().getRoot()
				.getFileForLocation(new Path(input));
		if (fileForLocation == null) {
			return null;
		}
		final IPath relativeLocationFromIncludePath = IncludePathUtils
				.getRelativeLocationFromIncludePath(sourceModule
						.getScriptProject(), DLTKCore.create(fileForLocation));

		if (relativeLocationFromIncludePath.isEmpty()) {
			return null;
		}
		return relativeLocationFromIncludePath.toString();
	}
}
