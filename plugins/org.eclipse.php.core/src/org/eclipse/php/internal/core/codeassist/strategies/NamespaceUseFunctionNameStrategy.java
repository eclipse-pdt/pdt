/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
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

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceUseFunctionNameContext;

public class NamespaceUseFunctionNameStrategy extends AbstractCompletionStrategy {

	public NamespaceUseFunctionNameStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof NamespaceUseFunctionNameContext)) {
			return;
		}

		NamespaceUseFunctionNameContext concreteContext = (NamespaceUseFunctionNameContext) context;
		String suffix = "";//$NON-NLS-1$
		ISourceRange replaceRange = getReplacementRange(concreteContext);

		for (IMethod method : getMethods(concreteContext)) {
			reporter.reportMethod(method, suffix, replaceRange, getExtraInfo());
		}
	}

	public IMethod[] getMethods(NamespaceUseFunctionNameContext context) throws BadLocationException {
		String prefix = context.getPrefix();

		List<IMethod> result = new LinkedList<IMethod>();
		for (IType ns : context.getNamespaces()) {
			try {
				for (IMethod method : ns.getMethods()) {
					if (StringUtils.startsWithIgnoreCase(method.getElementName(), prefix)) {
						result.add(method);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return result.toArray(new IMethod[result.size()]);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "::".equals(nextWord) ? "" : "::"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.DEFAULT | ProposalExtraInfo.NO_INSERT_NAMESPACE | ProposalExtraInfo.NO_INSERT_USE;
	}
}
