/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes function return types
 */
public class FunctionReturnTypeStrategy extends GlobalTypesStrategy {

	private static final String[] TYPES = new String[] { "string", "int", "float", "bool", "array", "callable",
			"Closure", "self", "parent" };

	public FunctionReturnTypeStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext context = (AbstractCompletionContext) getContext();
		String prefix = context.getPrefix();
		String suffix = ""; //$NON-NLS-1$
		ISourceRange replaceRange = getReplacementRange(context);
		for (String type : TYPES) {
			if (type.startsWith(prefix)) {
				reporter.reportKeyword(type, suffix, replaceRange);
			}
		}
		super.apply(reporter);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
