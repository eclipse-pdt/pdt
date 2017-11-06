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
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseStatementContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseStatementContext.TYPES;

public class UseNameStrategy extends TypesStrategy {

	public UseNameStrategy(ICompletionContext context, int trueFlag, int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public UseNameStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		reportKeyword(UseStatementContext.CONST_KEYWORD, reporter);
		reportKeyword(UseStatementContext.FUNCTION_KEYWORD, reporter);
		super.apply(reporter);
	}

	private void reportKeyword(String keyword, ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		if (completionContext instanceof UseStatementContext) {
			UseStatementContext context = (UseStatementContext) completionContext;
			if (context.isUseFunctionStatement() || context.isUseConstStatement() || context.getType() == TYPES.TRAIT) {
				return;
			}
			if (keyword.startsWith(context.getPrefixBeforeCursor())) {
				reporter.reportKeyword(keyword, getSuffix(context), getReplacementRange(context));
			}
		} else if (keyword.startsWith(completionContext.getPrefix())) {
			reporter.reportKeyword(keyword, getSuffix(completionContext), getReplacementRange(completionContext));
		}
	}

	@Override
	public ISourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {
		if (!isInsertMode()) {
			return getReplacementRangeWithSpaceAtPrefixEnd(context);
		}
		return super.getReplacementRange(context);
	}

	@Override
	public ISourceRange getReplacementRangeForMember(AbstractCompletionContext context) throws BadLocationException {
		UseStatementContext useStatementContext = (UseStatementContext) context;
		String groupPrefixBeforeOpeningCurly = useStatementContext.getGroupPrefixBeforeOpeningCurly();
		if (groupPrefixBeforeOpeningCurly != null) {
			String prefix = context.getPrefix();
			int move = prefix.length() - groupPrefixBeforeOpeningCurly.length();
			if (useStatementContext.getPrefixBeforeCursor().startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				move++;
			}
			ISourceRange basicRange = getReplacementRange(context);
			return new SourceRange(basicRange.getOffset() + move, basicRange.getLength() - move);
		}
		return super.getReplacementRangeForMember(useStatementContext);
	}

	@Override
	public String getSuffix(AbstractCompletionContext abstractContext) {
		return isInsertMode() && abstractContext.hasSpaceAtPosition(abstractContext.getOffset()) ? "" : " "; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY | ProposalExtraInfo.NO_INSERT_USE | ProposalExtraInfo.FULL_NAME;
	}
}
