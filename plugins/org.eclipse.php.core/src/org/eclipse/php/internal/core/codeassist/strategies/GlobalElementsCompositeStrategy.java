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

/**
 * This composite contains strategies that complete global elements
 * @author michael
 */
public class GlobalElementsCompositeStrategy extends AbstractCompletionStrategy {

	private final Collection<ICompletionStrategy> strategies = new ArrayList<ICompletionStrategy>();
	
	public GlobalElementsCompositeStrategy(ICompletionContext context, boolean includeKeywords) {
		super(context);
		
		strategies.add(new GlobalTypesStrategy(context));
		strategies.add(new GlobalFunctionsStrategy(context));
		strategies.add(new GlobalVariablesStrategy(context));
		strategies.add(new GlobalConstantsStrategy(context));
		if (includeKeywords) {
			strategies.add(new GlobalKeywordsStrategy(context));
		}
	}

	public void apply(ICompletionReporter reporter) throws Exception {
		for (ICompletionStrategy strategy : strategies) {
			strategy.apply(reporter);
		}
	}
}
