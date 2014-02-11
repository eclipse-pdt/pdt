/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [427907]
 *******************************************************************************/
package org.eclipse.php.internal.core.validation;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.preferences.TaskTagsProvider;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.xml.core.internal.parser.ContextRegionContainer;

/**
 * This Validator creates the task markers that will eventually show up in the
 * task view
 * 
 * @author Eden K.,2008
 * 
 */
public class PHPTodoTaskValidator implements IBuildParticipant {

	protected TaskTag[] taskTags = null;

	public void build(IBuildContext context) throws CoreException {
		if (context.get(IBuildContext.ATTR_MODULE_DECLARATION) == null
				|| !(context.get(IBuildContext.ATTR_MODULE_DECLARATION) instanceof PHPModuleDeclaration)) {
			return;
		}
		if (taskTags == null) {
			TaskTagsProvider taskTagsProvider = TaskTagsProvider.getInstance();
			getTaskTags(context.getSourceModule().getScriptProject()
					.getProject(), taskTagsProvider);
		}
		try {
			IStructuredDocument doc = StructuredModelManager
					.getModelManager()
					.createStructuredDocumentFor(
							context.getFileName(),
							String.copyValueOf(context.getContentsAsCharArray()),
							null);
			validateContent(context, doc);
		} catch (IOException e) {
		}

	}

	/**
	 * Search for tasks in the validated file and create a marker for each task
	 * found
	 * 
	 * TODO: Write fast scanner for this, instead of using document partitioner
	 */
	public void validateContent(IBuildContext context, IStructuredDocument doc) {
		if (doc == null) {
			return;
		}

		try {

			// collect the tasks info and report
			IStructuredDocumentRegion[] sdRegions = doc
					.getStructuredDocumentRegions();
			for (IStructuredDocumentRegion structuredDocumentRegion : sdRegions) {

				IStructuredDocument document = structuredDocumentRegion
						.getParentDocument();

				ITextRegionList textRegions = structuredDocumentRegion
						.getRegions();
				for (int i = 0; i < textRegions.size(); i++) {
					ITextRegion textRegion = textRegions.get(i);
					int regionStart = structuredDocumentRegion
							.getStartOffset(textRegion);

					// special handling for php tags inside html
					if (textRegion instanceof ContextRegionContainer) {
						textRegion = extractPhpScriptRegion(textRegion);
						regionStart += textRegion.getStart();
					}
					// parse the actual script
					if (textRegion instanceof PhpScriptRegion) {
						PhpScriptRegion scriptRegion = (PhpScriptRegion) textRegion;
						try {

							// go over the text regions and look for the tasks
							ITextRegion[] phpTokens = scriptRegion
									.getPhpTokens(0, textRegion.getLength());
							for (int j = 0; j < phpTokens.length; j++) {
								ITextRegion phpToken = phpTokens[j];
								if (PHPRegionTypes.PHPDOC_TODO.equals(phpToken
										.getType())) {
									// get the task information from the
									// document
									int offset = regionStart
											+ phpToken.getStart();
									int length = phpToken.getLength();

									String taskKeyword = document.get(offset,
											phpToken.getLength());
									int priority = getTaskPriority(taskKeyword);

									// get the actual message for this task - if
									// any
									if (j + 1 < phpTokens.length) {
										for (int k = j + 1; k < phpTokens.length; k++) {
											ITextRegion phpNextToken = phpTokens[k];
											if (PHPRegionTypes.PHPDOC_TODO
													.equals(phpNextToken
															.getType())) {
												break;
											}
											length = length
													+ phpNextToken.getLength();
										}
									}

									try {
										reportTask(context, document, offset,
												length, priority);
									} catch (CoreException e) {
										Logger.logException(
												"Failed creating task", e); //$NON-NLS-1$
									}
								}
							}
						} catch (BadLocationException e) {
							Logger.logException(CoreMessages
									.getString("PHPTodoTaskAstParser_0"), e); //$NON-NLS-1$
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.logException(
					"Failed validating file for tasks " + context.getFileName(), e); //$NON-NLS-1$
		}

	}

	/**
	 * Get the task tags from the preferences
	 * 
	 * @param project
	 * @param taskTagsProvider
	 */
	private void getTaskTags(IProject project, TaskTagsProvider taskTagsProvider) {
		taskTags = taskTagsProvider.getProjectTaskTags(project);
		if (taskTags == null) {
			taskTags = taskTagsProvider.getWorkspaceTaskTags();
		}
	}

	/**
	 * Get the task priority according to the preferences
	 * 
	 * @return
	 */
	private int getTaskPriority(String taskStr) {

		String taskTagLowerCase = taskStr.toLowerCase();
		for (int i = 0; i < taskTags.length; i++) {
			TaskTag taskTag = taskTags[i];
			if (taskTag.getTag().toLowerCase().equals(taskTagLowerCase)) {
				return taskTag.getPriority();
			}
		}

		return TaskTag.PRIORITY_NORMAL;
	}

	/**
	 * Reports the task
	 * 
	 * @param document
	 * @param taskReporter
	 * @param offset
	 * @param length
	 * @param priority
	 * @throws BadLocationException
	 * @throws CoreException
	 */
	private void reportTask(IBuildContext context,
			IStructuredDocument document, int offset, int length, int priority)
			throws BadLocationException, CoreException {
		int lineNumber = document.getLineOfOffset(offset);

		String taskStr = getTaskStr(document, lineNumber, offset, length);
		// the end of the string to be highlighted
		int charEnd = offset + taskStr.length();

		context.getProblemReporter().reportProblem(
				new ValidationProblem(taskStr, ProblemIdentifier.TASK,
						priority, offset, charEnd, lineNumber + 1));
	}

	/**
	 * Gets the Task message from the document
	 * 
	 * @param document
	 * @param lineNumber
	 * @param offset
	 * @param length
	 * @return
	 * @throws BadLocationException
	 */
	private String getTaskStr(IStructuredDocument document, int lineNumber,
			int offset, int length) throws BadLocationException {
		// get line info to identify the end of the task
		IRegion lineInformation = document.getLineInformation(lineNumber);
		int lineStart = lineInformation.getOffset();
		int lineEnd = lineStart + lineInformation.getLength();

		// identify the actual end of the task: either end of line or end of the
		// token
		// we could have 2 tasks in the same line
		int tokenEnd = offset + length;
		int taskEnd = Math.min(tokenEnd, lineEnd);

		String taskStr = document.get(offset, taskEnd - offset);
		taskStr = taskStr.trim();

		return taskStr;

	}

	/**
	 * Given a ContextRegionContainer, looks for the PHPScriptRegion in it and
	 * return it. If the container does not contain a PHPScript region, just
	 * return the given TextRegion
	 * 
	 * @param textRegion
	 * @return the PhpScript textRegion
	 */
	private ITextRegion extractPhpScriptRegion(ITextRegion textRegion) {
		ContextRegionContainer container = (ContextRegionContainer) textRegion;
		ITextRegionList containerRegions = container.getRegions();
		for (int z = 0; z < containerRegions.size(); z++) {
			ITextRegion containerTextRegion = containerRegions.get(z);
			if (containerTextRegion instanceof PhpScriptRegion) {
				return containerTextRegion;
			}
		}
		return textRegion;
	}
}
