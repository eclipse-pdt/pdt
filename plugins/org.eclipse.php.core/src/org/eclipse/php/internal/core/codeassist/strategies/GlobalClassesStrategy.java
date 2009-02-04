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

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;

/**
 * This strategy completes global classes 
 * @author michael
 */
public class GlobalClassesStrategy extends GlobalElementStrategy {
	
	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		int mask = 0;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		String prefix = abstractContext.getPrefix();
		IType[] classes = CodeAssistUtils.getGlobalTypes(abstractContext.getSourceModule(), prefix, mask);
		SourceRange replacementRange = getReplacementRange(abstractContext);

		for (IType type : classes) {
			try {
				if (!PHPFlags.isInternal(type.getFlags())) {
					reporter.reportType(type, " ", replacementRange);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
