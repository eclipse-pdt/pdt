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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerParserConstants;
import org.eclipse.php.internal.core.compiler.ast.parser.php56.PhpTokenNames;
import org.eclipse.php.internal.core.language.keywords.IPHPKeywordsInitializer;

public class UseNameStrategy extends GlobalTypesStrategy {

	public UseNameStrategy(ICompletionContext context, int trueFlag,
			int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public UseNameStrategy(ICompletionContext context) {
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

		reportKeyword(
				PhpTokenNames.getName(CompilerParserConstants.T_FUNCTION),
				reporter);
		reportKeyword(PhpTokenNames.getName(CompilerParserConstants.T_CONST),
				reporter);
		super.apply(reporter);
	}

	private void reportKeyword(String keyword, ICompletionReporter reporter)
			throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		if (completionContext.getPrefix() == null
				|| keyword.startsWith(completionContext.getPrefix())) {
			reporter.reportKeyword(keyword,
					IPHPKeywordsInitializer.WHITESPACE_SUFFIX,
					getReplacementRange(completionContext));
		}
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
