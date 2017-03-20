/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.*;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * This strategy completes namespaces
 */
public class TypeInStringStrategy extends AbstractCompletionStrategy {

	private final static String SUFFIX = ""; //$NON-NLS-1$
	protected static final IType[] EMPTY = {};

	protected int trueFlag;
	protected int falseFlag;

	public TypeInStringStrategy(ICompletionContext context) {
		super(context);
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.DEFAULT | ProposalExtraInfo.NO_INSERT_USE | ProposalExtraInfo.FULL_NAME
				| ProposalExtraInfo.TYPE_ONLY | ProposalExtraInfo.CLASS_IN_NAMESPACE;
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (abstractContext.getCompletionRequestor() instanceof IPHPCompletionRequestor) {
			IPHPCompletionRequestor phpCompletionRequestor = (IPHPCompletionRequestor) abstractContext
					.getCompletionRequestor();
			if (phpCompletionRequestor.filter(CompletionFlag.STOP_REPORT_TYPE)) {
				return;
			}
			if (!phpCompletionRequestor.isExplicit()) {
				return;
			}
		}
		if (StringUtils.isBlank(abstractContext.getPrefixWithoutProcessing())) {
			return;
		}
		ISourceRange replacementRange = getReplacementRange(abstractContext);

		IType[] types = getTypes(abstractContext);
		int extraInfo = getExtraInfo();

		for (IType type : types) {
			reporter.reportType(type, SUFFIX, replacementRange, extraInfo);
		}

	}

	/**
	 * Runs the query to retrieve all global types
	 * 
	 * @param context
	 * @return
	 * @throws BadLocationException
	 */
	protected IType[] getTypes(AbstractCompletionContext context) throws BadLocationException {

		String prefix = context.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return EMPTY;
		}

		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PhpModelAccess.getDefault().findTypes(prefix, MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		}

		List<IType> result = new LinkedList<IType>();
		if (prefix.contains(NamespaceReference.NAMESPACE_DELIMITER)) {
			if ((Modifiers.AccNameSpace & falseFlag) == 0) {
				result.addAll(CodeAssistUtils.removeDuplicatedElements(PhpModelAccess.getDefault().findNamespaces(null,
						prefix, MatchRule.PREFIX, trueFlag, falseFlag, scope, null)));
			}
			result.addAll(Arrays.asList(
					PhpModelAccess.getDefault().findTypes(prefix, MatchRule.PREFIX, trueFlag, falseFlag, scope, null)));
			result.addAll(Arrays.asList(
					PhpModelAccess.getDefault().findTypes(prefix, "", MatchRule.PREFIX, trueFlag, falseFlag, scope, //$NON-NLS-1$
							null)));
		} else {
			result.addAll(Arrays.asList(PhpModelAccess.getDefault().findTypes(null, prefix, MatchRule.PREFIX, trueFlag,
					falseFlag, scope, null)));
			if ((Modifiers.AccNameSpace & falseFlag) == 0) {
				result.addAll(CodeAssistUtils.removeDuplicatedElements(PhpModelAccess.getDefault().findNamespaces(null,
						prefix, MatchRule.PREFIX, trueFlag, falseFlag, scope, null)));
			}

		}

		return result.toArray(new IType[result.size()]);
	}

}
