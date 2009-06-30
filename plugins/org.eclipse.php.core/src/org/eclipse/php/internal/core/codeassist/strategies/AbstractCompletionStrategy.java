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

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * This strategy contains common utilities of all completion strategies.
 * @author michael
 */
public abstract class AbstractCompletionStrategy implements ICompletionStrategy {
	
	private IElementFilter elementFilter;
	private ICompletionContext context;
	private CompletionCompanion companion;
	
	public AbstractCompletionStrategy(ICompletionContext context) {
		this.context = context;
	}
	
	public AbstractCompletionStrategy(ICompletionContext context, IElementFilter elementFilter) {
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
	 * @return
	 */
	public ICompletionContext getContext() {
		return context;
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
	
	/**
	 * Whether code assist should respect case sensitivity
	 * @return
	 */
	protected boolean isCaseSensitive() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_CASE_SENSITIVITY, false, null);
	}
}
