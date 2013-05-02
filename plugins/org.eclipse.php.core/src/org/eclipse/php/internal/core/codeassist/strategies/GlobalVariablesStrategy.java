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

import java.util.*;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.BasicSearchEngine;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree.Node;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global variables including constants
 * 
 * @author michael
 */
public class GlobalVariablesStrategy extends GlobalElementStrategy {

	private boolean showPhpVariables = true;

	public GlobalVariablesStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalVariablesStrategy(ICompletionContext context,
			boolean showPhpVariables) {
		super(context);
		this.showPhpVariables = showPhpVariables;
	}

	public GlobalVariablesStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		String prefix = abstractContext.getPrefix();

		if (prefix.length() > 0 && !prefix.startsWith("$")) { //$NON-NLS-1$
			return;
		}
		CompletionRequestor requestor = abstractContext
				.getCompletionRequestor();

		MatchRule matchRule = MatchRule.PREFIX;
		if (requestor.isContextInformationMode()) {
			matchRule = MatchRule.EXACT;
		}

		IField[] fields = null;
		if (showVarsFromOtherFiles(PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES)) {
			IDLTKSearchScope scope = createSearchScope();
			fields = PhpModelAccess.getDefault().findFields(prefix, matchRule,
					Modifiers.AccGlobal, Modifiers.AccConstant, scope, null);
		} else if (showVarsFromOtherFiles(PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_REFERENCED_FILES)) {
			// FIXME why we can't get $myGlobalVar from php
			// code:list($myGlobalVar) = 0;
			IDLTKSearchScope scope = createSearchScopeWithReferencedFiles(abstractContext
					.getSourceModule());
			fields = PhpModelAccess.getDefault().findFields(prefix, matchRule,
					Modifiers.AccGlobal, Modifiers.AccConstant, scope, null);
		}

		List<IField> result = new LinkedList<IField>();
		if (fields != null) {
			result.addAll(Arrays.asList(fields));
		}

		fields = PHPModelUtils.getFileFields(abstractContext.getSourceModule(),
				prefix, false, null);
		if (fields != null) {
			result.addAll(Arrays.asList(fields));
		}
		fields = result.toArray(new IField[result.size()]);

		SourceRange replaceRange = getReplacementRange(context);
		for (IModelElement var : fields) {
			reporter.reportField((IField) var, "", replaceRange, false); //$NON-NLS-1$
		}

		if (showPhpVariables) {
			PHPVersion phpVersion = abstractContext.getPhpVersion();
			for (String variable : PHPVariables.getVariables(phpVersion)) {
				if (variable.startsWith(prefix)) {
					if (!requestor.isContextInformationMode()
							|| variable.length() == prefix.length()) {
						reporter.reportField(
								new FakeField((ModelElement) abstractContext
										.getSourceModule(), variable, 0, 0),
								"", replaceRange, false); // NON-NLS-1 //$NON-NLS-1$
					}
				}
			}
		}
	}

	private IDLTKSearchScope createSearchScopeWithReferencedFiles(
			ISourceModule sourceModule) {
		ReferenceTree tree = FileNetworkUtility.buildReferencedFilesTree(
				sourceModule, null);
		Set<ISourceModule> list = new HashSet<ISourceModule>();
		list.add(sourceModule);
		if (tree != null && tree.getRoot() != null) {
			Collection<Node> allIncludedNodes = tree.getRoot().getChildren();
			if (allIncludedNodes != null) {
				getNodeChildren(tree.getRoot(), list);
			}
		}
		return BasicSearchEngine.createSearchScope(
				list.toArray(new ISourceModule[list.size()]),
				DLTKLanguageManager.getLanguageToolkit(sourceModule));
	}

	private void getNodeChildren(Node root, Set<ISourceModule> list) {
		if (root.getChildren() == null) {
			return;
		}
		for (Node includedNode : root.getChildren()) {
			ISourceModule sm = includedNode.getFile();
			list.add(sm);
			getNodeChildren(includedNode, list);
		}
	}

	protected boolean showVarsFromOtherFiles(String id) {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				id, true, null);
	}

	@Override
	protected IDLTKSearchScope createSearchScope() {
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (abstractContext.getPrefixWithoutProcessing().trim().length() == 0) {
			ISourceModule sourceModule = ((AbstractCompletionContext) context)
					.getSourceModule();
			return SearchEngine.createSearchScope(sourceModule);
		}
		return super.createSearchScope();
	}
}
