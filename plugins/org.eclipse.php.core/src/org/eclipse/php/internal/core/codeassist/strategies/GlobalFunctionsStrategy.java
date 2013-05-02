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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * This strategy completes global functions
 * 
 * @author michael
 */
public class GlobalFunctionsStrategy extends GlobalElementStrategy {

	public GlobalFunctionsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext
				.getCompletionRequestor();

		if (abstractContext.getPrefixWithoutProcessing().trim().length() == 0) {
			return;
		}

		String prefix = abstractContext.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return;
		}

		MatchRule matchRule = MatchRule.PREFIX;
		if (requestor.isContextInformationMode()) {
			matchRule = MatchRule.EXACT;
		}
		IDLTKSearchScope scope = createSearchScope();

		IMethod[] functions = PhpModelAccess.getDefault().findMethods(prefix,
				matchRule, Modifiers.AccGlobal, 0, scope, null);

		SourceRange replacementRange = getReplacementRange(abstractContext);
		String suffix = getSuffix(abstractContext);

		for (IMethod method : functions) {
			reporter.reportMethod(method, suffix, replacementRange);
		}
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
