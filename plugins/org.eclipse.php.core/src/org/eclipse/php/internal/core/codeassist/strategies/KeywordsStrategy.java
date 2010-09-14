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

import java.util.Collection;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.language.keywords.IPHPKeywordsInitializer;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * This strategy completes keywords. Direct implementation must define what kind
 * of keywords should be proposed in code assist.
 * 
 * @author michael
 */
public abstract class KeywordsStrategy extends GlobalElementStrategy {

	public KeywordsStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public KeywordsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;
		ISourceModule sourceModule = concreteContext.getSourceModule();
		String prefix = concreteContext.getPrefix();
		SourceRange replaceRange = getReplacementRange(concreteContext);
		boolean withoutSemicolon = concreteContext.getNextWord().trim()
				.equals(IPHPKeywordsInitializer.SEMICOLON_SUFFIX);
		Collection<KeywordData> keywordsList = PHPKeywords.getInstance(
				sourceModule.getScriptProject().getProject()).findByPrefix(
				prefix);
		for (KeywordData keyword : keywordsList) {
			if (!filterKeyword(keyword)) {
				String suffix = keyword.suffix;
				if (withoutSemicolon
						&& suffix
								.endsWith(IPHPKeywordsInitializer.SEMICOLON_SUFFIX)) {
					suffix = suffix.substring(0, suffix.length() - 1);
				}
				reporter.reportKeyword(keyword.name, suffix, replaceRange);
			}
		}
	}

	/**
	 * Filters keyword from the proposal list
	 * 
	 * @return
	 */
	abstract protected boolean filterKeyword(KeywordData keyword);
}
