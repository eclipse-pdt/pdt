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
package org.eclipse.php.internal.ui.search.text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.internal.ui.text.FileMatch;
import org.eclipse.search.internal.ui.text.LineElement;

/**
 * Description: This is a visitor on the text results
 * 
 * @author Roy, 2007
 */
public class ResultVisitor extends TextSearchRequestor {
	private final List fResult = new LinkedList();
	private final boolean fIsFileSearchOnly;
	private final boolean fSearchInBinaries;
	private final List fCachedMatches = new ArrayList();

	public ResultVisitor(boolean isFileSearchOnly, boolean searchInBinaries) {
		fIsFileSearchOnly = isFileSearchOnly;
		fSearchInBinaries = searchInBinaries;
	}

	public ResultVisitor() {
		this(false, false);
	}

	public boolean acceptFile(IFile file) throws CoreException {
		if (fIsFileSearchOnly) {
			fResult.add(new FileMatch(file));
		}
		flushMatches();
		return true;
	}

	public boolean reportBinaryFile(IFile file) {
		return fSearchInBinaries;
	}

	public boolean acceptPatternMatch(TextSearchMatchAccess matchRequestor)
			throws CoreException {
		fCachedMatches.add(new FileMatch(matchRequestor.getFile(),
				matchRequestor.getMatchOffset(), matchRequestor
						.getMatchLength(), getLineElement(matchRequestor
						.getMatchOffset(), matchRequestor)));
		return true;
	}

	public void beginReporting() {
		fCachedMatches.clear();
	}

	public void endReporting() {
		flushMatches();
	}

	/**
	 * @return a list of {@link FileMatch}
	 */
	public List getResult() {
		return fResult;
	}

	/**
	 * Clears resuls
	 */
	public void clear() {
		fResult.clear();
	}

	private void flushMatches() {
		if (!fCachedMatches.isEmpty()) {
			fResult.addAll(fCachedMatches);
		}
	}

	// @see org.eclipse.search.internal.ui.text.FileSearchQuery
	// added due to changes in eclipse 3.4 API with the addition of LineElement
	private LineElement getLineElement(int offset,
			TextSearchMatchAccess matchRequestor) {
		int lineNumber = 1;
		int lineStart = 0;
		if (!fCachedMatches.isEmpty()) {
			// match on same line as last?
			FileMatch last = (FileMatch) fCachedMatches.get(fCachedMatches
					.size() - 1);
			LineElement lineElement = last.getLineElement();
			if (lineElement.contains(offset)) {
				return lineElement;
			}
			// start with the offset and line information from the last match
			lineStart = lineElement.getOffset() + lineElement.getLength();
			lineNumber = lineElement.getLine() + 1;
		}
		if (offset < lineStart) {
			return null; // offset before the last line
		}

		int i = lineStart;
		int contentLength = matchRequestor.getFileContentLength();
		while (i < contentLength) {
			char ch = matchRequestor.getFileContentChar(i++);
			if (ch == '\n' || ch == '\r') {
				if (ch == '\r' && i < contentLength
						&& matchRequestor.getFileContentChar(i) == '\n') {
					i++;
				}
				if (offset < i) {
					String lineContent = getContents(matchRequestor, lineStart,
							i); // include line delimiter
					return new LineElement(matchRequestor.getFile(),
							lineNumber, lineStart, lineContent);
				}
				lineNumber++;
				lineStart = i;
			}
		}
		if (offset < i) {
			String lineContent = getContents(matchRequestor, lineStart, i); // until
			// end
			// of
			// file
			return new LineElement(matchRequestor.getFile(), lineNumber,
					lineStart, lineContent);
		}
		return null; // offset outside of range
	}

	// @see org.eclipse.search.internal.ui.text.FileSearchQuery
	// added due to changes in eclipse 3.4 API with the addition of LineElement
	private static String getContents(TextSearchMatchAccess matchRequestor,
			int start, int end) {
		StringBuffer buf = new StringBuffer();
		for (int i = start; i < end; i++) {
			char ch = matchRequestor.getFileContentChar(i);
			if (Character.isWhitespace(ch) || Character.isISOControl(ch)) {
				buf.append(' ');
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}
}
