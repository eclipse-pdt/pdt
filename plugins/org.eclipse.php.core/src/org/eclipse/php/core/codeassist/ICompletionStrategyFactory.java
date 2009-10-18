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
