/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Thierry Blind - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseStatementContext;

public class UseConstStrategy extends ConstantsStrategy {

	public UseConstStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public UseConstStrategy(ICompletionContext context) {
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
		if (useStatementContext.getGroupPrefixBeforeOpeningCurly() != null) {
			int idx = useStatementContext.getPrefixBeforeCursor().lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
			int move = (idx != -1) ? idx + 1 : 0;
			ISourceRange basicRange = getReplacementRange(context);
			return new SourceRange(basicRange.getOffset() + move, basicRange.getLength() - move);
		}
		return super.getReplacementRangeForMember(useStatementContext);
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.NO_INSERT_USE | ProposalExtraInfo.FULL_NAME;
	}
}
