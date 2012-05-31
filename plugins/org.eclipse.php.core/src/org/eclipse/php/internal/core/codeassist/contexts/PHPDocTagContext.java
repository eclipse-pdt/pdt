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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a line of a PHPDoc after
 * the PHPDoc tag. <br/>
 * Example:
 * 
 * <pre>
 *   /**
 *    * @sometag |
 * </pre>
 * 
 * @author michael
 */
public abstract class PHPDocTagContext extends PHPDocContext {

	private String tagName;

	private Pattern[] todos;

	protected int tagStart;

	public void setPatterns(IProject project) {
		if (project != null) {
			todos = TaskPatternsProvider.getInstance().getPatternsForProject(
					project);
		} else {
			todos = TaskPatternsProvider.getInstance()
					.getPetternsForWorkspace();
		}
	}

	private ArrayList<Matcher> createMatcherList(String content) {
		ArrayList<Matcher> list = new ArrayList<Matcher>(todos.length);
		for (int i = 0; i < todos.length; i++) {
			list.add(i, todos[i].matcher(content));
		}
		return list;
	}

	private Matcher getMinimalMatcher(ArrayList<Matcher> matchers,
			int startPosition) {
		Matcher minimal = null;
		int size = matchers.size();
		for (int i = 0; i < size;) {
			Matcher tmp = (Matcher) matchers.get(i);
			if (tmp.find(startPosition)) {
				if (minimal == null || tmp.start() < minimal.start()) {
					minimal = tmp;
				}
				i++;
			} else {
				matchers.remove(i);
				size--;
			}
		}
		return minimal;
	}

	private boolean isPHPTag(String tagName) {
		ArrayList<Matcher> matchers = createMatcherList(tagName);
		Matcher matcher = getMinimalMatcher(matchers, 0);
		return matcher != null;
	}

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		setPatterns(sourceModule.getScriptProject().getProject());
		TextSequence statementText = getStatementText();

		int tagEnd = statementText.length();
		boolean found = false;
		do {
			tagEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText,
					tagEnd);
			tagStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
					getPhpVersion(), getStatementText(), tagEnd, true);

			tagName = statementText.subSequence(tagStart, tagEnd).toString();

			if (tagStart > 0 && statementText.charAt(tagStart - 1) == '@') {
				found = true;
			} else if (isPHPTag(tagName)) {
				found = true;
			}

			tagEnd = tagStart - 1;
		} while (!found && tagStart > 0);

		return found;
	}

	/**
	 * Returns the PHPDoc tag
	 * 
	 * @return
	 */
	public String getTagName() {
		return tagName;
	}
}
