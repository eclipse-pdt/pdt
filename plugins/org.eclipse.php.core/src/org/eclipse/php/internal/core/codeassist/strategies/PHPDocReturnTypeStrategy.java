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

	private static final String ARRAY_TYPE = "array";
	private static final String MIXED_TYPE = "mixed";
	private static final String VOID_TYPE = "void";
	private static final String EMPTY = "";

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
		reportKeyword(reporter, replaceRange, ARRAY_TYPE, prefix);
		reportKeyword(reporter, replaceRange, MIXED_TYPE, prefix);
		reportKeyword(reporter, replaceRange, VOID_TYPE, prefix);
	}

	private void reportKeyword(ICompletionReporter reporter,
			SourceRange replaceRange, String keyword, String prefix) {
		if (keyword.startsWith(prefix)) {
			reporter.reportKeyword(keyword, EMPTY, replaceRange);
		}
	}

	@Override
	protected Object getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
