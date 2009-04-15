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

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes global variables including constants 
 * @author michael
 */
public class GlobalVariablesStrategy extends GlobalElementStrategy {
	
	protected final static String[] PHP_VARIABLES = { "$_COOKIE", "$_ENV", "$_FILES", "$_GET", "$_POST", "$_REQUEST", "$_SERVER", "$_SESSION", "$GLOBALS", "$HTTP_COOKIE_VARS", "$HTTP_ENV_VARS", "$HTTP_GET_VARS", "$HTTP_POST_FILES", "$HTTP_POST_VARS", "$HTTP_SERVER_VARS", "$HTTP_SESSION_VARS", };
	
	public GlobalVariablesStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalVariablesStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		String prefix = abstractContext.getPrefix();
		
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		int mask = CodeAssistUtils.EXCLUDE_CONSTANTS;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		if (!showVarsFromOtherFiles()) {
			mask |= CodeAssistUtils.ONLY_CURRENT_FILE;
		}
		
		Set<IModelElement> variables = new TreeSet<IModelElement>(new CodeAssistUtils.AlphabeticComparator());
		variables.addAll(Arrays.asList(CodeAssistUtils.getGlobalFields(abstractContext.getSourceModule(), prefix, mask)));
		
		SourceRange replaceRange = getReplacementRange(context);

		for (IModelElement var : variables) {
			reporter.reportField((IField) var, "", replaceRange, false);
		}
		
		for (String variable : PHP_VARIABLES) {
			if (variable.startsWith(prefix)) {
				if (!requestor.isContextInformationMode() || variable.length() == prefix.length()) {
					reporter.reportField(new FakeField((ModelElement) abstractContext.getSourceModule(), variable, 0, 0), "", replaceRange, false); //NON-NLS-1
				}
			}
		}
	}

	protected boolean showVarsFromOtherFiles() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES, true, null);
	}
}
