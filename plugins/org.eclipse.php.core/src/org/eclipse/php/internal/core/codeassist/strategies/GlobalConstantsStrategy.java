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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
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

		ISourceModule sourceModule = abstractContext.getSourceModule();

		IType enclosingType = null;
		try {
			IModelElement enclosingElement = sourceModule
					.getElementAt(abstractContext.getOffset());

			if (enclosingElement != null && enclosingElement instanceof IType) {
				enclosingType = (IType) enclosingElement;
			}

		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}

		IDLTKSearchScope scope = null;
		IModelElement[] enclosingTypeConstants = null;

		if (enclosingType != null
				&& isStartOfStatement(prefix, abstractContext,
						abstractContext.getOffset())) {
			// See the case of testClassStatement1.pdtt and
			// testClassStatement2.pdtt
			scope = SearchEngine.createSearchScope(enclosingType);
		} else {
			scope = getSearchScope(abstractContext);
		}

		enclosingTypeConstants = PhpModelAccess.getDefault().findFields(prefix,
				matchRule, Modifiers.AccConstant, 0, scope, null);

		if (isCaseSensitive()) {
			enclosingTypeConstants = filterByCase(enclosingTypeConstants,
					prefix);
		}
		// workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=310383
		enclosingTypeConstants = filterClassConstants(enclosingTypeConstants);
		// workaround end
		SourceRange replaceRange = getReplacementRange(abstractContext);
		for (IModelElement constant : enclosingTypeConstants) {
			reporter.reportField((IField) constant, "", replaceRange, false); //$NON-NLS-1$
		}

	}

	private boolean isStartOfStatement(String prefix,
			AbstractCompletionContext abstractContext, int offset) {
		IDocument doc = abstractContext.getDocument();
		try {
			int pos = offset - prefix.length() - 1;
			char previousChar = doc.getChar(pos);

			while (Character.isWhitespace(previousChar)) {
				pos--;
				previousChar = doc.getChar(pos);
			}

			return previousChar == '{' || previousChar == ';';
		} catch (BadLocationException e) {
		}

		return false;
	}

	private IModelElement[] filterClassConstants(IModelElement[] elements) {
		List<IModelElement> result = new ArrayList<IModelElement>(
				elements.length);
		for (IModelElement element : elements) {
			try {
				if ((((IField) element).getFlags() & PHPCoreConstants.AccClassField) == 0) {
					result.add(element);
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return (IModelElement[]) result
				.toArray(new IModelElement[result.size()]);
	}

	private IDLTKSearchScope getSearchScope(
			AbstractCompletionContext abstractContext) {
		IDLTKSearchScope scope = createSearchScope();
		return scope;
	}

}
