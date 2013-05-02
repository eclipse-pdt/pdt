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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.InterfaceDeclarationKeywordContext;

/**
 * This strategy completes keywords that can be shown in a class body 
 * @author michael
 */
public class InterfaceDeclarationKeywordsStrategy extends AbstractCompletionStrategy {
	
	public InterfaceDeclarationKeywordsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public InterfaceDeclarationKeywordsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof InterfaceDeclarationKeywordContext)) {
			return;
		}
		
		InterfaceDeclarationKeywordContext concreteContext = (InterfaceDeclarationKeywordContext) context;
		SourceRange replaceRange = getReplacementRange(concreteContext);

		if (!concreteContext.hasExtends()) {
			reporter.reportKeyword("extends", getSuffix(concreteContext), replaceRange); //$NON-NLS-1$
		}
	}
	
	public String getSuffix(AbstractCompletionContext context) {
		return context.hasWhitespaceBeforeCursor() ? " " : ""; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
