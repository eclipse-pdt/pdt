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
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * This strategy completes namespaces
 * 
 * @author michael
 */
public class NamespacesStrategy extends GlobalTypesStrategy {

	public NamespacesStrategy(ICompletionContext context) {
		super(context, Modifiers.AccNameSpace, 0);
	}

	public NamespacesStrategy(ICompletionContext context,
			boolean addClassInNamespace) {
		super(context, Modifiers.AccNameSpace, 0, addClassInNamespace);
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	protected IType[] getTypes(AbstractCompletionContext context)
			throws BadLocationException {

		String prefix = context.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return EMPTY;
		}

		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PhpModelAccess.getDefault().findTypes(null, prefix,
					MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		}
		return PhpModelAccess.getDefault().findTypes(null, prefix,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);
	}
}
