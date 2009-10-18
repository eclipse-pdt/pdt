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

import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchScope;

/**
 * Description: Factory class to the {@link TextSearcher}
 * 
 * @author Roy, 2007
 */
public class TextSearcherFactory {

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
		final TextSearchScope searchScope = TextSearchScope.newSearchScope(
				new IResource[] { resource }, Pattern.compile(".*"), true); //$NON-NLS-1$
		return createSearcher(searchScope, visitor, searchPattern);
	}

	public final static TextSearcher createSearcher(IResource resource,
			Pattern searchPattern) {
		final ResultVisitor visitor = new ResultVisitor();
		return createSearcher(resource, visitor, searchPattern);
	}

	public final static TextSearcher createSearcher(IResource resource,
			String searchPattern) {
		final Pattern pattern = Pattern.compile(searchPattern);
		return createSearcher(resource, pattern);
	}
}
