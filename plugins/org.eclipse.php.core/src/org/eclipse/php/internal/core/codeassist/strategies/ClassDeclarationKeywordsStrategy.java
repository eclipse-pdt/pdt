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

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassDeclarationKeywordContext;

/**
 * This strategy completes keywords that can be shown in a class body
 * 
 * @author michael
 */
public class ClassDeclarationKeywordsStrategy extends
		AbstractCompletionStrategy {

	private static final String IMPLEMENTS_WITH_BLANK = " implements "; //$NON-NLS-1$
	private static final String EXTENDS_WITH_BLANK = " extends "; //$NON-NLS-1$
	private static final String IMPLEMENTS = "implements"; //$NON-NLS-1$
	private static final String EXTENDS = "extends"; //$NON-NLS-1$

	public ClassDeclarationKeywordsStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassDeclarationKeywordsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof ClassDeclarationKeywordContext)) {
			return;
		}

		ClassDeclarationKeywordContext concreteContext = (ClassDeclarationKeywordContext) context;
		SourceRange replaceRange = getReplacementRange(concreteContext);
		String prefix = concreteContext.getPrefix();
		String statementText = concreteContext.getStatementText().toString();
		if (CodeAssistUtils.startsWithIgnoreCase(EXTENDS, prefix)
				&& statementText.indexOf(EXTENDS_WITH_BLANK) < 0) {
			reporter.reportKeyword(EXTENDS, getSuffix(concreteContext),
					replaceRange);
		}
		if (CodeAssistUtils.startsWithIgnoreCase(IMPLEMENTS, prefix)
				&& concreteContext.getPhpVersion().isGreaterThan(
						PHPVersion.PHP4)
				&& statementText.indexOf(IMPLEMENTS_WITH_BLANK) < 0) {
			reporter.reportKeyword(IMPLEMENTS, getSuffix(concreteContext),
					replaceRange);
		}
	}

	public String getSuffix(AbstractCompletionContext context) {
		return context.hasWhitespaceBeforeCursor() ? " " : ""; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
