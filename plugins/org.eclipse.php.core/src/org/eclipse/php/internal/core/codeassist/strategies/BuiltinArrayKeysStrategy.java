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
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ArrayKeyContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes builtin array keys, like in _SERVER.
 * @author michael
 */
public class BuiltinArrayKeysStrategy extends GlobalElementStrategy {

	protected final static String[] SERVER_VARS = { "DOCUMENT_ROOT", "GATEWAY_INTERFACE", "HTTP_ACCEPT", "HTTP_ACCEPT_ENCODING", "HTTP_ACCEPT_LANGUAGE", "HTTP_CONNECTION", "HTTP_HOST", "HTTP_USER_AGENT", "PATH", "PATH_TRANSLATED", "PHP_SELF", "QUERY_STRING", "REMOTE_ADDR", "REMOTE_PORT",
		"REQUEST_METHOD", "REQUEST_URI", "SCRIPT_FILENAME", "SCRIPT_NAME", "SERVER_ADDR", "SERVER_ADMIN", "SERVER_NAME", "SERVER_PORT", "SERVER_PROTOCOL", "SERVER_SIGNATURE", "SERVER_SOFTWARE", };

	protected final static String[] SESSION_VARS = { "SID" };

	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		if (!(context instanceof ArrayKeyContext)) {
			return;
		}

		ArrayKeyContext arrayContext = (ArrayKeyContext) context;
		CompletionRequestor requestor = arrayContext.getCompletionRequestor();
		String arrayVarName = arrayContext.getArrayVarName();

		String prefix = arrayContext.getPrefix();

		if (arrayVarName.equals("$_SERVER") || arrayVarName.equals("$HTTP_SERVER_VARS")) { //NON-NLS-1 //NON-NLS-2
			reportVariables(reporter, arrayContext, SERVER_VARS, prefix);
		} else if (arrayVarName.equals("$_SESSION") || arrayVarName.equals("$HTTP_SESSION_VARS")) { //NON-NLS-1 //NON-NLS-2
			reportVariables(reporter, arrayContext, SESSION_VARS, prefix);
		} else if (arrayVarName.equals("$GLOBALS")) { //NON-NLS-1
			int mask = 0;
			if (requestor.isContextInformationMode()) {
				mask |= CodeAssistUtils.EXACT_NAME;
			}
			IModelElement[] elements = CodeAssistUtils.getGlobalFields(arrayContext.getSourceModule(), prefix, mask);
			SourceRange replaceRange = new SourceRange(arrayContext.getOffset() - prefix.length(), prefix.length());
			for (IModelElement element : elements) {
				IField field = (IField) element;
				reporter.reportField(field, "", replaceRange); //NON-NLS-1
			}

			reportVariables(reporter, arrayContext, PHP_VARIABLES, prefix, true);
		}

		if (!arrayContext.hasQuotes()) {
			super.apply(arrayContext, reporter);
		}
	}

	protected void reportVariables(ICompletionReporter reporter, ArrayKeyContext context, String[] variables, String prefix) {
		reportVariables(reporter, context, variables, prefix, false);
	}
	
	protected void reportVariables(ICompletionReporter reporter, ArrayKeyContext context, String[] variables, String prefix, boolean removeDollar) {
		CompletionRequestor requestor = context.getCompletionRequestor();
		SourceRange replaceRange = new SourceRange(context.getOffset() - prefix.length(), prefix.length());
		for (String variable : variables) {
			if (removeDollar) {
				variable = variable.substring(1);
			}
			if (variable.startsWith(prefix)) {
				if (!requestor.isContextInformationMode() || variable.length() == prefix.length()) {
					reporter.reportField(new FakeField((ModelElement) context.getSourceModule(), variable, 0, 0), "", replaceRange); //NON-NLS-1
				}
			}
		}
	}
}
