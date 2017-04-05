/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.task.ITaskReporter;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.compiler.ast.nodes.Comment;
import org.eclipse.php.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;
import org.eclipse.php.internal.core.preferences.TaskTagsProvider;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;

/**
 * This builder firstly finds all todo task tags in comments and secondly
 * creates (and removes) the task markers that will eventually show up in the
 * task view.
 * 
 * @author blind
 */
public class TaskTagBuildParticipantFactory extends AbstractBuildParticipantType implements IExecutableExtension {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {

		if (natureId != null) {
			return new TaskTagBuildParticipantParticipant(project);
		}
		return null;
	}

	protected String natureId = null;

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		natureId = config.getAttribute("nature"); //$NON-NLS-1$
	}

	private static class TaskTagBuildParticipantParticipant implements IBuildParticipant {
		private IScriptProject project;

		public TaskTagBuildParticipantParticipant(IScriptProject project) {
			this.project = project;
		}

		private void checkForTodo(Pattern[] todos, List<Scalar> foundTaskTags, int commentStart, String comment) {
			ArrayList<Matcher> matchers = createMatcherList(todos, comment);
			int startPosition = 0;
			Matcher matcher = getMinimalMatcher(matchers, startPosition);
			while (matcher != null) {
				int startIndex = matcher.start();
				int endIndex = matcher.end();
				foundTaskTags.add(new Scalar(commentStart + startIndex, commentStart + endIndex, matcher.group(),
						Scalar.TYPE_STRING));
				startPosition = endIndex;
				matcher = getMinimalMatcher(matchers, startPosition);
			}
		}

		private ArrayList<Matcher> createMatcherList(Pattern[] todos, String content) {
			ArrayList<Matcher> list = new ArrayList<Matcher>(todos.length);
			for (int i = 0; i < todos.length; i++) {
				list.add(i, todos[i].matcher(content));
			}
			return list;
		}

		private Matcher getMinimalMatcher(ArrayList<Matcher> matchers, int startPosition) {
			Matcher minimal = null;
			int size = matchers.size();
			for (int i = 0; i < size;) {
				Matcher tmp = (Matcher) matchers.get(i);
				if (tmp.find(startPosition)) {
					if (minimal == null || tmp.start() < minimal.start()) {
						minimal = tmp;
					}
					i++;
				} else {
					matchers.remove(i);
					size--;
				}
			}
			return minimal;
		}

		/**
		 * Get the task priority according to the preferences
		 * 
		 * @return
		 */
		private int getTaskPriority(TaskTag[] taskTags, String taskStr) {

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
		 * @param file
		 * @param taskReporter
		 * @param offset
		 * @param nextTaskTag
		 * @param priority
		 * @throws BadLocationException
		 * @throws CoreException
		 */
		private void reportTask(IDocument document, IFile file, ITaskReporter taskReporter, int offset,
				Scalar nextTaskTag, int priority) throws BadLocationException, CoreException {
			int lineNumber = document.getLineOfOffset(offset);

			String taskStr = getTaskStr(document, lineNumber, offset, nextTaskTag);
			// the end of the string to be highlighted
			int charEnd = offset + taskStr.length();

			// report the task
			createMarker(file, taskStr, lineNumber, priority, offset, charEnd);
		}

		/**
		 * Creates a PHP task marker based on the given information
		 * 
		 * @param file
		 * @param taskStr
		 * @param lineNumber
		 * @param priority
		 * @param offset
		 * @param charEnd
		 * @throws CoreException
		 */
		private void createMarker(IFile file, String taskStr, int lineNumber, int priority, int offset, int charEnd)
				throws CoreException {
			IMarker marker = file.createMarker(PHPCoreConstants.PHP_MARKER_TYPE);
			if (!marker.exists()) {
				return;
			}

			marker.setAttribute(IMarker.TASK, true);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber + 1);
			marker.setAttribute(IMarker.CHAR_START, offset);
			marker.setAttribute(IMarker.CHAR_END, charEnd);
			marker.setAttribute(IMarker.MESSAGE, taskStr);
			marker.setAttribute(IMarker.USER_EDITABLE, false);
			marker.setAttribute(IMarker.PRIORITY, priority);
		}

		/**
		 * Gets the Task message from the document
		 * 
		 * @param document
		 * @param lineNumber
		 * @param offset
		 * @param nextTaskTag
		 * @return
		 * @throws BadLocationException
		 */
		private String getTaskStr(IDocument document, int lineNumber, int offset, Scalar nextTaskTag)
				throws BadLocationException {
			// get line info to identify the end of the task
			IRegion lineInformation = document.getLineInformation(lineNumber);
			int lineStart = lineInformation.getOffset();
			int lineEnd = lineStart + lineInformation.getLength();

			// identify the actual end of the task: either end of line or begin
			// of the next token
			int taskEnd;
			if (nextTaskTag != null) {
				taskEnd = Math.min(nextTaskTag.start(), lineEnd);
			} else {
				taskEnd = lineEnd;
			}

			String taskStr = document.get(offset, taskEnd - offset);
			taskStr = taskStr.trim();

			return taskStr;
		}

		@Override
		public void build(IBuildContext context) throws CoreException {
			if (Boolean.TRUE.equals(context.get(ParserBuildParticipantFactory.IN_LIBRARY_FOLDER))) {
				// skip syntax check for code inside library folders
				return;
			}

			if (context.getFile() == null) {
				// skip when building external module
				return;
			}

			if (context.getFile().getType() != IResource.FILE || !(PHPToolkitUtil.isPHPFile(context.getFile()))) {
				// process only PHP files
				return;
			}

			// remove the markers currently existing for this file
			try {
				context.getFile().deleteMarkers(PHPCoreConstants.PHP_MARKER_TYPE, false, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
			}

			final ModuleDeclaration moduleDeclaration = (ModuleDeclaration) context
					.get(IBuildContext.ATTR_MODULE_DECLARATION);

			if (moduleDeclaration instanceof PHPModuleDeclaration) {
				try {
					Pattern[] todos;
					TaskTag[] taskTags;
					if (project != null && project.getProject() != null) {
						todos = TaskPatternsProvider.getInstance().getPatternsForProject(project.getProject());
						taskTags = TaskTagsProvider.getInstance().getProjectTaskTags(project.getProject());
					} else {
						todos = TaskPatternsProvider.getInstance().getPatternsForWorkspace();
						taskTags = TaskTagsProvider.getInstance().getWorkspaceTaskTags();
					}
					if (taskTags == null) {
						taskTags = TaskTagsProvider.getInstance().getWorkspaceTaskTags();
					}

					IDocument document = new Document(new String(context.getContents()));
					for (Comment comment : ((PHPModuleDeclaration) moduleDeclaration).getCommentList()) {
						List<Scalar> foundTaskTags = new ArrayList<Scalar>();
						checkForTodo(todos, foundTaskTags, comment.start(),
								document.get(comment.start(), comment.end() - comment.start()));
						if (foundTaskTags.isEmpty()) {
							comment.setTaskTags(null);
						} else {
							comment.setTaskTags(foundTaskTags);
							for (int i = 0; i < foundTaskTags.size(); i++) {
								Scalar foundTaskTag = foundTaskTags.get(i);
								String taskKeyword = document.get(foundTaskTag.start(),
										foundTaskTag.end() - foundTaskTag.start());
								int priority = getTaskPriority(taskTags, taskKeyword);
								reportTask(document, context.getFile(), context.getTaskReporter(), foundTaskTag.start(),
										i + 1 < foundTaskTags.size() ? foundTaskTags.get(i + 1) : null, priority);
							}
						}
					}
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}
	}

}
