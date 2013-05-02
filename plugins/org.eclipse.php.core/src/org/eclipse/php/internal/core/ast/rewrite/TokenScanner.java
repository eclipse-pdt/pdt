/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.rewrite;

/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
import java.io.IOException;

import java_cup.sym;
import java_cup.runtime.Symbol;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.scanner.AstLexer;
import org.eclipse.php.internal.core.ast.util.RandomAccessCharArrayReader;

/**
 * Wraps a scanner and offers convenient methods for finding tokens
 */
public class TokenScanner {

	public static final int END_OF_FILE = sym.EOF;
	public static final int LEXICAL_ERROR = 20002;
	private static final String[] MODIFIERS = { "public", "private", //$NON-NLS-1$ //$NON-NLS-2$
			"protected", "static", "abstract", "final" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	// public static final int DOCUMENT_ERROR= 20003;

	private AstLexer scanner;
	private RandomAccessCharArrayReader charReader;
	private Symbol currentToken;
	private int offset;

	/**
	 * Creates a TokenScanner
	 * 
	 * @param scanner
	 *            The scanner to be wrapped.
	 * @param content
	 *            The char array content of the scanner.
	 * @throws IOException
	 */
	public TokenScanner(AstLexer scanner, char[] content) throws IOException {
		this.scanner = scanner;
		this.charReader = new RandomAccessCharArrayReader(content);
		this.scanner.yyreset(this.charReader);
		this.scanner.resetCommentList();
	}

	/**
	 * Returns the wrapped scanner
	 * 
	 * @return IScanner
	 */
	public AstLexer getScanner() {
		return this.scanner;
	}

	/**
	 * Sets the scanner offset to the given offset.
	 * 
	 * @param offset
	 *            The offset to set
	 * @throws IOException
	 */
	public void setOffset(int offset) {
		this.offset = offset;
		charReader.reset(offset);
		try {
			scanner.yyreset(charReader);
			scanner.setInScriptingState();
			scanner.resetCommentList();
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	/**
	 * @return Returns the offset after the current token
	 */
	public int getCurrentEndOffset() {
		if (currentToken != null) {
			return offset + currentToken.right;
		}
		return 0;
	}

	/**
	 * @return Returns the start offset of the current token
	 */
	public int getCurrentStartOffset() {
		if (currentToken != null) {
			return offset + currentToken.left;
		}
		return 0;
	}

	/**
	 * @return Returns the length of the current token
	 */
	public int getCurrentLength() {
		return getCurrentEndOffset() - getCurrentStartOffset();
	}

	/**
	 * Reads the next token. Comments are always ignored.
	 * 
	 * @return Return the token {@link Symbol}.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public Symbol readNext() throws CoreException {
		currentToken = null;
		try {
			currentToken = this.scanner.next_token();
		} catch (Exception e) {
			throw new CoreException(createError(LEXICAL_ERROR, e.getMessage(),
					e));
		}
		if (currentToken.sym == END_OF_FILE) {
			throw new CoreException(createError(END_OF_FILE,
					"End Of File", null)); //$NON-NLS-1$
		}
		return currentToken;
	}

	/**
	 * Reads the next token from the given offset. Comments are always ignored.
	 * 
	 * @param offset
	 *            The offset to start reading from.
	 * @return Returns the token {@link Symbol}.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public Symbol readNext(int offset) throws CoreException {
		setOffset(offset);
		return readNext();
	}

	/**
	 * Reads the next token from the given offset and returns the start offset
	 * of the token. Comments are always ignored.
	 * 
	 * @param offset
	 *            The offset to start reading from.
	 * @return Returns the start position of the next token.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public int getNextStartOffset(int offset) throws CoreException {
		readNext(offset);
		return getCurrentStartOffset();
	}

	/**
	 * Reads the next token from the given offset and returns the offset after
	 * the token. Comments are always ignored.
	 * 
	 * @param offset
	 *            The offset to start reading from.
	 * @return Returns the start position of the next token.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public int getNextEndOffset(int offset) throws CoreException {
		readNext(offset);
		return getCurrentEndOffset();
	}

	/**
	 * Reads until a token is reached.
	 * 
	 * @param tok
	 *            The token to read to.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public void readToToken(Symbol tok) throws CoreException {
		currentToken = null;
		do {
			currentToken = readNext();
		} while (currentToken != null && currentToken.sym != tok.sym);
	}

	/**
	 * Reads until a token is reached, starting from the given offset.
	 * 
	 * @param tok
	 *            The token to read to.
	 * @param offset
	 *            The offset to start reading from.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public void readToToken(Symbol tok, int offset) throws CoreException {
		setOffset(offset);
		readToToken(tok);
	}

	/**
	 * Reads from the given offset until a token is reached and returns the
	 * start offset of the token.
	 * 
	 * @param token
	 *            The token to be found.
	 * @param startOffset
	 *            The offset to start reading from.
	 * @return Returns the start position of the found token.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public int getTokenStartOffset(Symbol token, int startOffset)
			throws CoreException {
		readToToken(token, startOffset);
		return getCurrentStartOffset();
	}

	/**
	 * Reads from the given offset until a token is reached and returns the
	 * offset after the token.
	 * 
	 * @param token
	 *            The token to be found.
	 * @param startOffset
	 *            Offset to start reading from
	 * @return Returns the end position of the found token.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public int getTokenEndOffset(Symbol token, int startOffset)
			throws CoreException {
		readToToken(token, startOffset);
		return getCurrentEndOffset();
	}

	/**
	 * Reads from the given offset until a token is reached and returns the
	 * offset after the previous token.
	 * 
	 * @param token
	 *            The token to be found.
	 * @param startOffset
	 *            The offset to start scanning from.
	 * @return Returns the end offset of the token previous to the given token.
	 * @exception CoreException
	 *                Thrown when the end of the file has been reached (code
	 *                END_OF_FILE) or a lexical error was detected while
	 *                scanning (code LEXICAL_ERROR)
	 */
	public int getPreviousTokenEndOffset(Symbol token, int startOffset)
			throws CoreException {
		setOffset(startOffset);
		int res = startOffset;
		Symbol curr = readNext();
		while (curr != null && curr.sym != token.sym) {
			res = getCurrentEndOffset();
			curr = readNext();
		}
		return res;
	}

	/**
	 * Returns if the token is a comment. The tokens that we read from the
	 * scanner are always non-comments. The comments are save in a separate
	 * structure in the lexer.
	 * 
	 * @param token
	 * @return false
	 */
	public static boolean isComment(Symbol token) {
		// The tokens that we read from the scanner are always non-comments.
		// The comments are save in a separate structure in the lexer.
		return false;

	}

	/**
	 * Returns true if and only if the token's value is one of the modifiers:
	 * "public", "private", "protected", "static", "abstract", "final".
	 * 
	 * @param token
	 *            A {@link Symbol} token.
	 * @return True, if the token's value is a modifier; False, otherwise.
	 */
	public static boolean isModifier(Symbol token) {
		if (token == null || token.value == null) {
			return false;
		}
		for (String modifier : MODIFIERS) {
			if (token.value.equals(modifier)) {
				return true;
			}
		}
		return false;
	}

	public static IStatus createError(int code, String message,
			Throwable throwable) {
		return new Status(IStatus.ERROR, PHPCorePlugin.ID, code, message,
				throwable);
	}

}
