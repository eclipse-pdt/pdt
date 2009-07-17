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

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.FakeGroupMethod;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;

/**
 * This strategy completes global functions 
 * @author michael
 */
public class GlobalFunctionsStrategy extends GlobalElementStrategy {

	public GlobalFunctionsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalFunctionsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		int mask = 0;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}

		String prefix = abstractContext.getPrefix();
		if (prefix.startsWith("$")) {
			return;
		}

		IModelElement[] functions = CodeAssistUtils.getGlobalMethods(abstractContext.getSourceModule(), prefix, mask);
		SourceRange replacementRange = getReplacementRange(abstractContext);
		String suffix = functions.length > 0 && functions[0] instanceof FakeGroupMethod ? "" : getSuffix(abstractContext);

		for (IModelElement function : functions) {
			try {
				IMethod method = (IMethod) function;
				int flags = method.getFlags();
				if (!PHPFlags.isInternal(flags)) {
					reporter.reportMethod(method, suffix, replacementRange);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
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
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
