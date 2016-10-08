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
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes exception types in catch clause
 * 
 * @author michael
 */
public class CatchTypeStrategy extends GlobalTypesStrategy {

	public CatchTypeStrategy(ICompletionContext context, int trueFlag, int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public CatchTypeStrategy(ICompletionContext context) {
		super(context);
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

	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
