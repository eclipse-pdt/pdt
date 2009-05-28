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
import org.eclipse.php.internal.core.codeassist.FakeGroupType;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

/**
 * This strategy completes global classes after 'new' statement 
 * @author michael
 */
public class ClassInstantiationStrategy extends GlobalTypesStrategy {
	
	public ClassInstantiationStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassInstantiationStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		
		ICompletionContext context = getContext();
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = concreteContext.getCompletionRequestor();
		
		IType enclosingClass = null;
		try {
			IModelElement enclosingElement = concreteContext.getSourceModule().getElementAt(concreteContext.getOffset());
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
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		
		SourceRange replaceRange = getReplacementRange(context);
		String defaultSuffix = getSuffix(concreteContext);

		IType[] types = getTypes(concreteContext);
		for (IType type : types) {

			String suffix = type instanceof FakeGroupType ? "" : defaultSuffix;
			
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
					if (DLTKCore.DEBUG_COMPLETION) {
						e.printStackTrace();
					}
				}
			}
			
			try {
				int flags = type.getFlags();
				if (PHPFlags.isAbstract(flags)) {
					continue;
				}
				
				if (ctor != null) {
					if (!PHPFlags.isPrivate(ctor.getFlags()) || type.equals(enclosingClass)) {
						ISourceRange sourceRange = type.getSourceRange();
						FakeMethod ctorMethod = new FakeMethod((ModelElement) type, type.getElementName(), 
							sourceRange.getOffset(), sourceRange.getLength(), sourceRange.getOffset(), sourceRange.getLength()) {
							public boolean isConstructor() throws ModelException {
								return true;
							}
						};
						ctorMethod.setParameters(ctor.getParameters());
						reporter.reportMethod(ctorMethod, suffix, replaceRange);
					}
				} else {
					if (!PHPFlags.isInternal(flags) && PHPFlags.isClass(flags)) {
						reporter.reportType(type, suffix, replaceRange);
					}
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		
		addSelf(concreteContext, reporter);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
