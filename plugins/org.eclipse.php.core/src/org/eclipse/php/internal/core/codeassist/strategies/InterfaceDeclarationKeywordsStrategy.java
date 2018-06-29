/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.InterfaceDeclarationKeywordContext;

/**
 * This strategy completes keywords that can be shown in a class body
 * 
 * @author michael
 */
public class InterfaceDeclarationKeywordsStrategy extends AbstractCompletionStrategy {

	public InterfaceDeclarationKeywordsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public InterfaceDeclarationKeywordsStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof InterfaceDeclarationKeywordContext)) {
			return;
		}

		InterfaceDeclarationKeywordContext concreteContext = (InterfaceDeclarationKeywordContext) context;
		ISourceRange replaceRange = getReplacementRange(concreteContext);

		if (!concreteContext.hasExtends()) {
			reporter.reportKeyword("extends", getSuffix(concreteContext), replaceRange); //$NON-NLS-1$
		}
	}

	@Override
	public ISourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {
		if (!isInsertMode()) {
			return getReplacementRangeWithSpaceAtPrefixEnd(context);
		}
		return super.getReplacementRange(context);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return isInsertMode() && abstractContext.hasSpaceAtPosition(abstractContext.getOffset()) ? "" : " "; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
