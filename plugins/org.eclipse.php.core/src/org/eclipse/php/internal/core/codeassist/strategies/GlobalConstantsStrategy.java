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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;

/**
 * This strategy completes global constants 
 * @author michael
 */
public class GlobalConstantsStrategy extends GlobalElementStrategy {

	public GlobalConstantsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalConstantsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		int mask = CodeAssistUtils.EXCLUDE_VARIABLES;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		if (isCaseSensitive()) {
			mask |= CodeAssistUtils.CASE_SENSITIVE;
		}

		String prefix = abstractContext.getPrefix();
		if (prefix.startsWith("$")) {
			return;
		}

		SourceRange replaceRange = getReplacementRange(abstractContext);

		IModelElement[] constants = CodeAssistUtils.getGlobalFields(abstractContext.getSourceModule(), prefix, mask);
		for (IModelElement constant : constants) {
			try {
				if (!constant.getElementName().startsWith("$") && PHPFlags.isConstant(((IField) constant).getFlags())) {
					reporter.reportField((IField) constant, "", replaceRange, false);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
	}
}
