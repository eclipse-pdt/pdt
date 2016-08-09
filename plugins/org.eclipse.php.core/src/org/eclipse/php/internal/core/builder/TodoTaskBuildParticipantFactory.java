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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;

public class TodoTaskBuildParticipantFactory extends AbstractBuildParticipantType implements IExecutableExtension {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {

		if (natureId != null) {
			return new OrganizeBuildParticipantParticipant(project);
		}
		return null;
	}

	private String natureId = null;

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		natureId = config.getAttribute("nature"); //$NON-NLS-1$
	}

	private static class OrganizeBuildParticipantParticipant implements IBuildParticipant {
		private IScriptProject project;

		public OrganizeBuildParticipantParticipant(IScriptProject project) {
			this.project = project;
		}

		private void checkForTodo(Pattern[] todos, List<Scalar> todoTasks, int commentStart, String comment) {
			ArrayList<Matcher> matchers = createMatcherList(todos, comment);
			int startPosition = 0;
			Matcher matcher = getMinimalMatcher(matchers, startPosition);
			while (matcher != null) {
				int startIndex = matcher.start();
				int endIndex = matcher.end();
				todoTasks.add(new Scalar(commentStart + startIndex, commentStart + endIndex, matcher.group(),
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

		@Override
		public void build(IBuildContext context) throws CoreException {
			if (Boolean.TRUE.equals(context.get(ParserBuildParticipantFactory.IN_LIBRARY_FOLDER))) {
				// skip syntax check for code inside library folders
				return;
			}

			final ModuleDeclaration moduleDeclaration = (ModuleDeclaration) context
					.get(IBuildContext.ATTR_MODULE_DECLARATION);

			if (moduleDeclaration != null) {
				// Run the validation visitor:
				try {
					Pattern[] todos;
					if (project != null && project.getProject() != null) {
						todos = TaskPatternsProvider.getInstance().getPatternsForProject(project.getProject());
					} else {
						todos = TaskPatternsProvider.getInstance().getPatternsForWorkspace();
					}

					if (moduleDeclaration instanceof PHPModuleDeclaration) {
						IDocument doc = new Document(new String(context.getContents()));
						for (PHPDocBlock phpDocBlock : ((PHPModuleDeclaration) moduleDeclaration).getPhpDocBlocks()) {
							List<Scalar> todoTasks = new ArrayList<Scalar>();
							checkForTodo(todos, todoTasks, phpDocBlock.start(),
									doc.get(phpDocBlock.start(), phpDocBlock.end() - phpDocBlock.start()));
							if (todoTasks.isEmpty()) {
								phpDocBlock.setTodoTasks(null);
							} else {
								phpDocBlock.setTodoTasks(todoTasks);
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
