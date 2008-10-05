/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.task.ITaskReporter;
import org.eclipse.dltk.compiler.task.ITodoTaskPreferences;
import org.eclipse.dltk.compiler.task.TodoTaskAstParser;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.validators.core.IBuildParticipant;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;

/**
 * This class analyzes the document on build (as a build participant), identifies the task tokens and report them.
 * @author Eden K.,2008
 *
 */
public class PHPTodoTaskAstParser extends TodoTaskAstParser implements IBuildParticipant {

	public PHPTodoTaskAstParser(ITodoTaskPreferences preferences) {
		super(preferences);
	}

	public void build(ISourceModule module, ModuleDeclaration astModuleDeclaration, IProblemReporter reporter) throws CoreException {
		if (!isValid()) {
			return;
		}
		final ITaskReporter taskReporter = (ITaskReporter) reporter.getAdapter(ITaskReporter.class);
		if (taskReporter == null) {
			return;
		}

		IStructuredModel model = null;
		try {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(module.getPath());
			model = StructuredModelManager.getModelManager().getExistingModelForRead(file);
			IStructuredDocumentRegion[] sdRegions = model.getStructuredDocument().getStructuredDocumentRegions();
			for (IStructuredDocumentRegion structuredDocumentRegion : sdRegions) {
				// analyze only php regions
				ITextRegionList textRegions = structuredDocumentRegion.getRegions();
				for (int i = 0; i < textRegions.size(); i++) {
					ITextRegion textRegion = textRegions.get(i);
					// only handle PHP regions
					if (textRegion.getType().indexOf("PHP") == -1) { //$NON-NLS-1$
						break;
					}
					// parse the actual script
					if (textRegion instanceof PhpScriptRegion) {
						PhpScriptRegion scriptRegion = (PhpScriptRegion) textRegion;
						try {
							//go over the text regions and look for the tasks
							ITextRegion[] phpTokens = scriptRegion.getPhpTokens(textRegion.getStart(), textRegion.getLength());
							for (int j = 0; j < phpTokens.length; j++) {
								ITextRegion phpToken = phpTokens[j]; 
								if (phpToken.getType().equals(PHPRegionTypes.TASK)) {
									// get the task information from the document 
									IStructuredDocument document = structuredDocumentRegion.getParentDocument();
									int offset = textRegion.getStart() + phpToken.getStart();
									int length = phpToken.getLength();
									// get the actual message for this task - if any
									if (j + 1 < phpTokens.length) {
										ITextRegion phpNextToken = phpTokens[j + 1];
										phpNextToken.getType().equals(PHPRegionTypes.TASK);
										//length = length + phpNextToken.getLength();
									}
									String taskStr = document.get(offset, length);
									taskStr = taskStr.trim();
									int lineNumber = document.getLineOfOffset(offset);
									
									// report the task
									taskReporter.reportTask(taskStr, lineNumber, TaskTag.PRIORITY_NORMAL, offset, length);
								}
							}
						} catch (BadLocationException e) {
							Logger.logException(CoreMessages.PHPTodoTaskAstParser_0, e);
						}

					}
				}
			}
		} finally {
			if (model != null) {
				model.releaseFromRead();
			}
		}

	}

}
