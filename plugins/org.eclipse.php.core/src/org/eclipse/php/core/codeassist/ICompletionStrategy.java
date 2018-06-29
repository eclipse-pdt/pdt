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
 * Completion strategy resolves completion proposals according to completion
 * context.
 * 
 * @author michael
 */
public interface ICompletionStrategy {

	/**
	 * Initializes this completion strategy
	 */
	public void init(CompletionCompanion companion);

	/**
	 * Applies completion strategy for the given context
	 * 
	 * @param reporter
	 *            Where model elements will be reported
	 * @throws Exception
	 */
	public void apply(ICompletionReporter reporter) throws Exception;
}
