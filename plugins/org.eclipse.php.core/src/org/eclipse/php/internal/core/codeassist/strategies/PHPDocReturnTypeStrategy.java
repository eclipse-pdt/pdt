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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

/**
 * This strategy completes variable names in 'param' PHPDoc tag
 * 
 * @author michael
 */
public class PHPDocReturnTypeStrategy extends GlobalTypesStrategy {

	// https://phpdoc.org/docs/latest/guides/types.html#primitives
	private static final String[] PRIMITIVE_TYPES = { "string", //$NON-NLS-1$
			"int", //$NON-NLS-1$
			"integer", //$NON-NLS-1$
			"float", //$NON-NLS-1$
			"bool", //$NON-NLS-1$
			"boolean", //$NON-NLS-1$
			"array", //$NON-NLS-1$
			"resource", //$NON-NLS-1$
			"null", //$NON-NLS-1$
			"callable", //$NON-NLS-1$

			// additional not specified in phpDoc manual
			"NULL", //$NON-NLS-1$
			"double" //$NON-NLS-1$
	};

	// https://phpdoc.org/docs/latest/guides/types.html#keywords
	private static final String[] KEYWORDS = { "mixed", //$NON-NLS-1$
			"void", //$NON-NLS-1$
			"object", //$NON-NLS-1$
			"false", //$NON-NLS-1$
			"true", //$NON-NLS-1$
			"self", //$NON-NLS-1$
			"static", //$NON-NLS-1$
			"$this", //$NON-NLS-1$
	};

	public PHPDocReturnTypeStrategy(ICompletionContext context) {
		super(context, 0, Modifiers.AccNameSpace);
	}

	public PHPDocReturnTypeStrategy(ICompletionContext context, int trueFlag, int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof PHPDocTagContext)) {
			return;
		}
		super.apply(reporter);
		if (!((PHPDocTagContext) context).isGlobal()) {
			String prefix = ((PHPDocTagContext) context).getPrefix();
			ISourceRange replaceRange = getReplacementRange(context);
			for (int i = 0; i < PRIMITIVE_TYPES.length; i++) {
				reportKeyword(reporter, replaceRange, PRIMITIVE_TYPES[i], prefix);
			}
			for (int i = 0; i < KEYWORDS.length; i++) {
				reportKeyword(reporter, replaceRange, KEYWORDS[i], prefix);
			}
		}
	}

	private void reportKeyword(ICompletionReporter reporter, ISourceRange replaceRange, String keyword, String prefix) {
		if (keyword.startsWith(prefix)) {
			reporter.reportKeyword(keyword, "", replaceRange); //$NON-NLS-1$
		}
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
