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

import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.model.PHPModelAccess;

/**
 * This strategy completes namespaces
 * 
 * @author michael
 */
public class NamespacesStrategy extends TypesStrategy {

	public NamespacesStrategy(ICompletionContext context) {
		super(context, 0, 0);
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected IType[] getTypes(AbstractCompletionContext context) throws BadLocationException {

		String prefix = context.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return EMPTY;
		} else if (prefix.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
			prefix = prefix.substring(1);
		}
		if (prefix.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
			prefix = prefix.substring(1);
		}

		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PHPModelAccess.getDefault().findNamespaces(null, prefix, MatchRule.EXACT, trueFlag, falseFlag, scope,
					null);
		}

		IType[] namespaces = PHPModelAccess.getDefault().findNamespaces(null, prefix, MatchRule.PREFIX, trueFlag,
				falseFlag, scope, null);
		List<IType> result = CodeAssistUtils.removeDuplicatedElements(namespaces);
		return result.toArray(new IType[0]);
	}
}
