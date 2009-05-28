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
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;

/**
 * This strategy completes class constants and variables.
 * @author michael
 */
public class ClassFieldsStrategy extends ClassMembersStrategy {
	
	public ClassFieldsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassFieldsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
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

		for (IType type : concreteContext.getLhsTypes()) {
			IModelElement[] fields = CodeAssistUtils.getTypeFields(type, prefix, mask);

			for (IModelElement element : fields) {
				IField field = (IField) element;
				try {
					if (!isFiltered(field, concreteContext)) {
						reporter.reportField(field, getSuffix(), replaceRange, concreteContext.getTriggerType() == Trigger.OBJECT);
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
		return super.showNonStaticMembers(context) && !isParentCall(context);
	}



	public String getSuffix() {
		return "";
	}
}