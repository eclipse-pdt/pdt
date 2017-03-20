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
 * This strategy completes function return types
 */
public class FunctionReturnTypeStrategy extends GlobalTypesStrategy {

	private static final List<SimpleProposal> TYPES = new ArrayList<SimpleProposal>(
			Arrays.asList(SimpleProposal.BASIC_TYPES));

	static {
		TYPES.add(new SimpleProposal("void", PHPVersion.PHP7_1)); //$NON-NLS-1$
	}

	public FunctionReturnTypeStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext context = (AbstractCompletionContext) getContext();
		String prefix = context.getPrefix();
		String suffix = ""; //$NON-NLS-1$
		ISourceRange replaceRange = getReplacementRange(context);
		PHPVersion phpVersion = context.getPhpVersion();
		for (SimpleProposal proposal : TYPES) {
			if (proposal.isValid(prefix, phpVersion)) {
				reporter.reportKeyword(proposal.getProposal(), suffix, replaceRange);
			}
		}
		super.apply(reporter);
	}

	@Override
	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
