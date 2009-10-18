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

import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;

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
