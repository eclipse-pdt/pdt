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

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;

/**
 * This strategy completes class methods
 * @author michael
 */
public class ClassMethodsStrategy extends ClassMembersStrategy {
	
	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		if (!(context instanceof ClassMemberContext)) {
			return;
		}

		ClassMemberContext concreteContext = (ClassMemberContext) context;
		CompletionRequestor requestor = concreteContext.getCompletionRequestor();

		int mask = 0;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}

		String prefix = concreteContext.getPrefix();
		SourceRange replaceRange = getReplacementRange(concreteContext);
		
		boolean isParentCall = isParentCall(concreteContext);
		
		String suffix = getSuffix(concreteContext);
		
		for (IType type : concreteContext.getLhsTypes()) {
			IMethod[] methods = isParentCall ? CodeAssistUtils.getSuperClassMethods(type, prefix, mask) 
				: CodeAssistUtils.getTypeMethods(type, prefix, mask);

			for (IMethod method : methods) {
				try {
					if (!isFiltered(method, concreteContext)) {
						reporter.reportMethod((IMethod) method, suffix, replaceRange);
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG_COMPLETION) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return super.showNonStaticMembers(context) || context.getTriggerType() == Trigger.CLASS;
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