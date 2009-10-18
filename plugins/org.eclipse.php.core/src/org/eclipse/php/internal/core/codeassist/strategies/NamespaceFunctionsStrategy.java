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

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceMemberContext;

/**
 * This strategy completes namespace functions
 * @author michael
 */
public class NamespaceFunctionsStrategy extends NamespaceMembersStrategy {
	
	public NamespaceFunctionsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public NamespaceFunctionsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof NamespaceMemberContext)) {
			return;
		}

		NamespaceMemberContext concreteContext = (NamespaceMemberContext) context;
		String prefix = concreteContext.getPrefix();
		String suffix = getSuffix(concreteContext);
		SourceRange replaceRange = getReplacementRange(concreteContext);

		for (IType ns : concreteContext.getNamespaces()) {
			try {
				for (IMethod method : ns.getMethods()) {
					if (CodeAssistUtils.startsWithIgnoreCase(method.getElementName(), prefix)) {
						reporter.reportMethod(method, suffix, replaceRange);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
	}
	
	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}