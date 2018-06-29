/*******************************************************************************
 * Copyright (c) 2010 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zaho Zhongwei
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * Will be used for special code assist in doc blocks such as:
 * 
 * <pre>
 *   &#64;var My|
 * </pre>
 */
public abstract class AbstractPHPDocTagStartContext extends PHPDocTagContext {

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
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
		Stack<String> stack = new Stack<>();
		while (st.hasMoreElements()) {
			stack.add((String) st.nextElement());
		}
		if (!stack.empty()) {
			String lastWord = stack.pop();
			if (lastWord.startsWith("@")) { //$NON-NLS-1$
				lastWord = lastWord.substring(1);
			}
			if (!statementTextString.endsWith(lastWord)) {
				return getTags().contains(lastWord);
			}

			if (!stack.empty() && isPrefix(lastWord)) {
				lastWord = stack.pop();
				return lastWord.endsWith(tagName);
			}
		}
		return false;
	}

	protected abstract List<String> getTags();

	/**
	 * only the lastWord is a valid class name prefix will return true if lastWord
	 * "A::",than the prefix is "", then this method should return false
	 * 
	 * @param lastWord
	 * @return
	 */
	private boolean isPrefix(String lastWord) {
		try {
			return getPrefix().endsWith(lastWord);
		} catch (BadLocationException e) {
		}
		return false;
	}

}
