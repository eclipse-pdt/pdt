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

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.MethodNameContext;
import org.eclipse.php.internal.core.language.PHPMagicMethods;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes variable names taken from function parameters list.
 * 
 * @author michael
 */
public class MethodNameStrategy extends AbstractCompletionStrategy {

	private static String CONSTRUCTOR_SUFFIX = "()"; //$NON-NLS-1$

	public MethodNameStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public MethodNameStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof MethodNameContext)) {
			return;
		}

		MethodNameContext concreteContext = (MethodNameContext) context;
		CompletionRequestor requestor = concreteContext
				.getCompletionRequestor();

		String prefix = concreteContext.getPrefix();
		boolean exactName = requestor.isContextInformationMode();
		IType declaringClass = concreteContext.getDeclaringClass();

		String suffix = getSuffix(concreteContext);
		SourceRange replaceRange = null;
		if (suffix.equals("")) { //$NON-NLS-1$
			replaceRange = getReplacementRange(concreteContext);
		} else {
			replaceRange = getReplacementRangeWithBraces(concreteContext);
		}

		try {
			ITypeHierarchy hierarchy = getCompanion().getSuperTypeHierarchy(
					declaringClass, null);
			IMethod[] superClassMethods = PHPModelUtils
					.getSuperTypeHierarchyMethod(declaringClass, hierarchy,
							prefix, exactName, null);
			for (IMethod superMethod : superClassMethods) {
				if (declaringClass.getMethod(superMethod.getElementName())
						.exists()) {
					continue;
				}
				int flags = superMethod.getFlags();
				if (!PHPFlags.isFinal(flags) && !PHPFlags.isPrivate(flags)
						&& !PHPFlags.isStatic(flags)) {
					reporter.reportMethod(superMethod, CONSTRUCTOR_SUFFIX,
							replaceRange);
				}
			}
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}

		PHPVersion phpVersion = concreteContext.getPhpVersion();

		// Add magic methods:
		Set<String> functions = new TreeSet<String>();
		functions.addAll(Arrays.asList(PHPMagicMethods.getMethods(phpVersion)));

		// Add constructors:
		functions.add(declaringClass.getElementName());
		if (phpVersion.isGreaterThan(PHPVersion.PHP4)) {
			functions.add("__construct"); //$NON-NLS-1$
			functions.add("__destruct"); //$NON-NLS-1$
		}

		for (String function : functions) {
			if (CodeAssistUtils.startsWithIgnoreCase(function, prefix)) {
				if (!requestor.isContextInformationMode()
						|| function.length() == prefix.length()) {
					FakeMethod fakeMethod = new FakeMethod(
							(ModelElement) declaringClass, function);
					if (function.equals("__construct")) { //$NON-NLS-1$
						fakeMethod.setConstructor(true);
					}
					reporter.reportMethod(fakeMethod, suffix, replaceRange);
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
		return "(".equals(nextWord) ? "" : CONSTRUCTOR_SUFFIX; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
