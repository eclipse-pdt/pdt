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
package org.eclipse.php.internal.core.codeassist.templates.strategies;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.strategies.LocalMethodVariablesStrategy;

/**
 * This strategy completes global variables including constants
 * 
 * @author zhaozw
 */
public class LocalMethodVariablesStrategyForTemplate extends
		LocalMethodVariablesStrategy {

	public LocalMethodVariablesStrategyForTemplate(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public LocalMethodVariablesStrategyForTemplate(ICompletionContext context) {
		super(context);
	}
}
