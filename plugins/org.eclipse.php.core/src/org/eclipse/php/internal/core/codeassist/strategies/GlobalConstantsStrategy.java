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
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * This strategy completes global constants
 * 
 * @author michael
 */
public class GlobalConstantsStrategy extends GlobalElementStrategy {

	public GlobalConstantsStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalConstantsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext
				.getCompletionRequestor();

		String prefix = abstractContext.getPrefix();
		if (prefix.startsWith("$")) {
			return;
		}

		int[] flags = { Modifiers.AccGlobal, Modifiers.AccConstant };
		MatchRule matchRule = MatchRule.PREFIX;
		if (requestor.isContextInformationMode()) {
			matchRule = MatchRule.EXACT;
		}
		IDLTKSearchScope scope = createSearchScope();
		IModelElement[] constants = PhpModelAccess.getDefault().findFields(
				prefix, matchRule, flags, scope, null);

		if (isCaseSensitive()) {
			constants = filterByCase(constants, prefix);
		}

		SourceRange replaceRange = getReplacementRange(abstractContext);
		for (IModelElement constant : constants) {
			reporter.reportField((IField) constant, "", replaceRange, false);
		}
	}
}
