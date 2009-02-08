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
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassObjMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;

/**
 * This strategy completes class constants and variables.
 * @author michael
 */
public class ClassFieldsStrategy extends ClassMembersStrategy {

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

		boolean showStaticMembers = showStaticMembers(concreteContext);
		boolean showNonStaticMembers = showNonStaticMembers(concreteContext);
		boolean showNonStrictOptions = showNonStrictOptions();
		boolean isThisCall = ((context instanceof ClassObjMemberContext) && ((ClassObjMemberContext) context).isThisCall());

		for (IType type : concreteContext.getLhsTypes()) {
			IModelElement[] fields = CodeAssistUtils.getTypeFields(type, prefix, mask);

			for (IModelElement element : fields) {
				IField field = (IField) element;
				try {
					int flags = field.getFlags();
					if (showNonStaticMembers && !PHPFlags.isStatic(flags)) {
						if (PHPFlags.isConstant(flags) || showNonStrictOptions || isThisCall || !PHPFlags.isPrivate(flags)) {
							reporter.reportField(field, getSuffix(), replaceRange, true);
						}
					}
					if (showStaticMembers) {
						if (PHPFlags.isConstant(flags) || concreteContext.getPhpVersion().isLessThan(PHPVersion.PHP5) 
								|| showNonStrictOptions || PHPFlags.isStatic(flags)) {
							reporter.reportField(field, getSuffix(), replaceRange, false);
						}
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG_COMPLETION) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String getSuffix() {
		return "";
	}
}