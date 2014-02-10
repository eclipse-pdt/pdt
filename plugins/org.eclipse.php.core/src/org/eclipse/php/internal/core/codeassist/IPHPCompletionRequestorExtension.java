/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.strategies.CompletionStrategyFactory;

/**
 * This interface allow change default PHPCompletionEngine behaviour, and inject
 * own context and strategies
 * 
 * @see bug #427408
 * @author Dawid Pakuła
 */
public interface IPHPCompletionRequestorExtension {

	/**
	 * @see {@link CompletionContextResolver#getActive()}
	 */
	public ICompletionContextResolver[] getContextResolvers();

	/**
	 * @see {@link CompletionStrategyFactory#getActive()}
	 */
	public ICompletionStrategyFactory[] getStrategyFactories();
}
