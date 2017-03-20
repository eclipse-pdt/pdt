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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasField;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseConstNameContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global constants
 * 
 * @author michael
 */
public class GlobalConstantsStrategy extends GlobalElementStrategy {

	public GlobalConstantsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalConstantsStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();
		if (StringUtils.isBlank(abstractContext.getPrefixWithoutProcessing())) {
			return;
		}

		String prefix = abstractContext.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return;
		}

		boolean isUseConstContext = context instanceof UseConstNameContext;
		int extraInfo = getExtraInfo();
		if (isUseConstContext) {
			extraInfo |= ProposalExtraInfo.NO_INSERT_USE;
			extraInfo |= ProposalExtraInfo.FULL_NAME;
		}

		MatchRule matchRule = MatchRule.PREFIX;
		if (requestor.isContextInformationMode()) {
			matchRule = MatchRule.EXACT;
		}

		ISourceModule sourceModule = abstractContext.getSourceModule();

		IType enclosingType = null;
		try {
			IModelElement enclosingElement = sourceModule.getElementAt(abstractContext.getOffset());

			if (enclosingElement != null && enclosingElement instanceof IType) {
				enclosingType = (IType) enclosingElement;
			}

		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}

		IDLTKSearchScope scope = null;
		IModelElement[] enclosingTypeConstants = null;

		if (enclosingType != null && isStartOfStatement(prefix, abstractContext, abstractContext.getOffset())) {
			// See the case of testClassStatement1.pdtt and
			// testClassStatement2.pdtt
			scope = SearchEngine.createSearchScope(enclosingType);
		} else {
			scope = getSearchScope(abstractContext);
		}

		enclosingTypeConstants = PhpModelAccess.getDefault().findFields(prefix, matchRule, Modifiers.AccConstant, 0,
				scope, null);

		if (isCaseSensitive()) {
			enclosingTypeConstants = filterByCase(enclosingTypeConstants, prefix);
		}
		// workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=310383
		enclosingTypeConstants = filterClassConstants(enclosingTypeConstants);
		// workaround end
		ISourceRange replaceRange = getReplacementRange(abstractContext);
		for (IModelElement constant : enclosingTypeConstants) {
			IField field = (IField) constant;
			reporter.reportField(field, "", replaceRange, false, 0, extraInfo); //$NON-NLS-1$
		}
		addAlias(reporter);
	}

	protected void addAlias(ICompletionReporter reporter) throws BadLocationException {
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
		reportAlias(reporter, abstractContext, module, result);
	}

	protected void reportAlias(ICompletionReporter reporter, AbstractCompletionContext abstractContext,
			IModuleSource module, final Map<String, UsePart> result) throws BadLocationException {
		ISourceRange replacementRange = getReplacementRange(abstractContext);
		IDLTKSearchScope scope = createSearchScope();
		for (Entry<String, UsePart> entry : result.entrySet()) {
			String name = entry.getKey();
			String fullName = entry.getValue().getNamespace().getFullyQualifiedName();
			if (fullName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				fullName = fullName.substring(1);
			}
			IField[] elements = PhpModelAccess.getDefault().findFields(null, fullName, MatchRule.PREFIX, 0, 0, scope,
					null);
			for (int i = 0; i < elements.length; i++) {
				String elementName = elements[i].getElementName();
				reportAlias(reporter, scope, module, replacementRange, elements[i], elementName,
						elementName.replace(fullName, name));
			}

			elements = PhpModelAccess.getDefault().findFields(fullName, MatchRule.EXACT, 0, 0, scope, null);
			for (int i = 0; i < elements.length; i++) {
				String elementName = elements[i].getElementName();
				reportAlias(reporter, scope, module, replacementRange, elements[i], elementName, name);
			}
		}
	}

	protected void reportAlias(ICompletionReporter reporter, IDLTKSearchScope scope, IModuleSource module,
			ISourceRange replacementRange, IMember member, String fullName, String alias) {
		reporter.reportField(new AliasField((ModelElement) member, fullName, alias), "", replacementRange, false, 0, //$NON-NLS-1$
				getExtraInfo());
	}

	private boolean isStartOfStatement(String prefix, AbstractCompletionContext abstractContext, int offset) {
		IDocument doc = abstractContext.getDocument();
		try {
			int pos = offset - prefix.length() - 1;
			char previousChar = doc.getChar(pos);

			while (Character.isWhitespace(previousChar)) {
				pos--;
				previousChar = doc.getChar(pos);
			}

			return previousChar == '{' || previousChar == ';';
		} catch (BadLocationException e) {
		}

		return false;
	}

	private IModelElement[] filterClassConstants(IModelElement[] elements) {
		List<IModelElement> result = new ArrayList<IModelElement>(elements.length);
		for (IModelElement element : elements) {
			try {
				if ((((IField) element).getFlags() & PHPCoreConstants.AccClassField) == 0) {
					result.add(element);
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return (IModelElement[]) result.toArray(new IModelElement[result.size()]);
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.DEFAULT;
	}

	private IDLTKSearchScope getSearchScope(AbstractCompletionContext abstractContext) {
		IDLTKSearchScope scope = createSearchScope();
		return scope;
	}

}
