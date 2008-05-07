/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.parser;

import java.util.List;

import java_cup.runtime.Scanner;

/**
 * Description:   
 * @author moshe, 2006
 */
public interface AstLexer extends Scanner {

	public void resetCommentList();

	public List getCommentList();

	public void setUseAspTagsAsPhp(boolean useAspTagsAsPhp);

	public int getCurrentLine();

	public int getLength();

	/**
	 * Closes the input stream.
	 */
	public void yyclose() throws java.io.IOException;

	/**
	 * Closes the current stream, and resets the
	 * scanner to read from a new input stream.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public void yyreset(java.io.Reader reader) throws java.io.IOException;

	/**
	 * Returns the current lexical state.
	 */
	public int yystate();

	/**
	 * Enters a new lexical state
	 *
	 * @param newState the new lexical state
	 */
	public void yybegin(int newState);

	/**
	 * Returns the text matched by the current regular expression.
	 */
	public String yytext();

	/**
	 * Returns the character at position <tt>pos</tt> from the 
	 * matched text. 
	 * 
	 * It is equivalent to yytext().charAt(pos), but faster
	 *
	 * @param pos the position of the character to fetch. 
	 *            A value from 0 to yylength()-1.
	 *
	 * @return the character at position pos
	 */
	public char yycharat(int pos);

	/**
	 * Returns the length of the matched text region.
	 */
	public int yylength();

	public PDTSymbol __next_token() throws java.io.IOException;

}