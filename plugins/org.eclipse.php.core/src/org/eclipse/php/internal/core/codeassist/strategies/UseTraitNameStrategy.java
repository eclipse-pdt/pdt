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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.model.PhpModelAccess;

public class UseTraitNameStrategy extends GlobalTypesStrategy {

	public UseTraitNameStrategy(ICompletionContext context, int trueFlag,
			int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public UseTraitNameStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		if (completionContext.getPrefix() != null
				&& completionContext.getPrefix().indexOf(
						NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
			return;
		}
		super.apply(reporter);
	}

	protected IType[] getTypes(AbstractCompletionContext context)
			throws BadLocationException {

		String prefix = context.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return EMPTY;
		}

		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PhpModelAccess.getDefault().findTypes(prefix,
					MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		}

		List<IType> result = new LinkedList<IType>();
		if (prefix.length() > 1 && prefix.toUpperCase().equals(prefix)) {
			// Search by camel-case
			IType[] types = PhpModelAccess.getDefault().findTraits(prefix,
					MatchRule.CAMEL_CASE, trueFlag, falseFlag, scope, null);
			result.addAll(Arrays.asList(types));
		}
		IType[] types = PhpModelAccess.getDefault().findTraits(null, prefix,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);
		result.addAll(Arrays.asList(types));

		return (IType[]) result.toArray(new IType[result.size()]);
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
