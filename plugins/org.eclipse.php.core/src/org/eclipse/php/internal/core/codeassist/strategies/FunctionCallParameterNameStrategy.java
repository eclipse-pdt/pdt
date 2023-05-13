/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.CompletionCompanion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.FunctionCallParameterContext;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

public class FunctionCallParameterNameStrategy extends AbstractCompletionStrategy {

	private static String ELLIPSIS = "..."; //$NON-NLS-1$

	/**
	 * @param context
	 * @param elementFilter
	 */
	public FunctionCallParameterNameStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	/**
	 * @param context
	 */
	public FunctionCallParameterNameStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void init(CompletionCompanion companion) {
		super.init(companion);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		if (PHPVersion.PHP8_0.isGreaterThan(getCompanion().getPHPVersion())) {
			return;
		}
		FunctionCallParameterContext context = (FunctionCallParameterContext) getContext();

		String prefix = context.getPrefix();
		if (!prefix.isEmpty() && (prefix.charAt(0) == '&' || prefix.charAt(0) == '$')) {
			return;
		}
		TextSequence statement = context.getStatementText();
		int pos = statement.length() - prefix.length();
		pos = PHPTextSequenceUtilities.readBackwardSpaces(statement, pos);
		if (pos < 1) {
			return;
		}
		pos--;
		char start = statement.charAt(pos);
		boolean hasDot = false;
		while (start == '.') {
			pos--;
			start = statement.charAt(pos);
			hasDot = true;
			prefix = '.' + prefix;
		}
		pos = PHPTextSequenceUtilities.readBackwardSpaces(statement, pos);
		if (start == '(' && !context.isConstructor() && PHPVersion.PHP8_0.isLessThan(getCompanion().getPHPVersion())) {
			if (ELLIPSIS.startsWith(prefix)) {
				int s = getCompanion().getOffset() - prefix.length();
				reporter.reportKeyword(ELLIPSIS, "", new SourceRange(s, context.getReplacementEnd() - s));
			}
		}
		if (hasDot || (start != '(' && start != ',')) {
			return;
		}
		IMethod[] method = context.getMethod();
		if (method.length == 0) {
			return;
		}
		List<String> names = new ArrayList<>();
		;
		for (String name : method[0].getParameterNames()) {
			if (name.substring(1).startsWith(prefix)) {
				names.add(name);
			}
		}
		for (IModelElement el : method[0].getChildren()) {
			if (el instanceof IField && names.contains(el.getElementName())) {
				reporter.reportField((IField) el, ": ", getReplacementRange(context), true,
						ICompletionReporter.RELEVANCE_KEYWORD, ProposalExtraInfo.NAMED_PARAMETER);
				names.remove(el.getElementName());
			}
		}

	}

}
