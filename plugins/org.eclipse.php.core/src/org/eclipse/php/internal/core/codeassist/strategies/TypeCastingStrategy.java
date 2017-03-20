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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.TypeCastingContext;

public class TypeCastingStrategy extends AbstractCompletionStrategy {

	private static final String[] TYPE_CASTS = { "int", "integer", "bool", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"boolean", "float", "double", "real", "string", "array", "object", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			"unset", "binary" }; //$NON-NLS-1$ //$NON-NLS-2$

	public TypeCastingStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		ISourceRange range = getReplacementRange(context);
		TypeCastingContext typeCastingContext = (TypeCastingContext) context;
		String prefix = typeCastingContext.getPrefix();
		for (String cast : TYPE_CASTS) {
			if (StringUtils.startsWithIgnoreCase(cast, prefix)) {
				reporter.reportKeyword(cast, "", range); //$NON-NLS-1$
			}
		}
	}

}
