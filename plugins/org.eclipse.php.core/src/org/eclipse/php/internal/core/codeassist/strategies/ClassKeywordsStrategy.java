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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This strategy completes keywords that can be shown in a class body 
 * @author michael
 */
public class ClassKeywordsStrategy extends KeywordsStrategy {

	private TextSequence statementText;
	
	public ClassKeywordsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassKeywordsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		statementText = ((AbstractCompletionContext) context).getStatementText();
		super.apply(reporter);
	}

	protected boolean filterKeyword(KeywordData keyword) {
		if ((keyword.context & PHPKeywords.CLASS_BODY) == 0) {
			return true;
		}
		// check whether this keyword is included in the statement already
		int i = statementText.toString().indexOf(keyword.name);
		if (i != -1) {
			if ((i == 0 || Character.isWhitespace(statementText.charAt(i-1)) && Character.isWhitespace(statementText.charAt(i + keyword.name.length())))) {
				return true;
			}
		}
		return false;
	}

}
