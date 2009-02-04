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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

/**
 * This strategy completes variable names in 'param' PHPDoc tag
 * @author michael
 */
public class PHPDocParamVariableStrategy extends FunctionArgumentsStrategy {
	
	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		if (!(context instanceof PHPDocTagContext)) {
			return;
		}
		PHPDocTagContext tagContext = (PHPDocTagContext) context;
		
		String prefix = tagContext.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			super.apply(tagContext, reporter);
		}
	}

}
