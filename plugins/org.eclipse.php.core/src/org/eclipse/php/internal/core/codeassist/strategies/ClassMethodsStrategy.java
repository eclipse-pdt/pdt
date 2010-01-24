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

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext.Trigger;
import org.eclipse.php.internal.core.language.PHPMagicMethods;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes class methods
 * 
 * @author michael
 */
public class ClassMethodsStrategy extends ClassMembersStrategy {

	public ClassMethodsStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassMethodsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof ClassMemberContext)) {
			return;
		}

		ClassMemberContext concreteContext = (ClassMemberContext) context;
		CompletionRequestor requestor = concreteContext
				.getCompletionRequestor();

		String prefix = concreteContext.getPrefix();
		boolean isParentCall = isParentCall(concreteContext);
		String suffix = getSuffix(concreteContext);

		SourceRange replaceRange = null;
		if (suffix.equals("")) {
			replaceRange = getReplacementRange(concreteContext);
		} else {
			replaceRange = getReplacementRangeWithBraces(concreteContext);
		}

		PHPVersion phpVersion = concreteContext.getPhpVersion();
		Set<String> magicMethods = new HashSet<String>();
		magicMethods.addAll(Arrays.asList(PHPMagicMethods
				.getMethods(phpVersion)));

		boolean exactName = requestor.isContextInformationMode();

		List<IMethod> result = new LinkedList<IMethod>();
		for (IType type : concreteContext.getLhsTypes()) {
			try {
				ITypeHierarchy hierarchy = getCompanion()
						.getSuperTypeHierarchy(type, null);

				IMethod[] methods = isParentCall ? PHPModelUtils
						.getSuperTypeHierarchyMethod(type, hierarchy, prefix,
								exactName, null) : PHPModelUtils
						.getTypeHierarchyMethod(type, hierarchy, prefix,
								exactName, null);

				for (IMethod method : removeOverriddenElements(Arrays
						.asList(methods))) {

					if (!magicMethods.contains(method.getElementName())
							&& !isFiltered(method, concreteContext)) {
						result.add(method);
					}
				}
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}
		for (IMethod method : result) {
			reporter.reportMethod((IMethod) method, suffix, replaceRange);
		}
	}

	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return super.showNonStaticMembers(context)
				|| context.getTriggerType() == Trigger.CLASS;
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}