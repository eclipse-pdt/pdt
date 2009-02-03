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

import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * Completion strategy resolves completion proposals according to completion context.
 * @author michael
 */
public interface ICompletionStrategy {

	/**
	 * Applies completion strategy for the given context
	 * @param context Completion context
	 * @param reporter Where model elements will be reported
	 */
	public void apply(ICompletionContext context, ICompletionReporter reporter);
}
