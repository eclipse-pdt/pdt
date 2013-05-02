/*******************************************************************************
 * Copyright (c) 2010 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zaho Zhongwei
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * Will be used for special code assist in doc blocks such as:
 * 
 * <pre>
 *   @var My|
 * </pre>
 */
public abstract class NamespaceAbstractPHPDocTagStartContext extends
		NamespacePHPDocTagContext {

	private IType[] namespaces;
	private boolean isGlobal;
	private IType[] possibleNamespaces;
	private IType currentNS;
	private String nsPrefix;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		String tagName = getTagName();
		if (!getTags().contains(tagName)) {
			return false;
		}
		TextSequence statementText = getStatementText();
		String statementTextString = statementText.toString();
		StringTokenizer st = new StringTokenizer(statementTextString);
		Stack<String> stack = new Stack<String>();
		while (st.hasMoreElements()) {
			stack.add((String) st.nextElement());
		}
		if (!stack.empty()) {
			String lastWord = stack.pop();
			if (lastWord.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
				if (!stack.empty() && isPrefix(lastWord)) {
					if (lastWord.startsWith("\\")) { //$NON-NLS-1$
						// isGlobal = true;
					}
					if (lastWord.startsWith("\\") //$NON-NLS-1$
							&& lastWord
									.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) == 0) {
						isGlobal = true;
					} else {
						initRelativeNamespace(sourceModule, offset, lastWord);
					}
					lastWord = stack.pop();
					return lastWord.endsWith(tagName);
				}
			} else {
				initRelativeNamespace(sourceModule, offset, lastWord);
				lastWord = stack.pop();
				return lastWord.endsWith(tagName);
			}
		}
		return false;
	}

	private void initRelativeNamespace(ISourceModule sourceModule, int offset,
			String lastWord) {
		String nsName = lastWord;
		String fullName = lastWord;
		nsPrefix = null;
		if (lastWord.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
			nsPrefix = lastWord.substring(0, lastWord
					.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR));
			nsName = nsName
					.substring(
							0,
							nsName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) + 1);

			try {
				namespaces = PHPModelUtils.getNamespaceOf(nsName, sourceModule,
						offset, null, null);
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		} else {
			namespaces = PhpModelAccess.NULL_TYPES;
		}
		if (lastWord.startsWith("\\")) { //$NON-NLS-1$
			nsPrefix = null;
		} else {

			currentNS = null;
			try {
				if (sourceModule.getElementAt(offset) != null) {
					IType type = (IType) sourceModule.getElementAt(offset)
							.getAncestor(IModelElement.TYPE);
					if (type.getParent() instanceof IType) {
						type = (IType) type.getParent();
					}
					if (type != null && (PHPFlags.isNamespace(type.getFlags()))) {
						currentNS = type;
						fullName = NamespaceReference.NAMESPACE_SEPARATOR
								+ currentNS.getElementName()
								+ NamespaceReference.NAMESPACE_SEPARATOR
								+ lastWord;
					} else {

					}
				}
			} catch (ModelException e1) {
				e1.printStackTrace();
			}
			if (currentNS != null) {
				if (nsPrefix == null) {
					nsPrefix = currentNS.getElementName();
				} else {
					nsPrefix = currentNS.getElementName()
							+ NamespaceReference.NAMESPACE_SEPARATOR + nsPrefix;
				}
			}
		}

		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule
				.getScriptProject());
		if (fullName.startsWith("\\")) { //$NON-NLS-1$
			fullName = fullName.substring(1);
		}
		possibleNamespaces = PhpModelAccess.getDefault().findTypes(null,
				fullName, MatchRule.PREFIX, Modifiers.AccNameSpace, 0, scope,
				null);
	}

	/**
	 * Returns the left hand side possible namespace elements.
	 */
	public IType[] getNamespaces() {
		return namespaces;
	}

	public IType[] getPossibleNamespaces() {
		return possibleNamespaces;
	}

	public IType getCurrentNS() {
		return currentNS;
	}

	public String getNsPrefix() {
		return nsPrefix;
	}

	/**
	 * Returns whether the namespace is global (only the '\' presents in the
	 * left side)
	 */
	public boolean isGlobal() {
		return isGlobal;
	}

	protected abstract List<String> getTags();

	/**
	 * only the lastWord is a valid class name prefix will return true if
	 * lastWord "A::",than the prefix is "", then this method should return
	 * false
	 * 
	 * @param lastWord
	 * @return
	 */
	private boolean isPrefix(String lastWord) {
		return getPrefixWithoutProcessing().endsWith(lastWord);
	}

	public String getPrefix() throws BadLocationException {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		TextSequence statementText = getStatementText();
		int statementLength = statementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, statementLength); // read whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				statementText, prefixEnd, true);
		return statementText.subSequence(prefixStart, prefixEnd).toString();
	}

	public int getPrefixEnd() throws BadLocationException {
		ITextRegion phpToken = getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_NS_SEPARATOR) {
			IPhpScriptRegion phpScriptRegion = getPhpScriptRegion();
			ITextRegion nextRegion = phpScriptRegion.getPhpToken(phpToken
					.getEnd());
			return getRegionCollection().getStartOffset()
					+ phpScriptRegion.getStart() + nextRegion.getTextEnd();
		}
		return super.getPrefixEnd();
	}

}
