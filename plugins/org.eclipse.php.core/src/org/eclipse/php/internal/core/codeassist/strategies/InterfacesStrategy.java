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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes global classes
 * 
 * @author michael
 */
public class InterfacesStrategy extends TypesStrategy {

	public InterfacesStrategy(ICompletionContext context) {
		super(context, Modifiers.AccInterface, 0);
	}

	@Override
	public String getSuffix(AbstractCompletionContext abstractContext, String addon) {
		return ""; //$NON-NLS-1$
	}
}
