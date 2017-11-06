/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Thierry Blind - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseStatementContext;

public class UseFunctionStrategy extends FunctionsStrategy {

	public UseFunctionStrategy(ICompletionContext context) {
		super(context);
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
		return ProposalExtraInfo.NO_INSERT_USE | ProposalExtraInfo.FULL_NAME;
	}
}
