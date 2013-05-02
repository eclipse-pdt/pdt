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

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

/**
 * This strategy completes variable names in 'param' PHPDoc tag
 * 
 * @author michael
 */
public class PHPDocReturnTypeStrategy extends GlobalClassesStrategy {

	private static final String[] ALL_TYPE = new String[] { "array", "array", //$NON-NLS-1$ //$NON-NLS-2$
			"mixed", "void", "integer", "int", "string", "float", "double", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			"bool", "boolean", "resource", "null", "NULL" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	private static final String EMPTY = ""; //$NON-NLS-1$

	public PHPDocReturnTypeStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof PHPDocTagContext)) {
			return;
		}
		super.apply(reporter);
		String prefix = ((PHPDocTagContext) context).getPrefix();
		SourceRange replaceRange = getReplacementRange(context);
		for (int i = 0; i < ALL_TYPE.length; i++) {
			reportKeyword(reporter, replaceRange, ALL_TYPE[i], prefix);

		}
	}

	private void reportKeyword(ICompletionReporter reporter,
			SourceRange replaceRange, String keyword, String prefix) {
		if (keyword.startsWith(prefix)) {
			reporter.reportKeyword(keyword, EMPTY, replaceRange);
		}
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
