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
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalMethodStatementContext;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global variables including constants
 * 
 * @author michael
 */
public class LocalMethodVariablesStrategy extends GlobalElementStrategy {

	private static final String THIS = "$this"; //$NON-NLS-1$

	public LocalMethodVariablesStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public LocalMethodVariablesStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter)
			throws BadLocationException, ModelException {
		ICompletionContext context = getContext();
		if (!(context instanceof GlobalMethodStatementContext)) {
			return;
		}

		GlobalMethodStatementContext concreteContext = (GlobalMethodStatementContext) context;
		CompletionRequestor requestor = concreteContext
				.getCompletionRequestor();
		String prefix = concreteContext.getPrefix();

		String suffix = getSuffix(concreteContext);
		SourceRange replaceRange = null;
		if (suffix.equals("")) { //$NON-NLS-1$
			replaceRange = getReplacementRange(concreteContext);
		} else {
			replaceRange = getReplacementRangeWithBraces(concreteContext);
		}
		replaceRange = getReplacementRange(context);

		IMethod enclosingMethod = concreteContext.getEnclosingMethod();

		// complete class variable: $this
		if (!PHPFlags.isStatic(enclosingMethod.getFlags())) {
			IType declaringType = enclosingMethod.getDeclaringType();
			if (declaringType != null) {
				if (THIS.startsWith(prefix)) { //$NON-NLS-1$
					reporter.reportField(new FakeField(
							(ModelElement) declaringType, THIS, 0, 0), suffix,
							replaceRange, false,
							ICompletionReporter.RELEVANCE_ADJUST); //NON-NLS-1 //$NON-NLS-2$
				}
			} else {
				if (enclosingMethod.getParent() instanceof IField
						&& concreteContext.getPhpVersion().isGreaterThan(
								PHPVersion.PHP5_3)) {
					IMethod method = (IMethod) enclosingMethod.getParent()
							.getAncestor(IModelElement.METHOD);
					if (method != null) {
						declaringType = method.getDeclaringType();
						if (THIS.startsWith(prefix)) { //$NON-NLS-1$
							reporter.reportField(new FakeField(
									(ModelElement) declaringType, THIS, 0, 0),
									suffix, replaceRange, false,
									ICompletionReporter.RELEVANCE_ADJUST); //NON-NLS-1 //$NON-NLS-2$
						}
					}
				}
			}
		}

		for (IModelElement element : PHPModelUtils.getMethodFields(
				enclosingMethod, prefix, requestor.isContextInformationMode(),
				null)) {
			reporter.reportField((IField) element, "", replaceRange, false, //$NON-NLS-1$
					ICompletionReporter.RELEVANCE_ADJUST);
		}

		PHPVersion phpVersion = concreteContext.getPhpVersion();
		for (String variable : PHPVariables.getVariables(phpVersion)) {
			if (variable.startsWith(prefix)) {
				if (!requestor.isContextInformationMode()
						|| variable.length() == prefix.length()) {
					reporter.reportField(new FakeField(
							(ModelElement) concreteContext.getSourceModule(),
							variable, 0, 0), "", replaceRange, false, //$NON-NLS-1$
							-ICompletionReporter.RELEVANCE_ADJUST); // NON-NLS-1
				}
			}
		}
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "->".equals(nextWord) ? "" : "->"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
