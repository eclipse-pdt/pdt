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
package org.eclipse.php.internal.ui.corext.dom.fragments;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.corext.SourceRange;
import org.eclipse.php.internal.core.documentModel.parser.Scanner;
import org.eclipse.php.internal.core.documentModel.parser.php5.PhpLexer;

/**
 * This class houses a collection of static methods which do not refer to, or
 * otherwise depend on, other classes in this package. Each package-visible
 * method is called by more than one other class in this package. Since they do
 * not depend on other classes in this package, they could be moved to some less
 * specialized package.
 */
class Util {

	static boolean rangeIncludesNonWhitespaceOutsideRange(
			SourceRange selection, SourceRange nodes, IDocument document)
			throws BadLocationException {
		if (!selection.covers(nodes))
			return false;

		// TODO: skip leading comments. Consider that leading line comment must
		// be followed by newline!

		// check the start of the nodes and the selection
		if (!isJustWhitespace(selection.getOffset(), nodes.getOffset(),
				document))
			return true;

		// check the end of the nodes and the selection
		if (!isJustWhitespaceOrComment(nodes.getOffset() + nodes.getLength(),
				selection.getOffset() + selection.getLength(), document))
			return true;
		return false;
	}

	private static boolean isJustWhitespace(int start, int end, IDocument buffer)
			throws BadLocationException {
		if (start == end)
			return true;
		Assert.isTrue(start <= end);
		return 0 == buffer.get(start, end - start).trim().length();
	}

	private static boolean isJustWhitespaceOrComment(int start, int end,
			IDocument document) {
		if (start == end)
			return true;
		Assert.isTrue(start <= end);

		// gets the new text from the document
		String trimmedText;
		try {
			trimmedText = document.get(start, end - start).trim();
		} catch (BadLocationException e1) {
			return false;
		}

		// if there are no tokens in the trimmed text return true
		if (0 == trimmedText.length()) {
			return true;
		} else {
			Scanner scanner = new PhpLexer(new StringReader(trimmedText));
			scanner.initialize(PhpLexer.ST_PHP_IN_SCRIPTING);
			try {
				return scanner.yylex() == null;
			} catch (IOException e) {
				return false;
			}
		}
	}
}
