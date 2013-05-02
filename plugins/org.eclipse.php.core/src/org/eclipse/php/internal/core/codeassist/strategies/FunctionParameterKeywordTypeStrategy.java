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

import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.FunctionParameterTypeContext;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * This strategy completes keywords that can be shown in a method parameters
 * completion context
 * 
 * @author vadim.p
 * 
 */
public class FunctionParameterKeywordTypeStrategy extends KeywordsStrategy {

	private static final String CALLABLE = "callable"; //$NON-NLS-1$
	public static final String[] KEYWORDS = { "self", "parent" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * @param context
	 * @param elementFilter
	 */
	public FunctionParameterKeywordTypeStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	/**
	 * @param context
	 */
	public FunctionParameterKeywordTypeStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		FunctionParameterTypeContext context = (FunctionParameterTypeContext) getContext();
		String prefix = context.getPrefix();
		String suffix = ""; //$NON-NLS-1$
		SourceRange replaceRange = getReplacementRange(context);
		if (context.getEnclosingType() != null) {
			try {
				int flags = context.getEnclosingType().getFlags();
				if (!PHPFlags.isNamespace(flags)) {
					for (String keyword : KEYWORDS) {
						if (keyword.startsWith(prefix)) {
							reporter.reportKeyword(keyword, suffix,
									replaceRange);
						}
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}

		PHPVersion phpVersion = context.getPhpVersion();
		if (phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			if (CALLABLE.startsWith(prefix)) {
				reporter.reportKeyword(CALLABLE, suffix, replaceRange);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.codeassist.strategies.KeywordsStrategy#
	 * filterKeyword
	 * (org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData)
	 */
	@Override
	protected boolean filterKeyword(KeywordData keyword) {
		return true;
	}

}
