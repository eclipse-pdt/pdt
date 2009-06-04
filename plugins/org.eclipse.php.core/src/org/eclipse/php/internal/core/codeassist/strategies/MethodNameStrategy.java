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

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.MethodNameContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.language.PHPMagicMethods;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

/**
 * This strategy completes variable names taken from function parameters list.
 * @author michael
 */
public class MethodNameStrategy extends AbstractCompletionStrategy {

	public MethodNameStrategy(ICompletionContext context, IElementFilter elementFilter) {
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
		CompletionRequestor requestor = concreteContext.getCompletionRequestor();

		String prefix = concreteContext.getPrefix();

		int mask = 0;
		if (requestor.isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		
		IType declaringClass = concreteContext.getDeclaringClass();
		SourceRange replaceRange = getReplacementRange(concreteContext);
		
		IMethod[] superClassMethods = CodeAssistUtils.getSuperClassMethods(declaringClass, prefix, mask);
		for (IMethod superMethod : superClassMethods) {
			if (declaringClass.getMethod(superMethod.getElementName()).exists()) {
				continue;
			}
			try {
				int flags = superMethod.getFlags();
				if (!PHPFlags.isFinal(flags) && !PHPFlags.isPrivate(flags) && !PHPFlags.isStatic(flags) && !PHPFlags.isInternal(flags)) {
					reporter.reportMethod(superMethod, "()", replaceRange);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}

		PHPVersion phpVersion = concreteContext.getPhpVersion();

		// Add magic methods:
		Set<String> functions = new TreeSet<String>();
		functions.addAll(Arrays.asList(PHPMagicMethods.getMethods(phpVersion)));
		
		// Add constructors:
		functions.add(declaringClass.getElementName());
		if (phpVersion.isGreaterThan(PHPVersion.PHP4)) {
			functions.add("__construct");
			functions.add("__destruct");
		}

		for (String function : functions) {
			if (CodeAssistUtils.startsWithIgnoreCase(function, prefix)) {
				if (!requestor.isContextInformationMode() || function.length() == prefix.length()) {
					FakeMethod fakeMethod = new FakeMethod((ModelElement) declaringClass, function);
					reporter.reportMethod(fakeMethod, "()", replaceRange);
				}
			}
		}
	}

}
