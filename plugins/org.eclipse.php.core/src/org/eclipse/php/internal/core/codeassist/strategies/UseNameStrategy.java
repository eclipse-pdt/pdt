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

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

public class UseNameStrategy extends GlobalTypesStrategy {

	public UseNameStrategy(ICompletionContext context, int trueFlag,
			int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public UseNameStrategy(ICompletionContext context) {
		super(context);
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		SourceRange result = super.getReplacementRange(context);
		result = new SourceRange(result.getOffset() + result.getLength() + 1, 0);
		return result;
	}
}
