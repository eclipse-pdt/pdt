/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

public class TraitMethodsStrategy extends ClassMethodsStrategy {

	public TraitMethodsStrategy(ICompletionContext context) {
		super(context);
	}

	public TraitMethodsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected void reportMethod(IMethod method, Object extraInfo, int subRelevance) {
		fReporter.reportMethod(method, fSuffix, fReplaceRange,
				ProposalExtraInfo.METHOD_ONLY | ProposalExtraInfo.FULL_NAME, subRelevance);

	}

}
