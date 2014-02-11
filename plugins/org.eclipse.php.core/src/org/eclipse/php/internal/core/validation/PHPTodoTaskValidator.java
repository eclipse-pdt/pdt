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
import java.io.StringReader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.internal.core.documentModel.parser.PHPTokenizer;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.preferences.TaskTagsProvider;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.xml.core.internal.parser.ContextRegionContainer;

/**
 * This Validator creates the task markers that will eventually show up in the
 * task view
 * 
 * @author Eden K.,2008
 */
public class PHPTodoTaskValidator implements IBuildParticipant {

	protected TaskTag[] taskTags = null;

	public void build(IBuildContext context) throws CoreException {
		if (context.get(IBuildContext.ATTR_MODULE_DECLARATION) == null
				|| !(context.get(IBuildContext.ATTR_MODULE_DECLARATION) instanceof PHPModuleDeclaration)) {
			return;
		}

		try {
			validateContent(context);
		} catch (IOException e) {
			Logger.logException(e);
		} catch (Throwable e) {
			Logger.logException(e);
		}
	}

	/**
	 * Search for tasks in the validated file and create a marker for each task
	 * found
	 * 
	 * @throws IOException
	 */
	public void validateContent(IBuildContext context) throws IOException {
		final String sourceContents = context.getSourceContents();
		final IProject project = context.getSourceModule().getScriptProject()
				.getProject();

		if (taskTags == null) {
			synchronized (TaskTagsProvider.getInstance()) {
				TaskTagsProvider taskTagsProvider = TaskTagsProvider
						.getInstance();
				getTaskTags(project, taskTagsProvider);
			}
		}

		final PHPTokenizer tokenizer = new PHPTokenizer(new StringReader(
				sourceContents));
		tokenizer.setProject(project);

		ITextRegion textRegion;
		int regionStart = 0;
		while ((textRegion = tokenizer.getNextToken()) != null) {
			// special handling for php tags inside html
			regionStart = textRegion.getStart();

			if (textRegion instanceof ContextRegionContainer) {
				ITextRegion tmp = textRegion;
				textRegion = extractPhpScriptRegion(textRegion);
				if (tmp != textRegion) {
					regionStart += textRegion.getStart();
				}
			}
			if (!(textRegion instanceof PhpScriptRegion)) {
				continue;
			}
			PhpScriptRegion scriptRegion = (PhpScriptRegion) textRegion;
			int offset = regionStart;
			try {

				// go over the text regions and look for the tasks

				ITextRegion[] phpTokens = scriptRegion.getPhpTokens(0,
						textRegion.getLength());
				for (int j = 0; j < phpTokens.length; j++) {
					ITextRegion phpToken = phpTokens[j];
					if (PHPRegionTypes.PHPDOC_TODO.equals(phpToken.getType())) {
						// get the task information from the
						// document
						offset = regionStart + phpToken.getStart();
						int length = phpToken.getLength();

						int priority = getTaskPriority(sourceContents
								.substring(offset,
										offset + phpToken.getLength()));

						// get the actual message for this task - if
						// any
						if (j + 1 < phpTokens.length) {
							for (int k = j + 1; k < phpTokens.length
									&& (PHPRegionTypes.PHPDOC_COMMENT
											.equals(phpTokens[k].getType())
											|| PHPRegionTypes.PHP_COMMENT
													.equals(phpTokens[k]
															.getType()) || PHPRegionTypes.PHP_LINE_COMMENT
												.equals(phpTokens[k].getType())); k++) {
								length += phpTokens[k].getLength();
							}
						}

						try {
							reportTask(context, sourceContents, offset, length,
									priority);
						} catch (CoreException e) {
							Logger.logException("Failed creating task", e); //$NON-NLS-1$
						}
					}
				}
			} catch (BadLocationException e) {
				Logger.logException(
						CoreMessages.getString("PHPTodoTaskAstParser_0"), e); //$NON-NLS-1$
			}

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
	private void reportTask(IBuildContext context, String sourceContents,
			int offset, int length, int priority) throws BadLocationException,
			CoreException {
		int lineNumber = context.getLineTracker().getLineNumberOfOffset(offset);
		String taskStr = getTaskStr(context, sourceContents, lineNumber,
				offset, length);
		// the end of the string to be highlighted
		int charEnd = offset + taskStr.length();

		context.getProblemReporter().reportProblem(
				new ValidationProblem(taskStr, ProblemIdentifier.TASK,
						priority, offset, charEnd, lineNumber));
	}

	/**
	 * Gets the Task message from the document
	 * 
	 * @param context
	 * @param lineNumber
	 * @param offset
	 * @param length
	 * @return
	 * @throws BadLocationException
	 */
	private String getTaskStr(IBuildContext context, String sourceContents,
			int lineNumber, int offset, int length) throws BadLocationException {
		// get line info to identify the end of the task
		ISourceRange lineInformation = context.getLineTracker()
				.getLineInformation(lineNumber);
		int lineStart = lineInformation.getOffset();
		int lineEnd = lineStart + lineInformation.getLength();

		// identify the actual end of the task: either end of line or end of the
		// token
		// we could have 2 tasks in the same line
		int tokenEnd = offset + length;
		int taskEnd = Math.min(tokenEnd, lineEnd);

		String taskStr = sourceContents.substring(offset, taskEnd);
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
