/*******************************************************************************
 * Copyright (c) 2009, 2019 IBM Corporation and others.
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
 *     Dawid Pakuła - Property type evaluator
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes property return type
 */
public class PropertyTypeStrategy extends TypesStrategy {

	private static final List<SimpleProposal> TYPES = new ArrayList<>(Arrays.asList(SimpleProposal.BASIC_TYPES));

	public PropertyTypeStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext context = (AbstractCompletionContext) getContext();

		if (PHPVersion.PHP7_4.isGreaterThan(getCompanion().getPHPVersion())) {
			return;
		}
		String prefix = context.getPrefix();
		String suffix = ""; //$NON-NLS-1$
		ISourceRange replaceRange = getReplacementRange(context);
		PHPVersion phpVersion = getCompanion().getPHPVersion();
		for (SimpleProposal proposal : TYPES) {
			if (proposal.isValidPrefix(prefix, phpVersion)) {
				reporter.reportKeyword(proposal.getProposal(), suffix, replaceRange);
			}
		}
		super.apply(reporter);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
