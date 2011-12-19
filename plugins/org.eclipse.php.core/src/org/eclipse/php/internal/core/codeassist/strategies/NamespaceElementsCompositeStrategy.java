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
import java.util.Collection;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ClassInstantiationContext;
import org.eclipse.php.internal.core.codeassist.contexts.ExceptionClassInstantiationContext;
import org.eclipse.php.internal.core.codeassist.contexts.InstanceOfContext;

/**
 * This composite contains strategies that complete namespace elements
 * 
 * @author michael
 */
public class NamespaceElementsCompositeStrategy extends
		AbstractCompletionStrategy {

	private final Collection<ICompletionStrategy> strategies = new ArrayList<ICompletionStrategy>();

	public NamespaceElementsCompositeStrategy(ICompletionContext context,
			ICompletionContext[] allContexts, boolean isGlobalNamespace) {
		super(context);

		boolean hasNewClassContext = false;
		boolean hasNewExceptionClassContext = false;
		boolean hasInstanceOfContext = false;
		for (ICompletionContext c : allContexts) {
			if (c instanceof ExceptionClassInstantiationContext) {
				hasNewExceptionClassContext = true;
				break;
			} else if (c instanceof ClassInstantiationContext) {
				hasNewClassContext = true;
				break;
			} else if (c instanceof InstanceOfContext) {
				hasInstanceOfContext = true;
				break;
			}
		}

		if (isGlobalNamespace) {
			if (hasNewClassContext) {
				strategies.add(new ClassInstantiationStrategy(context));
			} else if (hasNewExceptionClassContext) {
				strategies
						.add(new ExceptionClassInstantiationStrategy(context));
			} else if (hasInstanceOfContext) {
				strategies.add(new InstanceOfStrategy(context));
			} else {
				strategies.add(new GlobalTypesStrategy(context));
				strategies.add(new GlobalFunctionsStrategy(context));
				strategies.add(new GlobalConstantsStrategy(context));
			}
		} else {
			if (hasNewClassContext) {
				strategies
						.add(new NamespaceClassInstantiationStrategy(context));
			} else if (hasNewExceptionClassContext) {
				strategies
						.add(new NamespaceExceptionClassInstantiationStrategy(
								context));
			} else if (hasInstanceOfContext) {
				strategies.add(new NamespaceInstanceOfStrategy(context));
			} else {
				strategies.add(new NamespaceTypesStrategy(context));
				strategies.add(new NamespaceFunctionsStrategy(context));
				strategies.add(new NamespaceConstantsStrategy(context));
			}
		}
	}

	public void apply(ICompletionReporter reporter) throws Exception {
		for (ICompletionStrategy strategy : strategies) {
			strategy.apply(reporter);
		}
	}
}
