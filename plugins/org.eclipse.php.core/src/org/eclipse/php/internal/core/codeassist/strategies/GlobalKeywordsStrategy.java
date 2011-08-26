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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * This strategy completes keywords that can be shown in a class body
 * 
 * @author michael
 */
public class GlobalKeywordsStrategy extends KeywordsStrategy {

	public GlobalKeywordsStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalKeywordsStrategy(ICompletionContext context) {
		super(context);
	}

	protected boolean filterKeyword(KeywordData keyword) {
		return (keyword.context & PHPKeywords.GLOBAL) == 0;
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (abstractContext.getPrefixWithoutProcessing().trim().length() == 0) {
			return;
		}
		super.apply(reporter);
	}
}
