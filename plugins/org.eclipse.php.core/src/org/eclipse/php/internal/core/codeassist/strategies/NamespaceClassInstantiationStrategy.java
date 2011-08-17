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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceMemberContext;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

/**
 * This strategy completes global classes after 'new' statement
 * 
 * @author michael
 */
public class NamespaceClassInstantiationStrategy extends NamespaceTypesStrategy {

	public NamespaceClassInstantiationStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public NamespaceClassInstantiationStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		NamespaceMemberContext concreteContext = (NamespaceMemberContext) context;
		CompletionRequestor requestor = concreteContext
				.getCompletionRequestor();

		IType enclosingClass = null;
		try {
			IModelElement enclosingElement = concreteContext.getSourceModule()
					.getElementAt(concreteContext.getOffset());
			while (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if (enclosingElement instanceof IMethod) {
				IModelElement parent = ((IMethod) enclosingElement).getParent();
				if (parent instanceof IType) {
					enclosingClass = (IType) parent;
				}
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}

		SourceRange replaceRange = getReplacementRange(context);

		IType[] types = getTypes(concreteContext);
		String suffix = getSuffix(concreteContext);
		for (IType type : types) {
			IMethod ctor = null;
			if (requestor.isContextInformationMode()) {
				try {
					for (IMethod method : type.getMethods()) {
						if (method.isConstructor()) {
							ctor = method;
							break;
						}
					}
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}

			try {
				if (ctor != null) {
					if (!PHPFlags.isPrivate(ctor.getFlags())
							|| type.equals(enclosingClass)) {
						FakeMethod ctorMethod = new FakeMethod(
								(ModelElement) type, type.getElementName()) {
							public boolean isConstructor()
									throws ModelException {
								return true;
							}
						};
						ctorMethod.setParameters(ctor.getParameters());
						reporter.reportMethod(ctorMethod, suffix, replaceRange);
					}
				} else {
					int flags = type.getFlags();
					if (PHPFlags.isClass(flags)) {
						// here we use fake method,and do the real work in class
						// ParameterGuessingProposal
						IMethod ctorMethod = FakeConstructor
								.createFakeConstructor(null, type,
										type.equals(enclosingClass));
						reporter.reportMethod(ctorMethod, suffix, replaceRange);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
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
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
