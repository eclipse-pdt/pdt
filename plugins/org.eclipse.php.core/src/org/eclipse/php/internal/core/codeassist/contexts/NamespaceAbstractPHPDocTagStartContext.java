/*******************************************************************************
 * Copyright (c) 2010, 2015 Zend Technologies and others.
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

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * Will be used for special code assist in doc blocks such as:
 * 
 * <pre>
 *   &#64;var My|
 * </pre>
 */
public abstract class NamespaceAbstractPHPDocTagStartContext extends NamespacePHPDocTagContext {

	private IType[] namespaces;
	private boolean isGlobal;
	private IType[] possibleNamespaces;
	private IType currentNS;
	private String nsPrefix;

	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
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
			String[] elements = lastWord.split("[|]"); //$NON-NLS-1$
			if (elements.length > 1) {
				lastWord = elements[elements.length - 1];
			}
			if (lastWord.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
				if (!stack.empty() && isPrefix(lastWord)) {
					if (lastWord.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
						// isGlobal = true;
					}
					if (lastWord.startsWith(NamespaceReference.NAMESPACE_DELIMITER)
							&& lastWord.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) == 0) {
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

	private void initRelativeNamespace(ISourceModule sourceModule, int offset, String lastWord) {
		String nsName = lastWord;
		String fullName = lastWord;
		nsPrefix = null;
		if (lastWord.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
			nsPrefix = lastWord.substring(0, lastWord.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR));
			nsName = nsName.substring(0, nsName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) + 1);

			try {
				namespaces = PHPModelUtils.getNamespaceOf(nsName, sourceModule, offset, null, null);
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		} else {
			namespaces = PhpModelAccess.NULL_TYPES;
		}
		if (lastWord.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
			nsPrefix = null;
		} else {

			currentNS = null;
			try {
				IModelElement enclosingElement = getEnclosingElement();
				if (enclosingElement != null) {
					IType type = (IType) enclosingElement.getAncestor(IModelElement.TYPE);
					if (type != null && type.getParent() instanceof IType) {
						type = (IType) type.getParent();
					}
					if (type != null && (PHPFlags.isNamespace(type.getFlags()))) {
						currentNS = type;
						fullName = NamespaceReference.NAMESPACE_SEPARATOR + currentNS.getElementName()
								+ NamespaceReference.NAMESPACE_SEPARATOR + lastWord;
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
					nsPrefix = currentNS.getElementName() + NamespaceReference.NAMESPACE_SEPARATOR + nsPrefix;
				}
			}
		}

		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		if (fullName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
			fullName = fullName.substring(1);
		}
		possibleNamespaces = PhpModelAccess.getDefault().findNamespaces(null, fullName, MatchRule.PREFIX, 0, 0, scope,
				null);
		possibleNamespaces = CodeAssistUtils.removeDuplicatedElements(possibleNamespaces).toArray(new IType[0]);
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

	public int getPrefixEnd() throws BadLocationException {
		ITextRegion phpToken = getPHPToken();
		if (phpToken.getType() == PHPRegionTypes.PHP_NS_SEPARATOR
				// Check that there's no other (whitespace) characters
				// after the namespace separator, otherwise there's no reason
				// to retrieve the next region.
				&& phpToken.getLength() == NamespaceReference.NAMESPACE_DELIMITER.length()) {
			IPhpScriptRegion phpScriptRegion = getPhpScriptRegion();
			ITextRegion nextRegion = phpScriptRegion.getPhpToken(phpToken.getEnd());
			// Also check that we only retrieve PHP labels.
			if (nextRegion.getType() == PHPRegionTypes.PHP_LABEL) {
				return getRegionCollection().getStartOffset() + phpScriptRegion.getStart() + nextRegion.getTextEnd();
			}
		}
		return super.getPrefixEnd();
	}

}
