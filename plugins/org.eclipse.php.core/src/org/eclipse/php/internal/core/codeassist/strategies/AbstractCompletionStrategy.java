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
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ExternalScriptProject;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * This strategy contains common utilities of all completion strategies.
 * 
 * @author michael
 */
public abstract class AbstractCompletionStrategy implements ICompletionStrategy {

	private IElementFilter elementFilter;
	private ICompletionContext context;
	private CompletionCompanion companion;

	public AbstractCompletionStrategy(ICompletionContext context) {
		this.context = context;
	}

	public AbstractCompletionStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		this.context = context;
		this.elementFilter = elementFilter;
	}

	public void init(CompletionCompanion companion) {
		this.companion = companion;
	}

	protected CompletionCompanion getCompanion() {
		return companion;
	}

	/**
	 * Return completion context
	 * 
	 * @return
	 */
	public ICompletionContext getContext() {
		return context;
	}

	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {

		AbstractCompletionContext completionContext = (AbstractCompletionContext) context;

		int length = completionContext.getPrefix().length();
		int start = completionContext.getOffset() - length;
		int prefixEnd = completionContext.getPrefixEnd();

		if (start + length < prefixEnd) {
			length = prefixEnd - start;
		}

		SourceRange replacementRange = new SourceRange(start, length);
		return replacementRange;
	}

	public SourceRange getReplacementRangeWithBraces(ICompletionContext context)
			throws BadLocationException {

		AbstractCompletionContext completionContext = (AbstractCompletionContext) context;

		int length = completionContext.getPrefix().length();
		int start = completionContext.getOffset() - length;
		int prefixEnd = completionContext.getPrefixEnd();

		if (start + length < prefixEnd) {
			length = prefixEnd - start;
		}

		IStructuredDocument document = completionContext.getDocument();

		int endOfReplacement = start + length;
		if (document.getLength() == start) {
			endOfReplacement = start;
		} else if (start < document.getLength() - 2) {
			if (document.getChar(endOfReplacement) == '('
					&& document.getChar(endOfReplacement + 1) == ')') {
				endOfReplacement += 2;
			}
		}

		SourceRange replacementRange = new SourceRange(start, endOfReplacement
				- start);
		return replacementRange;
	}

	/**
	 * Returns element filter that will be used for filtering out model elements
	 * from proposals list
	 * 
	 * @return
	 */
	public IElementFilter getElementFilter() {
		return elementFilter;
	}

	/**
	 * Sets element filter that will be used for filtering out model elements
	 * from proposals list
	 * 
	 * @param elementFilter
	 */
	public void setElementFilter(IElementFilter elementFilter) {
		this.elementFilter = elementFilter;
	}

	/**
	 * Whether code assist should respect case sensitivity
	 * 
	 * @return
	 */
	protected boolean isCaseSensitive() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_CASE_SENSITIVITY, false, null);
	}

	/**
	 * Filters elements by prefix using case-sensitive comparison
	 * 
	 * @param elements
	 *            Model elements
	 * @param prefix
	 *            String prefix
	 */
	protected IModelElement[] filterByCase(IModelElement[] elements,
			String prefix) {
		List<IModelElement> result = new ArrayList<IModelElement>(
				elements.length);
		for (IModelElement element : elements) {
			if (element.getElementName().startsWith(prefix)) {
				result.add(element);
			}
		}
		return (IModelElement[]) result
				.toArray(new IModelElement[result.size()]);
	}

	/**
	 * Creates search scope
	 */
	protected IDLTKSearchScope createSearchScope() {
		ISourceModule sourceModule = ((AbstractCompletionContext) context)
				.getSourceModule();
		IScriptProject scriptProject = sourceModule.getScriptProject();
		if (scriptProject != null) {
			if (scriptProject instanceof ExternalScriptProject) {
				// Find language model
				try {
					IScriptModel scriptModel = DLTKCore.create(ResourcesPlugin
							.getWorkspace().getRoot());
					IScriptProject[] allProjects = scriptModel
							.getScriptProjects();
					for (IScriptProject project : allProjects) {
						IBuildpathContainer languageContainer = DLTKCore
								.getBuildpathContainer(
										new Path(
												LanguageModelInitializer.CONTAINER_PATH),
										project);

						if (languageContainer != null) {
							IBuildpathEntry[] buildpathEntries = languageContainer
									.getBuildpathEntries(project);
							List<IProjectFragment> fragments = new LinkedList<IProjectFragment>();
							for (IBuildpathEntry buildpathEntry : buildpathEntries) {
								IProjectFragment fragment = project
										.getProjectFragment(buildpathEntry
												.getPath().toString());
								if (fragment != null) {
									fragments.add(fragment);
								}
							}
							return SearchEngine
									.createSearchScope(
											(IModelElement[]) fragments
													.toArray(new IModelElement[fragments
															.size()]),
											PHPLanguageToolkit.getDefault());
						}
					}
				} catch (ModelException e) {
				}
			}
			return SearchEngine.createSearchScope(scriptProject);
		}
		IProjectFragment projectFragment = (IProjectFragment) sourceModule
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		if (projectFragment != null) {
			return SearchEngine.createSearchScope(projectFragment);
		}
		// XXX: add language model here
		return SearchEngine.createSearchScope(sourceModule);
	}
}
