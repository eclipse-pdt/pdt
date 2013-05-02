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
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
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
		if (suffix.equals("")) { //$NON-NLS-1$
			replaceRange = getReplacementRange(concreteContext);
		} else {
			replaceRange = getReplacementRangeWithBraces(concreteContext);
		}

		PHPVersion phpVersion = concreteContext.getPhpVersion();
		Set<String> magicMethods = new HashSet<String>();
		magicMethods.addAll(Arrays.asList(PHPMagicMethods
				.getMethods(phpVersion)));

		boolean exactName = requestor.isContextInformationMode();
		// for methodName(|),we need set exactName to true
		if (!exactName
				&& concreteContext.getOffset() - 1 >= 0
				&& concreteContext.getDocument().getChar(
						concreteContext.getOffset() - 1) == '(') {
			exactName = true;
		}
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

				boolean inConstructor = isInConstructor(type,
						type.getMethods(), concreteContext);
				for (IMethod method : removeOverriddenElements(Arrays
						.asList(methods))) {

					if (concreteContext.isInUseTraitStatement()) {
						// result.add(method);
						reporter.reportMethod((IMethod) method, "", //$NON-NLS-1$
								replaceRange, ProposalExtraInfo.METHOD_ONLY);
					} else if ((!isConstructor(method) || inConstructor
							&& isSuperConstructor(method, type, concreteContext))
							&& !isFiltered(method, type, concreteContext)) {
						if (magicMethods.contains(method.getElementName())) {
							reporter.reportMethod(method, suffix, replaceRange,
									ProposalExtraInfo.MAGIC_METHOD);
						} else {
							result.add(method);
						}
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

	private boolean isInConstructor(IType type, IMethod[] methods,
			ClassMemberContext concreteContext) {
		try {
			for (int i = 0; i < methods.length; i++) {
				IMethod method = methods[i];
				if (isConstructor(method)
						&& method.getDeclaringType().equals(type)) {
					ISourceRange construtorRange = method.getSourceRange();
					if (concreteContext.getOffset() > construtorRange
							.getOffset()
							&& concreteContext.getOffset() < construtorRange
									.getOffset() + construtorRange.getLength()) {
						return true;
					}
				}
			}
		} catch (ModelException e) {
		}
		return false;
	}

	private boolean isSuperConstructor(IMethod method, IType type,
			ClassMemberContext context) {
		if (isConstructor(method) && context.getTriggerType() == Trigger.CLASS
				&& isParent(context) && !method.getDeclaringType().equals(type)) {
			return true;
		}
		return false;
	}

	/**
	 * is 'parent' keyword
	 * 
	 * @param context
	 * @return
	 */
	private boolean isParent(ClassMemberContext context) {
		return !isThisCall(context) && isParentCall(context)
				&& isDirectParentCall(context);
	}

	private boolean isConstructor(IMethod method) {
		String methodName = method.getElementName();
		if (methodName.equals("__construct") //$NON-NLS-1$
				|| methodName
						.equals(method.getDeclaringType().getElementName())) {
			return true;
		}
		return false;
	}

	private IMethod getConstructor(IType type, IMethod[] methods) {
		for (int i = 0; i < methods.length; i++) {
			IMethod method = methods[i];
			if (isConstructor(method)) {
				return method;
			}
		}

		return null;
	}

	protected boolean showNonStaticMembers(ClassMemberContext context) {
		return super.showNonStaticMembers(context)
				|| context.getTriggerType() == Trigger.CLASS;
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
			if ("(".equals(nextWord)) { //$NON-NLS-1$
				return ""; //$NON-NLS-1$
			} else {
				// workaround for
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=323462
				if (abstractContext.getPrefix().trim().length() == 0) {
					nextWord = abstractContext.getNextWord(2);
				}
			}
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}