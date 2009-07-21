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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * This strategy completes global classes
 * 
 * @author michael
 */
public class GlobalClassesStrategy extends GlobalTypesStrategy {

	public GlobalClassesStrategy(ICompletionContext context) {
		super(context, ~Modifiers.AccInterface & ~Modifiers.AccNameSpace);
	}

	public GlobalClassesStrategy(ICompletionContext context, int extraFlags) {
		super(context, extraFlags);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}
}
