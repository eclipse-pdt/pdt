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
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;

/**
 * This composite contains strategies that complete elements inside method
 * 
 * @author michael
 */
public class LocalMethodElementsCompositeStrategy extends
		AbstractCompletionStrategy {

	private final Collection<ICompletionStrategy> strategies = new ArrayList<ICompletionStrategy>();

	public LocalMethodElementsCompositeStrategy(ICompletionContext context) {
		super(context);
		strategies.add(new GlobalTypesStrategy(context));
		strategies.add(new GlobalFunctionsStrategy(context));
		strategies.add(new LocalMethodVariablesStrategy(context));
		strategies.add(new GlobalConstantsStrategy(context));
		strategies.add(new GlobalKeywordsStrategy(context));
		strategies.add(new MethodKeywordStrategy(context));
	}

	@Override
	public void init(CompletionCompanion companion) {
		super.init(companion);
		for (ICompletionStrategy strategy : strategies) {
			strategy.init(companion);
		}
	}

	public void apply(ICompletionReporter reporter) throws Exception {
		for (ICompletionStrategy strategy : strategies) {
			strategy.apply(reporter);
		}
	}
}
