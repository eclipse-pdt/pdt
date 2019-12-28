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

import java.util.Set;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.IClassMemberContext;

public class TraitMethodsStrategy extends ClassMethodsStrategy {

	public TraitMethodsStrategy(ICompletionContext context) {
		super(context);
	}

	public TraitMethodsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	@Override
	public String getSuffix(AbstractCompletionContext abstractContext) throws BadLocationException {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected void reportMethod(IMethod method, IType type, IClassMemberContext concreteContext, boolean inConstructor,
			Set<String> magicMethods, String suffix, ISourceRange replaceRange, ICompletionReporter reporter)
			throws ModelException {
		reporter.reportMethod(method, suffix, replaceRange,
				ProposalExtraInfo.METHOD_ONLY | ProposalExtraInfo.FULL_NAME);

	}

}
