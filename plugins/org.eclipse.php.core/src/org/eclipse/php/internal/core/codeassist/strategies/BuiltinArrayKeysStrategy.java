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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.ArrayKeyContext;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes builtin array keys, like in _SERVER.
 * 
 * @author michael
 */
public class BuiltinArrayKeysStrategy extends AbstractCompletionStrategy {

	protected final static String[] SERVER_VARS = { "DOCUMENT_ROOT", //$NON-NLS-1$
			"GATEWAY_INTERFACE", "HTTP_ACCEPT", "HTTP_ACCEPT_ENCODING", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"HTTP_ACCEPT_LANGUAGE", "HTTP_CONNECTION", "HTTP_HOST", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"HTTP_USER_AGENT", "PATH", "PATH_TRANSLATED", "PHP_SELF", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"QUERY_STRING", "REMOTE_ADDR", "REMOTE_PORT", "REQUEST_METHOD", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"REQUEST_TIME", "REQUEST_URI", "SCRIPT_FILENAME", "SCRIPT_NAME", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"SERVER_ADDR", "SERVER_ADMIN", "SERVER_NAME", "SERVER_PORT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"SERVER_PROTOCOL", "SERVER_SIGNATURE", "SERVER_SOFTWARE", }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	protected final static String[] SESSION_VARS = { "SID" }; //$NON-NLS-1$

	public BuiltinArrayKeysStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
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

		int extraObject = ProposalExtraInfo.DEFAULT;
		if (!arrayContext.hasQuotes()) {
			extraObject |= ProposalExtraInfo.ADD_QUOTES;
		}
		// report server variables:
		if (arrayVarName.equals("$_SERVER") //$NON-NLS-1$
				|| arrayVarName.equals("$HTTP_SERVER_VARS")) { // NON-NLS-1 //$NON-NLS-1$
			// //NON-NLS-2
			reportVariables(reporter, arrayContext, SERVER_VARS, prefix,
					extraObject);
		}
		// report session variables:
		else if (arrayVarName.equals("$_SESSION") //$NON-NLS-1$
				|| arrayVarName.equals("$HTTP_SESSION_VARS")) { // NON-NLS-1 //$NON-NLS-1$
			// //NON-NLS-2
			reportVariables(reporter, arrayContext, SESSION_VARS, prefix,
					extraObject);
		}
		// report global variables in special globals array:
		else if (arrayVarName.equals("$GLOBALS")) { // NON-NLS-1 //$NON-NLS-1$

			MatchRule matchRule = MatchRule.PREFIX;
			if (requestor.isContextInformationMode()) {
				matchRule = MatchRule.EXACT;
			}
			IDLTKSearchScope scope = createSearchScope();
			IField[] elements = PhpModelAccess.getDefault().findFields(
					"$" + prefix, matchRule, Modifiers.AccGlobal, //$NON-NLS-1$
					Modifiers.AccConstant, scope, null);
			List<IField> list = new ArrayList<IField>();

			if (!prefix.startsWith("$")) { //$NON-NLS-1$
				elements = PhpModelAccess.getDefault().findFields("$" + prefix, //$NON-NLS-1$
						matchRule, Modifiers.AccGlobal, Modifiers.AccConstant,
						scope, null);
				list.addAll(Arrays.asList(elements));
				elements = list.toArray(new IField[list.size()]);
			}

			SourceRange replaceRange = getReplacementRange(arrayContext);
			for (IModelElement element : elements) {
				IField field = (IField) element;
				try {
					ISourceRange sourceRange = field.getSourceRange();
					FakeField fakeField = new FakeField(
							(ModelElement) field.getParent(), field
									.getElementName().substring(1),
							sourceRange.getOffset(), sourceRange.getLength());
					reporter.reportField(fakeField, "", replaceRange, true, 0, //$NON-NLS-1$
							extraObject); // NON-NLS-1
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}

			PHPVersion phpVersion = arrayContext.getPhpVersion();
			reportVariables(reporter, arrayContext,
					PHPVariables.getVariables(phpVersion), prefix, true,
					extraObject);
		}
	}

	protected void reportVariables(ICompletionReporter reporter,
			ArrayKeyContext context, String[] variables, String prefix,
			int extraObject) throws BadLocationException {
		reportVariables(reporter, context, variables, prefix, false,
				extraObject);
	}

	protected void reportVariables(ICompletionReporter reporter,
			ArrayKeyContext context, String[] variables, String prefix,
			boolean removeDollar, int extraObject) throws BadLocationException {
		CompletionRequestor requestor = context.getCompletionRequestor();
		SourceRange replaceRange = getReplacementRange(context);
		for (String variable : variables) {
			if (removeDollar) {
				variable = variable.substring(1);
			}
			if (variable.startsWith(prefix)) {
				if (!requestor.isContextInformationMode()
						|| variable.length() == prefix.length()) {
					reporter.reportField(
							new FakeField((ModelElement) context
									.getSourceModule(), variable, 0, 0), "", //$NON-NLS-1$
							replaceRange, false, 0, extraObject); // NON-NLS-1
				}
			}
		}
	}
}
