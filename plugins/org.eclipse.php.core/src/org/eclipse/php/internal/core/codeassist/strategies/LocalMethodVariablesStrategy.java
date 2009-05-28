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
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalMethodStatementContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes global variables including constants 
 * @author michael
 */
public class LocalMethodVariablesStrategy extends GlobalElementStrategy {
	
	public LocalMethodVariablesStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public LocalMethodVariablesStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException, ModelException {
		ICompletionContext context = getContext();
		if (!(context instanceof GlobalMethodStatementContext)) {
			return;
		}

		GlobalMethodStatementContext concreteContext = (GlobalMethodStatementContext) context;
		CompletionRequestor requestor = concreteContext.getCompletionRequestor();
		String prefix = concreteContext.getPrefix();
		SourceRange replaceRange = getReplacementRange(context);

		int mask = 0;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		IMethod enclosingMethod = concreteContext.getEnclosingMethod();
		for (IModelElement element : CodeAssistUtils.getMethodFields(enclosingMethod, prefix, mask)) {
			reporter.reportField((IField) element, "", replaceRange, false);
		}

		// complete class variable: $this
		if (!PHPFlags.isStatic(enclosingMethod.getFlags())) {
			IType declaringType = enclosingMethod.getDeclaringType();
			if (declaringType != null) {
				if ("$this".startsWith(prefix)) { //$NON-NLS-1$
					reporter.reportField(new FakeField((ModelElement) declaringType, "$this", 0, 0), "->", replaceRange, false); //NON-NLS-1 //$NON-NLS-2$
				}
			}
		}
	}
}
