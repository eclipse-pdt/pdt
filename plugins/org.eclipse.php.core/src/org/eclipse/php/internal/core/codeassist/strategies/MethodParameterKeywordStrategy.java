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

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * This strategy completes keywords that can be shown in a method parameters
 * completion context
 * 
 * @author vadim.p
 * 
 */
public class MethodParameterKeywordStrategy extends KeywordsStrategy {

	/**
	 * @param context
	 * @param elementFilter
	 */
	public MethodParameterKeywordStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	/**
	 * @param context
	 */
	public MethodParameterKeywordStrategy(ICompletionContext context) {
		super(context);
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
		return (keyword.context & PHPKeywords.METHOD_PARAM) == 0;
	}

}
