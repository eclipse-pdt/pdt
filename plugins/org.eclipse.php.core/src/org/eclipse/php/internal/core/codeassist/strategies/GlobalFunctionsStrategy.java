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

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasMethod;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseFunctionNameContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global functions
 * 
 * @author michael
 */
public class GlobalFunctionsStrategy extends GlobalElementStrategy {

	public GlobalFunctionsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		String prefix = abstractContext.getPrefix().isEmpty() ? abstractContext.getPreviousWord()
				: abstractContext.getPrefix();
		if (StringUtils.isBlank(prefix) || prefix.startsWith("$")) { //$NON-NLS-1$
			return;
		}

		boolean isUseFunctionContext = context instanceof UseFunctionNameContext;
		int extraInfo = getExtraInfo();
		if (isUseFunctionContext) {
			extraInfo |= ProposalExtraInfo.NO_INSERT_USE;
			extraInfo |= ProposalExtraInfo.FULL_NAME;
		}

		MatchRule matchRule = MatchRule.PREFIX;
		if (requestor.isContextInformationMode()) {
			matchRule = MatchRule.EXACT;
		}
		IDLTKSearchScope scope = createSearchScope();
		IMethod[] functions = PhpModelAccess.getDefault().findFunctions(prefix, matchRule, 0, 0, scope, null);

		ISourceRange replacementRange = getReplacementRange(abstractContext);
		String suffix = getSuffix(abstractContext);

		for (IMethod method : functions) {
			reporter.reportMethod(method, suffix, replacementRange, extraInfo);
		}

		addAlias(reporter, suffix);
	}

	protected void addAlias(ICompletionReporter reporter, String suffix) throws BadLocationException {
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (abstractContext.getCompletionRequestor().isContextInformationMode()) {
			return;
		}
		String prefix = abstractContext.getPrefixWithoutProcessing();
		if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1) {
			return;
		}
		IModuleSource module = reporter.getModule();
		org.eclipse.dltk.core.ISourceModule sourceModule = (org.eclipse.dltk.core.ISourceModule) module
				.getModelElement();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		final int offset = abstractContext.getOffset();
		IType namespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);

		final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration, offset, namespace,
				false);
		reportAlias(reporter, suffix, abstractContext, module, result);
	}

	protected void reportAlias(ICompletionReporter reporter, String suffix, AbstractCompletionContext abstractContext,
			IModuleSource module, final Map<String, UsePart> result) throws BadLocationException {
		ISourceRange replacementRange = getReplacementRange(abstractContext);
		IDLTKSearchScope scope = createSearchScope();
		for (Entry<String, UsePart> entry : result.entrySet()) {
			String name = entry.getKey();
			String fullName = entry.getValue().getNamespace().getFullyQualifiedName();
			if (fullName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				fullName = fullName.substring(1);
			}
			IMethod[] elements;
			if (!fullName.contains(NamespaceReference.NAMESPACE_DELIMITER)) {
				elements = PhpModelAccess.getDefault().findMethods(null, fullName, MatchRule.PREFIX, 0, 0, scope, null);
				for (int i = 0; i < elements.length; i++) {
					String elementName = elements[i].getElementName();
					reportAlias(reporter, scope, module, replacementRange, elements[i], elementName,
							elementName.replace(fullName, name), suffix);
				}
			}
			elements = PhpModelAccess.getDefault().findMethods(fullName, MatchRule.EXACT, 0, 0, scope, null);

			for (int i = 0; i < elements.length; i++) {
				String elementName = elements[i].getElementName();
				reportAlias(reporter, scope, module, replacementRange, elements[i], elementName, name, suffix);
			}
		}
	}

	protected void reportAlias(ICompletionReporter reporter, IDLTKSearchScope scope, IModuleSource module,
			ISourceRange replacementRange, IMember member, String fullName, String alias, String suffix) {
		reporter.reportMethod(new AliasMethod((ModelElement) member, fullName, alias), suffix, replacementRange,
				getExtraInfo());
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.DEFAULT;
	}

}
