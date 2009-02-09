/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceMemberContext;

/**
 * This strategy completes namespace classes and interfaces
 * @author michael
 */
public class NamespaceTypesStrategy extends NamespaceMembersStrategy {
	
	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		if (!(context instanceof NamespaceMemberContext)) {
			return;
		}

		NamespaceMemberContext concreteContext = (NamespaceMemberContext) context;
		String prefix = concreteContext.getPrefix();
		String suffix = getSuffix(concreteContext);
		SourceRange replaceRange = getReplacementRange(concreteContext);

		for (IType ns : concreteContext.getNamespaces()) {
			try {
				for (IType type : ns.getTypes()) {
					if (CodeAssistUtils.startsWithIgnoreCase(type.getElementName(), prefix)) {
						reporter.reportType(type, suffix, replaceRange);
					}
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public SourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {
		SourceRange replacementRange = super.getReplacementRange(context);
		if (replacementRange.getLength() > 0) {
			return new SourceRange(replacementRange.getOffset(), replacementRange.getLength() - 1);
		}
		return replacementRange;
	}
	
	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return "::".equals(nextWord) ? "" : "::"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}