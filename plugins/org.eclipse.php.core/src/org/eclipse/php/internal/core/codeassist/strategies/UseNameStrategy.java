/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

public class UseNameStrategy extends GlobalTypesStrategy {

	private static final String FUNCTION_KEYWORD = "function"; //$NON-NLS-1$
	private static final String CONST_KEYWORD = "const"; //$NON-NLS-1$

	public UseNameStrategy(ICompletionContext context, int trueFlag, int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public UseNameStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		if (completionContext.getPrefix().indexOf(NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
			return;
		}

		reportKeyword(FUNCTION_KEYWORD, reporter);
		reportKeyword(CONST_KEYWORD, reporter);
		super.apply(reporter);
	}

	private void reportKeyword(String keyword, ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		if (completionContext.getPrefix() == null || keyword.startsWith(completionContext.getPrefix())) {
			reporter.reportKeyword(keyword, getSuffix(completionContext), getReplacementRange(completionContext));
		}
	}

	public ISourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {
		if (!isInsertMode()) {
			return getReplacementRangeWithSpaceAtPrefixEnd(context);
		}
		return super.getReplacementRange(context);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return isInsertMode() && abstractContext.hasSpaceAtPosition(abstractContext.getOffset()) ? "" : " "; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
