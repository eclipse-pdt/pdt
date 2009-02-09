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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * This composite contains strategies that complete namespace elements
 * @author michael
 */
public class NamespaceElementsCompositeStrategy implements ICompletionStrategy {

	private final Collection<ICompletionStrategy> strategies = new ArrayList<ICompletionStrategy>();

	public NamespaceElementsCompositeStrategy(boolean isGlobalNamespace) {
		if (isGlobalNamespace) {
			strategies.add(new GlobalTypesStrategy());
			strategies.add(new GlobalFunctionsStrategy());
			strategies.add(new GlobalConstantsStrategy());
		} else {
			strategies.add(new NamespaceTypesStrategy());
			strategies.add(new NamespaceFunctionsStrategy());
			strategies.add(new NamespaceConstantsStrategy());
		}
	}

	public void apply(ICompletionContext context, ICompletionReporter reporter) throws Exception {
		for (ICompletionStrategy strategy : strategies) {
			strategy.apply(context, reporter);
		}
	}
}
