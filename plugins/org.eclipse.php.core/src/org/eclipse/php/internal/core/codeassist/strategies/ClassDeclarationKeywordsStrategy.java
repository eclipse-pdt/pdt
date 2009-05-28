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
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassDeclarationKeywordContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * This strategy completes keywords that can be shown in a class body 
 * @author michael
 */
public class ClassDeclarationKeywordsStrategy extends AbstractCompletionStrategy {

	public ClassDeclarationKeywordsStrategy(ICompletionContext context, IElementFilter elementFilter) {
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

		if (CodeAssistUtils.startsWithIgnoreCase("extends", prefix)) {
			reporter.reportKeyword("extends", getSuffix(concreteContext), replaceRange);
		}
		if (CodeAssistUtils.startsWithIgnoreCase("implements", prefix) && concreteContext.getPhpVersion().isGreaterThan(PHPVersion.PHP4)) {
			reporter.reportKeyword("implements", getSuffix(concreteContext), replaceRange);
		}
	}
	
	public String getSuffix(AbstractCompletionContext context) {
		return context.hasWhitespaceBeforeCursor() ? " " : "";
	}
}
