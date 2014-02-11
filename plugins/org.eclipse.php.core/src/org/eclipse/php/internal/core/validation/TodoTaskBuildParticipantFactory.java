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
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexerFactory;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.preferences.TaskTagsProvider;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;

public class TodoTaskBuildParticipantFactory extends
		AbstractBuildParticipantType {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		return new PHPTodoTaskValidator(project.getProject());
	}

	/**
	 * This Validator creates the task markers that will eventually show up in
	 * the task view
	 * 
	 * @author Eden K.,2008
	 */
	private class PHPTodoTaskValidator implements IBuildParticipant {

		final protected TaskTag[] taskTags;
		final protected IProject project;
		protected IBuildContext context;
		protected String sourceContents;

		public PHPTodoTaskValidator(IProject project) {
			this.project = project;
			taskTags = TaskTagsProvider.getInstance().getTaskTags(project);
		}

		public void build(IBuildContext context) throws CoreException {
			if (context.get(IBuildContext.ATTR_MODULE_DECLARATION) == null
					|| !(context.get(IBuildContext.ATTR_MODULE_DECLARATION) instanceof PHPModuleDeclaration)) {
				return;
			}
			this.context = context;
			this.sourceContents = context.getSourceContents();
			try {
				validateContent();
			} catch (Exception e) {
				Logger.logException(e);
			}
			this.context = null;
			this.sourceContents = null;
		}

		private boolean isComment(String token) {
			return PHPRegionTypes.PHP_COMMENT.equals(token)
					|| PHPRegionTypes.PHPDOC_COMMENT.equals(token)
					|| PHPRegionTypes.PHP_LINE_COMMENT.equals(token);
		}

		private void check(AbstractPhpLexer lexer, Comment comment)
				throws IOException {
			int maxLength = comment.sourceEnd() - comment.sourceStart();
			lexer.reset(
					sourceContents.substring(comment.sourceStart(),
							comment.sourceEnd()).toCharArray(), 0, maxLength);
			lexer.initialize(lexer.getScriptingState());
			String token = null;
			int contentOffset = comment.sourceStart();
			int contentLength = 0;
			boolean reuse = false;

			while (contentLength < maxLength
					&& (reuse || (token = lexer.getNextToken()) != null)) {
				reuse = false;
				if (!PHPRegionTypes.PHPDOC_TODO.equals(token)) {
					contentLength += lexer.getLength();
					continue;
				}

				final int offset = contentOffset + lexer.getOffset();
				final String tagName = sourceContents.substring(offset, offset
						+ lexer.getLength());
				final int priority = getTaskPriority(tagName);
				int length = lexer.getLength();
				while (lexer.getOffset() + lexer.getLength() <= maxLength
						&& (token = lexer.getNextToken()) != null
						&& isComment(token)) {
					length += lexer.getLength();
				}
				contentLength += length;

				try {
					reportTask(context, sourceContents, offset, length,
							priority);
				} catch (BadLocationException e) {
					Logger.logException(
							CoreMessages.getString("PHPTodoTaskAstParser_0"), e); //$NON-NLS-1$
				} catch (CoreException e) {
					Logger.logException("Failed creating task", e); //$NON-NLS-1$
				}

				if (token == null) {
					break;
				} else if (!isComment(token)) {
					reuse = true;
				}
			}
		}

		/**
		 * Search for tasks in the validated file and create a marker for each
		 * task found
		 * 
		 * @throws IOException
		 * @throws SecurityException
		 * @throws NoSuchFieldException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 */
		private void validateContent() throws IOException {
			final PHPModuleDeclaration decl = (PHPModuleDeclaration) context
					.get(IBuildContext.ATTR_MODULE_DECLARATION);

			AbstractPhpLexer lexer = PhpLexerFactory.createLexer(
					new StringReader(sourceContents),
					ProjectOptions.getPhpVersion(project));
			lexer.setAspTags(ProjectOptions.isSupportingAspTags(project));
			lexer.setPatterns(project);

			for (Comment comment : decl.getVarComments()) {
				check(lexer, comment);
			}
			for (Comment comment : decl.getPhpDocBlocks()) {
				check(lexer, comment);
			}
			for (Comment comment : decl.getOtherComments()) {
				check(lexer, comment);
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
				int offset, int length, int priority)
				throws BadLocationException, CoreException {
			int lineNumber = context.getLineTracker().getLineNumberOfOffset(
					offset);
			String taskStr = getTaskStr(context, sourceContents, lineNumber,
					offset, length);
			// the end of the string to be highlighted

			context.getProblemReporter().reportProblem(
					new ValidationProblem(taskStr, ProblemIdentifier.TASK,
							priority, offset, offset + taskStr.length(),
							lineNumber));
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
				int lineNumber, int offset, int length)
				throws BadLocationException {
			// get line info to identify the end of the task
			ISourceRange lineInformation = context.getLineTracker()
					.getLineInformation(lineNumber);
			int lineStart = lineInformation.getOffset();
			int lineEnd = lineStart + lineInformation.getLength();

			// identify the actual end of the task: either end of line or end of
			// the
			// token
			// we could have 2 tasks in the same line
			int tokenEnd = offset + length;
			int taskEnd = Math.min(tokenEnd, lineEnd);

			String taskStr = sourceContents.substring(offset, taskEnd);
			taskStr = taskStr.trim();
			return taskStr;

		}
	}

}
