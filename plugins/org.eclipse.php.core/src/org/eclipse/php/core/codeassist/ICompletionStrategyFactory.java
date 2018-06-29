/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.codeassist;

/**
 * This factory finds correct strategies according to the given completion
 * context.
 * 
 * @author michael
 */
public interface ICompletionStrategyFactory {

	/**
	 * Creates completion strategies for the given context.
	 * 
	 * @param contexts
	 *            Completion contexts list
	 * @return completion strategies or empty list in case no strategy could be
	 *         found for the given context
	 */
	public ICompletionStrategy[] create(ICompletionContext[] contexts);
}
