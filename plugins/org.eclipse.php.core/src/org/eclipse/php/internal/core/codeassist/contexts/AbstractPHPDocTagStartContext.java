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

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * Will be used for special code assist in doc blocks such as:
 * 
 * <pre>
 *   @var My|
 * </pre>
 */
public abstract class AbstractPHPDocTagStartContext extends PHPDocTagContext {

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
	 * only the lastWord is a valid class name prefix will return true if
	 * lastWord "A::",than the prefix is "", then this method should return
	 * false
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
