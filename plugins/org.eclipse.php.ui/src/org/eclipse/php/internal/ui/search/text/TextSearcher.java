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
package org.eclipse.php.internal.ui.search.text;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchScope;
import org.eclipse.search.internal.ui.text.FileMatch;

/**
 * Description: this utility searches for text in files (given a text pattern)
 * in a set of given resources
 * 
 * @author Roy, 2007
 */
public class TextSearcher {

	public final TextSearchEngine engine;
	public final TextSearchScope scope;
	public final ResultVisitor visitor;
	public final Pattern searchPattern;
	private boolean done;

	/**
	 * Should not be constructed directly Use factory instead
	 * 
	 * @param engine
	 * @param newSearchScope
	 * @param visitor
	 * @param searchPattern
	 */
	TextSearcher(TextSearchEngine engine, TextSearchScope newSearchScope,
			ResultVisitor visitor, Pattern searchPattern) {
		if (engine == null || newSearchScope == null || visitor == null) {
			throw new IllegalArgumentException();
		}

		this.engine = engine;
		this.scope = newSearchScope;
		this.visitor = visitor;
		this.searchPattern = searchPattern;
		this.done = false;
	}

	/**
	 * @return is the search was done
	 */
	public final boolean isDone() {
		return done;
	}

	/**
	 * default foactory - includes all parameters
	 * 
	 * @param engine
	 * @param newSearchScope
	 * @param visitor
	 * @param searchPattern
	 * @return
	 */
	public final static TextSearcher createSearcher(TextSearchEngine engine,
			TextSearchScope newSearchScope, ResultVisitor visitor,
			Pattern searchPattern) {
		return new TextSearcher(engine, newSearchScope, visitor, searchPattern);
	}

	public final static TextSearcher createSearcher(
			TextSearchScope newSearchScope, ResultVisitor visitor,
			Pattern searchPattern) {
		final TextSearchEngine engine = TextSearchEngine.createDefault();
		return createSearcher(engine, newSearchScope, visitor, searchPattern);
	}

	public final static TextSearcher createSearcher(IResource resource,
			ResultVisitor visitor, Pattern searchPattern) {
		TextSearchScope searchScope = TextSearchScope.newSearchScope(
				new IResource[] { resource }, null, true);
		return createSearcher(searchScope, visitor, searchPattern);
	}

	public final static TextSearcher createSearcher(IResource resource,
			Pattern searchPattern) {
		ResultVisitor visitor = new ResultVisitor();
		return createSearcher(resource, visitor, searchPattern);
	}

	public final static TextSearcher createSearcher(IResource resource,
			String searchText) {
		Pattern searchPattern = Pattern.compile(searchText);
		return createSearcher(resource, searchPattern);
	}

	/**
	 * Search for occurences
	 * 
	 * @param monitor
	 */
	public void search(IProgressMonitor monitor) {
		assert !done;

		engine.search(scope, visitor, searchPattern, monitor);
		done = true;
	}

	/**
	 * @return a list of {@link FileMatch}
	 */
	public List getResults() {
		assert done;
		return visitor.getResult();
	}
}
