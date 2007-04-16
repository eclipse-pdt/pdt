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

import java.io.*;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Scanner;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.lr_parser;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;


/**
 * A PHP language parser for creating abstract syntax trees (ASTs).<p>
 * Example: Create basic AST from source string
 * <pre>
 * String source = ...;
 * Program program = ASTParser.parse(source);  
 * </pre>
 */
public class ASTParser {

	// version tags
	public static final String VERSION_PHP4 = PHPVersion.PHP4;
	public static final String VERSION_PHP5 = PHPVersion.PHP5;

	// php 5 analysis
	private static final PhpAstParser5 PHP_AST_PARSER5 = new PhpAstParser5();
	private static final PhpAstLexer5 PHP_AST_LEXER5 = new PhpAstLexer5(ASTParser.EMPTY_STRING_READER);

	// php 4 analysis	
	private static final PhpAstParser4 PHP_AST_PARSER4 = new PhpAstParser4();
	private static final PhpAstLexer4 PHP_AST_LEXER4 = new PhpAstLexer4(ASTParser.EMPTY_STRING_READER);

	// empty buffer
	private static final StringReader EMPTY_STRING_READER = new StringReader("");

	/**
	 * @param phpCode String - represents the source code of the PHP program
	 * @param aspTagsAsPhp boolean - true if % is used as PHP process intructor   
	 * @return the {@link Program} node generated from the given source
	 * @throws Exception
	 */
	public static final Program parse(String phpCode, boolean aspTagsAsPhp) throws Exception {
		StringReader reader = new StringReader(phpCode);
		return parse(reader, aspTagsAsPhp, VERSION_PHP5);
	}

	/**
	 * @param phpFile File - represents the source file of the PHP program
	 * @param aspTagsAsPhp boolean - true if % is used as PHP process intructor   
	 * @return the {@link Program} node generated from the given source PHP file
	 * @throws Exception
	 */
	public static final Program parse(File phpFile, boolean aspTagsAsPhp) throws Exception {
		final Reader reader = new FileReader(phpFile);
		return parse(reader, aspTagsAsPhp, VERSION_PHP5);
	}

	public static final Program parse(final IDocument phpDocument, boolean aspTagsAsPhp, String phpVersion) throws Exception {
		return parse(phpDocument, aspTagsAsPhp, phpVersion, 0, phpDocument.getLength());
	}

	public static final Program parse(final IDocument phpDocument, boolean aspTagsAsPhp, String phpVersion, final int offset, final int length) throws Exception {
		final Reader reader = new InputStreamReader(new InputStream() {
			private int index = offset;
			private final int size = offset + length;

			public int read() throws IOException {
				try {
					if (index < size) {
						return phpDocument.getChar(index++);
					}
					return -1;
				} catch (BadLocationException e) {
					throw new IOException(e.getMessage());
				}
			}
		});
		return parse(reader, aspTagsAsPhp, phpVersion);
	}

	public static final Program parse(IDocument phpDocument, boolean aspTagsAsPhp) throws Exception {
		return parse(phpDocument, aspTagsAsPhp, VERSION_PHP5);
	}

	/**
	 * @see #parse(String, boolean)
	 */
	public static final Program parse(String phpCode) throws Exception {
		return parse(phpCode, true);
	}

	/**
	 * @see #parse(File, boolean)
	 */
	public static final Program parse(File phpFile) throws Exception {
		return parse(phpFile, true);
	}

	/**
	 * @see #parse(Reader, boolean)
	 */
	public static final Program parse(Reader reader) throws Exception {
		return parse(reader, true, VERSION_PHP5);
	}

	/**
	 * @param reader
	 * @return the {@link Program} node generated from the given {@link Reader}
	 * @throws Exception
	 */
	public static Program parse(Reader reader, boolean aspTagsAsPhp, String phpVersion) throws Exception {
		final Scanner lexer = getLexer(reader, phpVersion, aspTagsAsPhp);
		final lr_parser phpParser = getParser(phpVersion);
		phpParser.setScanner(lexer);

		final Symbol symbol = phpParser.parse();
		return symbol == null ? null : (Program) symbol.value;
	}

	/**
	 * Constructs a scanner from a given reader
	 * @param reader
	 * @param phpVersion
	 * @param aspTagsAsPhp
	 * @return
	 * @throws IOException
	 */
	private static Scanner getLexer(Reader reader, String phpVersion, boolean aspTagsAsPhp) throws IOException {
		if (VERSION_PHP4.equals(phpVersion)) {
			final PhpAstLexer4 lexer4 = getLexer4(reader);
			lexer4.setUseAspTagsAsPhp(aspTagsAsPhp);
			return lexer4;
		} else if (VERSION_PHP5.equals(phpVersion)) {
			final PhpAstLexer5 lexer5 = getLexer5(reader);
			lexer5.setUseAspTagsAsPhp(aspTagsAsPhp);
			return lexer5;
		} else {
			throw new IllegalArgumentException("unrecognized version " + phpVersion);
		}
	}

	private static lr_parser getParser(String phpVersion) {
		if (VERSION_PHP4.equals(phpVersion)) {
			return PHP_AST_PARSER4;
		} else if (VERSION_PHP5.equals(phpVersion)) {
			return PHP_AST_PARSER5;
		} else {
			throw new IllegalArgumentException("unrecognized version " + phpVersion);
		}

	}

	/**
	 * @param reader
	 * @return the singleton {@link PhpAstLexer5}
	 */
	private static PhpAstLexer5 getLexer5(Reader reader) throws IOException {
		final PhpAstLexer5 phpAstLexer5 = ASTParser.PHP_AST_LEXER5;
		phpAstLexer5.yyreset(reader);
		phpAstLexer5.resetCommentList();
		return phpAstLexer5;
	}

	/**
	 * @param reader
	 * @return the singleton {@link PhpAstLexer5}
	 */
	private static PhpAstLexer4 getLexer4(Reader reader) throws IOException {
		final PhpAstLexer4 phpAstLexer4 = ASTParser.PHP_AST_LEXER4;
		phpAstLexer4.yyreset(reader);
		phpAstLexer4.resetCommentList();
		return phpAstLexer4;
	}
}
