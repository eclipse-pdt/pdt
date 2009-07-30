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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

/**
 * This strategy completes variable names in 'param' PHPDoc tag
 * @author michael
 */
public class PHPDocParamVariableStrategy extends FunctionArgumentsStrategy {
	
	public PHPDocParamVariableStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public PHPDocParamVariableStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof PHPDocTagContext)) {
			return;
		}
		PHPDocTagContext tagContext = (PHPDocTagContext) context;
		
		String prefix = tagContext.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			super.apply(reporter);
		}
	}

}
