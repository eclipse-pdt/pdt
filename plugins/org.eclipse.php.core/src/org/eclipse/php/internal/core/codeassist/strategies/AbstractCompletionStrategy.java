/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * This strategy contains common utilities of all completion strategies.
 * @author michael
 */
public abstract class AbstractCompletionStrategy implements ICompletionStrategy {
	
	private IElementFilter elementFilter;
	
	public AbstractCompletionStrategy() {
	}
	
	public AbstractCompletionStrategy(IElementFilter elementFilter) {
		this.elementFilter = elementFilter;
	}

	public SourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {

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

	/**
	 * Returns element filter that will be used for filtering out model elements from proposals list
	 * @return
	 */
	public IElementFilter getElementFilter() {
		return elementFilter;
	}

	/**
	 * Sets element filter that will be used for filtering out model elements from proposals list
	 * @param elementFilter
	 */
	public void setElementFilter(IElementFilter elementFilter) {
		this.elementFilter = elementFilter;
	}
}
