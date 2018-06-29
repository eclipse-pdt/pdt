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
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;

/**
 * This strategy results like {@link ClassesStrategy}, but filters final
 * classes.
 * 
 * @author michael
 * 
 */
public class NonFinalClassesStrategy extends ClassesStrategy {

	public NonFinalClassesStrategy(ICompletionContext context) {
		super(context, 0, Modifiers.AccInterface | Modifiers.AccNameSpace | Modifiers.AccFinal);
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
