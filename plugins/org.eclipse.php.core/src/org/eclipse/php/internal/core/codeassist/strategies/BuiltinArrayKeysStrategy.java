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
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ArrayKeyContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes builtin array keys, like in _SERVER.
 * @author michael
 */
public class BuiltinArrayKeysStrategy extends AbstractCompletionStrategy {

	protected final static String[] SERVER_VARS = { "DOCUMENT_ROOT", "GATEWAY_INTERFACE", "HTTP_ACCEPT", "HTTP_ACCEPT_ENCODING", "HTTP_ACCEPT_LANGUAGE", "HTTP_CONNECTION", "HTTP_HOST", "HTTP_USER_AGENT", "PATH", "PATH_TRANSLATED", "PHP_SELF", "QUERY_STRING", "REMOTE_ADDR", "REMOTE_PORT",
		"REQUEST_METHOD", "REQUEST_URI", "SCRIPT_FILENAME", "SCRIPT_NAME", "SERVER_ADDR", "SERVER_ADMIN", "SERVER_NAME", "SERVER_PORT", "SERVER_PROTOCOL", "SERVER_SIGNATURE", "SERVER_SOFTWARE", };

	protected final static String[] SESSION_VARS = { "SID" };
	
	
	public BuiltinArrayKeysStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public BuiltinArrayKeysStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof ArrayKeyContext)) {
			return;
		}

		ArrayKeyContext arrayContext = (ArrayKeyContext) context;
		CompletionRequestor requestor = arrayContext.getCompletionRequestor();
		String arrayVarName = arrayContext.getArrayVarName();

		String prefix = arrayContext.getPrefix();

		// report server variables:
		if (arrayVarName.equals("$_SERVER") || arrayVarName.equals("$HTTP_SERVER_VARS")) { //NON-NLS-1 //NON-NLS-2
			reportVariables(reporter, arrayContext, SERVER_VARS, prefix);
		}
		// report session variables:
		else if (arrayVarName.equals("$_SESSION") || arrayVarName.equals("$HTTP_SESSION_VARS")) { //NON-NLS-1 //NON-NLS-2
			reportVariables(reporter, arrayContext, SESSION_VARS, prefix);
		}
		// report global variables in special globals array:
		else if (arrayVarName.equals("$GLOBALS")) { //NON-NLS-1
			int mask = CodeAssistUtils.EXCLUDE_CONSTANTS;
			if (requestor.isContextInformationMode()) {
				mask |= CodeAssistUtils.EXACT_NAME;
			}
			IModelElement[] elements = CodeAssistUtils.getGlobalFields(arrayContext.getSourceModule(), prefix, mask);
			SourceRange replaceRange = getReplacementRange(arrayContext);
			for (IModelElement element : elements) {
				IField field = (IField) element;
				try {
					ISourceRange sourceRange = field.getSourceRange();
					FakeField fakeField = new FakeField((ModelElement) field.getParent(), field.getElementName().substring(1), sourceRange.getOffset(), sourceRange.getLength());
					reporter.reportField(fakeField, "", replaceRange, true); //NON-NLS-1
				} catch (ModelException e) {
					if (DLTKCore.DEBUG_COMPLETION) {
						e.printStackTrace();
					}
				}
			}

			PHPVersion phpVersion = arrayContext.getPhpVersion();
			reportVariables(reporter, arrayContext, PHPVariables.getVariables(phpVersion), prefix, true);
		}
	}

	protected void reportVariables(ICompletionReporter reporter, ArrayKeyContext context, String[] variables, String prefix) throws BadLocationException {
		reportVariables(reporter, context, variables, prefix, false);
	}
	
	protected void reportVariables(ICompletionReporter reporter, ArrayKeyContext context, String[] variables, String prefix, boolean removeDollar) throws BadLocationException {
		CompletionRequestor requestor = context.getCompletionRequestor();
		SourceRange replaceRange = getReplacementRange(context);
		for (String variable : variables) {
			if (removeDollar) {
				variable = variable.substring(1);
			}
			if (variable.startsWith(prefix)) {
				if (!requestor.isContextInformationMode() || variable.length() == prefix.length()) {
					reporter.reportField(new FakeField((ModelElement) context.getSourceModule(), variable, 0, 0), "", replaceRange, false); //NON-NLS-1
				}
			}
		}
	}
}
