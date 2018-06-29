/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
